/*
 * CellContainer.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CellContainer {
	private Cell[] cells;
	
	public CellContainer(Cell[] cells) throws SudokuException {
		if (!(cells.length == Sudoku.SUDOKU_SIZE))
			throw new SudokuException("Wrong number of cells");
		
		this.cells = cells;
	}
	
	public List<Integer> getValues() throws SudokuException {
		List<Integer> values = new ArrayList<Integer>();

		for (Cell c : cells) {
			if(c.hasValue()) {
				values.add(c.getValue());
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
