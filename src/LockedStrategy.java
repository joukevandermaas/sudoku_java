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
 * if there are 2 or 3 possibilities on a column that are the same and don't occur
 * in another place in the square then remove those possibilities on every
 * other place on the same column
 */
public class LockedStrategy implements Strategy {

	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		boolean changed = false;
		// System.out.print("LockedStrategy");

		int columns = Sudoku.SQUARE_COLUMNS;
		for (int i = 1; i <= columns; i++) { // 3, 6 of 9

			int range = columns * i;
			List<List<Integer>> retainedPossSquare = new ArrayList<List<Integer>>();

			// for (CellContainer col : puzzle.getColumns()) {
			for (int k = range - columns; k < range; k++) {
				CellContainer col = puzzle.getColumns()[k];
				List<Cell> squareCol = new ArrayList<Cell>();

				for (int j = range - columns; j < range; j++) {
					Cell c = col.getCells()[j];
					squareCol.add(c);
				}
				// corresponding possibilities for every column in a square
				retainedPossSquare.add(retainPoss(squareCol));
			}

			System.out.print("size: "+retainedPossSquare.size());

			for (int k = 0; k < retainedPossSquare.size(); k++) {
				List<Integer> cells = removePoss(retainedPossSquare, k);
				/*
				 * dit moeten de possible values zijn die in de rest van de
				 * kolom moeten worden weggestreept
				 */

				for (CellContainer col : puzzle.getColumns()) {
					List<Cell> squareCol = new ArrayList<Cell>();

					for (int j = range - columns; j < range; j++) {
						Cell c = col.getCells()[j];
					}

					if (cells != null) {
						for (Cell c : col.getCells()) {
							for (int l = 0; l < columns * 3; l++)
								if (!((l >= range - columns) && (l <= range) || c
										.hasValue())) {
									col.getCells()[l]
											.removeAllPossibilities(cells);
									changed = true;
								}
						}
					}
				}
			}

		}

		return changed;
	}

	/* retains corresponding possibilities of a column in a square */
	public List<Integer> retainPoss(List<Cell> squareCol) {

		int end = Sudoku.SQUARE_COLUMNS;
		int beginFor = -1;
		for (int i = 0; i < (end - 1); i++) {
			if (!squareCol.get(i).hasValue()) {
				beginFor = i;
			}
		}

		if (beginFor != -1) {
			Cell c = squareCol.get(beginFor);
			List<Integer> poss = c.getPossibilities();
			for (int j = (beginFor + 1); j < end; j++) {
				Cell c2 = squareCol.get(beginFor);
				List<Integer> poss2 = c2.getPossibilities();
				poss.retainAll(poss2); // corresponding possibilities
			}
			return poss;
		} else
			return null;
	}

	/*
	 * returns possible values that are not occurring in other columns in a
	 * square
	 */
	public List<Integer> removePoss(List<List<Integer>> retainedPossSquare,
			int start) {

		List<Integer> possCompare = retainedPossSquare.get(start);

		for (int j = 0; j < retainedPossSquare.size(); j++) {
			if (j != start && retainedPossSquare.get(j) != null) {
				List<Integer> poss = retainedPossSquare.get(j);
				System.out.print(poss.size());
				for (int k = 0; k < poss.size(); k++) {
					if (poss.contains(possCompare.get(k))) {
						possCompare.remove(possCompare.get(k));
					}
				}
			}
		}

		return possCompare;
	}
}