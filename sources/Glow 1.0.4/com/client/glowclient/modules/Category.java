package com.client.glowclient.modules;

import com.client.glowclient.*;

public enum Category
{
    MOVEMENT("Movement", "Movement mods");
    
    private String c;
    private String k;
    
    PLAYER("Player", "Mods that interact with the local player"), 
    OTHER("Other", "Window for other mods"), 
    RENDER("Render", "2D/3D rendering mods"), 
    COMBAT("Combat", "Mods used for combat"), 
    SERVICE("Service", "Background mods"), 
    NONE("NONE", "No assigned category"), 
    SERVER("Server", "Any mod that has to do with the server"), 
    JEWISH TRICKS("Jewish Tricks", "Emperor's Jewish Tricks");
    
    private static final Category[] b;
    
    public String D() {
        return this.c;
    }
    
    public String M() {
        return this.k;
    }
    
    public static Category valueOf(final String s) {
        return Enum.valueOf((Class<Category>)fb.class, s);
    }
    
    public static Category[] values() {
        return Category.b.clone();
    }
    
    static {
        b = new Category[] { Category.PLAYER, Category.RENDER, Category.SERVER, Category.SERVICE, Category.MOVEMENT, Category.NONE, Category.OTHER, Category.COMBAT, Category.JEWISH TRICKS };
    }
    
    private Category(final String c, final String k) {
        this.c = c;
        this.k = k;
    }
}
