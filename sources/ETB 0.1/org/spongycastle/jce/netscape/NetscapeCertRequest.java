package org.spongycastle.jce.netscape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;


















public class NetscapeCertRequest
  extends ASN1Object
{
  AlgorithmIdentifier sigAlg;
  AlgorithmIdentifier keyAlg;
  byte[] sigBits;
  String challenge;
  DERBitString content;
  PublicKey pubkey;
  
  private static ASN1Sequence getReq(byte[] r)
    throws IOException
  {
    ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(r));
    
    return ASN1Sequence.getInstance(aIn.readObject());
  }
  

  public NetscapeCertRequest(byte[] req)
    throws IOException
  {
    this(getReq(req));
  }
  








  public NetscapeCertRequest(ASN1Sequence spkac)
  {
    try
    {
      if (spkac.size() != 3)
      {

        throw new IllegalArgumentException("invalid SPKAC (size):" + spkac.size());
      }
      
      sigAlg = AlgorithmIdentifier.getInstance(spkac.getObjectAt(1));
      sigBits = ((DERBitString)spkac.getObjectAt(2)).getOctets();
      






      ASN1Sequence pkac = (ASN1Sequence)spkac.getObjectAt(0);
      
      if (pkac.size() != 2)
      {

        throw new IllegalArgumentException("invalid PKAC (len): " + pkac.size());
      }
      
      challenge = ((DERIA5String)pkac.getObjectAt(1)).getString();
      


      content = new DERBitString(pkac);
      
      SubjectPublicKeyInfo pubkeyinfo = SubjectPublicKeyInfo.getInstance(pkac.getObjectAt(0));
      

      X509EncodedKeySpec xspec = new X509EncodedKeySpec(new DERBitString(pubkeyinfo).getBytes());
      
      keyAlg = pubkeyinfo.getAlgorithm();
      
      pubkey = KeyFactory.getInstance(keyAlg.getAlgorithm().getId(), "SC").generatePublic(xspec);

    }
    catch (Exception e)
    {
      throw new IllegalArgumentException(e.toString());
    }
  }
  




  public NetscapeCertRequest(String challenge, AlgorithmIdentifier signing_alg, PublicKey pub_key)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException
  {
    this.challenge = challenge;
    sigAlg = signing_alg;
    pubkey = pub_key;
    
    ASN1EncodableVector content_der = new ASN1EncodableVector();
    content_der.add(getKeySpec());
    
    content_der.add(new DERIA5String(challenge));
    
    try
    {
      content = new DERBitString(new DERSequence(content_der));
    }
    catch (IOException e)
    {
      throw new InvalidKeySpecException("exception encoding key: " + e.toString());
    }
  }
  
  public String getChallenge()
  {
    return challenge;
  }
  
  public void setChallenge(String value)
  {
    challenge = value;
  }
  
  public AlgorithmIdentifier getSigningAlgorithm()
  {
    return sigAlg;
  }
  
  public void setSigningAlgorithm(AlgorithmIdentifier value)
  {
    sigAlg = value;
  }
  
  public AlgorithmIdentifier getKeyAlgorithm()
  {
    return keyAlg;
  }
  
  public void setKeyAlgorithm(AlgorithmIdentifier value)
  {
    keyAlg = value;
  }
  
  public PublicKey getPublicKey()
  {
    return pubkey;
  }
  
  public void setPublicKey(PublicKey value)
  {
    pubkey = value;
  }
  
  public boolean verify(String challenge)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException
  {
    if (!challenge.equals(this.challenge))
    {
      return false;
    }
    




    Signature sig = Signature.getInstance(sigAlg.getAlgorithm().getId(), "SC");
    
    sig.initVerify(pubkey);
    sig.update(content.getBytes());
    
    return sig.verify(sigBits);
  }
  

  public void sign(PrivateKey priv_key)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException
  {
    sign(priv_key, null);
  }
  


  public void sign(PrivateKey priv_key, SecureRandom rand)
    throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException
  {
    Signature sig = Signature.getInstance(sigAlg.getAlgorithm().getId(), "SC");
    

    if (rand != null)
    {
      sig.initSign(priv_key, rand);
    }
    else
    {
      sig.initSign(priv_key);
    }
    
    ASN1EncodableVector pkac = new ASN1EncodableVector();
    
    pkac.add(getKeySpec());
    pkac.add(new DERIA5String(challenge));
    
    try
    {
      sig.update(new DERSequence(pkac).getEncoded("DER"));
    }
    catch (IOException ioe)
    {
      throw new SignatureException(ioe.getMessage());
    }
    
    sigBits = sig.sign();
  }
  
  private ASN1Primitive getKeySpec()
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    ASN1Primitive obj = null;
    
    try
    {
      baos.write(pubkey.getEncoded());
      baos.close();
      

      ASN1InputStream derin = new ASN1InputStream(new ByteArrayInputStream(baos.toByteArray()));
      
      obj = derin.readObject();
    }
    catch (IOException ioe)
    {
      throw new InvalidKeySpecException(ioe.getMessage());
    }
    return obj;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector spkac = new ASN1EncodableVector();
    ASN1EncodableVector pkac = new ASN1EncodableVector();
    
    try
    {
      pkac.add(getKeySpec());
    }
    catch (Exception localException) {}
    



    pkac.add(new DERIA5String(challenge));
    
    spkac.add(new DERSequence(pkac));
    spkac.add(sigAlg);
    spkac.add(new DERBitString(sigBits));
    
    return new DERSequence(spkac);
  }
}
