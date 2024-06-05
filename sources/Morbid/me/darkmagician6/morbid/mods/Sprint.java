package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;

public final class Sprint extends ModBase
{
    private double sprintSpeed;
    
    public Sprint() {
        super("Sprint", "G", true, ".t sprint");
        this.sprintSpeed = 1.05;
        this.setDescription("Sprints for you.");
    }
    
    @Override
    public void postUpdate() {
        if (this.canSprint()) {
            this.getWrapper();
            if (!MorbidWrapper.getPlayer().ah() && !this.stopSprint()) {
                this.getWrapper();
                MorbidWrapper.getPlayer().c(true);
                return;
            }
        }
        if (!this.canSprint() || this.stopSprint()) {
            this.getWrapper();
            MorbidWrapper.getPlayer().c(false);
        }
    }
    
    @Override
    public void preUpdate() {
        if (!this.isEnabled()) {
            return;
        }
        this.getWrapper();
        if (MorbidWrapper.getPlayer().ah()) {
            this.getWrapper();
            final bdv player = MorbidWrapper.getPlayer();
            player.aO *= (float)this.sprintSpeed;
            this.getWrapper();
            final bdv player2 = MorbidWrapper.getPlayer();
            player2.aP *= (float)this.sprintSpeed;
        }
    }
    
    @Override
    public void onCommand(final String s) {
        final String[] split = s.split(" ");
        if (s.toLowerCase().startsWith(".rs")) {
            try {
                final double speed = Double.parseDouble(split[1]);
                this.sprintSpeed = speed;
                this.getWrapper();
                MorbidWrapper.addChat("Sprint speed set to: " + this.sprintSpeed);
            }
            catch (Exception e) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .rs [speed]");
            }
            ModBase.setCommandExists(true);
        }
    }
    
    private boolean canSprint() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().cn().a() > 6) {
            this.getWrapper();
            if (MorbidWrapper.getPlayer().b.b > 0.0f) {
                if (KillAura.curTarget != null) {
                    this.getWrapper();
                    if (MorbidWrapper.getPlayer().aR > 0.0f) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    private boolean stopSprint() {
        this.getWrapper();
        if (!MorbidWrapper.getPlayer().g_()) {
            this.getWrapper();
            if (!MorbidWrapper.getPlayer().bX()) {
                this.getWrapper();
                if (!MorbidWrapper.getPlayer().ag()) {
                    this.getWrapper();
                    if (!MorbidWrapper.getPlayer().G) {
                        this.getWrapper();
                        if (!MorbidWrapper.getPlayer().G()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
