package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SecT193FieldElement
  extends ECFieldElement
{
  protected long[] x;
  
  public SecT193FieldElement(BigInteger x)
  {
    if ((x == null) || (x.signum() < 0) || (x.bitLength() > 193))
    {
      throw new IllegalArgumentException("x value invalid for SecT193FieldElement");
    }
    
    this.x = SecT193Field.fromBigInteger(x);
  }
  
  public SecT193FieldElement()
  {
    x = Nat256.create64();
  }
  
  protected SecT193FieldElement(long[] x)
  {
    this.x = x;
  }
  





  public boolean isOne()
  {
    return Nat256.isOne64(x);
  }
  
  public boolean isZero()
  {
    return Nat256.isZero64(x);
  }
  
  public boolean testBitZero()
  {
    return (x[0] & 1L) != 0L;
  }
  
  public BigInteger toBigInteger()
  {
    return Nat256.toBigInteger64(x);
  }
  
  public String getFieldName()
  {
    return "SecT193Field";
  }
  
  public int getFieldSize()
  {
    return 193;
  }
  
  public ECFieldElement add(ECFieldElement b)
  {
    long[] z = Nat256.create64();
    SecT193Field.add(x, x, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement addOne()
  {
    long[] z = Nat256.create64();
    SecT193Field.addOne(x, z);
    return new SecT193FieldElement(z);
  }
  

  public ECFieldElement subtract(ECFieldElement b)
  {
    return add(b);
  }
  
  public ECFieldElement multiply(ECFieldElement b)
  {
    long[] z = Nat256.create64();
    SecT193Field.multiply(x, x, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
  {
    return multiplyPlusProduct(b, x, y);
  }
  
  public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
  {
    long[] ax = this.x;long[] bx = x;
    long[] xx = x;long[] yx = x;
    
    long[] tt = Nat256.createExt64();
    SecT193Field.multiplyAddToExt(ax, bx, tt);
    SecT193Field.multiplyAddToExt(xx, yx, tt);
    
    long[] z = Nat256.create64();
    SecT193Field.reduce(tt, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement divide(ECFieldElement b)
  {
    return multiply(b.invert());
  }
  
  public ECFieldElement negate()
  {
    return this;
  }
  
  public ECFieldElement square()
  {
    long[] z = Nat256.create64();
    SecT193Field.square(x, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y)
  {
    return squarePlusProduct(x, y);
  }
  
  public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y)
  {
    long[] ax = this.x;
    long[] xx = x;long[] yx = x;
    
    long[] tt = Nat256.createExt64();
    SecT193Field.squareAddToExt(ax, tt);
    SecT193Field.multiplyAddToExt(xx, yx, tt);
    
    long[] z = Nat256.create64();
    SecT193Field.reduce(tt, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement squarePow(int pow)
  {
    if (pow < 1)
    {
      return this;
    }
    
    long[] z = Nat256.create64();
    SecT193Field.squareN(x, pow, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement invert()
  {
    long[] z = Nat256.create64();
    SecT193Field.invert(x, z);
    return new SecT193FieldElement(z);
  }
  
  public ECFieldElement sqrt()
  {
    long[] z = Nat256.create64();
    SecT193Field.sqrt(x, z);
    return new SecT193FieldElement(z);
  }
  
  public int getRepresentation()
  {
    return 2;
  }
  
  public int getM()
  {
    return 193;
  }
  
  public int getK1()
  {
    return 15;
  }
  
  public int getK2()
  {
    return 0;
  }
  
  public int getK3()
  {
    return 0;
  }
  
  public boolean equals(Object other)
  {
    if (other == this)
    {
      return true;
    }
    
    if (!(other instanceof SecT193FieldElement))
    {
      return false;
    }
    
    SecT193FieldElement o = (SecT193FieldElement)other;
    return Nat256.eq64(x, x);
  }
  
  public int hashCode()
  {
    return 0x1D731F ^ Arrays.hashCode(x, 0, 4);
  }
}
