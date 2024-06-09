package com.client.glowclient.modules.combat;

import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BoatAura extends ModuleContainer
{
    public static BooleanValue targetRidden;
    public static final NumberValue range;
    private long A;
    public static BooleanValue throughWalls;
    private long b;
    
    @Override
    public String M() {
        return String.format("%.1f", BoatAura.range.k());
    }
    
    static {
        range = ValueFactory.M("BoatAura", "Range", "Explode hit range", 3.5, 0.5, 0.0, 10.0);
        BoatAura.throughWalls = ValueFactory.M("BoatAura", "ThroughWalls", "Hit through walls", false);
        BoatAura.targetRidden = ValueFactory.M("BoatAura", "TargetRidden", "Targets the ridden boat", false);
    }
    
    @Override
    public void E() {
        y.M(this);
    }
    
    public BoatAura() {
        final long a = -1L;
        final long b = 0L;
        super(Category.COMBAT, "BoatAura", false, -1, "Automatically attacks nearby boats");
        this.b = b;
        this.A = a;
    }
    
    public boolean M(final long n) {
        return this.b - this.A >= n;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            final double n = 4.0;
            final EntityPlayerSP player = Wrapper.mc.player;
            final ItemStack heldItemMainhand = Wrapper.mc.player.getHeldItemMainhand();
            final ItemStack heldItemOffhand = Wrapper.mc.player.getHeldItemOffhand();
            if ((heldItemMainhand == null || (!(heldItemMainhand.getItem() instanceof ItemFood) && !(heldItemMainhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown()) && (heldItemOffhand == null || (!(heldItemOffhand.getItem() instanceof ItemFood) && !(heldItemOffhand.getItem() instanceof ItemAppleGold)) || !Wrapper.mc.gameSettings.keyBindUseItem.isKeyDown())) {
                final double n2 = 1000.0;
                this.b = System.nanoTime() / 1000000L;
                if (this.M((long)(n2 / n))) {
                    final Iterator<Entity> iterator = (Iterator<Entity>)Wrapper.mc.world.loadedEntityList.iterator();
                    while (iterator.hasNext()) {
                        final Entity entity;
                        if ((entity = iterator.next()) != null && player.getDistance(entity) < BoatAura.range.k() && this.M(entity)) {
                            if (entity instanceof EntityBoat) {
                                if (!BoatAura.targetRidden.M()) {
                                    if (entity == Wrapper.mc.player.getRidingEntity()) {
                                        continue;
                                    }
                                    final EntityPlayerSP entityPlayerSP = player;
                                    y.M(entity, this);
                                    Wrapper.mc.playerController.attackEntity((EntityPlayer)player, entity);
                                    entityPlayerSP.swingArm(EnumHand.MAIN_HAND);
                                    this.A = System.nanoTime() / 1000000L;
                                }
                                else {
                                    y.M(entity, this);
                                    Wrapper.mc.playerController.attackEntity((EntityPlayer)player, entity);
                                    player.swingArm(EnumHand.MAIN_HAND);
                                    this.A = System.nanoTime() / 1000000L;
                                }
                            }
                            else {
                                y.M(this);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public boolean M(final Entity entity) {
        return BoatAura.throughWalls.M() || Wrapper.mc.player.canEntityBeSeen(entity);
    }
}
