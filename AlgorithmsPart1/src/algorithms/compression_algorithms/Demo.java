package algorithms.compression_algorithms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Demo {
	public static void main(String[] args) {//fix \n in WordDictionary + javadoc+ rejar in My UI + put files in MVC+ delete unneccessary comments
		PrintWriter out;
		try {
			System.out.println("start");
			out = new PrintWriter(new HuffmanWriter(new FileWriter("out.hff")));
			out.println("Mississippi river");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new HuffmanReader(new FileReader("out.hff")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			System.out.println(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}  	
}
