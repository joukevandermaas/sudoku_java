/*
 * LockedStrategy.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

import java.util.ArrayList;
import java.util.List;

/* 
 * if there 2 or 3 possibilities on a column that are the same and don't occur
 * in another place in the square then remove those possibilities on every
 * other place on the same column
 */
public class LockedStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		boolean changed = false;
		// System.out.print("LockedStrategy");

		// for cell in same square and row, search for possibilities only in one
		// row
		for (CellContainer col : puzzle.getColumns()) {

			int columns = Sudoku.SQUARE_COLUMNS;
			for (int i = 1; i <= columns; i++) {
				List<Cell> squareCol = new ArrayList<Cell>();

				int range = columns * i;
				for (int j = range - columns; j < range; j++) {
					Cell c = col.getCells()[j];
					squareCol.add(c);
				}

				// for(Cell c : squareCol) { //per cell
				for (int j = 1; j < 3; j++) {
					Cell c = squareCol.get(j);
					List<Integer> poss = c.getPossibilities();

					if (!squareCol.get(2).hasValue()
							|| !squareCol.get(3).hasValue()) {
						if (!squareCol.get(2).hasValue()) {
							List<Integer> poss2 = squareCol.get(2)
									.getPossibilities();
							int size2 = poss2.size();
							for (int k = 0; k < size2; k++) {
								// if (poss2.get(k));
							}
						}
						int possSize = poss.size();
						for (int k = 0; k < possSize; k++) {

							// possPerCol[k] = poss.get(k);
							System.out.print(poss.get(k));
						}
						// System.out.print(poss.retainAll(poss.get(1)));
						// poss.get(0);
						System.out.print("bla");

					}
				}
				System.out.println();
			}

			// System.out.println();

		}

		return changed;
	}

}
