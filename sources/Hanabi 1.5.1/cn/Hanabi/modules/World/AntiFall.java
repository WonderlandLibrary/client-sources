package cn.Hanabi.modules.World;

import net.minecraft.util.*;
import cn.Hanabi.value.*;
import ClassSub.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.block.*;
import cn.Hanabi.events.*;
import cn.Hanabi.*;
import cn.Hanabi.modules.*;
import net.minecraft.client.entity.*;

public class AntiFall extends Mod
{
    BlockPos lastGroundPos;
    Class205 timer;
    Class205 timer2;
    Class205 spacetimer;
    public Value<Double> falldistance;
    public Value<Boolean> onlyvoid;
    
    
    public AntiFall() {
        super("AntiFall", Category.WORLD);
        this.timer = new Class205();
        this.timer2 = new Class205();
        this.spacetimer = new Class205();
        this.falldistance = new Value<Double>("AntiFall_FallDistance", 10.0, 5.0, 30.0, 0.1);
        this.onlyvoid = new Value<Boolean>("AntiFall_OnlyVoid", true);
    }
    
    @EventTarget
    public void onKey(final EventKey eventKey) {
        if (Class334.username.length() < 1) {
            System.exit(0);
        }
        if (eventKey.getKey() == 57) {
            this.spacetimer.reset();
        }
    }
    
    private boolean isBlockUnder() {
        for (int i = (int)(AntiFall.mc.thePlayer.posY - 1.0); i > 0; --i) {
            if (!(AntiFall.mc.theWorld.getBlockState(new BlockPos(AntiFall.mc.thePlayer.posX, (double)i, AntiFall.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (Class334.username.length() < 1) {
            System.exit(0);
        }
        boolean b = true;
        if (AntiFall.mc.thePlayer.fallDistance > this.falldistance.getValueState() && (!this.onlyvoid.getValueState() || !this.isBlockUnder())) {
            b = false;
        }
        if (Hanabi.flag > 0) {
            if (AntiFall.mc.thePlayer.onGround && this.timer.isDelayComplete(1000L)) {
                this.lastGroundPos = AntiFall.mc.thePlayer.getPosition();
                this.timer.reset();
            }
            if (!b && this.spacetimer.isDelayComplete(800L) && !ModManager.getModule("Fly").isEnabled()) {
                AntiFall.mc.thePlayer.setPosition((double)this.lastGroundPos.getX(), (double)this.lastGroundPos.getY(), (double)this.lastGroundPos.getZ());
            }
        }
        else if (AntiFall.mc.thePlayer.motionY < 0.0) {
            final EntityPlayerSP thePlayer = AntiFall.mc.thePlayer;
            thePlayer.motionY *= 1.2;
        }
    }
}
