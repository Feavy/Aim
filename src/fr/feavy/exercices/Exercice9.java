package fr.feavy.exercices;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice9 extends Exercice implements Listener{

	private World w;
	
	private int x,y,z;
	
	private Random r;
	
	private int kills = 0;
	
	private int maxKills = 20;
	
	private int[] stats = {19000,15600,13300};
	
	@Override
	public void onStart(Player sender) {
		super.onStart(sender);
		
		kills = 0;
		
		w = super.getWorld();
		
		r = new Random();
		
		super.setTimeStats(stats);
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 9.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de se débarasser rapidemment des monstres qui arrivent vers vous.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
		sender.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		
		Location l = new Location(w, 313.56, 56.01, -2.64, -181.0293f, 2.1894052f);
		sender.teleport(l);
		
		x = 308;
		y = 56;
		z = -3;
		
		generateRandomMonster();
		
	}
	
	private void generateRandomMonster(){
		
		int zScale = r.nextInt(3)*-1;
		int xScale = r.nextInt(11);
		
		w.spawnEntity(new Location(w, x+xScale, y, z+zScale), EntityType.ZOMBIE);
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent e){
		
		Bukkit.broadcastMessage(Strings.prefix+"§cVous êtes mort, rénitialisation de l'exercice...");
		MainPlugin.clearEntities(w);
		super.onStop();
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getEntity().getType() == EntityType.ZOMBIE){
			e.setDamage(40);
			kills++;
			if(kills == 1){
				super.startTime = System.currentTimeMillis();
				super.writeStartMessage(9);
				generateRandomMonster();
			}else if(kills == maxKills){
				super.endTime = System.currentTimeMillis();
				super.writeEndMessage(9);
				long diff = (super.endTime - super.startTime);
				super.showTimeResult(diff);
				super.onStop();
			}else{
				generateRandomMonster();
			}
		}
	}
	
}
