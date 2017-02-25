package demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class NeuralNetwork implements Comparable<NeuralNetwork>{
/*	private int numHiddenLayers;
	private int NeuronsPerHiddenLayer;
	private int inputSize;*/
	ArrayList<NeuronLayer> neuronLayers;
	private int totalFitness;
	
	public int getTotalFitness(){
		return totalFitness;
	}

	public void setTotalFitness(int totalFitness){
		this.totalFitness = totalFitness;
	}
	
	public void crossover(NeuralNetwork b){
		// figure out our cut points
		ArrayList<Integer> cutPoints = new ArrayList<>();
		
		for (NeuronLayer layer : this.neuronLayers){
			int min = 0;
			int max = layer.neurons.size();
			int cut = ThreadLocalRandom.current().nextInt(min, max);
			cutPoints.add(cut);
		}
		
		NeuralNetwork childOne = reproduce(this, b, cutPoints);
		NeuralNetwork childTwo = reproduce(b, this, cutPoints);
		
		childOne.mutate();
		childTwo.mutate();
	}
	
	public void mutate(){
		double rate = 0.3; // mutation 10% of all weights
		for (NeuronLayer neuronLayer : neuronLayers){
			for (Neuron n : neuronLayer.neurons){
				for (Double w : n.weights){
					double rand = Math.random();
					if (rand <= rate){
						// mutate the weight
						w = new Double((Math.random()));
					}
				}
			}
		}
	}
	

	public static NeuralNetwork reproduce(NeuralNetwork a, NeuralNetwork b, ArrayList<Integer> cutPoints){
		
		ArrayList<NeuronLayer> neuronLayers = new ArrayList<>();
		
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
		
		NeuralNetwork c = new NeuralNetwork(neuronLayers);
		return c;
	}
	
	
	private NeuralNetwork(ArrayList<NeuronLayer> neuronLayers){
		this.neuronLayers = neuronLayers;
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
	
	
	@Override
	public int compareTo(NeuralNetwork other){
		if (this.getTotalFitness() > other.getTotalFitness()){
			return -1; // this is greater so comes first
		}
		else if (this.getTotalFitness() < other.getTotalFitness()){
			return 1; // this is less some comes after
		}
		else {
			return 0; // they are equal
		}
	}
	
	
	
	
}
