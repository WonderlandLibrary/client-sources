package dev.client.tenacity.ui.altmanager.panels;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.ui.altmanager.panels.components.Component;
import dev.client.tenacity.ui.altmanager.panels.components.impl.Button;
import dev.client.tenacity.ui.altmanager.panels.components.impl.StringField;
import dev.client.tenacity.ui.altmanager.AltPanel;
import dev.client.tenacity.ui.altmanager.helpers.Alt;
import dev.client.tenacity.ui.altmanager.helpers.AltManagerUtils;
import dev.utils.animations.Animation;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginAltPanel implements AltPanel {
    ArrayList<Component> components = new ArrayList<Component>() {{
        addAll(Arrays.asList(
                new StringField("Email / Combo / TheAltening", 165, 250, false),
                new StringField("Password", 165, 250, false),
                new Button("Login", 117, 30),
                new Button("Random Cracked", 165, 30),
                new Button("method", 45, 30)
        ));
    }};


    @Override
    public void initGui() {
        components.forEach(Component::initGui);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        components.forEach(component -> component.keyTyped(typedChar, keyCode));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, Animation initAnimation) {
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth(), height = sr.getScaledHeight();

        Gui.drawRect2((width - 440) + (250 * initAnimation.getOutput()), height / 2.0F - 70, 175, 138, rectColorInt);

        // Email / Combo / Altening
        StringField emailField = (StringField) components.get(0);
        emailField.x = width - emailField.width - 20;
        emailField.y = height / 2.0F - 60;

        // Password
        StringField passField = (StringField) components.get(1);
        passField.x = width - passField.width - 20;
        passField.y = height / 2.0F - 30;

        // login button
        Button loginButton = (Button) components.get(2);
        loginButton.x = width - loginButton.width - 68; // 30
        loginButton.y = height / 2.0F; // 110

        // mojang/microsoft selector
        Button typeButton = (Button) components.get(4);
        typeButton.x = width - typeButton.width - 20;
        typeButton.y = height / 2.0F;

        // random cracked button
        Button crackedButton = (Button) components.get(3);
        crackedButton.x = width - crackedButton.width - 20;
        crackedButton.y = height / 2.0F + 33; // 145

        components.forEach(component -> component.drawScreen(mouseX, mouseY, partialTicks, initAnimation));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        components.forEach(component -> component.mouseClicked(mouseX, mouseY, button, altManagerUtils));
        if (button != 0) return;
        if (components.get(2).hovering) {
            altManagerUtils.login(((StringField) components.get(0)).getPasswordField(), ((StringField) components.get(1)).getPasswordField());
            ((AltListAltPanel) Tenacity.INSTANCE.altPanels.getPanel(AltListAltPanel.class)).reInitAltList();
        }
        if (components.get(3).hovering) {
            altManagerUtils.loginWithString("s", "", false);
            ((AltListAltPanel) Tenacity.INSTANCE.altPanels.getPanel(AltListAltPanel.class)).reInitAltList();
        }
        if (components.get(4).hovering) {
            if (Alt.currentLoginMethod == Alt.AltType.MOJANG) {
                Alt.currentLoginMethod = Alt.AltType.MICROSOFT;
            } else {
                Alt.currentLoginMethod = Alt.AltType.MOJANG;
            }
        }
    }

}
