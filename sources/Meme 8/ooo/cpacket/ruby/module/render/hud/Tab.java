package ooo.cpacket.ruby.module.render.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

import static ooo.cpacket.ruby.module.render.hud.TabGui.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glTranslated;

public class Tab {
    private List<SubTab> subTabs = new ArrayList<>();
    private String text;

    public Tab(String text) {
        this.text = text;
    }

    public void addSubTab(SubTab subTab) {
        subTabs.add(subTab);
    }

    public List<SubTab> getSubTabs() {
        return subTabs;
    }

    public void renderSubTabs(int x, int y, int selectedSubTab) {

        glTranslated(x, y, 0);

        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

        int height = (font.FONT_HEIGHT + OFFSET) * subTabs.size();

        int width = 0;

        for (SubTab tab : subTabs) {
            if (font.getStringWidth(tab.getText()) > width) {
                width = font.getStringWidth(tab.getText());
            }
        }

        width += 2 + 2;

        drawRect(GL_QUADS, 0, 0, width, height, BACKGROUND.getRGB());

        glLineWidth(1.0f);
        drawRect(GL_LINE_LOOP, 0, 0, width, height, BORDER.getRGB());

        int offset = 2;

        int i = 0;

        for (SubTab tab : subTabs) {
            if (selectedSubTab == i) {
                drawRect(GL_QUADS, 0, offset - 2, width, offset + font.FONT_HEIGHT + OFFSET - 1, SELECTED.getRGB());
            }

            font.drawString(tab.getText(), 2, offset, FOREGROUND.getRGB());
            offset += font.FONT_HEIGHT + OFFSET;
            i++;
        }

        glTranslated(-x, -y, 0);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
