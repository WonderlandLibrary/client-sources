/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import me.eagler.Client;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.stream.GuiTwitchUserMode;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import tv.twitch.chat.ChatUserInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiScreen
/*     */   extends Gui
/*     */   implements GuiYesNoCallback
/*     */ {
/*  55 */   private static final Logger LOGGER = LogManager.getLogger();
/*  56 */   private static final Set<String> PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
/*  57 */   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
/*     */ 
/*     */   
/*     */   protected Minecraft mc;
/*     */ 
/*     */   
/*     */   protected RenderItem itemRender;
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */   
/*  72 */   protected List<GuiButton> buttonList = Lists.newArrayList();
/*  73 */   protected List<GuiLabel> labelList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public boolean allowUserInput;
/*     */ 
/*     */   
/*     */   protected FontRenderer fontRendererObj;
/*     */ 
/*     */   
/*     */   private GuiButton selectedButton;
/*     */ 
/*     */   
/*     */   private int eventButton;
/*     */ 
/*     */   
/*     */   private long lastMouseEvent;
/*     */   
/*     */   private int touchValue;
/*     */   
/*     */   private URI clickedLinkURI;
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  96 */     for (int i = 0; i < this.buttonList.size(); i++)
/*     */     {
/*  98 */       ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
/*     */     }
/*     */     
/* 101 */     for (int j = 0; j < this.labelList.size(); j++)
/*     */     {
/* 103 */       ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 113 */     if (keyCode == 1) {
/*     */       
/* 115 */       this.mc.displayGuiScreen(null);
/*     */       
/* 117 */       if (this.mc.currentScreen == null)
/*     */       {
/* 119 */         this.mc.setIngameFocus();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClipboardString() {
/*     */     try {
/* 131 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/*     */       
/* 133 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
/*     */       {
/* 135 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*     */       }
/*     */     }
/* 138 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClipboardString(String copyText) {
/* 151 */     if (!StringUtils.isEmpty(copyText)) {
/*     */       
/*     */       try {
/*     */         
/* 155 */         StringSelection stringselection = new StringSelection(copyText);
/* 156 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
/*     */       }
/* 158 */       catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 167 */     List<String> list = stack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*     */     
/* 169 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 171 */       if (i == 0) {
/*     */         
/* 173 */         list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*     */       }
/*     */       else {
/*     */         
/* 177 */         list.set(i, EnumChatFormatting.GRAY + (String)list.get(i));
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     drawHoveringText(list, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 190 */     drawHoveringText(Arrays.asList(new String[] { tabName }, ), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHoveringText(List<String> textLines, int x, int y) {
/* 198 */     if (!textLines.isEmpty()) {
/*     */       
/* 200 */       GlStateManager.disableRescaleNormal();
/* 201 */       RenderHelper.disableStandardItemLighting();
/* 202 */       GlStateManager.disableLighting();
/* 203 */       GlStateManager.disableDepth();
/* 204 */       int i = 0;
/*     */       
/* 206 */       for (String s : textLines) {
/*     */         
/* 208 */         int j = this.fontRendererObj.getStringWidth(s);
/*     */         
/* 210 */         if (j > i)
/*     */         {
/* 212 */           i = j;
/*     */         }
/*     */       } 
/*     */       
/* 216 */       int l1 = x + 12;
/* 217 */       int i2 = y - 12;
/* 218 */       int k = 8;
/*     */       
/* 220 */       if (textLines.size() > 1)
/*     */       {
/* 222 */         k += 2 + (textLines.size() - 1) * 10;
/*     */       }
/*     */       
/* 225 */       if (l1 + i > this.width)
/*     */       {
/* 227 */         l1 -= 28 + i;
/*     */       }
/*     */       
/* 230 */       if (i2 + k + 6 > this.height)
/*     */       {
/* 232 */         i2 = this.height - k - 6;
/*     */       }
/*     */       
/* 235 */       this.zLevel = 300.0F;
/* 236 */       this.itemRender.zLevel = 300.0F;
/* 237 */       int l = -267386864;
/* 238 */       drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
/* 239 */       drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
/* 240 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
/* 241 */       drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
/* 242 */       drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
/* 243 */       int i1 = 1347420415;
/* 244 */       int j1 = (i1 & 0xFEFEFE) >> 1 | i1 & 0xFF000000;
/* 245 */       drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
/* 246 */       drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
/* 247 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
/* 248 */       drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);
/*     */       
/* 250 */       for (int k1 = 0; k1 < textLines.size(); k1++) {
/*     */         
/* 252 */         String s1 = textLines.get(k1);
/* 253 */         this.fontRendererObj.drawStringWithShadow(s1, l1, i2, -1);
/*     */         
/* 255 */         if (k1 == 0)
/*     */         {
/* 257 */           i2 += 2;
/*     */         }
/*     */         
/* 260 */         i2 += 10;
/*     */       } 
/*     */       
/* 263 */       this.zLevel = 0.0F;
/* 264 */       this.itemRender.zLevel = 0.0F;
/* 265 */       GlStateManager.enableLighting();
/* 266 */       GlStateManager.enableDepth();
/* 267 */       RenderHelper.enableStandardItemLighting();
/* 268 */       GlStateManager.enableRescaleNormal();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleComponentHover(IChatComponent p_175272_1_, int p_175272_2_, int p_175272_3_) {
/* 277 */     if (p_175272_1_ != null && p_175272_1_.getChatStyle().getChatHoverEvent() != null) {
/*     */       
/* 279 */       HoverEvent hoverevent = p_175272_1_.getChatStyle().getChatHoverEvent();
/*     */       
/* 281 */       if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
/*     */         
/* 283 */         ItemStack itemstack = null;
/*     */ 
/*     */         
/*     */         try {
/* 287 */           NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */           
/* 289 */           if (nBTTagCompound instanceof NBTTagCompound)
/*     */           {
/* 291 */             itemstack = ItemStack.loadItemStackFromNBT(nBTTagCompound);
/*     */           }
/*     */         }
/* 294 */         catch (NBTException nBTException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 299 */         if (itemstack != null)
/*     */         {
/* 301 */           renderToolTip(itemstack, p_175272_2_, p_175272_3_);
/*     */         }
/*     */         else
/*     */         {
/* 305 */           drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", p_175272_2_, p_175272_3_);
/*     */         }
/*     */       
/* 308 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
/*     */         
/* 310 */         if (this.mc.gameSettings.advancedItemTooltips) {
/*     */           
/*     */           try {
/*     */             
/* 314 */             NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */             
/* 316 */             if (nBTTagCompound instanceof NBTTagCompound)
/*     */             {
/* 318 */               List<String> list1 = Lists.newArrayList();
/* 319 */               NBTTagCompound nbttagcompound = nBTTagCompound;
/* 320 */               list1.add(nbttagcompound.getString("name"));
/*     */               
/* 322 */               if (nbttagcompound.hasKey("type", 8)) {
/*     */                 
/* 324 */                 String s = nbttagcompound.getString("type");
/* 325 */                 list1.add("Type: " + s + " (" + EntityList.getIDFromString(s) + ")");
/*     */               } 
/*     */               
/* 328 */               list1.add(nbttagcompound.getString("id"));
/* 329 */               drawHoveringText(list1, p_175272_2_, p_175272_3_);
/*     */             }
/*     */             else
/*     */             {
/* 333 */               drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
/*     */             }
/*     */           
/* 336 */           } catch (NBTException var10) {
/*     */             
/* 338 */             drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
/*     */           }
/*     */         
/*     */         }
/* 342 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
/*     */         
/* 344 */         drawHoveringText(NEWLINE_SPLITTER.splitToList(hoverevent.getValue().getFormattedText()), p_175272_2_, p_175272_3_);
/*     */       }
/* 346 */       else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
/*     */         
/* 348 */         StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
/*     */         
/* 350 */         if (statbase != null) {
/*     */           
/* 352 */           IChatComponent ichatcomponent = statbase.getStatName();
/* 353 */           ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"), new Object[0]);
/* 354 */           chatComponentTranslation.getChatStyle().setItalic(Boolean.valueOf(true));
/* 355 */           String s1 = (statbase instanceof Achievement) ? ((Achievement)statbase).getDescription() : null;
/* 356 */           List<String> list = Lists.newArrayList((Object[])new String[] { ichatcomponent.getFormattedText(), chatComponentTranslation.getFormattedText() });
/*     */           
/* 358 */           if (s1 != null)
/*     */           {
/* 360 */             list.addAll(this.fontRendererObj.listFormattedStringToWidth(s1, 150));
/*     */           }
/*     */           
/* 363 */           drawHoveringText(list, p_175272_2_, p_175272_3_);
/*     */         }
/*     */         else {
/*     */           
/* 367 */           drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", p_175272_2_, p_175272_3_);
/*     */         } 
/*     */       } 
/*     */       
/* 371 */       GlStateManager.disableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleComponentClick(IChatComponent p_175276_1_) {
/* 387 */     if (p_175276_1_ == null)
/*     */     {
/* 389 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 393 */     ClickEvent clickevent = p_175276_1_.getChatStyle().getChatClickEvent();
/*     */     
/* 395 */     if (isShiftKeyDown()) {
/*     */       
/* 397 */       if (p_175276_1_.getChatStyle().getInsertion() != null)
/*     */       {
/* 399 */         setText(p_175276_1_.getChatStyle().getInsertion(), false);
/*     */       }
/*     */     }
/* 402 */     else if (clickevent != null) {
/*     */       
/* 404 */       if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
/*     */         
/* 406 */         if (!this.mc.gameSettings.chatLinks)
/*     */         {
/* 408 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/* 413 */           URI uri = new URI(clickevent.getValue());
/* 414 */           String s = uri.getScheme();
/*     */           
/* 416 */           if (s == null)
/*     */           {
/* 418 */             throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
/*     */           }
/*     */           
/* 421 */           if (!PROTOCOLS.contains(s.toLowerCase()))
/*     */           {
/* 423 */             throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase());
/*     */           }
/*     */           
/* 426 */           if (this.mc.gameSettings.chatLinksPrompt)
/*     */           {
/* 428 */             this.clickedLinkURI = uri;
/* 429 */             this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
/*     */           }
/*     */           else
/*     */           {
/* 433 */             openWebLink(uri);
/*     */           }
/*     */         
/* 436 */         } catch (URISyntaxException urisyntaxexception) {
/*     */           
/* 438 */           LOGGER.error("Can't open url for " + clickevent, urisyntaxexception);
/*     */         }
/*     */       
/* 441 */       } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
/*     */         
/* 443 */         URI uri1 = (new File(clickevent.getValue())).toURI();
/* 444 */         openWebLink(uri1);
/*     */       }
/* 446 */       else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
/*     */         
/* 448 */         setText(clickevent.getValue(), true);
/*     */       }
/* 450 */       else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
/*     */         
/* 452 */         sendChatMessage(clickevent.getValue(), false);
/*     */       }
/* 454 */       else if (clickevent.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
/*     */         
/* 456 */         ChatUserInfo chatuserinfo = this.mc.getTwitchStream().func_152926_a(clickevent.getValue());
/*     */         
/* 458 */         if (chatuserinfo != null)
/*     */         {
/* 460 */           this.mc.displayGuiScreen((GuiScreen)new GuiTwitchUserMode(this.mc.getTwitchStream(), chatuserinfo));
/*     */         }
/*     */         else
/*     */         {
/* 464 */           LOGGER.error("Tried to handle twitch user but couldn't find them!");
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 469 */         LOGGER.error("Don't know how to handle " + clickevent);
/*     */       } 
/*     */       
/* 472 */       return true;
/*     */     } 
/*     */     
/* 475 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String msg) {
/* 481 */     sendChatMessage(msg, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendChatMessage(String msg, boolean addToChat) {
/* 486 */     if (addToChat)
/*     */     {
/* 488 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*     */     }
/*     */     
/* 491 */     this.mc.thePlayer.sendChatMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 499 */     if (mouseButton == 0)
/*     */     {
/* 501 */       for (int i = 0; i < this.buttonList.size(); i++) {
/*     */         
/* 503 */         GuiButton guibutton = this.buttonList.get(i);
/*     */         
/* 505 */         if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/*     */           
/* 507 */           this.selectedButton = guibutton;
/* 508 */           guibutton.playPressSound(this.mc.getSoundHandler());
/* 509 */           actionPerformed(guibutton);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 520 */     if (this.selectedButton != null && state == 0) {
/*     */       
/* 522 */       this.selectedButton.mouseReleased(mouseX, mouseY);
/* 523 */       this.selectedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 548 */     this.mc = mc;
/* 549 */     this.itemRender = mc.getRenderItem();
/* 550 */     this.fontRendererObj = mc.fontRendererObj;
/* 551 */     this.width = width;
/* 552 */     this.height = height;
/* 553 */     this.buttonList.clear();
/* 554 */     initGui();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleInput() throws IOException {
/* 570 */     if (Mouse.isCreated())
/*     */     {
/* 572 */       while (Mouse.next())
/*     */       {
/* 574 */         handleMouseInput();
/*     */       }
/*     */     }
/*     */     
/* 578 */     if (Keyboard.isCreated())
/*     */     {
/* 580 */       while (Keyboard.next())
/*     */       {
/* 582 */         handleKeyboardInput();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 592 */     int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
/* 593 */     int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
/* 594 */     int k = Mouse.getEventButton();
/*     */     
/* 596 */     if (Mouse.getEventButtonState()) {
/*     */       
/* 598 */       if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 603 */       this.eventButton = k;
/* 604 */       this.lastMouseEvent = Minecraft.getSystemTime();
/* 605 */       mouseClicked(i, j, this.eventButton);
/*     */     }
/* 607 */     else if (k != -1) {
/*     */       
/* 609 */       if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 614 */       this.eventButton = -1;
/* 615 */       mouseReleased(i, j, k);
/*     */     }
/* 617 */     else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
/*     */       
/* 619 */       long l = Minecraft.getSystemTime() - this.lastMouseEvent;
/* 620 */       mouseClickMove(i, j, this.eventButton, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 629 */     if (Keyboard.getEventKeyState())
/*     */     {
/* 631 */       keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
/*     */     }
/*     */     
/* 634 */     this.mc.dispatchKeypresses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/* 659 */     drawWorldBackground(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/* 664 */     if (this.mc.theWorld != null) {
/*     */       
/* 666 */       drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 672 */       drawBackground();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int tint) {
/* 682 */     GlStateManager.disableLighting();
/* 683 */     GlStateManager.disableFog();
/* 684 */     Tessellator tessellator = Tessellator.getInstance();
/* 685 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 686 */     this.mc.getTextureManager().bindTexture(optionsBackground);
/* 687 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 688 */     float f = 32.0F;
/* 689 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 690 */     worldrenderer.pos(0.0D, this.height, 0.0D).tex(0.0D, (this.height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 691 */     worldrenderer.pos(this.width, this.height, 0.0D).tex((this.width / 32.0F), (this.height / 32.0F + tint)).color(64, 64, 64, 255).endVertex();
/* 692 */     worldrenderer.pos(this.width, 0.0D, 0.0D).tex((this.width / 32.0F), tint).color(64, 64, 64, 255).endVertex();
/* 693 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint).color(64, 64, 64, 255).endVertex();
/* 694 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 702 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 707 */     if (id == 31102009) {
/*     */       
/* 709 */       if (result)
/*     */       {
/* 711 */         openWebLink(this.clickedLinkURI);
/*     */       }
/*     */       
/* 714 */       this.clickedLinkURI = null;
/* 715 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void openWebLink(URI p_175282_1_) {
/*     */     try {
/* 723 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 724 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 725 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { p_175282_1_ });
/*     */     }
/* 727 */     catch (Throwable throwable) {
/*     */       
/* 729 */       LOGGER.error("Couldn't open link", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBackground(String location) {
/* 735 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*     */     
/* 737 */     Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(location));
/*     */     
/* 739 */     drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), sr.getScaledHeight(), 
/* 740 */         sr.getScaledWidth(), sr.getScaledHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBackground() {
/* 746 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*     */     
/* 748 */     if (Client.instance.getUtils().isTheme("Dark")) {
/*     */       
/* 750 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler/background.png"));
/*     */     }
/* 752 */     else if (Client.instance.getUtils().isTheme("Bright")) {
/*     */       
/* 754 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler/brightback.png"));
/*     */     } 
/*     */ 
/*     */     
/* 758 */     drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), sr.getScaledHeight(), 
/* 759 */         sr.getScaledWidth(), sr.getScaledHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCtrlKeyDown() {
/* 768 */     return Minecraft.isRunningOnMac ? (!(!Keyboard.isKeyDown(219) && !Keyboard.isKeyDown(220))) : (!(!Keyboard.isKeyDown(29) && !Keyboard.isKeyDown(157)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 776 */     return !(!Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAltKeyDown() {
/* 784 */     return !(!Keyboard.isKeyDown(56) && !Keyboard.isKeyDown(184));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlX(int p_175277_0_) {
/* 789 */     return (p_175277_0_ == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlV(int p_175279_0_) {
/* 794 */     return (p_175279_0_ == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlC(int p_175280_0_) {
/* 799 */     return (p_175280_0_ == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isKeyComboCtrlA(int p_175278_0_) {
/* 804 */     return (p_175278_0_ == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResize(Minecraft mcIn, int p_175273_2_, int p_175273_3_) {
/* 812 */     setWorldAndResolution(mcIn, p_175273_2_, p_175273_3_);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */