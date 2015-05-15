package algorithms.compression_algorithms;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.io.output.WriterOutputStream;

/** BONUS - This class writes compressed huffman data to a writer.
 * 	implements Writer as part of the decorator pattern.
 * @author Tomer Cabouly
 *
 */
public class HuffmanWriter extends Writer {
	private Writer w;
	public HuffmanWriter(Writer w)
	{
		this.w=w;
	}
	@Override
	public void close() throws IOException {
		w.close();
	}

	@Override
	public void flush() throws IOException {
		w.flush();
	}
	/** This class writes data to the writer contained in this class. it gets buffer, compresses it and writes it
	 * @param cbuf - the character buffer
	 * @param off - offset to read from
	 * @param len - num of bytes to read
	 * 
	 */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException { 
		String buf=new String(cbuf,off,len);
		HuffmanAlg huff=new HuffmanAlg();
		ReaderInputStream stream = new ReaderInputStream(new StringReader(buf));
		WriterOutputStream out=new WriterOutputStream(w);
		huff.compress(stream,out);
		out.flush();		
	}
}