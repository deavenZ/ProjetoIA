package algorithms;

import utils.Commons;
import utils.GameController;

public class FeedforwardNeuralNetwork implements GameController {
	private int inputDim=Commons.BREAKOUT_STATE_SIZE;
	private int hiddenDim=10;
	private int outputDim=1;
	private double[][] hiddenWeights;
	private double[] hiddenBiases;
	private double[][] outputWeights;
	private double[] outputBiases;
	private double fitness=0;
	public FeedforwardNeuralNetwork(int inputDim, int hiddenDim, int outputDim) {
		this.inputDim = inputDim;
		this.hiddenDim = hiddenDim;
		this.outputDim = outputDim;
		hiddenWeights = new double[inputDim][hiddenDim];
		outputWeights = new double[hiddenDim][outputDim];
		hiddenBiases = new double[hiddenDim];
		outputBiases = new double[outputDim];
		initializeParameters();
	}

	public FeedforwardNeuralNetwork(int inputDim, int hiddenDim, int outputDim, double[] values) {
		this.inputDim = inputDim;
		this.hiddenDim = hiddenDim;
		this.outputDim = outputDim;
		hiddenWeights = new double[inputDim][hiddenDim];
		outputWeights = new double[hiddenDim][outputDim];
		hiddenBiases = new double[hiddenDim];
		outputBiases = new double[outputDim];
		initializeParameters(values);
	}

	private void initializeParameters() {
		for (int i = 0; i < inputDim; i++) {
			for (int j = 0; j < hiddenDim; j++) {
				hiddenWeights[i][j] = Math.random() * 0.5;
			}
		}

		for (int i = 0; i < hiddenDim; i++) {
			for (int j = 0; j < outputDim; j++) {
				outputWeights[i][j] = Math.random() * 0.5;
			}
		}
		for (int i = 0; i < hiddenDim; i++) {
			hiddenBiases[i] = Math.random() * 0.5;
		}
		for (int i = 0; i < outputDim; i++) {
			outputBiases[i] = Math.random() * 0.5;
		}
	}

	private void initializeParameters(double[] values) {
		int index = 0;
		for (int i = 0; i < inputDim; i++) {
			for (int j = 0; j < hiddenDim; j++) {
				hiddenWeights[i][j] = values[index++];
			}
		}
		for (int i = 0; i < hiddenDim; i++) {
			hiddenBiases[i] = values[index++];
		}
		for (int i = 0; i < hiddenDim; i++) {
			for (int j = 0; j < outputDim; j++) {
				outputWeights[i][j] = values[index++];
			}
		}
		for (int i = 0; i < outputDim; i++) {
			outputBiases[i] = values[index++];
		}
	}

//	public double[] forward(double[] inputValues) {
//		//double[] hiddenLayer = layerOutput(inputValues, hiddenWeights, hiddenBiases);
//		//double[] outputLayer = layerOutput(hiddenLayer, outputWeights, outputBiases);
//		//return outputLayer	;
//		return 0;
//	}

	private double[] layerOutput(double[] input, double[][] weights, double[] biases) {
		double[] layerValues = new double[weights[0].length];

		for (int i = 0; i < weights[0].length; i++) {
			double sum = 0;
			for (int j = 0; j < weights.length; j++) {
				sum += input[j] * weights[j][i];
			}
			sum += biases[i];
			layerValues[i] = 1 / (1 + Math.exp(-sum));
		}
		return layerValues;

	}
	public int size() {
		return hiddenBiases.length + outputBiases.length + inputDim * hiddenDim+ hiddenDim * outputDim;
	}
	public double[] getNeuralNetwork() {
		double[] values = new double[hiddenBiases.length + outputBiases.length + inputDim * hiddenDim
				+ hiddenDim * outputDim];

		int index = 0;

		for (int i = 0; i < inputDim; i++) {
			for (int j = 0; j < hiddenDim; j++) {
				values[index++] = hiddenWeights[i][j];
			}
		}
		for (int i = 0; i < hiddenDim; i++) {
			values[index++] = hiddenBiases[i];
		}
		for (int i = 0; i < hiddenDim; i++) {
			for (int j = 0; j < outputDim; j++) {
				values[index++] = outputWeights[i][j];
			}
		}
		for (int i = 0; i < outputDim; i++) {
			values[index++] = outputBiases[i];
		}
		return values;
	}
	public void setFitness(double fit) {
		fitness=fit;
	}
	
	public double getFitness() {
		return fitness;
	}
	@Override
	public String toString() {
		String result = "Neural Network: \nNumber of inputs: " + inputDim + "\n"
				+ "Weights between input and hidden layer with " + hiddenDim + "neurons: \n";
		String hidden = "";
		for (int input = 0; input < inputDim; input++) {
			for (int i = 0; i < hiddenDim; i++) {
				hidden += " w" + (input + 1) + (i + 1) + ": " + hiddenWeights[input][i] + "\n";
			}
		}
		result += hidden;
		String biasHidden = "Hidden biases: \n";
		for (int i = 0; i < hiddenDim; i++) {
			biasHidden += " b " + (i + 1) + ": " + hiddenBiases[i] + "\n";
		}
		result += biasHidden;
		String output = "Weights between hidden and output layer with " + outputDim + " neurons: \n";
		for (int hiddenw = 0; hiddenw < hiddenDim; hiddenw++) {
			for (int i = 0; i < outputDim; i++) {
				output += " w" + (hiddenw + 1) + "o" + (i + 1) + ": " + outputWeights[hiddenw][i] + "\n";
			}
		}
		result += output;
		String biasOutput = "Ouput biases: \n";
		for (int i = 0; i < outputDim; i++) {
			biasOutput += " bo" + (i + 1) + ": " + outputBiases[i] + "\n";
		}
		result += biasOutput;
		return result;
	}
	
	@Override 
	public int nextMove(int[] currentState){
	    // Convert the input currentState from int[] to double[]
	    double[] currentStateDouble = new double[currentState.length];
	    for (int i = 0; i < currentState.length; i++) {
	        currentStateDouble[i] = currentState[i];
	    }
	    
	    double[] hiddenLayer = layerOutput(currentStateDouble, hiddenWeights, hiddenBiases);
	    double[] outputLayer = layerOutput(hiddenLayer, outputWeights, outputBiases);
	    System.out.println(outputLayer[0]);
	    return (int) outputLayer[0]+1;
	}

}
