//------------------------------------------------------------------------------------- //
//																						//
//	Notes:  The Piece class represents chess pieces (e.g. King, Queen, Knight, etc.).	//
//			It holds the attributes for whether a piece is black or white, what type 	//
//			of piece it is, and it also holds the image file for its own graphical		//
//			representation.  A Piece can also represent empty space.  Graphics hand-	//
//			ling relies upon javax.swing. - dev-ay										//
//																						//
//------------------------------------------------------------------------------------- //

import javax.swing.*;

public class Piece {

	public boolean isBlack; //Indicates whether the instantiated chess piece is black or not
	public char type; //Indicates the type of chess piece instantiated (e.g. King, Queen, Knight, etc.)
	JLabel image = new JLabel(); //Stores the image file for the instantiated chess piece

	//Creates a chess piece
	public Piece(char c, boolean b){
		type = c;
		isBlack = b;	
		switch(c){
			case'k':
				if(b){
					image = new JLabel(new ImageIcon("img/kb.gif"));
				}
				else{
					image = new JLabel(new ImageIcon("img/kw.gif"));
				}
				break;
			case 'q':
				if(b){
					image = new JLabel(new ImageIcon("img/qb.gif"));			
				}
				else{
					image = new JLabel(new ImageIcon("img/qw.gif"));
				}
				break;
			case 'n':
				if(b){
					image = new JLabel(new ImageIcon("img/nb.gif"));
				}
				else{
					image = new JLabel(new ImageIcon("img/nw.gif"));
				}
				break;
			case 'b':
				if(b){
					image = new JLabel(new ImageIcon("img/bb.gif"));			
				}
				else{
					image = new JLabel(new ImageIcon("img/bw.gif"));
				}
				break;
			case 'p':
				if(b){
					image = new JLabel(new ImageIcon("img/pb.gif"));			
				}
				else{
					image = new JLabel(new ImageIcon("img/pw.gif"));
				}
				break;
			case 'r':
				if(b){
					image = new JLabel(new ImageIcon("img/rb.gif"));			
				}
				else{
					image = new JLabel(new ImageIcon("img/rw.gif"));
				}
				break;
		}			
	}

	//Creates an empty space
	public Piece(char c){
		type = 'e';
	}

}
