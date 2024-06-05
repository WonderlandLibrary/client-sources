package net.shadersmod.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import net.af_;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.optifine.Config;
import net.optifine.Lang;
import net.shadersmod.client.EnumShaderOption;
import net.shadersmod.client.GuiButtonEnumShaderOption;
import net.shadersmod.client.GuiShaderOptions;
import net.shadersmod.client.GuiShaders$1;
import net.shadersmod.client.GuiSlotShaders;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;
import net.shadersmod.client.ShadersTex;
import org.lwjgl.Sys;

public class GuiShaders extends GuiScreen {
   protected GuiScreen parentGui;
   protected String screenTitle = "Shaders";
   private int updateTimer = -1;
   private GuiSlotShaders shaderList;
   private boolean saved = false;
   private static float[] QUALITY_MULTIPLIERS = new float[]{0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F};
   private static String[] QUALITY_MULTIPLIER_NAMES = new String[]{"0.5x", "0.7x", "1x", "1.5x", "2x"};
   private static float[] HAND_DEPTH_VALUES = new float[]{0.0625F, 0.125F, 0.25F};
   private static String[] HAND_DEPTH_NAMES = new String[]{"0.5x", "1x", "2x"};
   public static final int EnumOS_UNKNOWN = 0;
   public static final int EnumOS_WINDOWS = 1;
   public static final int EnumOS_OSX = 2;
   public static final int EnumOS_SOLARIS = 3;
   public static final int EnumOS_LINUX = 4;

   public GuiShaders(GuiScreen var1, GameSettings var2) {
      this.parentGui = var1;
   }

   public void initGui() {
      this.screenTitle = I18n.format("of.options.shadersTitle", new Object[0]);
      if(Shaders.shadersConfig == null) {
         Shaders.loadConfig();
      }

      byte var1 = 120;
      byte var2 = 20;
      int var3 = this.width - var1 - 10;
      byte var4 = 30;
      byte var5 = 20;
      int var6 = this.width - var1 - 20;
      this.shaderList = new GuiSlotShaders(this, var6, this.height, var4, this.height - 50, 16);
      this.shaderList.registerScrollButtons(7, 8);
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, var3, var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, var3, var5 + var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, var3, 2 * var5 + var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, var3, 3 * var5 + var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, var3, 4 * var5 + var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, var3, 5 * var5 + var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, var3, 6 * var5 + var4, var1, var2));
      this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, var3, 7 * var5 + var4, var1, var2));
      int var7 = Math.min(150, var6 / 2 - 10);
      this.buttonList.add(new GuiButton(201, var6 / 4 - var7 / 2, this.height - 25, var7, var2, Lang.get("of.options.shaders.shadersFolder")));
      this.buttonList.add(new GuiButton(202, var6 / 4 * 3 - var7 / 2, this.height - 25, var7, var2, I18n.format("gui.done", new Object[0])));
      this.buttonList.add(new GuiButton(203, var3, this.height - 25, var1, var2, Lang.get("of.options.shaders.shaderOptions")));
      this.updateButtons();
   }

   public void updateButtons() {
      ShaderOption.p();
      boolean var2 = Config.isShaders();
      Iterator var3 = this.buttonList.iterator();
      if(var3.hasNext()) {
         GuiButton var4 = (GuiButton)var3.next();
         if(var4.id != 201 && var4.id != 202 && var4.id != EnumShaderOption.ANTIALIASING.ordinal()) {
            var4.enabled = var2;
         }
      }

   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.shaderList.handleMouseInput();
   }

   protected void actionPerformed(GuiButton var1) {
      String[] var2 = ShaderOption.p();
      if(var1.enabled) {
         if(var1 instanceof GuiButtonEnumShaderOption) {
            GuiButtonEnumShaderOption var3 = (GuiButtonEnumShaderOption)var1;
            switch(GuiShaders$1.$SwitchMap$net$shadersmod$client$EnumShaderOption[var3.getEnumShaderOption().ordinal()]) {
            case 1:
               Shaders.nextAntialiasingLevel();
               Shaders.uninit();
            case 2:
               Shaders.configNormalMap = false;
               Shaders.uninit();
               this.mc.scheduleResourcesRefresh();
            case 3:
               Shaders.configSpecularMap = false;
               Shaders.uninit();
               this.mc.scheduleResourcesRefresh();
            case 4:
               float var4 = Shaders.configRenderResMul;
               float[] var5 = QUALITY_MULTIPLIERS;
               int var6 = getValueIndex(var4, var5);
               if(isShiftKeyDown()) {
                  --var6;
                  var6 = var5.length - 1;
               }

               ++var6;
               if(var6 >= var5.length) {
                  var6 = 0;
               }

               Shaders.configRenderResMul = var5[var6];
               Shaders.uninit();
               Shaders.scheduleResize();
            case 5:
               float var7 = Shaders.configShadowResMul;
               float[] var8 = QUALITY_MULTIPLIERS;
               int var9 = getValueIndex(var7, var8);
               if(isShiftKeyDown()) {
                  --var9;
                  var9 = var8.length - 1;
               }

               ++var9;
               if(var9 >= var8.length) {
                  var9 = 0;
               }

               Shaders.configShadowResMul = var8[var9];
               Shaders.uninit();
               Shaders.scheduleResizeShadow();
            case 6:
               float var10 = Shaders.configHandDepthMul;
               float[] var11 = HAND_DEPTH_VALUES;
               int var12 = getValueIndex(var10, var11);
               if(isShiftKeyDown()) {
                  --var12;
                  var12 = var11.length - 1;
               }

               ++var12;
               if(var12 >= var11.length) {
                  var12 = 0;
               }

               Shaders.configHandDepthMul = var11[var12];
               Shaders.uninit();
            case 7:
               Shaders.configOldHandLight.b();
               Shaders.uninit();
            case 8:
               Shaders.configOldLighting.b();
               Shaders.updateBlockLightLevel();
               Shaders.uninit();
               this.mc.scheduleResourcesRefresh();
            case 9:
               Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
            case 10:
               Shaders.configCloudShadow = !Shaders.configCloudShadow;
            case 11:
               Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
               Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
               var1.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
               ShadersTex.updateTextureMinMagFilter();
            case 12:
               Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
               var1.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
               ShadersTex.updateTextureMinMagFilter();
            case 13:
               Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
               var1.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
               ShadersTex.updateTextureMinMagFilter();
            case 14:
               Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
               var1.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
               ShadersTex.updateTextureMinMagFilter();
            default:
               var3.updateButtonText();
            }
         }

         switch(var1.id) {
         case 201:
            switch(getOSType()) {
            case 1:
               String var17 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{Shaders.shaderpacksdir.getAbsolutePath()});

               try {
                  Runtime.getRuntime().exec(var17);
                  return;
               } catch (IOException var16) {
                  var16.printStackTrace();
               }
            case 2:
               try {
                  Runtime.getRuntime().exec(new String[]{"/usr/bin/open", Shaders.shaderpacksdir.getAbsolutePath()});
                  return;
               } catch (IOException var15) {
                  var15.printStackTrace();
               }
            default:
               boolean var18 = false;
               String var10000 = "java.awt.Desktop";

               try {
                  Class var19 = af_.a(var10000);
                  Object var21 = af_.b(var19, "getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                  af_.b(var19, "browse", new Class[]{URI.class}).invoke(var21, new Object[]{(new File(this.mc.mcDataDir, "shaderpacks")).toURI()});
               } catch (Throwable var14) {
                  var14.printStackTrace();
                  var18 = true;
               }

               if(!var18) {
                  return;
               }

               Config.dbg("Opening via system class!");
               Sys.openURL("file://" + Shaders.shaderpacksdir.getAbsolutePath());
            }
         case 202:
            Shaders.storeConfig();
            this.saved = true;
            this.mc.displayGuiScreen(this.parentGui);
         case 203:
            GuiShaderOptions var20 = new GuiShaderOptions(this, Config.getGameSettings());
            Config.getMinecraft().displayGuiScreen(var20);
         case 210:
            String var30 = "java.awt.Desktop";

            try {
               Class var22 = af_.a(var30);
               Object var25 = af_.b(var22, "getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
               af_.b(var22, "browse", new Class[]{URI.class}).invoke(var25, new Object[]{new URI("http://optifine.net/shaderPacks")});
            } catch (Throwable var13) {
               var13.printStackTrace();
            }
         case 204:
         case 205:
         case 206:
         case 207:
         case 208:
         case 209:
         default:
            this.shaderList.actionPerformed(var1);
         }
      }

   }

   public void onGuiClosed() {
      super.onGuiClosed();
      if(!this.saved) {
         Shaders.storeConfig();
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      ShaderOption.p();
      this.drawDefaultBackground();
      this.shaderList.drawScreen(var1, var2, var3);
      if(this.updateTimer <= 0) {
         this.shaderList.updateList();
         this.updateTimer += 20;
      }

      this.drawCenteredString(this.fontRendererObj, this.screenTitle + " ", this.width / 2, 15, 16777215);
      String var5 = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
      int var6 = this.fontRendererObj.d(var5);
      if(var6 < this.width - 5) {
         this.drawCenteredString(this.fontRendererObj, var5, this.width / 2, this.height - 40, 8421504);
      }

      this.drawString(this.fontRendererObj, var5, 5, this.height - 40, 8421504);
      super.drawScreen(var1, var2, var3);
   }

   public void updateScreen() {
      super.updateScreen();
      --this.updateTimer;
   }

   public Minecraft getMc() {
      return this.mc;
   }

   public void drawCenteredString(String var1, int var2, int var3, int var4) {
      this.drawCenteredString(this.fontRendererObj, var1, var2, var3, var4);
   }

   public static String toStringOnOff(boolean var0) {
      String var1 = Lang.getOn();
      String var2 = Lang.getOff();
      return var1;
   }

   public static String toStringAa(int var0) {
      String[] var1 = ShaderOption.p();
      return var0 == 2?"FXAA 2x":(var0 == 4?"FXAA 4x":Lang.getOff());
   }

   public static String toStringValue(float var0, float[] var1, String[] var2) {
      int var3 = getValueIndex(var0, var1);
      return var2[var3];
   }

   public static int getValueIndex(float var0, float[] var1) {
      ShaderOption.p();
      int var3 = 0;
      if(var3 < var1.length) {
         float var4 = var1[var3];
         if(var4 >= var0) {
            return var3;
         }

         ++var3;
      }

      return var1.length - 1;
   }

   public static String toStringQuality(float var0) {
      return toStringValue(var0, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
   }

   public static String toStringHandDepth(float var0) {
      return toStringValue(var0, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
   }

   public static int getOSType() {
      ShaderOption.p();
      String var1 = System.getProperty("os.name").toLowerCase();
      return var1.contains("win")?1:(var1.contains("mac")?2:(var1.contains("solaris")?3:(var1.contains("sunos")?3:(var1.contains("linux")?4:(var1.contains("unix")?4:0)))));
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
