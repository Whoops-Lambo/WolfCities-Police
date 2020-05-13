package me.pixelmania.wolfpolice.listeners;

import java.time.Instant;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.Prunt.simplejail.Main;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class PlayerJailed implements Listener
{
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent event)
	{
		if (event.getCause() == DamageCause.ENTITY_ATTACK
				&& event.getEntityType() == EntityType.PLAYER
				&& event.getDamager().getType() == EntityType.PLAYER)
		{
			Player damagedPlayer = (Player) event.getEntity();
			Player playerDamager = (Player) event.getDamager();
			if (playerDamager.getInventory().getItemInMainHand() != null
					&& playerDamager.getInventory().getItemInMainHand().getType() == Material.STICK
					&& playerDamager.getInventory().getItemInMainHand().hasItemMeta()
					&& playerDamager.getInventory().getItemInMainHand().getItemMeta().hasDisplayName())
			{
				ItemStack item = playerDamager.getInventory().getItemInMainHand();
				if (item.getItemMeta().getDisplayName().equals(ChatFormat.colors(Core.config.getString("police-baton.name"))))
				{
					if (Core.playersOnDuty.contains(playerDamager.getUniqueId()))
					{
						if (Core.playersOnDuty.contains(damagedPlayer.getUniqueId()))
						{
							playerDamager.sendMessage(ChatFormat.colors(Core.config.getString("messages.cannot-jail-officer")));
							return;
						}
						event.setCancelled(true);
						Main simpleJail = (Main) Bukkit.getPluginManager().getPlugin("SimpleJail");
						if (simpleJail.isJailed(damagedPlayer))
						{
							playerDamager.sendMessage(ChatFormat.colors(Core.config.getString("messages.player-already-jailed")));
						}
						else
						{
								if (Core.config.getBoolean("options.arrested-sound"))
								{
									playerDamager.getLocation().getWorld().playSound(playerDamager.getLocation(), Sound.BLOCK_ANVIL_PLACE, (float) 0.5, 1);
								}
								long time = Instant.now().getEpochSecond() + Core.config.getInt("options.jail-time") * 60;
								simpleJail.jail(damagedPlayer, time, "Jailed by " + playerDamager.getName());
								playerDamager.sendMessage(ChatFormat.colors(Core.config.getString("messages.jailed-player").replace("</player>", damagedPlayer.getName())));
								for (String command : Core.config.getStringList("jailed-commands")) {
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("</player>", damagedPlayer.getName()).replace("</jailer>", playerDamager.getName()));
								}
								boolean confiscated = false;
								String confiscatedItems = "";
								if (damagedPlayer.getInventory().getContents() != null)
								{
									for (ItemStack drugItem : damagedPlayer.getInventory().getContents())
									{
										if (drugItem != null)
										{
											if (Core.config.getStringList("drug-items").contains(drugItem.getType().name().toLowerCase())
													|| Core.config.getStringList("drug-items").contains(drugItem.getType().name().toUpperCase()))
											{
												if (!confiscated)
												{
													confiscated = true;
													if (drugItem.getItemMeta().hasDisplayName())
													{
														confiscatedItems = drugItem.getItemMeta().getDisplayName() + "&r&7x&f" + drugItem.getAmount();
													}
													else
													{
														confiscatedItems = StringUtils.capitalize(drugItem.getType().name().toLowerCase()) + "&r&7x&f" + drugItem.getAmount();
													}
												}
												else
												{
													if (drugItem.getItemMeta().hasDisplayName())
													{
														confiscatedItems += " | " + drugItem.getItemMeta().getDisplayName() + "&r&7x&f" + drugItem.getAmount();
													}
													else
													{
														confiscatedItems += " | " + StringUtils.capitalize(drugItem.getType().name().toLowerCase()) + "&r&7x&f" + drugItem.getAmount();
													}
												}
												final Map<Integer, ItemStack> itemMap = playerDamager.getInventory().addItem(drugItem);
												if (!itemMap.isEmpty())
												{
													Location location = playerDamager.getLocation();
													location.getWorld().dropItem(location, drugItem);
												}
												drugItem.setAmount(0);
											}
										}
									}
								}
								if (!Core.config.getBoolean("simplejail-jailed-message"))
								{
									damagedPlayer.sendMessage(ChatFormat.colors(Core.config.getString("messages.jailed-by-player").replace("</player>", playerDamager.getName())));
								}
								if (confiscated)
								{
									playerDamager.sendMessage(ChatFormat.colors(Core.config.getString("messages.confiscated-items").replace("</items>", confiscatedItems)));
									damagedPlayer.sendMessage(ChatFormat.colors(Core.config.getString("messages.player-confiscated-items").replace("</items>", confiscatedItems)).replace("</player>", playerDamager.getName()));
								}
						}
					}
				}
			}
		}
	}
}
