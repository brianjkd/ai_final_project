package demo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class Neuron implements Serializable{
	private static final long serialVersionUID = 7117372304753499139L;
	public int numberOfInputs;
	public ArrayList<Double> weights;
	
	public Neuron(int numberOfInputs){
		this.numberOfInputs =numberOfInputs;
		this.weights = new ArrayList<>();
		// initialize with random weights
		for (int i = 0; i < numberOfInputs + 1; i++){
			//double randWeight = Math.random();
			// a weight between -1 and 1
			double randWeight = genRandomWeight();
			weights.add(randWeight);
		}
	}
	
	
	public static double genRandomWeight(){
		 return ThreadLocalRandom.current().nextDouble(0, 2) - 1d;
		// return ThreadLocalRandom.current().nextDouble(0, 1);
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
