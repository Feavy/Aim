package fr.feavy.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.feavy.main.MainPlugin;

public enum Locations {

	hub("Hub", 344.5, 109.0, -0.5, -180.21902f, 2.2735765f),
	exercice1("Exercice 1", 348.41, 57.0, 9.78, -0.28634644f, -0.15144135f),
	exercice2("Exercice 2", 339.49, 56.01, 9.66, -0.20220947f, 0.9601068f),
	exercice3("Exercice 3", 330.53, 56.01, 9.83, -359.54504f, 0.40442523f),
	exercice4("Exercice 4", 317.40, 56.01, 9.56, -0.15142822f, 0.31996688f),
	exercice5("Exercice 5", 303.59, 56.01, 9.57, 0.7587274f, 1.583119f),
	exercice6("Exercice 6", 348.47, 56.01, 1.42, -180.2898f, 0.21882626f),
	exercice7("Exercice 7", 337.57, 56.01, 1.41, -180.75806f, -0.033652503f),
	exercice8("Exercice 8", 327.53, 56.01, 1.41, -180.0507f, -0.084129855f),
	exercice9("Exercice 9", 313.61, 56.01, 1.45, -181.07977f, 0.57263744f),
	exercice10("Exercice 10", 296.60, 56.01, 1.41, -180.32361f, -1.0946624f);
	
	private Location l;
	private String name;
	
	Locations(String name, double x, double y, double z, float yaw, float pitch){
		
		this.name = name;
		l = new Location(MainPlugin.instance.getServer().getWorld("world"), x, y, z, yaw, pitch);
		
	}
	
	public void teleport(Player p){
		p.teleport(l);
	}
	
	public Location getLocation(){
		return l;
	}
	
	public String getName(){
		return name;
	}
	
}
