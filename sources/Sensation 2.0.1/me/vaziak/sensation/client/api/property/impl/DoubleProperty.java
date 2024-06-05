package me.vaziak.sensation.client.api.property.impl;

import org.apache.commons.lang3.math.NumberUtils;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.event.events.UpdateValueEvent;

/**
 * @author antja03
 */
public class DoubleProperty extends Value<Double> {

    private double minimum;
    private double maximum;
    private double increment;
    private String numType;

    public DoubleProperty(String id, String description, me.vaziak.sensation.utils.anthony.Dependency dependency, double defaultValue, double minimum, double maximum, double increment, String numType) {
        super(id, description, dependency);
        this.value = defaultValue;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        this.numType = numType == null ? "" : numType;
    }
    

    public DoubleProperty(String id, String description, me.vaziak.sensation.utils.anthony.Dependency dependency, double defaultValue, double minimum, double maximum, double increment) {
        super(id, description, dependency);
        this.value = defaultValue;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        this.numType = "";
    }
    
    public DoubleProperty(String id, String description, double defaultValue, double minimum, double maximum, double increment) {
        super(id, description);
        this.value = defaultValue;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
        this.numType = "";
    }


	@Override
    public void setValue(String input) {
        if (NumberUtils.isNumber(input)) {
            double oldValue = getValue();
            double newValue = Double.parseDouble(input);

            if (newValue < minimum) {
                newValue = minimum;
            } else if (newValue > maximum) {
                newValue = maximum;
            }

            value = newValue;

            Sensation.eventBus.publish(new UpdateValueEvent(this, oldValue, getValue()));
        }
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getIncrement() {
        return increment;
    }

    public String getNumType() {
        return numType;
    }
}
