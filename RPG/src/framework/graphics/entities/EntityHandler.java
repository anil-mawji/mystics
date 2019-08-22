package framework.graphics.entities;

import framework.core.game.Game;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class EntityHandler {

    private Game game;
    private List<Entity> entities;

    public EntityHandler(Game game) {
        this.game = game;
        this.entities = new ArrayList<>();
    }

    public void tick() {
    	// Standard for loop to avoid java.util.ConcurrentModificationException
    	for (int i = 0; i < entities.size(); i++) {
    		Entity entity = entities.get(i);
    		// Update visibility if the entity is visible on the screen during this iteration
    		if (!entity.isVisible()
            		&& entity.getPosition().getX() + entity.getWidth() > game.getCamera().getOffset().getX()
                    && entity.getPosition().getX() < game.getWindow().getWidth() + game.getCamera().getOffset().getX()
                    && entity.getPosition().getY() + entity.getHeight() > game.getCamera().getOffset().getY()
                    && entity.getPosition().getY() < game.getWindow().getHeight() + game.getCamera().getOffset().getY()) {
                entity.setVisible(true);
            }
    	    entity.tick();
		
    	    // If the entity is dead remove it from the game
	    if (!entity.isActive()) {
	    	entities.remove(entity);
	    }
    	}
    }
    
    public void render(GraphicsContext gc) {
    	// Z-indexing: sort the entities based upon their Y values
	entities.sort((Entity a, Entity b) ->
		a.getPosition().getY() + a.getHeight() < b.getPosition().getY() + b.getHeight() ? -1 : 0
	);
	// Render all the entities if they are visible
        entities.forEach(entity -> {
        	if (entity.isVisible()) {
                	entity.render(gc);
               		// Disable entity visibility to prepare for a new iteration
                	entity.setVisible(false);
        	}
        });
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
    	entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
