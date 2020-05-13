package me.pixelmania.wolfpolice.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Getbaton implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.getbaton"))
			{
				ItemStack baton = new ItemStack(Material.STICK);
				ItemMeta batonMeta = baton.getItemMeta();
				batonMeta.setDisplayName(ChatFormat.colors(Core.config.getString("police-baton.name")));
				List<String> batonLore = new ArrayList<String>();
				if (Core.config.get("police-baton.lore") != null)
				{
					for (String lore : Core.config.getStringList("police-baton.lore"))
					{
						batonLore.add(ChatFormat.colors(lore));
					}
				}
				batonMeta.setLore(batonLore);
				baton.setItemMeta(batonMeta);
				final Map<Integer, ItemStack> giveItem = player.getInventory().addItem(baton);
				if (!giveItem.isEmpty())
				{
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.inventory-full-baton")));
				}
			}
			else
			{
				player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
			}
		}
		else
		{
			sender.sendMessage("[WolfPolice] This command can only be used by admins and minister of Security!");
		}
		return true;
	}

}
