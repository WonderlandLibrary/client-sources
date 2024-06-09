package com.client.glowclient.commands;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import com.client.glowclient.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import com.client.glowclient.modules.render.*;

public class Peek extends Command
{
    private static final Minecraft B;
    private static Block[] b;
    
    public Peek() {
        final int n = 16;
        super("peek");
        final Block[] b = new Block[n];
        b[0] = Blocks.BLACK_SHULKER_BOX;
        b[1] = Blocks.BLUE_SHULKER_BOX;
        b[2] = Blocks.BROWN_SHULKER_BOX;
        b[3] = Blocks.CYAN_SHULKER_BOX;
        b[4] = Blocks.GRAY_SHULKER_BOX;
        b[5] = Blocks.GREEN_SHULKER_BOX;
        b[6] = Blocks.LIGHT_BLUE_SHULKER_BOX;
        b[7] = Blocks.LIME_SHULKER_BOX;
        b[8] = Blocks.MAGENTA_SHULKER_BOX;
        b[9] = Blocks.ORANGE_SHULKER_BOX;
        b[10] = Blocks.PINK_SHULKER_BOX;
        b[11] = Blocks.PURPLE_SHULKER_BOX;
        b[12] = Blocks.RED_SHULKER_BOX;
        b[13] = Blocks.SILVER_SHULKER_BOX;
        b[14] = Blocks.WHITE_SHULKER_BOX;
        b[15] = Blocks.YELLOW_SHULKER_BOX;
        Peek.b = b;
    }
    
    @Override
    public void M(final String s, final String[] array) {
        ItemStack itemStack = null;
        if (!Peek.B.player.getHeldItemOffhand().isEmpty()) {
            itemStack = Peek.B.player.getHeldItemOffhand();
        }
        if (!Peek.B.player.getHeldItemMainhand().isEmpty()) {
            itemStack = Peek.B.player.getHeldItemMainhand();
        }
        if (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof ItemBlock) {
            if (M(((ItemBlock)itemStack.getItem()).getBlock())) {
                qd.D("§bOpening Shulker Box");
                if (itemStack.hasTagCompound()) {
                    Peek.B.displayGuiScreen((GuiScreen)new GuiChest((IInventory)Peek.B.player.inventory, (IInventory)M(itemStack.getTagCompound().getCompoundTag("BlockEntityTag"))));
                    return;
                }
                Peek.B.displayGuiScreen((GuiScreen)new GuiChest((IInventory)Peek.B.player.inventory, (IInventory)new InventoryBasic("Shulker Box", true, 27)));
            }
            else {
                qd.D("§cYou are not holding a Shulker Box");
            }
        }
    }
    
    @Override
    public String M(final String s, final String[] array) {
        return new StringBuilder().insert(0, Command.B.e()).append("peek").toString();
    }
    
    private static boolean M(final Block block) {
        final Block[] b;
        final int length = (b = Peek.b).length;
        int n;
        int i = n = 0;
        while (i < length) {
            if (b[n] == block) {
                return true;
            }
            i = ++n;
        }
        return false;
    }
    
    static {
        B = Minecraft.getMinecraft();
    }
    
    private static InventoryBasic M(final NBTTagCompound nbtTagCompound) {
        final NonNullList withSize = NonNullList.withSize(27, (Object)ItemStack.EMPTY);
        String string = "Shulker Box";
        if (nbtTagCompound.hasKey("Items", 9)) {
            PeekMod.M(nbtTagCompound, (NonNullList<ItemStack>)withSize);
        }
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            string = nbtTagCompound.getString("CustomName");
        }
        final InventoryBasic inventoryBasic = new InventoryBasic(string, true, withSize.size());
        int n;
        int i = n = 0;
        while (i < withSize.size()) {
            final InventoryBasic inventoryBasic2 = inventoryBasic;
            final NonNullList<ItemStack> list = (NonNullList<ItemStack>)withSize;
            final int n2 = n++;
            inventoryBasic2.setInventorySlotContents(n2, (ItemStack)list.get(n2));
            i = n;
        }
        return inventoryBasic;
    }
}
