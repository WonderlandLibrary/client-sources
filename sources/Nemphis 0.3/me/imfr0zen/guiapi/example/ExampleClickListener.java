/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

public class ExampleClickListener
implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionevent) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        System.out.println("Button " + actionevent.getID() + " was clicked at " + sdf.format(actionevent.getWhen()));
    }
}

