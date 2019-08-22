package framework.display.states;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import framework.core.game.Game;

public class State {

	private Scene scene;
	private Pane root;
	
	public State(Game game) {
		this.root = new Pane();
		this.scene = new Scene(root);
	}

	/**
	 * Adds a node to the root node
	 *
	 * @param node
	 */
	public void addToRoot(Node node) {
		root.getChildren().add(node);
	}

	/**
	 * Adds the given nodes to the root node
	 *
	 * @param nodes
	 */
	public void addAllToRoot(Node... nodes) {
		for (Node node : nodes) {
			root.getChildren().add(node);
		}
	}

	public Scene getScene() {
		return scene;
	}
}
