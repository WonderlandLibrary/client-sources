package cn.Hanabi.modules.Combat;

import ClassSub.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import java.util.function.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import java.util.*;

public class AutoSword extends Mod
{
    private ItemStack bestSword;
    private ItemStack prevBestSword;
    private boolean shouldSwitch;
    public Class205 timer;
    
    
    public AutoSword() {
        super("AutoSword", Category.COMBAT);
        this.shouldSwitch = false;
        this.timer = new Class205();
    }
    
    @EventTarget
    private void onUpdate(final EventUpdate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: istore          4
        //     5: goto            9
        //     8: return         
        //     9: iload           4
        //    11: ifne            8
        //    14: iload           4
        //    16: ifne            8
        //    19: getstatic       ClassSub/Class334.username:Ljava/lang/String;
        //    22: invokevirtual   java/lang/String.length:()I
        //    25: ldc             1
        //    27: if_icmpge       50
        //    30: iload           4
        //    32: ifne            50
        //    35: iload           4
        //    37: ifne            8
        //    40: ldc             0
        //    42: invokestatic    java/lang/System.exit:(I)V
        //    45: iload           4
        //    47: ifne            8
        //    50: iload           4
        //    52: ifne            8
        //    55: aload_0        
        //    56: pop            
        //    57: iload           4
        //    59: ifne            8
        //    62: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //    65: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    68: getfield        net/minecraft/client/entity/EntityPlayerSP.ticksExisted:I
        //    71: ldc             7
        //    73: irem           
        //    74: ifne            753
        //    77: iload           4
        //    79: ifne            753
        //    82: iload           4
        //    84: ifne            8
        //    87: iload           4
        //    89: ifne            8
        //    92: aload_0        
        //    93: pop            
        //    94: iload           4
        //    96: ifne            8
        //    99: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   102: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   105: getfield        net/minecraft/client/entity/EntityPlayerSP.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //   108: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //   111: ifne            180
        //   114: iload           4
        //   116: ifne            180
        //   119: iload           4
        //   121: ifne            8
        //   124: aload_0        
        //   125: pop            
        //   126: iload           4
        //   128: ifne            8
        //   131: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   134: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   137: getfield        net/minecraft/client/entity/EntityPlayerSP.openContainer:Lnet/minecraft/inventory/Container;
        //   140: ifnull          191
        //   143: iload           4
        //   145: ifne            8
        //   148: aload_0        
        //   149: pop            
        //   150: iload           4
        //   152: ifne            8
        //   155: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   158: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   161: getfield        net/minecraft/client/entity/EntityPlayerSP.openContainer:Lnet/minecraft/inventory/Container;
        //   164: getfield        net/minecraft/inventory/Container.windowId:I
        //   167: ifeq            191
        //   170: iload           4
        //   172: ifne            191
        //   175: iload           4
        //   177: ifne            8
        //   180: iload           4
        //   182: ifne            8
        //   185: return         
        //   186: nop            
        //   187: nop            
        //   188: nop            
        //   189: nop            
        //   190: athrow         
        //   191: iload           4
        //   193: ifne            8
        //   196: aload_0        
        //   197: aload_0        
        //   198: ldc             Lnet/minecraft/item/ItemSword;.class
        //   200: aload_0        
        //   201: invokedynamic   BootstrapMethod #0, applyAsDouble:(Lcn/Hanabi/modules/Combat/AutoSword;)Ljava/util/function/ToDoubleFunction;
        //   206: invokeinterface java/util/Comparator.comparingDouble:(Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
        //   211: invokespecial   cn/Hanabi/modules/Combat/AutoSword.getBestItem:(Ljava/lang/Class;Ljava/util/Comparator;)Lnet/minecraft/item/ItemStack;
        //   214: putfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   217: iload           4
        //   219: ifne            8
        //   222: iload           4
        //   224: ifne            8
        //   227: aload_0        
        //   228: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   231: ifnonnull       250
        //   234: iload           4
        //   236: ifne            8
        //   239: iload           4
        //   241: ifne            8
        //   244: return         
        //   245: nop            
        //   246: nop            
        //   247: nop            
        //   248: nop            
        //   249: athrow         
        //   250: iload           4
        //   252: ifne            8
        //   255: aload_0        
        //   256: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   259: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   262: ldc             0
        //   264: invokestatic    ClassSub/Class109.hotbarHas:(Lnet/minecraft/item/Item;I)Z
        //   267: istore_2       
        //   268: iload           4
        //   270: ifne            8
        //   273: iload           4
        //   275: ifne            8
        //   278: iload_2        
        //   279: ifeq            397
        //   282: iload           4
        //   284: ifne            397
        //   287: iload           4
        //   289: ifne            8
        //   292: iload           4
        //   294: ifne            8
        //   297: ldc             0
        //   299: invokestatic    ClassSub/Class109.getItemBySlotID:(I)Lnet/minecraft/item/ItemStack;
        //   302: ifnull          394
        //   305: iload           4
        //   307: ifne            8
        //   310: iload           4
        //   312: ifne            8
        //   315: ldc             0
        //   317: invokestatic    ClassSub/Class109.getItemBySlotID:(I)Lnet/minecraft/item/ItemStack;
        //   320: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   323: instanceof      Lnet/minecraft/item/ItemSword;
        //   326: ifeq            397
        //   329: iload           4
        //   331: ifne            397
        //   334: iload           4
        //   336: ifne            8
        //   339: iload           4
        //   341: ifne            8
        //   344: aload_0        
        //   345: ldc             0
        //   347: invokestatic    ClassSub/Class109.getItemBySlotID:(I)Lnet/minecraft/item/ItemStack;
        //   350: invokespecial   cn/Hanabi/modules/Combat/AutoSword.getSwordDamage:(Lnet/minecraft/item/ItemStack;)D
        //   353: aload_0        
        //   354: aload_0        
        //   355: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   358: invokespecial   cn/Hanabi/modules/Combat/AutoSword.getSwordDamage:(Lnet/minecraft/item/ItemStack;)D
        //   361: dcmpl          
        //   362: iflt            384
        //   365: iload           4
        //   367: ifne            384
        //   370: iload           4
        //   372: ifne            8
        //   375: ldc             1
        //   377: iload           4
        //   379: ifeq            386
        //   382: aconst_null    
        //   383: athrow         
        //   384: ldc             0
        //   386: istore_2       
        //   387: iload           4
        //   389: ifeq            397
        //   392: aconst_null    
        //   393: athrow         
        //   394: ldc             0
        //   396: istore_2       
        //   397: aload_0        
        //   398: getfield        cn/Hanabi/modules/Combat/AutoSword.prevBestSword:Lnet/minecraft/item/ItemStack;
        //   401: ifnull          432
        //   404: aload_0        
        //   405: getfield        cn/Hanabi/modules/Combat/AutoSword.prevBestSword:Lnet/minecraft/item/ItemStack;
        //   408: aload_0        
        //   409: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   412: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //   415: ifeq            432
        //   418: iload           4
        //   420: ifne            432
        //   423: iload_2        
        //   424: ifne            453
        //   427: iload           4
        //   429: ifne            453
        //   432: aload_0        
        //   433: ldc             1
        //   435: putfield        cn/Hanabi/modules/Combat/AutoSword.shouldSwitch:Z
        //   438: aload_0        
        //   439: aload_0        
        //   440: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   443: putfield        cn/Hanabi/modules/Combat/AutoSword.prevBestSword:Lnet/minecraft/item/ItemStack;
        //   446: iload           4
        //   448: ifeq            459
        //   451: aconst_null    
        //   452: athrow         
        //   453: aload_0        
        //   454: ldc             0
        //   456: putfield        cn/Hanabi/modules/Combat/AutoSword.shouldSwitch:Z
        //   459: aload_0        
        //   460: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   463: aload_0        
        //   464: aload_0        
        //   465: getfield        cn/Hanabi/modules/Combat/AutoSword.bestSword:Lnet/minecraft/item/ItemStack;
        //   468: invokespecial   cn/Hanabi/modules/Combat/AutoSword.getSwordDamage:(Lnet/minecraft/item/ItemStack;)D
        //   471: invokestatic    ClassSub/Class109.getBestSwordSlotID:(Lnet/minecraft/item/ItemStack;D)I
        //   474: istore_3       
        //   475: iload_3        
        //   476: tableswitch {
        //                0: 528
        //                1: 538
        //                2: 548
        //                3: 558
        //                4: 568
        //                5: 578
        //                6: 588
        //                7: 598
        //                8: 608
        //          default: 611
        //        }
        //   528: ldc             36
        //   530: istore_3       
        //   531: iload           4
        //   533: ifeq            611
        //   536: aconst_null    
        //   537: athrow         
        //   538: ldc             37
        //   540: istore_3       
        //   541: iload           4
        //   543: ifeq            611
        //   546: aconst_null    
        //   547: athrow         
        //   548: ldc             38
        //   550: istore_3       
        //   551: iload           4
        //   553: ifeq            611
        //   556: aconst_null    
        //   557: athrow         
        //   558: ldc             39
        //   560: istore_3       
        //   561: iload           4
        //   563: ifeq            611
        //   566: aconst_null    
        //   567: athrow         
        //   568: ldc             40
        //   570: istore_3       
        //   571: iload           4
        //   573: ifeq            611
        //   576: aconst_null    
        //   577: athrow         
        //   578: ldc             41
        //   580: istore_3       
        //   581: iload           4
        //   583: ifeq            611
        //   586: aconst_null    
        //   587: athrow         
        //   588: ldc             42
        //   590: istore_3       
        //   591: iload           4
        //   593: ifeq            611
        //   596: aconst_null    
        //   597: athrow         
        //   598: ldc             43
        //   600: istore_3       
        //   601: iload           4
        //   603: ifeq            611
        //   606: aconst_null    
        //   607: athrow         
        //   608: ldc             44
        //   610: istore_3       
        //   611: getstatic       cn/Hanabi/Hanabi.flag:I
        //   614: ifge            689
        //   617: iload           4
        //   619: ifne            689
        //   622: aload_0        
        //   623: pop            
        //   624: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   627: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   630: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //   633: iload_3        
        //   634: ldc             0
        //   636: ldc             4
        //   638: aload_0        
        //   639: pop            
        //   640: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   643: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   646: invokevirtual   net/minecraft/inventory/Container.slotClick:(IIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
        //   649: pop            
        //   650: aload_0        
        //   651: pop            
        //   652: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   655: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   658: aload_0        
        //   659: pop            
        //   660: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   663: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   666: getfield        net/minecraft/client/entity/EntityPlayerSP.inventoryContainer:Lnet/minecraft/inventory/Container;
        //   669: getfield        net/minecraft/inventory/Container.windowId:I
        //   672: iload_3        
        //   673: ldc             1
        //   675: ldc             4
        //   677: aload_0        
        //   678: pop            
        //   679: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   682: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   685: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.windowClick:(IIIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
        //   688: pop            
        //   689: aload_0        
        //   690: getfield        cn/Hanabi/modules/Combat/AutoSword.shouldSwitch:Z
        //   693: ifeq            753
        //   696: iload           4
        //   698: ifne            753
        //   701: aload_0        
        //   702: getfield        cn/Hanabi/modules/Combat/AutoSword.timer:LClassSub/Class205;
        //   705: ldc2_w          1000
        //   708: invokevirtual   ClassSub/Class205.isDelayComplete:(J)Z
        //   711: ifeq            753
        //   714: iload           4
        //   716: ifne            753
        //   719: aload_0        
        //   720: pop            
        //   721: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   724: getfield        net/minecraft/client/Minecraft.playerController:Lnet/minecraft/client/multiplayer/PlayerControllerMP;
        //   727: ldc             0
        //   729: iload_3        
        //   730: ldc             0
        //   732: ldc             2
        //   734: aload_0        
        //   735: pop            
        //   736: getstatic       cn/Hanabi/modules/Combat/AutoSword.mc:Lnet/minecraft/client/Minecraft;
        //   739: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   742: invokevirtual   net/minecraft/client/multiplayer/PlayerControllerMP.windowClick:(IIIILnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;
        //   745: pop            
        //   746: aload_0        
        //   747: getfield        cn/Hanabi/modules/Combat/AutoSword.timer:LClassSub/Class205;
        //   750: invokevirtual   ClassSub/Class205.reset:()V
        //   753: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException: -1
        //     at java.util.ArrayList.elementData(Unknown Source)
        //     at java.util.ArrayList.remove(Unknown Source)
        //     at com.strobel.assembler.ir.StackMappingVisitor.pop(StackMappingVisitor.java:267)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.execute(StackMappingVisitor.java:560)
        //     at com.strobel.assembler.ir.StackMappingVisitor$InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveForgeJarDecompiled(FileSaver.java:228)
        //     at us.deathmarine.luyten.FileSaver.lambda$saveAllForgeDir$0(FileSaver.java:142)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private ItemStack getBestItem(final Class<? extends Item> clazz, final Comparator comparator) {
        return (ItemStack)AutoSword.mc.thePlayer.inventoryContainer.inventorySlots.stream().map(Slot::getStack).filter(Objects::nonNull).filter(AutoSword::lambda$getBestItem$0).max(comparator).orElse(null);
    }
    
    private double getSwordDamage(final ItemStack itemStack) {
        double getAmount = 0.0;
        final Optional<AttributeModifier> first = itemStack.getAttributeModifiers().values().stream().findFirst();
        if (first.isPresent()) {
            getAmount = first.get().getAmount();
        }
        return getAmount + EnchantmentHelper.getModifierForCreature(itemStack, EnumCreatureAttribute.UNDEFINED);
    }
    
    private static boolean lambda$getBestItem$0(final Class clazz, final ItemStack itemStack) {
        return itemStack.getItem().getClass().equals(clazz);
    }
}
