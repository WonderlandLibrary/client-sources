package wwe.modules.combat;

import wwe.modules.*;
import wwe.utils.*;
import wwe.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.text.*;

public class AutoLog extends qMIe
{
    public BooleanValue autoLog;
    public BooleanValue crystalLog;
    public NumberValue health;
    public NumberValue crystalRange;
    public BooleanValue entities;
    
    public AutoLog() {
        super("Auto Log", "Automatically logs at a set health", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        this.autoLog = new BooleanValue("AutoLog", this, "Log from health", true);
        this.crystalLog = new BooleanValue("CrystalLog", this, "Log from crystal", false);
        this.health = new NumberValue("Health", this, "Health before you auto log", 4.0, 1.0, 20.0);
        this.crystalRange = new NumberValue("Crystal Range", this, "Range from a crystal to logout", 6.0, 1.0, 20.0);
        this.entities = new BooleanValue("Entities", this, "Log if the riding entity's health is low", false);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.autoLog.OnGi() && Wrapper.mc.player.getHealth() <= this.health.OnGi()) {
            Wrapper.mc.player.connection.getNetworkManager().closeChannel((ITextComponent)this.QQsS("Aborting mission, you were at " + Wrapper.mc.player.getHealth() + " health"));
            vlwv.hNxW.qHPF((Class<AutoLog>)YQHE.class).GDVp();
        }
        if (this.entities.OnGi() && Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.player.getRidingEntity() instanceof EntityLivingBase && ((EntityLivingBase)Wrapper.mc.player.getRidingEntity()).getHealth() <= this.health.OnGi()) {
            Wrapper.mc.player.connection.getNetworkManager().closeChannel((ITextComponent)this.QQsS("Aborting mission, the entity you were riding was at " + Wrapper.mc.player.getHealth() + " health"));
            vlwv.hNxW.qHPF((Class<AutoLog>)YQHE.class).GDVp();
        }
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (this.crystalLog.OnGi() && entity instanceof EntityEnderCrystal && Wrapper.mc.player.getDistance(entity) <= this.crystalRange.OnGi()) {
                Wrapper.mc.player.connection.getNetworkManager().closeChannel((ITextComponent)this.QQsS("Aborting mission, Ender crystal " + Wrapper.mc.player.getDistance(entity) + " away from you"));
                vlwv.hNxW.qHPF((Class<AutoLog>)YQHE.class).GDVp();
            }
            if (AutoLog.\u2003\u200a\u200c\u200f\u2002\u2003\u200d) {
                throw null;
            }
        }
    }
    
    public TextComponentString QQsS(final String s) {
        return new TextComponentString(TextFormatting.DARK_GRAY + "[" + TextFormatting.DARK_RED + TextFormatting.BOLD + "WWE" + TextFormatting.DARK_GRAY + "]" + TextFormatting.DARK_RED + " " + s.replace(" ", TextFormatting.DARK_RED + " "));
    }
}
