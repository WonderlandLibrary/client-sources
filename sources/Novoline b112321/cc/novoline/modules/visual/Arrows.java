package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.PlayerUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.ScaleUtils;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import java.awt.Color;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class Arrows extends AbstractModule {
   @Property("only_invisible")
   private final BooleanProperty onlyInvisibles = PropertyFactory.booleanTrue();
   @Property("radius")
   private final IntProperty radius = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(150)).minimum(Integer.valueOf(55))).maximum(Integer.valueOf(165));
   @Property("color")
   private final ColorProperty color = PropertyFactory.createColor(Integer.valueOf(-16776961));
   @Property("targets")
   private final ListProperty targets = PropertyFactory.createList((Object)"Players").acceptableValues((Object[])(new String[]{"Players", "Animals", "Mobs"}));
   @Property("filters")
   private final ListProperty filters = PropertyFactory.createList((Object)"Teams").acceptableValues((Object[])(new String[]{"Teams"}));
   @Property("targets-only")
   private final BooleanProperty targetsOnly = PropertyFactory.booleanFalse();
   @Property("color-type")
   private final StringProperty colorType = PropertyFactory.createString("Static").acceptableValues(new String[]{"Static", "Team", "Rainbow"});
   @Property("rainbow-type")
   private final StringProperty rainbowType = PropertyFactory.createString("Static").acceptableValues(new String[]{"Static", "Cycle"});
   @Property("alpha-type")
   private final StringProperty alphaType = PropertyFactory.createString("Distance").acceptableValues(new String[]{"Distance", "Pulse", "Static"});
   @Property("alpha")
   private final IntProperty alpha = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(255)).minimum(Integer.valueOf(0))).maximum(Integer.valueOf(255));
   private static final int HALF_PI_DEGREES_INT = 90;
   private static final float TWO_PI_DEGREES_FLOAT = 360.0F;
   private static final float RGB_MAX_FLOAT = 255.0F;
   private static double WIDTH = 0.03D;
   private static double HEIGHT = 5.0D;
   private int alph = 1;
   private boolean invert;

   public Arrows(ModuleManager var1) {
      super(var1, "Arrows", EnumModuleType.VISUALS, "Shows arrows to players");
      Manager.put(new Setting("ARROWS_ONLY_VISIBLES", "Not in FOV only", SettingType.CHECKBOX, this, this.onlyInvisibles));
      Manager.put(new Setting("ARROWS_TARGETS_ONLY", "Targets Only", SettingType.CHECKBOX, this, this.targetsOnly));
      Manager.put(new Setting("ARROWS_RADIUS", "Radius", SettingType.SLIDER, this, this.radius, 1.0D));
      Manager.put(new Setting("ARROWS_TARGETS", "Targets", SettingType.SELECTBOX, this, this.targets));
      Manager.put(new Setting("ARROWS_FILTER", "Filters", SettingType.SELECTBOX, this, this.filters, this::lambda$new$0));
      Manager.put(new Setting("ARROWS_COLORTYPE", "Color Type", SettingType.COMBOBOX, this, this.colorType));
      Manager.put(new Setting("ARROWS_RAINBOWTYPE", "Rainbow Type", SettingType.COMBOBOX, this, this.rainbowType, this::lambda$new$1));
      Manager.put(new Setting("ARROWS_COLOR", "Color", SettingType.COLOR_PICKER, this, this.color, (EnumSet)null));
      Manager.put(new Setting("ARROWS_ALPHA", "Alpha Type", SettingType.COMBOBOX, this, this.alphaType));
      Manager.put(new Setting("ARROWS_ALPHA_SLIDER", "Alpha", SettingType.SLIDER, this, this.alpha, 5.0D, this::lambda$new$3));
   }

   public static AbstractModule create(ModuleManager var0) {
      return new Arrows(var0);
   }

   protected final Vec3 getVectorForRotation(float var1, float var2) {
      float var3 = MathHelper.cos(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var4 = MathHelper.sin(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var5 = -MathHelper.cos(Math.toRadians((double)(-var1)));
      float var6 = MathHelper.sin(Math.toRadians((double)(-var1)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }

   @EventTarget
   public void onRender(Render2DEvent var1) {
      HUD.h();
      Minecraft var3 = this.mc;
      EntityPlayerSP var4 = var3.player;
      float var5 = var4.rotationYaw;
      WIDTH = 0.03D * (135.0D / ((Integer)this.radius.get()).doubleValue());
      ScaledResolution var6 = var1.getResolution();
      double var7 = (double)var6.getScaledWidthStatic(this.mc);
      double var9 = (double)var6.getScaledHeightStatic(this.mc);
      double var11 = var7 / 2.0D;
      double var13 = var9 / 2.0D;
      boolean var15 = ((Boolean)this.onlyInvisibles.get()).booleanValue();
      int var16 = ((Integer)this.radius.get()).intValue();
      ColorProperty var17 = this.color;
      int var18 = 1;

      label186:
      for(Entity var20 : (List)var3.world.getLoadedEntityList().stream().sorted(Comparator.comparingDouble(Arrows::lambda$onRender$4)).collect(Collectors.toList())) {
         if(this.isValidTarget(var20) && (!RenderUtils.isInViewFrustrum(var20) || !var15)) {
            if(((Boolean)this.targetsOnly.get()).booleanValue() && !this.novoline.getPlayerManager().hasType(var20.getName(), PlayerManager$EnumPlayerType.TARGET)) {
               ;
            }

            double var21 = (double)var20.getDistanceToEntity(var4);
            RenderUtils.start2D();
            GL11.glPushMatrix();
            ScaleUtils.scale(this.mc);
            GL11.glLineWidth(2.0F);
            float var23 = 0.0F;
            String var24 = (String)this.alphaType.get();
            byte var25 = -1;
            switch(var24.hashCode()) {
            case -1808614770:
               if(!var24.equals("Static")) {
                  break;
               }

               var25 = 0;
            case 353103893:
               if(!var24.equals("Distance")) {
                  break;
               }

               var25 = 1;
            case 77474681:
               if(var24.equals("Pulse")) {
                  var25 = 2;
               }
            }

            switch(var25) {
            case 0:
               var23 = (float)((Integer)this.alpha.get()).intValue() / 255.0F;
            case 1:
               var23 = 1.0F - MathHelper.clamp_float((float)var17.getAlpha() / 255.0F * (float)var21 * 3.0F, 0.0F, 255.0F) / 255.0F;
            case 2:
               var23 = (float)this.alph / 255.0F;
            default:
               Color var35 = new Color(102, 201, 255, 255);
               if(var20 instanceof EntityPlayer && this.novoline.getPlayerManager().hasType(var20.getName(), PlayerManager$EnumPlayerType.TARGET)) {
                  var35 = this.b;
               }

               if(var20 instanceof EntityPlayer && this.novoline.getPlayerManager().hasType(var20.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
                  var35 = this.c;
               }

               if(this.colorType.equals("Static")) {
                  var35 = var17.getAwtColor();
               }

               if(this.colorType.equals("Rainbow")) {
                  var35 = new Color(this.getArrayRainbow(((String)this.rainbowType.get()).equalsIgnoreCase("Static")?1:var18, 255));
               }

               if(var20 instanceof EntityPlayer && this.colorType.equals("Team")) {
                  var35 = PlayerUtils.c(var20);
               }

               float var36 = (float)(var35.getRGB() >> 16 & 255) / 255.0F;
               float var26 = (float)(var35.getRGB() >> 8 & 255) / 255.0F;
               float var27 = (float)(var35.getRGB() & 255) / 255.0F;
               GL11.glColor4f(var36, var26, var27, var23);
               GL11.glEnable(2881);
               GL11.glBegin(5);
               float var28 = (RotationUtil.getYawToPoint(var20.lastTickPosX + (var20.posX - var20.lastTickPosX) * (double)this.mc.timer.elapsedPartialTicks, var20.lastTickPosZ + (var20.posZ - var20.lastTickPosZ) * (double)this.mc.timer.elapsedPartialTicks) + 360.0F) % 360.0F;
               float var29 = (var28 - var5 + 360.0F) % 360.0F - 90.0F;
               double var30 = Math.toRadians((double)var29);
               GL11.glVertex2d(var11 + (double)(MathHelper.cos(var30) * (float)var16), var13 + (double)(MathHelper.sin(var30) * (float)var16));
               GL11.glVertex2d(var11 + (double)MathHelper.cos(var30 + WIDTH) * ((double)var16 - HEIGHT), var13 + (double)MathHelper.sin(var30 + WIDTH) * ((double)var16 - HEIGHT));
               GL11.glVertex2d(var11 + (double)MathHelper.cos(var30 - WIDTH) * ((double)var16 - HEIGHT), var13 + (double)MathHelper.sin(var30 - WIDTH) * ((double)var16 - HEIGHT));
               GL11.glVertex2d(var11 + (double)(MathHelper.cos(var30) * (float)var16), var13 + (double)(MathHelper.sin(var30) * (float)var16));
               GL11.glEnd();
               GL11.glDisable(2881);
               GL11.glPopMatrix();
               RenderUtils.stop2D();
               ++var18;
               break label186;
            }
         }
      }

      if(!this.invert && this.alph < 255) {
         ++this.alph;
      }

      this.invert = true;
      if(this.invert && this.alph > 0) {
         --this.alph;
      }

      this.invert = false;
   }

   public boolean isValidTarget(Entity var1) {
      int var2 = HUD.e();
      if(var1.isEntityAlive() && this.mc.player.getHealth() > 0.0F && !this.novoline.getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
         if(!(var1 instanceof EntityMob) && !(var1 instanceof EntitySlime) && !(var1 instanceof EntityGolem)) {
            if(!(var1 instanceof EntityAnimal) && !(var1 instanceof EntityVillager)) {
               if(var1 instanceof EntityPlayer) {
                  return this.targets.contains("Players") && var1 != this.mc.player && (!this.filters.contains("Teams") || !PlayerUtils.inTeamWithMinecraftPlayer(var1)) && (!var1.isInvisible() || this.targets.contains("Invisibles")) && !this.isAutismShopKeeperCheck(var1);
               } else {
                  return false;
               }
            } else {
               return this.targets.contains("Animals") && (!var1.isInvisible() || this.targets.contains("Invisibles"));
            }
         } else {
            return this.targets.contains("Mobs") && (!var1.isInvisible() || this.targets.contains("Invisibles"));
         }
      } else {
         return false;
      }
   }

   private static boolean hasArmor(EntityPlayer var0) {
      HUD.h();
      ItemStack[] var2 = var0.inventory.armorInventory;
      return var2[0] != null || var2[1] != null || var2[2] != null || var2[3] != null;
   }

   private boolean isAutismShopKeeperCheck(ICommandSender var1) {
      HUD.e();
      IChatComponent var3 = var1.getDisplayName();
      String var4 = var3.getFormattedText();
      String var5 = var3.getUnformattedText();
      boolean var6 = !var4.substring(0, var4.length() - 2).contains("§");
      boolean var7 = var4.substring(var4.length() - 2).contains("§");
      return ServerUtils.isHypixel() && ServerUtils.serverIs(Servers.BW) && var6 && var7;
   }

   public int getArrayRainbow(int var1, int var2) {
      boolean var3 = true;
      double var4 = Math.ceil((double)(System.currentTimeMillis() - (long)var1 * 50L)) / 10.0D;
      var4 = var4 % 360.0D;
      float[] var6 = this.color.getHSB();
      Color var7 = Color.getHSBColor((float)(var4 / 360.0D), var6[1], var6[2]);
      return (new Color(var7.getRed(), var7.getGreen(), var7.getBlue(), var2)).getRGB();
   }

   private static double lambda$onRender$4(float var0, Entity var1) {
      return Math.toRadians((double)(((RotationUtil.getYawToPoint(var1.posX, var1.posZ) + 360.0F) % 360.0F - var0 + 360.0F) % 360.0F - 90.0F));
   }

   private Boolean lambda$new$3() {
      return Boolean.valueOf(((String)this.alphaType.get()).equalsIgnoreCase("Static"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(((String)this.colorType.get()).equalsIgnoreCase("Rainbow"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.targets.contains("Players"));
   }
}
