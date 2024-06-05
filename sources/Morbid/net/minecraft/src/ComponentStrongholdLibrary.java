package net.minecraft.src;

import java.util.*;

public class ComponentStrongholdLibrary extends ComponentStronghold
{
    private static final WeightedRandomChestContent[] strongholdLibraryChestContents;
    protected final EnumDoor doorType;
    private final boolean isLargeRoom;
    
    static {
        strongholdLibraryChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.book.itemID, 0, 1, 3, 20), new WeightedRandomChestContent(Item.paper.itemID, 0, 2, 7, 20), new WeightedRandomChestContent(Item.emptyMap.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Item.compass.itemID, 0, 1, 1, 1) };
    }
    
    public ComponentStrongholdLibrary(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.doorType = this.getRandomDoor(par2Random);
        this.boundingBox = par3StructureBoundingBox;
        this.isLargeRoom = (par3StructureBoundingBox.getYSize() > 6);
    }
    
    public static ComponentStrongholdLibrary findValidPlacement(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 11, 15, par5);
        if (!ComponentStronghold.canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(par0List, var7) != null) {
            var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 14, 6, 15, par5);
            if (!ComponentStronghold.canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(par0List, var7) != null) {
                return null;
            }
        }
        return new ComponentStrongholdLibrary(par6, par1Random, var7, par5);
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        }
        byte var4 = 11;
        if (!this.isLargeRoom) {
            var4 = 6;
        }
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 13, var4 - 1, 14, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.doorType, 4, 1, 0);
        this.randomlyFillWithBlocks(par1World, par3StructureBoundingBox, par2Random, 0.07f, 2, 1, 1, 11, 4, 13, Block.web.blockID, Block.web.blockID, false);
        for (int var5 = 1; var5 <= 13; ++var5) {
            if ((var5 - 1) % 4 == 0) {
                this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, var5, 1, 4, var5, Block.planks.blockID, Block.planks.blockID, false);
                this.fillWithBlocks(par1World, par3StructureBoundingBox, 12, 1, var5, 12, 4, var5, Block.planks.blockID, Block.planks.blockID, false);
                this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 3, var5, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 11, 3, var5, par3StructureBoundingBox);
                if (this.isLargeRoom) {
                    this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 6, var5, 1, 9, var5, Block.planks.blockID, Block.planks.blockID, false);
                    this.fillWithBlocks(par1World, par3StructureBoundingBox, 12, 6, var5, 12, 9, var5, Block.planks.blockID, Block.planks.blockID, false);
                }
            }
            else {
                this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, var5, 1, 4, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
                this.fillWithBlocks(par1World, par3StructureBoundingBox, 12, 1, var5, 12, 4, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
                if (this.isLargeRoom) {
                    this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 6, var5, 1, 9, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
                    this.fillWithBlocks(par1World, par3StructureBoundingBox, 12, 6, var5, 12, 9, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
                }
            }
        }
        for (int var5 = 3; var5 < 12; var5 += 2) {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, var5, 4, 3, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 6, 1, var5, 7, 3, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 9, 1, var5, 10, 3, var5, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
        }
        if (this.isLargeRoom) {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 3, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 10, 5, 1, 12, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 1, 9, 5, 2, Block.planks.blockID, Block.planks.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 12, 9, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
            this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 9, 5, 11, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 5, 11, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 9, 5, 10, par3StructureBoundingBox);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 6, 2, 3, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 10, 6, 2, 10, 6, 10, Block.fence.blockID, Block.fence.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 6, 2, 9, 6, 2, Block.fence.blockID, Block.fence.blockID, false);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 6, 12, 8, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 9, 6, 11, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 8, 6, 11, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 9, 6, 10, par3StructureBoundingBox);
            final int var5 = this.getMetadataWithOffset(Block.ladder.blockID, 3);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 1, 13, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 2, 13, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 3, 13, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 4, 13, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 5, 13, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 6, 13, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, var5, 10, 7, 13, par3StructureBoundingBox);
            final byte var6 = 7;
            final byte var7 = 7;
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 - 1, 9, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6, 9, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 - 1, 8, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6, 8, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 - 1, 7, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6, 7, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 - 2, 7, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 + 1, 7, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 - 1, 7, var7 - 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6 - 1, 7, var7 + 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6, 7, var7 - 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, var6, 7, var7 + 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, var6 - 2, 8, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, var6 + 1, 8, var7, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, var6 - 1, 8, var7 - 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, var6 - 1, 8, var7 + 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, var6, 8, var7 - 1, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, var6, 8, var7 + 1, par3StructureBoundingBox);
        }
        this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 3, 5, WeightedRandomChestContent.func_92080_a(ComponentStrongholdLibrary.strongholdLibraryChestContents, Item.enchantedBook.func_92112_a(par2Random, 1, 5, 2)), 1 + par2Random.nextInt(4));
        if (this.isLargeRoom) {
            this.placeBlockAtCurrentPosition(par1World, 0, 0, 12, 9, 1, par3StructureBoundingBox);
            this.generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 12, 8, 1, WeightedRandomChestContent.func_92080_a(ComponentStrongholdLibrary.strongholdLibraryChestContents, Item.enchantedBook.func_92112_a(par2Random, 1, 5, 2)), 1 + par2Random.nextInt(4));
        }
        return true;
    }
}
