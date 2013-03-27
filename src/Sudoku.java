/**
 * Representation of a sudoku puzzle. The represented sudoku is
 * not necessarily valid or correct (it is not verified).
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */

public class Sudoku {
	/**
	 * The number of cells in each row/column/square.
	 */
	public final static int SUDOKU_SIZE = 9;
	/**
	 * The number of rows per square.
	 */
	public final static int SQUARE_ROWS = 3;
	/**
	 * The number of columns per square.
	 */
	public final static int SQUARE_COLUMNS = 3;

	private CellContainer[] rows = new CellContainer[SUDOKU_SIZE];
	private CellContainer[] columns = new CellContainer[SUDOKU_SIZE];
	private CellContainer[] squares = new CellContainer[SUDOKU_SIZE];
	private CellContainer[] all = new CellContainer[SUDOKU_SIZE * 3];

	/**
	 * Initializes the Sudoku based on the provided array representation.
	 * @param values
	 * Jagged array where the first index specifies the row and the second
	 * specifies the column of each value.
	 * 
	 * @throws SudokuException
	 * When the array contains invalid values.
	 */
	public Sudoku(int[][] values) throws SudokuException {
		Cell[][] rows = new Cell[SUDOKU_SIZE][SUDOKU_SIZE];
		Cell[][] columns = new Cell[SUDOKU_SIZE][SUDOKU_SIZE];
		Cell[][] squares = new Cell[SUDOKU_SIZE][SUDOKU_SIZE];

		for (int row = 0; row < values.length; row++) {
			for (int col = 0; col < values[row].length; col++) {
				int square = (row - (row % SQUARE_ROWS)) + col / SQUARE_COLUMNS;
				int squareIndex = (row % SQUARE_ROWS) * 3
						+ (col % SQUARE_COLUMNS);
				int value = values[row][col];
				Cell cell;

				if (value == 0)
					cell = new Cell(row, col, square);
				else
					cell = new Cell(row, col, square, value);

				rows[row][col] = cell;
				columns[col][row] = cell;
				squares[square][squareIndex] = cell;
			}
		}

		for (int i = 0; i < SUDOKU_SIZE; i++) {
			this.rows[i] = new CellContainer(rows[i]);
			this.columns[i] = new CellContainer(columns[i]);
			this.squares[i] = new SquareCellContainer(squares[i]);
		}
		System.arraycopy(this.rows, 0, all, 0, SUDOKU_SIZE);
		System.arraycopy(this.columns, 0, all, SUDOKU_SIZE, SUDOKU_SIZE);
		System.arraycopy(this.squares, 0, all, SUDOKU_SIZE * 2, SUDOKU_SIZE);

	}

	/**
	 * Returns an array containing all the rows in the sudoku.
	 */
	public CellContainer[] getRows() {
		return rows;
	}

	/**
	 * Returns an array containing all the columns in the sudoku.
	 */
	public CellContainer[] getColumns() {
		return columns;
	}

	/**
	 * Returns an array containing all the squares in the sudoku.
	 */
	public CellContainer[] getSquares() {
		return squares;
	}

	/**
	 * Returns an array containing all the rows, columns and squares in 
	 * the sudoku. There is overlap in the cells (each cell is in here
	 * three times).
	 */
	public CellContainer[] getAllContainers() {
		return all;
	}

	/**
	 * Returns the cell at the specified location.
	 * @param row
	 * The row of this cell.
	 * @param column
	 * The column of this cell.
	 */
	public Cell getCell(int row, int column) {
		return rows[row].getCells()[column];
	}

	/**
	 * Returns a jagged int array that contains all the values
	 * in the sudoku. Possibilities are ignored.
	 *
	 */
	public int[][] toIntArray() throws SudokuException {
		int[][] values = new int[SUDOKU_SIZE][SUDOKU_SIZE];

		for (int i = 0; i < SUDOKU_SIZE; i++) {
			for (int k = 0; k < SUDOKU_SIZE; k++) {
				Cell c = this.getCell(i, k);
				values[i][k] = c.hasValue() ? c.getValue() : 0;
			}
		}

		return values;
	}

	/**
	 * Returns true if the sudoku is solved, false otherwise.

	 * @throws SudokuException
	 */
	public boolean isSolved() throws SudokuException {
		for (CellContainer c : rows) {
			if (!c.isSolved()) {
				return false;
			}
		}

		return true;
	}
}
