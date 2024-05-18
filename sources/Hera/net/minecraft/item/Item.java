/*      */ package net.minecraft.item;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockWall;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.RegistryNamespaced;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Item
/*      */ {
/*      */   public Item() {
/*   60 */     this.maxStackSize = 64;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final RegistryNamespaced<ResourceLocation, Item> itemRegistry = new RegistryNamespaced();
/*      */   
/*      */   private static final Map<Block, Item> BLOCK_TO_ITEM = Maps.newHashMap();
/*      */   
/*      */   protected static final UUID itemModifierUUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*      */   
/*      */   private CreativeTabs tabToDisplayOn;
/*      */   
/*      */   protected static Random itemRand = new Random();
/*      */   
/*      */   protected int maxStackSize;
/*      */   
/*      */   private int maxDamage;
/*      */   protected boolean bFull3D;
/*      */   protected boolean hasSubtypes;
/*      */   private Item containerItem;
/*      */   private String potionEffect;
/*      */   private String unlocalizedName;
/*      */   
/*      */   public static int getIdFromItem(Item itemIn) {
/*   84 */     return (itemIn == null) ? 0 : itemRegistry.getIDForObject(itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemById(int id) {
/*   89 */     return (Item)itemRegistry.getObjectById(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemFromBlock(Block blockIn) {
/*   94 */     return BLOCK_TO_ITEM.get(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Item getByNameOrId(String id) {
/*  103 */     Item item = (Item)itemRegistry.getObject(new ResourceLocation(id));
/*      */     
/*  105 */     if (item == null) {
/*      */       
/*      */       try {
/*      */         
/*  109 */         return getItemById(Integer.parseInt(id));
/*      */       }
/*  111 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  117 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/*  125 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setMaxStackSize(int maxStackSize) {
/*  130 */     this.maxStackSize = maxStackSize;
/*  131 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  139 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(ItemStack stack, Block block) {
/*  144 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  152 */     return itemStackIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  161 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemStackLimit() {
/*  169 */     return this.maxStackSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetadata(int damage) {
/*  178 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  183 */     return this.hasSubtypes;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item setHasSubtypes(boolean hasSubtypes) {
/*  188 */     this.hasSubtypes = hasSubtypes;
/*  189 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  197 */     return this.maxDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setMaxDamage(int maxDamageIn) {
/*  205 */     this.maxDamage = maxDamageIn;
/*  206 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDamageable() {
/*  211 */     return (this.maxDamage > 0 && !this.hasSubtypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  220 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  228 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockIn) {
/*  236 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/*  244 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setFull3D() {
/*  252 */     this.bFull3D = true;
/*  253 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFull3D() {
/*  261 */     return this.bFull3D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldRotateAroundWhenRendering() {
/*  270 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setUnlocalizedName(String unlocalizedName) {
/*  278 */     this.unlocalizedName = unlocalizedName;
/*  279 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedNameInefficiently(ItemStack stack) {
/*  288 */     String s = getUnlocalizedName(stack);
/*  289 */     return (s == null) ? "" : StatCollector.translateToLocal(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  297 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName(ItemStack stack) {
/*  306 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setContainerItem(Item containerItem) {
/*  311 */     this.containerItem = containerItem;
/*  312 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getShareTag() {
/*  320 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item getContainerItem() {
/*  325 */     return this.containerItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasContainerItem() {
/*  333 */     return (this.containerItem != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  338 */     return 16777215;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMap() {
/*  361 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction(ItemStack stack) {
/*  369 */     return EnumAction.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration(ItemStack stack) {
/*  377 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setPotionEffect(String potionEffect) {
/*  392 */     this.potionEffect = potionEffect;
/*  393 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPotionEffect(ItemStack stack) {
/*  398 */     return this.potionEffect;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionIngredient(ItemStack stack) {
/*  403 */     return (getPotionEffect(stack) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemStackDisplayName(ItemStack stack) {
/*  415 */     return StatCollector.translateToLocal(String.valueOf(getUnlocalizedNameInefficiently(stack)) + ".name").trim();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect(ItemStack stack) {
/*  420 */     return stack.isItemEnchanted();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity(ItemStack stack) {
/*  428 */     return stack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemTool(ItemStack stack) {
/*  436 */     return (getItemStackLimit() == 1 && isDamageable());
/*      */   }
/*      */ 
/*      */   
/*      */   protected MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
/*  441 */     float f = playerIn.rotationPitch;
/*  442 */     float f1 = playerIn.rotationYaw;
/*  443 */     double d0 = playerIn.posX;
/*  444 */     double d1 = playerIn.posY + playerIn.getEyeHeight();
/*  445 */     double d2 = playerIn.posZ;
/*  446 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/*  447 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/*  448 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/*  449 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/*  450 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/*  451 */     float f6 = f3 * f4;
/*  452 */     float f7 = f2 * f4;
/*  453 */     double d3 = 5.0D;
/*  454 */     Vec3 vec31 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
/*  455 */     return worldIn.rayTraceBlocks(vec3, vec31, useLiquids, !useLiquids, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemEnchantability() {
/*  463 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/*  471 */     subItems.add(new ItemStack(itemIn, 1, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTab() {
/*  479 */     return this.tabToDisplayOn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setCreativeTab(CreativeTabs tab) {
/*  487 */     this.tabToDisplayOn = tab;
/*  488 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canItemEditBlocks() {
/*  497 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/*  505 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/*  510 */     return (Multimap<String, AttributeModifier>)HashMultimap.create();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerItems() {
/*  515 */     registerItemBlock(Blocks.stone, (new ItemMultiTexture(Blocks.stone, Blocks.stone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  519 */               return BlockStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  521 */           })).setUnlocalizedName("stone"));
/*  522 */     registerItemBlock((Block)Blocks.grass, new ItemColored((Block)Blocks.grass, false));
/*  523 */     registerItemBlock(Blocks.dirt, (new ItemMultiTexture(Blocks.dirt, Blocks.dirt, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  527 */               return BlockDirt.DirtType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  529 */           })).setUnlocalizedName("dirt"));
/*  530 */     registerItemBlock(Blocks.cobblestone);
/*  531 */     registerItemBlock(Blocks.planks, (new ItemMultiTexture(Blocks.planks, Blocks.planks, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  535 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  537 */           })).setUnlocalizedName("wood"));
/*  538 */     registerItemBlock(Blocks.sapling, (new ItemMultiTexture(Blocks.sapling, Blocks.sapling, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  542 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  544 */           })).setUnlocalizedName("sapling"));
/*  545 */     registerItemBlock(Blocks.bedrock);
/*  546 */     registerItemBlock((Block)Blocks.sand, (new ItemMultiTexture((Block)Blocks.sand, (Block)Blocks.sand, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  550 */               return BlockSand.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  552 */           })).setUnlocalizedName("sand"));
/*  553 */     registerItemBlock(Blocks.gravel);
/*  554 */     registerItemBlock(Blocks.gold_ore);
/*  555 */     registerItemBlock(Blocks.iron_ore);
/*  556 */     registerItemBlock(Blocks.coal_ore);
/*  557 */     registerItemBlock(Blocks.log, (new ItemMultiTexture(Blocks.log, Blocks.log, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  561 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  563 */           })).setUnlocalizedName("log"));
/*  564 */     registerItemBlock(Blocks.log2, (new ItemMultiTexture(Blocks.log2, Blocks.log2, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  568 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata() + 4).getUnlocalizedName();
/*      */             }
/*  570 */           })).setUnlocalizedName("log"));
/*  571 */     registerItemBlock((Block)Blocks.leaves, (new ItemLeaves(Blocks.leaves)).setUnlocalizedName("leaves"));
/*  572 */     registerItemBlock((Block)Blocks.leaves2, (new ItemLeaves(Blocks.leaves2)).setUnlocalizedName("leaves"));
/*  573 */     registerItemBlock(Blocks.sponge, (new ItemMultiTexture(Blocks.sponge, Blocks.sponge, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  577 */               return ((p_apply_1_.getMetadata() & 0x1) == 1) ? "wet" : "dry";
/*      */             }
/*  579 */           })).setUnlocalizedName("sponge"));
/*  580 */     registerItemBlock(Blocks.glass);
/*  581 */     registerItemBlock(Blocks.lapis_ore);
/*  582 */     registerItemBlock(Blocks.lapis_block);
/*  583 */     registerItemBlock(Blocks.dispenser);
/*  584 */     registerItemBlock(Blocks.sandstone, (new ItemMultiTexture(Blocks.sandstone, Blocks.sandstone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  588 */               return BlockSandStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  590 */           })).setUnlocalizedName("sandStone"));
/*  591 */     registerItemBlock(Blocks.noteblock);
/*  592 */     registerItemBlock(Blocks.golden_rail);
/*  593 */     registerItemBlock(Blocks.detector_rail);
/*  594 */     registerItemBlock((Block)Blocks.sticky_piston, new ItemPiston((Block)Blocks.sticky_piston));
/*  595 */     registerItemBlock(Blocks.web);
/*  596 */     registerItemBlock((Block)Blocks.tallgrass, (new ItemColored((Block)Blocks.tallgrass, true)).setSubtypeNames(new String[] { "shrub", "grass", "fern" }));
/*  597 */     registerItemBlock((Block)Blocks.deadbush);
/*  598 */     registerItemBlock((Block)Blocks.piston, new ItemPiston((Block)Blocks.piston));
/*  599 */     registerItemBlock(Blocks.wool, (new ItemCloth(Blocks.wool)).setUnlocalizedName("cloth"));
/*  600 */     registerItemBlock((Block)Blocks.yellow_flower, (new ItemMultiTexture((Block)Blocks.yellow_flower, (Block)Blocks.yellow_flower, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  604 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.YELLOW, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  606 */           })).setUnlocalizedName("flower"));
/*  607 */     registerItemBlock((Block)Blocks.red_flower, (new ItemMultiTexture((Block)Blocks.red_flower, (Block)Blocks.red_flower, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  611 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  613 */           })).setUnlocalizedName("rose"));
/*  614 */     registerItemBlock((Block)Blocks.brown_mushroom);
/*  615 */     registerItemBlock((Block)Blocks.red_mushroom);
/*  616 */     registerItemBlock(Blocks.gold_block);
/*  617 */     registerItemBlock(Blocks.iron_block);
/*  618 */     registerItemBlock((Block)Blocks.stone_slab, (new ItemSlab((Block)Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab)).setUnlocalizedName("stoneSlab"));
/*  619 */     registerItemBlock(Blocks.brick_block);
/*  620 */     registerItemBlock(Blocks.tnt);
/*  621 */     registerItemBlock(Blocks.bookshelf);
/*  622 */     registerItemBlock(Blocks.mossy_cobblestone);
/*  623 */     registerItemBlock(Blocks.obsidian);
/*  624 */     registerItemBlock(Blocks.torch);
/*  625 */     registerItemBlock(Blocks.mob_spawner);
/*  626 */     registerItemBlock(Blocks.oak_stairs);
/*  627 */     registerItemBlock((Block)Blocks.chest);
/*  628 */     registerItemBlock(Blocks.diamond_ore);
/*  629 */     registerItemBlock(Blocks.diamond_block);
/*  630 */     registerItemBlock(Blocks.crafting_table);
/*  631 */     registerItemBlock(Blocks.farmland);
/*  632 */     registerItemBlock(Blocks.furnace);
/*  633 */     registerItemBlock(Blocks.lit_furnace);
/*  634 */     registerItemBlock(Blocks.ladder);
/*  635 */     registerItemBlock(Blocks.rail);
/*  636 */     registerItemBlock(Blocks.stone_stairs);
/*  637 */     registerItemBlock(Blocks.lever);
/*  638 */     registerItemBlock(Blocks.stone_pressure_plate);
/*  639 */     registerItemBlock(Blocks.wooden_pressure_plate);
/*  640 */     registerItemBlock(Blocks.redstone_ore);
/*  641 */     registerItemBlock(Blocks.redstone_torch);
/*  642 */     registerItemBlock(Blocks.stone_button);
/*  643 */     registerItemBlock(Blocks.snow_layer, new ItemSnow(Blocks.snow_layer));
/*  644 */     registerItemBlock(Blocks.ice);
/*  645 */     registerItemBlock(Blocks.snow);
/*  646 */     registerItemBlock((Block)Blocks.cactus);
/*  647 */     registerItemBlock(Blocks.clay);
/*  648 */     registerItemBlock(Blocks.jukebox);
/*  649 */     registerItemBlock(Blocks.oak_fence);
/*  650 */     registerItemBlock(Blocks.spruce_fence);
/*  651 */     registerItemBlock(Blocks.birch_fence);
/*  652 */     registerItemBlock(Blocks.jungle_fence);
/*  653 */     registerItemBlock(Blocks.dark_oak_fence);
/*  654 */     registerItemBlock(Blocks.acacia_fence);
/*  655 */     registerItemBlock(Blocks.pumpkin);
/*  656 */     registerItemBlock(Blocks.netherrack);
/*  657 */     registerItemBlock(Blocks.soul_sand);
/*  658 */     registerItemBlock(Blocks.glowstone);
/*  659 */     registerItemBlock(Blocks.lit_pumpkin);
/*  660 */     registerItemBlock(Blocks.trapdoor);
/*  661 */     registerItemBlock(Blocks.monster_egg, (new ItemMultiTexture(Blocks.monster_egg, Blocks.monster_egg, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  665 */               return BlockSilverfish.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  667 */           })).setUnlocalizedName("monsterStoneEgg"));
/*  668 */     registerItemBlock(Blocks.stonebrick, (new ItemMultiTexture(Blocks.stonebrick, Blocks.stonebrick, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  672 */               return BlockStoneBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  674 */           })).setUnlocalizedName("stonebricksmooth"));
/*  675 */     registerItemBlock(Blocks.brown_mushroom_block);
/*  676 */     registerItemBlock(Blocks.red_mushroom_block);
/*  677 */     registerItemBlock(Blocks.iron_bars);
/*  678 */     registerItemBlock(Blocks.glass_pane);
/*  679 */     registerItemBlock(Blocks.melon_block);
/*  680 */     registerItemBlock(Blocks.vine, new ItemColored(Blocks.vine, false));
/*  681 */     registerItemBlock(Blocks.oak_fence_gate);
/*  682 */     registerItemBlock(Blocks.spruce_fence_gate);
/*  683 */     registerItemBlock(Blocks.birch_fence_gate);
/*  684 */     registerItemBlock(Blocks.jungle_fence_gate);
/*  685 */     registerItemBlock(Blocks.dark_oak_fence_gate);
/*  686 */     registerItemBlock(Blocks.acacia_fence_gate);
/*  687 */     registerItemBlock(Blocks.brick_stairs);
/*  688 */     registerItemBlock(Blocks.stone_brick_stairs);
/*  689 */     registerItemBlock((Block)Blocks.mycelium);
/*  690 */     registerItemBlock(Blocks.waterlily, new ItemLilyPad(Blocks.waterlily));
/*  691 */     registerItemBlock(Blocks.nether_brick);
/*  692 */     registerItemBlock(Blocks.nether_brick_fence);
/*  693 */     registerItemBlock(Blocks.nether_brick_stairs);
/*  694 */     registerItemBlock(Blocks.enchanting_table);
/*  695 */     registerItemBlock(Blocks.end_portal_frame);
/*  696 */     registerItemBlock(Blocks.end_stone);
/*  697 */     registerItemBlock(Blocks.dragon_egg);
/*  698 */     registerItemBlock(Blocks.redstone_lamp);
/*  699 */     registerItemBlock((Block)Blocks.wooden_slab, (new ItemSlab((Block)Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab)).setUnlocalizedName("woodSlab"));
/*  700 */     registerItemBlock(Blocks.sandstone_stairs);
/*  701 */     registerItemBlock(Blocks.emerald_ore);
/*  702 */     registerItemBlock(Blocks.ender_chest);
/*  703 */     registerItemBlock((Block)Blocks.tripwire_hook);
/*  704 */     registerItemBlock(Blocks.emerald_block);
/*  705 */     registerItemBlock(Blocks.spruce_stairs);
/*  706 */     registerItemBlock(Blocks.birch_stairs);
/*  707 */     registerItemBlock(Blocks.jungle_stairs);
/*  708 */     registerItemBlock(Blocks.command_block);
/*  709 */     registerItemBlock((Block)Blocks.beacon);
/*  710 */     registerItemBlock(Blocks.cobblestone_wall, (new ItemMultiTexture(Blocks.cobblestone_wall, Blocks.cobblestone_wall, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  714 */               return BlockWall.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  716 */           })).setUnlocalizedName("cobbleWall"));
/*  717 */     registerItemBlock(Blocks.wooden_button);
/*  718 */     registerItemBlock(Blocks.anvil, (new ItemAnvilBlock(Blocks.anvil)).setUnlocalizedName("anvil"));
/*  719 */     registerItemBlock(Blocks.trapped_chest);
/*  720 */     registerItemBlock(Blocks.light_weighted_pressure_plate);
/*  721 */     registerItemBlock(Blocks.heavy_weighted_pressure_plate);
/*  722 */     registerItemBlock((Block)Blocks.daylight_detector);
/*  723 */     registerItemBlock(Blocks.redstone_block);
/*  724 */     registerItemBlock(Blocks.quartz_ore);
/*  725 */     registerItemBlock((Block)Blocks.hopper);
/*  726 */     registerItemBlock(Blocks.quartz_block, (new ItemMultiTexture(Blocks.quartz_block, Blocks.quartz_block, new String[] { "default", "chiseled", "lines" })).setUnlocalizedName("quartzBlock"));
/*  727 */     registerItemBlock(Blocks.quartz_stairs);
/*  728 */     registerItemBlock(Blocks.activator_rail);
/*  729 */     registerItemBlock(Blocks.dropper);
/*  730 */     registerItemBlock(Blocks.stained_hardened_clay, (new ItemCloth(Blocks.stained_hardened_clay)).setUnlocalizedName("clayHardenedStained"));
/*  731 */     registerItemBlock(Blocks.barrier);
/*  732 */     registerItemBlock(Blocks.iron_trapdoor);
/*  733 */     registerItemBlock(Blocks.hay_block);
/*  734 */     registerItemBlock(Blocks.carpet, (new ItemCloth(Blocks.carpet)).setUnlocalizedName("woolCarpet"));
/*  735 */     registerItemBlock(Blocks.hardened_clay);
/*  736 */     registerItemBlock(Blocks.coal_block);
/*  737 */     registerItemBlock(Blocks.packed_ice);
/*  738 */     registerItemBlock(Blocks.acacia_stairs);
/*  739 */     registerItemBlock(Blocks.dark_oak_stairs);
/*  740 */     registerItemBlock(Blocks.slime_block);
/*  741 */     registerItemBlock((Block)Blocks.double_plant, (new ItemDoublePlant((Block)Blocks.double_plant, (Block)Blocks.double_plant, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  745 */               return BlockDoublePlant.EnumPlantType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  747 */           })).setUnlocalizedName("doublePlant"));
/*  748 */     registerItemBlock((Block)Blocks.stained_glass, (new ItemCloth((Block)Blocks.stained_glass)).setUnlocalizedName("stainedGlass"));
/*  749 */     registerItemBlock((Block)Blocks.stained_glass_pane, (new ItemCloth((Block)Blocks.stained_glass_pane)).setUnlocalizedName("stainedGlassPane"));
/*  750 */     registerItemBlock(Blocks.prismarine, (new ItemMultiTexture(Blocks.prismarine, Blocks.prismarine, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  754 */               return BlockPrismarine.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  756 */           })).setUnlocalizedName("prismarine"));
/*  757 */     registerItemBlock(Blocks.sea_lantern);
/*  758 */     registerItemBlock(Blocks.red_sandstone, (new ItemMultiTexture(Blocks.red_sandstone, Blocks.red_sandstone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  762 */               return BlockRedSandstone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  764 */           })).setUnlocalizedName("redSandStone"));
/*  765 */     registerItemBlock(Blocks.red_sandstone_stairs);
/*  766 */     registerItemBlock((Block)Blocks.stone_slab2, (new ItemSlab((Block)Blocks.stone_slab2, Blocks.stone_slab2, Blocks.double_stone_slab2)).setUnlocalizedName("stoneSlab2"));
/*  767 */     registerItem(256, "iron_shovel", (new ItemSpade(ToolMaterial.IRON)).setUnlocalizedName("shovelIron"));
/*  768 */     registerItem(257, "iron_pickaxe", (new ItemPickaxe(ToolMaterial.IRON)).setUnlocalizedName("pickaxeIron"));
/*  769 */     registerItem(258, "iron_axe", (new ItemAxe(ToolMaterial.IRON)).setUnlocalizedName("hatchetIron"));
/*  770 */     registerItem(259, "flint_and_steel", (new ItemFlintAndSteel()).setUnlocalizedName("flintAndSteel"));
/*  771 */     registerItem(260, "apple", (new ItemFood(4, 0.3F, false)).setUnlocalizedName("apple"));
/*  772 */     registerItem(261, "bow", (new ItemBow()).setUnlocalizedName("bow"));
/*  773 */     registerItem(262, "arrow", (new Item()).setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat));
/*  774 */     registerItem(263, "coal", (new ItemCoal()).setUnlocalizedName("coal"));
/*  775 */     registerItem(264, "diamond", (new Item()).setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials));
/*  776 */     registerItem(265, "iron_ingot", (new Item()).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials));
/*  777 */     registerItem(266, "gold_ingot", (new Item()).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials));
/*  778 */     registerItem(267, "iron_sword", (new ItemSword(ToolMaterial.IRON)).setUnlocalizedName("swordIron"));
/*  779 */     registerItem(268, "wooden_sword", (new ItemSword(ToolMaterial.WOOD)).setUnlocalizedName("swordWood"));
/*  780 */     registerItem(269, "wooden_shovel", (new ItemSpade(ToolMaterial.WOOD)).setUnlocalizedName("shovelWood"));
/*  781 */     registerItem(270, "wooden_pickaxe", (new ItemPickaxe(ToolMaterial.WOOD)).setUnlocalizedName("pickaxeWood"));
/*  782 */     registerItem(271, "wooden_axe", (new ItemAxe(ToolMaterial.WOOD)).setUnlocalizedName("hatchetWood"));
/*  783 */     registerItem(272, "stone_sword", (new ItemSword(ToolMaterial.STONE)).setUnlocalizedName("swordStone"));
/*  784 */     registerItem(273, "stone_shovel", (new ItemSpade(ToolMaterial.STONE)).setUnlocalizedName("shovelStone"));
/*  785 */     registerItem(274, "stone_pickaxe", (new ItemPickaxe(ToolMaterial.STONE)).setUnlocalizedName("pickaxeStone"));
/*  786 */     registerItem(275, "stone_axe", (new ItemAxe(ToolMaterial.STONE)).setUnlocalizedName("hatchetStone"));
/*  787 */     registerItem(276, "diamond_sword", (new ItemSword(ToolMaterial.EMERALD)).setUnlocalizedName("swordDiamond"));
/*  788 */     registerItem(277, "diamond_shovel", (new ItemSpade(ToolMaterial.EMERALD)).setUnlocalizedName("shovelDiamond"));
/*  789 */     registerItem(278, "diamond_pickaxe", (new ItemPickaxe(ToolMaterial.EMERALD)).setUnlocalizedName("pickaxeDiamond"));
/*  790 */     registerItem(279, "diamond_axe", (new ItemAxe(ToolMaterial.EMERALD)).setUnlocalizedName("hatchetDiamond"));
/*  791 */     registerItem(280, "stick", (new Item()).setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials));
/*  792 */     registerItem(281, "bowl", (new Item()).setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials));
/*  793 */     registerItem(282, "mushroom_stew", (new ItemSoup(6)).setUnlocalizedName("mushroomStew"));
/*  794 */     registerItem(283, "golden_sword", (new ItemSword(ToolMaterial.GOLD)).setUnlocalizedName("swordGold"));
/*  795 */     registerItem(284, "golden_shovel", (new ItemSpade(ToolMaterial.GOLD)).setUnlocalizedName("shovelGold"));
/*  796 */     registerItem(285, "golden_pickaxe", (new ItemPickaxe(ToolMaterial.GOLD)).setUnlocalizedName("pickaxeGold"));
/*  797 */     registerItem(286, "golden_axe", (new ItemAxe(ToolMaterial.GOLD)).setUnlocalizedName("hatchetGold"));
/*  798 */     registerItem(287, "string", (new ItemReed(Blocks.tripwire)).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials));
/*  799 */     registerItem(288, "feather", (new Item()).setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials));
/*  800 */     registerItem(289, "gunpowder", (new Item()).setUnlocalizedName("sulphur").setPotionEffect("+14&13-13").setCreativeTab(CreativeTabs.tabMaterials));
/*  801 */     registerItem(290, "wooden_hoe", (new ItemHoe(ToolMaterial.WOOD)).setUnlocalizedName("hoeWood"));
/*  802 */     registerItem(291, "stone_hoe", (new ItemHoe(ToolMaterial.STONE)).setUnlocalizedName("hoeStone"));
/*  803 */     registerItem(292, "iron_hoe", (new ItemHoe(ToolMaterial.IRON)).setUnlocalizedName("hoeIron"));
/*  804 */     registerItem(293, "diamond_hoe", (new ItemHoe(ToolMaterial.EMERALD)).setUnlocalizedName("hoeDiamond"));
/*  805 */     registerItem(294, "golden_hoe", (new ItemHoe(ToolMaterial.GOLD)).setUnlocalizedName("hoeGold"));
/*  806 */     registerItem(295, "wheat_seeds", (new ItemSeeds(Blocks.wheat, Blocks.farmland)).setUnlocalizedName("seeds"));
/*  807 */     registerItem(296, "wheat", (new Item()).setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials));
/*  808 */     registerItem(297, "bread", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("bread"));
/*  809 */     registerItem(298, "leather_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 0)).setUnlocalizedName("helmetCloth"));
/*  810 */     registerItem(299, "leather_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 1)).setUnlocalizedName("chestplateCloth"));
/*  811 */     registerItem(300, "leather_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 2)).setUnlocalizedName("leggingsCloth"));
/*  812 */     registerItem(301, "leather_boots", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 3)).setUnlocalizedName("bootsCloth"));
/*  813 */     registerItem(302, "chainmail_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 0)).setUnlocalizedName("helmetChain"));
/*  814 */     registerItem(303, "chainmail_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 1)).setUnlocalizedName("chestplateChain"));
/*  815 */     registerItem(304, "chainmail_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 2)).setUnlocalizedName("leggingsChain"));
/*  816 */     registerItem(305, "chainmail_boots", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 3)).setUnlocalizedName("bootsChain"));
/*  817 */     registerItem(306, "iron_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 0)).setUnlocalizedName("helmetIron"));
/*  818 */     registerItem(307, "iron_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 1)).setUnlocalizedName("chestplateIron"));
/*  819 */     registerItem(308, "iron_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 2)).setUnlocalizedName("leggingsIron"));
/*  820 */     registerItem(309, "iron_boots", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 3)).setUnlocalizedName("bootsIron"));
/*  821 */     registerItem(310, "diamond_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)).setUnlocalizedName("helmetDiamond"));
/*  822 */     registerItem(311, "diamond_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 1)).setUnlocalizedName("chestplateDiamond"));
/*  823 */     registerItem(312, "diamond_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 2)).setUnlocalizedName("leggingsDiamond"));
/*  824 */     registerItem(313, "diamond_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 3)).setUnlocalizedName("bootsDiamond"));
/*  825 */     registerItem(314, "golden_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 0)).setUnlocalizedName("helmetGold"));
/*  826 */     registerItem(315, "golden_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 1)).setUnlocalizedName("chestplateGold"));
/*  827 */     registerItem(316, "golden_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 2)).setUnlocalizedName("leggingsGold"));
/*  828 */     registerItem(317, "golden_boots", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 3)).setUnlocalizedName("bootsGold"));
/*  829 */     registerItem(318, "flint", (new Item()).setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials));
/*  830 */     registerItem(319, "porkchop", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("porkchopRaw"));
/*  831 */     registerItem(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("porkchopCooked"));
/*  832 */     registerItem(321, "painting", (new ItemHangingEntity((Class)EntityPainting.class)).setUnlocalizedName("painting"));
/*  833 */     registerItem(322, "golden_apple", (new ItemAppleGold(4, 1.2F, false)).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("appleGold"));
/*  834 */     registerItem(323, "sign", (new ItemSign()).setUnlocalizedName("sign"));
/*  835 */     registerItem(324, "wooden_door", (new ItemDoor(Blocks.oak_door)).setUnlocalizedName("doorOak"));
/*  836 */     Item item = (new ItemBucket(Blocks.air)).setUnlocalizedName("bucket").setMaxStackSize(16);
/*  837 */     registerItem(325, "bucket", item);
/*  838 */     registerItem(326, "water_bucket", (new ItemBucket((Block)Blocks.flowing_water)).setUnlocalizedName("bucketWater").setContainerItem(item));
/*  839 */     registerItem(327, "lava_bucket", (new ItemBucket((Block)Blocks.flowing_lava)).setUnlocalizedName("bucketLava").setContainerItem(item));
/*  840 */     registerItem(328, "minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.RIDEABLE)).setUnlocalizedName("minecart"));
/*  841 */     registerItem(329, "saddle", (new ItemSaddle()).setUnlocalizedName("saddle"));
/*  842 */     registerItem(330, "iron_door", (new ItemDoor(Blocks.iron_door)).setUnlocalizedName("doorIron"));
/*  843 */     registerItem(331, "redstone", (new ItemRedstone()).setUnlocalizedName("redstone").setPotionEffect("-5+6-7"));
/*  844 */     registerItem(332, "snowball", (new ItemSnowball()).setUnlocalizedName("snowball"));
/*  845 */     registerItem(333, "boat", (new ItemBoat()).setUnlocalizedName("boat"));
/*  846 */     registerItem(334, "leather", (new Item()).setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials));
/*  847 */     registerItem(335, "milk_bucket", (new ItemBucketMilk()).setUnlocalizedName("milk").setContainerItem(item));
/*  848 */     registerItem(336, "brick", (new Item()).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials));
/*  849 */     registerItem(337, "clay_ball", (new Item()).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials));
/*  850 */     registerItem(338, "reeds", (new ItemReed((Block)Blocks.reeds)).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials));
/*  851 */     registerItem(339, "paper", (new Item()).setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc));
/*  852 */     registerItem(340, "book", (new ItemBook()).setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc));
/*  853 */     registerItem(341, "slime_ball", (new Item()).setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc));
/*  854 */     registerItem(342, "chest_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.CHEST)).setUnlocalizedName("minecartChest"));
/*  855 */     registerItem(343, "furnace_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.FURNACE)).setUnlocalizedName("minecartFurnace"));
/*  856 */     registerItem(344, "egg", (new ItemEgg()).setUnlocalizedName("egg"));
/*  857 */     registerItem(345, "compass", (new Item()).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools));
/*  858 */     registerItem(346, "fishing_rod", (new ItemFishingRod()).setUnlocalizedName("fishingRod"));
/*  859 */     registerItem(347, "clock", (new Item()).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools));
/*  860 */     registerItem(348, "glowstone_dust", (new Item()).setUnlocalizedName("yellowDust").setPotionEffect("+5-6-7").setCreativeTab(CreativeTabs.tabMaterials));
/*  861 */     registerItem(349, "fish", (new ItemFishFood(false)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  862 */     registerItem(350, "cooked_fish", (new ItemFishFood(true)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  863 */     registerItem(351, "dye", (new ItemDye()).setUnlocalizedName("dyePowder"));
/*  864 */     registerItem(352, "bone", (new Item()).setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc));
/*  865 */     registerItem(353, "sugar", (new Item()).setUnlocalizedName("sugar").setPotionEffect("-0+1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabMaterials));
/*  866 */     registerItem(354, "cake", (new ItemReed(Blocks.cake)).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood));
/*  867 */     registerItem(355, "bed", (new ItemBed()).setMaxStackSize(1).setUnlocalizedName("bed"));
/*  868 */     registerItem(356, "repeater", (new ItemReed((Block)Blocks.unpowered_repeater)).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone));
/*  869 */     registerItem(357, "cookie", (new ItemFood(2, 0.1F, false)).setUnlocalizedName("cookie"));
/*  870 */     registerItem(358, "filled_map", (new ItemMap()).setUnlocalizedName("map"));
/*  871 */     registerItem(359, "shears", (new ItemShears()).setUnlocalizedName("shears"));
/*  872 */     registerItem(360, "melon", (new ItemFood(2, 0.3F, false)).setUnlocalizedName("melon"));
/*  873 */     registerItem(361, "pumpkin_seeds", (new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland)).setUnlocalizedName("seeds_pumpkin"));
/*  874 */     registerItem(362, "melon_seeds", (new ItemSeeds(Blocks.melon_stem, Blocks.farmland)).setUnlocalizedName("seeds_melon"));
/*  875 */     registerItem(363, "beef", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("beefRaw"));
/*  876 */     registerItem(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("beefCooked"));
/*  877 */     registerItem(365, "chicken", (new ItemFood(2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw"));
/*  878 */     registerItem(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).setUnlocalizedName("chickenCooked"));
/*  879 */     registerItem(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("rottenFlesh"));
/*  880 */     registerItem(368, "ender_pearl", (new ItemEnderPearl()).setUnlocalizedName("enderPearl"));
/*  881 */     registerItem(369, "blaze_rod", (new Item()).setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials).setFull3D());
/*  882 */     registerItem(370, "ghast_tear", (new Item()).setUnlocalizedName("ghastTear").setPotionEffect("+0-1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  883 */     registerItem(371, "gold_nugget", (new Item()).setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials));
/*  884 */     registerItem(372, "nether_wart", (new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand)).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4"));
/*  885 */     registerItem(373, "potion", (new ItemPotion()).setUnlocalizedName("potion"));
/*  886 */     registerItem(374, "glass_bottle", (new ItemGlassBottle()).setUnlocalizedName("glassBottle"));
/*  887 */     registerItem(375, "spider_eye", (new ItemFood(2, 0.8F, false)).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setUnlocalizedName("spiderEye").setPotionEffect("-0-1+2-3&4-4+13"));
/*  888 */     registerItem(376, "fermented_spider_eye", (new Item()).setUnlocalizedName("fermentedSpiderEye").setPotionEffect("-0+3-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  889 */     registerItem(377, "blaze_powder", (new Item()).setUnlocalizedName("blazePowder").setPotionEffect("+0-1-2+3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  890 */     registerItem(378, "magma_cream", (new Item()).setUnlocalizedName("magmaCream").setPotionEffect("+0+1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  891 */     registerItem(379, "brewing_stand", (new ItemReed(Blocks.brewing_stand)).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing));
/*  892 */     registerItem(380, "cauldron", (new ItemReed((Block)Blocks.cauldron)).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing));
/*  893 */     registerItem(381, "ender_eye", (new ItemEnderEye()).setUnlocalizedName("eyeOfEnder"));
/*  894 */     registerItem(382, "speckled_melon", (new Item()).setUnlocalizedName("speckledMelon").setPotionEffect("+0-1+2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  895 */     registerItem(383, "spawn_egg", (new ItemMonsterPlacer()).setUnlocalizedName("monsterPlacer"));
/*  896 */     registerItem(384, "experience_bottle", (new ItemExpBottle()).setUnlocalizedName("expBottle"));
/*  897 */     registerItem(385, "fire_charge", (new ItemFireball()).setUnlocalizedName("fireball"));
/*  898 */     registerItem(386, "writable_book", (new ItemWritableBook()).setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc));
/*  899 */     registerItem(387, "written_book", (new ItemEditableBook()).setUnlocalizedName("writtenBook").setMaxStackSize(16));
/*  900 */     registerItem(388, "emerald", (new Item()).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials));
/*  901 */     registerItem(389, "item_frame", (new ItemHangingEntity((Class)EntityItemFrame.class)).setUnlocalizedName("frame"));
/*  902 */     registerItem(390, "flower_pot", (new ItemReed(Blocks.flower_pot)).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations));
/*  903 */     registerItem(391, "carrot", (new ItemSeedFood(3, 0.6F, Blocks.carrots, Blocks.farmland)).setUnlocalizedName("carrots"));
/*  904 */     registerItem(392, "potato", (new ItemSeedFood(1, 0.3F, Blocks.potatoes, Blocks.farmland)).setUnlocalizedName("potato"));
/*  905 */     registerItem(393, "baked_potato", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("potatoBaked"));
/*  906 */     registerItem(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).setPotionEffect(Potion.poison.id, 5, 0, 0.6F).setUnlocalizedName("potatoPoisonous"));
/*  907 */     registerItem(395, "map", (new ItemEmptyMap()).setUnlocalizedName("emptyMap"));
/*  908 */     registerItem(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).setUnlocalizedName("carrotGolden").setPotionEffect("-0+1+2-3+13&4-4").setCreativeTab(CreativeTabs.tabBrewing));
/*  909 */     registerItem(397, "skull", (new ItemSkull()).setUnlocalizedName("skull"));
/*  910 */     registerItem(398, "carrot_on_a_stick", (new ItemCarrotOnAStick()).setUnlocalizedName("carrotOnAStick"));
/*  911 */     registerItem(399, "nether_star", (new ItemSimpleFoiled()).setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials));
/*  912 */     registerItem(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood));
/*  913 */     registerItem(401, "fireworks", (new ItemFirework()).setUnlocalizedName("fireworks"));
/*  914 */     registerItem(402, "firework_charge", (new ItemFireworkCharge()).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc));
/*  915 */     registerItem(403, "enchanted_book", (new ItemEnchantedBook()).setMaxStackSize(1).setUnlocalizedName("enchantedBook"));
/*  916 */     registerItem(404, "comparator", (new ItemReed((Block)Blocks.unpowered_comparator)).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone));
/*  917 */     registerItem(405, "netherbrick", (new Item()).setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials));
/*  918 */     registerItem(406, "quartz", (new Item()).setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials));
/*  919 */     registerItem(407, "tnt_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.TNT)).setUnlocalizedName("minecartTnt"));
/*  920 */     registerItem(408, "hopper_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.HOPPER)).setUnlocalizedName("minecartHopper"));
/*  921 */     registerItem(409, "prismarine_shard", (new Item()).setUnlocalizedName("prismarineShard").setCreativeTab(CreativeTabs.tabMaterials));
/*  922 */     registerItem(410, "prismarine_crystals", (new Item()).setUnlocalizedName("prismarineCrystals").setCreativeTab(CreativeTabs.tabMaterials));
/*  923 */     registerItem(411, "rabbit", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("rabbitRaw"));
/*  924 */     registerItem(412, "cooked_rabbit", (new ItemFood(5, 0.6F, true)).setUnlocalizedName("rabbitCooked"));
/*  925 */     registerItem(413, "rabbit_stew", (new ItemSoup(10)).setUnlocalizedName("rabbitStew"));
/*  926 */     registerItem(414, "rabbit_foot", (new Item()).setUnlocalizedName("rabbitFoot").setPotionEffect("+0+1-2+3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  927 */     registerItem(415, "rabbit_hide", (new Item()).setUnlocalizedName("rabbitHide").setCreativeTab(CreativeTabs.tabMaterials));
/*  928 */     registerItem(416, "armor_stand", (new ItemArmorStand()).setUnlocalizedName("armorStand").setMaxStackSize(16));
/*  929 */     registerItem(417, "iron_horse_armor", (new Item()).setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  930 */     registerItem(418, "golden_horse_armor", (new Item()).setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  931 */     registerItem(419, "diamond_horse_armor", (new Item()).setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  932 */     registerItem(420, "lead", (new ItemLead()).setUnlocalizedName("leash"));
/*  933 */     registerItem(421, "name_tag", (new ItemNameTag()).setUnlocalizedName("nameTag"));
/*  934 */     registerItem(422, "command_block_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.COMMAND_BLOCK)).setUnlocalizedName("minecartCommandBlock").setCreativeTab(null));
/*  935 */     registerItem(423, "mutton", (new ItemFood(2, 0.3F, true)).setUnlocalizedName("muttonRaw"));
/*  936 */     registerItem(424, "cooked_mutton", (new ItemFood(6, 0.8F, true)).setUnlocalizedName("muttonCooked"));
/*  937 */     registerItem(425, "banner", (new ItemBanner()).setUnlocalizedName("banner"));
/*  938 */     registerItem(427, "spruce_door", (new ItemDoor(Blocks.spruce_door)).setUnlocalizedName("doorSpruce"));
/*  939 */     registerItem(428, "birch_door", (new ItemDoor(Blocks.birch_door)).setUnlocalizedName("doorBirch"));
/*  940 */     registerItem(429, "jungle_door", (new ItemDoor(Blocks.jungle_door)).setUnlocalizedName("doorJungle"));
/*  941 */     registerItem(430, "acacia_door", (new ItemDoor(Blocks.acacia_door)).setUnlocalizedName("doorAcacia"));
/*  942 */     registerItem(431, "dark_oak_door", (new ItemDoor(Blocks.dark_oak_door)).setUnlocalizedName("doorDarkOak"));
/*  943 */     registerItem(2256, "record_13", (new ItemRecord("13")).setUnlocalizedName("record"));
/*  944 */     registerItem(2257, "record_cat", (new ItemRecord("cat")).setUnlocalizedName("record"));
/*  945 */     registerItem(2258, "record_blocks", (new ItemRecord("blocks")).setUnlocalizedName("record"));
/*  946 */     registerItem(2259, "record_chirp", (new ItemRecord("chirp")).setUnlocalizedName("record"));
/*  947 */     registerItem(2260, "record_far", (new ItemRecord("far")).setUnlocalizedName("record"));
/*  948 */     registerItem(2261, "record_mall", (new ItemRecord("mall")).setUnlocalizedName("record"));
/*  949 */     registerItem(2262, "record_mellohi", (new ItemRecord("mellohi")).setUnlocalizedName("record"));
/*  950 */     registerItem(2263, "record_stal", (new ItemRecord("stal")).setUnlocalizedName("record"));
/*  951 */     registerItem(2264, "record_strad", (new ItemRecord("strad")).setUnlocalizedName("record"));
/*  952 */     registerItem(2265, "record_ward", (new ItemRecord("ward")).setUnlocalizedName("record"));
/*  953 */     registerItem(2266, "record_11", (new ItemRecord("11")).setUnlocalizedName("record"));
/*  954 */     registerItem(2267, "record_wait", (new ItemRecord("wait")).setUnlocalizedName("record"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void registerItemBlock(Block blockIn) {
/*  962 */     registerItemBlock(blockIn, new ItemBlock(blockIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void registerItemBlock(Block blockIn, Item itemIn) {
/*  970 */     registerItem(Block.getIdFromBlock(blockIn), (ResourceLocation)Block.blockRegistry.getNameForObject(blockIn), itemIn);
/*  971 */     BLOCK_TO_ITEM.put(blockIn, itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, String textualID, Item itemIn) {
/*  976 */     registerItem(id, new ResourceLocation(textualID), itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, ResourceLocation textualID, Item itemIn) {
/*  981 */     itemRegistry.register(id, textualID, itemIn);
/*      */   }
/*      */   
/*      */   public enum ToolMaterial
/*      */   {
/*  986 */     WOOD(0, 59, 2.0F, 0.0F, 15),
/*  987 */     STONE(1, 131, 4.0F, 1.0F, 5),
/*  988 */     IRON(2, 250, 6.0F, 2.0F, 14),
/*  989 */     EMERALD(3, 1561, 8.0F, 3.0F, 10),
/*  990 */     GOLD(0, 32, 12.0F, 0.0F, 22);
/*      */     
/*      */     private final int harvestLevel;
/*      */     
/*      */     private final int maxUses;
/*      */     private final float efficiencyOnProperMaterial;
/*      */     private final float damageVsEntity;
/*      */     private final int enchantability;
/*      */     
/*      */     ToolMaterial(int harvestLevel, int maxUses, float efficiency, float damageVsEntity, int enchantability) {
/* 1000 */       this.harvestLevel = harvestLevel;
/* 1001 */       this.maxUses = maxUses;
/* 1002 */       this.efficiencyOnProperMaterial = efficiency;
/* 1003 */       this.damageVsEntity = damageVsEntity;
/* 1004 */       this.enchantability = enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxUses() {
/* 1009 */       return this.maxUses;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getEfficiencyOnProperMaterial() {
/* 1014 */       return this.efficiencyOnProperMaterial;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getDamageVsEntity() {
/* 1019 */       return this.damageVsEntity;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHarvestLevel() {
/* 1024 */       return this.harvestLevel;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getEnchantability() {
/* 1029 */       return this.enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public Item getRepairItem() {
/* 1034 */       return (this == WOOD) ? Item.getItemFromBlock(Blocks.planks) : ((this == STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : ((this == GOLD) ? Items.gold_ingot : ((this == IRON) ? Items.iron_ingot : ((this == EMERALD) ? Items.diamond : null))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */