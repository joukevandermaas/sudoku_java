import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Loads a generated sudoku file, the format: every non-value has a dot and 
 * every value is a number.
 * 
 * @version 1.0
 * @author Jouke van der Maas & Koen Keune
 * 
 */
public class GeneratedLoader extends Loader {

	public GeneratedLoader(BufferedReader file) throws FileNotFoundException {
		super(file);
	}

	public int[][] getNext() throws SudokuReaderException, IOException {
		String[] rows = readRows();
		int[][] puzzle = new int[Sudoku.SUDOKU_SIZE][Sudoku.SUDOKU_SIZE];

		if (rows == null)
			return null;

		try {
			for (int i = 0; i < rows.length; i++) {
				char[] chars = rows[i].toCharArray();
				for (int j = 0; j < chars.length; j++) {
					if (chars[j] == '.')
						continue;
					else
						puzzle[i][j] = Integer.parseInt(String
								.valueOf(chars[j]));
				}
			}
		} catch (NumberFormatException ex) {
			throw new SudokuReaderException("Invalid file");
		}

		return puzzle;
	}

	private String[] readRows() throws IOException {
		String[] rows = new String[Sudoku.SUDOKU_SIZE];
		int counter = 0;
		while (counter < Sudoku.SUDOKU_SIZE) {
			String row = file.readLine();
			if (row == null)
				return null;
			else if (row.startsWith("%") || row.isEmpty()) // comment
				continue;
			else
				rows[counter++] = row;
		}
		return rows;
	}

	@Override
	public void close() throws IOException {
		file.close();
	}

}
