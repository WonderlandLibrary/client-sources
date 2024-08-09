/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.List;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;

public class VClipCommand
implements Command,
CommandWithAdvice {
    private final Prefix prefix;
    private final Logger logger;
    private final Minecraft mc;

    @Override
    public void execute(Parameters parameters) {
        String string;
        BlockPos blockPos = this.mc.player.getPosition();
        float f = switch (string = parameters.asString(0).orElseThrow(VClipCommand::lambda$execute$0)) {
            case "up" -> this.findOffset(blockPos, false);
            case "down" -> this.findOffset(blockPos, true);
            default -> this.parseOffset(string);
        };
        if (f != 0.0f) {
            boolean bl;
            int n = this.getElytraSlot();
            boolean bl2 = bl = n != -1 && n != -2;
            if (bl) {
                this.switchElytra(n, false);
            }
            this.teleport(f, bl);
            if (bl) {
                this.switchElytra(n, true);
            }
        } else {
            this.logger.log(TextFormatting.RED + "\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0432\u044b\u043f\u043e\u043b\u043d\u0438\u0442\u044c \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u044e.");
        }
    }

    @Override
    public String name() {
        return "vclip";
    }

    @Override
    public String description() {
        return "\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0442 \u0432\u0432\u0435\u0440\u0445/\u0432\u043d\u0438\u0437 \u043f\u043e \u0432\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u0438";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + "vclip up - \u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u044f \u0432\u0432\u0435\u0440\u0445 \u0434\u043e \u0431\u043b\u0438\u0436\u0430\u0439\u0448\u0435\u0433\u043e \u0441\u0432\u043e\u0431\u043e\u0434\u043d\u043e\u0433\u043e \u043f\u0440\u043e\u0441\u0442\u0440\u0430\u043d\u0441\u0442\u0432\u0430"), (Object)(string + "vclip down - \u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u044f \u0432\u043d\u0438\u0437 \u0434\u043e \u0431\u043b\u0438\u0436\u0430\u0439\u0448\u0435\u0433\u043e \u0441\u0432\u043e\u0431\u043e\u0434\u043d\u043e\u0433\u043e \u043f\u0440\u043e\u0441\u0442\u0440\u0430\u043d\u0441\u0442\u0432\u0430"), (Object)(string + "vclip <distance> - \u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u044f \u043d\u0430 \u0443\u043a\u0430\u0437\u0430\u043d\u043d\u043e\u0435 \u0440\u0430\u0441\u0441\u0442\u043e\u044f\u043d\u0438\u0435"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "vclip 10"));
    }

    private float findOffset(BlockPos blockPos, boolean bl) {
        int n = bl ? 3 : -1;
        int n2 = bl ? 255 : -255;
        int n3 = bl ? 1 : -1;
        for (int i = n; i != n2; i += n3) {
            BlockPos blockPos2 = blockPos.add(0, i, 0);
            if (this.mc.world.getBlockState(blockPos2) == Blocks.AIR.getDefaultState()) {
                return i + (bl ? 1 : -1);
            }
            if (this.mc.world.getBlockState(blockPos2) != Blocks.BEDROCK.getDefaultState() || bl) continue;
            this.logger.log(TextFormatting.RED + "\u0422\u0443\u0442 \u043d\u0435\u043b\u044c\u0437\u044f \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u043f\u043e\u0434 \u0437\u0435\u043c\u043b\u044e.");
            return 0.0f;
        }
        return 0.0f;
    }

    private void teleport(float f, boolean bl) {
        if (bl) {
            for (int i = 0; i < 2; ++i) {
                this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY(), this.mc.player.getPosZ(), false));
            }
            this.mc.player.connection.sendPacket(new CEntityActionPacket(this.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
            this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY() + (double)f, this.mc.player.getPosZ(), false));
            this.mc.player.connection.sendPacket(new CEntityActionPacket(this.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
            this.mc.player.setPosition(this.mc.player.getPosX(), this.mc.player.getPosY() + (double)f, this.mc.player.getPosZ());
            String string = f > 1.0f ? "\u0431\u043b\u043e\u043a\u0430" : "\u0431\u043b\u043e\u043a";
            this.logger.log(TextFormatting.GRAY + "\u041f\u043e\u043f\u044b\u0442\u043a\u0430 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u0441 \u044d\u043b\u0438\u0442\u0440\u043e\u0439...");
            this.logger.log(String.format("\u0412\u044b \u0431\u044b\u043b\u0438 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u044b \u043d\u0430 %.1f %s \u043f\u043e \u0432\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u0438", Float.valueOf(f), string));
            return;
        }
        int n = this.calculatePacketsCount(f);
        for (int i = 0; i < n; ++i) {
            this.mc.player.connection.sendPacket(new CPlayerPacket(this.mc.player.isOnGround()));
        }
        this.mc.player.connection.sendPacket(new CPlayerPacket.PositionPacket(this.mc.player.getPosX(), this.mc.player.getPosY() + (double)f, this.mc.player.getPosZ(), false));
        this.mc.player.setPosition(this.mc.player.getPosX(), this.mc.player.getPosY() + (double)f, this.mc.player.getPosZ());
        String string = f > 1.0f ? "\u0431\u043b\u043e\u043a\u0430" : "\u0431\u043b\u043e\u043a";
        this.logger.log(String.format("\u0412\u044b \u0431\u044b\u043b\u0438 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u044b \u043d\u0430 %.1f %s \u043f\u043e \u0432\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u0438", Float.valueOf(f), string));
    }

    private float parseOffset(String string) {
        if (NumberUtils.isNumber(string)) {
            return Float.parseFloat(string);
        }
        this.logger.log(TextFormatting.RED + string + TextFormatting.GRAY + " \u043d\u0435 \u044f\u0432\u043b\u044f\u0435\u0442\u0441\u044f \u0447\u0438\u0441\u043b\u043e\u043c!");
        return 0.0f;
    }

    private int calculatePacketsCount(float f) {
        return Math.max((int)(f / 1000.0f), 3);
    }

    private void switchElytra(int n, boolean bl) {
        int n2 = 6;
        if (bl) {
            this.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, this.mc.player);
            this.mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, this.mc.player);
        } else {
            this.mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, this.mc.player);
            this.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, this.mc.player);
        }
    }

    private int getElytraSlot() {
        for (ItemStack itemStack : this.mc.player.getArmorInventoryList()) {
            if (itemStack.getItem() != Items.ELYTRA) continue;
            return 1;
        }
        int n = -1;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = this.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.ELYTRA) continue;
            n = i;
            break;
        }
        if (n < 9 && n != -1) {
            n += 36;
        }
        return n;
    }

    public VClipCommand(Prefix prefix, Logger logger, Minecraft minecraft) {
        this.prefix = prefix;
        this.logger = logger;
        this.mc = minecraft;
    }

    private static CommandException lambda$execute$0() {
        return new CommandException(TextFormatting.RED + "\u041d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u0443\u043a\u0430\u0437\u0430\u0442\u044c \u043d\u0430\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u0438\u043b\u0438 \u0440\u0430\u0441\u0441\u0442\u043e\u044f\u043d\u0438\u0435.");
    }
}

