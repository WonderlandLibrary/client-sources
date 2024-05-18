package net.minecraft.entity;

import net.minecraft.nbt.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.ai.attributes.*;

public class SharedMonsterAttributes
{
    public static final IAttribute maxHealth;
    private static final String[] I;
    public static final IAttribute knockbackResistance;
    public static final IAttribute attackDamage;
    public static final IAttribute followRange;
    public static final IAttribute movementSpeed;
    private static final Logger logger;
    
    private static NBTTagCompound writeAttributeInstanceToNBT(final IAttributeInstance attributeInstance) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString(SharedMonsterAttributes.I[0x72 ^ 0x7B], attributeInstance.getAttribute().getAttributeUnlocalizedName());
        nbtTagCompound.setDouble(SharedMonsterAttributes.I[0x88 ^ 0x82], attributeInstance.getBaseValue());
        final Collection<AttributeModifier> func_111122_c = attributeInstance.func_111122_c();
        if (func_111122_c != null && !func_111122_c.isEmpty()) {
            final NBTTagList list = new NBTTagList();
            final Iterator<AttributeModifier> iterator = func_111122_c.iterator();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final AttributeModifier attributeModifier = iterator.next();
                if (attributeModifier.isSaved()) {
                    list.appendTag(writeAttributeModifierToNBT(attributeModifier));
                }
            }
            nbtTagCompound.setTag(SharedMonsterAttributes.I[0x30 ^ 0x3B], list);
        }
        return nbtTagCompound;
    }
    
    public static AttributeModifier readAttributeModifierFromNBT(final NBTTagCompound nbtTagCompound) {
        final UUID uuid = new UUID(nbtTagCompound.getLong(SharedMonsterAttributes.I[0x52 ^ 0x4A]), nbtTagCompound.getLong(SharedMonsterAttributes.I[0x97 ^ 0x8E]));
        try {
            return new AttributeModifier(uuid, nbtTagCompound.getString(SharedMonsterAttributes.I[0x6A ^ 0x70]), nbtTagCompound.getDouble(SharedMonsterAttributes.I[0x89 ^ 0x92]), nbtTagCompound.getInteger(SharedMonsterAttributes.I[0xB2 ^ 0xAE]));
        }
        catch (Exception ex) {
            SharedMonsterAttributes.logger.warn(SharedMonsterAttributes.I[0x96 ^ 0x8B] + ex.getMessage());
            return null;
        }
    }
    
    private static NBTTagCompound writeAttributeModifierToNBT(final AttributeModifier attributeModifier) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString(SharedMonsterAttributes.I[0x70 ^ 0x7C], attributeModifier.getName());
        nbtTagCompound.setDouble(SharedMonsterAttributes.I[0x37 ^ 0x3A], attributeModifier.getAmount());
        nbtTagCompound.setInteger(SharedMonsterAttributes.I[0x69 ^ 0x67], attributeModifier.getOperation());
        nbtTagCompound.setLong(SharedMonsterAttributes.I[0x9 ^ 0x6], attributeModifier.getID().getMostSignificantBits());
        nbtTagCompound.setLong(SharedMonsterAttributes.I[0xAB ^ 0xBB], attributeModifier.getID().getLeastSignificantBits());
        return nbtTagCompound;
    }
    
    private static void I() {
        (I = new String[0x7D ^ 0x63])["".length()] = I("\f\u0012+'\u0002\u0002\u0014k/\u0011\u0013? #\u001c\u001f\u001f", "kwEBp");
        SharedMonsterAttributes.I[" ".length()] = I("91\u0012e\f\u00111\u00061,", "tPjED");
        SharedMonsterAttributes.I["  ".length()] = I("\u0003-,4'\r+l7:\b$-&\u0007\u0005&%4", "dHBQU");
        SharedMonsterAttributes.I["   ".length()] = I("\"\u001e<\u001a\u0019\u0013Q\u0002\u0017\u0018\u0003\u0014", "dqPvv");
        SharedMonsterAttributes.I[0x62 ^ 0x66] = I("\"=&\t:,;f\u0007&*;#\u000e)&3\u001a\t;,+<\r&&=", "EXHlH");
        SharedMonsterAttributes.I[0x8D ^ 0x88] = I("8\u001c\t\u0005\u001f\u0011\u0013\u0005\rT!\u0017\u0015\u000f\u0007\u0007\u0013\b\u0005\u0011", "srfft");
        SharedMonsterAttributes.I[0x2F ^ 0x29] = I("*+:\u000e\u0017$-z\u0006\n;+9\u000e\u000b9\u001d$\u000e\u0000)", "MNTke");
        SharedMonsterAttributes.I[0x2E ^ 0x29] = I("&>\".?\u000e? k\u0001\u001b41/", "kQTKR");
        SharedMonsterAttributes.I[0xBC ^ 0xB4] = I("#\u0003?03-\u0005\u007f450\u00072>\u0005%\u000b02$", "DfQUA");
        SharedMonsterAttributes.I[0x98 ^ 0x91] = I("\u000b\f9\u0014", "EmTqx");
        SharedMonsterAttributes.I[0xAC ^ 0xA6] = I(")\r)\f", "klZiI");
        SharedMonsterAttributes.I[0x79 ^ 0x72] = I("4#\u000b\u00040\u0010)\u001d\u001e", "yLomV");
        SharedMonsterAttributes.I[0x93 ^ 0x9F] = I("\u0017$\u000e?", "YEcZt");
        SharedMonsterAttributes.I[0x10 ^ 0x1D] = I("*\u0017\u001b\u001b\"\u001f", "kztnL");
        SharedMonsterAttributes.I[0x2A ^ 0x24] = I(";4\f#\u0012\u0000-\u0006?", "tDiQs");
        SharedMonsterAttributes.I[0x57 ^ 0x58] = I("\u001c\f\u0019\n$&*$", "IYPNi");
        SharedMonsterAttributes.I[0xB1 ^ 0xA1] = I("\u001b\u001c8\b\u0005+(\u00028", "NIqLI");
        SharedMonsterAttributes.I[0x26 ^ 0x37] = I("%\"\u0004-", "kCiHO");
        SharedMonsterAttributes.I[0xB1 ^ 0xA3] = I("+\f\u0001'\u0003\u000b\u0005\bh\u0004\f\u0000\u0001'\u0006\fK\u000e<\u0005\u0010\u0002\r=\u0005\u0007KH", "bkoHq");
        SharedMonsterAttributes.I[0x8 ^ 0x1B] = I("\u00070\u00175", "IQzPo");
        SharedMonsterAttributes.I[0x67 ^ 0x73] = I("Q", "vZsBa");
        SharedMonsterAttributes.I[0x33 ^ 0x26] = I("\b)\u00126", "JHaSw");
        SharedMonsterAttributes.I[0x6 ^ 0x10] = I("%<\u0010\u001e1\u00016\u0006\u0004", "hStwW");
        SharedMonsterAttributes.I[0x9E ^ 0x89] = I("&\u0007\u0002=*\u0002\r\u0014'", "khfTL");
        SharedMonsterAttributes.I[0xB9 ^ 0xA1] = I("1\u0019\n\u0010\b\u000b?7", "dLCTE");
        SharedMonsterAttributes.I[0x4B ^ 0x52] = I("\u0004\u0014\u00192\u001f4 #\u0002", "QAPvS");
        SharedMonsterAttributes.I[0x54 ^ 0x4E] = I(",(\u000b\u0011", "bIftr");
        SharedMonsterAttributes.I[0x93 ^ 0x88] = I("\r4?\u0006\"8", "LYPsL");
        SharedMonsterAttributes.I[0x30 ^ 0x2C] = I("#\"#75\u0018;)+", "lRFET");
        SharedMonsterAttributes.I[0xF ^ 0x12] = I("8\u0018 \u0012\u001c\bV5\u001fP\u000e\u0004$\u0011\u0004\bV \u0004\u0004\u001f\u001f#\u0005\u0004\bLa", "mvApp");
    }
    
    private static void applyModifiersToAttributeInstance(final IAttributeInstance attributeInstance, final NBTTagCompound nbtTagCompound) {
        attributeInstance.setBaseValue(nbtTagCompound.getDouble(SharedMonsterAttributes.I[0xD6 ^ 0xC3]));
        if (nbtTagCompound.hasKey(SharedMonsterAttributes.I[0x4B ^ 0x5D], 0x29 ^ 0x20)) {
            final NBTTagList tagList = nbtTagCompound.getTagList(SharedMonsterAttributes.I[0x39 ^ 0x2E], 0x3A ^ 0x30);
            int i = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (i < tagList.tagCount()) {
                final AttributeModifier attributeModifierFromNBT = readAttributeModifierFromNBT(tagList.getCompoundTagAt(i));
                if (attributeModifierFromNBT != null) {
                    final AttributeModifier modifier = attributeInstance.getModifier(attributeModifierFromNBT.getID());
                    if (modifier != null) {
                        attributeInstance.removeModifier(modifier);
                    }
                    attributeInstance.applyModifier(attributeModifierFromNBT);
                }
                ++i;
            }
        }
    }
    
    public static void func_151475_a(final BaseAttributeMap baseAttributeMap, final NBTTagList list) {
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
            final IAttributeInstance attributeInstanceByName = baseAttributeMap.getAttributeInstanceByName(compoundTag.getString(SharedMonsterAttributes.I[0xD7 ^ 0xC6]));
            if (attributeInstanceByName != null) {
                applyModifiersToAttributeInstance(attributeInstanceByName, compoundTag);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                SharedMonsterAttributes.logger.warn(SharedMonsterAttributes.I[0x83 ^ 0x91] + compoundTag.getString(SharedMonsterAttributes.I[0x32 ^ 0x21]) + SharedMonsterAttributes.I[0x82 ^ 0x96]);
            }
            ++i;
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static NBTTagList writeBaseAttributeMapToNBT(final BaseAttributeMap baseAttributeMap) {
        final NBTTagList list = new NBTTagList();
        final Iterator<IAttributeInstance> iterator = baseAttributeMap.getAllAttributes().iterator();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.appendTag(writeAttributeInstanceToNBT(iterator.next()));
        }
        return list;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        maxHealth = new RangedAttribute(null, SharedMonsterAttributes.I["".length()], 20.0, 0.0, 1024.0).setDescription(SharedMonsterAttributes.I[" ".length()]).setShouldWatch(" ".length() != 0);
        followRange = new RangedAttribute(null, SharedMonsterAttributes.I["  ".length()], 32.0, 0.0, 2048.0).setDescription(SharedMonsterAttributes.I["   ".length()]);
        knockbackResistance = new RangedAttribute(null, SharedMonsterAttributes.I[0x35 ^ 0x31], 0.0, 0.0, 1.0).setDescription(SharedMonsterAttributes.I[0x37 ^ 0x32]);
        movementSpeed = new RangedAttribute(null, SharedMonsterAttributes.I[0xB7 ^ 0xB1], 0.699999988079071, 0.0, 1024.0).setDescription(SharedMonsterAttributes.I[0x8A ^ 0x8D]).setShouldWatch(" ".length() != 0);
        attackDamage = new RangedAttribute(null, SharedMonsterAttributes.I[0xAF ^ 0xA7], 2.0, 0.0, 2048.0);
    }
}
