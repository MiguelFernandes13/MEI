/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Miguel Fernandes(Universidade do Minho)
 * License Type: Academic
 */
package pt.uminho.di.aa;

import java.util.ArrayList;

public class Platform {
	public Platform() {
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == ORMConstants.KEY_PLATFORM_GAMES) {
			return ORM_games;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private int attribute;
	
	private int ID;
	
	private String name;
	
	private int year;
	
	private String description;
	
	private String manufacturer;
	
	private java.util.Set ORM_games = new java.util.HashSet();
	
	public void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	private void setAttribute(int value) {
		this.attribute = value;
	}
	
	public int getAttribute() {
		return attribute;
	}
	
	public int getORMID() {
		return getAttribute();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setYear(int value) {
		this.year = value;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setManufacturer(String value) {
		this.manufacturer = value;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	private void setORM_Games(java.util.Set value) {
		this.ORM_games = value;
	}
	
	private java.util.Set getORM_Games() {
		return ORM_games;
	}
	
	public final pt.uminho.di.aa.GameSetCollection games = new pt.uminho.di.aa.GameSetCollection(this, _ormAdapter, ORMConstants.KEY_PLATFORM_GAMES, ORMConstants.KEY_GAME_PLATFORM, ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public ArrayList<Game> getGames() {
		//TODO: Implement Method
		return (ArrayList<Game>) getORM_Games();
	}
	
	public void setGames(ArrayList<Game> games) {
		this.setORM_Games(ORM_games);
	}
	
	public String toString() {
		return String.valueOf(getAttribute());
	}
	
}
