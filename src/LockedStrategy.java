/*
 * RemoveOnlyList.java
 * Searches for 2 or 3 possibilities on a column that are the same and don't 
 * occur in another place in the square if so remove those possibilities on 
 * every other place on the same column
 * Does not work properly yet.
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

		int columns = Sudoku.SQUARE_COLUMNS;
		for (int i = 1; i <= columns; i++) { // determines row (0-2, 3-5 or 6-8)

			int range = columns * i;

			for (int j = 1; j <= columns; j++) { // determines col (0-2, 3-5 or
													// 6-8)
				int range2 = columns * j;
				List<List<Integer>> retainedPossSquare = new ArrayList<List<Integer>>();

				for (int k = range - columns; k < range; k++) { // 3 rows

					List<Cell> squareCol = new ArrayList<Cell>();

					for (int l = range2 - columns; l < range2; l++) { // 3 cols
						Cell c = puzzle.getColumns()[l].getCells()[k];
						squareCol.add(c);
					}
					// corresponding possibilities for every column in a square
					retainedPossSquare.add(retainPoss(squareCol));
				}
				System.out.print("size: " + retainedPossSquare.size());
				// moet max 3 zijn
				for (int k = 0; k < retainedPossSquare.size(); k++) {
					List<Integer> cells = removeOccPoss(retainedPossSquare, k);
					if (cells != null) {
						CellContainer col = puzzle.getColumns()[i];
						if (removePoss(cells, col, i))
							return true;
					}
				}
			}
		}

		return false;
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
	 * input is a list of possible value lists in a square, returns possible
	 * values that are not occurring in other columns in a square
	 */
	public List<Integer> removeOccPoss(List<List<Integer>> retainedPossSquare,
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

	/*
	 * input is a column in a square consisting of possibilities that has to be
	 * removed in the rest of the column
	 */
	public boolean removePoss(List<Integer> cells, CellContainer col, int range)
			throws SudokuException {
		boolean changed = false;
		int columns = Sudoku.SQUARE_COLUMNS;

		for (int l = 0; l < columns * 3; l++) {
			// if it is in the colSquare range or it has a value, skip
			if (!((l >= range - columns) && (l <= range) || col.getCells()[l]
					.hasValue())) {
				col.getCells()[l].removeAllPossibilities(cells);
				changed = true;
			}
		}

		return changed;
	}

}