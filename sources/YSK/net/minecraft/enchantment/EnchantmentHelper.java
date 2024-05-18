package net.minecraft.enchantment;

import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;

public class EnchantmentHelper
{
    private static final Random enchantmentRand;
    private static final String[] I;
    private static final DamageIterator ENCHANTMENT_ITERATOR_DAMAGE;
    private static final ModifierLiving enchantmentModifierLiving;
    private static final HurtIterator ENCHANTMENT_ITERATOR_HURT;
    private static final ModifierDamage enchantmentModifierDamage;
    
    public static boolean getSilkTouchModifier(final EntityLivingBase entityLivingBase) {
        if (getEnchantmentLevel(Enchantment.silkTouch.effectId, entityLivingBase.getHeldItem()) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x1 ^ 0xB])["".length()] = I("\u0010\"", "yFrDL");
        EnchantmentHelper.I[" ".length()] = I("\u000e0\"", "bFNYH");
        EnchantmentHelper.I["  ".length()] = I(":+", "SOwgX");
        EnchantmentHelper.I["   ".length()] = I(".\u0007\u001f", "BqstG");
        EnchantmentHelper.I[0x17 ^ 0x13] = I("# ", "JDUKF");
        EnchantmentHelper.I[0x27 ^ 0x22] = I("\u001a3;", "vEWOz");
        EnchantmentHelper.I[0x6E ^ 0x68] = I("-\u001c\f!", "HroIx");
        EnchantmentHelper.I[0xE ^ 0x9] = I("\"?\u0013)", "GQpAa");
        EnchantmentHelper.I[0x2E ^ 0x26] = I("\u001b\u0006", "rbbvh");
        EnchantmentHelper.I[0x55 ^ 0x5C] = I(":,\n", "VZfWv");
    }
    
    public static int getEfficiencyModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, entityLivingBase.getHeldItem());
    }
    
    public static Map<Integer, Integer> getEnchantments(final ItemStack itemStack) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        NBTTagList list;
        if (itemStack.getItem() == Items.enchanted_book) {
            list = Items.enchanted_book.getEnchantments(itemStack);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            list = itemStack.getEnchantmentTagList();
        }
        final NBTTagList list2 = list;
        if (list2 != null) {
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < list2.tagCount()) {
                linkedHashMap.put((int)list2.getCompoundTagAt(i).getShort(EnchantmentHelper.I["  ".length()]), (int)list2.getCompoundTagAt(i).getShort(EnchantmentHelper.I["   ".length()]));
                ++i;
            }
        }
        return (Map<Integer, Integer>)linkedHashMap;
    }
    
    public static int getLureModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.lure.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getEnchantmentLevel(final int n, final ItemStack itemStack) {
        if (itemStack == null) {
            return "".length();
        }
        final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
        if (enchantmentTagList == null) {
            return "".length();
        }
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < enchantmentTagList.tagCount()) {
            final short short1 = enchantmentTagList.getCompoundTagAt(i).getShort(EnchantmentHelper.I["".length()]);
            final short short2 = enchantmentTagList.getCompoundTagAt(i).getShort(EnchantmentHelper.I[" ".length()]);
            if (short1 == n) {
                return short2;
            }
            ++i;
        }
        return "".length();
    }
    
    private static void applyEnchantmentModifierArray(final IModifier modifier, final ItemStack[] array) {
        final int length = array.length;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < length) {
            applyEnchantmentModifier(modifier, array[i]);
            ++i;
        }
    }
    
    public static int getRespiration(final Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.respiration.effectId, entity.getInventory());
    }
    
    public static int getEnchantmentModifierDamage(final ItemStack[] array, final DamageSource source) {
        EnchantmentHelper.enchantmentModifierDamage.damageModifier = "".length();
        EnchantmentHelper.enchantmentModifierDamage.source = source;
        applyEnchantmentModifierArray(EnchantmentHelper.enchantmentModifierDamage, array);
        if (EnchantmentHelper.enchantmentModifierDamage.damageModifier > (0x4E ^ 0x57)) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = (0xDB ^ 0xC2);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (EnchantmentHelper.enchantmentModifierDamage.damageModifier < 0) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = "".length();
        }
        return (EnchantmentHelper.enchantmentModifierDamage.damageModifier + " ".length() >> " ".length()) + EnchantmentHelper.enchantmentRand.nextInt((EnchantmentHelper.enchantmentModifierDamage.damageModifier >> " ".length()) + " ".length());
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static float func_152377_a(final ItemStack itemStack, final EnumCreatureAttribute entityLiving) {
        EnchantmentHelper.enchantmentModifierLiving.livingModifier = 0.0f;
        EnchantmentHelper.enchantmentModifierLiving.entityLiving = entityLiving;
        applyEnchantmentModifier(EnchantmentHelper.enchantmentModifierLiving, itemStack);
        return EnchantmentHelper.enchantmentModifierLiving.livingModifier;
    }
    
    public static int getMaxEnchantmentLevel(final int n, final ItemStack[] array) {
        if (array == null) {
            return "".length();
        }
        int length = "".length();
        final int length2 = array.length;
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < length2) {
            final int enchantmentLevel = getEnchantmentLevel(n, array[i]);
            if (enchantmentLevel > length) {
                length = enchantmentLevel;
            }
            ++i;
        }
        return length;
    }
    
    static {
        I();
        enchantmentRand = new Random();
        enchantmentModifierDamage = new ModifierDamage(null);
        enchantmentModifierLiving = new ModifierLiving(null);
        ENCHANTMENT_ITERATOR_HURT = new HurtIterator(null);
        ENCHANTMENT_ITERATOR_DAMAGE = new DamageIterator(null);
    }
    
    public static void applyThornEnchantments(final EntityLivingBase user, final Entity attacker) {
        EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT.attacker = attacker;
        EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT.user = user;
        if (user != null) {
            applyEnchantmentModifierArray(EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT, user.getInventory());
        }
        if (attacker instanceof EntityPlayer) {
            applyEnchantmentModifier(EnchantmentHelper.ENCHANTMENT_ITERATOR_HURT, user.getHeldItem());
        }
    }
    
    private static void applyEnchantmentModifier(final IModifier modifier, final ItemStack itemStack) {
        if (itemStack != null) {
            final NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
            if (enchantmentTagList != null) {
                int i = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                while (i < enchantmentTagList.tagCount()) {
                    final short short1 = enchantmentTagList.getCompoundTagAt(i).getShort(EnchantmentHelper.I[0x26 ^ 0x2E]);
                    final short short2 = enchantmentTagList.getCompoundTagAt(i).getShort(EnchantmentHelper.I[0x25 ^ 0x2C]);
                    if (Enchantment.getEnchantmentById(short1) != null) {
                        modifier.calculateModifier(Enchantment.getEnchantmentById(short1), short2);
                    }
                    ++i;
                }
            }
        }
    }
    
    public static int calcItemStackEnchantability(final Random random, final int n, int n2, final ItemStack itemStack) {
        if (itemStack.getItem().getItemEnchantability() <= 0) {
            return "".length();
        }
        if (n2 > (0xF ^ 0x0)) {
            n2 = (0x83 ^ 0x8C);
        }
        final int n3 = random.nextInt(0x43 ^ 0x4B) + " ".length() + (n2 >> " ".length()) + random.nextInt(n2 + " ".length());
        int n4;
        if (n == 0) {
            n4 = Math.max(n3 / "   ".length(), " ".length());
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (n == " ".length()) {
            n4 = n3 * "  ".length() / "   ".length() + " ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n4 = Math.max(n3, n2 * "  ".length());
        }
        return n4;
    }
    
    public static int getFireAspectModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getKnockbackModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.knockback.effectId, entityLivingBase.getHeldItem());
    }
    
    public static int getLuckOfSeaModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, entityLivingBase.getHeldItem());
    }
    
    public static Map<Integer, EnchantmentData> mapEnchantmentData(final int n, final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        Map<Integer, EnchantmentData> hashMap = null;
        int n2;
        if (itemStack.getItem() == Items.book) {
            n2 = " ".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        final Enchantment[] enchantmentsBookList;
        final int length = (enchantmentsBookList = Enchantment.enchantmentsBookList).length;
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < length) {
            final Enchantment enchantment = enchantmentsBookList[i];
            if (enchantment != null && (enchantment.type.canEnchantItem(item) || n3 != 0)) {
                int j = enchantment.getMinLevel();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                while (j <= enchantment.getMaxLevel()) {
                    if (n >= enchantment.getMinEnchantability(j) && n <= enchantment.getMaxEnchantability(j)) {
                        if (hashMap == null) {
                            hashMap = (Map<Integer, EnchantmentData>)Maps.newHashMap();
                        }
                        hashMap.put(enchantment.effectId, new EnchantmentData(enchantment, j));
                    }
                    ++j;
                }
            }
            ++i;
        }
        return hashMap;
    }
    
    public static int getDepthStriderModifier(final Entity entity) {
        return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, entity.getInventory());
    }
    
    public static ItemStack addRandomEnchantment(final Random random, final ItemStack itemStack, final int n) {
        final List<EnchantmentData> buildEnchantmentList = buildEnchantmentList(random, itemStack, n);
        int n2;
        if (itemStack.getItem() == Items.book) {
            n2 = " ".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        if (n3 != 0) {
            itemStack.setItem(Items.enchanted_book);
        }
        if (buildEnchantmentList != null) {
            final Iterator<EnchantmentData> iterator = buildEnchantmentList.iterator();
            "".length();
            if (2 == 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EnchantmentData enchantmentData = iterator.next();
                if (n3 != 0) {
                    Items.enchanted_book.addEnchantment(itemStack, enchantmentData);
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                    continue;
                }
                else {
                    itemStack.addEnchantment(enchantmentData.enchantmentobj, enchantmentData.enchantmentLevel);
                }
            }
        }
        return itemStack;
    }
    
    public static void applyArthropodEnchantments(final EntityLivingBase user, final Entity target) {
        EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE.user = user;
        EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE.target = target;
        if (user != null) {
            applyEnchantmentModifierArray(EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE, user.getInventory());
        }
        if (user instanceof EntityPlayer) {
            applyEnchantmentModifier(EnchantmentHelper.ENCHANTMENT_ITERATOR_DAMAGE, user.getHeldItem());
        }
    }
    
    public static ItemStack getEnchantedItem(final Enchantment enchantment, final EntityLivingBase entityLivingBase) {
        final ItemStack[] inventory;
        final int length = (inventory = entityLivingBase.getInventory()).length;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < length) {
            final ItemStack itemStack = inventory[i];
            if (itemStack != null && getEnchantmentLevel(enchantment.effectId, itemStack) > 0) {
                return itemStack;
            }
            ++i;
        }
        return null;
    }
    
    public static List<EnchantmentData> buildEnchantmentList(final Random random, final ItemStack itemStack, final int n) {
        final int itemEnchantability = itemStack.getItem().getItemEnchantability();
        if (itemEnchantability <= 0) {
            return null;
        }
        final int n2 = itemEnchantability / "  ".length();
        int length = (int)((" ".length() + random.nextInt((n2 >> " ".length()) + " ".length()) + random.nextInt((n2 >> " ".length()) + " ".length()) + n) * (1.0f + (random.nextFloat() + random.nextFloat() - 1.0f) * 0.15f) + 0.5f);
        if (length < " ".length()) {
            length = " ".length();
        }
        List<EnchantmentData> arrayList = null;
        final Map<Integer, EnchantmentData> mapEnchantmentData = mapEnchantmentData(length, itemStack);
        if (mapEnchantmentData != null && !mapEnchantmentData.isEmpty()) {
            final EnchantmentData enchantmentData = WeightedRandom.getRandomItem(random, mapEnchantmentData.values());
            if (enchantmentData != null) {
                arrayList = (List<EnchantmentData>)Lists.newArrayList();
                arrayList.add(enchantmentData);
                int n3 = length;
                "".length();
                if (!true) {
                    throw null;
                }
                while (random.nextInt(0x20 ^ 0x12) <= n3) {
                    final Iterator<Integer> iterator = mapEnchantmentData.keySet().iterator();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final Integer n4 = iterator.next();
                        int n5 = " ".length();
                        final Iterator<EnchantmentData> iterator2 = arrayList.iterator();
                        "".length();
                        if (0 < -1) {
                            throw null;
                        }
                        while (iterator2.hasNext()) {
                            if (!iterator2.next().enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(n4))) {
                                n5 = "".length();
                                "".length();
                                if (0 == 4) {
                                    throw null;
                                }
                                break;
                            }
                        }
                        if (n5 != 0) {
                            continue;
                        }
                        iterator.remove();
                    }
                    if (!mapEnchantmentData.isEmpty()) {
                        arrayList.add(WeightedRandom.getRandomItem(random, mapEnchantmentData.values()));
                    }
                    n3 >>= " ".length();
                }
            }
        }
        return arrayList;
    }
    
    public static int getLootingModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.looting.effectId, entityLivingBase.getHeldItem());
    }
    
    public static void setEnchantments(final Map<Integer, Integer> map, final ItemStack itemStack) {
        final NBTTagList list = new NBTTagList();
        final Iterator<Integer> iterator = map.keySet().iterator();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final int intValue = iterator.next();
            final Enchantment enchantmentById = Enchantment.getEnchantmentById(intValue);
            if (enchantmentById != null) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                nbtTagCompound.setShort(EnchantmentHelper.I[0x89 ^ 0x8D], (short)intValue);
                nbtTagCompound.setShort(EnchantmentHelper.I[0x3A ^ 0x3F], (short)(int)map.get(intValue));
                list.appendTag(nbtTagCompound);
                if (itemStack.getItem() != Items.enchanted_book) {
                    continue;
                }
                Items.enchanted_book.addEnchantment(itemStack, new EnchantmentData(enchantmentById, map.get(intValue)));
            }
        }
        if (list.tagCount() > 0) {
            if (itemStack.getItem() != Items.enchanted_book) {
                itemStack.setTagInfo(EnchantmentHelper.I[0x87 ^ 0x81], list);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
        }
        else if (itemStack.hasTagCompound()) {
            itemStack.getTagCompound().removeTag(EnchantmentHelper.I[0xA0 ^ 0xA7]);
        }
    }
    
    public static int getFortuneModifier(final EntityLivingBase entityLivingBase) {
        return getEnchantmentLevel(Enchantment.fortune.effectId, entityLivingBase.getHeldItem());
    }
    
    public static boolean getAquaAffinityModifier(final EntityLivingBase entityLivingBase) {
        if (getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, entityLivingBase.getInventory()) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    interface IModifier
    {
        void calculateModifier(final Enchantment p0, final int p1);
    }
    
    static final class ModifierLiving implements IModifier
    {
        public EnumCreatureAttribute entityLiving;
        public float livingModifier;
        
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
                if (-1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            this.livingModifier += enchantment.calcDamageByCreature(n, this.entityLiving);
        }
        
        private ModifierLiving() {
        }
        
        ModifierLiving(final ModifierLiving modifierLiving) {
            this();
        }
    }
    
    static final class DamageIterator implements IModifier
    {
        public EntityLivingBase user;
        public Entity target;
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            enchantment.onEntityDamaged(this.user, this.target, n);
        }
        
        private DamageIterator() {
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
                if (4 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        DamageIterator(final DamageIterator damageIterator) {
            this();
        }
    }
    
    static final class HurtIterator implements IModifier
    {
        public EntityLivingBase user;
        public Entity attacker;
        
        HurtIterator(final HurtIterator hurtIterator) {
            this();
        }
        
        private HurtIterator() {
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
                if (4 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            enchantment.onUserHurt(this.user, this.attacker, n);
        }
    }
    
    static final class ModifierDamage implements IModifier
    {
        public int damageModifier;
        public DamageSource source;
        
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private ModifierDamage() {
        }
        
        ModifierDamage(final ModifierDamage modifierDamage) {
            this();
        }
        
        @Override
        public void calculateModifier(final Enchantment enchantment, final int n) {
            this.damageModifier += enchantment.calcModifierDamage(n, this.source);
        }
    }
}
