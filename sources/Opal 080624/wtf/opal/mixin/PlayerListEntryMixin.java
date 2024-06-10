package wtf.opal.mixin;

import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2960;
import net.minecraft.class_640;
import net.minecraft.class_8685;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.jg;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_640.class})
public class PlayerListEntryMixin {
  @Shadow
  @Final
  private GameProfile field_3741;
  
  private static final long a = on.a(4712613778530846350L, -9103071308645411431L, MethodHandles.lookup().lookupClass()).a(141469991450620L);
  
  private static final String b;
  
  @Inject(method = {"getSkinTextures"}, at = {@At("TAIL")}, cancellable = true)
  private void hookGetSkinTextures(CallbackInfoReturnable<class_8685> paramCallbackInfoReturnable) {
    long l1 = a ^ 0x1F18DB3C4D43L;
    long l2 = l1 ^ 0x5C6015B6A6B0L;
    try {
      if (!this.field_3741.getId().equals(b9.c.method_1548().method_44717()))
        return; 
    } catch (x5 x5) {
      throw a(null);
    } 
    jg jg = (jg)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { jg.class });
    try {
      if (!jg.D(new Object[0]))
        return; 
    } catch (x5 x5) {
      throw a(null);
    } 
    class_8685 class_8685 = (class_8685)paramCallbackInfoReturnable.getReturnValue();
    class_2960 class_2960 = new class_2960(b);
    new Object[1];
    paramCallbackInfoReturnable.setReturnValue(new class_8685(class_8685.comp_1626(), class_8685.comp_1911(), jg.L(new Object[] { Long.valueOf(l2) }, ), class_2960, class_8685.comp_1629(), class_8685.comp_1630()));
  }
  
  static {
    long l = a ^ 0x23C6F7E111FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 a(x5 paramx5) {
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\PlayerListEntryMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */