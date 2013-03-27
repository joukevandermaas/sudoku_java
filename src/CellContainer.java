/*
 * CellContainer.java
 * Contains 'sudoku' long size cells, for instance: square, row and column are 
 * CellContainers. Has methods to get all the Cells of the container and
 * values of the Cells in the Container.
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CellContainer {
	private Cell[] cells;

	public CellContainer(Cell[] cells) throws SudokuException {
		if (!(cells.length == Sudoku.SUDOKU_SIZE))
			throw new SudokuException("Wrong number of cells");

		this.cells = cells;
	}

	public Set<Integer> getValues() throws SudokuException {
		Set<Integer> values = new HashSet<Integer>();

		for (Cell c : cells) {
			if (c.hasValue()) {
				if (!values.add(c.getValue()))
					throw new InvalidSudokuException(
							"Double values are not allowed.");
			}
		}

		return values;
	}

	public Cell[] getCells() {
		return cells;
	}

	public boolean isSolved() throws SudokuException {
		return getValues().size() == cells.length;
	}

	public String toString() {
		return Arrays.toString(cells);
	}

}
