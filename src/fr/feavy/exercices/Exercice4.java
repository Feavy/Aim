package fr.feavy.exercices;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import fr.feavy.data.Strings;

public class Exercice4 extends Exercice implements Listener{

	private World w;
	
	private int nbBlocks = 110;
	private int blockBroken = 0;
	
	private int[] stats = {18000, 16000, 13000};
	
	public void onStart(Player p) {
		super.onStart(p);
		
		blockBroken = 0;
		
		super.setTimeStats(stats);
		
		w = super.getWorld();
		
		ItemStack item = new ItemStack(Material.DIAMOND_SPADE);
		item.addEnchantment(Enchantment.DIG_SPEED, 5);
		
		p.getInventory().addItem(item);
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 4.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de casser les blocs de neige le plus rapidemment possible.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 10; j++){
				w.getBlockAt(322-i, 56, 13+j).setType(Material.SNOW);
			}
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		
		if(e.getBlock().getType() == Material.SNOW){
			blockBroken++;
			if(blockBroken == 1){
				super.writeStartMessage(4);
				super.startTime = System.currentTimeMillis();
			}else if(blockBroken == nbBlocks){
				super.writeEndMessage(4);
				super.showTimeResult((System.currentTimeMillis() - super.startTime));
				super.onStop();
			}
		}
	}
	
}
