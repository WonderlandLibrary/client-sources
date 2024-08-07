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
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;


public class McEliecePrivateKey
  extends ASN1Object
{
  private int n;
  private int k;
  private byte[] encField;
  private byte[] encGp;
  private byte[] encSInv;
  private byte[] encP1;
  private byte[] encP2;
  
  public McEliecePrivateKey(int n, int k, GF2mField field, PolynomialGF2mSmallM goppaPoly, Permutation p1, Permutation p2, GF2Matrix sInv)
  {
    this.n = n;
    this.k = k;
    encField = field.getEncoded();
    encGp = goppaPoly.getEncoded();
    encSInv = sInv.getEncoded();
    encP1 = p1.getEncoded();
    encP2 = p2.getEncoded();
  }
  
  public static McEliecePrivateKey getInstance(Object o)
  {
    if ((o instanceof McEliecePrivateKey))
    {
      return (McEliecePrivateKey)o;
    }
    if (o != null)
    {
      return new McEliecePrivateKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  private McEliecePrivateKey(ASN1Sequence seq)
  {
    BigInteger bigN = ((ASN1Integer)seq.getObjectAt(0)).getValue();
    n = bigN.intValue();
    
    BigInteger bigK = ((ASN1Integer)seq.getObjectAt(1)).getValue();
    k = bigK.intValue();
    
    encField = ((ASN1OctetString)seq.getObjectAt(2)).getOctets();
    
    encGp = ((ASN1OctetString)seq.getObjectAt(3)).getOctets();
    
    encP1 = ((ASN1OctetString)seq.getObjectAt(4)).getOctets();
    
    encP2 = ((ASN1OctetString)seq.getObjectAt(5)).getOctets();
    
    encSInv = ((ASN1OctetString)seq.getObjectAt(6)).getOctets();
  }
  
  public int getN()
  {
    return n;
  }
  
  public int getK()
  {
    return k;
  }
  
  public GF2mField getField()
  {
    return new GF2mField(encField);
  }
  
  public PolynomialGF2mSmallM getGoppaPoly()
  {
    return new PolynomialGF2mSmallM(getField(), encGp);
  }
  
  public GF2Matrix getSInv()
  {
    return new GF2Matrix(encSInv);
  }
  
  public Permutation getP1()
  {
    return new Permutation(encP1);
  }
  
  public Permutation getP2()
  {
    return new Permutation(encP2);
  }
  


  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    

    v.add(new ASN1Integer(n));
    

    v.add(new ASN1Integer(k));
    

    v.add(new DEROctetString(encField));
    

    v.add(new DEROctetString(encGp));
    

    v.add(new DEROctetString(encP1));
    

    v.add(new DEROctetString(encP2));
    

    v.add(new DEROctetString(encSInv));
    
    return new DERSequence(v);
  }
}
