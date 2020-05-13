package me.pixelmania.wolfpolice.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import me.pixelmania.wolfpolice.main.Core;

public class PlayerDropItem implements Listener
{

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();
		if (Core.playersOnDuty.contains(player.getUniqueId()))
		{
			event.setCancelled(true);
		}
	}
	
}
