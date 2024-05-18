package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class CombatTracker
{
    private boolean field_94552_d;
    private int field_152775_d;
    private static final String[] I;
    private int field_152776_e;
    private int field_94555_c;
    private boolean field_94553_e;
    private final EntityLivingBase fighter;
    private String field_94551_f;
    private final List<CombatEntry> combatEntries;
    
    public EntityLivingBase getFighter() {
        return this.fighter;
    }
    
    private static void I() {
        (I = new String[0x6F ^ 0x64])["".length()] = I("?\u0010\r\u000b.!", "SqioK");
        CombatTracker.I[" ".length()] = I("\u0017\f&\"0", "aeHGC");
        CombatTracker.I["  ".length()] = I("\u001a;'\u00027", "mZSgE");
        CombatTracker.I["   ".length()] = I("121=#{6$=*6<~..;2\" (", "UWPIK");
        CombatTracker.I[0x32 ^ 0x36] = I("\u0000\u00100\u0001'J\u00134\u0019#J\u0014\"\u0006&\u0017\u0001\u007f\u001c;\u0001\u0018", "duQuO");
        CombatTracker.I[0x22 ^ 0x27] = I("\u0015\u0017;\u001b$_\u0014?\u0003 _\u0013)\u001c%\u0002\u0006", "qrZoL");
        CombatTracker.I[0xA4 ^ 0xA2] = I(".'8\u000e9d$<\u0016=d$0\u001489*w\u0013%//", "JBYzQ");
        CombatTracker.I[0xAC ^ 0xAB] = I("+\u0015-\u001b\fa\u0016)\u0003\ba\u0016%\u0001\r<\u0018", "OpLod");
        CombatTracker.I[0x24 ^ 0x2C] = I(">12\u0001\u0002t26\u0019\u0006t?:\u0019\u0006?&", "ZTSuj");
        CombatTracker.I[0x4C ^ 0x45] = I("-\u0003\b\u0016=g\u0000\f\u000e9g\u0007\n\u0001<-\u0003\u0007\u0016{", "IfibU");
        CombatTracker.I[0xAD ^ 0xA7] = I("11\u0000\u00147?7", "VTnqE");
    }
    
    public IChatComponent getDeathMessage() {
        if (this.combatEntries.size() == 0) {
            final String s = CombatTracker.I["   ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = this.fighter.getDisplayName();
            return new ChatComponentTranslation(s, array);
        }
        final CombatEntry func_94544_f = this.func_94544_f();
        final CombatEntry combatEntry = this.combatEntries.get(this.combatEntries.size() - " ".length());
        final IChatComponent damageSrcDisplayName = combatEntry.getDamageSrcDisplayName();
        final Entity entity = combatEntry.getDamageSrc().getEntity();
        IChatComponent deathMessage;
        if (func_94544_f != null && combatEntry.getDamageSrc() == DamageSource.fall) {
            final IChatComponent damageSrcDisplayName2 = func_94544_f.getDamageSrcDisplayName();
            if (func_94544_f.getDamageSrc() != DamageSource.fall && func_94544_f.getDamageSrc() != DamageSource.outOfWorld) {
                if (damageSrcDisplayName2 != null && (damageSrcDisplayName == null || !damageSrcDisplayName2.equals(damageSrcDisplayName))) {
                    final Entity entity2 = func_94544_f.getDamageSrc().getEntity();
                    ItemStack heldItem;
                    if (entity2 instanceof EntityLivingBase) {
                        heldItem = ((EntityLivingBase)entity2).getHeldItem();
                        "".length();
                        if (3 < -1) {
                            throw null;
                        }
                    }
                    else {
                        heldItem = null;
                    }
                    final ItemStack itemStack = heldItem;
                    if (itemStack != null && itemStack.hasDisplayName()) {
                        final String s2 = CombatTracker.I[0x46 ^ 0x42];
                        final Object[] array2 = new Object["   ".length()];
                        array2["".length()] = this.fighter.getDisplayName();
                        array2[" ".length()] = damageSrcDisplayName2;
                        array2["  ".length()] = itemStack.getChatComponent();
                        deathMessage = new ChatComponentTranslation(s2, array2);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        final String s3 = CombatTracker.I[0x63 ^ 0x66];
                        final Object[] array3 = new Object["  ".length()];
                        array3["".length()] = this.fighter.getDisplayName();
                        array3[" ".length()] = damageSrcDisplayName2;
                        deathMessage = new ChatComponentTranslation(s3, array3);
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                }
                else if (damageSrcDisplayName != null) {
                    ItemStack heldItem2;
                    if (entity instanceof EntityLivingBase) {
                        heldItem2 = ((EntityLivingBase)entity).getHeldItem();
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        heldItem2 = null;
                    }
                    final ItemStack itemStack2 = heldItem2;
                    if (itemStack2 != null && itemStack2.hasDisplayName()) {
                        final String s4 = CombatTracker.I[0xAD ^ 0xAB];
                        final Object[] array4 = new Object["   ".length()];
                        array4["".length()] = this.fighter.getDisplayName();
                        array4[" ".length()] = damageSrcDisplayName;
                        array4["  ".length()] = itemStack2.getChatComponent();
                        deathMessage = new ChatComponentTranslation(s4, array4);
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                    }
                    else {
                        final String s5 = CombatTracker.I[0xA5 ^ 0xA2];
                        final Object[] array5 = new Object["  ".length()];
                        array5["".length()] = this.fighter.getDisplayName();
                        array5[" ".length()] = damageSrcDisplayName;
                        deathMessage = new ChatComponentTranslation(s5, array5);
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                }
                else {
                    final String s6 = CombatTracker.I[0x16 ^ 0x1E];
                    final Object[] array6 = new Object[" ".length()];
                    array6["".length()] = this.fighter.getDisplayName();
                    deathMessage = new ChatComponentTranslation(s6, array6);
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
            }
            else {
                final String string = CombatTracker.I[0x11 ^ 0x18] + this.func_94548_b(func_94544_f);
                final Object[] array7 = new Object[" ".length()];
                array7["".length()] = this.fighter.getDisplayName();
                deathMessage = new ChatComponentTranslation(string, array7);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
        }
        else {
            deathMessage = combatEntry.getDamageSrc().getDeathMessage(this.fighter);
        }
        return deathMessage;
    }
    
    private CombatEntry func_94544_f() {
        CombatEntry combatEntry = null;
        CombatEntry combatEntry2 = null;
        final int length = "".length();
        float damageAmount = 0.0f;
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < this.combatEntries.size()) {
            final CombatEntry combatEntry3 = this.combatEntries.get(i);
            CombatEntry combatEntry4;
            if (i > 0) {
                combatEntry4 = this.combatEntries.get(i - " ".length());
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                combatEntry4 = null;
            }
            final CombatEntry combatEntry5 = combatEntry4;
            if ((combatEntry3.getDamageSrc() == DamageSource.fall || combatEntry3.getDamageSrc() == DamageSource.outOfWorld) && combatEntry3.getDamageAmount() > 0.0f && (combatEntry == null || combatEntry3.getDamageAmount() > damageAmount)) {
                if (i > 0) {
                    combatEntry = combatEntry5;
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else {
                    combatEntry = combatEntry3;
                }
                damageAmount = combatEntry3.getDamageAmount();
            }
            if (combatEntry3.func_94562_g() != null && (combatEntry2 == null || combatEntry3.func_94563_c() > length)) {
                combatEntry2 = combatEntry3;
            }
            ++i;
        }
        if (damageAmount > 5.0f && combatEntry != null) {
            return combatEntry;
        }
        if (length > (0x37 ^ 0x32) && combatEntry2 != null) {
            return combatEntry2;
        }
        return null;
    }
    
    public void trackDamage(final DamageSource damageSource, final float n, final float n2) {
        this.reset();
        this.func_94545_a();
        final CombatEntry combatEntry = new CombatEntry(damageSource, this.fighter.ticksExisted, n, n2, this.field_94551_f, this.fighter.fallDistance);
        this.combatEntries.add(combatEntry);
        this.field_94555_c = this.fighter.ticksExisted;
        this.field_94553_e = (" ".length() != 0);
        if (combatEntry.isLivingDamageSrc() && !this.field_94552_d && this.fighter.isEntityAlive()) {
            this.field_94552_d = (" ".length() != 0);
            this.field_152775_d = this.fighter.ticksExisted;
            this.field_152776_e = this.field_152775_d;
            this.fighter.sendEnterCombat();
        }
    }
    
    private void func_94542_g() {
        this.field_94551_f = null;
    }
    
    public EntityLivingBase func_94550_c() {
        EntityLivingBase entityLivingBase = null;
        EntityLivingBase entityLivingBase2 = null;
        float func_94563_c = 0.0f;
        float func_94563_c2 = 0.0f;
        final Iterator<CombatEntry> iterator = this.combatEntries.iterator();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final CombatEntry combatEntry = iterator.next();
            if (combatEntry.getDamageSrc().getEntity() instanceof EntityPlayer && (entityLivingBase2 == null || combatEntry.func_94563_c() > func_94563_c2)) {
                func_94563_c2 = combatEntry.func_94563_c();
                entityLivingBase2 = (EntityPlayer)combatEntry.getDamageSrc().getEntity();
            }
            if (combatEntry.getDamageSrc().getEntity() instanceof EntityLivingBase && (entityLivingBase == null || combatEntry.func_94563_c() > func_94563_c)) {
                func_94563_c = combatEntry.func_94563_c();
                entityLivingBase = (EntityLivingBase)combatEntry.getDamageSrc().getEntity();
            }
        }
        if (entityLivingBase2 != null && func_94563_c2 >= func_94563_c / 3.0f) {
            return entityLivingBase2;
        }
        return entityLivingBase;
    }
    
    public CombatTracker(final EntityLivingBase fighter) {
        this.combatEntries = (List<CombatEntry>)Lists.newArrayList();
        this.fighter = fighter;
    }
    
    public void reset() {
        int n;
        if (this.field_94552_d) {
            n = 120 + 12 + 56 + 112;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            n = (0x36 ^ 0x52);
        }
        final int n2 = n;
        if (this.field_94553_e && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.field_94555_c > n2)) {
            final boolean field_94552_d = this.field_94552_d;
            this.field_94553_e = ("".length() != 0);
            this.field_94552_d = ("".length() != 0);
            this.field_152776_e = this.fighter.ticksExisted;
            if (field_94552_d) {
                this.fighter.sendEndCombat();
            }
            this.combatEntries.clear();
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public int func_180134_f() {
        int n;
        if (this.field_94552_d) {
            n = this.fighter.ticksExisted - this.field_152775_d;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n = this.field_152776_e - this.field_152775_d;
        }
        return n;
    }
    
    private String func_94548_b(final CombatEntry combatEntry) {
        String func_94562_g;
        if (combatEntry.func_94562_g() == null) {
            func_94562_g = CombatTracker.I[0x10 ^ 0x1A];
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            func_94562_g = combatEntry.func_94562_g();
        }
        return func_94562_g;
    }
    
    public void func_94545_a() {
        this.func_94542_g();
        if (this.fighter.isOnLadder()) {
            final Block block = this.fighter.worldObj.getBlockState(new BlockPos(this.fighter.posX, this.fighter.getEntityBoundingBox().minY, this.fighter.posZ)).getBlock();
            if (block == Blocks.ladder) {
                this.field_94551_f = CombatTracker.I["".length()];
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else if (block == Blocks.vine) {
                this.field_94551_f = CombatTracker.I[" ".length()];
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        else if (this.fighter.isInWater()) {
            this.field_94551_f = CombatTracker.I["  ".length()];
        }
    }
}
