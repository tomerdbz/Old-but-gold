package algorithms.compression_algorithms;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class features the Huffman Compresser.
 * @author Tomer Cabouly
 *
 */
public class HuffmanAlg extends CommonCompresser{
	private HuffmanDictionary countAppearance;
	private PriorityQueue<Hchar> pq;
	
	/**
	 * @param input - text
	 * @return HuffmanDictionary - a HashMap mapping between characters and Hchars (Hchar contains Binary Representation)
	 */
	public HuffmanDictionary huffman(String input)
	{
		countAppearances(input);
		pq.addAll(countAppearance.values());
		
		while(pq.size()>1){
			Hchar hc0=pq.poll();
			Hchar hc1=pq.poll();
			Hchar hc2=new Hchar();
			hc2.setCount(hc0.getCount()+hc1.getCount());
			hc2.setCharacter(hc0.getCharacter()+hc1.getCharacter());
			hc2.setLeft(hc0);
			hc2.setRight(hc1);
			pq.add(hc2);
		}
		Hchar top=pq.peek();
		DFSbinRep(top,new FixedBitSet(),0);
		//System.out.println(countAppearance);
		return countAppearance;
		
	}
	public HuffmanAlg()
	{
		this.pq=new PriorityQueue<Hchar>(
				new Comparator<Hchar>() {
				@Override
				public int compare(Hchar o1, Hchar o2) {
				return o1.getCount()-o2.getCount();
				}
				});
	}
	/** a helper for huffman method. creates Huffman Dictionary that doesn't contain Binary representations.
	 * @param input
	 */
	private void countAppearances(String input)
	{
		// count appearances of characters
		countAppearance=new HuffmanDictionary();
		for(char c: input.toCharArray()){
			Hchar hc=countAppearance.get(c);
			if(hc==null){
				hc=new Hchar();
				hc.setCharacter(""+c);
				hc.setCount(0);
				countAppearance.put(c, hc);
			}
			hc.setCount(hc.getCount()+1);
		}
	}

	/** Recursive method that gives actual binReps. it does that by the classic huffman thinking.
	 * think of a binary tree that gives its children binReps.
	 * @param node - The current node in the tree. in the beginning the root
	 * @param bin - binRep is been made on each step. method is recursive - thus giving sons binReps.
	 * @param size - the distance from the root
	 */
	private void DFSbinRep(Hchar node,FixedBitSet bin,int size) {
		
		node.setBinRep(copyBitSets(bin));
	
		if(node.getLeft()!=null){
			FixedBitSet tempSet=copyBitSets(node.getBinRep());
			tempSet.set(size,false);
			DFSbinRep(node.getLeft(), tempSet,size+1);//node.binRep+"0");
		}
		if(node.getRight()!=null){
			FixedBitSet tempSet=copyBitSets(node.getBinRep());
			tempSet.set(size,true);
			DFSbinRep(node.getRight(), tempSet,size+1);//node.binRep+"1");
		}
			
		}
	  /**Classic BitSet clone. but also puts the correct size of the BitSet. BitSets in this neighborhood are fixed!
	 * @param set
	 * @return cloned FixedBitSet
	 */
	private FixedBitSet copyBitSets(FixedBitSet set)
	  {
		  BitSet cl=(BitSet)set.clone();
		  FixedBitSet fSet=new FixedBitSet(cl);
		  fSet.setSize(set.getSize());
		  return fSet;
	  }
	/**  This function takes data from a given InputStream and writes the compressed data to a given OutputStream
	 * @param in - an InputStream from which info will be read
	 * @param out - an OutputStream to which info will be written.
	 */
	@Override
	public void compress(InputStream in, OutputStream out) {//out is where to write
		BufferedReader bufIn=new BufferedReader(new InputStreamReader(in));
		StringBuilder str=new StringBuilder();
		int temp;
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(out);
			char [] buffer=new char[1024];
			while((temp=bufIn.read(buffer))!=-1) //while there's data to read
			{
				for(int i=0;i<temp;i++)
					str.append(buffer[i]);
			}
			HuffmanDictionary dict=huffman(str.toString());//gets the dictionary
			BitSet bs=new BitSet();
			int i=0,bitIndex=0;
			while(i < str.length())//constructs the BitSet based on the string and dict
			{
				Hchar hchar=dict.get(str.charAt(i));
				for(int j=0;j<hchar.getBinRep().getSize();j++)
					bs.set(bitIndex++,hchar.getBinRep().get(j));
				i++;
			}
			bs.set(bitIndex);//know the end - to know the size I added 1 - the size will be length-1
			objOut.writeObject(dict.toString());
			objOut.writeObject(bs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  This function takes compressed data from a given InputStream and writes the data to a given OutputStream.
	 * 	Note that it will work only if the data was compressed by the compress method in this class.
	 * @param in - an InputStream from which info will be read
	 * @param out - an OutputStream to which info will be written.
	 */
	@Override
	public void decompress(InputStream in,OutputStream out) {		
		try {
		ObjectInputStream objIn=new ObjectInputStream(in);
		String textDict;
		
			textDict = (String)objIn.readObject();
			BitSet set1=(BitSet)objIn.readObject();
			FixedBitSet set=new FixedBitSet(set1.get(0, set1.length()-1),set1.length()-1); 
			String[] letters=textDict.split("\n"); //the way I wrote Dictionary - splitting with "\n" will give the dictionary values like:"x:1010"
			HashMap<FixedBitSet,String> map=lettersMapping(letters); //map letters. took consideration of case:"\n:10101"
			int offset=0;
			int len=1;
			String result="";
			while(offset!=set.getSize()) //decompression! finding BitSet-chars connection in the BitSet and uncover its meaning
			{
				FixedBitSet offSet=new FixedBitSet(set.get(offset, offset+len),len);
				if(map.containsKey(offSet))
				{
					result+=map.get(offSet);
					offset+=len;
					len=1;
				}
				else
					len++;
			}
			BufferedWriter w=new BufferedWriter(new OutputStreamWriter(out));//write and Chao ;)
			w.write(result);
			w.flush();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	/** a helper for decompress. maps between BinRep and letters.
	 * @param letters - A string like: "p:1010\nk:01010\ns:1010111......"
	 * @return
	 */
	private HashMap<FixedBitSet,String> lettersMapping(String[] letters)
	{
		HashMap<FixedBitSet,String> dict=new HashMap<FixedBitSet,String>();
		String prev="1";//had to initialize with something different than \n
		for(String letter: letters)
		{
			if(letter.equals(""))
			{
				prev=letter;
				continue;
			}
			
			String[] content=letter.split(":");
			FixedBitSet set=new FixedBitSet();
			for(int i=0;i<content[1].length();i++)
			{
				if(content[1].charAt(i)=='1')
					set.set(i, true);
				else
					set.set(i,false);
			}
			set.setSize(content[1].length());
			if(prev.equals(""))
			{
				dict.put(set, "\n");
				prev="1";
			}
			else
				dict.put(set, content[0]);
		}
		return dict;
	}
}
