package wtf.shiyeno.ui.clickgui.configs;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.RandomStringUtils;
import wtf.shiyeno.ui.clickgui.Window;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.config.ConfigManager;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.OutlineUtils;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.SmartScissor;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfigDrawing {
    public static ConfigDrawing configDrawing = new ConfigDrawing();

    public CopyOnWriteArrayList<ConfigObject> objects = new CopyOnWriteArrayList<>();

    public ConfigDrawing() {
        objects.clear();
        for (String cfg : Managment.CONFIG_MANAGER.getAllConfigurations()) {
            objects.add(new ConfigObject(cfg));
        }
        configDrawing = this;
    }

    public TimerUtil refresh = new TimerUtil();

    float x, y, width, height;

    public void draw(MatrixStack stack, int mouseX, int mouseY, float x, float y, float width, float height) {
        if (refresh.hasTimeElapsed(1000)) {
            for (String cfg : Managment.CONFIG_MANAGER.getAllConfigurations()) {
                if (!objects.stream().map(o -> o.staticF).toList().contains(cfg))
                    objects.add(new ConfigObject(cfg));
            }
            objects.removeIf(object -> Managment.CONFIG_MANAGER.findConfig(object.staticF) == null);
            refresh.reset();
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        float offsetX = 10;
        float offsetY = 25 + 25 + wtf.shiyeno.ui.beta.ClickGui.scrollingOut;

        RenderUtil.Render2D.drawRect(x + 1, y, width - 2, 33, ColorUtil.rgba(100, 100, 100, 25));
        RenderUtil.Render2D.drawRect(x + 1, y, width - 2, 33, ColorUtil.rgba(30, 30, 30, 255));

        SmartScissor.push();
        SmartScissor.setFromComponentCoordinates(x, y + 33, width, height - 33);

        float size = 0;
        for (ConfigObject object : objects) {
            object.x = x + offsetX;
            object.y = y + offsetY;
            object.width = 313 / 2f;
            object.height = 97 / 2f;
            RenderUtil.Render2D.drawRect(object.x, object.y, object.width, object.height, ColorUtil.rgba(30, 30, 30, 255));
            offsetX += object.width + 16;
            if (offsetX > 450 - 100 - object.width) {
                size += 97 / 2f + 7;
                offsetX = 10;
                offsetY += 55;
            }
        }

        if (size < 400 - 33 - 25) {
            wtf.shiyeno.ui.beta.ClickGui.scrolling = 0;
            wtf.shiyeno.ui.beta.ClickGui.scrollingOut = 0;
        } else {
            wtf.shiyeno.ui.beta.ClickGui.scrolling = MathHelper.clamp(Window.scrolling, -(size - 250), 0);
        }

        for (ConfigObject object : objects) {
            object.draw(stack, mouseX, mouseY);
        }
        SmartScissor.unset();
        SmartScissor.pop();
        Fonts.configIcon[22].drawString(stack, "E", x + 15, y + 13, -1);
        Fonts.configIcon[22].drawString(stack, "L", x + 35, y + 13, -1);

        RenderUtil.Render2D.drawRoundedRect(x + 150 + 30, y + 10, 150, 14.5f, 4, ColorUtil.rgba(0, 0, 0, 128));

        OutlineUtils.draw(2);
    }

    public void click(int mouseX, int mouseY, int button) {
        for (ConfigObject object : objects) {
            object.click(mouseX, mouseY);
        }

        if (RenderUtil.isInRegion(mouseX, mouseY, x + 15, y + 10, 11, 11)) {
            Managment.CONFIG_MANAGER.saveConfiguration("NewConfig" + RandomStringUtils.randomAlphabetic(2));
            objects.clear();
            for (String cfg : Managment.CONFIG_MANAGER.getAllConfigurations()) {
                objects.add(new ConfigObject(cfg));
            }
        }

        if (RenderUtil.isInRegion(mouseX, mouseY, x + 150 + 30, y + 10, 150, 14.5f)) {
        }

        if (RenderUtil.isInRegion(mouseX, mouseY, x + 35, y + 10, 11, 11)) {
            try {
                Runtime.getRuntime().exec("explorer " + ConfigManager.CONFIG_DIR.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void charTyped(char chars) {
        for (ConfigObject object : objects) {
            object.charTyped(chars);
        }
    }

    public void keyTyped(int key) {
        for (ConfigObject object : objects) {
            object.keyTyped(key);
        }
    }
}