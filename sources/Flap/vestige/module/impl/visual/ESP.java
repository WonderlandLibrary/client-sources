package vestige.module.impl.visual;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.vecmath.Vector3d;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.Render2DEvent;
import vestige.event.impl.Render3DEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Antibot;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.render.RenderUtil;
import vestige.util.render.RenderUtils2;

public class ESP extends Module {
   private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
   private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
   private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
   public final BooleanSetting boxmode = new BooleanSetting("Box Full", false);
   public final BooleanSetting skeleton = new BooleanSetting("Skeleton", false);
   public final BooleanSetting outline = new BooleanSetting("Outline", false);
   public final BooleanSetting boxoutline = new BooleanSetting("Box Outline", false);
   public final BooleanSetting healthbar = new BooleanSetting("Health Bar", false);
   public final BooleanSetting filleps = new BooleanSetting("2D Model", false);
   public final IntegerSetting red = new IntegerSetting("Red", () -> {
      return this.filleps.isEnabled();
   }, 5, 0, 10, 1);
   public final IntegerSetting green = new IntegerSetting("Green", () -> {
      return this.filleps.isEnabled();
   }, 5, 0, 10, 1);
   public final IntegerSetting blue = new IntegerSetting("Blue", () -> {
      return this.filleps.isEnabled();
   }, 5, 0, 10, 1);
   public final BooleanSetting twodmode = new BooleanSetting("2D ESP", false);
   public final BooleanSetting twodmodehealth = new BooleanSetting("Health", () -> {
      return this.twodmode.isEnabled();
   }, false);
   public final BooleanSetting twodmodebox = new BooleanSetting("Box", () -> {
      return this.twodmode.isEnabled();
   }, false);
   private final DoubleSetting lineWidth = new DoubleSetting("Line width", 3.25D, 0.5D, 4.0D, 0.25D);
   private final DoubleSetting alpha = new DoubleSetting("Alpha", 0.8D, 0.2D, 1.0D, 0.05D);
   private final BooleanSetting renderInvisibles = new BooleanSetting("Render invisibles", false);
   private static Map<EntityPlayer, float[][]> entities = new HashMap();
   private ClientTheme theme;
   private Antibot antibotModule;
   public ModelBase mainModel;

   public ESP() {
      super("ESP", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.boxmode, this.boxoutline, this.healthbar, this.filleps, this.red, this.green, this.blue, this.twodmode, this.twodmodehealth, this.twodmodebox, this.lineWidth, this.alpha, this.renderInvisibles});
   }

   @Listener
   public void onRenderWorld(WorldRenderer event) {
   }

   @Listener
   public void onRender2D(Render2DEvent param1) {
      // $FF: Couldn't be decompiled
   }

   private void drawSkeleton(Render3DEvent event, EntityPlayer e) {
      if (!e.isInvisible()) {
         float[][] entPos = (float[][])entities.get(e);
         if (entPos != null && e.getEntityId() != -1488 && e.isEntityAlive() && !((Antibot)Flap.instance.getModuleManager().getModule(Antibot.class)).isBot(e) && RenderUtil.isInViewFrustrum((Entity)e) && !e.isDead && e != mc.thePlayer && !e.isPlayerSleeping() && mc.thePlayer.canEntityBeSeen(e)) {
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glLineWidth(1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Vec3 vec = this.getVec3(event, e);
            double x = vec.xCoord - mc.getRenderManager().renderPosX;
            double y = vec.yCoord - mc.getRenderManager().renderPosY;
            double z = vec.zCoord - mc.getRenderManager().renderPosZ;
            GL11.glTranslated(x, y, z);
            float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
            GL11.glRotatef(-xOff, 0.0F, 1.0F, 0.0F);
            GL11.glTranslated(0.0D, 0.0D, e.isSneaking() ? -0.235D : 0.0D);
            float yOff = e.isSneaking() ? 0.6F : 0.75F;
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(-0.125D, (double)yOff, 0.0D);
            if (entPos[3][0] != 0.0F) {
               GL11.glRotatef(entPos[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            if (entPos[3][1] != 0.0F) {
               GL11.glRotatef(entPos[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (entPos[3][2] != 0.0F) {
               GL11.glRotatef(entPos[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, (double)(-yOff), 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(0.125D, (double)yOff, 0.0D);
            if (entPos[4][0] != 0.0F) {
               GL11.glRotatef(entPos[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            if (entPos[4][1] != 0.0F) {
               GL11.glRotatef(entPos[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (entPos[4][2] != 0.0F) {
               GL11.glRotatef(entPos[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, (double)(-yOff), 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated(0.0D, 0.0D, e.isSneaking() ? 0.25D : 0.0D);
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(0.0D, e.isSneaking() ? -0.05D : 0.0D, e.isSneaking() ? -0.01725D : 0.0D);
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(-0.375D, (double)yOff + 0.55D, 0.0D);
            if (entPos[1][0] != 0.0F) {
               GL11.glRotatef(entPos[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            if (entPos[1][1] != 0.0F) {
               GL11.glRotatef(entPos[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (entPos[1][2] != 0.0F) {
               GL11.glRotatef(-entPos[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, -0.5D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.375D, (double)yOff + 0.55D, 0.0D);
            if (entPos[2][0] != 0.0F) {
               GL11.glRotatef(entPos[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            if (entPos[2][1] != 0.0F) {
               GL11.glRotatef(entPos[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if (entPos[2][2] != 0.0F) {
               GL11.glRotatef(-entPos[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, -0.5D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(xOff - e.rotationYawHead, 0.0F, 1.0F, 0.0F);
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(0.0D, (double)yOff + 0.55D, 0.0D);
            if (entPos[0][0] != 0.0F) {
               GL11.glRotatef(entPos[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
            }

            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, 0.3D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(e.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(0.0D, e.isSneaking() ? -0.16175D : 0.0D, e.isSneaking() ? -0.48025D : 0.0D);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, (double)yOff, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
            GL11.glVertex3d(0.125D, 0.0D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(0.0D, (double)yOff, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0D, 0.0D, 0.0D);
            GL11.glVertex3d(0.0D, 0.55D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0D, (double)yOff + 0.55D, 0.0D);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
            GL11.glVertex3d(0.375D, 0.0D, 0.0D);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
         }
      }

   }

   private Vec3 getVec3(Render3DEvent event, EntityPlayer var0) {
      float pt = event.getPartialTicks();
      double x = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * (double)pt;
      double y = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * (double)pt;
      double z = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * (double)pt;
      return new Vec3(x, y, z);
   }

   public static void addEntity(EntityPlayer e, ModelPlayer model) {
      entities.put(e, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
   }

   private boolean doesntContain(EntityPlayer var0) {
      return !mc.theWorld.playerEntities.contains(var0);
   }

   private void startEnd(boolean revert) {
      if (revert) {
         GlStateManager.pushMatrix();
         GlStateManager.enableBlend();
         GL11.glEnable(2848);
         GlStateManager.disableDepth();
         GlStateManager.disableTexture2D();
         GL11.glHint(3154, 4354);
      } else {
         GlStateManager.disableBlend();
         GlStateManager.enableTexture2D();
         GL11.glDisable(2848);
         GlStateManager.enableDepth();
         GlStateManager.popMatrix();
      }

      GlStateManager.depthMask(!revert);
   }

   private void draw2DBox(float width, float height, float lineWidth, float offset, Color c) {
      RenderUtils2.drawRect((double)(-width / 2.0F - offset), (double)(-offset), (double)(width / 4.0F), (double)lineWidth, c.getRGB());
      RenderUtils2.drawRect((double)(width / 2.0F - offset), (double)(-offset), (double)(-width / 4.0F), (double)lineWidth, c.getRGB());
      RenderUtils2.drawRect((double)(width / 2.0F - offset), (double)(height - offset), (double)(-width / 4.0F), (double)lineWidth, c.getRGB());
      RenderUtils2.drawRect((double)(-width / 2.0F - offset), (double)(height - offset), (double)(width / 4.0F), (double)lineWidth, c.getRGB());
      RenderUtils2.drawRect((double)(-width / 2.0F - offset), (double)(height - offset), (double)lineWidth, (double)(-height / 4.0F), c.getRGB());
      RenderUtils2.drawRect((double)(width / 2.0F - lineWidth - offset), (double)(height - offset), (double)lineWidth, (double)(-height / 4.0F), c.getRGB());
      RenderUtils2.drawRect((double)(width / 2.0F - lineWidth - offset), (double)(-offset), (double)lineWidth, (double)(height / 4.0F), c.getRGB());
      RenderUtils2.drawRect((double)(-width / 2.0F - offset), (double)(-offset), (double)lineWidth, (double)(height / 4.0F), c.getRGB());
   }

   private Vector3d project2D(int scaleFactor, double x, double y, double z) {
      GL11.glGetFloat(2982, this.modelview);
      GL11.glGetFloat(2983, this.projection);
      GL11.glGetInteger(2978, this.viewport);
      return GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector) ? new Vector3d((double)(this.vector.get(0) / (float)scaleFactor), (double)(((float)Display.getHeight() - this.vector.get(1)) / (float)scaleFactor), (double)this.vector.get(2)) : null;
   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      event.getPartialTicks();
      if (this.healthbar.isEnabled()) {
         if (this.theme == null) {
            this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
         }

         if (this.antibotModule == null) {
            this.antibotModule = (Antibot)Flap.instance.getModuleManager().getModule(Antibot.class);
         }

         mc.theWorld.getLoadedEntityList().stream().filter((entity) -> {
            return entity != mc.thePlayer && (!entity.isInvisible() && !entity.isInvisibleToPlayer(mc.thePlayer) || this.renderInvisibles.isEnabled()) && entity instanceof EntityPlayer && this.antibotModule.canAttack((EntityPlayer)entity, this);
         }).forEach((entity) -> {
            RenderUtils2.renderEntity(entity, 4, 0.0D, 0.0D, this.theme.getColor(100), ((EntityPlayer)entity).hurtTime != 0);
         });
      }

      if (this.boxoutline.isEnabled()) {
         if (this.theme == null) {
            this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
         }

         if (this.antibotModule == null) {
            this.antibotModule = (Antibot)Flap.instance.getModuleManager().getModule(Antibot.class);
         }

         mc.theWorld.getLoadedEntityList().stream().filter((entity) -> {
            return entity != mc.thePlayer && (!entity.isInvisible() && !entity.isInvisibleToPlayer(mc.thePlayer) || this.renderInvisibles.isEnabled()) && entity instanceof EntityPlayer && this.antibotModule.canAttack((EntityPlayer)entity, this);
         }).forEach((entity) -> {
            RenderUtils2.renderEntity(entity, 1, 0.0D, 0.0D, this.theme.getColor(100), ((EntityPlayer)entity).hurtTime != 0);
         });
      }

      if (this.boxmode.isEnabled()) {
         if (this.theme == null) {
            this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
         }

         if (this.antibotModule == null) {
            this.antibotModule = (Antibot)Flap.instance.getModuleManager().getModule(Antibot.class);
         }

         new Color(this.theme.getColor(100));
         mc.theWorld.getLoadedEntityList().stream().filter((entity) -> {
            return entity != mc.thePlayer && (!entity.isInvisible() && !entity.isInvisibleToPlayer(mc.thePlayer) || this.renderInvisibles.isEnabled()) && entity instanceof EntityPlayer && this.antibotModule.canAttack((EntityPlayer)entity, this);
         }).forEach((entity) -> {
            this.renderBox((EntityLivingBase)entity);
         });
      }

   }

   public void renderBox(@NotNull EntityLivingBase target) {
      if (target == null) {
         $$$reportNull$$$0(0);
      }

      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      RenderUtils2.renderEntity(target, 2, 0.0D, 0.0D, this.theme.getColor(100), target.hurtTime != 0);
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "target", "vestige/module/impl/visual/ESP", "renderBox"));
   }
}
