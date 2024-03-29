package tictactoe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NeuralNetwork implements Comparable<NeuralNetwork>, Serializable {
	private static final long serialVersionUID = 2751926090659843399L;
	private static String PATH = "trainingData/neuralNetwork.ser";
	ArrayList<NeuronLayer> neuronLayers;
	private double fitness;
	private int generationsLived;
	
	ArrayList<Square[][]> trainingBoards = new ArrayList<>();
	
	public double getFitness(){
		return fitness;
		// return totalFitness / (generationsLived * trainingBoards.size());
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}

	
	public int getGenerationsLived(){
		return this.generationsLived;		
	}
	

	
	public void crossover(NeuralNetwork b){
		// figure out our cut points
		ArrayList<Integer> cutPoints = new ArrayList<>();
		
		for (NeuronLayer layer : this.neuronLayers){
			int max = layer.neurons.size();
			Random random = new Random();

			int cut = random.nextInt(max);
			
			cutPoints.add(cut);
		}
		
		NeuralNetwork childOne = reproduce(this, b, cutPoints);
		NeuralNetwork childTwo = reproduce(b, this, cutPoints);
		
		childOne.mutate();
		childTwo.mutate();
	}
	
	public void mutate(){
		double rate = 0.2; // mutation 10% of all weights
		for (NeuronLayer neuronLayer : neuronLayers){
			for (Neuron n : neuronLayer.neurons){
				for (int i = 0 ; i < n.weights.size(); i ++){
					if (i == 0) continue;
					double rand = Math.random();
					if (rand <= rate){
						// mutate the weight
						double randWeight = Neuron.genRandomWeight();
						n.weights.set(i, randWeight);
					}
				}
			}
		}
	}
	
	public static void saveBestNNToFile(ArrayList<NeuralNetwork> neuralNetworks){
		Collections.sort(neuralNetworks);
		System.out.println("The fitness of saved neural network: " + neuralNetworks.get(0).getFitness());
		saveNNToFile(neuralNetworks.get(0));		
	}
	
   public static void saveNNToFile(NeuralNetwork nn) {
	      try {
	         FileOutputStream fileOut = new FileOutputStream(PATH);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(nn);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved in " + PATH);
	      }catch(IOException i) {
	         i.printStackTrace();
      }
   }
   
   public static NeuralNetwork loadNNFromFile(){
	   NeuralNetwork nn = null;
	      try {
	         FileInputStream fileIn = new FileInputStream(PATH);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         nn = (NeuralNetwork) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return nn;
	      }catch(ClassNotFoundException c) {
	         System.out.println("NeuralNetwork class not found");
	         c.printStackTrace();
	         return nn;
	      }
	      return nn;
	   }

	public static NeuralNetwork reproduce(NeuralNetwork a, NeuralNetwork b, ArrayList<Integer> cutPoints){
		
		ArrayList<NeuronLayer> neuronLayers = new ArrayList<>();
		// deep copy board array
		ArrayList<Square [][]> trainingBoards = new ArrayList<>();
		int generationsLived = a.generationsLived;
		for (Square[][] board : a.trainingBoards){
			Square [][] duplicate = TicTacToe.duplicateBoard(board);
			trainingBoards.add(duplicate);
		}
		
		for (int i = 0; i < a.neuronLayers.size(); i ++){
			int cutPoint = cutPoints.get(i);
			ArrayList<Neuron> layer = new ArrayList<>();
			for(int j = 0; j < cutPoint; j++){
				Neuron n = new Neuron(a.neuronLayers.get(i).neurons.get(j));
				layer.add(n);
			}
			
			for (int j = cutPoint; j < a.neuronLayers.get(i).neurons.size(); j++){
				Neuron n = new Neuron(b.neuronLayers.get(i).neurons.get(j));
				layer.add(n);
			}
			NeuronLayer nl = new NeuronLayer(layer);
			neuronLayers.add(nl);
		}
		
		NeuralNetwork c = new NeuralNetwork(neuronLayers, trainingBoards, generationsLived);
		return c;
	}
	
	
	private NeuralNetwork(ArrayList<NeuronLayer> neuronLayers, ArrayList<Square [][]> trainingBoards, int generationsLived){
		this.neuronLayers = neuronLayers;
		this.trainingBoards = trainingBoards;
		this.generationsLived = generationsLived;
	}
	
	
	public static ArrayList<Square [][]> makeTrainingBoards(){
		ArrayList<Square[][]> trainingBoards = new ArrayList<>();
		for (int i = 1; i <= 10; i++){
			// trainingBoards.add(TicTacToe.createRandomBoard2());
			// trainingBoards.add(TrainingBoards.getTrainingBoard(i));
		}
		return trainingBoards;
	}
	
	
	public ArrayList<Square [][]> makeRandomTrainingBoards(){
		ArrayList<Square[][]> trainingBoards = new ArrayList<>();
		for (int i = 0; i <= 5; i++){
			trainingBoards.add(TicTacToe.createRandomBoard());
			//trainingBoards.add(TrainingBoards.getTrainingBoard(i));
		}
		return trainingBoards;
	}
	
	
	public NeuralNetwork(int inputSize, int outputSize, int hiddenLayerNeuronsSize) {
		neuronLayers = new ArrayList<>();
		trainingBoards = makeTrainingBoards();
		generationsLived = 0;
	
		/**
		 * Create One Hidden Layer
		 */
		if (hiddenLayerNeuronsSize > 0){
			ArrayList<Neuron> hiddenLayerNeurons = new ArrayList<>();
			for (int j = 0; j < hiddenLayerNeuronsSize; j ++){
				Neuron n = new Neuron(inputSize);
				hiddenLayerNeurons.add(n);
			}
			NeuronLayer hiddenLayer = new NeuronLayer(hiddenLayerNeurons);
			neuronLayers.add(hiddenLayer);	
		}
		/**
		 * Create Output Layer
		 */
		ArrayList<Neuron> outputNeurons = new ArrayList<>();
		int numOfEdgesPerOutputNeuron = neuronLayers.size() == 0
				? inputSize : neuronLayers.get(0).neurons.size();
		for (int i = 0; i < outputSize; i++) {
			Neuron n = new Neuron(numOfEdgesPerOutputNeuron);
			outputNeurons.add(n);
		}
		NeuronLayer outputLayer = new NeuronLayer(outputNeurons);
		neuronLayers.add(outputLayer);
	}
	
	public int evaluateOutput(Square [][] inputBoard, int inputSize){
		ArrayList<Double> input = new ArrayList<>();
		for (int i = 0; i < inputBoard.length; i++) {
			for (int j = 0; j < inputBoard[0].length; j++) {
				if (inputSize == 18){
				double X = 0d;
				double O = 0d;
				if (inputBoard[i][j] == Square.X) {
					X = 1d;
					//O = 0d;
				}
				else if (inputBoard[i][j] == Square.O) 
					{
						O = 1d;
						//X = 0d;
					}
				
				input.add(X);
				input.add(O);
				}
				else
				input.add((double) inputBoard[i][j].get());
			}
		}
		ArrayList<Double> moves = activation(neuronLayers, input);
		return chooseMove(moves);
	}
	
	
	private ArrayList<Double> activation(ArrayList<NeuronLayer> layers, ArrayList<Double> inputs) {
		ArrayList<Double> outputs = inputs;
		//for (NeuronLayer layer : layers){
		for (int j = 0; j < layers.size(); j ++){
			NeuronLayer layer = layers.get(j);
			// System.out.println("Activating Layer. There are " + layers.size() + " layers.");
			inputs = outputs;
			outputs = new ArrayList<>();
			for (Neuron n : layer.neurons) {
				// sad attempt at dropout
				/*if (j == 1 && layers.size() == 2){ // we are in the hidden layer
					double rand = Math.random();
					if (rand <= 0.5d){
						continue; // skip this with dropout
					}
					
				}*/
				double sum = 0;
				for (int i = 0; i < inputs.size(); i++) {
					sum += inputs.get(i) * n.weights.get(i);
				}
				// System.out.println(sum);
				sum = sigmoid(sum);
				outputs.add(sum);
			}
			// printOutputs(outputs);
		}
		return outputs;
	}
	
	private static void printOutputs(ArrayList<Double> outputs){
		System.out.println("Outputs Size: " + outputs.size());	
	}
	
	
	
	private int chooseMove(ArrayList<Double> moves) {
		int move = moves.indexOf(Collections.max(moves));
		return move;
	}
		
	
	
	private double sigmoid(double input) {
		double result = 0;
		result = 1d/(1d + Math.pow(Math.E, -(input)));
		return result;
	}
	
	
	@Override
	public int compareTo(NeuralNetwork other){
		if (this.getFitness() > other.getFitness()){
			return -1; // this is greater so comes first
		}
		else if (this.getFitness() < other.getFitness()){
			return 1; // this is less some comes after
		}
		else {
			return 0; // they are equal
		}
	}
	
}
