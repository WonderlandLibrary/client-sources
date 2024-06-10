package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2350;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2743;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class ul extends u_<h> {
  private int y;
  
  private int V;
  
  private boolean p;
  
  private boolean W;
  
  private final List<class_2596<?>> P = new ArrayList<>();
  
  private class_243 j = new class_243(0.0D, 0.0D, 0.0D);
  
  private final gm<b6> c = this::lambda$new$0;
  
  private final gm<p> a = this::lambda$new$1;
  
  private final gm<lb> s = this::lambda$new$2;
  
  private final gm<lu> L = this::lambda$new$3;
  
  private static final long b = on.a(-1860003048236275280L, -3090562744748075614L, MethodHandles.lookup().lookupClass()).a(84177610991875L);
  
  private static final long d;
  
  public ul(short paramShort1, h paramh, short paramShort2, int paramInt) {
    super(paramh);
    int i = uz.r();
    try {
      if (d.D() != null)
        uz.g(++i); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.y = 0;
    this.V = (int)d;
    this.p = false;
    this.W = false;
    this.j = new class_243(0.0D, 0.0D, 0.0D);
    this.P.clear();
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  public void X() {
    Objects.requireNonNull(b9.c.method_1562().method_48296());
    this.P.forEach(b9.c.method_1562().method_48296()::method_10743);
    this.P.clear();
    super.X();
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l0.EXPERIMENTAL;
  }
  
  private void lambda$new$3(lu paramlu) {
    long l = b ^ 0x613C1EC581F8L;
    class_2596 class_2596 = paramlu.g(new Object[0]);
    int i = uz.r();
    try {
      if (i == 0)
        try {
          if (class_2596 instanceof class_2743) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2743 class_2743 = (class_2743)class_2596;
    try {
      if (i == 0)
        try {
          if (class_2743.method_11818() == b9.c.field_1724.method_5628()) {
            this.W = true;
            this.V = 0;
            paramlu.Z(new Object[0]);
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    this.j = new class_243(class_2743.method_11815() / 8000.0D, class_2743.method_11816() / 8000.0D, class_2743.method_11819() / 8000.0D);
  }
  
  private void lambda$new$2(lb paramlb) {
    long l = b ^ 0x61434B341144L;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    int i = uz.r();
    try {
      if (i == 0)
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
      if (i == 0)
        try {
          if (this.y < 4) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    if (this.y != 0);
  }
  
  private void lambda$new$1(p paramp) {
    long l = b ^ 0x4C8D5085FFCBL;
    if (this.y < 4);
  }
  
  private void lambda$new$0(b6 paramb6) {
    long l = b ^ 0x369041C79FB3L;
    int i = uz.M();
    try {
      if (i != 0)
        if (!paramb6.W(new Object[0]))
          return;  
    } catch (x5 x5) {
      throw b(null);
    } 
    try {
      if (this.W)
        b9.c.field_1724.method_18799(b9.c.field_1724.method_18798().method_38499(class_2350.class_2351.field_11052, 0.005D)); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  static {
    long l = b ^ 0x2572692E8654L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ul.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */