/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.stats.RecipeBook;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeBookServer
extends RecipeBook {
    private static final Logger field_192828_d = LogManager.getLogger();

    public void func_193835_a(List<IRecipe> p_193835_1_, EntityPlayerMP p_193835_2_) {
        ArrayList<IRecipe> list = Lists.newArrayList();
        for (IRecipe irecipe : p_193835_1_) {
            if (this.field_194077_a.get(RecipeBookServer.func_194075_d(irecipe)) || irecipe.func_192399_d()) continue;
            this.func_194073_a(irecipe);
            this.func_193825_e(irecipe);
            list.add(irecipe);
            CriteriaTriggers.field_192126_f.func_192225_a(p_193835_2_, irecipe);
        }
        this.func_194081_a(SPacketRecipeBook.State.ADD, p_193835_2_, list);
    }

    public void func_193834_b(List<IRecipe> p_193834_1_, EntityPlayerMP p_193834_2_) {
        ArrayList<IRecipe> list = Lists.newArrayList();
        for (IRecipe irecipe : p_193834_1_) {
            if (!this.field_194077_a.get(RecipeBookServer.func_194075_d(irecipe))) continue;
            this.func_193831_b(irecipe);
            list.add(irecipe);
        }
        this.func_194081_a(SPacketRecipeBook.State.REMOVE, p_193834_2_, list);
    }

    private void func_194081_a(SPacketRecipeBook.State p_194081_1_, EntityPlayerMP p_194081_2_, List<IRecipe> p_194081_3_) {
        p_194081_2_.connection.sendPacket(new SPacketRecipeBook(p_194081_1_, p_194081_3_, Collections.emptyList(), this.field_192818_b, this.field_192819_c));
    }

    public NBTTagCompound func_192824_e() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setBoolean("isGuiOpen", this.field_192818_b);
        nbttagcompound.setBoolean("isFilteringCraftable", this.field_192819_c);
        NBTTagList nbttaglist = new NBTTagList();
        for (IRecipe irecipe : this.func_194079_d()) {
            nbttaglist.appendTag(new NBTTagString(CraftingManager.field_193380_a.getNameForObject(irecipe).toString()));
        }
        nbttagcompound.setTag("recipes", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();
        for (IRecipe irecipe1 : this.func_194080_e()) {
            nbttaglist1.appendTag(new NBTTagString(CraftingManager.field_193380_a.getNameForObject(irecipe1).toString()));
        }
        nbttagcompound.setTag("toBeDisplayed", nbttaglist1);
        return nbttagcompound;
    }

    public void func_192825_a(NBTTagCompound p_192825_1_) {
        this.field_192818_b = p_192825_1_.getBoolean("isGuiOpen");
        this.field_192819_c = p_192825_1_.getBoolean("isFilteringCraftable");
        NBTTagList nbttaglist = p_192825_1_.getTagList("recipes", 8);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            ResourceLocation resourcelocation = new ResourceLocation(nbttaglist.getStringTagAt(i));
            IRecipe irecipe = CraftingManager.func_193373_a(resourcelocation);
            if (irecipe == null) {
                field_192828_d.info("Tried to load unrecognized recipe: {} removed now.", (Object)resourcelocation);
                continue;
            }
            this.func_194073_a(irecipe);
        }
        NBTTagList nbttaglist1 = p_192825_1_.getTagList("toBeDisplayed", 8);
        for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
            ResourceLocation resourcelocation1 = new ResourceLocation(nbttaglist1.getStringTagAt(j));
            IRecipe irecipe1 = CraftingManager.func_193373_a(resourcelocation1);
            if (irecipe1 == null) {
                field_192828_d.info("Tried to load unrecognized recipe: {} removed now.", (Object)resourcelocation1);
                continue;
            }
            this.func_193825_e(irecipe1);
        }
    }

    private List<IRecipe> func_194079_d() {
        ArrayList<IRecipe> list = Lists.newArrayList();
        int i = this.field_194077_a.nextSetBit(0);
        while (i >= 0) {
            list.add(CraftingManager.field_193380_a.getObjectById(i));
            i = this.field_194077_a.nextSetBit(i + 1);
        }
        return list;
    }

    private List<IRecipe> func_194080_e() {
        ArrayList<IRecipe> list = Lists.newArrayList();
        int i = this.field_194078_b.nextSetBit(0);
        while (i >= 0) {
            list.add(CraftingManager.field_193380_a.getObjectById(i));
            i = this.field_194078_b.nextSetBit(i + 1);
        }
        return list;
    }

    public void func_192826_c(EntityPlayerMP p_192826_1_) {
        p_192826_1_.connection.sendPacket(new SPacketRecipeBook(SPacketRecipeBook.State.INIT, this.func_194079_d(), this.func_194080_e(), this.field_192818_b, this.field_192819_c));
    }
}

