import java.util.*;

/**
 * This class attempts to solve a sudoku puzzle using various Strategy-
 * implementations in increasing complexity.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class Solver {
	private Sudoku sudoku;
	private Strategy[] strategies = { new OneOfEachStrategy(),
			new LockedStrategy(), new DoubleLockedStrategy(),
			new NakedGroupStrategy(), new HiddenTwinStrategy(),
			new ForcingChainsStrategy() };

	/**
	 * Initializes the solver and fixes the possibilities in the sudoku.
	 * @param puzzle
	 * The puzzle to solve.
	 *
	 * @throws InvalidSudokuException
	 * When the specified sudoku is invalid. This exception is not
	 * always thrown even if the sudoku is invalid.
	 */
	public Solver(Sudoku puzzle) throws SudokuException, InvalidSudokuException {
		this.sudoku = puzzle;

		// strategies[0] fixes special case and ensures no wrong number will
		// be entered in a CellContainer with only one empty cell at the start.
		strategies[0].removePossibilities(sudoku);
	}

	/**
	 * Solves the sudoku. Returns when the sudoku is solved, or when
	 * no more solving steps could be taken (i.e. the sudoku is invalid
	 * or it was too hard).
	 * 
	 * @throws SudokuException
	 * When an illegal operation was attempted.
	 * @throws InvalidSudokuException
	 * When the sudoku is invalid. This exception is only thrown if the
	 * invalid state was reached (i.e. if the solver could solve it to that
	 * point).
	 */
	public void solve() throws SudokuException, InvalidSudokuException {
		while (takeStep() && !sudoku.isSolved())
			;
	}

	/**
	 * Takes a single solving step. A step consists of two stages:
	 * 1. Filling in numbers in cells where the value is known
	 * 2. Removing possibilities using various strategies.
	 * 
	 * Whenever an easier strategy works, more complex strategies
	 * are not attempted. This means easier sudokus are faster to
	 * solve.
	 * @return
	 * True if progress was made, false otherwise.
	 * @throws InvalidSudokuException
	 * When two cells in the same container have the same value, or
	 * when an empty cell has no possibilities left.
	 * @throws SudokuException
	 * When an invalid operation is attempted.
	 */
	public boolean takeStep() throws InvalidSudokuException, SudokuException {
		boolean madeProgress = false;

		if (enterValue())
			madeProgress = true;

		for (Strategy strat : strategies) {
			// Only use more advanced strategies when the most simple
			// one fails and no more numbers can be entered directly.
			if (strat.removePossibilities(sudoku) || madeProgress) {
				madeProgress = true;
				break;
			}
		}

		return madeProgress;
	}

	/**
	 * Enters one or more values in cells where they are known.
	 * @return
	 * true if a value could be entered, false otherwise.
	 * @throws SudokuException
	 * When an invalid operation is attempted.
	 */
	private boolean enterValue() throws SudokuException {
		boolean result = false;
		
		// all single-possibility cells can safely be 
		// filled in together, as filling one in doesn't
		// cause any new ones to exist.
		for (CellContainer c : sudoku.getSquares()) {
			if (enterSingles(c))
				result = true;
		}

		// Filling in a number because it was the only possibility
		// in that container can cause new ones to exist, so here
		// only one can be entered at a time.
		if (!result)
			for (CellContainer c : sudoku.getAllContainers())
				if (enterHiddenSingle(c))
					return true;
		
		return result;
	}

	/**
	 * Enters values where a cell has only one possibility.
	 * @param container
	 * The container to search.
	 * @return
	 * True if a value was entered, false otherwise.
	 * @throws SudokuException
	 */
	private boolean enterSingles(CellContainer container)
			throws SudokuException {
		boolean result = false;

		for (Cell c : container.getCells()) {
			if (!c.hasValue() && c.getPossibilities().size() == 1) {
				c.setValue(c.getPossibilities().get(0));
				result = true;
			}
		}
		return result;
	}

	/**
	 * Enters a value if that value can go in only one spot in the container,
	 * even if other numbers can go in that same spot.
	 * @param container
	 * The container to search.
	 * @return
	 * True if a value was entered, false otherwise.
	 * @throws SudokuException
	 */
	private boolean enterHiddenSingle(CellContainer container)
			throws SudokuException {
		List<ArrayList<Cell>> values = new ArrayList<ArrayList<Cell>>();

		for (int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
			values.add(new ArrayList<Cell>());

			for (Cell c : container.getCells()) {
				if (!c.hasValue() && c.getPossibilities().contains(i))
					values.get(i - 1).add(c);
			}
		}

		for (int i = 1; i < Sudoku.SUDOKU_SIZE; i++) {
			List<Cell> l = values.get(i - 1);
			if (l.size() == 1) {
				l.get(0).setValue(i);
				return true;
			}
		}

		return false;
	}
}
