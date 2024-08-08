package me.napoleon.napoline.junk.values.type;

import me.napoleon.napoline.junk.values.Value;

public class Bool<B> extends Value<B> {
    public static int animx = 0;
    public Bool(String name,B value){
        super(name);
        this.setValue(value);
    }

    public int getAnimx(){
        return animx;
    }

    public void setAnimx(int animx) {
        Bool.animx = animx;
    }
}
