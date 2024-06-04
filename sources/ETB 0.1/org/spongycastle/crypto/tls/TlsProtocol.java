package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.prng.RandomGenerator;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;


public abstract class TlsProtocol
{
  protected static final Integer EXT_RenegotiationInfo = Integers.valueOf(65281);
  protected static final Integer EXT_SessionTicket = Integers.valueOf(35);
  
  protected static final short CS_START = 0;
  
  protected static final short CS_CLIENT_HELLO = 1;
  
  protected static final short CS_SERVER_HELLO = 2;
  
  protected static final short CS_SERVER_SUPPLEMENTAL_DATA = 3;
  
  protected static final short CS_SERVER_CERTIFICATE = 4;
  
  protected static final short CS_CERTIFICATE_STATUS = 5;
  
  protected static final short CS_SERVER_KEY_EXCHANGE = 6;
  
  protected static final short CS_CERTIFICATE_REQUEST = 7;
  
  protected static final short CS_SERVER_HELLO_DONE = 8;
  
  protected static final short CS_CLIENT_SUPPLEMENTAL_DATA = 9;
  
  protected static final short CS_CLIENT_CERTIFICATE = 10;
  
  protected static final short CS_CLIENT_KEY_EXCHANGE = 11;
  protected static final short CS_CERTIFICATE_VERIFY = 12;
  protected static final short CS_CLIENT_FINISHED = 13;
  protected static final short CS_SERVER_SESSION_TICKET = 14;
  protected static final short CS_SERVER_FINISHED = 15;
  protected static final short CS_END = 16;
  protected static final short ADS_MODE_1_Nsub1 = 0;
  protected static final short ADS_MODE_0_N = 1;
  protected static final short ADS_MODE_0_N_FIRSTONLY = 2;
  private ByteQueue applicationDataQueue = new ByteQueue(0);
  private ByteQueue alertQueue = new ByteQueue(2);
  private ByteQueue handshakeQueue = new ByteQueue(0);
  

  RecordStream recordStream;
  

  protected SecureRandom secureRandom;
  

  private TlsInputStream tlsInputStream = null;
  private TlsOutputStream tlsOutputStream = null;
  
  private volatile boolean closed = false;
  private volatile boolean failedWithError = false;
  private volatile boolean appDataReady = false;
  private volatile boolean appDataSplitEnabled = true;
  private volatile int appDataSplitMode = 0;
  private byte[] expected_verify_data = null;
  
  protected TlsSession tlsSession = null;
  protected SessionParameters sessionParameters = null;
  protected SecurityParameters securityParameters = null;
  protected Certificate peerCertificate = null;
  
  protected int[] offeredCipherSuites = null;
  protected short[] offeredCompressionMethods = null;
  protected Hashtable clientExtensions = null;
  protected Hashtable serverExtensions = null;
  
  protected short connection_state = 0;
  protected boolean resumedSession = false;
  protected boolean receivedChangeCipherSpec = false;
  protected boolean secure_renegotiation = false;
  protected boolean allowCertificateStatus = false;
  protected boolean expectSessionTicket = false;
  
  protected boolean blocking;
  protected ByteQueueInputStream inputBuffers;
  protected ByteQueueOutputStream outputBuffer;
  
  public TlsProtocol(InputStream input, OutputStream output, SecureRandom secureRandom)
  {
    blocking = true;
    recordStream = new RecordStream(this, input, output);
    this.secureRandom = secureRandom;
  }
  
  public TlsProtocol(SecureRandom secureRandom)
  {
    blocking = false;
    inputBuffers = new ByteQueueInputStream();
    outputBuffer = new ByteQueueOutputStream();
    recordStream = new RecordStream(this, inputBuffers, outputBuffer);
    this.secureRandom = secureRandom;
  }
  
  protected abstract TlsContext getContext();
  
  abstract AbstractTlsContext getContextAdmin();
  
  protected abstract TlsPeer getPeer();
  
  protected void handleAlertMessage(short alertLevel, short alertDescription)
    throws IOException
  {
    getPeer().notifyAlertReceived(alertLevel, alertDescription);
    
    if (alertLevel == 1)
    {
      handleAlertWarningMessage(alertDescription);
    }
    else
    {
      handleFailure();
      
      throw new TlsFatalAlertReceived(alertDescription);
    }
  }
  




  protected void handleAlertWarningMessage(short alertDescription)
    throws IOException
  {
    if (alertDescription == 0)
    {
      if (!appDataReady)
      {
        throw new TlsFatalAlert((short)40);
      }
      handleClose(false);
    }
  }
  
  protected void handleChangeCipherSpecMessage()
    throws IOException
  {}
  
  protected void handleClose(boolean user_canceled)
    throws IOException
  {
    if (!closed)
    {
      closed = true;
      
      if ((user_canceled) && (!appDataReady))
      {
        raiseAlertWarning((short)90, "User canceled handshake");
      }
      
      raiseAlertWarning((short)0, "Connection closed");
      
      recordStream.safeClose();
      
      if (!appDataReady)
      {
        cleanupHandshake();
      }
    }
  }
  
  protected void handleException(short alertDescription, String message, Throwable cause)
    throws IOException
  {
    if (!closed)
    {
      raiseAlertFatal(alertDescription, message, cause);
      
      handleFailure();
    }
  }
  
  protected void handleFailure()
  {
    closed = true;
    failedWithError = true;
    





    invalidateSession();
    
    recordStream.safeClose();
    
    if (!appDataReady)
    {
      cleanupHandshake();
    }
  }
  
  protected abstract void handleHandshakeMessage(short paramShort, ByteArrayInputStream paramByteArrayInputStream)
    throws IOException;
  
  protected void applyMaxFragmentLengthExtension()
    throws IOException
  {
    if (securityParameters.maxFragmentLength >= 0)
    {
      if (!MaxFragmentLength.isValid(securityParameters.maxFragmentLength))
      {
        throw new TlsFatalAlert((short)80);
      }
      
      int plainTextLimit = 1 << 8 + securityParameters.maxFragmentLength;
      recordStream.setPlaintextLimit(plainTextLimit);
    }
  }
  
  protected void checkReceivedChangeCipherSpec(boolean expected)
    throws IOException
  {
    if (expected != receivedChangeCipherSpec)
    {
      throw new TlsFatalAlert((short)10);
    }
  }
  
  protected void cleanupHandshake()
  {
    if (expected_verify_data != null)
    {
      Arrays.fill(expected_verify_data, (byte)0);
      expected_verify_data = null;
    }
    
    securityParameters.clear();
    peerCertificate = null;
    
    offeredCipherSuites = null;
    offeredCompressionMethods = null;
    clientExtensions = null;
    serverExtensions = null;
    
    resumedSession = false;
    receivedChangeCipherSpec = false;
    secure_renegotiation = false;
    allowCertificateStatus = false;
    expectSessionTicket = false;
  }
  
  protected void blockForHandshake() throws IOException
  {
    if (blocking)
    {
      while (connection_state != 16)
      {
        if (closed)
        {

          throw new TlsFatalAlert((short)80);
        }
        
        safeReadRecord();
      }
    }
  }
  
  protected void completeHandshake()
    throws IOException
  {
    try
    {
      connection_state = 16;
      
      alertQueue.shrink();
      handshakeQueue.shrink();
      
      recordStream.finaliseHandshake();
      
      appDataSplitEnabled = (!TlsUtils.isTLSv11(getContext()));
      



      if (!appDataReady)
      {
        appDataReady = true;
        
        if (blocking)
        {
          tlsInputStream = new TlsInputStream(this);
          tlsOutputStream = new TlsOutputStream(this);
        }
      }
      
      if (tlsSession != null)
      {
        if (sessionParameters == null)
        {









          sessionParameters = new SessionParameters.Builder().setCipherSuite(securityParameters.getCipherSuite()).setCompressionAlgorithm(securityParameters.getCompressionAlgorithm()).setMasterSecret(securityParameters.getMasterSecret()).setPeerCertificate(peerCertificate).setPSKIdentity(securityParameters.getPSKIdentity()).setSRPIdentity(securityParameters.getSRPIdentity()).setServerExtensions(serverExtensions).build();
          
          tlsSession = new TlsSessionImpl(tlsSession.getSessionID(), sessionParameters);
        }
        
        getContextAdmin().setResumableSession(tlsSession);
      }
      
      getPeer().notifyHandshakeComplete();
      


      cleanupHandshake(); } finally { cleanupHandshake();
    }
  }
  



  protected void processRecord(short protocol, byte[] buf, int off, int len)
    throws IOException
  {
    switch (protocol)
    {

    case 21: 
      alertQueue.addData(buf, off, len);
      processAlertQueue();
      break;
    

    case 23: 
      if (!appDataReady)
      {
        throw new TlsFatalAlert((short)10);
      }
      applicationDataQueue.addData(buf, off, len);
      processApplicationDataQueue();
      break;
    

    case 20: 
      processChangeCipherSpec(buf, off, len);
      break;
    

    case 22: 
      if (handshakeQueue.available() > 0)
      {
        handshakeQueue.addData(buf, off, len);
        processHandshakeQueue(handshakeQueue);
      }
      else
      {
        ByteQueue tmpQueue = new ByteQueue(buf, off, len);
        processHandshakeQueue(tmpQueue);
        int remaining = tmpQueue.available();
        if (remaining > 0)
        {
          handshakeQueue.addData(buf, off + len - remaining, remaining);
        }
      }
      break;
    












    default: 
      throw new TlsFatalAlert((short)80);
    }
    
  }
  
  private void processHandshakeQueue(ByteQueue queue) throws IOException
  {
    while (queue.available() >= 4)
    {



      byte[] beginning = new byte[4];
      queue.read(beginning, 0, 4, 0);
      short type = TlsUtils.readUint8(beginning, 0);
      int length = TlsUtils.readUint24(beginning, 1);
      int totalLength = 4 + length;
      



      if (queue.available() < totalLength) {
        break;
      }
      

      checkReceivedChangeCipherSpec((connection_state == 16) || (type == 20));
      





      switch (type)
      {
      case 0: 
        break;
      
      case 20: 
        TlsContext ctx = getContext();
        if ((expected_verify_data == null) && 
          (ctx.getSecurityParameters().getMasterSecret() != null))
        {
          expected_verify_data = createVerifyData(!ctx.isServer());
        }
        
        break;
      }
      
      queue.copyTo(recordStream.getHandshakeHashUpdater(), totalLength);
      


      queue.removeData(4);
      
      ByteArrayInputStream buf = queue.readFrom(length);
      



      handleHandshakeMessage(type, buf);
    }
  }
  




  private void processApplicationDataQueue() {}
  



  private void processAlertQueue()
    throws IOException
  {
    while (alertQueue.available() >= 2)
    {



      byte[] alert = alertQueue.removeData(2, 0);
      short alertLevel = (short)alert[0];
      short alertDescription = (short)alert[1];
      
      handleAlertMessage(alertLevel, alertDescription);
    }
  }
  






  private void processChangeCipherSpec(byte[] buf, int off, int len)
    throws IOException
  {
    for (int i = 0; i < len; i++)
    {
      short message = TlsUtils.readUint8(buf, off + i);
      
      if (message != 1)
      {
        throw new TlsFatalAlert((short)50);
      }
      
      if ((receivedChangeCipherSpec) || 
        (alertQueue.available() > 0) || 
        (handshakeQueue.available() > 0))
      {
        throw new TlsFatalAlert((short)10);
      }
      
      recordStream.receivedReadCipherSpec();
      
      receivedChangeCipherSpec = true;
      
      handleChangeCipherSpecMessage();
    }
  }
  
  protected int applicationDataAvailable()
  {
    return applicationDataQueue.available();
  }
  










  protected int readApplicationData(byte[] buf, int offset, int len)
    throws IOException
  {
    if (len < 1)
    {
      return 0;
    }
    
    while (applicationDataQueue.available() == 0)
    {
      if (closed)
      {
        if (failedWithError)
        {
          throw new IOException("Cannot read application data on failed TLS connection");
        }
        if (!appDataReady)
        {
          throw new IllegalStateException("Cannot read application data until initial handshake completed.");
        }
        
        return -1;
      }
      
      safeReadRecord();
    }
    
    len = Math.min(len, applicationDataQueue.available());
    applicationDataQueue.removeData(buf, offset, len, 0);
    return len;
  }
  
  protected void safeCheckRecordHeader(byte[] recordHeader)
    throws IOException
  {
    try
    {
      recordStream.checkRecordHeader(recordHeader);
    }
    catch (TlsFatalAlert e)
    {
      handleException(e.getAlertDescription(), "Failed to read record", e);
      throw e;
    }
    catch (IOException e)
    {
      handleException((short)80, "Failed to read record", e);
      throw e;
    }
    catch (RuntimeException e)
    {
      handleException((short)80, "Failed to read record", e);
      throw new TlsFatalAlert((short)80, e);
    }
  }
  
  protected void safeReadRecord()
    throws IOException
  {
    try
    {
      if (recordStream.readRecord())
      {
        return;
      }
      
      if (!appDataReady)
      {
        throw new TlsFatalAlert((short)40);
      }
      
    }
    catch (TlsFatalAlertReceived e)
    {
      throw e;
    }
    catch (TlsFatalAlert e)
    {
      handleException(e.getAlertDescription(), "Failed to read record", e);
      throw e;
    }
    catch (IOException e)
    {
      handleException((short)80, "Failed to read record", e);
      throw e;
    }
    catch (RuntimeException e)
    {
      handleException((short)80, "Failed to read record", e);
      throw new TlsFatalAlert((short)80, e);
    }
    
    handleFailure();
    
    throw new TlsNoCloseNotifyException();
  }
  
  protected void safeWriteRecord(short type, byte[] buf, int offset, int len)
    throws IOException
  {
    try
    {
      recordStream.writeRecord(type, buf, offset, len);
    }
    catch (TlsFatalAlert e)
    {
      handleException(e.getAlertDescription(), "Failed to write record", e);
      throw e;
    }
    catch (IOException e)
    {
      handleException((short)80, "Failed to write record", e);
      throw e;
    }
    catch (RuntimeException e)
    {
      handleException((short)80, "Failed to write record", e);
      throw new TlsFatalAlert((short)80, e);
    }
  }
  










  protected void writeData(byte[] buf, int offset, int len)
    throws IOException
  {
    if (closed)
    {
      throw new IOException("Cannot write application data on closed/failed TLS connection");
    }
    
    while (len > 0)
    {







      if (appDataSplitEnabled)
      {





        switch (appDataSplitMode) {
        case 2: 
          appDataSplitEnabled = false;
        
        case 1: 
          safeWriteRecord((short)23, TlsUtils.EMPTY_BYTES, 0, 0);
          break;
        case 0: 
        default: 
          safeWriteRecord((short)23, buf, offset, 1);
          offset++;
          len--;
        }
        
      }
      
      if (len > 0)
      {

        int toWrite = Math.min(len, recordStream.getPlaintextLimit());
        safeWriteRecord((short)23, buf, offset, toWrite);
        offset += toWrite;
        len -= toWrite;
      }
    }
  }
  
  protected void setAppDataSplitMode(int appDataSplitMode) {
    if ((appDataSplitMode < 0) || (appDataSplitMode > 2))
    {

      throw new IllegalArgumentException("Illegal appDataSplitMode mode: " + appDataSplitMode);
    }
    this.appDataSplitMode = appDataSplitMode;
  }
  
  protected void writeHandshakeMessage(byte[] buf, int off, int len) throws IOException
  {
    if (len < 4)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    short type = TlsUtils.readUint8(buf, off);
    if (type != 0)
    {
      recordStream.getHandshakeHashUpdater().write(buf, off, len);
    }
    
    int total = 0;
    
    do
    {
      int toWrite = Math.min(len - total, recordStream.getPlaintextLimit());
      safeWriteRecord((short)22, buf, off + total, toWrite);
      total += toWrite;
    }
    while (total < len);
  }
  



  public OutputStream getOutputStream()
  {
    if (!blocking)
    {
      throw new IllegalStateException("Cannot use OutputStream in non-blocking mode! Use offerOutput() instead.");
    }
    return tlsOutputStream;
  }
  



  public InputStream getInputStream()
  {
    if (!blocking)
    {
      throw new IllegalStateException("Cannot use InputStream in non-blocking mode! Use offerInput() instead.");
    }
    return tlsInputStream;
  }
  


  public void closeInput()
    throws IOException
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use closeInput() in blocking mode!");
    }
    
    if (closed)
    {
      return;
    }
    
    if (inputBuffers.available() > 0)
    {
      throw new EOFException();
    }
    
    if (!appDataReady)
    {
      throw new TlsFatalAlert((short)40);
    }
    
    throw new TlsNoCloseNotifyException();
  }
  

















  public void offerInput(byte[] input)
    throws IOException
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use offerInput() in blocking mode! Use getInputStream() instead.");
    }
    
    if (closed)
    {
      throw new IOException("Connection is closed, cannot accept any more input");
    }
    
    inputBuffers.addBytes(input);
    

    while (inputBuffers.available() >= 5)
    {
      byte[] recordHeader = new byte[5];
      inputBuffers.peek(recordHeader);
      
      int totalLength = TlsUtils.readUint16(recordHeader, 3) + 5;
      if (inputBuffers.available() < totalLength)
      {

        safeCheckRecordHeader(recordHeader);
        break;
      }
      
      safeReadRecord();
      
      if (closed)
      {
        if (connection_state == 16) {
          break;
        }
        throw new TlsFatalAlert((short)80);
      }
    }
  }
  









  public int getAvailableInputBytes()
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use getAvailableInputBytes() in blocking mode! Use getInputStream().available() instead.");
    }
    return applicationDataAvailable();
  }
  













  public int readInput(byte[] buffer, int offset, int length)
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use readInput() in blocking mode! Use getInputStream() instead.");
    }
    
    try
    {
      return readApplicationData(buffer, offset, Math.min(length, applicationDataAvailable()));

    }
    catch (IOException e)
    {
      throw new RuntimeException(e.toString());
    }
  }
  














  public void offerOutput(byte[] buffer, int offset, int length)
    throws IOException
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use offerOutput() in blocking mode! Use getOutputStream() instead.");
    }
    
    if (!appDataReady)
    {
      throw new IOException("Application data cannot be sent until the handshake is complete!");
    }
    
    writeData(buffer, offset, length);
  }
  








  public int getAvailableOutputBytes()
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use getAvailableOutputBytes() in blocking mode! Use getOutputStream() instead.");
    }
    
    return outputBuffer.getBuffer().available();
  }
  













  public int readOutput(byte[] buffer, int offset, int length)
  {
    if (blocking)
    {
      throw new IllegalStateException("Cannot use readOutput() in blocking mode! Use getOutputStream() instead.");
    }
    
    int bytesToRead = Math.min(getAvailableOutputBytes(), length);
    outputBuffer.getBuffer().removeData(buffer, offset, bytesToRead, 0);
    return bytesToRead;
  }
  
  protected void invalidateSession()
  {
    if (sessionParameters != null)
    {
      sessionParameters.clear();
      sessionParameters = null;
    }
    
    if (tlsSession != null)
    {
      tlsSession.invalidate();
      tlsSession = null;
    }
  }
  
  protected void processFinishedMessage(ByteArrayInputStream buf)
    throws IOException
  {
    if (expected_verify_data == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    byte[] verify_data = TlsUtils.readFully(expected_verify_data.length, buf);
    
    assertEmpty(buf);
    



    if (!Arrays.constantTimeAreEqual(expected_verify_data, verify_data))
    {



      throw new TlsFatalAlert((short)51);
    }
  }
  
  protected void raiseAlertFatal(short alertDescription, String message, Throwable cause)
    throws IOException
  {
    getPeer().notifyAlertRaised((short)2, alertDescription, message, cause);
    
    byte[] alert = { 2, (byte)alertDescription };
    
    try
    {
      recordStream.writeRecord((short)21, alert, 0, 2);
    }
    catch (Exception localException) {}
  }
  



  protected void raiseAlertWarning(short alertDescription, String message)
    throws IOException
  {
    getPeer().notifyAlertRaised((short)1, alertDescription, message, null);
    
    byte[] alert = { 1, (byte)alertDescription };
    
    safeWriteRecord((short)21, alert, 0, 2);
  }
  
  protected void sendCertificateMessage(Certificate certificate)
    throws IOException
  {
    if (certificate == null)
    {
      certificate = Certificate.EMPTY_CHAIN;
    }
    
    if (certificate.isEmpty())
    {
      TlsContext context = getContext();
      if (!context.isServer())
      {
        ProtocolVersion serverVersion = getContext().getServerVersion();
        if (serverVersion.isSSL())
        {
          String errorMessage = serverVersion.toString() + " client didn't provide credentials";
          raiseAlertWarning((short)41, errorMessage);
          return;
        }
      }
    }
    
    HandshakeMessage message = new HandshakeMessage((short)11);
    
    certificate.encode(message);
    
    message.writeToRecordStream();
  }
  
  protected void sendChangeCipherSpecMessage()
    throws IOException
  {
    byte[] message = { 1 };
    safeWriteRecord((short)20, message, 0, message.length);
    recordStream.sentWriteCipherSpec();
  }
  
  protected void sendFinishedMessage()
    throws IOException
  {
    byte[] verify_data = createVerifyData(getContext().isServer());
    
    HandshakeMessage message = new HandshakeMessage((short)20, verify_data.length);
    
    message.write(verify_data);
    
    message.writeToRecordStream();
  }
  
  protected void sendSupplementalDataMessage(Vector supplementalData)
    throws IOException
  {
    HandshakeMessage message = new HandshakeMessage((short)23);
    
    writeSupplementalData(message, supplementalData);
    
    message.writeToRecordStream();
  }
  
  protected byte[] createVerifyData(boolean isServer)
  {
    TlsContext context = getContext();
    String asciiLabel = isServer ? "server finished" : "client finished";
    byte[] sslSender = isServer ? TlsUtils.SSL_SERVER : TlsUtils.SSL_CLIENT;
    byte[] hash = getCurrentPRFHash(context, recordStream.getHandshakeHash(), sslSender);
    return TlsUtils.calculateVerifyData(context, asciiLabel, hash);
  }
  





  public void close()
    throws IOException
  {
    handleClose(true);
  }
  
  protected void flush()
    throws IOException
  {
    recordStream.flush();
  }
  
  public boolean isClosed()
  {
    return closed;
  }
  

  protected short processMaxFragmentLengthExtension(Hashtable clientExtensions, Hashtable serverExtensions, short alertDescription)
    throws IOException
  {
    short maxFragmentLength = TlsExtensionsUtils.getMaxFragmentLengthExtension(serverExtensions);
    if (maxFragmentLength >= 0)
    {
      if (MaxFragmentLength.isValid(maxFragmentLength)) { if (!resumedSession)
        {
          if (maxFragmentLength == TlsExtensionsUtils.getMaxFragmentLengthExtension(clientExtensions)) {} }
      } else {
        throw new TlsFatalAlert(alertDescription);
      }
    }
    return maxFragmentLength;
  }
  



  protected void refuseRenegotiation()
    throws IOException
  {
    if (TlsUtils.isSSL(getContext()))
    {
      throw new TlsFatalAlert((short)40);
    }
    
    raiseAlertWarning((short)100, "Renegotiation not supported");
  }
  






  protected static void assertEmpty(ByteArrayInputStream buf)
    throws IOException
  {
    if (buf.available() > 0)
    {
      throw new TlsFatalAlert((short)50);
    }
  }
  
  protected static byte[] createRandomBlock(boolean useGMTUnixTime, RandomGenerator randomGenerator)
  {
    byte[] result = new byte[32];
    randomGenerator.nextBytes(result);
    
    if (useGMTUnixTime)
    {
      TlsUtils.writeGMTUnixTime(result, 0);
    }
    
    return result;
  }
  
  protected static byte[] createRenegotiationInfo(byte[] renegotiated_connection)
    throws IOException
  {
    return TlsUtils.encodeOpaque8(renegotiated_connection);
  }
  
  protected static void establishMasterSecret(TlsContext context, TlsKeyExchange keyExchange)
    throws IOException
  {
    byte[] pre_master_secret = keyExchange.generatePremasterSecret();
    
    try
    {
      getSecurityParametersmasterSecret = TlsUtils.calculateMasterSecret(context, pre_master_secret);
      







      if (pre_master_secret != null)
      {
        Arrays.fill(pre_master_secret, (byte)0);
      }
    }
    finally
    {
      if (pre_master_secret != null)
      {
        Arrays.fill(pre_master_secret, (byte)0);
      }
    }
  }
  



  protected static byte[] getCurrentPRFHash(TlsContext context, TlsHandshakeHash handshakeHash, byte[] sslSender)
  {
    Digest d = handshakeHash.forkPRFHash();
    
    if ((sslSender != null) && (TlsUtils.isSSL(context)))
    {
      d.update(sslSender, 0, sslSender.length);
    }
    
    byte[] bs = new byte[d.getDigestSize()];
    d.doFinal(bs, 0);
    return bs;
  }
  
  protected static Hashtable readExtensions(ByteArrayInputStream input)
    throws IOException
  {
    if (input.available() < 1)
    {
      return null;
    }
    
    byte[] extBytes = TlsUtils.readOpaque16(input);
    
    assertEmpty(input);
    
    ByteArrayInputStream buf = new ByteArrayInputStream(extBytes);
    

    Hashtable extensions = new Hashtable();
    
    while (buf.available() > 0)
    {
      Integer extension_type = Integers.valueOf(TlsUtils.readUint16(buf));
      byte[] extension_data = TlsUtils.readOpaque16(buf);
      



      if (null != extensions.put(extension_type, extension_data))
      {
        throw new TlsFatalAlert((short)47);
      }
    }
    
    return extensions;
  }
  
  protected static Vector readSupplementalDataMessage(ByteArrayInputStream input)
    throws IOException
  {
    byte[] supp_data = TlsUtils.readOpaque24(input);
    
    assertEmpty(input);
    
    ByteArrayInputStream buf = new ByteArrayInputStream(supp_data);
    
    Vector supplementalData = new Vector();
    
    while (buf.available() > 0)
    {
      int supp_data_type = TlsUtils.readUint16(buf);
      byte[] data = TlsUtils.readOpaque16(buf);
      
      supplementalData.addElement(new SupplementalDataEntry(supp_data_type, data));
    }
    
    return supplementalData;
  }
  
  protected static void writeExtensions(OutputStream output, Hashtable extensions)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    




    writeSelectedExtensions(buf, extensions, true);
    writeSelectedExtensions(buf, extensions, false);
    
    byte[] extBytes = buf.toByteArray();
    
    TlsUtils.writeOpaque16(extBytes, output);
  }
  
  protected static void writeSelectedExtensions(OutputStream output, Hashtable extensions, boolean selectEmpty)
    throws IOException
  {
    Enumeration keys = extensions.keys();
    while (keys.hasMoreElements())
    {
      Integer key = (Integer)keys.nextElement();
      int extension_type = key.intValue();
      byte[] extension_data = (byte[])extensions.get(key);
      
      if (selectEmpty == (extension_data.length == 0))
      {
        TlsUtils.checkUint16(extension_type);
        TlsUtils.writeUint16(extension_type, output);
        TlsUtils.writeOpaque16(extension_data, output);
      }
    }
  }
  
  protected static void writeSupplementalData(OutputStream output, Vector supplementalData)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    
    for (int i = 0; i < supplementalData.size(); i++)
    {
      SupplementalDataEntry entry = (SupplementalDataEntry)supplementalData.elementAt(i);
      
      int supp_data_type = entry.getDataType();
      TlsUtils.checkUint16(supp_data_type);
      TlsUtils.writeUint16(supp_data_type, buf);
      TlsUtils.writeOpaque16(entry.getData(), buf);
    }
    
    byte[] supp_data = buf.toByteArray();
    
    TlsUtils.writeOpaque24(supp_data, output);
  }
  
  protected static int getPRFAlgorithm(TlsContext context, int ciphersuite) throws IOException
  {
    boolean isTLSv12 = TlsUtils.isTLSv12(context);
    
    switch (ciphersuite)
    {

    case 59: 
    case 60: 
    case 61: 
    case 62: 
    case 63: 
    case 64: 
    case 103: 
    case 104: 
    case 105: 
    case 106: 
    case 107: 
    case 108: 
    case 109: 
    case 156: 
    case 158: 
    case 160: 
    case 162: 
    case 164: 
    case 166: 
    case 168: 
    case 170: 
    case 172: 
    case 186: 
    case 187: 
    case 188: 
    case 189: 
    case 190: 
    case 191: 
    case 192: 
    case 193: 
    case 194: 
    case 195: 
    case 196: 
    case 197: 
    case 49187: 
    case 49189: 
    case 49191: 
    case 49193: 
    case 49195: 
    case 49197: 
    case 49199: 
    case 49201: 
    case 49266: 
    case 49268: 
    case 49270: 
    case 49272: 
    case 49274: 
    case 49276: 
    case 49278: 
    case 49280: 
    case 49282: 
    case 49284: 
    case 49286: 
    case 49288: 
    case 49290: 
    case 49292: 
    case 49294: 
    case 49296: 
    case 49298: 
    case 49308: 
    case 49309: 
    case 49310: 
    case 49311: 
    case 49312: 
    case 49313: 
    case 49314: 
    case 49315: 
    case 49316: 
    case 49317: 
    case 49318: 
    case 49319: 
    case 49320: 
    case 49321: 
    case 49322: 
    case 49323: 
    case 49324: 
    case 49325: 
    case 49326: 
    case 49327: 
    case 52392: 
    case 52393: 
    case 52394: 
    case 52395: 
    case 52396: 
    case 52397: 
    case 52398: 
    case 65280: 
    case 65281: 
    case 65282: 
    case 65283: 
    case 65284: 
    case 65285: 
    case 65296: 
    case 65297: 
    case 65298: 
    case 65299: 
    case 65300: 
    case 65301: 
      if (isTLSv12)
      {
        return 1;
      }
      throw new TlsFatalAlert((short)47);
    


    case 157: 
    case 159: 
    case 161: 
    case 163: 
    case 165: 
    case 167: 
    case 169: 
    case 171: 
    case 173: 
    case 49188: 
    case 49190: 
    case 49192: 
    case 49194: 
    case 49196: 
    case 49198: 
    case 49200: 
    case 49202: 
    case 49267: 
    case 49269: 
    case 49271: 
    case 49273: 
    case 49275: 
    case 49277: 
    case 49279: 
    case 49281: 
    case 49283: 
    case 49285: 
    case 49287: 
    case 49289: 
    case 49291: 
    case 49293: 
    case 49295: 
    case 49297: 
    case 49299: 
      if (isTLSv12)
      {
        return 2;
      }
      throw new TlsFatalAlert((short)47);
    


    case 175: 
    case 177: 
    case 179: 
    case 181: 
    case 183: 
    case 185: 
    case 49208: 
    case 49211: 
    case 49301: 
    case 49303: 
    case 49305: 
    case 49307: 
      if (isTLSv12)
      {
        return 2;
      }
      return 0;
    }
    
    

    if (isTLSv12)
    {
      return 1;
    }
    return 0;
  }
  
  class HandshakeMessage
    extends ByteArrayOutputStream
  {
    HandshakeMessage(short handshakeType)
      throws IOException
    {
      this(handshakeType, 60);
    }
    
    HandshakeMessage(short handshakeType, int length) throws IOException
    {
      super();
      TlsUtils.writeUint8(handshakeType, this);
      
      count += 3;
    }
    
    void writeToRecordStream()
      throws IOException
    {
      int length = count - 4;
      TlsUtils.checkUint24(length);
      TlsUtils.writeUint24(length, buf, 1);
      writeHandshakeMessage(buf, 0, count);
      buf = null;
    }
  }
}
