package com.alan.clients.protection.impl;

import com.alan.clients.protection.ProtectionCheck;
import com.alan.clients.protection.api.McqBFVadWB;

public final class McqBFVeaWD extends ProtectionCheck {

    public McqBFVeaWD() {
        super(McqBFVadWB.REPETITIVE, false);
    }

    @Override
    public boolean check() {
        System.setProperty("http.ProxyHost", "");
        System.setProperty("https.ProxyHost", "");
        System.setProperty("http.ProxyPort", "");
        System.setProperty("https.ProxyPort", "");

        return false;
    }
}
