package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.security.SecureRandom;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.prng.RandomGenerator;
import org.spongycastle.util.Arrays;






public class TlsBlockCipher
  implements TlsCipher
{
  protected TlsContext context;
  protected byte[] randomData;
  protected boolean useExplicitIV;
  protected boolean encryptThenMAC;
  protected BlockCipher encryptCipher;
  protected BlockCipher decryptCipher;
  protected TlsMac writeMac;
  protected TlsMac readMac;
  
  public TlsMac getWriteMac()
  {
    return writeMac;
  }
  
  public TlsMac getReadMac()
  {
    return readMac;
  }
  
  public TlsBlockCipher(TlsContext context, BlockCipher clientWriteCipher, BlockCipher serverWriteCipher, Digest clientWriteDigest, Digest serverWriteDigest, int cipherKeySize)
    throws IOException
  {
    this.context = context;
    
    randomData = new byte['Ā'];
    context.getNonceRandomGenerator().nextBytes(randomData);
    
    useExplicitIV = TlsUtils.isTLSv11(context);
    encryptThenMAC = getSecurityParametersencryptThenMAC;
    

    int key_block_size = 2 * cipherKeySize + clientWriteDigest.getDigestSize() + serverWriteDigest.getDigestSize();
    

    if (!useExplicitIV)
    {
      key_block_size += clientWriteCipher.getBlockSize() + serverWriteCipher.getBlockSize();
    }
    
    byte[] key_block = TlsUtils.calculateKeyBlock(context, key_block_size);
    
    int offset = 0;
    

    TlsMac clientWriteMac = new TlsMac(context, clientWriteDigest, key_block, offset, clientWriteDigest.getDigestSize());
    offset += clientWriteDigest.getDigestSize();
    
    TlsMac serverWriteMac = new TlsMac(context, serverWriteDigest, key_block, offset, serverWriteDigest.getDigestSize());
    offset += serverWriteDigest.getDigestSize();
    
    KeyParameter client_write_key = new KeyParameter(key_block, offset, cipherKeySize);
    offset += cipherKeySize;
    KeyParameter server_write_key = new KeyParameter(key_block, offset, cipherKeySize);
    offset += cipherKeySize;
    byte[] server_write_IV;
    byte[] client_write_IV;
    byte[] server_write_IV; if (useExplicitIV)
    {
      byte[] client_write_IV = new byte[clientWriteCipher.getBlockSize()];
      server_write_IV = new byte[serverWriteCipher.getBlockSize()];
    }
    else
    {
      client_write_IV = Arrays.copyOfRange(key_block, offset, offset + clientWriteCipher.getBlockSize());
      offset += clientWriteCipher.getBlockSize();
      server_write_IV = Arrays.copyOfRange(key_block, offset, offset + serverWriteCipher.getBlockSize());
      offset += serverWriteCipher.getBlockSize();
    }
    
    if (offset != key_block_size)
    {
      throw new TlsFatalAlert((short)80); }
    CipherParameters decryptParams;
    CipherParameters encryptParams;
    CipherParameters decryptParams;
    if (context.isServer())
    {
      writeMac = serverWriteMac;
      readMac = clientWriteMac;
      encryptCipher = serverWriteCipher;
      decryptCipher = clientWriteCipher;
      CipherParameters encryptParams = new ParametersWithIV(server_write_key, server_write_IV);
      decryptParams = new ParametersWithIV(client_write_key, client_write_IV);
    }
    else
    {
      writeMac = clientWriteMac;
      readMac = serverWriteMac;
      encryptCipher = clientWriteCipher;
      decryptCipher = serverWriteCipher;
      encryptParams = new ParametersWithIV(client_write_key, client_write_IV);
      decryptParams = new ParametersWithIV(server_write_key, server_write_IV);
    }
    
    encryptCipher.init(true, encryptParams);
    decryptCipher.init(false, decryptParams);
  }
  
  public int getPlaintextLimit(int ciphertextLimit)
  {
    int blockSize = encryptCipher.getBlockSize();
    int macSize = writeMac.getSize();
    
    int plaintextLimit = ciphertextLimit;
    

    if (useExplicitIV)
    {
      plaintextLimit -= blockSize;
    }
    

    if (encryptThenMAC)
    {
      plaintextLimit -= macSize;
      plaintextLimit -= plaintextLimit % blockSize;
    }
    else
    {
      plaintextLimit -= plaintextLimit % blockSize;
      plaintextLimit -= macSize;
    }
    

    plaintextLimit--;
    
    return plaintextLimit;
  }
  
  public byte[] encodePlaintext(long seqNo, short type, byte[] plaintext, int offset, int len)
  {
    int blockSize = encryptCipher.getBlockSize();
    int macSize = writeMac.getSize();
    
    ProtocolVersion version = context.getServerVersion();
    
    int enc_input_length = len;
    if (!encryptThenMAC)
    {
      enc_input_length += macSize;
    }
    
    int padding_length = blockSize - 1 - enc_input_length % blockSize;
    






    if ((encryptThenMAC) || (!context.getSecurityParameters().truncatedHMac))
    {

      if ((!version.isDTLS()) && (!version.isSSL()))
      {

        int maxExtraPadBlocks = (255 - padding_length) / blockSize;
        int actualExtraPadBlocks = chooseExtraPadBlocks(context.getSecureRandom(), maxExtraPadBlocks);
        padding_length += actualExtraPadBlocks * blockSize;
      }
    }
    
    int totalSize = len + macSize + padding_length + 1;
    if (useExplicitIV)
    {
      totalSize += blockSize;
    }
    
    byte[] outBuf = new byte[totalSize];
    int outOff = 0;
    
    if (useExplicitIV)
    {
      byte[] explicitIV = new byte[blockSize];
      context.getNonceRandomGenerator().nextBytes(explicitIV);
      
      encryptCipher.init(true, new ParametersWithIV(null, explicitIV));
      
      System.arraycopy(explicitIV, 0, outBuf, outOff, blockSize);
      outOff += blockSize;
    }
    
    int blocks_start = outOff;
    
    System.arraycopy(plaintext, offset, outBuf, outOff, len);
    outOff += len;
    
    if (!encryptThenMAC)
    {
      byte[] mac = writeMac.calculateMac(seqNo, type, plaintext, offset, len);
      System.arraycopy(mac, 0, outBuf, outOff, mac.length);
      outOff += mac.length;
    }
    
    for (int i = 0; i <= padding_length; i++)
    {
      outBuf[(outOff++)] = ((byte)padding_length);
    }
    
    for (int i = blocks_start; i < outOff; i += blockSize)
    {
      encryptCipher.processBlock(outBuf, i, outBuf, i);
    }
    
    if (encryptThenMAC)
    {
      byte[] mac = writeMac.calculateMac(seqNo, type, outBuf, 0, outOff);
      System.arraycopy(mac, 0, outBuf, outOff, mac.length);
      outOff += mac.length;
    }
    


    return outBuf;
  }
  
  public byte[] decodeCiphertext(long seqNo, short type, byte[] ciphertext, int offset, int len)
    throws IOException
  {
    int blockSize = decryptCipher.getBlockSize();
    int macSize = readMac.getSize();
    
    int minLen = blockSize;
    if (encryptThenMAC)
    {
      minLen += macSize;
    }
    else
    {
      minLen = Math.max(minLen, macSize + 1);
    }
    
    if (useExplicitIV)
    {
      minLen += blockSize;
    }
    
    if (len < minLen)
    {
      throw new TlsFatalAlert((short)50);
    }
    
    int blocks_length = len;
    if (encryptThenMAC)
    {
      blocks_length -= macSize;
    }
    
    if (blocks_length % blockSize != 0)
    {
      throw new TlsFatalAlert((short)21);
    }
    
    if (encryptThenMAC)
    {
      int end = offset + len;
      byte[] receivedMac = Arrays.copyOfRange(ciphertext, end - macSize, end);
      byte[] calculatedMac = readMac.calculateMac(seqNo, type, ciphertext, offset, len - macSize);
      
      boolean badMac = !Arrays.constantTimeAreEqual(calculatedMac, receivedMac);
      if (badMac)
      {








        throw new TlsFatalAlert((short)20);
      }
    }
    
    if (useExplicitIV)
    {
      decryptCipher.init(false, new ParametersWithIV(null, ciphertext, offset, blockSize));
      
      offset += blockSize;
      blocks_length -= blockSize;
    }
    
    for (int i = 0; i < blocks_length; i += blockSize)
    {
      decryptCipher.processBlock(ciphertext, offset + i, ciphertext, offset + i);
    }
    

    int totalPad = checkPaddingConstantTime(ciphertext, offset, blocks_length, blockSize, encryptThenMAC ? 0 : macSize);
    boolean badMac = totalPad == 0;
    
    int dec_output_length = blocks_length - totalPad;
    
    if (!encryptThenMAC)
    {
      dec_output_length -= macSize;
      int macInputLen = dec_output_length;
      int macOff = offset + macInputLen;
      byte[] receivedMac = Arrays.copyOfRange(ciphertext, macOff, macOff + macSize);
      byte[] calculatedMac = readMac.calculateMacConstantTime(seqNo, type, ciphertext, offset, macInputLen, blocks_length - macSize, randomData);
      

      badMac |= !Arrays.constantTimeAreEqual(calculatedMac, receivedMac);
    }
    
    if (badMac)
    {
      throw new TlsFatalAlert((short)20);
    }
    
    return Arrays.copyOfRange(ciphertext, offset, offset + dec_output_length);
  }
  
  protected int checkPaddingConstantTime(byte[] buf, int off, int len, int blockSize, int macSize)
  {
    int end = off + len;
    byte lastByte = buf[(end - 1)];
    int padlen = lastByte & 0xFF;
    int totalPad = padlen + 1;
    
    int dummyIndex = 0;
    byte padDiff = 0;
    
    if (((TlsUtils.isSSL(context)) && (totalPad > blockSize)) || (macSize + totalPad > len))
    {
      totalPad = 0;
    }
    else
    {
      int padPos = end - totalPad;
      do
      {
        padDiff = (byte)(padDiff | buf[(padPos++)] ^ lastByte);
      }
      while (padPos < end);
      
      dummyIndex = totalPad;
      
      if (padDiff != 0)
      {
        totalPad = 0;
      }
    }
    


    byte[] dummyPad = randomData;
    while (dummyIndex < 256)
    {
      padDiff = (byte)(padDiff | dummyPad[(dummyIndex++)] ^ lastByte);
    }
    
    int tmp144_143 = 0; byte[] tmp144_141 = dummyPad;tmp144_141[tmp144_143] = ((byte)(tmp144_141[tmp144_143] ^ padDiff));
    

    return totalPad;
  }
  


  protected int chooseExtraPadBlocks(SecureRandom r, int max)
  {
    int x = r.nextInt();
    int n = lowestBitSet(x);
    return Math.min(n, max);
  }
  
  protected int lowestBitSet(int x)
  {
    if (x == 0)
    {
      return 32;
    }
    
    int n = 0;
    while ((x & 0x1) == 0)
    {
      n++;
      x >>= 1;
    }
    return n;
  }
}
