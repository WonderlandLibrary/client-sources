package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;

public class TabMod extends ModuleContainer
{
    public static BooleanValue noPing;
    public static BooleanValue extended;
    public static BooleanValue noHeads;
    public static BooleanValue noNames;
    
    public TabMod() {
        super(Category.RENDER, "TabMod", false, -1, "Allows the tab menu to be customized");
    }
    
    @Override
    public void E() {
        HookTranslator.v7 = false;
        HookTranslator.v13 = false;
        HookTranslator.v11 = false;
        HookTranslator.v12 = false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (TabMod.extended.M()) {
            HookTranslator.v7 = true;
        }
        else {
            HookTranslator.v7 = false;
        }
        if (TabMod.noHeads.M()) {
            HookTranslator.v13 = true;
        }
        else {
            HookTranslator.v13 = false;
        }
        if (TabMod.noNames.M()) {
            HookTranslator.v11 = true;
        }
        else {
            HookTranslator.v11 = false;
        }
        if (TabMod.noPing.M()) {
            HookTranslator.v12 = true;
            return;
        }
        HookTranslator.v12 = false;
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    static {
        TabMod.extended = ValueFactory.M("TabMod", "Extended", "Shows all players in tab menu", false);
        TabMod.noHeads = ValueFactory.M("TabMod", "NoHeads", "Stops player heads from rendering", false);
        TabMod.noNames = ValueFactory.M("TabMod", "NoNames", "Stops playernames from rendering", false);
        TabMod.noPing = ValueFactory.M("TabMod", "NoPing", "Stops ping from rendering", false);
    }
}
