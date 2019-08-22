package framework.core.input;

import framework.display.states.State;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

public final class KeyHandler {

	private final List<String> keys = new ArrayList<>();

	public KeyHandler(State state) {
		Scene scene = state.getScene();

		scene.setOnKeyPressed(event -> {
			String key = event.getCode().toString();
			// Add the key if it isn't in the list
			if (!keys.contains(key)) {
				keys.add(key);
			}
		});
		// Remove the key from the list
		scene.setOnKeyReleased(event -> keys.remove(event.getCode().toString()));
	}
	
	public boolean isKeyHeld(String key) {
		return keys.contains(key);
	}
}
