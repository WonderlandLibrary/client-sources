package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;

public class TlsServerProtocol
  extends TlsProtocol
{
  protected TlsServer tlsServer = null;
  TlsServerContextImpl tlsServerContext = null;
  
  protected TlsKeyExchange keyExchange = null;
  protected TlsCredentials serverCredentials = null;
  protected CertificateRequest certificateRequest = null;
  
  protected short clientCertificateType = -1;
  protected TlsHandshakeHash prepareFinishHash = null;
  






  public TlsServerProtocol(InputStream input, OutputStream output, SecureRandom secureRandom)
  {
    super(input, output, secureRandom);
  }
  















  public TlsServerProtocol(SecureRandom secureRandom)
  {
    super(secureRandom);
  }
  










  public void accept(TlsServer tlsServer)
    throws IOException
  {
    if (tlsServer == null)
    {
      throw new IllegalArgumentException("'tlsServer' cannot be null");
    }
    if (this.tlsServer != null)
    {
      throw new IllegalStateException("'accept' can only be called once");
    }
    
    this.tlsServer = tlsServer;
    
    securityParameters = new SecurityParameters();
    securityParameters.entity = 0;
    
    tlsServerContext = new TlsServerContextImpl(secureRandom, securityParameters);
    
    securityParameters.serverRandom = createRandomBlock(tlsServer.shouldUseGMTUnixTime(), tlsServerContext
      .getNonceRandomGenerator());
    
    this.tlsServer.init(tlsServerContext);
    recordStream.init(tlsServerContext);
    
    recordStream.setRestrictReadVersion(false);
    
    blockForHandshake();
  }
  
  protected void cleanupHandshake()
  {
    super.cleanupHandshake();
    
    keyExchange = null;
    serverCredentials = null;
    certificateRequest = null;
    prepareFinishHash = null;
  }
  
  protected TlsContext getContext()
  {
    return tlsServerContext;
  }
  
  AbstractTlsContext getContextAdmin()
  {
    return tlsServerContext;
  }
  
  protected TlsPeer getPeer()
  {
    return tlsServer;
  }
  
  protected void handleHandshakeMessage(short type, ByteArrayInputStream buf)
    throws IOException
  {
    switch (type)
    {

    case 1: 
      switch (connection_state)
      {

      case 0: 
        receiveClientHelloMessage(buf);
        connection_state = 1;
        
        sendServerHelloMessage();
        connection_state = 2;
        
        recordStream.notifyHelloComplete();
        
        Vector serverSupplementalData = tlsServer.getServerSupplementalData();
        if (serverSupplementalData != null)
        {
          sendSupplementalDataMessage(serverSupplementalData);
        }
        connection_state = 3;
        
        keyExchange = tlsServer.getKeyExchange();
        keyExchange.init(getContext());
        
        serverCredentials = tlsServer.getCredentials();
        
        Certificate serverCertificate = null;
        
        if (serverCredentials == null)
        {
          keyExchange.skipServerCredentials();
        }
        else
        {
          keyExchange.processServerCredentials(serverCredentials);
          
          serverCertificate = serverCredentials.getCertificate();
          sendCertificateMessage(serverCertificate);
        }
        connection_state = 4;
        

        if ((serverCertificate == null) || (serverCertificate.isEmpty()))
        {
          allowCertificateStatus = false;
        }
        
        if (allowCertificateStatus)
        {
          CertificateStatus certificateStatus = tlsServer.getCertificateStatus();
          if (certificateStatus != null)
          {
            sendCertificateStatusMessage(certificateStatus);
          }
        }
        
        connection_state = 5;
        
        byte[] serverKeyExchange = keyExchange.generateServerKeyExchange();
        if (serverKeyExchange != null)
        {
          sendServerKeyExchangeMessage(serverKeyExchange);
        }
        connection_state = 6;
        
        if (serverCredentials != null)
        {
          certificateRequest = tlsServer.getCertificateRequest();
          if (certificateRequest != null)
          {
            if (TlsUtils.isTLSv12(getContext()) != (certificateRequest.getSupportedSignatureAlgorithms() != null))
            {
              throw new TlsFatalAlert((short)80);
            }
            
            keyExchange.validateCertificateRequest(certificateRequest);
            
            sendCertificateRequestMessage(certificateRequest);
            
            TlsUtils.trackHashAlgorithms(recordStream.getHandshakeHash(), certificateRequest
              .getSupportedSignatureAlgorithms());
          }
        }
        connection_state = 7;
        
        sendServerHelloDoneMessage();
        connection_state = 8;
        
        recordStream.getHandshakeHash().sealHashAlgorithms();
        
        break;
      

      case 16: 
        refuseRenegotiation();
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 23: 
      switch (connection_state)
      {

      case 8: 
        tlsServer.processClientSupplementalData(readSupplementalDataMessage(buf));
        connection_state = 9;
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 11: 
      switch (connection_state)
      {

      case 8: 
        tlsServer.processClientSupplementalData(null);
      


      case 9: 
        if (certificateRequest == null)
        {
          throw new TlsFatalAlert((short)10);
        }
        receiveCertificateMessage(buf);
        connection_state = 10;
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 16: 
      switch (connection_state)
      {

      case 8: 
        tlsServer.processClientSupplementalData(null);
      


      case 9: 
        if (certificateRequest == null)
        {
          keyExchange.skipClientCredentials();
        }
        else
        {
          if (TlsUtils.isTLSv12(getContext()))
          {






            throw new TlsFatalAlert((short)10);
          }
          if (TlsUtils.isSSL(getContext()))
          {
            if (peerCertificate == null)
            {
              throw new TlsFatalAlert((short)10);
            }
            
          }
          else {
            notifyClientCertificate(Certificate.EMPTY_CHAIN);
          }
        }
      


      case 10: 
        receiveClientKeyExchangeMessage(buf);
        connection_state = 11;
        break;
      }
      
      throw new TlsFatalAlert((short)10);
    



    case 15: 
      switch (connection_state)
      {






      case 11: 
        if (!expectCertificateVerifyMessage())
        {
          throw new TlsFatalAlert((short)10);
        }
        
        receiveCertificateVerifyMessage(buf);
        connection_state = 12;
        
        break;
      
      default: 
        throw new TlsFatalAlert((short)10);
      }
      
      
      break;
    case 20: 
      switch (connection_state)
      {

      case 11: 
        if (expectCertificateVerifyMessage())
        {
          throw new TlsFatalAlert((short)10);
        }
      


      case 12: 
        processFinishedMessage(buf);
        connection_state = 13;
        
        if (expectSessionTicket)
        {
          sendNewSessionTicketMessage(tlsServer.getNewSessionTicket());
          sendChangeCipherSpecMessage();
        }
        connection_state = 14;
        
        sendFinishedMessage();
        connection_state = 15;
        
        completeHandshake();
        break;
      }
      
      throw new TlsFatalAlert((short)10);
    case 0: case 2: 
    case 3: case 4: 
    case 5: case 6: 
    case 7: case 8: 
    case 9: case 10: 
    case 12: case 13: 
    case 14: case 17: 
    case 18: case 19: 
    case 21: 
    case 22: 
    default: 
      throw new TlsFatalAlert((short)10);
    }
    
  }
  
  protected void handleAlertWarningMessage(short alertDescription) throws IOException
  {
    super.handleAlertWarningMessage(alertDescription);
    
    switch (alertDescription)
    {





    case 41: 
      if ((TlsUtils.isSSL(getContext())) && (certificateRequest != null))
      {
        switch (connection_state)
        {

        case 8: 
          tlsServer.processClientSupplementalData(null);
        


        case 9: 
          notifyClientCertificate(Certificate.EMPTY_CHAIN);
          connection_state = 10;
          return;
        }
        
      }
      throw new TlsFatalAlert((short)10);
    }
    
  }
  
  protected void notifyClientCertificate(Certificate clientCertificate)
    throws IOException
  {
    if (certificateRequest == null)
    {
      throw new IllegalStateException();
    }
    
    if (peerCertificate != null)
    {
      throw new TlsFatalAlert((short)10);
    }
    
    peerCertificate = clientCertificate;
    
    if (clientCertificate.isEmpty())
    {
      keyExchange.skipClientCredentials();




    }
    else
    {



      clientCertificateType = TlsUtils.getClientCertificateType(clientCertificate, serverCredentials
        .getCertificate());
      
      keyExchange.processClientCertificate(clientCertificate);
    }
    








    tlsServer.notifyClientCertificate(clientCertificate);
  }
  
  protected void receiveCertificateMessage(ByteArrayInputStream buf)
    throws IOException
  {
    Certificate clientCertificate = Certificate.parse(buf);
    
    assertEmpty(buf);
    
    notifyClientCertificate(clientCertificate);
  }
  
  protected void receiveCertificateVerifyMessage(ByteArrayInputStream buf)
    throws IOException
  {
    if (certificateRequest == null)
    {
      throw new IllegalStateException();
    }
    
    DigitallySigned clientCertificateVerify = DigitallySigned.parse(getContext(), buf);
    
    assertEmpty(buf);
    

    try
    {
      SignatureAndHashAlgorithm signatureAlgorithm = clientCertificateVerify.getAlgorithm();
      byte[] hash;
      byte[] hash;
      if (TlsUtils.isTLSv12(getContext()))
      {
        TlsUtils.verifySupportedSignatureAlgorithm(certificateRequest.getSupportedSignatureAlgorithms(), signatureAlgorithm);
        hash = prepareFinishHash.getFinalHash(signatureAlgorithm.getHash());
      }
      else
      {
        hash = securityParameters.getSessionHash();
      }
      
      org.spongycastle.asn1.x509.Certificate x509Cert = peerCertificate.getCertificateAt(0);
      SubjectPublicKeyInfo keyInfo = x509Cert.getSubjectPublicKeyInfo();
      AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(keyInfo);
      
      TlsSigner tlsSigner = TlsUtils.createTlsSigner(clientCertificateType);
      tlsSigner.init(getContext());
      if (!tlsSigner.verifyRawSignature(signatureAlgorithm, clientCertificateVerify.getSignature(), publicKey, hash))
      {
        throw new TlsFatalAlert((short)51);
      }
    }
    catch (TlsFatalAlert e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new TlsFatalAlert((short)51, e);
    }
  }
  
  protected void receiveClientHelloMessage(ByteArrayInputStream buf)
    throws IOException
  {
    ProtocolVersion client_version = TlsUtils.readVersion(buf);
    recordStream.setWriteVersion(client_version);
    
    if (client_version.isDTLS())
    {
      throw new TlsFatalAlert((short)47);
    }
    
    byte[] client_random = TlsUtils.readFully(32, buf);
    




    byte[] sessionID = TlsUtils.readOpaque8(buf);
    if (sessionID.length > 32)
    {
      throw new TlsFatalAlert((short)47);
    }
    





    int cipher_suites_length = TlsUtils.readUint16(buf);
    if ((cipher_suites_length < 2) || ((cipher_suites_length & 0x1) != 0))
    {
      throw new TlsFatalAlert((short)50);
    }
    offeredCipherSuites = TlsUtils.readUint16Array(cipher_suites_length / 2, buf);
    




    int compression_methods_length = TlsUtils.readUint8(buf);
    if (compression_methods_length < 1)
    {
      throw new TlsFatalAlert((short)47);
    }
    offeredCompressionMethods = TlsUtils.readUint8Array(compression_methods_length, buf);
    





    clientExtensions = readExtensions(buf);
    






    securityParameters.extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(clientExtensions);
    
    getContextAdmin().setClientVersion(client_version);
    
    tlsServer.notifyClientVersion(client_version);
    tlsServer.notifyFallback(Arrays.contains(offeredCipherSuites, 22016));
    
    securityParameters.clientRandom = client_random;
    
    tlsServer.notifyOfferedCipherSuites(offeredCipherSuites);
    tlsServer.notifyOfferedCompressionMethods(offeredCompressionMethods);
    















    if (Arrays.contains(offeredCipherSuites, 255))
    {
      secure_renegotiation = true;
    }
    




    byte[] renegExtData = TlsUtils.getExtensionData(clientExtensions, EXT_RenegotiationInfo);
    if (renegExtData != null)
    {





      secure_renegotiation = true;
      
      if (!Arrays.constantTimeAreEqual(renegExtData, createRenegotiationInfo(TlsUtils.EMPTY_BYTES)))
      {
        throw new TlsFatalAlert((short)40);
      }
    }
    

    tlsServer.notifySecureRenegotiation(secure_renegotiation);
    
    if (clientExtensions != null)
    {

      TlsExtensionsUtils.getPaddingExtension(clientExtensions);
      
      tlsServer.processClientExtensions(clientExtensions);
    }
  }
  
  protected void receiveClientKeyExchangeMessage(ByteArrayInputStream buf)
    throws IOException
  {
    keyExchange.processClientKeyExchange(buf);
    
    assertEmpty(buf);
    
    if (TlsUtils.isSSL(getContext()))
    {
      establishMasterSecret(getContext(), keyExchange);
    }
    
    prepareFinishHash = recordStream.prepareToFinish();
    securityParameters.sessionHash = getCurrentPRFHash(getContext(), prepareFinishHash, null);
    
    if (!TlsUtils.isSSL(getContext()))
    {
      establishMasterSecret(getContext(), keyExchange);
    }
    
    recordStream.setPendingConnectionState(getPeer().getCompression(), getPeer().getCipher());
    
    if (!expectSessionTicket)
    {
      sendChangeCipherSpecMessage();
    }
  }
  
  protected void sendCertificateRequestMessage(CertificateRequest certificateRequest)
    throws IOException
  {
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)13);
    
    certificateRequest.encode(message);
    
    message.writeToRecordStream();
  }
  
  protected void sendCertificateStatusMessage(CertificateStatus certificateStatus)
    throws IOException
  {
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)22);
    
    certificateStatus.encode(message);
    
    message.writeToRecordStream();
  }
  
  protected void sendNewSessionTicketMessage(NewSessionTicket newSessionTicket)
    throws IOException
  {
    if (newSessionTicket == null)
    {
      throw new TlsFatalAlert((short)80);
    }
    
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)4);
    
    newSessionTicket.encode(message);
    
    message.writeToRecordStream();
  }
  
  protected void sendServerHelloMessage()
    throws IOException
  {
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)2);
    

    ProtocolVersion server_version = tlsServer.getServerVersion();
    if (!server_version.isEqualOrEarlierVersionOf(getContext().getClientVersion()))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    recordStream.setReadVersion(server_version);
    recordStream.setWriteVersion(server_version);
    recordStream.setRestrictReadVersion(true);
    getContextAdmin().setServerVersion(server_version);
    
    TlsUtils.writeVersion(server_version, message);
    

    message.write(securityParameters.serverRandom);
    




    TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, message);
    
    int selectedCipherSuite = tlsServer.getSelectedCipherSuite();
    if ((!Arrays.contains(offeredCipherSuites, selectedCipherSuite)) || (selectedCipherSuite == 0) || 
    
      (CipherSuite.isSCSV(selectedCipherSuite)) || 
      (!TlsUtils.isValidCipherSuiteForVersion(selectedCipherSuite, getContext().getServerVersion())))
    {
      throw new TlsFatalAlert((short)80);
    }
    securityParameters.cipherSuite = selectedCipherSuite;
    
    short selectedCompressionMethod = tlsServer.getSelectedCompressionMethod();
    if (!Arrays.contains(offeredCompressionMethods, selectedCompressionMethod))
    {
      throw new TlsFatalAlert((short)80);
    }
    securityParameters.compressionAlgorithm = selectedCompressionMethod;
    
    TlsUtils.writeUint16(selectedCipherSuite, message);
    TlsUtils.writeUint8(selectedCompressionMethod, message);
    
    serverExtensions = tlsServer.getServerExtensions();
    



    if (secure_renegotiation)
    {
      byte[] renegExtData = TlsUtils.getExtensionData(serverExtensions, EXT_RenegotiationInfo);
      boolean noRenegExt = null == renegExtData;
      
      if (noRenegExt)
      {












        serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(serverExtensions);
        serverExtensions.put(EXT_RenegotiationInfo, createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
      }
    }
    
    if (securityParameters.extendedMasterSecret)
    {
      serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(serverExtensions);
      TlsExtensionsUtils.addExtendedMasterSecretExtension(serverExtensions);
    }
    






    if (serverExtensions != null)
    {
      securityParameters.encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(serverExtensions);
      
      securityParameters.maxFragmentLength = processMaxFragmentLengthExtension(clientExtensions, serverExtensions, (short)80);
      

      securityParameters.truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(serverExtensions);
      





      allowCertificateStatus = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(serverExtensions, TlsExtensionsUtils.EXT_status_request, (short)80)));
      


      expectSessionTicket = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(serverExtensions, TlsProtocol.EXT_SessionTicket, (short)80)));
      

      writeExtensions(message, serverExtensions);
    }
    
    securityParameters.prfAlgorithm = getPRFAlgorithm(getContext(), securityParameters.getCipherSuite());
    




    securityParameters.verifyDataLength = 12;
    
    applyMaxFragmentLengthExtension();
    
    message.writeToRecordStream();
  }
  
  protected void sendServerHelloDoneMessage()
    throws IOException
  {
    byte[] message = new byte[4];
    TlsUtils.writeUint8((short)14, message, 0);
    TlsUtils.writeUint24(0, message, 1);
    
    writeHandshakeMessage(message, 0, message.length);
  }
  
  protected void sendServerKeyExchangeMessage(byte[] serverKeyExchange)
    throws IOException
  {
    TlsProtocol.HandshakeMessage message = new TlsProtocol.HandshakeMessage(this, (short)12, serverKeyExchange.length);
    
    message.write(serverKeyExchange);
    
    message.writeToRecordStream();
  }
  
  protected boolean expectCertificateVerifyMessage()
  {
    return (clientCertificateType >= 0) && (TlsUtils.hasSigningCapability(clientCertificateType));
  }
}
