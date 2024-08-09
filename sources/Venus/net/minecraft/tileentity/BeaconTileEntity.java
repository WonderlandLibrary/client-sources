/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.BeaconContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LockCode;
import net.minecraft.world.gen.Heightmap;

public class BeaconTileEntity
extends TileEntity
implements INamedContainerProvider,
ITickableTileEntity {
    public static final Effect[][] EFFECTS_LIST = new Effect[][]{{Effects.SPEED, Effects.HASTE}, {Effects.RESISTANCE, Effects.JUMP_BOOST}, {Effects.STRENGTH}, {Effects.REGENERATION}};
    private static final Set<Effect> VALID_EFFECTS = Arrays.stream(EFFECTS_LIST).flatMap(Arrays::stream).collect(Collectors.toSet());
    private List<BeamSegment> beamSegments = Lists.newArrayList();
    private List<BeamSegment> beamColorSegments = Lists.newArrayList();
    private int levels;
    private int beaconSize = -1;
    @Nullable
    private Effect primaryEffect;
    @Nullable
    private Effect secondaryEffect;
    @Nullable
    private ITextComponent customName;
    private LockCode lockCode = LockCode.EMPTY_CODE;
    private final IIntArray beaconData = new IIntArray(this){
        final BeaconTileEntity this$0;
        {
            this.this$0 = beaconTileEntity;
        }

        @Override
        public int get(int n) {
            switch (n) {
                case 0: {
                    return this.this$0.levels;
                }
                case 1: {
                    return Effect.getId(this.this$0.primaryEffect);
                }
                case 2: {
                    return Effect.getId(this.this$0.secondaryEffect);
                }
            }
            return 1;
        }

        @Override
        public void set(int n, int n2) {
            switch (n) {
                case 0: {
                    this.this$0.levels = n2;
                    break;
                }
                case 1: {
                    if (!this.this$0.world.isRemote && !this.this$0.beamSegments.isEmpty()) {
                        this.this$0.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT);
                    }
                    this.this$0.primaryEffect = BeaconTileEntity.isBeaconEffect(n2);
                    break;
                }
                case 2: {
                    this.this$0.secondaryEffect = BeaconTileEntity.isBeaconEffect(n2);
                }
            }
        }

        @Override
        public int size() {
            return 0;
        }
    };

    public BeaconTileEntity() {
        super(TileEntityType.BEACON);
    }

    @Override
    public void tick() {
        Object object;
        int n;
        BlockPos blockPos;
        int n2 = this.pos.getX();
        int n3 = this.pos.getY();
        int n4 = this.pos.getZ();
        if (this.beaconSize < n3) {
            blockPos = this.pos;
            this.beamColorSegments = Lists.newArrayList();
            this.beaconSize = blockPos.getY() - 1;
        } else {
            blockPos = new BlockPos(n2, this.beaconSize + 1, n4);
        }
        BeamSegment beamSegment = this.beamColorSegments.isEmpty() ? null : this.beamColorSegments.get(this.beamColorSegments.size() - 1);
        int n5 = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, n2, n4);
        for (n = 0; n < 10 && blockPos.getY() <= n5; ++n) {
            BlockState blockState = this.world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block instanceof IBeaconBeamColorProvider) {
                object = ((IBeaconBeamColorProvider)((Object)block)).getColor().getColorComponentValues();
                if (this.beamColorSegments.size() <= 1) {
                    beamSegment = new BeamSegment((float[])object);
                    this.beamColorSegments.add(beamSegment);
                } else if (beamSegment != null) {
                    if (Arrays.equals((float[])object, beamSegment.colors)) {
                        beamSegment.incrementHeight();
                    } else {
                        beamSegment = new BeamSegment(new float[]{(beamSegment.colors[0] + object[0]) / 2.0f, (beamSegment.colors[1] + object[1]) / 2.0f, (beamSegment.colors[2] + object[2]) / 2.0f});
                        this.beamColorSegments.add(beamSegment);
                    }
                }
            } else {
                if (beamSegment == null || blockState.getOpacity(this.world, blockPos) >= 15 && block != Blocks.BEDROCK) {
                    this.beamColorSegments.clear();
                    this.beaconSize = n5;
                    break;
                }
                beamSegment.incrementHeight();
            }
            blockPos = blockPos.up();
            ++this.beaconSize;
        }
        n = this.levels;
        if (this.world.getGameTime() % 80L == 0L) {
            if (!this.beamSegments.isEmpty()) {
                this.checkBeaconLevel(n2, n3, n4);
            }
            if (this.levels > 0 && !this.beamSegments.isEmpty()) {
                this.addEffectsToPlayers();
                this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT);
            }
        }
        if (this.beaconSize >= n5) {
            this.beaconSize = -1;
            boolean bl = n > 0;
            this.beamSegments = this.beamColorSegments;
            if (!this.world.isRemote) {
                boolean bl2;
                boolean bl3 = bl2 = this.levels > 0;
                if (!bl && bl2) {
                    this.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE);
                    object = this.world.getEntitiesWithinAABB(ServerPlayerEntity.class, new AxisAlignedBB(n2, n3, n4, n2, n3 - 4, n4).grow(10.0, 5.0, 10.0)).iterator();
                    while (object.hasNext()) {
                        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)object.next();
                        CriteriaTriggers.CONSTRUCT_BEACON.trigger(serverPlayerEntity, this);
                    }
                } else if (bl && !bl2) {
                    this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
                }
            }
        }
    }

    private void checkBeaconLevel(int n, int n2, int n3) {
        int n4;
        this.levels = 0;
        int n5 = 1;
        while (n5 <= 4 && (n4 = n2 - n5) >= 0) {
            boolean bl = true;
            block1: for (int i = n - n5; i <= n + n5 && bl; ++i) {
                for (int j = n3 - n5; j <= n3 + n5; ++j) {
                    if (this.world.getBlockState(new BlockPos(i, n4, j)).isIn(BlockTags.BEACON_BASE_BLOCKS)) continue;
                    bl = false;
                    continue block1;
                }
            }
            if (!bl) break;
            this.levels = n5++;
        }
    }

    @Override
    public void remove() {
        this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
        super.remove();
    }

    private void addEffectsToPlayers() {
        if (!this.world.isRemote && this.primaryEffect != null) {
            double d = this.levels * 10 + 10;
            int n = 0;
            if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
                n = 1;
            }
            int n2 = (9 + this.levels * 2) * 20;
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.pos).grow(d).expand(0.0, this.world.getHeight(), 0.0);
            List<PlayerEntity> list = this.world.getEntitiesWithinAABB(PlayerEntity.class, axisAlignedBB);
            for (PlayerEntity playerEntity : list) {
                playerEntity.addPotionEffect(new EffectInstance(this.primaryEffect, n2, n, true, true));
            }
            if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null) {
                for (PlayerEntity playerEntity : list) {
                    playerEntity.addPotionEffect(new EffectInstance(this.secondaryEffect, n2, 0, true, true));
                }
            }
        }
    }

    public void playSound(SoundEvent soundEvent) {
        this.world.playSound(null, this.pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public List<BeamSegment> getBeamSegments() {
        return this.levels == 0 ? ImmutableList.of() : this.beamSegments;
    }

    public int getLevels() {
        return this.levels;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 256.0;
    }

    @Nullable
    private static Effect isBeaconEffect(int n) {
        Effect effect = Effect.get(n);
        return VALID_EFFECTS.contains(effect) ? effect : null;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.primaryEffect = BeaconTileEntity.isBeaconEffect(compoundNBT.getInt("Primary"));
        this.secondaryEffect = BeaconTileEntity.isBeaconEffect(compoundNBT.getInt("Secondary"));
        if (compoundNBT.contains("CustomName", 1)) {
            this.customName = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("CustomName"));
        }
        this.lockCode = LockCode.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putInt("Primary", Effect.getId(this.primaryEffect));
        compoundNBT.putInt("Secondary", Effect.getId(this.secondaryEffect));
        compoundNBT.putInt("Levels", this.levels);
        if (this.customName != null) {
            compoundNBT.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        this.lockCode.write(compoundNBT);
        return compoundNBT;
    }

    public void setCustomName(@Nullable ITextComponent iTextComponent) {
        this.customName = iTextComponent;
    }

    @Override
    @Nullable
    public Container createMenu(int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return LockableTileEntity.canUnlock(playerEntity, this.lockCode, this.getDisplayName()) ? new BeaconContainer(n, playerInventory, this.beaconData, IWorldPosCallable.of(this.world, this.getPos())) : null;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("container.beacon");
    }

    public static class BeamSegment {
        private final float[] colors;
        private int height;

        public BeamSegment(float[] fArray) {
            this.colors = fArray;
            this.height = 1;
        }

        protected void incrementHeight() {
            ++this.height;
        }

        public float[] getColors() {
            return this.colors;
        }

        public int getHeight() {
            return this.height;
        }
    }
}

