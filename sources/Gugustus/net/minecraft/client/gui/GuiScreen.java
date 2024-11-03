package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.augustus.Augustus;
import net.augustus.utils.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.EntityList;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
   private static final Logger LOGGER = new Logger();
   private static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");
   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
   protected Minecraft mc;
   protected RenderItem itemRender;
   public int width;
   public int height;
   protected List<GuiButton> buttonList = Lists.newArrayList();
   protected List<GuiLabel> labelList = Lists.newArrayList();
   public boolean allowUserInput;
   protected FontRenderer fontRendererObj;
   private GuiButton selectedButton;
   private int eventButton;
   private long lastMouseEvent;
   private int touchValue;
   private URI clickedLinkURI;

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      for(int i = 0; i < this.buttonList.size(); ++i) {
         this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY);
      }

      for(int j = 0; j < this.labelList.size(); ++j) {
         this.labelList.get(j).drawLabel(this.mc, mouseX, mouseY);
      }
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }
   }

   public static String getClipboardString() {
      try {
         Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
         if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String)transferable.getTransferData(DataFlavor.stringFlavor);
         }
      } catch (Exception var1) {
      }

      return "";
   }

   public static void setClipboardString(String copyText) {
      if (!StringUtils.isEmpty(copyText)) {
         try {
            StringSelection stringselection = new StringSelection(copyText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, (ClipboardOwner)null);
         } catch (Exception var2) {
         }
      }
   }

   protected void renderToolTip(ItemStack stack, int x, int y) {
      List<String> list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

      for(int i = 0; i < list.size(); ++i) {
         if (i == 0) {
            list.set(i, stack.getRarity().rarityColor + (String)list.get(i));
         } else {
            list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
         }
      }

      this.drawHoveringText(list, x, y);
   }

   protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
      this.drawHoveringText(Arrays.asList(tabName), mouseX, mouseY);
   }

   protected void drawHoveringText(List<String> textLines, int x, int y) {
      if (!textLines.isEmpty()) {
         GlStateManager.disableRescaleNormal();
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         int i = 0;

         for(String s : textLines) {
            int j = this.fontRendererObj.getStringWidth(s);
            if (j > i) {
               i = j;
            }
         }

         int l1 = x + 12;
         int i2 = y - 12;
         int k = 8;
         if (textLines.size() > 1) {
            k += 2 + (textLines.size() - 1) * 10;
         }

         if (l1 + i > this.width) {
            l1 -= 28 + i;
         }

         if (i2 + k + 6 > this.height) {
            i2 = this.height - k - 6;
         }

         this.zLevel = 300.0F;
         this.itemRender.zLevel = 300.0F;
         int l = -267386864;
         this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
         this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
         this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
         this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
         this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
         int i1 = 1347420415;
         int j1 = (i1 & 16711422) >> 1 | i1 & 0xFF000000;
         this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
         this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
         this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
         this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);

         for(int k1 = 0; k1 < textLines.size(); ++k1) {
            String s1 = textLines.get(k1);
            this.fontRendererObj.drawStringWithShadow(s1, (float)l1, (float)i2, -1);
            if (k1 == 0) {
               i2 += 2;
            }

            i2 += 10;
         }

         this.zLevel = 0.0F;
         this.itemRender.zLevel = 0.0F;
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.enableRescaleNormal();
      }
   }

   protected void handleComponentHover(IChatComponent p_175272_1_, int p_175272_2_, int p_175272_3_) {
      if (p_175272_1_ != null && p_175272_1_.getChatStyle().getChatHoverEvent() != null) {
         HoverEvent hoverevent = p_175272_1_.getChatStyle().getChatHoverEvent();
         if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
            ItemStack itemstack = null;

            try {
               NBTBase nbtbase = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
               if (nbtbase instanceof NBTTagCompound) {
                  itemstack = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbtbase);
               }
            } catch (NBTException var11) {
            }

            if (itemstack != null) {
               this.renderToolTip(itemstack, p_175272_2_, p_175272_3_);
            } else {
               this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", p_175272_2_, p_175272_3_);
            }
         } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
            if (this.mc.gameSettings.advancedItemTooltips) {
               try {
                  NBTBase nbtbase1 = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
                  if (nbtbase1 instanceof NBTTagCompound) {
                     List<String> list1 = Lists.newArrayList();
                     NBTTagCompound nbttagcompound = (NBTTagCompound)nbtbase1;
                     list1.add(nbttagcompound.getString("name"));
                     if (nbttagcompound.hasKey("type", 8)) {
                        String s = nbttagcompound.getString("type");
                        list1.add("Type: " + s + " (" + EntityList.getIDFromString(s) + ")");
                     }

                     list1.add(nbttagcompound.getString("id"));
                     this.drawHoveringText(list1, p_175272_2_, p_175272_3_);
                  } else {
                     this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
                  }
               } catch (NBTException var10) {
                  this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
               }
            }
         } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
            this.drawHoveringText(NEWLINE_SPLITTER.splitToList(hoverevent.getValue().getFormattedText()), p_175272_2_, p_175272_3_);
         } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
            StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
            if (statbase != null) {
               IChatComponent ichatcomponent = statbase.getStatName();
               IChatComponent ichatcomponent1 = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"));
               ichatcomponent1.getChatStyle().setItalic(true);
               String s1 = statbase instanceof Achievement ? ((Achievement)statbase).getDescription() : null;
               List<String> list = Lists.newArrayList(ichatcomponent.getFormattedText(), ichatcomponent1.getFormattedText());
               if (s1 != null) {
                  list.addAll(this.fontRendererObj.listFormattedStringToWidth(s1, 150));
               }

               this.drawHoveringText(list, p_175272_2_, p_175272_3_);
            } else {
               this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", p_175272_2_, p_175272_3_);
            }
         }

         GlStateManager.disableLighting();
      }
   }

   protected void setText(String newChatText, boolean shouldOverwrite) {
   }

   protected boolean handleComponentClick(IChatComponent p_175276_1_) {
      if (p_175276_1_ == null) {
         return false;
      } else {
         ClickEvent clickevent = p_175276_1_.getChatStyle().getChatClickEvent();
         if (isShiftKeyDown()) {
            if (p_175276_1_.getChatStyle().getInsertion() != null) {
               this.setText(p_175276_1_.getChatStyle().getInsertion(), false);
            }
         } else if (clickevent != null) {
            if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.mc.gameSettings.chatLinks) {
                  return false;
               }

               try {
                  URI uri = new URI(clickevent.getValue());
                  String s = uri.getScheme();
                  if (s == null) {
                     throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
                  }

                  if (!PROTOCOLS.contains(s.toLowerCase())) {
                     throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase());
                  }

                  if (this.mc.gameSettings.chatLinksPrompt) {
                     this.clickedLinkURI = uri;
                     this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
                  } else {
                     this.openWebLink(uri);
                  }
               } catch (URISyntaxException var5) {
                  LOGGER.error("Can't open url for " + clickevent, (Throwable)var5);
               }
            } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI uri1 = new File(clickevent.getValue()).toURI();
               this.openWebLink(uri1);
            } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.setText(clickevent.getValue(), true);
            } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.sendChatMessage(clickevent.getValue(), false);
            } else  {
               LOGGER.error("Don't know how to handle " + clickevent);
            }

            return true;
         }

         return false;
      }
   }

   public void sendChatMessage(String msg) {
      this.sendChatMessage(msg, true);
   }

   public void sendChatMessage(String msg, boolean addToChat) {
      if (addToChat) {
         this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
      }

      this.mc.thePlayer.sendChatMessage(msg);
   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      if (mouseButton == 0) {
         for(int i = 0; i < this.buttonList.size(); ++i) {
            GuiButton guibutton = this.buttonList.get(i);
            if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
               this.selectedButton = guibutton;
               guibutton.playPressSound(this.mc.getSoundHandler());
               this.actionPerformed(guibutton);
            }
         }
      }
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      if (this.selectedButton != null && state == 0) {
         this.selectedButton.mouseReleased(mouseX, mouseY);
         this.selectedButton = null;
      }
   }

   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
   }

   protected void actionPerformed(GuiButton button) throws IOException {
   }

   public void setWorldAndResolution(Minecraft mc, int width, int height) {
      this.mc = mc;
      this.itemRender = mc.getRenderItem();
      this.fontRendererObj = mc.fontRendererObj;
      this.width = width;
      this.height = height;
      this.buttonList.clear();
      this.initGui();
   }

   public void initGui() {
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

   public void handleMouseInput() throws IOException {
      int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
      int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
      int k = Mouse.getEventButton();
      if (Mouse.getEventButtonState()) {
         if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
            return;
         }

         this.eventButton = k;
         this.lastMouseEvent = Minecraft.getSystemTime();
         this.mouseClicked(i, j, this.eventButton);
      } else if (k != -1) {
         if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
            return;
         }

         this.eventButton = -1;
         this.mouseReleased(i, j, k);
      } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
         long l = Minecraft.getSystemTime() - this.lastMouseEvent;
         this.mouseClickMove(i, j, this.eventButton, l);
      }
   }

   public void handleKeyboardInput() throws IOException {
	   char echar = Keyboard.getEventCharacter();
	   int key = Keyboard.getEventKey();
      if (Keyboard.getEventKeyState() || echar >= ' ' && key == 0) {
         this.keyTyped(echar, key);
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

   public void drawWorldBackground(int tint) {
      if (this.mc.theWorld != null) {
         this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
      } else {
         this.drawBackground(tint);
      }
   }

   public void drawBackground(int tint) {
      ScaledResolution sr = new ScaledResolution(mc);
      Augustus.getInstance().getBackgroundShaderUtil().getCurrentShader().useBackGroundShader(Augustus.getInstance().getShaderSpeed());
//      Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(25, 25, 25).getRGB());
   }

   public boolean doesGuiPauseGame() {
      return true;
   }

   @Override
   public void confirmClicked(boolean result, int id) {
      if (id == 31102009) {
         if (result) {
            this.openWebLink(this.clickedLinkURI);
         }

         this.clickedLinkURI = null;
         this.mc.displayGuiScreen(this);
      }
   }

   private void openWebLink(URI p_175282_1_) {
      try {
         Class<?> oclass = Class.forName("java.awt.Desktop");
         Object object = oclass.getMethod("getDesktop").invoke(null);
         oclass.getMethod("browse", URI.class).invoke(object, p_175282_1_);
      } catch (Throwable var4) {
         LOGGER.error("Couldn't open link", var4);
      }
   }

   public static boolean isCtrlKeyDown() {
      return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
   }

   public static boolean isShiftKeyDown() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public static boolean isAltKeyDown() {
      return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
   }

   public static boolean isKeyComboCtrlX(int p_175277_0_) {
      return p_175277_0_ == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlV(int p_175279_0_) {
      return p_175279_0_ == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlC(int p_175280_0_) {
      return p_175280_0_ == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlA(int p_175278_0_) {
      return p_175278_0_ == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public void onResize(Minecraft mcIn, int p_175273_2_, int p_175273_3_) {
      this.setWorldAndResolution(mcIn, p_175273_2_, p_175273_3_);
   }

   public int getEventButton() {
      return this.eventButton;
   }

   public void setEventButton(int eventButton) {
      this.eventButton = eventButton;
   }
}
