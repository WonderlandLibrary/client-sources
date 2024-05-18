/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.example;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.net.URI;
import me.imfr0zen.guiapi.ClickGui;
import me.imfr0zen.guiapi.Colors;
import me.imfr0zen.guiapi.Frame;
import me.imfr0zen.guiapi.GuiFrame;
import me.imfr0zen.guiapi.components.Button;
import me.imfr0zen.guiapi.components.GuiComponent;
import me.imfr0zen.guiapi.components.GuiToggleButton;
import me.imfr0zen.guiapi.example.ExampleClickListener;
import me.imfr0zen.guiapi.example.ExampleExtendListener;
import me.imfr0zen.guiapi.listeners.ExtendListener;

public class ExampleGuiScreen
extends ClickGui {
    @Override
    public void initGui() {
        Colors.setButtonColor(0, 87, 160, 255);
        GuiFrame testFrame = new GuiFrame("Test", 25, 50);
        Button testButton = new Button("TestButton");
        testButton.addClickListener(new ExampleClickListener());
        testButton.addExtendListener(new ExampleExtendListener());
        testFrame.addButton(testButton);
        Button anotherTestButton = new Button("Another Button");
        anotherTestButton.addExtendListener(new ExtendListener(){

            @Override
            public void addComponents() {
                final GuiToggleButton togglebutton = new GuiToggleButton("Click me!");
                togglebutton.addClickListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent actionevent) {
                        System.out.println("Button-State: " + togglebutton.isToggled());
                    }
                });
                this.add(togglebutton);
            }

        });
        testFrame.addButton(anotherTestButton);
        this.addFrame(testFrame);
        GuiFrame anotherTestFrame = new GuiFrame("Another TestFrame", 135, 50);
        Button madeByImFr0zenButton = new Button("Made by ImFr0zen");
        madeByImFr0zenButton.addClickListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.youtube.com/c/ImFr0zen"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        anotherTestFrame.addButton(madeByImFr0zenButton);
        this.addFrame(anotherTestFrame);
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
    }

}

