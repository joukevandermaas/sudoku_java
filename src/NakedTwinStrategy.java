import java.util.ArrayList;
import java.util.List;


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

	private boolean findNakedTwins(CellContainer container) throws SudokuException {
		List<Cell> cells = findDoublePossibilityCells(container);
		if(cells.size() < 2)
			return false;
		
		for(int i = cells.size() - 1; i >= 0; i--) {
			Cell c1 = cells.get(i);
			for(int j = 0; j < cells.size(); j++) {
				if(j == i)
					continue;
				Cell c2 = cells.get(j);
				
				if(c1.getPossibilities().containsAll(c2.getPossibilities())) {
					return removeFromOthers(container, c1, c2);
				}
			}
		}
		
		return false;
	}
	
	private boolean removeFromOthers(CellContainer container, Cell c1, Cell c2) throws SudokuException {
		boolean result = false;
		for (Cell c : container.getCells()) {
			if(!c.hasValue() && !c.equals(c1) && !c.equals(c2)) {
				if(c.removeAllPossibilities(c1.getPossibilities()))
					result = true;
			}
		}
		
		return result;
	}

	private List<Cell> findDoublePossibilityCells(CellContainer container) {
		List<Cell> doubles = new ArrayList<Cell>();
		
		for(Cell c : container.getCells()) {
			if(!c.hasValue() && c.getPossibilities().size() == 2)
				doubles.add(c);
		}
		
		return doubles;
	}

}
