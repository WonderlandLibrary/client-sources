package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.util.Arrays;


public class DTLSClientProtocol
  extends DTLSProtocol
{
  public DTLSClientProtocol(SecureRandom secureRandom)
  {
    super(secureRandom);
  }
  
  public DTLSTransport connect(TlsClient client, DatagramTransport transport)
    throws IOException
  {
    if (client == null)
    {
      throw new IllegalArgumentException("'client' cannot be null");
    }
    if (transport == null)
    {
      throw new IllegalArgumentException("'transport' cannot be null");
    }
    
    SecurityParameters securityParameters = new SecurityParameters();
    entity = 1;
    
    ClientHandshakeState state = new ClientHandshakeState();
    client = client;
    clientContext = new TlsClientContextImpl(secureRandom, securityParameters);
    
    clientRandom = TlsProtocol.createRandomBlock(client.shouldUseGMTUnixTime(), clientContext
      .getNonceRandomGenerator());
    
    client.init(clientContext);
    
    DTLSRecordLayer recordLayer = new DTLSRecordLayer(transport, clientContext, client, (short)22);
    
    TlsSession sessionToResume = client.getSessionToResume();
    SessionParameters sessionParameters; if ((sessionToResume != null) && (sessionToResume.isResumable()))
    {
      sessionParameters = sessionToResume.exportSessionParameters();
      if (sessionParameters != null)
      {
        tlsSession = sessionToResume;
        sessionParameters = sessionParameters;
      }
    }
    
    try
    {
      return clientHandshake(state, recordLayer);
    }
    catch (TlsFatalAlert fatalAlert)
    {
      abortClientHandshake(state, recordLayer, fatalAlert.getAlertDescription());
      throw fatalAlert;
    }
    catch (IOException e)
    {
      abortClientHandshake(state, recordLayer, (short)80);
      throw e;
    }
    catch (RuntimeException e)
    {
      abortClientHandshake(state, recordLayer, (short)80);
      throw new TlsFatalAlert((short)80, e);
    }
    finally
    {
      securityParameters.clear();
    }
  }
  
  protected void abortClientHandshake(ClientHandshakeState state, DTLSRecordLayer recordLayer, short alertDescription)
  {
    recordLayer.fail(alertDescription);
    invalidateSession(state);
  }
  
  protected DTLSTransport clientHandshake(ClientHandshakeState state, DTLSRecordLayer recordLayer)
    throws IOException
  {
    SecurityParameters securityParameters = clientContext.getSecurityParameters();
    DTLSReliableHandshake handshake = new DTLSReliableHandshake(clientContext, recordLayer);
    
    byte[] clientHelloBody = generateClientHello(state, client);
    
    recordLayer.setWriteVersion(ProtocolVersion.DTLSv10);
    
    handshake.sendMessage((short)1, clientHelloBody);
    
    DTLSReliableHandshake.Message serverMessage = handshake.receiveMessage();
    
    while (serverMessage.getType() == 3)
    {
      ProtocolVersion recordLayerVersion = recordLayer.getReadVersion();
      ProtocolVersion client_version = clientContext.getClientVersion();
      






      if (!recordLayerVersion.isEqualOrEarlierVersionOf(client_version))
      {
        throw new TlsFatalAlert((short)47);
      }
      
      recordLayer.setReadVersion(null);
      
      byte[] cookie = processHelloVerifyRequest(state, serverMessage.getBody());
      byte[] patched = patchClientHelloWithCookie(clientHelloBody, cookie);
      
      handshake.resetHandshakeMessagesDigest();
      handshake.sendMessage((short)1, patched);
      
      serverMessage = handshake.receiveMessage();
    }
    
    if (serverMessage.getType() == 2)
    {
      ProtocolVersion recordLayerVersion = recordLayer.getReadVersion();
      reportServerVersion(state, recordLayerVersion);
      recordLayer.setWriteVersion(recordLayerVersion);
      
      processServerHello(state, serverMessage.getBody());
    }
    else
    {
      throw new TlsFatalAlert((short)10);
    }
    
    handshake.notifyHelloComplete();
    
    applyMaxFragmentLengthExtension(recordLayer, maxFragmentLength);
    
    if (resumedSession)
    {
      masterSecret = Arrays.clone(sessionParameters.getMasterSecret());
      recordLayer.initPendingEpoch(client.getCipher());
      

      byte[] expectedServerVerifyData = TlsUtils.calculateVerifyData(clientContext, "server finished", 
        TlsProtocol.getCurrentPRFHash(clientContext, handshake.getHandshakeHash(), null));
      processFinished(handshake.receiveMessageBody((short)20), expectedServerVerifyData);
      

      byte[] clientVerifyData = TlsUtils.calculateVerifyData(clientContext, "client finished", 
        TlsProtocol.getCurrentPRFHash(clientContext, handshake.getHandshakeHash(), null));
      handshake.sendMessage((short)20, clientVerifyData);
      
      handshake.finish();
      
      clientContext.setResumableSession(tlsSession);
      
      client.notifyHandshakeComplete();
      
      return new DTLSTransport(recordLayer);
    }
    
    invalidateSession(state);
    
    if (selectedSessionID.length > 0)
    {
      tlsSession = new TlsSessionImpl(selectedSessionID, null);
    }
    
    serverMessage = handshake.receiveMessage();
    
    if (serverMessage.getType() == 23)
    {
      processServerSupplementalData(state, serverMessage.getBody());
      serverMessage = handshake.receiveMessage();
    }
    else
    {
      client.processServerSupplementalData(null);
    }
    
    keyExchange = client.getKeyExchange();
    keyExchange.init(clientContext);
    
    Certificate serverCertificate = null;
    
    if (serverMessage.getType() == 11)
    {
      serverCertificate = processServerCertificate(state, serverMessage.getBody());
      serverMessage = handshake.receiveMessage();

    }
    else
    {
      keyExchange.skipServerCredentials();
    }
    

    if ((serverCertificate == null) || (serverCertificate.isEmpty()))
    {
      allowCertificateStatus = false;
    }
    
    if (serverMessage.getType() == 22)
    {
      processCertificateStatus(state, serverMessage.getBody());
      serverMessage = handshake.receiveMessage();
    }
    




    if (serverMessage.getType() == 12)
    {
      processServerKeyExchange(state, serverMessage.getBody());
      serverMessage = handshake.receiveMessage();

    }
    else
    {
      keyExchange.skipServerKeyExchange();
    }
    
    if (serverMessage.getType() == 13)
    {
      processCertificateRequest(state, serverMessage.getBody());
      




      TlsUtils.trackHashAlgorithms(handshake.getHandshakeHash(), certificateRequest
        .getSupportedSignatureAlgorithms());
      
      serverMessage = handshake.receiveMessage();
    }
    




    if (serverMessage.getType() == 14)
    {
      if (serverMessage.getBody().length != 0)
      {
        throw new TlsFatalAlert((short)50);
      }
      
    }
    else {
      throw new TlsFatalAlert((short)10);
    }
    
    handshake.getHandshakeHash().sealHashAlgorithms();
    
    Vector clientSupplementalData = client.getClientSupplementalData();
    if (clientSupplementalData != null)
    {
      byte[] supplementalDataBody = generateSupplementalData(clientSupplementalData);
      handshake.sendMessage((short)23, supplementalDataBody);
    }
    
    if (certificateRequest != null)
    {
      clientCredentials = authentication.getClientCredentials(certificateRequest);
      






      Certificate clientCertificate = null;
      if (clientCredentials != null)
      {
        clientCertificate = clientCredentials.getCertificate();
      }
      if (clientCertificate == null)
      {
        clientCertificate = Certificate.EMPTY_CHAIN;
      }
      
      byte[] certificateBody = generateCertificate(clientCertificate);
      handshake.sendMessage((short)11, certificateBody);
    }
    
    if (clientCredentials != null)
    {
      keyExchange.processClientCredentials(clientCredentials);
    }
    else
    {
      keyExchange.skipClientCredentials();
    }
    
    byte[] clientKeyExchangeBody = generateClientKeyExchange(state);
    handshake.sendMessage((short)16, clientKeyExchangeBody);
    
    TlsHandshakeHash prepareFinishHash = handshake.prepareToFinish();
    sessionHash = TlsProtocol.getCurrentPRFHash(clientContext, prepareFinishHash, null);
    
    TlsProtocol.establishMasterSecret(clientContext, keyExchange);
    recordLayer.initPendingEpoch(client.getCipher());
    
    if ((clientCredentials != null) && ((clientCredentials instanceof TlsSignerCredentials)))
    {
      TlsSignerCredentials signerCredentials = (TlsSignerCredentials)clientCredentials;
      



      SignatureAndHashAlgorithm signatureAndHashAlgorithm = TlsUtils.getSignatureAndHashAlgorithm(clientContext, signerCredentials);
      
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
      byte[] certificateVerifyBody = generateCertificateVerify(state, certificateVerify);
      handshake.sendMessage((short)15, certificateVerifyBody);
    }
    

    byte[] clientVerifyData = TlsUtils.calculateVerifyData(clientContext, "client finished", 
      TlsProtocol.getCurrentPRFHash(clientContext, handshake.getHandshakeHash(), null));
    handshake.sendMessage((short)20, clientVerifyData);
    
    if (expectSessionTicket)
    {
      serverMessage = handshake.receiveMessage();
      if (serverMessage.getType() == 4)
      {
        processNewSessionTicket(state, serverMessage.getBody());
      }
      else
      {
        throw new TlsFatalAlert((short)10);
      }
    }
    

    byte[] expectedServerVerifyData = TlsUtils.calculateVerifyData(clientContext, "server finished", 
      TlsProtocol.getCurrentPRFHash(clientContext, handshake.getHandshakeHash(), null));
    processFinished(handshake.receiveMessageBody((short)20), expectedServerVerifyData);
    
    handshake.finish();
    
    if (tlsSession != null)
    {









      sessionParameters = new SessionParameters.Builder().setCipherSuite(securityParameters.getCipherSuite()).setCompressionAlgorithm(securityParameters.getCompressionAlgorithm()).setMasterSecret(securityParameters.getMasterSecret()).setPeerCertificate(serverCertificate).setPSKIdentity(securityParameters.getPSKIdentity()).setSRPIdentity(securityParameters.getSRPIdentity()).setServerExtensions(serverExtensions).build();
      
      tlsSession = TlsUtils.importSession(tlsSession.getSessionID(), sessionParameters);
      
      clientContext.setResumableSession(tlsSession);
    }
    
    client.notifyHandshakeComplete();
    
    return new DTLSTransport(recordLayer);
  }
  
  protected byte[] generateCertificateVerify(ClientHandshakeState state, DigitallySigned certificateVerify)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    certificateVerify.encode(buf);
    return buf.toByteArray();
  }
  
  protected byte[] generateClientHello(ClientHandshakeState state, TlsClient client)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    
    ProtocolVersion client_version = client.getClientVersion();
    if (!client_version.isDTLS())
    {
      throw new TlsFatalAlert((short)80);
    }
    
    TlsClientContextImpl context = clientContext;
    
    context.setClientVersion(client_version);
    TlsUtils.writeVersion(client_version, buf);
    
    SecurityParameters securityParameters = context.getSecurityParameters();
    buf.write(securityParameters.getClientRandom());
    

    byte[] session_id = TlsUtils.EMPTY_BYTES;
    if (tlsSession != null)
    {
      session_id = tlsSession.getSessionID();
      if ((session_id == null) || (session_id.length > 32))
      {
        session_id = TlsUtils.EMPTY_BYTES;
      }
    }
    TlsUtils.writeOpaque8(session_id, buf);
    

    TlsUtils.writeOpaque8(TlsUtils.EMPTY_BYTES, buf);
    
    boolean fallback = client.isFallback();
    



    offeredCipherSuites = client.getCipherSuites();
    

    clientExtensions = client.getClientExtensions();
    







    byte[] renegExtData = TlsUtils.getExtensionData(clientExtensions, TlsProtocol.EXT_RenegotiationInfo);
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
    
    TlsUtils.writeUint16ArrayWithUint16Length(offeredCipherSuites, buf);
    




    offeredCompressionMethods = new short[] { 0 };
    
    TlsUtils.writeUint8ArrayWithUint8Length(offeredCompressionMethods, buf);
    

    if (clientExtensions != null)
    {
      TlsProtocol.writeExtensions(buf, clientExtensions);
    }
    
    return buf.toByteArray();
  }
  
  protected byte[] generateClientKeyExchange(ClientHandshakeState state)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    keyExchange.generateClientKeyExchange(buf);
    return buf.toByteArray();
  }
  
  protected void invalidateSession(ClientHandshakeState state)
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
  
  protected void processCertificateRequest(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    if (authentication == null)
    {




      throw new TlsFatalAlert((short)40);
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    certificateRequest = CertificateRequest.parse(clientContext, buf);
    
    TlsProtocol.assertEmpty(buf);
    
    keyExchange.validateCertificateRequest(certificateRequest);
  }
  
  protected void processCertificateStatus(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    if (!allowCertificateStatus)
    {





      throw new TlsFatalAlert((short)10);
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    certificateStatus = CertificateStatus.parse(buf);
    
    TlsProtocol.assertEmpty(buf);
  }
  


  protected byte[] processHelloVerifyRequest(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    ProtocolVersion server_version = TlsUtils.readVersion(buf);
    byte[] cookie = TlsUtils.readOpaque8(buf);
    
    TlsProtocol.assertEmpty(buf);
    


    if (!server_version.isEqualOrEarlierVersionOf(clientContext.getClientVersion()))
    {
      throw new TlsFatalAlert((short)47);
    }
    




    if ((!ProtocolVersion.DTLSv12.isEqualOrEarlierVersionOf(server_version)) && (cookie.length > 32))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    return cookie;
  }
  
  protected void processNewSessionTicket(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    NewSessionTicket newSessionTicket = NewSessionTicket.parse(buf);
    
    TlsProtocol.assertEmpty(buf);
    
    client.notifyNewSessionTicket(newSessionTicket);
  }
  
  protected Certificate processServerCertificate(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    Certificate serverCertificate = Certificate.parse(buf);
    
    TlsProtocol.assertEmpty(buf);
    
    keyExchange.processServerCertificate(serverCertificate);
    authentication = client.getAuthentication();
    authentication.notifyServerCertificate(serverCertificate);
    
    return serverCertificate;
  }
  
  protected void processServerHello(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    SecurityParameters securityParameters = clientContext.getSecurityParameters();
    
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    

    ProtocolVersion server_version = TlsUtils.readVersion(buf);
    reportServerVersion(state, server_version);
    

    serverRandom = TlsUtils.readFully(32, buf);
    
    selectedSessionID = TlsUtils.readOpaque8(buf);
    if (selectedSessionID.length > 32)
    {
      throw new TlsFatalAlert((short)47);
    }
    client.notifySessionID(selectedSessionID);
    
    resumedSession = ((selectedSessionID.length > 0) && (tlsSession != null) && (Arrays.areEqual(selectedSessionID, tlsSession.getSessionID())));
    
    int selectedCipherSuite = TlsUtils.readUint16(buf);
    if ((!Arrays.contains(offeredCipherSuites, selectedCipherSuite)) || (selectedCipherSuite == 0) || 
    
      (CipherSuite.isSCSV(selectedCipherSuite)) || 
      (!TlsUtils.isValidCipherSuiteForVersion(selectedCipherSuite, clientContext.getServerVersion())))
    {
      throw new TlsFatalAlert((short)47);
    }
    validateSelectedCipherSuite(selectedCipherSuite, (short)47);
    client.notifySelectedCipherSuite(selectedCipherSuite);
    
    short selectedCompressionMethod = TlsUtils.readUint8(buf);
    if (!Arrays.contains(offeredCompressionMethods, selectedCompressionMethod))
    {
      throw new TlsFatalAlert((short)47);
    }
    client.notifySelectedCompressionMethod(selectedCompressionMethod);
    
















    serverExtensions = TlsProtocol.readExtensions(buf);
    





    if (serverExtensions != null)
    {
      Enumeration e = serverExtensions.keys();
      while (e.hasMoreElements())
      {
        Integer extType = (Integer)e.nextElement();
        







        if (!extType.equals(TlsProtocol.EXT_RenegotiationInfo))
        {










          if (null == TlsUtils.getExtensionData(clientExtensions, extType))
          {
            throw new TlsFatalAlert((short)110);
          }
          





          if (!resumedSession) {}
        }
      }
    }
    













    byte[] renegExtData = TlsUtils.getExtensionData(serverExtensions, TlsProtocol.EXT_RenegotiationInfo);
    if (renegExtData != null)
    {






      secure_renegotiation = true;
      
      if (!Arrays.constantTimeAreEqual(renegExtData, 
        TlsProtocol.createRenegotiationInfo(TlsUtils.EMPTY_BYTES)))
      {
        throw new TlsFatalAlert((short)40);
      }
    }
    


    client.notifySecureRenegotiation(secure_renegotiation);
    
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
    
    cipherSuite = selectedCipherSuite;
    compressionAlgorithm = selectedCompressionMethod;
    
    if (sessionServerExtensions != null)
    {







      boolean serverSentEncryptThenMAC = TlsExtensionsUtils.hasEncryptThenMACExtension(sessionServerExtensions);
      if ((serverSentEncryptThenMAC) && (!TlsUtils.isBlockCipherSuite(securityParameters.getCipherSuite())))
      {
        throw new TlsFatalAlert((short)47);
      }
      encryptThenMAC = serverSentEncryptThenMAC;
      

      extendedMasterSecret = TlsExtensionsUtils.hasExtendedMasterSecretExtension(sessionServerExtensions);
      
      maxFragmentLength = evaluateMaxFragmentLengthExtension(resumedSession, sessionClientExtensions, sessionServerExtensions, (short)47);
      

      truncatedHMac = TlsExtensionsUtils.hasTruncatedHMacExtension(sessionServerExtensions);
      





      allowCertificateStatus = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(sessionServerExtensions, TlsExtensionsUtils.EXT_status_request, (short)47)));
      


      expectSessionTicket = ((!resumedSession) && (TlsUtils.hasExpectedEmptyExtensionData(sessionServerExtensions, TlsProtocol.EXT_SessionTicket, (short)47)));
    }
    








    if (sessionClientExtensions != null)
    {
      client.processServerExtensions(sessionServerExtensions);
    }
    
    prfAlgorithm = TlsProtocol.getPRFAlgorithm(clientContext, securityParameters
      .getCipherSuite());
    




    verifyDataLength = 12;
  }
  
  protected void processServerKeyExchange(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    
    keyExchange.processServerKeyExchange(buf);
    
    TlsProtocol.assertEmpty(buf);
  }
  
  protected void processServerSupplementalData(ClientHandshakeState state, byte[] body)
    throws IOException
  {
    ByteArrayInputStream buf = new ByteArrayInputStream(body);
    Vector serverSupplementalData = TlsProtocol.readSupplementalDataMessage(buf);
    client.processServerSupplementalData(serverSupplementalData);
  }
  
  protected void reportServerVersion(ClientHandshakeState state, ProtocolVersion server_version)
    throws IOException
  {
    TlsClientContextImpl clientContext = clientContext;
    ProtocolVersion currentServerVersion = clientContext.getServerVersion();
    if (null == currentServerVersion)
    {
      clientContext.setServerVersion(server_version);
      client.notifyServerVersion(server_version);
    }
    else if (!currentServerVersion.equals(server_version))
    {
      throw new TlsFatalAlert((short)47);
    }
  }
  
  protected static byte[] patchClientHelloWithCookie(byte[] clientHelloBody, byte[] cookie)
    throws IOException
  {
    int sessionIDPos = 34;
    int sessionIDLength = TlsUtils.readUint8(clientHelloBody, sessionIDPos);
    
    int cookieLengthPos = sessionIDPos + 1 + sessionIDLength;
    int cookiePos = cookieLengthPos + 1;
    
    byte[] patched = new byte[clientHelloBody.length + cookie.length];
    System.arraycopy(clientHelloBody, 0, patched, 0, cookieLengthPos);
    TlsUtils.checkUint8(cookie.length);
    TlsUtils.writeUint8(cookie.length, patched, cookieLengthPos);
    System.arraycopy(cookie, 0, patched, cookiePos, cookie.length);
    System.arraycopy(clientHelloBody, cookiePos, patched, cookiePos + cookie.length, clientHelloBody.length - cookiePos);
    

    return patched;
  }
  
  protected static class ClientHandshakeState
  {
    TlsClient client = null;
    TlsClientContextImpl clientContext = null;
    TlsSession tlsSession = null;
    SessionParameters sessionParameters = null;
    SessionParameters.Builder sessionParametersBuilder = null;
    int[] offeredCipherSuites = null;
    short[] offeredCompressionMethods = null;
    Hashtable clientExtensions = null;
    Hashtable serverExtensions = null;
    byte[] selectedSessionID = null;
    boolean resumedSession = false;
    boolean secure_renegotiation = false;
    boolean allowCertificateStatus = false;
    boolean expectSessionTicket = false;
    TlsKeyExchange keyExchange = null;
    TlsAuthentication authentication = null;
    CertificateStatus certificateStatus = null;
    CertificateRequest certificateRequest = null;
    TlsCredentials clientCredentials = null;
    
    protected ClientHandshakeState() {}
  }
}
