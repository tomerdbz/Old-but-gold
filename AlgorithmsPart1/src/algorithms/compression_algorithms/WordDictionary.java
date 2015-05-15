package algorithms.compression_algorithms;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/** This class can compress and decompress data by the naive dictionary methods. not a very good compresser.
 * @author Tomer Cabouly
 *
 */
public class WordDictionary extends CommonCompresser {
	
	/** map between words and Indexes appearance.
	 * 
	 */
	HashMap<String, ArrayList<Integer>> map;
	public WordDictionary() {
		map= new  HashMap<String, ArrayList<Integer>>();
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (String word:map.keySet())
		{
			sb.append(word + ": ");
			ArrayList<Integer> indices = map.get(word);
			for (int index : indices)
			{
				sb.append(index + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}
		
		return sb.toString();
	}

	/** compressed data by word to Indexes appearance dictionary.
	 * the dictionary will be written to the given output stream.
	 * @param in - input stream from which data will be read (textual data but implementation of Compresser makes me work InputStream and OutputStream)
	 * @param out - Output stream to which the dictionary will be written.
	 */
	@Override
	public void compress(InputStream in,OutputStream out) {
		 HashMap<String, ArrayList<Integer>> dict = new  HashMap<String, ArrayList<Integer>>();
		Scanner scan=new Scanner(in);
		int i=0;
		while(scan.hasNext()){
			String word = scan.next();
			ArrayList<Integer> indices;
			
			if (dict.containsKey(word))
				indices = dict.get(word);
			else
				indices = new ArrayList<Integer>();
			indices.add(i++);	
			dict.put(word, indices); 
		}
		scan.close();
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(out);
			objOut.writeObject(dict);
			objOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	/** decompress by reading the dictionary.
	 * @param in - InputStream from which dictionary will be read.
	 * @param out - OutputStream to which data will be written.
	 */
	@Override
	public void decompress(InputStream in,OutputStream out) {
		ObjectInputStream objIn;
		try {
			objIn = new ObjectInputStream(in);
			int size=0;
			StringBuilder s=new StringBuilder("");
			 @SuppressWarnings("unchecked")
			HashMap<String, ArrayList<Integer>> dict=(HashMap<String, ArrayList<Integer>>)objIn.readObject();
			 HashMap<ArrayList<Integer>,String> map=new HashMap<ArrayList<Integer>,String>();
			 for(String str : dict.keySet())
				 {
					 map.put(dict.get(str), str);
				 }
			 for(ArrayList<Integer> indexes: dict.values())
			 {
				 size+=indexes.size();
			 }		
			 String [] collection=new String[size];

			 for(ArrayList<Integer> indices: map.keySet())
			 {
				 for(int j=0;j<indices.size();j++)
				 {
					 collection[indices.get(j)]=map.get(indices);
				 } 
			 }
			 for(int i=0;i<size;i++)
			 {
				 if(i!=size-1)
					 s.append(collection[i]+ " ");
				 else
					 s.append(collection[i]);
			 }
			OutputStreamWriter w=new OutputStreamWriter(out);
			w.write(s.toString());
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}