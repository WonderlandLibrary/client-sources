package org.spongycastle.x509.util;

import java.util.Collection;

public abstract interface StreamParser
{
  public abstract Object read()
    throws StreamParsingException;
  
  public abstract Collection readAll()
    throws StreamParsingException;
}
