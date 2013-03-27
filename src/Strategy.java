/**
 * Implementations of this interface attempt to remove possible values
 * from cells in a sudoku, following a specific strategy.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public interface Strategy {
	/**
	 * Removes one or more possible values from the cells in puzzle.
	 * Returns true if possibilities were removed, false otherwise.
	 * @param puzzle
	 * The puzzle to check.
	 *
	 * @throws SudokuException
	 * When an invalid operation is attempted.
	 * @throws InvalidSudokuException
	 * When all possibilities are removed from a cell.
	 */
	boolean removePossibilities(Sudoku puzzle) throws SudokuException,
			InvalidSudokuException;
}
