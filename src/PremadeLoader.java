import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Loads premade sudoku files, where empty spots are denoted by a 0
 * and each sudoku takes up one line (numbers are read from left to right,
 * top to bottom).
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class PremadeLoader extends Loader {

	public PremadeLoader(BufferedReader file) throws FileNotFoundException {
		super(file);
	}

	public int[][] getNext() throws SudokuReaderException, IOException {
		int[][] puzzle = new int[Sudoku.SUDOKU_SIZE][Sudoku.SUDOKU_SIZE];
		String line = file.readLine();

		if (line == null || line.isEmpty())
			return null;

		char[] chars = line.toCharArray();

		for (int row = 0; row < Sudoku.SUDOKU_SIZE; row++)
			for (int col = 0; col < Sudoku.SUDOKU_SIZE; col++)
				puzzle[row][col] = Integer.parseInt(String.valueOf(chars[row
						* Sudoku.SUDOKU_SIZE + col]));

		return puzzle;
	}

	@Override
	public void close() throws IOException {
		file.close();
	}

}
