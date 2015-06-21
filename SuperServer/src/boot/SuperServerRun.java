package boot;

import gui.ClassInputDialog;
import model.RemoteControlUDPServer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import presenter.ServerProperties;

public class SuperServerRun {

	public static void main(String[] args) {
		Display display=new Display();
		Shell shell=new Shell(display);
		ClassInputDialog dlg=new ClassInputDialog(shell, ServerProperties.class);
		ServerProperties properties=(ServerProperties)dlg.open();
		if(properties!=null)
		{
			MessageBox messageBox = new MessageBox(shell,SWT.OK | SWT.Activate);
	        messageBox.setText("Maze Generations");
	        messageBox.setMessage("Server is Operating");
			messageBox.open();
			new RemoteControlUDPServer(properties).run();
		}
	}

}