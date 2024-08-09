package ru.FecuritySQ.option.imp;


import ru.FecuritySQ.option.Option;

public class OptionNumric extends Option {

    public float min, max, current, increment;

    public OptionNumric(String name, float current, float min, float max, float increment) {
        super(name, current);
        this.current = current;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public float get(){
        return this.current;
    }

    public float getMax() {
        return max;
    }

    public float getCurrent() {
        return current;
    }

    public float getMin() {
        return min;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public void setMin(float min) {
        this.min = min;
    }
}
