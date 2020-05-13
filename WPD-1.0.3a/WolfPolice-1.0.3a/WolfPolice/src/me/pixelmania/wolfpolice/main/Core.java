package me.pixelmania.wolfpolice.main;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

import me.pixelmania.wolfpolice.commands.Additems;
import me.pixelmania.wolfpolice.commands.Duty;
import me.pixelmania.wolfpolice.commands.Getbaton;
import me.pixelmania.wolfpolice.commands.Incidents;
import me.pixelmania.wolfpolice.commands.Pc;
import me.pixelmania.wolfpolice.commands.Pctoggle;
import me.pixelmania.wolfpolice.commands.Police;
import me.pixelmania.wolfpolice.commands.Policesay;
import me.pixelmania.wolfpolice.commands.Release;
import me.pixelmania.wolfpolice.commands.Wpd;
import me.pixelmania.wolfpolice.functions.PlayerFiles;
import me.pixelmania.wolfpolice.listeners.AsyncPlayerChat;
import me.pixelmania.wolfpolice.listeners.InventoryClose;
import me.pixelmania.wolfpolice.listeners.PlayerDeath;
import me.pixelmania.wolfpolice.listeners.PlayerDropItem;
import me.pixelmania.wolfpolice.listeners.PlayerJailed;
import me.pixelmania.wolfpolice.listeners.PlayerJoin;
import me.pixelmania.wolfpolice.listeners.PlayerQuit;
import me.pixelmania.wolfpolice.skinsrestorer.WPSkinsRestorer;

public class Core extends JavaPlugin
{
	
	public static Core plugin;
	
	public static File playersFolder;
	public static File configFile;
	public static FileConfiguration config;
	public static File dutyItemsFile;
	public static FileConfiguration dutyItemsConfig;
	
	public static List<UUID> playersOnDuty = Lists.newArrayList();
	public static List<UUID> playersInPoliceRadio = Lists.newArrayList();
	
	public static Map<UUID, File> playerFiles = new HashMap<UUID, File>();
	public static Map<UUID, FileConfiguration> playerConfigs = new HashMap<UUID, FileConfiguration>();
	
	public static File incidentsTimeValueFile;
	public static FileConfiguration incidentsTimeValueConfig;
	
	public static boolean SkinsRestorer = false;
	
	private static String[] supportedVersions = {"1_9_R1", "1_9_R1", "1_10_R1", "1_11_R1", "1_12_R1", "1_13_R1", "1_13_R2", "1_14_R1", "1_15_R1"};
	public static String serverVersion;
	
	@Override
	public void onEnable()
	{
		boolean supportedVersion = false;
		for (int i = 0; i != supportedVersions.length; i++)
		{
			if (Bukkit.getServer().getClass().getPackage().getName().contains(supportedVersions[i]))
			{
				supportedVersion = true;
				serverVersion = supportedVersions[i];
			}
		}
		if (!supportedVersion)
		{
			Bukkit.getConsoleSender().sendMessage("[WolfPolice] Disabling Plugin: The version of the server is not supported.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		Bukkit.getServer().getConsoleSender().sendMessage("[WolfPolice] Successfully enabled!");
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new PlayerJailed(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChat(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDropItem(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
		if (serverVersion.equals("1_9_R1") || serverVersion.equals("1_9_R1") || serverVersion.equals("1_10_R1") || serverVersion.equals("1_11_R1") || serverVersion.equals("1_12_R1"))
		{
			this.getCommand("police").setExecutor((CommandExecutor) new me.pixelmania.wolfpolice.legacy.v_1_12_2.commands.Police());
		} else {
			this.getCommand("police").setExecutor((CommandExecutor) new Police());
		}
		this.getCommand("additems").setExecutor((CommandExecutor) new Additems());
		this.getCommand("getbaton").setExecutor((CommandExecutor) new Getbaton());
		this.getCommand("release").setExecutor((CommandExecutor) new Release());
		this.getCommand("pc").setExecutor((CommandExecutor) new Pc());
		this.getCommand("duty").setExecutor((CommandExecutor) new Duty());
		this.getCommand("incidents").setExecutor((CommandExecutor) new Incidents());
		this.getCommand("wpd").setExecutor((CommandExecutor) new Wpd());
		this.getCommand("policesay").setExecutor((CommandExecutor) new Policesay());
		this.getCommand("pctoggle").setExecutor((CommandExecutor) new Pctoggle());
		playersFolder = new File(this.getDataFolder() + "/players");
		if (!playersFolder.exists())
		{
			playersFolder.mkdirs();
		}
		incidentsTimeValueFile = new File(this.getDataFolder() + "/incidents.timevalue");
		if (incidentsTimeValueFile.exists())
		{
			incidentsTimeValueConfig = YamlConfiguration.loadConfiguration(incidentsTimeValueFile);
		}
		else
		{
			try
			{
				incidentsTimeValueFile.createNewFile();
				incidentsTimeValueConfig = YamlConfiguration.loadConfiguration(incidentsTimeValueFile);
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		if (incidentsTimeValueConfig.getInt("value") == -1)
		{
			incidentsTimeValueConfig.set("value", Instant.now().getEpochSecond());
			saveIncidentsTimeValueConfig();
		}
		else
		{
			if (Instant.now().getEpochSecond() >= incidentsTimeValueConfig.getInt("value") + 86400)
			{
				incidentsTimeValueConfig.set("value", Instant.now().getEpochSecond());
				saveIncidentsTimeValueConfig();
				if (playersFolder.listFiles() != null)
				{
				for (File file : playersFolder.listFiles())
				{
					YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
					if (config.contains("incidents"))
					{
						config.set("incidents", 0);
						PlayerFiles.saveConfig(config, file);
					}
				}
			}
		}
			else
			{
				new BukkitRunnable()
				{

					@Override
					public void run() {
						if (Instant.now().getEpochSecond() >= incidentsTimeValueConfig.getInt("value") + 86400)
						{
							incidentsTimeValueConfig.set("value", Instant.now().getEpochSecond());
							saveIncidentsTimeValueConfig();
							if (playersFolder.listFiles() != null)
							{
							for (File file : playersFolder.listFiles())
							{
								YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
								if (config.contains("incidents"))
								{
									config.set("incidents", 0);
									PlayerFiles.saveConfig(config, file);
									}
								}
							}
						}
					}
					
				}.runTaskTimer(this, 1200L, 1200L);
			}
		}
		configFile = new File(this.getDataFolder() + "/config.yml");
		if (configFile.exists())
		{
			config = this.getConfig();
		}
		else
		{
		this.saveDefaultConfig();
		config = this.getConfig();
		}
		dutyItemsFile = new File(this.getDataFolder() + "/dutyitems.yml");
		if (dutyItemsFile.exists())
		{
			dutyItemsConfig = YamlConfiguration.loadConfiguration(dutyItemsFile);
		}
		else
		{
			try
			{
				dutyItemsFile.createNewFile();
				dutyItemsConfig = YamlConfiguration.loadConfiguration(dutyItemsFile);
			}
			catch (IOException exception)
			{
				exception.printStackTrace();
			}
		}
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		if (Bukkit.getServer().getPluginManager().getPlugin("SkinsRestorer") != null)
		{
			SkinsRestorer = true;
		}
		if (!Bukkit.getServer().getOnlinePlayers().isEmpty())
		{
			for (Player player : Bukkit.getServer().getOnlinePlayers())
			{
				PlayerFiles.putConfig(player);
			}
		}
	}
	
	public static void saveDutyItemsConfig()
	{
		try
		{
			dutyItemsConfig.save(dutyItemsFile);
			dutyItemsConfig = YamlConfiguration.loadConfiguration(dutyItemsFile);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public static void saveIncidentsTimeValueConfig()
	{
		try
		{
			incidentsTimeValueConfig.save(incidentsTimeValueFile);
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}
	
	@Override
	public void onDisable()
	{
		if (!playersOnDuty.isEmpty())
		{
			for (UUID uuid : playersOnDuty)
			{
				Player player = Bukkit.getServer().getPlayer(uuid);
				FileConfiguration playerConfig = playerConfigs.get(player.getUniqueId());
				playerConfig.set("on-duty", false);
				if (PlayerFiles.saveConfig(player))
				{
					player.getInventory().clear();
					@SuppressWarnings("unchecked")
					final List<ItemStack> items = (List<ItemStack>) playerConfig.get("inventory.items");
					for (int i = 0; i != items.size(); i++)
					{
						if (items.get(i) != null)
						{
							player.getInventory().setItem(i, items.get(i));
						}
					}
					@SuppressWarnings("unchecked")
					final List<ItemStack> armor = (List<ItemStack>) playerConfig.get("inventory.armor");
					final ItemStack[] armorList = new ItemStack[4];
					for (int i = 0; i != armor.size(); i++)
					{
						armorList[i] = armor.get(i);
					}
					player.getInventory().setArmorContents(armorList);
					playerConfig = playerConfigs.get(player.getUniqueId());
					playerConfig.set("inventory", null);
					PlayerFiles.saveConfig(player);
				}
				if (SkinsRestorer && config.getBoolean("options.skinsrestorer-police-skins"))
				{
					WPSkinsRestorer.applySkin(player, true);
				}
			}
		}
	}
	
}
