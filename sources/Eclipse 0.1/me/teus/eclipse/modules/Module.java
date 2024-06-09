package me.teus.eclipse.modules;

import me.teus.eclipse.Client;
import me.teus.eclipse.modules.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Module {

    public static Module instance;

    public Minecraft mc = Minecraft.getMinecraft();

    public Category category = this.getClass().getAnnotation(Info.class).category();
    public String name = this.getClass().getAnnotation(Info.class).name();
    public String displayName = this.getClass().getAnnotation(Info.class).displayName();
    public Color color;
    public String suffix;
    public List<Value> values = new ArrayList<>();
    public int keybind = this.getClass().getAnnotation(Info.class).bind();
    public boolean toggled;

    private boolean showSet;

    public void toggle(){
        toggled = !toggled;
        if(toggled){
            onEnable();
            Client.getInstance().eventProtocol.register(this);
        }else{
            onDisable();
            Client.getInstance().eventProtocol.unregister(this);
        }
    }
    public int getColor() {
        return color.getRGB();
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public boolean isToggled(){
        return toggled;
    }

    public void toggle2(boolean state){
        toggled = state;
        if(toggled){
            Client.getInstance().eventProtocol.register(this);
        }else{
            Client.getInstance().eventProtocol.unregister(this);
        }
    }

    public void onEnable(){ }
    public void onDisable(){ }

    public String getName() {
        return name;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKey(int keybind) {
        this.keybind = keybind;
    }

    public String getDisplayName() {
        String name;
        if(!(suffix == null || suffix == " ")){
            name = this.displayName + " " + EnumChatFormatting.GRAY + suffix;
        }else{
            name = this.displayName;
        }
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public List<Value> getValues(){
        return values;
    }

    public void addValues(Value value){
        values.add(value);
    }

    public boolean isShowSet() {
        return showSet;
    }

    public void setShowSet(boolean showed) {
        this.showSet = showed;
    }

    public void addValues(Value... values) {
        for (Value value : values){
            this.values.add(value);
        }
    }
}
