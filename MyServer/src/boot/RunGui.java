package boot;

import model.MyModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.Presenter;
import presenter.RemoteControlProperties;
import view.ServerWindow;
/**
 * 
 * @author Alon
 *This class is a lot like the Main class yet is
 *created in case we want to rewrite or load properties during run time
 *and this allows us to change it.
 */
public class RunGui {

	public void  loadWindow(RemoteControlProperties sp){
		Display display= new Display(); //creating display
		Shell shell=new Shell(display); //creating a shell
		ServerWindow SE= new ServerWindow("StarShip phoenix",500,500,display,shell); //creating the window
		MyModel m = new MyModel(sp); // creating the model with appropriate Properties
		Presenter p = new Presenter(m, SE); //creating the presenter
		m.addObserver(p); //adding the observers for the MVP Architecture
		SE.addObserver(p);
		SE.run(); //RUNNING
	}
}
