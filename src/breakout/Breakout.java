package breakout;

import java.util.List;

import javax.swing.JFrame;

import algorithms.FeedforwardNeuralNetwork;
import algorithms.GeneticAlgorithm;
import pacman.RandomController;
import utils.Commons;
import utils.GameController;

public class Breakout extends JFrame {

	private static final long serialVersionUID = 1L;
	private BreakoutBoard game;
	public Breakout(GameController network, int i) {
		add(new BreakoutBoard(network, true, i));
		setTitle("Breakout");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		pack();
		setVisible(true);
	}
}

