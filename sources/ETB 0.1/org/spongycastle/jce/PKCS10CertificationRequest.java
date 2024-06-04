package org.spongycastle.jce;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.CertificationRequest;
import org.spongycastle.asn1.pkcs.CertificationRequestInfo;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.util.Strings;






























/**
 * @deprecated
 */
public class PKCS10CertificationRequest
  extends CertificationRequest
{
  private static Hashtable algorithms = new Hashtable();
  private static Hashtable params = new Hashtable();
  private static Hashtable keyAlgorithms = new Hashtable();
  private static Hashtable oids = new Hashtable();
  private static Set noParams = new HashSet();
  
  static
  {
    algorithms.put("MD2WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"));
    algorithms.put("MD2WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"));
    algorithms.put("MD5WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
    algorithms.put("MD5WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
    algorithms.put("RSAWITHMD5", new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"));
    algorithms.put("SHA1WITHRSAENCRYPTION", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
    algorithms.put("SHA1WITHRSA", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
    algorithms.put("SHA224WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha224WithRSAEncryption);
    algorithms.put("SHA224WITHRSA", PKCSObjectIdentifiers.sha224WithRSAEncryption);
    algorithms.put("SHA256WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha256WithRSAEncryption);
    algorithms.put("SHA256WITHRSA", PKCSObjectIdentifiers.sha256WithRSAEncryption);
    algorithms.put("SHA384WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha384WithRSAEncryption);
    algorithms.put("SHA384WITHRSA", PKCSObjectIdentifiers.sha384WithRSAEncryption);
    algorithms.put("SHA512WITHRSAENCRYPTION", PKCSObjectIdentifiers.sha512WithRSAEncryption);
    algorithms.put("SHA512WITHRSA", PKCSObjectIdentifiers.sha512WithRSAEncryption);
    algorithms.put("SHA1WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA224WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA256WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA384WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("SHA512WITHRSAANDMGF1", PKCSObjectIdentifiers.id_RSASSA_PSS);
    algorithms.put("RSAWITHSHA1", new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"));
    algorithms.put("RIPEMD128WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    algorithms.put("RIPEMD128WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    algorithms.put("RIPEMD160WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    algorithms.put("RIPEMD160WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    algorithms.put("RIPEMD256WITHRSAENCRYPTION", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    algorithms.put("RIPEMD256WITHRSA", TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
    algorithms.put("SHA1WITHDSA", new ASN1ObjectIdentifier("1.2.840.10040.4.3"));
    algorithms.put("DSAWITHSHA1", new ASN1ObjectIdentifier("1.2.840.10040.4.3"));
    algorithms.put("SHA224WITHDSA", NISTObjectIdentifiers.dsa_with_sha224);
    algorithms.put("SHA256WITHDSA", NISTObjectIdentifiers.dsa_with_sha256);
    algorithms.put("SHA384WITHDSA", NISTObjectIdentifiers.dsa_with_sha384);
    algorithms.put("SHA512WITHDSA", NISTObjectIdentifiers.dsa_with_sha512);
    algorithms.put("SHA1WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("SHA224WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA224);
    algorithms.put("SHA256WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA256);
    algorithms.put("SHA384WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA384);
    algorithms.put("SHA512WITHECDSA", X9ObjectIdentifiers.ecdsa_with_SHA512);
    algorithms.put("ECDSAWITHSHA1", X9ObjectIdentifiers.ecdsa_with_SHA1);
    algorithms.put("GOST3411WITHGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3410WITHGOST3411", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    algorithms.put("GOST3411WITHECGOST3410", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    algorithms.put("GOST3411WITHECGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    algorithms.put("GOST3411WITHGOST3410-2001", CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    



    oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
    oids.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
    oids.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");
    
    oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
    oids.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
    oids.put(new ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
    oids.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
    oids.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
    oids.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
    oids.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
    



    keyAlgorithms.put(PKCSObjectIdentifiers.rsaEncryption, "RSA");
    keyAlgorithms.put(X9ObjectIdentifiers.id_dsa, "DSA");
    




    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA1);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA224);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA256);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA384);
    noParams.add(X9ObjectIdentifiers.ecdsa_with_SHA512);
    noParams.add(X9ObjectIdentifiers.id_dsa_with_sha1);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha224);
    noParams.add(NISTObjectIdentifiers.dsa_with_sha256);
    



    noParams.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94);
    noParams.add(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001);
    


    AlgorithmIdentifier sha1AlgId = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
    params.put("SHA1WITHRSAANDMGF1", creatPSSParams(sha1AlgId, 20));
    
    AlgorithmIdentifier sha224AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha224, DERNull.INSTANCE);
    params.put("SHA224WITHRSAANDMGF1", creatPSSParams(sha224AlgId, 28));
    
    AlgorithmIdentifier sha256AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE);
    params.put("SHA256WITHRSAANDMGF1", creatPSSParams(sha256AlgId, 32));
    
    AlgorithmIdentifier sha384AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha384, DERNull.INSTANCE);
    params.put("SHA384WITHRSAANDMGF1", creatPSSParams(sha384AlgId, 48));
    
    AlgorithmIdentifier sha512AlgId = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha512, DERNull.INSTANCE);
    params.put("SHA512WITHRSAANDMGF1", creatPSSParams(sha512AlgId, 64));
  }
  
  private static RSASSAPSSparams creatPSSParams(AlgorithmIdentifier hashAlgId, int saltSize)
  {
    return new RSASSAPSSparams(hashAlgId, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, hashAlgId), new ASN1Integer(saltSize), new ASN1Integer(1L));
  }
  





  private static ASN1Sequence toDERSequence(byte[] bytes)
  {
    try
    {
      ASN1InputStream dIn = new ASN1InputStream(bytes);
      
      return (ASN1Sequence)dIn.readObject();
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("badly encoded request");
    }
  }
  





  public PKCS10CertificationRequest(byte[] bytes)
  {
    super(toDERSequence(bytes));
  }
  

  public PKCS10CertificationRequest(ASN1Sequence sequence)
  {
    super(sequence);
  }
  









  public PKCS10CertificationRequest(String signatureAlgorithm, X509Name subject, PublicKey key, ASN1Set attributes, PrivateKey signingKey)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    this(signatureAlgorithm, subject, key, attributes, signingKey, "SC");
  }
  

  private static X509Name convertName(X500Principal name)
  {
    try
    {
      return new X509Principal(name.getEncoded());
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("can't convert name");
    }
  }
  









  public PKCS10CertificationRequest(String signatureAlgorithm, X500Principal subject, PublicKey key, ASN1Set attributes, PrivateKey signingKey)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    this(signatureAlgorithm, convertName(subject), key, attributes, signingKey, "SC");
  }
  










  public PKCS10CertificationRequest(String signatureAlgorithm, X500Principal subject, PublicKey key, ASN1Set attributes, PrivateKey signingKey, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    this(signatureAlgorithm, convertName(subject), key, attributes, signingKey, provider);
  }
  










  public PKCS10CertificationRequest(String signatureAlgorithm, X509Name subject, PublicKey key, ASN1Set attributes, PrivateKey signingKey, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    String algorithmName = Strings.toUpperCase(signatureAlgorithm);
    ASN1ObjectIdentifier sigOID = (ASN1ObjectIdentifier)algorithms.get(algorithmName);
    
    if (sigOID == null)
    {
      try
      {
        sigOID = new ASN1ObjectIdentifier(algorithmName);
      }
      catch (Exception e)
      {
        throw new IllegalArgumentException("Unknown signature type requested");
      }
    }
    
    if (subject == null)
    {
      throw new IllegalArgumentException("subject must not be null");
    }
    
    if (key == null)
    {
      throw new IllegalArgumentException("public key must not be null");
    }
    
    if (noParams.contains(sigOID))
    {
      sigAlgId = new AlgorithmIdentifier(sigOID);
    }
    else if (params.containsKey(algorithmName))
    {
      sigAlgId = new AlgorithmIdentifier(sigOID, (ASN1Encodable)params.get(algorithmName));
    }
    else
    {
      sigAlgId = new AlgorithmIdentifier(sigOID, DERNull.INSTANCE);
    }
    
    try
    {
      ASN1Sequence seq = (ASN1Sequence)ASN1Primitive.fromByteArray(key.getEncoded());
      reqInfo = new CertificationRequestInfo(subject, SubjectPublicKeyInfo.getInstance(seq), attributes);
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("can't encode public key");
    }
    Signature sig;
    Signature sig;
    if (provider == null)
    {
      sig = Signature.getInstance(signatureAlgorithm);
    }
    else
    {
      sig = Signature.getInstance(signatureAlgorithm, provider);
    }
    
    sig.initSign(signingKey);
    
    try
    {
      sig.update(reqInfo.getEncoded("DER"));
    }
    catch (Exception e)
    {
      throw new IllegalArgumentException("exception encoding TBS cert request - " + e);
    }
    
    sigBits = new DERBitString(sig.sign());
  }
  




  public PublicKey getPublicKey()
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    return getPublicKey("SC");
  }
  



























































  public boolean verify()
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    return verify("SC");
  }
  





  public boolean verify(String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    return verify(getPublicKey(provider), provider);
  }
  




  public boolean verify(PublicKey pubKey, String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException
  {
    try
    {
      Signature sig;
      


      if (provider == null)
      {
        sig = Signature.getInstance(getSignatureName(sigAlgId));
      }
      else
      {
        sig = Signature.getInstance(getSignatureName(sigAlgId), provider);
      }
    }
    catch (NoSuchAlgorithmException e)
    {
      Signature sig;
      
      Signature sig;
      if (oids.get(sigAlgId.getAlgorithm()) != null)
      {
        String signatureAlgorithm = (String)oids.get(sigAlgId.getAlgorithm());
        Signature sig;
        if (provider == null)
        {
          sig = Signature.getInstance(signatureAlgorithm);
        }
        else
        {
          sig = Signature.getInstance(signatureAlgorithm, provider);
        }
      }
      else
      {
        throw e;
      }
    }
    Signature sig;
    setSignatureParameters(sig, sigAlgId.getParameters());
    
    sig.initVerify(pubKey);
    
    try
    {
      sig.update(reqInfo.getEncoded("DER"));
    }
    catch (Exception e)
    {
      throw new SignatureException("exception encoding TBS cert request - " + e);
    }
    
    return sig.verify(sigBits.getOctets());
  }
  



  public byte[] getEncoded()
  {
    try
    {
      return getEncoded("DER");
    }
    catch (IOException e)
    {
      throw new RuntimeException(e.toString());
    }
  }
  


  private void setSignatureParameters(Signature signature, ASN1Encodable params)
    throws NoSuchAlgorithmException, SignatureException, InvalidKeyException
  {
    if ((params != null) && (!DERNull.INSTANCE.equals(params)))
    {
      AlgorithmParameters sigParams = AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
      
      try
      {
        sigParams.init(params.toASN1Primitive().getEncoded("DER"));
      }
      catch (IOException e)
      {
        throw new SignatureException("IOException decoding parameters: " + e.getMessage());
      }
      
      if (signature.getAlgorithm().endsWith("MGF1"))
      {
        try
        {
          signature.setParameter(sigParams.getParameterSpec(PSSParameterSpec.class));
        }
        catch (GeneralSecurityException e)
        {
          throw new SignatureException("Exception extracting parameters: " + e.getMessage());
        }
      }
    }
  }
  

  static String getSignatureName(AlgorithmIdentifier sigAlgId)
  {
    ASN1Encodable params = sigAlgId.getParameters();
    
    if ((params != null) && (!DERNull.INSTANCE.equals(params)))
    {
      if (sigAlgId.getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS))
      {
        RSASSAPSSparams rsaParams = RSASSAPSSparams.getInstance(params);
        return getDigestAlgName(rsaParams.getHashAlgorithm().getAlgorithm()) + "withRSAandMGF1";
      }
    }
    
    return sigAlgId.getAlgorithm().getId();
  }
  

  private static String getDigestAlgName(ASN1ObjectIdentifier digestAlgOID)
  {
    if (PKCSObjectIdentifiers.md5.equals(digestAlgOID))
    {
      return "MD5";
    }
    if (OIWObjectIdentifiers.idSHA1.equals(digestAlgOID))
    {
      return "SHA1";
    }
    if (NISTObjectIdentifiers.id_sha224.equals(digestAlgOID))
    {
      return "SHA224";
    }
    if (NISTObjectIdentifiers.id_sha256.equals(digestAlgOID))
    {
      return "SHA256";
    }
    if (NISTObjectIdentifiers.id_sha384.equals(digestAlgOID))
    {
      return "SHA384";
    }
    if (NISTObjectIdentifiers.id_sha512.equals(digestAlgOID))
    {
      return "SHA512";
    }
    if (TeleTrusTObjectIdentifiers.ripemd128.equals(digestAlgOID))
    {
      return "RIPEMD128";
    }
    if (TeleTrusTObjectIdentifiers.ripemd160.equals(digestAlgOID))
    {
      return "RIPEMD160";
    }
    if (TeleTrusTObjectIdentifiers.ripemd256.equals(digestAlgOID))
    {
      return "RIPEMD256";
    }
    if (CryptoProObjectIdentifiers.gostR3411.equals(digestAlgOID))
    {
      return "GOST3411";
    }
    

    return digestAlgOID.getId();
  }
  
  /* Error */
  public PublicKey getPublicKey(String provider)
    throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 201	org/spongycastle/jce/PKCS10CertificationRequest:reqInfo	Lorg/spongycastle/asn1/pkcs/CertificationRequestInfo;
    //   4: invokevirtual 265	org/spongycastle/asn1/pkcs/CertificationRequestInfo:getSubjectPublicKeyInfo	()Lorg/spongycastle/asn1/x509/SubjectPublicKeyInfo;
    //   7: astore_2
    //   8: new 267	java/security/spec/X509EncodedKeySpec
    //   11: dup
    //   12: new 241	org/spongycastle/asn1/DERBitString
    //   15: dup
    //   16: aload_2
    //   17: invokespecial 270	org/spongycastle/asn1/DERBitString:<init>	(Lorg/spongycastle/asn1/ASN1Encodable;)V
    //   20: invokevirtual 273	org/spongycastle/asn1/DERBitString:getOctets	()[B
    //   23: invokespecial 274	java/security/spec/X509EncodedKeySpec:<init>	([B)V
    //   26: astore_3
    //   27: aload_2
    //   28: invokevirtual 278	org/spongycastle/asn1/x509/SubjectPublicKeyInfo:getAlgorithm	()Lorg/spongycastle/asn1/x509/AlgorithmIdentifier;
    //   31: astore 4
    //   33: aload_1
    //   34: ifnonnull +19 -> 53
    //   37: aload 4
    //   39: invokevirtual 281	org/spongycastle/asn1/x509/AlgorithmIdentifier:getAlgorithm	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   42: invokevirtual 284	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   45: invokestatic 289	java/security/KeyFactory:getInstance	(Ljava/lang/String;)Ljava/security/KeyFactory;
    //   48: aload_3
    //   49: invokevirtual 293	java/security/KeyFactory:generatePublic	(Ljava/security/spec/KeySpec;)Ljava/security/PublicKey;
    //   52: areturn
    //   53: aload 4
    //   55: invokevirtual 281	org/spongycastle/asn1/x509/AlgorithmIdentifier:getAlgorithm	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   58: invokevirtual 284	org/spongycastle/asn1/ASN1ObjectIdentifier:getId	()Ljava/lang/String;
    //   61: aload_1
    //   62: invokestatic 296	java/security/KeyFactory:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyFactory;
    //   65: aload_3
    //   66: invokevirtual 293	java/security/KeyFactory:generatePublic	(Ljava/security/spec/KeySpec;)Ljava/security/PublicKey;
    //   69: areturn
    //   70: astore 5
    //   72: getstatic 298	org/spongycastle/jce/PKCS10CertificationRequest:keyAlgorithms	Ljava/util/Hashtable;
    //   75: aload 4
    //   77: invokevirtual 281	org/spongycastle/asn1/x509/AlgorithmIdentifier:getAlgorithm	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   80: invokevirtual 141	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   83: ifnull +44 -> 127
    //   86: getstatic 298	org/spongycastle/jce/PKCS10CertificationRequest:keyAlgorithms	Ljava/util/Hashtable;
    //   89: aload 4
    //   91: invokevirtual 281	org/spongycastle/asn1/x509/AlgorithmIdentifier:getAlgorithm	()Lorg/spongycastle/asn1/ASN1ObjectIdentifier;
    //   94: invokevirtual 141	java/util/Hashtable:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   97: checkcast 300	java/lang/String
    //   100: astore 6
    //   102: aload_1
    //   103: ifnonnull +13 -> 116
    //   106: aload 6
    //   108: invokestatic 289	java/security/KeyFactory:getInstance	(Ljava/lang/String;)Ljava/security/KeyFactory;
    //   111: aload_3
    //   112: invokevirtual 293	java/security/KeyFactory:generatePublic	(Ljava/security/spec/KeySpec;)Ljava/security/PublicKey;
    //   115: areturn
    //   116: aload 6
    //   118: aload_1
    //   119: invokestatic 296	java/security/KeyFactory:getInstance	(Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyFactory;
    //   122: aload_3
    //   123: invokevirtual 293	java/security/KeyFactory:generatePublic	(Ljava/security/spec/KeySpec;)Ljava/security/PublicKey;
    //   126: areturn
    //   127: aload 5
    //   129: athrow
    //   130: astore_3
    //   131: new 84	java/security/InvalidKeyException
    //   134: dup
    //   135: ldc_w 302
    //   138: invokespecial 303	java/security/InvalidKeyException:<init>	(Ljava/lang/String;)V
    //   141: athrow
    //   142: astore_3
    //   143: new 84	java/security/InvalidKeyException
    //   146: dup
    //   147: ldc_w 302
    //   150: invokespecial 303	java/security/InvalidKeyException:<init>	(Ljava/lang/String;)V
    //   153: athrow
    // Line number table:
    //   Java source line #395	-> byte code offset #0
    //   Java source line #400	-> byte code offset #8
    //   Java source line #401	-> byte code offset #27
    //   Java source line #404	-> byte code offset #33
    //   Java source line #406	-> byte code offset #37
    //   Java source line #410	-> byte code offset #53
    //   Java source line #413	-> byte code offset #70
    //   Java source line #418	-> byte code offset #72
    //   Java source line #420	-> byte code offset #86
    //   Java source line #422	-> byte code offset #102
    //   Java source line #424	-> byte code offset #106
    //   Java source line #428	-> byte code offset #116
    //   Java source line #432	-> byte code offset #127
    //   Java source line #435	-> byte code offset #130
    //   Java source line #437	-> byte code offset #131
    //   Java source line #439	-> byte code offset #142
    //   Java source line #441	-> byte code offset #143
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	this	PKCS10CertificationRequest
    //   0	154	1	provider	String
    //   7	21	2	subjectPKInfo	SubjectPublicKeyInfo
    //   26	97	3	xspec	java.security.spec.X509EncodedKeySpec
    //   130	2	3	e	java.security.spec.InvalidKeySpecException
    //   142	2	3	e	IOException
    //   31	59	4	keyAlg	AlgorithmIdentifier
    //   70	58	5	e	NoSuchAlgorithmException
    //   100	17	6	keyAlgorithm	String
    // Exception table:
    //   from	to	target	type
    //   33	52	70	java/security/NoSuchAlgorithmException
    //   53	69	70	java/security/NoSuchAlgorithmException
    //   8	52	130	java/security/spec/InvalidKeySpecException
    //   53	69	130	java/security/spec/InvalidKeySpecException
    //   70	115	130	java/security/spec/InvalidKeySpecException
    //   116	126	130	java/security/spec/InvalidKeySpecException
    //   127	130	130	java/security/spec/InvalidKeySpecException
    //   8	52	142	java/io/IOException
    //   53	69	142	java/io/IOException
    //   70	115	142	java/io/IOException
    //   116	126	142	java/io/IOException
    //   127	130	142	java/io/IOException
  }
}
