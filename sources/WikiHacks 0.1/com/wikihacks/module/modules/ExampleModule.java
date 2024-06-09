package com.wikihacks.module.modules;

import com.wikihacks.module.Category;
import com.wikihacks.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ExampleModule extends Module {

    public ExampleModule() {
        super("ExampleModule", new String[]{"example", "exmpl", "cock"}, "Epic Example module", Category.MISC);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onUpdate() {
        System.out.println("test");
    }
}
