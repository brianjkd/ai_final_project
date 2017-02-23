package demo;

import java.util.ArrayList;
import java.util.Collections;

public class NeuralNetwork {

/*	int numInputs;
	int numOutputs;*/
	int numHiddenLayers;
	int NeuronsPerHiddenLayer;
/*	// for tweeking the sigmoid function
	static double dActivationResponse = 1d;
	// bias value
	static double dBias = 1d;*/

	// storage for each layer of neurons including the output layer
	// Note: the input is not part of the layers
	ArrayList<NeuronLayer> neuronLayers;

	NeuralNetwork(Square[][] board) {
		// numInputs = board.length * board[0].length; // should be 9
		neuronLayers = new ArrayList<>();

		ArrayList<Integer> input = new ArrayList<>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				input.add(board[i][j].get());
			}
		}
		
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
		for (int i = 0; i < input.size(); i++) {
			Neuron n = new Neuron(input.size());
			outputNeurons.add(n);
		}
		NeuronLayer outputLayer = new NeuronLayer(outputNeurons);
		neuronLayers.add(outputLayer);
	}
	
	public int chooseMove(ArrayList<Double> moves) {
		int move = moves.indexOf(Collections.max(moves));
		return move;
	}
	
	public ArrayList<Double> activation(NeuronLayer layer, ArrayList<Integer> inputs) {
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
	
	public double sigmoid(double input) {
		double result = 0;
		result = 1d/(1d + Math.pow(Math.E, -(input)));
		return result;
	}
}
