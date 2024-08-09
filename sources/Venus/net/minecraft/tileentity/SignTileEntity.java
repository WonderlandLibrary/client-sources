/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;

public class SignTileEntity
extends TileEntity {
    private final ITextComponent[] signText = new ITextComponent[]{StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY};
    private boolean isEditable = true;
    private PlayerEntity player;
    private final IReorderingProcessor[] renderText = new IReorderingProcessor[4];
    private DyeColor textColor = DyeColor.BLACK;

    public SignTileEntity() {
        super(TileEntityType.SIGN);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        for (int i = 0; i < 4; ++i) {
            String string = ITextComponent.Serializer.toJson(this.signText[i]);
            compoundNBT.putString("Text" + (i + 1), string);
        }
        compoundNBT.putString("Color", this.textColor.getTranslationKey());
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        this.isEditable = false;
        super.read(blockState, compoundNBT);
        this.textColor = DyeColor.byTranslationKey(compoundNBT.getString("Color"), DyeColor.BLACK);
        for (int i = 0; i < 4; ++i) {
            String string = compoundNBT.getString("Text" + (i + 1));
            IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(string.isEmpty() ? "\"\"" : string);
            if (this.world instanceof ServerWorld) {
                try {
                    this.signText[i] = TextComponentUtils.func_240645_a_(this.getCommandSource(null), iFormattableTextComponent, null, 0);
                } catch (CommandSyntaxException commandSyntaxException) {
                    this.signText[i] = iFormattableTextComponent;
                }
            } else {
                this.signText[i] = iFormattableTextComponent;
            }
            this.renderText[i] = null;
        }
    }

    public ITextComponent getText(int n) {
        return this.signText[n];
    }

    public void setText(int n, ITextComponent iTextComponent) {
        this.signText[n] = iTextComponent;
        this.renderText[n] = null;
    }

    @Nullable
    public IReorderingProcessor func_242686_a(int n, Function<ITextComponent, IReorderingProcessor> function) {
        if (this.renderText[n] == null && this.signText[n] != null) {
            this.renderText[n] = function.apply(this.signText[n]);
        }
        return this.renderText[n];
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 9, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public boolean onlyOpsCanSetNbt() {
        return false;
    }

    public boolean getIsEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean bl) {
        this.isEditable = bl;
        if (!bl) {
            this.player = null;
        }
    }

    public void setPlayer(PlayerEntity playerEntity) {
        this.player = playerEntity;
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public boolean executeCommand(PlayerEntity playerEntity) {
        for (ITextComponent iTextComponent : this.signText) {
            ClickEvent clickEvent;
            Style style;
            Style style2 = style = iTextComponent == null ? null : iTextComponent.getStyle();
            if (style == null || style.getClickEvent() == null || (clickEvent = style.getClickEvent()).getAction() != ClickEvent.Action.RUN_COMMAND) continue;
            playerEntity.getServer().getCommandManager().handleCommand(this.getCommandSource((ServerPlayerEntity)playerEntity), clickEvent.getValue());
        }
        return false;
    }

    public CommandSource getCommandSource(@Nullable ServerPlayerEntity serverPlayerEntity) {
        String string = serverPlayerEntity == null ? "Sign" : serverPlayerEntity.getName().getString();
        ITextComponent iTextComponent = serverPlayerEntity == null ? new StringTextComponent("Sign") : serverPlayerEntity.getDisplayName();
        return new CommandSource(ICommandSource.DUMMY, Vector3d.copyCentered(this.pos), Vector2f.ZERO, (ServerWorld)this.world, 2, string, iTextComponent, this.world.getServer(), serverPlayerEntity);
    }

    public DyeColor getTextColor() {
        return this.textColor;
    }

    public boolean setTextColor(DyeColor dyeColor) {
        if (dyeColor != this.getTextColor()) {
            this.textColor = dyeColor;
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
            return false;
        }
        return true;
    }
}

