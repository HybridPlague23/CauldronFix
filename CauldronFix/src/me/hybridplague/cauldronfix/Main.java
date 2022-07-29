package me.hybridplague.cauldronfix;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			Player p = e.getPlayer();
			int amount = p.getInventory().getItemInMainHand().getAmount();
			
			ItemStack bottle = new ItemStack(Material.POTION, amount - 1);
			
			ItemMeta meta = bottle.getItemMeta();
			
			PotionMeta pmeta = (PotionMeta) meta;
			
			PotionData pdata = new PotionData(PotionType.WATER);
			
			pmeta.setBasePotionData(pdata);
			
			bottle.setItemMeta(meta);
			
			if ((p.getInventory().getItemInMainHand().getType() == Material.POTION || p.getInventory().getItemInOffHand().getType() == Material.POTION) && b.getType() == Material.CAULDRON) { // has water bottle in either hand
				e.setCancelled(true);
				Levelled cdata = (Levelled) b.getBlockData();
				if (b.getType() == Material.CAULDRON) { // is right clicking a cauldron with water bottle
					if (p.getInventory().getItemInMainHand().getType() == Material.POTION ) { // water bottle is in main hand
						cdata.setLevel(cdata.getLevel() + 1);
						b.setBlockData(cdata);
						p.getInventory().setItemInMainHand(bottle);
						p.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, 1));
						return;
					}
					if (p.getInventory().getItemInOffHand().getType() == Material.POTION) {
						return;
					}
				}
			}
		}
	}
	
}
