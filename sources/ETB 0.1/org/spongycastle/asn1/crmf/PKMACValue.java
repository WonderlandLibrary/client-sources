package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.cmp.CMPObjectIdentifiers;
import org.spongycastle.asn1.cmp.PBMParameter;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;




public class PKMACValue
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private DERBitString value;
  
  private PKMACValue(ASN1Sequence seq)
  {
    algId = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    value = DERBitString.getInstance(seq.getObjectAt(1));
  }
  
  public static PKMACValue getInstance(Object o)
  {
    if ((o instanceof PKMACValue))
    {
      return (PKMACValue)o;
    }
    
    if (o != null)
    {
      return new PKMACValue(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public static PKMACValue getInstance(ASN1TaggedObject obj, boolean isExplicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, isExplicit));
  }
  







  public PKMACValue(PBMParameter params, DERBitString value)
  {
    this(new AlgorithmIdentifier(CMPObjectIdentifiers.passwordBasedMac, params), value);
  }
  








  public PKMACValue(AlgorithmIdentifier aid, DERBitString value)
  {
    algId = aid;
    this.value = value;
  }
  
  public AlgorithmIdentifier getAlgId()
  {
    return algId;
  }
  
  public DERBitString getValue()
  {
    return value;
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(algId);
    v.add(value);
    
    return new DERSequence(v);
  }
}
