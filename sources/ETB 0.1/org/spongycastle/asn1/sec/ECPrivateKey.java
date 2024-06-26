package org.spongycastle.asn1.sec;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
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
import org.spongycastle.util.BigIntegers;






public class ECPrivateKey
  extends ASN1Object
{
  private ASN1Sequence seq;
  
  private ECPrivateKey(ASN1Sequence seq)
  {
    this.seq = seq;
  }
  

  public static ECPrivateKey getInstance(Object obj)
  {
    if ((obj instanceof ECPrivateKey))
    {
      return (ECPrivateKey)obj;
    }
    
    if (obj != null)
    {
      return new ECPrivateKey(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  /**
   * @deprecated
   */
  public ECPrivateKey(BigInteger key)
  {
    this(key.bitLength(), key);
  }
  








  public ECPrivateKey(int orderBitLength, BigInteger key)
  {
    byte[] bytes = BigIntegers.asUnsignedByteArray((orderBitLength + 7) / 8, key);
    
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(new ASN1Integer(1L));
    v.add(new DEROctetString(bytes));
    
    seq = new DERSequence(v);
  }
  


  /**
   * @deprecated
   */
  public ECPrivateKey(BigInteger key, ASN1Encodable parameters)
  {
    this(key, null, parameters);
  }
  



  /**
   * @deprecated
   */
  public ECPrivateKey(BigInteger key, DERBitString publicKey, ASN1Encodable parameters)
  {
    this(key.bitLength(), key, publicKey, parameters);
  }
  



  public ECPrivateKey(int orderBitLength, BigInteger key, ASN1Encodable parameters)
  {
    this(orderBitLength, key, null, parameters);
  }
  




  public ECPrivateKey(int orderBitLength, BigInteger key, DERBitString publicKey, ASN1Encodable parameters)
  {
    byte[] bytes = BigIntegers.asUnsignedByteArray((orderBitLength + 7) / 8, key);
    
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(new ASN1Integer(1L));
    v.add(new DEROctetString(bytes));
    
    if (parameters != null)
    {
      v.add(new DERTaggedObject(true, 0, parameters));
    }
    
    if (publicKey != null)
    {
      v.add(new DERTaggedObject(true, 1, publicKey));
    }
    
    seq = new DERSequence(v);
  }
  
  public BigInteger getKey()
  {
    ASN1OctetString octs = (ASN1OctetString)seq.getObjectAt(1);
    
    return new BigInteger(1, octs.getOctets());
  }
  
  public DERBitString getPublicKey()
  {
    return (DERBitString)getObjectInTag(1);
  }
  
  public ASN1Primitive getParameters()
  {
    return getObjectInTag(0);
  }
  
  private ASN1Primitive getObjectInTag(int tagNo)
  {
    Enumeration e = seq.getObjects();
    
    while (e.hasMoreElements())
    {
      ASN1Encodable obj = (ASN1Encodable)e.nextElement();
      
      if ((obj instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject tag = (ASN1TaggedObject)obj;
        if (tag.getTagNo() == tagNo)
        {
          return tag.getObject().toASN1Primitive();
        }
      }
    }
    return null;
  }
  







  public ASN1Primitive toASN1Primitive()
  {
    return seq;
  }
}
