
public class NakedTwinStrategy implements Strategy {

	
	// When two cells have exactly two the same possibilities, remove those as
	// possibilities everywhere else.
	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		return false;
	}

}
