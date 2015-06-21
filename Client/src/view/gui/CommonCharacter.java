package view.gui;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public abstract class CommonCharacter extends Canvas implements Character{

	  
		   /**
		    * row in the maze
		    */
		   int currentCellX=0;
		   /**
		    * col in the maze
		    */
		   int currentCellY=0;


	   /**
	    * constructor
	    * 
	    * 
	    */
	public CommonCharacter(Composite parent, int style) {
		super(parent, style);
	}
	/**
	 * a function for drawing the character
	 */
	@Override
	public void drawCharacter() {
		this.redraw();
		
	}

	public abstract ImageData[] getCharacterImagesArray();
	public abstract void setCharacterImagesArray(ImageData[] images);
	public abstract int getCharacterImageIndex();
	public abstract void setCharacterImageIndex(int frameIndex);
	/**
	 * getter
	 * @return current character row in the array
	 */
	public int getCurrentCellX() {
		return currentCellX;
	}
	/**
	 * setter
	 * @param currentCellX current characterrow in the array
	 */
	public void setCurrentCellX(int currentCellX) {
		this.currentCellX = currentCellX;
	}
	/**
	 * 
	 * @return current column of character in the array
	 */
	public int getCurrentCellY() {
		return currentCellY;
	}
	/**
	 * 
	 * @param currentCellY current column of character in the array
	 */
	public void setCurrentCellY(int currentCellY) {
		this.currentCellY = currentCellY;
	}


}
