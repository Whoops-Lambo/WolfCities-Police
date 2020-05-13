package me.pixelmania.wolfpolice.utils;

import org.bukkit.ChatColor;

public class ChatFormat
{
	
	public static String colors(String string)
	{
		if (string == null) return "";
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static String stripColors(String string)
	{
		if (string == null) return "";
		String[] colorCodes = {"&4", "&c", "&6", "&e", "&2", "&a", "&b", "&3", "&1", "&9", "&d", "&5", "&f", "&7", "&8", "&0", "&r", "&l", "&o", "&n", "&m", "&k",
				"§4", "§c", "§6", "§e", "§2", "§a", "§b", "§3", "§1", "§9", "§d", "§5", "§f", "§7", "§8", "§0", "§r", "§l", "§o", "§n", "§m", "§k",
				"&C", "&E", "&A", "&B", "&D", "&F", "&R", "&L", "&O", "&N", "&M", "&K",
				"§C", "§E", "§A", "§B", "§D", "§F", "§R", "§L", "§O", "§N", "§M", "§K"};
		String newString = string;
		for (int i = 0; i!= colorCodes.length; i++)
		{
			newString = newString.replace(colorCodes[i], "");
		}
		return newString;
	}
	
}
