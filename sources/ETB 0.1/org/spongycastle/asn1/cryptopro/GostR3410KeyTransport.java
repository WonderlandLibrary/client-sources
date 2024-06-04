package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;










public class GostR3410KeyTransport
  extends ASN1Object
{
  private final Gost2814789EncryptedKey sessionEncryptedKey;
  private final GostR3410TransportParameters transportParameters;
  
  private GostR3410KeyTransport(ASN1Sequence seq)
  {
    sessionEncryptedKey = Gost2814789EncryptedKey.getInstance(seq.getObjectAt(0));
    transportParameters = GostR3410TransportParameters.getInstance(ASN1TaggedObject.getInstance(seq.getObjectAt(1)), false);
  }
  

  public static GostR3410KeyTransport getInstance(Object obj)
  {
    if ((obj instanceof GostR3410KeyTransport))
    {
      return (GostR3410KeyTransport)obj;
    }
    
    if (obj != null)
    {
      return new GostR3410KeyTransport(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public Gost2814789EncryptedKey getSessionEncryptedKey()
  {
    return sessionEncryptedKey;
  }
  
  public GostR3410TransportParameters getTransportParameters()
  {
    return transportParameters;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(sessionEncryptedKey);
    if (transportParameters != null)
    {
      v.add(new DERTaggedObject(false, 0, transportParameters));
    }
    
    return new DERSequence(v);
  }
}
