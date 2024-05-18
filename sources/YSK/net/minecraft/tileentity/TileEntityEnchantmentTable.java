package net.minecraft.tileentity;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class TileEntityEnchantmentTable extends TileEntity implements ITickable, IInteractionObject
{
    public float bookRotationPrev;
    private static final String[] I;
    public float field_145932_k;
    public float pageFlip;
    public float field_145929_l;
    public float bookRotation;
    public float bookSpread;
    public float pageFlipPrev;
    public float bookSpreadPrev;
    public float field_145924_q;
    public int tickCount;
    private static Random rand;
    private String customName;
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerEnchantment(inventoryPlayer, this.worldObj, this.pos);
    }
    
    @Override
    public void update() {
        this.bookSpreadPrev = this.bookSpread;
        this.bookRotationPrev = this.bookRotation;
        final EntityPlayer closestPlayer = this.worldObj.getClosestPlayer(this.pos.getX() + 0.5f, this.pos.getY() + 0.5f, this.pos.getZ() + 0.5f, 3.0);
        if (closestPlayer != null) {
            this.field_145924_q = (float)MathHelper.func_181159_b(closestPlayer.posZ - (this.pos.getZ() + 0.5f), closestPlayer.posX - (this.pos.getX() + 0.5f));
            this.bookSpread += 0.1f;
            if (this.bookSpread < 0.5f || TileEntityEnchantmentTable.rand.nextInt(0xB6 ^ 0x9E) == 0) {
                do {
                    this.field_145932_k += TileEntityEnchantmentTable.rand.nextInt(0xB5 ^ 0xB1) - TileEntityEnchantmentTable.rand.nextInt(0x63 ^ 0x67);
                } while (this.field_145932_k == this.field_145932_k);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else {
            this.field_145924_q += 0.02f;
            this.bookSpread -= 0.1f;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        while (this.bookRotation >= 3.1415927f) {
            this.bookRotation -= 6.2831855f;
        }
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (this.bookRotation < -3.1415927f) {
            this.bookRotation += 6.2831855f;
        }
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (this.field_145924_q >= 3.1415927f) {
            this.field_145924_q -= 6.2831855f;
        }
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (this.field_145924_q < -3.1415927f) {
            this.field_145924_q += 6.2831855f;
        }
        float n = this.field_145924_q - this.bookRotation;
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (n >= 3.1415927f) {
            n -= 6.2831855f;
        }
        "".length();
        if (false) {
            throw null;
        }
        while (n < -3.1415927f) {
            n += 6.2831855f;
        }
        this.bookRotation += n * 0.4f;
        this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0f, 1.0f);
        this.tickCount += " ".length();
        this.pageFlipPrev = this.pageFlip;
        final float n2 = (this.field_145932_k - this.pageFlip) * 0.4f;
        final float n3 = 0.2f;
        this.field_145929_l += (MathHelper.clamp_float(n2, -n3, n3) - this.field_145929_l) * 0.9f;
        this.pageFlip += this.field_145929_l;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        if (this.hasCustomName()) {
            nbtTagCompound.setString(TileEntityEnchantmentTable.I["".length()], this.customName);
        }
    }
    
    private static void I() {
        (I = new String[0x8D ^ 0x88])["".length()] = I("\f\u0013\t\u001e+\"(\u001b\u0007!", "OfzjD");
        TileEntityEnchantmentTable.I[" ".length()] = I("\u0015;%5\u0017;\u00007,\u001d", "VNVAx");
        TileEntityEnchantmentTable.I["  ".length()] = I(";>\u0004?)\u0015\u0005\u0016&#", "xKwKF");
        TileEntityEnchantmentTable.I["   ".length()] = I(".\u001f\u001c\u0016)$\u001e\u0017\u0010f(\u001e\u0011\n)#\u0004", "MprbH");
        TileEntityEnchantmentTable.I[0x6C ^ 0x68] = I("\u0001\u00006\u0007\f\u001e\b>\u0016U\t\u0007;\n\u000e\u0002\u001d1\f\b3\u001d9\u0000\u0003\t", "liXbo");
    }
    
    @Override
    public String getGuiID() {
        return TileEntityEnchantmentTable.I[0xB3 ^ 0xB7];
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            customName = TileEntityEnchantmentTable.I["   ".length()];
        }
        return customName;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(TileEntityEnchantmentTable.I[" ".length()], 0xC8 ^ 0xC0)) {
            this.customName = nbtTagCompound.getString(TileEntityEnchantmentTable.I["  ".length()]);
        }
    }
    
    public void setCustomName(final String customName) {
        this.customName = customName;
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        TileEntityEnchantmentTable.rand = new Random();
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.customName != null && this.customName.length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
}
