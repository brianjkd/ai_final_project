package demo;
import java.io.Serializable;
import java.util.ArrayList;


public class Neuron implements Serializable{
	private static final long serialVersionUID = 7117372304753499139L;
	public int numberOfInputs;
	public ArrayList<Double> weights;
	
	public Neuron(int numberOfInputs){
		this.numberOfInputs =numberOfInputs;
		this.weights = new ArrayList<>();
		// initialize with random weights
		for (int i = 0; i < numberOfInputs + 1; i++){
			double randWeight = Math.random();
			weights.add(randWeight);
		}
	}
	
	/** 
	 * Make a duplicate of the neuron
	 * @param other
	 */
	public Neuron(Neuron other){
		this.numberOfInputs = other.numberOfInputs;
		this.weights = new ArrayList<>();
		for(Double w : other.weights){
			weights.add(w);
		}
	}
	
	
}
