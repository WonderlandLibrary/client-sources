package org.spongycastle.jce.interfaces;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

public abstract interface IESKey
  extends Key
{
  public abstract PublicKey getPublic();
  
  public abstract PrivateKey getPrivate();
}
