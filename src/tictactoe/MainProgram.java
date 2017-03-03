package tictactoe;

public class MainProgram {
	
	public static void main(String[] args){
	    trainWithSettings();
	    // play();
	    // playAgainstSelf();
	}
	
	public static void promptUserForInput(){
		// train ?
		
		// play ?
		
		// random ?
		// self ?
	}
	
	public static void trainWithSettings(){
		int populationSize = 20; // number of neural networks
		int numOfGenerations = 1000; // number of iterations or generations
		int trainingSize = 1000; // the number of boards
		int hiddenLayerNeuronSize = 0; // can be 0 meaning no hidden layer, or any positive integer
		FitnessFunctionType fitnessType = FitnessFunctionType.ValidAndWinningMoves;
		Training.trainWithGeneticAlgorithm(populationSize, numOfGenerations, trainingSize,
				hiddenLayerNeuronSize, fitnessType);
	}

}
