package view.cli;

import java.io.PrintWriter;

import algorithms.search.Solution;

/**	Interface for every solutiondisplayer
 * @author Alon
 *
 */
public interface SolutionDisplayer {
		public void SolutionDisplay(PrintWriter out,Solution s);
}