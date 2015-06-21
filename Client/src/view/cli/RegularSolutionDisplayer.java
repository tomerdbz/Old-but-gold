package view.cli;

import java.io.PrintWriter;

import algorithms.search.Solution;

/** class which displays solutions
 * @author Alon
 *
 */
public class RegularSolutionDisplayer implements SolutionDisplayer {

	@Override
	public void SolutionDisplay(PrintWriter out, Solution s) {
		
		if(s!=null){
		out.println(s);
		out.flush();
		}
		
			
		
	}

	

}