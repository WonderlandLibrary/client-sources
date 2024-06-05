package net.minecraft.util.datafix;

import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.*;
import net.minecraft.util.datafix.fixes.*;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.WorldInfo;

public class DataFixesManager {
    private static void registerFixes(DataFixer fixer) {
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
        fixer.registerFix(FixTypes.ENTITY, new ElderGuardianSplit());
        fixer.registerFix(FixTypes.ENTITY, new SkeletonSplit());
        fixer.registerFix(FixTypes.ENTITY, new ZombieSplit());
        fixer.registerFix(FixTypes.ENTITY, new HorseSplit());
        fixer.registerFix(FixTypes.BLOCK_ENTITY, new TileEntityId());
        fixer.registerFix(FixTypes.ENTITY, new EntityId());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new BannerItemColor());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new PotionWater());
        fixer.registerFix(FixTypes.ENTITY, new ShulkerBoxEntityColor());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new ShulkerBoxItemColor());
        fixer.registerFix(FixTypes.BLOCK_ENTITY, new ShulkerBoxTileColor());
        fixer.registerFix(FixTypes.OPTIONS, new OptionsLowerCaseLanguage());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new TotemItemRename());
        fixer.registerFix(FixTypes.CHUNK, new AddBedTileEntity());
        fixer.registerFix(FixTypes.ITEM_INSTANCE, new BedItemColor());
    }

    public static DataFixer createFixer() {
        DataFixer datafixer = new DataFixer(1343);
        WorldInfo.registerFixes(datafixer);
        EntityPlayerMP.func_191522_a(datafixer);
        EntityPlayer.registerFixesPlayer(datafixer);
        AnvilChunkLoader.registerFixes(datafixer);
        ItemStack.registerFixes(datafixer);
        Template.func_191158_a(datafixer);
        Entity.func_190533_a(datafixer);
        EntityArmorStand.registerFixesArmorStand(datafixer);
        EntityArrow.registerFixesArrow(datafixer);
        EntityBat.registerFixesBat(datafixer);
        EntityBlaze.registerFixesBlaze(datafixer);
        EntityCaveSpider.registerFixesCaveSpider(datafixer);
        EntityChicken.registerFixesChicken(datafixer);
        EntityCow.registerFixesCow(datafixer);
        EntityCreeper.registerFixesCreeper(datafixer);
        EntityDonkey.func_190699_b(datafixer);
        EntityDragonFireball.registerFixesDragonFireball(datafixer);
        EntityElderGuardian.func_190768_b(datafixer);
        EntityDragon.registerFixesDragon(datafixer);
        EntityEnderman.registerFixesEnderman(datafixer);
        EntityEndermite.registerFixesEndermite(datafixer);
        EntityEvoker.func_190759_b(datafixer);
        EntityFallingBlock.registerFixesFallingBlock(datafixer);
        EntityFireworkRocket.registerFixesFireworkRocket(datafixer);
        EntityGhast.registerFixesGhast(datafixer);
        EntityGiantZombie.registerFixesGiantZombie(datafixer);
        EntityGuardian.registerFixesGuardian(datafixer);
        EntityHorse.registerFixesHorse(datafixer);
        EntityHusk.func_190740_b(datafixer);
        EntityItem.registerFixesItem(datafixer);
        EntityItemFrame.registerFixesItemFrame(datafixer);
        EntityLargeFireball.registerFixesLargeFireball(datafixer);
        EntityMagmaCube.registerFixesMagmaCube(datafixer);
        EntityMinecartChest.registerFixesMinecartChest(datafixer);
        EntityMinecartCommandBlock.registerFixesMinecartCommand(datafixer);
        EntityMinecartFurnace.registerFixesMinecartFurnace(datafixer);
        EntityMinecartHopper.registerFixesMinecartHopper(datafixer);
        EntityMinecartEmpty.registerFixesMinecartEmpty(datafixer);
        EntityMinecartMobSpawner.registerFixesMinecartMobSpawner(datafixer);
        EntityMinecartTNT.registerFixesMinecartTNT(datafixer);
        EntityMule.func_190700_b(datafixer);
        EntityMooshroom.registerFixesMooshroom(datafixer);
        EntityOcelot.registerFixesOcelot(datafixer);
        EntityPig.registerFixesPig(datafixer);
        EntityPigZombie.registerFixesPigZombie(datafixer);
        EntityRabbit.registerFixesRabbit(datafixer);
        EntitySheep.registerFixesSheep(datafixer);
        EntityShulker.registerFixesShulker(datafixer);
        EntitySilverfish.registerFixesSilverfish(datafixer);
        EntitySkeleton.registerFixesSkeleton(datafixer);
        EntitySkeletonHorse.func_190692_b(datafixer);
        EntitySlime.registerFixesSlime(datafixer);
        EntitySmallFireball.registerFixesSmallFireball(datafixer);
        EntitySnowman.registerFixesSnowman(datafixer);
        EntitySnowball.registerFixesSnowball(datafixer);
        EntitySpectralArrow.registerFixesSpectralArrow(datafixer);
        EntitySpider.registerFixesSpider(datafixer);
        EntitySquid.registerFixesSquid(datafixer);
        EntityStray.func_190728_b(datafixer);
        EntityEgg.registerFixesEgg(datafixer);
        EntityEnderPearl.registerFixesEnderPearl(datafixer);
        EntityExpBottle.registerFixesExpBottle(datafixer);
        EntityPotion.registerFixesPotion(datafixer);
        EntityTippedArrow.registerFixesTippedArrow(datafixer);
        EntityVex.func_190663_b(datafixer);
        EntityVillager.registerFixesVillager(datafixer);
        EntityIronGolem.registerFixesIronGolem(datafixer);
        EntityVindicator.func_190641_b(datafixer);
        EntityWitch.registerFixesWitch(datafixer);
        EntityWither.registerFixesWither(datafixer);
        EntityWitherSkeleton.func_190729_b(datafixer);
        EntityWitherSkull.registerFixesWitherSkull(datafixer);
        EntityWolf.registerFixesWolf(datafixer);
        EntityZombie.registerFixesZombie(datafixer);
        EntityZombieHorse.func_190693_b(datafixer);
        EntityZombieVillager.func_190737_b(datafixer);
        TileEntityPiston.registerFixesPiston(datafixer);
        TileEntityFlowerPot.registerFixesFlowerPot(datafixer);
        TileEntityFurnace.registerFixesFurnace(datafixer);
        TileEntityChest.registerFixesChest(datafixer);
        TileEntityDispenser.registerFixes(datafixer);
        TileEntityDropper.registerFixesDropper(datafixer);
        TileEntityBrewingStand.registerFixesBrewingStand(datafixer);
        TileEntityHopper.registerFixesHopper(datafixer);
        BlockJukebox.registerFixesJukebox(datafixer);
        TileEntityMobSpawner.registerFixesMobSpawner(datafixer);
        TileEntityShulkerBox.func_190593_a(datafixer);
        registerFixes(datafixer);
        return datafixer;
    }

    public static NBTTagCompound processItemStack(IDataFixer fixer, NBTTagCompound compound, int version, String key) {
        if (compound.hasKey(key, 10)) {
            compound.setTag(key, fixer.process(FixTypes.ITEM_INSTANCE, compound.getCompoundTag(key), version));
        }

        return compound;
    }

    public static NBTTagCompound processInventory(IDataFixer fixer, NBTTagCompound compound, int version, String key) {
        if (compound.hasKey(key, 9)) {
            NBTTagList nbttaglist = compound.getTagList(key, 10);

            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                nbttaglist.set(i, fixer.process(FixTypes.ITEM_INSTANCE, nbttaglist.getCompoundTagAt(i), version));
            }
        }

        return compound;
    }
}
