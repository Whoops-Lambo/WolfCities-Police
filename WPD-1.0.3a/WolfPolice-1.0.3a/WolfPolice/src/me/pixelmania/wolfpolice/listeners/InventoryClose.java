package me.pixelmania.wolfpolice.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.pixelmania.wolfpolice.commands.Additems;
import me.pixelmania.wolfpolice.main.Core;

public class InventoryClose implements Listener
{
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent event)
	{
		if (event.getView().getTitle().equals(Additems.inventoryName))
		{
			Core.dutyItemsConfig.set("items", event.getInventory().getContents());
			Core.saveDutyItemsConfig();
		}
	}
	
}
