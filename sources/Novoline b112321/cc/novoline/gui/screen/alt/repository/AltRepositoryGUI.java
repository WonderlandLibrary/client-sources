package cc.novoline.gui.screen.alt.repository;

import cc.novoline.Novoline;
import cc.novoline.gui.button.RoundedButton;
import cc.novoline.gui.group.GuiGroupPlayerBox;
import cc.novoline.gui.screen.alt.login.AltLoginThread;
import cc.novoline.gui.screen.alt.repository.Alt;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI$1;
import cc.novoline.gui.screen.alt.repository.AltRepositoryGUI$EnumSort;
import cc.novoline.gui.screen.alt.repository.GuiAddAlt;
import cc.novoline.gui.screen.alt.repository.TokenField;
import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import cc.novoline.gui.screen.alt.repository.credential.AlteningAltCredential;
import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfile;
import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfileFactory;
import cc.novoline.gui.screen.alt.repository.tclient.TClient;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_16;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_28;
import cc.novoline.utils.java.FilteredArrayList;
import cc.novoline.utils.minecraft.FakeEntityPlayer;
import cc.novoline.utils.notifications.NotificationType;
import cc.novoline.utils.shader.GLSLSandboxShader;
import com.google.common.base.Strings;
import com.mojang.authlib.GameProfile;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.response.Account;
import com.thealtening.api.response.AccountDetails;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.CI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class AltRepositoryGUI extends GuiScreen {
   static final int PLAYER_BOX_WIDTH = 320;
   static final int PLAYER_BOX_HEIGHT = 36;
   static final float PLAYER_BOX_SPACE = 3.0F;
   static final int BUTTON_WIDTH = 159;
   static final int BUTTON_HEIGHT = 30;
   static final int VERTICAL_MARGIN = 25;
   static final int HORIZONTAL_MARGIN = 15;
   static final int SCROLL_ALTS = 1;
   private final Novoline novoline;
   private final TClient tclient;
   private final Logger logger;
   private Alt currentAlt;
   private GLSLSandboxShader N;
   private long initTime;
   private GuiGroupPlayerBox groupPlayerBox;
   private AltRepositoryGUI$EnumSort sortType;
   private RoundedButton sortButton;
   private final String F;
   private boolean A;
   private TokenField searchField;
   private final FilteredArrayList alts;
   private TokenField apiKeyField;
   private String tokenContent;
   private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##");
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
   private float altWidth;
   private float altBoxAnimationStep;
   private float altBoxAlphaStep;
   private static final float DOWN_MARGIN = 5.0F;
   private static final int SCROLL_BAR_EMPTY_COLOR = (new Color(0, 0, 0, 50)).getRGB();
   private static final int SCROLL_BAR_SELECTED_COLOR = (new Color(255, 255, 255, 30)).getRGB();
   private int scrolled;
   private List visibleAlts;
   private int visibleAltsCount;
   private float sliderHeight;
   private boolean dragging;

   public AltRepositoryGUI(Novoline var1) {
      label0: {
         super();
         CI.b();
         this.tclient = new TClient();
         this.logger = LogManager.getLogger();
         this.initTime = System.currentTimeMillis();
         this.sortType = AltRepositoryGUI$EnumSort.DATE;
         this.F = Novoline.getInstance().getPathString() + "shader_properties.novo";
         this.alts = new FilteredArrayList(ObjectLists.synchronize(new ObjectArrayList()), this::lambda$new$0, this::lambda$new$1);
         this.tokenContent = "";
         this.altWidth = 0.0F;
         this.novoline = var1;
         if(!Files.exists(Paths.get(this.F, new String[0]), new LinkOption[0])) {
            try {
               FileWriter var11 = new FileWriter(this.F);
               var11.append("loginMenu: true \n");
               var11.append("mainMenu: true \n");
               var11.append("altRepository: true \n");
               var11.close();
               this.A = true;
               break label0;
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }

         Scanner var3 = null;

         try {
            var3 = new Scanner(Paths.get(this.F, new String[0]));
            if(var3.hasNextLine()) {
               String var4 = var3.nextLine();
               System.out.println(var4);
               if(var4.startsWith("altRepository: ")) {
                  String var5 = var4.split("altRepository: ")[1].replace(" ", "");
                  this.A = Boolean.parseBoolean(var5);
               }
            }
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      if(this.A) {
         try {
            this.N = new GLSLSandboxShader("/assets/minecraft/shaders/program/novoline_alt.fsh");
         } catch (IOException var7) {
            throw new IllegalStateException("Failed to load backgound shader", var7);
         }

         this.initTime = System.currentTimeMillis();
      }

      this.loadAlts();
      if(StringUtils.isBlank(this.tokenContent)) {
         try {
            this.tokenContent = Strings.nullToEmpty(this.readApiKey());
         } catch (IOException var6) {
            this.logger.error("An error occurred while reading data file", var6);
            this.tokenContent = "";
         }
      }

   }

   protected void actionPerformed(GuiButton var1) {
      int[] var2 = CI.b();
      switch(var1.id) {
      case 69:
         Object var3 = null;
         Clipboard var4 = Toolkit.getDefaultToolkit().getSystemClipboard();
         var4.setContents((Transferable)var3, (ClipboardOwner)var3);
         this.novoline.getNotificationManager().pop("Copied your token to a clipboard!", 4000, NotificationType.INFO);
         this.novoline.getNotificationManager().pop("Failed to grab a token!", 4000, NotificationType.ERROR);
      case 0:
         this.mc.displayGuiScreen(new GuiAddAlt(this, "Add Alt", "Add Alt", "Generate and save", this::lambda$actionPerformed$2));
      case 1:
         this.removeCurrentAlt();
      case 2:
         this.mc.displayGuiScreen(new GuiAddAlt(this, "Login", "Alt Login", "Generate and log in", this::lambda$actionPerformed$3));
      case 3:
         this.refreshAlts();
      case 4:
         String var5 = this.apiKeyField.getText();
         if(StringUtils.isBlank(var5)) {
            Novoline.getInstance().getNotificationManager().pop("No TheAltening token!", NotificationType.ERROR);
            return;
         } else {
            this.novoline.getDataRetriever().updateKey(var5);
            ForkJoinPool.commonPool().execute(this::lambda$actionPerformed$5);
         }
      case 5:
         AltRepositoryGUI$EnumSort[] var6 = AltRepositoryGUI$EnumSort.values();
         this.sortType = var6[(this.sortType.ordinal() + 1) % var6.length];
         this.sortButton.displayString = this.sortType.getCriteria();
         this.alts.update();
         this.setScrolledAndUpdate(0);
      case 10:
         try {
            Runtime var7 = Runtime.getRuntime();
            String var8 = "https://shop.thealtening.com/?r=novoline";
            var7.exec("rundll32 url.dll,FileProtocolHandler " + var8);
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      default:
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) {
      CI.b();
      int var5 = 0;
      if(var5 < this.buttonList.size()) {
         GuiButton var6 = (GuiButton)this.buttonList.get(var5);
         if(var6.mousePressed(this.mc, var1, var2)) {
            this.selectedButton = var6;
            var6.playPressSound(this.mc.getSoundHandler());
            this.actionPerformed(var6);
            return;
         }

         ++var5;
      }

      var5 = 0;
      if(var5 < this.visibleAlts.size()) {
         Alt var11 = (Alt)this.visibleAlts.get(var5);
         if(var11.mouseClicked(this.altWidth, 25.0F + (float)var5 * 39.0F, var1, var2)) {
            return;
         }

         ++var5;
      }

      if(var1 >= 3 && var1 <= 12 && var2 >= 25 && (float)var2 <= 25.0F + this.sliderHeight) {
         float var10 = (this.sliderHeight - 25.0F) / (float)this.alts.size();
         boolean var12 = (float)var2 >= 25.0F + var10 * (float)this.scrolled;
         if(var12 && (float)var2 <= Math.min(25.0F + var10 * (float)this.visibleAltsCount, this.sliderHeight) + 25.0F + var10 * (float)this.scrolled) {
            this.dragging = true;
         }

         this.setScrolledAndUpdate(this.scrolled + MathHelper.ceiling_double_int((double)this.alts.size() / 5.0D) * (var12?1:-1));
      }

      this.apiKeyField.mouseClicked(var1, var2, var3);
      this.searchField.mouseClicked(var1, var2, var3);
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      TokenField var2 = this.searchField;
      int var3 = this.width - 320 - 15;
      int var4 = this.height - 25 - 24 - 90;
      CI.b();
      this.altWidth = (float)(-15 + this.width - 15 - 320 - 15);
      this.altBoxAnimationStep = this.altWidth / 564.0F * 5.0F;
      this.altBoxAlphaStep = 255.0F / (this.altWidth / 5.0F);
      this.apiKeyField = new TokenField(0, this.mc.fontRendererObj, this.width - 255, 4, 100, 20, "The Altening Key:");
      this.searchField = new TokenField(0, this.mc.fontRendererObj, this.width - 740, 4, 180, 20, "Search:");
      this.sortType = AltRepositoryGUI$EnumSort.DATE;
      this.searchField.setText(var2.getText());
      this.groupPlayerBox = new GuiGroupPlayerBox(var3, 25, 320, var4, this::getSelectedAlt);
      this.groupPlayerBox.addLine(AltRepositoryGUI::lambda$initGui$6);
      this.groupPlayerBox.addLine(AltRepositoryGUI::lambda$initGui$7);
      this.groupPlayerBox.addLine(AltRepositoryGUI::lambda$initGui$8);
      this.sliderHeight = (float)(-25 + this.height) + -5.0F;
      int var5 = this.visibleAltsCount;
      this.visibleAltsCount = this.getVisibleAltsCount();
      if(var5 < this.visibleAltsCount && this.alts.size() - this.scrolled < this.visibleAltsCount) {
         this.setScrolledAndUpdate(this.alts.size() - this.visibleAltsCount);
      }

      this.updateVisibleAlts();
      this.apiKeyField.setText(this.tokenContent);
      this.buttonList.add(new RoundedButton("Add", 0, var3, var4 + 25 + 5, 158, 30, 15, Fonts$SFBOLD$SFBOLD_28.SFBOLD_28));
      this.buttonList.add(new RoundedButton("Remove", 1, var3 + 159 + 3, var4 + 25 + 5, 158, 30, 15, Fonts$SFBOLD$SFBOLD_28.SFBOLD_28));
      this.buttonList.add(new RoundedButton("Direct Login", 2, var3, var4 + 25 + 5 + 30 + 6, 320, 30, 15, Fonts$SFBOLD$SFBOLD_28.SFBOLD_28));
      this.buttonList.add(new RoundedButton("Refresh", 3, var3, var4 + 25 + 5 + 72, 320, 30, 15, Fonts$SFBOLD$SFBOLD_28.SFBOLD_28));
      this.buttonList.add(new RoundedButton("Generate", 4, this.width - 150, 2, 75, 20, 8, Fonts$SFBOLD$SFBOLD_16.SFBOLD_16));
      this.buttonList.add(new RoundedButton("Buy Alts", 10, this.width - 70, 2, 55, 20, 8, Fonts$SFBOLD$SFBOLD_16.SFBOLD_16));
      this.buttonList.add(this.sortButton = new RoundedButton(this.sortType.getCriteria(), 5, this.width - 550, 2, 100, 20, 8, Fonts$SFBOLD$SFBOLD_16.SFBOLD_16));
   }

   public void drawScreen(int var1, int var2, float var3) {
      int[] var4 = CI.b();
      AltRepositoryGUI var10000 = this;

      try {
         if(var10000.A) {
            GlStateManager.disableCull();
            this.N.useShader(this.width * 2, this.height * 2, (float)var1, (float)var2, (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
            GL11.glBegin(7);
            GL11.glVertex2f(-1.0F, -1.0F);
            GL11.glVertex2f(-1.0F, 1.0F);
            GL11.glVertex2f(1.0F, 1.0F);
            GL11.glVertex2f(1.0F, -1.0F);
            GL11.glEnd();
            GL20.glUseProgram(0);
         }

         Gui.drawRect(0, 0, 9999, 9999, (new Color(50, 50, 50)).getRGB());
         int var5 = this.alts.size();
         float var6 = this.sliderHeight / (float)this.alts.size();
         if(this.dragging) {
            int var7 = MathHelper.clamp_int(var2 - 25, 0, (int)this.sliderHeight - 25);
            int var8 = (int)((float)var7 / this.sliderHeight * (float)this.alts.size());
            this.setScrolledAndUpdate(var8);
         }

         drawRect(0, 0, this.width, this.height, (new Color(0, 0, 0, 100)).getRGB());
         Fonts$SFBOLD$SFBOLD_28.SFBOLD_28.drawString("N  O  V  O  L  I  N  E", 15.0F, 5.0F, -1);
         Fonts$SFBOLD$SFBOLD_28.SFBOLD_28.drawString("N", 15.0F, 5.0F, ((HUD)this.novoline.getModuleManager().getModule(HUD.class)).getHUDColor());
         this.apiKeyField.drawTextBox();
         this.searchField.drawTextBox();
         RenderUtils.drawRoundedRect(3.0F, 25.0F, 9.0F, this.sliderHeight, 8.0F, SCROLL_BAR_EMPTY_COLOR);
         RenderUtils.drawRoundedRect(3.0F, 25.0F + Math.min(var6 * (float)this.scrolled, this.sliderHeight - Math.min(var6 * (float)this.visibleAltsCount, this.sliderHeight)), 9.0F, Math.min(var6 * (float)this.visibleAltsCount, this.sliderHeight), 8.0F, SCROLL_BAR_SELECTED_COLOR);
         this.groupPlayerBox.drawGroup(this.mc, var1, var2);
         if(!this.alts.isEmpty()) {
            if(var5 > this.visibleAltsCount) {
               this.scrollWithWheel(var5);
            }

            int var10 = 0;
            if(var10 < this.visibleAlts.size()) {
               Alt var13 = (Alt)this.visibleAlts.get(var10);
               var13.drawAlt(this.altWidth, (int)(25.0F + (float)var10 * 39.0F), var1, var2);
               ++var10;
            }

            Alt var12 = this.getSelectedAlt();
            var12.drawEntity(var1, var2);
         }

         super.drawScreen(var1, var2, var3);
      } catch (Throwable var9) {
         this.logger.warn("scrolled: " + this.scrolled, var9);
      }

   }

   private void setScrolledAndUpdate(int var1) {
      this.setScrolled(var1);
      this.updateVisibleAlts();
   }

   private void setScrolled(int var1) {
      this.scrolled = MathHelper.clamp_int(var1, 0, Math.max(0, this.alts.size() - this.visibleAltsCount));
   }

   private void updateVisibleAlts() {
      int[] var1 = CI.b();
      if(this.alts.size() - this.scrolled < this.visibleAltsCount) {
         this.setScrolled(this.alts.size() - this.visibleAltsCount);
      }

      int var2 = this.alts.size();
      this.visibleAlts = this.alts.subList(MathHelper.clamp_int(this.scrolled, 0, var2), MathHelper.clamp_int(this.scrolled + this.visibleAltsCount, 0, var2));
   }

   private void scrollWithWheel(int var1) {
      CI.b();
      int var3 = Mouse.getDWheel();
      if(var3 != 0) {
         if(var3 < 0) {
            int var4 = this.scrolled + 1;
         }

         int var5 = this.scrolled - 1;
         if(var5 >= 0 && var5 <= var1 - this.visibleAltsCount) {
            this.setScrolledAndUpdate(var5);
         }

      }
   }

   private int getVisibleAltsCount() {
      float var1 = (float)(this.height - 25) / 39.0F;
      return MathHelper.floor_float(var1);
   }

   public String readApiKey() throws IOException {
      CI.b();
      Path var2 = this.novoline.getDataFolder().resolve("Data.txt");
      if(Files.notExists(var2, new LinkOption[0])) {
         return null;
      } else {
         List var3 = Files.readAllLines(var2);
         return !var3.isEmpty()?(this.tokenContent = (String)var3.get(0)):null;
      }
   }

   public Alt getRandomAlt() {
      CI.b();
      List var2 = (List)this.alts.stream().filter(AltRepositoryGUI::lambda$getRandomAlt$9).collect(Collectors.toList());
      if(var2.isEmpty()) {
         return null;
      } else {
         Alt var3 = (Alt)var2.get(var2.size() == 1?0:ThreadLocalRandom.current().nextInt(0, var2.size() - 1));
         var3.select();
         return var3;
      }
   }

   public Alt getSelectedAlt() {
      return (Alt)this.alts.stream().filter(Alt::isSelected).findAny().orElse((Object)null);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.saveAlts();
      this.tokenContent = this.apiKeyField.getText();
      AltRepositoryGUI var10000 = this;

      try {
         Files.write(var10000.novoline.getDataFolder().resolve("Data.txt"), this.tokenContent.getBytes(StandardCharsets.UTF_8), new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
      } catch (Throwable var2) {
         this.logger.error("Unable to reach clients folder", var2);
      }

   }

   protected void mouseReleased(int var1, int var2, int var3) {
      this.dragging = false;
      super.mouseReleased(var1, var2, var3);
   }

   protected void keyTyped(char param1, int param2) throws IOException {
      // $FF: Couldn't be decompiled
   }

   void selectAlt(Alt var1, Alt var2, Integer var3) {
      int[] var4 = CI.b();
      if(var3 != null || (var3 = Integer.valueOf(this.alts.indexOf(var2))).intValue() != -1) {
         if(var1 != null) {
            var1.setSelectedProperty(false);
         }

         var2.setSelectedProperty(true);
         if(var3.intValue() < this.scrolled) {
            this.setScrolledAndUpdate(var3.intValue());
         }

         if(var3.intValue() >= this.scrolled + this.visibleAltsCount) {
            this.setScrolledAndUpdate(var3.intValue() - this.visibleAltsCount + 1);
         }

      }
   }

   private void refreshAlts() {
      this.loadAlts();
      this.setScrolledAndUpdate(0);
   }

   private void pasteAltsFromClipboard() {
      int[] var1 = CI.b();

      try {
         Transferable var2 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
         if(var2 != null) {
            if(var2.isDataFlavorSupported(DataFlavor.stringFlavor)) {
               Stream var3 = Arrays.stream(((String)var2.getTransferData(DataFlavor.stringFlavor)).split("\n"));
            }

            if(var2.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
               Stream var5 = ((List)var2.getTransferData(DataFlavor.javaFileListFlavor)).stream().map(AltRepositoryGUI::lambda$pasteAltsFromClipboard$10).filter(Objects::nonNull).flatMap(AltRepositoryGUI::lambda$pasteAltsFromClipboard$11);
            }

         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }
   }

   private void removeCurrentAlt() {
      CI.b();
      Object var2 = null;
      Iterator var3 = this.alts.iterator();
      if(var3.hasNext()) {
         Alt var4 = (Alt)var3.next();
         if(var4.isSelected()) {
            this.removeAlt(var4);
            if(var2 != null) {
               ((Alt)var2).setSelectedProperty(true);
            }

            return;
         }
      }

      this.saveAlts();
   }

   public boolean hasAlt(AltCredential var1) {
      return this.alts.getUnfiltered().stream().anyMatch(AltRepositoryGUI::lambda$hasAlt$17);
   }

   public Alt addAlt(AltCredential var1) {
      int[] var2 = CI.b();
      if(!this.hasAlt(var1)) {
         if(var1 instanceof AlteningAltCredential) {
            AlteningAltCredential var4 = (AlteningAltCredential)var1;
            AccountDetails var5 = var4.getDetails();
            new Alt(var1, new FakeEntityPlayer(new GameProfile(UUID.randomUUID(), var4.getName()), (ResourceLocation)null), HypixelProfileFactory.hypixelProfile(var5.getHypixelRank(), Math.max(1, var5.getHypixelLevel())), this, Long.valueOf(0L), "Default", 1.0D, false);
         }

         String var6 = var1.getLogin();
         String var7 = GuiAddAlt.isEmail(var6)?"<Unknown Name>":var6;
         Alt var3 = new Alt(var1, new FakeEntityPlayer(new GameProfile(UUID.randomUUID(), var7), (ResourceLocation)null), HypixelProfile.empty(), this, Long.valueOf(0L), "Default", 1.0D, false);
         this.alts.add(var3);
         this.updateVisibleAlts();
         var3.select();
         return var3;
      } else {
         Novoline.getInstance().getNotificationManager().pop("Account is already added!", NotificationType.ERROR);
         return null;
      }
   }

   public void removeAlt(Alt var1) {
      int[] var2 = CI.b();
      if(this.alts.remove(var1)) {
         this.updateVisibleAlts();
      }

   }

   public void loadAlts() {
      CI.b();
      this.alts.clear();

      try {
         NBTTagCompound var2 = CompressedStreamTools.read(new File(Novoline.getInstance().getPathString(), "alts.novo"));
         if(var2 == null) {
            return;
         }

         NBTTagList var3 = var2.getTagList("alts", 10);
         int var4 = 0;
         if(var4 < var3.tagCount()) {
            AltRepositoryGUI var10000 = this;
            NBTTagList var10001 = var3;
            int var10002 = var4;

            label67: {
               Alt var5;
               try {
                  var5 = Alt.fromNBT(var10000, var10001.getCompoundTagAt(var10002));
               } catch (Throwable var7) {
                  this.logger.error("Failed to parse account: " + var3.getCompoundTagAt(var4).toString(), var7);
                  break label67;
               }

               this.alts.add(var5);
            }

            ++var4;
         }
      } catch (Exception var8) {
         this.logger.error("Couldn\'t load alt list", var8);
      }

      this.updateVisibleAlts();
   }

   public void saveAlts() {
      int[] var1 = CI.b();

      try {
         NBTTagList var2 = new NBTTagList();
         Iterator var3 = this.alts.getUnfiltered().iterator();
         if(var3.hasNext()) {
            Alt var4 = (Alt)var3.next();
            var2.appendTag(var4.asNBTCompound());
         }

         NBTTagCompound var6 = new NBTTagCompound();
         var6.setTag("alts", var2);
         CompressedStreamTools.safeWrite(var6, new File(Novoline.getInstance().getPathString(), "alts.novo"));
      } catch (Exception var5) {
         this.logger.error("Couldn\'t save alt list", var5);
      }

   }

   public List getAlts() {
      return (List)this.alts.getUnfiltered();
   }

   public TClient getTClient() {
      return this.tclient;
   }

   public String getTokenContent() {
      return this.tokenContent;
   }

   public Alt getCurrentAlt() {
      return this.currentAlt;
   }

   public void setCurrentAlt(Alt var1) {
      this.currentAlt = var1;
   }

   public float getAltBoxAlphaStep() {
      return this.altBoxAlphaStep;
   }

   public float getAltBoxAnimationStep() {
      return this.altBoxAnimationStep;
   }

   public TokenField getApiKeyField() {
      return this.apiKeyField;
   }

   private static boolean lambda$hasAlt$17(AltCredential var0, Alt var1) {
      return var1.getCredential().getLogin().equals(var0.getLogin());
   }

   private void lambda$pasteAltsFromClipboard$16(String[] var1) {
      this.addAlt(new AltCredential(var1[0], var1[1]));
   }

   private static String lambda$pasteAltsFromClipboard$15(String[] var0) {
      return var0[0];
   }

   private static boolean lambda$pasteAltsFromClipboard$14(Set var0, String[] var1) {
      return var0.add(var1[0]);
   }

   private static boolean lambda$pasteAltsFromClipboard$13(String[] var0) {
      int[] var1 = CI.b();
      return !var0[0].trim().isEmpty() && !var0[1].trim().isEmpty();
   }

   private static String[] lambda$pasteAltsFromClipboard$12(String var0) {
      int[] var1 = CI.b();
      if(!var0.endsWith("@alt.com")) {
         int var2 = var0.indexOf(58);
         return var2 == -1?null:new String[]{var0.substring(0, var2), var0.substring(var2 + 1)};
      } else {
         return new String[]{var0, (new String(new char[ThreadLocalRandom.current().nextInt(7) + 1])).replace('\u0000', 'a')};
      }
   }

   private static Stream lambda$pasteAltsFromClipboard$11(Stream var0) {
      return var0;
   }

   private static Stream lambda$pasteAltsFromClipboard$10(File var0) {
      File var10000 = var0;

      try {
         return Files.lines(var10000.toPath());
      } catch (Exception var2) {
         return null;
      }
   }

   private static boolean lambda$getRandomAlt$9(Alt var0) {
      int[] var1 = CI.b();
      return var0.getUnbanDate() == 0L && !var0.isInvalid();
   }

   private static String lambda$initGui$8(Alt var0) {
      return "Hypixel Rank: " + var0.getRank();
   }

   private static String lambda$initGui$7(Alt var0) {
      return "Hypixel Level: " + DECIMAL_FORMAT.format(var0.getNetworkLevel());
   }

   private static String lambda$initGui$6(Alt var0) {
      return "In-game name: " + var0.getPlayer().getName();
   }

   private void lambda$actionPerformed$5() {
      this.novoline.generateAlteningAlt().whenCompleteAsync(this::lambda$null$4);
   }

   private void lambda$null$4(Account var1, Throwable var2) {
      int[] var3 = CI.b();
      if(var2 != null) {
         Novoline.getLogger().warn("An error occurred while generating an account!", var2);
         if(var2 instanceof TheAlteningException) {
            Novoline.getInstance().getNotificationManager().pop("Invalid TheAltening token!", NotificationType.ERROR);
         }

         Novoline.getLogger().warn("An unexpected error occurred while generating an alt!", var2);
         Novoline.getInstance().getNotificationManager().pop("Unexpected error occurred!", NotificationType.ERROR);
      } else {
         Alt var4 = this.addAlt(new AlteningAltCredential(var1.getToken(), var1.getUsername(), var1.getInfo()));
         Novoline.getInstance().getNotificationManager().pop("Added alt! " + var4.toString(), NotificationType.SUCCESS);
      }
   }

   private void lambda$actionPerformed$3(GuiAddAlt var1, AltCredential var2) {
      var1.getGroupAltInfo().updateStatus(EnumChatFormatting.GREEN + "Logging in...");
      (new AltLoginThread(var2, new AltRepositoryGUI$1(this, var2, var1))).run();
   }

   private void lambda$actionPerformed$2(GuiAddAlt var1, AltCredential var2) {
      this.addAlt(var2);
      this.saveAlts();
      this.mc.displayGuiScreen(this);
   }

   private Comparator lambda$new$1() {
      return this.sortType.getComparator();
   }

   private Alt lambda$new$0(Alt var1) {
      int[] var2 = CI.b();
      if(this.searchField != null && !StringUtils.isBlank(this.searchField.getText())) {
         if(var1 == null) {
            return null;
         } else {
            if(var1.getPlayer() != null && var1.getPlayer().getName() != null) {
               String var3 = var1.getPlayer().getName();
            }

            if(var1.getCredential() != null) {
               String var4 = var1.getCredential().getLogin();
            }

            return null;
         }
      } else {
         return var1;
      }
   }

   static Minecraft access$000(AltRepositoryGUI var0) {
      return var0.mc;
   }

   static {
      DECIMAL_FORMAT.setGroupingSize(3);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
