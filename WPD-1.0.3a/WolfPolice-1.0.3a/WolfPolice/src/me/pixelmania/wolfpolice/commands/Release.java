package me.pixelmania.wolfpolice.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Prunt.simplejail.Main;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class Release implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			if (player.hasPermission("wolfpd.release"))
			{
				if (args.length == 0)
				{
					player.sendMessage(ChatFormat.colors(Core.config.getString("messages.invalid-usage").replace("</usage>", "/release <player>")));
				}
				else
				{
					if (Bukkit.getServer().getPlayer(args[0]) != null)
					{
						Player releasePlayer = Bukkit.getServer().getPlayer(args[0]);
						Main simpleJail = (Main) Bukkit.getPluginManager().getPlugin("SimpleJail");
						if (simpleJail.isJailed(releasePlayer))
						{
							simpleJail.unjail(releasePlayer);
							sender.sendMessage(ChatFormat.colors(Core.config.getString("messages.released-player").replace("</player>", releasePlayer.getName())));
							releasePlayer.sendMessage(ChatFormat.colors(Core.config.getString("messages.released-by-player").replace("</player>", sender.getName())));
						}
						else
						{
							sender.sendMessage(ChatFormat.colors(Core.config.getString("messages.player-not-jailed")));
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
