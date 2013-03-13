/*
 * Loader.java
 * 
 * Version information
 *
 * Date
 * 
 * Copyright notice
 */

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Loader implements Closeable {

	private BufferedReader file;
	
	public static Loader fromFile(String filename) throws FileNotFoundException {
		return new Loader(new BufferedReader(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-16"))));
	}
	
	public Loader(BufferedReader file) throws FileNotFoundException {
		this.file = file;
	}
	
	public int[][] getNext() {
        String[] rows = readRows();
        int[][] puzzle = new int[Sudoku.SUDOKU_SIZE][Sudoku.SUDOKU_SIZE];
        
        if(rows == null)
        	return null;
        
        for(int i = 0; i < rows.length; i++) {
        	char[] chars = rows[i].toCharArray();
        	for(int j = 0; j < chars.length; j++) {
        		if(chars[j] == '.')
        			continue;
        		else
        			puzzle[i][j] = Integer.parseInt(String.valueOf(chars[j]));
        	}
        }
        
        return puzzle;
	}
	
	private String[] readRows() {
		String[] rows = new String[Sudoku.SUDOKU_SIZE];
		try {
			int counter = 0;
			while(counter < Sudoku.SUDOKU_SIZE) {
				String row = file.readLine();
				if(row == null)
					return null;
				else if(row.startsWith("%") || row.isEmpty()) // comment
					continue;
				else
					rows[counter++] = row;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public void close() throws IOException {
		file.close();
	}

}
