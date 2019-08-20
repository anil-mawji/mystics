package framework.display.states;

import framework.graphics.renderers.EffectsRenderer;
import framework.graphics.renderers.PrimaryRenderer;
import framework.graphics.renderers.StackedRenderer;
import framework.core.game.Game;
import framework.core.input.KeyHandler;
import framework.core.input.MouseHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class GameState extends State {

	private Game game;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private StackedRenderer stackedRenderer;
	private boolean debuggingEnabled = false;

	private StackPane debugPane;
	private Text debugText;
	private int frames = 0;
	private int currentFrames = 0;
	private long last = -1;
	
	public GameState(Game game) {
		super(game);
		this.game = game;
		this.keyHandler = new KeyHandler(this);
		this.mouseHandler = new MouseHandler(this);
		this.stackedRenderer = new StackedRenderer(this);
		
		stackedRenderer.addRenderer(new PrimaryRenderer(game));
		stackedRenderer.addRenderer(new EffectsRenderer(game));

		// Initialize debug pane
		debugText = new Text();
		debugText.setFont(Font.font("Verdana", 14));
		debugText.setFill(Color.WHITE);

		debugPane = new StackPane(debugText);
		debugPane.setAlignment(Pos.TOP_LEFT);
		debugPane.setPadding(new Insets(10, 0, 0, 10));
		debugPane.setPrefSize(100, 150);
		debugPane.setLayoutX(10);
		debugPane.setLayoutY(10);
		debugPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 5");
		debugPane.setVisible(false);
		addToRoot(debugPane);
	}

	public void tick() {
		// Enable/disable debug mode if the F1 key is pressed
		if (keyHandler.isKeyHeld("F1")) {
			debugPane.setVisible(!debugPane.isVisible());
			debuggingEnabled = !debuggingEnabled;
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// If debugging is enabled
		if (debugPane.isVisible()) {
			frames++;
			long now = System.currentTimeMillis();
			if (now - last >= 1000) {
				// Get current FPS
				currentFrames = frames;
				// Reset counter every second
				last = now;
				frames = 0;
			}
			debugText.setText("FPS: " + currentFrames + "\nWX: " + (int) game.getPlayer().getPosition().getX()
					+ "\nWY: " + (int) game.getPlayer().getPosition().getY() + "\nSX: "
					+ (int) (game.getPlayer().getPosition().getX() - game.getCamera().getOffset().getX()) + "\nSY: "
					+ (int) (game.getPlayer().getPosition().getY() - game.getCamera().getOffset().getY()));
		}
	}

	public void render() {
		stackedRenderer.render();
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}

	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}

	public boolean isDebuggingEnabled() {
		return debuggingEnabled;
	}

	public void setDebuggingEnabled(boolean debuggingEnabled) {
		this.debuggingEnabled = debuggingEnabled;
		debugPane.setVisible(debuggingEnabled);
	}

	public StackedRenderer getStackedRenderer() {
		return stackedRenderer;
	}
}