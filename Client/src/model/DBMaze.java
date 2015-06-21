package model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;


/**This class represents the table in the Database - the annotations were finally not used due to a mapping file, but remained for readability.
 * for this class - we had to serialize Maze and Solution - thus rewriting them and complex objects they have contained.
 * NOTICE: Maze and Solution are represented as a blob - we serialized them with hibernate tools and wrote them that way.
 * @author Tomer,Alon
 *
 */
@Entity
public class DBMaze {
	/**
	 * Maze Name
	 */
	private String name;
	/**
	 * a Maze as a blob
	 * Mazes will be Serialized and kept here - then saved to DB.
	 */
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private Blob maze;
	/**
	 * a solution as a blob
	 * Soltuons will be Serialized and kept here - then saved to DB.
	 */
	@Lob()
	@Column(columnDefinition = "LONGBLOB")
	private Blob solution;
	public DBMaze() {
	}
	public DBMaze(String name,Blob maze,Blob solution) {
		this.name=name;
		this.maze=maze;
		this.solution=solution;
	}
	@Id
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Blob getMaze() {
		return maze;
	}

	public void setMaze(Blob maze) {
		this.maze = maze;
	}
	public Blob getSolution() {
		return solution;
	}

	public void setSolution(Blob solution) {
		this.solution = solution;
	}
	
}
