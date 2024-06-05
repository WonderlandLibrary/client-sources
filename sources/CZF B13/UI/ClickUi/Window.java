package gq.vapu.czfclient.UI.ClickUi;

import com.google.common.collect.Lists;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Render.ClickGui;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.ClientUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class Window {
    public ModuleType category;
    public ArrayList<Button> buttons = Lists.newArrayList();
    public boolean drag;
    public boolean extended;
    public int x;
    public int y;
    public int expand;
    public int dragX;
    public int dragY;
    public int max;
    public int scroll;
    public int scrollTo;
    public double angel;
    private final TimerUtil timer = new TimerUtil();

    public Window(ModuleType category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.max = 120;
        int y2 = y + 20;
        for (Module c : ModuleManager.getModules()) {
            if (c.getType() != category)
                continue;
            this.buttons.add(new Button(c, x + 5, y2));
            y2 += 15;
        }
        for (Button b2 : this.buttons) {
            b2.setParent(this);
        }
    }

    public void render(int mouseX, int mouseY) {
        int current = 0;
        for (Button b3 : this.buttons) {
            if (b3.expand) {
                for (ValueButton v : b3.buttons) {
                    current += 15;
                }
            }
            current += 15;
        }
        int height = 15 + current;
        if (this.extended) {
//            if(this.timer.hasReached(10))
//            {
            this.expand = this.expand + 1 < height ? (this.expand += 5) : height;
            this.angel = this.angel + 20.0 < 180.0 ? (this.angel += 20.0) : 180.0;
//                this.timer.reset();
//            }
        } else {
            this.expand = this.expand - 5 > 0 ? (this.expand -= 5) : 0;
            this.angel = this.angel - 20.0 > 0.0 ? (this.angel -= 20.0) : 0.0;
        }
//		RenderUtil.R2DUtils.drawRoundedRect(this.x - 2, this.y, this.x + 92, this.y + 17,
//				new Color(255, 255, 255).getRGB(), new Color(255, 255, 255).getRGB());// category
        Gui.drawRect(this.x - 2, this.y, this.x + 92, this.y + 15, ClientUtil.reAlpha(new Color(ClickGui.red, ClickGui.green, ClickGui.blue).getRGB(), 1.0f));
//        mc.fontRendererObj.drawStringWithShadow(this.category.name(), this.x + 15, this.y + 4, new Color(255, 255, 255).getRGB());
        FontLoaders.GoogleSans18.drawString(this.category.name(), this.x + 15, this.y + 3, new Color(255, 255, 255).getRGB());
        if (this.category.name().equals("Combat")) {
            FontLoaders.NovICON24.drawString("H", this.x + 3, this.y + 4, new Color(255, 255, 255).getRGB());
        }
        if (this.category.name().equals("Render")) {
            FontLoaders.NovICON18.drawString("F", this.x + 3, this.y + 5, new Color(255, 255, 255).getRGB());
        }
        if (this.category.name().equals("Movement")) {
            FontLoaders.NovICON18.drawString("I", this.x + 3, this.y + 5, new Color(255, 255, 255).getRGB());
        }
        if (this.category.name().equals("Blatant")) {
            FontLoaders.NovICON20.drawString("G", this.x + 3, this.y + 5, new Color(255, 255, 255).getRGB());
        }
        if (this.category.name().equals("Player")) {
            FontLoaders.NovICON20.drawString("C", this.x + 3, this.y + 5, new Color(255, 255, 255).getRGB());
        }
        if (this.category.name().equals("World")) {
            FontLoaders.NovICON20.drawString("E", this.x + 3, this.y + 5, new Color(255, 255, 255).getRGB());
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.x + 90 - 10, this.y + 5, 0.0f);
        GlStateManager.rotate((float) this.angel, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-this.x + 90 - 10, -this.y + 5, 0.0f);
        GlStateManager.popMatrix();
        if (this.expand > 0) {
            this.buttons.forEach(b2 -> b2.render(mouseX, mouseY));
        }
        if (this.drag) {
            if (!Mouse.isButtonDown(0)) {
                this.drag = false;
            }
            this.x = mouseX - this.dragX;
            this.y = mouseY - this.dragY;
            this.buttons.get(0).y = this.y + 20 - this.scroll;
            for (Button b4 : this.buttons) {
                b4.x = this.x + 5;
            }
        }
    }

    public void key(char typedChar, int keyCode) {
        this.buttons.forEach(b2 -> b2.key(typedChar, keyCode));
    }

    @EventHandler
    public void onTick() {
        int mouseX = 0;
        int mouseY = 0;
        render(mouseX, mouseY);
    }

    public void mouseScroll(int mouseX, int mouseY, int amount) {
        if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17 + this.expand) {
            this.scrollTo = (int) ((float) this.scrollTo - (float) (amount / 120 * 28));
        }
    }

    public void click(int mouseX, int mouseY, int button) {
        if (mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17) {
            if (button == 1) {
                boolean bl = this.extended = !this.extended;
            }
            if (button == 0) {
                this.drag = true;
                this.dragX = mouseX - this.x;
                this.dragY = mouseY - this.y;
            }
        }
        if (this.extended) {
            this.buttons.stream().filter(b2 -> b2.y < this.y + this.expand)
                    .forEach(b2 -> b2.click(mouseX, mouseY, button));
        }
    }
}
