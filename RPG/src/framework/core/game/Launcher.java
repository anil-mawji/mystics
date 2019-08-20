package framework.core.game;

import javafx.application.Application;
import javafx.stage.Stage;

public final class Launcher extends Application {

	@Override
	public void start(Stage stage) {
		new Game();
	}

	public static void main(String[] args) {
		launch(args);
	}
}