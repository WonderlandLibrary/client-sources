package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.af_;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent$Action;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import tv.twitch.chat.ChatUserInfo;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set t = Sets.newHashSet(new String[]{"http", "https"});
   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
   protected Minecraft mc;
   protected RenderItem itemRender;
   public int width;
   public int height;
   protected List buttonList = Lists.newArrayList();
   protected List labelList = Lists.newArrayList();
   public boolean allowUserInput = true;
   protected FontRenderer fontRendererObj;
   public GuiButton selectedButton;
   private int eventButton;
   private long lastMouseEvent;
   private int touchValue;
   private URI i;

   public void drawScreen(int var1, int var2, float var3) {
      for(GuiButton var5 : this.buttonList) {
         if(!var5.displayString.equalsIgnoreCase("Log In")) {
            var5.drawButton(this.mc, var1, var2);
         }
      }

      for(GuiLabel var7 : this.labelList) {
         var7.drawLabel(this.mc, var1, var2);
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(var2 == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
         if(this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }

   }

   public static String getClipboardString() {
      try {
         Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
         if(var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)var0.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var1) {
         ;
      }

      return "";
   }

   public static void setClipboardString(String var0) {
      if(!StringUtils.isEmpty(var0)) {
         try {
            StringSelection var1 = new StringSelection(var0);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, (ClipboardOwner)null);
         } catch (Exception var2) {
            ;
         }
      }

   }

   protected void renderToolTip(ItemStack var1, int var2, int var3) {
      List var4 = var1.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         var4.set(var5, var1.getRarity().rarityColor + (String)var4.get(var5));
      }

      this.drawHoveringText(var4, var2, var3);
   }

   protected void drawCreativeTabHoveringText(String var1, int var2, int var3) {
      this.drawHoveringText(Collections.singletonList(var1), var2, var3);
   }

   protected void drawHoveringText(List var1, int var2, int var3) {
      if(!var1.isEmpty()) {
         GlStateManager.disableRescaleNormal();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         int var4 = 0;

         for(String var6 : var1) {
            int var7 = this.fontRendererObj.d(var6);
            if(var7 > var4) {
               var4 = var7;
            }
         }

         int var13 = var2 + 12;
         int var14 = var3 - 12;
         int var16 = 8;
         if(var1.size() > 1) {
            var16 += 2 + (var1.size() - 1) * 10;
         }

         if(var13 + var4 > this.width) {
            var13 -= 28 + var4;
         }

         if(var14 + var16 + 6 > this.height) {
            var14 = this.height - var16 - 6;
         }

         this.zLevel = 300.0F;
         this.itemRender.zLevel = 300.0F;
         int var8 = -267386864;
         this.drawGradientRect((float)(var13 - 3), (float)(var14 - 4), (float)(var13 + var4 + 3), (float)(var14 - 3), var8, var8);
         this.drawGradientRect((float)(var13 - 3), (float)(var14 + var16 + 3), (float)(var13 + var4 + 3), (float)(var14 + var16 + 4), var8, var8);
         this.drawGradientRect((float)(var13 - 3), (float)(var14 - 3), (float)(var13 + var4 + 3), (float)(var14 + var16 + 3), var8, var8);
         this.drawGradientRect((float)(var13 - 4), (float)(var14 - 3), (float)(var13 - 3), (float)(var14 + var16 + 3), var8, var8);
         this.drawGradientRect((float)(var13 + var4 + 3), (float)(var14 - 3), (float)(var13 + var4 + 4), (float)(var14 + var16 + 3), var8, var8);
         int var9 = 1347420415;
         int var10 = (var9 & 16711422) >> 1 | var9 & -16777216;
         this.drawGradientRect((float)(var13 - 3), (float)(var14 - 3 + 1), (float)(var13 - 3 + 1), (float)(var14 + var16 + 3 - 1), var9, var10);
         this.drawGradientRect((float)(var13 + var4 + 2), (float)(var14 - 3 + 1), (float)(var13 + var4 + 3), (float)(var14 + var16 + 3 - 1), var9, var10);
         this.drawGradientRect((float)(var13 - 3), (float)(var14 - 3), (float)(var13 + var4 + 3), (float)(var14 - 3 + 1), var9, var9);
         this.drawGradientRect((float)(var13 - 3), (float)(var14 + var16 + 2), (float)(var13 + var4 + 3), (float)(var14 + var16 + 3), var10, var10);

         for(int var11 = 0; var11 < var1.size(); ++var11) {
            String var12 = (String)var1.get(var11);
            this.fontRendererObj.drawStringWithShadow(var12, (float)var13, (float)var14, -1);
            var14 = var14 + 2;
            var14 = var14 + 10;
         }

         this.zLevel = 0.0F;
         this.itemRender.zLevel = 0.0F;
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.enableRescaleNormal();
      }

   }

   protected void handleComponentHover(IChatComponent var1, int var2, int var3) {
      if(var1.getChatStyle().getChatHoverEvent() != null) {
         HoverEvent var4 = var1.getChatStyle().getChatHoverEvent();
         if(var4.getAction() == HoverEvent$Action.SHOW_ITEM) {
            ItemStack var5 = null;

            try {
               NBTTagCompound var6 = JsonToNBT.getTagFromJson(var4.getValue().getUnformattedText());
               var5 = ItemStack.loadItemStackFromNBT((NBTTagCompound)var6);
            } catch (NBTException var11) {
               ;
            }

            this.renderToolTip(var5, var2, var3);
         } else if(var4.getAction() == HoverEvent$Action.SHOW_ENTITY) {
            if(this.mc.gameSettings.advancedItemTooltips) {
               try {
                  NBTTagCompound var12 = JsonToNBT.getTagFromJson(var4.getValue().getUnformattedText());
                  ArrayList var14 = Lists.newArrayList();
                  NBTTagCompound var7 = (NBTTagCompound)var12;
                  var14.add(var7.getString("name"));
                  if(var7.hasKey("type", 8)) {
                     String var8 = var7.getString("type");
                     var14.add("Type: " + var8 + " (" + EntityList.getIDFromString(var8) + ")");
                  }

                  var14.add(var7.getString("id"));
                  this.drawHoveringText(var14, var2, var3);
               } catch (NBTException var10) {
                  this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", var2, var3);
               }
            }
         } else if(var4.getAction() == HoverEvent$Action.SHOW_TEXT) {
            this.drawHoveringText(NEWLINE_SPLITTER.splitToList(var4.getValue().getFormattedText()), var2, var3);
         } else if(var4.getAction() == HoverEvent$Action.SHOW_ACHIEVEMENT) {
            StatBase var13 = StatList.getOneShotStat(var4.getValue().getUnformattedText());
            IChatComponent var15 = var13.getStatName();
            ChatComponentTranslation var16 = new ChatComponentTranslation("stats.tooltip.type." + (var13.isAchievement()?"achievement":"statistic"), new Object[0]);
            var16.getChatStyle().setItalic(Boolean.valueOf(true));
            String var17 = var13 instanceof Achievement?((Achievement)var13).getDescription():null;
            ArrayList var9 = Lists.newArrayList(new String[]{var15.getFormattedText(), var16.getFormattedText()});
            var9.addAll(this.fontRendererObj.listFormattedStringToWidth(var17, 150));
            this.drawHoveringText(var9, var2, var3);
         }

         GlStateManager.disableLighting();
      }

   }

   protected void setText(String var1, boolean var2) {
   }

   protected boolean a(IChatComponent var1) {
      ClickEvent var2 = var1.getChatStyle().getChatClickEvent();
      if(isShiftKeyDown()) {
         if(var1.getChatStyle().getInsertion() != null) {
            this.setText(var1.getChatStyle().getInsertion(), false);
         }

         return false;
      } else {
         if(var2.getAction() == ClickEvent$Action.OPEN_URL) {
            if(!this.mc.gameSettings.chatLinks) {
               return false;
            }

            try {
               URI var3 = new URI(var2.getValue());
               String var4 = var3.getScheme();
               throw new URISyntaxException(var2.getValue(), "Missing protocol");
            } catch (URISyntaxException var5) {
               LOGGER.error("Can\'t open url for " + var2, var5);
            }
         } else if(var2.getAction() == ClickEvent$Action.OPEN_FILE) {
            URI var6 = (new File(var2.getValue())).toURI();
            this.openWebLink(var6);
         } else if(var2.getAction() == ClickEvent$Action.SUGGEST_COMMAND) {
            this.setText(var2.getValue(), true);
         } else if(var2.getAction() == ClickEvent$Action.RUN_COMMAND) {
            this.sendChatMessage(var2.getValue(), false);
         } else if(var2.getAction() == ClickEvent$Action.TWITCH_USER_INFO) {
            ChatUserInfo var7 = this.mc.getTwitchStream().func_152926_a(var2.getValue());
            this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.getTwitchStream(), var7));
         } else {
            LOGGER.error("Don\'t know how to handle " + var2);
         }

         return true;
      }
   }

   public void sendChatMessage(String var1) {
      this.sendChatMessage(var1, true);
   }

   public void sendChatMessage(String var1, boolean var2) {
      this.mc.ingameGUI.n().a(var1);
      this.mc.player.c(var1);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      for(int var4 = 0; var4 < this.buttonList.size(); ++var4) {
         GuiButton var5 = (GuiButton)this.buttonList.get(var4);
         if(var5.mousePressed(this.mc, var1, var2)) {
            this.selectedButton = var5;
            var5.playPressSound(this.mc.getSoundHandler());
            this.actionPerformed(var5);
         }
      }

   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if(this.selectedButton != null) {
         this.selectedButton.mouseReleased(var1, var2);
         this.selectedButton = null;
      }

   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
   }

   public void setWorldAndResolution(Minecraft var1, int var2, int var3) {
      this.mc = var1;
      this.itemRender = var1.getRenderItem();
      this.fontRendererObj = var1.fontRendererObj;
      this.width = var2;
      this.height = var3;
      this.buttonList.clear();
      this.initGui();
   }

   public void initGui() {
   }

   public void handleInput() throws IOException {
      if(Mouse.isCreated()) {
         while(Mouse.next()) {
            this.handleMouseInput();
         }
      }

      if(Keyboard.isCreated()) {
         while(Keyboard.next()) {
            this.handleKeyboardInput();
         }
      }

   }

   public void handleMouseInput() throws IOException {
      int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
      int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
      int var3 = Mouse.getEventButton();
      if(Mouse.getEventButtonState()) {
         if(this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
            return;
         }

         this.eventButton = var3;
         this.lastMouseEvent = Minecraft.getSystemTime();
         this.mouseClicked(var1, var2, this.eventButton);
      } else if(var3 != -1) {
         if(this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
            return;
         }

         this.eventButton = -1;
         this.mouseReleased(var1, var2, var3);
      } else if(this.eventButton != -1 && this.lastMouseEvent > 0L) {
         long var4 = Minecraft.getSystemTime() - this.lastMouseEvent;
         this.mouseClickMove(var1, var2, this.eventButton, var4);
      }

   }

   public void handleKeyboardInput() throws IOException {
      char var1 = Keyboard.getEventCharacter();
      int var2 = Keyboard.getEventKey();
      if(Keyboard.getEventKeyState() || Character.isDefined(var1)) {
         this.keyTyped(var1, var2);
      }

      this.mc.dispatchKeypresses();
   }

   public void updateScreen() {
   }

   public void onGuiClosed() {
   }

   public void drawDefaultBackground() {
      this.drawWorldBackground(0);
   }

   public void drawWorldBackground(int var1) {
      if(this.mc.world != null) {
         this.drawGradientRect(0.0F, 0.0F, (float)this.width, (float)this.height, -1072689136, -804253680);
      } else {
         this.drawBackground(var1);
      }

   }

   public void drawBackground(int var1) {
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(optionsBackground);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var3.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var3.pos(0.0D, (double)this.height, 0.0D).tex(0.0D, (double)((float)this.height / 32.0F + (float)var1)).color(64, 64, 64, 255).endVertex();
      var3.pos((double)this.width, (double)this.height, 0.0D).tex((double)((float)this.width / 32.0F), (double)((float)this.height / 32.0F + (float)var1)).color(64, 64, 64, 255).endVertex();
      var3.pos((double)this.width, 0.0D, 0.0D).tex((double)((float)this.width / 32.0F), (double)var1).color(64, 64, 64, 255).endVertex();
      var3.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double)var1).color(64, 64, 64, 255).endVertex();
      var2.draw();
   }

   public boolean doesGuiPauseGame() {
      return true;
   }

   public void confirmClicked(boolean var1, int var2) {
      if(var2 == 31102009) {
         this.openWebLink(this.i);
         this.i = null;
         this.mc.displayGuiScreen(this);
      }

   }

   private void openWebLink(URI var1) {
      String var10000 = "java.awt.Desktop";

      try {
         Class var2 = af_.a(var10000);
         Object var3 = af_.b(var2, "getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
         af_.b(var2, "browse", new Class[]{URI.class}).invoke(var3, new Object[]{var1});
      } catch (Throwable var4) {
         LOGGER.error("Couldn\'t open link", var4);
      }

   }

   public static boolean isCtrlKeyDown() {
      return Minecraft.isRunningOnMac?Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220):Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   public static boolean isShiftKeyDown() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public static boolean isAltKeyDown() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   public static boolean isKeyComboCtrlX(int var0) {
      return var0 == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlV(int var0) {
      return var0 == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlC(int var0) {
      return var0 == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlA(int var0) {
      return var0 == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public void onResize(Minecraft var1, int var2, int var3) {
      this.setWorldAndResolution(var1, var2, var3);
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}
