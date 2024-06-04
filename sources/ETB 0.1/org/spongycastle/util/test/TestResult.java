package org.spongycastle.util.test;

public abstract interface TestResult
{
  public abstract boolean isSuccessful();
  
  public abstract Throwable getException();
  
  public abstract String toString();
}
