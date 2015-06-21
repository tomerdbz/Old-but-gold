package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.ClientModel;
import presenter.ClientProperties;
import presenter.Presenter;
import view.cli.MyView;

/** This class is used when the client decides to change in the middle of the program to CLI interface.
 * @author Tomer,Alon
 *
 */
public class RunCLI {

	public void startProgram(ClientProperties properties) {
		MyView v=new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		ClientModel m;
		m=new ClientModel(properties);
		Presenter p=new Presenter(m,v);
		v.addObserver(p);
		m.addObserver(p);
		v.cl.addObserver(v);
		v.start();
	}
	}