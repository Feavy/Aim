package fr.feavy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.feavy.data.Locations;
import fr.feavy.data.Strings;
import fr.feavy.exercices.Exercice;
import fr.feavy.main.MainPlugin;

public class PluginListener implements Listener{
	
	public PluginListener(){
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		for(PotionEffect eff : p.getActivePotionEffects()){
			p.removePotionEffect(eff.getType());
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 5));
		Damageable d = (Damageable)p;
		d.setHealth(20.0);
		p.getInventory().clear();
		MainPlugin.instance.initialize(p);
		Locations.hub.teleport(p);
		
		Bukkit.broadcastMessage(Strings.prefix+"§aBienvenue sur la map d'entraînement à la visé.\nPour commencer à vous entraîner, cliquez sur l'un des panneaux. §7(clique droit)\n§6Auteur : §b§lFeavy\n§7( https://www.youtube.com/channel/UCi47f3U6x83XMQcAorJm5hQ )");
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent e){
		
		if(e.getClickedBlock() == null)return;
		
		if(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN){
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				Sign s = (Sign)e.getClickedBlock().getState();
				
				if(s.getLine(1).equalsIgnoreCase("Exercice")){
					String name = "Exercice "+s.getLine(2);
					Bukkit.broadcastMessage(Strings.prefix+"§aTéléportation à l'exercice §6"+s.getLine(2)+"§a.");
					
					for(Locations l : Locations.values()){
						if(l.getName().equals(name)){
							l.teleport(e.getPlayer());
							break;
						}
					}
				}
				
			}
		}
		
	}
	
}
