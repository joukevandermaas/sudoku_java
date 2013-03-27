/*
 * OneOfEachStrategy.java
 * Removes possibilities in a cell by looking at remaining possibilities in 
 * rows, columns and squares.
 * 
 * Version information
 * v1
 *
 * Date
 * 27/03/2013
 * 
 * Author
 * Jouke van der Maas & Koen Keune
 * 
 */
import java.util.Set;

public class OneOfEachStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException,
			InvalidSudokuException {
		boolean changed = false;

		for (CellContainer container : puzzle.getAllContainers()) {
			Set<Integer> values = container.getValues();
			for (Cell c : container.getCells()) {
				if (!c.hasValue() && c.removeAllPossibilities(values)) {
					changed = true;
				}
			}
		}

		return changed;
	}

}
