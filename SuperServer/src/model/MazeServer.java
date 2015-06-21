package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.util.SerializationHelper;

import presenter.ServerProperties;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;

/**	This Class defines a TCP/IP Server which loads data from DB - Hibernate
 * upon closing the Server it saves the data in the DB.
 * @author Tomer
 *
 */
public class MazeServer extends MyTCPIPServer implements Runnable {

	/**	every solved maze will be inserted to the cache - a HashMap linking between a maze and its solution. 
	 * notice the Concurrent because of the workings with Threads.
	 * 
	 */
	ConcurrentHashMap<Maze,Solution> cache=new ConcurrentHashMap<Maze,Solution>();
	/**	Temporary variable for Hint calculation. after calculating a hint - the hint will be saved here as a State for the Presenter to pick him up.
	 * 
	 */
	ConcurrentHashMap<String,State> hints=new ConcurrentHashMap<String,State>();
	/**	databaseNames will contain names of mazes which were brought from the database. when we'll write back at the end we won't write them again..
	 * 
	 */
	ConcurrentLinkedQueue<String> databaseNames=new ConcurrentLinkedQueue<String>();
	
	/** Any generated maze will be inserted to this field, a HashMap mapping between Maze Names and the mazes themselves.
	 * 
	 */
	ConcurrentHashMap<String,Maze> generatedMazes=new ConcurrentHashMap<String,Maze>();
	
	
	/** Ct'r that gets a MazeClientHandler to inject to TCP/IPServer and the Server properties.  
	 * @param serverProperties
	 * @param clientHandler
	 */
	public MazeServer(ServerProperties serverProperties, MazeClientHandler clientHandler) {
		super(serverProperties, clientHandler);
		loadFromDatabase();
	}
	@Override
	public void stoppedServer() {
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		ConcurrentLinkedQueue<String> names=namesToWriteToDB();
		for(String str : names)
		{
			if(cache.containsKey(generatedMazes.get(str)))
			{
				byte[] mazeToWrite=SerializationHelper.serialize(new SerializableMaze(generatedMazes.get(str)));
				byte[] solutionToWrite=SerializationHelper.serialize(new SerializableSolution(cache.get(generatedMazes.get(str))));
				DBMaze data=new DBMaze(str,Hibernate.createBlob(mazeToWrite),Hibernate.createBlob(solutionToWrite));
				session.save(data);//add flush every once in a while - 20?
			}
		}
		session.getTransaction().commit();
		super.stoppedServer();
	}
	
	
	
	
	/**
	 * 	loads mazes and solutions from the database. inserts them to the data structures generatedMazes, cache,...
	 */
	private void loadFromDatabase()
	{
		SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
		Session session = factory.openSession();
		Query query = session.createQuery("from model.DBMaze");

		@SuppressWarnings("unchecked")
		List <DBMaze>list = query.list();
		Iterator<DBMaze> it=list.iterator();
		DBMaze dbmaze;
		while(it.hasNext())
		{
			dbmaze=it.next();
			databaseNames.add(dbmaze.getName());
			
			try {
				byte[] mazeBytes;
				Blob bMaze=dbmaze.getMaze();
				int bMazeLength=(int)bMaze.length();
				mazeBytes=bMaze.getBytes(1, bMazeLength);
				if(bMazeLength!=bMaze.length())//data loss for really really big mazes - we will get one byte array and then another - then merge.
				{
					int diff=(int)(bMaze.length()-(long)bMazeLength);//long= 2*int - will only need to do this once!
					byte[] mazeBytes2=bMaze.getBytes(bMazeLength, diff);
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
					try {
						outputStream.write( mazeBytes);
						outputStream.write( mazeBytes2 );
						mazeBytes = outputStream.toByteArray( );
					} catch (IOException e) {

					}
					
				}
				Maze maze = ((SerializableMaze) (SerializationHelper.deserialize(mazeBytes))).getOriginalMaze();
				generatedMazes.put(dbmaze.getName(), maze);
				Blob bSolution=dbmaze.getSolution();
				int bSolutionLength=(int)bSolution.length();
				byte[] solutionBytes=bSolution.getBytes(1, bSolutionLength);
				if(bSolutionLength!=bSolution.length())//data loss for really really big mazes - we will get one byte array and then another - then merge.
				{
					int diff=(int)(bSolution.length()-(long)bSolutionLength);//long= 2*int - will only need to do this once!
					byte[] solutionBytes2=bSolution.getBytes(bSolutionLength, diff);
					
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
					try {
						outputStream.write( solutionBytes);
						outputStream.write( solutionBytes2 );
						solutionBytes = outputStream.toByteArray( );
					} catch (IOException e) {

					}
					
				}
				SerializableSolution sol=(SerializableSolution)SerializationHelper.deserialize(solutionBytes);
				cache.put(maze, sol.getOriginalSolution());
			} catch (Exception e1) {

			} 
		}
	}
	
	
	
	
	/**Helper to stop - to know based on the field databaseNames what mazes should be written to DB. (don't wanna write them again)
	 * @return a concurrent linked queue containing mazes' names that should be written to DB. 
	 */
	private ConcurrentLinkedQueue<String> namesToWriteToDB()
	{
		ConcurrentLinkedQueue<String> names=new ConcurrentLinkedQueue<String>(); 
		for(String str : generatedMazes.keySet())
		{
			if(!databaseNames.contains(str))
				names.add(str);
		}
		return names;
	}
	/** start server as Runnable.
	 */
	@Override
	public void run() {
		startServer();
	}
	
	
	
	
}
