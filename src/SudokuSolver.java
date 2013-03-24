import java.io.FileNotFoundException;

import javax.swing.*;


public class SudokuSolver {

	public static void main(String[] args) {
		String filename = "";
		if(args.length > 0) {
			filename = args[0];
		}
		if(args.length > 1) {
			if(args[1].equals("-b"))
				executeBatchMode(filename);
		}
		
		Loader loader = null;
		try {
			loader = Loader.fromFile(filename);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		JFrame gui = new JFrame();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(660, 500);
		gui.setContentPane(new SudokuPanel(loader));
		gui.setVisible(true);

	}

	private static void executeBatchMode(String filename) {
		Loader loader = null;
		try {
			loader = Loader.fromFile(filename);
		} catch(FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(1);
		}
		System.out.println("Starting batchmode on file " + filename + "\nThis might take a while.\n");
		
		int solved = 0;
		int puzzles = 0;
		int invalid = 0;
		int[][] puzzle = loader.getNext();
		long end;
		long start = System.currentTimeMillis();
		
		while (puzzle != null) {
			puzzles++;
			try {
				Sudoku sudoku = new Sudoku(puzzle);
				try {
					Solver solver = new Solver(sudoku);
					solver.solve();
					if(sudoku.isSolved())
						solved++;
				} catch (InvalidSudokuException ex) {
					invalid++;
				}
			} catch(SudokuException ex) {
				// count as not solved
			}
			puzzle = loader.getNext();
		}
		end = System.currentTimeMillis();
		
		System.out.printf("Puzzles: %d\nSolved: %d (%.1f%%)\nInvalid puzzles: %d\nTime: %.2fs (%.2fms average per puzzle)\n\n", 
				puzzles, solved, (double)solved/puzzles * 100, invalid, (double)(end-start)/1000, (double)(end-start)/puzzles);
		
		System.exit(0);
		
	}

}























