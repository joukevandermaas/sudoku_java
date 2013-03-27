import java.util.ArrayList;
import java.util.List;

/**
 * Represents a n x n square in a sudoku.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class SquareCellContainer extends CellContainer {

	/**
	 * Initialises the cell container.
	 * @param cells
	 * The cells in this container, from left to right, top to bottom.
	 * @throws SudokuException
	 */
	public SquareCellContainer(Cell[] cells) throws SudokuException {
		super(cells);
	}

	/**
	 * Finds the rows (within the square) that have the specified value.
	 * @param value
	 * The value to look for.
	 * @return
	 * A list of indices within the square (i.e. if the square has three
	 * rows, the maximum value in this list is 2).
	 */
	public List<Integer> findActivatedRows(int value) {
		return findActivatedRowsAndCols(value).get(0);
	}

	/**
	 * Finds the columns (within the square) that have the specified value.
	 * @param value
	 * The value to look for.
	 * @return
	 * A list of indices within the square (i.e. if the square has three
	 * columns, the maximum value in this list is 2).
	 */
	public List<Integer> findActivatedColumns(int value) {
		return findActivatedRowsAndCols(value).get(1);
	}

	/**
	 * Finds the rows and columns (within the square) that have the specified value.
	 * @param value
	 * The value to look for.
	 * @return
	 * A list lists of indices within the square (i.e. if the square has three
	 * rows and columns, the maximum value in each list is 2).
	 */
	private List<List<Integer>> findActivatedRowsAndCols(int value) {
		Cell[] cells = this.getCells();
		List<Integer> rows = new ArrayList<Integer>();
		List<Integer> cols = new ArrayList<Integer>();
		List<List<Integer>> both = new ArrayList<List<Integer>>();
		both.add(rows);
		both.add(cols);

		for (int row = 0; row < Sudoku.SQUARE_ROWS; row++) {
			for (int col = 0; col < Sudoku.SQUARE_COLUMNS; col++) {
				Cell c = cells[row * Sudoku.SQUARE_COLUMNS + col];
				if (!c.hasValue() && c.getPossibilities().contains(value)) {
					if (!rows.contains(row))
						rows.add(row);
					if (!cols.contains(col))
						cols.add(col);
				}
			}
		}

		return both;
	}
}
