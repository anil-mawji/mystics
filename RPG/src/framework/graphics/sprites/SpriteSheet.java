package framework.graphics.sprites;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public final class SpriteSheet {

	private Image sheet;
	private PixelReader pixelReader;
	private int spriteWidth;
	private int spriteHeight;

	/**
	 * A collection of sprites read from the same file
	 * 
	 * @param filePath
	 * @param spriteWidth
	 * @param spriteHeight
	 */
	public SpriteSheet(String filePath, int spriteWidth, int spriteHeight) {
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.sheet = new Image(filePath);
		this.pixelReader = sheet.getPixelReader();
	}

	public SpriteSheet(String filePath) {
		this.sheet = new Image(filePath);
		this.pixelReader = sheet.getPixelReader();
	}

	/**
	 * Cuts an image with the default sprite size from the sprite sheet
	 * 
	 * @param column location of the image in widths
	 * @param row    location of the image in heights
	 * @return image
	 */
	public Image crop(int column, int row) {
		int startX = column * spriteWidth;
		int startY = row * spriteHeight;
		return trim(startX, startY, spriteWidth, spriteHeight);
	}

	/**
	 * Cuts a sprite from the sprite sheet
	 * 
	 * @param startX location of the image pixels
	 * @param startY location of the image pixels
	 * @param width
	 * @param height
	 * @return image
	 */
	public Image trim(int startX, int startY, int width, int height) {
		WritableImage output = new WritableImage(pixelReader, startX, startY, width, height);
		PixelWriter writer = output.getPixelWriter();

		for (int y = startY; y < startY + height; y++) {
			for (int x = startX; x < startX + width; x++) {
				writer.setColor(0, 0, pixelReader.getColor(x, y));
			}
		}
		return output;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}
}