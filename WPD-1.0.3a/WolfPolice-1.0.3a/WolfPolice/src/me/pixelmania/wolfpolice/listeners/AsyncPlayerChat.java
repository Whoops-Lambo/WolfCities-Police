package me.pixelmania.wolfpolice.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.pixelmania.wolfpolice.functions.PoliceRadio;
import me.pixelmania.wolfpolice.main.Core;
import me.pixelmania.wolfpolice.utils.ChatFormat;

public class AsyncPlayerChat implements Listener
{
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		if (Core.playersInPoliceRadio.contains(player.getUniqueId()))
		{
			event.setCancelled(true);
			PoliceRadio.sendMessage(ChatFormat.stripColors(event.getMessage()), player);
		}
	}
	
}
