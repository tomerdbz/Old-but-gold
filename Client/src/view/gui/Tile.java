package view.gui;

import org.eclipse.swt.events.PaintEvent;
/**
 * 
 * @author Alon,Tomer
 * A tile to be presented in a display
 */
 interface Tile {
	 /**
	  * Each tile has a different way of drawing therefore
	  * we create a function that know how to draw different tiles
	  * when implemented
	  * @param e the paint event allowing us to draw the tile
	  */
	void drawTile(PaintEvent e);
}
