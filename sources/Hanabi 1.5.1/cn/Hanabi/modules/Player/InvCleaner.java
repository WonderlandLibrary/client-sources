package cn.Hanabi.modules.Player;

import cn.Hanabi.value.*;
import ClassSub.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import java.lang.reflect.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.potion.*;

public class InvCleaner extends Mod
{
    public Value<Boolean> autoclose;
    public static Class205 timer;
    public final Set<Integer> blacklistedItemIDs;
    public ItemStack[] bestArmorSet;
    public ItemStack bestSword;
    public ItemStack bestPickAxe;
    public ItemStack bestBow;
    
    
    public InvCleaner() {
        super("InvCleaner", Category.PLAYER);
        this.autoclose = new Value<Boolean>("InvCleaner_AutoToggle", false);
        this.blacklistedItemIDs = new HashSet<Integer>();
        InvCleaner.timer = new Class205();
    }
    
    @EventTarget
    public void onUpdate(final EventPreMotion p0) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore_3       
        //     4: goto            8
        //     7: return         
        //     8: iload_3        
        //     9: ifne            7
        //    12: iload_3        
        //    13: ifne            7
        //    16: getstatic       cn/Hanabi/modules/Player/InvCleaner.mc:Lnet/minecraft/client/Minecraft;
        //    19: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    22: getfield        net/minecraft/client/entity/EntityPlayerSP.ticksExisted:I
        //    25: ldc             2
        //    27: irem           
        //    28: ifne            333
        //    31: iload_3        
        //    32: ifne            333
        //    35: iload_3        
        //    36: ifne            7
        //    39: new             Ljava/util/Random;
        //    42: dup            
        //    43: invokespecial   java/util/Random.<init>:()V
        //    46: ldc             1
        //    48: invokevirtual   java/util/Random.nextInt:(I)I
        //    51: ifne            333
        //    54: iload_3        
        //    55: ifne            333
        //    58: iload_3        
        //    59: ifne            7
        //    62: iload_3        
        //    63: ifne            7
        //    66: aload_0        
        //    67: aload_0        
        //    68: invokevirtual   cn/Hanabi/modules/Player/InvCleaner.getBestArmorSet:()[Lnet/minecraft/item/ItemStack;
        //    71: checkcast       [Lnet/minecraft/item/ItemStack;
        //    74: putfield        cn/Hanabi/modules/Player/InvCleaner.bestArmorSet:[Lnet/minecraft/item/ItemStack;
        //    77: iload_3        
        //    78: ifne            7
        //    81: iload_3        
        //    82: ifne            7
        //    85: aload_0        
        //    86: aload_0        
        //    87: ldc             Lnet/minecraft/item/ItemSword;.class
        //    89: aload_0        
        //    90: invokedynamic   BootstrapMethod #0, applyAsDouble:(Lcn/Hanabi/modules/Player/InvCleaner;)Ljava/util/function/ToDoubleFunction;
        //    95: invokeinterface java/util/Comparator.comparingDouble:(Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
        //   100: invokevirtual   cn/Hanabi/modules/Player/InvCleaner.getBestItem:(Ljava/lang/Class;Ljava/util/Comparator;)Lnet/minecraft/item/ItemStack;
        //   103: putfield        cn/Hanabi/modules/Player/InvCleaner.bestSword:Lnet/minecraft/item/ItemStack;
        //   106: iload_3        
        //   107: ifne            7
        //   110: iload_3        
        //   111: ifne            7
        //   114: aload_0        
        //   115: aload_0        
        //   116: ldc             Lnet/minecraft/item/ItemPickaxe;.class
        //   118: aload_0        
        //   119: invokedynamic   BootstrapMethod #1, applyAsDouble:(Lcn/Hanabi/modules/Player/InvCleaner;)Ljava/util/function/ToDoubleFunction;
        //   124: invokeinterface java/util/Comparator.comparingDouble:(Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
        //   129: invokevirtual   cn/Hanabi/modules/Player/InvCleaner.getBestItem:(Ljava/lang/Class;Ljava/util/Comparator;)Lnet/minecraft/item/ItemStack;
        //   132: putfield        cn/Hanabi/modules/Player/InvCleaner.bestPickAxe:Lnet/minecraft/item/ItemStack;
        //   135: iload_3        
        //   136: ifne            7
        //   139: iload_3        
        //   140: ifne            7
        //   143: aload_0        
        //   144: aload_0        
        //   145: ldc             Lnet/minecraft/item/ItemBow;.class
        //   147: aload_0        
        //   148: invokedynamic   BootstrapMethod #2, applyAsDouble:(Lcn/Hanabi/modules/Player/InvCleaner;)Ljava/util/function/ToDoubleFunction;
        //   153: invokeinterface java/util/Comparator.comparingDouble:(Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
        //   158: invokevirtual   cn/Hanabi/modules/Player/InvCleaner.getBestItem:(Ljava/lang/Class;Ljava/util/Comparator;)Lnet/minecraft/item/ItemStack;
        //   161: putfield        cn/Hanabi/modules/Player/InvCleaner.bestBow:Lnet/minecraft/item/ItemStack;
        //   164: iload_3        
        //   165: ifne            7
        //   168: iload_3        
        //   169: ifne            7
        //   172: getstatic       cn/Hanabi/modules/Player/InvCleaner.mc:Lnet/minecraft/client/Minecraft;
        //   175: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   178: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //   181: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //   184: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //   189: invokedynamic   BootstrapMethod #3, test:()Ljava/util/function/Predicate;
        //   194: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   199: invokedynamic   BootstrapMethod #4, test:()Ljava/util/function/Predicate;
        //   204: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   209: invokedynamic   BootstrapMethod #5, test:()Ljava/util/function/Predicate;
        //   214: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   219: aload_0        
        //   220: invokedynamic   BootstrapMethod #6, test:(Lcn/Hanabi/modules/Player/InvCleaner;)Ljava/util/function/Predicate;
        //   225: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   230: invokeinterface java/util/stream/Stream.findFirst:()Ljava/util/Optional;
        //   235: astore_2       
        //   236: iload_3        
        //   237: ifne            7
        //   240: iload_3        
        //   241: ifne            7
        //   244: aload_2        
        //   245: invokevirtual   java/util/Optional.isPresent:()Z
        //   248: ifeq            291
        //   251: iload_3        
        //   252: ifne            291
        //   255: iload_3        
        //   256: ifne            7
        //   259: iload_3        
        //   260: ifne            7
        //   263: aload_0        
        //   264: aload_2        
        //   265: invokevirtual   java/util/Optional.get:()Ljava/lang/Object;
        //   268: checkcast       Lnet/minecraft/inventory/Slot;
        //   271: getfield        net/minecraft/inventory/Slot.slotNumber:I
        //   274: invokevirtual   cn/Hanabi/modules/Player/InvCleaner.dropItem:(I)V
        //   277: iload_3        
        //   278: ifne            7
        //   281: iload_3        
        //   282: ifeq            333
        //   285: aconst_null    
        //   286: athrow         
        //   287: nop            
        //   288: nop            
        //   289: nop            
        //   290: athrow         
        //   291: iload_3        
        //   292: ifne            7
        //   295: aload_0        
        //   296: getfield        cn/Hanabi/modules/Player/InvCleaner.autoclose:Lcn/Hanabi/value/Value;
        //   299: invokevirtual   cn/Hanabi/value/Value.getValueState:()Ljava/lang/Object;
        //   302: checkcast       Ljava/lang/Boolean;
        //   305: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   308: ifeq            333
        //   311: iload_3        
        //   312: ifne            333
        //   315: iload_3        
        //   316: ifne            7
        //   319: iload_3        
        //   320: ifne            7
        //   323: aload_0        
        //   324: ldc             0
        //   326: invokevirtual   cn/Hanabi/modules/Player/InvCleaner.set:(Z)V
        //   329: iload_3        
        //   330: ifne            7
        //   333: iload_3        
        //   334: ifne            7
        //   337: return         
        //   338: nop            
        //   339: nop            
        //   340: nop            
        //   341: athrow         
        //    Exceptions:
        //  throws java.lang.IllegalAccessException
        //  throws java.lang.IllegalArgumentException
        //  throws java.lang.reflect.InvocationTargetException
        //  throws java.lang.Exception
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void dropItem(final int n) {
        InvCleaner.mc.playerController.windowClick(0, n, 1, 4, (EntityPlayer)InvCleaner.mc.thePlayer);
    }
    
    public boolean isItemBlackListed(final ItemStack itemStack) {
        final Item getItem = itemStack.getItem();
        return this.blacklistedItemIDs.contains(Item.getIdFromItem(getItem)) || (getItem instanceof ItemBow && !this.bestBow.equals(itemStack)) || (getItem instanceof ItemTool && !this.bestPickAxe.equals(itemStack)) || getItem instanceof ItemFishingRod || getItem instanceof ItemGlassBottle || getItem instanceof ItemBucket || getItem instanceof ItemEgg || getItem instanceof ItemSnowball || (getItem instanceof ItemSword && !this.bestSword.equals(itemStack)) || (getItem instanceof ItemArmor && !this.bestArmorSet[((ItemArmor)getItem).armorType].equals(itemStack)) || (getItem instanceof ItemPotion && this.isPotionNegative(itemStack));
    }
    
    public ItemStack getBestItem(final Class<? extends Item> clazz, final Comparator comparator) {
        return (ItemStack)InvCleaner.mc.thePlayer.inventoryContainer.inventorySlots.stream().map(Slot::getStack).filter(Objects::nonNull).filter(InvCleaner::lambda$getBestItem$3).max(comparator).orElse(null);
    }
    
    public ItemStack[] getBestArmorSet() {
        final ItemStack[] array = new ItemStack[4];
        for (final ItemStack itemStack : (List)InvCleaner.mc.thePlayer.inventoryContainer.inventorySlots.stream().filter(Slot::getHasStack).map(Slot::getStack).filter(InvCleaner::lambda$getBestArmorSet$4).collect(Collectors.toList())) {
            final ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
            final ItemStack itemStack2 = array[itemArmor.armorType];
            if (itemStack2 == null || this.getArmorDamageReduction(itemStack) > this.getArmorDamageReduction(itemStack2)) {
                array[itemArmor.armorType] = itemStack;
            }
        }
        return array;
    }
    
    public double getSwordDamage(final ItemStack itemStack) {
        double getAmount = 0.0;
        final Optional<AttributeModifier> first = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (first.isPresent()) {
            getAmount = first.get().getAmount();
        }
        return getAmount + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    public double getBowPower(final ItemStack itemStack) {
        double getAmount = 0.0;
        final Optional<AttributeModifier> first = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (first.isPresent()) {
            getAmount = first.get().getAmount();
        }
        return getAmount + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    public double getMiningSpeed(final ItemStack itemStack) {
        double getAmount = 0.0;
        final Optional<AttributeModifier> first = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (first.isPresent()) {
            getAmount = first.get().getAmount();
        }
        return getAmount + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    public double getArmorDamageReduction(final ItemStack itemStack) {
        return ((ItemArmor)itemStack.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[] { itemStack }, DamageSource.causePlayerDamage((EntityPlayer)InvCleaner.mc.thePlayer));
    }
    
    public boolean isPotionNegative(final ItemStack itemStack) {
        return ((ItemPotion)itemStack.getItem()).getEffects(itemStack).stream().map(InvCleaner::lambda$isPotionNegative$5).anyMatch(Potion::isBadEffect);
    }
    
    private boolean stackIsUseful(final int n) {
        final ItemStack getStack = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(n).getStack();
        boolean b = false;
        if (getStack.getItem() instanceof ItemSword || getStack.getItem() instanceof ItemPickaxe || getStack.getItem() instanceof ItemAxe) {
            for (int i = 0; i < 45; ++i) {
                if (i != n) {
                    if (InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        final ItemStack getStack2 = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if (((getStack2 != null && getStack2.getItem() instanceof ItemSword) || getStack2.getItem() instanceof ItemAxe || getStack2.getItem() instanceof ItemPickaxe) && getItemDamage(getStack2) + EnchantmentHelper.getModifierForCreature(getStack2, EnumCreatureAttribute.UNDEFINED) > getItemDamage(getStack) + EnchantmentHelper.getModifierForCreature(getStack, EnumCreatureAttribute.UNDEFINED)) {
                            b = true;
                            break;
                        }
                    }
                }
            }
        }
        else if (getStack.getItem() instanceof ItemArmor) {
            for (int j = 0; j < 45; ++j) {
                if (n != j) {
                    if (InvCleaner.mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                        final ItemStack getStack3 = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                        if (getStack3 != null && getStack3.getItem() instanceof ItemArmor) {
                            final List<Integer> list = Arrays.asList(298, 314, 302, 306, 310);
                            final List<Integer> list2 = Arrays.asList(299, 315, 303, 307, 311);
                            final List<Integer> list3 = Arrays.asList(300, 316, 304, 308, 312);
                            final List<Integer> list4 = Arrays.asList(301, 317, 305, 309, 313);
                            if (list.contains(Item.getIdFromItem(getStack3.getItem())) && list.contains(Item.getIdFromItem(getStack.getItem()))) {
                                if (list.indexOf(Item.getIdFromItem(getStack.getItem())) < list.indexOf(Item.getIdFromItem(getStack3.getItem()))) {
                                    b = true;
                                    break;
                                }
                            }
                            else if (list2.contains(Item.getIdFromItem(getStack3.getItem())) && list2.contains(Item.getIdFromItem(getStack.getItem()))) {
                                if (list2.indexOf(Item.getIdFromItem(getStack.getItem())) < list2.indexOf(Item.getIdFromItem(getStack3.getItem()))) {
                                    b = true;
                                    break;
                                }
                            }
                            else if (list3.contains(Item.getIdFromItem(getStack3.getItem())) && list3.contains(Item.getIdFromItem(getStack.getItem()))) {
                                if (list3.indexOf(Item.getIdFromItem(getStack.getItem())) < list3.indexOf(Item.getIdFromItem(getStack3.getItem()))) {
                                    b = true;
                                    break;
                                }
                            }
                            else if (list4.contains(Item.getIdFromItem(getStack3.getItem())) && list4.contains(Item.getIdFromItem(getStack.getItem())) && list4.indexOf(Item.getIdFromItem(getStack.getItem())) < list4.indexOf(Item.getIdFromItem(getStack3.getItem()))) {
                                b = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int k = 0; k < 45; ++k) {
            if (n != k) {
                if (InvCleaner.mc.thePlayer.inventoryContainer.getSlot(k).getHasStack()) {
                    final ItemStack getStack4 = InvCleaner.mc.thePlayer.inventoryContainer.getSlot(k).getStack();
                    if (getStack4 != null && (getStack4.getItem() instanceof ItemSword || getStack4.getItem() instanceof ItemAxe || getStack4.getItem() instanceof ItemBow || getStack4.getItem() instanceof ItemFishingRod || getStack4.getItem() instanceof ItemArmor || getStack4.getItem() instanceof ItemAxe || getStack4.getItem() instanceof ItemPickaxe || Item.getIdFromItem(getStack4.getItem()) == 346)) {
                        getStack4.getItem();
                        if (Item.getIdFromItem(getStack.getItem()) == Item.getIdFromItem(getStack4.getItem())) {
                            b = true;
                            break;
                        }
                    }
                }
            }
        }
        return Item.getIdFromItem(getStack.getItem()) != 367 && (Item.getIdFromItem(getStack.getItem()) == 30 || Item.getIdFromItem(getStack.getItem()) == 259 || Item.getIdFromItem(getStack.getItem()) == 262 || Item.getIdFromItem(getStack.getItem()) == 264 || Item.getIdFromItem(getStack.getItem()) == 265 || Item.getIdFromItem(getStack.getItem()) == 346 || Item.getIdFromItem(getStack.getItem()) == 384 || Item.getIdFromItem(getStack.getItem()) == 345 || Item.getIdFromItem(getStack.getItem()) == 296 || Item.getIdFromItem(getStack.getItem()) == 336 || Item.getIdFromItem(getStack.getItem()) == 266 || Item.getIdFromItem(getStack.getItem()) == 280 || getStack.hasDisplayName() || (!b && (getStack.getItem() instanceof ItemArmor || getStack.getItem() instanceof ItemAxe || getStack.getItem() instanceof ItemBow || getStack.getItem() instanceof ItemSword || getStack.getItem() instanceof ItemPotion || getStack.getItem() instanceof ItemFlintAndSteel || getStack.getItem() instanceof ItemEnderPearl || getStack.getItem() instanceof ItemBlock || getStack.getItem() instanceof ItemFood || getStack.getItem() instanceof ItemPickaxe)));
    }
    
    static float getItemDamage(final ItemStack itemStack) {
        final Multimap getAttributeModifiers = itemStack.getAttributeModifiers();
        if (!getAttributeModifiers.isEmpty()) {
            final Iterator iterator = getAttributeModifiers.entries().iterator();
            if (iterator.hasNext()) {
                final AttributeModifier attributeModifier = iterator.next().getValue();
                double getAmount;
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    getAmount = attributeModifier.getAmount();
                }
                else {
                    getAmount = attributeModifier.getAmount() * 100.0;
                }
                if (attributeModifier.getAmount() > 1.0) {
                    return 1.0f + (float)getAmount;
                }
                return 1.0f;
            }
        }
        return 1.0f;
    }
    
    private static Potion lambda$isPotionNegative$5(final PotionEffect potionEffect) {
        return Potion.potionTypes[potionEffect.getPotionID()];
    }
    
    private static boolean lambda$getBestArmorSet$4(final ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor;
    }
    
    private static boolean lambda$getBestItem$3(final Class clazz, final ItemStack itemStack) {
        return itemStack.getItem().getClass().equals(clazz);
    }
    
    private boolean lambda$onUpdate$2(final Slot slot) {
        return this.isItemBlackListed(slot.getStack());
    }
    
    private static boolean lambda$onUpdate$1(final Slot slot) {
        return !slot.getStack().equals(InvCleaner.mc.thePlayer.getHeldItem());
    }
    
    private static boolean lambda$onUpdate$0(final Slot slot) {
        return Arrays.stream(InvCleaner.mc.thePlayer.inventory.armorInventory).noneMatch(slot.getStack()::equals);
    }
}
