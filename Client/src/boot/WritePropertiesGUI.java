package boot;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.swt.widgets.Shell;

import presenter.ClientProperties;
import view.gui.ClassInputDialog;

/** This class is reponsible to write Properties in a GUI UI.
 * @author Tomer
 *
 */
public class WritePropertiesGUI {
	/**	Writes the properties after prompting a window to submit them.
	 * 
	 */
	public int writeProperties(Shell shell)
	{
		XMLEncoder e;
		//Display display = new Display();
	    //Shell shell = new Shell(display);

	    ClassInputDialog dlg = new ClassInputDialog(shell,ClientProperties.class);
	    ClientProperties input = (ClientProperties) dlg.open();
	    if (input != null) {
	      // User clicked OK; set the text into the label
	    	try {
				e = new XMLEncoder(new FileOutputStream("properties.xml"));
				e.writeObject(input);
				e.flush();
				e.close();
				return 0;
	    	} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	    }
	    /*
	  //A check - for debug only!

		XMLDecoder d;
		Properties p=null;
		try {
			FileInputStream in=new FileInputStream("properties.xml");
			d=new XMLDecoder(in);
			p=(Properties)d.readObject();
			System.out.println(p);
			d.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	     */
	    return -1;
	}
}
