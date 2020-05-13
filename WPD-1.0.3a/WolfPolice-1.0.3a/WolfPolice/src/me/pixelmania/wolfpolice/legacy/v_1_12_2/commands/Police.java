package me.pixelmania.wolfpolice.legacy.v_1_12_2.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Police implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (!player.hasPermission("wolfpd.policetp") && !player.hasPermission("wpd.promote") && !player.hasPermission("wpd.demote"))
			{
				player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
			}
			else
			{
				if (args.length > 1 && args[0].equalsIgnoreCase("tp") || args.length > 2 && args[1].equalsIgnoreCase("promote") && args[2].equalsIgnoreCase("jrpolice") || args.length > 2 && args[1].equalsIgnoreCase("promote") && args[2].equalsIgnoreCase("srpolice") || args.length > 1 && args[1].equalsIgnoreCase("demote"))
				{
					if (args[0].equalsIgnoreCase("tp"))
					{
						if (!player.hasPermission("wolfpd.policetp"))
						{
							player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
						}
						else
						{
							if (Bukkit.getServer().getPlayer(args[1]) != null)
							{
								Player teleportPlayer = Bukkit.getServer().getPlayer(args[1]);
								Location location = teleportPlayer.getLocation();
								Location newLocation = location.clone();
								newLocation.setX((double) (int) newLocation.getX());
								newLocation.setY((double) (int) newLocation.getY());
								newLocation.setZ((double) (int) newLocation.getZ());
								Random random = new Random();
								int X1 = 100 + random.nextInt(300);
								int Z1 = 100 + random.nextInt(300);
								int X2 = -100 - random.nextInt(300);
								int Z2 = -100 - random.nextInt(300);
								switch (random.nextInt(2))
								{
								case (0): newLocation.add(X1, 0, Z1);
								case (1): newLocation.add(X2, 0, Z2);
								}
								for (int i = (int) newLocation.getY(); i != newLocation.getWorld().getMaxHeight(); i++)
								{
									newLocation.add(0, 1, 0);
									if (newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 0, 0.5)).getType() != Material.AIR)
									{
										if (newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType() == Material.AIR
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType() == Material.AIR
												|| newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType().toString().equals("LONG_GRASS")
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType() == Material.AIR
												|| newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType().toString().equals("DOUBLE_PLANT")
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType().toString().equals("DOUBLE_PLANT"))
										{
											player.teleport(newLocation.clone().add(0.5, 1, 0.5));
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.teleported-near").replace("</player>", teleportPlayer.getName())));
											return true;
										}
									}
								}
								newLocation.setY(61);
								for (int i = 61; i != newLocation.getWorld().getMaxHeight(); i++)
								{
									newLocation.add(0, 1, 0);
									if (newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 0, 0.5)).getType() != Material.AIR)
									{
										if (newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType() == Material.AIR
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType() == Material.AIR
												|| newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType().toString().equals("LONG_GRASS")
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType() == Material.AIR
												|| newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType().toString().equals("DOUBLE_PLANT")
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType().toString().equals("DOUBLE_PLANT"))
										{
											player.teleport(newLocation.clone().add(0.5, 1, 0.5));
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.teleported-near").replace("</player>", teleportPlayer.getName())));
											return true;
										}
									}
								}
								newLocation.setY(3);
								for (int i = 3; i != newLocation.getWorld().getMaxHeight(); i++)
								{
									newLocation.add(0, 1, 0);
									if (newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 0, 0.5)).getType() != Material.AIR)
									{
										if (newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType() == Material.AIR
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType() == Material.AIR
												|| newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType().toString().equals("LONG_GRASS")
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType() == Material.AIR
												|| newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 1, 0.5)).getType().toString().equals("DOUBLE_PLANT")
												&& newLocation.getWorld().getBlockAt(newLocation.clone().add(0.5, 2, 0.5)).getType().toString().equals("DOUBLE_PLANT"))
										{
											player.teleport(newLocation.clone().add(0.5, 1, 0.5));
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.teleported-near").replace("</player>", teleportPlayer.getName())));
											return true;
										}
									}
								}
								player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-safe-location")));
							}
							else
							{
								player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-player")));
							}
						}
					}
					else
					{
						if (args[1].equalsIgnoreCase("promote"))
						{
							if (player.hasPermission("wpd.promote"))
							{
								if (Bukkit.getServer().getPlayer(args[0]) != null)
								{
									Player promotedPlayer = Bukkit.getServer().getPlayer(args[0]);
									if (args[2].equalsIgnoreCase("jrpolice"))
									{
										if (PermissionsEx.getUser(promotedPlayer).inGroup("jr.police"))
										{
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.already-rank").replace("</player>", promotedPlayer.getName()).replace("</rank>", "Jr.Police")));
										}
										else
										{
											if (PermissionsEx.getUser(promotedPlayer).inGroup("citizen"))
											{
												PermissionsEx.getUser(promotedPlayer).removeGroup("citizen");
											}
											if (PermissionsEx.getUser(promotedPlayer).inGroup("sr.police"))
											{
												PermissionsEx.getUser(promotedPlayer).removeGroup("sr.police");
											}
											PermissionsEx.getUser(promotedPlayer).addGroup("jr.police");
											PermissionsEx.getUser(promotedPlayer).save();
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.player-promoted").replace("</player>", promotedPlayer.getName()).replace("</rank>", "Jr.Police")));
											promotedPlayer.sendMessage(ChatFormat.colors(Core.config.getString("messages.got-promoted").replace("</rank>", "Jr.Police")));
										}
									}
									else if (args[2].equalsIgnoreCase("srpolice"))
									{
										if (PermissionsEx.getUser(promotedPlayer).inGroup("sr.police"))
										{
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.already-rank").replace("</player>", promotedPlayer.getName()).replace("</rank>", "Sr.Police")));
										}
										else
										{
											if (PermissionsEx.getUser(promotedPlayer).inGroup("citizen"))
											{
												PermissionsEx.getUser(promotedPlayer).removeGroup("citizen");
											}
											if (PermissionsEx.getUser(promotedPlayer).inGroup("jr.police"))
											{
												PermissionsEx.getUser(promotedPlayer).removeGroup("jr.police");
											}
											PermissionsEx.getUser(promotedPlayer).addGroup("sr.police");
											PermissionsEx.getUser(promotedPlayer).save();
											player.sendMessage(ChatFormat.colors(Core.config.getString("messages.player-promoted").replace("</player>", promotedPlayer.getName()).replace("</rank>", "Sr.Police")));
											promotedPlayer.sendMessage(ChatFormat.colors(Core.config.getString("messages.got-promoted").replace("</rank>", "Sr.Police")));
										}
									}
								}
								else
								{
									player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-player")));
								}	
							}
							else
							{
								player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
							}
						}
						else if (args[1].equalsIgnoreCase("demote"))
						{
							if (player.hasPermission("wpd.demote"))
							{
								if (Bukkit.getServer().getPlayer(args[0]) != null)
								{
									Player demotedPlayer = Bukkit.getServer().getPlayer(args[0]);
									if (!PermissionsEx.getUser(demotedPlayer).inGroup("jr.police") && !PermissionsEx.getUser(demotedPlayer).inGroup("sr.police"))
									{
										player.sendMessage(ChatFormat.colors(Core.config.getString("messages.already-citizen").replace("</player>", demotedPlayer.getName())));
									}
									else
									{
										if (PermissionsEx.getUser(demotedPlayer).inGroup("jr.police"))
										{
											PermissionsEx.getUser(demotedPlayer).removeGroup("jr.police");
										}
										if (PermissionsEx.getUser(demotedPlayer).inGroup("sr.police"))
										{
											PermissionsEx.getUser(demotedPlayer).removeGroup("sr.police");
										}
										PermissionsEx.getUser(demotedPlayer).addGroup("citizen");
										PermissionsEx.getUser(demotedPlayer).save();
										player.sendMessage(ChatFormat.colors(Core.config.getString("messages.player-demoted").replace("</player>", demotedPlayer.getName())));
										player.sendMessage(ChatFormat.colors(Core.config.getString("messages.got-demoted")));
										if (Core.playersInPoliceRadio.contains(demotedPlayer.getUniqueId()))
										{
											Core.playersInPoliceRadio.remove(demotedPlayer.getUniqueId());
										}
										if (Core.playersOnDuty.contains(demotedPlayer.getUniqueId()))
										{
											Core.playersOnDuty.remove(demotedPlayer.getUniqueId());
											FileConfiguration playerConfig = Core.playerConfigs.get(demotedPlayer.getUniqueId());
											playerConfig.set("on-duty", false);
											if (PlayerFiles.saveConfig(demotedPlayer))
											{
												demotedPlayer.getInventory().clear();
												@SuppressWarnings("unchecked")
												final List<ItemStack> items = (List<ItemStack>) playerConfig.get("inventory.items");
												for (int i = 0; i != items.size(); i++)
												{
													if (items.get(i) != null)
													{
														demotedPlayer.getInventory().setItem(i, items.get(i));
													}
												}
												@SuppressWarnings("unchecked")
												final List<ItemStack> armor = (List<ItemStack>) playerConfig.get("inventory.armor");
												final ItemStack[] armorList = new ItemStack[4];
												for (int i = 0; i != armor.size(); i++)
												{
													armorList[i] = armor.get(i);
												}
												demotedPlayer.getInventory().setArmorContents(armorList);
												playerConfig = Core.playerConfigs.get(demotedPlayer.getUniqueId());
												playerConfig.set("inventory", null);
												PlayerFiles.saveConfig(demotedPlayer);
											}
											if (Core.SkinsRestorer && Core.config.getBoolean("options.skinsrestorer-police-skins"))
											{
												WPSkinsRestorer.applySkin(demotedPlayer, true);
											}
										}
									}
								}
								else
								{
									player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-player")));
								}
							}
							else
							{
								player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
							}
						}
					}
				}
				else
				{
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-usage").replace("</usage>", "/police tp <player> | /police <player> promote/demote jrpolice/srpolice/citizen")));
				}
			}
		}
		else
		{
			sender.sendMessage("[WolfPolice] This command can only be used by the Police team!");
		}
		return true;
	}

}
