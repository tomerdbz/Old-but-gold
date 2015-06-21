package boot;

import java.beans.XMLDecoder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.MyModel;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import presenter.ClientProperties;
import presenter.Presenter;
import view.cli.MyView;
import view.gui.MazeWindow;

/** ID'S: 208415513, 318507209 - Tomer Cabouly, Alon Orlovsky 
 * Run ConfigureDB FIRST!!!
 * @author Tomer,Alon
 *	the main program 
 */
public class Run {
	public static void main(String []args)
	{
		//MazeWindow v=new MazeWindow("Check the Sonic Maze Runner Edition", 600, 600);
				WritePropertiesGUI guiProp=new WritePropertiesGUI();
				Display display=new Display();
				Shell shell=new Shell(display);
				guiProp.writeProperties(shell);
				MyModel m;
				ClientProperties prop;
				if((prop=readProperties())!=null)
				{
					m=new MyModel(prop);
					
					switch(prop.getUi())
					{
						case CLI:
							MyView v=new MyView(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
							Presenter p=new Presenter(m,v);
							v.addObserver(p);
							m.addObserver(p);
							v.start();
							break;
						case GUI:
							MazeWindow vMaze=new MazeWindow(display, shell, "bobo", 1300, 700);
							//MazeWindow vMaze=new MazeWindow(display,shell,"Maze Generations", 1300, 700,new view.gui.ThreeDMazeDisplay(shell, SWT.NONE));
							Presenter pMaze=new Presenter(m,vMaze);
							vMaze.addObserver(pMaze);
							m.addObserver(pMaze);
							vMaze.start();
							break;
						default:
							return;	
					}
				}
				else
					return;
			

		}
			public static ClientProperties readProperties()
			{
				XMLDecoder d;
				ClientProperties p=null;
				try {
					FileInputStream in=new FileInputStream("properties.xml");
					d=new XMLDecoder(in);
					p=(ClientProperties)d.readObject();
					System.out.println(p);
					d.close();
				} catch (IOException e) {
					return new ClientProperties();
				}
				return p;
			}
			/*public static MazeProperties readPropertiesM()
			{
				XMLDecoder d;
				MazeProperties p=null;
				try {
					FileInputStream in=new FileInputStream("properties.xml");
					d=new XMLDecoder(in);
					p=(MazeProperties)d.readObject();
					System.out.println(p);
					d.close();
				} catch (IOException e) {
					return new MazeProperties();
				}
				return p;
			}*/
		}
