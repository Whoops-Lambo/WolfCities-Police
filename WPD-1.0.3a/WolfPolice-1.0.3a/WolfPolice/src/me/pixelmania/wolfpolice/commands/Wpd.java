package me.pixelmania.wolfpolice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Wpd implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.wpd"))
			{
				Core.plugin.reloadConfig();
				Core.config = Core.plugin.getConfig();
				player.sendMessage(ChatFormat.colors(Core.config.getString("messages.reloaded-config")));
			}
			else
			{
				player.sendMessage(ChatFormat.colors(Core.config.getString("messages.no-permission")));
			}
		}
		else
		{
			Core.plugin.reloadConfig();
			Core.config = Core.plugin.getConfig();
			sender.sendMessage(ChatFormat.stripColors(Core.config.getString("messages.reloaded-config")));
		}
		return true;
	}

}
