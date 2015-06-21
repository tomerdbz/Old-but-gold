package view.gui;

import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;

import algorithms.search.Solution;
import algorithms.search.State;

public abstract class CommonBoard extends Composite implements Board {
	
	Timer timer;
	/**
	 * Gives true if we beat the maze
	 */
	boolean won=false;
	/**
	 * a 2 dimension array which represents the tiles in the board
	 */
	CommonTile[][] board;
	/**
	 * number of rows in the board
	 */
	int boardRows; 
	/**
	 * number of cols in the board
	 */
	int boardCols;
	/**
	 * representation of the character in the board
	 */
	CommonCharacter character=null;
	/**
	 * allows us to check what kind of a drag has occurred if there was any
	 */
	boolean checkDragged=false; 
	
	/**
	 * Constructor
	 */
	public CommonBoard(Composite parent, int style) {
		super(parent, style);
			addPaintListener(new PaintListener() { 
			
			@Override
			public void paintControl(PaintEvent arg0) {
				drawBoard(arg0);
			}
		});
			this.addKeyListener(new KeyListener(){	
				@Override
				public void keyPressed(KeyEvent e) { //each of those codes represents a key on the keyboard in our case up down right left arrows
					if (e.keyCode == 16777217 && hasPathUP(character.currentCellX,character.currentCellY)){
						applyInputDirection((Direction.UP));
						 //up
						 	
				    } 
					 else 
						 if (e.keyCode == 16777220 && hasPathRIGHT(character.currentCellX,character.currentCellY)) {
							 applyInputDirection((Direction.RIGHT));
				    	//right
							 
				    } 
						else 
						if (e.keyCode == 16777219 &&  hasPathLEFT(character.currentCellX,character.currentCellY)) {
							applyInputDirection(Direction.LEFT);
				    	//left
						
				    } 
						else
						if (e.keyCode == 16777218  && hasPathDOWN(character.currentCellX,character.currentCellY)) {
							applyInputDirection(Direction.DOWN);
				    	//down
							 
				    } 
					
					
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					
					
				}
			});
			//allows us to set the size of window by pressing ctrl + scrolling
			this.addMouseWheelListener(new MouseWheelListener(){

				@Override
				public void mouseScrolled(MouseEvent arg0) {
					if((arg0.stateMask& SWT.CTRL)!=0){ //if control is pressed
						if(arg0.count>0){ //and we scroll up
							//up zoom in										//Bonus!!
							setSize(getSize().x+30, getSize().y+30);
						}
						if(arg0.count<0){ //and we scroll down
							setSize(getSize().x-30, getSize().y-30);
							//down zoom out
							
						}
					}
					
					
				}
		});
			
	}

	
	

	/**
	 * apply direction on board
	 */
	@Override
	public abstract void  applyInputDirection(Direction direction);
	
	public void addMouseListenerToComposite() {
		MouseListener ma=new MouseListener(){

			int[] before=new int[2]; //place of cursor before click
			int[] after=new int[2]; // place of cursor after releasing click
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {	}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				checkDragged=false; 
				if(board!=null){
				int sizeOftileX = CommonBoard.this.getSize().x/(board[0].length); //get the size of each tile x,y
				int sizeOftileY = CommonBoard.this.getSize().y/(board.length);
					String str =CommonBoard.this.toControl(getDisplay().getCursorLocation()).toString(); //calculates mouse location by pixels
					String []loc = str.substring(7).split(",");
					before[0]=Integer.parseInt(loc[0].substring(0)); //taking cording
					before[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1));
					int Ycell=((before[0]/sizeOftileX)); //which tile we are on
					int xcell=((before[1]/sizeOftileY));
					System.out.println(before[0] + " " +before[1]);
					if((character.currentCellX==xcell)&&character.currentCellY==Ycell)//check if tile that we are on contains character
						checkDragged=true; //if character is conatined
				}
			}

			@Override
			public void mouseUp(MouseEvent arg0) {
					if(checkDragged){
					String str =CommonBoard.this.toControl(getDisplay().getCursorLocation()).toString();
					String []loc = str.substring(7).split(",");
					after[0]=Integer.parseInt(loc[0].substring(0));
					after[1]=Integer.parseInt(loc[1].substring(1, loc[1].length()-1)); //calculates mouse location by pixels
					double Shipua = ((double)((double)after[1]-before[1])/((double)after[0]-before[0]));
					if(after[0]> before[0] && after[1]>before[1]){
						if(Shipua<1.5 && Shipua>  0.5 )
						{
							if(character.currentCellX+1 <= board.length-1 && character.currentCellY+1<= board[0].length-1)
								if(( hasPathRIGHT(character.currentCellX, character.currentCellY)&&  hasPathDOWN(character.currentCellX, character.currentCellY+1))||( hasPathDOWN(character.currentCellX,character.currentCellY)&& hasPathRIGHT(character.currentCellX+1, character.currentCellY)))
									applyInputDirection(Direction.DOWNRIGHT);
						}
						else
							if(Shipua>1.5 && character.currentCellX+1<=board.length-1)
							{
								if(hasPathDOWN(character.currentCellX,character.currentCellY))
									applyInputDirection(Direction.DOWN);
							}
							else
								if( character.currentCellY+1<= board[0].length-1){
									if(hasPathRIGHT(character.currentCellX,character.currentCellY))
										applyInputDirection(Direction.RIGHT);
								}
					}
					if(after[0]> before[0] && after[1]<before[1]){
						if(Shipua>-1.5 && Shipua<-0.5)
						{
							if(character.currentCellX-1 >=0 && character.currentCellY+1<=  board[0].length-1) //not out of bounds
								if((hasPathRIGHT(character.currentCellX, character.currentCellY)&&  hasPathUP(character.currentCellX, character.currentCellY+1))||( hasPathUP(character.currentCellX,character.currentCellY)&& hasPathRIGHT(character.currentCellX-1, character.currentCellY)))
										applyInputDirection(Direction.UPRIGHT);
						}
						else
							if(Shipua<-0.5 && character.currentCellX-1>=0)
							{
								if(hasPathUP(character.currentCellX,character.currentCellY))
									applyInputDirection(Direction.UP);
							}
							else
								if( character.currentCellY+1<= board[0].length-1)
								{
									if(hasPathRIGHT(character.currentCellX,character.currentCellY))
										applyInputDirection(Direction.RIGHT);
								}
						
					}
					if(after[0]< before[0] && after[1]>before[1]){
						if(Shipua>-1.5 && Shipua<-0.5)
							{
							if(character.currentCellX+1 <= board.length-1 && character.currentCellY-1>=0)
								if(( hasPathLEFT(character.currentCellX, character.currentCellY)&&  hasPathDOWN(character.currentCellX, character.currentCellY-1))||( hasPathDOWN(character.currentCellX,character.currentCellY)&& hasPathLEFT(character.currentCellX+1, character.currentCellY)))
									applyInputDirection(Direction.DOWNLEFT);
							}
							else
								if(Shipua<-1.5  && character.currentCellX+1<=board.length-1)
								{
									if(hasPathDOWN(character.currentCellX,character.currentCellY))
										applyInputDirection(Direction.DOWN);
								}
								else
									if(character.currentCellY-1>=0)
									{
										if(hasPathLEFT(character.currentCellX,character.currentCellY))
											applyInputDirection(Direction.LEFT);
									}
						
					}
					
					if(after[0]< before[0] && after[1]<before[1]){
						if(Shipua<1.5 && Shipua> 0.5)
						{
							if(character.currentCellX-1 >=0 && character.currentCellY-1>=0)
								if( hasPathLEFT(character.currentCellX, character.currentCellY)&&  hasPathUP(character.currentCellX, character.currentCellY-1)||( hasPathUP(character.currentCellX,character.currentCellY)&& hasPathLEFT(character.currentCellX-1, character.currentCellY)))
									applyInputDirection(Direction.UPLEFT);
						}
						else
							if(Shipua<0.5 && character.currentCellY-1>=0)
							{
								if(hasPathLEFT(character.currentCellX, character.currentCellY))
									applyInputDirection(Direction.LEFT);
							}
							else
								if(character.currentCellX-1>=0)
								{
									if(hasPathUP(character.currentCellX, character.currentCellY))
										applyInputDirection(Direction.UP);
								}
						
					}
					}
					
					
				
						
			}
		};
		
	    addMouseListener(ma);
	    if(board!=null)
	    for (int i=0;i<board.length;i++)
	     	for(int j=0;j<board[0].length;j++)
	      		board[i][j].addMouseListener(ma);
	}
	
	@Override
	public abstract boolean hasPathUP(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathRIGHT(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathDOWN(int characterRow,int characterCol);
	@Override
	public abstract boolean hasPathLEFT(int characterRow,int characterCol);
	@Override
	public abstract void displayProblem(Object o);
	@Override
	public abstract void displaySolution(Solution s);
	@Override
	public abstract void displayHint(State h);

	/**
	 * disposes of board cells
	 */
	public abstract void destructBoard();
	


}
