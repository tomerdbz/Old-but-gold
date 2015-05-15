package algorithms.compression_algorithms;

import java.io.InputStream;
import java.io.OutputStream;

/** This interface defines what each Compresser has to do. compress and decompress data.
 * @author Tomer Cabouly
 *
 */
public interface Compresser {
	void compress(InputStream in,OutputStream dest);
	void decompress(InputStream in,OutputStream dest);
}
