package me.vaziak.sensation.client.api.gui.ingame.HUD.element;


import java.util.HashMap;

import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.Value;

/**
 * @author antja03
 */
public abstract class Element {

    public String identifier;
    public Quadrant quadrant;
    public double positionX, positionY;
    public double editX, editY, width, height;
    public final HashMap<String, Value> valueRegistry = new HashMap<>();

    public Element(String identifier, Quadrant quadrant, double positionX, double positionY) {
        this.identifier = identifier;
        this.quadrant = quadrant;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    protected void registerValue(Value value) {
        this.valueRegistry.put(value.getId(), value);
    }

    public abstract void drawElement(boolean editor);

    public void onKeyPress(int keyCode) {
    }

    public String getIdentifier() {
        return identifier;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        this.quadrant = quadrant;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public HashMap<String, Value> getValueRegistry() {
        return valueRegistry;
    }
}
