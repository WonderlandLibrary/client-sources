package ru.FecuritySQ.option.imp;

import ru.FecuritySQ.option.Option;

public class OptionBoolean extends Option {

    public boolean option;
    public OptionBoolean(String name, boolean option) {
        super(name, option);
        this.option = option;
    }

    public void set(boolean o){
        this.option = o;
    }

    public boolean get(){
        return this.option;
    }
}
