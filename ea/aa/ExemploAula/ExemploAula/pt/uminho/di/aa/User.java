package pt.uminho.di.aa;

import java.util.ArrayList;
import pt.uminho.di.aa.Game;

public class User {
	private int iD;
	private String name;
	private String email;
	private String password;
	private ArrayList<Game> games = new ArrayList<Game>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addGames(Game games) {
		this.games.add(games);
	}

	public void removeGames(Game games) {
		this.games.remove(games);
	}

	public Game[] toGamesArray() {
		Game[] lGames_Temp = new Game[this.games.size()];
		this.games.toArray(lGames_Temp);
		return lGames_Temp;
	}
}