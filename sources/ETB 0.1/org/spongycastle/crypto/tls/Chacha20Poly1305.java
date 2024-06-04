package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.engines.ChaCha7539Engine;
import org.spongycastle.crypto.macs.Poly1305;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Pack;




public class Chacha20Poly1305
  implements TlsCipher
{
  private static final byte[] ZEROES = new byte[15];
  protected TlsContext context;
  protected ChaCha7539Engine encryptCipher;
  protected ChaCha7539Engine decryptCipher;
  protected byte[] encryptIV;
  protected byte[] decryptIV;
  
  public Chacha20Poly1305(TlsContext context) throws IOException
  {
    if (!TlsUtils.isTLSv12(context))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    this.context = context;
    
    int cipherKeySize = 32;
    
    int fixed_iv_length = 12;
    

    int key_block_size = 2 * cipherKeySize + 2 * fixed_iv_length;
    
    byte[] key_block = TlsUtils.calculateKeyBlock(context, key_block_size);
    
    int offset = 0;
    
    KeyParameter client_write_key = new KeyParameter(key_block, offset, cipherKeySize);
    offset += cipherKeySize;
    KeyParameter server_write_key = new KeyParameter(key_block, offset, cipherKeySize);
    offset += cipherKeySize;
    byte[] client_write_IV = Arrays.copyOfRange(key_block, offset, offset + fixed_iv_length);
    offset += fixed_iv_length;
    byte[] server_write_IV = Arrays.copyOfRange(key_block, offset, offset + fixed_iv_length);
    offset += fixed_iv_length;
    
    if (offset != key_block_size)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    encryptCipher = new ChaCha7539Engine();
    decryptCipher = new ChaCha7539Engine();
    KeyParameter encryptKey;
    KeyParameter decryptKey;
    if (context.isServer())
    {
      KeyParameter encryptKey = server_write_key;
      KeyParameter decryptKey = client_write_key;
      encryptIV = server_write_IV;
      decryptIV = client_write_IV;
    }
    else
    {
      encryptKey = client_write_key;
      decryptKey = server_write_key;
      encryptIV = client_write_IV;
      decryptIV = server_write_IV;
    }
    
    encryptCipher.init(true, new ParametersWithIV(encryptKey, encryptIV));
    decryptCipher.init(false, new ParametersWithIV(decryptKey, decryptIV));
  }
  
  public int getPlaintextLimit(int ciphertextLimit)
  {
    return ciphertextLimit - 16;
  }
  
  public byte[] encodePlaintext(long seqNo, short type, byte[] plaintext, int offset, int len) throws IOException
  {
    KeyParameter macKey = initRecord(encryptCipher, true, seqNo, encryptIV);
    
    byte[] output = new byte[len + 16];
    encryptCipher.processBytes(plaintext, offset, len, output, 0);
    
    byte[] additionalData = getAdditionalData(seqNo, type, len);
    byte[] mac = calculateRecordMAC(macKey, additionalData, output, 0, len);
    System.arraycopy(mac, 0, output, len, mac.length);
    
    return output;
  }
  
  public byte[] decodeCiphertext(long seqNo, short type, byte[] ciphertext, int offset, int len) throws IOException
  {
    if (getPlaintextLimit(len) < 0)
    {
      throw new TlsFatalAlert((short)50);
    }
    
    KeyParameter macKey = initRecord(decryptCipher, false, seqNo, decryptIV);
    
    int plaintextLength = len - 16;
    
    byte[] additionalData = getAdditionalData(seqNo, type, plaintextLength);
    byte[] calculatedMAC = calculateRecordMAC(macKey, additionalData, ciphertext, offset, plaintextLength);
    byte[] receivedMAC = Arrays.copyOfRange(ciphertext, offset + plaintextLength, offset + len);
    
    if (!Arrays.constantTimeAreEqual(calculatedMAC, receivedMAC))
    {
      throw new TlsFatalAlert((short)20);
    }
    
    byte[] output = new byte[plaintextLength];
    decryptCipher.processBytes(ciphertext, offset, plaintextLength, output, 0);
    return output;
  }
  
  protected KeyParameter initRecord(StreamCipher cipher, boolean forEncryption, long seqNo, byte[] iv)
  {
    byte[] nonce = calculateNonce(seqNo, iv);
    cipher.init(forEncryption, new ParametersWithIV(null, nonce));
    return generateRecordMACKey(cipher);
  }
  
  protected byte[] calculateNonce(long seqNo, byte[] iv)
  {
    byte[] nonce = new byte[12];
    TlsUtils.writeUint64(seqNo, nonce, 4);
    
    for (int i = 0; i < 12; tmp27_25++)
    {
      int tmp27_25 = i; byte[] tmp27_23 = nonce;tmp27_23[tmp27_25] = ((byte)(tmp27_23[tmp27_25] ^ iv[tmp27_25]));
    }
    
    return nonce;
  }
  
  protected KeyParameter generateRecordMACKey(StreamCipher cipher)
  {
    byte[] firstBlock = new byte[64];
    cipher.processBytes(firstBlock, 0, firstBlock.length, firstBlock, 0);
    
    KeyParameter macKey = new KeyParameter(firstBlock, 0, 32);
    Arrays.fill(firstBlock, (byte)0);
    return macKey;
  }
  
  protected byte[] calculateRecordMAC(KeyParameter macKey, byte[] additionalData, byte[] buf, int off, int len)
  {
    Mac mac = new Poly1305();
    mac.init(macKey);
    
    updateRecordMACText(mac, additionalData, 0, additionalData.length);
    updateRecordMACText(mac, buf, off, len);
    updateRecordMACLength(mac, additionalData.length);
    updateRecordMACLength(mac, len);
    
    byte[] output = new byte[mac.getMacSize()];
    mac.doFinal(output, 0);
    return output;
  }
  
  protected void updateRecordMACLength(Mac mac, int len)
  {
    byte[] longLen = Pack.longToLittleEndian(len & 0xFFFFFFFF);
    mac.update(longLen, 0, longLen.length);
  }
  
  protected void updateRecordMACText(Mac mac, byte[] buf, int off, int len)
  {
    mac.update(buf, off, len);
    
    int partial = len % 16;
    if (partial != 0)
    {
      mac.update(ZEROES, 0, 16 - partial);
    }
  }
  



  protected byte[] getAdditionalData(long seqNo, short type, int len)
    throws IOException
  {
    byte[] additional_data = new byte[13];
    TlsUtils.writeUint64(seqNo, additional_data, 0);
    TlsUtils.writeUint8(type, additional_data, 8);
    TlsUtils.writeVersion(context.getServerVersion(), additional_data, 9);
    TlsUtils.writeUint16(len, additional_data, 11);
    
    return additional_data;
  }
}
