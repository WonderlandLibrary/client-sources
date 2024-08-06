package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Correct GCD",
    uniqueId = "gcdcorrection",
    description = "Divides All Rotations By A Common Denominator",
    category = ModuleCategory.Combat
)
public class CorrectGCD extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @ConfigOption(
        name = "Greatest Common Divisor",
        description = "Number To Divide By",
        min = 0.05,
        max = 1,
        order = 2,
        precision = 2
    )
    public static Float GCDnumber = 0.08f;
}
