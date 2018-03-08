package fr.feavy.exercices;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.feavy.data.Locations;
import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;

public class Exercice8 extends Exercice implements Listener{

	private World w;
	
	private int hit;
	private Player sender;
	
	private int[] stats = {18000,14000,10500};
	
	@Override
	public void onStart(Player sender) {
		super.onStart(sender);
		
		w = super.getWorld();
		
		this.sender = sender;
		
		super.setTimeStats(stats);
		
		hit = 0;
		
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice 8.");
		Bukkit.broadcastMessage("--------------------------");
		Bukkit.broadcastMessage("§bLe but de cet exercice est de donner 25 coups à la chauve-souris le plus rapidemment possible.\n§6Bonne chance !");
		Bukkit.broadcastMessage("--------------------------");
		
		sender.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5));
		sender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 2));
		
		Location l = new Location(w, 326.48, 24.01, -6.57, -179.96411f, 0.016967196f);
		sender.teleport(l);
		
		w.spawnEntity(new Location(w, 326, 26, -21), EntityType.BAT);
		
		sender.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		
		e.setDamage(0.5);
		LivingEntity en = (LivingEntity)e.getEntity();
		
		if(en.getType() == EntityType.BAT){
			hit++;
			Bukkit.broadcastMessage(hit+" / 25");
			en.setHealth(5.0);
			if(hit == 1){
				super.startTime = System.currentTimeMillis();
				en.setMaximumNoDamageTicks(0);
				super.writeStartMessage(8);
			}else if(hit == 25){
				super.endTime = System.currentTimeMillis();
				super.writeEndMessage(8);
				MainPlugin.clearEntities(w);
				MainPlugin.resetPlayer(sender);
				long diff = (super.endTime - super.startTime);
				super.showTimeResult(diff);
				Bukkit.broadcastMessage("time: "+diff);
				Locations.exercice8.teleport(sender);
				super.onStop();
			}
			
		}
		
	}
	
}
