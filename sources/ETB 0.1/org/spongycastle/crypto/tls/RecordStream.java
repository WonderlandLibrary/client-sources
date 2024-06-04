package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.util.io.SimpleOutputStream;





class RecordStream
{
  private static int DEFAULT_PLAINTEXT_LIMIT = 16384;
  
  static final int TLS_HEADER_SIZE = 5;
  static final int TLS_HEADER_TYPE_OFFSET = 0;
  static final int TLS_HEADER_VERSION_OFFSET = 1;
  static final int TLS_HEADER_LENGTH_OFFSET = 3;
  private TlsProtocol handler;
  private InputStream input;
  private OutputStream output;
  private TlsCompression pendingCompression = null; private TlsCompression readCompression = null; private TlsCompression writeCompression = null;
  private TlsCipher pendingCipher = null; private TlsCipher readCipher = null; private TlsCipher writeCipher = null;
  private SequenceNumber readSeqNo = new SequenceNumber(null); private SequenceNumber writeSeqNo = new SequenceNumber(null);
  private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  
  private TlsHandshakeHash handshakeHash = null;
  private SimpleOutputStream handshakeHashUpdater = new SimpleOutputStream()
  {
    public void write(byte[] buf, int off, int len) throws IOException
    {
      handshakeHash.update(buf, off, len);
    }
  };
  
  private ProtocolVersion readVersion = null; private ProtocolVersion writeVersion = null;
  private boolean restrictReadVersion = true;
  private int plaintextLimit;
  private int compressedLimit;
  private int ciphertextLimit;
  
  RecordStream(TlsProtocol handler, InputStream input, OutputStream output) {
    this.handler = handler;
    this.input = input;
    this.output = output;
    readCompression = new TlsNullCompression();
    writeCompression = readCompression;
  }
  
  void init(TlsContext context)
  {
    readCipher = new TlsNullCipher(context);
    writeCipher = readCipher;
    handshakeHash = new DeferredHash();
    handshakeHash.init(context);
    
    setPlaintextLimit(DEFAULT_PLAINTEXT_LIMIT);
  }
  
  int getPlaintextLimit()
  {
    return plaintextLimit;
  }
  
  void setPlaintextLimit(int plaintextLimit)
  {
    this.plaintextLimit = plaintextLimit;
    compressedLimit = (this.plaintextLimit + 1024);
    ciphertextLimit = (compressedLimit + 1024);
  }
  
  ProtocolVersion getReadVersion()
  {
    return readVersion;
  }
  
  void setReadVersion(ProtocolVersion readVersion)
  {
    this.readVersion = readVersion;
  }
  
  void setWriteVersion(ProtocolVersion writeVersion)
  {
    this.writeVersion = writeVersion;
  }
  







  void setRestrictReadVersion(boolean enabled)
  {
    restrictReadVersion = enabled;
  }
  
  void setPendingConnectionState(TlsCompression tlsCompression, TlsCipher tlsCipher)
  {
    pendingCompression = tlsCompression;
    pendingCipher = tlsCipher;
  }
  
  void sentWriteCipherSpec()
    throws IOException
  {
    if ((pendingCompression == null) || (pendingCipher == null))
    {
      throw new TlsFatalAlert((short)40);
    }
    writeCompression = pendingCompression;
    writeCipher = pendingCipher;
    writeSeqNo = new SequenceNumber(null);
  }
  
  void receivedReadCipherSpec()
    throws IOException
  {
    if ((pendingCompression == null) || (pendingCipher == null))
    {
      throw new TlsFatalAlert((short)40);
    }
    readCompression = pendingCompression;
    readCipher = pendingCipher;
    readSeqNo = new SequenceNumber(null);
  }
  
  void finaliseHandshake()
    throws IOException
  {
    if ((readCompression != pendingCompression) || (writeCompression != pendingCompression) || (readCipher != pendingCipher) || (writeCipher != pendingCipher))
    {

      throw new TlsFatalAlert((short)40);
    }
    pendingCompression = null;
    pendingCipher = null;
  }
  
  void checkRecordHeader(byte[] recordHeader) throws IOException
  {
    short type = TlsUtils.readUint8(recordHeader, 0);
    




    checkType(type, (short)10);
    
    if (!restrictReadVersion)
    {
      int version = TlsUtils.readVersionRaw(recordHeader, 1);
      if ((version & 0xFF00) != 768)
      {
        throw new TlsFatalAlert((short)47);
      }
    }
    else
    {
      ProtocolVersion version = TlsUtils.readVersion(recordHeader, 1);
      if (readVersion != null)
      {


        if (!version.equals(readVersion))
        {
          throw new TlsFatalAlert((short)47);
        }
      }
    }
    int length = TlsUtils.readUint16(recordHeader, 3);
    
    checkLength(length, ciphertextLimit, (short)22);
  }
  
  boolean readRecord()
    throws IOException
  {
    byte[] recordHeader = TlsUtils.readAllOrNothing(5, input);
    if (recordHeader == null)
    {
      return false;
    }
    
    short type = TlsUtils.readUint8(recordHeader, 0);
    




    checkType(type, (short)10);
    
    if (!restrictReadVersion)
    {
      int version = TlsUtils.readVersionRaw(recordHeader, 1);
      if ((version & 0xFF00) != 768)
      {
        throw new TlsFatalAlert((short)47);
      }
    }
    else
    {
      ProtocolVersion version = TlsUtils.readVersion(recordHeader, 1);
      if (readVersion == null)
      {
        readVersion = version;
      }
      else if (!version.equals(readVersion))
      {
        throw new TlsFatalAlert((short)47);
      }
    }
    
    int length = TlsUtils.readUint16(recordHeader, 3);
    
    checkLength(length, ciphertextLimit, (short)22);
    
    byte[] plaintext = decodeAndVerify(type, input, length);
    handler.processRecord(type, plaintext, 0, plaintext.length);
    return true;
  }
  
  byte[] decodeAndVerify(short type, InputStream input, int len)
    throws IOException
  {
    byte[] buf = TlsUtils.readFully(len, input);
    
    long seqNo = readSeqNo.nextValue((short)10);
    byte[] decoded = readCipher.decodeCiphertext(seqNo, type, buf, 0, buf.length);
    
    checkLength(decoded.length, compressedLimit, (short)22);
    




    OutputStream cOut = readCompression.decompress(buffer);
    if (cOut != buffer)
    {
      cOut.write(decoded, 0, decoded.length);
      cOut.flush();
      decoded = getBufferContents();
    }
    





    checkLength(decoded.length, plaintextLimit, (short)30);
    




    if ((decoded.length < 1) && (type != 23))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    return decoded;
  }
  

  void writeRecord(short type, byte[] plaintext, int plaintextOffset, int plaintextLength)
    throws IOException
  {
    if (writeVersion == null)
    {
      return;
    }
    




    checkType(type, (short)80);
    



    checkLength(plaintextLength, plaintextLimit, (short)80);
    




    if ((plaintextLength < 1) && (type != 23))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    OutputStream cOut = writeCompression.compress(buffer);
    
    long seqNo = writeSeqNo.nextValue((short)80);
    byte[] ciphertext;
    byte[] ciphertext;
    if (cOut == buffer)
    {
      ciphertext = writeCipher.encodePlaintext(seqNo, type, plaintext, plaintextOffset, plaintextLength);
    }
    else
    {
      cOut.write(plaintext, plaintextOffset, plaintextLength);
      cOut.flush();
      byte[] compressed = getBufferContents();
      




      checkLength(compressed.length, plaintextLength + 1024, (short)80);
      
      ciphertext = writeCipher.encodePlaintext(seqNo, type, compressed, 0, compressed.length);
    }
    



    checkLength(ciphertext.length, ciphertextLimit, (short)80);
    
    byte[] record = new byte[ciphertext.length + 5];
    TlsUtils.writeUint8(type, record, 0);
    TlsUtils.writeVersion(writeVersion, record, 1);
    TlsUtils.writeUint16(ciphertext.length, record, 3);
    System.arraycopy(ciphertext, 0, record, 5, ciphertext.length);
    output.write(record);
    output.flush();
  }
  
  void notifyHelloComplete()
  {
    handshakeHash = handshakeHash.notifyPRFDetermined();
  }
  
  TlsHandshakeHash getHandshakeHash()
  {
    return handshakeHash;
  }
  
  OutputStream getHandshakeHashUpdater()
  {
    return handshakeHashUpdater;
  }
  
  TlsHandshakeHash prepareToFinish()
  {
    TlsHandshakeHash result = handshakeHash;
    handshakeHash = handshakeHash.stopTracking();
    return result;
  }
  
  void safeClose()
  {
    try
    {
      input.close();
    }
    catch (IOException localIOException) {}
    


    try
    {
      output.close();
    }
    catch (IOException localIOException1) {}
  }
  


  void flush()
    throws IOException
  {
    output.flush();
  }
  
  private byte[] getBufferContents()
  {
    byte[] contents = buffer.toByteArray();
    buffer.reset();
    return contents;
  }
  
  private static void checkType(short type, short alertDescription)
    throws IOException
  {
    switch (type)
    {
    case 20: 
    case 21: 
    case 22: 
    case 23: 
      break;
    
    default: 
      throw new TlsFatalAlert(alertDescription);
    }
  }
  
  private static void checkLength(int length, int limit, short alertDescription)
    throws IOException
  {
    if (length > limit)
    {
      throw new TlsFatalAlert(alertDescription);
    }
  }
  
  private static class SequenceNumber
  {
    private long value = 0L;
    private boolean exhausted = false;
    
    private SequenceNumber() {}
    
    synchronized long nextValue(short alertDescription) throws TlsFatalAlert { if (exhausted)
      {
        throw new TlsFatalAlert(alertDescription);
      }
      long result = value;
      if (++value == 0L)
      {
        exhausted = true;
      }
      return result;
    }
  }
}
