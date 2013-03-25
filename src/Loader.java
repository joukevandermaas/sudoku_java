/*
 * Loader.java
 * Loads the sudoku file, the format: every non-value has a dot and every 
 * value is a number
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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public abstract class Loader implements Closeable {

	protected BufferedReader file;
	
	public static Loader loadGeneratedFile(String filename) throws FileNotFoundException {
		return new GeneratedLoader(new BufferedReader(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-16"))));
	}
	public static Loader loadPremadeFile(String filename) throws FileNotFoundException {
		return new PremadeLoader(new BufferedReader(new FileReader(filename)));
	}
	
	public Loader(BufferedReader file) throws FileNotFoundException {
		this.file = file;
	}
	
	public abstract int[][] getNext() throws SudokuReaderException, IOException;
	
	@Override
	public void close() throws IOException {
		file.close();
	}

}
