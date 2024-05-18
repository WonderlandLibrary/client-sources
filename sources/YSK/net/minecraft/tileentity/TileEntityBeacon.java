package net.minecraft.tileentity;

import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;

public class TileEntityBeacon extends TileEntityLockable implements IInventory, ITickable
{
    private int primaryEffect;
    private static final String[] I;
    private int secondaryEffect;
    private float field_146014_j;
    private int levels;
    public static final Potion[][] effectsList;
    private final List<BeamSegment> beamSegments;
    private boolean isComplete;
    private ItemStack payment;
    private String customName;
    private long beamRenderCounter;
    
    public List<BeamSegment> getBeamSegments() {
        return this.beamSegments;
    }
    
    @Override
    public int getSizeInventory() {
        return " ".length();
    }
    
    private int func_183001_h(final int n) {
        if (n >= 0 && n < Potion.potionTypes.length && Potion.potionTypes[n] != null) {
            final Potion potion = Potion.potionTypes[n];
            int length;
            if (potion != Potion.moveSpeed && potion != Potion.digSpeed && potion != Potion.resistance && potion != Potion.jump && potion != Potion.damageBoost && potion != Potion.regeneration) {
                length = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                length = n;
            }
            return length;
        }
        return "".length();
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (n != 0 || this.payment == null) {
            return null;
        }
        if (n2 >= this.payment.stackSize) {
            final ItemStack payment = this.payment;
            this.payment = null;
            return payment;
        }
        final ItemStack payment2 = this.payment;
        payment2.stackSize -= n2;
        return new ItemStack(this.payment.getItem(), n2, this.payment.getMetadata());
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    public TileEntityBeacon() {
        this.beamSegments = (List<BeamSegment>)Lists.newArrayList();
        this.levels = -" ".length();
    }
    
    private void updateSegmentColors() {
        final int levels = this.levels;
        final int x = this.pos.getX();
        final int y = this.pos.getY();
        final int z = this.pos.getZ();
        this.levels = "".length();
        this.beamSegments.clear();
        this.isComplete = (" ".length() != 0);
        BeamSegment beamSegment = new BeamSegment(EntitySheep.func_175513_a(EnumDyeColor.WHITE));
        this.beamSegments.add(beamSegment);
        int n = " ".length();
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = y + " ".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < 210 + 132 - 325 + 239) {
            final IBlockState blockState = this.worldObj.getBlockState(mutableBlockPos.func_181079_c(x, i, z));
            Label_0458: {
                float[] array;
                if (blockState.getBlock() == Blocks.stained_glass) {
                    array = EntitySheep.func_175513_a(blockState.getValue(BlockStainedGlass.COLOR));
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                else if (blockState.getBlock() != Blocks.stained_glass_pane) {
                    if (blockState.getBlock().getLightOpacity() >= (0x2F ^ 0x20) && blockState.getBlock() != Blocks.bedrock) {
                        this.isComplete = ("".length() != 0);
                        this.beamSegments.clear();
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        beamSegment.incrementHeight();
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                        break Label_0458;
                    }
                }
                else {
                    array = EntitySheep.func_175513_a(blockState.getValue(BlockStainedGlassPane.COLOR));
                }
                if (n == 0) {
                    final float[] array2 = new float["   ".length()];
                    array2["".length()] = (beamSegment.getColors()["".length()] + array["".length()]) / 2.0f;
                    array2[" ".length()] = (beamSegment.getColors()[" ".length()] + array[" ".length()]) / 2.0f;
                    array2["  ".length()] = (beamSegment.getColors()["  ".length()] + array["  ".length()]) / 2.0f;
                    array = array2;
                }
                if (Arrays.equals(array, beamSegment.getColors())) {
                    beamSegment.incrementHeight();
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                }
                else {
                    beamSegment = new BeamSegment(array);
                    this.beamSegments.add(beamSegment);
                }
                n = "".length();
            }
            ++i;
        }
        if (this.isComplete) {
            int j = " ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (j <= (0x1C ^ 0x18)) {
                final int n2 = y - j;
                if (n2 < 0) {
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    int n3 = " ".length();
                    int n4 = x - j;
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                    while (n4 <= x + j && n3 != 0) {
                        int k = z - j;
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                        while (k <= z + j) {
                            final Block block = this.worldObj.getBlockState(new BlockPos(n4, n2, k)).getBlock();
                            if (block != Blocks.emerald_block && block != Blocks.gold_block && block != Blocks.diamond_block && block != Blocks.iron_block) {
                                n3 = "".length();
                                "".length();
                                if (2 == -1) {
                                    throw null;
                                }
                                break;
                            }
                            else {
                                ++k;
                            }
                        }
                        ++n4;
                    }
                    if (n3 == 0) {
                        "".length();
                        if (2 < 1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        this.levels = j++;
                    }
                }
            }
            if (this.levels == 0) {
                this.isComplete = ("".length() != 0);
            }
        }
        if (!this.worldObj.isRemote && this.levels == (0x89 ^ 0x8D) && levels < this.levels) {
            final Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityPlayer>)EntityPlayer.class, new AxisAlignedBB(x, y, z, x, y - (0xA ^ 0xE), z).expand(10.0, 5.0, 10.0)).iterator();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().triggerAchievement(AchievementList.fullBeacon);
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int n, final int n2) {
        if (n == " ".length()) {
            this.updateBeacon();
            return " ".length() != 0;
        }
        return super.receiveClientEvent(n, n2);
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        if (itemStack.getItem() != Items.emerald && itemStack.getItem() != Items.diamond && itemStack.getItem() != Items.gold_ingot && itemStack.getItem() != Items.iron_ingot) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, "   ".length(), nbtTagCompound);
    }
    
    @Override
    public void setField(final int n, final int levels) {
        switch (n) {
            case 0: {
                this.levels = levels;
                "".length();
                if (0 == -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                this.primaryEffect = this.func_183001_h(levels);
                "".length();
                if (0 == -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.secondaryEffect = this.func_183001_h(levels);
                break;
            }
        }
    }
    
    private static void I() {
        (I = new String[0xBB ^ 0xB3])["".length()] = I("\u0014; (760", "DIIEV");
        TileEntityBeacon.I[" ".length()] = I("\u0004$\u0014\r;3 \u0005\u001b", "WAwbU");
        TileEntityBeacon.I["  ".length()] = I("*\u0011\u0010#>\u0015", "ftfFR");
        TileEntityBeacon.I["   ".length()] = I("\u0007?>\u001b$%4", "WMWvE");
        TileEntityBeacon.I[0x10 ^ 0x14] = I("\u0017\u001d07< \u0019!!", "DxSXR");
        TileEntityBeacon.I[0x7A ^ 0x7F] = I("=\"\u001e6\u000e\u0002", "qGhSb");
        TileEntityBeacon.I[0xB8 ^ 0xBE] = I("+%$!\u0016!$/'Y*/+6\u0018&", "HJJUw");
        TileEntityBeacon.I[0x42 ^ 0x45] = I("&1>.'996?~)=1(+%", "KXPKD");
    }
    
    @Override
    public void update() {
        if (this.worldObj.getTotalWorldTime() % 80L == 0L) {
            this.updateBeacon();
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(TileEntityBeacon.I["   ".length()], this.primaryEffect);
        nbtTagCompound.setInteger(TileEntityBeacon.I[0x8B ^ 0x8F], this.secondaryEffect);
        nbtTagCompound.setInteger(TileEntityBeacon.I[0x8 ^ 0xD], this.levels);
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (n == 0 && this.payment != null) {
            final ItemStack payment = this.payment;
            this.payment = null;
            return payment;
        }
        return null;
    }
    
    @Override
    public void clear() {
        this.payment = null;
    }
    
    @Override
    public boolean hasCustomName() {
        if (this.customName != null && this.customName.length() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerBeacon(inventoryPlayer, this);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.primaryEffect = this.func_183001_h(nbtTagCompound.getInteger(TileEntityBeacon.I["".length()]));
        this.secondaryEffect = this.func_183001_h(nbtTagCompound.getInteger(TileEntityBeacon.I[" ".length()]));
        this.levels = nbtTagCompound.getInteger(TileEntityBeacon.I["  ".length()]);
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        ItemStack payment;
        if (n == 0) {
            payment = this.payment;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            payment = null;
        }
        return payment;
    }
    
    @Override
    public int getFieldCount() {
        return "   ".length();
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getTileEntity(this.pos) != this) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack payment) {
        if (n == 0) {
            this.payment = payment;
        }
    }
    
    public void updateBeacon() {
        this.updateSegmentColors();
        this.addEffectsToPlayers();
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return " ".length();
    }
    
    static {
        I();
        final Potion[][] effectsList2 = new Potion[0xA0 ^ 0xA4][];
        final int length = "".length();
        final Potion[] array = new Potion["  ".length()];
        array["".length()] = Potion.moveSpeed;
        array[" ".length()] = Potion.digSpeed;
        effectsList2[length] = array;
        final int length2 = " ".length();
        final Potion[] array2 = new Potion["  ".length()];
        array2["".length()] = Potion.resistance;
        array2[" ".length()] = Potion.jump;
        effectsList2[length2] = array2;
        final int length3 = "  ".length();
        final Potion[] array3 = new Potion[" ".length()];
        array3["".length()] = Potion.damageBoost;
        effectsList2[length3] = array3;
        final int length4 = "   ".length();
        final Potion[] array4 = new Potion[" ".length()];
        array4["".length()] = Potion.regeneration;
        effectsList2[length4] = array4;
        effectsList = effectsList2;
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.levels;
            }
            case 1: {
                return this.primaryEffect;
            }
            case 2: {
                return this.secondaryEffect;
            }
            default: {
                return "".length();
            }
        }
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    private void addEffectsToPlayers() {
        if (this.isComplete && this.levels > 0 && !this.worldObj.isRemote && this.primaryEffect > 0) {
            final double n = this.levels * (0x1E ^ 0x14) + (0xB4 ^ 0xBE);
            int n2 = "".length();
            if (this.levels >= (0x6E ^ 0x6A) && this.primaryEffect == this.secondaryEffect) {
                n2 = " ".length();
            }
            final int x = this.pos.getX();
            final int y = this.pos.getY();
            final int z = this.pos.getZ();
            final List<Entity> entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityPlayer.class, new AxisAlignedBB(x, y, z, x + " ".length(), y + " ".length(), z + " ".length()).expand(n, n, n).addCoord(0.0, this.worldObj.getHeight(), 0.0));
            final Iterator<EntityPlayer> iterator = entitiesWithinAABB.iterator();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().addPotionEffect(new PotionEffect(this.primaryEffect, 117 + 41 - 58 + 80, n2, (boolean)(" ".length() != 0), (boolean)(" ".length() != 0)));
            }
            if (this.levels >= (0x1F ^ 0x1B) && this.primaryEffect != this.secondaryEffect && this.secondaryEffect > 0) {
                final Iterator<EntityPlayer> iterator2 = entitiesWithinAABB.iterator();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    iterator2.next().addPotionEffect(new PotionEffect(this.secondaryEffect, 72 + 8 - 70 + 170, "".length(), (boolean)(" ".length() != 0), (boolean)(" ".length() != 0)));
                }
            }
        }
    }
    
    public float shouldBeamRender() {
        if (!this.isComplete) {
            return 0.0f;
        }
        final int n = (int)(this.worldObj.getTotalWorldTime() - this.beamRenderCounter);
        this.beamRenderCounter = this.worldObj.getTotalWorldTime();
        if (n > " ".length()) {
            this.field_146014_j -= n / 40.0f;
            if (this.field_146014_j < 0.0f) {
                this.field_146014_j = 0.0f;
            }
        }
        this.field_146014_j += 0.025f;
        if (this.field_146014_j > 1.0f) {
            this.field_146014_j = 1.0f;
        }
        return this.field_146014_j;
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            customName = TileEntityBeacon.I[0x96 ^ 0x90];
        }
        return customName;
    }
    
    @Override
    public String getGuiID() {
        return TileEntityBeacon.I[0x6 ^ 0x1];
    }
    
    public void setName(final String customName) {
        this.customName = customName;
    }
    
    public static class BeamSegment
    {
        private int height;
        private final float[] colors;
        
        protected void incrementHeight() {
            this.height += " ".length();
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
                if (4 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public float[] getColors() {
            return this.colors;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public BeamSegment(final float[] colors) {
            this.colors = colors;
            this.height = " ".length();
        }
    }
}
