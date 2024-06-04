package org.silvertunnel_ng.netlib.layer.tor.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;












public class RSAKeyEncoder
{
  private static final String HEADER = "-----BEGIN RSA PUBLIC KEY-----";
  private static final String FOOTER = "-----END RSA PUBLIC KEY-----";
  private final ASN1Parser asn1Parser = new ASN1Parser();
  





  public RSAKeyEncoder() {}
  




  public RSAPublicKey parsePEMPublicKey(String pem)
    throws GeneralSecurityException
  {
    try
    {
      byte[] bs = decodeAsciiArmoredPEM(pem);
      ByteBuffer data = ByteBuffer.wrap(bs);
      ASN1Parser.ASN1Object ob = asn1Parser.parseASN1(data);
      List<ASN1Parser.ASN1Object> seq = asn1ObjectToSequence(ob, 2);
      BigInteger modulus = asn1ObjectToBigInt((ASN1Parser.ASN1Object)seq.get(0));
      BigInteger exponent = asn1ObjectToBigInt((ASN1Parser.ASN1Object)seq.get(1));
      return createKeyFromModulusAndExponent(modulus, exponent);
    }
    catch (IllegalArgumentException e)
    {
      throw new InvalidKeyException(e);
    }
  }
  
  private RSAPublicKey createKeyFromModulusAndExponent(BigInteger modulus, BigInteger exponent) throws GeneralSecurityException
  {
    RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
    KeyFactory fac = KeyFactory.getInstance("RSA");
    return (RSAPublicKey)fac.generatePublic(spec);
  }
  










  public byte[] getPKCS1Encoded(RSAPublicKey publicKey)
  {
    return extractPKCS1KeyFromSubjectPublicKeyInfo(publicKey.getEncoded());
  }
  









  private byte[] extractPKCS1KeyFromSubjectPublicKeyInfo(byte[] input)
  {
    ASN1Parser.ASN1Object ob = asn1Parser.parseASN1(ByteBuffer.wrap(input));
    List<ASN1Parser.ASN1Object> seq = asn1ObjectToSequence(ob, 2);
    return asn1ObjectToBitString((ASN1Parser.ASN1Object)seq.get(1));
  }
  
  private BigInteger asn1ObjectToBigInt(ASN1Parser.ASN1Object ob)
  {
    if (!(ob instanceof ASN1Parser.ASN1Integer))
    {
      throw new IllegalArgumentException();
    }
    ASN1Parser.ASN1Integer n = (ASN1Parser.ASN1Integer)ob;
    return n.getValue();
  }
  
  private List<ASN1Parser.ASN1Object> asn1ObjectToSequence(ASN1Parser.ASN1Object ob, int expectedSize)
  {
    if ((ob instanceof ASN1Parser.ASN1Sequence))
    {
      ASN1Parser.ASN1Sequence seq = (ASN1Parser.ASN1Sequence)ob;
      if (seq.getItems().size() != expectedSize)
      {
        throw new IllegalArgumentException();
      }
      return seq.getItems();
    }
    throw new IllegalArgumentException("ASN1Object not of type ASN1Sequence. ASN1Object : " + ob);
  }
  
  private byte[] asn1ObjectToBitString(ASN1Parser.ASN1Object ob)
  {
    if (!(ob instanceof ASN1Parser.ASN1BitString))
    {
      throw new IllegalArgumentException();
    }
    ASN1Parser.ASN1BitString bitstring = (ASN1Parser.ASN1BitString)ob;
    return bitstring.getBytes();
  }
  
  private byte[] decodeAsciiArmoredPEM(String pem) {
    String trimmed = removeDelimiters(pem);
    return DatatypeConverter.parseBase64Binary(trimmed);
  }
  
  private String removeDelimiters(String pem)
  {
    int headerIdx = pem.indexOf("-----BEGIN RSA PUBLIC KEY-----");
    int footerIdx = pem.indexOf("-----END RSA PUBLIC KEY-----");
    if ((headerIdx == -1) || (footerIdx == -1) || (footerIdx <= headerIdx))
    {
      return pem;
    }
    return pem.substring(headerIdx + "-----BEGIN RSA PUBLIC KEY-----".length(), footerIdx);
  }
}
