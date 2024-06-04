package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;











public class Gost2814789EncryptedKey
  extends ASN1Object
{
  private final byte[] encryptedKey;
  private final byte[] maskKey;
  private final byte[] macKey;
  
  private Gost2814789EncryptedKey(ASN1Sequence seq)
  {
    if (seq.size() == 2)
    {
      encryptedKey = Arrays.clone(ASN1OctetString.getInstance(seq.getObjectAt(0)).getOctets());
      macKey = Arrays.clone(ASN1OctetString.getInstance(seq.getObjectAt(1)).getOctets());
      maskKey = null;
    }
    else if (seq.size() == 3)
    {
      encryptedKey = Arrays.clone(ASN1OctetString.getInstance(seq.getObjectAt(0)).getOctets());
      maskKey = Arrays.clone(ASN1OctetString.getInstance(ASN1TaggedObject.getInstance(seq.getObjectAt(1)), false).getOctets());
      macKey = Arrays.clone(ASN1OctetString.getInstance(seq.getObjectAt(2)).getOctets());
    }
    else
    {
      throw new IllegalArgumentException("unknown sequence length: " + seq.size());
    }
  }
  

  public static Gost2814789EncryptedKey getInstance(Object obj)
  {
    if ((obj instanceof Gost2814789EncryptedKey))
    {
      return (Gost2814789EncryptedKey)obj;
    }
    
    if (obj != null)
    {
      return new Gost2814789EncryptedKey(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public Gost2814789EncryptedKey(byte[] encryptedKey, byte[] macKey)
  {
    this(encryptedKey, null, macKey);
  }
  
  public Gost2814789EncryptedKey(byte[] encryptedKey, byte[] maskKey, byte[] macKey)
  {
    this.encryptedKey = Arrays.clone(encryptedKey);
    this.maskKey = Arrays.clone(maskKey);
    this.macKey = Arrays.clone(macKey);
  }
  
  public byte[] getEncryptedKey()
  {
    return encryptedKey;
  }
  
  public byte[] getMaskKey()
  {
    return maskKey;
  }
  
  public byte[] getMacKey()
  {
    return macKey;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(new DEROctetString(encryptedKey));
    if (maskKey != null)
    {
      v.add(new DERTaggedObject(false, 0, new DEROctetString(encryptedKey)));
    }
    v.add(new DEROctetString(macKey));
    
    return new DERSequence(v);
  }
}
