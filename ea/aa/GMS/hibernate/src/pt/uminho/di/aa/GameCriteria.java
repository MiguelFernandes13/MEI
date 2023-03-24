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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class GameCriteria extends AbstractORMCriteria {
	public final IntegerExpression attribute;
	public final IntegerExpression platformId;
	public final AssociationExpression platform;
	public final IntegerExpression ID;
	public final StringExpression name;
	public final IntegerExpression year;
	public final DoubleExpression price;
	public final StringExpression description;
	
	public GameCriteria(Criteria criteria) {
		super(criteria);
		attribute = new IntegerExpression("attribute", this);
		platformId = new IntegerExpression("platform.attribute", this);
		platform = new AssociationExpression("platform", this);
		ID = new IntegerExpression("ID", this);
		name = new StringExpression("name", this);
		year = new IntegerExpression("year", this);
		price = new DoubleExpression("price", this);
		description = new StringExpression("description", this);
	}
	
	public GameCriteria(PersistentSession session) {
		this(session.createCriteria(Game.class));
	}
	
	public GameCriteria() throws PersistentException {
		this(AagamesPersistentManager.instance().getSession());
	}
	
	public PlatformCriteria createPlatformCriteria() {
		return new PlatformCriteria(createCriteria("platform"));
	}
	
	public Game uniqueGame() {
		return (Game) super.uniqueResult();
	}
	
	public Game[] listGame() {
		java.util.List list = super.list();
		return (Game[]) list.toArray(new Game[list.size()]);
	}
}

