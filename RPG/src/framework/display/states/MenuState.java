package framework.display.states;

import framework.core.game.Game;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class MenuState extends State {

	private Game game;
	private Menu menu;

	public MenuState(Game game) {
		super(game);
		this.game = game;
		this.menu = new Menu();

		getScene().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				if (!menu.isVisible()) {
					// Fade the menu into view
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), menu);
					ft.setFromValue(0);
					ft.setToValue(1);

					menu.setVisible(true);
					ft.play();
				} else {
					// Fade the menu out of view
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), menu);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> menu.setVisible(false));
					ft.play();
				}
			}
		});

		Rectangle border = new Rectangle(0, 25, 800, 60);
		border.setOpacity(0.8);

		Text title = new Text("MYSTICS");
		title.setFont(Font.font(34));
		title.setFill(Color.WHITE);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(border, title);

		ImageView backdrop = new ImageView(new Image("res/textures/backdrop.png"));
		addAllToRoot(backdrop, stackPane, menu);
	}

	public class Menu extends Parent {

		public Menu() {
			// Main menu
			VBox main = new VBox(10);
			main.setTranslateX(100);
			main.setTranslateY(200);

			// Options menu
			VBox options = new VBox(10);
			options.setTranslateX(100);
			options.setTranslateY(200);

			final int offset = 400;
			options.setTranslateX(offset);

			MenuButton playButton = new MenuButton("PLAY");
			playButton.setOnMouseClicked(event -> {
				// Fade game state into view
				FadeTransition fade = new FadeTransition(Duration.seconds(0.5), this);
				fade.setFromValue(1);
				fade.setToValue(0);
				fade.setOnFinished(fadeEvent -> {
					setVisible(false);
					// Begin updating the game now that it is visible
					game.getGameLoop().start();
					// Switch to game state
					game.getWindow().setState(game.getGameState());
				});
				fade.play();
			});

			MenuButton optionsButton = new MenuButton("OPTIONS");
			optionsButton.setOnMouseClicked(event -> {
				getChildren().add(options);

				TranslateTransition translate = new TranslateTransition(Duration.seconds(0.25), main);
				translate.setToX(main.getTranslateX() - offset);

				TranslateTransition translate2 = new TranslateTransition(Duration.seconds(0.5), options);
				translate2.setToX(main.getTranslateX());

				translate.play();
				translate2.play();
				translate.setOnFinished(evt -> getChildren().remove(main));
			});

			MenuButton exitButton = new MenuButton("EXIT");
			// Exit the program with code 0 when button is clicked
			exitButton.setOnMouseClicked(event -> System.exit(0));

			MenuButton soundButton = new MenuButton("SOUND");
			soundButton.setOnMouseClicked(event -> {
				// TODO: Disable or enable game sound
			});

			MenuButton backButton = new MenuButton("BACK");
			backButton.setOnMouseClicked(event -> {
				getChildren().add(main);

				TranslateTransition translate = new TranslateTransition(Duration.seconds(0.25), options);
				translate.setToX(options.getTranslateX() + offset);

				TranslateTransition translate2 = new TranslateTransition(Duration.seconds(0.5), main);
				translate2.setToX(options.getTranslateX());

				translate.play();
				translate2.play();

				translate.setOnFinished(evt -> getChildren().remove(options));
			});

			main.getChildren().addAll(playButton, optionsButton, exitButton);
			options.getChildren().addAll(soundButton, backButton);

			Rectangle background = new Rectangle(800, 800);
			background.setFill(Color.GREY);
			background.setOpacity(0.3);

			getChildren().addAll(background, main);
		}
	}

	public class MenuButton extends StackPane {

		private Text text;

		public MenuButton(String name) {
			this.text = new Text(name);
			text.setFont(Font.font(20));
			text.setFill(Color.WHITE);

			Rectangle background = new Rectangle(250, 30);
			background.setOpacity(0.6);
			background.setFill(Color.BLACK);
			background.setEffect(new GaussianBlur(3.5));

			setAlignment(Pos.CENTER_LEFT);
			getChildren().addAll(background, text);

			setOnMouseEntered(event -> {
				background.setTranslateX(10);
				background.setFill(Color.WHITE);
				// Move button over 10 pixels
				text.setTranslateX(10);
				text.setFill(Color.BLACK);
			});

			DropShadow dropShadow = new DropShadow(50, Color.WHITE);
			dropShadow.setInput(new Glow());

			setOnMouseExited(event -> {
				background.setTranslateX(0);
				background.setFill(Color.BLACK);
				// Move button back into place
				text.setTranslateX(0);
				text.setFill(Color.WHITE);
				// Remove effects when mouse exits the button
				setEffect(null);
			});

			// Add shadow effects when button is clicked
			setOnMousePressed(event -> setEffect(dropShadow));
			setOnMouseReleased(event -> setEffect(null));
		}
	}
}