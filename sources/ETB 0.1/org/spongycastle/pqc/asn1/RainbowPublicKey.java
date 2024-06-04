package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;


















public class RainbowPublicKey
  extends ASN1Object
{
  private ASN1Integer version;
  private ASN1ObjectIdentifier oid;
  private ASN1Integer docLength;
  private byte[][] coeffQuadratic;
  private byte[][] coeffSingular;
  private byte[] coeffScalar;
  
  private RainbowPublicKey(ASN1Sequence seq)
  {
    if ((seq.getObjectAt(0) instanceof ASN1Integer))
    {
      version = ASN1Integer.getInstance(seq.getObjectAt(0));
    }
    else
    {
      oid = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(0));
    }
    
    docLength = ASN1Integer.getInstance(seq.getObjectAt(1));
    
    ASN1Sequence asnCoeffQuad = ASN1Sequence.getInstance(seq.getObjectAt(2));
    coeffQuadratic = new byte[asnCoeffQuad.size()][];
    for (int quadSize = 0; quadSize < asnCoeffQuad.size(); quadSize++)
    {
      coeffQuadratic[quadSize] = ASN1OctetString.getInstance(asnCoeffQuad.getObjectAt(quadSize)).getOctets();
    }
    
    ASN1Sequence asnCoeffSing = (ASN1Sequence)seq.getObjectAt(3);
    coeffSingular = new byte[asnCoeffSing.size()][];
    for (int singSize = 0; singSize < asnCoeffSing.size(); singSize++)
    {
      coeffSingular[singSize] = ASN1OctetString.getInstance(asnCoeffSing.getObjectAt(singSize)).getOctets();
    }
    
    ASN1Sequence asnCoeffScalar = (ASN1Sequence)seq.getObjectAt(4);
    coeffScalar = ASN1OctetString.getInstance(asnCoeffScalar.getObjectAt(0)).getOctets();
  }
  
  public RainbowPublicKey(int docLength, short[][] coeffQuadratic, short[][] coeffSingular, short[] coeffScalar)
  {
    version = new ASN1Integer(0L);
    this.docLength = new ASN1Integer(docLength);
    this.coeffQuadratic = RainbowUtil.convertArray(coeffQuadratic);
    this.coeffSingular = RainbowUtil.convertArray(coeffSingular);
    this.coeffScalar = RainbowUtil.convertArray(coeffScalar);
  }
  
  public static RainbowPublicKey getInstance(Object o)
  {
    if ((o instanceof RainbowPublicKey))
    {
      return (RainbowPublicKey)o;
    }
    if (o != null)
    {
      return new RainbowPublicKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  



  public int getDocLength()
  {
    return docLength.getValue().intValue();
  }
  



  public short[][] getCoeffQuadratic()
  {
    return RainbowUtil.convertArray(coeffQuadratic);
  }
  



  public short[][] getCoeffSingular()
  {
    return RainbowUtil.convertArray(coeffSingular);
  }
  



  public short[] getCoeffScalar()
  {
    return RainbowUtil.convertArray(coeffScalar);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    

    if (version != null)
    {
      v.add(version);
    }
    else
    {
      v.add(oid);
    }
    

    v.add(docLength);
    

    ASN1EncodableVector asnCoeffQuad = new ASN1EncodableVector();
    for (int i = 0; i < coeffQuadratic.length; i++)
    {
      asnCoeffQuad.add(new DEROctetString(coeffQuadratic[i]));
    }
    v.add(new DERSequence(asnCoeffQuad));
    

    ASN1EncodableVector asnCoeffSing = new ASN1EncodableVector();
    for (int i = 0; i < coeffSingular.length; i++)
    {
      asnCoeffSing.add(new DEROctetString(coeffSingular[i]));
    }
    v.add(new DERSequence(asnCoeffSing));
    

    ASN1EncodableVector asnCoeffScalar = new ASN1EncodableVector();
    asnCoeffScalar.add(new DEROctetString(coeffScalar));
    v.add(new DERSequence(asnCoeffScalar));
    

    return new DERSequence(v);
  }
}
