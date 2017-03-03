package tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Training {

	public static final int OUTPUTSIZE = 9;
	public static final int INPUTSIZE = 9; // can also be 18, then binary input will be used
	
	public static void trainWithGeneticAlgorithm(int populationSize, int numOfGenerations,
			int trainingSize, int hiddenLayerNeuronSize, FitnessFunctionType fitnessType){
		
		ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();
		ArrayList<Square [][]> trainingBoards = TicTacToe.makeNRandomTrainingBoards(trainingSize);
	
		for (int i = 0; i < populationSize; i++){
			NeuralNetwork n = new NeuralNetwork(INPUTSIZE, OUTPUTSIZE, hiddenLayerNeuronSize);
			neuralNetworks.add(n);
		}
		
		evaluate(neuralNetworks, trainingBoards, fitnessType);

		for (int i = 0; i < numOfGenerations; i++){
			neuralNetworks = createNextGeneration(neuralNetworks, trainingBoards, fitnessType);
			printGenerationBestFitness(neuralNetworks, i + 1);
		}
		
		NeuralNetwork.saveBestNNToFile(neuralNetworks);
	}
	
	
	public static void printGenerationBestFitness(ArrayList<NeuralNetwork> neuralNetworks, int generation){
		Collections.sort(neuralNetworks);
		NeuralNetwork n = neuralNetworks.get(0);
		System.out.println("Generation " + generation + "'s Best fitness is : " + n.getFitness());
	}

	/**
	 * @param neuralNetworks the current population of neural networks.
	 * @return the next generation of neural networks
	 */
	public static ArrayList<NeuralNetwork> createNextGeneration(
			ArrayList<NeuralNetwork> neuralNetworks, ArrayList<Square [][]> trainingBoards,
			FitnessFunctionType fitnessType)
	{
		ArrayList<NeuralNetwork> nextGeneration =  new ArrayList<>();		
		
		Collections.sort(neuralNetworks);
		// preserve the best 30% from the last group
		int eliteSize = (int)(neuralNetworks.size() * 0.3);
		
		for (int i = 0; i < eliteSize; i++){
			nextGeneration.add(neuralNetworks.get(i));
		}
		
		while (nextGeneration.size() < neuralNetworks.size()){
			NeuralNetwork a = choose(neuralNetworks);
			NeuralNetwork b = choose(neuralNetworks);
			
			ArrayList<Integer> cutPoints = new ArrayList<>();
			
			for (NeuronLayer layer : a.neuronLayers){
				int min = 0;
				int max = layer.neurons.size();
				int cut = ThreadLocalRandom.current().nextInt(min, max);
				cutPoints.add(cut);
			}
			
			NeuralNetwork childOne = NeuralNetwork.reproduce(a, b, cutPoints);
			NeuralNetwork childTwo = NeuralNetwork.reproduce(b, a, cutPoints);
			
			childOne.mutate();
			childTwo.mutate();
			
			nextGeneration.add(childOne);
			nextGeneration.add(childTwo);
		}
		evaluate(nextGeneration, trainingBoards, fitnessType);
		return nextGeneration;
	}
	
	/**
	 * @param neuralNetworks
	 * @return the NeuralNetwork probabilistically chosen to be a parent in reproduction.
	 */
	public static NeuralNetwork choose(ArrayList<NeuralNetwork> neuralNetworks){
		NeuralNetwork chosen = null;
		while(chosen == null){
			double rand = Math.random();
			
			for (NeuralNetwork network : neuralNetworks ){
				double maxScore = 100d ;// network.getGenerationsLived() * 100d;
				double probability = (network.getFitness() / maxScore) + 0.05;
					if (rand < probability){
						chosen = network;				
					}
			}
		}
		return chosen;
	}
	
	/**
	 * Evaluates each neural network, updating the average fitness score for all
	 * training boards belonging to each neural network.
	 * @param neuralNetworks a list of neural networks.
	 */
	public static void evaluate(ArrayList<NeuralNetwork> neuralNetworks,
			ArrayList<Square [][]> trainingBoards,FitnessFunctionType fitnessType){
		for (NeuralNetwork n : neuralNetworks){
			double scoreSum = 0;
			// int trainingBoardSize = n.trainingBoards.size();
			for(int i = 0; i < trainingBoards.size(); i++){
				/*// if the game is over, create a new random board and train on that
				if (TicTacToe.isGameOver(n.trainingBoards.get(i))){
					n.trainingBoards.set(i, TicTacToe.createRandomBoard2());
				}*/
				int destination = n.evaluateOutput(trainingBoards.get(i),INPUTSIZE);
				// Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				Square aspect = TicTacToe.whoIsTurn(trainingBoards.get(i)); // who's turn it is
				Square [][] board = trainingBoards.get(i);
				
				if (aspect == Square.O){
					board = TicTacToe.invertBoard(board);
				}
				double score = FitnessFunctions.getBoardFitness(trainingBoards.get(i), aspect, destination, fitnessType);
				
				if (aspect == Square.O){
					board = TicTacToe.invertBoard(board);
				}
				scoreSum += score;

				/*// if move was valid do move and mutate the board
				if (TicTacToe.isMoveValid(n.trainingBoards.get(i), destinationCoordinate)){
					//System.out.println("Moves is valid");
					n.trainingBoards.set(i, TicTacToe.doValidMove(
							n.trainingBoards.get(i), aspect, destinationCoordinate));
				}*/
			}
			// System.out.println("sum: " + scoreSum + " max: "  + 100 * trainingBoards.size());
			double averageFitness = scoreSum /  ( (double) FitnessFunctions.GOOD * trainingBoards.size());
			// System.out.println("score sum " + scoreSum + " average fitness " + averageFitness);
			n.setFitness(averageFitness * (double) FitnessFunctions.GOOD);
		}
	}
	
	/**
	 * Load up a NN from disk and watch it play against
	 * itself. No learning is done currently
	 */
	public static void play(){
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		Square [][] board = TicTacToe.createBoard();
		
		int turn = 1;
		while(!TicTacToe.isGameOver(board)){ // while game is going on		
			Square aspect = TicTacToe.whoIsTurn(board);
			if (aspect == Square.X){
				TicTacToe.displayBoard(board);
				System.out.println(turn+ ": " + aspect + "'s turn.");
				System.out.println();
				int destination = a.evaluateOutput(board, INPUTSIZE);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
				}
				else {
					System.out.print("Error. NN could not make a valid move.");
					System.out.print("Exiting...");
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
	
/*	public static void runSavedAgainstTrainingBoards(){
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		ArrayList<Square [][]> trainingBoards = NeuralNetwork.makeTrainingBoards();
		for (Square[][] board : trainingBoards){
			int destination = a.evaluateOutput(board);
			Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
			if (!TicTacToe.isMoveValid(board, destinationCoordinate)){
				System.out.print("Error. NN could not make a valid move.");
			}
		}
	}*/

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
				int destination = a.evaluateOutput(board, INPUTSIZE);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.doValidMove(board, Square.X, destinationCoordinate);
				}
				else {
					System.out.print("Error. NN could not make a valid move.");
					System.out.print("Exiting...");
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
				int destination = a.evaluateOutput(board, INPUTSIZE);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.invertBoard(board);
					board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
				}
				else {
					System.out.print("Error. NN could not make a valid move.");
					System.out.print("Exiting...");
					return;
				}
			}
		}
		TicTacToe.displayBoard(board);
		System.out.println("The Game is Over!");		
	}
}
