package me.pixelmania.wolfpolice.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.functions.PlayerFiles;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Incidents implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.incidents"))
			{
				if (args.length == 0)
				{
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-usage").replace("</usage>", "/incidents <player>")));
				}
				else if (args.length > 0)
				{
					if (Bukkit.getServer().getPlayer(args[0]) != null)
					{
						Player incidentsPlayer = Bukkit.getServer().getPlayer(args[0]);
						if (PlayerFiles.getConfig(incidentsPlayer) == null && !PlayerFiles.putConfig(incidentsPlayer) || !PlayerFiles.getConfig(incidentsPlayer).contains("incidents"))
						{
							player.sendMessage(ChatFormat.colors(Core.config.getString("messages.incidents").replace("</amount>", "0")));
						}
						else
						{
							player.sendMessage(ChatFormat.colors(Core.config.getString("messages.incidents").replace("</amount>", String.valueOf(PlayerFiles.getConfig(incidentsPlayer).getInt("incidents")))));
						}
					}
					else
					{
						player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-player")));
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
			sender.sendMessage("[WolfPolice] This command can only be used by the Police team!");
		}
		return true;
	}

}
