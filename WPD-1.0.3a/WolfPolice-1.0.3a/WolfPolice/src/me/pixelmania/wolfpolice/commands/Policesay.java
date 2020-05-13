package me.pixelmania.wolfpolice.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Policesay implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wpd.announce"))
			{
				if (args.length > 0)
				{
					String message = "";
					for (int i = 0; i != args.length; i++)
					{
						if (message.isEmpty())
						{
							message = args[0];
						}
						else
						{
							message += " " + args[i];
						}
					}
					for (Player messagePlayer : Bukkit.getOnlinePlayers())
					{
						if (player.hasPermission("wolfcolors.allow"))
						{
							messagePlayer.sendMessage(ChatFormat.colors(Core.config.getString("policesay-options.format").replace("</announcer>", player.getName()).replace("</message>", ChatFormat.colors(message))));
						}
						else
						{
							messagePlayer.sendMessage(ChatFormat.colors(Core.config.getString("policesay-options.format").replace("</announcer>", player.getName()).replace("</message>", message)));
						}
					}
				}
				else
				{
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-usage").replace("</usage>", "/policesay <message>")));
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
