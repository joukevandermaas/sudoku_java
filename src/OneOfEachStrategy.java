import java.util.Set;

/**
 * Removes possibilities in a cell by looking at remaining possibilities in 
 * rows, columns and squares.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
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
