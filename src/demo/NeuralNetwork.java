package demo;
import java.util.ArrayList;

public class NeuralNetwork {
	
  int numInputs;
  int numOutputs;
  int numHiddenLayers;
  int NeuronsPerHiddenLayer;
  //for tweeking the sigmoid function
  static double dActivationResponse = 1d;
  //bias value
  static double dBias = 1d;

  //storage for each layer of neurons including the output layer
  // Note: the input is not part of the layers
  ArrayList<NeuronLayer> neuronLayers;
  
  

  
  NeuralNetwork(Square [][]board){	  
	  numInputs = board.length * board[0].length; // should be 9
	  System.out.println(numInputs);
	  
	  int [] input = new int [9];
	  
	  // take our board and make our input array
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				input[i+j] = board[i][j].get();
			}
		}
	  
		//for (int i = 0)
	  System.out.println(input.toString());
  }
  
  
/*  ArrayList<Double> update(ArrayList<Double> inputs)
  {
    //stores the resultant outputs from each layer
    ArrayList<Double> outputs;
    int cWeight = 0;

    //first check that we have the correct amount of inputs
    if (inputs.size() != numInputs)
    {
      //just return an empty vector if incorrect.
      return outputs;
    }
    
    //For each layer....
    for (int i=0; i<numHiddenLayers + 1; ++i)
    {
      if ( i > 0 )
      {
        inputs = outputs;
      }
      outputs.clear();
      cWeight = 0;

      //for each neuron sum the (inputs * corresponding weights).Throw
      //the total at our sigmoid function to get the output.
      for (int j=0; j < neuronLayers.get(i).numberOfNeurons; ++j)
      {
        double netinput = 0;
        int NumInputs = neuronLayers.get(i).neurons.get(j).numberOfInputs;
        //for each weight
        for (int k=0; k < NumInputs - 1; ++k)
        {
          //sum the weights x inputs
          netinput += neuronLayers.get(i).neurons.get(j).weights.get(k) * inputs.get(cWeight++);
        }
        //add in the bias
        netinput += neuronLayers.get(i).neurons.get(j).weights.get(NumInputs -1 ) * dBias;

        //we can store the outputs from each layer as we generate them.
        //The combined activation is first filtered through the sigmoid
        //function
        outputs.push_back(Sigmoid(netinput, dActivationResponse));
        cWeight = 0;
      }
    }
    return outputs;
  }*/


}