package view.gui;

import org.eclipse.swt.graphics.ImageData;
/**
 * 
 * @author Alon,Tomer
 * this is an interface for a later to be used character
 */
public interface Character {
	/**
	 * Draw The character
	 */
	public void drawCharacter();
	/**
	 * 
	 * @return the array image of all character images
	 */
	public ImageData[] getCharacterImagesArray();
	/**
	 * 
	 * @param images the character images
	 */
	public void setCharacterImagesArray(ImageData[] images);
	/**
	 * 
	 * @return which index is the character we use now
	 */
	public int getCharacterImageIndex();
	/**
	 * 
	 * @param frameIndex on which index is the character we use now
	 */
	public void setCharacterImageIndex(int frameIndex);
}
