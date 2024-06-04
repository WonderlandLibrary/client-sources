package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;




public class BERGenerator
  extends ASN1Generator
{
  private boolean _tagged = false;
  
  private boolean _isExplicit;
  private int _tagNo;
  
  protected BERGenerator(OutputStream out)
  {
    super(out);
  }
  



  protected BERGenerator(OutputStream out, int tagNo, boolean isExplicit)
  {
    super(out);
    
    _tagged = true;
    _isExplicit = isExplicit;
    _tagNo = tagNo;
  }
  
  public OutputStream getRawOutputStream()
  {
    return _out;
  }
  

  private void writeHdr(int tag)
    throws IOException
  {
    _out.write(tag);
    _out.write(128);
  }
  

  protected void writeBERHeader(int tag)
    throws IOException
  {
    if (_tagged)
    {
      int tagNum = _tagNo | 0x80;
      
      if (_isExplicit)
      {
        writeHdr(tagNum | 0x20);
        writeHdr(tag);


      }
      else if ((tag & 0x20) != 0)
      {
        writeHdr(tagNum | 0x20);
      }
      else
      {
        writeHdr(tagNum);
      }
      
    }
    else
    {
      writeHdr(tag);
    }
  }
  
  protected void writeBEREnd()
    throws IOException
  {
    _out.write(0);
    _out.write(0);
    
    if ((_tagged) && (_isExplicit))
    {
      _out.write(0);
      _out.write(0);
    }
  }
}
