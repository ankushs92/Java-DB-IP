package in.ankushs.dbip.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;
/**
 * 
 * Utilities for handling Gzipped files
 * @author Ankush Sharma
 */
public class GzipUtils {
	private GzipUtils(){}
	/**
	 * Checks if the file is gzipped.
	 * @param file The file to be tested
	 * @throws IOException if file does not exist or isn't gzipped.
	 * @return true if  {@code file} is gzipped, and false otherwise
	 */
	public static boolean isGzipped(final File file) throws IOException{
		 int magic = 0;
		 final RandomAccessFile raf = new RandomAccessFile(file, "r");
		 magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);
		 raf.close();
	     return magic == GZIPInputStream.GZIP_MAGIC;
	}
}
