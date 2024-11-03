package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
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
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import tv.twitch.chat.ChatUserInfo;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.BlurUtils;
import xyz.cucumber.base.utils.render.Particle;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback {
   private long initTime = System.currentTimeMillis();
   private List<Particle> particles = new ArrayList<>();
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<String> PROTOCOLS = Sets.newHashSet(new String[]{"http", "https"});
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
   private float startTime;

   public GuiScreen() {
      this.initTime = System.currentTimeMillis();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      for (int i = 0; i < this.buttonList.size(); i++) {
         this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY);
      }

      for (int j = 0; j < this.labelList.size(); j++) {
         this.labelList.get(j).drawLabel(this.mc, mouseX, mouseY);
      }
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1) {
         this.mc.displayGuiScreen(null);
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
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
         } catch (Exception var2) {
         }
      }
   }

   protected void renderToolTip(ItemStack stack, int x, int y) {
      List<String> list = stack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

      for (int i = 0; i < list.size(); i++) {
         if (i == 0) {
            list.set(i, stack.getRarity().rarityColor + list.get(i));
         } else {
            list.set(i, EnumChatFormatting.GRAY + list.get(i));
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

         for (String s : textLines) {
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

         zLevel = 300.0F;
         this.itemRender.zLevel = 300.0F;
         int l = -267386864;
         drawGradientRect((double)(l1 - 3), (double)(i2 - 4), (double)(l1 + i + 3), (double)(i2 - 3), l, l);
         drawGradientRect((double)(l1 - 3), (double)(i2 + k + 3), (double)(l1 + i + 3), (double)(i2 + k + 4), l, l);
         drawGradientRect((double)(l1 - 3), (double)(i2 - 3), (double)(l1 + i + 3), (double)(i2 + k + 3), l, l);
         drawGradientRect((double)(l1 - 4), (double)(i2 - 3), (double)(l1 - 3), (double)(i2 + k + 3), l, l);
         drawGradientRect((double)(l1 + i + 3), (double)(i2 - 3), (double)(l1 + i + 4), (double)(i2 + k + 3), l, l);
         int i1 = 1347420415;
         int j1 = (i1 & 16711422) >> 1 | i1 & 0xFF000000;
         drawGradientRect((double)(l1 - 3), (double)(i2 - 3 + 1), (double)(l1 - 3 + 1), (double)(i2 + k + 3 - 1), i1, j1);
         drawGradientRect((double)(l1 + i + 2), (double)(i2 - 3 + 1), (double)(l1 + i + 3), (double)(i2 + k + 3 - 1), i1, j1);
         drawGradientRect((double)(l1 - 3), (double)(i2 - 3), (double)(l1 + i + 3), (double)(i2 - 3 + 1), i1, i1);
         drawGradientRect((double)(l1 - 3), (double)(i2 + k + 2), (double)(l1 + i + 3), (double)(i2 + k + 3), j1, j1);

         for (int k1 = 0; k1 < textLines.size(); k1++) {
            String s1 = textLines.get(k1);
            this.fontRendererObj.drawStringWithShadow(s1, (double)((float)l1), (double)((float)i2), -1);
            if (k1 == 0) {
               i2 += 2;
            }

            i2 += 10;
         }

         zLevel = 0.0F;
         this.itemRender.zLevel = 0.0F;
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
         RenderHelper.enableStandardItemLighting();
         GlStateManager.enableRescaleNormal();
      }
   }

   protected void handleComponentHover(IChatComponent component, int x, int y) {
      if (component != null && component.getChatStyle().getChatHoverEvent() != null) {
         HoverEvent hoverevent = component.getChatStyle().getChatHoverEvent();
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
               this.renderToolTip(itemstack, x, y);
            } else {
               this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", x, y);
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
                     this.drawHoveringText(list1, x, y);
                  } else {
                     this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
                  }
               } catch (NBTException var10) {
                  this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
               }
            }
         } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
            this.drawHoveringText(NEWLINE_SPLITTER.splitToList(hoverevent.getValue().getFormattedText()), x, y);
         } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
            StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
            if (statbase != null) {
               IChatComponent ichatcomponent = statbase.getStatName();
               IChatComponent ichatcomponent1 = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"));
               ichatcomponent1.getChatStyle().setItalic(true);
               String s1 = statbase instanceof Achievement ? ((Achievement)statbase).getDescription() : null;
               List<String> list = Lists.newArrayList(new String[]{ichatcomponent.getFormattedText(), ichatcomponent1.getFormattedText()});
               if (s1 != null) {
                  list.addAll(this.fontRendererObj.listFormattedStringToWidth(s1, 150));
               }

               this.drawHoveringText(list, x, y);
            } else {
               this.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", x, y);
            }
         }

         GlStateManager.disableLighting();
      }
   }

   protected void setText(String newChatText, boolean shouldOverwrite) {
   }

   protected boolean handleComponentClick(IChatComponent component) {
      if (component == null) {
         return false;
      } else {
         ClickEvent clickevent = component.getChatStyle().getChatClickEvent();
         if (isShiftKeyDown()) {
            if (component.getChatStyle().getInsertion() != null) {
               this.setText(component.getChatStyle().getInsertion(), false);
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
                  LOGGER.error("Can't open url for " + clickevent, var5);
               }
            } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI uri1 = new File(clickevent.getValue()).toURI();
               this.openWebLink(uri1);
            } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.setText(clickevent.getValue(), true);
            } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.sendChatMessage(clickevent.getValue(), false);
            } else if (clickevent.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
               ChatUserInfo chatuserinfo = this.mc.getTwitchStream().func_152926_a(clickevent.getValue());
               if (chatuserinfo != null) {
                  this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.getTwitchStream(), chatuserinfo));
               } else {
                  LOGGER.error("Tried to handle twitch user but couldn't find them!");
               }
            } else {
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
         for (int i = 0; i < this.buttonList.size(); i++) {
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

   public void setGuiSize(int w, int h) {
      this.width = w;
      this.height = h;
   }

   public void initGui() {
      this.startTime = (float)System.nanoTime();
   }

   public void handleInput() throws IOException {
      if (Mouse.isCreated()) {
         while (Mouse.next()) {
            this.handleMouseInput();
         }
      }

      if (Keyboard.isCreated()) {
         while (Keyboard.next()) {
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
      if (Keyboard.getEventKeyState()) {
         this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
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
         drawGradientRect(0.0, 0.0, (double)this.width, (double)this.height, -1072689136, -804253680);
         BlurUtils.renderBlur(10.0F);
      } else {
         this.drawBackground(0);
      }
   }

   public void drawBackground(int tint) {
      RenderUtils.drawOtherBackground(0.0, 0.0, (double)this.width, (double)this.height, ((float)System.nanoTime() - this.startTime) / 1.0E9F);
      if (this.particles.size() < 200) {
         int needed = 200 - this.particles.size();

         for (int i = 0; i < needed; i++) {
            this.particles
               .add(
                  new Particle(
                     (double)RandomUtils.nextInt(0, this.width),
                     (double)RandomUtils.nextInt(0, this.height),
                     (double)(RandomUtils.nextInt(2, 4) / 2),
                     2,
                     -1,
                     (float)RandomUtils.nextInt(0, 360),
                     (float)RandomUtils.nextInt(1000, 3000)
                  )
               );
         }
      }

      Iterator<Particle> iterator = this.particles.iterator();

      while (iterator.hasNext()) {
         boolean b = iterator.next().draw();
         if (b) {
            iterator.remove();
         }
      }

      for (Particle p : this.particles) {
         double diffX = p.getX() - (double)Mouse.getEventX();
         double diffY = p.getY() - (double)Mouse.getEventY();
         double dist = Math.sqrt(diffX * diffX + diffY * diffY);

         for (Particle p2 : this.particles) {
            if (p2 != p) {
               double difX = p.getX() - p2.getX();
               double difY = p.getY() - p2.getY();
               double dit = Math.sqrt(difX * difX + difY * difY);
               if (dit < 30.0) {
                  RenderUtils.drawLine(p.getX(), p.getY(), p2.getX(), p2.getY(), 100663295, 0.5F);
               }
            }
         }
      }
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

   private void openWebLink(URI url) {
      try {
         Class<?> oclass = Class.forName("java.awt.Desktop");
         Object object = oclass.getMethod("getDesktop").invoke(null);
         oclass.getMethod("browse", URI.class).invoke(object, url);
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

   public static boolean isKeyComboCtrlX(int keyID) {
      return keyID == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlV(int keyID) {
      return keyID == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlC(int keyID) {
      return keyID == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public static boolean isKeyComboCtrlA(int keyID) {
      return keyID == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown();
   }

   public void onResize(Minecraft mcIn, int w, int h) {
      this.setWorldAndResolution(mcIn, w, h);
   }
}
