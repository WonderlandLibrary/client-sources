package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class pn extends u_<j4> {
  private final ke R;
  
  private final gm<lb> U;
  
  private static final long a = on.a(4742488868058594314L, 2677961246675751846L, MethodHandles.lookup().lookupClass()).a(134572393314924L);
  
  private static final String b;
  
  public pn(long paramLong, j4 paramj4) {
    super(paramj4);
    this.R = new ke(l, b, this, false);
    this.U = this::lambda$new$0;
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return km.NO_GROUND;
  }
  
  private void lambda$new$0(lb paramlb) {
    long l = a ^ 0x5834767CFB57L;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    boolean bool = pw.y();
    try {
      if (!bool)
        try {
          if (class_2596 instanceof class_2828) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2828 class_2828 = (class_2828)class_2596;
    PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2828;
    try {
      if (!bool)
        try {
          playerMoveC2SPacketAccessor.setOnGround(false);
          if (this.R.z().booleanValue()) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    playerMoveC2SPacketAccessor.setY(class_2828.method_12268(b9.c.field_1724.method_23318()) + 1.0E-7D);
  }
  
  static {
    long l = a ^ 0x734097B8E611L;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */