/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click.component.window;

import java.util.concurrent.CopyOnWriteArrayList;
import me.thekirkayt.client.gui.click.ClickGui;
import me.thekirkayt.client.gui.click.component.Component;
import me.thekirkayt.client.gui.click.component.window.ModuleWindow;
import me.thekirkayt.client.gui.click.component.window.Window;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;

public class Handle
extends Component<Window> {
    private static final float BORDER_WIDTH = 1.5f;
    private static final double SEPERATOR_HEIGHT = 2.0;
    public static final double TITLE_SCALE = 1.1;
    private static final int BACKGROUND_COLOR = -11184641;
    private static final int SEPERATOR_COLOR = -11184641;
    private String name;

    public Handle(String name, double x, double y, double width, double height) {
        super(null, x, y, width, height);
        this.name = name;
    }

    public void draw(int mouseX, int mouseY, boolean extended) {
        RenderUtils.rectangle(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), -11184641);
        ClientUtils.clientFont().drawCenteredString(this.name, this.getX() + this.getWidth() / 2.0, this.getY() + 5.0, -1);
    }

    @Override
    public void click(int mouseX, int mouseY, int button) {
        for (Window window : ClickGui.getInstance().getWindows()) {
            window.setDragging(false);
        }
        if (button == 0) {
            ((Window)this.getParent()).setStartOffset(new double[]{(double)mouseX - ((Window)this.getParent()).getX(), (double)mouseY - ((Window)this.getParent()).getY()});
            ((Window)this.getParent()).setDragging(true);
        } else if (button == 1) {
            ((Window)this.getParent()).setExtended(!((Window)this.getParent()).isExtended());
        }
    }

    @Override
    public void drag(int mouseX, int mouseY, int button) {
        if (button == 0) {
            ((Window)this.getParent()).drag(mouseX, mouseY, button);
        }
    }

    @Override
    public void release(int mouseX, int mouseY, int button) {
        if (button == 0) {
            for (Window window : ClickGui.getInstance().getWindows()) {
                window.setDragging(false);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
    }

    @Override
    public void keyPress(int keyInt, char keyChar) {
    }
}

