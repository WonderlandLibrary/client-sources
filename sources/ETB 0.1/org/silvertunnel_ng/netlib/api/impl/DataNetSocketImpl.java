package org.silvertunnel_ng.netlib.api.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


















public class DataNetSocketImpl
  implements DataNetSocket
{
  private final DataInputStream is;
  private final DataOutputStream os;
  
  public DataNetSocketImpl(InputStream is, OutputStream os)
  {
    this.is = ((is instanceof DataInputStream) ? (DataInputStream)is : new DataInputStream(is));
    this.os = ((os instanceof DataOutputStream) ? (DataOutputStream)os : new DataOutputStream(os));
  }
  
  public DataInputStream getDataInputStream()
    throws IOException
  {
    return is;
  }
  
  public DataOutputStream getDataOutputStream()
    throws IOException
  {
    return os;
  }
  
  public DataInputStream getInputStream()
    throws IOException
  {
    return is;
  }
  
  public DataOutputStream getOutputStream()
    throws IOException
  {
    return os;
  }
  
  public void close()
    throws IOException
  {
    is.close();
    os.close();
  }
}
