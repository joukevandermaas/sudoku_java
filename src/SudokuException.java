/*
 * SudokuException.java
 * This exception should be thrown for any sudoku related errors.
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

public class SudokuException extends Exception{
	private static final long serialVersionUID = -152735031103016681L;
	
	public SudokuException() {
		super();
	}
	public SudokuException(String message) {
		super(message);
	}
	public SudokuException(Throwable cause) {
		super(cause);
	}
	public SudokuException(String message, Throwable cause) {
		super(message, cause);
	}

}
