package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;















public class AuthorityKeyIdentifier
  extends ASN1Object
{
  ASN1OctetString keyidentifier = null;
  GeneralNames certissuer = null;
  ASN1Integer certserno = null;
  


  public static AuthorityKeyIdentifier getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static AuthorityKeyIdentifier getInstance(Object obj)
  {
    if ((obj instanceof AuthorityKeyIdentifier))
    {
      return (AuthorityKeyIdentifier)obj;
    }
    if (obj != null)
    {
      return new AuthorityKeyIdentifier(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public static AuthorityKeyIdentifier fromExtensions(Extensions extensions)
  {
    return getInstance(extensions.getExtensionParsedValue(Extension.authorityKeyIdentifier));
  }
  

  protected AuthorityKeyIdentifier(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    while (e.hasMoreElements())
    {
      ASN1TaggedObject o = DERTaggedObject.getInstance(e.nextElement());
      
      switch (o.getTagNo())
      {
      case 0: 
        keyidentifier = ASN1OctetString.getInstance(o, false);
        break;
      case 1: 
        certissuer = GeneralNames.getInstance(o, false);
        break;
      case 2: 
        certserno = ASN1Integer.getInstance(o, false);
        break;
      default: 
        throw new IllegalArgumentException("illegal tag");
      }
      
    }
  }
  










  /**
   * @deprecated
   */
  public AuthorityKeyIdentifier(SubjectPublicKeyInfo spki)
  {
    Digest digest = new SHA1Digest();
    byte[] resBuf = new byte[digest.getDigestSize()];
    
    byte[] bytes = spki.getPublicKeyData().getBytes();
    digest.update(bytes, 0, bytes.length);
    digest.doFinal(resBuf, 0);
    keyidentifier = new DEROctetString(resBuf);
  }
  





  /**
   * @deprecated
   */
  public AuthorityKeyIdentifier(SubjectPublicKeyInfo spki, GeneralNames name, BigInteger serialNumber)
  {
    Digest digest = new SHA1Digest();
    byte[] resBuf = new byte[digest.getDigestSize()];
    
    byte[] bytes = spki.getPublicKeyData().getBytes();
    digest.update(bytes, 0, bytes.length);
    digest.doFinal(resBuf, 0);
    
    keyidentifier = new DEROctetString(resBuf);
    certissuer = GeneralNames.getInstance(name.toASN1Primitive());
    certserno = new ASN1Integer(serialNumber);
  }
  






  public AuthorityKeyIdentifier(GeneralNames name, BigInteger serialNumber)
  {
    this((byte[])null, name, serialNumber);
  }
  




  public AuthorityKeyIdentifier(byte[] keyIdentifier)
  {
    this(keyIdentifier, null, null);
  }
  







  public AuthorityKeyIdentifier(byte[] keyIdentifier, GeneralNames name, BigInteger serialNumber)
  {
    keyidentifier = (keyIdentifier != null ? new DEROctetString(keyIdentifier) : null);
    certissuer = name;
    certserno = (serialNumber != null ? new ASN1Integer(serialNumber) : null);
  }
  
  public byte[] getKeyIdentifier()
  {
    if (keyidentifier != null)
    {
      return keyidentifier.getOctets();
    }
    
    return null;
  }
  
  public GeneralNames getAuthorityCertIssuer()
  {
    return certissuer;
  }
  
  public BigInteger getAuthorityCertSerialNumber()
  {
    if (certserno != null)
    {
      return certserno.getValue();
    }
    
    return null;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (keyidentifier != null)
    {
      v.add(new DERTaggedObject(false, 0, keyidentifier));
    }
    
    if (certissuer != null)
    {
      v.add(new DERTaggedObject(false, 1, certissuer));
    }
    
    if (certserno != null)
    {
      v.add(new DERTaggedObject(false, 2, certserno));
    }
    

    return new DERSequence(v);
  }
  
  public String toString()
  {
    return "AuthorityKeyIdentifier: KeyID(" + keyidentifier.getOctets() + ")";
  }
}
