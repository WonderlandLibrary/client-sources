package it.unimi.dsi.fastutil.io;

import java.io.IOException;

public interface MeasurableStream {
  long length() throws IOException;
  
  long position() throws IOException;
}


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\io\MeasurableStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */