/**
 * Licensee: Miguel Fernandes(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class ListAagamesData {
	private static final int ROW_COUNT = 100;
	
	public void listTestData() throws PersistentException {
		System.out.println("Listing User...");
		pt.uminho.di.aa.User[] ptuminhodiaaUsers = pt.uminho.di.aa.UserDAO.listUserByQuery(null, null);
		int length = Math.min(ptuminhodiaaUsers.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(ptuminhodiaaUsers[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Game...");
		pt.uminho.di.aa.Game[] ptuminhodiaaGames = pt.uminho.di.aa.GameDAO.listGameByQuery(null, null);
		length = Math.min(ptuminhodiaaGames.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(ptuminhodiaaGames[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
		System.out.println("Listing Platform...");
		pt.uminho.di.aa.Platform[] ptuminhodiaaPlatforms = pt.uminho.di.aa.PlatformDAO.listPlatformByQuery(null, null);
		length = Math.min(ptuminhodiaaPlatforms.length, ROW_COUNT);
		for (int i = 0; i < length; i++) {
			System.out.println(ptuminhodiaaPlatforms[i]);
		}
		System.out.println(length + " record(s) retrieved.");
		
	}
	
	public void listByCriteria() throws PersistentException {
		System.out.println("Listing User by Criteria...");
		pt.uminho.di.aa.UserCriteria lptuminhodiaaUserCriteria = new pt.uminho.di.aa.UserCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lptuminhodiaaUserCriteria.attribute.eq();
		lptuminhodiaaUserCriteria.setMaxResults(ROW_COUNT);
		pt.uminho.di.aa.User[] ptuminhodiaaUsers = lptuminhodiaaUserCriteria.listUser();
		int length =ptuminhodiaaUsers== null ? 0 : Math.min(ptuminhodiaaUsers.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(ptuminhodiaaUsers[i]);
		}
		System.out.println(length + " User record(s) retrieved."); 
		
		System.out.println("Listing Game by Criteria...");
		pt.uminho.di.aa.GameCriteria lptuminhodiaaGameCriteria = new pt.uminho.di.aa.GameCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lptuminhodiaaGameCriteria.attribute.eq();
		lptuminhodiaaGameCriteria.setMaxResults(ROW_COUNT);
		pt.uminho.di.aa.Game[] ptuminhodiaaGames = lptuminhodiaaGameCriteria.listGame();
		length =ptuminhodiaaGames== null ? 0 : Math.min(ptuminhodiaaGames.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(ptuminhodiaaGames[i]);
		}
		System.out.println(length + " Game record(s) retrieved."); 
		
		System.out.println("Listing Platform by Criteria...");
		pt.uminho.di.aa.PlatformCriteria lptuminhodiaaPlatformCriteria = new pt.uminho.di.aa.PlatformCriteria();
		// Please uncomment the follow line and fill in parameter(s) 
		//lptuminhodiaaPlatformCriteria.attribute.eq();
		lptuminhodiaaPlatformCriteria.setMaxResults(ROW_COUNT);
		pt.uminho.di.aa.Platform[] ptuminhodiaaPlatforms = lptuminhodiaaPlatformCriteria.listPlatform();
		length =ptuminhodiaaPlatforms== null ? 0 : Math.min(ptuminhodiaaPlatforms.length, ROW_COUNT); 
		for (int i = 0; i < length; i++) {
			 System.out.println(ptuminhodiaaPlatforms[i]);
		}
		System.out.println(length + " Platform record(s) retrieved."); 
		
	}
	
	public static void main(String[] args) {
		try {
			ListAagamesData listAagamesData = new ListAagamesData();
			try {
				listAagamesData.listTestData();
				//listAagamesData.listByCriteria();
			}
			finally {
				pt.uminho.di.aa.AagamesPersistentManager.instance().disposePersistentManager();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
