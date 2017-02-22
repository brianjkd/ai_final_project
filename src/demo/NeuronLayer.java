package demo;
import java.util.ArrayList;
public class NeuronLayer {
	public int numberOfNeurons;
	public ArrayList<Neuron> neurons;
	
	public NeuronLayer(ArrayList<Neuron> neurons){
		this.neurons = neurons;
		this.numberOfNeurons = neurons.size();
	}
}
