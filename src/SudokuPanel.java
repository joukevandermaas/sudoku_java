/*
 * SudokuApp.java
 * JApplet that attempts to solve all sudokus in sudokus.txt and
 * displays either the last sudoku that could not be solved or the
 * solution to the last sudoku in the file.
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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


public class SudokuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 3028028885712932036L;
	private ButtonGroup numberGroup;
	private JSudokuViewer viewer;
	private Sudoku puzzle;
	private Solver solver;
	private Loader loader;

	public SudokuPanel(Loader puzzleLoader) {
		super();
		
		this.loader = puzzleLoader;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        viewer = new JSudokuViewer();
        viewer.setPuzzle(puzzle);
        viewer.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewer.setAlignmentY(Component.CENTER_ALIGNMENT);
               
        add(Box.createRigidArea(new Dimension(30, 30)));
        add(viewer);
        
        JPanel controls = new JPanel();
        JPanel numberButtons = new JPanel();
        
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        numberButtons.setLayout(new BoxLayout(numberButtons, BoxLayout.Y_AXIS));
        
        numberGroup = new ButtonGroup();
        for(int i = 1; i <= Sudoku.SUDOKU_SIZE; i++) {
        	JToggleButton b = new JToggleButton(Integer.toString(i));
        	b.putClientProperty("value", i);
        	numberGroup.add(b);
        	b.addActionListener(this);
        	numberButtons.add(b);
        }
        
        JButton next = new JButton("Next puzzle");
        next.addActionListener(this);
        next.putClientProperty("type", 0);
        JButton solve = new JButton("Solve puzzle");
        solve.putClientProperty("type", 1);
        solve.addActionListener(this);
        JButton solveAll = new JButton("Solve all");
        solveAll.setToolTipText("Solves all puzzles until an unsolvable puzzle is encountered.");
        solveAll.putClientProperty("type", 2);
        solveAll.addActionListener(this);
        
        controls.add(numberButtons);
        controls.add(Box.createVerticalStrut(20));
        controls.add(next);
        controls.add(solve);
        controls.add(solveAll);

        add(controls);
	}
	
	// loads and solves as many sudokus as possible from
	// sudokus.txt and sets the final solved or unsolved sudoku to this.puzzle
	private boolean loadSudoku() throws SudokuException {
		int[][] loadedSudoku;
		try {
			loadedSudoku = loader.getNext();
			if(loadedSudoku != null) {
				puzzle = new Sudoku(loadedSudoku);
				solver = new Solver(this.puzzle);
				viewer.setPuzzle(puzzle);
				return true;
			}

		} catch (SudokuReaderException e) {
			showError("Entered file is invalid.");
			System.exit(1);
		} catch (IOException e) {
			showError("File could not be read.");
			System.exit(1);
		}
		return false;
	}

	// handles the button presses
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JToggleButton) {
			JToggleButton b = (JToggleButton) e.getSource();
			int value = (Integer)b.getClientProperty("value");
			
			if(b.isSelected()) {
				viewer.setSpecial(value);
			}
		} else if(e.getSource() instanceof JButton) {
			JButton action = (JButton) e.getSource();
			try {
				switch((Integer)action.getClientProperty("type")) {
				case 0: // next
					if(!loadSudoku())
						action.setEnabled(false);
					break;
				case 1: // solve 1
					solver.solve();
					break;
				case 2: // solve all
					while(loadSudoku()) {
						solver.solve();
						if(!puzzle.isSolved())
							break;
					}
				}

			} catch(InvalidSudokuException ex) {
				showError("Invalid sudoku");
			} catch (SudokuException ex) {
				showError("Solver encountered a problem.");
			}
		}
		
		viewer.repaint();
	}
	
	private void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
}

























