/**
 * Licensee: Miguel Fernandes(Universidade do Minho)
 * License Type: Academic
 */
package ormsamples;

import org.orm.*;
public class CreateAagamesDatabaseSchema {
	public static void main(String[] args) {
		try {
			ORMDatabaseInitiator.createSchema(pt.uminho.di.aa.AagamesPersistentManager.instance());
			pt.uminho.di.aa.AagamesPersistentManager.instance().disposePersistentManager();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
