package de.tired.api.userinterface.renderers;

import de.tired.api.userinterface.UIAnnotation;
import de.tired.api.userinterface.element.ElementHoveringButton;
import de.tired.api.userinterface.element.ElementText;
import de.tired.api.userinterface.UI;

@UIAnnotation
public class UIMainMenu extends UI {

    private final ElementHoveringButton elementHoveringButton;
    private final ElementText elementText;

    public UIMainMenu() {
        elementHoveringButton = new ElementHoveringButton(this, 42, 144, 25,"Test");
        elementText = new ElementText(this, ElementText.FONT.IBMPlexSans, 12, 122, "SussyAmongus");

    }

    @Override
    public void renderUI(int mouseX, int mouseY) {

        update();

        renderShaderBackground();

        elementHoveringButton.renderButtonRectangle(mouseX, mouseY);

        elementHoveringButton.renderButtonText();

       // elementText.renderText();

    }
}
