package framework.graphics.tiles.animated;

import framework.core.game.Game;
import framework.graphics.sprites.Animation;
import framework.graphics.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;

public class AnimatedTile extends Tile {

	private Game game;
    private Animation animation;
    
    public AnimatedTile(Game game, Animation animation, int id, int x, int y, boolean walkable) {
        super(game, id, x, y, walkable);
        this.game = game;
        this.animation = animation;
    }

    public void tick() {
        animation.tick();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
        		animation.getCurrentFrame().getScaledImage((int) game.getGameState().getMouseHandler().getScale()),
        		getX() * SIZE - game.getCamera().getOffset().getX(),
                getY() * SIZE - game.getCamera().getOffset().getY(),
        		SIZE, SIZE
        );
    }
}