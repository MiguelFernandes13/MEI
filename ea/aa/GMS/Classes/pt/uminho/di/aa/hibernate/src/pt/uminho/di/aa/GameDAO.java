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

import org.orm.*;
import org.hibernate.Query;
import org.hibernate.LockMode;
import java.util.List;

public class GameDAO {
	public static Game loadGameByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadGameByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game getGameByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return getGameByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadGameByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game getGameByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return getGameByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (Game) session.load(pt.uminho.di.aa.Game.class, Integer.valueOf(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game getGameByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (Game) session.get(pt.uminho.di.aa.Game.class, Integer.valueOf(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Game) session.load(pt.uminho.di.aa.Game.class, Integer.valueOf(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game getGameByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Game) session.get(pt.uminho.di.aa.Game.class, Integer.valueOf(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGame(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return queryGame(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGame(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return queryGame(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game[] listGameByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return listGameByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game[] listGameByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return listGameByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGame(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Game as Game");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGame(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Game as Game");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Game", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game[] listGameByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryGame(session, condition, orderBy);
			return (Game[]) list.toArray(new Game[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game[] listGameByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryGame(session, condition, orderBy, lockMode);
			return (Game[]) list.toArray(new Game[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadGameByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadGameByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Game[] games = listGameByQuery(session, condition, orderBy);
		if (games != null && games.length > 0)
			return games[0];
		else
			return null;
	}
	
	public static Game loadGameByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Game[] games = listGameByQuery(session, condition, orderBy, lockMode);
		if (games != null && games.length > 0)
			return games[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateGameByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return iterateGameByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateGameByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return iterateGameByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateGameByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Game as Game");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateGameByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Game as Game");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Game", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game createGame() {
		return new pt.uminho.di.aa.Game();
	}
	
	public static boolean save(pt.uminho.di.aa.Game game) throws PersistentException {
		try {
			AagamesPersistentManager.instance().saveObject(game);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(pt.uminho.di.aa.Game game) throws PersistentException {
		try {
			AagamesPersistentManager.instance().deleteObject(game);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(pt.uminho.di.aa.Game game)throws PersistentException {
		try {
			if (game.getPlatform() != null) {
				game.getPlatform().games.remove(game);
			}
			
			return delete(game);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(pt.uminho.di.aa.Game game, org.orm.PersistentSession session)throws PersistentException {
		try {
			if (game.getPlatform() != null) {
				game.getPlatform().games.remove(game);
			}
			
			try {
				session.delete(game);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(pt.uminho.di.aa.Game game) throws PersistentException {
		try {
			AagamesPersistentManager.instance().getSession().refresh(game);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(pt.uminho.di.aa.Game game) throws PersistentException {
		try {
			AagamesPersistentManager.instance().getSession().evict(game);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Game loadGameByCriteria(GameCriteria gameCriteria) {
		Game[] games = listGameByCriteria(gameCriteria);
		if(games == null || games.length == 0) {
			return null;
		}
		return games[0];
	}
	
	public static Game[] listGameByCriteria(GameCriteria gameCriteria) {
		return gameCriteria.listGame();
	}
}
