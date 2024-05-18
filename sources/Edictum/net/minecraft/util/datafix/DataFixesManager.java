package net.minecraft.util.datafix;

import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.datafix.fixes.ArmorStandSilent;
import net.minecraft.util.datafix.fixes.BookPagesStrictJSON;
import net.minecraft.util.datafix.fixes.CookedFishIDTypo;
import net.minecraft.util.datafix.fixes.EntityArmorAndHeld;
import net.minecraft.util.datafix.fixes.EntityHealth;
import net.minecraft.util.datafix.fixes.ForceVBOOn;
import net.minecraft.util.datafix.fixes.HorseSaddle;
import net.minecraft.util.datafix.fixes.ItemIntIDToString;
import net.minecraft.util.datafix.fixes.MinecartEntityTypes;
import net.minecraft.util.datafix.fixes.PaintingDirection;
import net.minecraft.util.datafix.fixes.PotionItems;
import net.minecraft.util.datafix.fixes.RedundantChanceTags;
import net.minecraft.util.datafix.fixes.RidingToPassengers;
import net.minecraft.util.datafix.fixes.SignStrictJSON;
import net.minecraft.util.datafix.fixes.SpawnEggNames;
import net.minecraft.util.datafix.fixes.SpawnerEntityTypes;
import net.minecraft.util.datafix.fixes.StringToUUID;
import net.minecraft.util.datafix.fixes.ZombieProfToType;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.storage.WorldInfo;

public class DataFixesManager
{
    private static void registerFixes(DataFixer fixer)
    {
        fixer.registerFix(FixTypes.ENTITY, new EntityArmorAndHeld());
        fixer.registerFix(FixTypes.BLOCK_ENTITY, new SignStrictJSON());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemIntIDToString());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new PotionItems());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new SpawnEggNames());
        fixer.registerFix(FixTypes.ENTITY, new MinecartEntityTypes());
        fixer.registerFix(FixTypes.BLOCK_ENTITY, new SpawnerEntityTypes());
        fixer.registerFix(FixTypes.ENTITY, new StringToUUID());
        fixer.registerFix(FixTypes.ENTITY, new EntityHealth());
        fixer.registerFix(FixTypes.ENTITY, new HorseSaddle());
        fixer.registerFix(FixTypes.ENTITY, new PaintingDirection());
        fixer.registerFix(FixTypes.ENTITY, new RedundantChanceTags());
        fixer.registerFix(FixTypes.ENTITY, new RidingToPassengers());
        fixer.registerFix(FixTypes.ENTITY, new ArmorStandSilent());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new BookPagesStrictJSON());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new CookedFishIDTypo());
        fixer.registerFix(FixTypes.ENTITY, new ZombieProfToType());
        fixer.registerFix(FixTypes.OPTIONS, new ForceVBOOn());
    }

    public static DataFixer createFixer()
    {
        DataFixer datafixer = new DataFixer(510);
        WorldInfo.func_189967_a(datafixer);
        EntityPlayer.func_189806_a(datafixer);
        AnvilChunkLoader.func_189889_a(datafixer);
        ItemStack.func_189868_a(datafixer);
        EntityArmorStand.func_189805_a(datafixer);
        EntityArrow.func_189658_a(datafixer);
        EntityBat.func_189754_b(datafixer);
        EntityBlaze.func_189761_b(datafixer);
        EntityCaveSpider.func_189775_b(datafixer);
        EntityChicken.func_189789_b(datafixer);
        EntityCow.func_189790_b(datafixer);
        EntityCreeper.func_189762_b(datafixer);
        EntityDragonFireball.func_189747_a(datafixer);
        EntityDragon.func_189755_b(datafixer);
        EntityEnderman.func_189763_b(datafixer);
        EntityEndermite.func_189764_b(datafixer);
        EntityFallingBlock.func_189741_a(datafixer);
        EntityLargeFireball.func_189744_a(datafixer);
        EntityFireworkRocket.func_189656_a(datafixer);
        EntityGhast.func_189756_b(datafixer);
        EntityGiantZombie.func_189765_b(datafixer);
        EntityGuardian.func_189766_b(datafixer);
        EntityHorse.func_189803_b(datafixer);
        EntityItem.func_189742_a(datafixer);
        EntityItemFrame.func_189738_a(datafixer);
        EntityMagmaCube.func_189759_b(datafixer);
        EntityMinecartChest.func_189681_a(datafixer);
        EntityMinecartCommandBlock.func_189670_a(datafixer);
        EntityMinecartFurnace.func_189671_a(datafixer);
        EntityMinecartHopper.func_189682_a(datafixer);
        EntityMinecartEmpty.func_189673_a(datafixer);
        EntityMinecartMobSpawner.func_189672_a(datafixer);
        EntityMinecartTNT.func_189674_a(datafixer);
        EntityLiving.func_189753_a(datafixer);
        EntityMob.func_189760_c(datafixer);
        EntityMooshroom.func_189791_c(datafixer);
        EntityOcelot.func_189787_b(datafixer);
        EntityPig.func_189792_b(datafixer);
        EntityPigZombie.func_189781_b(datafixer);
        EntityRabbit.func_189801_b(datafixer);
        EntitySheep.func_189802_b(datafixer);
        EntityShulker.func_189757_b(datafixer);
        EntitySilverfish.func_189767_b(datafixer);
        EntitySkeleton.func_189772_b(datafixer);
        EntitySlime.func_189758_c(datafixer);
        EntitySmallFireball.func_189745_a(datafixer);
        EntitySnowman.func_189783_b(datafixer);
        EntitySnowball.func_189662_a(datafixer);
        EntitySpectralArrow.func_189659_b(datafixer);
        EntitySpider.func_189774_d(datafixer);
        EntitySquid.func_189804_b(datafixer);
        EntityEgg.func_189664_a(datafixer);
        EntityEnderPearl.func_189663_a(datafixer);
        EntityExpBottle.func_189666_a(datafixer);
        EntityPotion.func_189665_a(datafixer);
        EntityTippedArrow.func_189660_b(datafixer);
        EntityVillager.func_189785_b(datafixer);
        EntityIronGolem.func_189784_b(datafixer);
        EntityWitch.func_189776_b(datafixer);
        EntityWither.func_189782_b(datafixer);
        EntityWitherSkull.func_189746_a(datafixer);
        EntityWolf.func_189788_b(datafixer);
        EntityZombie.func_189779_d(datafixer);
        TileEntityPiston.func_189685_a(datafixer);
        TileEntityFlowerPot.func_189699_a(datafixer);
        TileEntityFurnace.func_189676_a(datafixer);
        TileEntityChest.func_189677_a(datafixer);
        TileEntityDispenser.func_189678_a(datafixer);
        TileEntityDropper.func_189679_b(datafixer);
        TileEntityBrewingStand.func_189675_a(datafixer);
        TileEntityHopper.func_189683_a(datafixer);
        BlockJukebox.func_189873_a(datafixer);
        TileEntityMobSpawner.func_189684_a(datafixer);
        registerFixes(datafixer);
        return datafixer;
    }

    public static NBTTagCompound processItemStack(IDataFixer fixer, NBTTagCompound compound, int version, String key)
    {
        if (compound.hasKey(key, 10))
        {
            compound.setTag(key, fixer.process(FixTypes.ITEM_INSTANCE, compound.getCompoundTag(key), version));
        }

        return compound;
    }

    public static NBTTagCompound processInventory(IDataFixer fixer, NBTTagCompound compound, int version, String key)
    {
        if (compound.hasKey(key, 9))
        {
            NBTTagList nbttaglist = compound.getTagList(key, 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                nbttaglist.set(i, fixer.process(FixTypes.ITEM_INSTANCE, nbttaglist.getCompoundTagAt(i), version));
            }
        }

        return compound;
    }
}
