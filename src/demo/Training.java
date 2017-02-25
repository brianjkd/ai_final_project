package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Training {
		
	public static void train(){
		
		int populationSize = 50;
		int numOfIterations = 5000;
		
		ArrayList<Integer> fitnesses = new ArrayList<>();
		
		int inputSize = 9;
		ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();
		for (int i = 0; i < populationSize; i++){
			NeuralNetwork n = new NeuralNetwork(inputSize);
			neuralNetworks.add(n);
		}
		
		for (int i = 0; i < numOfIterations; i++){			
			neuralNetworks = createNextGeneration(neuralNetworks);
			addTotalFitness(fitnesses, neuralNetworks);
		}
		
		NeuralNetwork.saveBestNNToFile(neuralNetworks);
		System.out.print(Collections.max(fitnesses));
	}
	
	public static void addTotalFitness(ArrayList<Integer> fitnesses, ArrayList<NeuralNetwork> neuralNetworks){
		for (NeuralNetwork n : neuralNetworks){
			fitnesses.add(n.getTotalFitness());
		}
	}
	
	public static void printFitnesses(ArrayList<NeuralNetwork> neuralNetworks){
		for (NeuralNetwork n : neuralNetworks){
			System.out.println("Average Fitness on the training boards: " + n.getTotalFitness());
		}
	}
	
	public static ArrayList<NeuralNetwork> createNextGeneration(
			ArrayList<NeuralNetwork> neuralNetworks)
	{
		ArrayList<NeuralNetwork> nextGeneration =  new ArrayList<>();
		evaluate(neuralNetworks);
	
		Collections.sort(neuralNetworks);
		// preserve the best 20% from the last group
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
	
	public static NeuralNetwork choose(ArrayList<NeuralNetwork> neuralNetworks){
		NeuralNetwork chosen = null;
		while(chosen == null){
			double rand = Math.random();
			double maxScore = 100d;
			for (NeuralNetwork network : neuralNetworks ){
			double probability = network.getTotalFitness() / maxScore;
				if (rand < probability){
					chosen = network;				
				}
			}
		}
		return chosen;
	}
	
	public static void evaluate(ArrayList<NeuralNetwork> neuralNetworks){
		// for each neural network, compute its average fitness for all training boards
		for (NeuralNetwork n : neuralNetworks){
			int scoreSum = 0;
			//int trainingSize = 0; // n.trainingBoards.size();
			for(Square[][] board : n.trainingBoards){
				if (TicTacToe.isGameOver(board)){
					int rand = ThreadLocalRandom.current().nextInt(0, 14);
					board = TrainingBoards.getTrainingBoard(rand);
				}
				int destination = n.evaluateOutput(board);
				Vector2D destinationCoordinate = TicTacToe.convertIndexToRowCol(destination);
				Square aspect = TicTacToe.whoIsTurn(board); // who's turn it is
				
				//trainingSize++;
				int score = TicTacToe.getBoardFitness(board, aspect, destination);
				scoreSum += score;
				
				// if move was valid do move and mutate the board
				if (TicTacToe.isMoveValid(board, destinationCoordinate)){
					board = TicTacToe.doValidMove(board, aspect, destinationCoordinate);
				}
				// System.out.println("score from evaluation : " + score);
			}
			n.setTotalFitness(scoreSum / n.trainingBoards.size());
		}
	}
	
	public static void main(String[] args){
		train();
		play();
	}
	
	/**
	 * Load up a NN from disk and watch it play against
	 * itself. No learning is done currently
	 */
	public static void play(){
		NeuralNetwork a = NeuralNetwork.loadNNFromFile();

		Square [][] board = TicTacToe.createBoard(); // fresh empty board
		
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
