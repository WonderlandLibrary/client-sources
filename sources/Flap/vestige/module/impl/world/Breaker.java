package vestige.module.impl.world;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.JumpEvent;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.Render2DEvent;
import vestige.event.impl.Render3DEvent;
import vestige.event.impl.StrafeEvent;
import vestige.event.impl.TickEvent;
import vestige.font.VestigeFontRenderer;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Killaura;
import vestige.module.impl.visual.ClientTheme;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.shaders.ShaderUtil;
import vestige.util.player.FixedRotations;
import vestige.util.player.RotationsUtil;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtil;
import vestige.util.render.RenderUtils2;

public class Breaker extends Module {
   private BlockPos bedPos;
   private FixedRotations rotations;
   private final IntegerSetting range = new IntegerSetting("Range", 4, 1, 5, 1);
   private final BooleanSetting rotate = new BooleanSetting("Rotate", true);
   private final BooleanSetting hypixel = new BooleanSetting("Legit Mode", false);
   private ClientTheme theme;
   private VestigeFontRenderer productSans;

   public Breaker() {
      super("Breaker", Category.WORLD);
      this.addSettings(new AbstractSetting[]{this.range, this.rotate, this.hypixel});
   }

   public void onEnable() {
      this.bedPos = null;
      this.rotations = new FixedRotations(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
   }

   public boolean onDisable() {
      mc.gameSettings.keyBindAttack.pressed = Mouse.isButtonDown(0);
      return false;
   }

   @Listener
   public void onTick(TickEvent event) {
      this.bedPos = null;
      boolean found = false;
      float yaw = this.rotations.getYaw();
      float pitch = this.rotations.getPitch();

      for(double x = mc.thePlayer.posX - (double)this.range.getValue(); x <= mc.thePlayer.posX + (double)this.range.getValue(); ++x) {
         for(double y = mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - (double)this.range.getValue(); y <= mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() + (double)this.range.getValue(); ++y) {
            for(double z = mc.thePlayer.posZ - (double)this.range.getValue(); z <= mc.thePlayer.posZ + (double)this.range.getValue(); ++z) {
               BlockPos pos = new BlockPos(x, y, z);
               if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockBed && !found) {
                  this.bedPos = pos;
                  if (this.hypixel.isEnabled() && this.isBlockOver(this.bedPos)) {
                     BlockPos posOver = pos.add(0, 1, 0);
                     mc.objectMouseOver = new MovingObjectPosition(new Vec3((double)posOver.getX() + 0.5D, (double)(posOver.getY() + 1), (double)posOver.getZ() + 0.5D), EnumFacing.UP, posOver);
                     mc.gameSettings.keyBindAttack.pressed = true;
                     float[] rots = RotationsUtil.getRotationsToPosition((double)posOver.getX() + 0.5D, (double)(posOver.getY() + 1), (double)posOver.getZ() + 0.5D);
                     yaw = rots[0];
                     pitch = rots[1];
                  } else {
                     mc.objectMouseOver = new MovingObjectPosition(new Vec3((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D), EnumFacing.UP, this.bedPos);
                     mc.gameSettings.keyBindAttack.pressed = true;
                     float[] rots = RotationsUtil.getRotationsToPosition((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
                     yaw = rots[0];
                     pitch = rots[1];
                  }

                  found = true;
               }
            }
         }
      }

      if (!found) {
         mc.gameSettings.keyBindAttack.pressed = Mouse.isButtonDown(0);
      }

      this.rotations.updateRotations(yaw, pitch);
   }

   public boolean isBlockOver(BlockPos pos) {
      BlockPos posOver = pos.add(0, 1, 0);
      Block block = mc.theWorld.getBlockState(posOver).getBlock();
      return !(block instanceof BlockAir) && !(block instanceof BlockLiquid);
   }

   public boolean isBreakingBed() {
      return this.bedPos != null;
   }

   @Listener
   public void onStrafe(StrafeEvent event) {
      if (this.bedPos != null && this.rotate.isEnabled()) {
      }

   }

   @Listener
   public void onJump(JumpEvent event) {
      if (this.bedPos != null && this.rotate.isEnabled()) {
      }

   }

   @Listener
   public void onRender3d(Render3DEvent event) {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      this.productSans = Flap.instance.getFontManager().getProductSans();
      if (this.bedPos != null) {
         float rotateX = mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F;
         GL11.glPushMatrix();
         GL11.glDisable(2929);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GL11.glTranslatef((float)((double)this.bedPos.getX() - mc.getRenderManager().viewerPosX + 0.5D), (float)((double)this.bedPos.getY() - mc.getRenderManager().viewerPosY + 1.0D), (float)((double)this.bedPos.getZ() - mc.getRenderManager().viewerPosZ + 0.5D));
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(mc.getRenderManager().playerViewX, rotateX, 0.0F, 0.0F);
         float var10000 = 0.5F;
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         GL11.glScaled(-0.01666666753590107D * Math.sqrt(mc.thePlayer.getDistance((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ())), -0.01666666753590107D * Math.sqrt(mc.thePlayer.getDistance((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ())), 0.01666666753590107D * Math.sqrt(mc.thePlayer.getDistance((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ())));
         drawRound(Math.max(17.5D, 70.0D) / -2.0D, -40.0D, Math.max(17.5D, 70.0D) - 2.5D, 26.5D, 10.0D, this.theme.getColortocolor(1));
         this.productSans.drawString("Nuking", (float)(Math.max(14.5D, 14.5D) / -2.0D), -31.0F, -1);
         mc.thePlayer.getHeldItem();
         DrawUtil.drawImage(new ResourceLocation("flap/imagens/pickaxe.png"), (int)(Math.max(17.5D, 54.25D) / -2.0D), -34, 15, 15);
         GlStateManager.disableBlend();
         GL11.glEnable(2929);
         GL11.glPopMatrix();
      }

      if (this.bedPos != null && this.theme != null) {
         mc.objectMouseOver = new MovingObjectPosition(new Vec3((double)(this.bedPos.getX() + 1), (double)this.bedPos.getY() + 0.5D, (double)this.bedPos.getZ() + 0.5D), EnumFacing.UP, this.bedPos);
         RenderUtil.renderBlock(mc.objectMouseOver.getBlockPos(), this.theme.getColor(1), true, true);
      }

   }

   @Listener
   public void on2drendr(Render2DEvent event) {
      if (this.bedPos != null) {
         float rotateX = mc.gameSettings.thirdPersonView == 2 ? -1.0F : 1.0F;
         GL11.glPushMatrix();
         GL11.glDisable(2929);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GL11.glTranslatef((float)((double)this.bedPos.getX() - mc.getRenderManager().viewerPosX + 0.5D), (float)((double)this.bedPos.getY() - mc.getRenderManager().viewerPosY + 1.0D), (float)((double)this.bedPos.getZ() - mc.getRenderManager().viewerPosZ + 0.5D));
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(mc.getRenderManager().playerViewX, rotateX, 0.0F, 0.0F);
         GL11.glScaled(-0.01666666753590107D * Math.sqrt(mc.thePlayer.getDistance((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ())), -0.01666666753590107D * Math.sqrt(mc.thePlayer.getDistance((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ())), 0.01666666753590107D * Math.sqrt(mc.thePlayer.getDistance((double)this.bedPos.getX(), (double)this.bedPos.getY(), (double)this.bedPos.getZ())));
         drawRound(Math.max(17.5D, 17.5D) / -2.0D, -0.5D, Math.max(17.5D, 17.5D) - 2.5D, 26.5D, 3.0D, new Color(0, 0, 0, 90));
         GlStateManager.disableBlend();
         GL11.glEnable(2929);
         GL11.glPopMatrix();
      }

   }

   public static void drawRound(double x, double y, double width, double height, double radius, Color color) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderUtils2.roundedShader.init();
      setupRoundedRectUniforms(x, y, width, height, radius);
      RenderUtils2.roundedShader.setUniformf("color", (float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      ShaderUtil.drawQuads((float)(x - 1.0D), (float)(y - 1.0D), (float)(width + 2.0D), (float)(height + 2.0D));
      RenderUtils2.roundedShader.unload();
   }

   private static void setupRoundedRectUniforms(double x, double y, double width, double height, double radius) {
      ScaledResolution sr = new ScaledResolution(mc);
      RenderUtils2.roundedShader.setUniformf("location", (float)(x * (double)sr.getScaleFactor()), (float)((double)mc.displayHeight - height * (double)sr.getScaleFactor() - y * (double)sr.getScaleFactor()));
      RenderUtils2.roundedShader.setUniformf("rectSize", (float)(width * (double)sr.getScaleFactor()), (float)(height * (double)sr.getScaleFactor()));
      RenderUtils2.roundedShader.setUniformf("radius", (float)(radius * (double)sr.getScaleFactor()));
   }

   @Listener
   public void onMotion(MotionEvent event) {
      if (this.bedPos != null && this.rotate.isEnabled() && (!((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).isEnabled() || ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).target == null || ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).target.hurtTime != 0)) {
         event.setYaw(this.rotations.getYaw());
         event.setPitch(this.rotations.getPitch());
      }

   }
}
