package org.spongycastle.pqc.crypto.ntru;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;








public class NTRUEncryptionKeyGenerationParameters
  extends KeyGenerationParameters
  implements Cloneable
{
  public static final NTRUEncryptionKeyGenerationParameters EES1087EP2 = new NTRUEncryptionKeyGenerationParameters(1087, 2048, 120, 120, 256, 13, 25, 14, true, new byte[] { 0, 6, 3 }, true, false, new SHA512Digest());
  



  public static final NTRUEncryptionKeyGenerationParameters EES1171EP1 = new NTRUEncryptionKeyGenerationParameters(1171, 2048, 106, 106, 256, 13, 20, 15, true, new byte[] { 0, 6, 4 }, true, false, new SHA512Digest());
  



  public static final NTRUEncryptionKeyGenerationParameters EES1499EP1 = new NTRUEncryptionKeyGenerationParameters(1499, 2048, 79, 79, 256, 13, 17, 19, true, new byte[] { 0, 6, 5 }, true, false, new SHA512Digest());
  



  public static final NTRUEncryptionKeyGenerationParameters APR2011_439 = new NTRUEncryptionKeyGenerationParameters(439, 2048, 146, 130, 128, 9, 32, 9, true, new byte[] { 0, 7, 101 }, true, false, new SHA256Digest());
  



  public static final NTRUEncryptionKeyGenerationParameters APR2011_439_FAST = new NTRUEncryptionKeyGenerationParameters(439, 2048, 9, 8, 5, 130, 128, 9, 32, 9, true, new byte[] { 0, 7, 101 }, true, true, new SHA256Digest());
  



  public static final NTRUEncryptionKeyGenerationParameters APR2011_743 = new NTRUEncryptionKeyGenerationParameters(743, 2048, 248, 220, 256, 10, 27, 14, true, new byte[] { 0, 7, 105 }, false, false, new SHA512Digest());
  



  public static final NTRUEncryptionKeyGenerationParameters APR2011_743_FAST = new NTRUEncryptionKeyGenerationParameters(743, 2048, 11, 11, 15, 220, 256, 10, 27, 14, true, new byte[] { 0, 7, 105 }, false, true, new SHA512Digest());
  
  public int N;
  
  public int q;
  
  public int df;
  
  public int df1;
  
  public int df2;
  
  public int df3;
  
  public int dr;
  
  public int dr1;
  
  public int dr2;
  
  public int dr3;
  
  public int dg;
  
  int llen;
  
  public int maxMsgLenBytes;
  public int db;
  public int bufferLenBits;
  int bufferLenTrits;
  public int dm0;
  public int pkLen;
  public int c;
  public int minCallsR;
  public int minCallsMask;
  public boolean hashSeed;
  public byte[] oid;
  public boolean sparse;
  public boolean fastFp;
  public int polyType;
  public Digest hashAlg;
  
  public NTRUEncryptionKeyGenerationParameters(int N, int q, int df, int dm0, int db, int c, int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid, boolean sparse, boolean fastFp, Digest hashAlg)
  {
    super(new SecureRandom(), db);
    this.N = N;
    this.q = q;
    this.df = df;
    this.db = db;
    this.dm0 = dm0;
    this.c = c;
    this.minCallsR = minCallsR;
    this.minCallsMask = minCallsMask;
    this.hashSeed = hashSeed;
    this.oid = oid;
    this.sparse = sparse;
    this.fastFp = fastFp;
    polyType = 0;
    this.hashAlg = hashAlg;
    init();
  }
  



















  public NTRUEncryptionKeyGenerationParameters(int N, int q, int df1, int df2, int df3, int dm0, int db, int c, int minCallsR, int minCallsMask, boolean hashSeed, byte[] oid, boolean sparse, boolean fastFp, Digest hashAlg)
  {
    super(new SecureRandom(), db);
    
    this.N = N;
    this.q = q;
    this.df1 = df1;
    this.df2 = df2;
    this.df3 = df3;
    this.db = db;
    this.dm0 = dm0;
    this.c = c;
    this.minCallsR = minCallsR;
    this.minCallsMask = minCallsMask;
    this.hashSeed = hashSeed;
    this.oid = oid;
    this.sparse = sparse;
    this.fastFp = fastFp;
    polyType = 1;
    this.hashAlg = hashAlg;
    init();
  }
  
  private void init()
  {
    dr = df;
    dr1 = df1;
    dr2 = df2;
    dr3 = df3;
    dg = (N / 3);
    llen = 1;
    maxMsgLenBytes = (N * 3 / 2 / 8 - llen - db / 8 - 1);
    bufferLenBits = ((N * 3 / 2 + 7) / 8 * 8 + 1);
    bufferLenTrits = (N - 1);
    pkLen = db;
  }
  






  public NTRUEncryptionKeyGenerationParameters(InputStream is)
    throws IOException
  {
    super(new SecureRandom(), -1);
    DataInputStream dis = new DataInputStream(is);
    N = dis.readInt();
    q = dis.readInt();
    df = dis.readInt();
    df1 = dis.readInt();
    df2 = dis.readInt();
    df3 = dis.readInt();
    db = dis.readInt();
    dm0 = dis.readInt();
    c = dis.readInt();
    minCallsR = dis.readInt();
    minCallsMask = dis.readInt();
    hashSeed = dis.readBoolean();
    oid = new byte[3];
    dis.readFully(oid);
    sparse = dis.readBoolean();
    fastFp = dis.readBoolean();
    polyType = dis.read();
    
    String alg = dis.readUTF();
    
    if ("SHA-512".equals(alg))
    {
      hashAlg = new SHA512Digest();
    }
    else if ("SHA-256".equals(alg))
    {
      hashAlg = new SHA256Digest();
    }
    
    init();
  }
  
  public NTRUEncryptionParameters getEncryptionParameters()
  {
    if (polyType == 0)
    {
      return new NTRUEncryptionParameters(N, q, df, dm0, db, c, minCallsR, minCallsMask, hashSeed, oid, sparse, fastFp, hashAlg);
    }
    

    return new NTRUEncryptionParameters(N, q, df1, df2, df3, dm0, db, c, minCallsR, minCallsMask, hashSeed, oid, sparse, fastFp, hashAlg);
  }
  

  public NTRUEncryptionKeyGenerationParameters clone()
  {
    if (polyType == 0)
    {
      return new NTRUEncryptionKeyGenerationParameters(N, q, df, dm0, db, c, minCallsR, minCallsMask, hashSeed, oid, sparse, fastFp, hashAlg);
    }
    

    return new NTRUEncryptionKeyGenerationParameters(N, q, df1, df2, df3, dm0, db, c, minCallsR, minCallsMask, hashSeed, oid, sparse, fastFp, hashAlg);
  }
  






  public int getMaxMessageLength()
  {
    return maxMsgLenBytes;
  }
  






  public void writeTo(OutputStream os)
    throws IOException
  {
    DataOutputStream dos = new DataOutputStream(os);
    dos.writeInt(N);
    dos.writeInt(q);
    dos.writeInt(df);
    dos.writeInt(df1);
    dos.writeInt(df2);
    dos.writeInt(df3);
    dos.writeInt(db);
    dos.writeInt(dm0);
    dos.writeInt(c);
    dos.writeInt(minCallsR);
    dos.writeInt(minCallsMask);
    dos.writeBoolean(hashSeed);
    dos.write(oid);
    dos.writeBoolean(sparse);
    dos.writeBoolean(fastFp);
    dos.write(polyType);
    dos.writeUTF(hashAlg.getAlgorithmName());
  }
  

  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + N;
    result = 31 * result + bufferLenBits;
    result = 31 * result + bufferLenTrits;
    result = 31 * result + c;
    result = 31 * result + db;
    result = 31 * result + df;
    result = 31 * result + df1;
    result = 31 * result + df2;
    result = 31 * result + df3;
    result = 31 * result + dg;
    result = 31 * result + dm0;
    result = 31 * result + dr;
    result = 31 * result + dr1;
    result = 31 * result + dr2;
    result = 31 * result + dr3;
    result = 31 * result + (fastFp ? 1231 : 1237);
    result = 31 * result + (hashAlg == null ? 0 : hashAlg.getAlgorithmName().hashCode());
    result = 31 * result + (hashSeed ? 1231 : 1237);
    result = 31 * result + llen;
    result = 31 * result + maxMsgLenBytes;
    result = 31 * result + minCallsMask;
    result = 31 * result + minCallsR;
    result = 31 * result + Arrays.hashCode(oid);
    result = 31 * result + pkLen;
    result = 31 * result + polyType;
    result = 31 * result + q;
    result = 31 * result + (sparse ? 1231 : 1237);
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    NTRUEncryptionKeyGenerationParameters other = (NTRUEncryptionKeyGenerationParameters)obj;
    if (N != N)
    {
      return false;
    }
    if (bufferLenBits != bufferLenBits)
    {
      return false;
    }
    if (bufferLenTrits != bufferLenTrits)
    {
      return false;
    }
    if (c != c)
    {
      return false;
    }
    if (db != db)
    {
      return false;
    }
    if (df != df)
    {
      return false;
    }
    if (df1 != df1)
    {
      return false;
    }
    if (df2 != df2)
    {
      return false;
    }
    if (df3 != df3)
    {
      return false;
    }
    if (dg != dg)
    {
      return false;
    }
    if (dm0 != dm0)
    {
      return false;
    }
    if (dr != dr)
    {
      return false;
    }
    if (dr1 != dr1)
    {
      return false;
    }
    if (dr2 != dr2)
    {
      return false;
    }
    if (dr3 != dr3)
    {
      return false;
    }
    if (fastFp != fastFp)
    {
      return false;
    }
    if (hashAlg == null)
    {
      if (hashAlg != null)
      {
        return false;
      }
    }
    else if (!hashAlg.getAlgorithmName().equals(hashAlg.getAlgorithmName()))
    {
      return false;
    }
    if (hashSeed != hashSeed)
    {
      return false;
    }
    if (llen != llen)
    {
      return false;
    }
    if (maxMsgLenBytes != maxMsgLenBytes)
    {
      return false;
    }
    if (minCallsMask != minCallsMask)
    {
      return false;
    }
    if (minCallsR != minCallsR)
    {
      return false;
    }
    if (!Arrays.equals(oid, oid))
    {
      return false;
    }
    if (pkLen != pkLen)
    {
      return false;
    }
    if (polyType != polyType)
    {
      return false;
    }
    if (q != q)
    {
      return false;
    }
    if (sparse != sparse)
    {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    StringBuilder output = new StringBuilder("EncryptionParameters(N=" + N + " q=" + q);
    if (polyType == 0)
    {
      output.append(" polyType=SIMPLE df=" + df);
    }
    else
    {
      output.append(" polyType=PRODUCT df1=" + df1 + " df2=" + df2 + " df3=" + df3);
    }
    output.append(" dm0=" + dm0 + " db=" + db + " c=" + c + " minCallsR=" + minCallsR + " minCallsMask=" + minCallsMask + " hashSeed=" + hashSeed + " hashAlg=" + hashAlg + " oid=" + 
      Arrays.toString(oid) + " sparse=" + sparse + ")");
    return output.toString();
  }
}
