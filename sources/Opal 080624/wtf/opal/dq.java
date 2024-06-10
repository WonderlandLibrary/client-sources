package wtf.opal;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;

public final class dq {
  private final pa W;
  
  private final long c;
  
  private final ByteBuffer U;
  
  private final int N;
  
  private static int C;
  
  private static final long a = on.a(-2926691578916101197L, 6056778933031062841L, MethodHandles.lookup().lookupClass()).a(93700149944808L);
  
  private static final long b;
  
  public dq(long paramLong, InputStream paramInputStream) {
    int i = k();
    try {
      this.W = d1.q(new Object[0]).z(new Object[0]);
      this.c = d1.q(new Object[0]).z(new Object[0]).y(new Object[0]);
      (new Object[3])[2] = Integer.valueOf((int)b);
      new Object[3];
      this.U = dz.v(new Object[] { null, Long.valueOf(l), paramInputStream });
      this.N = NanoVG.nvgCreateImageMem(this.c, 0, this.U);
      if (d.D() != null)
        B(++i); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void q(Object[] paramArrayOfObject) {
    float f3 = ((Float)paramArrayOfObject[0]).floatValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    float f4 = ((Float)paramArrayOfObject[2]).floatValue();
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    float f2 = ((Float)paramArrayOfObject[4]).floatValue();
    l = a ^ l;
    NVGPaint nVGPaint = this.W.N(new Object[0]);
    NanoVG.nvgImagePattern(this.c, f3, f4, f1, f2, 0.0F, this.N, 1.0F, nVGPaint);
    NanoVG.nvgBeginPath(this.c);
    NanoVG.nvgRect(this.c, f3, f4, f1, f2);
    NanoVG.nvgImagePattern(this.c, f3, f4, f1, f2, 0.0F, this.N, 1.0F, nVGPaint);
    NanoVG.nvgFillPaint(this.c, nVGPaint);
    NanoVG.nvgFill(this.c);
    int i = N();
    try {
      NanoVG.nvgClosePath(this.c);
      if (i != 0)
        d.p(new d[1]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public static void B(int paramInt) {
    C = paramInt;
  }
  
  public static int N() {
    return C;
  }
  
  public static int k() {
    int i = N();
    try {
      if (i == 0)
        return 97; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    long l = a ^ 0x6EBFA218B4DEL;
    B(0);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dq.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */