package org.lwjgl.opencl.api;

public abstract interface Filter<T>
{
  public abstract boolean accept(T paramT);
}
