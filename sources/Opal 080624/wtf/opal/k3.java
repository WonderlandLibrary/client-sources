package wtf.opal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class k3<T> {
  @Expose
  @SerializedName("name")
  private final String J;
  
  private final List<dy<? extends k3<?>>> f = new ArrayList<>();
  
  @Expose
  @SerializedName("value")
  private T k;
  
  private static int[] t;
  
  private static final long a = on.a(-3258799375582533964L, -8953725643814256653L, MethodHandles.lookup().lookupClass()).a(224924112777740L);
  
  private static final String d;
  
  protected k3(String paramString) {
    this.J = paramString;
  }
  
  protected k3(long paramLong, String paramString, u_ paramu_) {
    int[] arrayOfInt = i();
    this.J = paramString;
    d d = paramu_.y(new Object[0]);
    ky ky = d.V(new Object[0]);
    try {
      if (arrayOfInt == null)
        try {
          if (ky == null) {
            LogUtils.getLogger().error(d.m(new Object[0]) + d.m(new Object[0]));
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    Enum[] arrayOfEnum = ky.A(new Object[0]);
    int i = arrayOfEnum.length;
    byte b = 0;
    while (b < i) {
      Enum enum_ = arrayOfEnum[b];
      try {
        if (paramLong >= 0L)
          if (arrayOfInt == null) {
            try {
              if (enum_.equals(paramu_.V(new Object[0])))
                try {
                  (new Object[3])[2] = paramu_::lambda$new$0;
                  new Object[3];
                  C(new Object[] { null, Long.valueOf(l1), ky });
                  (new k3[1])[0] = this;
                  (new Object[2])[1] = new k3[1];
                  new Object[2];
                  d.o(new Object[] { Long.valueOf(l2) });
                  if (arrayOfInt != null) {
                    d.p(new d[3]);
                  } else {
                    break;
                  } 
                } catch (x5 x5) {
                  throw b(null);
                }  
            } catch (x5 x5) {
              throw b(null);
            } 
            b++;
          }  
      } catch (x5 x5) {
        throw b(null);
      } 
      if (arrayOfInt != null)
        break; 
    } 
  }
  
  public final String r(Object[] paramArrayOfObject) {
    return this.J;
  }
  
  public final boolean G(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    int[] arrayOfInt = i();
    try {
      if (arrayOfInt == null)
        if (!this.f.isEmpty()) {
        
        } else {
          return false;
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public final List Q(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public final void c(Object[] paramArrayOfObject) {
    dy<? extends k3<?>> dy = (dy)paramArrayOfObject[0];
    this.f.add(dy);
  }
  
  public final void C(Object[] paramArrayOfObject) {
    k3 k31 = (k3)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    Predicate predicate = (Predicate)paramArrayOfObject[2];
    l1 = a ^ l1;
    long l2 = l1 ^ 0x13677953EC61L;
    c(new Object[] { new dy<>(l2, k31, predicate) });
  }
  
  public final void A(Object... paramVarArgs) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/k3
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/util/function/Predicate
    //   14: astore #4
    //   16: dup
    //   17: iconst_2
    //   18: aaload
    //   19: checkcast [Lwtf/opal/k3;
    //   22: astore_3
    //   23: pop
    //   24: aload_3
    //   25: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
    //   28: aload_2
    //   29: aload #4
    //   31: <illegal opcode> accept : (Lwtf/opal/k3;Ljava/util/function/Predicate;)Ljava/util/function/Consumer;
    //   36: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   41: return
  }
  
  public boolean v(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x4CADBDA6F083L;
    int[] arrayOfInt = i();
    try {
      new Object[1];
      if (arrayOfInt == null)
        try {
          if (!G(new Object[] { Long.valueOf(l2) }))
            return false; 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    return this.f.stream().noneMatch(dy::X);
  }
  
  public final T z() {
    return this.k;
  }
  
  public final void V(Object[] paramArrayOfObject) {
    Object object = paramArrayOfObject[0];
    this.k = (T)object;
  }
  
  private static void lambda$addParent$1(k3 paramk31, Predicate paramPredicate, k3 paramk32) {
    long l1 = a ^ 0x5CD2CE4541FL;
    long l2 = l1 ^ 0xAF6CB68883EL;
    paramk32.c(new Object[] { new dy<>(l2, paramk31, paramPredicate) });
  }
  
  private static boolean lambda$new$0(u_ paramu_, ky<Enum> paramky) {
    return ((Enum)paramky.z()).equals(paramu_.V(new Object[0]));
  }
  
  public static void K(int[] paramArrayOfint) {
    t = paramArrayOfint;
  }
  
  public static int[] i() {
    return t;
  }
  
  static {
    long l = a ^ 0x7B986CB084CFL;
    K(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\k3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */