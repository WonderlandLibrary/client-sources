package de.labystudio.gui;

import com.google.common.collect.Lists;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.listener.ChatListener;
import de.labystudio.utils.DrawUtils;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewModChat
{
    public static final Logger logger = LogManager.getLogger();
    public static DrawUtils draw;
    public static final List sentMessages = Lists.newArrayList();
    public static final List chatLines = Lists.newArrayList();
    public static final List field_146253_i = Lists.newArrayList();
    public static int scrollPos;
    public static boolean isScrolled;
    public static final String __OBFID = "CL_00000669";

    public static void drawChat(int p_146230_1_)
    {
        if (Minecraft.getMinecraft().gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN)
        {
            if (!getChatOpen())
            {
                resetScroll();
            }

            if (ConfigManager.settings.extraChat.booleanValue() || ConfigManager.settings.chatFilter.booleanValue() || ConfigManager.settings.chatPositionRight)
            {
                int i = getLineCount();
                boolean flag = false;
                int j = 0;
                int k = field_146253_i.size();
                float f = Minecraft.getMinecraft().gameSettings.chatOpacity * 0.9F + 0.1F;

                if (k > 0)
                {
                    if (getChatOpen())
                    {
                        flag = true;
                    }

                    float f1 = getChatScale();
                    int l = MathHelper.ceiling_float_int((float)getModChatWidth() / f1 + 2.0F);
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(2.0F, 20.0F, 0.0F);
                    GlStateManager.scale(f1, f1, 1.0F);
                    int l1 = 0;

                    for (int i1 = 0; i1 + scrollPos < field_146253_i.size() && i1 < i; ++i1)
                    {
                        ChatLine chatline = (ChatLine)field_146253_i.get(i1 + scrollPos);

                        if (chatline != null)
                        {
                            int j1 = p_146230_1_ - chatline.getUpdatedCounter();

                            if (j1 < 200 || flag)
                            {
                                double d0 = (double)j1 / 200.0D;
                                d0 = 1.0D - d0;
                                d0 = d0 * 10.0D;
                                d0 = MathHelper.clamp_double(d0, 0.0D, 1.0D);
                                d0 = d0 * d0;
                                int k1 = (int)(255.0D * d0);

                                if (flag)
                                {
                                    k1 = 255;
                                }

                                k1 = (int)((float)k1 * f);
                                ++j;

                                if (k1 > 3)
                                {
                                    byte b0 = 0;
                                    int j2 = -l1 * 9 - 30;
                                    ++l1;
                                    String s = chatline.getChatComponent().getFormattedText();
                                    s = ChatListener.replaceMessage(chatline.getChatComponent().getFormattedText(), chatline.getChatComponent().getUnformattedText());
                                    l = MathHelper.ceiling_float_int((float)getChatWidth() / f1 + 2.0F);
                                    DrawUtils drawutils1 = LabyMod.getInstance().draw;
                                    DrawUtils.drawRect(l + 5, j2 - 9, 3000, j2, k1 / 2 << 24);
                                    GlStateManager.enableBlend();
                                    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(s, (float)(l + 8), (float)(j2 - 8), 16777215 + (k1 << 24));
                                    GlStateManager.disableAlpha();
                                    GlStateManager.disableBlend();
                                }
                            }
                        }
                    }

                    if (flag)
                    {
                        int k2 = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
                        GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                        int j3 = k * k2 + k;
                        int l2 = j * k2 + j;
                        int k3 = scrollPos * l2 / k + 30;
                        int i2 = l2 * l2 / j3;

                        if (j3 != l2)
                        {
                            int i3 = k3 > 0 ? 170 : 96;
                            int l3 = isScrolled ? 13382451 : 3355562;
                            DrawUtils drawutils = LabyMod.getInstance().draw;
                            DrawUtils.drawRect(MathHelper.ceiling_float_int((float)getChatWidth() / f1 + 9.0F), -k3, MathHelper.ceiling_float_int((float)getChatWidth() / f1 + 10.0F), -k3 - i2, l3 + (i3 << 24));
                            drawutils = LabyMod.getInstance().draw;
                            DrawUtils.drawRect(MathHelper.ceiling_float_int((float)getChatWidth() / f1 + 10.0F), -k3, MathHelper.ceiling_float_int((float)getChatWidth() / f1 + 9.0F), -k3 - i2, 13421772 + (i3 << 24));
                        }
                    }

                    GlStateManager.popMatrix();
                }
            }
        }
    }

    public static void clearChatMessages()
    {
        field_146253_i.clear();
        chatLines.clear();
        sentMessages.clear();
    }

    public static void printChatMessage(IChatComponent p_146227_1_)
    {
        printChatMessageWithOptionalDeletion(p_146227_1_, 0);
    }

    public static void printChatMessageWithOptionalDeletion(IChatComponent p_146234_1_, int p_146234_2_)
    {
        if (ChatListener.allowedToPrint(p_146234_1_.getFormattedText(), p_146234_1_.getUnformattedText()))
        {
            setChatLine(p_146234_1_, p_146234_2_, Minecraft.getMinecraft().ingameGUI.getUpdateCounter(), false);
            logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
        }
    }

    public static void setChatLine(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_)
    {
        if (p_146237_2_ != 0)
        {
            deleteChatLine(p_146237_2_);
        }

        int i = MathHelper.floor_float((float)getModChatWidth() / getChatScale());
        List list = GuiUtilRenderComponents.func_178908_a(p_146237_1_, i, Minecraft.getMinecraft().fontRendererObj, false, false);
        boolean flag = getChatOpen();

        for (Object ichatcomponent0 : list)
        {
            IChatComponent ichatcomponent = (IChatComponent) ichatcomponent0;

            if (flag && scrollPos > 0)
            {
                isScrolled = true;
                scroll(1);
            }

            field_146253_i.add(0, new ChatLine(p_146237_3_, ichatcomponent, p_146237_2_));
        }

        while (field_146253_i.size() > 100)
        {
            field_146253_i.remove(field_146253_i.size() - 1);
        }

        if (!p_146237_4_)
        {
            chatLines.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));

            while (chatLines.size() > 100)
            {
                chatLines.remove(chatLines.size() - 1);
            }
        }
    }

    public static void refreshChat()
    {
        ChatListener.init = Minecraft.getSystemTime();
        field_146253_i.clear();
        resetScroll();

        for (int i = chatLines.size() - 1; i >= 0; --i)
        {
            ChatLine chatline = (ChatLine)chatLines.get(i);
            setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }
    }

    public List getSentMessages()
    {
        return sentMessages;
    }

    public void addToSentMessages(String p_146239_1_)
    {
        if (sentMessages.isEmpty() || !((String)sentMessages.get(sentMessages.size() - 1)).equals(p_146239_1_))
        {
            sentMessages.add(p_146239_1_);
        }
    }

    public static void resetScroll()
    {
        scrollPos = 0;
        isScrolled = false;
    }

    public static void scroll(int p_146229_1_)
    {
        DrawUtils drawutils = LabyMod.getInstance().draw;

        if (DrawUtils.getMouseX() >= getChatWidth())
        {
            scrollPos += p_146229_1_;
            int i = field_146253_i.size();

            if (scrollPos > i - getLineCount())
            {
                scrollPos = i - getLineCount();
            }

            if (scrollPos <= 0)
            {
                scrollPos = 0;
                isScrolled = false;
            }
        }
    }

    public static IChatComponent getChatComponent(int p_146236_1_, int p_146236_2_)
    {
        if (!getChatOpen())
        {
            return null;
        }
        else
        {
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaleFactor();
            float f = getChatScale();
            int j = p_146236_1_ / i - 3;
            int k = p_146236_2_ / i - 27;
            j = MathHelper.floor_float((float)j / f);
            k = MathHelper.floor_float((float)k / f);
            j = j - (MathHelper.floor_float((float)getChatWidth() / getChatScale()) + 10);
            k = k - 30;

            if (j >= 0 && k >= 0)
            {
                int l = Math.min(getLineCount(), field_146253_i.size());

                if (j <= MathHelper.floor_float((float)getChatWidth() / getChatScale()) && k < Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * l + l)
                {
                    int i1 = k / Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + scrollPos;

                    if (i1 >= 0 && i1 < field_146253_i.size())
                    {
                        ChatLine chatline = (ChatLine)field_146253_i.get(i1);
                        int j1 = 0;

                        for (IChatComponent ichatcomponent : chatline.getChatComponent())
                        {
                            if (ichatcomponent instanceof ChatComponentText)
                            {
                                j1 += Minecraft.getMinecraft().fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false));

                                if (j1 > j)
                                {
                                    return ichatcomponent;
                                }
                            }
                        }
                    }

                    return null;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }

    public static boolean getChatOpen()
    {
        return Minecraft.getMinecraft().currentScreen instanceof GuiChat || Minecraft.getMinecraft().currentScreen instanceof GuiSymbolSelector;
    }

    public static void deleteChatLine(int p_146242_1_)
    {
        Iterator iterator = field_146253_i.iterator();

        while (iterator.hasNext())
        {
            ChatLine chatline = (ChatLine)iterator.next();

            if (chatline.getChatLineID() == p_146242_1_)
            {
                iterator.remove();
            }
        }

        iterator = chatLines.iterator();

        while (iterator.hasNext())
        {
            ChatLine chatline1 = (ChatLine)iterator.next();

            if (chatline1.getChatLineID() == p_146242_1_)
            {
                iterator.remove();
                break;
            }
        }
    }

    public static int getChatWidth()
    {
        return calculateChatboxWidth(Minecraft.getMinecraft().gameSettings.chatWidth);
    }

    public static int getModChatWidth()
    {
        return LabyMod.getInstance().draw.getWidth() - calculateChatboxWidth(Minecraft.getMinecraft().gameSettings.chatWidth) - 10;
    }

    public static int getChatHeight()
    {
        return calculateChatboxHeight(getChatOpen() ? Minecraft.getMinecraft().gameSettings.chatHeightFocused : Minecraft.getMinecraft().gameSettings.chatHeightUnfocused);
    }

    public static float getChatScale()
    {
        return Minecraft.getMinecraft().gameSettings.chatScale;
    }

    public static int calculateChatboxWidth(float p_146233_0_)
    {
        short short1 = 320;
        byte b0 = 40;
        return MathHelper.floor_float(p_146233_0_ * (float)(short1 - b0) + (float)b0);
    }

    public static int calculateChatboxHeight(float p_146243_0_)
    {
        short short1 = 180;
        byte b0 = 20;
        return MathHelper.floor_float(p_146243_0_ * (float)(short1 - b0) + (float)b0);
    }

    public static int getLineCount()
    {
        return getChatHeight() / 9;
    }
}
