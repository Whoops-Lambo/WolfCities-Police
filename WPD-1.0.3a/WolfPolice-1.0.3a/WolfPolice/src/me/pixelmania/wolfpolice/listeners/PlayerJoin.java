package me.pixelmania.wolfpolice.listeners;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.pixelmania.wolfpolice.functions.PlayerFiles;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.skinsrestorer.WPSkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;

public class PlayerJoin implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		new BukkitRunnable()
		{

			@Override
			public void run() {
				Player player = event.getPlayer();
				if (!PlayerFiles.putConfig(player))
				{
					player.kickPlayer("error.wolfpolice.files: Couldn't create a player file for you. Please try to rejoin.");
				}
				else
				{
					FileConfiguration playerConfig = Core.playerConfigs.get(player.getUniqueId());
					if (playerConfig.contains("on-duty")
							&& playerConfig.getBoolean("on-duty"))
					{
						playerConfig.set("on-duty", false);
						if (PlayerFiles.saveConfig(player))
						{
							player.getInventory().clear();
							@SuppressWarnings("unchecked")
							final List<ItemStack> items = (List<ItemStack>) playerConfig.get("inventory.items");
							for (int i = 0; i != items.size(); i++)
							{
								if (items.get(i) != null)
								{
									player.getInventory().setItem(i, items.get(i));
								}
							}
							@SuppressWarnings("unchecked")
							final List<ItemStack> armor = (List<ItemStack>) playerConfig.get("inventory.armor");
							final ItemStack[] armorList = new ItemStack[4];
							for (int i = 0; i != armor.size(); i++)
							{
								armorList[i] = armor.get(i);
							}
							player.getInventory().setArmorContents(armorList);
							playerConfig = Core.playerConfigs.get(player.getUniqueId());
							playerConfig.set("inventory", null);
							PlayerFiles.saveConfig(player);
							if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
							{
								SkinsRestorer skinsRestorer = Core.getPlugin(SkinsRestorer.class);
								SkinsRestorerBukkitAPI skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();
								skinsRestorerBukkitAPI.applySkin(player, skinsRestorerBukkitAPI.getSkinData(player.getName()));
							}
						}
					}
					else
					{
						new BukkitRunnable()
						{

							@Override
							public void run()
							{
								if (player.isOnline())
								{
									if (Core.playersOnDuty.contains(player.getUniqueId()))
									{
										this.cancel();
									}
									else
									{
										if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
										{
											if (Core.config.getStringList("options.skinsrestorer-skin-names").contains(WPSkinsRestorer.getSkin(player)))
											{
												WPSkinsRestorer.applySkin(player, true);
											}
											else
											{
												this.cancel();
											}
										}
										else
										{
											this.cancel();
										}
									}
								}
								else
								{
									this.cancel();
								}
							}
							
						}.runTaskLater(Core.plugin, 20L);
					}
				}
			}
			
		}.runTaskLater(Core.plugin, 3L);
	}
}
