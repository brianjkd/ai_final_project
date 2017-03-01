package demo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TicTacToeTests {

	@Test
	public void allTrainingBoardsAreXsTurn() {
		for (int i = 0; i <= 14; i ++){
			Square [][] board = TrainingBoards.getTrainingBoard(i);
			Square aspect = TicTacToe.whoIsTurn(board);
			// System.out.println(i + " " + aspect);
			assertEquals(Square.X, aspect);		
		}
	}
	
	
	
	@Test
	public void allRandomTrainingBoardsAreXsTurn() {
		int n = 10;
		ArrayList<Square [][]> boards = TicTacToe.makeNRandomTrainingBoards(n);
		
		for (Square [][] board : boards){
			Square aspect = TicTacToe.whoIsTurn(board);
			assertEquals(Square.X, aspect);		
		}
	}

}
