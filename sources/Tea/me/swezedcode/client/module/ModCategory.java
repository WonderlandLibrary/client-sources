package me.swezedcode.client.module;

public enum ModCategory {

	//Exploit, Movement, Player, World, Render, Combat, NONE;, 
	Exploit("EXPLOIT", 0),
    Motion("MOTION", 1), 
    Player("PLAYER", 2), 
    World("WORLD", 3), 
    Visual("VISUAL", 4), 
    Fight("FIGHT", 5), 
    NONE("NONE", 6),
    Gui("GUI", 7),
    Options("OPTIONS", 8);
	
	private ModCategory(final String s, final int n) {
    }
	
}
