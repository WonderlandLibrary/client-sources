package me.teus.eclipse.modules.value.impl;

import lombok.Getter;
import lombok.Setter;
import me.teus.eclipse.modules.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModeValue extends Value {
    public List<String> modes = new ArrayList<>();
    private String selected;
    public int index;
    public ModeValue(String name, String defaultM, String... modes) {
        super(name, false);
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultM);
        this.selected = this.modes.get(this.index);
    }

    public String getMode(){
        return this.modes.get(this.index);
    }

    public List<String> getModes(){
        return this.modes;
    }

    public boolean is(String mode){
        return (this.index == this.modes.indexOf(mode));
    }

    public void cycle(boolean positive){
        if(positive){
            if(this.index < this.modes.size() - 1){
                this.index++;
            }else{
                this.index = 0;
            }
        }else{
            if(this.index <= 0){
                this.index = this.modes.size() - 1;
            }else{
                this.index--;
            }
        }
    }

    public void setSelected(String arg) {
        this.selected = arg;
        this.index = this.modes.indexOf(arg);
    }
}
