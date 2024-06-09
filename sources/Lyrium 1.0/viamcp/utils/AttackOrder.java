// 
// Decompiled by Procyon v0.5.36
// 

package viamcp.utils;

import viamcp.protocols.ProtocolCollection;
import viamcp.ViaMCP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.client.Minecraft;

public class AttackOrder
{
    private static final Minecraft mc;
    private static final int VER_1_8_ID = 47;
    
    public static void sendConditionalSwing(final MovingObjectPosition mop) {
        if (mop != null && mop.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
            AttackOrder.mc.thePlayer.swingItem();
        }
    }
    
    public static void sendFixedAttack(final EntityPlayer entityIn, final Entity target) {
        if (ViaMCP.getInstance().getVersion() <= ProtocolCollection.getProtocolById(47).getVersion()) {
            send1_8Attack(entityIn, target);
        }
        else {
            send1_9Attack(entityIn, target);
        }
    }
    
    private static void send1_8Attack(final EntityPlayer entityIn, final Entity target) {
        AttackOrder.mc.thePlayer.swingItem();
        AttackOrder.mc.playerController.attackEntity(entityIn, target);
    }
    
    private static void send1_9Attack(final EntityPlayer entityIn, final Entity target) {
        AttackOrder.mc.playerController.attackEntity(entityIn, target);
        AttackOrder.mc.thePlayer.swingItem();
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
