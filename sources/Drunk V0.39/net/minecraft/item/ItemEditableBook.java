/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class ItemEditableBook
extends Item {
    public ItemEditableBook() {
        this.setMaxStackSize(1);
    }

    public static boolean validBookTagContents(NBTTagCompound nbt) {
        if (!ItemWritableBook.isNBTValid(nbt)) {
            return false;
        }
        if (!nbt.hasKey("title", 8)) {
            return false;
        }
        String s = nbt.getString("title");
        if (s == null) return false;
        if (s.length() > 32) return false;
        boolean bl = nbt.hasKey("author", 8);
        return bl;
    }

    public static int getGeneration(ItemStack book) {
        return book.getTagCompound().getInteger("generation");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasTagCompound()) return super.getItemStackDisplayName(stack);
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        String s = nbttagcompound.getString("title");
        if (StringUtils.isNullOrEmpty(s)) return super.getItemStackDisplayName(stack);
        return s;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (!stack.hasTagCompound()) return;
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        String s = nbttagcompound.getString("author");
        if (!StringUtils.isNullOrEmpty(s)) {
            tooltip.add((Object)((Object)EnumChatFormatting.GRAY) + StatCollector.translateToLocalFormatted("book.byAuthor", s));
        }
        tooltip.add((Object)((Object)EnumChatFormatting.GRAY) + StatCollector.translateToLocal("book.generation." + nbttagcompound.getInteger("generation")));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if (!worldIn.isRemote) {
            this.resolveContents(itemStackIn, playerIn);
        }
        playerIn.displayGUIBook(itemStackIn);
        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStackIn;
    }

    private void resolveContents(ItemStack stack, EntityPlayer player) {
        if (stack == null) return;
        if (stack.getTagCompound() == null) return;
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        if (nbttagcompound.getBoolean("resolved")) return;
        nbttagcompound.setBoolean("resolved", true);
        if (!ItemEditableBook.validBookTagContents(nbttagcompound)) return;
        NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);
        int i = 0;
        while (true) {
            IChatComponent lvt_7_1_;
            if (i >= nbttaglist.tagCount()) {
                nbttagcompound.setTag("pages", nbttaglist);
                if (!(player instanceof EntityPlayerMP)) return;
                if (player.getCurrentEquippedItem() != stack) return;
                Slot slot = player.openContainer.getSlotFromInventory(player.inventory, player.inventory.currentItem);
                ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S2FPacketSetSlot(0, slot.slotNumber, stack));
                return;
            }
            String s = nbttaglist.getStringTagAt(i);
            try {
                lvt_7_1_ = IChatComponent.Serializer.jsonToComponent(s);
                lvt_7_1_ = ChatComponentProcessor.processComponent(player, lvt_7_1_, player);
            }
            catch (Exception var9) {
                lvt_7_1_ = new ChatComponentText(s);
            }
            nbttaglist.set(i, new NBTTagString(IChatComponent.Serializer.componentToJson(lvt_7_1_)));
            ++i;
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}

