/*
 * Strategy.java
 * Implementations of this interface attempt to remove possible values
 * from cells in a sudoku, following a specific strategy.
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

public interface Strategy {
	// Removes one or more possible values from the cells in puzzle.
	// Returns true if possibilities were removed, false otherwise.
	boolean removePossibilities(Sudoku puzzle) throws SudokuException, InvalidSudokuException;
}
