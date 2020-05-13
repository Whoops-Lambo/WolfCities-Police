package me.pixelmania.wolfpolice.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.pixelmania.wolfpolice.functions.PlayerFiles;

public class PlayerDeath implements Listener
{

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		if (player.getKiller() != null)
		{
			Player killer = player.getKiller();
			if (PlayerFiles.getConfig(killer) != null)
			{
				FileConfiguration config = PlayerFiles.getConfig(killer);
				int incidents = 1;
				if (config.contains("incidents"))
				{
					incidents += config.getInt("incidents");
				}
				config.set("incidents", incidents);
				PlayerFiles.saveConfig(killer);
			}
		}
	}
	
}
