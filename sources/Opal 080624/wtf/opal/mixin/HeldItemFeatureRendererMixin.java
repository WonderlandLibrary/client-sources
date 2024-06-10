package wtf.opal.mixin;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_7833;
import net.minecraft.class_811;
import net.minecraft.class_989;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.opal.b9;
import wtf.opal.d1;
import wtf.opal.je;
import wtf.opal.k0;
import wtf.opal.on;
import wtf.opal.x5;

@Mixin({class_989.class})
public class HeldItemFeatureRendererMixin {
  private static final long a = on.a(-6538201894233250403L, 6195615217620196423L, MethodHandles.lookup().lookupClass()).a(74385402065842L);
  
  @Inject(method = {"renderItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V")})
  private void hookRenderItem(class_1309 paramclass_1309, class_1799 paramclass_1799, class_811 paramclass_811, class_1306 paramclass_1306, class_4587 paramclass_4587, class_4597 paramclass_4597, int paramInt, CallbackInfo paramCallbackInfo) {
    long l = a ^ 0x36F32039881DL;
    int i = (int)((l ^ 0x6648FFB40D9CL) >>> 48L);
    int j = (int)((l ^ 0x6648FFB40D9CL) << 16L >>> 32L);
    int k = (int)((l ^ 0x6648FFB40D9CL) << 48L >>> 48L);
    l ^ 0x6648FFB40D9CL;
    je je = (je)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { je.class });
    try {
      if (paramclass_1306 == class_1306.field_6183)
        try {
          if (je.D(new Object[0]))
            try {
              if (((Boolean)je.w(new Object[0]).z()).booleanValue())
                try {
                  if (k0.J(new Object[] { null, null, null, Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf((short)i), paramclass_1309 }))
                    try {
                      paramclass_4587.method_22907(class_7833.field_40716.rotationDegrees(-70.0F));
                      if (paramclass_1309 == b9.c.field_1724) {
                        try {
                          paramclass_4587.method_46416(-0.15F, -0.1F, 0.3F);
                          if (paramclass_1309.method_18276())
                            paramclass_4587.method_46416(0.15F, 0.05F, -0.05F); 
                        } catch (x5 x5) {
                          throw a(null);
                        } 
                      } else {
                        try {
                          paramclass_4587.method_22907(class_7833.field_40714.rotationDegrees(5.0F));
                          paramclass_4587.method_46416(-0.3F, -0.05F, 0.3F);
                          if (paramclass_1309.method_18276())
                            paramclass_4587.method_46416(0.2F, 0.0F, -0.1F); 
                        } catch (x5 x5) {
                          throw a(null);
                        } 
                      } 
                    } catch (x5 x5) {
                      throw a(null);
                    }  
                } catch (x5 x5) {
                  throw a(null);
                }  
            } catch (x5 x5) {
              throw a(null);
            }  
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\mixin\HeldItemFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */