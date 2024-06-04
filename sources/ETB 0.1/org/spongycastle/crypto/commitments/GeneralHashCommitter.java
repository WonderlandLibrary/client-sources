package org.spongycastle.crypto.commitments;

import java.security.SecureRandom;
import org.spongycastle.crypto.Commitment;
import org.spongycastle.crypto.Committer;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.ExtendedDigest;
import org.spongycastle.util.Arrays;
















public class GeneralHashCommitter
  implements Committer
{
  private final Digest digest;
  private final int byteLength;
  private final SecureRandom random;
  
  public GeneralHashCommitter(ExtendedDigest digest, SecureRandom random)
  {
    this.digest = digest;
    byteLength = digest.getByteLength();
    this.random = random;
  }
  






  public Commitment commit(byte[] message)
  {
    if (message.length > byteLength / 2)
    {
      throw new DataLengthException("Message to be committed to too large for digest.");
    }
    
    byte[] w = new byte[byteLength - message.length];
    
    random.nextBytes(w);
    
    return new Commitment(w, calculateCommitment(w, message));
  }
  







  public boolean isRevealed(Commitment commitment, byte[] message)
  {
    if (message.length + commitment.getSecret().length != byteLength)
    {
      throw new DataLengthException("Message and witness secret lengths do not match.");
    }
    
    byte[] calcCommitment = calculateCommitment(commitment.getSecret(), message);
    
    return Arrays.constantTimeAreEqual(commitment.getCommitment(), calcCommitment);
  }
  
  private byte[] calculateCommitment(byte[] w, byte[] message)
  {
    byte[] commitment = new byte[digest.getDigestSize()];
    
    digest.update(w, 0, w.length);
    digest.update(message, 0, message.length);
    
    digest.update((byte)(message.length >>> 8));
    digest.update((byte)message.length);
    
    digest.doFinal(commitment, 0);
    
    return commitment;
  }
}
