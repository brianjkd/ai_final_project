package demo;

public class TicTacToe {
	
	static void displayBoard(Square[][] board){
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board[0].length; j++){
				if (board[i][j] == Square.EMPTY)
					System.out.print("  ");
				else 
					System.out.print(board[i][j] + " ");
			}
			System.out.println();	
		}
	}
	
	static Square [][] createBoard(){
		Square [][] board = new Square [3][3];
		for(int i = 0; i < board[0].length; i++){
			for(int j = 0; j < board[0].length; j++){
				board[i][j] = Square.EMPTY;
				}	
			}
		return board;
	}
	
	
	static void doValidMove(Square [][] board, Square type, int r, int c){
		board[r][c] = type;
	}
	
	static boolean hasWon(Square [][] board, Square type){
		// check vertical
		if (board[0][0].get() + board[1][0].get() + board[2][0].get() == 3 * type.get()) return true;
		if (board[0][1].get() + board[1][1].get() + board[2][1].get() == 3 * type.get()) return true;
		if (board[0][2].get() + board[1][2].get() + board[2][2].get() == 3 * type.get()) return true;
		
		// check horizontal
		if (board[0][0].get() + board[0][1].get() + board[0][2].get() == 3 * type.get()) return true;
		if (board[1][0].get() + board[1][1].get() + board[1][2].get() == 3 * type.get()) return true;
		if (board[2][0].get() + board[2][1].get() + board[2][2].get() == 3 * type.get()) return true;
		
		//check diagonal
		if(board[0][0].get() + board[1][1].get() + board[2][2].get() == 3 * type.get()) return true;
		if (board[0][2].get() + board[1][1].get() + board[0][0].get() == 3 * type.get()) return true;
		return false;
	}
	

	public static void main(String[] args){
		Square [][] board = createBoard();
		doValidMove(board, Square.X, 1,2);
		doValidMove(board, Square.O, 0,0);
		doValidMove(board, Square.X, 2,2);
		displayBoard(board);
		
		NeuralNetwork n = new NeuralNetwork(board);
	}
		
}
	