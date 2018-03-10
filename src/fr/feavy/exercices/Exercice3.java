package fr.feavy.exercices;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice3 extends Exercice implements Listener{

	private World w;
	
	private Player sender;
	
	private Set<Material> materials;
	
	private double inAxeTime;
	
	private double startAxeTime;

	private String last = "notInAxe";
	
	private int[] stats = {7800, 8300, 9200};
	
	public void onStart(Player p){
		
		Set<Material> materials = new HashSet<Material>();
		materials.add(Material.THIN_GLASS);
		materials.add(Material.STONE);
		materials.add(Material.AIR);
		
		this.sender = p;
		
		inAxeTime = 0;
		last = "notInAxe";
		startAxeTime = 0;
		super.startTime = 0;
		
		super.onStart(p);
		//super.setTimeStats(stats);
		w = super.getWorld();

		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 3.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de toujours avoir le curseur sur le bloc de lapis tout en bougeant. Durée: 10 secs.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		List<Block> blocks = sender.getLineOfSight(materials, 50);
		
		boolean inAxe = false;
		
		for(Block b : blocks){
			if(b.getTypeId() == 22){
				if(super.startTime == 0){
					super.startTime = System.currentTimeMillis();
					startAxeTime = System.currentTimeMillis();
					super.writeStartMessage(3);
					new Timer().schedule(new TimerTask() {
						
						@Override
						public void run() {
							
							if(last.equals("inAxe")){
								inAxeTime += (System.currentTimeMillis() - startAxeTime);
								last = "notInAxe";
							}
							Exercice.writeEndMessage(3);
							Bukkit.broadcastMessage(Strings.prefix+"§aLe block de lapis était sur votre curseur : §6"+(inAxeTime/1000d)+" §asecondes sur 10.");
							int lvl = 0;
							
							for(int i : stats){
								if(inAxeTime >= i){
									lvl++;
								}
							}
							
							Bukkit.broadcastMessage(Strings.prefix+"§6Résultat: "+Strings.resultMessages.get(lvl));
							MainPlugin.clearEntities(w);
							
						}
					}, 10000l);
				}else if(last.equals("notInAxe")){
					startAxeTime = System.currentTimeMillis();
				}
				inAxe = true;
				last = "inAxe";
				break;
			}
		}
		
		if(last.equals("inAxe") && inAxe == false){
			inAxeTime += (System.currentTimeMillis() - startAxeTime);
			last = "notInAxe";
		}
		
	}
	
}
