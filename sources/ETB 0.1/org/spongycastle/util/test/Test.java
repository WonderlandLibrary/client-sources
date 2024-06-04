package org.spongycastle.util.test;

public abstract interface Test
{
  public abstract String getName();
  
  public abstract TestResult perform();
}
