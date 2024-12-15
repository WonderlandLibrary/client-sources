package com.alan.clients.packetlog.impl;

import com.alan.clients.packetlog.Check;
import com.sun.tools.attach.VirtualMachine;

import java.util.Arrays;

public class ProxyCheck extends Check {

    private static final String[] HARAM = {
            "proxycap"
    };

    @Override
    public boolean run() {
        return VirtualMachine.list().stream().anyMatch(descriptor -> {
            final String name = descriptor.displayName().toLowerCase().trim();

            return Arrays.stream(HARAM).anyMatch(name::contains);
        });
    }
}
