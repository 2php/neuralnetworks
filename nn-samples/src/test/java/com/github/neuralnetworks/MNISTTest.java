package com.github.neuralnetworks;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.github.neuralnetworks.architecture.types.MultiLayerPerceptron;
import com.github.neuralnetworks.architecture.types.NNFactory;
import com.github.neuralnetworks.input.MultipleNeuronsOutputError;
import com.github.neuralnetworks.input.ScalingInputModifier;
import com.github.neuralnetworks.samples.mnist.MnistInputProvider;
import com.github.neuralnetworks.samples.mnist.MnistTargetMultiNeuronOutputConverter;
import com.github.neuralnetworks.training.TrainerFactory;
import com.github.neuralnetworks.training.backpropagation.BackPropagationTrainer;
import com.github.neuralnetworks.training.events.LogTrainingListener;
import com.github.neuralnetworks.training.random.MersenneTwisterRandomInitializer;

/**
 * MNIST test
 */
public class MnistTest {

    public static final String RESOURCES_PATH = "\\java\\resources\\ML\\datasets\\mnist\\";
    private static final String T10K_LABELS_IDX1_UBYTE = RESOURCES_PATH + "t10k-labels.idx1-ubyte";
    private static final String T10K_IMAGES_IDX3_UBYTE = RESOURCES_PATH + "t10k-images.idx3-ubyte";
    private static final String TRAIN_LABELS_IDX1_UBYTE = RESOURCES_PATH + "train-labels.idx1-ubyte";
    private static final String TRAIN_IMAGES_IDX3_UBYTE = RESOURCES_PATH + "train-images.idx3-ubyte";

    /**
     * Simple mnist backpropagation test
     */
    @Test
    public void testMnistMultipleSigmoidBP() {
	MultiLayerPerceptron mlp = NNFactory.mlpSigmoid(new int[] { 784, 10 }, true);
	MnistInputProvider trainInputProvider = new MnistInputProvider(TRAIN_IMAGES_IDX3_UBYTE, TRAIN_LABELS_IDX1_UBYTE, 1000, new MnistTargetMultiNeuronOutputConverter());
	trainInputProvider.addInputModifier(new ScalingInputModifier(255));
	MnistInputProvider testInputProvider = new MnistInputProvider(T10K_IMAGES_IDX3_UBYTE, T10K_LABELS_IDX1_UBYTE, 1000, new MnistTargetMultiNeuronOutputConverter());
	testInputProvider.addInputModifier(new ScalingInputModifier(255));
	@SuppressWarnings("unchecked")
	BackPropagationTrainer<MultiLayerPerceptron> bpt = TrainerFactory.backPropagationSigmoid(mlp, trainInputProvider, testInputProvider, new MultipleNeuronsOutputError(), new MersenneTwisterRandomInitializer(-0.01f, 0.01f), 0.02f, 0.5f, 0f);
	//Environment.getInstance().setExecutionMode(EXECUTION_MODE.JTP);
	bpt.addEventListener(new LogTrainingListener());

	bpt.train();
	bpt.test();
	assertEquals(0, bpt.getOutputError().getTotalNetworkError(), 0.1);
    }

    /**
     * Simple mnist backpropagation test
     */
    @Ignore
    @Test
    public void testMnistMultipleSigmoidBP2() {
	MultiLayerPerceptron mlp = NNFactory.mlpSigmoid(new int[] { 784, 256, 10 }, true);
	MnistInputProvider trainInputProvider = new MnistInputProvider(TRAIN_IMAGES_IDX3_UBYTE, TRAIN_LABELS_IDX1_UBYTE, 1000, new MnistTargetMultiNeuronOutputConverter());
	trainInputProvider.addInputModifier(new ScalingInputModifier(255));
	MnistInputProvider testInputProvider = new MnistInputProvider(T10K_IMAGES_IDX3_UBYTE, T10K_LABELS_IDX1_UBYTE, 1000, new MnistTargetMultiNeuronOutputConverter());
	testInputProvider.addInputModifier(new ScalingInputModifier(255));
	@SuppressWarnings("unchecked")
	BackPropagationTrainer<MultiLayerPerceptron> bpt = TrainerFactory.backPropagationSigmoid(mlp, trainInputProvider, testInputProvider, new MultipleNeuronsOutputError(), new MersenneTwisterRandomInitializer(-0.01f, 0.01f), 0.02f, 0.5f, 0f);
	//Environment.getInstance().setExecutionMode(EXECUTION_MODE.JTP);
	bpt.addEventListener(new LogTrainingListener(true));

	bpt.train();
	bpt.test();
	assertEquals(0, bpt.getOutputError().getTotalNetworkError(), 0.1);
    }

}
