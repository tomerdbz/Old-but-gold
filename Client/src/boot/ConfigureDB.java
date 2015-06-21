package boot;

import model.DBMaze;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/** This class main has to run prior to running the main Run - it configures the Database.
 * @author Tomer,Alon
 *
 */
public class ConfigureDB {
	public static void main(String [] args)
	{
		//
		AnnotationConfiguration config;
		config = new AnnotationConfiguration();
		config.addAnnotatedClass(DBMaze.class);
		config.configure();
		new SchemaExport(config).create(true, true);
		
	}

}
