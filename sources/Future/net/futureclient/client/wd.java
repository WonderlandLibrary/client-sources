package net.futureclient.client;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;

public class wd extends XB
{
    public wd() {
        super(new String[] { "Breed", "Rape" });
    }
    
    @Override
    public String M(final String[] array) {
        int n = 0;
        final Iterator<Entity> iterator = this.k.world.getLoadedEntityList().iterator();
    Label_0018:
        while (true) {
            Iterator<Entity> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final Entity entity;
                if (!((entity = iterator.next()) instanceof EntityAnimal)) {
                    continue Label_0018;
                }
                final EntityAnimal entityAnimal = (EntityAnimal)entity;
                if (this.M(entityAnimal)) {
                    int i = 36;
                    int n2 = 36;
                    while (i < 45) {
                        final ItemStack stack;
                        if (!((stack = this.k.player.inventoryContainer.getSlot(n2).getStack()).getItem() instanceof ItemAir) && entityAnimal.isBreedingItem(stack)) {
                            this.k.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n2 - 36));
                            final NetHandlerPlayClient connection = this.k.player.connection;
                            final EntityAnimal entityAnimal2 = entityAnimal;
                            ++n;
                            connection.sendPacket((Packet)new CPacketUseEntity((Entity)entityAnimal2, EnumHand.MAIN_HAND));
                            if (this.k.player.capabilities.isCreativeMode) {
                                break;
                            }
                            int count = stack.getCount();
                            if (--count <= 0) {
                                this.k.player.inventory.setInventorySlotContents(n2, stack);
                                break;
                            }
                            break;
                        }
                        else {
                            i = ++n2;
                        }
                    }
                    continue Label_0018;
                }
                iterator2 = iterator;
            }
            break;
        }
        this.k.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.k.player.inventory.currentItem));
        final String s = "Bred %s animal%s.";
        final Object[] array2 = { n, null };
        final int n3 = n;
        final int n4 = 1;
        array2[n4] = ((n3 == n4) ? "" : "s");
        return String.format(s, array2);
    }
    
    @Override
    public String M() {
        return null;
    }
    
    private boolean M(final EntityAnimal entityAnimal) {
        return !entityAnimal.isChild() && !entityAnimal.isInLove() && this.k.player.getDistance((Entity)entityAnimal) < (this.k.player.canEntityBeSeen((Entity)entityAnimal) ? 6 : 3);
    }
}
