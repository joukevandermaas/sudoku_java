import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;


public class SudokuSolver {

	public static void main(String[] args) {
		String filename = "";
		int depth = 4;
		boolean batchMode = false;
		
		if(args.length > 0) {
			filename = args[0];
		}
		
		for(int i = 1; i < args.length; i++) {
			String arg = args[i];
			try {
				int value = Integer.parseInt(arg);
				depth = value;
			} catch(NumberFormatException ex) {
				if(arg.equals("-b")) {
					batchMode = true;
				} else {
					errorExit("Syntax:\njava SudokuSolver <filename> [-b] [max depth]");
				}
					
			}
		}
		
		ForcingChainsStrategy.setMaxDepth(depth);
		Loader loader = null;
		try {
			loader = Loader.loadPremadeFile(filename);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
		if(batchMode)
			executeBatchMode(loader);
		
		JFrame gui = new JFrame();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(660, 500);
		gui.setContentPane(new SudokuPanel(loader));
		gui.setVisible(true);

	}

	private static void executeBatchMode(Loader loader) {
		System.out.println("Starting batchmode\nThis might take a while.");
		
		int solved = 0;
		int puzzles = 0;
		int invalid = 0;
		long totalTime = 0;
		long end, start;

		int[][] puzzle;

		try {
			puzzle = loader.getNext();		
			while (puzzle != null) {
				puzzles++;
				System.out.print("\rSolving puzzle " + puzzles);
				try {
					Sudoku sudoku = new Sudoku(puzzle);
					Solver solver = new Solver(sudoku);

					start = System.currentTimeMillis();
					solver.solve();
					end = System.currentTimeMillis();
					totalTime += end - start;
					
					if(sudoku.isSolved())
						solved++;
					
				} catch(InvalidSudokuException ex) {
					invalid++;
				} catch(SudokuException ex) {
					// count as not solved
				}
				puzzle = loader.getNext();
				//break;
			}
		} catch (SudokuReaderException e) {
			errorExit("Invalid file.");
		} catch (IOException e) {
			errorExit("Error reading the file.");
		}
		
		if(puzzles == 0) {
			errorExit("No puzzles found.");
		}
		System.out.printf("\n\nPuzzles: %d\nSolved: %d (%.1f%%)\nInvalid puzzles: %d\nTime: %.2fs (%.2fms average per puzzle)\n\n", 
				puzzles, solved, (double)solved/puzzles * 100, invalid, (double)totalTime/1000, (double)totalTime/puzzles);
		
		System.exit(0);
		
	}
	
	private static void errorExit(String message) {
		System.err.println("\n" + message);
		System.exit(1);
	}

}























