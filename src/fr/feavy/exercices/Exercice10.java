package fr.feavy.exercices;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.feavy.data.Locations;
import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice10 extends Exercice implements Listener{

	private World w;
	private Random r;
	
	private int kills = 0;
	private int nbKills = 20;
	
	private int[] stats = {21500,19500,18100};
	
	private Player sender;
	
	@Override
	public void onStart(Player sender) {
		super.onStart(sender);
		
		kills = 0;
		
		this.sender = sender;
		w = super.getWorld();
		r = new Random();
		
		super.setTimeStats(stats);
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 10.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de tuer mes bonhomme de neige tombant le plus rapidemment possible.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
		Location l = new Location(w, 296.48, 56.01, -5.49, -181.48232f, -90.0f);
		sender.teleport(l);
		
		generateRandomFallingMonster();
		
	}
	
	private int generateRandomNumber(int min, int max){
		
		return r.nextInt(max-min)+min;
		
	}
	
	public void generateRandomFallingMonster(){
		
		int x = 296;
		int z = -6;
		
		int xScale = generateRandomNumber(-2, 2);
		int zScale;
		if(xScale == 1 || xScale == -1){
			zScale = generateRandomNumber(-2, 2);
		}else{
			zScale = generateRandomNumber(-1, 1);
		}
		
		w.spawnEntity(new Location(w, x+xScale, 66, z+zScale), EntityType.SNOWMAN);
		
	}
	
	
	
	@EventHandler
	public void onPlayerInteractWithEntity(EntityDamageEvent e){
		
		LivingEntity en = (LivingEntity)e.getEntity();
		if(en.getType() == EntityType.SNOWMAN){
			if(e.getCause() == DamageCause.MELTING){
				e.setCancelled(true);
				return;
			}
			kills++;
			e.setDamage(20.0);
			
			if(kills == 1){
				super.writeStartMessage(10);
				super.startTime = System.currentTimeMillis();
			}else if(kills == nbKills){
				super.writeEndMessage(10);
				Locations.exercice10.teleport(sender);
				super.endTime = System.currentTimeMillis();
				long diff = (super.endTime - super.startTime);
				super.showTimeResult(diff);
				MainPlugin.resetPlayer(sender);
				super.onStop();
				return;
			}
			
			generateRandomFallingMonster();
			
		}
		
		
	}
	
}
