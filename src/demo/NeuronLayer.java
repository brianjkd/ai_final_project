package demo;
import java.io.Serializable;
import java.util.ArrayList;
public class NeuronLayer implements Serializable {
	private static final long serialVersionUID = 8551201257325262611L;
	public ArrayList<Neuron> neurons;
	
	public NeuronLayer(ArrayList<Neuron> neurons){
		this.neurons = neurons;
	}
}
