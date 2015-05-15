package algorithms.compression_algorithms;

import java.io.Serializable;
import java.util.HashMap;

/** A dictionary mapping between characters and Hchars. good only for the HuffmanAlg class!
 * @author Tomer Cabouly
 *
 */
public class HuffmanDictionary extends  HashMap<Character,Hchar> implements Serializable {
	
	
	

	/**
	 * A serial code for the Serializable interface
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		String str;
		str = "";
		for(Hchar hc : this.values()){
			str +=(hc.toString()+"\n");
		}
		return str.substring(0,str.length()-1);
	}


	



	
	
}
