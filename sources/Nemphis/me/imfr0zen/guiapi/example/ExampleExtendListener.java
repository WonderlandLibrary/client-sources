/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.example;

import java.io.PrintStream;
import me.imfr0zen.guiapi.components.GuiComponent;
import me.imfr0zen.guiapi.components.GuiGetKey;
import me.imfr0zen.guiapi.components.GuiLabel;
import me.imfr0zen.guiapi.components.GuiSlider;
import me.imfr0zen.guiapi.components.GuiTextField;
import me.imfr0zen.guiapi.components.GuiTree;
import me.imfr0zen.guiapi.listeners.ExtendListener;
import me.imfr0zen.guiapi.listeners.KeyListener;
import me.imfr0zen.guiapi.listeners.TextListener;
import me.imfr0zen.guiapi.listeners.ValueListener;

public class ExampleExtendListener
extends ExtendListener {
    @Override
    public void addComponents() {
        this.add(new GuiLabel("Hello!"));
        GuiSlider slider = new GuiSlider("Value:", 88.0f, 1337.0f, 666.0f);
        slider.addValueListener(new ValueListener(){

            @Override
            public void valueUpdated(float value) {
                System.out.println("Value updated: " + value);
            }

            @Override
            public void valueChanged(float value) {
                System.out.println("Value changed: " + value);
            }
        });
        GuiGetKey getKey = new GuiGetKey(54);
        getKey.addKeyListener(new KeyListener(){

            @Override
            public void keyChanged(int key) {
                System.out.println("Key: " + key);
            }
        });
        GuiTextField textField = new GuiTextField("Enter some Text here", "Text");
        textField.addTextListener(new TextListener(){

            @Override
            public void stringEntered(String text) {
                System.out.println("You have entered: " + text);
            }

            @Override
            public void keyTyped(char key, String text) {
                System.out.println("You have added " + key + " to this text: " + text);
            }
        });
        this.add(new GuiTree("Examples", slider, getKey, textField));
    }

}

