//------------------------------------------------------------------------------------- //
//																						//
//	Notes:  The GUI class manages the graphical display of the chess game, as well as	//
//			in-game notifications.  This class tracks mouse actions by the user, and	//
//			in essence becomes the command center for the game, centered around drag-	//
//			and-drop actions.  It calls upon the Board class to generate the logical	//
//			game board that serves as the basis for the GUI display.  It also calls		//
//			upon the Rules class in order to verify the validity of every attempted		//
//			move by the user. GUI functionality relies upon javax.swing. - dev-ay		//
//																						//
//------------------------------------------------------------------------------------- //

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;


public class GUI implements MouseListener{
	private boolean outOfBoard = false; //Indicates whether cursor is inside the chess board
	private JPanel mainPanel; //Main panel that holds all cells of the GUI chess board
	private JPanel [][] subPanels = new JPanel[8][8]; //Create an array of 8x8 cells
	private int origin, destination; //Indicates origin and destination for an attempted move
	private int x0, y0, x, y; //Origin and destination coordinates in an 8x8 array
	private JFrame frame = new JFrame("CHESS GAME - dev-ay"); //Initialize GUI window
	public Board chess = new Board(); //Initialize logical chess board
	private Rules referee = new Rules(); //Referee that checks for the legality of moves
	private boolean gameOver = false; //Indicates whether the game is over
	private Toolkit tk = Toolkit.getDefaultToolkit(); //Toolkit object for playing system beeps
	
	//Initialize graphical game board
	public void generate(){
		chess.initialize(); //Create and initialize logical game board
		System.out.println("***************************************************");
		System.out.println("************ WELCOME TO THE CHESS GAME ************");
		System.out.println("***************************************************");
		System.out.println("*                                                 *");
		System.out.println("* GAME RULES:                                     *");
		System.out.println("*  - This is a two player game                    *");
		System.out.println("*  - Move chess pieces by dragging and dropping   *");
		System.out.println("*  - Either team/side may start the game          *");
		System.out.println("*  - Players must alternate making moves          *");
		System.out.println("*  - Only legal moves are permitted               *");
		System.out.println("*  - Game is over when a King is captured         *");
		System.out.println("*                                                 *");
		System.out.println("***************************************************");
		System.out.println();
		System.out.println();
		System.out.println("Please choose a team to make the first move...");
		System.out.println();
		
		mainPanel = new JPanel(new GridLayout(8,8));
		mainPanel.setBackground(Color.white);
		
		//Initialize each cell of chess board
		for(int j=0;j<8;j++){
			for(int i=0;i<8;i++){
				subPanels[j][i] = new JPanel();
			}
		} 
		
		//Add chess piece images to each applicable cell
		for(int j=0;j<8;j++){
			for(int i=0;i<8;i++){
				subPanels[j][i].add(chess.cells[j][i].image);
			}
		} 
		
		//Add all cells to main GUI board 
		for(int j=0;j<8;j++){
			for(int i=0;i<8;i++){
				mainPanel.add(subPanels[j][i]);
			}
		}
		
		//Set board color
		for(int j=0;j<8;j++){
			for(int i=0;i<8;i=i+2){
			(subPanels[j][(i+j)%8]).setBackground(new Color(0,0,0,0));
			}
		}
		
		mainPanel.addMouseListener(this);	
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
		frame.setResizable(false);
		
	}	
	

	//Acquire beginning location of dragging and dropping
	public void mousePressed(MouseEvent e){
		x0 = e.getX()/69;
		y0 = e.getY()/69; 	
		origin = y0*8+x0;
	}
	
	//Acquire destination location of dragging and dropping, and redraw cells if move is valid
	public void mouseReleased(MouseEvent e){
		
		//Only process a mouse release event if 
		//  - Cursor is inside the chess board
		//  - Origin cell contains a chess piece
		if(!outOfBoard && chess.cells[y0][x0].type != 'e') {
			
			//Acquire final location of dragging and dropping
			x = e.getX()/69;
			y = e.getY()/69;
			destination = y*8+x;
			
			//Process an attempted move if 
			//	- Game is not over
			//  - Origin and destination are different locations
			if(!gameOver && origin != destination){
					
				//Process the attempted move if it is legal and valid
				if(referee.checkMove(y0,x0,y,x,chess)){ 
					
					//Check if a King has been captured by the move
					if(chess.cells[y][x].type == 'k'){ 
						System.out.println();
						System.out.println();
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println("!!!!!!!!!!!!!!!! CONGRATULATIONS !!!!!!!!!!!!!!!!!!");
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println("!!!!!!!!!!!! " + (referee.currentlyBlack ? "BLACK" : "WHITE") 
								+ " TEAM WINS THE GAME !!!!!!!!!!!!!");
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						System.out.println();
						System.out.println("***************************************************");
						System.out.println("******************** GAME OVER ********************");
						System.out.println("***************************************************");
						gameOver = true;
					}
					
					//Perform the move in the logical board
					chess.move(y0,x0,y,x); 
					
					//Update the cells of the GUI board and redraw the board
					subPanels[y0][x0].removeAll();	
					subPanels[y0][x0].add(chess.cells[y0][x0].image);
					subPanels[y][x].removeAll();	
					subPanels[y][x].add(chess.cells[y][x].image);
					frame.repaint();
					
					//Indicate which team makes the first move
					if(referee.moves == 1) { 
						System.out.println( (referee.currentlyBlack ? "BLACK" : "WHITE") + " TEAM starts the game!");
						System.out.println();
					}
				}
				//Provide error notification if the attempted move is illegal or invalid
				else {
					
					//Play system beep to indicate error
				    tk.beep();
					
					//Notify if it is not the current player's turn
					if (referee.yourTurn == false) { 
						System.out.println("Please wait, it is the " + (referee.turnBlack ? "BLACK" : "WHITE") 
								+ " TEAM's turn...");
						System.out.println();
					}
					//Notify that the attempted move is illegal
					else { 
						System.out.println("That is an invalid move, please try again...");
						System.out.println();
					}
				}
				
				
			}
		}
	}
	
	//Keep track of whether the mouse is inside or outside the chess board
	public void mouseEntered(MouseEvent e){ outOfBoard = false; }
	public void mouseExited(MouseEvent e){ outOfBoard = true; }
	public void mouseClicked(MouseEvent e){  }
}

















