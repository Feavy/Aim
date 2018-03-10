package fr.feavy.main;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Iterables;

import fr.feavy.data.Locations;
import fr.feavy.data.Strings;
import fr.feavy.exercices.Exercice;
import fr.feavy.exercices.ExerciceList;
import fr.feavy.listeners.PluginListener;

public class MainPlugin extends JavaPlugin {

	public static MainPlugin instance;

	private Exercice lastExercice;
	
	private static int speedLevel = 0;
	
	@Override
	public void onEnable() {
		super.onEnable();

		ExerciceList.initialize();
		Strings.initialize();
		instance = this;
		
		Location hubLocation = Locations.hub.getLocation();
		
		getServer().getWorld("world").setSpawnLocation(hubLocation.getBlockX(), hubLocation.getBlockY(), hubLocation.getBlockZ());
		
		getServer().getPluginManager().registerEvents(new PluginListener(), this);

	}

	@Override
	public void onDisable() {
		super.onDisable();

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (label.equalsIgnoreCase("initialize")) {
			
			initialize(getSender());
			
			return true;
		} else if (label.equalsIgnoreCase("hub")) {
			Player p = getSender();
			Locations.hub.teleport(p);
			sender.sendMessage(Strings.prefix + "§aTéléportation au hub.");
			return true;
		} else if(label.equalsIgnoreCase("startexercice")){
			
			if(args.length >= 1){
				
				if(lastExercice != null)
					HandlerList.unregisterAll(lastExercice);
				
				Player p = getSender();
				Exercice ex = ExerciceList.exercices.get(args[0]);
				ex.onStart(p);
				
				lastExercice = ex;
				
				getServer().getPluginManager().registerEvents(ex, this);
				
			}else{
				
				Bukkit.broadcastMessage("§cErreur: commande invalide. Utilisation: /startexercice [1-10]");
			
			}
			
			return true;
			
		} else if(label.equalsIgnoreCase("speed")){
			
			if(args.length >= 1){
				
				if(StringUtils.isNumeric(args[0])){
					int speedLevel = Integer.parseInt(args[0]);
					
					if(speedLevel >= 0 && speedLevel <= 255){
						
						MainPlugin.speedLevel = speedLevel;
						
						Bukkit.broadcastMessage(Strings.prefix+"§aVous aurez un effet de speed §6"+speedLevel+" §apendant les prochains exercices");
						
						if(speedLevel == 0){
							resetPlayer(getSender());
						}
						
					}else{
						Bukkit.broadcastMessage("§cErreur: commande invalide. Utilisation: /speed [0-255]");
					}
				}
				
			}else{
				Bukkit.broadcastMessage("§cErreur: commande invalide. Utilisation: /speed [0-255]");
			}
			
			return true;
			
		}
		return super.onCommand(sender, command, label, args);
	}

	public void initialize(Player p){
		p.setGameMode(GameMode.SURVIVAL);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 5));
		Locations.hub.teleport(p);
	}
	
	public static void resetPlayer(Player p){
		
		p.getInventory().clear();
		
		for(PotionEffect eff : p.getActivePotionEffects()){
			p.removePotionEffect(eff.getType());
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 5));
		Damageable d = (Damageable)p;
		d.setHealth(20.0);
		
	}
	
	public static void clearEntities(World w){
		
		for(Entity e : w.getEntities()){
			if(e.getType() != EntityType.PLAYER){
				if(e.getType() == EntityType.ITEM_FRAME){
					continue;
				}
				e.remove();
			}
		}
		
	}
	
	public static int getSpeedLevel(){
		return speedLevel;
	}
	
	public Player getSender() {
		return Iterables.get(getServer().getOnlinePlayers(), 0);
	}

}
