package com.client.glowclient;

import net.minecraft.profiler.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.village.*;
import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.util.text.event.*;
import net.minecraft.util.text.*;
import net.minecraft.tileentity.*;
import java.lang.reflect.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class JA
{
    private static final Profiler b;
    
    private static void M(final ContainerHorseInventory containerHorseInventory, final AbstractHorse abstractHorse) {
        final ContainerHorseChest containerHorseChest = new ContainerHorseChest("HorseChest", containerHorseInventory.inventorySlots.size() - 36);
        int n;
        int i = n = 0;
        while (i < containerHorseChest.getSizeInventory()) {
            final Slot slot;
            if ((slot = containerHorseInventory.getSlot(n)).getHasStack()) {
                containerHorseChest.setInventorySlotContents(n, slot.getStack());
            }
            i = ++n;
        }
        Hd.D(abstractHorse, AbstractHorse.class, ContainerHorseChest.class, containerHorseChest);
    }
    
    static {
        b = Minecraft.getMinecraft().profiler;
    }
    
    public JA() {
        super();
    }
    
    public static void M(final WorldClient worldClient) {
        JA.b.startSection("Core");
        if (uc.A.isIntegratedServerRunning()) {
            JA.b.endSection();
            return;
        }
        if (uc.G) {
            if (!uc.g) {
                Qd.M(ub.I, "com.client.glowclient.utils.mod.imports.wdl.messages.generalInfo.worldChanged", new Object[0]);
                uc.H = true;
                uc.d();
            }
            JA.b.endSection();
            return;
        }
        final boolean m = uc.M();
        JA.b.endSection();
        final Iterator<ra<m>> iterator2;
        Iterator<ra<m>> iterator = iterator2 = Oa.M(m.class).iterator();
        while (iterator.hasNext()) {
            final ra<m> ra = iterator2.next();
            JA.b.startSection(ra.B);
            ra.A.M(worldClient, m);
            JA.b.endSection();
            iterator = iterator2;
        }
    }
    
    public static void M() {
        if (!uc.G) {
            return;
        }
        if (uc.A.objectMouseOver == null) {
            return;
        }
        if (uc.A.objectMouseOver.typeOfHit == RayTraceResult$Type.ENTITY) {
            uc.x = uc.A.objectMouseOver.entityHit;
            return;
        }
        uc.x = null;
        uc.D = uc.A.objectMouseOver.getBlockPos();
    }
    
    public static void M(final BlockPos blockPos, final Block block, final int n, final int n2) {
        if (!uc.G) {
            return;
        }
        if (!WA.A(blockPos.getX() << 4, blockPos.getZ() << 4)) {
            return;
        }
        if (block == Blocks.NOTEBLOCK) {
            final TileEntityNote tileEntityNote2;
            final TileEntityNote tileEntityNote = tileEntityNote2 = new TileEntityNote();
            tileEntityNote.note = (byte)(n2 % 25);
            uc.C.setTileEntity(blockPos, (TileEntity)tileEntityNote2);
            uc.M(blockPos, (TileEntity)tileEntityNote);
            Qd.M(ub.A, "com.client.glowclient.utils.mod.imports.wdl.messages.onBlockEvent.noteblock", blockPos, n2, tileEntityNote2);
        }
    }
    
    public static void M(final Chunk chunk) {
        if (!uc.G) {
            return;
        }
        if (chunk == null) {
            return;
        }
        if (WA.e(chunk)) {
            Qd.M(ub.b, "com.client.glowclient.utils.mod.imports.wdl.messages.onChunkNoLongerNeeded.saved", chunk.x, chunk.z);
            uc.M(chunk);
            return;
        }
        Qd.M(ub.b, "com.client.glowclient.utils.mod.imports.wdl.messages.onChunkNoLongerNeeded.didNotSave", chunk.x, chunk.z);
    }
    
    public static void M(final int n, final MapData mapData) {
        if (!uc.G) {
            return;
        }
        if (!WA.d()) {
            return;
        }
        uc.d.put(n, mapData);
        Qd.M(ub.B, "com.client.glowclient.utils.mod.imports.wdl.messages.onMapSaved", n);
    }
    
    public static boolean M() {
        if (!uc.G) {
            return true;
        }
        if (uc.h == null || Hd.M(uc.h.getClass())) {
            return true;
        }
        final AbstractHorse abstractHorse;
        if (uc.a.getRidingEntity() instanceof AbstractHorse && uc.h instanceof ContainerHorseInventory && (abstractHorse = Hd.D(uc.h, AbstractHorse.class)) == uc.a.getRidingEntity()) {
            final AbstractHorse abstractHorse2 = abstractHorse;
            if (!WA.M(abstractHorse2.chunkCoordX, abstractHorse2.chunkCoordZ)) {
                Qd.M(ub.i, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.cannotSaveEntities", new Object[0]);
                return true;
            }
            M((ContainerHorseInventory)uc.h, (AbstractHorse)uc.a.getRidingEntity());
            Qd.M(ub.i, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.savedRiddenHorse", new Object[0]);
            return true;
        }
        else if (uc.x != null) {
            if (!WA.M(uc.x.chunkCoordX, uc.x.chunkCoordZ)) {
                Qd.M(ub.i, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.cannotSaveEntities", new Object[0]);
                return true;
            }
            String s;
            if (uc.x instanceof EntityMinecartChest && uc.h instanceof ContainerChest) {
                final EntityMinecartChest entityMinecartChest = (EntityMinecartChest)uc.x;
                int n;
                int i = n = 0;
                while (i < entityMinecartChest.getSizeInventory()) {
                    final Slot slot;
                    if ((slot = uc.h.getSlot(n)).getHasStack()) {
                        entityMinecartChest.setInventorySlotContents(n, slot.getStack());
                    }
                    i = ++n;
                }
                s = "storageMinecart";
            }
            else if (uc.x instanceof EntityMinecartHopper && uc.h instanceof ContainerHopper) {
                final EntityMinecartHopper entityMinecartHopper = (EntityMinecartHopper)uc.x;
                int n2;
                int j = n2 = 0;
                while (j < entityMinecartHopper.getSizeInventory()) {
                    final Slot slot2;
                    if ((slot2 = uc.h.getSlot(n2)).getHasStack()) {
                        entityMinecartHopper.setInventorySlotContents(n2, slot2.getStack());
                    }
                    j = ++n2;
                }
                s = "hopperMinecart";
            }
            else if (uc.x instanceof EntityVillager && uc.h instanceof ContainerMerchant) {
                final EntityVillager entityVillager = (EntityVillager)uc.x;
                final IMerchant merchant;
                Hd.M(entityVillager, MerchantRecipeList.class, (merchant = Hd.D(uc.h, IMerchant.class)).getRecipes((EntityPlayer)uc.a));
                Label_0690: {
                    try {
                        final ITextComponent displayName;
                        if (!((displayName = merchant.getDisplayName()) instanceof TextComponentTranslation)) {
                            throw new CommandException("com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedWarning.villagerCareer.notAComponent", new Object[] { String.valueOf(displayName) });
                        }
                        final TextComponentTranslation textComponentTranslation;
                        final String key;
                        final int m = iC.M(key = (textComponentTranslation = (TextComponentTranslation)displayName).getKey(), entityVillager.getProfession());
                        int n3 = 0;
                        Field field = null;
                        final Field[] declaredFields;
                        final int length = (declaredFields = EntityVillager.class.getDeclaredFields()).length;
                        int n4;
                        int k = n4 = 0;
                        while (true) {
                            while (k < length) {
                                final Field field2;
                                if ((field2 = declaredFields[n4]).getType().equals(Integer.TYPE) && ++n3 == 4) {
                                    final Field field3 = field = field2;
                                    if (field3 == null) {
                                        throw new CommandException("com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedWarning.villagerCareer.professionField", new Object[0]);
                                    }
                                    final Field field4 = field;
                                    final EntityVillager entityVillager2 = entityVillager;
                                    final int n5 = m;
                                    field.setAccessible(true);
                                    field4.setInt(entityVillager2, n5);
                                    final TextComponentTranslation textComponentTranslation2;
                                    ((ITextComponent)(textComponentTranslation2 = new TextComponentTranslation(key, textComponentTranslation.getFormatArgs()))).getStyle().setHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_TEXT, (ITextComponent)new TextComponentString(key)));
                                    Qd.M(ub.i, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.savedEntity.villager.career", textComponentTranslation2, m);
                                    break Label_0690;
                                }
                                else {
                                    k = ++n4;
                                }
                            }
                            final Field field3 = field;
                            continue;
                        }
                    }
                    catch (CommandException ex) {
                        Qd.M(ub.L, ex.getMessage(), ex.getErrorObjects());
                    }
                    catch (Throwable t) {
                        Qd.M(ub.L, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedWarning.villagerCareer.exception", t);
                    }
                }
                s = "villager";
            }
            else {
                if (!(uc.x instanceof AbstractHorse) || !(uc.h instanceof ContainerHorseInventory)) {
                    return false;
                }
                M((ContainerHorseInventory)uc.h, (AbstractHorse)uc.x);
                s = "horse";
            }
            Qd.M(ub.i, new StringBuilder().insert(0, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.savedEntity.").append(s).toString(), new Object[0]);
            return true;
        }
        else {
            final TileEntity tileEntity;
            if ((tileEntity = uc.C.getTileEntity(uc.D)) == null) {
                Qd.M(ub.L, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedWarning.couldNotGetTE", uc.D);
                return true;
            }
            if (!WA.k(tileEntity.getPos().getX() << 4, tileEntity.getPos().getZ() << 4)) {
                Qd.M(ub.i, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.cannotSaveTileEntities", new Object[0]);
                return true;
            }
            String s3;
            if (uc.h instanceof ContainerChest && tileEntity instanceof TileEntityChest) {
                if (uc.h.inventorySlots.size() > 63) {
                    final BlockPos d = uc.D;
                    final TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
                    BlockPos blockPos = null;
                    BlockPos blockPos2 = null;
                    Object o = null;
                    Object o2 = null;
                    final BlockPos blockPos3 = d;
                    final int n6 = 0;
                    final BlockPos add = blockPos3.add(n6, n6, 1);
                    final TileEntity tileEntity2;
                    if ((tileEntity2 = uc.C.getTileEntity(add)) instanceof TileEntityChest && ((TileEntityChest)tileEntity2).getChestType() == tileEntityChest.getChestType()) {
                        o = tileEntityChest;
                        o2 = tileEntity2;
                        blockPos = d;
                        blockPos2 = add;
                    }
                    final BlockPos blockPos4 = d;
                    final int n7 = 0;
                    final BlockPos add2 = blockPos4.add(n7, n7, -1);
                    final TileEntity tileEntity3;
                    if ((tileEntity3 = uc.C.getTileEntity(add2)) instanceof TileEntityChest && ((TileEntityChest)tileEntity3).getChestType() == tileEntityChest.getChestType()) {
                        o = tileEntity3;
                        o2 = tileEntityChest;
                        blockPos = add2;
                        blockPos2 = d;
                    }
                    final BlockPos blockPos5 = d;
                    final int n8 = 1;
                    final int n9 = 0;
                    final BlockPos add3 = blockPos5.add(n8, n9, n9);
                    final TileEntity tileEntity4;
                    if ((tileEntity4 = uc.C.getTileEntity(add3)) instanceof TileEntityChest && ((TileEntityChest)tileEntity4).getChestType() == tileEntityChest.getChestType()) {
                        o = tileEntityChest;
                        o2 = tileEntity4;
                        blockPos = d;
                        blockPos2 = add3;
                    }
                    final BlockPos blockPos6 = d;
                    final int n10 = -1;
                    final int n11 = 0;
                    final BlockPos add4 = blockPos6.add(n10, n11, n11);
                    final TileEntity tileEntity5;
                    if ((tileEntity5 = uc.C.getTileEntity(add4)) instanceof TileEntityChest && ((TileEntityChest)tileEntity5).getChestType() == tileEntityChest.getChestType()) {
                        o = tileEntity5;
                        o2 = tileEntityChest;
                        blockPos = add4;
                        blockPos2 = d;
                    }
                    if (o == null || o2 == null || blockPos == null || blockPos2 == null) {
                        Qd.M(ub.d, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedWarning.failedToFindDoubleChest", new Object[0]);
                        return true;
                    }
                    uc.M(uc.h, (IInventory)o, 0);
                    uc.M(uc.h, (IInventory)o2, 27);
                    final String s2 = "rKcFzAULsWb";
                    final BlockPos blockPos7 = blockPos2;
                    final IInventory inventory = (IInventory)o2;
                    uc.M(blockPos, (TileEntity)o);
                    uc.M(blockPos7, (TileEntity)inventory);
                    s3 = N.M(s2);
                }
                else {
                    uc.M(uc.h, (IInventory)tileEntity, 0);
                    uc.M(uc.D, tileEntity);
                    s3 = "singleChest";
                }
            }
            else if (uc.h instanceof ContainerChest && tileEntity instanceof TileEntityEnderChest) {
                InventoryEnderChest inventoryEnderChest;
                int n13;
                for (int sizeInventory = (inventoryEnderChest = uc.a.getInventoryEnderChest()).getSizeInventory(), size = uc.h.inventorySlots.size(), n12 = n13 = 0; n12 < size && n13 < sizeInventory; n12 = ++n13) {
                    final Slot slot3;
                    if ((slot3 = uc.h.getSlot(n13)).getHasStack()) {
                        inventoryEnderChest.setInventorySlotContents(n13, slot3.getStack());
                    }
                }
                s3 = "enderChest";
            }
            else if (uc.h instanceof ContainerBrewingStand && tileEntity instanceof TileEntityBrewingStand) {
                final IInventory inventory2 = Hd.D(uc.h, IInventory.class);
                uc.M(uc.h, (IInventory)tileEntity, 0);
                uc.M(inventory2, (IInventory)tileEntity);
                uc.M(uc.D, tileEntity);
                s3 = "brewingStand";
            }
            else if (uc.h instanceof ContainerDispenser && tileEntity instanceof TileEntityDispenser) {
                uc.M(uc.h, (IInventory)tileEntity, 0);
                uc.M(uc.D, tileEntity);
                s3 = "dispenser";
            }
            else if (uc.h instanceof ContainerFurnace && tileEntity instanceof TileEntityFurnace) {
                final IInventory inventory3 = Hd.D(uc.h, IInventory.class);
                uc.M(uc.h, (IInventory)tileEntity, 0);
                uc.M(inventory3, (IInventory)tileEntity);
                uc.M(uc.D, tileEntity);
                s3 = "furnace";
            }
            else if (uc.h instanceof ContainerHopper && tileEntity instanceof TileEntityHopper) {
                uc.M(uc.h, (IInventory)tileEntity, 0);
                uc.M(uc.D, tileEntity);
                s3 = "hopper";
            }
            else {
                if (!(uc.h instanceof ContainerBeacon) || !(tileEntity instanceof TileEntityBeacon)) {
                    return false;
                }
                final IInventory tileEntity6 = ((ContainerBeacon)uc.h).getTileEntity();
                final TileEntityBeacon tileEntityBeacon = (TileEntityBeacon)tileEntity;
                uc.M(uc.h, (IInventory)tileEntityBeacon, 0);
                final String s4 = "2^1X?U";
                uc.M(tileEntity6, (IInventory)tileEntityBeacon);
                uc.M(uc.D, tileEntity);
                s3 = Rd.M(s4);
            }
            Qd.M(ub.i, new StringBuilder().insert(0, "com.client.glowclient.utils.mod.imports.wdl.messages.onGuiClosedInfo.savedTileEntity.").append(s3).toString(), new Object[0]);
            return true;
        }
    }
    
    public static void M(final String s) {
        if (uc.G && s.startsWith("Seed: ")) {
            final String substring = s.substring(6);
            uc.k.setProperty("RandomSeed", substring);
            if (uc.k.getProperty("MapGenerator", "void").equals("void")) {
                uc.k.setProperty("MapGenerator", "default");
                uc.k.setProperty("GeneratorName", "default");
                uc.k.setProperty("GeneratorVersion", "1");
                uc.k.setProperty("GeneratorOptions", "");
                Qd.M(ub.I, "com.client.glowclient.utils.mod.imports.wdl.messages.generalInfo.seedAndGenSet", substring);
                return;
            }
            Qd.M(ub.I, "com.client.glowclient.utils.mod.imports.wdl.messages.generalInfo.seedSet", substring);
        }
    }
    
    public static void M(final String s, final byte[] array) {
        WA.M(s, array);
    }
    
    public static void M(final Entity entity) {
        if (uc.G && entity != null && WA.M(entity.chunkCoordX, entity.chunkCoordZ)) {
            if (!iC.M(entity)) {
                Qd.M(ub.g, "com.client.glowclient.utils.mod.imports.wdl.messages.removeEntity.allowingRemoveUserPref", entity);
                return;
            }
            final int m;
            if ((m = iC.M(entity)) < 0) {
                Qd.M(ub.g, "com.client.glowclient.utils.mod.imports.wdl.messages.removeEntity.allowingRemoveUnrecognizedDistance", entity);
                return;
            }
            final double distance;
            if ((distance = entity.getDistance(uc.a.posX, entity.posY, uc.a.posZ)) > m) {
                Qd.M(ub.g, "com.client.glowclient.utils.mod.imports.wdl.messages.removeEntity.savingDistance", entity, distance, m);
                entity.chunkCoordX = MathHelper.floor(entity.posX / 16.0);
                entity.chunkCoordZ = MathHelper.floor(entity.posZ / 16.0);
                uc.e.put((Object)new ChunkPos(entity.chunkCoordX, entity.chunkCoordZ), (Object)entity);
                return;
            }
            Qd.M(ub.g, "com.client.glowclient.utils.mod.imports.wdl.messages.removeEntity.allowingRemoveDistance", entity, distance, m);
        }
    }
}
