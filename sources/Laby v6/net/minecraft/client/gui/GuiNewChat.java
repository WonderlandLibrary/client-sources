package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import de.labystudio.gui.GuiNewModChat;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.listener.ChatListener;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.FilterLoader;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.module.modules.render.TransparentChat;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiNewChat extends Gui
{
    private static final Logger logger = LogManager.getLogger();
    private final Minecraft mc;
    private DrawUtils draw;

    /** A list of messages previously sent through the chat GUI */
    private final List sentMessages = Lists.newArrayList();

    /** Chat lines to be displayed in the chat box */
    private final List chatLines = Lists.newArrayList();
    private final List field_146253_i = Lists.newArrayList();
    private int scrollPos;
    private boolean isScrolled;
    private static final String __OBFID = "CL_00000669";
    int detectChange = 0;

    public GuiNewChat(Minecraft mcIn)
    {
        this.mc = mcIn;
        this.draw = LabyMod.getInstance().draw;
    }

    public void drawChat(int p_146230_1_)
    {
        this.draw = LabyMod.getInstance().draw;

        if (this.detectChange != this.draw.getWidth())
        {
            this.refreshChat();
        }

        this.detectChange = this.draw.getWidth();
        GuiNewModChat.drawChat(p_146230_1_);

        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN)
        {
            int i = this.getLineCount();
            boolean flag = false;
            int j = 0;
            int k = this.field_146253_i.size();
            float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (k > 0)
            {
                if (this.getChatOpen())
                {
                    flag = true;
                }

                float f1 = this.getChatScale();
                int l = MathHelper.ceiling_float_int((float)this.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0F, 20.0F, 0.0F);
                GlStateManager.scale(f1, f1, 1.0F);
                int l1 = 0;

                for (int i1 = 0; i1 + this.scrollPos < this.field_146253_i.size() && i1 < i; ++i1)
                {
                    ChatLine chatline = (ChatLine)this.field_146253_i.get(i1 + this.scrollPos);

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
                                int j2 = -l1 * 9;
                                ++l1;
                                String s = chatline.getChatComponent().getFormattedText();
                                char c0 = '\u270e';
                                String s1 = " " + Color.cl("e") + c0 + Color.cl("f");
                                s = ChatListener.replaceMessage(chatline.getChatComponent().getFormattedText(), chatline.getChatComponent().getUnformattedText());

                                if (!s.equals(s))
                                {
                                    s = s + s1;
                                }

                                if (ChatListener.isMarked(chatline.getChatComponent().getUnformattedText()) && FilterLoader.enabled)
                                {
                                	if (!Jannik.getModuleManager().getModuleByClass(TransparentChat.class).isEnabled()) {
                                		drawRect(b0, j2 - 9, b0 + l + 4, j2, 1574235432);
                                	}                                    
                                }
                                else
                                {
                                	if (!Jannik.getModuleManager().getModuleByClass(TransparentChat.class).isEnabled()) {
                                		drawRect(b0, j2 - 9, b0 + l + 4, j2, k1 / 2 << 24);
                                	}                                    
                                }

                                GlStateManager.enableBlend();

                                try
                                {
                                    this.mc.fontRendererObj.drawStringWithShadow(s, (float)b0, (float)(j2 - 8), 16777215 + (k1 << 24));
                                }
                                catch (Exception exception)
                                {
                                    exception.printStackTrace();
                                }

                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (flag)
                {
                    int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0F, 0.0F, 0.0F);
                    int j3 = k * k2 + k;
                    int l2 = j * k2 + j;
                    int k3 = this.scrollPos * l2 / k;
                    int i2 = l2 * l2 / j3;

                    if (j3 != l2)
                    {
                        int i3 = k3 > 0 ? 170 : 96;
                        int l3 = this.isScrolled ? 13382451 : 3355562;
                        if (!Jannik.getModuleManager().getModuleByClass(TransparentChat.class).isEnabled()) {
                        	drawRect(0, -k3, 2, -k3 - i2, l3 + (i3 << 24));
                            drawRect(2, -k3, 1, -k3 - i2, 13421772 + (i3 << 24));
                        }                       
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }

    /**
     * Clears the chat.
     */
    public void clearChatMessages()
    {
        this.field_146253_i.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
        GuiNewModChat.clearChatMessages();
    }

    public void printChatMessage(IChatComponent p_146227_1_)
    {
        this.printChatMessageWithOptionalDeletion(p_146227_1_, 0);
    }

    /**
     * prints the ChatComponent to Chat. If the ID is not 0, deletes an existing Chat Line of that ID from the GUI
     */
    public void printChatMessageWithOptionalDeletion(IChatComponent p_146234_1_, int p_146234_2_)
    {
        if (ChatListener.allowedToPrint(p_146234_1_))
        {
            this.setChatLine(p_146234_1_, p_146234_2_, this.mc.ingameGUI.getUpdateCounter(), false);
            logger.info("[CHAT] " + p_146234_1_.getUnformattedText());
        }
    }

    private void setChatLine(IChatComponent p_146237_1_, int p_146237_2_, int p_146237_3_, boolean p_146237_4_)
    {
        boolean flag = ChatListener.isServerMSG(Color.removeColor(p_146237_1_.getUnformattedText()));

        if (!ConfigManager.settings.chatPositionRight)
        {
            flag = !flag;
        }

        if (flag)
        {
            GuiNewModChat.setChatLine(p_146237_1_, p_146237_2_, p_146237_3_, p_146237_4_);
        }
        else
        {
            if (p_146237_2_ != 0)
            {
                this.deleteChatLine(p_146237_2_);
            }

            int i = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
            List list = GuiUtilRenderComponents.func_178908_a(p_146237_1_, i, this.mc.fontRendererObj, false, false);
            boolean flag1 = this.getChatOpen();

            for (Object ichatcomponent : list)
            {
                if (flag1 && this.scrollPos > 0)
                {
                    this.isScrolled = true;
                    this.scroll(1);
                }

                this.field_146253_i.add(0, new ChatLine(p_146237_3_, (IChatComponent) ichatcomponent, p_146237_2_));
            }

            while (this.field_146253_i.size() > 100)
            {
                this.field_146253_i.remove(this.field_146253_i.size() - 1);
            }

            if (!p_146237_4_)
            {
                this.chatLines.add(0, new ChatLine(p_146237_3_, p_146237_1_, p_146237_2_));

                while (this.chatLines.size() > 100)
                {
                    this.chatLines.remove(this.chatLines.size() - 1);
                }
            }
        }
    }

    public void refreshChat()
    {
        ChatListener.init = Minecraft.getSystemTime();
        this.field_146253_i.clear();
        this.resetScroll();

        for (int i = this.chatLines.size() - 1; i >= 0; --i)
        {
            ChatLine chatline = (ChatLine)this.chatLines.get(i);
            this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }

        GuiNewModChat.refreshChat();
    }

    /**
     * Gets the list of messages previously sent through the chat GUI
     */
    public List getSentMessages()
    {
        return this.sentMessages;
    }

    /**
     * Adds this string to the list of sent messages, for recall using the up/down arrow keys
     */
    public void addToSentMessages(String p_146239_1_)
    {
        if (this.sentMessages.isEmpty() || !((String)this.sentMessages.get(this.sentMessages.size() - 1)).equals(p_146239_1_))
        {
            this.sentMessages.add(p_146239_1_);
        }
    }

    /**
     * Resets the chat scroll (executed when the GUI is closed, among others)
     */
    public void resetScroll()
    {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    /**
     * Scrolls the chat by the given number of lines.
     */
    public void scroll(int p_146229_1_)
    {
        GuiNewModChat.scroll(p_146229_1_);
        DrawUtils drawutils = LabyMod.getInstance().draw;

        if (DrawUtils.getMouseX() <= this.getChatWidth() || !ConfigManager.settings.extraChat && !ConfigManager.settings.chatFilter)
        {
            this.scrollPos += p_146229_1_;
            int i = this.field_146253_i.size();

            if (this.scrollPos > i - this.getLineCount())
            {
                this.scrollPos = i - this.getLineCount();
            }

            if (this.scrollPos <= 0)
            {
                this.scrollPos = 0;
                this.isScrolled = false;
            }
        }
    }

    /**
     * Gets the chat component under the mouse
     */
    public IChatComponent getChatComponent(int p_146236_1_, int p_146236_2_)
    {
        if (!this.getChatOpen())
        {
            return null;
        }
        else
        {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = scaledresolution.getScaleFactor();
            float f = this.getChatScale();
            int j = p_146236_1_ / i - 3;
            int k = p_146236_2_ / i - 27;
            j = MathHelper.floor_float((float)j / f);
            k = MathHelper.floor_float((float)k / f);

            if (j >= 0 && k >= 0)
            {
                int l = Math.min(this.getLineCount(), this.field_146253_i.size());

                if (j <= MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l)
                {
                    int i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;

                    if (i1 >= 0 && i1 < this.field_146253_i.size())
                    {
                        ChatLine chatline = (ChatLine)this.field_146253_i.get(i1);
                        int j1 = 0;

                        for (IChatComponent ichatcomponent : chatline.getChatComponent())
                        {
                            if (ichatcomponent instanceof ChatComponentText)
                            {
                                j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)ichatcomponent).getChatComponentText_TextValue(), false));

                                if (j1 > j)
                                {
                                    return ichatcomponent;
                                }
                            }
                        }
                    }

                    return GuiNewModChat.getChatComponent(p_146236_1_, p_146236_2_);
                }
                else
                {
                    return GuiNewModChat.getChatComponent(p_146236_1_, p_146236_2_);
                }
            }
            else
            {
                return GuiNewModChat.getChatComponent(p_146236_1_, p_146236_2_);
            }
        }
    }

    /**
     * Returns true if the chat GUI is open
     */
    public boolean getChatOpen()
    {
        return Minecraft.getMinecraft().currentScreen instanceof GuiChat;
    }

    /**
     * finds and deletes a Chat line by ID
     */
    public void deleteChatLine(int p_146242_1_)
    {
        Iterator iterator = this.field_146253_i.iterator();

        while (iterator.hasNext())
        {
            ChatLine chatline = (ChatLine)iterator.next();

            if (chatline.getChatLineID() == p_146242_1_)
            {
                iterator.remove();
            }
        }

        iterator = this.chatLines.iterator();

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

    public int getChatWidth()
    {
        return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }

    public int getChatHeight()
    {
        return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }

    /**
     * Returns the chatscale from mc.gameSettings.chatScale
     */
    public float getChatScale()
    {
        return this.mc.gameSettings.chatScale;
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

    public int getLineCount()
    {
        return this.getChatHeight() / 9;
    }
}
