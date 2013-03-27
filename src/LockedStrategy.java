import java.util.List;

/**
 * Searches for 2 or 3 possibilities in a column that are the same and don't 
 * occur in another place in the square if so remove those possibilities on 
 * every other place on the same column.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class LockedStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException,
			InvalidSudokuException {
		CellContainer[] squares = puzzle.getSquares();
		boolean result = false;

		for (int i = 0; i < squares.length; i++) {
			SquareCellContainer square = (SquareCellContainer) squares[i];

			// repeat for each number
			for (int n = 1; n <= Sudoku.SUDOKU_SIZE; n++) {
				List<Integer> rows = square.findActivatedRows(n);
				List<Integer> cols = square.findActivatedColumns(n);

				if (rows.size() == 1) {
					Cell[] row = puzzle.getRows()[getRealRow(i, rows.get(0))]
							.getCells();
					int startCol = getRealCol(i, 0);
					if (removePossibilities(row, n, startCol, startCol
							+ Sudoku.SQUARE_ROWS))
						result = true;
				}
				if (cols.size() == 1) {
					Cell[] col = puzzle.getColumns()[getRealCol(i, cols.get(0))]
							.getCells();
					int startRow = getRealRow(i, 0);
					if (removePossibilities(col, n, startRow, startRow
							+ Sudoku.SQUARE_COLUMNS))
						result = true;
				}
			}

		}

		return result;
	}

	private boolean removePossibilities(Cell[] cells, int value,
			int lowerBound, int upperBound) throws SudokuException,
			InvalidSudokuException {
		boolean result = false;
		for (int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
			if (i < lowerBound || i >= upperBound) {
				if (!cells[i].hasValue() && cells[i].removePossibility(value))
					result = true;
			}
		}
		return result;
	}

	protected int getRealCol(int square, int colInSquare)
			throws SudokuException {
		// squaresPerRow is always an int (no rounding is done).
		int squaresPerRow = Sudoku.SUDOKU_SIZE / Sudoku.SQUARE_COLUMNS;
		int col = (square % squaresPerRow) * Sudoku.SQUARE_COLUMNS
				+ colInSquare;

		return col;
	}

	protected int getRealRow(int square, int rowInSquare)
			throws SudokuException {
		int squaresPerRow = Sudoku.SUDOKU_SIZE / Sudoku.SQUARE_COLUMNS;
		int row = (square / squaresPerRow) * Sudoku.SQUARE_ROWS + rowInSquare;

		return row;
	}

}
