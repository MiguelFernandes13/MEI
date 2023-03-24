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

public class Game {
	public Game() {
	}
	
	private void this_setOwner(Object owner, int key) {
		if (key == ORMConstants.KEY_GAME_PLATFORM) {
			this.platform = (pt.uminho.di.aa.Platform) owner;
		}
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public void setOwner(Object owner, int key) {
			this_setOwner(owner, key);
		}
		
	};
	
	private int attribute;
	
	private pt.uminho.di.aa.Platform platform;
	
	private int ID;
	
	private String name;
	
	private int year;
	
	private double price;
	
	private String description;
	
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
	
	public void setPrice(double value) {
		this.price = value;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setPlatform(pt.uminho.di.aa.Platform value) {
		if (platform != null) {
			platform.games.remove(this);
		}
		if (value != null) {
			value.games.add(this);
		}
	}
	
	public pt.uminho.di.aa.Platform getPlatform() {
		return platform;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_Platform(pt.uminho.di.aa.Platform value) {
		this.platform = value;
	}
	
	private pt.uminho.di.aa.Platform getORM_Platform() {
		return platform;
	}
	
	public String toString() {
		return String.valueOf(getAttribute());
	}
	
}
