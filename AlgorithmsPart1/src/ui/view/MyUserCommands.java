package ui.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.compression_algorithms.HuffmanAlg;
import algorithms.compression_algorithms.WordDictionary;


/** my implementation of UserCommands with the commands that were defined in the assignment.
 * @author Tomer Cabouly
 *
 */
public class MyUserCommands extends CommonUserCommands {
	/** in this exercise - I had to print output somehow - whether it's in case there's an error or all went well.
	 * one way to do this it to define doCommand() as returning a String. but looking at return values is "passe", and besides, it wouldn't be smart in the MVC exercise.
	 * thus - I decided UserCommands will get the output to which it will print - and out was born.
	 */
	private PrintWriter out;
	/**putting the commands that were defined bellow as part of the command pattern.
	 * @param out - output to print to. (PrintWriter)
	 */
	public MyUserCommands(PrintWriter out)
	{
		this.out=out;
		commands.put("zip", new ZipCommand());
		commands.put("dir", new DirCommand());
		commands.put("dic", new DicCommand());
		commands.put("unzip", new UnZipCommand());
		commands.put("size", new SizeCommand());
		commands.put("undic", new UnDicCommand());
		commands.put("huff", new HuffmanCommand());
		commands.put("unhuff", new UnHuffmanCommand());	
	}
	
	/**Selecting a command from the HashMap.
	 * @param CommandName - command name as string.
	 * @return Command - the appropriate command.
	 */
	public Command selectCommand(String commandName)
	{
		if(commands.containsKey(commandName))
			return commands.get(commandName);
		else
		{
			out.println("Command not found");
			out.flush();
			return null;
		}
	}
	
	/** Zipping file
	 * @author Tomer
	 *
	 */
	private class ZipCommand implements Command
	{
		@Override
		public void doCommand(String fileName) {
			
			BufferedReader reader;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
				StringBuffer buffer = new StringBuffer();
				String line;
				while ((line = reader.readLine()) != null)
				{
					buffer.append(line);
				}
				
				String str = buffer.toString();
				GZIPOutputStream zipOut=new GZIPOutputStream(new FileOutputStream(fileName+".zip"));
				zipOut.write(str.getBytes());
				zipOut.close();
				reader.close();
				out.println("File has been succesfully compressed.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}		
	}
	/** Unzipping file. checks prior to unzipping if file ends with ".zip" and if it does not exist in current path.
	 * 	overwriting was doing some problems. (maybe because of permissions and crap)
	 * @author Tomer
	 *
	 */
	private class UnZipCommand implements Command
	{
		@Override
		public void doCommand(String fileName) {
			
			try {

				if(!fileName.endsWith(".zip"))
				{
					out.println(fileName+ " is not a zip file");
					return;
				}
				
				File f = new File(fileNameToBeDecompressed(fileName));
				if(!f.exists() && !f.isDirectory())
				{
					GZIPInputStream zipIn=new GZIPInputStream(new FileInputStream(fileName));
					InputStreamReader reader = new InputStreamReader(zipIn);
					BufferedReader in = new BufferedReader(reader);
					//StringBuffer buffer = new StringBuffer();
					String line;
					PrintWriter out = new PrintWriter((new FileWriter(fileNameToBeDecompressed(fileName))));//without the ".zip"
					while ((line=in.readLine())!=null)
					{
						out.println(line);
					}
					in.close();
					out.close();
					out.println("File has been successfully decompressed.");
				}
				else
				{
					out.println("File has not been decompressed. decompressed file already exists ot it's a directory.");
				}
					
			} catch (FileNotFoundException e) {
				out.println("File not found.");
			} catch (IOException e) {
				out.println("Something went wrong. file has not been decompressed");
		}
		}		
	}
	/** calculates file size
	 * @author Tomer
	 *
	 */
	private class SizeCommand implements Command
	{
		@Override
		public void doCommand(String fileName) {
			File f= new File(fileName);
			out.println(f.length() + " bytes");
		}
	}
	/** Compresses with word dictionary
	 * @author Tomer
	 *
	 */
	private class DicCommand implements Command
	{
		@Override
		public void doCommand(String fileName) {
				try{
					WordDictionary dict=new WordDictionary();
					dict.compress(new FileInputStream(fileName),new FileOutputStream(fileName+ ".dic"));
					out.println("File has been successfully compressed");
				}catch(FileNotFoundException e) {
					out.println("File not found");
				}
		}		
	}
	/** decompressed with word dictionary. check if file ends with ".dic" and if it does not exist in current path.
	 * @author Tomer
	 *
	 */
	private class UnDicCommand implements Command
	{
		@Override
		public void doCommand(String fileName){
			if(!fileName.endsWith(".dic"))
			{
				out.println(fileName+ " is not a dic file");
				return;
			}
			try{
				WordDictionary dict=new WordDictionary();
				File f = new File(fileNameToBeDecompressed(fileName));
				if(!f.exists() && !f.isDirectory())
				{
					dict.decompress(new FileInputStream(fileName),new FileOutputStream(fileNameToBeDecompressed(fileName)));
					out.println("File has been successfully decompressed");
				}
				else
					out.println("File has not been decompressed. decompressed file already exists or it's a directory");
			}catch(FileNotFoundException e) {
				out.println("File not found");
			}
		}
	}
	/**Huffman compress to file
	 * @author Tomer
	 *
	 */
	private class HuffmanCommand implements Command
	{
		@Override
		public void doCommand(String fileName){
			HuffmanAlg huff=new HuffmanAlg();
			try {	
				huff.compress(new FileInputStream(fileName), new FileOutputStream(fileName+".huff"));
				out.println("File has been successfully compressed");
			} catch (FileNotFoundException e) {
				out.println("File not found");
			} 
		}
	}
	/**huffman decompress to file. check if file ends with ".huff".
	 * @author Tomer
	 *
	 */
	private class UnHuffmanCommand implements Command
	{
		@Override
		public void doCommand(String fileName)
		{
			HuffmanAlg huff=new HuffmanAlg();
			if(!fileName.endsWith(".huff"))
			{
				out.println(fileName+ " is not a huff file");
				return;
			}
			try {
				File f = new File(fileNameToBeDecompressed(fileName));
				if(!f.exists() && !f.isDirectory())
				{
					huff.decompress(new FileInputStream(fileName),new FileOutputStream(fileNameToBeDecompressed(fileName)));
					out.println("File has been succesfully decompressed");
				}
				else
				{
					out.println("File has not been decompressed. decompressed file already exists or it's a directory");
				}
			} catch (FileNotFoundException e) {
				out.println("File not found");
			}
		}
	}
	/** Shows files and directiories in path.
	 * @author Tomer
	 *
	 */
	private class DirCommand implements Command
	{

		@Override
		public void doCommand(String path) {
			File file = new File(path);
			
			if (file.isFile())
			{
				out.println(file);
			}
			else
			{
				for (File f : file.listFiles())
				{
					out.println(f);
				}
			}
		}
		
	}
	/** helper for decompress commands. for example if zip file I need to remove the .zip in the end... so it gives the new file name.
	 * @param fileName
	 * @return
	 */
	private String fileNameToBeDecompressed(String fileName)
	{
		String[] str=fileName.split("\\.");
		String newName="";
		for(int i=0;i<str.length-1;i++)
			newName+=str[i]+".";
		return newName.substring(0, newName.length()-1); //without the dot
	}
}
