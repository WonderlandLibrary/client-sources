package net.minecraft.tileentity;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class TileEntityFlowerPot extends TileEntity
{
    private static final String[] I;
    private Item flowerPotItem;
    private int flowerPotData;
    
    public void setFlowerPotData(final Item flowerPotItem, final int flowerPotData) {
        this.flowerPotItem = flowerPotItem;
        this.flowerPotData = flowerPotData;
    }
    
    static {
        I();
    }
    
    public Item getFlowerPotItem() {
        return this.flowerPotItem;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        final ResourceLocation resourceLocation = Item.itemRegistry.getNameForObject(this.flowerPotItem);
        final String s = TileEntityFlowerPot.I["".length()];
        String string;
        if (resourceLocation == null) {
            string = TileEntityFlowerPot.I[" ".length()];
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            string = resourceLocation.toString();
        }
        nbtTagCompound.setString(s, string);
        nbtTagCompound.setInteger(TileEntityFlowerPot.I["  ".length()], this.flowerPotData);
    }
    
    private static void I() {
        (I = new String[0xAF ^ 0xA6])["".length()] = I(":\"7\b", "sVRec");
        TileEntityFlowerPot.I[" ".length()] = I("", "hWnwX");
        TileEntityFlowerPot.I["  ".length()] = I("26?&", "vWKGj");
        TileEntityFlowerPot.I["   ".length()] = I("\u0003\u0019=/", "JmXBO");
        TileEntityFlowerPot.I[0x9E ^ 0x9A] = I("%\u0019\u0012\u0005", "lmwhL");
        TileEntityFlowerPot.I[0x6B ^ 0x6E] = I("+\u0018+\u0015", "blNxk");
        TileEntityFlowerPot.I[0x4F ^ 0x49] = I("\u0012\n\u00158", "VkaYC");
        TileEntityFlowerPot.I[0x0 ^ 0x7] = I(",\"\u0007\u0019", "eVbtj");
        TileEntityFlowerPot.I[0x77 ^ 0x7F] = I("\u001c\u0005\u0004<", "UqaQG");
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(TileEntityFlowerPot.I["   ".length()], 0xAB ^ 0xA3)) {
            this.flowerPotItem = Item.getByNameOrId(nbtTagCompound.getString(TileEntityFlowerPot.I[0x68 ^ 0x6C]));
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            this.flowerPotItem = Item.getItemById(nbtTagCompound.getInteger(TileEntityFlowerPot.I[0x67 ^ 0x62]));
        }
        this.flowerPotData = nbtTagCompound.getInteger(TileEntityFlowerPot.I[0xB ^ 0xD]);
    }
    
    public int getFlowerPotData() {
        return this.flowerPotData;
    }
    
    public TileEntityFlowerPot() {
    }
    
    public TileEntityFlowerPot(final Item flowerPotItem, final int flowerPotData) {
        this.flowerPotItem = flowerPotItem;
        this.flowerPotData = flowerPotData;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        nbtTagCompound.removeTag(TileEntityFlowerPot.I[0x5 ^ 0x2]);
        nbtTagCompound.setInteger(TileEntityFlowerPot.I[0xAE ^ 0xA6], Item.getIdFromItem(this.flowerPotItem));
        return new S35PacketUpdateTileEntity(this.pos, 0x61 ^ 0x64, nbtTagCompound);
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
}
