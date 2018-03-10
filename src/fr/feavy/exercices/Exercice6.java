package fr.feavy.exercices;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice6 extends Exercice{

	private World w;
	private int blocksNb = 10;
	private int blockBroken = 0;
	private int blockPlaced = 0;
	
	private Player sender;
	
	private int[] stats = {4000, 3500, 2860};
			
	@Override
	public void onStart(Player sender) {
		super.onStart(sender);
	
		super.setTimeStats(stats);
		
		this.sender = sender;
		
		w = super.getWorld();
		super.startTime = 0;
		
		blockBroken = 0;
		blockPlaced = 0;
		
		Random r = new Random();
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 13; j++){
				w.getBlockAt(346+i, 55, -2-j).setType(Material.DIAMOND_BLOCK);
			}
		}
		
		do{
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 13; j++){
				if(r.nextInt(10) == 1){
					if(w.getBlockAt(346+i, 55, -2-j).getType() == Material.DIAMOND_BLOCK){
						blockBroken++;
						w.getBlockAt(346+i, 55, -2-j).setType(Material.AIR);
						if(blockBroken == blocksNb)break;
					}
				}
			}
			if(blockBroken == blocksNb)break;
		}
		}while(blockBroken < blocksNb);
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 6.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de remplir les trous le plus vite possible avec vos blocs d'emeraude.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
		sender.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK, blocksNb));
		
	}
	
	public void onStop(){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 13; j++){
				w.getBlockAt(346+i, 55, -2-j).setType(Material.DIAMOND_BLOCK);
			}
		}
		super.onStop();
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		
		Block b = e.getBlock();
		
		if(b.getType() == Material.EMERALD_BLOCK){
			if(b.getLocation().getBlockY() == 55){
				blockPlaced++;
				if(blockPlaced == 1){
					super.writeStartMessage(6);
					super.startTime = System.currentTimeMillis();
				}else if(blockPlaced == 10){
					super.writeEndMessage(6);
					super.endTime = System.currentTimeMillis();
					long diff = (super.endTime - super.startTime);
					
					super.showTimeResult(diff);					
				}
			}else{
				e.setCancelled(true);
				Bukkit.broadcastMessage(Strings.prefix+"§cVous n'avez pas bien placé le block, rénitialisation de l'exercice...");
				onStop();
			}
		}
	}
	
}
