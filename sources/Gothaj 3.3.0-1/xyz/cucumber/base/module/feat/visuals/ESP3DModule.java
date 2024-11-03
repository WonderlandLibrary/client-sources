package xyz.cucumber.base.module.feat.visuals;

import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Allows you see players behind obsticles",
   name = "ESP3D"
)
public class ESP3DModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Hitbox", "Circle"});
   public BooleanSettings filled = new BooleanSettings("Filled", true);
   public ColorSettings color = new ColorSettings("Fill Color", "Static", -1, -1, 50);
   public BooleanSettings outline = new BooleanSettings("Outline", true);
   public ColorSettings outlineColor = new ColorSettings("Outline Color", "Static", -1, -1, 100);
   public ColorSettings hurtColor = new ColorSettings("Hit Fill Color", "Static", -1, -1, 100);
   public ColorSettings hurtOutlineColor = new ColorSettings("Hit Outline Color", "Static", -1, -1, 100);
   public ColorSettings murdColor = new ColorSettings("Murder Fill Color", "Static", -1, -1, 100);
   public ColorSettings murdOutlineColor = new ColorSettings("Murder Outline Color", "Static", -1, -1, 100);
   public NumberSettings size = new NumberSettings("Size", 1.0, 0.1, 2.0, 0.05);
   public NumberSettings outlineThick = new NumberSettings("Outline Thickness", 1.0, 0.1, 5.0, 0.05);

   public ESP3DModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.size,
            this.outlineThick,
            this.mode,
            this.filled,
            this.color,
            this.outline,
            this.outlineColor,
            this.hurtColor,
            this.hurtOutlineColor,
            this.murdColor,
            this.murdOutlineColor
         }
      );
   }

   @EventListener
   public void onRender3D(EventRender3D e) {
      GL11.glPushMatrix();
      Iterator var3 = this.mc.theWorld.playerEntities.iterator();

      while (true) {
         label55:
         while (true) {
            if (!var3.hasNext()) {
               GL11.glPopMatrix();
               return;
            }

            EntityPlayer player = (EntityPlayer)var3.next();
            if (RenderUtils.isInViewFrustrum(player) && (player != this.mc.thePlayer || this.mc.gameSettings.thirdPersonView != 0)) {
               GL11.glPushMatrix();
               double x = player.prevPosX + (player.posX - player.prevPosX) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
               double y = player.prevPosY + (player.posY - player.prevPosY) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
               double z = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
               String var10;
               switch ((var10 = this.mode.getMode().toLowerCase()).hashCode()) {
                  case -1360216880:
                     if (!var10.equals("circle")) {
                        break label55;
                     }

                     double width = (double)player.width * this.size.getValue() / 2.0;
                     GL11.glPushMatrix();
                     RenderUtils.start2D();
                     GL11.glShadeModel(7425);
                     GL11.glBegin(8);

                     for (double i = 0.0; i <= 360.0; i += 10.0) {
                        double px = x + Math.sin(i * Math.PI / 180.0) * width;
                        double pz = z - Math.cos(i * Math.PI / 180.0) * width;
                        int color = ColorUtils.getAlphaColor(
                           ColorUtils.mix(
                              ColorUtils.getColor(this.color, (double)(System.nanoTime() / 1000000L), i, 5.0),
                              ColorUtils.getColor(this.hurtColor, (double)(System.nanoTime() / 1000000L), i, 5.0),
                              (double)player.hurtTime,
                              10.0
                           ),
                           this.color.getAlpha()
                        );
                        if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
                           MurderFinderModule mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);

                           for (Entry<String, Entity> entry : mod.murders.entrySet()) {
                              if (entry.getKey().equalsIgnoreCase(player.getName())) {
                                 color = ColorUtils.getAlphaColor(
                                    ColorUtils.mix(
                                       ColorUtils.getColor(this.murdColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                       ColorUtils.getColor(this.hurtColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                       (double)player.hurtTime,
                                       10.0
                                    ),
                                    this.murdColor.getAlpha()
                                 );
                                 break;
                              }
                           }
                        }

                        RenderUtils.color(color);
                        GL11.glVertex3d(px, y, pz);
                        RenderUtils.color(color, 0.0F);
                        GL11.glVertex3d(px, y + 1.0, pz);
                     }

                     GL11.glEnd();
                     RenderUtils.stop2D();
                     GL11.glPopMatrix();
                     break label55;
                  case -1217012392:
                     if (var10.equals("hitbox")) {
                        double width = (double)player.width * this.size.getValue() / 2.0;
                        AxisAlignedBB bb = new AxisAlignedBB(
                           x - width, y + (double)player.height + (player.isSneaking() ? 0.0 : 0.2), z - width, x + width, y, z + width
                        );
                        GL11.glPushMatrix();
                        double yaw = (double)(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * e.getPartialTicks());
                        GL11.glTranslated(x, y, z);
                        GL11.glRotated(-yaw, 0.0, 1.0, 0.0);
                        GL11.glTranslated(-x, -y, -z);
                        RenderUtils.start2D();
                        if (this.filled.isEnabled()) {
                           int color = ColorUtils.getAlphaColor(
                              ColorUtils.mix(
                                 ColorUtils.getColor(this.color, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                 ColorUtils.getColor(this.hurtColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                 (double)player.hurtTime,
                                 10.0
                              ),
                              this.color.getAlpha()
                           );
                           if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
                              MurderFinderModule mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);

                              for (Entry<String, Entity> entryx : mod.murders.entrySet()) {
                                 if (entryx.getKey().equals(player.getName())) {
                                    color = ColorUtils.getAlphaColor(
                                       ColorUtils.mix(
                                          ColorUtils.getColor(this.murdColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                          ColorUtils.getColor(this.hurtColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                          (double)player.hurtTime,
                                          10.0
                                       ),
                                       this.murdColor.getAlpha()
                                    );
                                    break;
                                 }
                              }
                           }

                           RenderUtils.color(color);
                           RenderUtils.renderHitbox(bb, 7);
                        }

                        if (this.outline.isEnabled()) {
                           GL11.glLineWidth((float)this.outlineThick.getValue());
                           int color = ColorUtils.getAlphaColor(
                              ColorUtils.mix(
                                 ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                 ColorUtils.getColor(this.hurtOutlineColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                 (double)player.hurtTime,
                                 10.0
                              ),
                              this.outlineColor.getAlpha()
                           );
                           if (Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class).isEnabled()) {
                              MurderFinderModule mod = (MurderFinderModule)Client.INSTANCE.getModuleManager().getModule(MurderFinderModule.class);

                              for (Entry<String, Entity> entryxx : mod.murders.entrySet()) {
                                 if (entryxx.getKey().equalsIgnoreCase(player.getName())) {
                                    color = ColorUtils.getAlphaColor(
                                       ColorUtils.mix(
                                          ColorUtils.getColor(this.murdOutlineColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                          ColorUtils.getColor(this.hurtColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0),
                                          (double)player.hurtTime,
                                          10.0
                                       ),
                                       this.murdOutlineColor.getAlpha()
                                    );
                                    break;
                                 }
                              }
                           }

                           RenderUtils.color(color);
                           RenderUtils.renderHitbox(bb, 2);
                        }

                        RenderUtils.stop2D();
                        GL11.glPopMatrix();
                     }
                  default:
                     break label55;
               }
            }
         }

         GL11.glPopMatrix();
      }
   }
}
