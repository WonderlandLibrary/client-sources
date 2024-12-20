package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;






public class TlsStreamCipher
  implements TlsCipher
{
  protected TlsContext context;
  protected StreamCipher encryptCipher;
  protected StreamCipher decryptCipher;
  protected TlsMac writeMac;
  protected TlsMac readMac;
  protected boolean usesNonce;
  
  public TlsStreamCipher(TlsContext context, StreamCipher clientWriteCipher, StreamCipher serverWriteCipher, Digest clientWriteDigest, Digest serverWriteDigest, int cipherKeySize, boolean usesNonce)
    throws IOException
  {
    boolean isServer = context.isServer();
    
    this.context = context;
    this.usesNonce = usesNonce;
    
    encryptCipher = clientWriteCipher;
    decryptCipher = serverWriteCipher;
    

    int key_block_size = 2 * cipherKeySize + clientWriteDigest.getDigestSize() + serverWriteDigest.getDigestSize();
    
    byte[] key_block = TlsUtils.calculateKeyBlock(context, key_block_size);
    
    int offset = 0;
    


    TlsMac clientWriteMac = new TlsMac(context, clientWriteDigest, key_block, offset, clientWriteDigest.getDigestSize());
    offset += clientWriteDigest.getDigestSize();
    
    TlsMac serverWriteMac = new TlsMac(context, serverWriteDigest, key_block, offset, serverWriteDigest.getDigestSize());
    offset += serverWriteDigest.getDigestSize();
    

    KeyParameter clientWriteKey = new KeyParameter(key_block, offset, cipherKeySize);
    offset += cipherKeySize;
    KeyParameter serverWriteKey = new KeyParameter(key_block, offset, cipherKeySize);
    offset += cipherKeySize;
    
    if (offset != key_block_size)
    {
      throw new TlsFatalAlert((short)80); }
    CipherParameters decryptParams;
    CipherParameters encryptParams;
    CipherParameters decryptParams;
    if (isServer)
    {
      writeMac = serverWriteMac;
      readMac = clientWriteMac;
      encryptCipher = serverWriteCipher;
      decryptCipher = clientWriteCipher;
      CipherParameters encryptParams = serverWriteKey;
      decryptParams = clientWriteKey;
    }
    else
    {
      writeMac = clientWriteMac;
      readMac = serverWriteMac;
      encryptCipher = clientWriteCipher;
      decryptCipher = serverWriteCipher;
      encryptParams = clientWriteKey;
      decryptParams = serverWriteKey;
    }
    
    if (usesNonce)
    {
      byte[] dummyNonce = new byte[8];
      encryptParams = new ParametersWithIV(encryptParams, dummyNonce);
      decryptParams = new ParametersWithIV(decryptParams, dummyNonce);
    }
    
    encryptCipher.init(true, encryptParams);
    decryptCipher.init(false, decryptParams);
  }
  
  public int getPlaintextLimit(int ciphertextLimit)
  {
    return ciphertextLimit - writeMac.getSize();
  }
  
  public byte[] encodePlaintext(long seqNo, short type, byte[] plaintext, int offset, int len)
  {
    if (usesNonce)
    {
      updateIV(encryptCipher, true, seqNo);
    }
    
    byte[] outBuf = new byte[len + writeMac.getSize()];
    
    encryptCipher.processBytes(plaintext, offset, len, outBuf, 0);
    
    byte[] mac = writeMac.calculateMac(seqNo, type, plaintext, offset, len);
    encryptCipher.processBytes(mac, 0, mac.length, outBuf, len);
    
    return outBuf;
  }
  
  public byte[] decodeCiphertext(long seqNo, short type, byte[] ciphertext, int offset, int len)
    throws IOException
  {
    if (usesNonce)
    {
      updateIV(decryptCipher, false, seqNo);
    }
    
    int macSize = readMac.getSize();
    if (len < macSize)
    {
      throw new TlsFatalAlert((short)50);
    }
    
    int plaintextLength = len - macSize;
    
    byte[] deciphered = new byte[len];
    decryptCipher.processBytes(ciphertext, offset, len, deciphered, 0);
    checkMAC(seqNo, type, deciphered, plaintextLength, len, deciphered, 0, plaintextLength);
    return Arrays.copyOfRange(deciphered, 0, plaintextLength);
  }
  
  protected void checkMAC(long seqNo, short type, byte[] recBuf, int recStart, int recEnd, byte[] calcBuf, int calcOff, int calcLen)
    throws IOException
  {
    byte[] receivedMac = Arrays.copyOfRange(recBuf, recStart, recEnd);
    byte[] computedMac = readMac.calculateMac(seqNo, type, calcBuf, calcOff, calcLen);
    
    if (!Arrays.constantTimeAreEqual(receivedMac, computedMac))
    {
      throw new TlsFatalAlert((short)20);
    }
  }
  
  protected void updateIV(StreamCipher cipher, boolean forEncryption, long seqNo)
  {
    byte[] nonce = new byte[8];
    TlsUtils.writeUint64(seqNo, nonce, 0);
    cipher.init(forEncryption, new ParametersWithIV(null, nonce));
  }
}
