//------------------------------------------------------------------------------------- //
//																						//
//	Notes:  The Rules class adjudicates the validity of all attempted moves by a user.	//
//			It tracks which team has the current turn, whether there are chess pieces	//
//			on the board that blocks an intended move, and verifies legal moves based 	//
//			on the type of chess piece being played.  - dev-ay							//
//																						//
//------------------------------------------------------------------------------------- //

public class Rules {

	private int x0, y0, x, y; //Coordinates for the starting (x0,y0) and ending (x,y) positions
	private int dx, dy, adx, ady; //Relative deltas (dx,dy) and their absolute values (adx,ady)
	private boolean isLegal; //Indicates if the attempted move is legal
	private char type; //Indicates the types of chess piece
	public boolean currentlyBlack; //Indicates whether the piece being played currently is black
	public int moves = 0; //Counter for how many moves have been made
	public boolean turnBlack; //Tracks whether it is the black team's turn or not
	public boolean yourTurn = true; //Indicates if it is the current player's turn

	//Returns true if attempted move is legal
	public boolean checkMove(int y1, int x1, int y2, int x2, Board c){
		dx = x2-x1;
		dy = y2-y1;
		adx = Math.abs(dx);
		ady = Math.abs(dy);
		x0 = x1; 
		y0 = y1;
	    x = x2; 
	    y = y2;
	    type = c.cells[y1][x1].type;
	    currentlyBlack = c.cells[y1][x1].isBlack;

	    //If it is not the current player's turn, set yourTurn to false and return
	    if(moves>0 && turnBlack != currentlyBlack) {
	    	yourTurn = false;
	    	return false;
	    }
		//If it is the current player's turn, then set yourTurn to true
	    else {
	    	yourTurn = true;
	    }
	    
	    //If attempted move is not blocked by other pieces, then check for legality based on type
	    if(notBlocked(c)){	    
	    	switch(type){
	    		case('k'): isLegal = King(c); break;
	    		case('q'): isLegal = Queen(c); break;
	    		case('b'): isLegal = Bishop(c); break;
	    		case('n'): isLegal = Knight(c); break;
	    		case('r'): isLegal = Rook(c); break;
	    		case('p'): isLegal = Pawn(c); break;
	    		default: isLegal = false; break;
	    	}
		
	    }
	    else {
	    	return false;
	    }
	    
	    //If the attempted move is legal, 
	    //  then indicate that it is now the other team's turn by updating turnBlack,
		//  and also update the moves counter
	    if (isLegal) {
	    	turnBlack = !currentlyBlack; 
	    	moves++;	
	    }
	
		return yourTurn && isLegal;
	
	}

	//Check if chess pieces from the same team is blocking the attempted move
	private boolean notBlocked(Board c){
		//Check if a piece from the same team is in the destination cell
		if(c.cells[y][x].type != 'e')
			if(c.cells[y][x].isBlack == currentlyBlack)
				return false;			
		//Check for blocking of vertical traveling
		if(adx==0){
			for(int j=y0+ady/dy ; j!=y ; j=ady/dy+j){
				if(c.cells[j][x0].type != 'e')
					return false;
			}
		}
		//Check for blocking of horizontal traveling
		if(ady==0){
			for(int j=x0+adx/dx ; j!=x ; j=adx/dx+j)
				if(c.cells[y0][j].type != 'e')
					return false;
		}
		//Check for blocking of diagonal traveling
		if(adx==ady){
			int temp = x0+adx/dx;
			for(int j=y0+ady/dy ; j!=y ; j=ady/dy+j){
				if(c.cells[j][temp].type != 'e')
					return false;
				temp = temp + adx/dx;
			}
		}		

		return true;
	}
	
	
	//Check for legal moves for each type of chess piece:
	
	//Verify valid moves for bishops 
	private boolean Bishop(Board c){
		if(adx==ady)
			return true;
		else
			return false;
		
	}
	
	//Verify valid moves for rooks
	private boolean Rook(Board c){
		if(dx==0||dy==0)
			return true;
		else
			return false;
		
	}	
	
	//Verify valid moves for knights
	private boolean Knight(Board c){
		if( (adx==2 && ady==1) || (adx==1 && ady==2) )
			return true;
		else
			return false;
	}	
	
	//Verify valid moves for kings
	private boolean King(Board c){
		if(adx<=1 && ady<=1)
			return true;
		else
			return false;
	}		

	//Verify valid moves for queens
	private boolean Queen(Board c){
		if(Bishop(c) || Rook(c))
			return true;
		else
			return false;
	}
		
	//Verify valid moves for pawns	
	private boolean Pawn(Board c){
		if(currentlyBlack){
			if( ( (adx==0)&&(dy==1) && c.cells[y][x].type == 'e') 
				|| (adx==0 && y==3 && y0==1 && c.cells[y][x].type == 'e')	
				|| (adx==1 && dy==1 && c.cells[y][x].type != 'e')   ) 
				return true;
			else
				return false;
		}
		else {
			if((adx==0)&&(dy==-1) && c.cells[y][x].type == 'e' 
				|| (adx==0 && y==4 && y0==6 && c.cells[y][x].type == 'e')	
				|| (adx==1 && dy==-1 && c.cells[y][x].type != 'e') )
				return true;
			else
				return false;

		}
	}
}
