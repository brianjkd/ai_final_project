package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Training {
		
	public static void train(){
		
		int populationSize = 500;
		int numOfIterations = 50;
		
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
		// System.out.println(neuralNetworks.size() + " : " + nextGeneration.size());
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
		// create a list of training boards
		ArrayList<Square[][]> trainingBoards = new ArrayList<>();
		for (int j = 1; j <= 14; j++){
			trainingBoards.add(TrainingBoards.getTrainingBoard(j));
		}
		// for each neural network, compute its average fitness for all training boards
		for (NeuralNetwork n : neuralNetworks){
			int scoreSum = 0;
			int trainingSize = trainingBoards.size();
			for(Square[][] board : trainingBoards){
				int destination = n.evaluateNN(board);
				Square aspect = TicTacToe.whoIsTurn(board); // who's turn it is
				int score = TicTacToe.getBoardFitness(board, aspect, destination);
				scoreSum += score;
				// System.out.println("score from evaluation : " + score);
			}
			n.setTotalFitness(scoreSum / trainingSize);
		}
	}
	
	public static void main(String[] args){
		train();
		//Square [][] board = TicTacToe.createRandomBoard();
	}
	
	
	
	
	
	
	
}
