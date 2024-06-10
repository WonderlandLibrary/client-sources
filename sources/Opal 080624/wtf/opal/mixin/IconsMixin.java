package wtf.opal.mixin;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.fabricmc.loader.impl.launch.knot.Knot;
import net.minecraft.class_3262;
import net.minecraft.class_7367;
import net.minecraft.class_8518;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wtf.opal.on;

@Mixin({class_8518.class})
public final class IconsMixin {
  private static final long a = on.a(-8713359928692829975L, -5269471358267164150L, MethodHandles.lookup().lookupClass()).a(97159178590672L);
  
  private static final String b;
  
  @Inject(at = {@At("HEAD")}, method = {"getIcon"}, cancellable = true)
  private void getIcon(class_3262 paramclass_3262, String paramString, CallbackInfoReturnable<class_7367<InputStream>> paramCallbackInfoReturnable) throws URISyntaxException {
    paramCallbackInfoReturnable.setReturnValue(paramString::lambda$getIcon$0);
  }
  
  private static InputStream lambda$getIcon$0(String paramString) throws IOException {
    long l = a ^ 0x1E39A036A8AEL;
    return Knot.getLauncher().getTargetClassLoader().getResourceAsStream(b + b);
  }
  
  static {
    long l = a ^ 0x6CE962BC87L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\IconsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */