package tictactoe;

import java.text.DecimalFormat;
import java.util.Scanner;

public class MainProgram {
	
	public static void main(String[] args){
		 promptUserForInput();
	}
	
	public static void promptUserForInput() {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to NEAT for Tic-Tac_Toe!");
		int choice1 = -1;
		do {
			System.out.println("\nWhat would you like to do?");
			System.out.println("1.Train a Neural Network"
					+ "\n2.Test a Previously Trained Neural Network"
					+ "\n3.Exit");
			choice1 =  in.nextInt();
		} while (choice1 != 1 && choice1 != 2 && choice1 != 3);
		
		if (choice1 == 1){
			promptTrainNeuralNetwork();
		}
		else if (choice1 == 2){
			promptTestNeuralNetwork();
		}
		else if (choice1 == 3){
			System.out.print("Goodbye!");
		}
	}
	
	
	public static void promptTrainNeuralNetwork() {	
		Scanner in = new Scanner(System.in);
		FitnessFunctionType fitnessType = FitnessFunctionType.ValidMoves;
		int populationSize = 20; // number of neural networks
		int numOfGenerations = 1000; // number of iterations or generations
		int trainingSize = 1000; // the number of boards
		int hiddenLayerNeuronSize = 0; // can be 0 meaning no hidden layer, or any positive integer

		int choice = -1;
		do {
			System.out.println("\nWhat would you like to use as the Fitness Function?");
			System.out.println("1.Valid Moves"
					+ "\n2.Valid and Winning Moves"
					+ "\n3.Optimal Moves using Minimax aglorithm (VERY SLOW)");
			choice =  in.nextInt();
		} while (choice != 1 && choice != 2 && choice != 3);
		
		if (choice == 1){
			fitnessType = FitnessFunctionType.ValidMoves;
		}
		else if (choice == 2){
			fitnessType = FitnessFunctionType.ValidAndWinningMoves;

		}
		else if (choice == 3){
			fitnessType = FitnessFunctionType.OptimalMoves;
		}
		
		
		do {
			System.out.println("\nEnter a postive population size of neural networks for the genetic algorithm:");
			choice =  in.nextInt();
			populationSize = choice;
		} while (choice < 4);
	
		
		do {
			System.out.println("\nEnter the number of iterations / generations the genetic algorithm should perform:");
			choice =  in.nextInt();
			numOfGenerations = choice;
		} while (choice < 1);
		
		do {
			System.out.println("\nEnter the number of Tic-Tac-Toe boards to train with:");
			choice =  in.nextInt();
			trainingSize = choice;
		} while (choice < 2);
		
		do {
			System.out.println("\nEnter the number of neurons in the hidden layer (Recommended 0, which means no hidden layer):");
			choice =  in.nextInt();
			hiddenLayerNeuronSize = choice;
		} while (choice < 0);
		
		System.out.println("Beginning Training...");
		Training.trainWithGeneticAlgorithm(populationSize, numOfGenerations, trainingSize,
				hiddenLayerNeuronSize, fitnessType);
		
		promptUserForInput();
	}
	
	public static void promptTestNeuralNetwork() {
		
		Scanner in = new Scanner(System.in);
		int choice = -1;
		do {
			System.out.println("\nWhat would you like to do?");
			System.out.println("1.Play Neural Network Against Random Player n Times"
					+ "\n2.Play Saved Neural Network Against Random Player"
					+ "\n3.Play Saved Neural Network Against Self");
			choice =  in.nextInt();
		} while (choice != 1 && choice != 2 && choice != 3);
		
		if (choice == 1){
			do {
				System.out.println("\nHow many times would you like Neural Network to play agains Random Player?");
				choice =  in.nextInt();
			} while (choice < 0);
			int n = choice;
			playAgainstRandomPlayerNTimes(n);
		}
		else if (choice == 2){
			playAgainstRandomPlayer();
		}
		else if (choice == 3){
			playAgainstSelf();
		}
		promptUserForInput();
	}
	
	public static void trainWithSettings(){
		int populationSize = 20; // number of neural networks
		int numOfGenerations = 1000; // number of iterations or generations
		int trainingSize = 1000; // the number of boards
		int hiddenLayerNeuronSize = 0; // can be 0 meaning no hidden layer, or any positive integer
		FitnessFunctionType fitnessType = FitnessFunctionType.ValidAndWinningMoves;
		Training.trainWithGeneticAlgorithm(populationSize, numOfGenerations, trainingSize,
				hiddenLayerNeuronSize, fitnessType);
	}
	
	
	
	public static void playAgainstRandomPlayerNTimes(int n){
		int completedGames = 0;
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		for (int i = 0 ; i < n; i++){
			Square [][] board = TicTacToe.createBoard();
			boolean success = true;
			while(!TicTacToe.isGameOver(board)){ // while game is going on		
				Square aspect = TicTacToe.whoIsTurn(board);
				if (aspect == Square.X){
					int destination = a.evaluateOutput(board, Training.INPUTSIZE);
					Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
					if (TicTacToe.isMoveValid(board, destinationCoordinate)){
						board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
					}
					else {
						// System.out.print("Error. NN could not make a valid move.");
						success = false;
						break;
					}
				}
				else { // it's O's turn and we are not training for O
					// other player does random move
					board = TicTacToe.randomMove(board, aspect);
				}
			}
			if (success) completedGames ++;
		}
			DecimalFormat df = new DecimalFormat("#.00"); 
			double percentage= completedGames / (double) n * 100d;
			double estimated = Math.pow(a.getFitness() / 100d, 5d) * 100d;
			System.out.println("Neural Network playing against a random player.");
			System.out.println("Saved Neural Network has a fitness of " + df.format(a.getFitness()) + "%.");
			System.out.println("This means " + df.format(a.getFitness()) + "% of the time a valid move will be executed.");
			System.out.println("Neural Network was able to complete " + percentage + " % of the " + n + " games.");
			System.out.println("The estimated percentage of games the Neural Network should be able complete\nis " + df.format(estimated) + "% of the games.");
			System.out.println("This was obtained by raising (" + a.getFitness() + " / 100.0) to the 5th power.");
	}
	
	
	// Not useful! A neural network is deterministic, so each game playing against itself will result in the same outcome
	/*public static void playAgainstSelfNTimes(int n){
		int completedGames = 0;
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		for (int i = 0 ; i < n; i++){
			Square [][] board = TicTacToe.createBoard();
			boolean success = true;
			while(!TicTacToe.isGameOver(board)){ // while game is going on		
				Square aspect = TicTacToe.whoIsTurn(board);
				if (aspect == Square.X){
					int destination = a.evaluateOutput(board, Training.INPUTSIZE);
					Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
					if (TicTacToe.isMoveValid(board, destinationCoordinate)){
						board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
					}
					else {
						success = false;
						break;
					}
				}
				else {
					// it's O's turn and we are not training for O
					TicTacToe.invertBoard(board);
					int destination = a.evaluateOutput(board, Training.INPUTSIZE);
					Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
					if (TicTacToe.isMoveValid(board, destinationCoordinate)){
						board = TicTacToe.invertBoard(board);
						board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
					}
					else {
						success = false;
						break;
					}
				}
			}
			if (success) completedGames ++;
		}
			DecimalFormat df = new DecimalFormat("#.00"); 
			double percentage= completedGames / (double) n * 100d;
			double estimated = Math.pow(a.getFitness() / 100d, 9d) * 100d;
			System.out.println("Neural Network playing against itself with inverted input results.");
			System.out.println("Saved Neural Network has a fitness of " + df.format(a.getFitness()) + "%.");
			System.out.println("This means " + df.format(a.getFitness()) + "% of the time a valid move will be executed.");
			System.out.println("Neural Network was able to complete " + percentage + " % of the " + n + " games.");
			System.out.println("The estimated percentage of games the Neural Network should be able complete\nis " + df.format(estimated) + "% of the games.");
			System.out.println("This was obtained by raising (" + a.getFitness() + " / 100.0) to the 9th power.");
	}
	*/
	
	
	
	public static void playAgainstRandomPlayer(){
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		Square [][] board = TicTacToe.createBoard();
		
		int turn = 1;
		while(!TicTacToe.isGameOver(board)){ // while game is going on		
			Square aspect = TicTacToe.whoIsTurn(board);
			if (aspect == Square.X){
				TicTacToe.displayBoard(board);
				System.out.println(turn+ ": " + aspect + "'s turn.");
				System.out.println();
				int destination = a.evaluateOutput(board, Training.INPUTSIZE);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
				}
				else {
					System.out.println("Error. NN could not make a valid move.");
					System.out.println("Exiting...");
					return;
				}
				turn++;
			}
			else { // it's O's turn and we are not training for O
				// other player does random move
				TicTacToe.displayBoard(board);
				System.out.println(turn+ ": " + aspect + "'s turn.");
				System.out.println();
				board = TicTacToe.randomMove(board, aspect);
			}
		}
		TicTacToe.displayBoard(board);
		System.out.println("The Game is Over!");
	}

	public static void playAgainstSelf(){
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		Square [][] board = TicTacToe.createBoard();
		int turn = 1;
		while(!TicTacToe.isGameOver(board)){ // while game is going on		
			Square aspect = TicTacToe.whoIsTurn(board);
			if (aspect == Square.X){
				TicTacToe.displayBoard(board);
				System.out.println(turn+ ": " + aspect + "'s turn.");
				System.out.println();
				int destination = a.evaluateOutput(board, Training.INPUTSIZE);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.doValidMove(board, Square.X, destinationCoordinate);
				}
				else {
					System.out.println("Error. NN could not make a valid move.");
					System.out.println("Exiting...");
					return;
				}
				turn++;
			}
			else { 
				// it's O's turn and we are not training for O
				// other player does random move
				TicTacToe.displayBoard(board);
				System.out.println(turn+ ": " + aspect + "'s turn.");
				System.out.println();
				board = TicTacToe.invertBoard(board);
				int destination = a.evaluateOutput(board, Training.INPUTSIZE);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.invertBoard(board);
					board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
				}
				else {
					System.out.println("Error. NN could not make a valid move.");
					System.out.println("Exiting...");
					return;
				}
			}
		}
		TicTacToe.displayBoard(board);
		System.out.println("The Game is Over!");		
	}
}
