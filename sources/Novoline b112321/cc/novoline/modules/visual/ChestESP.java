package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import net.Ls;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public final class ChestESP extends AbstractModule {
   @Property("color")
   private final ColorProperty color = PropertyFactory.createColor(Integer.valueOf(-65339));
   @Property("mode")
   private final StringProperty mode = PropertyFactory.createString("Outline").acceptableValues(new String[]{"Outline", "Filled", "Chams", "Box"});
   @Property("color-visible")
   private final ColorProperty visible = PropertyFactory.createColor(Integer.valueOf((new Color(255, 101, 101)).getRGB()));
   @Property("color-hidden")
   private final ColorProperty hidden = PropertyFactory.createColor(Integer.valueOf((new Color(165, 163, 165)).getRGB()));
   @Property("box")
   private final BooleanProperty A = PropertyFactory.createBoolean(Boolean.valueOf(true));
   private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
   private final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);

   public StringProperty getMode() {
      return this.mode;
   }

   public ChestESP(ModuleManager var1) {
      super(var1, "ChestESP", "Chest ESP", 0, EnumModuleType.VISUALS);
      Manager.put(new Setting("CHEST_ESP_MODE", "Mode", SettingType.COMBOBOX, this, this.mode));
      Manager.put(new Setting("CHESTESP_COLOR", "Color", SettingType.COLOR_PICKER, this, this.color, (EnumSet)null));
      Manager.put(new Setting("CHESTESP_COLOR", "Chams Visible", SettingType.COLOR_PICKER, this, this.visible, (EnumSet)null, this::lambda$new$0));
      Manager.put(new Setting("CHESTESP_COLOR", "Chams Hidden", SettingType.COLOR_PICKER, this, this.hidden, (EnumSet)null, this::lambda$new$1));
      Manager.put(new Setting("CHEST_ESP_BOX", "Box", SettingType.CHECKBOX, this, this.A, this::lambda$new$2));
   }

   private void renderOutline(TileEntity var1) {
      HUD.e();
      double var3 = (double)var1.getPos().getX();
      double var5 = (double)var1.getPos().getY();
      double var7 = (double)var1.getPos().getZ();
      AxisAlignedBB var9 = null;
      Block var10 = this.mc.world.getBlockState(new BlockPos(var3, var5, var7)).getBlock();
      Block var11 = this.mc.world.getBlockState(new BlockPos(var3 + 1.0D, var5, var7)).getBlock();
      Block var12 = this.mc.world.getBlockState(new BlockPos(var3 - 1.0D, var5, var7)).getBlock();
      Block var13 = this.mc.world.getBlockState(new BlockPos(var3, var5, var7 + 1.0D)).getBlock();
      Block var14 = this.mc.world.getBlockState(new BlockPos(var3, var5, var7 - 1.0D)).getBlock();
      if(var11 == var10) {
         var9 = this.renderOutlineZero(var3, var5, var7);
      } else if(var14 == var10) {
         var9 = this.renderOutlineFirst(var3, var5, var7);
      } else if(var12 != var10 && var13 != var10) {
         var9 = this.renderOutlineSecond(var3, var5, var7);
      }

      GlStateManager.disableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.disableTexture2D();
      GlStateManager.disableDepth();
      GL11.glEnable(2848);
      float[] var15 = this.getColorForTileEntity();
      RenderHelper.drawCompleteBoxFilled(var9, 1.0F, this.toRGBAHex(var15[0] / 255.0F, var15[1] / 255.0F, var15[2] / 255.0F, 0.2F));
      GL11.glDisable(2848);
      GlStateManager.enableDepth();
      GlStateManager.enableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
   }

   private AxisAlignedBB renderOutlineSecond(double var1, double var3, double var5) {
      RenderManager var7 = this.mc.getRenderManager();
      return new AxisAlignedBB(var1 + 0.05000000074505806D - var7.renderPosX, var3 - var7.renderPosY, var5 + 0.05000000074505806D - var7.renderPosZ, var1 + 0.949999988079071D - var7.renderPosX, var3 + 0.8999999761581421D - var7.renderPosY, var5 + 0.949999988079071D - var7.renderPosZ);
   }

   private AxisAlignedBB renderOutlineFirst(double var1, double var3, double var5) {
      RenderManager var7 = this.mc.getRenderManager();
      return new AxisAlignedBB(var1 + 0.05000000074505806D - var7.renderPosX, var3 - var7.renderPosY, var5 + 0.05000000074505806D - var7.renderPosZ - 1.0D, var1 + 0.949999988079071D - var7.renderPosX, var3 + 0.8999999761581421D - var7.renderPosY, var5 + 0.949999988079071D - var7.renderPosZ);
   }

   private AxisAlignedBB renderOutlineZero(double var1, double var3, double var5) {
      RenderManager var7 = this.mc.getRenderManager();
      return new AxisAlignedBB(var1 + 0.05000000074505806D - var7.renderPosX, var3 - var7.renderPosY, var5 + 0.05000000074505806D - var7.renderPosZ, var1 + 1.9500000476837158D - var7.renderPosX, var3 + 0.8999999761581421D - var7.renderPosY, var5 + 0.949999988079071D - var7.renderPosZ);
   }

   public float[] getColorForTileEntity() {
      Color var1 = this.color.getAwtColor();
      return new float[]{(float)var1.getRed(), (float)var1.getGreen(), (float)var1.getBlue(), 200.0F};
   }

   public int toRGBAHex(float var1, float var2, float var3, float var4) {
      return ((int)(var4 * 255.0F) & 255) << 24 | ((int)(var1 * 255.0F) & 255) << 16 | ((int)(var2 * 255.0F) & 255) << 8 | (int)(var3 * 255.0F) & 255;
   }

   @EventTarget
   public void onRender3D(Render3DEvent var1) {
      int var2 = HUD.e();
      if(this.mode.equals("Filled")) {
         Iterator var3 = this.mc.world.getLoadedTileEntityList().iterator();
         if(var3.hasNext()) {
            TileEntity var4 = (TileEntity)var3.next();
            if((var4 instanceof TileEntityChest || var4 instanceof TileEntityEnderChest) && !var4.isInvalid() && this.mc.world.getBlockState(var4.getPos()) != null) {
               this.renderOutline(var4);
            }
         }
      }

   }

   @EventTarget
   public void on2D(Render2DEvent var1) {
      int var2 = HUD.h();
      if(this.mode.equals("Box") || ((Boolean)this.A.get()).booleanValue()) {
         Iterator var3 = ((List)this.mc.world.getLoadedTileEntityList().stream().filter(ChestESP::lambda$on2D$3).collect(Collectors.toList())).iterator();
         if(var3.hasNext()) {
            TileEntity var4 = (TileEntity)var3.next();
            BlockPos var5 = var4.getPos();
            AxisAlignedBB var6 = new AxisAlignedBB((double)var5.getX(), (double)var5.getY(), (double)var5.getZ(), (double)(var5.getX() + 1), (double)(var5.getY() + 1), (double)(var5.getZ() + 1));
            List var7 = Arrays.asList(new Vector3d[]{new Vector3d(var6.minX, var6.minY, var6.minZ), new Vector3d(var6.minX, var6.maxY, var6.minZ), new Vector3d(var6.maxX, var6.minY, var6.minZ), new Vector3d(var6.maxX, var6.maxY, var6.minZ), new Vector3d(var6.minX, var6.minY, var6.maxZ), new Vector3d(var6.minX, var6.maxY, var6.maxZ), new Vector3d(var6.maxX, var6.minY, var6.maxZ), new Vector3d(var6.maxX, var6.maxY, var6.maxZ)});
            this.mc.entityRenderer.setupCameraTransform(var1.getPartialTicks(), 0);
            Vector4d var8 = null;
            Iterator var9 = var7.iterator();
            if(var9.hasNext()) {
               Vector3d var10 = (Vector3d)var9.next();
               var10 = this.project2D(var1.getResolution(), var10.x - this.mc.getRenderManager().viewerPosX, var10.y - this.mc.getRenderManager().viewerPosY, var10.z - this.mc.getRenderManager().viewerPosZ);
               if(var10 != null && var10.z >= 0.0D && var10.z < 1.0D) {
                  if(var8 == null) {
                     var8 = new Vector4d(var10.x, var10.y, var10.z, 0.0D);
                  }

                  var8.x = Math.min(var10.x, var8.x);
                  var8.y = Math.min(var10.y, var8.y);
                  var8.z = Math.max(var10.x, var8.z);
                  var8.w = Math.max(var10.y, var8.w);
               }
            }

            this.mc.entityRenderer.setupOverlayRendering();
            if(var8 != null) {
               double var17 = var8.x;
               double var11 = var8.y;
               double var13 = var8.z;
               double var15 = var8.w;
               RenderUtils.drawCornerBox(var17, var11, var13, var15, 3.0D, Color.BLACK);
               RenderUtils.drawCornerBox(var17, var11, var13, var15, 1.0D, this.color.getAwtColor());
            }
         }
      }

   }

   private Vector3d project2D(ScaledResolution var1, double var2, double var4, double var6) {
      GL11.glGetFloat(2982, this.modelView);
      GL11.glGetFloat(2983, this.projection);
      GL11.glGetInteger(2978, this.viewport);
      return Ls.a((float)var2, (float)var4, (float)var6, this.modelView, this.projection, this.viewport, this.vector)?new Vector3d((double)(this.vector.get(0) / (float)var1.getScaleFactor()), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)var1.getScaleFactor()), (double)this.vector.get(2)):null;
   }

   public void pre3D() {
      this.checkSetupFBO();
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(3.0F);
      GL11.glEnable(2848);
      GL11.glEnable(2960);
      GL11.glClear(1024);
      GL11.glClearStencil(15);
      GL11.glStencilFunc(512, 1, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6913);
   }

   public void checkSetupFBO() {
      HUD.h();
      Framebuffer var2 = Minecraft.getInstance().getFramebuffer();
      if(var2 != null && var2.depthBuffer > -1) {
         this.setupFBO(var2);
         var2.depthBuffer = -1;
      }

   }

   public void setupFBO(Framebuffer var1) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(var1.depthBuffer);
      int var2 = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT('赁', var2);
      EXTFramebufferObject.glRenderbufferStorageEXT('赁', '蓹', Minecraft.getInstance().displayWidth, Minecraft.getInstance().displayHeight);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('赀', '贠', '赁', var2);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('赀', '贀', '赁', var2);
   }

   public void setupStencil() {
      GL11.glStencilFunc(512, 0, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6914);
   }

   public void setupStencil2() {
      GL11.glStencilFunc(514, 1, 15);
      GL11.glStencilOp(7680, 7680, 7680);
      GL11.glPolygonMode(1032, 6913);
   }

   public void setupStencilFirst() {
      GL11.glStencilFunc(512, 0, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6914);
   }

   public void setupStencilSecond() {
      GL11.glStencilFunc(514, 1, 15);
      GL11.glStencilOp(7680, 7680, 7680);
      GL11.glPolygonMode(1032, 6913);
   }

   public void renderOutline(int var1) {
      this.setColor(var1);
      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(10754);
      GL11.glPolygonOffset(1.0F, -2000000.0F);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
   }

   public void setColor(int var1) {
      HUD.e();
      float var3 = (float)(var1 >> 24 & 255) / 255.0F;
      float var4 = (float)(var1 >> 16 & 255) / 255.0F;
      float var5 = (float)(var1 >> 8 & 255) / 255.0F;
      float var6 = (float)(var1 & 255) / 255.0F;
      if(var3 == 0.0F) {
         var3 = 1.0F;
      }

      GL11.glColor4f(var4, var5, var6, var3);
   }

   public void post3D() {
      GL11.glPolygonOffset(1.0F, 2000000.0F);
      GL11.glDisable(10754);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(2960);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glEnable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      GL11.glPopAttrib();
   }

   public ColorProperty getHidden() {
      return this.hidden;
   }

   public ColorProperty getVisible() {
      return this.visible;
   }

   private static boolean lambda$on2D$3(TileEntity var0) {
      return var0 instanceof TileEntityChest;
   }

   private Boolean lambda$new$2() {
      int var1 = HUD.h();
      return Boolean.valueOf(!this.mode.equals("Box"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.mode.equals("Chams"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.mode.equals("Chams"));
   }
}
