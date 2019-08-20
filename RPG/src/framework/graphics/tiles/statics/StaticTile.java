package framework.graphics.tiles.statics;

import framework.core.game.Game;
import framework.graphics.sprites.Sprite;
import framework.graphics.tiles.Tile;
import javafx.scene.canvas.GraphicsContext;

public class StaticTile extends Tile {

	private Game game;
    private Sprite sprite;

    public StaticTile(Game game, Sprite sprite, int id, int x, int y, boolean walkable) {
        super(game, id, x, y, walkable);
        this.game = game;
        this.sprite = sprite;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
        		sprite.getScaledImage((int) game.getGameState().getMouseHandler().getScale()),
        		(getX() * SIZE - game.getCamera().getOffset().getX()),
                (getY() * SIZE - game.getCamera().getOffset().getY()),
                SIZE, SIZE
        );
    }
}