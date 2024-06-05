package de.tired.api.guis.guipainting;

import de.tired.api.extension.Extension;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.api.guis.guipainting.enums.LineType;
import de.tired.api.guis.guipainting.enums.Tools;
import de.tired.api.guis.guipainting.features.Positions;
import de.tired.api.guis.guipainting.features.Text;
import de.tired.api.util.render.Scissoring;
import de.tired.api.util.font.CustomFont;
import de.tired.api.util.math.Vec;
import de.tired.interfaces.FHook;
import de.tired.shaderloader.ShaderRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiPainting extends GuiScreen {

    private Vec startPosition, endPosition, endPosStatic;

    private final ArrayList<Positions> positions = new ArrayList<>();

    private final ArrayList<Text> texts = new ArrayList<>();

    private int posX, posY, lastPosX, lastPosY;

    public boolean isSomethingDragging;

    private Tools currentTool;

    private LineType lineType;

    public boolean allSelected = false;

    public static boolean typing = false;

    private int thickness = 1;

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            thickness += 1;
        }
        if (button.id == 2) {
            thickness -= 1;
        }

        if (button.id == 3) {
            currentTool = Tools.ERASER;
        }

        if (button.id == 4) {
            currentTool = Tools.PENCIL;
        }

        if (button.id == 5) {
            lineType = LineType.FEEL_FREE;
        }

        if (button.id == 6) {
            lineType = LineType.STATIC_LINE;
        }

        if (button.id == 15) {
            texts.add(new Text("", 2, 2, true));
        }

        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        this.lastPosY = posY;
        this.lastPosX = posX;

        if (thickness < 1) {
            thickness = 1;
        }

        if (currentTool == null) {
            currentTool = Tools.PENCIL;
        }

        if (lineType == null) {
            lineType = LineType.FEEL_FREE;
        }

        ShaderRenderer.startBlur();
        Gui.drawRect(20, 20, width - 20, height - 20, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur();
        Scissoring.SCISSORING.startScissor();
        Scissoring.SCISSORING.scissorOtherWay(20, 20, width + 20, height - 20);

        for (Positions positions : positions) {
            if (lineType == LineType.STATIC_LINE) {
                if (positions.endPosStatic != null) {
                    RenderProcessor.drawLine(positions.startPos.x, positions.startPos.y, 0, positions.endPosStatic.x, positions.endPosStatic.y, 0, Color.WHITE, positions.thickness);
                }
            } else {
                RenderProcessor.drawLine(positions.startPos.x, positions.startPos.y, positions.startPos.z, positions.endPos.x, positions.endPos.y, positions.endPos.z, Color.WHITE, positions.thickness);
            }
        }
        Scissoring.SCISSORING.disableScissor();


        for (Text text : texts) {
            if (isOver(text.x, text.y, FHook.fontRenderer.getStringWidth(text.text), 6, posX, posY)) {
                if (Mouse.isButtonDown(0)) {
                    if (!isSomethingDragging) {
                        text.drag = true;
                    }
                }
            }
            if (text.drag) {
                text.x = posX;
                text.y = posY;
            }
        }

        boolean isOver = isOver(20, 20, width - 20, height - 20, mouseX, mouseY);

        if (isOver) {
            if (Mouse.isButtonDown(0)) {
                if (currentTool == Tools.ERASER) {
                    isSomethingDragging = true;
                    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderObjectCircle(mouseX, mouseY, 22, new Color(20, 20, 20).getRGB());
                    if (positions.size() > 0) {
                        positions.removeIf(positions2 -> isOver((int) positions2.endPos.x, (int) positions2.endPos.y, 55, 55, mouseX, mouseY));
                    }
                }
            }
        }

        for (Text text : texts) {
            FHook.fontRenderer.drawStringWithShadow(text.text, text.x, text.y, -1);
        }

        this.posY = mouseY;
        this.posX = mouseX;

        if (isOver) {
            startPosition = new Vec(posX, posY, 1);
        }


        if (isOver) {
            endPosition = new Vec(lastPosX, lastPosY, 1);
        }


        if (isOver) {
            if (Mouse.isButtonDown(0) && currentTool == Tools.PENCIL) {
                positions.add(new Positions(startPosition, endPosition, endPosStatic, thickness));

            }
        }

        FHook.fontRenderer.drawString("Thickness", 31, 25, -1);

        FHook.fontRenderer.drawString("Tool", 192, 25, -1);

        FHook.fontRenderer.drawString("LineType", 180, 45, -1);

        FHook.fontRenderer.drawString("Current tool: " + currentTool, calculateMiddle("current tool: " + currentTool, FHook.fontRenderer, 20, width - 20), 30, -1);

        FHook.fontRenderer.drawString("Thickness: " + thickness, calculateMiddle("Thickness: " + thickness, FHook.fontRenderer, 20, width - 20), 50, -1);

        FHook.fontRenderer.drawString("LineType: " + lineType, calculateMiddle("LineType: " + lineType, FHook.fontRenderer, 20, width - 20), 60, -1);


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public int calculateMiddle(String text, CustomFont fontRenderer, double x, double widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (Text text : texts) {
            if (texts.isEmpty()) return;
            typing = true;

            if (keyCode == Keyboard.KEY_RETURN) {
                text.drag = false;
            }

            if (text.drag) {

                Keyboard.enableRepeatEvents(true);
                if (typing) {
                    if (GuiScreen.isKeyComboCtrlA(keyCode) && !text.text.isEmpty()) {
                        this.allSelected = true;
                    }
                    if (allSelected) {
                        if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !text.text.isEmpty()) {
                            text.text = "";
                            allSelected = false;
                        }
                    } else if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !text.text.isEmpty()) {
                        text.text = text.text.substring(0, text.text.length() - 1);
                    } else if (keyCode == Keyboard.KEY_RETURN) {
                        typing = false;

                    } else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                        text.text = text.text + typedChar;
                    } else if (GuiScreen.isKeyComboCtrlV(keyCode)) {
                        text.text = text.text + GuiScreen.getClipboardString();

                    }
                }
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(1, 20, 20, 10, 10, "+"));
        this.buttonList.add(new GuiButton(2, 80, 20, 10, 10, "-"));

        this.buttonList.add(new GuiButton(15, 330, 25, 50, 10, "Add Text"));

        this.buttonList.add(new GuiButton(3, 230, 20, 50, 10, "Eraser"));

        this.buttonList.add(new GuiButton(4, 120, 20, 50, 10, "Pencil"));


        this.buttonList.add(new GuiButton(5, 230, 40, 50, 10, "FREE"));

        this.buttonList.add(new GuiButton(6, 120, 40, 50, 10, "STATIC"));


        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (endPosStatic == null) {
            endPosStatic = new Vec(mouseX, mouseY, 1);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        boolean isOver = isOver(20, 20, width - 20, height - 20, mouseX, mouseY);

        for (Text text : texts) {
            if (texts.isEmpty()) return;
            if (isOver(text.x, text.y, FHook.fontRenderer.getStringWidth(text.text), 10, posX, posY)) {
                if (text.drag) {
                    text.drag = false;
                }
            }
        }

        if (isOver) {
            endPosStatic = new Vec(mouseX, mouseY, 1);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }


    public boolean isOver(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

}
