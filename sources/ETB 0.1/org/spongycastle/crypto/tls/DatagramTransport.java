package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface DatagramTransport
{
  public abstract int getReceiveLimit()
    throws IOException;
  
  public abstract int getSendLimit()
    throws IOException;
  
  public abstract int receive(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    throws IOException;
  
  public abstract void send(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract void close()
    throws IOException;
}
