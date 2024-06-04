package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;












public class RecipientEncryptedKey
  extends ASN1Object
{
  private KeyAgreeRecipientIdentifier identifier;
  private ASN1OctetString encryptedKey;
  
  private RecipientEncryptedKey(ASN1Sequence seq)
  {
    identifier = KeyAgreeRecipientIdentifier.getInstance(seq.getObjectAt(0));
    encryptedKey = ((ASN1OctetString)seq.getObjectAt(1));
  }
  











  public static RecipientEncryptedKey getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  














  public static RecipientEncryptedKey getInstance(Object obj)
  {
    if ((obj instanceof RecipientEncryptedKey))
    {
      return (RecipientEncryptedKey)obj;
    }
    
    if (obj != null)
    {
      return new RecipientEncryptedKey(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public RecipientEncryptedKey(KeyAgreeRecipientIdentifier id, ASN1OctetString encryptedKey)
  {
    identifier = id;
    this.encryptedKey = encryptedKey;
  }
  
  public KeyAgreeRecipientIdentifier getIdentifier()
  {
    return identifier;
  }
  
  public ASN1OctetString getEncryptedKey()
  {
    return encryptedKey;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(identifier);
    v.add(encryptedKey);
    
    return new DERSequence(v);
  }
}
