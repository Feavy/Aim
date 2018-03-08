package fr.feavy.exercices;

import java.util.HashMap;
import java.util.Map;

public class ExerciceList {

	public static Map<String, Exercice> exercices = new HashMap<String, Exercice>();
	
	public static void initialize(){
		
		exercices.put("1", new Exercice1());
		exercices.put("2", new Exercice2());
		exercices.put("3", new Exercice3());
		exercices.put("4", new Exercice4());
		exercices.put("5", new Exercice5());
		exercices.put("6", new Exercice6());
		exercices.put("7", new Exercice7());
		exercices.put("8", new Exercice8());
		exercices.put("9", new Exercice9());
		exercices.put("10", new Exercice10());
		
	}
	
}
