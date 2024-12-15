package com.alan.clients.module.impl.render.chat;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.GuiKeyBoardEvent;
import com.alan.clients.event.impl.input.KeyboardInputEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.render.Interface;
import com.alan.clients.ui.theme.Themes;
import com.alan.clients.util.EvictingList;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.gui.ScrollUtil;
import com.alan.clients.util.gui.textbox.TextAlign;
import com.alan.clients.util.gui.textbox.TextBox;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.DragValue;
import com.alan.clients.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.optifine.gui.GuiChatOF;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.alan.clients.layer.Layers.*;
import static com.alan.clients.util.animation.Easing.*;

@Getter
@ModuleInfo(aliases = {"module.render.chat.name"}, description = "module.render.chat.description", category = Category.RENDER, autoEnabled = true, allowDisable = false)
public final class Chat extends Module {
    private final NumberValue openHeight = new NumberValue("Open Height", this, 130, 0, 200, 1);
    private final NumberValue widthChat = new NumberValue("Width", this, 320, 40, 320, 1);

    private final NumberValue closedHeight = new NumberValue("Max Closed Height", this, 130, 0, 500, 1);
    private final NumberValue disappear = new NumberValue("Message disappearance speed", this, 5000, 0, 5000, 1);
    private final BooleanValue background = new BooleanValue("Background", this, true);
    private final BooleanValue hidePlayers = new BooleanValue("Hide Player Source Messages", this, false);

    private final DragValue structure = new DragValue("", this, new Vector2d(200, 200), false, true);
    private final Animation animation = new Animation(Easing.EASE_OUT_EXPO, 500);
    private final Animation inputAnimation = new Animation(Easing.EASE_OUT_ELASTIC, 400);
    private final Animation inputOpacity = new Animation(LINEAR, 400);
    private final Animation scaleAnimation = new Animation(EASE_OUT_EXPO, 500);
    private final ScrollUtil scroll = new ScrollUtil();

    private final Font chatFont = mc.fontRendererObj;
    private final Font inputFont = mc.fontRendererObj;

    private TextBox textField = new TextBox(new Vector2d(0, 0), inputFont, Color.WHITE, TextAlign.LEFT, "", 1000f, "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()-_+=[{]};:.>,</?|  ' \" ` ~");
    private int lineCount, recentLineCount;
    private float lineHeight;
    public boolean clicked, open;
    private StopWatch stopWatch = new StopWatch();
    private EvictingList<String> recentMessages = new EvictingList<>(20);
    private ArrayList<String> matches;
    private Interface interfaceModule;

    public Chat() {
        mc.ingameGUI.persistantChatGUI = new RiseGuiNewChat(mc);
    }

    @EventLink
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if (event.getCharacter() == '/' || event.getCharacter() == '.') {
            textField.setSelected(true);
            textField.key(event.getCharacter(), event.getKeyCode());
        }
    };

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S3APacketTabComplete) {
            matches = new ArrayList<>(Arrays.asList(((S3APacketTabComplete) packet).getMatches()));

            for (String match : matches) ChatUtil.display(match);
        }
    };

    @EventLink
    public final Listener<GuiKeyBoardEvent> onGuiKey = event -> {
        switch (event.getKeyCode()) {
            // Enter
            case 28:
                if (textField.getText().isEmpty()) return;
                mc.thePlayer.sendChatMessage(textField.getText());
                recentMessages.remove(textField.getText());
                recentMessages.add(textField.getText());
                textField.text = "";
                mc.displayGuiScreen(null);
                break;

            // Down arrow
            case 200:
                List<String> recentMessages = new ArrayList<>(this.recentMessages);
                Collections.reverse(recentMessages);
                int index = recentMessages.indexOf(textField.text);
                if (recentMessages.size() > index + 1) {
                    textField.text = recentMessages.get(index + 1);
                    textField.cursor = textField.text.length();
                }
                break;

            // Up Arrow
            case 208:
                List<String> recentMessages2 = new ArrayList<>(this.recentMessages);
                Collections.reverse(recentMessages2);
                int indexDown = recentMessages2.indexOf(textField.text);
                if (indexDown > 0) {
                    textField.text = recentMessages2.get(indexDown - 1);
                    textField.cursor = textField.text.length();
                }
                break;

            // Tab
            case 15:
                if (matches == null) {
                    String text = getTextField().getText();
                    if (!text.startsWith(".") && !text.isEmpty()) { // !p_146405_1_.startsWith(".") added to prevent autocomplete of commands
                        BlockPos blockpos = null;

                        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                            blockpos = this.mc.objectMouseOver.getBlockPos();
                        }

                        this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(text, blockpos));
                    }

                    matches = new ArrayList<>();
                } else if (!matches.isEmpty()) {
                    String[] parts = textField.getText().split(" ");
                    int indexof = matches.indexOf(parts[parts.length - 1]);

                    parts[parts.length - 1] = matches.get((matches.size() > indexof + 1) ? indexof + 1 : 0);
                    textField.text = String.join(" ", parts);

                    textField.cursor = textField.getText().length();
                }
                break;
        }

        textField.selected = open;
        textField.key(event.getCharacter(), event.getKeyCode());
    };

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        this.renderChat();

        this.renderInput();
    };

    public void renderChat() {
        getTheme();
        if (interfaceModule == null) interfaceModule = getModule(Interface.class);
        boolean rise = interfaceModule.getInformationType().getValue().getName().equals("Rise");

        open = mc.currentScreen instanceof GuiChatOF;
        lineHeight = chatFont.height();

        float expandedHeight = Math.min((open ? openHeight : closedHeight).getValue().intValue(), lineHeight * lineCount + (lineCount == 0 ? 0 : 7));

        scaleAnimation.run(Math.min(expandedHeight, (lineHeight * (open ? expandedHeight : recentLineCount)) + (lineHeight * (open ? expandedHeight : recentLineCount) == 0 ? 0 : 7)));
        structure.scale = new Vector2d(widthChat.getValue().doubleValue(), scaleAnimation.getValue());

        structure.position = new Vector2d(getTheme().getPadding(), mc.scaledResolution.getScaledHeight() - 10 - 20 - structure.scale.y);
        Vector2d position = new Vector2d(structure.position.x + 5, structure.position.y + structure.scale.y - 1.5);
        Vector2d startPosition = new Vector2d(position.getX(), position.getY());
        List<ChatLine> lines = new ArrayList<>(mc.ingameGUI.getChatGUI().drawnChatLines);

        double round = Math.min(structure.scale.y / 5f, 6);

        if (background.getValue()) {
            if (rise) {
                getLayer(REGULAR, 1).add(() -> RenderUtil.roundedRectangle(structure.position.x, structure.position.y, structure.scale.x, structure.scale.y, round, Themes.getBackgroundShade()));
                getLayer(BLOOM).add(() -> RenderUtil.roundedRectangle(structure.position.x + 1, structure.position.y + 1, structure.scale.x - 2, structure.scale.y - 2, round, getTheme().getDropShadow()));
                getLayer(BLUR).add(() -> RenderUtil.roundedRectangle(structure.position.x, structure.position.y, structure.scale.x, structure.scale.y, round, Color.BLACK));
            } else {
                getLayer(REGULAR, 1).add(() -> RenderUtil.rectangle(structure.position.x, structure.position.y, structure.scale.x, structure.scale.y, new Color(0, 0, 0, 130)));
            }
        }

        // Chat
        getLayer(REGULAR, 1).add(() -> {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.scissor(structure.position.x, structure.position.y, structure.scale.x, structure.scale.y);

            animation.run(0);
            position.setY(position.getY() + animation.getValue() - scroll.getScroll());
            scroll.onRender(open);
            scroll.setReverse(true);
            scroll.setMax((-lineHeight * lines.size()) + structure.scale.y - 6);

            if (!open) scroll.setTarget(0);

            if (lines.size() > 150) {
                List<ChatLine> linesPointer = mc.ingameGUI.getChatGUI().drawnChatLines;

                while (linesPointer.size() > 100) {
                    linesPointer.remove(linesPointer.getLast());
                }
            }

            update:
            {
                if (lineCount < lines.size()) {
                    if (hidePlayers.getValue()) {
                        mc.ingameGUI.getChatGUI().drawnChatLines.removeIf(line -> line.getChatComponent().getFormattedText().contains(":"));

                        if (lineCount >= mc.ingameGUI.getChatGUI().drawnChatLines.size()) {
                            break update;
                        }
                    }
                    animation.setValue(animation.getValue() + ((lines.size() - lineCount) * lineHeight));
                    animation.run(animation.getValue());
                    recentLineCount += lines.size() - lineCount;
                    recentLineCount = (int) Math.max(Math.min((closedHeight.getValue().intValue() / lineHeight), recentLineCount), 0);
                }
            }

            lineCount = lines.size();

            if (stopWatch.finished(disappear.getValue().intValue()) || recentLineCount == 0) {
                stopWatch.reset();
                recentLineCount = Math.max(0, recentLineCount - 1);
            }

            IChatComponent chatComponent2 = null;

            // Rendering chat
            for (ChatLine chatLine : lines) {
                position.setX(startPosition.getX());
                position.setY(position.getY() - lineHeight);

                if (position.y < structure.position.y - lineHeight + (open ? 0 : 6) || position.y > structure.position.y + structure.scale.y) {
                    continue;
                }

                for (IChatComponent chatComponent : chatLine.getChatComponent()) {
                    String text = chatComponent.getUnformattedTextForChat();
                    EnumChatFormatting color = chatComponent.getChatStyle().getColor() == null ? EnumChatFormatting.RESET : chatComponent.getChatStyle().getColor();
                    double componentWidth = chatFont.drawWithShadow(color + text, position.getX(), position.getY(), 16777215) - position.x;

                    // Hover & Click
                    if (open && MouseUtil.isHovered(position.x, position.y, componentWidth, lineHeight)) {
                        chatComponent2 = chatComponent;
                    }

                    position.x += componentWidth;
                }
            }

            if (chatComponent2 != null) {
                Vector2d mouse = MouseUtil.mouse();
                mc.currentScreen.handleComponentHover(chatComponent2, (int) mouse.getX(), (int) mouse.getY());
                if (!clicked && Mouse.isButtonDown(0)) mc.currentScreen.handleComponentClick(chatComponent2);
                clicked = Mouse.isButtonDown(0);
            }
            // Scrollbar
            if (background.getValue()) {
                scroll.renderScrollBar(new Vector2d(structure.position.x + structure.scale.x - 5, structure.position.y + 5), structure.scale.y - 10);
            }

            // Scissoring
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        });
    }

    public void renderInput() {
        getTheme();
        boolean rise = interfaceModule.getInformationType().getValue().getName().equals("Rise");

        // ChatBox
        if (rise) {
            inputAnimation.setDuration(!open ? 300 : 850);
            inputAnimation.setEasing(!open ? EASE_IN_EXPO : EASE_OUT_ELASTIC);
        } else {
            inputAnimation.setDuration(0);
            inputAnimation.setEasing(!open ? EASE_IN_EXPO : EASE_OUT_ELASTIC);
        }
        inputAnimation.run(!open ? 0 : 1);
        inputOpacity.run(open ? 255 : 0);

        double scale = (inputAnimation.getValue() > 1) ? (1 + (inputAnimation.getValue() - 1) * 0.4) : inputAnimation.getValue();

        textField.setEmptyText("");

        if (inputAnimation.getValue() <= 0) return;

        Vector2d inputPosition = new Vector2d(getTheme().getPadding(), mc.scaledResolution.getScaledHeight() - getTheme().getPadding() - Fonts.MAIN.get(20, Weight.REGULAR).height() - 3);
        Vector2d inputScale = new Vector2d(structure.scale.x, inputFont.height() + 7.5);

        Runnable glOpen = () -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((inputPosition.x + inputScale.x / 2) * (1 - scale), (inputPosition.y + inputScale.y / 2) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);
        };

        if (rise) {
            getLayer(REGULAR, 1).add(() -> {
                glOpen.run();
                RenderUtil.roundedRectangle(inputPosition.x, inputPosition.y, inputScale.x, inputScale.y, 6, ColorUtil.withAlpha(Themes.getBackgroundShade(), Math.min((int) inputOpacity.getValue(), getTheme().getBackgroundShade().getAlpha())));

                GlStateManager.popMatrix();
            });

            getLayer(BLOOM).add(() -> {
                glOpen.run();
                RenderUtil.roundedRectangle(inputPosition.x, inputPosition.y, inputScale.x, inputScale.y, 6.5, getTheme().getDropShadow());
                GlStateManager.popMatrix();
            });

            getLayer(BLUR).add(() -> {
                glOpen.run();
                RenderUtil.roundedRectangle(inputPosition.x, inputPosition.y, inputScale.x, inputScale.y, 6, Color.BLACK);
                GlStateManager.popMatrix();
            });
        } else {
            getLayer(REGULAR, 1).add(() -> {
                glOpen.run();
                RenderUtil.rectangle(inputPosition.x, inputPosition.y, inputScale.x, inputScale.y, new Color(0, 0, 0, Math.min((int) inputOpacity.getValue(), 130)));

                GlStateManager.popMatrix();
            });
        }

        textField.setColor(ColorUtil.withAlpha(Color.WHITE, (int) inputOpacity.getValue()));
        textField.setFontRenderer(inputFont);
        textField.setPosition(new Vector2d(getTheme().getPadding() + 5, mc.scaledResolution.getScaledHeight() - inputFont.height() - getTheme().getPadding()));

        getLayer(REGULAR, 1).add(() -> {
            glOpen.run();
            textField.draw();
            GlStateManager.popMatrix();

            if (!open) textField.text = "";
        });

        if (!open) {
            textField.text = "";
            matches = null;
        }
    }
}