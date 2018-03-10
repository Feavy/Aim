package fr.feavy.exercices;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Button;

import fr.feavy.data.Strings;

public class Exercice7 extends Exercice implements Listener {

	private World w;

	private int buttonCount = 10;
	private int buttonPlaced;
	
	private int buttonBroken;

	private int[] stats = {4200,3800,3310};
	
	@Override
	public void onStart(Player sender) {
		super.onStart(sender);

		super.setTimeStats(stats);
		
		buttonPlaced = 0;
		buttonBroken = 0;
		
		Random r = new Random();
		Block b;

		w = super.getWorld();

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 4; j++) {
				w.getBlockAt(new Location(w, 334 + i, 56 + j, -6)).setType(Material.AIR);
			}
		}
			
		do {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 4; j++) {
					b = w.getBlockAt(new Location(w, 334 + i, 56 + j, -6));
					if (b.getType() == Material.AIR) {
						if (r.nextInt(10) == 0) {
							w.getBlockAt(new Location(w, 334 + i, 56 + j, -6)).setType(Material.WOOD_BUTTON);
							BlockState state = w.getBlockAt(new Location(w, 334 + i, 56 + j, -6)).getState();
							Button but = new Button(Material.WOOD_BUTTON);
							but.setFacingDirection(BlockFace.SOUTH);
							state.setData(but);
							state.update();
							buttonPlaced++;
							if(buttonPlaced == buttonCount)break;
						}
					}
				}
				if(buttonPlaced == buttonCount)break;
			}
		} while (buttonPlaced < buttonCount);

		ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
		axe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 20);
		
		sender.getInventory().addItem(axe);
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 7.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de casser les boutous le plus vite possible.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getClickedBlock() != null){
			if(e.getClickedBlock().getType() == Material.WOOD_BUTTON){
				buttonBroken++;
				e.getClickedBlock().breakNaturally();
				if(buttonBroken == 1){
					super.startTime = System.currentTimeMillis();
					super.writeStartMessage(7);
				}else if(buttonBroken == buttonCount){
					super.endTime = System.currentTimeMillis();
					super.writeEndMessage(7);
					long diff = (super.endTime - super.startTime);
					super.showTimeResult(diff);
				}
			}
		}
	}
	
}
