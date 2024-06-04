package org.spongycastle.pqc.jcajce.interfaces;

public abstract interface XMSSKey
{
  public abstract int getHeight();
  
  public abstract String getTreeDigest();
}
