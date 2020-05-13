package me.pixelmania.wolfpolice.skinsrestorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import me.pixelmania.wolfpolice.main.Core;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;
import skinsrestorer.shared.exception.SkinRequestException;

public class WPSkinsRestorer {
	
	public static void applySkin(Player player, boolean reset)
	{
	if (reset)
	{
		SkinsRestorer skinsRestorer = Core.getPlugin(SkinsRestorer.class);
		SkinsRestorerBukkitAPI skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();
		skinsRestorerBukkitAPI.applySkin(player, skinsRestorerBukkitAPI.getSkinData(player.getName()));
	}
	else
	{
		SkinsRestorer skinsRestorer = Core.getPlugin(SkinsRestorer.class);
		SkinsRestorerBukkitAPI skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();
		List<String> skins = new ArrayList<String>();
		for (String skin : Core.config.getStringList("options.skinsrestorer-skin-names"))
		{
			skins.add(skin);
		}
		Random random = new Random();
		int randomNumber = random.nextInt(skins.size());
		String randomSkin = skins.get(randomNumber);
		try
		{
			skinsRestorerBukkitAPI.setSkin(player.getName(), randomSkin);
			skinsRestorerBukkitAPI.applySkin(player);
		} catch (SkinRequestException exception) {
			exception.printStackTrace();
		}
	}
	}
	
	public static String getSkin(Player player)
	{
		SkinsRestorer skinsRestorer = Core.getPlugin(SkinsRestorer.class);
		SkinsRestorerBukkitAPI skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();
		return skinsRestorerBukkitAPI.getSkinName(player.getName());
	}
	
}
