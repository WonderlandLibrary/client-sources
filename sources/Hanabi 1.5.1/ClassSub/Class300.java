package ClassSub;

import java.awt.*;
import org.jetbrains.annotations.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Class300<T>
{
    static final int OFFSET = 3;
    @NotNull
    static Color BACKGROUND;
    @NotNull
    static Color BORDER;
    @NotNull
    static Color SELECTED;
    static Color FOREGROUND;
    @NotNull
    private List<Class245<T>> tabs;
    private int selectedTab;
    private int selectedSubTab;
    
    
    public Class300() {
        this.tabs = new ArrayList<Class245<T>>();
        this.selectedTab = 0;
        this.selectedSubTab = -1;
    }
    
    public static void drawRect(final int n, int n2, int n3, int n4, int n5, final int n6) {
        if (n2 < n4) {
            final int n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        if (n3 < n5) {
            final int n8 = n3;
            n3 = n5;
            n5 = n8;
        }
        final float n9 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n6 & 0xFF) / 255.0f;
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(n10, n11, n12, n9);
        getWorldRenderer.begin(n, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos((double)n2, (double)n5, 0.0).endVertex();
        getWorldRenderer.pos((double)n4, (double)n5, 0.0).endVertex();
        getWorldRenderer.pos((double)n4, (double)n3, 0.0).endVertex();
        getWorldRenderer.pos((double)n2, (double)n3, 0.0).endVertex();
        getInstance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public void addTab(final Class245<T> class245) {
        this.tabs.add(class245);
    }
    
    public void render(final int n, final int n2) {
        GL11.glTranslated((double)n, (double)n2, 0.0);
        final FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        final int n3 = (fontRendererObj.FONT_HEIGHT + 3) * this.tabs.size();
        int getStringWidth = 0;
        for (final Class245<T> class245 : this.tabs) {
            if (fontRendererObj.getStringWidth(class245.getText()) > getStringWidth) {
                getStringWidth = fontRendererObj.getStringWidth(class245.getText());
            }
        }
        getStringWidth += 4;
        drawRect(7, 0, 0, getStringWidth, n3, Class300.BACKGROUND.getRGB());
        int n4 = 2;
        int n5 = 0;
        for (final Class245<T> class246 : this.tabs) {
            if (this.selectedTab == n5) {
                drawRect(7, 0, n4 - 2, getStringWidth, n4 + fontRendererObj.FONT_HEIGHT + 3 - 2, Class300.SELECTED.getRGB());
                if (this.selectedSubTab != -1) {
                    class246.renderSubTabs(getStringWidth, n4 - 2, this.selectedSubTab);
                }
            }
            fontRendererObj.drawString(class246.getText(), 2, n4, Class300.FOREGROUND.getRGB());
            n4 += fontRendererObj.FONT_HEIGHT + 3;
            ++n5;
        }
        GL11.glLineWidth(1.0f);
        drawRect(2, 0, 0, getStringWidth, n3, Class300.BORDER.getRGB());
        GL11.glTranslated((double)(-n), (double)(-n2), 0.0);
    }
    
    public void handleKey(final int n) {
        if (n == 208) {
            if (this.selectedSubTab == -1) {
                ++this.selectedTab;
                if (this.selectedTab >= this.tabs.size()) {
                    this.selectedTab = 0;
                }
            }
            else {
                ++this.selectedSubTab;
                if (this.selectedSubTab >= this.tabs.get(this.selectedTab).getSubTabs().size()) {
                    this.selectedSubTab = 0;
                }
            }
        }
        else if (n == 200) {
            if (this.selectedSubTab == -1) {
                --this.selectedTab;
                if (this.selectedTab < 0) {
                    this.selectedTab = this.tabs.size() - 1;
                }
            }
            else {
                --this.selectedSubTab;
                if (this.selectedSubTab < 0) {
                    this.selectedSubTab = this.tabs.get(this.selectedTab).getSubTabs().size() - 1;
                }
            }
        }
        else if (n == 203) {
            this.selectedSubTab = -1;
        }
        else if (this.selectedSubTab == -1 && (n == 28 || n == 205)) {
            this.selectedSubTab = 0;
        }
        else if (n == 28 || n == 205) {
            this.tabs.get(this.selectedTab).getSubTabs().get(this.selectedSubTab).press();
        }
    }
    
    static {
        Class300.BACKGROUND = new Color(0, 0, 0, 175);
        Class300.BORDER = new Color(0, 0, 0, 255);
        Class300.SELECTED = new Color(38, 164, 78, 200);
        Class300.FOREGROUND = Color.white;
    }
}
