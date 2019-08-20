package framework.graphics.renderers;

import framework.display.states.GameState;
import javafx.scene.Group;

import java.util.Stack;

public final class StackedRenderer {

    private Stack<Renderer> renderers;
    private Group renderGroup;

    /**
     * A collection of render layers that can be added or removed on the fly
     * @param gameState
     */
    public StackedRenderer(GameState gameState) {
        this.renderers = new Stack<>();
        this.renderGroup = new Group();
        
        gameState.addToRoot(renderGroup);
    }

    public void render() {
        renderers.forEach(renderer -> {
            renderer.prepare();
            renderer.render();
        });
    }
    
    /**
     * Add a given renderer to the stack
     * @param renderer
     */
    public void addRenderer(Renderer renderer) {
        renderers.push(renderer);
        renderGroup.getChildren().add(renderer.getCanvas());
    }
    
    /**
     * Remove the top renderer from the stack
     */
    public void removeRenderer() {
    	renderers.pop();
    }
    
    public Stack<Renderer> getRenderers() {
    	return renderers;
    }
}