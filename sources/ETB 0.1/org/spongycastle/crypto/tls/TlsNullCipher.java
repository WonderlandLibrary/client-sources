package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;






public class TlsNullCipher
  implements TlsCipher
{
  protected TlsContext context;
  protected TlsMac writeMac;
  protected TlsMac readMac;
  
  public TlsNullCipher(TlsContext context)
  {
    this.context = context;
    writeMac = null;
    readMac = null;
  }
  
  public TlsNullCipher(TlsContext context, Digest clientWriteDigest, Digest serverWriteDigest)
    throws IOException
  {
    if ((clientWriteDigest == null ? 1 : 0) != (serverWriteDigest == null ? 1 : 0))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    this.context = context;
    
    TlsMac clientWriteMac = null;TlsMac serverWriteMac = null;
    
    if (clientWriteDigest != null)
    {

      int key_block_size = clientWriteDigest.getDigestSize() + serverWriteDigest.getDigestSize();
      byte[] key_block = TlsUtils.calculateKeyBlock(context, key_block_size);
      
      int offset = 0;
      

      clientWriteMac = new TlsMac(context, clientWriteDigest, key_block, offset, clientWriteDigest.getDigestSize());
      offset += clientWriteDigest.getDigestSize();
      

      serverWriteMac = new TlsMac(context, serverWriteDigest, key_block, offset, serverWriteDigest.getDigestSize());
      offset += serverWriteDigest.getDigestSize();
      
      if (offset != key_block_size)
      {
        throw new TlsFatalAlert((short)80);
      }
    }
    
    if (context.isServer())
    {
      writeMac = serverWriteMac;
      readMac = clientWriteMac;
    }
    else
    {
      writeMac = clientWriteMac;
      readMac = serverWriteMac;
    }
  }
  
  public int getPlaintextLimit(int ciphertextLimit)
  {
    int result = ciphertextLimit;
    if (writeMac != null)
    {
      result -= writeMac.getSize();
    }
    return result;
  }
  
  public byte[] encodePlaintext(long seqNo, short type, byte[] plaintext, int offset, int len)
    throws IOException
  {
    if (writeMac == null)
    {
      return Arrays.copyOfRange(plaintext, offset, offset + len);
    }
    
    byte[] mac = writeMac.calculateMac(seqNo, type, plaintext, offset, len);
    byte[] ciphertext = new byte[len + mac.length];
    System.arraycopy(plaintext, offset, ciphertext, 0, len);
    System.arraycopy(mac, 0, ciphertext, len, mac.length);
    return ciphertext;
  }
  
  public byte[] decodeCiphertext(long seqNo, short type, byte[] ciphertext, int offset, int len)
    throws IOException
  {
    if (readMac == null)
    {
      return Arrays.copyOfRange(ciphertext, offset, offset + len);
    }
    
    int macSize = readMac.getSize();
    if (len < macSize)
    {
      throw new TlsFatalAlert((short)50);
    }
    
    int macInputLen = len - macSize;
    
    byte[] receivedMac = Arrays.copyOfRange(ciphertext, offset + macInputLen, offset + len);
    byte[] computedMac = readMac.calculateMac(seqNo, type, ciphertext, offset, macInputLen);
    
    if (!Arrays.constantTimeAreEqual(receivedMac, computedMac))
    {
      throw new TlsFatalAlert((short)20);
    }
    
    return Arrays.copyOfRange(ciphertext, offset, offset + macInputLen);
  }
}
