package fr.feavy.exercices;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice1 extends Exercice implements Listener {
	
	private int nbBlock = 50;
	private int blockBroke = 0;
	
	private long startTime = 0;
	private long endTime = 0;
	
	private World w;

	private int[] stats = {12000, 10000, 8000};
	
	public void onStart(Player p) {
		
		blockBroke = 0;
		
		super.onStart(p);
		super.setTimeStats(stats);
		w = super.getWorld();

		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 1.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de casser les blocs de sable le plus rapidemment possible tout en tournant autour d'eux.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");

		ItemStack item = new ItemStack(Material.DIAMOND_SPADE);
		item.addEnchantment(Enchantment.DIG_SPEED, 5);
		
		p.getInventory().addItem(item);
		
		for (int i = 56; i <= 56 + nbBlock-1; i++) {
			w.getBlockAt(348, i, 15).setType(Material.SAND);
		}
		
	}
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e) {
		
		if(e.getEntityType() != EntityType.DROPPED_ITEM)
			return;
		
		blockBroke++;
		if (blockBroke == 1) {
			super.writeStartMessage(1);
			startTime = System.currentTimeMillis();
		} else if (blockBroke == nbBlock) {
			super.writeEndMessage(1);
			endTime = System.currentTimeMillis();
			
			long diff = endTime - startTime;
			
			super.showTimeResult(diff);
			super.onStop();
			
		}

	}

}
