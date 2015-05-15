package algorithms.compression_algorithms;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.io.output.WriterOutputStream;

/**	BONUS - This class reads Compressed data from a Reader and returns the data.
 *	implements Reader as part of the decorator pattern.
 * @author Tomer Cabouly
 *
 */
public class HuffmanReader extends Reader {
	/**	Contains Reader as part of the decorator pattern.
	 * 
	 */
	private Reader r;
	/**Contains a queue implemented by LinkedList. its role is to contain Strings we can't read due to memory restrictions. more info at read()
	 * 
	 */
	private LinkedList<String> queue;
	public HuffmanReader(Reader r) {
		this.r=r;
		queue=new LinkedList<String>();
	}
	@Override
	public void close() throws IOException {
		r.close();
	}

	/** The read function. it's not trivial. it gets compressed data from a buffer and needs to put in this buffer uncompressed data.
	 * the problem arise when considering the buffer's length is according to the compressed data size - when decompressing I have more data than the buffer can contain.
	 * with that in mind, I give the data I can - and what I cannot give - I save. in future calls this data will be returned.
	 * @param cbuf - Character buffer from which compressed info will be read and decompressed info will be written.
	 * @param off - offset to read and put chars from.
	 * @param len - length you can read and write from and to cbuf.
	 * @return Integer which says how much chars were actually read.
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if(!queue.isEmpty())
		{
			String now=queue.removeFirst();
			char[] buf=now.toCharArray();
			for(int i=0;i<now.length();i++)
				cbuf[off+i]=buf[i];
			off=off+now.length();
		}
		if(r.read(cbuf, off, len)==-1)
			return -1;
		HuffmanAlg huff=new HuffmanAlg();
		String str=new String(cbuf,off,len);
		StringReader sr=new StringReader(str);
		ReaderInputStream in=new ReaderInputStream(sr);
		CharArrayWriter w=new CharArrayWriter();
		WriterOutputStream out=new WriterOutputStream(w);
		huff.decompress(in, out);
		char[] copy=w.toCharArray();
		if(copy.length>len)
		{
			String later=new String(copy,len,copy.length-len);
			queue.add(later);
		}
		for(int i=0;i<copy.length;i++)
		{
			cbuf[off+i]=copy[i];
		}
		len=copy.length;
		return len;
	}

}
