package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.digests.LongDigest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;













public class TlsMac
{
  protected TlsContext context;
  protected byte[] secret;
  protected Mac mac;
  protected int digestBlockSize;
  protected int digestOverhead;
  protected int macLength;
  
  public TlsMac(TlsContext context, Digest digest, byte[] key, int keyOff, int keyLen)
  {
    this.context = context;
    
    KeyParameter keyParameter = new KeyParameter(key, keyOff, keyLen);
    
    secret = Arrays.clone(keyParameter.getKey());
    

    if ((digest instanceof LongDigest))
    {
      digestBlockSize = 128;
      digestOverhead = 16;
    }
    else
    {
      digestBlockSize = 64;
      digestOverhead = 8;
    }
    
    if (TlsUtils.isSSL(context))
    {
      mac = new SSL3Mac(digest);
      

      if (digest.getDigestSize() == 20)
      {




        digestOverhead = 4;
      }
    }
    else
    {
      mac = new HMac(digest);
    }
    


    mac.init(keyParameter);
    
    macLength = mac.getMacSize();
    if (getSecurityParameterstruncatedHMac)
    {
      macLength = Math.min(macLength, 10);
    }
  }
  



  public byte[] getMACSecret()
  {
    return secret;
  }
  



  public int getSize()
  {
    return macLength;
  }
  









  public byte[] calculateMac(long seqNo, short type, byte[] message, int offset, int length)
  {
    ProtocolVersion serverVersion = context.getServerVersion();
    boolean isSSL = serverVersion.isSSL();
    
    byte[] macHeader = new byte[isSSL ? 11 : 13];
    TlsUtils.writeUint64(seqNo, macHeader, 0);
    TlsUtils.writeUint8(type, macHeader, 8);
    if (!isSSL)
    {
      TlsUtils.writeVersion(serverVersion, macHeader, 9);
    }
    TlsUtils.writeUint16(length, macHeader, macHeader.length - 2);
    
    mac.update(macHeader, 0, macHeader.length);
    mac.update(message, offset, length);
    
    byte[] result = new byte[mac.getMacSize()];
    mac.doFinal(result, 0);
    return truncate(result);
  }
  




  public byte[] calculateMacConstantTime(long seqNo, short type, byte[] message, int offset, int length, int fullLength, byte[] dummyData)
  {
    byte[] result = calculateMac(seqNo, type, message, offset, length);
    




    int headerLength = TlsUtils.isSSL(context) ? 11 : 13;
    

    int extra = getDigestBlockCount(headerLength + fullLength) - getDigestBlockCount(headerLength + length);
    for (;;) {
      extra--; if (extra < 0)
        break;
      mac.update(dummyData, 0, digestBlockSize);
    }
    

    mac.update(dummyData[0]);
    mac.reset();
    
    return result;
  }
  

  protected int getDigestBlockCount(int inputLength)
  {
    return (inputLength + digestOverhead) / digestBlockSize;
  }
  
  protected byte[] truncate(byte[] bs)
  {
    if (bs.length <= macLength)
    {
      return bs;
    }
    
    return Arrays.copyOf(bs, macLength);
  }
}
