package me.neildennis.pokemongo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import com.pokegoapi.exceptions.LoginFailedException;
import com.pokegoapi.exceptions.RemoteServerException;

import okhttp3.OkHttpClient;

public class Main {
	
	private boolean running = true;
	
	private OkHttpClient http;
	private GoogleUserCredentialProvider provider;
	
	private PokemonGo go;
	private Scanner sc;
	
	public Main(){
		
	}
	
	public static void main(String[] args){
		Main main = new Main();
		
		try {
			main.login();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (LoginFailedException e) {
			e.printStackTrace();
		} catch (RemoteServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		main.loop();
	}
	
	public void login() throws LoginFailedException, RemoteServerException, MalformedURLException, IOException, URISyntaxException{
		http = new OkHttpClient();
		provider = new GoogleUserCredentialProvider(http);
		
		Desktop.getDesktop().browse(new URL(GoogleUserCredentialProvider.LOGIN_URL).toURI());
		sc = new Scanner(System.in);
		String access = sc.nextLine();
		
		provider.login(access);
		go = new PokemonGo(http);
		go.login(provider);
	}
	
	private void loop(){
		while (running){
			String line = sc.nextLine();
			
			if (line.equalsIgnoreCase("exit")){
				print("Goodbye");
				running = false;
			} else {
				Trainer trainer = new Trainer(go);
				trainer.analyzePokemon();
			}
		}
	}
	
	public static void print(Object obj){
		System.out.println(obj);
	}

}
