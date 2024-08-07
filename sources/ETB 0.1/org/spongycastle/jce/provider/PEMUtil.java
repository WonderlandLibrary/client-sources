package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.util.encoders.Base64;



public class PEMUtil
{
  private final String _header1;
  private final String _header2;
  private final String _footer1;
  private final String _footer2;
  
  PEMUtil(String type)
  {
    _header1 = ("-----BEGIN " + type + "-----");
    _header2 = ("-----BEGIN X509 " + type + "-----");
    _footer1 = ("-----END " + type + "-----");
    _footer2 = ("-----END X509 " + type + "-----");
  }
  


  private String readLine(InputStream in)
    throws IOException
  {
    StringBuffer l = new StringBuffer();
    int c;
    do
    {
      while (((c = in.read()) != 13) && (c != 10) && (c >= 0))
      {
        if (c != 13)
        {



          l.append((char)c);
        }
      }
    } while ((c >= 0) && (l.length() == 0));
    
    if (c < 0)
    {
      return null;
    }
    
    return l.toString();
  }
  


  ASN1Sequence readPEMObject(InputStream in)
    throws IOException
  {
    StringBuffer pemBuf = new StringBuffer();
    String line;
    while ((line = readLine(in)) != null)
    {
      if (!line.startsWith(_header1)) { if (line.startsWith(_header2)) {
          break;
        }
      }
    }
    
    while ((line = readLine(in)) != null)
    {
      if ((line.startsWith(_footer1)) || (line.startsWith(_footer2))) {
        break;
      }
      

      pemBuf.append(line);
    }
    
    if (pemBuf.length() != 0)
    {
      ASN1Primitive o = new ASN1InputStream(Base64.decode(pemBuf.toString())).readObject();
      if (!(o instanceof ASN1Sequence))
      {
        throw new IOException("malformed PEM data encountered");
      }
      
      return (ASN1Sequence)o;
    }
    
    return null;
  }
}
