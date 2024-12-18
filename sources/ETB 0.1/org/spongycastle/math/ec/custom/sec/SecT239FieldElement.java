package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.Arrays;

public class SecT239FieldElement
  extends ECFieldElement
{
  protected long[] x;
  
  public SecT239FieldElement(BigInteger x)
  {
    if ((x == null) || (x.signum() < 0) || (x.bitLength() > 239))
    {
      throw new IllegalArgumentException("x value invalid for SecT239FieldElement");
    }
    
    this.x = SecT239Field.fromBigInteger(x);
  }
  
  public SecT239FieldElement()
  {
    x = Nat256.create64();
  }
  
  protected SecT239FieldElement(long[] x)
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
    return "SecT239Field";
  }
  
  public int getFieldSize()
  {
    return 239;
  }
  
  public ECFieldElement add(ECFieldElement b)
  {
    long[] z = Nat256.create64();
    SecT239Field.add(x, x, z);
    return new SecT239FieldElement(z);
  }
  
  public ECFieldElement addOne()
  {
    long[] z = Nat256.create64();
    SecT239Field.addOne(x, z);
    return new SecT239FieldElement(z);
  }
  

  public ECFieldElement subtract(ECFieldElement b)
  {
    return add(b);
  }
  
  public ECFieldElement multiply(ECFieldElement b)
  {
    long[] z = Nat256.create64();
    SecT239Field.multiply(x, x, z);
    return new SecT239FieldElement(z);
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
    SecT239Field.multiplyAddToExt(ax, bx, tt);
    SecT239Field.multiplyAddToExt(xx, yx, tt);
    
    long[] z = Nat256.create64();
    SecT239Field.reduce(tt, z);
    return new SecT239FieldElement(z);
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
    SecT239Field.square(x, z);
    return new SecT239FieldElement(z);
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
    SecT239Field.squareAddToExt(ax, tt);
    SecT239Field.multiplyAddToExt(xx, yx, tt);
    
    long[] z = Nat256.create64();
    SecT239Field.reduce(tt, z);
    return new SecT239FieldElement(z);
  }
  
  public ECFieldElement squarePow(int pow)
  {
    if (pow < 1)
    {
      return this;
    }
    
    long[] z = Nat256.create64();
    SecT239Field.squareN(x, pow, z);
    return new SecT239FieldElement(z);
  }
  
  public ECFieldElement invert()
  {
    long[] z = Nat256.create64();
    SecT239Field.invert(x, z);
    return new SecT239FieldElement(z);
  }
  
  public ECFieldElement sqrt()
  {
    long[] z = Nat256.create64();
    SecT239Field.sqrt(x, z);
    return new SecT239FieldElement(z);
  }
  
  public int getRepresentation()
  {
    return 2;
  }
  
  public int getM()
  {
    return 239;
  }
  
  public int getK1()
  {
    return 158;
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
    
    if (!(other instanceof SecT239FieldElement))
    {
      return false;
    }
    
    SecT239FieldElement o = (SecT239FieldElement)other;
    return Nat256.eq64(x, x);
  }
  
  public int hashCode()
  {
    return 0x16CAFFE ^ Arrays.hashCode(x, 0, 4);
  }
}
