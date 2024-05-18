package pw.latematt.xiv.ui.clickgui.panel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.ui.clickgui.element.Element;
import pw.latematt.xiv.ui.clickgui.element.elements.ModButton;
import pw.latematt.xiv.ui.clickgui.element.elements.PanelButton;
import pw.latematt.xiv.ui.clickgui.element.elements.ValueButton;
import pw.latematt.xiv.ui.clickgui.element.elements.ValueSlider;
import pw.latematt.xiv.utils.RenderUtils;
import pw.latematt.xiv.value.ClampedValue;
import pw.latematt.xiv.value.Value;

import java.util.ArrayList;

public class Panel {
    private final String name;
    private float x, y, width, height, openheight, buttonoffset = 1;
    private boolean dragging, open, showing;
    private float dragX, dragY;
    private ArrayList<Element> elements;

    public Panel(String name, ArrayList<Element> elements, float x, float y, float width, float height) {
        this(name, elements, x, y, width, height, false);
    }

    public Panel(String name, ArrayList<Element> elements, float x, float y, float width, float height, boolean showing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.openheight = this.height = height;
        this.name = name;
        this.elements = elements;
        this.showing = showing;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getOpenHeight() {
        return openheight;
    }

    public void setOpenHeight(float openheight) {
        this.openheight = openheight;
    }

    public float getButtonOffset() {
        return buttonoffset;
    }

    public void setButtonOffset(float buttonoffset) {
        this.buttonoffset = buttonoffset;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public void drawPanel(int mouseX, int mouseY) {
        if (isDragging()) {
            this.x = mouseX + dragX;
            this.y = mouseY + dragY;

            if (!Mouse.isButtonDown(0)) {
                setDragging(false);
                XIV.getInstance().getFileManager().saveFile("gui");
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && isDragging())) {
            if (x < 0)
                x = 1;
            if (y < 0)
                y = 1;
            if (x + width > RenderUtils.newScaledResolution().getScaledWidth())
                x = RenderUtils.newScaledResolution().getScaledWidth() - width;
            if (y + height > RenderUtils.newScaledResolution().getScaledHeight())
                y = RenderUtils.newScaledResolution().getScaledHeight() - height - 2;
        }

        XIV.getInstance().getGuiClick().getTheme().renderPanel(this);

        if (isOpen()) {
            float y = getOpenHeight();

            for (Element element : elements) {
                element.setX(getX() + 2);
                element.setY(getY() + y + 2);
                element.drawElement(mouseX, mouseY);

                if (element.getWidth() + 4 > getWidth()) {
                    this.setWidth(element.getWidth() + 4);
                }

                y += element.getHeight() + buttonoffset;
            }

            this.setHeight(y + 3);
        } else {
            this.setHeight(getOpenHeight());
        }
    }

    public void keyPressed(int key) {
        if (isOpen()) {
            for (Element element : elements) {
                element.keyPressed(key);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isOverPanel(mouseX, mouseY)) {
            if (mouseButton == 0) {
                dragX = (getX() - mouseX);
                dragY = (getY() - mouseY);

                dragging = true;
                for (Panel panel : XIV.getInstance().getGuiClick().getPanels()) {
                    if (panel.equals(this)) continue;
                    panel.dragging = false;
                }
                XIV.getInstance().getGuiClick().getPanels().remove(this);
                XIV.getInstance().getGuiClick().getPanels().add(this);
            } else if (mouseButton == 1) {
                open = !open;
            }
        }

        if (isOpen()) {

            for (Element element : elements) {
                if (XIV.getInstance().getGuiClick().getTheme().hasSubMenus()) {
                    if (element instanceof ModButton) {
                        ModButton butt = (ModButton) element;
                        if (butt.open) {
                            for (Element elem : butt.elements) {
                                elem.mouseClicked(mouseX, mouseY, mouseButton);
                            }
                        }
                    }
                }

                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public boolean isOverPanel(int mouseX, int mouseY) {
        return mouseX > getX() && mouseY > getY() && mouseX < getX() + getWidth() && mouseY < getY() + getOpenHeight();
    }

    public void onGuiClosed() {
        this.dragging = false;

        elements.forEach(Element::onGuiClosed);
    }

    public void addModuleElements(ModType type) {
        float elementY = 4;
        for (Mod mod : XIV.getInstance().getModManager().getContents()) {
            if (!mod.getModType().equals(type))
                continue;
            if (mod.getName().equals("ClickGUI"))
                continue;

            getElements().add(new ModButton(mod, x + 2, elementY + 2, XIV.getInstance().getGuiClick().getTheme().getElementWidth(), XIV.getInstance().getGuiClick().getTheme().getElementHeight()));
            elementY += XIV.getInstance().getGuiClick().getTheme().getElementHeight() + 1;
        }
    }

    public void addPanelElements() {
        float elementY = 4;
        for (Panel panel : XIV.getInstance().getGuiClick().getPanels()) {
            getElements().add(new PanelButton(panel, x + 2, elementY + 2, XIV.getInstance().getGuiClick().getTheme().getElementWidth(), XIV.getInstance().getGuiClick().getTheme().getElementHeight()));
            elementY += XIV.getInstance().getGuiClick().getTheme().getElementHeight() + 1;
        }
    }

    public void addSliders() {
        float elementY = 4;

        for (Value value : XIV.getInstance().getValueManager().getContents()) {
            String prefix = value.getName();

            if (value.getName().contains("_")) {
                prefix = value.getName().substring(value.getName().split("_")[0].length());
            }

            String actualName = value.getName().replaceAll(prefix, "");
            String prettyName = "";
            String[] actualNameSplit = actualName.split("_");
            if (actualNameSplit.length > 0) {
                for (String arg : actualNameSplit) {
                    arg = arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length());
                    prettyName += arg + " ";
                }
            } else {
                prettyName = actualNameSplit[0].substring(0, 1).toUpperCase() + actualNameSplit[0].substring(1, actualNameSplit[0].length());
            }

            if (value instanceof ClampedValue) {
                getElements().add(new ValueSlider((ClampedValue) value, prettyName, x + 2, elementY + 2, XIV.getInstance().getGuiClick().getTheme().getElementWidth(), XIV.getInstance().getGuiClick().getTheme().getElementHeight()));
            }

            elementY += XIV.getInstance().getGuiClick().getTheme().getElementHeight() + 1;
        }
    }

    public void addValueElements(String prefix) {
        float elementY = 4;

        for (Value value : XIV.getInstance().getValueManager().getContents()) {
            if (!value.getName().startsWith(prefix))
                continue;
            String actualName = value.getName().replaceAll(prefix, "");
            String prettyName = "";
            String[] actualNameSplit = actualName.split("_");
            if (actualNameSplit.length > 0) {
                for (String arg : actualNameSplit) {
                    arg = arg.substring(0, 1).toUpperCase() + arg.substring(1, arg.length());
                    prettyName += arg + " ";
                }
            } else {
                prettyName = actualNameSplit[0].substring(0, 1).toUpperCase() + actualNameSplit[0].substring(1, actualNameSplit[0].length());
            }

            if (value.getValue() instanceof Boolean) {
                getElements().add(new ValueButton(value, prettyName, x + 2, elementY + 2, XIV.getInstance().getGuiClick().getTheme().getElementWidth(), XIV.getInstance().getGuiClick().getTheme().getElementHeight()));
            }

            if (value instanceof ClampedValue) {
                if (value.getValue() instanceof Float) {
                    ClampedValue<Float> cv = (ClampedValue<Float>) value;
                    elements.add(new ValueSlider(cv, prettyName, x, y + 12, width, height));
                }
            }
            elementY += XIV.getInstance().getGuiClick().getTheme().getElementHeight() + 1;
        }
    }
}
