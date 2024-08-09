/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_10to1_11.storage;

public class ChestedHorseStorage {
    private boolean chested;
    private int liamaStrength;
    private int liamaCarpetColor = -1;
    private int liamaVariant;

    public boolean isChested() {
        return this.chested;
    }

    public void setChested(boolean bl) {
        this.chested = bl;
    }

    public int getLiamaStrength() {
        return this.liamaStrength;
    }

    public void setLiamaStrength(int n) {
        this.liamaStrength = n;
    }

    public int getLiamaCarpetColor() {
        return this.liamaCarpetColor;
    }

    public void setLiamaCarpetColor(int n) {
        this.liamaCarpetColor = n;
    }

    public int getLiamaVariant() {
        return this.liamaVariant;
    }

    public void setLiamaVariant(int n) {
        this.liamaVariant = n;
    }

    public String toString() {
        return "ChestedHorseStorage{chested=" + this.chested + ", liamaStrength=" + this.liamaStrength + ", liamaCarpetColor=" + this.liamaCarpetColor + ", liamaVariant=" + this.liamaVariant + '}';
    }
}

