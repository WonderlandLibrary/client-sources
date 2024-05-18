package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;

public final class Test extends ModBase
{
    public Test() {
        super("Test", "L", true, ".t test");
        this.setDescription("Test Modules (Only dev)");
    }
    
    @Override
    public void preUpdate() {
        MorbidWrapper.addChat("Need developer rank to use this!");
    }
}
