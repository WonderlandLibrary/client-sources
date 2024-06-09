/*
 * Decompiled with CFR 0_122.
 */
package winter.console;

import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StringUtils;
import winter.console.cmds.Bclip;
import winter.console.cmds.Bind;
import winter.console.cmds.Command;
import winter.console.cmds.Friend;
import winter.console.cmds.Loadsong;
import winter.console.cmds.Pclip;
import winter.console.cmds.Rainbow;
import winter.console.cmds.Toggle;
import winter.console.cmds.Vclip;
import winter.console.cmds.Visible;
import winter.console.cmds.Watermark;
import winter.console.cmds.Waypoint;
import winter.console.cmds.blank;
import winter.console.cmds.help;

public class Console
extends GuiScreen {
    public static ArrayList<Command> cmds;
    private GuiTextField console;
    private boolean drag = false;
    private int x = 10;
    private int y = 10;
    private int dragX;
    private int dragY;
    public ConsoleText cText;

    public Console() {
        cmds = new ArrayList();
        cmds.add(new help());
        cmds.add(new Bind());
        cmds.add(new blank());
        cmds.add(new Toggle());
        cmds.add(new Friend());
        cmds.add(new Watermark());
        cmds.add(new Visible());
        cmds.add(new Waypoint());
        cmds.add(new Loadsong());
        cmds.add(new Bclip());
        cmds.add(new Vclip());
        cmds.add(new Rainbow());
        cmds.add(new Pclip());
        this.cText = new ConsoleText();
    }

    @Override
    public void initGui() {
        this.console = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, this.width / 2 - 110, 2, 220, 13);
        this.console.setMaxStringLength(500);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.console.drawTextBox();
        this.console.setFocused(true);
        this.console.setColors(-12303292, -7895161);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.dragX = mouseX - this.x;
        this.dragY = mouseY - this.y;
        if (mouseButton == 0 && this.isXButton(mouseX, mouseY)) {
            this.mc.displayGuiScreen(null);
        } else if (mouseButton == 0 && this.isDraggable(mouseX, mouseY)) {
            this.drag = true;
        } else if (mouseButton == 0 && this.isSend(mouseX, mouseY)) {
            for (Command cmd2 : cmds) {
                if (StringUtils.isNullOrEmpty(this.console.getText())) continue;
                cmd2.run(this.console.getText());
            }
            if (!StringUtils.isNullOrEmpty(this.console.getText()) && this.console.getText().equalsIgnoreCase("clear")) {
                this.cText.clear();
            }
            this.console.setText("");
        }
    }

    public boolean isDraggable(int x2, int y2) {
        if (x2 >= this.x && x2 <= this.x + 230 + 50 && y2 >= this.y && y2 <= this.y + 18) {
            return true;
        }
        return false;
    }

    public boolean isSend(int x2, int y2) {
        if (x2 >= this.x + 180 + 50 && x2 <= this.x + 225 + 50 && y2 >= this.y + 195 && y2 <= this.y + 208) {
            return true;
        }
        return false;
    }

    public boolean isXButton(int x2, int y2) {
        if (x2 >= this.x + 220 + 50 && x2 <= this.x + 225 + 50 && y2 >= this.y + 5 && y2 <= this.y + 11) {
            return true;
        }
        return false;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.drag = false;
    }

    public void updatePosition(int x2, int y2) {
        if (this.drag) {
            this.x = x2 - this.dragX;
            this.y = y2 - this.dragY;
        }
    }

    @Override
    public void updateScreen() {
        this.console.updateCursorCounter();
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (key == 28) {
            for (Command cmd2 : cmds) {
                if (StringUtils.isNullOrEmpty(this.console.getText())) continue;
                cmd2.run(this.console.getText());
            }
            if (!StringUtils.isNullOrEmpty(this.console.getText()) && this.console.getText().equalsIgnoreCase("clear")) {
                this.cText.clear();
            }
            this.console.setText("");
        } else {
            this.console.textboxKeyTyped(typedChar, key);
        }
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    public class ConsoleText {
        public String line1;
        public String line2;
        public String line3;
        public String line4;
        public String line5;
        public String line6;
        public String line7;
        public String line8;
        public String line9;
        public String line10;
        public String line11;
        public String line12;
        public String line13;
        public String line14;
        public String line15;
        public String line16;
        public String line17;

        public ConsoleText() {
            this.line1 = "";
            this.line2 = "";
            this.line3 = "";
            this.line4 = "";
            this.line5 = "";
            this.line6 = "";
            this.line7 = "";
            this.line8 = "";
            this.line9 = "";
            this.line10 = "";
            this.line11 = "";
            this.line12 = "";
            this.line13 = "";
            this.line14 = "";
            this.line15 = "";
            this.line16 = "";
            this.line17 = "";
        }

        public void render(int x2, int y2) {
            FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
            int renderY = y2 + 180;
            font.drawString(this.line1, x2 + 7, renderY, -1);
            font.drawString(this.line2, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line3, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line4, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line5, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line6, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line7, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line8, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line9, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line10, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line11, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line12, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line13, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line14, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line15, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line16, x2 + 7, renderY -= 10, -1);
            font.drawString(this.line17, x2 + 7, renderY -= 10, -1);
            renderY -= 10;
        }

        public void clear() {
            this.line17 = "";
            this.line16 = "";
            this.line15 = "";
            this.line14 = "";
            this.line13 = "";
            this.line12 = "";
            this.line11 = "";
            this.line10 = "";
            this.line9 = "";
            this.line8 = "";
            this.line7 = "";
            this.line6 = "";
            this.line5 = "";
            this.line4 = "";
            this.line3 = "";
            this.line2 = "";
            this.line1 = "";
        }

        public void send(String text) {
            this.line17 = this.line16;
            this.line16 = this.line15;
            this.line15 = this.line14;
            this.line14 = this.line13;
            this.line13 = this.line12;
            this.line12 = this.line11;
            this.line11 = this.line10;
            this.line10 = this.line9;
            this.line9 = this.line8;
            this.line8 = this.line7;
            this.line7 = this.line6;
            this.line6 = this.line5;
            this.line5 = this.line4;
            this.line4 = this.line3;
            this.line3 = this.line2;
            this.line2 = this.line1;
            this.line1 = text;
        }
    }

}

