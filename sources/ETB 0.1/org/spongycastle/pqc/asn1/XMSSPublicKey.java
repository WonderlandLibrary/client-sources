package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;











public class XMSSPublicKey
  extends ASN1Object
{
  private final byte[] publicSeed;
  private final byte[] root;
  
  public XMSSPublicKey(byte[] publicSeed, byte[] root)
  {
    this.publicSeed = Arrays.clone(publicSeed);
    this.root = Arrays.clone(root);
  }
  
  private XMSSPublicKey(ASN1Sequence seq)
  {
    if (!ASN1Integer.getInstance(seq.getObjectAt(0)).getValue().equals(BigInteger.valueOf(0L)))
    {
      throw new IllegalArgumentException("unknown version of sequence");
    }
    
    publicSeed = Arrays.clone(DEROctetString.getInstance(seq.getObjectAt(1)).getOctets());
    root = Arrays.clone(DEROctetString.getInstance(seq.getObjectAt(2)).getOctets());
  }
  
  public static XMSSPublicKey getInstance(Object o)
  {
    if ((o instanceof XMSSPublicKey))
    {
      return (XMSSPublicKey)o;
    }
    if (o != null)
    {
      return new XMSSPublicKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public byte[] getPublicSeed()
  {
    return Arrays.clone(publicSeed);
  }
  
  public byte[] getRoot()
  {
    return Arrays.clone(root);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(new ASN1Integer(0L));
    
    v.add(new DEROctetString(publicSeed));
    v.add(new DEROctetString(root));
    
    return new DERSequence(v);
  }
}
