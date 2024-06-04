package org.spongycastle.pqc.jcajce.interfaces;

public abstract interface XMSSMTKey
{
  public abstract int getHeight();
  
  public abstract int getLayers();
  
  public abstract String getTreeDigest();
}
