//------------------------------------------------------------------------------------- //
//																						//
//	Notes:  The ChessGame class is the main java file from which to run the chess		//
//			game.  It calls the GUI class to generate the graphical display, which in 	//
//			turn generates the logical game board upon which the GUI is based.			//
//			- dev-ay																	//
//																						//
//------------------------------------------------------------------------------------- //

public class ChessGame {

	public static void main(String args[]) {
		GUI game = new GUI();
		game.generate();

	}
}
