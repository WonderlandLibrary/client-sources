package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public abstract class StructureStart
{
    protected LinkedList<StructureComponent> components;
    private int chunkPosZ;
    protected StructureBoundingBox boundingBox;
    private static final String[] I;
    private int chunkPosX;
    
    public int getChunkPosX() {
        return this.chunkPosX;
    }
    
    protected void setRandomHeight(final World world, final Random random, final int n, final int n2) {
        final int n3 = n2 - n + " ".length() - this.boundingBox.getYSize();
        " ".length();
        int n4;
        if (n3 > " ".length()) {
            n4 = n + random.nextInt(n3);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        final int n5 = n4 - this.boundingBox.minY;
        this.boundingBox.offset("".length(), n5, "".length());
        final Iterator<StructureComponent> iterator = this.components.iterator();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().func_181138_a("".length(), n5, "".length());
        }
    }
    
    public LinkedList<StructureComponent> getComponents() {
        return this.components;
    }
    
    public boolean func_175788_a(final ChunkCoordIntPair chunkCoordIntPair) {
        return " ".length() != 0;
    }
    
    public StructureStart(final int chunkPosX, final int chunkPosZ) {
        this.components = new LinkedList<StructureComponent>();
        this.chunkPosX = chunkPosX;
        this.chunkPosZ = chunkPosZ;
    }
    
    public void func_175787_b(final ChunkCoordIntPair chunkCoordIntPair) {
    }
    
    public NBTTagCompound writeStructureComponentsToNBT(final int n, final int n2) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString(StructureStart.I["".length()], MapGenStructureIO.getStructureStartName(this));
        nbtTagCompound.setInteger(StructureStart.I[" ".length()], n);
        nbtTagCompound.setInteger(StructureStart.I["  ".length()], n2);
        nbtTagCompound.setTag(StructureStart.I["   ".length()], this.boundingBox.toNBTTagIntArray());
        final NBTTagList list = new NBTTagList();
        final Iterator<StructureComponent> iterator = this.components.iterator();
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            list.appendTag(iterator.next().createStructureBaseNBT());
        }
        nbtTagCompound.setTag(StructureStart.I[0x20 ^ 0x24], list);
        this.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }
    
    public int getChunkPosZ() {
        return this.chunkPosZ;
    }
    
    public void readStructureComponentsFromNBT(final World world, final NBTTagCompound nbtTagCompound) {
        this.chunkPosX = nbtTagCompound.getInteger(StructureStart.I[0x3A ^ 0x3F]);
        this.chunkPosZ = nbtTagCompound.getInteger(StructureStart.I[0xA4 ^ 0xA2]);
        if (nbtTagCompound.hasKey(StructureStart.I[0xA8 ^ 0xAF])) {
            this.boundingBox = new StructureBoundingBox(nbtTagCompound.getIntArray(StructureStart.I[0x4B ^ 0x43]));
        }
        final NBTTagList tagList = nbtTagCompound.getTagList(StructureStart.I[0x31 ^ 0x38], 0xB1 ^ 0xBB);
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            this.components.add(MapGenStructureIO.getStructureComponent(tagList.getCompoundTagAt(i), world));
            ++i;
        }
        this.readFromNBT(nbtTagCompound);
    }
    
    static {
        I();
    }
    
    protected void updateBoundingBox() {
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();
        final Iterator<StructureComponent> iterator = this.components.iterator();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.boundingBox.expandTo(iterator.next().getBoundingBox());
        }
    }
    
    public boolean isSizeableStructure() {
        return " ".length() != 0;
    }
    
    protected void markAvailableHeight(final World world, final Random random, final int n) {
        final int n2 = world.func_181545_F() - n;
        int n3 = this.boundingBox.getYSize() + " ".length();
        if (n3 < n2) {
            n3 += random.nextInt(n2 - n3);
        }
        final int n4 = n3 - this.boundingBox.maxY;
        this.boundingBox.offset("".length(), n4, "".length());
        final Iterator<StructureComponent> iterator = this.components.iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().func_181138_a("".length(), n4, "".length());
        }
    }
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public StructureStart() {
        this.components = new LinkedList<StructureComponent>();
    }
    
    public void generateStructure(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
        final Iterator<StructureComponent> iterator = (Iterator<StructureComponent>)this.components.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final StructureComponent structureComponent = iterator.next();
            if (structureComponent.getBoundingBox().intersectsWith(structureBoundingBox) && !structureComponent.addComponentParts(world, random, structureBoundingBox)) {
                iterator.remove();
            }
        }
    }
    
    private static void I() {
        (I = new String[0x6E ^ 0x64])["".length()] = I("- ", "DDteO");
        StructureStart.I[" ".length()] = I(")\u000e07\u00182", "jfEYs");
        StructureStart.I["  ".length()] = I("\u0011\n?\u001f\u0013\b", "RbJqx");
        StructureStart.I["   ".length()] = I("\u0011\u0014", "SVvEv");
        StructureStart.I[0x96 ^ 0x92] = I("\u0015\u0019?\u0004\u0012$\u00148", "VqVhv");
        StructureStart.I[0x6E ^ 0x6B] = I("(\u000e\u001d\u001e\u00063", "kfhpm");
        StructureStart.I[0x37 ^ 0x31] = I("\u0011#\u0011\u001e\u000e\b", "RKdpe");
        StructureStart.I[0x4A ^ 0x4D] = I("\n!", "HctOY");
        StructureStart.I[0xBF ^ 0xB7] = I("\u00145", "VwGwd");
        StructureStart.I[0x55 ^ 0x5C] = I("\"\u001f'\u0001#\u0013\u0012 ", "awNmG");
    }
}
