package org.spongycastle.crypto.tls;

import java.io.IOException;



class DTLSRecordLayer
  implements DatagramTransport
{
  private static final int RECORD_HEADER_LENGTH = 13;
  private static final int MAX_FRAGMENT_LENGTH = 16384;
  private static final long TCP_MSL = 120000L;
  private static final long RETRANSMIT_TIMEOUT = 240000L;
  private final DatagramTransport transport;
  private final TlsContext context;
  private final TlsPeer peer;
  private final ByteQueue recordQueue = new ByteQueue();
  
  private volatile boolean closed = false;
  private volatile boolean failed = false;
  private volatile ProtocolVersion readVersion = null; private volatile ProtocolVersion writeVersion = null;
  private volatile boolean inHandshake;
  private volatile int plaintextLimit;
  private DTLSEpoch currentEpoch;
  private DTLSEpoch pendingEpoch;
  private DTLSEpoch readEpoch;
  private DTLSEpoch writeEpoch; private DTLSHandshakeRetransmit retransmit = null;
  private DTLSEpoch retransmitEpoch = null;
  private long retransmitExpiry = 0L;
  
  DTLSRecordLayer(DatagramTransport transport, TlsContext context, TlsPeer peer, short contentType)
  {
    this.transport = transport;
    this.context = context;
    this.peer = peer;
    
    inHandshake = true;
    
    currentEpoch = new DTLSEpoch(0, new TlsNullCipher(context));
    pendingEpoch = null;
    readEpoch = currentEpoch;
    writeEpoch = currentEpoch;
    
    setPlaintextLimit(16384);
  }
  
  void setPlaintextLimit(int plaintextLimit)
  {
    this.plaintextLimit = plaintextLimit;
  }
  
  int getReadEpoch()
  {
    return readEpoch.getEpoch();
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
  
  void initPendingEpoch(TlsCipher pendingCipher)
  {
    if (pendingEpoch != null)
    {
      throw new IllegalStateException();
    }
    







    pendingEpoch = new DTLSEpoch(writeEpoch.getEpoch() + 1, pendingCipher);
  }
  
  void handshakeSuccessful(DTLSHandshakeRetransmit retransmit)
  {
    if ((readEpoch == currentEpoch) || (writeEpoch == currentEpoch))
    {

      throw new IllegalStateException();
    }
    
    if (retransmit != null)
    {
      this.retransmit = retransmit;
      retransmitEpoch = currentEpoch;
      retransmitExpiry = (System.currentTimeMillis() + 240000L);
    }
    
    inHandshake = false;
    currentEpoch = pendingEpoch;
    pendingEpoch = null;
  }
  
  void resetWriteEpoch()
  {
    if (retransmitEpoch != null)
    {
      writeEpoch = retransmitEpoch;
    }
    else
    {
      writeEpoch = currentEpoch;
    }
  }
  
  public int getReceiveLimit()
    throws IOException
  {
    return Math.min(plaintextLimit, readEpoch
      .getCipher().getPlaintextLimit(transport.getReceiveLimit() - 13));
  }
  
  public int getSendLimit()
    throws IOException
  {
    return Math.min(plaintextLimit, writeEpoch
      .getCipher().getPlaintextLimit(transport.getSendLimit() - 13));
  }
  
  public int receive(byte[] buf, int off, int len, int waitMillis)
    throws IOException
  {
    byte[] record = null;
    
    for (;;)
    {
      int receiveLimit = Math.min(len, getReceiveLimit()) + 13;
      if ((record == null) || (record.length < receiveLimit))
      {
        record = new byte[receiveLimit];
      }
      
      try
      {
        if ((retransmit != null) && (System.currentTimeMillis() > retransmitExpiry))
        {
          retransmit = null;
          retransmitEpoch = null;
        }
        
        int received = receiveRecord(record, 0, receiveLimit, waitMillis);
        if (received < 0)
        {
          return received;
        }
        if (received < 13) {
          continue;
        }
        
        int length = TlsUtils.readUint16(record, 11);
        if (received != length + 13) {
          continue;
        }
        

        short type = TlsUtils.readUint8(record, 0);
        

        switch (type)
        {
        case 20: 
        case 21: 
        case 22: 
        case 23: 
        case 24: 
          break;
        }
        
        


        int epoch = TlsUtils.readUint16(record, 3);
        
        DTLSEpoch recordEpoch = null;
        if (epoch == readEpoch.getEpoch())
        {
          recordEpoch = readEpoch;
        }
        else if ((type == 22) && (retransmitEpoch != null) && 
          (epoch == retransmitEpoch.getEpoch()))
        {
          recordEpoch = retransmitEpoch;
        }
        
        if (recordEpoch == null) {
          continue;
        }
        

        long seq = TlsUtils.readUint48(record, 5);
        if (recordEpoch.getReplayWindow().shouldDiscard(seq)) {
          continue;
        }
        

        ProtocolVersion version = TlsUtils.readVersion(record, 1);
        if ((!version.isDTLS()) || (
        



          (readVersion != null) && (!readVersion.equals(version)))) {
          continue;
        }
        

        byte[] plaintext = recordEpoch.getCipher().decodeCiphertext(
          getMacSequenceNumber(recordEpoch.getEpoch(), seq), type, record, 13, received - 13);
        

        recordEpoch.getReplayWindow().reportAuthenticated(seq);
        
        if (plaintext.length > plaintextLimit) {
          continue;
        }
        

        if (readVersion == null)
        {
          readVersion = version;
        }
        
        switch (type)
        {

        case 21: 
          if (plaintext.length == 2)
          {
            short alertLevel = (short)plaintext[0];
            short alertDescription = (short)plaintext[1];
            
            peer.notifyAlertReceived(alertLevel, alertDescription);
            
            if (alertLevel == 2)
            {
              failed();
              throw new TlsFatalAlert(alertDescription);
            }
            

            if (alertDescription == 0)
            {
              closeTransport();
            }
          }
          
          break;
        

        case 23: 
          if (!inHandshake) {}
          


          break;
        





        case 20: 
          for (int i = 0; i < plaintext.length; i++)
          {
            short message = TlsUtils.readUint8(plaintext, i);
            if (message == 1)
            {



              if (pendingEpoch != null)
              {
                readEpoch = pendingEpoch;
              }
            }
          }
          break;
        

        case 22: 
          if (!inHandshake)
          {
            if (retransmit != null)
            {
              retransmit.receivedHandshakeRecord(epoch, plaintext, 0, plaintext.length);
            }
          }
          
          break;
        




        case 24: 
          break;
        





        default: 
          if ((!inHandshake) && (retransmit != null))
          {
            retransmit = null;
            retransmitEpoch = null;
          }
          
          System.arraycopy(plaintext, 0, buf, off, plaintext.length);
          return plaintext.length;
        }
        
      }
      catch (IOException e) {
        throw e;
      }
    }
  }
  
  public void send(byte[] buf, int off, int len)
    throws IOException
  {
    short contentType = 23;
    
    if ((inHandshake) || (writeEpoch == retransmitEpoch))
    {
      contentType = 22;
      
      short handshakeType = TlsUtils.readUint8(buf, off);
      if (handshakeType == 20)
      {
        DTLSEpoch nextEpoch = null;
        if (inHandshake)
        {
          nextEpoch = pendingEpoch;
        }
        else if (writeEpoch == retransmitEpoch)
        {
          nextEpoch = currentEpoch;
        }
        
        if (nextEpoch == null)
        {

          throw new IllegalStateException();
        }
        



        byte[] data = { 1 };
        sendRecord((short)20, data, 0, data.length);
        
        writeEpoch = nextEpoch;
      }
    }
    
    sendRecord(contentType, buf, off, len);
  }
  
  public void close()
    throws IOException
  {
    if (!closed)
    {
      if (inHandshake)
      {
        warn((short)90, "User canceled handshake");
      }
      closeTransport();
    }
  }
  
  void fail(short alertDescription)
  {
    if (!closed)
    {
      try
      {
        raiseAlert((short)2, alertDescription, null, null);
      }
      catch (Exception localException) {}
      



      failed = true;
      
      closeTransport();
    }
  }
  
  void failed()
  {
    if (!closed)
    {
      failed = true;
      
      closeTransport();
    }
  }
  
  void warn(short alertDescription, String message)
    throws IOException
  {
    raiseAlert((short)1, alertDescription, message, null);
  }
  
  private void closeTransport()
  {
    if (!closed)
    {




      try
      {



        if (!failed)
        {
          warn((short)0, null);
        }
        transport.close();
      }
      catch (Exception localException) {}
      



      closed = true;
    }
  }
  
  private void raiseAlert(short alertLevel, short alertDescription, String message, Throwable cause)
    throws IOException
  {
    peer.notifyAlertRaised(alertLevel, alertDescription, message, cause);
    
    byte[] error = new byte[2];
    error[0] = ((byte)alertLevel);
    error[1] = ((byte)alertDescription);
    
    sendRecord((short)21, error, 0, 2);
  }
  
  private int receiveRecord(byte[] buf, int off, int len, int waitMillis)
    throws IOException
  {
    if (recordQueue.available() > 0)
    {
      int length = 0;
      if (recordQueue.available() >= 13)
      {
        byte[] lengthBytes = new byte[2];
        recordQueue.read(lengthBytes, 0, 2, 11);
        length = TlsUtils.readUint16(lengthBytes, 0);
      }
      
      int received = Math.min(recordQueue.available(), 13 + length);
      recordQueue.removeData(buf, off, received, 0);
      return received;
    }
    
    int received = transport.receive(buf, off, len, waitMillis);
    if (received >= 13)
    {
      int fragmentLength = TlsUtils.readUint16(buf, off + 11);
      int recordLength = 13 + fragmentLength;
      if (received > recordLength)
      {
        recordQueue.addData(buf, off + recordLength, received - recordLength);
        received = recordLength;
      }
    }
    
    return received;
  }
  

  private void sendRecord(short contentType, byte[] buf, int off, int len)
    throws IOException
  {
    if (writeVersion == null)
    {
      return;
    }
    
    if (len > plaintextLimit)
    {
      throw new TlsFatalAlert((short)80);
    }
    




    if ((len < 1) && (contentType != 23))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    int recordEpoch = writeEpoch.getEpoch();
    long recordSequenceNumber = writeEpoch.allocateSequenceNumber();
    
    byte[] ciphertext = writeEpoch.getCipher().encodePlaintext(
      getMacSequenceNumber(recordEpoch, recordSequenceNumber), contentType, buf, off, len);
    


    byte[] record = new byte[ciphertext.length + 13];
    TlsUtils.writeUint8(contentType, record, 0);
    TlsUtils.writeVersion(writeVersion, record, 1);
    TlsUtils.writeUint16(recordEpoch, record, 3);
    TlsUtils.writeUint48(recordSequenceNumber, record, 5);
    TlsUtils.writeUint16(ciphertext.length, record, 11);
    System.arraycopy(ciphertext, 0, record, 13, ciphertext.length);
    
    transport.send(record, 0, record.length);
  }
  
  private static long getMacSequenceNumber(int epoch, long sequence_number)
  {
    return (epoch & 0xFFFFFFFF) << 48 | sequence_number;
  }
}
