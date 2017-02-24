package demo;

import java.util.ArrayList;

public class Training {

	public static void train(){
		
		// create a list of training boards
		ArrayList<Square[][]> trainingBoards = new ArrayList<>();
		for (int i = 1; i <= 14; i++){
			trainingBoards.add(TrainingBoards.getTrainingBoard(i));
		}
		
		// create 5 neural networks with random weights
		int inputSize = 9;
		ArrayList<NeuralNetwork> neuralNetworks = new ArrayList<>();
		for (int i = 0; i < 5; i++){
			NeuralNetwork n = new NeuralNetwork(inputSize);
			neuralNetworks.add(n);
		}
		
		// for each neural network, compute its average fitness for all 14 boards
		for (NeuralNetwork n : neuralNetworks){
			int scoreSum = 0;
			int trainingSize= trainingBoards.size();
			for(Square[][] board : trainingBoards){
				int destination = n.evaluateNN(board);
				Square aspect = TicTacToe.whoIsTurn(board); // who's turn it is
				int score = TicTacToe.getBoardFitness(board, aspect, destination);
				scoreSum += score;
			}
			n.setTotalFitness(scoreSum / trainingSize);
		}
		
		// print out all the fitness for all the neural networks
		for (NeuralNetwork n : neuralNetworks){
			System.out.println("Average Fitness on the training boards: " + n.getTotalFitness());
		}
	}
	
	
	public static void main(String[] args){
		train();
	}
	
	
	
	
	
	
	
}
