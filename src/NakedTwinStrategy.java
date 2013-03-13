
public class NakedTwinStrategy implements Strategy {

	
	// When two cells have exactly two the same possibilities, remove those as
	// possibilities everywhere else.
	@Override
	public boolean removePossibilities(Sudoku puzzle) throws SudokuException {
		boolean result = false;
		
		for(CellContainer container : puzzle.getAllContainers()) {
			if(findNakedTwins(container)) {
				result = true;
			}
		}
		
		return result;
	}

	private boolean findNakedTwins(CellContainer container) {
		// TODO Auto-generated method stub
		return false;
	}

}
