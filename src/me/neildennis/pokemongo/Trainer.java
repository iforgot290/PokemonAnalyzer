package me.neildennis.pokemongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.util.PokeDictionary;

import POGOProtos.Enums.PokemonIdOuterClass.PokemonId;

public class Trainer {
	
	private PokemonGo go;
	
	public Trainer(PokemonGo go){
		this.go = go;
	}
	
	public void analyzePokemon(){
		//List<Pokemon> strongest = new ArrayList<Pokemon>();
		
		for (int id = 1; id < 152; id++){
			Pokemon strong = null;
			List<Pokemon> therest = new ArrayList<Pokemon>();
			
			for (Pokemon poke : getPokemonsById(id)){
				if (strong == null){
					strong = poke;
					continue;
				}
				
				if (strong.getCpAfterFullEvolve() < poke.getCpAfterFullEvolve()){
					therest.add(strong);
					strong = poke;
					continue;
				} else {
					therest.add(poke);
				}
			}
			
			if (strong == null) continue;
			
			String name = PokeDictionary.getDisplayName(id, Locale.ENGLISH);
			int currentcp = strong.getCp();
			
			Main.print("Strongest " + name + " is " + currentcp + " CP current");
			Main.print("      " + strong.getCpAfterFullEvolve() + " CP after full evolve no powerup");
			Main.print("      " + strong.getMaxCpFullEvolveAndPowerupForPlayer() + " CP max evolve and powerup");
			Main.print(" ");
			
			for (Pokemon poke : therest){
				Main.print("Other " + name + " is " + poke.getCp() + " CP current");
				Main.print("      " + poke.getCpAfterFullEvolve() + " CP after full evolve no powerup");
				Main.print("      " + poke.getMaxCpFullEvolveAndPowerupForPlayer() + " CP max evolve and powerup");
				Main.print(" ");
			}
		}
	}
	
	private List<Pokemon> getPokemonsById(int id){
		return go.getInventories().getPokebank().getPokemonByPokemonId(PokemonId.forNumber(id));
	}
	
	

}
