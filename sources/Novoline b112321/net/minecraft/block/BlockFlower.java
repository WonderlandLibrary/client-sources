package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.Block$EnumOffsetType;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower$EnumFlowerColor;
import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class BlockFlower extends BlockBush {
   protected PropertyEnum type;

   protected BlockFlower() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(this.getTypeProperty(), this.getBlockType() == BlockFlower$EnumFlowerColor.RED?BlockFlower$EnumFlowerType.POPPY:BlockFlower$EnumFlowerType.DANDELION));
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockFlower$EnumFlowerType)var1.getValue(this.getTypeProperty())).getMeta();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockFlower$EnumFlowerType var7 : BlockFlower$EnumFlowerType.getTypes(this.getBlockType())) {
         var3.add(new ItemStack(var1, 1, var7.getMeta()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(this.getTypeProperty(), BlockFlower$EnumFlowerType.getType(this.getBlockType(), var1));
   }

   public abstract BlockFlower$EnumFlowerColor getBlockType();

   public IProperty getTypeProperty() {
      if(this.type == null) {
         this.type = PropertyEnum.create("type", BlockFlower$EnumFlowerType.class, this::lambda$getTypeProperty$0);
      }

      return this.type;
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockFlower$EnumFlowerType)var1.getValue(this.getTypeProperty())).getMeta();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{this.getTypeProperty()});
   }

   public Block$EnumOffsetType getOffsetType() {
      return Block$EnumOffsetType.XZ;
   }

   private boolean lambda$getTypeProperty$0(BlockFlower$EnumFlowerType var1) {
      return var1.getBlockType() == this.getBlockType();
   }
}
