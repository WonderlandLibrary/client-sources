package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import net.minecraft.client.*;
import me.darkmagician6.morbid.*;
import java.util.*;

public final class AmericaAutoarmor extends ModBase
{
    Minecraft mc;
    private final Integer[] helmetPriority;
    private final Integer[] chestPriority;
    private final Integer[] legsPriority;
    private final Integer[] bootsPriority;
    
    public AmericaAutoarmor() {
        super("AutoArmor", "L", true, ".t armor");
        this.mc = MorbidWrapper.mcObj();
        this.helmetPriority = new Integer[] { 298, 314, 302, 306, 310 };
        this.chestPriority = new Integer[] { 299, 315, 303, 307, 311 };
        this.legsPriority = new Integer[] { 300, 316, 304, 308, 312 };
        this.bootsPriority = new Integer[] { 301, 317, 305, 309, 313 };
        this.setDescription("Sceglie e ti mette l'armor migliore.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final wm playerHelmet = MorbidWrapper.getPlayer().bK.f(3);
            final wm playerChestplate = MorbidWrapper.getPlayer().bK.f(2);
            final wm playerLeggings = MorbidWrapper.getPlayer().bK.f(1);
            final wm playerBoots = MorbidWrapper.getPlayer().bK.f(0);
            if (playerHelmet == null) {
                this.wearHelmet();
            }
            else {
                this.compareItem(playerHelmet, this.helmetPriority, 3);
            }
            if (playerChestplate == null) {
                this.wearChestplate();
            }
            else {
                this.compareItem(playerChestplate, this.chestPriority, 2);
            }
            if (playerLeggings == null) {
                this.wearLeggings();
            }
            else {
                this.compareItem(playerLeggings, this.legsPriority, 1);
            }
            if (playerBoots == null) {
                this.wearBoots();
            }
            else {
                this.compareItem(playerBoots, this.bootsPriority, 0);
            }
        }
    }
    
    private void removeArmor(final int armorPiece) {
        MorbidWrapper.mcObj().b.a(0, 8 - armorPiece, 0, 1, MorbidWrapper.mcObj().g);
    }
    
    private void compareItem(final wm currentItem, final Integer[] itemPriority, final int armorPiece) {
        for (int itemSlot = 44; itemSlot >= 9; --itemSlot) {
            final wm itemStack = MorbidWrapper.mcObj().g.bL.a(itemSlot).c();
            if (itemStack != null && Arrays.asList(itemPriority).indexOf(wk.getIdFromItem(itemStack.b())) > Arrays.asList(itemPriority).indexOf(wk.getIdFromItem(currentItem.b()))) {
                if (itemSlot >= 36 && itemSlot <= 44) {
                    this.removeArmor(armorPiece);
                    MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                }
                else {
                    this.removeArmor(armorPiece);
                    MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                }
            }
        }
    }
    
    private void wearHelmet() {
        for (int itemSlot = 44; itemSlot >= 9; --itemSlot) {
            final wm itemStack = MorbidWrapper.mcObj().g.bL.a(itemSlot).c();
            if (itemStack != null) {
                if (itemSlot >= 36 && itemSlot <= 44) {
                    if (Arrays.asList(this.helmetPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                        MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                    }
                }
                else if (Arrays.asList(this.helmetPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                    MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                }
            }
        }
    }
    
    private void wearChestplate() {
        for (int itemSlot = 44; itemSlot >= 9; --itemSlot) {
            final wm itemStack = MorbidWrapper.mcObj().g.bL.a(itemSlot).c();
            if (itemStack != null) {
                if (itemSlot >= 36 && itemSlot <= 44) {
                    if (Arrays.asList(this.chestPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                        MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                    }
                }
                else if (Arrays.asList(this.chestPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                    MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                }
            }
        }
    }
    
    private void wearLeggings() {
        for (int itemSlot = 44; itemSlot >= 9; --itemSlot) {
            final wm itemStack = MorbidWrapper.mcObj().g.bL.a(itemSlot).c();
            if (itemStack != null) {
                if (itemSlot >= 36 && itemSlot <= 44) {
                    if (Arrays.asList(this.legsPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                        MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                    }
                }
                else if (Arrays.asList(this.legsPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                    MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                }
            }
        }
    }
    
    private void wearBoots() {
        for (int itemSlot = 44; itemSlot >= 9; --itemSlot) {
            final wm itemStack = MorbidWrapper.mcObj().g.bL.a(itemSlot).c();
            if (itemStack != null) {
                if (itemSlot >= 36 && itemSlot <= 44) {
                    if (Arrays.asList(this.bootsPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                        MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                    }
                }
                else if (Arrays.asList(this.bootsPriority).contains(wk.getIdFromItem(itemStack.b()))) {
                    MorbidWrapper.mcObj().b.a(0, itemSlot, 0, 1, MorbidWrapper.mcObj().g);
                }
            }
        }
    }
}
