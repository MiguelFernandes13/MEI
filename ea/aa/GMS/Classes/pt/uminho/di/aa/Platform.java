package pt.uminho.di.aa;

import java.util.ArrayList;
import pt.uminho.di.aa.Game;

public class Platform {
	private int iD;
	private String name;
	private int year;
	private String description;
	private String manufacturer;
	private ArrayList<Game> games = new ArrayList<Game>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
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