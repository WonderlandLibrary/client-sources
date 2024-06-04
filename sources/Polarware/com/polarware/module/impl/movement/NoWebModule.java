package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.noweb.IntaveNoWeb;
import com.polarware.value.impl.ModeValue;


@ModuleInfo(name = "module.movement.noweb.name", description = "we cuming", category = Category.MOVEMENT)
public class NoWebModule extends Module {
    private final ModeValue mode = new ModeValue("Bypass Mode", this)
            .add(new IntaveNoWeb("Intave", this))
            .setDefault("Intave");
}