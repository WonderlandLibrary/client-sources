package org.spongycastle.math.ec.custom.gm;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SM2P256V1FieldElement
  extends ECFieldElement
{
  public static final BigInteger Q = SM2P256V1Curve.q;
  
  protected int[] x;
  
  public SM2P256V1FieldElement(BigInteger x)
  {
    if ((x == null) || (x.signum() < 0) || (x.compareTo(Q) >= 0))
    {
      throw new IllegalArgumentException("x value invalid for SM2P256V1FieldElement");
    }
    
    this.x = SM2P256V1Field.fromBigInteger(x);
  }
  
  public SM2P256V1FieldElement()
  {
    x = Nat256.create();
  }
  
  protected SM2P256V1FieldElement(int[] x)
  {
    this.x = x;
  }
  
  public boolean isZero()
  {
    return Nat256.isZero(x);
  }
  
  public boolean isOne()
  {
    return Nat256.isOne(x);
  }
  
  public boolean testBitZero()
  {
    return Nat256.getBit(x, 0) == 1;
  }
  
  public BigInteger toBigInteger()
  {
    return Nat256.toBigInteger(x);
  }
  
  public String getFieldName()
  {
    return "SM2P256V1Field";
  }
  
  public int getFieldSize()
  {
    return Q.bitLength();
  }
  
  public ECFieldElement add(ECFieldElement b)
  {
    int[] z = Nat256.create();
    SM2P256V1Field.add(x, x, z);
    return new SM2P256V1FieldElement(z);
  }
  
  public ECFieldElement addOne()
  {
    int[] z = Nat256.create();
    SM2P256V1Field.addOne(x, z);
    return new SM2P256V1FieldElement(z);
  }
  
  public ECFieldElement subtract(ECFieldElement b)
  {
    int[] z = Nat256.create();
    SM2P256V1Field.subtract(x, x, z);
    return new SM2P256V1FieldElement(z);
  }
  
  public ECFieldElement multiply(ECFieldElement b)
  {
    int[] z = Nat256.create();
    SM2P256V1Field.multiply(x, x, z);
    return new SM2P256V1FieldElement(z);
  }
  

  public ECFieldElement divide(ECFieldElement b)
  {
    int[] z = Nat256.create();
    Mod.invert(SM2P256V1Field.P, x, z);
    SM2P256V1Field.multiply(z, x, z);
    return new SM2P256V1FieldElement(z);
  }
  
  public ECFieldElement negate()
  {
    int[] z = Nat256.create();
    SM2P256V1Field.negate(x, z);
    return new SM2P256V1FieldElement(z);
  }
  
  public ECFieldElement square()
  {
    int[] z = Nat256.create();
    SM2P256V1Field.square(x, z);
    return new SM2P256V1FieldElement(z);
  }
  

  public ECFieldElement invert()
  {
    int[] z = Nat256.create();
    Mod.invert(SM2P256V1Field.P, x, z);
    return new SM2P256V1FieldElement(z);
  }
  













  public ECFieldElement sqrt()
  {
    int[] x1 = x;
    if ((Nat256.isZero(x1)) || (Nat256.isOne(x1)))
    {
      return this;
    }
    
    int[] x2 = Nat256.create();
    SM2P256V1Field.square(x1, x2);
    SM2P256V1Field.multiply(x2, x1, x2);
    int[] x4 = Nat256.create();
    SM2P256V1Field.squareN(x2, 2, x4);
    SM2P256V1Field.multiply(x4, x2, x4);
    int[] x6 = Nat256.create();
    SM2P256V1Field.squareN(x4, 2, x6);
    SM2P256V1Field.multiply(x6, x2, x6);
    int[] x12 = x2;
    SM2P256V1Field.squareN(x6, 6, x12);
    SM2P256V1Field.multiply(x12, x6, x12);
    int[] x24 = Nat256.create();
    SM2P256V1Field.squareN(x12, 12, x24);
    SM2P256V1Field.multiply(x24, x12, x24);
    int[] x30 = x12;
    SM2P256V1Field.squareN(x24, 6, x30);
    SM2P256V1Field.multiply(x30, x6, x30);
    int[] x31 = x6;
    SM2P256V1Field.square(x30, x31);
    SM2P256V1Field.multiply(x31, x1, x31);
    
    int[] t1 = x24;
    SM2P256V1Field.squareN(x31, 31, t1);
    
    int[] x62 = x30;
    SM2P256V1Field.multiply(t1, x31, x62);
    
    SM2P256V1Field.squareN(t1, 32, t1);
    SM2P256V1Field.multiply(t1, x62, t1);
    SM2P256V1Field.squareN(t1, 62, t1);
    SM2P256V1Field.multiply(t1, x62, t1);
    SM2P256V1Field.squareN(t1, 4, t1);
    SM2P256V1Field.multiply(t1, x4, t1);
    SM2P256V1Field.squareN(t1, 32, t1);
    SM2P256V1Field.multiply(t1, x1, t1);
    SM2P256V1Field.squareN(t1, 62, t1);
    
    int[] t2 = x4;
    SM2P256V1Field.square(t1, t2);
    
    return Nat256.eq(x1, t2) ? new SM2P256V1FieldElement(t1) : null;
  }
  
  public boolean equals(Object other)
  {
    if (other == this)
    {
      return true;
    }
    
    if (!(other instanceof SM2P256V1FieldElement))
    {
      return false;
    }
    
    SM2P256V1FieldElement o = (SM2P256V1FieldElement)other;
    return Nat256.eq(x, x);
  }
  
  public int hashCode()
  {
    return Q.hashCode() ^ Arrays.hashCode(x, 0, 8);
  }
}
