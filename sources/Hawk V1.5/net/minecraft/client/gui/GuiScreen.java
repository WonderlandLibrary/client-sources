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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
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
   private static final Logger field_175287_a = LogManager.getLogger();
   public int height;
   private GuiButton selectedButton;
   private static final Set field_175284_f = Sets.newHashSet(new String[]{"http", "https"});
   private static final String __OBFID = "CL_00000710";
   public int width;
   protected FontRenderer fontRendererObj;
   protected RenderItem itemRender;
   public int eventButton;
   public boolean allowUserInput;
   private URI field_175286_t;
   private int touchValue;
   private long lastMouseEvent;
   protected Minecraft mc;
   protected List buttonList = Lists.newArrayList();
   private static final Splitter field_175285_g = Splitter.on('\n');
   protected List labelList = Lists.newArrayList();

   public void func_175281_b(String var1, boolean var2) {
      if (var2) {
         this.mc.ingameGUI.getChatGUI().addToSentMessages(var1);
      }

      this.mc.thePlayer.sendChatMessage(var1);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }

   }

   public void updateScreen() {
   }

   public static void setClipboardString(String var0) {
      if (!StringUtils.isEmpty(var0)) {
         try {
            StringSelection var1 = new StringSelection(var0);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, (ClipboardOwner)null);
         } catch (Exception var2) {
         }
      }

   }

   public static boolean isCtrlKeyDown() {
      return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   public void drawWorldBackground(int var1) {
      if (this.mc.theWorld != null) {
         this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
      } else {
         this.drawBackground(var1);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         for(int var4 = 0; var4 < this.buttonList.size(); ++var4) {
            GuiButton var5 = (GuiButton)this.buttonList.get(var4);
            if (var5.mousePressed(this.mc, var1, var2)) {
               this.selectedButton = var5;
               var5.playPressSound(this.mc.getSoundHandler());
               this.actionPerformed(var5);
            }
         }
      }

   }

   protected void drawHoveringText(List var1, int var2, int var3) {
      if (!var1.isEmpty()) {
         GlStateManager.disableRescaleNormal();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         int var4 = 0;
         Iterator var5 = var1.iterator();

         int var7;
         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            var7 = this.fontRendererObj.getStringWidth(var6);
            if (var7 > var4) {
               var4 = var7;
            }
         }

         int var14 = var2 + 12;
         var7 = var3 - 12;
         int var8 = 8;
         if (var1.size() > 1) {
            var8 += 2 + (var1.size() - 1) * 10;
         }

         if (var14 + var4 > this.width) {
            var14 -= 28 + var4;
         }

         if (var7 + var8 + 6 > this.height) {
            var7 = this.height - var8 - 6;
         }

         this.zLevel = 300.0F;
         this.itemRender.zLevel = 300.0F;
         int var9 = -267386864;
         this.drawGradientRect(var14 - 3, var7 - 4, var14 + var4 + 3, var7 - 3, var9, var9);
         this.drawGradientRect(var14 - 3, var7 + var8 + 3, var14 + var4 + 3, var7 + var8 + 4, var9, var9);
         this.drawGradientRect(var14 - 3, var7 - 3, var14 + var4 + 3, var7 + var8 + 3, var9, var9);
         this.drawGradientRect(var14 - 4, var7 - 3, var14 - 3, var7 + var8 + 3, var9, var9);
         this.drawGradientRect(var14 + var4 + 3, var7 - 3, var14 + var4 + 4, var7 + var8 + 3, var9, var9);
         int var10 = 1347420415;
         int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
         this.drawGradientRect(var14 - 3, var7 - 3 + 1, var14 - 3 + 1, var7 + var8 + 3 - 1, var10, var11);
         this.drawGradientRect(var14 + var4 + 2, var7 - 3 + 1, var14 + var4 + 3, var7 + var8 + 3 - 1, var10, var11);
         this.drawGradientRect(var14 - 3, var7 - 3, var14 + var4 + 3, var7 - 3 + 1, var10, var10);
         this.drawGradientRect(var14 - 3, var7 + var8 + 2, var14 + var4 + 3, var7 + var8 + 3, var11, var11);

         for(int var12 = 0; var12 < var1.size(); ++var12) {
            String var13 = (String)var1.get(var12);
            this.fontRendererObj.drawStringWithShadow(var13, (double)((float)var14), (double)((float)var7), -1);
            if (var12 == 0) {
               var7 += 2;
            }

            var7 += 10;
         }

         this.zLevel = 0.0F;
         this.itemRender.zLevel = 0.0F;
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.enableRescaleNormal();
      }

   }

   protected void func_175272_a(IChatComponent var1, int var2, int var3) {
      if (var1 != null && var1.getChatStyle().getChatHoverEvent() != null) {
         HoverEvent var4 = var1.getChatStyle().getChatHoverEvent();
         NBTTagCompound var6;
         if (var4.getAction() == HoverEvent.Action.SHOW_ITEM) {
            ItemStack var5 = null;

            try {
               var6 = JsonToNBT.func_180713_a(var4.getValue().getUnformattedText());
               if (var6 instanceof NBTTagCompound) {
                  var5 = ItemStack.loadItemStackFromNBT(var6);
               }
            } catch (NBTException var11) {
            }

            if (var5 != null) {
               this.renderToolTip(var5, var2, var3);
            } else {
               this.drawCreativeTabHoveringText(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Invalid Item!")), var2, var3);
            }
         } else {
            String var12;
            if (var4.getAction() == HoverEvent.Action.SHOW_ENTITY) {
               if (this.mc.gameSettings.advancedItemTooltips) {
                  try {
                     var6 = JsonToNBT.func_180713_a(var4.getValue().getUnformattedText());
                     if (var6 instanceof NBTTagCompound) {
                        ArrayList var7 = Lists.newArrayList();
                        var7.add(var6.getString("name"));
                        if (var6.hasKey("type", 8)) {
                           var12 = var6.getString("type");
                           var7.add(String.valueOf((new StringBuilder("Type: ")).append(var12).append(" (").append(EntityList.func_180122_a(var12)).append(")")));
                        }

                        var7.add(var6.getString("id"));
                        this.drawHoveringText(var7, var2, var3);
                     } else {
                        this.drawCreativeTabHoveringText(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Invalid Entity!")), var2, var3);
                     }
                  } catch (NBTException var10) {
                     this.drawCreativeTabHoveringText(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Invalid Entity!")), var2, var3);
                  }
               }
            } else if (var4.getAction() == HoverEvent.Action.SHOW_TEXT) {
               this.drawHoveringText(field_175285_g.splitToList(var4.getValue().getFormattedText()), var2, var3);
            } else if (var4.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
               StatBase var13 = StatList.getOneShotStat(var4.getValue().getUnformattedText());
               if (var13 != null) {
                  IChatComponent var14 = var13.getStatName();
                  ChatComponentTranslation var8 = new ChatComponentTranslation(String.valueOf((new StringBuilder("stats.tooltip.type.")).append(var13.isAchievement() ? "achievement" : "statistic")), new Object[0]);
                  var8.getChatStyle().setItalic(true);
                  var12 = var13 instanceof Achievement ? ((Achievement)var13).getDescription() : null;
                  ArrayList var9 = Lists.newArrayList(new String[]{var14.getFormattedText(), var8.getFormattedText()});
                  if (var12 != null) {
                     var9.addAll(this.fontRendererObj.listFormattedStringToWidth(var12, 150));
                  }

                  this.drawHoveringText(var9, var2, var3);
               } else {
                  this.drawCreativeTabHoveringText(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Invalid statistic/achievement!")), var2, var3);
               }
            }
         }

         GlStateManager.disableLighting();
      }

   }

   public static boolean isShiftKeyDown() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public void handleInput() throws IOException {
      if (Mouse.isCreated()) {
         while(Mouse.next()) {
            this.handleMouseInput();
         }
      }

      if (Keyboard.isCreated()) {
         while(Keyboard.next()) {
            this.handleKeyboardInput();
         }
      }

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

   public void drawDefaultBackground() {
      this.drawWorldBackground(0);
   }

   public boolean doesGuiPauseGame() {
      return true;
   }

   protected void mouseClickMove(int var1, int var2, int var3, long var4) {
   }

   public void func_175273_b(Minecraft var1, int var2, int var3) {
      this.setWorldAndResolution(var1, var2, var3);
   }

   public void handleKeyboardInput() throws IOException {
      if (Keyboard.getEventKeyState()) {
         this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
      }

      this.mc.dispatchKeypresses();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
   }

   protected void drawCreativeTabHoveringText(String var1, int var2, int var3) {
      this.drawHoveringText(Arrays.asList(var1), var2, var3);
   }

   private void func_175282_a(URI var1) {
      try {
         Class var2 = Class.forName("java.awt.Desktop");
         Object var3 = var2.getMethod("getDesktop").invoke((Object)null);
         var2.getMethod("browse", URI.class).invoke(var3, var1);
      } catch (Throwable var4) {
         field_175287_a.error("Couldn't open link", var4);
      }

   }

   protected void renderToolTip(ItemStack var1, int var2, int var3) {
      List var4 = var1.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

      for(int var5 = 0; var5 < var4.size(); ++var5) {
         if (var5 == 0) {
            var4.set(var5, String.valueOf((new StringBuilder()).append(var1.getRarity().rarityColor).append((String)var4.get(var5))));
         } else {
            var4.set(var5, String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append((String)var4.get(var5))));
         }
      }

      this.drawHoveringText(var4, var2, var3);
   }

   public void handleMouseInput() throws IOException {
      int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
      int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
      int var3 = Mouse.getEventButton();
      if (Mouse.getEventButtonState()) {
         if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
            return;
         }

         this.eventButton = var3;
         this.lastMouseEvent = Minecraft.getSystemTime();
         this.mouseClicked(var1, var2, this.eventButton);
      } else if (var3 != -1) {
         if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
            return;
         }

         this.eventButton = -1;
         this.mouseReleased(var1, var2, var3);
      } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
         long var4 = Minecraft.getSystemTime() - this.lastMouseEvent;
         this.mouseClickMove(var1, var2, this.eventButton, var4);
      }

   }

   public static String getClipboardString() {
      try {
         Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
         if (var0 != null && var0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)var0.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var1) {
      }

      return "";
   }

   public void confirmClicked(boolean var1, int var2) {
      if (var2 == 31102009) {
         if (var1) {
            this.func_175282_a(this.field_175286_t);
         }

         this.field_175286_t = null;
         this.mc.displayGuiScreen(this);
      }

   }

   protected void func_175274_a(String var1, boolean var2) {
   }

   protected boolean func_175276_a(IChatComponent var1) {
      if (var1 == null) {
         return false;
      } else {
         ClickEvent var2 = var1.getChatStyle().getChatClickEvent();
         if (isShiftKeyDown()) {
            if (var1.getChatStyle().getInsertion() != null) {
               this.func_175274_a(var1.getChatStyle().getInsertion(), false);
            }
         } else if (var2 != null) {
            URI var3;
            if (var2.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.mc.gameSettings.chatLinks) {
                  return false;
               }

               try {
                  var3 = new URI(var2.getValue());
                  if (!field_175284_f.contains(var3.getScheme().toLowerCase())) {
                     throw new URISyntaxException(var2.getValue(), String.valueOf((new StringBuilder("Unsupported protocol: ")).append(var3.getScheme().toLowerCase())));
                  }

                  if (this.mc.gameSettings.chatLinksPrompt) {
                     this.field_175286_t = var3;
                     this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, var2.getValue(), 31102009, false));
                  } else {
                     this.func_175282_a(var3);
                  }
               } catch (URISyntaxException var5) {
                  field_175287_a.error(String.valueOf((new StringBuilder("Can't open url for ")).append(var2)), var5);
               }
            } else if (var2.getAction() == ClickEvent.Action.OPEN_FILE) {
               var3 = (new File(var2.getValue())).toURI();
               this.func_175282_a(var3);
            } else if (var2.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.func_175274_a(var2.getValue(), true);
            } else if (var2.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.func_175281_b(var2.getValue(), false);
            } else if (var2.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
               ChatUserInfo var4 = this.mc.getTwitchStream().func_152926_a(var2.getValue());
               if (var4 != null) {
                  this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.getTwitchStream(), var4));
               } else {
                  field_175287_a.error("Tried to handle twitch user but couldn't find them!");
               }
            } else {
               field_175287_a.error(String.valueOf((new StringBuilder("Don't know how to handle ")).append(var2)));
            }

            return true;
         }

         return false;
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      int var4;
      for(var4 = 0; var4 < this.buttonList.size(); ++var4) {
         ((GuiButton)this.buttonList.get(var4)).drawButton(this.mc, var1, var2);
      }

      for(var4 = 0; var4 < this.labelList.size(); ++var4) {
         ((GuiLabel)this.labelList.get(var4)).drawLabel(this.mc, var1, var2);
      }

   }

   public static boolean func_175279_e(int var0) {
      return var0 == 47 && isCtrlKeyDown();
   }

   public static boolean func_175280_f(int var0) {
      return var0 == 46 && isCtrlKeyDown();
   }

   public void initGui() {
   }

   public void onGuiClosed() {
   }

   public void func_175275_f(String var1) {
      this.func_175281_b(var1, true);
   }

   public static boolean func_175283_s() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if (this.selectedButton != null && var3 == 0) {
         this.selectedButton.mouseReleased(var1, var2);
         this.selectedButton = null;
      }

   }

   public void drawBackground(int var1) {
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(optionsBackground);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float var4 = 32.0F;
      var3.startDrawingQuads();
      var3.func_178991_c(4210752);
      var3.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / var4 + (float)var1));
      var3.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / var4), (double)((float)this.height / var4 + (float)var1));
      var3.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / var4), (double)var1);
      var3.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double)var1);
      var2.draw();
   }

   public static boolean func_175277_d(int var0) {
      return var0 == 45 && isCtrlKeyDown();
   }

   public static boolean func_175278_g(int var0) {
      return var0 == 30 && isCtrlKeyDown();
   }
}
