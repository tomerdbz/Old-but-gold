package algorithms.compression_algorithms;

import java.io.Serializable;
import java.util.BitSet;

/**
 * This class is the original BitSet fixed - wrapped with a size field.
 * the problem I had with BitSet is that it saves the logical size of the set, means for example for the binary string: 0011 the size will be 2.
 * here I added the actual size of the set.
 * @author Tomer Cabouly
 *
 */
public class FixedBitSet extends BitSet implements Serializable {
	
	/**
	 *  A serial code for the Serializable interface
	 */
	private static final long serialVersionUID = -8827211583236376346L;
	private int size;
	@Override
	public void set(int bitIndex) {
		if(bitIndex >=size)
			size=bitIndex+1;
		super.set(bitIndex);
	}
	@Override
	public void set(int bitIndex, boolean value) {
		if(bitIndex >=size)
			size=bitIndex+1;
		super.set(bitIndex, value);
	}
	public FixedBitSet(BitSet set) {
		size=0;
		this.or(set);
	}
	public FixedBitSet(BitSet set, int size) {
		this.size=size;
		this.or(set);
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)& (((FixedBitSet)obj).getSize()==this.getSize());
	}
	public FixedBitSet()
	{
		size=0;
	}
	@Override
	public Object clone() {
		
		return super.clone();
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

}
