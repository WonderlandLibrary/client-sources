/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewChatGui
extends AbstractGui {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Minecraft mc;
    private final List<String> sentMessages = Lists.newArrayList();
    private final List<ChatLine<ITextComponent>> chatLines = Lists.newArrayList();
    private final List<ChatLine<IReorderingProcessor>> drawnChatLines = Lists.newArrayList();
    private final Deque<ITextComponent> field_238489_i_ = Queues.newArrayDeque();
    private int scrollPos;
    private boolean isScrolled;
    private long field_238490_l_ = 0L;
    private int lastChatWidth = 0;

    public NewChatGui(Minecraft minecraft) {
        this.mc = minecraft;
    }

    public void func_238492_a_(MatrixStack matrixStack, int n) {
        int n2 = this.getChatWidth();
        if (this.lastChatWidth != n2) {
            this.lastChatWidth = n2;
            this.refreshChat();
        }
        if (!this.func_238496_i_()) {
            this.func_238498_k_();
            int n3 = this.getLineCount();
            int n4 = this.drawnChatLines.size();
            if (n4 > 0) {
                int n5;
                int n6;
                int n7;
                int n8;
                boolean bl = false;
                if (this.getChatOpen()) {
                    bl = true;
                }
                double d = this.getScale();
                int n9 = MathHelper.ceil((double)this.getChatWidth() / d);
                RenderSystem.pushMatrix();
                RenderSystem.translatef(2.0f, 8.0f, 0.0f);
                RenderSystem.scaled(d, d, 1.0);
                double d2 = this.mc.gameSettings.chatOpacity * (double)0.9f + (double)0.1f;
                double d3 = this.mc.gameSettings.accessibilityTextBackgroundOpacity;
                double d4 = 9.0 * (this.mc.gameSettings.chatLineSpacing + 1.0);
                double d5 = -8.0 * (this.mc.gameSettings.chatLineSpacing + 1.0) + 4.0 * this.mc.gameSettings.chatLineSpacing;
                int n10 = 0;
                for (n8 = 0; n8 + this.scrollPos < this.drawnChatLines.size() && n8 < n3; ++n8) {
                    ChatLine<IReorderingProcessor> chatLine = this.drawnChatLines.get(n8 + this.scrollPos);
                    if (chatLine == null || (n7 = n - chatLine.getUpdatedCounter()) >= 200 && !bl) continue;
                    double d6 = bl ? 1.0 : NewChatGui.getLineBrightness(n7);
                    n6 = (int)(255.0 * d6 * d2);
                    n5 = (int)(255.0 * d6 * d3);
                    ++n10;
                    if (n6 <= 3) continue;
                    boolean bl2 = false;
                    double d7 = (double)(-n8) * d4;
                    matrixStack.push();
                    matrixStack.translate(0.0, 0.0, 50.0);
                    if (this.mc.gameSettings.ofChatBackground == 5) {
                        n9 = this.mc.fontRenderer.func_243245_a(chatLine.getLineString()) - 2;
                    }
                    if (this.mc.gameSettings.ofChatBackground != 3) {
                        NewChatGui.fill(matrixStack, -2, (int)(d7 - d4), 0 + n9 + 4, (int)d7, n5 << 24);
                    }
                    RenderSystem.enableBlend();
                    matrixStack.translate(0.0, 0.0, 50.0);
                    if (!this.mc.gameSettings.ofChatShadow) {
                        this.mc.fontRenderer.func_238422_b_(matrixStack, chatLine.getLineString(), 0.0f, (int)(d7 + d5), 0xFFFFFF + (n6 << 24));
                    } else {
                        this.mc.fontRenderer.func_238407_a_(matrixStack, chatLine.getLineString(), 0.0f, (int)(d7 + d5), 0xFFFFFF + (n6 << 24));
                    }
                    RenderSystem.disableAlphaTest();
                    RenderSystem.disableBlend();
                    matrixStack.pop();
                }
                if (!this.field_238489_i_.isEmpty()) {
                    n8 = (int)(128.0 * d2);
                    int n11 = (int)(255.0 * d3);
                    matrixStack.push();
                    matrixStack.translate(0.0, 0.0, 50.0);
                    NewChatGui.fill(matrixStack, -2, 0, n9 + 4, 9, n11 << 24);
                    RenderSystem.enableBlend();
                    matrixStack.translate(0.0, 0.0, 50.0);
                    this.mc.fontRenderer.func_243246_a(matrixStack, new TranslationTextComponent("chat.queue", this.field_238489_i_.size()), 0.0f, 1.0f, 0xFFFFFF + (n8 << 24));
                    matrixStack.pop();
                    RenderSystem.disableAlphaTest();
                    RenderSystem.disableBlend();
                }
                if (bl) {
                    n8 = 9;
                    RenderSystem.translatef(-3.0f, 0.0f, 0.0f);
                    int n12 = n4 * n8 + n4;
                    n7 = n10 * n8 + n10;
                    int n13 = this.scrollPos * n7 / n4;
                    int n14 = n7 * n7 / n12;
                    if (n12 != n7) {
                        n6 = n13 > 0 ? 170 : 96;
                        n5 = this.isScrolled ? 0xCC3333 : 0x3333AA;
                        NewChatGui.fill(matrixStack, 0, -n13, 2, -n13 - n14, n5 + (n6 << 24));
                        NewChatGui.fill(matrixStack, 2, -n13, 1, -n13 - n14, 0xCCCCCC + (n6 << 24));
                    }
                }
                RenderSystem.popMatrix();
            }
        }
    }

    private boolean func_238496_i_() {
        return this.mc.gameSettings.chatVisibility == ChatVisibility.HIDDEN;
    }

    private static double getLineBrightness(int n) {
        double d = (double)n / 200.0;
        d = 1.0 - d;
        d *= 10.0;
        d = MathHelper.clamp(d, 0.0, 1.0);
        return d * d;
    }

    public void clearChatMessages(boolean bl) {
        this.field_238489_i_.clear();
        this.drawnChatLines.clear();
        this.chatLines.clear();
        if (bl) {
            this.sentMessages.clear();
        }
    }

    public void clearClientChatMessages(boolean bl) {
        this.field_238489_i_.clear();
        for (ChatLine<ITextComponent> chatLine : this.chatLines) {
            if (!chatLine.isClient()) continue;
            this.chatLines.remove(chatLine);
        }
        for (ChatLine<Object> chatLine : this.drawnChatLines) {
            if (!chatLine.isClient()) continue;
            this.chatLines.remove(chatLine);
        }
    }

    public void printChatMessage(ITextComponent iTextComponent) {
        this.printChatMessageWithOptionalDeletion(iTextComponent, 0);
    }

    public void printChatMessageWithOptionalDeletion(ITextComponent iTextComponent, int n) {
        this.func_238493_a_(iTextComponent, n, this.mc.ingameGUI.getTicks(), true);
        LOGGER.info("[CHAT] {}", (Object)iTextComponent.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }

    public void printClient(ITextComponent iTextComponent, int n) {
        this.func_238493_a_Client(iTextComponent, n, this.mc.ingameGUI.getTicks(), true);
        LOGGER.info("[CHAT] {}", (Object)iTextComponent.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }

    private void func_238493_a_(ITextComponent iTextComponent, int n, int n2, boolean bl) {
        if (n != 0) {
            this.deleteChatLine(n);
        }
        int n3 = MathHelper.floor((double)this.getChatWidth() / this.getScale());
        List<IReorderingProcessor> list = RenderComponentsUtil.func_238505_a_(iTextComponent, n3, this.mc.fontRenderer);
        boolean bl2 = this.getChatOpen();
        for (IReorderingProcessor iReorderingProcessor : list) {
            if (bl2 && this.scrollPos > 0) {
                this.isScrolled = true;
                this.addScrollPos(1.0);
            }
            this.drawnChatLines.add(0, new ChatLine<IReorderingProcessor>(n2, iReorderingProcessor, n, false));
        }
        while (this.drawnChatLines.size() > 100) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }
        if (!bl) {
            this.chatLines.add(0, new ChatLine<ITextComponent>(n2, iTextComponent, n, false));
            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }

    private void func_238493_a_Client(ITextComponent iTextComponent, int n, int n2, boolean bl) {
        if (n != 0) {
            this.deleteChatLine(n);
        }
        int n3 = MathHelper.floor((double)this.getChatWidth() / this.getScale());
        List<IReorderingProcessor> list = RenderComponentsUtil.func_238505_a_(iTextComponent, n3, this.mc.fontRenderer);
        boolean bl2 = this.getChatOpen();
        for (IReorderingProcessor iReorderingProcessor : list) {
            if (bl2 && this.scrollPos > 0) {
                this.isScrolled = true;
                this.addScrollPos(1.0);
            }
            this.drawnChatLines.add(0, new ChatLine<IReorderingProcessor>(n2, iReorderingProcessor, n, true));
        }
        while (this.drawnChatLines.size() > 100) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }
        if (!bl) {
            this.chatLines.add(0, new ChatLine<ITextComponent>(n2, iTextComponent, n, true));
            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }

    public void refreshChat() {
        this.drawnChatLines.clear();
        this.resetScroll();
        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            ChatLine<ITextComponent> chatLine = this.chatLines.get(i);
            this.func_238493_a_(chatLine.getLineString(), chatLine.getChatLineID(), chatLine.getUpdatedCounter(), false);
        }
    }

    public List<String> getSentMessages() {
        return this.sentMessages;
    }

    public void addToSentMessages(String string) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(string)) {
            this.sentMessages.add(string);
        }
    }

    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    public void addScrollPos(double d) {
        this.scrollPos = (int)((double)this.scrollPos + d);
        int n = this.drawnChatLines.size();
        if (this.scrollPos > n - this.getLineCount()) {
            this.scrollPos = n - this.getLineCount();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    public boolean func_238491_a_(double d, double d2) {
        if (this.getChatOpen() && !this.mc.gameSettings.hideGUI && !this.func_238496_i_() && !this.field_238489_i_.isEmpty()) {
            double d3 = d - 2.0;
            double d4 = (double)this.mc.getMainWindow().getScaledHeight() - d2 - 40.0;
            if (d3 <= (double)MathHelper.floor((double)this.getChatWidth() / this.getScale()) && d4 < 0.0 && d4 > (double)MathHelper.floor(-9.0 * this.getScale())) {
                this.printChatMessage(this.field_238489_i_.remove());
                this.field_238490_l_ = System.currentTimeMillis();
                return false;
            }
            return true;
        }
        return true;
    }

    @Nullable
    public Style func_238494_b_(double d, double d2) {
        if (this.getChatOpen() && !this.mc.gameSettings.hideGUI && !this.func_238496_i_()) {
            double d3 = d - 2.0;
            double d4 = (double)this.mc.getMainWindow().getScaledHeight() - d2 - 40.0;
            d3 = MathHelper.floor(d3 / this.getScale());
            d4 = MathHelper.floor(d4 / (this.getScale() * (this.mc.gameSettings.chatLineSpacing + 1.0)));
            if (!(d3 < 0.0) && !(d4 < 0.0)) {
                int n;
                int n2 = Math.min(this.getLineCount(), this.drawnChatLines.size());
                if (d3 <= (double)MathHelper.floor((double)this.getChatWidth() / this.getScale()) && d4 < (double)(9 * n2 + n2) && (n = (int)(d4 / 9.0 + (double)this.scrollPos)) >= 0 && n < this.drawnChatLines.size()) {
                    ChatLine<IReorderingProcessor> chatLine = this.drawnChatLines.get(n);
                    return this.mc.fontRenderer.getCharacterManager().func_243239_a(chatLine.getLineString(), (int)d3);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    private boolean getChatOpen() {
        return this.mc.currentScreen instanceof ChatScreen;
    }

    public void deleteChatLine(int n) {
        this.drawnChatLines.removeIf(arg_0 -> NewChatGui.lambda$deleteChatLine$0(n, arg_0));
        this.chatLines.removeIf(arg_0 -> NewChatGui.lambda$deleteChatLine$1(n, arg_0));
    }

    public int getChatWidth() {
        int n = NewChatGui.calculateChatboxWidth(this.mc.gameSettings.chatWidth);
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        int n2 = (int)((double)(mainWindow.getFramebufferWidth() - 3) / mainWindow.getGuiScaleFactor());
        return MathHelper.clamp(n, 0, n2);
    }

    public int getChatHeight() {
        return NewChatGui.calculateChatboxHeight((this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused) / (this.mc.gameSettings.chatLineSpacing + 1.0));
    }

    public double getScale() {
        return this.mc.gameSettings.chatScale;
    }

    public static int calculateChatboxWidth(double d) {
        int n = 320;
        int n2 = 40;
        return MathHelper.floor(d * 280.0 + 40.0);
    }

    public static int calculateChatboxHeight(double d) {
        int n = 180;
        int n2 = 20;
        return MathHelper.floor(d * 160.0 + 20.0);
    }

    public int getLineCount() {
        return this.getChatHeight() / 9;
    }

    private long func_238497_j_() {
        return (long)(this.mc.gameSettings.chatDelay * 1000.0);
    }

    private void func_238498_k_() {
        long l;
        if (!this.field_238489_i_.isEmpty() && (l = System.currentTimeMillis()) - this.field_238490_l_ >= this.func_238497_j_()) {
            this.printChatMessage(this.field_238489_i_.remove());
            this.field_238490_l_ = l;
        }
    }

    public void removeChatMessages(ITextComponent iTextComponent) {
        this.drawnChatLines.removeIf(arg_0 -> NewChatGui.lambda$removeChatMessages$2(iTextComponent, arg_0));
        this.chatLines.removeIf(arg_0 -> NewChatGui.lambda$removeChatMessages$3(iTextComponent, arg_0));
    }

    public void func_238495_b_(ITextComponent iTextComponent) {
        if (this.mc.gameSettings.chatDelay <= 0.0) {
            this.printChatMessage(iTextComponent);
        } else {
            long l = System.currentTimeMillis();
            if (l - this.field_238490_l_ >= this.func_238497_j_()) {
                this.printChatMessage(iTextComponent);
                this.field_238490_l_ = l;
            } else {
                this.field_238489_i_.add(iTextComponent);
            }
        }
    }

    private static boolean lambda$removeChatMessages$3(ITextComponent iTextComponent, ChatLine chatLine) {
        return ((ITextComponent)chatLine.getLineString()).equals(iTextComponent);
    }

    private static boolean lambda$removeChatMessages$2(ITextComponent iTextComponent, ChatLine chatLine) {
        return ((IReorderingProcessor)chatLine.getLineString()).equals(iTextComponent);
    }

    private static boolean lambda$deleteChatLine$1(int n, ChatLine chatLine) {
        return chatLine.getChatLineID() == n;
    }

    private static boolean lambda$deleteChatLine$0(int n, ChatLine chatLine) {
        return chatLine.getChatLineID() == n;
    }
}

