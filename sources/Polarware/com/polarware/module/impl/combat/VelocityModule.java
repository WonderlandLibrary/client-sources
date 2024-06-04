package com.polarware.module.impl.combat;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.combat.velocity.*;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;

@ModuleInfo(name = "module.combat.velocity.name", description = "module.combat.velocity.description" /* Sorry, Tecnio. */ /* Sorry Hazsi. */, category = Category.COMBAT)
public final class VelocityModule extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new StandardVelocity("Normal", this))
            .add(new BufferAbuseVelocity("Buffer Abuse", this))
            .add(new DelayVelocity("Delay", this))
            .add(new GrimVelocity("Brim", this))
            .add(new LegitVelocity("Legit", this))
            .add(new GroundVelocity("Ground", this))
            .add(new IntaveVelocity("Intave", this))
            .add(new MatrixVelocity("Matrix", this))
            .add(new AACVelocity("AAC", this))
            .add(new VulcanVelocity("Vulcan", this))
            .add(new RedeskyVelocity("Redesky", this))
            .add(new TickVelocity("Tick", this))
            .add(new BounceVelocity("Bounce", this))
            .add(new KarhuVelocity("Karhu", this))
            .add(new MMCVelocity("MMC", this))
            .add(new UniversoCraftVelocity("Universocraft", this))
            .add(new WatchdogVelocity("Watchdog", this))
            .setDefault("Standard");

    public final BooleanValue onSwing = new BooleanValue("On Swing", this, false);
    public final BooleanValue onSprint = new BooleanValue("On Sprint", this, false);
}
