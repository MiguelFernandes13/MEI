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

public class PlatformDAO {
	public static Platform loadPlatformByORMID(int attribute) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadPlatformByORMID(session, attribute);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform getPlatformByORMID(int attribute) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return getPlatformByORMID(session, attribute);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByORMID(int attribute, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadPlatformByORMID(session, attribute, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform getPlatformByORMID(int attribute, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return getPlatformByORMID(session, attribute, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByORMID(PersistentSession session, int attribute) throws PersistentException {
		try {
			return (Platform) session.load(pt.uminho.di.aa.Platform.class, Integer.valueOf(attribute));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform getPlatformByORMID(PersistentSession session, int attribute) throws PersistentException {
		try {
			return (Platform) session.get(pt.uminho.di.aa.Platform.class, Integer.valueOf(attribute));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByORMID(PersistentSession session, int attribute, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Platform) session.load(pt.uminho.di.aa.Platform.class, Integer.valueOf(attribute), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform getPlatformByORMID(PersistentSession session, int attribute, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Platform) session.get(pt.uminho.di.aa.Platform.class, Integer.valueOf(attribute), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPlatform(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return queryPlatform(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPlatform(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return queryPlatform(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform[] listPlatformByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return listPlatformByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform[] listPlatformByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return listPlatformByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPlatform(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Platform as Platform");
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
	
	public static List queryPlatform(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Platform as Platform");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Platform", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform[] listPlatformByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryPlatform(session, condition, orderBy);
			return (Platform[]) list.toArray(new Platform[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform[] listPlatformByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryPlatform(session, condition, orderBy, lockMode);
			return (Platform[]) list.toArray(new Platform[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadPlatformByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return loadPlatformByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Platform[] platforms = listPlatformByQuery(session, condition, orderBy);
		if (platforms != null && platforms.length > 0)
			return platforms[0];
		else
			return null;
	}
	
	public static Platform loadPlatformByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Platform[] platforms = listPlatformByQuery(session, condition, orderBy, lockMode);
		if (platforms != null && platforms.length > 0)
			return platforms[0];
		else
			return null;
	}
	
	public static java.util.Iterator iteratePlatformByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return iteratePlatformByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iteratePlatformByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = AagamesPersistentManager.instance().getSession();
			return iteratePlatformByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iteratePlatformByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Platform as Platform");
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
	
	public static java.util.Iterator iteratePlatformByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pt.uminho.di.aa.Platform as Platform");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Platform", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform createPlatform() {
		return new pt.uminho.di.aa.Platform();
	}
	
	public static boolean save(pt.uminho.di.aa.Platform platform) throws PersistentException {
		try {
			AagamesPersistentManager.instance().saveObject(platform);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(pt.uminho.di.aa.Platform platform) throws PersistentException {
		try {
			AagamesPersistentManager.instance().deleteObject(platform);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(pt.uminho.di.aa.Platform platform)throws PersistentException {
		try {
			pt.uminho.di.aa.Game[] lGamess = platform.games.toArray();
			for(int i = 0; i < lGamess.length; i++) {
				lGamess[i].setPlatform(null);
			}
			return delete(platform);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(pt.uminho.di.aa.Platform platform, org.orm.PersistentSession session)throws PersistentException {
		try {
			pt.uminho.di.aa.Game[] lGamess = platform.games.toArray();
			for(int i = 0; i < lGamess.length; i++) {
				lGamess[i].setPlatform(null);
			}
			try {
				session.delete(platform);
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
	
	public static boolean refresh(pt.uminho.di.aa.Platform platform) throws PersistentException {
		try {
			AagamesPersistentManager.instance().getSession().refresh(platform);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(pt.uminho.di.aa.Platform platform) throws PersistentException {
		try {
			AagamesPersistentManager.instance().getSession().evict(platform);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Platform loadPlatformByCriteria(PlatformCriteria platformCriteria) {
		Platform[] platforms = listPlatformByCriteria(platformCriteria);
		if(platforms == null || platforms.length == 0) {
			return null;
		}
		return platforms[0];
	}
	
	public static Platform[] listPlatformByCriteria(PlatformCriteria platformCriteria) {
		return platformCriteria.listPlatform();
	}
}
