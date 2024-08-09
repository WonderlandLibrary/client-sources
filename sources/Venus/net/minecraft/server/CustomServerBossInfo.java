/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.server.ServerBossInfo;

public class CustomServerBossInfo
extends ServerBossInfo {
    private final ResourceLocation id;
    private final Set<UUID> players = Sets.newHashSet();
    private int value;
    private int max = 100;

    public CustomServerBossInfo(ResourceLocation resourceLocation, ITextComponent iTextComponent) {
        super(iTextComponent, BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
        this.id = resourceLocation;
        this.setPercent(0.0f);
    }

    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void addPlayer(ServerPlayerEntity serverPlayerEntity) {
        super.addPlayer(serverPlayerEntity);
        this.players.add(serverPlayerEntity.getUniqueID());
    }

    public void addPlayer(UUID uUID) {
        this.players.add(uUID);
    }

    @Override
    public void removePlayer(ServerPlayerEntity serverPlayerEntity) {
        super.removePlayer(serverPlayerEntity);
        this.players.remove(serverPlayerEntity.getUniqueID());
    }

    @Override
    public void removeAllPlayers() {
        super.removeAllPlayers();
        this.players.clear();
    }

    public int getValue() {
        return this.value;
    }

    public int getMax() {
        return this.max;
    }

    public void setValue(int n) {
        this.value = n;
        this.setPercent(MathHelper.clamp((float)n / (float)this.max, 0.0f, 1.0f));
    }

    public void setMax(int n) {
        this.max = n;
        this.setPercent(MathHelper.clamp((float)this.value / (float)n, 0.0f, 1.0f));
    }

    public final ITextComponent getFormattedName() {
        return TextComponentUtils.wrapWithSquareBrackets(this.getName()).modifyStyle(this::lambda$getFormattedName$0);
    }

    public boolean setPlayers(Collection<ServerPlayerEntity> collection) {
        boolean bl;
        HashSet<UUID> hashSet = Sets.newHashSet();
        HashSet<ServerPlayerEntity> hashSet2 = Sets.newHashSet();
        for (UUID object : this.players) {
            bl = false;
            for (ServerPlayerEntity serverPlayerEntity : collection) {
                if (!serverPlayerEntity.getUniqueID().equals(object)) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            hashSet.add(object);
        }
        for (ServerPlayerEntity serverPlayerEntity : collection) {
            bl = false;
            for (UUID uUID : this.players) {
                if (!serverPlayerEntity.getUniqueID().equals(uUID)) continue;
                bl = true;
                break;
            }
            if (bl) continue;
            hashSet2.add(serverPlayerEntity);
        }
        for (UUID uUID : hashSet) {
            for (ServerPlayerEntity serverPlayerEntity : this.getPlayers()) {
                if (!serverPlayerEntity.getUniqueID().equals(uUID)) continue;
                this.removePlayer(serverPlayerEntity);
                break;
            }
            this.players.remove(uUID);
        }
        for (ServerPlayerEntity serverPlayerEntity : hashSet2) {
            this.addPlayer(serverPlayerEntity);
        }
        return !hashSet.isEmpty() || !hashSet2.isEmpty();
    }

    public CompoundNBT write() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Name", ITextComponent.Serializer.toJson(this.name));
        compoundNBT.putBoolean("Visible", this.isVisible());
        compoundNBT.putInt("Value", this.value);
        compoundNBT.putInt("Max", this.max);
        compoundNBT.putString("Color", this.getColor().getName());
        compoundNBT.putString("Overlay", this.getOverlay().getName());
        compoundNBT.putBoolean("DarkenScreen", this.shouldDarkenSky());
        compoundNBT.putBoolean("PlayBossMusic", this.shouldPlayEndBossMusic());
        compoundNBT.putBoolean("CreateWorldFog", this.shouldCreateFog());
        ListNBT listNBT = new ListNBT();
        for (UUID uUID : this.players) {
            listNBT.add(NBTUtil.func_240626_a_(uUID));
        }
        compoundNBT.put("Players", listNBT);
        return compoundNBT;
    }

    public static CustomServerBossInfo read(CompoundNBT compoundNBT, ResourceLocation resourceLocation) {
        CustomServerBossInfo customServerBossInfo = new CustomServerBossInfo(resourceLocation, ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("Name")));
        customServerBossInfo.setVisible(compoundNBT.getBoolean("Visible"));
        customServerBossInfo.setValue(compoundNBT.getInt("Value"));
        customServerBossInfo.setMax(compoundNBT.getInt("Max"));
        customServerBossInfo.setColor(BossInfo.Color.byName(compoundNBT.getString("Color")));
        customServerBossInfo.setOverlay(BossInfo.Overlay.byName(compoundNBT.getString("Overlay")));
        customServerBossInfo.setDarkenSky(compoundNBT.getBoolean("DarkenScreen"));
        customServerBossInfo.setPlayEndBossMusic(compoundNBT.getBoolean("PlayBossMusic"));
        customServerBossInfo.setCreateFog(compoundNBT.getBoolean("CreateWorldFog"));
        ListNBT listNBT = compoundNBT.getList("Players", 11);
        for (int i = 0; i < listNBT.size(); ++i) {
            customServerBossInfo.addPlayer(NBTUtil.readUniqueId(listNBT.get(i)));
        }
        return customServerBossInfo;
    }

    public void onPlayerLogin(ServerPlayerEntity serverPlayerEntity) {
        if (this.players.contains(serverPlayerEntity.getUniqueID())) {
            this.addPlayer(serverPlayerEntity);
        }
    }

    public void onPlayerLogout(ServerPlayerEntity serverPlayerEntity) {
        super.removePlayer(serverPlayerEntity);
    }

    private Style lambda$getFormattedName$0(Style style) {
        return style.setFormatting(this.getColor().getFormatting()).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(this.getId().toString()))).setInsertion(this.getId().toString());
    }
}

