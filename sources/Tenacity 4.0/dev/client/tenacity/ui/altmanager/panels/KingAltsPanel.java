package dev.client.tenacity.ui.altmanager.panels;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.ui.altmanager.panels.components.Component;
import dev.client.tenacity.ui.altmanager.panels.components.impl.Button;
import dev.client.tenacity.ui.altmanager.panels.components.impl.StringField;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.notifications.NotificationType;
import dev.client.tenacity.ui.altmanager.AltPanel;
import dev.client.tenacity.ui.altmanager.helpers.AltManagerUtils;
import dev.client.tenacity.ui.altmanager.helpers.KingGenApi;
import dev.client.tenacity.utils.render.ColorUtil;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class KingAltsPanel implements AltPanel {
    private final KingGenApi kingGenApi = Tenacity.INSTANCE.kingGenApi;
    public Animation errorAnimation;
    public Animation hasKingAltKey;
    ArrayList<Component> components = new ArrayList<Component>() {{
        addAll(Arrays.asList(
                new StringField("Enter your KingAlts key", 230, 250, true),
                new Button("Submit Key", 230, 30, 250),
                new Button("Reset Key", 105, 30),
                new Button("Refresh API", 105, 30),
                new Button("Gen and Login", 105, 30)
        ));
    }};
    private Animation errorShake;
    private boolean doesHaveKingAltKey = false;
    private boolean kingGenError = false;
    private boolean kingGenErrorShake = false;

    @Override
    public void initGui() {
        kingGenError = false;
        kingGenErrorShake = false;
        doesHaveKingAltKey = kingGenApi.hasKeyInFile();
        components.forEach(Component::initGui);
        hasKingAltKey = new DecelerateAnimation(400, 1, doesHaveKingAltKey ? Direction.FORWARDS : Direction.BACKWARDS);
        errorShake = new DecelerateAnimation(200, 1);
        errorAnimation = new DecelerateAnimation(200, 1, Direction.BACKWARDS);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        components.forEach(stringField -> stringField.keyTyped(typedChar, keyCode));
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation) {
        float mainRectAnimation = (float) (105 + (45 * hasKingAltKey.getOutput()) + (30 * errorAnimation.getOutput()));

        float kingAltsRectX2 = (float) ((30 - 250) + (250 * initAnimation.getOutput()));
        errorAnimation.setDirection(kingGenError ? Direction.FORWARDS : Direction.BACKWARDS);
        hasKingAltKey.setDirection(doesHaveKingAltKey ? Direction.FORWARDS : Direction.BACKWARDS);
        float alreadyHasKeyAnimation = (float) (260 * hasKingAltKey.getOutput());
        Gui.drawRect2((20 - 250) + (250 * initAnimation.getOutput()), 195, 250,
                mainRectAnimation, rectColorInt);

        float errorX = (float) ((((kingAltsRectX2 + 250) / 2f) - 300) + (300 * errorAnimation.getOutput()));
        errorX += 5 * errorShake.getOutput();
        errorShake.setDirection(kingGenErrorShake ? Direction.FORWARDS : Direction.BACKWARDS);

        if (errorShake.getDirection() == Direction.FORWARDS && errorShake.isDone()) {
            errorShake.setDirection(Direction.BACKWARDS);
            kingGenErrorShake = false;
        }

        ScaledResolution sr = new ScaledResolution(mc);

        FontUtil.tenacityBoldFont26.drawCenteredString("Error Invalid Key", errorX, 305,
                new Color(255, 30, 90).getRGB());

        FontUtil.tenacityBoldFont32.drawCenteredString("KingAlts", (kingAltsRectX2 + 250) / 2f, 205, -1);

        //Enter kingalts key
        components.get(0).x = 30 - alreadyHasKeyAnimation;
        components.get(0).y = 230;
        ((StringField) components.get(0)).placeHolderTextXOffset = (int) alreadyHasKeyAnimation;

        //Submit key button
        components.get(1).x = (float) ((30 - 400) + (400 * Math.abs(hasKingAltKey.getOutput() - 1)));
        components.get(1).y = 260;
        ((Button) components.get(1)).textColor =
                ColorUtil.interpolateColor(-1, new Color(255, 30, 90).getRGB(), (float) errorShake.getOutput());

        //Reset key Button
        components.get(2).x = 30;
        components.get(2).y = 235;
        ((Button) components.get(2)).alternateAnimation = hasKingAltKey;

        //Refresh button
        components.get(3).x = 30;
        components.get(3).y = 270;
        ((Button) components.get(3)).alternateAnimation = hasKingAltKey;

        //Gen and login button
        components.get(4).x = 30;
        components.get(4).y = 305;
        ((Button) components.get(4)).alternateAnimation = hasKingAltKey;
        components.forEach(stringField -> stringField.drawScreen(mouseX, mouseY, partialTicks, initAnimation));


        float animation = (float) (280 * hasKingAltKey.getOutput());
        Gui.drawRect2((140 - 280) + animation, 235, 120, 100, rectColorInt);

        FontUtil.tenacityBoldFont26.drawCenteredString("User Profile", (200 - 280) + animation, 245, -1);

        FontUtil.tenacityFont18.drawString("Username: " + kingGenApi.username, (145 - 280) + animation,
                265, -1);
        FontUtil.tenacityFont18.drawString("Alts generated (total): " + kingGenApi.generated, (145 - 280) + animation,
                280, -1);
        FontUtil.tenacityFont18.drawString("Alts generated (today): " + kingGenApi.generatedToday, (145 - 280) + animation,
                295, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        components.forEach(stringField -> stringField.mouseClicked(mouseX, mouseY, button, altManagerUtils));
        if (button != 0) return;
        if (doesHaveKingAltKey) {
            boolean hoveringReset = components.get(2).hovering;

            boolean hoveringRefresh = components.get(3).hovering;

            boolean hoveringGen = components.get(4).hovering;

            if (hoveringReset) {
                kingGenApi.setKey("");
                doesHaveKingAltKey = false;
                NotificationManager.post(NotificationType.INFO, "KingAlts", "Your KingAlts key was reset");
            }
            if (hoveringRefresh) {
                kingGenApi.refreshKey();
                NotificationManager.post(NotificationType.INFO, "KingAlts", "Refreshed the KingAlts API");
            }
            if (hoveringGen) {
                String[] newAlt = kingGenApi.genAlt();
                altManagerUtils.loginWithString(newAlt[0], newAlt[1], false);
                ((AltListAltPanel) Tenacity.INSTANCE.altPanels.getPanel(AltListAltPanel.class)).reInitAltList();
            }
        }

        boolean hoveringSubmitKey = components.get(1).hovering;
        if (!doesHaveKingAltKey && hoveringSubmitKey) {
            kingGenApi.setKey(((StringField) components.get(0)).getText());
            if (kingGenApi.checkKey()) {
                doesHaveKingAltKey = true;
                kingGenError = false;
                NotificationManager.post(NotificationType.SUCCESS, "KingAlts key validated", "You will now be able to use the KingAlts API in the client!");
            } else {
                kingGenErrorShake = kingGenError;
                kingGenError = true;
                kingGenApi.setKey("");
                NotificationManager.post(NotificationType.DISABLE, "KingAlts key error", "The key you entered was invalid!");
            }
        }

    }
}
