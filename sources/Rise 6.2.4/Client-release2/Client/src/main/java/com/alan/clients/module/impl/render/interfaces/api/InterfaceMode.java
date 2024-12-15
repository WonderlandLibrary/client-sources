package com.alan.clients.module.impl.render.interfaces.api;

import com.alan.clients.module.Module;
import com.alan.clients.module.impl.render.Interface;
import com.alan.clients.module.impl.render.TargetInfo;
import com.alan.clients.value.Mode;
import lombok.Getter;

@Getter
public class InterfaceMode<I extends Module> extends Mode<Interface> {

    private final Mode<TargetInfo> targetInfo;

    public InterfaceMode(String name, Interface parent, Mode<TargetInfo> targetInfo) {
        super(name, parent);
        this.targetInfo = targetInfo;
    }
}
