package algorithms.compression_algorithms;

import java.io.Serializable;


/**
 *  This class is a helper in the HuffmanAlg class - it saves the char with its count in the text and its binary representation - as FixedBitSet.
 * @author Tomer Cabouly
 *
 */
public class Hchar implements Serializable{
	
	/**
	 * A serial code for the Serializable interface
	 */
	private static final long serialVersionUID = 266069052803364576L;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public Hchar getLeft() {
		return left;
	}
	public void setLeft(Hchar left) {
		this.left = left;
	}
	public Hchar getRight() {
		return right;
	}
	public void setRight(Hchar right) {
		this.right = right;
	}
	public FixedBitSet getBinRep() {
		return binRep;
	}
	public void setBinRep(FixedBitSet binRep) {
		this.binRep = binRep;
	}

	private int count;
	private String character;
	private Hchar left=null,right=null;
	// an inefficient representation, change it to real bits!
	private FixedBitSet binRep;
	public Hchar()
	{
		binRep=new FixedBitSet();
	}
	@Override
	public int hashCode()
	{
		return character.hashCode(); 
	}
	@Override
	public String toString() {
		String str="";
		str+=character+":";
		for(int i=0;i<binRep.getSize();i++)
		{
			if(binRep.get(i))
				str+="1";
			else
				str+="0";
		}
		return str;
	}
	
}
