/*
 * SudokuReaderException.java
 * This exception should be thrown when there is an error
 * loading a sudoku from a file or network. 
 * 
 * Version information
 * v1
 *
 * Date
 * 27/03/2013
 * 
 * Author
 * Jouke van der Maas & Koen Keune
 * 
 */

public class SudokuReaderException extends Exception {
	private static final long serialVersionUID = -152735031103016681L;

	public SudokuReaderException() {
		super();
	}

	public SudokuReaderException(String message) {
		super(message);
	}

	public SudokuReaderException(Throwable cause) {
		super(cause);
	}

	public SudokuReaderException(String message, Throwable cause) {
		super(message, cause);
	}

}
