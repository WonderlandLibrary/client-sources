package exhibition.util.render.para;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class Render {
   private static final List STATES = Lists.newArrayList();
   protected static final List GL_STATES = Lists.newArrayList();
   protected static final List BLEND_FUNCS = Lists.newArrayList();
   protected static final List ALPHAS = Lists.newArrayList();
   protected static double alphaMult = 1.0D;

   public static void pushAlpha(double alpha) {
      ALPHAS.add(0, alpha);
      alphaMult = alpha;
   }

   public static void popAlpha() {
      ALPHAS.remove(0);
      alphaMult = ALPHAS.isEmpty() ? 1.0D : ((Double)ALPHAS.get(0)).doubleValue();
   }

   public static void pre() {
      Map glStateList = Maps.newHashMap();
      Iterator var1 = STATES.iterator();

      while(var1.hasNext()) {
         int i = ((Integer)var1.next()).intValue();
         glStateList.put(i, GL11.glGetBoolean(i));
      }

      GL_STATES.add(0, glStateList);
      GL11.glEnable(2848);
      GL11.glEnable(2832);
      GlStateManager.alphaFunc(516, 0.001F);
   }

   public static void post() {
      GlStateManager.alphaFunc(516, 0.1F);
      Map stateList = (Map)GL_STATES.get(0);
      GL_STATES.remove(0);
      if (((Boolean)stateList.get(Integer.valueOf(3008))).booleanValue()) {
         GlStateManager.enableAlpha();
      } else {
         GlStateManager.disableAlpha();
      }

      if (((Boolean)stateList.get(Integer.valueOf(3042))).booleanValue()) {
         GlStateManager.enableBlend();
      } else {
         GlStateManager.disableBlend();
      }

      if (((Boolean)stateList.get(Integer.valueOf(3058))).booleanValue()) {
         GlStateManager.enableColorLogic();
      } else {
         GlStateManager.disableColorLogic();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2903))).booleanValue()) {
         GlStateManager.enableColorMaterial();
      } else {
         GlStateManager.disableColorMaterial();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2884))).booleanValue()) {
         GlStateManager.enableCull();
      } else {
         GlStateManager.disableCull();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2929))).booleanValue()) {
         GlStateManager.enableDepth();
      } else {
         GlStateManager.disableDepth();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2912))).booleanValue()) {
         GlStateManager.enableFog();
      } else {
         GlStateManager.disableFog();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2896))).booleanValue()) {
         GlStateManager.enableLighting();
      } else {
         GlStateManager.disableLighting();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2977))).booleanValue()) {
         GlStateManager.enableNormalize();
      } else {
         GlStateManager.disableNormalize();
      }

      if (((Boolean)stateList.get(Integer.valueOf(32823))).booleanValue()) {
         GlStateManager.enablePolygonOffset();
      } else {
         GlStateManager.disablePolygonOffset();
      }

      if (((Boolean)stateList.get(Integer.valueOf(32826))).booleanValue()) {
         GlStateManager.enableRescaleNormal();
      } else {
         GlStateManager.disableRescaleNormal();
      }

      if (((Boolean)stateList.get(Integer.valueOf(3553))).booleanValue()) {
         GlStateManager.enableTextures();
      } else {
         GlStateManager.disableTextures();
      }

      if (((Boolean)stateList.get(Integer.valueOf(2848))).booleanValue()) {
         GL11.glEnable(2848);
      } else {
         GL11.glDisable(2848);
      }

      if (((Boolean)stateList.get(Integer.valueOf(2832))).booleanValue()) {
         GL11.glEnable(2832);
      } else {
         GL11.glDisable(2832);
      }

   }

   static {
      STATES.add(Integer.valueOf(3008));
      STATES.add(Integer.valueOf(3042));
      STATES.add(Integer.valueOf(3058));
      STATES.add(Integer.valueOf(2903));
      STATES.add(Integer.valueOf(2884));
      STATES.add(Integer.valueOf(2929));
      STATES.add(Integer.valueOf(2912));
      STATES.add(Integer.valueOf(2896));
      STATES.add(Integer.valueOf(2977));
      STATES.add(Integer.valueOf(32823));
      STATES.add(Integer.valueOf(32826));
      STATES.add(Integer.valueOf(3553));
      STATES.add(Integer.valueOf(2848));
      STATES.add(Integer.valueOf(2832));
   }
}
