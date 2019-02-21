//------------------------------------------------------------------------------------- //
//																						//
//	Notes:  The Board class creates the logical board which tracks the movement of		//
//			all chess pieces on the chess board.  The GUI class simply reads the 	 	//
//			current state of the logical board in order to display its graphical 		//
//			representation to the user.  The Board class calls upon the Piece class		//
//			to create an array of chess pieces that can move within the array in 		//
//			accordance with gameplay. - dev-ay											//
//																						//
//------------------------------------------------------------------------------------- //

public class Board{

	public Piece cells[][] = new Piece[8][8]; //Array of chess pieces
	private boolean isBlack = true;

	//Initialize chess pieces on the logical board
	public void initialize() {
		//Set Pawns
		for(int j=0;j<8;j++){
			cells[1][j] = new Piece('p', isBlack);
			cells[6][j] = new Piece('p', !isBlack);
		}	
		//Set Rooks
		for(int j=0;j<8;j=j+7){
			cells[0][j] = new Piece('r', isBlack);
			cells[7][j] = new Piece('r', !isBlack);
		}
		//Set Knights
		for(int j=1;j<7;j=j+5){
			cells[0][j] = new Piece('n', isBlack);
			cells[7][j] = new Piece('n', !isBlack);
		}			
		//Set Bishops
		for(int j=2;j<6;j=j+3){
			cells[0][j] = new Piece('b', isBlack);
			cells[7][j] = new Piece('b', !isBlack);
		}			
		//Set Queens
		cells[0][3] = new Piece('q', isBlack);
		cells[7][3] = new Piece('q', !isBlack);
		//Set Kings
		cells[0][4] = new Piece('k', isBlack);
		cells[7][4] = new Piece('k', !isBlack);
		//Set empty spaces
		for(int j=2;j<6;j++){	
			for(int i=0;i<8;i++){
				cells[j][i] = new Piece('e');
			}			
		}
	}
	
	//Method for moving a chess piece to a new location
	public void move(int a, int b, int c, int d){
		cells[c][d] = cells[a][b];
		cells[a][b] = new Piece('e');	
	}
	
}