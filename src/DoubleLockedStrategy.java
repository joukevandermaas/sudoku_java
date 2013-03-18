/*
 * DoubleLockedStrategy.java
 * Werkt voor 1 geval, weet niet of 'col if' werkt
 * 
 * Version information
 * v0.2 (alpha 1)
 *
 * Date
 * 18/03/2013
 * 
 * Author
 * Jouke van der Maas & Koen Keune
 * 
 */

import java.util.List;

public class DoubleLockedStrategy extends LockedStrategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		CellContainer[] squares = puzzle.getSquares();
		// boolean result = false;
		for (int i = 0; i < squares.length; i++) { // for every square
			SquareCellContainer square = (SquareCellContainer) squares[i];

			for (int n = 1; n <= Sudoku.SUDOKU_SIZE; n++) { // for every digit
				List<Integer> rows = square.findActivatedRows(n);
				List<Integer> cols = square.findActivatedColumns(n);
				if (rows.size() == 2) {
					int startCol2 = findNextRowSquare(i, n, rows, squares);
					if (startCol2 != -1) {
						int startCol = getRealCol(i, 0);
						for (int j = 0; j < 2; j++) {
							Cell[] row = puzzle.getRows()[getRealRow(i,
									rows.get(j))].getCells();

							if (removePossibilities(row, n, startCol, startCol
									+ Sudoku.SQUARE_ROWS, startCol2, startCol2
									+ Sudoku.SQUARE_ROWS))
								return true; // or result = true
						}
					}
				}
				if (cols.size() == 2) {
					int startRow2 = findNextColSquare(i, n, rows, squares);
					if (startRow2 != -1) {
						int startRow = getRealRow(i, 0);
						for (int j = 0; j < 2; j++) {
							Cell[] row = puzzle.getColumns()[getRealCol(i,
									rows.get(j))].getCells();

							if (removePossibilities(row, n, startRow, startRow
									+ Sudoku.SQUARE_ROWS, startRow2, startRow2
									+ Sudoku.SQUARE_ROWS))
								return true; // or result = true
						}
					}
				}
			}
		}
		return false; // or return result
	}

	// double bound version
	private boolean removePossibilities(Cell[] cells, int value,
			int lowerBound, int upperBound, int lowerBound2, int upperBound2)
			throws SudokuException {
		boolean result = false;
		for (int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
			if ((i < lowerBound || i >= upperBound) && i < lowerBound2
					|| i >= upperBound2) {
				if (!cells[i].hasValue() && cells[i].removePossibility(value))
					result = true;
			}
		}
		return result;
	}

	private int findNextRowSquare(int squareNum, int digit, List<Integer> rows,
			CellContainer[] squares) throws SudokuException {
		int squaresPerRow = Sudoku.SUDOKU_SIZE / Sudoku.SQUARE_COLUMNS;
		int squareRow = squareNum / squaresPerRow;
		int j = 1;
		int squareRowNext = (squareNum + j) / squaresPerRow;

		SquareCellContainer square2 = null;

		while (squareRow == squareRowNext) { // checks if next square is on the
												// same row
			square2 = (SquareCellContainer) squares[squareNum + j];

			List<Integer> rows2 = square2.findActivatedRows(digit);
			if (rows2.size() == 2) {
				if (rows.get(0) == rows2.get(0) && rows.get(1) == rows2.get(1)) {
					return getRealCol(squareNum + j, 0);
				}
			}

			j++; // next square
			squareRowNext = (squareNum + j) / squaresPerRow;
		}
		return -1;
	}

	private int findNextColSquare(int squareNum, int digit, List<Integer> cols,
			CellContainer[] squares) throws SudokuException {
		int squaresPerCol = Sudoku.SUDOKU_SIZE / Sudoku.SQUARE_ROWS;
		int squareCol = squareNum % squaresPerCol;
		int j = 1;
		int squareColNext = (squareNum + j) / squaresPerCol;

		SquareCellContainer square2 = null;

		while (squareCol == squareColNext) { // checks if next square is on the
												// same row
			square2 = (SquareCellContainer) squares[squareNum + j];

			List<Integer> cols2 = square2.findActivatedColumns(digit);
			if (cols2.size() == 2) {
				if (cols.get(0) == cols2.get(0) && cols.get(1) == cols2.get(1)) {
					return getRealRow(squareNum + j, 0);
				}
			}

			j++; // next square
			squareColNext = (squareNum + j) % squaresPerCol;
		}
		return -1;
	}
}