package framework.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import framework.graphics.tiles.Tile;
import framework.graphics.tiles.TileMap;

public class AStar {
	
	public static final List<Tile> findPath(TileMap tileMap, int startX, int startY, int goalX, int goalY) {
		// If the goal is not on the map
		if ((goalX < 0 || goalY < 0 || goalX >= tileMap.getWidth() || goalY >= tileMap.getHeight())
				|| (startX == goalX && startY == goalY)) {
			// Return no path
			return null;
		}
		
		// Open list holds tiles that are being checked
		Queue<Tile> openList = new PriorityQueue<>();
		// Closed list holds tiles that not part of the path
		List<Tile> closedList = new LinkedList<Tile>();
		
		// Add the start tile to the open list
		openList.add(tileMap.getTiles()[startX][startY]);
		
		while (!openList.isEmpty()) {
			// Get the next tile in the open list and remove it
			Tile current = openList.poll();
			// Add the tile to the closed list
			closedList.add(current);
			
			// If the current tile is the goal, return the path
			if (current.getX() == goalX && current.getY() == goalY) {
				return getPath(tileMap.getTiles()[startX][startY], current);
			}
			
			// Successors are the tiles adjacent to the current one
			List<Tile> successors = new ArrayList<>();
			int x = current.getX();
			int y = current.getY();
			addSuccessor(tileMap, x - 1, y, successors, closedList); // Left
			addSuccessor(tileMap, x + 1, y, successors, closedList); // Right
			addSuccessor(tileMap, x, y - 1, successors, closedList); // Bottom
			addSuccessor(tileMap, x, y + 1, successors, closedList); // Top
			
			for (Tile candidate : successors) {
				if (!openList.contains(candidate)) {
					// Set parent of adjacent tile to the current tile
					candidate.setParent(current);
					// Calculate H cost of candidate
					candidate.setH(tileMap.getTiles()[goalX][goalY]);
					// Calculate G cost of candidate
					candidate.setG(current);
					// Add the candidate back to the open list
					openList.add(candidate);
				} else if (candidate.getG() > candidate.calcG(current)) {
					// A tile with a smaller cost was found
					candidate.setParent(current);
					candidate.setG(current);
				}
			}
		}
		return null;
	}
	
	/**
	 * Collect and order all tiles from start to goal
	 * 
	 * @param start
	 * @param goal
	 * @return ordered path of tiles from closest to the furthest
	 */
	private static List<Tile> getPath(Tile start, Tile goal) {
		LinkedList<Tile> path = new LinkedList<Tile>();
		Tile Tile = goal;
		boolean finished = false;
		
		while (!finished) {
			path.addFirst(Tile);
			Tile = Tile.getParent();
			if (Tile.equals(start)) {
				finished = true;
			}
		}
		return path;
	}
	
	/**
	 * Find an adjacent tile at the given indices
	 * 
	 * @param tileMap
	 * @param ix
	 * @param iy
	 * @param successors
	 * @param closedList
	 */
	private static void addSuccessor(TileMap tileMap, int ix, int iy, List<Tile> successors, List<Tile> closedList) {
		if (!tileMap.positionExistsOnMap(ix, iy)) {
		    return;
		}
        	Tile Tile = tileMap.getTiles()[ix][iy];
		if (Tile != null && Tile.isWalkable() && !closedList.contains(Tile)) {
				successors.add(Tile);
		}
	}
}
