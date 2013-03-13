/*
 * SudokuProject.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

import java.io.FileNotFoundException;
import java.util.Arrays;


public class SudokuProject {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Loader l = null;
		try {
			l = Loader.fromFile("sudokus.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int[][] puzzle = l.getNext();
		
		printPuzzle(puzzle);
		System.out.println();
		
		Solver s;
		try {
			s = new Solver(new Sudoku(puzzle));
			s.solve();
			printPuzzle(s.getSudoku().toIntArray());
		} catch (SudokuException e) {
			e.printStackTrace();
		}

	}
	
	public static void printPuzzle(int[][] puzzle) {
		
		 for(int[] row : puzzle)
				System.out.println(Arrays.toString(row));
		 System.out.println();
	}

}
