package view.gui;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
/**
 * 
 * @author Alon
 * this is a widget representing the character in the maze
 * it extends canvas
 */

public class MazeCharacter extends CommonCharacter {
	 /**
	 *  Image Loader is used in order to take a gif and turn it into single pictures
	 */
	ImageLoader gifs=new ImageLoader();
	/**
	 * loads gif of character movment
	 */
	   ImageData[] images; 
	   /**
	    * frame of the gif at this point.
	    */
	   int frameIndex=0; 	

   /**
    * constructor
    * 
    * 
    */
	public MazeCharacter(Composite parent, int style) {
		super(parent, style);
		images=gifs.load(".\\resources\\images\\UpAndDown.gif"); 
		
	}

	@Override
	public ImageData[] getCharacterImagesArray() {
		return images;
	}
	@Override
	public void setCharacterImagesArray(ImageData[] images) {
		this.images = images;
	}
	@Override
	public int getCharacterImageIndex() {
		return frameIndex;
	}
	@Override
	public void setCharacterImageIndex(int frameIndex) {
		this.frameIndex = frameIndex;
	}


	
}
