package ru.FecuritySQ.module.сражение;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CUseEntityPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventRightClickBlock;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionMode;

import java.util.ArrayList;
import java.util.List;

public class AutoExplosion extends Module {
    private int lastSlot = -1;
    private final List<BlockPos> anchors = new ArrayList<>();
    private final List<EnderCrystalEntity> crystals = new ArrayList<>();

    public OptionBoolList elements = new OptionBoolList("Что взрывать?",
            new OptionBoolean("Якорь возрождения", true),
            new OptionBoolean("Кристалл Края", true));

    String[] modes = {"Обычный", "Автоматический"};
    public OptionMode mode = new OptionMode("Режим", modes, 0);


    public OptionBoolean silent = new OptionBoolean("Незаметно", true);
    public OptionBoolean secure = new OptionBoolean("Безопасный", true);
    public AutoExplosion() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(elements);
        addOption(mode);
        addOption(silent);
        addOption(secure);
    }

    @Override
    public void event(Event e) {
        if(!isEnabled()) return;

        if(e instanceof EventRightClickBlock eventInteractBlock){

            if(mc.player.isHandActive()) return;
            if(mc.player.getHeldItemMainhand().getItem().isFood()) return;
            if(mc.player.getHeldItemMainhand().getItem() instanceof ThrowablePotionItem) return;
            if(mc.player.getHeldItemMainhand().getItem() instanceof PotionItem) return;

            BlockPos pos = eventInteractBlock.pos.add(0, 1, 0);
            if(mode.current().equals("Автоматический")){

                if(hasAllForAnchor() && elements.setting("Якорь возрождения").get()){
                    if(mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() != Items.RESPAWN_ANCHOR){
                        this.lastSlot = mc.player.inventory.currentItem;
                    }
                    for (int i = 0; i < 9; i++) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.RESPAWN_ANCHOR) {
                            if(silent.get()){
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(i));
                            }else mc.player.inventory.currentItem = i;
                        }
                    }
                    mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), Direction.DOWN, pos, false));
                    if(silent.get()){
                        mc.getConnection().sendPacket(new CHeldItemChangePacket(lastSlot));
                    }else mc.player.inventory.currentItem = lastSlot;
                }

                if(hasAllForCrystal() && elements.setting("Кристалл Края").get()){


                    BlockState state = mc.world.getBlockState(pos.add(0,-1,0));

                    boolean flag = state.getBlock() != Blocks.OBSIDIAN;


                        for (int i = 0; i < 9; i++) {
                            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.OBSIDIAN) {
                                if(silent.get()){
                                    mc.getConnection().sendPacket(new CHeldItemChangePacket(i));
                                }else mc.player.inventory.currentItem = i;
                            }
                        }
                        mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), Direction.DOWN, pos, false));


                    for (int i = 0; i < 9; i++) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                            if(silent.get()){
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(i));
                            }else mc.player.inventory.currentItem = i;
                        }
                    }

                    mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(pos.getX(), pos.getY(), pos.getZ()), Direction.DOWN, pos, false));

                    if(silent.get()){
                        mc.getConnection().sendPacket(new CHeldItemChangePacket(lastSlot));
                    }else mc.player.inventory.currentItem = lastSlot;


                }
            }
        }

        if(e instanceof EventUpdate){

            if(elements.setting("Якорь возрождения").get()){
                this.anchors.clear();
                find(6);
                for (BlockPos p : this.anchors) {
                    if(mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() != Items.GLOWSTONE){
                        this.lastSlot = mc.player.inventory.currentItem;
                    }
                    for (int i = 0; i < 9; i++) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GLOWSTONE) {
                            if(silent.get()){
                                mc.getConnection().sendPacket(new CHeldItemChangePacket(i));
                            }else mc.player.inventory.currentItem = i;
                        }
                    }
                    int count = Integer.valueOf(mc.world.getBlockState(p).get(RespawnAnchorBlock.CHARGES));
                    if(count <= 3){

                        if(secure.get()){
                            if(p.getY() >= mc.player.getPosY() + 1){
                                mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(p.getX(), p.getY(), p.getZ()), Direction.DOWN, p, false));
                            }
                        }else{
                            mc.playerController.func_217292_a(mc.player, mc.world, Hand.MAIN_HAND, new BlockRayTraceResult(new Vector3d(p.getX(), p.getY(), p.getZ()), Direction.DOWN, p, false));
                        }

                    }
                    if(count >= 3){
                        if(silent.get()){
                            mc.getConnection().sendPacket(new CHeldItemChangePacket(lastSlot));
                        }else mc.player.inventory.currentItem = lastSlot;
                    }
                }

            }

            if(elements.setting("Кристалл Края").get()){
                this.crystals.clear();

                for(Entity entity : mc.world.getAllEntities()){
                    if(entity instanceof EnderCrystalEntity crystal){
                        if(mc.player.getDistance(crystal) <= 6 && crystal.isAlive()){
                            crystals.add(crystal);
                        }
                    }
                }
                for (EnderCrystalEntity crystal : this.crystals) {

                    if(secure.get()){
                        if(crystal.getPosY() >= mc.player.getPosY() + 1){
                            mc.getConnection().sendPacket(new CUseEntityPacket(crystal, true));
                        }
                    }else  mc.getConnection().sendPacket(new CUseEntityPacket(crystal, true));
                }
            }
        }
    }

    private boolean hasAllForCrystal(){
        boolean firstcheck = false;
        boolean secondCheck = false;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                firstcheck = true;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.OBSIDIAN) {
                secondCheck = true;
            }
        }
        return firstcheck && secondCheck;
    }

    private boolean hasAllForAnchor(){
        boolean firstcheck = false;
        boolean secondCheck = false;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GLOWSTONE) {
                firstcheck = true;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.RESPAWN_ANCHOR) {
                secondCheck = true;
            }
        }
        return firstcheck && secondCheck;
    }

    private void find(int range){
        BlockPos player = mc.player.getPosition();
        for (int y = (int) -Math.min(range, mc.player.getPosY()); y < Math.min(range, 255 - mc.player.getPosY()); ++y)
        {
            for (int x = -range; x < range; ++x)
            {
                for (int z = -range; z < range; ++z)
                {
                   BlockPos pos = player.add(x, y, z);
                    assert this.mc.world != null;
                    if (this.mc.world.getBlockState(pos).getBlock().getDefaultState().toString().equals("Block{minecraft:respawn_anchor}[charges=0]"))
                    {
                        this.anchors.add(pos);
                    }
                }
            }
        }
    }
}
