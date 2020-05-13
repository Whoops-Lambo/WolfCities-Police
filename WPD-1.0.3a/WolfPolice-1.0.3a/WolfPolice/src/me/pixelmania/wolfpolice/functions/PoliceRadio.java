package me.pixelmania.wolfpolice.functions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PoliceRadio
{
	
	public static void sendMessage(String message, Player sender)
	{
			for (Player policePlayer : Bukkit.getOnlinePlayers())
			{
				if (PermissionsEx.getUser(policePlayer).inGroup("jr.police") || PermissionsEx.getUser(policePlayer).inGroup("sr.police") || PermissionsEx.getUser(policePlayer).inGroup("cheif") || PermissionsEx.getUser(policePlayer).inGroup("Chief") || PermissionsEx.getUser(policePlayer).inGroup("MinisterS"))
				{
					policePlayer.sendMessage(ChatFormat.colors(Core.config.getString("options.police-radio-format").replace("</player>", sender.getName()).replace("</message>", message).replace("</l_message>", message.toLowerCase())).replace("</u_message>", message.toUpperCase()));
				}
			}
	}
	
}
