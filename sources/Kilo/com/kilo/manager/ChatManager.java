package com.kilo.manager;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import tv.twitch.chat.ChatUserInfo;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kilo.render.Draw;
import com.kilo.ui.UIChat;
import com.kilo.ui.UIHandler;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.slotlist.part.ChatLine;
import com.kilo.util.Util;

public class ChatManager {
	
	private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Set PROTOCOLS = Sets.newHashSet(new String[] {"http", "https"});
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
	
	public static float chatWidth = 320;
	public static float chatHeight = 180;
	
	public static List<ChatLine> chatLines = new CopyOnWriteArrayList<ChatLine>();
	public static List<String> userHistory = new CopyOnWriteArrayList<String>(); 
	
	public static void addChatLine(final IChatComponent m) {
		chatLines.add(0, new ChatLine(m));
	}

	public static List<ChatLine> getList() {
		return chatLines;
	}
	
	public static void addChatLine(ChatLine s) {
		chatLines.add(s);
	}
	
	public static void addChatLine(int index, ChatLine s) {
		chatLines.add(index, s);
	}
	
	public static void removeChatLine(ChatLine s) {
		chatLines.remove(s);
	}
	
	public static void removeChatLine(int index) {
		chatLines.remove(chatLines.get(index));
	}
	
	public static ChatLine getChatLine(int index) {
		if (chatLines.size() == 0 || index >= chatLines.size()) {
			return null;
		}
		return chatLines.get(index);
	}
	
	public static ChatLine getChatLine(String m) {
		for(ChatLine s : chatLines) {
			if (s.unformatted.equalsIgnoreCase(m)) {
				return s;
			}
		}
		return null;
	}
	
	public static int getIndex(ChatLine s) {
		return chatLines.indexOf(s);
	}
	
	public static int getSize() {
		return getList().size();
	}
	
	public static void handleComponentClick(IChatComponent icc) {
		if (icc == null || mc.currentScreen == null)
        {
            return;
        }
        else
        {
            ClickEvent var2 = icc.getChatStyle().getChatClickEvent();

            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            {
                if (icc.getChatStyle().getInsertion() != null)
                {
                	if (UIHandler.currentUI instanceof UIChat) {
                		((TextBox)UIHandler.currentUI.inters.get(0)).text+= icc.getChatStyle().getInsertion();
                		((TextBox)UIHandler.currentUI.inters.get(0)).cursorPos = ((TextBox)UIHandler.currentUI.inters.get(0)).text.length();
                		((TextBox)UIHandler.currentUI.inters.get(0)).startSelect = ((TextBox)UIHandler.currentUI.inters.get(0)).cursorPos;
                	}
                }
            }
            else if (var2 != null)
            {
                URI var3;

                if (var2.getAction() == ClickEvent.Action.OPEN_URL)
                {
                    if (!mc.gameSettings.chatLinks)
                    {
                        return;
                    }

                    try
                    {
                        var3 = new URI(var2.getValue());

                        if (!PROTOCOLS.contains(var3.getScheme().toLowerCase()))
                        {
                            throw new URISyntaxException(var2.getValue(), "Unsupported protocol: " + var3.getScheme().toLowerCase());
                        }

                        if (mc.gameSettings.chatLinksPrompt)
                        {
                            mc.currentScreen.clickedLinkURI = var3;
                            mc.displayGuiScreen(new GuiConfirmOpenLink(mc.currentScreen, var2.getValue(), 31102009, false));
                        }
                        else
                        {
                            Util.openWeb(var3.getPath());
                        }
                    }
                    catch (URISyntaxException var4)
                    {
                        LOGGER.error("Can\'t open url for " + var2, var4);
                    }
                }
                else if (var2.getAction() == ClickEvent.Action.OPEN_FILE)
                {
                    var3 = (new File(var2.getValue())).toURI();
                    Util.openWeb(var3.getPath());
                }
                else if (var2.getAction() == ClickEvent.Action.SUGGEST_COMMAND)
                {
                    //this.setText(var2.getValue(), true);
                	if (UIHandler.currentUI instanceof UIChat) {
                		((TextBox)UIHandler.currentUI.inters.get(0)).text = var2.getValue();
                		((TextBox)UIHandler.currentUI.inters.get(0)).cursorPos = ((TextBox)UIHandler.currentUI.inters.get(0)).text.length();
                		((TextBox)UIHandler.currentUI.inters.get(0)).startSelect = ((TextBox)UIHandler.currentUI.inters.get(0)).cursorPos;
                	}
                }
                else if (var2.getAction() == ClickEvent.Action.RUN_COMMAND)
                {
                    mc.thePlayer.sendChatMessage(var2.getValue());
                }
                else if (var2.getAction() == ClickEvent.Action.TWITCH_USER_INFO)
                {
                    ChatUserInfo var5 = mc.getTwitchStream().func_152926_a(var2.getValue());

                    if (var5 != null)
                    {
                        mc.displayGuiScreen(new GuiTwitchUserMode(mc.getTwitchStream(), var5));
                    }
                    else
                    {
                        LOGGER.error("Tried to handle twitch user but couldn\'t find them!");
                    }
                }
                else
                {
                    LOGGER.error("Don\'t know how to handle " + var2);
                }

                return;
            }

            return;
        }
	}
	
	public static void handleComponentHover(IChatComponent icc, int x, int y)
    {
		x/= 2;
		y/= 2;
		GlStateManager.pushMatrix();
		GlStateManager.scale(2, 2, 2);
		if (mc.currentScreen == null) {
			GlStateManager.popMatrix();
			return;
		}
		mc.getTextureManager().bindTexture(Gui.icons);
		
        if (icc != null && icc.getChatStyle().getChatHoverEvent() != null)
        {
            HoverEvent var4 = icc.getChatStyle().getChatHoverEvent();

            if (var4.getAction() == HoverEvent.Action.SHOW_ITEM)
            {
                ItemStack var5 = null;

                try
                {
                    NBTTagCompound var6 = JsonToNBT.getTagFromJson(var4.getValue().getUnformattedText());

                    if (var6 instanceof NBTTagCompound)
                    {
                        var5 = ItemStack.loadItemStackFromNBT(var6);
                    }
                }
                catch (NBTException var11)
                {
                    ;
                }

                if (var5 != null)
                {
                    mc.currentScreen.renderToolTip(var5, x, y);
                }
                else
                {
                	mc.currentScreen.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Item!", x, y);
                }
            }
            else
            {
                String var8;

                if (var4.getAction() == HoverEvent.Action.SHOW_ENTITY)
                {
                    if (mc.gameSettings.advancedItemTooltips)
                    {
                        try
                        {
                            NBTTagCompound var12 = JsonToNBT.getTagFromJson(var4.getValue().getUnformattedText());

                            if (var12 instanceof NBTTagCompound)
                            {
                                ArrayList var14 = Lists.newArrayList();
                                NBTTagCompound var7 = var12;
                                var14.add(var7.getString("name"));

                                if (var7.hasKey("type", 8))
                                {
                                    var8 = var7.getString("type");
                                    var14.add("Type: " + var8 + " (" + EntityList.getIDFromString(var8) + ")");
                                }

                                var14.add(var7.getString("id"));
                                mc.currentScreen.drawHoveringText(var14, x, y);
                            }
                            else
                            {
                            	mc.currentScreen.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
                            }
                        }
                        catch (NBTException var10)
                        {
                        	mc.currentScreen.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid Entity!", x, y);
                        }
                    }
                }
                else if (var4.getAction() == HoverEvent.Action.SHOW_TEXT)
                {
                	mc.currentScreen.drawHoveringText(NEWLINE_SPLITTER.splitToList(var4.getValue().getFormattedText()), x, y);
                }
                else if (var4.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT)
                {
                    StatBase var13 = StatList.getOneShotStat(var4.getValue().getUnformattedText());

                    if (var13 != null)
                    {
                        IChatComponent var15 = var13.getStatName();
                        ChatComponentTranslation var16 = new ChatComponentTranslation("stats.tooltip.type." + (var13.isAchievement() ? "achievement" : "statistic"), new Object[0]);
                        var16.getChatStyle().setItalic(Boolean.valueOf(true));
                        var8 = var13 instanceof Achievement ? ((Achievement)var13).getDescription() : null;
                        ArrayList var9 = Lists.newArrayList(new String[] {var15.getFormattedText(), var16.getFormattedText()});

                        if (var8 != null)
                        {
                            var9.addAll(mc.fontRendererObj.listFormattedStringToWidth(var8, 150));
                        }

                        mc.currentScreen.drawHoveringText(var9, x, y);
                    }
                    else
                    {
                    	mc.currentScreen.drawCreativeTabHoveringText(EnumChatFormatting.RED + "Invalid statistic/achievement!", x, y);
                    }
                }
            }

            GlStateManager.disableLighting();
        }
        GlStateManager.popMatrix();
    }
}
