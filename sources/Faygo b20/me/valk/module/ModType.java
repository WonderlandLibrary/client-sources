package me.valk.module;

public enum ModType
{
    COMBAT("Combat", 0, "Combat"), 
    MOVEMENT("Movement", 1, "Movement"), 
    PLAYER("Player", 2, "Player"),
    RENDER("MotionBlur", 3, "MotionBlur"), 
    WORLD("World", 4, "World"),
    OTHER("Other", 5, "Other");
	//COMBAT, MOVEMENT, PLAYER, MotionBlur WORLD
    
    private ModType(final String s, final int n, final String catName) {
    }
}