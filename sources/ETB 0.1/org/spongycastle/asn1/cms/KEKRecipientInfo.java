package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

















public class KEKRecipientInfo
  extends ASN1Object
{
  private ASN1Integer version;
  private KEKIdentifier kekid;
  private AlgorithmIdentifier keyEncryptionAlgorithm;
  private ASN1OctetString encryptedKey;
  
  public KEKRecipientInfo(KEKIdentifier kekid, AlgorithmIdentifier keyEncryptionAlgorithm, ASN1OctetString encryptedKey)
  {
    version = new ASN1Integer(4L);
    this.kekid = kekid;
    this.keyEncryptionAlgorithm = keyEncryptionAlgorithm;
    this.encryptedKey = encryptedKey;
  }
  

  public KEKRecipientInfo(ASN1Sequence seq)
  {
    version = ((ASN1Integer)seq.getObjectAt(0));
    kekid = KEKIdentifier.getInstance(seq.getObjectAt(1));
    keyEncryptionAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(2));
    encryptedKey = ((ASN1OctetString)seq.getObjectAt(3));
  }
  











  public static KEKRecipientInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  














  public static KEKRecipientInfo getInstance(Object obj)
  {
    if ((obj instanceof KEKRecipientInfo))
    {
      return (KEKRecipientInfo)obj;
    }
    
    if (obj != null)
    {
      return new KEKRecipientInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public KEKIdentifier getKekid()
  {
    return kekid;
  }
  
  public AlgorithmIdentifier getKeyEncryptionAlgorithm()
  {
    return keyEncryptionAlgorithm;
  }
  
  public ASN1OctetString getEncryptedKey()
  {
    return encryptedKey;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(kekid);
    v.add(keyEncryptionAlgorithm);
    v.add(encryptedKey);
    
    return new DERSequence(v);
  }
}
