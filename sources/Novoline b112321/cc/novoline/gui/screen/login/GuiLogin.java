package cc.novoline.gui.screen.login;

import cc.novoline.Initializer;
import cc.novoline.Novoline;
import cc.novoline.gui.button.HydraButton;
import cc.novoline.gui.screen.login.textbox.UIDField;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_50;
import cc.novoline.utils.fonts.impl.Fonts$OXIDE$OXIDE_55;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_16;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import cc.novoline.utils.shader.GLSLSandboxShader;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Scanner;
import net.AD;
import net.acE;
import net.qf;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.skidunion.J;
import net.skidunion.n;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GuiLogin extends GuiScreen implements n {
   private String status;
   private long ticks;
   private boolean launched;
   private boolean authenticated;
   private boolean darkTheme;
   private boolean falseError;
   private String token;
   private float fraction;
   public int alpha;
   private GLSLSandboxShader J;
   private long initTime;
   private final Color blackish;
   private final Color black;
   private final Color blueish;
   private final Color blue;
   private final String N;
   private AD z;
   private boolean M;
   private float hHeight;
   private float hWidth;
   private float errorBoxHeight;
   HydraButton button;
   UIDField field;

   public GuiLogin() {
      label0: {
         super();
         this.launched = true;
         this.authenticated = false;
         this.darkTheme = false;
         this.token = null;
         this.alpha = 0;
         this.initTime = System.currentTimeMillis();
         qf.a();
         this.blackish = new Color(20, 23, 26);
         this.black = new Color(40, 46, 51);
         this.blueish = new Color(131, 45, 241);
         this.blue = new Color(-13930063);
         this.N = Novoline.getInstance().getPathString() + "shader_properties.novo";
         this.z = new AD(0.0F, 0.0F, 0.0F, 0.0F, this.blueish.getRGB());
         this.hHeight = 540.0F;
         this.hWidth = 960.0F;
         this.errorBoxHeight = 0.0F;
         this.button = new HydraButton(0, (int)this.hWidth - 70, (int)(this.hHeight + 5.0F), 140, 30, "Log In");
         if(!Files.exists(Paths.get(this.N, new String[0]), new LinkOption[0])) {
            try {
               FileWriter var8 = new FileWriter(this.N);
               var8.append("loginMenu: true \n");
               var8.append("mainMenu: true \n");
               var8.append("altRepository: true \n");
               var8.close();
               this.M = true;
               break label0;
            } catch (IOException var6) {
               var6.printStackTrace();
            }
         }

         Scanner var2 = null;

         try {
            var2 = new Scanner(Paths.get(this.N, new String[0]));
            if(var2.hasNextLine()) {
               String var3 = var2.nextLine();
               if(var3.startsWith("loginMenu: ")) {
                  this.M = Boolean.parseBoolean(var3.split("loginMenu: ")[1].replace(" ", ""));
               }
            }
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

      this.status = "Idle";
      if(this.M) {
         try {
            this.J = new GLSLSandboxShader("/assets/minecraft/shaders/program/novoline_alt.fsh");
         } catch (IOException var4) {
            throw new IllegalStateException("Failed to load backgound shader", var4);
         }

         this.initTime = System.currentTimeMillis();
      }

   }

   public void initGui() {
      Display.setTitle("Novoline - Not logged in");
      this.buttonList.add(this.button);
      qf.b();
      this.field = new UIDField(1, this.mc.fontRendererObj, (int)this.hWidth - 70, (int)this.hHeight - 35, 140, 30, "idk");
      this.alpha = 100;
      this.darkTheme = true;
      super.initGui();
   }

   public void drawScreen(int var1, int var2, float var3) {
      int var4 = qf.a();
      if(this.M) {
         GlStateManager.disableCull();
         this.J.useShader(this.width * 2, this.height * 2, (float)var1, (float)var2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
         GL11.glBegin(7);
         GL11.glVertex2f(-1.0F, -1.0F);
         GL11.glVertex2f(-1.0F, 1.0F);
         GL11.glVertex2f(1.0F, 1.0F);
         GL11.glVertex2f(1.0F, -1.0F);
         GL11.glEnd();
         GL20.glUseProgram(0);
      }

      if(this.launched && this.darkTheme && this.fraction != 1.0049993F) {
         this.fraction = 1.0049993F;
      }

      if(this.darkTheme && this.fraction < 1.0F) {
         this.fraction = (float)((double)this.fraction + 0.015D);
      }

      if(!this.darkTheme && this.fraction > 0.0F) {
         this.fraction = (float)((double)this.fraction - 0.015D);
      }

      if(var1 <= 20 && var2 <= 20 && this.alpha < 255) {
         ++this.alpha;
      }

      if(this.alpha > 100) {
         --this.alpha;
      }

      new Color(-723464);
      Color var6 = Color.WHITE;
      Color var7 = new Color(150, 150, 150);
      this.button.b(this.interpolateColor(this.button.hovered(var1, var2)?this.blue.brighter():this.blue, this.button.hovered(var1, var2)?this.blueish.brighter():this.blueish, this.fraction));
      this.field.setColor(this.interpolateColor(var6, this.black, this.fraction));
      this.field.setTextColor(this.interpolateColor(var7, var6, this.fraction));
      ScaledResolution var8 = new ScaledResolution(this.mc);
      this.button.updateCoordinates(this.hWidth - 70.0F, this.hHeight + 5.0F);
      this.field.updateCoordinates(this.hWidth - 70.0F, this.hHeight - 35.0F);
      int var9 = var8.getScaledWidth();
      int var10 = var8.getScaledHeight();
      this.hHeight += ((float)(var10 / 2) - this.hHeight) * 0.02F;
      this.hWidth = (float)(var9 / 2);
      Gui.drawRect(0, 0, var9, var10, (new Color(0, 0, 0, 150)).getRGB());
      new Color(this.interpolateColor(this.blue, this.blueish, this.fraction));
      RenderUtils.drawBorderedRect(this.hWidth - 90.0F, this.hHeight - 50.0F, this.hWidth + 90.0F, this.hHeight + 57.0F, 2.0F, (new Color(26, 26, 26, 150)).getRGB(), (new Color(26, 26, 26, 150)).getRGB());
      this.z.a(this.hWidth - 90.0F, this.hHeight - 50.0F, this.hWidth + 90.0F, this.hHeight + 57.0F);
      this.z.a();
      Fonts$OXIDE$OXIDE_55.OXIDE_55.drawString("NOVOLINE", this.hWidth - (float)(Fonts$OXIDE$OXIDE_55.OXIDE_55.stringWidth("NOVOLINE") / 2) + 12.0F, this.hHeight - 85.0F, this.interpolateColor(this.blue, this.blueish, this.fraction));
      Fonts$ICONFONT$ICONFONT_50.ICONFONT_50.drawString("L", this.hWidth - 72.0F, this.hHeight - 90.0F, this.interpolateColor(this.blue, this.blueish, this.fraction));
      this.button.drawButton(this.mc, var1, var2);
      if(this.status.startsWith("Idle") || this.status.startsWith("Initializing") || this.status.startsWith("Logging")) {
         Fonts$SF$SF_16.SF_16.drawString(this.status, this.hWidth - (float)(Fonts$SF$SF_16.SF_16.stringWidth(this.status) / 2), this.hHeight + 45.0F, this.interpolateColor(new Color(150, 150, 150), var6, this.fraction));
         this.errorBoxHeight = 0.0F;
      }

      String var12 = this.status.equals("Success")?"Logged in as " + J.V:this.status;
      this.errorBoxHeight += (10.0F - this.errorBoxHeight) * 0.01F;
      RenderUtils.drawBorderedRect(this.hWidth - (float)(Fonts$SF$SF_16.SF_16.stringWidth(var12) / 2) - 10.0F, this.errorBoxHeight, this.hWidth + (float)(Fonts$SF$SF_16.SF_16.stringWidth(var12) / 2) + 10.0F, this.errorBoxHeight + 12.0F, 1.0F, (new Color(131, 45, 241, 150)).getRGB(), (new Color(131, 45, 241, 90)).getRGB());
      Fonts$SF$SF_16.SF_16.drawString(var12, (double)(this.hWidth - (float)(Fonts$SF$SF_16.SF_16.stringWidth(var12) / 2)), (double)(this.errorBoxHeight + 7.0F - (float)(Fonts$SF$SF_16.SF_16.getHeight() / 2)), (new Color(187, 102, 238)).darker().getRGB(), true);
      this.field.drawTextBox();
      Fonts$SF$SF_18.SF_18.drawString("made by gast and tasteful", this.hWidth - (float)(Fonts$SF$SF_18.SF_18.stringWidth("made by gast and tasteful") / 2), (float)(var10 - Fonts$SF$SF_18.SF_18.getHeight() - 4), (new Color(150, 150, 150)).getRGB());
      if(this.authenticated) {
         this.status = "Success";
         if(System.currentTimeMillis() - this.ticks > 250L) {
            Initializer.getInstance().a(this.token);
         }
      }

      if(this.falseError) {
         try {
            ScaledResolution var14 = new ScaledResolution(this.mc);
            this.mouseClicked(var14.getScaledWidth() / 2, var14.getScaledHeight() / 2 + 20, 0);
         } catch (IOException var13) {
            var13.printStackTrace();
         }

         this.falseError = false;
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      int var3 = qf.a();
      if(this.field.isFocused() && var2 >= 2 && var2 <= 11 || var2 == 14) {
         this.field.textboxKeyTyped(var1, var2);
      }

      if(var2 == 1) {
         this.initGui();
      }

      if(var2 == 64) {
         this.mc.displayGuiScreen(this);
      }

      if(acE.b() == null) {
         ++var3;
         qf.b(var3);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      this.field.mouseClicked(var1, var2, var3);
      super.mouseClicked(var1, var2, var3);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      int var2 = qf.a();
      if(var1.id == 0) {
         (new Thread(this::lambda$actionPerformed$0, "SAL Authentication Thread")).start();
      }

      super.actionPerformed(var1);
   }

   public void d(String var1) {
      this.ticks = System.currentTimeMillis();
      this.authenticated = true;
      this.token = var1;
   }

   private int interpolateColor(Color var1, Color var2, float var3) {
      int var4 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var3);
      int var5 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var3);
      int var6 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var3);
      int var7 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var3);

      try {
         return (new Color(var4, var5, var6, var7)).getRGB();
      } catch (Exception var9) {
         return -1;
      }
   }

   private void lambda$actionPerformed$0(GuiButton var1) {
      qf.b();
      var1.enabled = false;
      GuiLogin var10000 = this;
      String var10001 = "Initializing";

      try {
         var10000.status = var10001;
         Novoline.getInstance().a(new J(this, true, true));
         this.status = "Logging in";
         Novoline.getInstance().A().a(this.field.getText());
         this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.orb"), 1.0F));
      } catch (Throwable var4) {
         var4.printStackTrace();
         if(var4.getMessage().contains("ConcurrentModificationException")) {
            this.falseError = true;
         }

         this.status = var4.getMessage();
         var1.enabled = true;
         this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.orb"), -1.0F));
      }

   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
