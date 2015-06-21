package boot;

import model.ClientModel;
import presenter.ClientProperties;
import presenter.Presenter;
import view.gui.MazeWindow;

/**	This class is used in case that in the middle of the program the client decides to change interface - gui interface.
 * @author Alon
 *
 */
public class RunGUI {
	public void start(ClientProperties properties){
		MazeWindow v=new MazeWindow("Maze Generations", 600, 600);
		ClientModel m;
		m=new ClientModel(properties);
		Presenter p=new Presenter(m,v);
		v.addObserver(p);
		m.addObserver(p);
		v.start();
	}
}
