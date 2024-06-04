package org.spongycastle.x509;

import java.io.InputStream;
import java.util.Collection;
import org.spongycastle.x509.util.StreamParsingException;

public abstract class X509StreamParserSpi
{
  public X509StreamParserSpi() {}
  
  public abstract void engineInit(InputStream paramInputStream);
  
  public abstract Object engineRead()
    throws StreamParsingException;
  
  public abstract Collection engineReadAll()
    throws StreamParsingException;
}
