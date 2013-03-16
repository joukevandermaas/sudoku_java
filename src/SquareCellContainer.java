import java.util.ArrayList;
import java.util.List;


public class SquareCellContainer extends CellContainer {

	public SquareCellContainer(Cell[] cells) throws SudokuException {
		super(cells);
	}

	public List<Integer> findActivatedRows(int value) {
		return findActivatedRowsAndCols(value).get(0);
	}
	public List<Integer> findActivatedColumns(int value) {
		return findActivatedRowsAndCols(value).get(1);
	}
	
	private List<List<Integer>> findActivatedRowsAndCols(int value) {
		Cell[] cells = this.getCells();
		List<Integer> rows = new ArrayList<Integer>();
		List<Integer> cols = new ArrayList<Integer>();
		List<List<Integer>> both = new ArrayList<List<Integer>>();
		both.add(rows);
		both.add(cols);
		
		for(int row = 0; row < Sudoku.SQUARE_ROWS; row++) {
			for(int col = 0; col < Sudoku.SQUARE_COLUMNS; col++) {
				Cell c = cells[row * Sudoku.SQUARE_COLUMNS + col];
				if(!c.hasValue() && c.getPossibilities().contains(value)) {
					if(!rows.contains(row))
						rows.add(row);
					if(!cols.contains(col))
						cols.add(col);
				}
			}
		}
		
		return both;
	}
}
