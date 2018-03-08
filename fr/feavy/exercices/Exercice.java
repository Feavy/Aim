package fr.feavy.exercices;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.feavy.data.Strings;
import fr.feavy.main.MainPlugin;


public class Exercice implements Listener{

	private World w;
	private int[] stats;
	
	protected long startTime = 0;
	protected long endTime = 0;
	
	private Player sender;
	
	public void onStart(Player sender){
		
		this.sender = sender;
		
		sender.setGameMode(GameMode.SURVIVAL);
		
		w = MainPlugin.instance.getServer().getWorld("world");
		MainPlugin.resetPlayer(sender);
		startTime = 0;
		
		if(MainPlugin.getSpeedLevel() > 0){
			sender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, MainPlugin.getSpeedLevel()-1));
		}
		
		MainPlugin.clearEntities(w);
		
	}
	
	public void onStop(){
		MainPlugin.resetPlayer(sender);
	}
	
	public World getWorld(){
		return w;
	}
	
	public void setTimeStats(int[] stats){
		this.stats = stats;
	}
	
	public static void writeStartMessage(int exerciceNb){
		Bukkit.broadcastMessage(Strings.prefix + "§aDébut de l'exercice §6"+exerciceNb+" §a!");
	}
	
	public static void writeEndMessage(int exerciceNb){
		Bukkit.broadcastMessage(Strings.prefix + "§aExercice §6"+exerciceNb+" §aterminé !");		
	}
	
	public void showTimeResult(long diff){
		
		String str = String.format("%d min %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(diff),
			    TimeUnit.MILLISECONDS.toSeconds(diff) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff))
			);
		
		Bukkit.broadcastMessage(Strings.prefix+"§aTemps passé : "+str);
		
		int lvl = 0;
		
		for(int i : stats){
			if(diff <= i){
				lvl++;
			}
		}
		
		Bukkit.broadcastMessage(Strings.prefix+"§6Résultat: "+Strings.resultMessages.get(lvl));
		
	}
	
}
