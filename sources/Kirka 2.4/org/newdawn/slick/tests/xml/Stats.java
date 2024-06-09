/*
 * Decompiled with CFR 0.143.
 */
package org.newdawn.slick.tests.xml;

import java.io.PrintStream;

public class Stats {
    private int hp;
    private int mp;
    private float age;
    private int exp;

    public void dump(String prefix) {
        System.out.println(prefix + "Stats " + this.hp + "," + this.mp + "," + this.age + "," + this.exp);
    }
}

