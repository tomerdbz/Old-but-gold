package boot;

import model.MyModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.Presenter;
import view.ServerWindow;
import view.WriteServerPropertiesGUI;
/**
 * 
 * @author Alon
 *The main class used to execute the program
 */
public class RemoteControlRun {

	public static void main(String[] args) {
		WriteServerPropertiesGUI sp = new WriteServerPropertiesGUI(); //we start by writing the properties we need for the server
		Display display= new Display(); // creating display
		Shell shell=new Shell(display); // creating a shell
		sp.writeProperties(display, shell); // writing the properties onto the display and shell
		ServerWindow SE= new ServerWindow("StarShip phoenix",500,500,display,shell);
		MyModel m = new MyModel(ServerWindow.readProperties()); //writing the properties to the model
		Presenter p = new Presenter(m, SE); // creating presenter
		m.addObserver(p); //adding observers and running
		SE.addObserver(p);
		SE.run();

	}
}

