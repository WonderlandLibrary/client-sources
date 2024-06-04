package org.spongycastle.jce.provider;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;





























class OldPKCS12ParametersGenerator
  extends PBEParametersGenerator
{
  public static final int KEY_MATERIAL = 1;
  public static final int IV_MATERIAL = 2;
  public static final int MAC_MATERIAL = 3;
  private Digest digest;
  private int u;
  private int v;
  
  public OldPKCS12ParametersGenerator(Digest digest)
  {
    this.digest = digest;
    if ((digest instanceof MD5Digest))
    {
      u = 16;
      v = 64;
    }
    else if ((digest instanceof SHA1Digest))
    {
      u = 20;
      v = 64;
    }
    else if ((digest instanceof RIPEMD160Digest))
    {
      u = 20;
      v = 64;
    }
    else
    {
      throw new IllegalArgumentException("Digest " + digest.getAlgorithmName() + " unsupported");
    }
  }
  








  private void adjust(byte[] a, int aOff, byte[] b)
  {
    int x = (b[(b.length - 1)] & 0xFF) + (a[(aOff + b.length - 1)] & 0xFF) + 1;
    
    a[(aOff + b.length - 1)] = ((byte)x);
    x >>>= 8;
    
    for (int i = b.length - 2; i >= 0; i--)
    {
      x += (b[i] & 0xFF) + (a[(aOff + i)] & 0xFF);
      a[(aOff + i)] = ((byte)x);
      x >>>= 8;
    }
  }
  





  private byte[] generateDerivedKey(int idByte, int n)
  {
    byte[] D = new byte[v];
    byte[] dKey = new byte[n];
    
    for (int i = 0; i != D.length; i++)
    {
      D[i] = ((byte)idByte);
    }
    
    byte[] S;
    
    if ((salt != null) && (salt.length != 0))
    {
      byte[] S = new byte[v * ((salt.length + v - 1) / v)];
      
      for (int i = 0; i != S.length; i++)
      {
        S[i] = salt[(i % salt.length)];
      }
    }
    else
    {
      S = new byte[0];
    }
    
    byte[] P;
    
    if ((password != null) && (password.length != 0))
    {
      byte[] P = new byte[v * ((password.length + v - 1) / v)];
      
      for (int i = 0; i != P.length; i++)
      {
        P[i] = password[(i % password.length)];
      }
    }
    else
    {
      P = new byte[0];
    }
    
    byte[] I = new byte[S.length + P.length];
    
    System.arraycopy(S, 0, I, 0, S.length);
    System.arraycopy(P, 0, I, S.length, P.length);
    
    byte[] B = new byte[v];
    int c = (n + u - 1) / u;
    
    for (int i = 1; i <= c; i++)
    {
      byte[] A = new byte[u];
      
      digest.update(D, 0, D.length);
      digest.update(I, 0, I.length);
      digest.doFinal(A, 0);
      for (int j = 1; j != iterationCount; j++)
      {
        digest.update(A, 0, A.length);
        digest.doFinal(A, 0);
      }
      
      for (int j = 0; j != B.length; j++)
      {
        B[i] = A[(j % A.length)];
      }
      
      for (int j = 0; j != I.length / v; j++)
      {
        adjust(I, j * v, B);
      }
      
      if (i == c)
      {
        System.arraycopy(A, 0, dKey, (i - 1) * u, dKey.length - (i - 1) * u);
      }
      else
      {
        System.arraycopy(A, 0, dKey, (i - 1) * u, A.length);
      }
    }
    
    return dKey;
  }
  








  public CipherParameters generateDerivedParameters(int keySize)
  {
    keySize /= 8;
    
    byte[] dKey = generateDerivedKey(1, keySize);
    
    return new KeyParameter(dKey, 0, keySize);
  }
  











  public CipherParameters generateDerivedParameters(int keySize, int ivSize)
  {
    keySize /= 8;
    ivSize /= 8;
    
    byte[] dKey = generateDerivedKey(1, keySize);
    
    byte[] iv = generateDerivedKey(2, ivSize);
    
    return new ParametersWithIV(new KeyParameter(dKey, 0, keySize), iv, 0, ivSize);
  }
  








  public CipherParameters generateDerivedMacParameters(int keySize)
  {
    keySize /= 8;
    
    byte[] dKey = generateDerivedKey(3, keySize);
    
    return new KeyParameter(dKey, 0, keySize);
  }
}
