package dev.client.tenacity.ui.sidegui;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.config.ConfigManager;
import dev.client.tenacity.config.LocalConfig;
import dev.client.tenacity.config.OnlineConfig;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.objects.PasswordField;
import dev.client.tenacity.utils.objects.Scroll;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.utils.render.StencilUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConfigPanel extends GuiPanel {

    private final Scroll configScroll = new Scroll();
    public float x, rawY, rectHeight, rectWidth;
    public boolean reInit;
    private dev.client.tenacity.ui.sidegui.Button openConfigFolder;
    private Button saveConfigButton;
    private Button finalSave;
    private Button reloadButton;
    private ToggleButton loadVisuals;
    private PasswordField stringField;
    private Animation stringFieldAnimation;
    private boolean showStringField;
    private List<OnlineConfigRect> onlineConfigRects;
    private List<LocalConfigRect> localConfigRects;

    @Override
    public void initGui() {
        showStringField = false;
        saveConfigButton = new Button("Save Config");
        finalSave = new Button("Save");
        openConfigFolder = new Button("Open Folder");
        reloadButton = new Button("Reload");
        loadVisuals = new ToggleButton("Load Visuals");
        loadVisuals.initGui();
        openConfigFolder.initGui();
        saveConfigButton.initGui();
        reloadButton.initGui();
        finalSave.initGui();
        stringFieldAnimation = new DecelerateAnimation(250, 1);
        stringField = new PasswordField("Enter config name",
                0, 0, 0, (int) (rectWidth - 20), 20, FontUtil.tenacityFont20);

        if (onlineConfigRects == null) {
            onlineConfigRects = new ArrayList<>();
            for (OnlineConfig onlineConfig : ConfigManager.onlineConfigs) {
                onlineConfigRects.add(new OnlineConfigRect(onlineConfig));
            }
            onlineConfigRects.sort(Comparator.comparingInt(config -> config.onlineConfig.getVotes()));
            onlineConfigRects.sort((config1, config2) -> Boolean.compare(config1.onlineConfig.isVerified(), config2.onlineConfig.isVerified()));
            Collections.reverse(onlineConfigRects);
        }

        Tenacity.INSTANCE.getConfigManager().collectConfigs();
        localConfigRects = new ArrayList<>();
        for (LocalConfig config : ConfigManager.localConfigs) {
            localConfigRects.add(new LocalConfigRect(config));
        }

        onlineConfigRects.forEach(OnlineConfigRect::initGui);
        localConfigRects.forEach(LocalConfigRect::initGui);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (showStringField) {
            stringField.textboxKeyTyped(typedChar, keyCode);
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, int alpha) {
        boolean hoveringPanel = HoveringUtil.isHovering(x, rawY + 55, rectWidth, rectHeight - 55, mouseX, mouseY);
        if (hoveringPanel) configScroll.onScroll(35);
        float y = rawY + configScroll.getScroll();


        StencilUtil.initStencilToWrite();
        Gui.drawRect2(x, rawY + 51, rectWidth, rectHeight - 55, -1);
        StencilUtil.readStencilBuffer(1);

        GL11.glEnable(GL11.GL_BLEND);
        mc.getTextureManager().bindTexture(new ResourceLocation("Tenacity/configbackground.png"));
        RoundedUtil.drawRoundTextured(x + 25, y + 70, rectWidth - 50, 75, 12, alpha / 255f);

        int textColor = ColorUtil.applyOpacity(-1, alpha / 255f);
        FontUtil.tenacityBoldFont40.drawCenteredString("Tenacity Configs", x + rectWidth / 2f, y + 77, textColor);

        openConfigFolder.x = x + 25;
        openConfigFolder.y = y + 160;
        openConfigFolder.clickAction = this::openConfigFolder;
        openConfigFolder.drawScreen(mouseX, mouseY, partialTicks, alpha);

        saveConfigButton.x = openConfigFolder.x + openConfigFolder.width + 10;
        saveConfigButton.y = openConfigFolder.y;
        saveConfigButton.drawScreen(mouseX, mouseY, partialTicks, alpha);

        saveConfigButton.clickAction = () -> showStringField = !showStringField;

        stringFieldAnimation.setDirection(showStringField ? Direction.FORWARDS : Direction.BACKWARDS);

        stringField.xPosition = (int) (x + 25);
        stringField.yPosition = (int) (y + 178 + (18 * stringFieldAnimation.getOutput()));
        stringField.placeHolderTextX = x + 70;
        stringField.width = 172;

        int stringColor = ColorUtil.applyOpacity(textColor, (float) stringFieldAnimation.getOutput());
        stringField.textColor = stringColor;
        stringField.bottomBarColor = stringColor;
        if (showStringField || !stringFieldAnimation.isDone()) {
            stringField.drawTextBox();
        }

        finalSave.x = x + 25 + 172 / 2f - finalSave.width / 2f;
        finalSave.y = (float) (y + 210 + (18 * stringFieldAnimation.getOutput()));
        finalSave.drawScreen(mouseX, mouseY, partialTicks, (int) (alpha * stringFieldAnimation.getOutput()));
        finalSave.clickAction = () -> {
            if (Tenacity.INSTANCE.getConfigManager().saveConfig(stringField.getText())) {
                NotificationManager.post(NotificationType.SUCCESS, "Config saved", "Successfully saved config", 5);
                reInit = true;
            } else {
                NotificationManager.post(NotificationType.WARNING, "Error", "Failed to save config", 5);
            }
        };

        reloadButton.x = saveConfigButton.x + saveConfigButton.width + 10;
        reloadButton.y = saveConfigButton.y;
        reloadButton.drawScreen(mouseX, mouseY, partialTicks, alpha);

        reloadButton.clickAction = () -> {
            Tenacity.INSTANCE.getConfigManager().collectOnlineConfigs();
            Tenacity.INSTANCE.getConfigManager().collectConfigs();
            onlineConfigRects = null;
            reInit = true;
        };
        if (reInit) return;

        loadVisuals.x = reloadButton.x + reloadButton.width + 10;
        loadVisuals.y = reloadButton.y;
        loadVisuals.drawScreen(mouseX, mouseY, partialTicks, alpha);


        ConfigManager.loadVisuals = loadVisuals.toggled;

        float yMovement = (float) (62 * stringFieldAnimation.getOutput());

        Gui.drawRect2(x + 25, y + 195 + yMovement, rectWidth - 50, 1, new Color(45, 45, 45, alpha).getRGB());

        FontUtil.tenacityBoldFont32.drawCenteredString("Online Configs", x + rectWidth / 2f, y + 210 + yMovement, textColor);

        FontUtil.tenacityFont16.drawCenteredString("To upload an online config, DM the Tenacity Bot \"/config submit\"",
                x + rectWidth / 2f, y + 235 + yMovement, textColor);

        FontUtil.tenacityFont16.drawCenteredString("Verified configs are outlined and configs that have been voted on increase in text color",
                x + rectWidth / 2f, y + 248 + yMovement, textColor);

        FontUtil.tenacityFont16.drawCenteredString("To vote for configs you must have a config approver " +
                        "role in the discord which can be attained by making a ticket and asking for the role",
                x + rectWidth / 2f, y + 260 + yMovement, textColor);


        int count = 0;
        int seperation = 0;
        int seperationY = 0;
        for (OnlineConfigRect onlineConfigRect : onlineConfigRects) {
            if (count != 0 && count % 3 == 0) {
                seperationY += 110 + 10;
                seperation = 0;
            }
            onlineConfigRect.x = x + 25 + seperation;
            onlineConfigRect.y = y + 282 + seperationY + yMovement;
            onlineConfigRect.height = 110;
            onlineConfigRect.width = 160;
            onlineConfigRect.drawScreen(mouseX, mouseY, partialTicks, alpha);
            seperation += onlineConfigRect.width + 10;
            count++;
        }

        float newY = y + 412 + seperationY + yMovement;

        Gui.drawRect2(x + 25, newY, rectWidth - 50, 1, new Color(45, 45, 45, alpha).getRGB());

        FontUtil.tenacityBoldFont32.drawCenteredString("Local Configs", x + rectWidth / 2f, newY + 15, textColor);

        int localCount = 0;
        int localSeperation = 0;
        int localSeperationY = 0;
        for (LocalConfigRect localConfigRect : localConfigRects) {
            if (localCount != 0 && localCount % 3 == 0) {
                localSeperationY += 85 + 10;
                localSeperation = 0;
            }
            localConfigRect.x = x + 25 + localSeperation;
            localConfigRect.y = newY + 50 + localSeperationY;
            localConfigRect.height = 85;
            localConfigRect.width = 160;
            if (localConfigRect.reinit) {
                reInit = true;
                return;
            }
            localConfigRect.drawScreen(mouseX, mouseY, partialTicks, alpha);
            localSeperation += localConfigRect.width + 10;
            localCount++;
        }

        configScroll.setMaxScroll(260 + seperationY + yMovement + localSeperationY);


        StencilUtil.uninitStencilBuffer();
    }

    public void openConfigFolder() {
        try {
            Desktop.getDesktop().open(Tenacity.INSTANCE.getConfigManager().file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        openConfigFolder.mouseClicked(mouseX, mouseY, button);
        saveConfigButton.mouseClicked(mouseX, mouseY, button);
        reloadButton.mouseClicked(mouseX, mouseY, button);
        loadVisuals.mouseClicked(mouseX, mouseY, button);
        if (reInit) return;
        if (showStringField) {
            stringField.mouseClicked(mouseX, mouseY, button);
            finalSave.mouseClicked(mouseX, mouseY, button);
        }
        onlineConfigRects.forEach(onlineConfigRect -> onlineConfigRect.mouseClicked(mouseX, mouseY, button));
        localConfigRects.forEach(localConfigRect -> localConfigRect.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {

    }

}
