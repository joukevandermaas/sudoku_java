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
		for (int i = 1; i <= columns; i++) {

			List<List<Integer>> retainedPossSquare = null;

			for (CellContainer col : puzzle.getColumns()) {
				List<Cell> squareCol = new ArrayList<Cell>();

				int range = columns * i;
				for (int j = range - columns; j < range; j++) {
					Cell c = col.getCells()[j];
					squareCol.add(c);
				}

				retainedPossSquare.add(retainPoss(squareCol));

				// ik snap de warning niet
				for (int k = 0; k < retainedPossSquare.size(); k++) {
					List<Integer> cells = removePoss(retainedPossSquare, k);
					/*
					 * dit moeten de possible values zijn die in de rest van de
					 * kolom moeten worden weggestreept
					 */
					for (Cell c : col.getCells()) {
						for (int l = 0; l < columns * 3; l++)
							if (!((l >= range - columns) && (l <= range))) {
								col.getCells()[l].removeAllPossibilities(cells);
							}
					}
				}
			}

		}

		return changed;
	}

	/* retaines possible values of a column in a square */
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
				poss.retainAll(poss2);
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
			if (j != start) {
				List<Integer> poss = retainedPossSquare.get(j);
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
