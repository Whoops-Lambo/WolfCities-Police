package me.pixelmania.wolfpolice.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.pixelmania.wolfpolice.functions.PlayerFiles;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.skinsrestorer.WPSkinsRestorer;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Duty implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.duty"))
			{
				if (Core.playersOnDuty.contains(player.getUniqueId()))
				{
					if (PlayerFiles.getConfig(player) != null)
					{
						FileConfiguration playerConfig = PlayerFiles.getConfig(player);
						playerConfig.set("on-duty", false);
						if (PlayerFiles.saveConfig(player))
						{
							Core.playersOnDuty.remove(player.getUniqueId());
							player.getInventory().clear();
							player.sendMessage(ChatFormat.colors(Core.config.getString("messages.duty-toggled").replace("</status>", "off")));
							if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
							{
								WPSkinsRestorer.applySkin(player, true);
							}
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
						else
						{
							player.kickPlayer("error.wolfpolice.files: Couldn't save your player file. Please try to rejoin.");
						}
					}
					else
					{
						if (PlayerFiles.putConfig(player))
						{
							Core.playersOnDuty.remove(player.getUniqueId());
							player.sendMessage(ChatFormat.colors(Core.config.getString("messages.duty-toggled").replace("</status>", "off")));
							if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
							{
								WPSkinsRestorer.applySkin(player, true);
							}
						}
						else
						{
							player.kickPlayer("error.wolfpolice.files: Couldn't find and create a player file for you. Please try to rejoin.");
						}
					}
				}
				else
				{
					if (PlayerFiles.getConfig(player) != null)
					{
						FileConfiguration playerConfig = PlayerFiles.getConfig(player);
						playerConfig.set("on-duty", true);
						playerConfig.set("inventory.items", player.getInventory().getContents());
						playerConfig.set("inventory.armor", player.getInventory().getArmorContents());
						if (PlayerFiles.saveConfig(player))
						{
							player.getInventory().clear();
							Core.playersOnDuty.add(player.getUniqueId());
							player.sendMessage(ChatFormat.colors(Core.config.getString("messages.duty-toggled").replace("</status>", "on")));
							if (Core.dutyItemsConfig.contains("items"))
							{
								@SuppressWarnings("unchecked")
								final List<ItemStack> dutyItems = (List<ItemStack>) Core.dutyItemsConfig.get("items");
								for (int i = 0; i != dutyItems.size(); i++)
								{
									if (dutyItems.get(i) != null)
									{
										player.getInventory().setItem(i, dutyItems.get(i));
									}
								}
							}
							if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
							{
								WPSkinsRestorer.applySkin(player, false);
							}
						}
					}
					else
					{
						if (PlayerFiles.putConfig(player))
						{
							FileConfiguration playerConfig = PlayerFiles.getConfig(player);
							playerConfig.set("on-duty", true);
							playerConfig.set("inventory.items", player.getInventory().getContents());
							playerConfig.set("inventory.armor", player.getInventory().getArmorContents());
							if (PlayerFiles.saveConfig(player))
							{
								player.getInventory().clear();
								Core.playersOnDuty.add(player.getUniqueId());
								player.sendMessage(ChatFormat.colors(Core.config.getString("messages.duty-toggled").replace("</status>", "on")));
								if (Core.dutyItemsConfig.contains("items"))
								{
									@SuppressWarnings("unchecked")
									final List<ItemStack> dutyItems = (List<ItemStack>) Core.dutyItemsConfig.get("items");
									for (int i = 0; i != dutyItems.size(); i++)
									{
										if (dutyItems.get(i) != null)
										{
											player.getInventory().setItem(i, dutyItems.get(i));
										}
									}
								}
								if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
								{
									WPSkinsRestorer.applySkin(player, false);
								}
							}
						}
						else
						{
							player.kickPlayer("error.wolfpolice.files: Couldn't find and create a player file for you. Please try to rejoin.");
						}
					}
				}
			}
			else
			{
				player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
			}
		}
		else
		{
			sender.sendMessage("[WolfPolice] This command can only be used by players!");
		}
		return true;
	}

}
