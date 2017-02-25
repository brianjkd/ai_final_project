/*package demo;

import java.util.Arrays;

public class SimulatedAnnealing {

	public int [] array;
	public State currState;


	public SimulatedAnnealing(int [] array){
		this.array = array;
		this.currState = new State(this.array);
	}

	*//**
	 * 
	 * @param temperature
	 * @param schedule 1 is for linear cooling, 2 is for exponential cooling
	 * @param countdown
	 * @return
	 *//*
	public int [] search(double temperature, int schedule, float countdown){
		double orig = temperature;
		State bestState = currState;
		// This is for the timer.
		countdown = countdown * 1000;
		long currentTime = System.currentTimeMillis();
		// -----------------------------

		int successorStates = 0;
		int time = 0; // used only for the logarithm schedule, which never resets
		while (countdown > (System.currentTimeMillis() - currentTime)){
			State next = getNewState(currState.array);
			
			int delta = ScoreFunction.computeFinalScore(next.array) - ScoreFunction.computeFinalScore(currState.array);
			if (delta > 0){
				currState = next;
				if(ScoreFunction.computeFinalScore(currState.array) > ScoreFunction.computeFinalScore(bestState.array)){
					bestState = currState;
				}
			}
			
			else { //chance of maybe going down, depending on how much the change is and the temperature
				double choice = Math.random();
				double probability;
				if(temperature > 0.001){
					probability = Math.exp(delta/temperature);					
				}
				else {
					probability = 0;
					successorStates++;
				}
				//this was done following the pseudocode in the book, p126
				if(choice < probability){
					currState = next;
				}
			}
			
			//restart once temperature has stabilized and reached local maxima
			if(successorStates > 99) {
				currState.array = Utility.randomizeArray(currState.array);
				temperature = orig;
				successorStates = 0;
				time = 0;
			}
	
			switch(schedule) {
				case 1: temperature -= .01; //linear temperature decrease
						break;
				case 2: temperature *= .99; //exponential temperature decrease
						break;
				case 3: temperature = 1/(Math.log10(time+1));
						time++;
						break;
				default:temperature -= .01;
						break;
			}
		}

		return bestState.array;
	}


	public State getNewState(int [] originalArray){

		int[] array = Arrays.copyOf(originalArray, originalArray.length);

		int x = Utility.randomRange(0, array.length -1);
		int y = Utility.randomRange(0, array.length -1);
		while (y == x){
			y = Utility.randomRange(0, array.length-1);	
		}

		int temp = array[x];
		array[x] = array[y];
		array[y] = temp;

		State state = new State(array);

		return state;
	}

}
*/