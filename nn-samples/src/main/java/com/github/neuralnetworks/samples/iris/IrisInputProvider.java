package com.github.neuralnetworks.samples.iris;

import java.util.Random;

import com.github.neuralnetworks.input.InputConverter;
import com.github.neuralnetworks.training.TrainingInputProviderImpl;
import com.github.neuralnetworks.util.Matrix;
import com.github.neuralnetworks.util.TensorFactory;

/**
 * Iris dataset (http://archive.ics.uci.edu/ml/datasets/Iris) with random order
 */
public class IrisInputProvider extends TrainingInputProviderImpl {

    private static final long serialVersionUID = 1L;

    private Matrix dataset;
    private float[] input;
    private float[] target;
    private InputConverter targetConverter;
    private Random random;
    private boolean useRandom;
    private boolean scale;
    private boolean attachTargetToInput;

    public IrisInputProvider(InputConverter targetConverter, boolean useRandom, boolean scale, boolean attachTargetToInput) {
	super();
	this.targetConverter = targetConverter;
	this.random = new Random();
	this.useRandom = useRandom;
	this.scale = scale;
	this.attachTargetToInput = attachTargetToInput;
	this.dataset = createDataset();
	this.input = new float[dataset.getRows() + (attachTargetToInput == false ? 1 : 0)];
	this.target = new float[3];
	reset();
    }

//    @Override
//    public TrainingInputData getNextInput() {
//	if (currentInputCount < totalInputSize) {
//	    for (int i = 0; i < batchSize; i++, currentInputCount++) {
//		int k = useRandom ? random.nextInt(150) : currentInputCount % 150;
//		for (int j = 0; j < dataset.getRows() - 1; j++) {
//		    currentExample.getInput().set(dataset.get(j, k), j, i);
//		}
//
//		if (attachTargetToInput) {
//		    Matrix input = (Matrix) currentExample.getInput();
//		    input.set(dataset.get(dataset.getRows() - 1, k), input.getRows() - 1, i);
//		}
//
//		target[i] = (int) dataset.get(dataset.getRows() - 1, k);
//	    }
//
//	    currentExample.setTarget(inputConverter.convert(target));
//
//	    return currentExample;
//	}
//
//	return null;
//    }

    @Override
    public float[] getNextInput() {
	int k = useRandom ? random.nextInt(150) : currentInput % 150;
	for (int j = 0; j < dataset.getRows() - 1; j++) {
	    input[j] = dataset.get(j, k);
	}

	if (attachTargetToInput) {
	    input[input.length - 1] = dataset.get(dataset.getRows() - 1, k);
	}

	return input;
    }

    @Override
    public float[] getNextTarget() {
	int k = useRandom ? random.nextInt(150) : currentInput % 150;
	targetConverter.convert(dataset.get(dataset.getRows() - 1, k), target);
	return target;
    }

    @Override
    public int getInputSize() {
	return 150;
    }

    protected Matrix createDataset() {
	double[] d = new double[] {
		5.1,3.5,1.4,0.2,0,
		4.9,3.0,1.4,0.2,0,
		4.7,3.2,1.3,0.2,0,
		4.6,3.1,1.5,0.2,0,
		5.0,3.6,1.4,0.2,0,
		5.4,3.9,1.7,0.4,0,
		4.6,3.4,1.4,0.3,0,
		5.0,3.4,1.5,0.2,0,
		4.4,2.9,1.4,0.2,0,
		4.9,3.1,1.5,0.1,0,
		5.4,3.7,1.5,0.2,0,
		4.8,3.4,1.6,0.2,0,
		4.8,3.0,1.4,0.1,0,
		4.3,3.0,1.1,0.1,0,
		5.8,4.0,1.2,0.2,0,
		5.7,4.4,1.5,0.4,0,
		5.4,3.9,1.3,0.4,0,
		5.1,3.5,1.4,0.3,0,
		5.7,3.8,1.7,0.3,0,
		5.1,3.8,1.5,0.3,0,
		5.4,3.4,1.7,0.2,0,
		5.1,3.7,1.5,0.4,0,
		4.6,3.6,1.0,0.2,0,
		5.1,3.3,1.7,0.5,0,
		4.8,3.4,1.9,0.2,0,
		5.0,3.0,1.6,0.2,0,
		5.0,3.4,1.6,0.4,0,
		5.2,3.5,1.5,0.2,0,
		5.2,3.4,1.4,0.2,0,
		4.7,3.2,1.6,0.2,0,
		4.8,3.1,1.6,0.2,0,
		5.4,3.4,1.5,0.4,0,
		5.2,4.1,1.5,0.1,0,
		5.5,4.2,1.4,0.2,0,
		4.9,3.1,1.5,0.1,0,
		5.0,3.2,1.2,0.2,0,
		5.5,3.5,1.3,0.2,0,
		4.9,3.1,1.5,0.1,0,
		4.4,3.0,1.3,0.2,0,
		5.1,3.4,1.5,0.2,0,
		5.0,3.5,1.3,0.3,0,
		4.5,2.3,1.3,0.3,0,
		4.4,3.2,1.3,0.2,0,
		5.0,3.5,1.6,0.6,0,
		5.1,3.8,1.9,0.4,0,
		4.8,3.0,1.4,0.3,0,
		5.1,3.8,1.6,0.2,0,
		4.6,3.2,1.4,0.2,0,
		5.3,3.7,1.5,0.2,0,
		5.0,3.3,1.4,0.2,0,
		7.0,3.2,4.7,1.4,1,
		6.4,3.2,4.5,1.5,1,
		6.9,3.1,4.9,1.5,1,
		5.5,2.3,4.0,1.3,1,
		6.5,2.8,4.6,1.5,1,
		5.7,2.8,4.5,1.3,1,
		6.3,3.3,4.7,1.6,1,
		4.9,2.4,3.3,1.0,1,
		6.6,2.9,4.6,1.3,1,
		5.2,2.7,3.9,1.4,1,
		5.0,2.0,3.5,1.0,1,
		5.9,3.0,4.2,1.5,1,
		6.0,2.2,4.0,1.0,1,
		6.1,2.9,4.7,1.4,1,
		5.6,2.9,3.6,1.3,1,
		6.7,3.1,4.4,1.4,1,
		5.6,3.0,4.5,1.5,1,
		5.8,2.7,4.1,1.0,1,
		6.2,2.2,4.5,1.5,1,
		5.6,2.5,3.9,1.1,1,
		5.9,3.2,4.8,1.8,1,
		6.1,2.8,4.0,1.3,1,
		6.3,2.5,4.9,1.5,1,
		6.1,2.8,4.7,1.2,1,
		6.4,2.9,4.3,1.3,1,
		6.6,3.0,4.4,1.4,1,
		6.8,2.8,4.8,1.4,1,
		6.7,3.0,5.0,1.7,1,
		6.0,2.9,4.5,1.5,1,
		5.7,2.6,3.5,1.0,1,
		5.5,2.4,3.8,1.1,1,
		5.5,2.4,3.7,1.0,1,
		5.8,2.7,3.9,1.2,1,
		6.0,2.7,5.1,1.6,1,
		5.4,3.0,4.5,1.5,1,
		6.0,3.4,4.5,1.6,1,
		6.7,3.1,4.7,1.5,1,
		6.3,2.3,4.4,1.3,1,
		5.6,3.0,4.1,1.3,1,
		5.5,2.5,4.0,1.3,1,
		5.5,2.6,4.4,1.2,1,
		6.1,3.0,4.6,1.4,1,
		5.8,2.6,4.0,1.2,1,
		5.0,2.3,3.3,1.0,1,
		5.6,2.7,4.2,1.3,1,
		5.7,3.0,4.2,1.2,1,
		5.7,2.9,4.2,1.3,1,
		6.2,2.9,4.3,1.3,1,
		5.1,2.5,3.0,1.1,1,
		5.7,2.8,4.1,1.3,1,
		6.3,3.3,6.0,2.5,2,
		5.8,2.7,5.1,1.9,2,
		7.1,3.0,5.9,2.1,2,
		6.3,2.9,5.6,1.8,2,
		6.5,3.0,5.8,2.2,2,
		7.6,3.0,6.6,2.1,2,
		4.9,2.5,4.5,1.7,2,
		7.3,2.9,6.3,1.8,2,
		6.7,2.5,5.8,1.8,2,
		7.2,3.6,6.1,2.5,2,
		6.5,3.2,5.1,2.0,2,
		6.4,2.7,5.3,1.9,2,
		6.8,3.0,5.5,2.1,2,
		5.7,2.5,5.0,2.0,2,
		5.8,2.8,5.1,2.4,2,
		6.4,3.2,5.3,2.3,2,
		6.5,3.0,5.5,1.8,2,
		7.7,3.8,6.7,2.2,2,
		7.7,2.6,6.9,2.3,2,
		6.0,2.2,5.0,1.5,2,
		6.9,3.2,5.7,2.3,2,
		5.6,2.8,4.9,2.0,2,
		7.7,2.8,6.7,2.0,2,
		6.3,2.7,4.9,1.8,2,
		6.7,3.3,5.7,2.1,2,
		7.2,3.2,6.0,1.8,2,
		6.2,2.8,4.8,1.8,2,
		6.1,3.0,4.9,1.8,2,
		6.4,2.8,5.6,2.1,2,
		7.2,3.0,5.8,1.6,2,
		7.4,2.8,6.1,1.9,2,
		7.9,3.8,6.4,2.0,2,
		6.4,2.8,5.6,2.2,2,
		6.3,2.8,5.1,1.5,2,
		6.1,2.6,5.6,1.4,2,
		7.7,3.0,6.1,2.3,2,
		6.3,3.4,5.6,2.4,2,
		6.4,3.1,5.5,1.8,2,
		6.0,3.0,4.8,1.8,2,
		6.9,3.1,5.4,2.1,2,
		6.7,3.1,5.6,2.4,2,
		6.9,3.1,5.1,2.3,2,
		5.8,2.7,5.1,1.9,2,
		6.8,3.2,5.9,2.3,2,
		6.7,3.3,5.7,2.5,2,
		6.7,3.0,5.2,2.3,2,
		6.3,2.5,5.0,1.9,2,
		6.5,3.0,5.2,2.0,2,
		6.2,3.4,5.4,2.3,2,
		5.9,3.0,5.1,1.8,2
	};

	Matrix result = TensorFactory.matrix(new float[d.length], 150);
	for (int i = 0; i < d.length; i++) {
	    result.set((float) d[i], i % 5, i / 5);
	}

	if (scale) {
	    for (int i = 0; i < result.getRows() - 1; i++) {
		float max = result.get(i, 0);
		for (int j = 0; j < result.getColumns(); j++) {
		    if (result.get(i, j) > max) {
			max = result.get(i, j);
		    }
		}

		for (int j = 0; j < result.getColumns(); j++) {
		    result.set(result.get(i, j) / max, i, j);
		}
	    }
	}

	return result;
    }
}
