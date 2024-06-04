package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;
















public class ECDSAPublicKey
  extends PublicKeyDataObject
{
  private ASN1ObjectIdentifier usage;
  private BigInteger primeModulusP;
  private BigInteger firstCoefA;
  private BigInteger secondCoefB;
  private byte[] basePointG;
  private BigInteger orderOfBasePointR;
  private byte[] publicPointY;
  private BigInteger cofactorF;
  private int options;
  private static final int P = 1;
  private static final int A = 2;
  private static final int B = 4;
  private static final int G = 8;
  private static final int R = 16;
  private static final int Y = 32;
  private static final int F = 64;
  
  ECDSAPublicKey(ASN1Sequence seq)
    throws IllegalArgumentException
  {
    Enumeration en = seq.getObjects();
    
    usage = ASN1ObjectIdentifier.getInstance(en.nextElement());
    
    options = 0;
    while (en.hasMoreElements())
    {
      Object obj = en.nextElement();
      
      if ((obj instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject to = (ASN1TaggedObject)obj;
        switch (to.getTagNo())
        {
        case 1: 
          setPrimeModulusP(UnsignedInteger.getInstance(to).getValue());
          break;
        case 2: 
          setFirstCoefA(UnsignedInteger.getInstance(to).getValue());
          break;
        case 3: 
          setSecondCoefB(UnsignedInteger.getInstance(to).getValue());
          break;
        case 4: 
          setBasePointG(ASN1OctetString.getInstance(to, false));
          break;
        case 5: 
          setOrderOfBasePointR(UnsignedInteger.getInstance(to).getValue());
          break;
        case 6: 
          setPublicPointY(ASN1OctetString.getInstance(to, false));
          break;
        case 7: 
          setCofactorF(UnsignedInteger.getInstance(to).getValue());
          break;
        default: 
          options = 0;
          throw new IllegalArgumentException("Unknown Object Identifier!");
        }
      }
      else
      {
        throw new IllegalArgumentException("Unknown Object Identifier!");
      }
    }
    if ((options != 32) && (options != 127))
    {
      throw new IllegalArgumentException("All options must be either present or absent!");
    }
  }
  
  public ECDSAPublicKey(ASN1ObjectIdentifier usage, byte[] ppY)
    throws IllegalArgumentException
  {
    this.usage = usage;
    setPublicPointY(new DEROctetString(ppY));
  }
  
  public ECDSAPublicKey(ASN1ObjectIdentifier usage, BigInteger p, BigInteger a, BigInteger b, byte[] basePoint, BigInteger order, byte[] publicPoint, int cofactor)
  {
    this.usage = usage;
    setPrimeModulusP(p);
    setFirstCoefA(a);
    setSecondCoefB(b);
    setBasePointG(new DEROctetString(basePoint));
    setOrderOfBasePointR(order);
    setPublicPointY(new DEROctetString(publicPoint));
    setCofactorF(BigInteger.valueOf(cofactor));
  }
  
  public ASN1ObjectIdentifier getUsage()
  {
    return usage;
  }
  
  public byte[] getBasePointG()
  {
    if ((options & 0x8) != 0)
    {
      return Arrays.clone(basePointG);
    }
    

    return null;
  }
  

  private void setBasePointG(ASN1OctetString basePointG)
    throws IllegalArgumentException
  {
    if ((options & 0x8) == 0)
    {
      options |= 0x8;
      this.basePointG = basePointG.getOctets();
    }
    else
    {
      throw new IllegalArgumentException("Base Point G already set");
    }
  }
  
  public BigInteger getCofactorF()
  {
    if ((options & 0x40) != 0)
    {
      return cofactorF;
    }
    

    return null;
  }
  

  private void setCofactorF(BigInteger cofactorF)
    throws IllegalArgumentException
  {
    if ((options & 0x40) == 0)
    {
      options |= 0x40;
      this.cofactorF = cofactorF;
    }
    else
    {
      throw new IllegalArgumentException("Cofactor F already set");
    }
  }
  
  public BigInteger getFirstCoefA()
  {
    if ((options & 0x2) != 0)
    {
      return firstCoefA;
    }
    

    return null;
  }
  

  private void setFirstCoefA(BigInteger firstCoefA)
    throws IllegalArgumentException
  {
    if ((options & 0x2) == 0)
    {
      options |= 0x2;
      this.firstCoefA = firstCoefA;
    }
    else
    {
      throw new IllegalArgumentException("First Coef A already set");
    }
  }
  
  public BigInteger getOrderOfBasePointR()
  {
    if ((options & 0x10) != 0)
    {
      return orderOfBasePointR;
    }
    

    return null;
  }
  

  private void setOrderOfBasePointR(BigInteger orderOfBasePointR)
    throws IllegalArgumentException
  {
    if ((options & 0x10) == 0)
    {
      options |= 0x10;
      this.orderOfBasePointR = orderOfBasePointR;
    }
    else
    {
      throw new IllegalArgumentException("Order of base point R already set");
    }
  }
  
  public BigInteger getPrimeModulusP()
  {
    if ((options & 0x1) != 0)
    {
      return primeModulusP;
    }
    

    return null;
  }
  

  private void setPrimeModulusP(BigInteger primeModulusP)
  {
    if ((options & 0x1) == 0)
    {
      options |= 0x1;
      this.primeModulusP = primeModulusP;
    }
    else
    {
      throw new IllegalArgumentException("Prime Modulus P already set");
    }
  }
  
  public byte[] getPublicPointY()
  {
    if ((options & 0x20) != 0)
    {
      return Arrays.clone(publicPointY);
    }
    

    return null;
  }
  

  private void setPublicPointY(ASN1OctetString publicPointY)
    throws IllegalArgumentException
  {
    if ((options & 0x20) == 0)
    {
      options |= 0x20;
      this.publicPointY = publicPointY.getOctets();
    }
    else
    {
      throw new IllegalArgumentException("Public Point Y already set");
    }
  }
  
  public BigInteger getSecondCoefB()
  {
    if ((options & 0x4) != 0)
    {
      return secondCoefB;
    }
    

    return null;
  }
  

  private void setSecondCoefB(BigInteger secondCoefB)
    throws IllegalArgumentException
  {
    if ((options & 0x4) == 0)
    {
      options |= 0x4;
      this.secondCoefB = secondCoefB;
    }
    else
    {
      throw new IllegalArgumentException("Second Coef B already set");
    }
  }
  
  public boolean hasParameters()
  {
    return primeModulusP != null;
  }
  
  public ASN1EncodableVector getASN1EncodableVector(ASN1ObjectIdentifier oid, boolean publicPointOnly)
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    v.add(oid);
    
    if (!publicPointOnly)
    {
      v.add(new UnsignedInteger(1, getPrimeModulusP()));
      v.add(new UnsignedInteger(2, getFirstCoefA()));
      v.add(new UnsignedInteger(3, getSecondCoefB()));
      v.add(new DERTaggedObject(false, 4, new DEROctetString(getBasePointG())));
      v.add(new UnsignedInteger(5, getOrderOfBasePointR()));
    }
    v.add(new DERTaggedObject(false, 6, new DEROctetString(getPublicPointY())));
    if (!publicPointOnly)
    {
      v.add(new UnsignedInteger(7, getCofactorF()));
    }
    
    return v;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERSequence(getASN1EncodableVector(usage, !hasParameters()));
  }
}
