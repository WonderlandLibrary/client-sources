package me.vaziak.sensation.client.api.gui.ingame.clickui.configuration;

import java.util.HashMap;

import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Element;
import me.vaziak.sensation.client.api.gui.ingame.HUD.element.Quadrant;
import me.vaziak.sensation.client.api.property.impl.Value;

/**
 * @author antja03
 */
public class HudElementData {

    private String name;
    private Quadrant quadrant;
    private double positionX;
    private double positionY;
    private HashMap<String, String> valueData;

    public HudElementData(String name, Quadrant quadrant, double positionX, double positionY, HashMap<String, String> valueData) {
        this.name = name;
        this.quadrant = quadrant;
        this.positionX = positionX;
        this.positionY = positionY;
        this.valueData = valueData;
    }

    public HudElementData(Element element) {
        this.name = element.getIdentifier();
        this.quadrant = element.getQuadrant();
        this.positionX = element.getPositionX();
        this.positionY = element.getPositionY();
        this.valueData = new HashMap<>();

        for (Value value : element.getValueRegistry().values()) {
            valueData.put(value.getId(), value.getValueAsString());
        }
    }

    public String getName() {
        return name;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public HashMap<String, String> getValueData() {
        return valueData;
    }

}
