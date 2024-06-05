package ClassSub;

import org.jetbrains.annotations.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class Class245<T>
{
    @NotNull
    private List<Class176<T>> subTabs;
    private String text;
    
    
    public Class245(final String text) {
        this.subTabs = new ArrayList<Class176<T>>();
        this.text = text;
    }
    
    public void addSubTab(final Class176<T> class176) {
        this.subTabs.add(class176);
    }
    
    @NotNull
    public List<Class176<T>> getSubTabs() {
        return this.subTabs;
    }
    
    public void renderSubTabs(final int n, final int n2, final int n3) {
        GL11.glTranslated((double)n, (double)n2, 0.0);
        final FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        final int n4 = (fontRendererObj.FONT_HEIGHT + 3) * this.subTabs.size();
        int getStringWidth = 0;
        for (final Class176<T> class176 : this.subTabs) {
            if (fontRendererObj.getStringWidth(class176.getText()) > getStringWidth) {
                getStringWidth = fontRendererObj.getStringWidth(class176.getText());
            }
        }
        getStringWidth += 4;
        Class300.drawRect(7, 0, 0, getStringWidth, n4, Class300.BACKGROUND.getRGB());
        GL11.glLineWidth(1.0f);
        Class300.drawRect(2, 0, 0, getStringWidth, n4, Class300.BORDER.getRGB());
        int n5 = 2;
        int n6 = 0;
        for (final Class176<T> class177 : this.subTabs) {
            if (n3 == n6) {
                Class300.drawRect(7, 0, n5 - 2, getStringWidth, n5 + fontRendererObj.FONT_HEIGHT + 3 - 1, Class300.SELECTED.getRGB());
            }
            fontRendererObj.drawString(class177.getText(), 2, n5, Class300.FOREGROUND.getRGB());
            n5 += fontRendererObj.FONT_HEIGHT + 3;
            ++n6;
        }
        GL11.glTranslated((double)(-n), (double)(-n2), 0.0);
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
}
