package com.alan.clients.protection;

import com.alan.clients.protection.api.McqBFVadWB;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ProtectionCheck {

    private final McqBFVadWB trigger;
    private final boolean exemptDev;

    public abstract boolean check() throws Throwable;
}
