package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;


public class DTLSServerProtocol
  extends DTLSProtocol
{
  protected boolean verifyRequests = true;
  
  public DTLSServerProtocol(SecureRandom secureRandom)
  {
    super(secureRandom);
  }
  
  public boolean getVerifyRequests()
  {
    return verifyRequests;
  }
  
  public void setVerifyRequests(boolean verifyRequests)
  {
    this.verifyRequests = verifyRequests;
  }
  
  public DTLSTransport accept(TlsServer server, DatagramTransport transport)
    throws IOException
  {
    if (server == null)
    {
      throw new IllegalArgumentException("'server' cannot be null");
    }
    if (transport == null)
    {
      throw new IllegalArgumentException("'transport' cannot be null");
    }
    
    SecurityParameters securityParameters = new SecurityParameters();
    entity = 0;
    
    ServerHandshakeState state = new ServerHandshakeState();
    server = server;
    serverContext = new TlsServerContextImpl(secureRandom, securityParameters);
    
    serverRandom = TlsProtocol.createRandomBlock(server.shouldUseGMTUnixTime(), serverContext
      .getNonceRandomGenerator());
    
    server.init(serverContext);
    
    DTLSRecordLayer recordLayer = new DTLSRecordLayer(transport, serverContext, server, (short)22);
    


    try
    {
      return serverHandshake(state, recordLayer);
    }
    catch (TlsFatalAlert fatalAlert)
    {
      abortServerHandshake(state, recordLayer, fatalAlert.getAlertDescription());
      throw fatalAlert;
    }
    catch (IOException e)
    {
      abortServerHandshake(state, recordLayer, (short)80);
      throw e;
    }
    catch (RuntimeException e)
    {
      abortServerHandshake(state, recordLayer, (short)80);
      throw new TlsFatalAlert((short)80, e);
    }
    finally
    {
      securityParameters.clear();
    }
  }
  
  protected void abortServerHandshake(ServerHandshakeState state, DTLSRecordLayer recordLayer, short alertDescription)
  {
    recordLayer.fail(alertDescription);
    invalidateSession(state);
  }
  
  protected DTLSTransport serverHandshake(ServerHandshakeState state, DTLSRecordLayer recordLayer)
    throws IOException
  {
    SecurityParameters securityParameters = serverContext.getSecurityParameters();
    DTLSReliableHandshake handshake = new DTLSReliableHandshake(serverContext, recordLayer);
    
    DTLSReliableHandshake.Message clientMessage = handshake.receiveMessage();
    



    if (clientMessage.getType() == 1)
    {
      processClientHello(state, clientMessage.getBody());
    }
    else
    {
      throw new TlsFatalAlert((short)10);
    }
    

    byte[] serverHelloBody = generateServerHello(state);
    
    applyMaxFragmentLengthExtension(recordLayer, maxFragmentLength);
    
    ProtocolVersion recordLayerVersion = serverContext.getServerVersion();
    recordLayer.setReadVersion(recordLayerVersion);
    recordLayer.setWriteVersion(recordLayerVersion);
    
    handshake.sendMessage((short)2, serverHelloBody);
    

    handshake.notifyHelloComplete();
    
    Vector serverSupplementalData = server.getServerSupplementalData();
    if (serverSupplementalData != null)
    {
      byte[] supplementalDataBody = generateSupplementalData(serverSupplementalData);
      handshake.sendMessage((short)23, supplementalDataBody);
    }
    
    keyExchange = server.getKeyExchange();
    keyExchange.init(serverContext);
    
    serverCredentials = server.getCredentials();
    
    Certificate serverCertificate = null;
    
    if (serverCredentials == null)
    {
      keyExchange.skipServerCredentials();
    }
    else
    {
      keyExchange.processServerCredentials(serverCredentials);
      
      serverCertificate = serverCredentials.getCertificate();
      byte[] certificateBody = generateCertificate(serverCertificate);
      handshake.sendMessage((short)11, certificateBody);
    }
    

    if ((serverCertificate == null) || (serverCertificate.isEmpty()))
    {
      allowCertificateStatus = false;
    }
    
    if (allowCertificateStatus)
    {
      CertificateStatus certificateStatus = server.getCertificateStatus();
      if (certificateStatus != null)
      {
        byte[] certificateStatusBody = generateCertificateStatus(state, certificateStatus);
        handshake.sendMessage((short)22, certificateStatusBody);
      }
    }
    
    byte[] serverKeyExchange = keyExchange.generateServerKeyExchange();
    if (serverKeyExchange != null)
    {
      handshake.sendMessage((short)12, serverKeyExchange);
    }
    
    if (serverCredentials != null)
    {
      certificateRequest = server.getCertificateRequest();
      if (certificateRequest != null)
      {
        if (TlsUtils.isTLSv12(serverContext) != (certificateRequest.getSupportedSignatureAlgorithms() != null))
        {
          throw new TlsFatalAlert((short)80);
        }
        
        keyExchange.validateCertificateRequest(certificateRequest);
        
        byte[] certificateRequestBody = generateCertificateRequest(state, certificateRequest);
        handshake.sendMessage((short)13, certificateRequestBody);
        
        TlsUtils.trackHashAlgorithms(handshake.getHandshakeHash(), certificateRequest
          .getSupportedSignatureAlgorithms());
      }
    }
    
    handshake.sendMessage((short)14, TlsUtils.EMPTY_BYTES);
    
    handshake.getHandshakeHash().sealHashAlgorithms();
    
    clientMessage = handshake.receiveMessage();
    
    if (clientMessage.getType() == 23)
    {
      processClientSupplementalData(state, clientMessage.getBody());
      clientMessage = handshake.receiveMessage();
    }
    else
    {
      server.processClientSupplementalData(null);
    }
    
    if (certificateRequest == null)
    {
      keyExchange.skipClientCredentials();


    }
    else if (clientMessage.getType() == 11)
    {
      processClientCertificate(state, clientMessage.getBody());
      clientMessage = handshake.receiveMessage();
    }
    else
    {
      if (TlsUtils.isTLSv12(serverContext))
      {






        throw new TlsFatalAlert((short)10);
      }
      
      notifyClientCertificate(state, Certificate.EMPTY_CHAIN);
    }
    

    if (clientMessage.getType() == 16)
    {
      processClientKeyExchange(state, clientMessage.getBody());
    }
    else
    {
      throw new TlsFatalAlert((short)10);
    }
    
    TlsHandshakeHash prepareFinishHash = handshake.prepareToFinish();
    sessionHash = TlsProtocol.getCurrentPRFHash(serverContext, prepareFinishHash, null);
    
    TlsProtocol.establishMasterSecret(serverContext, keyExchange);
    recordLayer.initPendingEpoch(server.getCipher());
    





    if (expectCertificateVerifyMessage(state))
    {
      byte[] certificateVerifyBody = handshake.receiveMessageBody((short)15);
      processCertificateVerify(state, certificateVerifyBody, prepareFinishHash);
    }
    

    byte[] expectedClientVerifyData = TlsUtils.calculateVerifyData(serverContext, "client finished", 
      TlsProtocol.getCurrentPRFHash(serverContext, handshake.getHandshakeHash(), null));
    processFinished(handshake.receiveMessageBody((short)20), expectedClientVerifyData);
    
    if (expectSessionTicket)
    {
      NewSessionTicket newSessionTicket = server.getNewSessionTicket();
      byte[] newSessionTicketBody = generateNewSessionTicket(state, newSessionTicket);
      handshake.sendMessage((short)4, newSessionTicketBody);
    }
    

    byte[] serverVerifyData = TlsUtils.calculateVerifyData(serverContext, "server finished", 
      TlsProtocol.getCurrentPRFHash(serverContext, handshake.getHandshakeHash(), null));
    handshake.sendMessage((short)20, serverVerifyData);
    
    handshake.finish();
    
    server.notifyHandshakeComplete();
    
    return new DTLSTransport(recordLayer);
  }
  
  protected byte[] generateCertificateRequest(ServerHandshakeState state, CertificateRequest certificateRequest)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    certificateRequest.encode(buf);
    return buf.toByteArray();
  }
  
  protected byte[] generateCertificateStatus(ServerHandshakeState state, CertificateStatus certificateStatus)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    certificateStatus.encode(buf);
    return buf.toByteArray();
  }
  
  protected byte[] generateNewSessionTicket(ServerHandshakeState state, NewSessionTicket newSessionTicket)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    newSessionTicket.encode(buf);
    return buf.toByteArray();
  }
  
  protected byte[] generateServerHello(ServerHandshakeState state)
    throws IOException
  {
    SecurityParameters securityParameters = serverContext.getSecurityParameters();
    
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    

    ProtocolVersion server_version = server.getServerVersion();
    if (!server_version.isEqualOrEarlierVersionOf(serverContext.getClientVersion()))
    {
      throw new TlsFatalAlert((short)80);
    }
    




    serverContext.setServerVersion(server_version);
    
    TlsUtils.writeVersion(serverContext.getServerVersion(), buf);
    

    buf.write(securityParameters.getServerRandom());
    




    TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, buf);
    
    int selectedCipherSuite = server.getSelectedCipherSuite();
    if ((!Arrays.contains(offeredCipherSuites, selectedCipherSuite)) || (selectedCipherSuite == 0) || 
    
      (CipherSuite.isSCSV(selectedCipherSuite)) || 
      (!TlsUtils.isValidCipherSuiteForVersion(selectedCipherSuite, serverContext.getServerVersion())))
    {
      throw new TlsFatalAlert((short)80);
    }
    validateSelectedCipherSuite(selectedCipherSuite, (short)80);
    cipherSuite = selectedCipherSuite;
    
    short selectedCompressionMethod = server.getSelectedCompressionMethod();
    if (!Arrays.contains(offeredCompressionMethods, selectedCompressionMethod))
    {
      throw new TlsFatalAlert((short)80);
    }
    compressionAlgorithm = selectedCompressionMethod;
    
    TlsUtils.writeUint16(selectedCipherSuite, buf);
    TlsUtils.writeUint8(selectedCompressionMethod, buf);
    
    serverExtensions = server.getServerExtensions();
    



    if (secure_renegotiation)
    {
      byte[] renegExtData = TlsUtils.getExtensionData(serverExtensions, TlsProtocol.EXT_RenegotiationInfo);
      boolean noRenegExt = null == renegExtData;
      
      if (noRenegExt)
      {












        serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(serverExtensions);
        serverExtensions.put(TlsProtocol.EXT_RenegotiationInfo, 
          TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES));
      }
    }
    
    if (extendedMasterSecret)
    {
      serverExtensions = TlsExtensionsUtils.ensureExtensionsInitialised(serverExtensions);
      TlsExtensionsUtils.addExtendedMasterSecretExtension(serverExtensions);
    }
    






    if (serverExtensions != null)
    {
      encryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(serverExtensions);
      
      maxFragmentLength = evaluateMaxFragmentLengthExtension(resumedSession, clientExtensions, serverExtensions, (short)80);
      

      truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(serverExtensions);
      





      allowCertificateStatus = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(serverExtensions, TlsExtensionsUtils.EXT_status_request, (short)80)));
      


      expectSessionTicket = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(serverExtensions, TlsProtocol.EXT_SessionTicket, (short)80)));
      

      TlsProtocol.writeExtensions(buf, serverExtensions);
    }
    
    prfAlgorithm = TlsProtocol.getPRFAlgorithm(serverContext, securityParameters
      .getCipherSuite());
    




    verifyDataLength = 12;
    
    return buf.toByteArray();
  }
  
  protected void invalidateSession(ServerHandshakeState state)
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
  
  protected void notifyClientCertificate(ServerHandshakeState state, Certificate clientCertificate)
    throws IOException
  {
    if (certificateRequest == null)
    {
      throw new IllegalStateException();
    }
    
    if (clientCertificate != null)
    {
      throw new TlsFatalAlert((short)10);
    }
    
    clientCertificate = clientCertificate;
    
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
    








    server.notifyClientCertificate(clientCertificate);
  }
  
  protected void processClientCertificate(ServerHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    Certificate clientCertificate = Certificate.parse(buf);
    
    TlsProtocol.assertEmpty(buf);
    
    notifyClientCertificate(state, clientCertificate);
  }
  
  protected void processCertificateVerify(ServerHandshakeState state, byte[] body, TlsHandshakeHash prepareFinishHash)
    throws IOException
  {
    if (certificateRequest == null)
    {
      throw new IllegalStateException();
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    TlsServerContextImpl context = serverContext;
    DigitallySigned clientCertificateVerify = DigitallySigned.parse(context, buf);
    
    TlsProtocol.assertEmpty(buf);
    

    try
    {
      SignatureAndHashAlgorithm signatureAlgorithm = clientCertificateVerify.getAlgorithm();
      byte[] hash;
      byte[] hash;
      if (TlsUtils.isTLSv12(context))
      {
        TlsUtils.verifySupportedSignatureAlgorithm(certificateRequest.getSupportedSignatureAlgorithms(), signatureAlgorithm);
        hash = prepareFinishHash.getFinalHash(signatureAlgorithm.getHash());
      }
      else
      {
        hash = context.getSecurityParameters().getSessionHash();
      }
      
      org.spongycastle.asn1.x509.Certificate x509Cert = clientCertificate.getCertificateAt(0);
      SubjectPublicKeyInfo keyInfo = x509Cert.getSubjectPublicKeyInfo();
      AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(keyInfo);
      
      TlsSigner tlsSigner = TlsUtils.createTlsSigner(clientCertificateType);
      tlsSigner.init(context);
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
  
  protected void processClientHello(ServerHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    

    ProtocolVersion client_version = TlsUtils.readVersion(buf);
    if (!client_version.isDTLS())
    {
      throw new TlsFatalAlert((short)47);
    }
    



    byte[] client_random = TlsUtils.readFully(32, buf);
    
    byte[] sessionID = TlsUtils.readOpaque8(buf);
    if (sessionID.length > 32)
    {
      throw new TlsFatalAlert((short)47);
    }
    

    byte[] cookie = TlsUtils.readOpaque8(buf);
    
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
    





    clientExtensions = TlsProtocol.readExtensions(buf);
    
    TlsServerContextImpl context = serverContext;
    SecurityParameters securityParameters = context.getSecurityParameters();
    






    extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(clientExtensions);
    
    context.setClientVersion(client_version);
    
    server.notifyClientVersion(client_version);
    server.notifyFallback(Arrays.contains(offeredCipherSuites, 22016));
    
    clientRandom = client_random;
    
    server.notifyOfferedCipherSuites(offeredCipherSuites);
    server.notifyOfferedCompressionMethods(offeredCompressionMethods);
    















    if (Arrays.contains(offeredCipherSuites, 255))
    {
      secure_renegotiation = true;
    }
    




    byte[] renegExtData = TlsUtils.getExtensionData(clientExtensions, TlsProtocol.EXT_RenegotiationInfo);
    if (renegExtData != null)
    {





      secure_renegotiation = true;
      
      if (!Arrays.constantTimeAreEqual(renegExtData, TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES)))
      {
        throw new TlsFatalAlert((short)40);
      }
    }
    

    server.notifySecureRenegotiation(secure_renegotiation);
    
    if (clientExtensions != null)
    {

      TlsExtensionsUtils.getPaddingExtension(clientExtensions);
      
      server.processClientExtensions(clientExtensions);
    }
  }
  
  protected void processClientKeyExchange(ServerHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    keyExchange.processClientKeyExchange(buf);
    
    TlsProtocol.assertEmpty(buf);
  }
  
  protected void processClientSupplementalData(ServerHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    Vector clientSupplementalData = TlsProtocol.readSupplementalDataMessage(buf);
    server.processClientSupplementalData(clientSupplementalData);
  }
  
  protected boolean expectCertificateVerifyMessage(ServerHandshakeState state)
  {
    return (clientCertificateType >= 0) && (TlsUtils.hasSigningCapability(clientCertificateType));
  }
  
  protected static class ServerHandshakeState
  {
    TlsServer server = null;
    TlsServerContextImpl serverContext = null;
    TlsSession tlsSession = null;
    SessionParameters sessionParameters = null;
    SessionParameters.Builder sessionParametersBuilder = null;
    int[] offeredCipherSuites = null;
    short[] offeredCompressionMethods = null;
    Hashtable clientExtensions = null;
    Hashtable serverExtensions = null;
    boolean resumedSession = false;
    boolean secure_renegotiation = false;
    boolean allowCertificateStatus = false;
    boolean expectSessionTicket = false;
    TlsKeyExchange keyExchange = null;
    TlsCredentials serverCredentials = null;
    CertificateRequest certificateRequest = null;
    short clientCertificateType = -1;
    Certificate clientCertificate = null;
    
    protected ServerHandshakeState() {}
  }
}
