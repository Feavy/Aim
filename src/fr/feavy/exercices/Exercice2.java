package fr.feavy.exercices;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.feavy.data.Locations;
import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice2 extends Exercice implements Listener {
	
	private World w;
	
	private Player sender;
	
	private int[] stats = {12000, 14000, 16000};

	public void onStart(Player sender) {
		
		super.onStart(sender);
		super.setTimeStats(stats);
		
		this.sender = sender;

		super.startTime = 0;
		
		w = super.getWorld();

		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 2.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est tuer le pigman le plus rapidement possible.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");

		sender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2));
		Location l = new Location(w, 348, 47, 16);
		sender.teleport(l);

		w.spawnEntity(new Location(w, 348, 45, 27), EntityType.PIG_ZOMBIE);

	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		
		if (e.getEntityType() == EntityType.PIG_ZOMBIE) {
			if (super.startTime == 0){
				super.writeStartMessage(2);
				super.startTime = System.currentTimeMillis();
			}
		}

	}

	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent e){
		
		Bukkit.broadcastMessage(Strings.prefix+"§cVous êtes mort, rénitialisation de l'exercice...");
		MainPlugin.clearEntities(w);
		super.onStop();
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		
		if (e.getEntityType() == EntityType.PIG_ZOMBIE) {

			super.writeEndMessage(2);
			
			super.endTime = System.currentTimeMillis();
			Locations.exercice2.teleport(sender);
			
			long diff = super.endTime - super.startTime;
			
			super.showTimeResult(diff);
			
			super.onStop();	
		}

	}

}
