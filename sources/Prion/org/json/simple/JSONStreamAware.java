package org.json.simple;

import java.io.IOException;
import java.io.Writer;

public abstract interface JSONStreamAware
{
  public abstract void writeJSONString(Writer paramWriter)
    throws IOException;
}
