/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ChatHelper;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.utils.Command.impl.Chat;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class GuiNewChat
extends Gui {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    public static final List<String> sentMessages = Lists.newArrayList();
    private final List<ChatLine> chatLines = Lists.newArrayList();
    private final List<ChatLine> drawnChatLines = Lists.newArrayList();
    private int scrollPos;
    AnimationUtils scroll = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private boolean isScrolled;
    private float scrollAnim1;
    private float scrollAnim2;
    private String lastMessage;
    private int sameMessageAmount;
    private int line;

    public GuiNewChat(Minecraft mcIn) {
        this.mc = mcIn;
    }

    public void drawChat(int updateCounter) {
        boolean censure;
        this.scroll.to = this.scrollPos;
        boolean bl = censure = ChatHelper.get.actived && ChatHelper.get.CensureText.bValue;
        if (Panic.stop || !ComfortUi.get.isChatAnimations()) {
            if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                int i = this.getLineCount();
                int j = this.drawnChatLines.size();
                float f = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
                if (j > 0) {
                    boolean flag = this.getChatOpen();
                    float f1 = this.getChatScale();
                    int k = MathHelper.ceil((float)this.getChatWidth() / f1);
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(2.0f, 8.0f, 0.0f);
                    GlStateManager.scale(f1, f1, 1.0f);
                    int l = 0;
                    for (int i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; ++i1) {
                        int j1;
                        ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);
                        if (chatline == null || (j1 = updateCounter - chatline.getUpdatedCounter()) >= 200 && !flag) continue;
                        double d0 = (double)j1 / 200.0;
                        d0 = 1.0 - d0;
                        d0 *= 10.0;
                        d0 = MathHelper.clamp(d0, 0.0, 1.0);
                        d0 *= d0;
                        int l1 = (int)(255.0 * d0);
                        if (flag) {
                            l1 = 255;
                        }
                        l1 = (int)((float)l1 * f);
                        ++l;
                        if (l1 <= 3) continue;
                        boolean i2 = false;
                        int j2 = -i1 * 9;
                        String s = chatline.getChatString();
                        s = this.reString(censure, s);
                        RenderUtils.drawAlphedRect(-0.5, j2 - 9, k + 4, j2, ChatHelper.get.highlightSelf(chatline.getChatComponent().getUnformattedText()) ? ColorUtils.getColor(125, 125, 125, 125) : l1 / 4 << 24);
                        GlStateManager.enableBlend();
                        this.mc.fontRendererObj.drawStringWithShadow(s, 0.0f, -i1 * 9 - 8, 0xFFFFFF + (l1 << 24));
                        GlStateManager.disableAlpha();
                        GlStateManager.disableBlend();
                    }
                    if (flag) {
                        int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
                        GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                        int l2 = j * k2 + j;
                        int i3 = l * k2 + l;
                        int j3 = this.scrollPos * i3 / j;
                        int k1 = i3 * i3 / l2;
                        if (l2 != i3) {
                            int k3 = j3 > 0 ? 170 : 96;
                            int l3 = this.isScrolled ? 0xCC3333 : 0x3333AA;
                            GuiNewChat.drawRect(0, (double)(-j3), 2.0, (double)(-j3 - k1), l3 + (k3 << 24));
                            GuiNewChat.drawRect(2, (double)(-j3), 1.0, (double)(-j3 - k1), 0xCCCCCC + (k3 << 24));
                        }
                    }
                    GlStateManager.popMatrix();
                }
            }
        } else if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int i = this.getLineCount();
            int j = this.drawnChatLines.size();
            float f = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (j > 0) {
                int j2;
                boolean i2;
                int l1;
                double d0;
                int j1;
                ChatLine chatline;
                boolean flag = this.getChatOpen();
                float f1 = this.getChatScale();
                int k = MathHelper.ceil((float)this.getChatWidth() / f1);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0f, 8.0f, 0.0f);
                GlStateManager.scale(f1, f1, 1.0f);
                int l = 0;
                int ee = 0;
                int i1 = 0;
                while (i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i) {
                    chatline = this.drawnChatLines.get(i1 + this.scrollPos);
                    if (chatline != null && ((j1 = updateCounter - chatline.getUpdatedCounter()) < 200 || flag)) {
                        d0 = (double)j1 / 200.0;
                        d0 = 1.0 - d0;
                        d0 *= 10.0;
                        d0 = MathHelper.clamp(d0, 0.0, 1.0);
                        d0 *= d0;
                        l1 = (int)(255.0 * d0);
                        if (flag) {
                            l1 = 255;
                        }
                        l1 = (int)((float)l1 * f);
                        ++l;
                        if (l1 > 3) {
                            i2 = false;
                            j2 = -i1 * 9;
                            RenderUtils.drawAlphedRect(-0.5, j2 - 9, k + 4, j2, l1 / 4 << 24);
                        }
                    }
                    ee = i1++;
                }
                try {
                    StencilUtil.initStencilToWrite();
                    RenderUtils.drawAlphedRect(-0.5, (double)(ee - 9 * l) - (this.getChatOpen() ? 18.5 : 9.0), k + 4, ee - l + 1, -1);
                    StencilUtil.readStencilBuffer(1);
                    i1 = 0;
                    while ((float)i1 < (float)i + this.scroll.getAnim() + 2.0f) {
                        chatline = this.drawnChatLines.get(i1);
                        if (chatline != null && ((j1 = updateCounter - chatline.getUpdatedCounter()) < 200 || flag)) {
                            d0 = (double)j1 / 200.0;
                            d0 = 1.0 - d0;
                            d0 *= 10.0;
                            d0 = MathHelper.clamp(d0, 0.0, 1.0);
                            d0 *= d0;
                            l1 = (int)(255.0 * d0);
                            if (flag) {
                                l1 = 255;
                            }
                            l1 = (int)((float)l1 * f);
                            i2 = false;
                            j2 = -i1 * 9;
                            String s = chatline.getChatString();
                            s = this.reString(censure, s);
                            float speed = 0.1f;
                            this.scroll.speed = 0.1f;
                            chatline.anim.speed = 0.1f;
                            chatline.anim2.speed = 0.1f;
                            chatline.anim3.speed = 0.1f;
                            chatline.anim2.to = 8.0f;
                            if (chatline.anim3.getAnim() == 0.0f) {
                                chatline.anim3.setAnim(9.0f);
                            }
                            chatline.anim3.to = 0.0f;
                            chatline.anim.to = i1;
                            if (chatline.anim.getAnim() == 0.0f && !flag) {
                                chatline.anim.setAnim(i1);
                            }
                            if (ColorUtils.getAlphaFromColor(0xFFFFFF + (l1 << 24)) >= 26 && -chatline.anim.getAnim() * 9.0f - chatline.anim2.getAnim() + chatline.anim3.getAnim() + this.scroll.getAnim() * 9.0f < 0.0f) {
                                if (chatline.getChatString().startsWith("\ufffd7\ufffdf\ufffdlModules:\ufffdr \ufffd7")) {
                                    RenderUtils.drawAlphedRect(-0.5, (float)(j2 - 9) + this.scroll.getAnim() * 9.0f, k + 4, (float)j2 + this.scroll.getAnim() * 9.0f, ColorUtils.getColor(73, 126, 255, ColorUtils.getAlphaFromColor(0xFFFFFF + (l1 << 24)) / 2));
                                } else if (ChatHelper.get.highlightSelf(chatline.getChatComponent().getUnformattedText())) {
                                    RenderUtils.drawAlphedRect(-0.5, (float)(j2 - 9) + this.scroll.getAnim() * 9.0f, k + 4, (float)j2 + this.scroll.getAnim() * 9.0f, ColorUtils.getColor(125, 125, 125, ColorUtils.getAlphaFromColor(0xFFFFFF + (l1 << 24)) / 2));
                                }
                                GL11.glTranslated(0.0, -chatline.anim.getAnim() * 9.0f - chatline.anim2.getAnim() + chatline.anim3.getAnim() + this.scroll.getAnim() * 9.0f, 1.0);
                                GlStateManager.disableDepth();
                                this.mc.fontRendererObj.drawStringWithShadow(s, 2.0f, 0.0f, 0xFFFFFF + (l1 << 24));
                                GL11.glTranslated(0.0, -(-chatline.anim.getAnim() * 9.0f - chatline.anim2.getAnim() + chatline.anim3.getAnim() + this.scroll.getAnim() * 9.0f), -1.0);
                            }
                            GlStateManager.enableBlend();
                            GlStateManager.enableAlpha();
                        }
                        ++i1;
                    }
                } catch (Exception i4) {
                    // empty catch block
                }
                StencilUtil.uninitStencilBuffer();
                if (flag) {
                    int k2 = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                    int l2 = j * k2 + j;
                    int i3 = l * k2 + l;
                    int j3 = (int)(this.scroll.getAnim() * (float)i3 / (float)j);
                    int k1 = i3 * i3 / l2;
                    if (l2 != i3) {
                        int k3 = j3 > 0 ? 170 : 96;
                        int l3 = this.isScrolled ? 0xCC3333 : 0x3333AA;
                        int c = ColorUtils.astolfoColorsCool(0, 0);
                        int c2 = ColorUtils.astolfoColorsCool(0, 200);
                        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(1.0f, -j3 - k1 / 2, 2.0f, -j3, 0.5f, 1.0f, c, c, c2, c2, false, true, true);
                    }
                }
                GlStateManager.popMatrix();
            }
        }
    }

    public void clearChatMessages(boolean p_146231_1_) {
        this.drawnChatLines.clear();
        this.chatLines.clear();
        sentMessages.clear();
    }

    private String fixString(String str) {
        str = str.replaceAll("\uf8ff", "");
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c > '\uff01' && c < '\uff60') {
                sb.append(Character.toChars(c - 65248));
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private boolean fixSpam() {
        return !Panic.stop && Client.moduleManager != null && ChatHelper.get.actived && ChatHelper.get.NoExtraCopy.bValue;
    }

    public void printChatMessage(ITextComponent chatComponent) {
        if (!Panic.stop && Chat.stringIsContainsBadMassage(chatComponent.getFormattedText())) {
            return;
        }
        String text = this.fixString(chatComponent.getFormattedText());
        if (this.fixSpam()) {
            if (!chatComponent.getFormattedText().startsWith("|")) {
                if (text.equals(this.lastMessage)) {
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().deleteChatLine(this.line);
                    ++this.sameMessageAmount;
                    chatComponent.appendText(ChatFormatting.WHITE + " <" + ChatFormatting.GRAY + "x" + this.sameMessageAmount + ChatFormatting.WHITE + ">");
                } else {
                    this.sameMessageAmount = 1;
                }
                boolean infiniteLines = !Panic.stop && ChatHelper.get.isInfiniteChatHistory();
                this.lastMessage = text;
                ++this.line;
                if (this.line > (infiniteLines ? 1280 : 256)) {
                    this.line = 0;
                }
            } else {
                this.lastMessage = text;
                this.line = 0;
            }
            this.printChatMessageWithOptionalDeletion(chatComponent, this.line);
        } else {
            this.printChatMessageWithOptionalDeletion(chatComponent, 0);
        }
    }

    public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
        this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
        LOGGER.info("[CHAT] {}", (Object)chatComponent.getUnformattedText().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }

    private void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }
        int i = MathHelper.floor((float)this.getChatWidth() / this.getChatScale());
        List<ITextComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, this.mc.fontRendererObj, false, false);
        boolean flag = this.getChatOpen();
        for (ITextComponent itextcomponent : list) {
            if (flag && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
            this.drawnChatLines.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId));
        }
        int maxLines = (int)(!Panic.stop && ChatHelper.get.isInfiniteChatHistory() ? 896.0 : 100.0);
        while (this.drawnChatLines.size() > maxLines) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }
        if (!displayOnly) {
            this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
            while (this.chatLines.size() > maxLines) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }

    public void refreshChat() {
        this.drawnChatLines.clear();
        this.resetScroll();
        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            ChatLine chatline = this.chatLines.get(i);
            this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }
    }

    public List<String> getSentMessages() {
        return sentMessages;
    }

    public void addToSentMessages(String message) {
        if (sentMessages.isEmpty() || !sentMessages.get(sentMessages.size() - 1).equals(message)) {
            if (Panic.stop && message.startsWith(".")) {
                sentMessages.remove(message);
            } else {
                sentMessages.add(message);
            }
        }
    }

    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    public void scroll(int amount) {
        this.scrollPos += amount;
        int i = this.drawnChatLines.size();
        if (this.scrollPos > i - this.getLineCount()) {
            this.scrollPos = i - this.getLineCount();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    @Nullable
    public ITextComponent getChatComponent(int mouseX, int mouseY) {
        if (!this.getChatOpen()) {
            return null;
        }
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int i = ScaledResolution.getScaleFactor();
        float f = this.getChatScale();
        int j = mouseX / i - 2;
        int k = mouseY / i - 40;
        j = MathHelper.floor((float)j / f);
        k = MathHelper.floor((float)k / f);
        if (j >= 0 && k >= 0) {
            int l = Math.min(this.getLineCount(), this.drawnChatLines.size());
            if (j <= MathHelper.floor((float)this.getChatWidth() / this.getChatScale()) && k < this.mc.fontRendererObj.FONT_HEIGHT * l + l) {
                int i1 = k / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
                if (i1 >= 0 && i1 < this.drawnChatLines.size()) {
                    ChatLine chatline = this.drawnChatLines.get(i1);
                    int j1 = 0;
                    for (ITextComponent itextcomponent : chatline.getChatComponent()) {
                        if (!(itextcomponent instanceof TextComponentString) || (j1 += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.removeTextColorsIfConfigured(((TextComponentString)itextcomponent).getText(), false))) <= j) continue;
                        return itextcomponent;
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }

    public void deleteChatLine(int id) {
        Iterator<ChatLine> iterator = this.drawnChatLines.iterator();
        while (iterator.hasNext()) {
            ChatLine chatline = iterator.next();
            if (!Panic.stop && Chat.stringIsContainsBadMassage(chatline.getChatString())) {
                iterator.remove();
            }
            if (chatline.getChatLineID() != id) continue;
            iterator.remove();
        }
        iterator = this.chatLines.iterator();
        while (iterator.hasNext()) {
            ChatLine chatline1 = iterator.next();
            if (chatline1.getChatLineID() != id) continue;
            iterator.remove();
            break;
        }
    }

    public int getChatWidth() {
        return GuiNewChat.calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }

    public int getChatHeight() {
        return GuiNewChat.calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }

    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }

    public static int calculateChatboxWidth(float scale) {
        int i = 320;
        int j = 40;
        return MathHelper.floor(scale * 280.0f + 40.0f);
    }

    public static int calculateChatboxHeight(float scale) {
        int i = 180;
        int j = 20;
        return MathHelper.floor(scale * 160.0f + 20.0f);
    }

    public int getLineCount() {
        return this.getChatHeight() / 9;
    }

    public String reString(boolean censure, String s) {
        String censStart = "";
        String censEnd = "";
        if (censure) {
            s = s.replace("\u0445\u0443\u0439", censStart + "\u0445*\u0439" + censEnd);
            s = s.replace("\u0445\u0443\u0438", censStart + "\u0445*\u0438" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440", censStart + "\u043f\u0438*\u043e\u0440" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440\u044b", censStart + "\u043f\u0438*\u043e\u0440\u044b" + censEnd);
            s = s.replace("\u0445\u0443\u0439\u043b\u043e", censStart + "\u0445**\u043b\u043e" + censEnd);
            s = s.replace("\u0431\u043b\u00a4\u0442\u044c", censStart + "\u0431\u043b*\u0442\u044c" + censEnd);
            s = s.replace("\u0431\u043b\u044f\u0434\u044c", censStart + "\u0431\u043b*\u0434\u044c" + censEnd);
            s = s.replace("\u0431\u043b\u044f\u0434\u0438", censStart + "\u0431\u043b*\u0434\u0438" + censEnd);
            s = s.replace("\u0431\u043b\u0442\u044c", censStart + "\u0431*\u0442\u044c" + censEnd);
            s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0451\u0431", censStart + "\u0434\u0430\u043b\u0431**\u0431" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0430\u0431\u043e\u043b", censStart + "\u043f\u0438***\u0431\u043e\u043b" + censEnd);
            s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0451\u0431\u044b", censStart + "\u0434\u0430\u043b\u0431**\u0431\u044b" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0430\u0431\u043e\u043b\u044b", censStart + "\u043f\u0438***\u0431\u043e\u043b\u044b" + censEnd);
            s = s.replace("\u0434\u0430\u0443\u043d", censStart + "\u0434*\u0443\u043d" + censEnd);
            s = s.replace("\u0434\u0430\u0443\u043d\u044b", censStart + "\u0434*\u0443\u043d\u044b" + censEnd);
            s = s.replace("\u0433\u0430\u043d\u0434\u043e\u043d", censStart + "\u0433\u0430**\u043e\u043d" + censEnd);
            s = s.replace("\u0433\u0430\u043d\u0434\u043e\u043d\u044b", censStart + "\u0433\u0430**\u043e\u043d\u044b" + censEnd);
            s = s.replace("\u0447\u043c\u043e", censStart + "\u0447*\u043e" + censEnd);
            s = s.replace("\u0445\u0443\u0435\u0441\u043e\u0441", censStart + "\u0445**\u0441\u043e\u0441" + censEnd);
            s = s.replace("\u0432\u044b\u0431\u043b\u044f\u0434\u043e\u043a", censStart + "\u0432\u044b\u0431\u043b**\u043e\u043a" + censEnd);
            s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
            s = s.replace("\u0441\u043e\u0441\u0438", censStart + "\u0441\u043e*\u0438" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0435\u0446", censStart + "\u043f\u0438\u0437*\u0435\u0446" + censEnd);
            s = s.replace("\u0445\u0443\u0439\u043d\u044f", censStart + "\u0445**\u043d\u00a4" + censEnd);
            s = s.replace("\u0445\u0435\u0440\u043d\u044f", censStart + "\u0445**\u043d\u044f" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0438", censStart + "\u043f**\u0434\u0438" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0451\u0436", censStart + "\u043f\u0438**\u0451\u0436" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0435\u0436", censStart + "\u043f\u0438**\u0435\u0436" + censEnd);
            s = s.replace("\u0437\u0430\u043b\u0443\u043f\u0430", censStart + "\u0437\u0430\u043b*\u043f\u0430" + censEnd);
            s = s.replace("\u0437\u0430\u043b\u0443\u043f\u043e\u0439", censStart + "\u0437\u0430\u043b*\u043f\u043e\u0439" + censEnd);
            s = s.replace("\u0437\u0430\u043b\u0443\u043f\u0430\u043c\u0438", censStart + "\u0437\u0430\u043b*\u043f\u0430\u043c\u0438" + censEnd);
            s = s.replace("\u0433\u0430\u0432\u043d\u043e", censStart + "\u0433\u0430\u0432*\u043d\u043e" + censEnd);
            s = s.replace("\u043c\u0443\u0434\u0430\u043a", censStart + "\u043c\u0443\u0434*\u043a" + censEnd);
            s = s.replace("\u043c\u0443\u0434\u0438\u043b\u0430", censStart + "\u043c\u0443\u0434*\u043b\u0430" + censEnd);
            s = s.replace("\u043c\u0443\u0434\u0438\u043b\u043e", censStart + "\u043c\u0443\u0434*\u043b\u043e" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440\u044b", censStart + "\u043f\u0438\u0434*\u0440\u044b" + censEnd);
            s = s.replace("\u043f\u0435\u043d\u0438\u0441", censStart + "\u043f\u0435\u043d*\u0441" + censEnd);
            s = s.replace("\u0443\u0435\u0431\u0430\u043d", censStart + "\u0443\u0435*\u0430\u043d" + censEnd);
            s = s.replace("\u0443\u0435\u0431\u043e\u043a", censStart + "\u0443\u0435*\u043e\u043a" + censEnd);
            s = s.replace("\u0443\u0404\u0431\u043e\u043a", censStart + "\u0443\u0404*\u043e\u043a" + censEnd);
            s = s.replace("\u0443\u0404\u0431\u0438\u0449\u0435", censStart + "\u0443\u0451*\u0438\u0449\u0435" + censEnd);
            s = s.replace("\u0443\u0435\u0431\u0438\u0449\u0435", censStart + "\u0443\u0435*\u0438\u0449\u0435" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043b\u043e", censStart + "\u0435\u0431*\u043b\u043e" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u043e", censStart + "\u0435*\u043b\u043e" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u0438\u0449\u0435", censStart + "\u0435**\u0438\u0449\u0435" + censEnd);
            s = s.replace("\u0404\u0431\u0430\u043d\u044b\u0439", censStart + "\u0404**\u043d\u044b\u0439" + censEnd);
            s = s.replace("\u0404\u0431\u0430\u043d\u044b\u0435", censStart + "\u0404**\u043d\u044b\u0435" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043d\u044b\u0439", censStart + "\u0435**\u043d\u044b\u0439" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043d\u044b\u0435", censStart + "\u0435**\u043d\u044b\u0435" + censEnd);
            s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0438\u0441\u044c", censStart + "\u043e\u0442\u0431**\u0438\u0441\u044c" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u0442\u044c", censStart + "\u0435**\u0442\u044c" + censEnd);
            s = s.replace("\u0430\u0445\u0443\u0435\u0442\u044c", censStart + "\u0430\u0445**\u0442\u044c" + censEnd);
            s = s.replace("\u0432\u0430\u0445\u0443\u0435", censStart + "\u0432\u0430*\u0443\u0435" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0435\u0446", censStart + "\u043f\u0438**\u0435\u0446" + censEnd);
            s = s.replace("\u0434\u043e\u0435\u0431\u0430\u043b\u0441\u00a4", censStart + "\u0434\u043e\u0435**\u043b\u0441\u044f" + censEnd);
            s = s.replace("\u0451\u0431\u043d\u0443\u043b", censStart + "\u0451*\u043d\u0443\u043b" + censEnd);
            s = s.replace("\u0435\u0431\u043d\u0443\u043b", censStart + "\u0435*\u043d\u0443\u043b" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043d\u0443\u043b", censStart + "\u0435**\u043d\u0443\u043b" + censEnd);
            s = s.replace("\u0437\u0430\u0435\u0431\u0430\u043b", censStart + "\u0437\u0430**\u0430\u043b" + censEnd);
            s = s.replace("\u0435\u043b\u0434\u0430", censStart + "\u0435\u043b*\u0430" + censEnd);
            s = s.replace("\u0435\u043b\u0434\u0438\u043d\u0430", censStart + "\u0435\u043b*\u0438\u043d\u0430" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0430", censStart + "\u043f*\u0437\u0434\u0430" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0430\u0431\u043e\u043b", censStart + "\u043f\u0438\u0437\u0434*\u0431\u043e\u043b" + censEnd);
            s = s.replace("\u043f\u043e\u043f\u0438\u0437\u0434\u0438", censStart + "\u043f\u043e\u043f**\u0434\u0438" + censEnd);
            s = s.replace("\u043b\u043e\u0445 ", censStart + "\u043b*\u0445" + censEnd);
            s = s.replace("\u043b\u043e\u043e\u0445", censStart + "\u043b**\u0445" + censEnd);
            s = s.replace("\u043b\u043e\u043e\u043e\u0445", censStart + "\u043b***\u0445" + censEnd);
            s = s.replace("\u043b\u043e\u043e\u043e\u043e\u0445", censStart + "\u043b****\u0445" + censEnd);
            s = s.replace("\u043b\u043e\u043e\u043e\u043e\u043e\u0445", censStart + "\u043b*****\u0445" + censEnd);
            s = s.replace("\u043b\u043e\u043e\u043e\u043e\u043e\u043e\u0445", censStart + "\u043b******\u0445" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0443\u0439", censStart + "\u043f\u0438\u0437**\u0439" + censEnd);
            s = s.replace("\u0445\u0443\u0451\u0432", censStart + "\u0445*\u0451\u0432" + censEnd);
            s = s.replace("\u0445\u0443\u0435\u0432", censStart + "\u0445*\u0435\u0432" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u043c\u0438", censStart + "\u0445**\u043c\u0438" + censEnd);
            s = s.replace("\u0445\u0443\u0451\u0432\u044b\u0439", censStart + "\u0445**\u0432\u044b\u0439" + censEnd);
            s = s.replace("\u043d\u0430\u0445\u0443\u0439", censStart + "\u043d\u0430**\u0439" + censEnd);
            s = s.replace("\u0445\u0443\u044e", censStart + "\u0445*\u044e" + censEnd);
            s = s.replace("\u0445\u0443\u0435", censStart + "\u0445*\u0435" + censEnd);
            s = s.replace("\u0441\u043e\u0441\u0451\u0448\u044c", censStart + "\u0441\u043e\u0441*\u0448\u044c" + censEnd);
            s = s.replace("\u0441\u043e\u0441\u0435\u0448\u044c", censStart + "\u0441\u043e\u0441*\u0448\u044c" + censEnd);
            s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
            s = s.replace("\u043e\u0442\u0445\u0443\u044f\u0440\u044e", censStart + "\u043e\u0442\u0445*\u044f\u0440\u044e" + censEnd);
            s = s.replace("\u043e\u0442\u043f\u0438\u0437\u0434\u0438\u043b", censStart + "\u043e\u0442\u043f**\u0434\u0438\u043b" + censEnd);
            s = s.replace("\u043e\u0442\u043c\u0443\u0434\u043e\u0445\u0430\u043b", censStart + "\u043e\u0442\u043c**\u043e\u0445\u0430\u043b" + censEnd);
            s = s.replace("\u0437\u0430\u0445\u0443\u044f\u0440\u0438\u043b", censStart + "\u0437\u0430\u0445**\u0440\u0438\u043b" + censEnd);
            s = s.replace("\u043e\u0442\u0445\u0443\u044f\u0440\u0438\u043b", censStart + "\u043e\u0442\u0445**\u0440\u0438\u043b" + censEnd);
            s = s.replace("\u043e\u0442\u0445\u0443\u044f\u0440\u044e", censStart + "\u043e\u0442\u0445**\u0440\u044e" + censEnd);
            s = s.replace("\u043d\u0438\u0445\u0443\u00a4", censStart + "\u043d\u0438\u0445*\u044f" + censEnd);
            s = s.replace("\u0445\u0443\u0435\u0433\u043b\u043e\u0442", censStart + "\u0445\u0443*\u0433\u043b\u043e\u0442" + censEnd);
            s = s.replace("\u0445\u0443\u0435\u0433\u0440\u044b\u0437", censStart + "\u0445**\u0433\u0440\u044b\u0437" + censEnd);
            s = s.replace("\u043e\u0442\u0441\u043e\u0441", censStart + "\u043e\u0442\u0441*\u0441" + censEnd);
            s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043b", censStart + "\u0435\u0431*\u043b" + censEnd);
            s = s.replace("\u0447\u043c\u043e\u0448\u043d\u0438\u043a", censStart + "\u0447\u043c**\u043d\u0438\u043a" + censEnd);
            s = s.replace("\u043d\u0438\u0445\u0435\u0440\u0430", censStart + "\u043d\u0438\u0445*\u0440\u0430" + censEnd);
            s = s.replace("\u0448\u043b\u044e\u0445\u0430", censStart + "\u0448\u043b*\u0445\u0430" + censEnd);
            s = s.replace("\u0433\u043d\u0438\u0434\u0430", censStart + "\u0433\u043d*\u0434\u0430" + censEnd);
            s = s.replace("\u0445\u0443\u0435\u043f\u043b\u0451\u0442", censStart + "\u0445**\u043f\u043b\u0451\u0442" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0443\u0439", censStart + "\u043f**\u0434\u0443\u0439" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u0440", censStart + "\u043f*\u0434\u0440" + censEnd);
            s = s.replace("\u0432\u044b\u0404\u0431\u044b\u0432\u0430\u0442\u044c\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0442\u044c\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0404\u0431\u044b\u0432\u0430\u0435\u0448\u044c\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0435\u0448\u044c\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0404\u0431\u044b\u0432\u0430\u0442\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0442\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0404\u0431\u044b\u0432\u0430\u0435\u0448\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0435\u0448\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0435\u0431\u043d\u0443\u043b\u0441\u044f", censStart + "\u0432\u044b**\u043d\u0443\u043b\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0435\u0431\u044b\u0432\u0430\u044e\u0441\u044c", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0441\u044c" + censEnd);
            s = s.replace("\u0432\u044b\u0404\u0431\u044b\u0432\u0430\u044e\u0441\u044c", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0441\u044c" + censEnd);
            s = s.replace("\u0432\u044b\u0404\u0431\u044b\u0432\u0430\u044e\u0442\u0441\u044f", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0442\u0441\u044f" + censEnd);
            s = s.replace("\u0434\u043e\u043f\u0438\u0437\u0434\u0435\u043b\u0441\u00a4", censStart + "\u0434\u043e\u043f**\u0434\u0435\u043b\u0441\u044f" + censEnd);
            s = s.replace("\u0434\u043e\u043f\u0438\u0437\u0434\u0435\u043b\u0438\u0441\u044c", censStart + "\u0434\u043e\u043f**\u0434\u0435\u043b\u0438\u0441\u044c" + censEnd);
            s = s.replace("\u0445\u0443\u00a4\u043c\u0438", censStart + "\u0445*\u044f\u043c\u0438" + censEnd);
            s = s.replace("\u0435\u0431\u0443", censStart + "\u0435*\u0443" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043d\u044b\u0439", censStart + "\u0435\u0431**\u044b\u0439" + censEnd);
            s = s.replace("\u0435\u0431\u0443\u0447\u0438\u0439", censStart + "\u0435**\u0447\u0438\u0439" + censEnd);
            s = s.replace("\u0435\u0431\u0443\u0447\u0438\u0435", censStart + "\u0435**\u0447\u0438\u0435" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u0430\u043d", censStart + "\u0435*\u043b\u0430\u043d" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u0430\u043d\u043e\u0438\u0434", censStart + "\u0435*\u043b\u0430\u043d\u043e\u0438\u0434" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u0430\u043d\u044b", censStart + "\u0435*\u043b\u0430\u043d\u044b" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u0430\u043d\u043e\u0438\u0434\u044b", censStart + "\u0435*\u043b\u0430\u043d\u043e\u0438\u0434\u044b" + censEnd);
            s = s.replace("\u043e\u0431\u043e\u0441\u0441\u0430\u043b", censStart + "\u043e\u0431\u043e\u0441\u0441*\u043b" + censEnd);
            s = s.replace("\u043d\u0430\u0435\u0431\u043d\u0443\u043b\u0441\u044f", censStart + "\u043d\u0430**\u043d\u0443\u043b\u0441\u00a4" + censEnd);
            s = s.replace("\u043d\u0430\u0435\u0431\u043d\u0443\u043b\u0438\u0441\u044c", censStart + "\u043d\u0430**\u043d\u0443\u043b\u0438\u0441\u044c" + censEnd);
            s = s.replace("\u043d\u0430\u0435\u0431\u043d\u0443\u043b", censStart + "\u043d\u0430**\u043d\u0443\u043b" + censEnd);
            s = s.replace("sosi", censStart + "s*si" + censEnd);
            s = s.replace("otsosi", censStart + "ots*si" + censEnd);
            s = s.replace("otsos", censStart + "ots*s" + censEnd);
            s = s.replace("\u043e\u0442\u0441\u043e\u0441\u0438", censStart + "\u043e\u0442\u0441*\u0441\u0438" + censEnd);
            s = s.replace("\u043e\u0442\u0441\u043e\u0441", censStart + "\u043e\u0442\u0441*\u0441" + censEnd);
            s = s.replace("\u043f\u0435\u0440\u0435\u0435\u0431\u0443", censStart + "\u043f\u0435\u0440\u0435*\u0431\u0443" + censEnd);
            s = s.replace("\u043f\u0435\u0440\u0435\u0435\u0431\u0430\u043b", censStart + "\u043f\u0435\u0440\u0435*\u0431\u0430\u043b" + censEnd);
            s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0430\u043b", censStart + "\u043e\u0442\u044c**\u0430\u043b" + censEnd);
            s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0443", censStart + "\u043e\u0442\u044c**\u0443" + censEnd);
            s = s.replace("\u043b\u043e\u0448\u0430\u0440\u0430", censStart + "\u043b*\u0448\u0430\u0440\u0430" + censEnd);
            s = s.replace("\u043f\u0440\u043e\u0435\u0431\u0430\u043b", censStart + "\u043f\u0440\u043e**\u0430\u043b" + censEnd);
            s = s.replace("\u0443\u0435\u0431\u0430\u043b", censStart + "\u0443**\u0430\u043b" + censEnd);
            s = s.replace("\u0443\u0435\u0431\u0430\u043b\u0441\u00a4", censStart + "\u0443**\u0430\u043b\u0441\u044f" + censEnd);
            s = s.replace("\u0443\u0441\u0440\u0438\u0441\u044c", censStart + "\u0443\u0441\u0440*\u0441\u044c" + censEnd);
            s = s.replace("\u0443\u0441\u0440\u0430\u043b\u0441\u044f", censStart + "\u0443\u0441\u0440*\u043b\u0441\u00a4" + censEnd);
            s = s.replace("\u0443\u0441\u0440\u0438\u0440\u0430\u044e\u0442\u0441\u044f", censStart + "\u0443\u0441\u0440*\u0440\u0430\u044e\u0442\u0441\u044f" + censEnd);
            s = s.replace("\u043e\u0431\u043e\u0441\u0440\u0430\u043b", censStart + "\u043e\u0431\u043e\u0441*\u0430\u043b" + censEnd);
            s = s.replace("\u043e\u0431\u043e\u0441\u0440\u0430\u043b\u0441\u00a4", censStart + "\u043e\u0431\u043e\u0441*\u0430\u043b\u0441\u00a4" + censEnd);
            s = s.replace("\u0432\u044b\u0441\u0435\u0440", censStart + "\u0432\u044b\u0441*\u0440" + censEnd);
            s = s.replace("\u0448\u0430\u043b\u0430\u0432\u0430", censStart + "\u0448\u0430\u043b*\u0432\u0430" + censEnd);
            s = s.replace("\u0433\u0430\u0432\u043d\u043e\u044e\u0437\u0435\u0440", censStart + "\u0442\u044b \u043b\u0443\u0447\u0448\u0438\u0439" + censEnd);
            s = s.replace("\u0433\u043e\u0432\u043d\u043e\u044e\u0437\u0435\u0440", censStart + "\u0442\u044b \u043b\u0443\u0447\u0448\u0438\u0439" + censEnd);
            s = s.replace("\u043f\u043e\u0435\u0431\u0435\u043d\u044c", censStart + "\u043f\u043e**\u0435\u043d\u044c" + censEnd);
            s = s.replace("\u0432\u044b\u0435\u0431\u0430\u043d", censStart + "\u0432\u044b**\u0430\u043d" + censEnd);
            s = s.replace("\u0432\u044b\u0435\u0431\u0430\u043b", censStart + "\u0432\u044b**\u0430\u043b" + censEnd);
            s = s.replace("\u043e\u0442\u044c\u0435\u0431\u0430\u043b", censStart + "\u043e\u0442\u044c**\u0430\u043b" + censEnd);
            s = s.replace("\u0435\u0431\u043b\u044f", censStart + "\u0435*\u043b\u044f" + censEnd);
            s = s.replace("\u0435\u0431\u0438", censStart + "\u0435*\u0438" + censEnd);
            s = s.replace("\u0437\u0430\u0435\u0431\u0438\u0441\u044c", censStart + "\u0437\u0430**\u0438\u0441\u044c" + censEnd);
            s = s.replace("\u0437\u0430\u0435\u0431\u043e\u043a", censStart + "\u0437\u0430**\u043e\u043a" + censEnd);
            s = s.replace("\u0445\u0443\u0439\u043d\u0438", censStart + "\u0445**\u043d\u0438" + censEnd);
            s = s.replace("\u0448\u0430\u043b\u0430\u0432\u044b", censStart + "\u0448\u0430\u043b**\u044b" + censEnd);
            s = s.replace("\u0440\u0430\u0437\u044c\u0404\u0431", censStart + "\u0440\u0430\u0437\u044c*\u0431" + censEnd);
            s = s.replace("\u0440\u0430\u0437\u044c\u0435\u0431", censStart + "\u0440\u0430\u0437\u044c*\u0431" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441", censStart + "\u043f\u0438\u0434**\u0430\u0441" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441", censStart + "\u043f\u0438\u0434**\u0430\u0441" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u044b" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u044b" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u0438\u043d\u0430", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u0430" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u0438\u043d\u0430", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u0430" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u0430\u0440\u0430\u0441\u0438\u043d\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u044b" + censEnd);
            s = s.replace("\u043f\u0438\u0434\u043e\u0440\u0430\u0441\u0438\u043d\u044b", censStart + "\u043f\u0438\u0434**\u0430\u0441\u0438\u043d\u044b" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043d\u0443\u0442\u044c\u0441\u00a4", censStart + "\u0435**\u043d\u0443\u0442\u044c\u0441\u044f" + censEnd);
            s = s.replace("\u0404\u0431\u043d\u0443\u0442\u044c\u0441\u00a4", censStart + "\u0451*\u043d\u0443\u0442\u044c\u0441\u044f" + censEnd);
            s = s.replace("\u0404\u0431\u043d\u0443\u043b\u0441\u00a4", censStart + "\u0451*\u043d\u0443\u043b\u0441\u044f" + censEnd);
            s = s.replace("\u0441\u0443\u043a\u0430", censStart + "\u0441*\u043a\u0430" + censEnd);
            s = s.replace("\u0441\u0443\u043a\u0438", censStart + "\u0441*\u043a\u0438" + censEnd);
            s = s.replace("\u0441\u0443\u0447\u043a\u0430", censStart + "\u0441*\u0447\u043a\u0430" + censEnd);
            s = s.replace("\u0441\u0443\u0447\u043a\u0438", censStart + "\u0441*\u0447\u043a\u0438" + censEnd);
            s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0451\u0431\u044b", censStart + "\u0434\u0430\u043b\u0431\u043e**\u044b" + censEnd);
            s = s.replace("\u0434\u0430\u043b\u0431\u043e\u0435\u0431\u044b", censStart + "\u0434\u0430\u043b\u0431\u043e**\u044b" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u0442\u044c\u0441\u00a4", censStart + "\u0435**\u0442\u044c\u0441\u044f" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u0442\u0441\u00a4", censStart + "\u0435**\u0442\u0441\u044f" + censEnd);
            s = s.replace("\u0435\u0431\u0443\u0441\u044c", censStart + "\u0435**\u0441\u044c" + censEnd);
            s = s.replace("\u0432\u044c\u0435\u0431\u0443", censStart + "\u0432\u044c*\u0431\u0443" + censEnd);
            s = s.replace("\u0432\u044b\u0435\u0431\u0443", censStart + "\u0432\u044b*\u0431\u0443" + censEnd);
            s = s.replace("\u0431\u043b\u044f\u0434\u0438", censStart + "\u0431**\u0434\u0438" + censEnd);
            s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u0439\u0441\u00a4", censStart + "\u0432\u044b**\u044b\u0432\u0430\u0439\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0451\u0431\u044b\u0432\u0430\u044e\u0442\u0441\u00a4", censStart + "\u0432\u044b**\u044b\u0432\u0430\u044e\u0442\u0441\u044f" + censEnd);
            s = s.replace("\u0432\u044b\u0441\u0438\u0440\u0430\u0435\u0442", censStart + "\u0432\u044b\u0441*\u0440\u0430\u0435\u0442" + censEnd);
            s = s.replace("\u0432\u044b\u0441\u0435\u0440\u0430\u0435\u0442", censStart + "\u0432\u044b\u0441*\u0440\u0430\u0435\u0442" + censEnd);
            s = s.replace("\u0432\u044b\u0441\u0440\u0430\u043b", censStart + "\u0432\u044b\u0441\u0440*\u043b" + censEnd);
            s = s.replace("\u0433\u043e\u0432\u043d\u0438\u0449\u0435", censStart + "\u0433\u043e\u0432\u043d*\u0449\u0435" + censEnd);
            s = s.replace("\u0433\u043e\u0432\u043d\u0430", censStart + "\u0433\u043e\u0432*\u0430" + censEnd);
            s = s.replace("\u0430\u0443\u0442\u0438\u0441\u0442", censStart + "\u0430\u0443\u0442*\u0441\u0442" + censEnd);
            s = s.replace("\u0435\u0431\u0430\u043d\u0430\u0442", censStart + "\u0435*\u0430\u043d\u0430\u0442" + censEnd);
            s = s.replace("\u0434\u0443\u0440\u0430", censStart + "\u0434*\u0440\u0430" + censEnd);
            s = s.replace("\u0448\u043b\u044e\u0448\u043a\u0438", censStart + "\u0448\u043b*\u0448\u043a\u0438" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0438\u0442", censStart + "\u043f\u0438\u0437*\u0438\u0442" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u044f\u0442", censStart + "\u043f\u0438\u0437*\u044f\u0442" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0438\u0448\u044c", censStart + "\u043f\u0438\u0437*\u0438\u0448\u044c" + censEnd);
            s = s.replace("\u043f\u0438\u0437\u0434\u0438\u0448", censStart + "\u043f\u0438\u0437*\u0438\u0448" + censEnd);
            s = s.replace("\u0432\u0438\u0431\u043b\u044f\u0434\u043a\u0430", censStart + "\u0432\u0438**\u044f\u0434\u043a\u0430" + censEnd);
            s = s.replace("\u0432\u0438\u0431\u043b\u044f\u0434\u043a\u0438", censStart + "\u0432\u0438**\u044f\u0434\u043a\u0438" + censEnd);
            s = s.replace("\u0432\u044b\u0431\u043b\u044f\u0434\u043a\u0430", censStart + "\u0432\u044b**\u044f\u0434\u043a\u0430" + censEnd);
            s = s.replace("\u0435\u0431\u0443\u0447\u0430\u044f", censStart + "\u0435\u0431*\u0447\u0430\u044f" + censEnd);
            s = s.replace("\u0435\u0431\u0443\u0447\u0435\u0435", censStart + "\u0435\u0431*\u0447\u0435\u0435" + censEnd);
            s = s.replace("\u043a\u043e\u043d\u0447\u0438\u043b", censStart + "\u043a\u043e\u043d\u0447*\u043b" + censEnd);
            s = s.replace("\u043a\u043e\u043d\u0447\u0430\u043b", censStart + "\u043a\u043e\u043d\u0447*\u043b" + censEnd);
            s = s.replace("\u043a\u043e\u043d\u0447\u0430", censStart + "\u043a\u043e\u043d*\u0430" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u0440\u0438\u043b", censStart + "\u0445\u0443*\u0440\u0438\u043b" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u0447\u0438\u043b", censStart + "\u0445\u0443*\u0447\u0438\u043b" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u0440\u044e", censStart + "\u0445\u0443*\u0440\u044e" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u0447\u044e", censStart + "\u0445\u0443*\u0447\u044e" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u0440\u0443", censStart + "\u0445\u0443*\u0440\u0443" + censEnd);
            s = s.replace("\u0431\u043b\u044f", censStart + "\u0431**" + censEnd);
            s = s.replace("\u0445\u0443\u044f\u0447\u0443", censStart + "\u0445\u0443*\u0447\u0443" + censEnd);
            s = s.replace("\u0441\u0432\u0438\u043d\u044c\u044f", censStart + "\u0441\u0432\u0438\u043d\u044e\u0448\u043a\u0430" + censEnd);
            s = s.replace("\u0447\u0443\u0440\u043a\u0430", censStart + "\u0447*\u0440\u043a\u0430" + censEnd);
            s = s.replace("\u0447\u0443\u0440\u043a\u0438", censStart + "\u0447*\u0440\u043a\u0438" + censEnd);
            s = s.replace("Penis", censStart + "Pen*s" + censEnd);
            s = s.replace("penis", censStart + "pen*s" + censEnd);
            s = s.replace("\u0446\u0435\u043b\u0435\u0441\u0442\u0438\u0430\u043b", censStart + "\u0446\u0435\u043b\u0435\u043f\u0443\u043a\u0441\u0442\u0438\u0430\u043b" + censEnd);
            s = s.replace("\u0430\u043a\u0440\u0438\u0435\u043d", censStart + "\u0430\u043a\u0440\u0438\u043f\u0443\u043a" + censEnd);
            s = s.replace("\u043d\u0443\u0440\u0438\u043a", censStart + "\u043d\u0443\u0440\u0438\u043f\u0443\u043a" + censEnd);
            s = s.replace("\u043d\u0443\u0440\u0441\u0443\u043b\u0442\u0430\u043d", censStart + "\u043d\u0443\u0440\u0441\u0443\u043b\u043f\u0443\u043a" + censEnd);
            s = s.replace("\u0440\u0438\u0447", censStart + "\u0441\u0440\u0438\u0447" + censEnd);
            s = s.replace("rich", censStart + "srich" + censEnd);
            s = s.replace("celestial", censStart + "celepukstial" + censEnd);
            s = s.replace("\u043d\u0435\u0432\u0435\u0440\u0445\u0443\u043a", censStart + "\u043d\u0435\u0432\u0435\u0440\u0445\u0440\u044e\u043a" + censEnd);
            s = s.replace("neverhook", censStart + "neverpuk" + censEnd);
            s = s.replace("\u044f \u043b\u044e\u0431\u043b\u044e \u044d\u0442\u043e\u0442 \u0447\u0438\u0442\u0435\u0440\u0441\u043a\u0438\u0439 \u0441\u0435\u0440\u0432\u0435\u0440 StormHVH", censStart + "\u044f \u0434\u0443\u0440\u0430\u043a" + censEnd);
            s = s.replace("YT", censStart + "LOH" + censEnd);
            s = s.replace("akrien", censStart + "akripuk" + censEnd);
            s = s.replace("Celka", censStart + "Dirka" + censEnd);
            s = s.replace("\u0435\u043a\u043f\u0435\u043d\u0441\u0438\u0432", censStart + "\u0435\u043a\u0441\u043f\u0443\u043a\u0441\u0438\u0432" + censEnd);
            s = s.replace("expensive", censStart + "expuksive" + censEnd);
            s = s.replace("\u0445\u0435\u0432\u0435\u043d", censStart + "\u0445\u0443\u0435\u0432\u0435\u043d" + censEnd);
            s = s.replace("heaven", censStart + "hueven" + censEnd);
            s = s.replace("\u0434\u0435\u0434\u043a\u043e\u0434", censStart + "\u0434\u0435\u0434\u043f\u0443\u043a" + censEnd);
            s = s.replace("deadcode", censStart + "deadpuk" + censEnd);
            s = s.replace("\u0432\u0435\u043a\u0441", censStart + "\u0432\u0435\u043d\u0438\u043a" + censEnd);
            s = s.replace("\u0432\u0435\u043a\u0441\u0430\u0439\u0434", censStart + "\u043f\u0443\u043a\u0441\u0430\u0439\u0434" + censEnd);
            s = s.replace("wexside", censStart + "pukside" + censEnd);
        }
        return s;
    }
}

