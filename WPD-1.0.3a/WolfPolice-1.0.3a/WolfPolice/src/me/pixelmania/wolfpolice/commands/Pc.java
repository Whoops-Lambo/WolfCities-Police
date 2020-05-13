package me.pixelmania.wolfpolice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.functions.PoliceRadio;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Pc implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.pc"))
			{
				if (args.length == 0)
				{
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-usage").replace("</usage>", "/pc <message>")));
				}
				else
				{
					String message = args[0];
					for (int i = 1; i != args.length; i++)
					{
						message += " " + args[i];
					}
					PoliceRadio.sendMessage(ChatFormat.stripColors(message), player);
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
