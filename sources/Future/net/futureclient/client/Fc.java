package net.futureclient.client;

import java.util.Arrays;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import java.util.List;

public class Fc extends Ea
{
    private final List<SoundEvent> k;
    
    public Fc() {
        super("QpRnwlu]oi", new String[] { "RsnR6-Rznh", "QnRuca]oi", "Qu_{\u007fulnR6-Rznh", "\u0013<kvRnwlu]oi", "^tncYk}Mx;3ZYf{" }, true, -51200, Category.MISCELLANEOUS);
        this.k = Arrays.<SoundEvent>asList(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundEvents.ITEM_ARMOR_EQIIP_ELYTRA, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
        this.M(new n[] { new Cd(this) });
    }
    
    public static List M(final Fc fc) {
        return fc.k;
    }
}
