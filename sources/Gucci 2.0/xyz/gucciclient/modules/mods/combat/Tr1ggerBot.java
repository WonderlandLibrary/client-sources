package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import xyz.gucciclient.utils.*;
import java.util.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;

public class Tr1ggerBot extends Module
{
    private NumberValue maxcps;
    private NumberValue mincps;
    private BooleanValue weapon;
    private Random random;
    private Timer2 tajmManager;
    public static final HashMap<String, Object> PROPERTIES;
    
    public Tr1ggerBot() {
        super("Tr1ggerBot", 0, Category.OTHER);
        this.maxcps = new NumberValue("MaxAPS", 12.0, 1.0, 20.0);
        this.mincps = new NumberValue("MinAPS", 6.0, 1.0, 20.0);
        this.weapon = new BooleanValue("Weapon", false);
        this.addValue(this.maxcps);
        this.addValue(this.mincps);
        this.addBoolean(this.weapon);
        this.random = new Random();
        adProperti("heh", 100L);
        this.tajmManager = new Timer2();
        adProperti("hehe", 10);
    }
    
    public static void adProperti(final String name, final Object value) {
        Tr1ggerBot.PROPERTIES.put(name, value);
    }
    
    public static Integer getInteger(final String name) {
        return (Integer) Tr1ggerBot.PROPERTIES.get(name);
    }
    
    public static Long getLong(final String name) {
        return (Long) Tr1ggerBot.PROPERTIES.get(name);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) throws Exception, Throwable {
        final int fluctuation = getInteger("hehe");
        if (this.tajmManager.sleep(getLong("heh") + this.randoasm(-fluctuation, fluctuation)) && this.hehjehehe() && (!this.weapon.getState() || this.ludacina()) && this.mc.currentScreen == null) {
            this.mc.thePlayer.swingItem();
            final int skip = this.randoasm(0, 10);
            if (skip != this.mincps.getValue() || skip != this.maxcps.getValue()) {
                this.mc.playerController.attackEntity((EntityPlayer)this.mc.thePlayer, this.mc.objectMouseOver.entityHit);
            }
            this.tajmManager.reset();
        }
    }
    
    private boolean hehjehehe() {
        return this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer;
    }
    
    private boolean ludacina() {
        return this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
    }
    
    private int randoasm(final int floor, final int cap) {
        return floor + this.random.nextInt(cap - floor + 1);
    }
    
    static {
        PROPERTIES = new HashMap<String, Object>();
    }
}
