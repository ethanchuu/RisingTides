package tides;

import java.util.*;

/**
 * This class contains methods that provide information about select terrains 
 * using 2D arrays. Uses floodfill to flood given maps and uses that 
 * information to understand the potential impacts. 
 * Instance Variables:
 *  - a double array for all the heights for each cell
 *  - a GridLocation array for the sources of water on empty terrain 
 * 
 * @author Original Creator Keith Scharz (NIFTY STANFORD) 
 * @author Vian Miranda (Rutgers University)
 */
public class RisingTides {

    // Instance variables
    private double[][] terrain;     // an array for all the heights for each cell
    private GridLocation[] sources; // an array for the sources of water on empty terrain 

    /**
     * DO NOT EDIT!
     * Constructor for RisingTides.
     * @param terrain passes in the selected terrain 
     */
    public RisingTides(Terrain terrain) {
        this.terrain = terrain.heights;
        this.sources = terrain.sources;
    }

    /**
     * Find the lowest and highest point of the terrain and output it.
     * 
     * @return double[], with index 0 and index 1 being the lowest and 
     * highest points of the terrain, respectively
     */
    public double[] elevationExtrema() {

        /* WRITE YOUR CODE BELOW */

        double lowest = Double.MAX_VALUE;
        double highest = Double.MIN_VALUE;

        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[0].length; j++) {
                lowest = Math.min(lowest, terrain[i][j]);
                highest = Math.max(highest, terrain[i][j]);
            }
        }

        return new double[]{lowest, highest}; // substitute this line. It is provided so that the code compiles.
    }

    /**
     * Implement the floodfill algorithm using the provided terrain and sources.
     * 
     * All water originates from the source GridLocation. If the height of the 
     * water is greater than that of the neighboring terrain, flood the cells. 
     * Repeat iteratively till the neighboring terrain is higher than the water 
     * height.
     * 
     * 
     * @param height of the water
     * @return boolean[][], where flooded cells are true, otherwise false
     */
    public boolean[][] floodedRegionsIn(double height) {
        
        /* WRITE YOUR CODE BELOW */
        int rows = terrain.length;
        int cols = terrain[0].length;

        boolean[][] results = new boolean[rows][cols];

        ArrayList<GridLocation> grid = new ArrayList<GridLocation>();

        for (int i = 0; i < sources.length; i++) {
            grid.add(sources[i]);
            results[sources[i].row][sources[i].col] = true;
        }

        while (!grid.isEmpty()) {
            GridLocation current = grid.remove(0);

            int row = current.row;
            int col = current.col;

            if (row - 1 >= 0 && terrain[row - 1][col] <= height && !results[row - 1][col]) {
                results[row - 1][col] = true;
                grid.add(new GridLocation(row - 1, col));
            }

            if (row + 1 < rows && terrain[row + 1][col] <= height && !results[row + 1][col]) {
                results[row + 1][col] = true;
                grid.add(new GridLocation(row + 1, col));
            }

            if (col - 1 >= 0 && terrain[row][col - 1] <= height && !results[row][col - 1]) {
                results[row][col - 1] = true;
                grid.add(new GridLocation(row, col - 1));
            }

            if (col + 1 < cols && terrain[row][col + 1] <= height && !results[row][col + 1]) {
                results[row][col + 1] = true;
                grid.add(new GridLocation(row, col + 1));
            }
        }
        return results;
    }

    public boolean isFlooded(double height, GridLocation cell) {
        /* WRITE YOUR CODE BELOW */
        boolean[][] floodedRegions = floodedRegionsIn(height);
        System.out.println(floodedRegions);

        return floodedRegions[cell.row][cell.col];

    }

    /**
     * Given the water height and a GridLocation find the difference between 
     * the chosen cells height and the water height.
     * 
     * If the return value is negative, the Driver will display "meters below"
     * If the return value is positive, the Driver will display "meters above"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param cell location
     * @return double, representing how high/deep a cell is above/below water
     */
    public double heightAboveWater(double height, GridLocation cell) {
        
        /* WRITE YOUR CODE BELOW */
        return terrain[cell.row][cell.col] - height; // substitute this line. It is provided so that the code compiles.
    }

    /**
     * Total land available (not underwater) given a certain water height.
     * 
     * @param height of the water
     * @return int, representing every cell above water
     */
    public int totalVisibleLand(double height) {
        
        /* WRITE YOUR CODE BELOW */
        boolean[][] flooded = floodedRegionsIn(height);
        int count = 0;

        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[0].length; j++) {
                if (!flooded[i][j]) {
                    count++;
                }
            }
        }

        return count; // substitute this line. It is provided so that the code compiles.
    } 

    /**
     * Given 2 heights, find the difference in land available at each height. 
     * 
     * If the return value is negative, the Driver will display "Will gain"
     * If the return value is positive, the Driver will display "Will lose"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param newHeight the future height of the water
     * @return int, representing the amount of land lost or gained
     */
    public int landLost(double height, double newHeight) {
        
        /* WRITE YOUR CODE BELOW */
        return totalVisibleLand(height) - totalVisibleLand(newHeight); // substitute this line. It is provided so that the code compiles.
    }

    /**
     * Count the total number of islands on the flooded terrain.
     * 
     * Parts of the terrain are considered "islands" if they are completely 
     * surround by water in all 8-directions. Should there be a direction (ie. 
     * left corner) where a certain piece of land is connected to another 
     * landmass, this should be considered as one island. A better example 
     * would be if there were two landmasses connected by one cell. Although 
     * seemingly two islands, after further inspection it should be realized 
     * this is one single island. Only if this connection were to be removed 
     * (height of water increased) should these two landmasses be considered 
     * two separate islands.
     * 
     * @param height of the water
     * @return int, representing the total number of islands
     */
    public int numOfIslands(double height) {

        int rows = terrain.length;
        int cols = terrain[0].length;
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(rows, cols);

        boolean[][] floodedRegions = floodedRegionsIn(height);

        for(int i = 0; i <  terrain.length; i++){
            for(int j = 0; j < terrain[0].length; j++){
                if(!floodedRegions[i][j]){
                    int []drow= {-1,-1,-1,0,0,1,1,1};
                    int []dcol ={-1,0,1,-1,1,-1,0,1};
                    for(int f = 0; f < 8; f++){
                        int x = i+drow[f];
                        int y = j+dcol[f];

                        if (x >= 0 && x < rows && y >= 0 && y < cols && !floodedRegions[x][y]) {
                            uf.union(new GridLocation(i, j), new GridLocation(x,y));
                        }
                    }
                }
            }
        }
        HashSet<GridLocation> isl = new HashSet<GridLocation>();
        for(int i = 0; i < terrain.length; i++){
            for(int j = 0; j < terrain[0].length; j ++){
                if(!floodedRegions[i][j]){
                    isl.add (uf. find(new GridLocation(i,j))) ;
                }
            }
        }
        return isl.size();
    }
}