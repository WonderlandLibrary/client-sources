package com.alan.clients.protection.impl;

import com.alan.clients.Client;
import com.alan.clients.protection.ProtectionCheck;
import com.alan.clients.protection.api.McqBFVadWB;

public final class McqBFVsdWB extends ProtectionCheck {

    public McqBFVsdWB() {
        super(McqBFVadWB.REPETITIVE, false);

        // Make an attempt at removing the security manager if one is present for some reason.
        System.setSecurityManager(null);
    }

    @Override
    public boolean check() {
        if (System.getSecurityManager() != null) {
            Client.INSTANCE.getMcqAFVeaWB().crash();
            return true;
        }

        return false;
    }
}
