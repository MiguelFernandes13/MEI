/**
 * Licensee: Miguel Fernandes(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class CreateAagamesData {
	public void createTestData() throws PersistentException {
		PersistentTransaction t = pt.uminho.di.aa.AagamesPersistentManager.instance().getSession().beginTransaction();
		try {
			pt.uminho.di.aa.User lptuminhodiaaUser = pt.uminho.di.aa.UserDAO.createUser();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : games
			pt.uminho.di.aa.UserDAO.save(lptuminhodiaaUser);
			pt.uminho.di.aa.Game lptuminhodiaaGame = pt.uminho.di.aa.GameDAO.createGame();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : price, year, platform
			pt.uminho.di.aa.GameDAO.save(lptuminhodiaaGame);
			pt.uminho.di.aa.Platform lptuminhodiaaPlatform = pt.uminho.di.aa.PlatformDAO.createPlatform();
			// TODO Initialize the properties of the persistent object here, the following properties must be initialized before saving : games, year
			pt.uminho.di.aa.PlatformDAO.save(lptuminhodiaaPlatform);
			t.commit();
		}
		catch (Exception e) {
			t.rollback();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			CreateAagamesData createAagamesData = new CreateAagamesData();
			try {
				createAagamesData.createTestData();
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
