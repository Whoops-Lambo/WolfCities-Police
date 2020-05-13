package me.pixelmania.wolfpolice.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Additems implements CommandExecutor
{

	public static String inventoryName = "Adding items to /duty";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.additems"))
			{
				final Inventory inventory = Bukkit.createInventory(player, 9, inventoryName);
				if (!Core.dutyItemsConfig.contains("items") || Core.dutyItemsConfig.get("items") == null)
				{
					player.openInventory(inventory);
				}
				else
				{
					@SuppressWarnings("unchecked")
					final List<ItemStack> items = (List<ItemStack>) Core.dutyItemsConfig.get("items");
					for (int i = 0; i != items.size(); i++)
					{
						if (items.get(i) != null)
						{
							inventory.setItem(i, items.get(i));
						}
					}
					player.openInventory(inventory);
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
