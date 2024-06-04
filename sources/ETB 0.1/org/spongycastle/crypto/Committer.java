package org.spongycastle.crypto;

public abstract interface Committer
{
  public abstract Commitment commit(byte[] paramArrayOfByte);
  
  public abstract boolean isRevealed(Commitment paramCommitment, byte[] paramArrayOfByte);
}
