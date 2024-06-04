package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;










public class SubjectPublicKeyInfo
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private DERBitString keyData;
  
  public static SubjectPublicKeyInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static SubjectPublicKeyInfo getInstance(Object obj)
  {
    if ((obj instanceof SubjectPublicKeyInfo))
    {
      return (SubjectPublicKeyInfo)obj;
    }
    if (obj != null)
    {
      return new SubjectPublicKeyInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public SubjectPublicKeyInfo(AlgorithmIdentifier algId, ASN1Encodable publicKey)
    throws IOException
  {
    keyData = new DERBitString(publicKey);
    this.algId = algId;
  }
  


  public SubjectPublicKeyInfo(AlgorithmIdentifier algId, byte[] publicKey)
  {
    keyData = new DERBitString(publicKey);
    this.algId = algId;
  }
  

  /**
   * @deprecated
   */
  public SubjectPublicKeyInfo(ASN1Sequence seq)
  {
    if (seq.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    Enumeration e = seq.getObjects();
    
    algId = AlgorithmIdentifier.getInstance(e.nextElement());
    keyData = DERBitString.getInstance(e.nextElement());
  }
  
  public AlgorithmIdentifier getAlgorithm()
  {
    return algId;
  }
  

  /**
   * @deprecated
   */
  public AlgorithmIdentifier getAlgorithmId()
  {
    return algId;
  }
  








  public ASN1Primitive parsePublicKey()
    throws IOException
  {
    ASN1InputStream aIn = new ASN1InputStream(keyData.getOctets());
    
    return aIn.readObject();
  }
  






  /**
   * @deprecated
   */
  public ASN1Primitive getPublicKey()
    throws IOException
  {
    ASN1InputStream aIn = new ASN1InputStream(keyData.getOctets());
    
    return aIn.readObject();
  }
  





  public DERBitString getPublicKeyData()
  {
    return keyData;
  }
  








  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(algId);
    v.add(keyData);
    
    return new DERSequence(v);
  }
}
