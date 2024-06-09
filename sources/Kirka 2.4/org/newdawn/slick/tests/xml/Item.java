/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests.xml;

import java.io.PrintStream;

public class Item {
    protected String name;
    protected int condition;

    public void dump(String prefix) {
        System.out.println(prefix + "Item " + this.name + "," + this.condition);
    }
}

