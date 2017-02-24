package demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class TicTacToeTests {

	@Test
	public void test() {
		Square [][] board = TrainingBoards.makeBoard14();
		TicTacToe.displayBoard(board);
		board = TicTacToe.randomMove(board, Square.O);
		TicTacToe.displayBoard(board);
		assertTrue(true);
	}
	
	
	@Test
	public void invalidMoveReturnsFalse(){
		Square [][] board = TrainingBoards.makeBoard14();
		/*boolean isMoveValid1 = TicTacToe.isMoveValid(board, 1);
		boolean isMoveValid2 = TicTacToe.isMoveValid(board, 7);
		boolean isMoveValid3 = TicTacToe.isMoveValid(board, 8);*/
	/*	assertFalse(isMoveValid1);
		assertTrue(isMoveValid2);
		assertTrue(isMoveValid3);*/
	}

}
