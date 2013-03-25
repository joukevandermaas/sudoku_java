/*
 * Loader.java
 * Loads the sudoku file, the format: every non-value has a dot and every 
 * value is a number
 * 
 * Version information
 * v0.2 (alpha 1)
 *
 * Date
 * 15/03/2013
 * 
 * Author
 * Jouke van der Maas & Koen Keune
 * 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PremadeLoader extends Loader {
		
	public PremadeLoader(BufferedReader file) throws FileNotFoundException {
		super(file);
	}
	
	public int[][] getNext() throws SudokuReaderException, IOException {
        int[][] puzzle = new int[Sudoku.SUDOKU_SIZE][Sudoku.SUDOKU_SIZE];
        String line = file.readLine();
        
        if(line == null)
        	return null;
        
        char[] chars = line.toCharArray();

        for(int row = 0; row < Sudoku.SUDOKU_SIZE; row++)
        	for(int col = 0; col < Sudoku.SUDOKU_SIZE; col++)
        		puzzle[row][col] = Integer.parseInt(String.valueOf(chars[row * Sudoku.SUDOKU_SIZE + col]));
        
        return puzzle;
	}

	@Override
	public void close() throws IOException {
		file.close();
	}

}
