import java.util.List;

public class DoubleLockedStrategy extends LockedStrategy{

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		CellContainer[] squares = puzzle.getSquares();
		boolean result = false;
		for (int i = 0; i < squares.length; i++) { // for every square
			SquareCellContainer square = (SquareCellContainer) squares[i];

			
			for (int n = 1; n <= Sudoku.SUDOKU_SIZE; n++) { // for every digit
				List<Integer> rows = square.findActivatedRows(n);
				List<Integer> cols = square.findActivatedColumns(n);

				if (rows.size() == 2) {
					//findNextDoubleRow(i,squareRow1,squareRow2);
					System.out.print("digit: "+ n + " square: "+i+"\n");
					
					int squaresPerRow = Sudoku.SUDOKU_SIZE / Sudoku.SQUARE_COLUMNS;
					int squareRow = i / squaresPerRow;
					int j = 1;
					int squareRowNext = (i+j) / squaresPerRow;
					System.out.print("squareRow: "+squareRow+" squareRowNext: "+squareRowNext+"\n");
					SquareCellContainer square2 = null;
					
					while (squareRow==squareRowNext) {
						square2 = (SquareCellContainer) squares[i+j];
						
						List<Integer> rows2 = square2.findActivatedRows(n);
						if (rows2.size() == 2) {
							if (rows.get(0)==rows2.get(0) && rows.get(1)==rows2.get(1)) {
								int startCol = getRealCol(i, 0);
								int startCol2 = getRealCol(j, 0);
								int deleteRow = rows.get(0) + rows.get(1);
								if (deleteRow == 1) {
									deleteRow = 2;
								} else if (deleteRow == 2) {
									deleteRow = 1;
								} else {
									deleteRow = 0;
								}
									
									
								Cell[] row = puzzle.getRows()[getRealRow(i, deleteRow)]
										.getCells();
								//row + row
								if (removePossibilities(row, n, startCol, startCol
										+ Sudoku.SQUARE_ROWS, startCol2, startCol2 + Sudoku.SQUARE_ROWS))
									result = true;
							}
						}
						
						j++;
						squareRowNext = (i+j) / squaresPerRow;
					}
					
				}
			}
			
		}
		return result;
	}
	
	protected boolean removePossibilities(Cell[] cells, int value,
			int lowerBound, int upperBound, int lowerBound2, int upperBound2)
					throws SudokuException {
		boolean result = false;
		for (int i = 0; i < Sudoku.SUDOKU_SIZE; i++) {
			if ((i < lowerBound || i >= upperBound) && i < lowerBound2 || i >= upperBound2) {
				//System.out.print("\ncell: "+cells[i].getPossibilities());
				if (!cells[i].hasValue() && cells[i].removePossibility(value)) {
					result = true;
				}
					
					
					
			}
		}
		return result;
	}
}
	/*public int findNextDoubleRow(int square, int row1, int row2) {
		int squaresPerRow = Sudoku.SUDOKU_SIZE / Sudoku.SQUARE_COLUMNS;
		int bla = square / squaresPerRow;
		int i = 0;
		int bla2 = (square+i) / squaresPerRow;
		while (bla==bla2) {
			i++;
			square+i;
		}
		return 0;
	}*/


