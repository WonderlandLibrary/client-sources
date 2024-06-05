package ClassSub;

import cn.Hanabi.value.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import java.awt.*;

public class Class91 extends Class48<Value>
{
    private boolean isSelected;
    private int width;
    private int height;
    
    
    public Class91(final Value value) {
        super(-1, -1, 0, 0, value);
        this.isSelected = false;
        this.width = 0;
        try {
            this.height = this.fontRenderer.FONT_HEIGHT + 4;
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void draw() {
        GL11.glPushMatrix();
        GL11.glTranslated((double)(this.x + 2), (double)this.y, 0.0);
        final Rectangle rectangle = new Rectangle(this.x, this.y, this.width, this.height);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2884);
        int n = 0;
        if (this.isSelected) {
            final String[] array = ((Value)this.value).getModes();
            for (int i = 0; i < array.length - 1; ++i) {
                n += this.fontRenderer.FONT_HEIGHT + 2;
            }
            n += 2;
        }
        Class145.setColor(Class8.PANEL_SECONDARY_COLOR);
        GL11.glLineWidth(2.0f);
        Class145.drawRect(2, 0, 0, rectangle.width, rectangle.height + n, Class8.PANEL_SECONDARY_COLOR.getRGB());
        Class145.setColor(Class8.PANEL_MAIN_COLOR);
        Gui.drawRect(0, 0, rectangle.width, rectangle.height + n, Class145.toRGBA(Class8.PANEL_MAIN_COLOR));
        final Point calculateMouseLocation = Class170.calculateMouseLocation();
        GL11.glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
        if (rectangle.contains(calculateMouseLocation)) {
            Gui.drawRect(0, 0, rectangle.width, rectangle.height, Class145.toRGBA(new Color(0, 0, 0, Mouse.isButtonDown(0) ? 125 : 70)));
        }
        else if (this.isSelected && calculateMouseLocation.x >= rectangle.x && calculateMouseLocation.x <= rectangle.x + rectangle.width) {
            int height = this.height;
            final String[] array2 = ((Value)this.value).getModes();
            for (int j = 0; j < array2.length; ++j) {
                if (j != this.getSelectedIndex()) {
                    int n2 = this.fontRenderer.FONT_HEIGHT + 2;
                    Label_0444: {
                        Label_0441: {
                            if (this.getSelectedIndex() == 0) {
                                if (j == 1) {
                                    break Label_0441;
                                }
                            }
                            else if (j == 0) {
                                break Label_0441;
                            }
                            if (this.getSelectedIndex() == array2.length - 1) {
                                if (j != array2.length - 2) {
                                    break Label_0444;
                                }
                            }
                            else if (j != array2.length - 1) {
                                break Label_0444;
                            }
                        }
                        ++n2;
                    }
                    if (calculateMouseLocation.y >= rectangle.y + height && calculateMouseLocation.y <= rectangle.y + height + n2) {
                        Gui.drawRect(0, height, rectangle.width, rectangle.height + height - 1, Class145.toRGBA(new Color(0, 0, 0, Mouse.isButtonDown(0) ? 125 : 70)));
                        break;
                    }
                    height += n2;
                }
            }
        }
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glLineWidth(2.0f);
        if (this.isSelected) {
            GL11.glBegin(1);
            GL11.glVertex2d(2.0, (double)rectangle.height);
            GL11.glVertex2d((double)(rectangle.width - 2), (double)rectangle.height);
            GL11.glEnd();
        }
        Class145.setColor(Color.white);
        this.fontRenderer.drawString(((Value)this.value).getModes()[((Value)this.value).getCurrentMode()], 2, rectangle.height / 2 - this.fontRenderer.FONT_HEIGHT / 2, Class145.toRGBA(Color.white));
        if (this.isSelected) {
            int n3 = rectangle.height + 2;
            final String[] array3 = ((Value)this.value).getModes();
            for (int k = 0; k < array3.length; ++k) {
                if (k != this.getSelectedIndex()) {
                    Class145.setColor(Color.white);
                    this.fontRenderer.drawString(array3[k], 2, n3, Class145.toRGBA(Color.white));
                    n3 += this.fontRenderer.FONT_HEIGHT + 2;
                }
            }
        }
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    private int getSelectedIndex() {
        return ((Value)this.value).getCurrentMode();
    }
    
    @Override
    public void update() {
        int max = 0;
        final String[] array = ((Value)this.value).getModes();
        for (int length = array.length, i = 0; i < length; ++i) {
            max = Math.max(max, this.fontRenderer.getStringWidth(array[i]));
        }
        this.width = max + 8 + this.fontRenderer.FONT_HEIGHT;
        super.width = max + 12 + this.fontRenderer.FONT_HEIGHT;
        if (this.isSelected) {
            super.height = 0;
            for (final String s : ((Value)this.value).getModes()) {
                this.height += this.fontRenderer.FONT_HEIGHT + 2;
            }
            super.height = this.height;
        }
        else {
            super.height = this.fontRenderer.FONT_HEIGHT + 4;
        }
        this.height = this.fontRenderer.FONT_HEIGHT + 4;
    }
    
    @Override
    public boolean onMouseClick(final int n, final int n2, final int n3) {
        final Point point = new Point(n, n2);
        final Rectangle rectangle = new Rectangle(this.getX(), this.getY(), this.width, this.height);
        if (n3 != 0) {
            return false;
        }
        if (rectangle.contains(point)) {
            this.isSelected = !this.isSelected;
            return true;
        }
        if (point.x <= rectangle.getWidth() + this.getX() && this.isSelected) {
            int n4 = (int)(rectangle.getHeight() + this.getY() + 2.0);
            final String[] array = ((Value)this.value).getModes();
            for (int i = 0; i < array.length; ++i) {
                if (i != this.getSelectedIndex()) {
                    if (point.y >= n4 && point.y <= n4 + this.fontRenderer.FONT_HEIGHT) {
                        ((Value)this.value).setCurrentMode(i);
                        this.isSelected = false;
                        break;
                    }
                    n4 += this.fontRenderer.FONT_HEIGHT + 2;
                }
            }
            return true;
        }
        return false;
    }
}
