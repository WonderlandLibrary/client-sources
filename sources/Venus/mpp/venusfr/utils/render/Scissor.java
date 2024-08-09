/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render;

import com.google.common.collect.Lists;
import java.awt.Rectangle;
import java.util.List;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class Scissor {
    private static State state = new State();
    private static final List<State> stateStack = Lists.newArrayList();

    public static void push() {
        stateStack.add(state.clone());
        GL11.glPushAttrib(524288);
    }

    public static void pop() {
        state = stateStack.remove(stateStack.size() - 1);
        GL11.glPopAttrib();
    }

    public static void unset() {
        GL11.glDisable(3089);
        Scissor.state.enabled = false;
    }

    public static void setFromComponentCoordinates(int n, int n2, int n3, int n4) {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        int n5 = 2;
        int n6 = n * n5;
        int n7 = n2 * n5;
        int n8 = n3 * n5;
        int n9 = n4 * n5;
        n7 = Minecraft.getInstance().getMainWindow().getHeight() - n7 - n9;
        Scissor.set(n6, n7, n8, n9);
    }

    public static void setFromComponentCoordinates(double d, double d2, double d3, double d4) {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        int n = 2;
        int n2 = (int)(d * (double)n);
        int n3 = (int)(d2 * (double)n);
        int n4 = (int)(d3 * (double)n);
        int n5 = (int)(d4 * (double)n);
        n3 = Minecraft.getInstance().getMainWindow().getHeight() - n3 - n5;
        Scissor.set(n2, n3, n4, n5);
    }

    public static void setFromComponentCoordinates(double d, double d2, double d3, double d4, float f) {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        float f2 = f;
        float f3 = (1.0f - f2) / 2.0f;
        double d5 = d + d3 * (double)f3;
        double d6 = d2 + d4 * (double)f3;
        double d7 = d3 * (double)f2;
        double d8 = d4 * (double)f2;
        d5 = d5 * (double)f2 + ((double)Minecraft.getInstance().getMainWindow().getScaledWidth() - d7) * (double)f3;
        float f4 = 2.0f;
        int n = (int)(d5 * (double)f4);
        int n2 = (int)(d6 * (double)f4);
        int n3 = (int)(d7 * (double)f4);
        int n4 = (int)(d8 * (double)f4);
        n2 = Minecraft.getInstance().getMainWindow().getHeight() - n2 - n4;
        Scissor.set(n, n2, n3, n4);
    }

    public static void set(int n, int n2, int n3, int n4) {
        Rectangle rectangle = new Rectangle(0, 0, Minecraft.getInstance().getMainWindow().getWidth(), Minecraft.getInstance().getMainWindow().getHeight());
        Rectangle rectangle2 = Scissor.state.enabled ? new Rectangle(Scissor.state.x, Scissor.state.y, Scissor.state.width, Scissor.state.height) : rectangle;
        Rectangle rectangle3 = new Rectangle(n + Scissor.state.transX, n2 + Scissor.state.transY, n3, n4);
        Rectangle rectangle4 = rectangle2.intersection(rectangle3);
        rectangle4 = rectangle4.intersection(rectangle);
        if (rectangle4.width < 0) {
            rectangle4.width = 0;
        }
        if (rectangle4.height < 0) {
            rectangle4.height = 0;
        }
        Scissor.state.enabled = true;
        Scissor.state.x = rectangle4.x;
        Scissor.state.y = rectangle4.y;
        Scissor.state.width = rectangle4.width;
        Scissor.state.height = rectangle4.height;
        GL11.glEnable(3089);
        GL11.glScissor(rectangle4.x, rectangle4.y, rectangle4.width, rectangle4.height);
    }

    public static void translate(int n, int n2) {
        Scissor.state.transX = n;
        Scissor.state.transY = n2;
    }

    public static void translateFromComponentCoordinates(int n, int n2) {
        MainWindow mainWindow = Minecraft.getInstance().getMainWindow();
        int n3 = mainWindow.getScaledHeight();
        int n4 = (int)mainWindow.getGuiScaleFactor();
        int n5 = n * n4;
        int n6 = n2 * n4;
        n6 = n3 * n4 - n6;
        Scissor.translate(n5, n6);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class State
    implements Cloneable {
        public boolean enabled;
        public int transX;
        public int transY;
        public int x;
        public int y;
        public int width;
        public int height;

        private State() {
        }

        public State clone() {
            try {
                return (State)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new AssertionError((Object)cloneNotSupportedException);
            }
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }
    }
}

