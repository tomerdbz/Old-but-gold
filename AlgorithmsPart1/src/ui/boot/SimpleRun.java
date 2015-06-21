package ui.boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ui.view.CLI;
import ui.view.MyUserCommands;

public class SimpleRun {

	public static void main(String[] args) {
		PrintWriter w=new PrintWriter(System.out);
		CLI cli=new CLI(new BufferedReader(new InputStreamReader(System.in)),w,new MyUserCommands(w));
		cli.start();
	}

}
