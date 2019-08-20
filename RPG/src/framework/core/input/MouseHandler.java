package framework.core.input;

import java.util.ArrayList;
import java.util.List;

import framework.display.states.State;
import javafx.scene.Scene;

public class MouseHandler {

	private List<String> buttons = new ArrayList<>();
	
	private static final int MAX_SCALE = 8;
	private float scale = 1;

	public MouseHandler(State state) {
		Scene scene = state.getScene();

		scene.setOnMousePressed(event -> {
			String button = event.getButton().toString();
			// If button is not in the list
			if (!buttons.contains(button)) {
				buttons.add(button);
			}
		});
		// Remove button from the list when it is released
		scene.setOnMouseReleased(event -> buttons.remove(event.getButton().toString()));

		scene.setOnScroll(event -> {
			if (event.getDeltaY() == 0) {
				// No change in scroll
				return;
			}
			// Scrolling up results in a factor of 1.4, down results in a factor of 1/1.4
			double scaleFactor = event.getDeltaY() > 0 ? 1.4 : 1 / 1.4;
			// If the desired scale is within 1x and 8x size
			if (scale * scaleFactor > 1 && scale * scaleFactor < MAX_SCALE) {
				// Update the scale value
				scale *= scaleFactor;
			}
		});
	}

	public boolean isButtonHeld(String button) {
		return buttons.contains(button);
	}

	public float getScale() {
		return scale;
	}

	public static int getMaxScale() {
		return MAX_SCALE;
	}
}