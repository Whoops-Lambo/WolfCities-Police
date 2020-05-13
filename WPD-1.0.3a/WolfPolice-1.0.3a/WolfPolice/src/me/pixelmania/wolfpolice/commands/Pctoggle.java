package me.pixelmania.wolfpolice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Pctoggle implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.pc.toggle"))
			{
				if (Core.playersInPoliceRadio.contains(player.getUniqueId()))
				{
					Core.playersInPoliceRadio.remove(player.getUniqueId());
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.radio-toggled").replace("</status>", "off")));
				}
				else
				{
					Core.playersInPoliceRadio.add(player.getUniqueId());
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.radio-toggled").replace("</status>", "on")));
				}
			}
			else
			{
				player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
			}
		}
		else
		{
			sender.sendMessage("[WolfPolice] This command can only be used by the Police team!");
		}
		return true;
	}

}
