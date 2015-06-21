package view.gui;


import jaco.mp3.player.MP3Player;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import presenter.ClientProperties;
import presenter.Presenter.Command;
import view.cli.View;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import algorithms.search.State;
import boot.Run;
import boot.RunCLI;
import boot.RunGUI;
import boot.WritePropertiesGUI;
/**
 * 
 * @author Alon,Tomer
 * This class is an extension of the basic windows and is the main window of the program
 * it is also an implementation of the view as part of the MVP system
 *
 */
public class MazeWindow extends BasicWindow implements View {
	/**
	 * a hash map which is safe to work with threads containing string
	 * and there matching command
	 */
	protected ConcurrentHashMap<String, presenter.Presenter.Command>  commands;
	/**
	 * last command that has been used in the mvp system
	 */
	protected Command LastUserCommand =null;
	/**
	 * an instance of boardWidget which represents the maze
	 */
	CommonBoard boardWidget;
	/**
	 * properties of the maze
	 * Thread pools Random maze generator or dfs and so...
	 */
	ClientProperties properties;
	/**
	 * name of the maze
	 */
	String mazeName=null;
	/**
	 * true if the data we sent already exists in the database
	 */
	Maze dataRecieved=null; 
	/**
	 * represents the maze properties
	 */
	MazeProperties input;
	/**
	 * Constructor
	 */
	public MazeWindow(Display display,Shell shell,String title, int width, int height) {
		super(display,shell,title,width,height);
	}
	/**
	 * second constructor less specific
	 */
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		
	}
	/**
	 * a fucnction that sets all the widget in the maze
	 */
	@Override
	void initWidgets() {
		shell.addListener(SWT.Close,new Listener(){

			@Override
			public void handleEvent(Event arg0) {
				closeCorrect();
				display.dispose();
				LastUserCommand= commands.get("exit");
				setChanged();
				notifyObservers();
				
			}
			
		});
		//sets the background image to white
		shell.setBackground(new Color(null,255,255,255));
		shell.setLayout(new GridLayout(2,false));
		shell.setText("Maze Generations"); //sets the text of window
		//creates a tool bar
		Menu menuBar = new Menu(shell, SWT.BAR);
		//creates a file category in toolbar
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        
 
        //creates a help category in toolbar
        MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeHelpMenu.setText("&Help");
        Menu HelpMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeHelpMenu.setMenu(HelpMenu);
        
      
        //add item to file menu open properties
		MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Open Properties");
			item.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN); //opens a dialog box in which we can select a xml file and load it
				fd.setText("open");
				fd.setFilterPath("C:\\");
				String[] filterExt = { "*.xml"};
				fd.setFilterExtensions(filterExt);
				String filename=fd.open(); //choose the file
				if(filename!=null){
					setProperties(filename);
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							switch(properties.getUi())
							{
								case CLI: //if the properties calls for CLI
									closeCorrect(); //dispose all data
									display.dispose(); //dispose display
									LastUserCommand= commands.get("exit");
									setChanged();
									notifyObservers();//exit correctly
									RunCLI demo=new RunCLI(); //call for a function that works with cli
									demo.startProgram(getProperties());
									break;
								case GUI:
									closeCorrect();// dispose all and close timer task
									display.dispose();
									LastUserCommand = commands.get("exit"); 
									setChanged();
									notifyObservers();//exit correctly
									RunGUI demoG = new RunGUI(); //calls for a function that recreates a gui window
									demoG.start(getProperties());
									break;
								default:
									return;	
							}
							
						}
					});
				}
			}
	    	
	    });
			//new item to file menu
			item = new MenuItem(fileMenu, SWT.PUSH);
			item.setText("Write Properties");
			item.addSelectionListener(new SelectionListener(){
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					
					
					
				}
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					display.asyncExec(new Runnable() {
						
						@Override
						public void run() {//this function works on the same basis as open Properties the only difference is the source of the Properties data here we recieve it directly from the user
							WritePropertiesGUI guiProp=new WritePropertiesGUI();
							if(guiProp.writeProperties(shell)!=-1)
							{
								properties=Run.readProperties();
								switch(properties.getUi())
								{
									case CLI:
										closeCorrect();
										display.dispose();
										LastUserCommand= commands.get("exit");
										setChanged();
										notifyObservers();
										RunCLI demo=new RunCLI();
										demo.startProgram(getProperties());
										break;
									case GUI:
										closeCorrect();
										display.dispose();
										LastUserCommand = commands.get("exit");
										setChanged();
										notifyObservers();
										RunGUI demoG = new RunGUI();
										demoG.start(getProperties());
										break;
									default:
										return;	
								}
							}
						}
					});
				
					
				}
				
			});
			//new item for file menu
			 item = new MenuItem(fileMenu, SWT.PUSH);
			    item.setText("Exit");
			    item.addSelectionListener(new SelectionListener(){

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						
						
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						closeCorrect();
						shell.dispose();
						LastUserCommand= commands.get("exit");
						setChanged();
						notifyObservers();
						
						
					}
			    	
			    });
			    //a new item in the help menu prints a msg box telling the user some details about us:)
			    item = new MenuItem(HelpMenu, SWT.PUSH);
			    item.setText("About");
			    item.addSelectionListener(new SelectionListener(){
					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
				        messageBox.setText("Information");
				        messageBox.setMessage("This entire software was created by Tomer Cabouly and Alon Orlovsky Enjoy It!");
						messageBox.open();
						
					}
			    	
			    });
			shell.setMenuBar(menuBar);
	    
	   //buttons for generate maze
		Button generateButton=new Button(shell,SWT.PUSH);
		generateButton.setText("Generate Maze");
		generateButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
			//button that solves the maze
		//creates an instance of boardWidget
		   boardWidget=new MazeDisplay(shell, SWT.NONE);// CommonBoard f(x)
		   boardWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		   Button clueButton=new Button(shell,SWT.PUSH);
		   clueButton.setText("Help me solve this!");
		   clueButton.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false,1,1));
		   Button solveMaze = new Button (shell ,SWT.PUSH);
		   solveMaze.setText("Solve the maze I give up");
		   solveMaze.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		   clueButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player(); //selected button music starts playing
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3"));
			    player.play();
				if(MazeWindow.this.mazeName!=null && !boardWidget.won){
				LastUserCommand= commands.get("calculate hint");
				setChanged(); //mvp solve maze
				notifyObservers(" "+MazeWindow.this.mazeName + " "+ boardWidget.character.currentCellX+","+ boardWidget.character.currentCellY);
				boardWidget.drawBoard(null);
				boardWidget.forceFocus();
				}
				else{ //if there is no maze to be solved error
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Maze Generations");
			        messageBox.setMessage("No hint to show");
					messageBox.open();
				}
				
			}
			   
		   });
		   generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MP3Player player = new MP3Player(); //play sound
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3"));
			    player.play();

			    dataRecieved=null;
			    ClassInputDialog dlg = new ClassInputDialog(shell,MazeProperties.class);
			    MazeProperties tempInput=(MazeProperties)dlg.open();
			    if(tempInput!=null)
			    {
			    	input=tempInput;
			    }
			    boardWidget.forceFocus();
			    if(input!=null && input.getColGoal()<input.getCols() && input.getRowGoal()<input.getRows()){
			    	MazeWindow.this.mazeName=input.getMazeName();
			    }
			    if(input!=null &&(input.getColGoal()>=input.getCols() || input.getRowGoal()>=input.getRows())){// || !(input.getColGoal()<input.getCols() && input.getRowGoal()<input.getRows())){ //if error has occureed 
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("An error has occureed");
					messageBox.open();
				}
			    else
			    {
				    LastUserCommand= commands.get("maze exists");
				    setChanged(); //check if maze already exists
					notifyObservers(" " +MazeWindow.this.mazeName); 
			    }
				
				
					
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		   //new button that solves the entire maze same concept as hint
		   
		   solveMaze.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MP3Player player = new MP3Player();
			    player.addToPlayList(new File(".\\resources\\sounds\\menuselect.mp3")); //play sound
			    player.play();
				if(MazeWindow.this.mazeName!=null && !boardWidget.won){
				LastUserCommand= commands.get("solve maze");
				setChanged(); //solve the maze
				notifyObservers(" "+MazeWindow.this.mazeName);
				}
				else{ //if there is no maze to solve
					MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
			        messageBox.setText("Information");
			        messageBox.setMessage("No maze to solve");
					messageBox.open();
				}
			}
			   
		   });
		   
			//boardWidget.setVisible(false);
		
		   
	}
	/**
	 * sets properties
	 * @param filename represents the file name of the properties file
	 */
	protected void setProperties(String filename) {//sets properties from a certain file
		
		FileInputStream in;
		try {
			XMLDecoder d;
			in = new FileInputStream(filename);
			d=new XMLDecoder(in);
			properties=(ClientProperties)d.readObject();
			d.close();
		} catch (FileNotFoundException e) {
				Display("Error Loading Properties");
		}
		
	}
	/**
	 * starts the MVP system
	 */
	@Override
	public void start() { 
		
		this.run();
		
	}
	/**
	 * 
	 * @param commands a hash map with all the commands
	 * initialize our hash map with the commands
	 */
	@Override
	public void setCommands(ConcurrentHashMap<String, Command> commands) {
		this.commands=commands;
		
	}
	/**
	 * 
	 * @return last user Command we used
	 */
	@Override
	public Command getUserCommand() {
		return this.LastUserCommand;
	}
	/**
	 * display string s
	 * @param s string
 	 */
	@Override
	public void Display(String s) {
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
        messageBox.setText("Information");
        messageBox.setMessage(s);
		messageBox.open();
		
	}
	/**
	 * display a maze in a gui way
	 * @param m
	 */
	@Override
	public void displayMaze(Maze m) {
		boardWidget.displayProblem(m);
	}
	/**
	 * disposes all data of the maze
	 */
	public void closeCorrect(){
		boardWidget.destructBoard();
		boardWidget.dispose();
	}
	/**
	 * display solution
	 * @param s is the solution
	 * we can either show a full solution or just a hint
	 */
	@Override
	public void displaySolution(Solution s) {
			boardWidget.displaySolution(s);		
		}
	
			
	
	/**
	 * 
	 * @param data checks if data exists in database true if not false if yes
	 */
	@Override
	public void receiveExistsMaze(Maze data) {
		if(input!=null && MazeWindow.this.mazeName!=null &&  data==null ){ //if maze doesnt exist create a new one
			MazeWindow.this.boardWidget.won=false;
			boardWidget.setVisible(true); //makes sure the boardWidget is visible
			LastUserCommand= commands.get("generate maze");
			setChanged();
			String board= "" + MazeWindow.this.mazeName + " "+(Integer)input.getRows() + ","+ (Integer)input.getCols()+ ","+input.getRowSource()+","+input.getColSource()+","+(input.getRowGoal())+","+(input.getColGoal());
			notifyObservers(" "+ board); //passses data to generate maze in MVP System
		}
		else if(data!=null)
		{
			MessageBox messageBox = new MessageBox(shell,SWT.ICON_INFORMATION|SWT.OK);
	        messageBox.setText("Information");
	        messageBox.setMessage("Maze already exists. Loading it!");
			messageBox.open();
			displayMaze(data);
		}
	}
	/**
	 * getter
	 * @return properties
	 */
	@Override
	public ClientProperties getProperties() {
		return properties;
	}
	/**
	 * Displays the hint on the maze
	 */
	@Override
	public void displayHint(State h) {
		boardWidget.displayHint(h);	
	}
}
	
	


