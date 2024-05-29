package dev.client.tenacity.ui.altmanager;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.impl.render.HudMod;
import dev.client.tenacity.ui.altmanager.helpers.Alt;
import dev.client.tenacity.ui.altmanager.helpers.AltManagerUtils;
import dev.client.tenacity.ui.altmanager.panels.AltListAltPanel;
import dev.client.tenacity.ui.mainmenu.TenacityMainMenu;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.GradientUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.utils.animations.Animation;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiAltManager extends GuiScreen {

    private final AltManagerUtils utils = new AltManagerUtils();
    private final AltPanels panels = Tenacity.INSTANCE.altPanels;
    private Animation initAnimation;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      //  utils.writeAltsToFile();

        ScaledResolution sr = new ScaledResolution(mc);

        Color gradient1 = ColorUtil.interpolateColorsBackAndForth(
                15, 1, Tenacity.INSTANCE.getClientColor(), Tenacity.INSTANCE.getAlternateClientColor(), false);
        Color gradient2 = ColorUtil.interpolateColorsBackAndForth(
                15, 1, Tenacity.INSTANCE.getAlternateClientColor(), Tenacity.INSTANCE.getClientColor(), false);

        GradientUtil.drawGradient(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 1, gradient1, gradient1, gradient2, gradient2);

        Color[] clientColors = ((HudMod) Tenacity.INSTANCE.getModuleCollection().get(HudMod.class)).getClientColors();
        Color grad1 = clientColors[0], grad2 = clientColors[1];
        int altCount = AltManagerUtils.alts.size();
        double anim = initAnimation.getOutput();

        Gui.drawRect2(20 * anim, 10, sr.getScaledWidth() - 35, 25, new Color(20, 20, 29, 120).getRGB());

        GradientUtil.applyGradientHorizontal(23, 12, (float) FontUtil.tenacityBoldFont40.getStringWidth(Tenacity.NAME.toLowerCase()), 20, 1, grad1, grad2, () -> {
            RenderUtil.setAlphaLimit(0);
            FontUtil.tenacityBoldFont40.drawString(Tenacity.NAME.toLowerCase(), 23 * anim, 12, Tenacity.INSTANCE.getClientColor().getRGB());
        });
        RenderUtil.resetColor();
        FontUtil.tenacityFont20.drawString(Tenacity.VERSION, (FontUtil.tenacityBoldFont40.getStringWidth(Tenacity.NAME.toLowerCase()) + 24) * anim, 12, grad2.getRGB());

        if (mc.getSession() != null && mc.getSession().getUsername() != null) {
            String name = mc.getSession().getUsername();
            AltListAltPanel altListPanel = (AltListAltPanel) panels.getPanel(AltListAltPanel.class);
            Alt fakeAlt = new Alt("_", "_");
            fakeAlt.username = name;
//            altListPanel.drawAltHead(fakeAlt, (float) (sr.getScaledWidth() - FontUtil.tenacityFont24.getStringWidth(name) * anim - 42.5F), 12.5F, 20);
            FontUtil.tenacityFont24.drawStringWithShadow(name, sr.getScaledWidth() - FontUtil.tenacityFont24.getStringWidth(name) * anim - 20, 17, -1);
        }

        panels.getPanels().forEach(panel -> panel.drawScreen(mouseX, mouseY, partialTicks, initAnimation));

        Tenacity.INSTANCE.getNotificationManager().drawNotifications(sr);
        switch (Alt.stage) {
            case 1:
                NotificationManager.post(NotificationType.INFO, "Alt Manager", "Invalid credentials!", 3);
                Alt.stage = 0;
                break;
            case 2:
                NotificationManager.post(NotificationType.SUCCESS, "Alt Manager", "Logged in successfully!", 3);
                Alt.stage = 0;
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        panels.getPanels().forEach(panel -> panel.mouseClicked(mouseX, mouseY, mouseButton, utils));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new TenacityMainMenu());
        } else {
            panels.getPanels().forEach(panel -> panel.keyTyped(typedChar, keyCode));
        }
    }

    @Override
    public void initGui() {
        initAnimation = new DecelerateAnimation(600, 1);
        panels.getPanels().forEach(AltPanel::initGui);
    }

}
