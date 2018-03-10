package fr.feavy.data;

import java.util.HashMap;
import java.util.Map;

public class Strings {

	public static String prefix = "§7§l[§c§lAim§7§l] ";
	public static Map<Integer, String> resultMessages = new HashMap<Integer, String>();
	
	public static void initialize(){
		resultMessages.put(0, "§cVotre aim n'est pas très bonne, il va falloir vous entraîner. Commencez par réessayez cet exercice.");
		resultMessages.put(1, "§6Votre aim est moyenne, continuez de vous entraîner.");
		resultMessages.put(2, "§aVotre aim est bonne, mais elle pourrait être encore meilleure.");
		resultMessages.put(3, "§bVotre aim est §aE§cX§9C§dE§7L§bL§1E§6N§3T§eE §b! Vous pouvez passer aux autres exercices.");
	}
	
}
