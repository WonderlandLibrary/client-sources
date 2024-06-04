package net.java.games.input;

import java.io.IOException;

abstract interface LinuxDevice
{
  public abstract void close()
    throws IOException;
}
