package framework.graphics.tiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public final class TileMapReader {

	/**
	 * Text file to tile map parser
	 * 
	 * @param filePath
	 * @return
	 */
    public static String[][] loadTextFileAsMap(String filePath) {
        List<String[]> list = new ArrayList<>();
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.split(" "));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[0][0]);
    }

    /**
     * TODO: Direct RGB to tile map parser
     * 
     * @param filePath
     * @return map
     */
    public static int[][] loadImageAsMap(String filePath) {
    	Image image = new Image(filePath);
    	PixelReader pixelReader = image.getPixelReader();
    	
    	int width = (int) image.getWidth();
    	int height = (int) image.getHeight();
    	int[][] map = new int[width][height];
    	
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = pixelReader.getArgb(x, y);
            }
        }
        return map;
    }
}