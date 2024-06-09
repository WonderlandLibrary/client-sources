package me.valk.overlay.tabGui.parts;

import org.lwjgl.input.Keyboard;

import me.valk.module.ModMode;
import me.valk.module.Module;
import me.valk.overlay.tabGui.TabPart;
import me.valk.utils.value.BooleanValue;
import me.valk.utils.value.RestrictedValue;
import me.valk.utils.value.Value;

/**
 * Created by Zeb on 4/22/2016.
 */
public class TabValuePart extends TabPart {

    private Module module;
    private Value value;

    public TabValuePart(String text, TabPanel parent, Value value, Module module){
        super(text, parent);
        this.value = value;
        this.module = module;
    }

    @Override
    public void onKeyPress(int key) {
        if(key == Keyboard.KEY_RIGHT){
            TabPanel panel = new TabPanel(this.getParent().getTabGui());
            panel.setVisible(true);

            if(value.getClass() == RestrictedValue.class){
                panel.addElement(new TabRestrictedValuePart(value.getValue() + "", panel, (RestrictedValue) value));
            }else if(value.getClass() == BooleanValue.class){
                panel.addElement(new TabBooleanValuePart(value.getValue() + "", panel, (BooleanValue) value));
            }

            if(value.getName().equals("mode")){
                for(ModMode mode : module.getModes()){
                    panel.addElement(new TabModePart(module, mode, panel));
                }
            }

            if(panel.getElements().isEmpty()) return;

            this.getParent().getTabGui().addPanel(panel);
        }
    }
}
