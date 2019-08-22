package framework.graphics.sprites;

import framework.core.input.MouseHandler;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public final class Sprite {

    private final Image[] resamples = new Image[MouseHandler.getMaxScale()];
	
    private Image image;

    public Sprite(SpriteSheet spriteSheet, int column, int row) {
        this.image = spriteSheet.crop(column, row);
        createResamples();
    }
    
    public Sprite(SpriteSheet spriteSheet, int x, int y, int width, int height) {
    	this.image = spriteSheet.trim(x, y, width, height);
    	createResamples();
    }
    
    private void createResamples() {
    	for (int i = 1; i < resamples.length; i++) {
    		resamples[i - 1] = resample(i);
    	}
    }
    
    public Image resample(int scale) {
        final int width = (int) image.getWidth();
        final int height = (int) image.getHeight();
        
        WritableImage output = new WritableImage(width * scale, height * scale);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        
        // Loop through each pixel in the image
        for (int y = 0; y < height; y++) {
        	for (int x = 0; x < width; x++) {
        		final int argb = reader.getArgb(x, y);
        		// Add new pixels of the same color to the new image
        		for (int dy = 0; dy < scale; dy++) {
        			for (int dx = 0; dx < scale; dx++) {
        				writer.setArgb(x * scale + dx, y * scale + dy, argb);
        			}
        		}
            }
        }
        return output;
    }
    
    public Image getScaledImage(int scale) {
    	return resamples[scale - 1];
    }

    public Image getImage() {
        return image;
    }
}
