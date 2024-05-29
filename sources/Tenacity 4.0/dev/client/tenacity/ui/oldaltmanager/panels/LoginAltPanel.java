package dev.client.tenacity.ui.oldaltmanager.panels;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.ui.oldaltmanager.AltPanel;
import dev.client.tenacity.ui.oldaltmanager.backend.Alt;
import dev.client.tenacity.ui.oldaltmanager.backend.AltManagerUtils;
import dev.client.tenacity.ui.oldaltmanager.panels.components.Component;
import dev.client.tenacity.ui.oldaltmanager.panels.components.impl.Button;
import dev.client.tenacity.ui.oldaltmanager.panels.components.impl.StringField;
import dev.utils.animations.Animation;
import net.minecraft.client.gui.Gui;


import java.util.ArrayList;
import java.util.Arrays;

public class LoginAltPanel implements AltPanel {
    ArrayList<Component> components = new ArrayList<Component>() {{
        addAll(Arrays.asList(
                new StringField("Email / Combo / Altening", 230, 250, false),
                new StringField("Password", 230, 250, false),
                new Button("Login", 180, 30),
                new Button("Random Cracked", 230, 30),
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
        Gui.drawRect2((20 - 250) + (250 * initAnimation.getOutput()), 40, 250, 145, rectColorInt);
        components.get(0).x = 30;
        components.get(1).x = 30;
        components.get(0).y = 50;
        components.get(1).y = 80;

        // login button
        components.get(2).x = 30;
        components.get(2).y = 110;

        // random cracked button
        components.get(3).x = 30;
        components.get(3).y = 145;

        // mojang/microsoft selector
        components.get(4).x = 215;
        components.get(4).y = 110;

        components.forEach(component -> component.drawScreen(mouseX, mouseY, partialTicks, initAnimation));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button, AltManagerUtils altManagerUtils) {
        components.forEach(component -> component.mouseClicked(mouseX, mouseY, button, altManagerUtils));
        if (button != 0) return;
        if (components.get(2).hovering) {
            altManagerUtils.login(((StringField) components.get(0)).getPasswordField(), ((StringField) components.get(1)).getPasswordField());
            //((AltListAltPanel) Tenacity.INSTANCE.altPanels.getPanel(AltListAltPanel.class)).reInitAltList();
        }
        if (components.get(3).hovering) {
            altManagerUtils.loginWithString("s", "", false);
           // ((AltListAltPanel) Tenacity.INSTANCE.altPanels.getPanel(AltListAltPanel.class)).reInitAltList();
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
