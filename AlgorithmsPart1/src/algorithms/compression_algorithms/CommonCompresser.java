package algorithms.compression_algorithms;

import java.io.InputStream;
import java.io.OutputStream;

/** This class should contain what's common to all compressers. to be honest there's not much left to have here.
 * if Eli asks - we do it. maybe in the future it will come in handy.
 * @author Tomer Cabouly
 *
 */
public abstract class CommonCompresser implements Compresser{
	public abstract void compress(InputStream in,OutputStream dest);
	public abstract void decompress(InputStream in,OutputStream dest);
}
