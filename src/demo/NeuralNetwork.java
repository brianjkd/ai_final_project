package demo;

import java.util.ArrayList;
import java.util.Collections;

public class NeuralNetwork {
/*	private int numHiddenLayers;
	private int NeuronsPerHiddenLayer;
	private int inputSize;*/
	private ArrayList<NeuronLayer> neuronLayers;
	private int totalFitness;
	
	
	public int getTotalFitness(){
		return totalFitness;
	}

	public void setTotalFitness(int totalFitness){
		this.totalFitness = totalFitness;
	}
	
	public NeuralNetwork(int inputSize) {
		//this.inputSize = inputSize;
		neuronLayers = new ArrayList<>();
		
		/**
		 * Create Hidden Layer
		 */
/*		ArrayList<Neuron> hiddenNeurons = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			Neuron n = new Neuron(input.size());
			hiddenNeurons.add(n);
		}
		NeuronLayer hiddenLayer = new NeuronLayer(hiddenNeurons);
		neuronLayers.add(hiddenLayer);*/

		/**
		 * Create Output Layer
		 */
		ArrayList<Neuron> outputNeurons = new ArrayList<>();
		for (int i = 0; i < inputSize; i++) {
			Neuron n = new Neuron(inputSize);
			outputNeurons.add(n);
		}
		NeuronLayer outputLayer = new NeuronLayer(outputNeurons);
		neuronLayers.add(outputLayer);
	}
	
	public int evaluateNN(Square [][] inputBoard){
		ArrayList<Integer> input = new ArrayList<>();
		for (int i = 0; i < inputBoard.length; i++) {
			for (int j = 0; j < inputBoard[0].length; j++) {
				input.add(inputBoard[i][j].get());
			}
		}
		ArrayList<Double> moves = activation(neuronLayers.get(0), input);
		return chooseMove(moves);
	}
	
	private ArrayList<Double> activation(NeuronLayer layer, ArrayList<Integer> inputs) {
		ArrayList<Double> outputs = new ArrayList<Double>();
		
		for (Neuron n : layer.neurons) {
			double sum = 0;
			for (int i = 0; i < inputs.size(); i++) {
				sum += inputs.get(i) * n.weights.get(i);
			}
			sum = sigmoid(sum);
			outputs.add(sum);
		}
		return outputs;
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
}
