package net.java.games.input;

import java.io.IOException;









































final class OSXHIDDeviceIterator
{
  private final long iterator_address;
  
  public OSXHIDDeviceIterator()
    throws IOException
  {
    iterator_address = nCreateIterator();
  }
  
  private static final native long nCreateIterator();
  
  public final void close() { nReleaseIterator(iterator_address); }
  
  private static final native void nReleaseIterator(long paramLong);
  
  public final OSXHIDDevice next() throws IOException {
    return nNext(iterator_address);
  }
  
  private static final native OSXHIDDevice nNext(long paramLong)
    throws IOException;
}
