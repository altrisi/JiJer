package altrisi.dev.jijer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public class NoThrowBufferedReader extends BufferedReader {
	public NoThrowBufferedReader(Reader in) {
		super(in);
	}
	
	@Override
	public String readLine() {
		try {
			return super.readLine();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
