package me.pixelmania.wolfpolice.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.pixelmania.wolfpolice.functions.PlayerFiles;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.skinsrestorer.WPSkinsRestorer;

public class PlayerQuit implements Listener
{
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		if (Core.playersOnDuty.contains(playerUUID))
		{
			Core.playersOnDuty.remove(playerUUID);
			Player player = event.getPlayer();
			FileConfiguration playerConfig = Core.playerConfigs.get(player.getUniqueId());
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
			}
			if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
			{
				WPSkinsRestorer.applySkin(player, true);
			}
		}
		if (Core.playersInPoliceRadio.contains(playerUUID))
		{
			Core.playersInPoliceRadio.remove(playerUUID);
		}
		if (Core.playerConfigs.containsKey(playerUUID))
		{
			Core.playerConfigs.remove(playerUUID);
		}
		if (Core.playerFiles.containsKey(playerUUID))
		{
			Core.playerFiles.remove(playerUUID);
		}
	}
	
}
