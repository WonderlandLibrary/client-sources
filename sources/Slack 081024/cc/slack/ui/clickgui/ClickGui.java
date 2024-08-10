package cc.slack.ui.clickgui;

import java.io.IOException;
import java.util.ArrayList;

import cc.slack.utils.client.Login;
import net.minecraft.client.gui.GuiScreen;
import cc.slack.ui.clickgui.component.Frame;
import cc.slack.features.modules.api.Category;

public class ClickGui extends GuiScreen {

    public static ArrayList<Frame> frames;
    public static boolean waitingBind = false;

    public ClickGui() {
        waitingBind = false;
        this.frames = new ArrayList<>();
        int frameX = 5;
        for (Category category : Category.values()) {
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        frames.forEach(frame -> {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            frame.getComponents().forEach(comp -> comp.updateComponent(mouseX, mouseY));
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        frames.forEach(frame -> {
            if (frame.isWithinHeader(mouseX, mouseY)) {
                switch (mouseButton) {
                    case 0:
                        frame.setDrag(true);
                        frame.dragX = mouseX - frame.getX();
                        frame.dragY = mouseY - frame.getY();
                        break;
                    case 1:
                        frame.setOpen(!frame.isOpen());
                        break;
                }
            }
            if (frame.isOpen()) {
                if (!frame.getComponents().isEmpty()) {
                    frame.getComponents().forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
                }
            }
        });
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        boolean escBind = waitingBind && keyCode == 1;
        frames.forEach(frame -> {
            if (frame.isOpen() && (keyCode != 1 || waitingBind)) {
                if (!frame.getComponents().isEmpty()) {
                    frame.getComponents().forEach(component -> component.keyTyped(typedChar, keyCode));
                }
                waitingBind = false;
            }
        });
        if (keyCode == 1 && !escBind) {
            this.mc.displayGuiScreen(null);
            if (!Login.pj423j.contains(Login.sha256("true" + Login.yeu13))) {
                System.exit(1);
            }
        }
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        frames.forEach(frame -> {
            frame.setDrag(false);
            if (frame.isOpen()) {
                if (!frame.getComponents().isEmpty()) {
                    frame.getComponents().forEach(component -> component.mouseReleased(mouseX, mouseY, state));
                }
            }
        });
    }
}
