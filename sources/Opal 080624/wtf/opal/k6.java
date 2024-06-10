package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class k6 extends k3<HashMap<String, Float>> {
  private float S;
  
  private float m;
  
  private float w;
  
  private float I;
  
  private float M;
  
  private float P;
  
  private boolean V;
  
  private float B = 6.0F;
  
  private static final long b = on.a(-4620409540693846108L, 119073636257981021L, MethodHandles.lookup().lookupClass()).a(56390248988229L);
  
  private static final String c;
  
  public k6(byte paramByte, float paramFloat1, long paramLong, float paramFloat2) {
    super(c);
    this.S = paramFloat1;
    this.m = paramFloat2;
    p(new Object[0]);
  }
  
  public k6(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, long paramLong) {
    this((byte)i, paramFloat1, l, paramFloat2);
    this.w = paramFloat3;
    this.I = paramFloat4;
  }
  
  public float t(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = b ^ l;
    float f = this.S * b9.c.method_22683().method_4486();
    d[] arrayOfD = ke.s();
    try {
      if (arrayOfD == null)
        try {
          if (this.S > 0.5F) {
          
          } else {
            return f;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public float x(Object[] paramArrayOfObject) {
    return this.m * b9.c.method_22683().method_4502();
  }
  
  public void X(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    l = b ^ l;
    int i = b9.c.method_22683().method_4486();
    d[] arrayOfD = ke.s();
    try {
      this.S = f / i;
      if (arrayOfD == null)
        try {
          if (this.S > 0.5F)
            this.S += this.w / i; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    p(new Object[0]);
  }
  
  public void q(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.m = f / b9.c.method_22683().method_4502();
    p(new Object[0]);
  }
  
  public float g(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  public float n(Object[] paramArrayOfObject) {
    return this.m;
  }
  
  public void E(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.S = f;
    p(new Object[0]);
  }
  
  public void W(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.m = f;
    p(new Object[0]);
  }
  
  public float D(Object[] paramArrayOfObject) {
    return this.B;
  }
  
  public void U(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.B = f;
  }
  
  public float K(Object[] paramArrayOfObject) {
    return this.w;
  }
  
  public void r(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.w = f;
  }
  
  public float p(Object[] paramArrayOfObject) {
    return this.I;
  }
  
  public void k(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.I = f;
  }
  
  public void Y(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    this.w = f2;
    this.I = f1;
  }
  
  public boolean A(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  public void H(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.V = bool;
  }
  
  public float z(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = b ^ l;
    d[] arrayOfD = ke.s();
    try {
      if (arrayOfD == null) {
        try {
          if (this.M > b9.c.method_22683().method_4486() / 2.0F) {
          
          } else {
          
          } 
        } catch (x5 x5) {
          throw a(null);
        } 
        return this.M;
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void u(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.M = f;
  }
  
  public float h(Object[] paramArrayOfObject) {
    return this.P;
  }
  
  public void e(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.P = f;
  }
  
  private void p(Object[] paramArrayOfObject) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    hashMap.put("x", Float.valueOf(this.S));
    hashMap.put("y", Float.valueOf(this.m));
    V(new Object[] { hashMap });
  }
  
  static {
    long l = b ^ 0x6A3D5D9388ECL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static String b(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < i; b2++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */