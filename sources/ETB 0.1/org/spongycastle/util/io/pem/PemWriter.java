package org.spongycastle.util.io.pem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Base64;





public class PemWriter
  extends BufferedWriter
{
  private static final int LINE_LENGTH = 64;
  private final int nlLength;
  private char[] buf = new char[64];
  





  public PemWriter(Writer out)
  {
    super(out);
    
    String nl = Strings.lineSeparator();
    if (nl != null)
    {
      nlLength = nl.length();
    }
    else
    {
      nlLength = 2;
    }
  }
  








  public int getOutputSize(PemObject obj)
  {
    int size = 2 * (obj.getType().length() + 10 + nlLength) + 6 + 4;
    
    if (!obj.getHeaders().isEmpty())
    {
      for (Iterator it = obj.getHeaders().iterator(); it.hasNext();)
      {
        PemHeader hdr = (PemHeader)it.next();
        
        size += hdr.getName().length() + ": ".length() + hdr.getValue().length() + nlLength;
      }
      
      size += nlLength;
    }
    

    int dataLen = (obj.getContent().length + 2) / 3 * 4;
    
    size += dataLen + (dataLen + 64 - 1) / 64 * nlLength;
    
    return size;
  }
  
  public void writeObject(PemObjectGenerator objGen)
    throws IOException
  {
    PemObject obj = objGen.generate();
    
    writePreEncapsulationBoundary(obj.getType());
    
    if (!obj.getHeaders().isEmpty())
    {
      for (Iterator it = obj.getHeaders().iterator(); it.hasNext();)
      {
        PemHeader hdr = (PemHeader)it.next();
        
        write(hdr.getName());
        write(": ");
        write(hdr.getValue());
        newLine();
      }
      
      newLine();
    }
    
    writeEncoded(obj.getContent());
    writePostEncapsulationBoundary(obj.getType());
  }
  
  private void writeEncoded(byte[] bytes)
    throws IOException
  {
    bytes = Base64.encode(bytes);
    
    for (int i = 0; i < bytes.length; i += buf.length)
    {
      int index = 0;
      
      while (index != buf.length)
      {
        if (i + index >= bytes.length) {
          break;
        }
        
        buf[index] = ((char)bytes[(i + index)]);
        index++;
      }
      write(buf, 0, index);
      newLine();
    }
  }
  

  private void writePreEncapsulationBoundary(String type)
    throws IOException
  {
    write("-----BEGIN " + type + "-----");
    newLine();
  }
  

  private void writePostEncapsulationBoundary(String type)
    throws IOException
  {
    write("-----END " + type + "-----");
    newLine();
  }
}
