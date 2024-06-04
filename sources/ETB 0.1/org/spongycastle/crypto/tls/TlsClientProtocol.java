package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.util.Arrays;


public class TlsClientProtocol
  extends TlsProtocol
{
  protected TlsClient tlsClient = null;
  TlsClientContextImpl tlsClientContext = null;
  
  protected byte[] selectedSessionID = null;
  
  protected TlsKeyExchange keyExchange = null;
  protected TlsAuthentication authentication = null;
  
  protected CertificateStatus certificateStatus = null;
  protected CertificateRequest certificateRequest = null;
  






  public TlsClientProtocol(InputStream input, OutputStream output, SecureRandom secureRandom)
  {
    super(input, output, secureRandom);
  }
  















  public TlsClientProtocol(SecureRandom secureRandom)
  {
    super(secureRandom);
  }
  









  public void connect(TlsClient tlsClient)
    throws IOException
  {
    if (tlsClient == null)
    {
      throw new IllegalArgumentException("'tlsClient' cannot be null");
    }
    if (this.tlsClient != null)
    {
      throw new IllegalStateException("'connect' can only be called once");
    }
    
    this.tlsClient = tlsClient;
    
    securityParameters = new SecurityParameters();
    securityParameters.entity = 1;
    
    tlsClientContext = new TlsClientContextImpl(secureRandom, securityParameters);
    
    securityParameters.clientRandom = createRandomBlock(tlsClient.shouldUseGMTUnixTime(), tlsClientContext
      .getNonceRandomGenerator());
    
    this.tlsClient.init(tlsClientContext);
    recordStream.init(tlsClientContext);
    
    TlsSession sessionToResume = tlsClient.getSessionToResume();
    if ((sessionToResume != null) && (sessionToResume.isResumable()))
    {
      SessionParameters sessionParameters = sessionToResume.exportSessionParameters();
      if (sessionParameters != null)
      {
        tlsSession = sessionToResume;
        this.sessionParameters = sessionParameters;
      }
    }
    
    sendClientHelloMessage();
    connection_state = 1;
    
    blockForHandshake();
  }
  
  protected void cleanupHandshake()
  {
    super.cleanupHandshake();
    
    selectedSessionID = null;
    keyExchange = null;
    authentication = null;
    certificateStatus = null;
    certificateRequest = null;
  }
  
  protected TlsContext getContext()
  {
    return tlsClientContext;
  }
  
  AbstractTlsContext getContextAdmin()
  {
    return tlsClientContext;
  }
  
  protected TlsPeer getPeer()
  {
    return tlsClient;
  }
  
  protected void handleHandshakeMessage(short type, ByteArrayInputStream buf)
    throws IOException
  {
    if (resumedSession)
    {
      if ((type != 20) || (connection_state != 2))
      {
        throw new TlsFatalAlert((short)10);
      }
      
      processFinishedMessage(buf);
      connection_state = 15;
      
      sendFinishedMessage();
      connection_state = 13;
      
      completeHandshake();
      return;
    }
    
    switch (type)
    {

    case 11: 
      switch (connection_state)
      {

      case 2: 
        handleSupplementalData(null);
      




      case 3: 
        peerCertificate = Certificate.parse(buf);
        
        assertEmpty(buf);
        

        if ((peerCertificate == null) || (peerCertificate.isEmpty()))
        {
          allowCertificateStatus = false;
        }
        
        keyExchange.processServerCertificate(peerCertificate);
        
        authentication = tlsClient.getAuthentication();
        authentication.notifyServerCertificate(peerCertificate);
        
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      connection_state = 4;
      break;
    

    case 22: 
      switch (connection_state)
      {

      case 4: 
        if (!allowCertificateStatus)
        {





          throw new TlsFatalAlert((short)10);
        }
        
        certificateStatus = CertificateStatus.parse(buf);
        
        assertEmpty(buf);
        


        connection_state = 5;
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 20: 
      switch (connection_state)
      {

      case 13: 
        if (expectSessionTicket)
        {




          throw new TlsFatalAlert((short)10);
        }
      



      case 14: 
        processFinishedMessage(buf);
        connection_state = 15;
        
        completeHandshake();
        break;
      }
      
      throw new TlsFatalAlert((short)10);
    



    case 2: 
      switch (connection_state)
      {

      case 1: 
        receiveServerHelloMessage(buf);
        connection_state = 2;
        
        recordStream.notifyHelloComplete();
        
        applyMaxFragmentLengthExtension();
        
        if (resumedSession)
        {
          securityParameters.masterSecret = Arrays.clone(sessionParameters.getMasterSecret());
          recordStream.setPendingConnectionState(getPeer().getCompression(), getPeer().getCipher());
          
          sendChangeCipherSpecMessage();
        }
        else
        {
          invalidateSession();
          
          if (selectedSessionID.length > 0)
          {
            tlsSession = new TlsSessionImpl(selectedSessionID, null);
          }
        }
        

        break;
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 23: 
      switch (connection_state)
      {

      case 2: 
        handleSupplementalData(readSupplementalDataMessage(buf));
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 14: 
      switch (connection_state)
      {

      case 2: 
        handleSupplementalData(null);
      



      case 3: 
        keyExchange.skipServerCredentials();
        authentication = null;
      




      case 4: 
      case 5: 
        keyExchange.skipServerKeyExchange();
      



      case 6: 
      case 7: 
        assertEmpty(buf);
        
        connection_state = 8;
        
        recordStream.getHandshakeHash().sealHashAlgorithms();
        
        Vector clientSupplementalData = tlsClient.getClientSupplementalData();
        if (clientSupplementalData != null)
        {
          sendSupplementalDataMessage(clientSupplementalData);
        }
        connection_state = 9;
        
        TlsCredentials clientCreds = null;
        if (certificateRequest == null)
        {
          keyExchange.skipClientCredentials();
        }
        else
        {
          clientCreds = authentication.getClientCredentials(certificateRequest);
          
          if (clientCreds == null)
          {
            keyExchange.skipClientCredentials();
            






            sendCertificateMessage(Certificate.EMPTY_CHAIN);
          }
          else
          {
            keyExchange.processClientCredentials(clientCreds);
            
            sendCertificateMessage(clientCreds.getCertificate());
          }
        }
        
        connection_state = 10;
        




        sendClientKeyExchangeMessage();
        connection_state = 11;
        
        if (TlsUtils.isSSL(getContext()))
        {
          establishMasterSecret(getContext(), keyExchange);
        }
        
        TlsHandshakeHash prepareFinishHash = recordStream.prepareToFinish();
        securityParameters.sessionHash = getCurrentPRFHash(getContext(), prepareFinishHash, null);
        
        if (!TlsUtils.isSSL(getContext()))
        {
          establishMasterSecret(getContext(), keyExchange);
        }
        
        recordStream.setPendingConnectionState(getPeer().getCompression(), getPeer().getCipher());
        
        if ((clientCreds != null) && ((clientCreds instanceof TlsSignerCredentials)))
        {
          TlsSignerCredentials signerCredentials = (TlsSignerCredentials)clientCreds;
          



          SignatureAndHashAlgorithm signatureAndHashAlgorithm = TlsUtils.getSignatureAndHashAlgorithm(
            getContext(), signerCredentials);
          byte[] hash;
          byte[] hash;
          if (signatureAndHashAlgorithm == null)
          {
            hash = securityParameters.getSessionHash();
          }
          else
          {
            hash = prepareFinishHash.getFinalHash(signatureAndHashAlgorithm.getHash());
          }
          
          byte[] signature = signerCredentials.generateCertificateSignature(hash);
          DigitallySigned certificateVerify = new DigitallySigned(signatureAndHashAlgorithm, signature);
          sendCertificateVerifyMessage(certificateVerify);
          
          connection_state = 12;
        }
        
        sendChangeCipherSpecMessage();
        sendFinishedMessage();
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      connection_state = 13;
      break;
    

    case 12: 
      switch (connection_state)
      {

      case 2: 
        handleSupplementalData(null);
      



      case 3: 
        keyExchange.skipServerCredentials();
        authentication = null;
      



      case 4: 
      case 5: 
        keyExchange.processServerKeyExchange(buf);
        
        assertEmpty(buf);
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      connection_state = 6;
      break;
    

    case 13: 
      switch (connection_state)
      {


      case 4: 
      case 5: 
        keyExchange.skipServerKeyExchange();
      



      case 6: 
        if (authentication == null)
        {




          throw new TlsFatalAlert((short)40);
        }
        
        certificateRequest = CertificateRequest.parse(getContext(), buf);
        
        assertEmpty(buf);
        
        keyExchange.validateCertificateRequest(certificateRequest);
        




        TlsUtils.trackHashAlgorithms(recordStream.getHandshakeHash(), certificateRequest
          .getSupportedSignatureAlgorithms());
        
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      connection_state = 7;
      break;
    

    case 4: 
      switch (connection_state)
      {

      case 13: 
        if (!expectSessionTicket)
        {




          throw new TlsFatalAlert((short)10);
        }
        




        invalidateSession();
        
        receiveNewSessionTicketMessage(buf);
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      connection_state = 14;
      break;
    

    case 0: 
      assertEmpty(buf);
      






      if (connection_state == 16)
      {
        refuseRenegotiation();
      }
      break;
    case 1: case 3: case 5: 
    case 6: case 7: case 8: 
    case 9: case 10: case 15: 
    case 16: case 17: 
    case 18: case 19: 
    case 21: default: 
      throw new TlsFatalAlert((short)10);
    }
    
  }
  
  protected void handleSupplementalData(Vector serverSupplementalData) throws IOException
  {
    tlsClient.processServerSupplementalData(serverSupplementalData);
    connection_state = 3;
    
    keyExchange = tlsClient.getKeyExchange();
    keyExchange.init(getContext());
  }
  
  protected void receiveNewSessionTicketMessage(ByteArrayInputStream buf)
    throws IOException
  {
    NewSessionTicket newSessionTicket = NewSessionTicket.parse(buf);
    
    assertEmpty(buf);
    
    tlsClient.notifyNewSessionTicket(newSessionTicket);
  }
  

  protected void receiveServerHelloMessage(ByteArrayInputStream buf)
    throws IOException
  {
    ProtocolVersion server_version = TlsUtils.readVersion(buf);
    if (server_version.isDTLS())
    {
      throw new TlsFatalAlert((short)47);
    }
    

    if (!server_version.equals(recordStream.getReadVersion()))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    ProtocolVersion client_version = getContext().getClientVersion();
    if (!server_version.isEqualOrEarlierVersionOf(client_version))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    recordStream.setWriteVersion(server_version);
    getContextAdmin().setServerVersion(server_version);
    tlsClient.notifyServerVersion(server_version);
    




    securityParameters.serverRandom = TlsUtils.readFully(32, buf);
    
    selectedSessionID = TlsUtils.readOpaque8(buf);
    if (selectedSessionID.length > 32)
    {
      throw new TlsFatalAlert((short)47);
    }
    tlsClient.notifySessionID(selectedSessionID);
    
    resumedSession = ((selectedSessionID.length > 0) && (tlsSession != null) && (Arrays.areEqual(selectedSessionID, tlsSession.getSessionID())));
    




    int selectedCipherSuite = TlsUtils.readUint16(buf);
    if ((!Arrays.contains(offeredCipherSuites, selectedCipherSuite)) || (selectedCipherSuite == 0) || 
    
      (CipherSuite.isSCSV(selectedCipherSuite)) || 
      (!TlsUtils.isValidCipherSuiteForVersion(selectedCipherSuite, getContext().getServerVersion())))
    {
      throw new TlsFatalAlert((short)47);
    }
    tlsClient.notifySelectedCipherSuite(selectedCipherSuite);
    




    short selectedCompressionMethod = TlsUtils.readUint8(buf);
    if (!Arrays.contains(offeredCompressionMethods, selectedCompressionMethod))
    {
      throw new TlsFatalAlert((short)47);
    }
    tlsClient.notifySelectedCompressionMethod(selectedCompressionMethod);
    








    serverExtensions = readExtensions(buf);
    







    if (serverExtensions != null)
    {
      Enumeration e = serverExtensions.keys();
      while (e.hasMoreElements())
      {
        Integer extType = (Integer)e.nextElement();
        







        if (!extType.equals(EXT_RenegotiationInfo))
        {










          if (null == TlsUtils.getExtensionData(clientExtensions, extType))
          {
            throw new TlsFatalAlert((short)110);
          }
          





          if (!resumedSession) {}
        }
      }
    }
    













    byte[] renegExtData = TlsUtils.getExtensionData(serverExtensions, EXT_RenegotiationInfo);
    if (renegExtData != null)
    {






      secure_renegotiation = true;
      
      if (!Arrays.constantTimeAreEqual(renegExtData, createRenegotiationInfo(TlsUtils.EMPTY_BYTES)))
      {
        throw new TlsFatalAlert((short)40);
      }
    }
    


    tlsClient.notifySecureRenegotiation(secure_renegotiation);
    
    Hashtable sessionClientExtensions = clientExtensions;Hashtable sessionServerExtensions = serverExtensions;
    if (resumedSession)
    {
      if ((selectedCipherSuite != sessionParameters.getCipherSuite()) || 
        (selectedCompressionMethod != sessionParameters.getCompressionAlgorithm()))
      {
        throw new TlsFatalAlert((short)47);
      }
      
      sessionClientExtensions = null;
      sessionServerExtensions = sessionParameters.readServerExtensions();
    }
    
    securityParameters.cipherSuite = selectedCipherSuite;
    securityParameters.compressionAlgorithm = selectedCompressionMethod;
    
    if (sessionServerExtensions != null)
    {







      boolean serverSentEncryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(sessionServerExtensions);
      if ((serverSentEncryptThenMAC) && (!TlsUtils.isBlockCipherSuite(selectedCipherSuite)))
      {
        throw new TlsFatalAlert((short)47);
      }
      securityParameters.encryptThenMAC = serverSentEncryptThenMAC;
      

      securityParameters.extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(sessionServerExtensions);
      
      securityParameters.maxFragmentLength = processMaxFragmentLengthExtension(sessionClientExtensions, sessionServerExtensions, (short)47);
      

      securityParameters.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(sessionServerExtensions);
      





      allowCertificateStatus = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(sessionServerExtensions, TlsExtensionsUtils.EXT_status_request, (short)47)));
      


      expectSessionTicket = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(sessionServerExtensions, TlsProtocol.EXT_SessionTicket, (short)47)));
    }
    








    if (sessionClientExtensions != null)
    {
      tlsClient.processServerExtensions(sessionServerExtensions);
    }
    
    securityParameters.prfAlgorithm = getPRFAlgorithm(getContext(), securityParameters
      .getCipherSuite());
    





    securityParameters.verifyDataLength = 12;
  }
  
  protected void sendCertificateVerifyMessage(DigitallySigned certificateVerify)
    throws IOException
  {
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)15);
    
    certificateVerify.encode(message);
    
    message.writeToRecordStream();
  }
  
  protected void sendClientHelloMessage()
    throws IOException
  {
    recordStream.setWriteVersion(tlsClient.getClientHelloRecordLayerVersion());
    
    ProtocolVersion client_version = tlsClient.getClientVersion();
    if (client_version.isDTLS())
    {
      throw new TlsFatalAlert((short)80);
    }
    
    getContextAdmin().setClientVersion(client_version);
    




    byte[] session_id = TlsUtils.EMPTY_BYTES;
    if (tlsSession != null)
    {
      session_id = tlsSession.getSessionID();
      if ((session_id == null) || (session_id.length > 32))
      {
        session_id = TlsUtils.EMPTY_BYTES;
      }
    }
    
    boolean fallback = tlsClient.isFallback();
    
    offeredCipherSuites = tlsClient.getCipherSuites();
    
    offeredCompressionMethods = tlsClient.getCompressionMethods();
    
    if ((session_id.length > 0) && (sessionParameters != null))
    {
      if ((!Arrays.contains(offeredCipherSuites, sessionParameters.getCipherSuite())) || 
        (!Arrays.contains(offeredCompressionMethods, sessionParameters.getCompressionAlgorithm())))
      {
        session_id = TlsUtils.EMPTY_BYTES;
      }
    }
    
    clientExtensions = tlsClient.getClientExtensions();
    
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)1);
    
    TlsUtils.writeVersion(client_version, message);
    
    message.write(securityParameters.getClientRandom());
    
    TlsUtils.writeOpaque8(session_id, message);
    







    byte[] renegExtData = TlsUtils.getExtensionData(clientExtensions, EXT_RenegotiationInfo);
    boolean noRenegExt = null == renegExtData;
    
    boolean noRenegSCSV = !Arrays.contains(offeredCipherSuites, 255);
    
    if ((noRenegExt) && (noRenegSCSV))
    {



      offeredCipherSuites = Arrays.append(offeredCipherSuites, 255);
    }
    







    if ((fallback) && (!Arrays.contains(offeredCipherSuites, 22016)))
    {
      offeredCipherSuites = Arrays.append(offeredCipherSuites, 22016);
    }
    
    TlsUtils.writeUint16ArrayWithUint16Length(offeredCipherSuites, message);
    

    TlsUtils.writeUint8ArrayWithUint8Length(offeredCompressionMethods, message);
    
    if (clientExtensions != null)
    {
      writeExtensions(message, clientExtensions);
    }
    
    message.writeToRecordStream();
  }
  
  protected void sendClientKeyExchangeMessage()
    throws IOException
  {
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)16);
    
    keyExchange.generateClientKeyExchange(message);
    
    message.writeToRecordStream();
  }
}
