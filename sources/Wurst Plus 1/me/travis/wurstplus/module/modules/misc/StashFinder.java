package me.travis.wurstplus.module.modules.misc;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.modules.combat.CrystalAura;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.BlockInteractionHelper;
import me.travis.wurstplus.util.WorldUtils;
import net.minecraft.init.Blocks;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Module.Info(name = "Stash Finder", category = Module.Category.MISC)
public class StashFinder extends Module {

    private Setting<Integer> chestCount = this.register(Settings.integerBuilder("Chests Per Chunk").withRange(4, 20).withValue(6).build());
    private Setting<Integer> waitDelay = this.register(Settings.integerBuilder("waitDelay (seconds)").withRange(2, 30).withValue(5).build());
    private List<BlockPos> positions;
    private int wait;

    @Override
    protected void onEnable() {
        this.wait = this.waitDelay.getValue();
        this.positions = NonNullList.create();
        this.positions.clear();
    }

    @Override
    protected void onDisable() {
        this.positions.clear();
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() || mc.world == null) {
            return;
        }
        if (this.wait > this.waitDelay.getValue()*20 && isChestBlocks()) {
            saveCoords();
            this.wait = 0;
        }
        this.wait++;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(CrystalAura.mc.player.posX), Math.floor(CrystalAura.mc.player.posY), Math.floor(CrystalAura.mc.player.posZ));
    }

    public Boolean isChest(BlockPos blockPos) {
        if (mc.world.getBlockState(blockPos).getBlock() == Blocks.CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || mc.world.getBlockState(blockPos).getBlock() == Blocks.TRAPPED_CHEST) {
            return true;
        }
        return false;
    }

    private boolean isChestBlocks() {
        int c = 0;
        this.positions.clear();
        this.positions.addAll((Collection)BlockInteractionHelper.getSphere(getPlayerPos(), 256, 256, false, true, 0).stream().filter(this::isChest).collect(Collectors.toList()));
        for (BlockPos bp : this.positions) {
            c++;
        }
        if (!this.positions.isEmpty() && c > this.chestCount.getValue()) {
            return true;
        }
        return false;
    }

    public void saveCoords() {
        final Vec3d playerPos = new Vec3d((double) (int) mc.player.posX, (double) (int) mc.player.posY, (double) (int) mc.player.posZ);
        saveFile(WorldUtils.vectorToString(playerPos, new boolean[0]));
    }

    public void saveFile(String pos) {
        Command.sendChatMessage("found a thing, saving file");
        try {
            File file = new File("./wurst+_stash.txt");
            file.getParentFile().mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
            writer.println("Found a stash @ "+pos);
            writer.close();
        } catch (Exception e) {
            Command.sendChatMessage("Error saving file: "+e);
        }
        
    }

}