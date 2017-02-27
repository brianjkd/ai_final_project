package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Training {
	
	public static void main(String[] args){
		// train();
		  play();
		//makeRandomBoards();
	}
		
	public static void train(){
		
		int populationSize = 40; // number of neural networks
		int numOfIterations = 1000; // number of iterations or generations
		
		int inputSize = 9;
		ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();
		for (int i = 0; i < populationSize; i++){
			NeuralNetwork n = new NeuralNetwork(inputSize);
			neuralNetworks.add(n);
		}
		evaluate(neuralNetworks);
		
		for (int i = 0; i < numOfIterations; i++){	
			neuralNetworks = createNextGeneration(neuralNetworks);
			printBestFitness(neuralNetworks);
		}
		NeuralNetwork.saveBestNNToFile(neuralNetworks);
	}
	
	
	public static void printBestFitness(ArrayList<NeuralNetwork> neuralNetworks){
		Collections.sort(neuralNetworks);
		NeuralNetwork n = neuralNetworks.get(0);
		System.out.println("Best fitness is : " + n.getAverageFitness() 
			+ " generationsLived " + n.getGenerationsLived() 
			+ " total fitness " + n.getTotalFitness());
	}

	/**
	 * @param neuralNetworks the current population of neural networks.
	 * @return the next generation of neural networks
	 */
	public static ArrayList<NeuralNetwork> createNextGeneration(
			ArrayList<NeuralNetwork> neuralNetworks)
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
		evaluate(nextGeneration);
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
				double maxScore = network.getGenerationsLived() * 100d;
				double probability = network.getAverageFitness() / maxScore + 0.05;
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
	public static void evaluate(ArrayList<NeuralNetwork> neuralNetworks){
		for (NeuralNetwork n : neuralNetworks){
			int scoreSum = 0;
			int trainingBoardSize = n.trainingBoards.size();
			for(int i = 0; i < trainingBoardSize; i++){
				/*// if the game is over, create a new random board and train on that
				if (TicTacToe.isGameOver(n.trainingBoards.get(i))){
					n.trainingBoards.set(i, TicTacToe.createRandomBoard2());
				}*/
				int destination = n.evaluateOutput(n.trainingBoards.get(i));
				//Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				Square aspect = TicTacToe.whoIsTurn(n.trainingBoards.get(i)); // who's turn it is
				
				int score = TicTacToe.getBoardFitness(n.trainingBoards.get(i), aspect, destination);
				scoreSum += score;

				/*// if move was valid do move and mutate the board
				if (TicTacToe.isMoveValid(n.trainingBoards.get(i), destinationCoordinate)){
					//System.out.println("Moves is valid");
					n.trainingBoards.set(i, TicTacToe.doValidMove(
							n.trainingBoards.get(i), aspect, destinationCoordinate));
				}*/
			}
			n.addTotalFitness(scoreSum);
		}
	}
	
	
	public static void makeRandomBoards(){
		ArrayList<Square [][]> randomBoards = new ArrayList<>();
		
		for (int i = 0; i < 50; i++){
			Square[][] board = TicTacToe.createRandomBoard2();
			randomBoards.add(board);			
		}		
		
		for (Square[][] board: randomBoards){
			TicTacToe.displayBoard(board);
			System.out.println();
		}
	}
	
	/**
	 * Load up a NN from disk and watch it play against
	 * itself. No learning is done currently
	 */
	public static void play(){
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();
		Square [][] board = TicTacToe.createRandomBoard2(); // fresh empty board
		
		int turn = 1;
		while(!TicTacToe.isGameOver(board)){ // while game is going on
			Square aspect = TicTacToe.whoIsTurn(board);
			TicTacToe.displayBoard(board);
			System.out.println(turn+ ": " + aspect + "'s turn.");
			System.out.println();
			int destination = a.evaluateOutput(board);
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
	}
}
