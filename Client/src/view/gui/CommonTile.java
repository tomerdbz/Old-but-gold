package view.gui;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonTile extends Canvas implements Tile{
	
	/**
	 * image of the cell (the way the cell looks walls up down right or left 
	 */
	Image cellImage; 
	String imageName;
	/**
	 *  hint represnets a special image that shows the user
	 */
	Image Hint=null; 
	/**
	 * a represenation of character if not null the character is in the cell
	 */
	CommonCharacter ch = null;  
	/**
	 * does the tile represent the goal or not
	 */
	Image goal =null; 
	public CommonTile(Composite parent, int style) {
		super(parent, style);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {   
				drawTile(e);
		}
	});
	}
	/**
	 * Drawing a tile using a paint event
	 * every tile has a different way of being drawed
	 */
	@Override
	public abstract void drawTile(PaintEvent e);
	/**
	 * 
	 * Setter
	 */
	public  void setHint(Image img){
		this.Hint=img;
	}
	/**
	 * 
	 * getter
	 */
	public Image getHint(){
		return this.Hint;
	}
	/**
	 * 
	 * setter
	 * 
	 */
	public  void setGoal(Image img){
		this.goal=img;
	}
	/**
	 * getter
	 * 
	 */
	public  Image getGoal(){
		return this.goal;
	}
	/**
	 * setter
	 * 
	 */
	public void setCellImage(Image img){
		this.cellImage=img;
	}
	/**
	 * setter
	 */
	public  void setCharacter(CommonCharacter character){
		this.ch=character;
	}
	/**
	 * getter
	 * 
	 */
	public  String getImageName()
	{
		return this.imageName;
	}
	/**
	 * setter
	 */
	public void setImageName(String name)
	{
		this.imageName=name;
	}
	/**
	 * getter
	 */
	public  Image getCellImage(){
		return this.cellImage;
	}
	/**
	 * 
	 * getter
	 */
	public  CommonCharacter getCharacter(){
		return this.ch;
	}
	

}
