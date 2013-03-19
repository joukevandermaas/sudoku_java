/*
 * Sudoku.java
 * Representation of a sudoku puzzle. The represented sudoku is
 * not necessarily valid or correct (it is not verified).
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

public class Sudoku {
	public final static int SUDOKU_SIZE = 9;
	public final static int SQUARE_ROWS = 3;
	public final static int SQUARE_COLUMNS = 3;
	
	private CellContainer[] rows = new CellContainer[SUDOKU_SIZE];
	private CellContainer[] columns = new CellContainer[SUDOKU_SIZE];
	private CellContainer[] squares = new CellContainer[SUDOKU_SIZE];
	private CellContainer[] all = new CellContainer[SUDOKU_SIZE * 3];
	
	public Sudoku(int[][] values) throws SudokuException {
		Cell[][] rows = new Cell[SUDOKU_SIZE][SUDOKU_SIZE];
		Cell[][] columns = new Cell[SUDOKU_SIZE][SUDOKU_SIZE];;
		Cell[][] squares = new Cell[SUDOKU_SIZE][SUDOKU_SIZE];;
		
		for(int row = 0; row < values.length; row++) {
			for(int col = 0; col < values[row].length; col++) {
				int square = (row - (row % SQUARE_ROWS)) + col / SQUARE_COLUMNS;
				int squareIndex = (row % SQUARE_ROWS) * 3 + (col % SQUARE_COLUMNS);
				int value = values[row][col];
				Cell cell;
				
				if(value == 0)
					cell = new Cell(row, col, square);
				else
					cell = new Cell(row, col, square, value);
				
				rows[row][col] = cell;
				columns[col][row] = cell;
				squares[square][squareIndex] = cell;
			}
		}
		
		for(int i = 0; i < SUDOKU_SIZE; i++) {
			this.rows[i] = new CellContainer(rows[i]);
			this.columns[i] = new CellContainer(columns[i]);
			this.squares[i] = new SquareCellContainer(squares[i]);
		}
		System.arraycopy(this.rows, 0, all, 0, SUDOKU_SIZE);
		System.arraycopy(this.columns, 0, all, SUDOKU_SIZE, SUDOKU_SIZE);
		System.arraycopy(this.squares, 0, all, SUDOKU_SIZE * 2, SUDOKU_SIZE);
		
	}
	
	public CellContainer[] getRows() {
		return rows;
	}
	public CellContainer[] getColumns() {
		return columns;
	}
	public CellContainer[] getSquares() {
		return squares;
	}
	
	// Returns a combination of rows, columns and squares. There
	// is overlap between the cells in these CellContainers
	public CellContainer[] getAllContainers() {
		return all;
	}
	public Cell getCell(int row, int column) {
		return rows[row].getCells()[column];
	}
	
	public int[][] toIntArray() throws SudokuException {
		int[][] values = new int[SUDOKU_SIZE][SUDOKU_SIZE];
		
		for(int i = 0; i < SUDOKU_SIZE; i++) {
			for(int k = 0; k < SUDOKU_SIZE; k++) {
				Cell c = this.getCell(i, k);
				values[i][k] = c.hasValue() ? c.getValue() : 0;
			}
		}
		
		return values;
	}
	
	public boolean isSolved() throws SudokuException {
		boolean isSolved = true;
		for(CellContainer c : rows) {
			if(!c.isSolved()) {
				isSolved = false;
				break;
			}
		}
		
		return isSolved;
	}
}
