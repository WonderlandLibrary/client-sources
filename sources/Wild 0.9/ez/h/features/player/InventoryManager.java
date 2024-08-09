package ez.h.features.player;

import java.util.function.*;
import java.util.*;
import ez.h.*;
import ez.h.features.combat.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class InventoryManager extends Feature
{
    final OptionBoolean moveArrows;
    OptionBoolean weapon;
    OptionBoolean pickaxe;
    final OptionSlider delay;
    OptionBoolean axe;
    OptionBoolean shovel;
    final OptionBoolean dropFood;
    final String[] serverItems;
    final Counter timer;
    final OptionBoolean inventoryPackets;
    final OptionBoolean dropArchery;
    OptionBoolean bow;
    final OptionSlider slotWeapon;
    final String[] shitItems;
    OptionBoolean throwItems;
    OptionBoolean blocks;
    final OptionSlider slotPick;
    final OptionSlider slotAxe;
    boolean isInvOpen;
    final OptionSlider slotShovel;
    final OptionSlider slotBlock;
    final OptionSlider slotBow;
    
    void getBestAxe() {
        if (!this.axe.enabled) {
            return;
        }
        for (int i = 9; i < (0x1E ^ 0x33); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (this.isBestAxe(d) && !this.isBestWeapon(d)) {
                    final int desiredSlot = this.getDesiredSlot(ItemType.AXE);
                    if (i == desiredSlot) {
                        return;
                    }
                    final agr a2 = InventoryManager.mc.h.bx.a(desiredSlot);
                    if (!a2.e() || !this.isBestAxe(a2.d())) {
                        this.swap(i, desiredSlot - (0xE0 ^ 0xC4));
                        this.timer.reset();
                    }
                }
            }
        }
    }
    
    void fakeClose() {
        if (this.isInvOpen) {
            if (this.inventoryPackets.isEnabled()) {
                InventoryManager.mc.h.d.a((ht)new lg(InventoryManager.mc.h.bx.d));
            }
            this.isInvOpen = false;
        }
    }
    
    float getPowerLevel(final aip aip) {
        float n = 0.0f;
        if (aip.c() instanceof ahg) {
            n = n + alm.a(alo.w, aip) + alm.a(alo.y, aip) * 0.5f + alm.a(alo.u, aip) * 0.1f;
        }
        return n;
    }
    
    public static boolean isValidBlock(final aow aow, final boolean b) {
        return !(aow instanceof avd) && !(aow instanceof atw) && !(aow instanceof aoq) && !(aow instanceof aor) && !(aow instanceof aru) && (!b || (!(aow instanceof arf) && !(aow instanceof aud) && !(aow instanceof arq) && !(aow instanceof aub) && !(aow instanceof auv) && !(aow instanceof auz) && !(aow instanceof ape) && !(aow instanceof aqm) && aow != aox.bj && aow != aox.bi)) && aow.m();
    }
    
    void getBestPickaxe() {
        if (!this.pickaxe.enabled) {
            return;
        }
        for (int i = 9; i < (0x18 ^ 0x35); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (this.isBestPickaxe(d) && !this.isBestWeapon(d)) {
                    final int desiredSlot = this.getDesiredSlot(ItemType.PICKAXE);
                    if (i == desiredSlot) {
                        return;
                    }
                    final agr a2 = InventoryManager.mc.h.bx.a(desiredSlot);
                    if (!a2.e() || !this.isBestPickaxe(a2.d())) {
                        this.swap(i, desiredSlot - (0x89 ^ 0xAD));
                    }
                }
            }
        }
    }
    
    int getDesiredSlot(final ItemType itemType) {
        int n = 0x11 ^ 0x35;
        switch (itemType) {
            case WEAPON: {
                n = (int)this.slotWeapon.getNum();
                break;
            }
            case PICKAXE: {
                n = (int)this.slotPick.getNum();
                break;
            }
            case AXE: {
                n = (int)this.slotAxe.getNum();
                break;
            }
            case SHOVEL: {
                n = (int)this.slotShovel.getNum();
                break;
            }
            case BLOCK: {
                n = (int)this.slotBlock.getNum();
                break;
            }
            case BOW: {
                n = (int)this.slotBow.getNum();
                break;
            }
        }
        return n + (0x30 ^ 0x13);
    }
    
    boolean isBestBow(final aip aip) {
        if (!(aip.c() instanceof ahg)) {
            return false;
        }
        final float powerLevel = this.getPowerLevel(aip);
        for (int i = 9; i < (0x8C ^ 0xA1); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (this.getPowerLevel(d) > powerLevel && d.c() instanceof ahg && !this.isBestWeapon(aip)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean isBestAxe(final aip aip) {
        if (!(aip.c() instanceof agy)) {
            return false;
        }
        final float toolScore = this.getToolScore(aip);
        for (int i = 9; i < (0x55 ^ 0x78); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (this.getToolScore(d) > toolScore && d.c() instanceof agy && !this.isBestWeapon(aip)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean shouldDrop(final aip aip, final int n) {
        final String lowerCase = aip.r().toLowerCase();
        final ain c = aip.c();
        final String a = c.a();
        if (Arrays.stream(this.serverItems).anyMatch(lowerCase::contains)) {
            return false;
        }
        if (c instanceof ahb) {
            return !isValidBlock(((ahb)c).d(), true);
        }
        final int desiredSlot = this.getDesiredSlot(ItemType.WEAPON);
        final int desiredSlot2 = this.getDesiredSlot(ItemType.PICKAXE);
        final int desiredSlot3 = this.getDesiredSlot(ItemType.AXE);
        final int desiredSlot4 = this.getDesiredSlot(ItemType.SHOVEL);
        if ((n != desiredSlot || !this.isBestWeapon(InventoryManager.mc.h.bx.a(desiredSlot).d())) && (n != desiredSlot2 || !this.isBestPickaxe(InventoryManager.mc.h.bx.a(desiredSlot2).d())) && (n != desiredSlot3 || !this.isBestAxe(InventoryManager.mc.h.bx.a(desiredSlot3).d())) && (n != desiredSlot4 || !this.isBestShovel(InventoryManager.mc.h.bx.a(desiredSlot4).d()))) {
            if (c instanceof agv) {
                for (int i = 1; i < 5; ++i) {
                    if (!InventoryManager.mc.h.bx.a(4 + i).e() || !this.isBestArmor(InventoryManager.mc.h.bx.a(4 + i).d(), i)) {
                        if (this.isBestArmor(aip, i)) {
                            return false;
                        }
                    }
                }
            }
            return c == air.R || c == air.bU || (c instanceof aij && this.dropFood.isEnabled() && !(c instanceof aik)) || (c instanceof ajd && this.isShitPotion(aip)) || c instanceof ajy || c instanceof ahq || c instanceof aim || c instanceof agv || (this.dropArchery.isEnabled() && (c instanceof ahg || c == air.h)) || c instanceof ahf || Arrays.stream(this.shitItems).anyMatch(a::contains);
        }
        return false;
    }
    
    boolean isShitPotion(final aip aip) {
        if (aip != null && aip.c() instanceof ajd) {
            final ajd ajd = (ajd)aip.c();
            if (aki.a(aip) == null) {
                return true;
            }
            final Iterator<va> iterator = aki.a(aip).iterator();
            while (iterator.hasNext()) {
                if (iterator.next().a().e()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    boolean isBestWeapon(final aip aip) {
        final float damageScore = getDamageScore(aip);
        for (int i = 9; i < (0x9C ^ 0xB1); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (getDamageScore(d) > damageScore && d.c() instanceof ajy) {
                    return false;
                }
            }
        }
        return aip.c() instanceof ajy;
    }
    
    public static float getDamageScore(final aip aip) {
        if (aip == null || aip.c() == null) {
            return 0.0f;
        }
        float n = 0.0f;
        final ain c = aip.c();
        if (c instanceof ajy) {
            n += ((ajy)c).g();
        }
        else if (c instanceof ahq) {
            n += c.l();
        }
        return n + (alm.a(alo.l, aip) * 1.25f + alm.a(alo.p, aip) * 0.1f);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (Main.getFeatureByName("AutoTotem").isEnabled() && AutoTotem.swapping) {
            return;
        }
        if (!InventoryManager.mc.h.cG() && (InventoryManager.mc.m == null || InventoryManager.mc.m instanceof bkn || InventoryManager.mc.m instanceof bmx || InventoryManager.mc.m instanceof blg)) {
            final long n = (long)this.delay.getNum();
            if (this.timer.hasReached((float)n)) {
                final agr a = InventoryManager.mc.h.bx.a(this.getDesiredSlot(ItemType.WEAPON));
                if (!a.e() || !this.isBestWeapon(a.d())) {
                    this.getBestWeapon();
                }
            }
            if (this.timer.hasReached((float)n)) {
                this.getBestPickaxe();
            }
            if (this.timer.hasReached((float)n)) {
                this.getBestAxe();
            }
            if (this.timer.hasReached((float)n)) {
                this.getBestShovel();
            }
            if (this.timer.hasReached((float)n) && this.throwItems.enabled) {
                for (int i = 9; i < (0xA7 ^ 0x8A); ++i) {
                    if (this.stop()) {
                        return;
                    }
                    final aip d = InventoryManager.mc.h.bx.a(i).d();
                    if (d != null && this.shouldDrop(d, i)) {
                        InventoryUtils.drop(i);
                        this.timer.reset();
                        break;
                    }
                }
            }
            if (this.timer.hasReached((float)n)) {
                this.swapBlocks();
            }
            if (this.timer.hasReached((float)n)) {
                this.getBestBow();
            }
            if (this.timer.hasReached((float)n)) {
                this.moveArrows();
            }
        }
    }
    
    boolean isBestPickaxe(final aip aip) {
        if (!(aip.c() instanceof ajb)) {
            return false;
        }
        final float toolScore = this.getToolScore(aip);
        for (int i = 9; i < (0x2E ^ 0x3); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (d.c() instanceof ajb && this.getToolScore(d) > toolScore) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public InventoryManager() {
        super("InventoryManager", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0441\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0442 \u0438 \u0432\u044b\u0431\u0440\u0430\u0441\u044b\u0432\u0430\u0435\u0442 \u043d\u0435 \u043d\u0443\u0436\u043d\u044b\u0435 \u0432\u0435\u0449\u0438 \u0438\u0437 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044f.", Category.PLAYER);
        this.delay = new OptionSlider(this, "Delay", 120.0f, 0.0f, 500.0f, OptionSlider.SliderType.MS);
        this.inventoryPackets = new OptionBoolean(this, "Spoof Window", true);
        this.dropArchery = new OptionBoolean(this, "Drop Archery", false);
        this.moveArrows = new OptionBoolean(this, "Move Arrows", true);
        this.dropFood = new OptionBoolean(this, "Drop Food", false);
        this.slotWeapon = new OptionSlider(this, "Weapon Slot", 1.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        this.slotPick = new OptionSlider(this, "Pickaxe Slot", 2.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        this.slotAxe = new OptionSlider(this, "Axe Slot", 3.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        this.slotShovel = new OptionSlider(this, "Shovel Slot", 4.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        this.slotBow = new OptionSlider(this, "Bow Slot", 5.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        this.slotBlock = new OptionSlider(this, "Block Slot", 6.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        final String[] shitItems = new String[0xA8 ^ 0xB2];
        shitItems[0] = "tnt";
        shitItems[1] = "stick";
        shitItems[2] = "egg";
        shitItems[3] = "string";
        shitItems[4] = "cake";
        shitItems[5] = "mushroom";
        shitItems[6] = "flint";
        shitItems[7] = "compass";
        shitItems[8] = "dyePowder";
        shitItems[9] = "feather";
        shitItems[0xB1 ^ 0xBB] = "bucket";
        shitItems[0x3C ^ 0x37] = "chest";
        shitItems[0x6B ^ 0x67] = "snow";
        shitItems[0x96 ^ 0x9B] = "fish";
        shitItems[0xCD ^ 0xC3] = "enchant";
        shitItems[0xA4 ^ 0xAB] = "exp";
        shitItems[0x1C ^ 0xC] = "shears";
        shitItems[0x27 ^ 0x36] = "anvil";
        shitItems[0x5B ^ 0x49] = "torch";
        shitItems[0x5A ^ 0x49] = "seeds";
        shitItems[0x3A ^ 0x2E] = "leather";
        shitItems[0x37 ^ 0x22] = "reeds";
        shitItems[0x1C ^ 0xA] = "skull";
        shitItems[0xF ^ 0x18] = "record";
        shitItems[0x3E ^ 0x26] = "snowball";
        shitItems[0xAE ^ 0xB7] = "piston";
        this.shitItems = shitItems;
        final String[] serverItems = new String[0x7B ^ 0x67];
        serverItems[0] = "selector";
        serverItems[1] = "tracking compass";
        serverItems[2] = "(right click)";
        serverItems[3] = "tienda ";
        serverItems[4] = "perfil";
        serverItems[5] = "salir";
        serverItems[6] = "shop";
        serverItems[7] = "collectibles";
        serverItems[8] = "game";
        serverItems[9] = "profil";
        serverItems[0x94 ^ 0x9E] = "lobby";
        serverItems[0x37 ^ 0x3C] = "show all";
        serverItems[0x16 ^ 0x1A] = "hub";
        serverItems[0x90 ^ 0x9D] = "friends only";
        serverItems[0x1F ^ 0x11] = "cofre";
        serverItems[0x1C ^ 0x13] = "(click";
        serverItems[0xD4 ^ 0xC4] = "teleport";
        serverItems[0x5B ^ 0x4A] = "play";
        serverItems[0xBF ^ 0xAD] = "exit";
        serverItems[0x5A ^ 0x49] = "hide all";
        serverItems[0x15 ^ 0x1] = "jeux";
        serverItems[0x30 ^ 0x25] = "gadget";
        serverItems[0x39 ^ 0x2F] = " (activ";
        serverItems[0x61 ^ 0x76] = "emote";
        serverItems[0x53 ^ 0x4B] = "amis";
        serverItems[0x72 ^ 0x6B] = "bountique";
        serverItems[0x2B ^ 0x31] = "choisir";
        serverItems[0xBD ^ 0xA6] = "choose ";
        this.serverItems = serverItems;
        this.timer = new Counter();
        this.weapon = new OptionBoolean(this, "Weapon", true);
        this.pickaxe = new OptionBoolean(this, "Pickaxe", true);
        this.axe = new OptionBoolean(this, "Axe", true);
        this.shovel = new OptionBoolean(this, "Shovel", true);
        this.bow = new OptionBoolean(this, "Bow", true);
        this.blocks = new OptionBoolean(this, "Blocks", true);
        this.throwItems = new OptionBoolean(this, "Throw", true);
        final Option[] array = new Option[0x9D ^ 0x8F];
        array[0] = this.delay;
        array[1] = this.throwItems;
        array[2] = this.inventoryPackets;
        array[3] = this.dropArchery;
        array[4] = this.moveArrows;
        array[5] = this.dropFood;
        array[6] = this.weapon;
        array[7] = this.slotWeapon;
        array[8] = this.pickaxe;
        array[9] = this.slotPick;
        array[0x98 ^ 0x92] = this.axe;
        array[0x25 ^ 0x2E] = this.slotAxe;
        array[0x88 ^ 0x84] = this.shovel;
        array[0x1A ^ 0x17] = this.slotShovel;
        array[0x5 ^ 0xB] = this.bow;
        array[0xB2 ^ 0xBD] = this.slotBow;
        array[0x8C ^ 0x9C] = this.blocks;
        array[0x5E ^ 0x4F] = this.slotBlock;
        this.addOptions(array);
    }
    
    void getBestShovel() {
        if (!this.shovel.enabled) {
            return;
        }
        for (int i = 9; i < (0xE8 ^ 0xC5); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (this.isBestShovel(d) && !this.isBestWeapon(d)) {
                    final int desiredSlot = this.getDesiredSlot(ItemType.SHOVEL);
                    if (i == desiredSlot) {
                        return;
                    }
                    final agr a2 = InventoryManager.mc.h.bx.a(desiredSlot);
                    if (!a2.e() || !this.isBestShovel(a2.d())) {
                        this.swap(i, desiredSlot - (0xA4 ^ 0x80));
                        this.timer.reset();
                    }
                }
            }
        }
    }
    
    void moveArrows() {
        if (this.dropArchery.isEnabled() || !this.moveArrows.isEnabled()) {
            return;
        }
        for (int i = 0xE4 ^ 0xC0; i < (0x45 ^ 0x68); ++i) {
            final aip d = InventoryManager.mc.h.bx.a(i).d();
            if (d != null && d.c() == air.h) {
                for (int j = 0; j < (0x35 ^ 0x11); ++j) {
                    if (InventoryManager.mc.h.bx.a(j).d() == null) {
                        this.fakeOpen();
                        InventoryUtils.click(i, 0, true);
                        this.fakeClose();
                        this.timer.reset();
                        break;
                    }
                }
            }
        }
    }
    
    void getBestWeapon() {
        if (!this.weapon.enabled) {
            return;
        }
        for (int i = 9; i < (0x2A ^ 0x7); ++i) {
            if (InventoryManager.mc.h.bx.a(i).e()) {
                final aip d = InventoryManager.mc.h.bx.a(i).d();
                if (this.isBestWeapon(d) && getDamageScore(d) > 0.0f && d.c() instanceof ajy) {
                    this.swap(i, this.getDesiredSlot(ItemType.WEAPON) - (0xBE ^ 0x9A));
                    break;
                }
            }
        }
    }
    
    boolean isBestShovel(final aip aip) {
        if (!(aip.c() instanceof ajn)) {
            return false;
        }
        final float toolScore = this.getToolScore(aip);
        for (int i = 9; i < (0xA6 ^ 0x8B); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (d.c() instanceof ajn && this.getToolScore(d) > toolScore) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean stop() {
        return !(InventoryManager.mc.m instanceof bmx);
    }
    
    void fakeOpen() {
        if (!this.isInvOpen) {
            this.timer.reset();
            if (this.inventoryPackets.isEnabled()) {
                InventoryManager.mc.h.d.a((ht)new lq((vg)InventoryManager.mc.h, lq.a.h));
            }
            this.isInvOpen = true;
        }
    }
    
    void swapBlocks() {
        if (!this.blocks.enabled) {
            return;
        }
        final int mostBlocks = this.getMostBlocks();
        final int desiredSlot = this.getDesiredSlot(ItemType.BLOCK);
        if (mostBlocks != -1 && mostBlocks != desiredSlot) {
            final aip d = InventoryManager.mc.h.bx.a(desiredSlot).d();
            if (d == null || !(d.c() instanceof ahb) || d.c < InventoryManager.mc.h.bx.a(mostBlocks).d().c || !Arrays.stream(this.serverItems).noneMatch(d.r().toLowerCase()::contains)) {
                this.swap(mostBlocks, desiredSlot - (0x47 ^ 0x63));
            }
        }
    }
    
    void swap(final int n, final int n2) {
        this.fakeOpen();
        InventoryUtils.swap(n, n2);
        this.fakeClose();
        this.timer.reset();
    }
    
    float getToolScore(final aip aip) {
        float n = 0.0f;
        final ain c = aip.c();
        if (c instanceof ahq) {
            final ahq ahq = (ahq)c;
            final String lowerCase = c.a().toLowerCase();
            float n2;
            if (c instanceof ajb) {
                n2 = ahq.a(aip, aox.b.t()) - (lowerCase.contains("gold") ? 5 : 0);
            }
            else if (c instanceof ajn) {
                n2 = ahq.a(aip, aox.d.t()) - (lowerCase.contains("gold") ? 5 : 0);
            }
            else {
                if (!(c instanceof agy)) {
                    return 1.0f;
                }
                n2 = ahq.a(aip, aox.r.t()) - (lowerCase.contains("gold") ? 5 : 0);
            }
            n = n2 + alm.a(alo.s, aip) * 0.0075f + alm.a(alo.u, aip) / 100.0f;
        }
        return n;
    }
    
    void getBestBow() {
        if (!this.bow.enabled) {
            return;
        }
        for (int i = 9; i < (0xB4 ^ 0x99); ++i) {
            final agr a = InventoryManager.mc.h.bx.a(i);
            if (a.e()) {
                final aip d = a.d();
                if (!Arrays.stream(this.serverItems).anyMatch(d.r().toLowerCase()::contains)) {
                    if (d.c() instanceof ahg) {
                        if (this.isBestBow(d) && !this.isBestWeapon(d)) {
                            final int desiredSlot = this.getDesiredSlot(ItemType.BOW);
                            if (i == desiredSlot) {
                                return;
                            }
                            final agr a2 = InventoryManager.mc.h.bx.a(desiredSlot);
                            if (!a2.e() || !this.isBestBow(a2.d())) {
                                this.swap(i, desiredSlot - (0x2 ^ 0x26));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean isBestArmor(final aip aip, final int n) {
        String s = "";
        switch (n) {
            case 1: {
                s = "helmet";
                break;
            }
            case 2: {
                s = "chestplate";
                break;
            }
            case 3: {
                s = "leggings";
                break;
            }
            case 4: {
                s = "boots";
                break;
            }
        }
        if (aip.a().contains(s)) {
            final float protScore = getProtScore(aip);
            for (int i = 5; i < (0x55 ^ 0x78); ++i) {
                final agr a = InventoryManager.mc.h.bx.a(i);
                if (a.e()) {
                    final aip d = a.d();
                    if (d.a().contains(s) && getProtScore(d) > protScore) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void updateElements() {
        this.slotWeapon.display = this.weapon.enabled;
        this.slotPick.display = this.pickaxe.enabled;
        this.slotAxe.display = this.axe.enabled;
        this.slotShovel.display = this.shovel.enabled;
        this.slotBow.display = this.bow.enabled;
        this.slotBlock.display = this.blocks.enabled;
        super.updateElements();
    }
    
    public static float getProtScore(final aip aip) {
        float n = 0.0f;
        if (aip.c() instanceof agv) {
            final agv agv = (agv)aip.c();
            n = n + (agv.d + ((0x55 ^ 0x31) - agv.d) * alm.a(alo.a, aip) * 0.0075f) + alm.a(alo.d, aip) / 100.0f + alm.a(alo.b, aip) / 100.0f + alm.a(alo.h, aip) / 100.0f + alm.a(alo.u, aip) / 25.0f + alm.a(alo.c, aip) / 100.0f;
        }
        return n;
    }
    
    int getMostBlocks() {
        int c = 0;
        int n = -1;
        for (int i = 9; i < (0x41 ^ 0x6C); ++i) {
            final aip d = InventoryManager.mc.h.bx.a(i).d();
            if (d != null && d.c() instanceof ahb && d.c > c && Arrays.stream(this.serverItems).noneMatch(d.r().toLowerCase()::contains)) {
                c = d.c;
                n = i;
            }
        }
        return n;
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (this.isInvOpen) {
            final ht<?> packet = eventPacketSend.getPacket();
            if ((packet instanceof lq && ((lq)packet).b() == lq.a.h) || packet instanceof lg) {
                eventPacketSend.setCancelled(true);
            }
            else if (packet instanceof li) {
                this.fakeClose();
            }
        }
    }
    
    enum ItemType
    {
        SHOVEL, 
        WEAPON, 
        BLOCK, 
        PICKAXE, 
        BOW, 
        AXE;
    }
}
