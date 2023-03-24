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

public class User {
	public User() {
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == ORMConstants.KEY_USER_GAMES) {
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
	
	private String email;
	
	private String password;
	
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
	
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return password;
	}
	
	private void setORM_Games(java.util.Set value) {
		this.ORM_games = value;
	}
	
	private java.util.Set getORM_Games() {
		return ORM_games;
	}
	
	public final pt.uminho.di.aa.GameSetCollection games = new pt.uminho.di.aa.GameSetCollection(this, _ormAdapter, ORMConstants.KEY_USER_GAMES, ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public ArrayList<pt.uminho.di.aa.Game> getGames() {
		//TODO: Implement Method
		throw new UnsupportedOperationException();
	}
	
	public void setGames(ArrayList<pt.uminho.di.aa.Game> games) {
		//TODO: Implement Method
		throw new UnsupportedOperationException();
	}
	
	public String toString() {
		return String.valueOf(getAttribute());
	}
	
}
