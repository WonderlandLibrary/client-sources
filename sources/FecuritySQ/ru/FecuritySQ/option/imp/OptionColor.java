package ru.FecuritySQ.option.imp;

import ru.FecuritySQ.option.Option;

import java.awt.*;

public class OptionColor extends Option {

    public Color option;
    public OptionColor(String name, Color option) {
        super(name, option);
        this.option = option;
    }

    public void set(Color o){
        this.option = o;
    }
    public void setColorValue(int color) {
        this.option = new Color(color);
    }

    public Color get(){
        return this.option;
    }
}
