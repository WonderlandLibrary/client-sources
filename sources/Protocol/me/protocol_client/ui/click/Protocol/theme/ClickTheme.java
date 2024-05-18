package me.protocol_client.ui.click.Protocol.theme;


import me.protocol_client.ui.click.Protocol.GuiClick;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import net.minecraft.client.Minecraft;

public abstract class ClickTheme {
    protected final Minecraft mc = Minecraft.getMinecraft();
    private final String name;
    private float elementWidth, elementHeight;
    private boolean subMenus;

    public ClickTheme(String name, float elementWidth, float elementHeight, GuiClick gui, boolean subMenus) {
        this.name = name;
        this.elementWidth = elementWidth;
        this.elementHeight = elementHeight;
        this.subMenus = subMenus;

        for (Panel panel : gui.getPanels()) {
            for (Element element : panel.getElements()) {
                element.setWidth(this.getElementWidth());
                element.setHeight(this.getElementHeight());
            }
        }
    }

    public String getName() {
        return name;
    }

    public float getElementWidth() {
        return elementWidth;
    }

    public void setElementWidth(float elementWidth) {
        this.elementWidth = elementWidth;
    }

    public float getElementHeight() {
        return elementHeight;
    }

    public void setElementHeight(float elementHeight) {
        this.elementHeight = elementHeight;
    }

    public boolean hasSubMenus() {
        return subMenus;
    }

    public abstract void renderPanel(Panel panel);

    public abstract void renderButton(String name, boolean enabled, float x, float y, float width, float height, boolean overElement, Element element);

    public abstract void renderSlider(String name, float value, float x, float y, float width, float height, float sliderX, boolean overElement, Element element);

	public abstract void drawTheShit(Element element, boolean overElement);
}
