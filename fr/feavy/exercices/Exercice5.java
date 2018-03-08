package fr.feavy.exercices;

import java.util.Timer;
import java.util.TimerTask;

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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.feavy.data.Locations;
import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice5 extends Exercice implements Listener{

	private World w;
	private int hits = 0;
	
	private Player sender;
	
	private int[] stats = {25,37,43};
	
	@Override
	public void onStart(Player sender) {
		super.onStart(sender);
		
		this.sender = sender;
		
		hits = 0;
		
		w = super.getWorld();
		
		sender.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		
		w.spawnEntity(new Location(w, 303, 37, 23), EntityType.ZOMBIE);
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 5.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de donner le plus de coups possible au zombie pendant 15 secondes, en tournant autour de lui.§7 (Les coups se donnent deux fois plus vite)§b.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
		Location l = new Location(w, 303.61, 37.01, 14.60, -358.89587f, 1.7346203f);
		sender.teleport(l);
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerRespawnEvent e){
		
		Bukkit.broadcastMessage(Strings.prefix+"§cVous êtes mort, rénitialisation de l'exercice...");
		MainPlugin.clearEntities(w);
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.ZOMBIE){
			
			hits++;
			
			LivingEntity en = (LivingEntity)e.getEntity();
			en.setHealth(20.0);
			if(hits == 1){
				super.writeStartMessage(5);
				en.setMaximumNoDamageTicks(10);
				en.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 2));
				new Timer().schedule(new TimerTask() {
					
					@Override
					public void run() {
						
						MainPlugin.clearEntities(w);
						Locations.exercice5.teleport(sender);
						
						Exercice.writeEndMessage(5);
						Bukkit.broadcastMessage(Strings.prefix+"§aVous avez tapé le zombie §6"+hits+" §afois en §615 §asecondes.");
						
						int lvl = 0;
						
						for(int i : stats){
							if(hits >= i){
								lvl++;
							}
						}
						
						Bukkit.broadcastMessage(Strings.prefix+"§6Résultat: "+Strings.resultMessages.get(lvl));
						
					}
				}, 15000l);
			}
			
		}
		
	}
	
}
