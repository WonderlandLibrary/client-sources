package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Mod;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SecP256R1FieldElement
  extends ECFieldElement
{
  public static final BigInteger Q = SecP256R1Curve.q;
  
  protected int[] x;
  
  public SecP256R1FieldElement(BigInteger x)
  {
    if ((x == null) || (x.signum() < 0) || (x.compareTo(Q) >= 0))
    {
      throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
    }
    
    this.x = SecP256R1Field.fromBigInteger(x);
  }
  
  public SecP256R1FieldElement()
  {
    x = Nat256.create();
  }
  
  protected SecP256R1FieldElement(int[] x)
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
    return "SecP256R1Field";
  }
  
  public int getFieldSize()
  {
    return Q.bitLength();
  }
  
  public ECFieldElement add(ECFieldElement b)
  {
    int[] z = Nat256.create();
    SecP256R1Field.add(x, x, z);
    return new SecP256R1FieldElement(z);
  }
  
  public ECFieldElement addOne()
  {
    int[] z = Nat256.create();
    SecP256R1Field.addOne(x, z);
    return new SecP256R1FieldElement(z);
  }
  
  public ECFieldElement subtract(ECFieldElement b)
  {
    int[] z = Nat256.create();
    SecP256R1Field.subtract(x, x, z);
    return new SecP256R1FieldElement(z);
  }
  
  public ECFieldElement multiply(ECFieldElement b)
  {
    int[] z = Nat256.create();
    SecP256R1Field.multiply(x, x, z);
    return new SecP256R1FieldElement(z);
  }
  

  public ECFieldElement divide(ECFieldElement b)
  {
    int[] z = Nat256.create();
    Mod.invert(SecP256R1Field.P, x, z);
    SecP256R1Field.multiply(z, x, z);
    return new SecP256R1FieldElement(z);
  }
  
  public ECFieldElement negate()
  {
    int[] z = Nat256.create();
    SecP256R1Field.negate(x, z);
    return new SecP256R1FieldElement(z);
  }
  
  public ECFieldElement square()
  {
    int[] z = Nat256.create();
    SecP256R1Field.square(x, z);
    return new SecP256R1FieldElement(z);
  }
  

  public ECFieldElement invert()
  {
    int[] z = Nat256.create();
    Mod.invert(SecP256R1Field.P, x, z);
    return new SecP256R1FieldElement(z);
  }
  






  public ECFieldElement sqrt()
  {
    int[] x1 = x;
    if ((Nat256.isZero(x1)) || (Nat256.isOne(x1)))
    {
      return this;
    }
    
    int[] t1 = Nat256.create();
    int[] t2 = Nat256.create();
    
    SecP256R1Field.square(x1, t1);
    SecP256R1Field.multiply(t1, x1, t1);
    
    SecP256R1Field.squareN(t1, 2, t2);
    SecP256R1Field.multiply(t2, t1, t2);
    
    SecP256R1Field.squareN(t2, 4, t1);
    SecP256R1Field.multiply(t1, t2, t1);
    
    SecP256R1Field.squareN(t1, 8, t2);
    SecP256R1Field.multiply(t2, t1, t2);
    
    SecP256R1Field.squareN(t2, 16, t1);
    SecP256R1Field.multiply(t1, t2, t1);
    
    SecP256R1Field.squareN(t1, 32, t1);
    SecP256R1Field.multiply(t1, x1, t1);
    
    SecP256R1Field.squareN(t1, 96, t1);
    SecP256R1Field.multiply(t1, x1, t1);
    
    SecP256R1Field.squareN(t1, 94, t1);
    SecP256R1Field.square(t1, t2);
    
    return Nat256.eq(x1, t2) ? new SecP256R1FieldElement(t1) : null;
  }
  
  public boolean equals(Object other)
  {
    if (other == this)
    {
      return true;
    }
    
    if (!(other instanceof SecP256R1FieldElement))
    {
      return false;
    }
    
    SecP256R1FieldElement o = (SecP256R1FieldElement)other;
    return Nat256.eq(x, x);
  }
  
  public int hashCode()
  {
    return Q.hashCode() ^ Arrays.hashCode(x, 0, 8);
  }
}
