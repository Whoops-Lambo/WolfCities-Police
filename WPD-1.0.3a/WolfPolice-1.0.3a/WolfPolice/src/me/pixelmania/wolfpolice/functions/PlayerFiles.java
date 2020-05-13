package me.pixelmania.wolfpolice.functions;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.main.Core;

public class PlayerFiles {
	
	public static boolean saveConfig(Player player)
	{
		if (Core.playerFiles.containsKey(player.getUniqueId()) && Core.playerConfigs.containsKey(player.getUniqueId()))
		{
			try
			{
				Core.playerConfigs.get(player.getUniqueId()).save(Core.playerFiles.get(player.getUniqueId()));
				Core.playerConfigs.put(player.getUniqueId(), YamlConfiguration.loadConfiguration(Core.playerFiles.get(player.getUniqueId())));
				return true;
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		return false;
	}
	
	public static FileConfiguration getConfig(Player player)
	{
		if (Core.playerConfigs.containsKey(player.getUniqueId()))
		{
			return Core.playerConfigs.get(player.getUniqueId());
		}
		else
		{
			return null;
		}
	}
	
	public static FileConfiguration getConfigUUID(String uuid)
	{
		if (Core.playerConfigs.containsKey(UUID.fromString(uuid)))
		{
			return Core.playerConfigs.get(UUID.fromString(uuid));
		}
		else
		{
			return null;
		}
	}
	
	public static boolean saveConfigUUID(String uuid)
	{
		if (Core.playerFiles.containsKey(UUID.fromString(uuid)) && Core.playerConfigs.containsKey(UUID.fromString(uuid)))
		{
			try
			{
				Core.playerConfigs.get(UUID.fromString(uuid)).save(Core.playerFiles.get(UUID.fromString(uuid)));
				Core.playerConfigs.put(UUID.fromString(uuid), YamlConfiguration.loadConfiguration(Core.playerFiles.get(UUID.fromString(uuid))));
				return true;
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean saveConfig(YamlConfiguration config, File file)
	{
			try
			{
				 config.save(file);
				 String uuid = file.getName().replace(".yml", "");
				 Core.playerConfigs.put(UUID.fromString(uuid), config);
				return true;
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		return false;
	}
	
	public static boolean putConfig(Player player)
	{
		UUID playerUUID = player.getUniqueId();
		String playerName = player.getName();
		if (Core.playersFolder.exists())
		{
			FileConfiguration playerFileConfig = null;
			File playerFile = new File(Core.playersFolder + "/" + playerUUID.toString() + ".yml");
			if (playerFile.exists())
			{
				playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
				playerFileConfig.options().header("Player Save File for " + playerUUID + "\nOriginal Username: " + playerName);
				try
				{
					playerFileConfig.save(playerFile);
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
				Core.playerFiles.put(playerUUID, playerFile);
				Core.playerConfigs.put(playerUUID, playerFileConfig);
				return true;
			}
			else
			{
				try
				{
					playerFile.createNewFile();
					playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
					playerFileConfig.options().header("Player Save File for " + playerUUID + "\nOriginal Username: " + playerName);
					try
					{
						playerFileConfig.save(playerFile);
					}
					catch (IOException exception)
					{
						exception.printStackTrace();
					}
					Core.playerFiles.put(playerUUID, playerFile);
					Core.playerConfigs.put(playerUUID, playerFileConfig);
					return true;
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
			}
		}
		return false;
	}
	
}
