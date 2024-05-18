package me.protocol_client.ui.click.Protocol.Panel;

import java.util.ArrayList;

import me.protocol_client.Protocol;
import me.protocol_client.files.allfiles.ValuesFile;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ModStuff;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.PanelStuff;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ValueButton;
import me.protocol_client.ui.click.Protocol.Elements.moreElements.ValueSlider;
import me.protocol_client.utils.RenderUtils2D;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Panel {
    private final String name;
    private float x, y, width, height, openheight, buttonoffset = 1;
    private boolean dragging, open, showing;
    private boolean pinned;
    private float dragX, dragY;
    private ArrayList<Element> elements;
    public float tempx;
    public float tempy;
    public boolean opened;

    public Panel(String name, ArrayList<Element> elements, float x, float y, float width, float height) {
        this(name, elements, x, y, width, height, false, false);
    }

    public Panel(String name, ArrayList<Element> elements, float x, float y, float width, float height, boolean showing, boolean pinned) {
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
    public float getTempX(){
    	return tempx;
    }
    public float getTempY(){
    	return tempy;
    }
    public void setTempX(float tempx){
    	this.tempx = tempx;
    }
    public void setTempY(float tempy){
    	this.tempx = tempy;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setPinned(boolean pinned){
    	this.pinned = pinned;
    }
    public boolean isPinned(){
    	return pinned;
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
        return true;
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
            this.tempx = x;
            this.tempy = y;
            
            if (!Mouse.isButtonDown(0)) {
                setDragging(false);
                Protocol.getOtherFileManager().saveFile("gui");
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (x < 0)
                x = 1;
            if (y < 0)
                y = 1;
            if (x + width > RenderUtils2D.newScaledResolution().getScaledWidth())
                x = RenderUtils2D.newScaledResolution().getScaledWidth() - width;
            if (y + height > RenderUtils2D.newScaledResolution().getScaledHeight())
                y = RenderUtils2D.newScaledResolution().getScaledHeight() - height - 2;
        }

        Protocol.getGuiClick().getTheme().renderPanel(this);

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
        	ValuesFile.save();
            if (mouseButton == 0) {
                dragX = (getX() - mouseX);
                dragY = (getY() - mouseY);

                dragging = true;
                for (Panel panel : Protocol.getGuiClick().getPanels()) {
                    if (panel.equals(this)) continue;
                    panel.dragging = false;
                }
                Protocol.getGuiClick().getPanels().remove(this);
                Protocol.getGuiClick().getPanels().add(this);
            } else if (mouseButton == 1) {
            	if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                open = !open;
            	}else{
            		this.pinned = !pinned;
            	}
            }
        }

        if (isOpen()) {
            for (Element element : elements) {
                if (Protocol.getGuiClick().getTheme().hasSubMenus()) {
                    if (element instanceof ModStuff) {
                        ModStuff butt = (ModStuff) element;
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
        tempx = this.getX();
        tempy = this.getY();
        elements.forEach(Element::onGuiClosed);
    }

    public void addModuleElements(Category type) {
        float elementY = 4;
        for (Module mod : Protocol.getModules()) {
            if (!mod.getCategory().equals(type))
                continue;
            getElements().add(new ModStuff(mod, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
            elementY += Protocol.getGuiClick().getTheme().getElementHeight() + 1;
        }
    }

    public void addPanelElements() {
        float elementY = 4;
        for (Panel panel : Protocol.getGuiClick().getPanels()) {
            getElements().add(new PanelStuff(panel, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
            elementY += Protocol.getGuiClick().getTheme().getElementHeight() + 1;
        }
    }

    public void addSliders() {
        float elementY = 4;

        for (Value value : Protocol.getValueManager().getContents()) {
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
                getElements().add(new ValueSlider((ClampedValue) value, prettyName, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
            }

            elementY += Protocol.getGuiClick().getTheme().getElementHeight() + 1;
        }
    }

    public void addValueElements(String prefix) {
        float elementY = 4;

        for (Value value : Protocol.getValueManager().getContents()) {
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
                getElements().add(new ValueButton(value, prettyName, x + 2, elementY + 2, Protocol.getGuiClick().getTheme().getElementWidth(), Protocol.getGuiClick().getTheme().getElementHeight()));
            }

            if (value instanceof ClampedValue) {
                if (value.getValue() instanceof Float) {
                    ClampedValue<Float> cv = (ClampedValue<Float>) value;
                    elements.add(new ValueSlider(cv, prettyName, x, y + 12, width, height));
                }
            }
            elementY += Protocol.getGuiClick().getTheme().getElementHeight() + 1;
        }
    }
}
