package net.futureclient.client.modules.world;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;
import net.futureclient.client.tA;
import net.futureclient.client.pg;
import net.futureclient.client.modules.render.Fullbright;
import net.minecraft.init.Blocks;
import java.lang.reflect.Field;
import net.futureclient.client.oh;
import net.futureclient.client.Z;
import net.futureclient.client.modules.world.wallhack.Listener3;
import net.futureclient.client.modules.world.wallhack.Listener2;
import net.futureclient.client.modules.world.wallhack.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.futureclient.client.Ja;
import net.futureclient.client.R;
import net.minecraft.block.Block;
import java.util.List;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class Wallhack extends Ea
{
    public NumberValue opacity;
    private int M;
    public List<Block> d;
    private List<Block> a;
    private R<Ja.v> mode;
    private boolean k;
    
    public Wallhack() {
        super("Wallhack", new String[] { "Wallhack", "X-Ray", "wh" }, true, -2525659, Category.WORLD);
        this.mode = new R<Ja.v>(Ja.v.D, new String[] { "Mode", "Type" });
        this.opacity = new NumberValue(120.0f, 0.0f, 255.0f, 1, new String[] { "Opacity", "Op", "Oapcity", "Oapcit", "Opacit", "o" });
        final int n = 2;
        final boolean k = false;
        this.d = new ArrayList<Block>();
        this.a = new ArrayList<Block>();
        this.k = k;
        this.i();
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.opacity;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this) });
        new Z(this, "wallhack_blocks.txt");
    }
    
    public void B() {
        Wallhack wallhack;
        try {
            final Field declaredField;
            final Field field = declaredField = Class.forName("net.minecraftforge.common.ForgeModContainer", (boolean)(1 != 0), this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
            final boolean accessible = field.isAccessible();
            declaredField.setAccessible(true);
            this.k = field.getBoolean(null);
            declaredField.set(null, false);
            declaredField.setAccessible(accessible);
            wallhack = this;
        }
        catch (Exception ex) {
            wallhack = this;
        }
        wallhack.M = this.opacity.B().intValue();
        Wallhack.D.renderChunksMany = false;
        oh.M();
        super.B();
    }
    
    private void C() {
        this.d.add(Blocks.EMERALD_ORE);
        this.d.add(Blocks.GOLD_ORE);
        this.d.add(Blocks.IRON_ORE);
        this.d.add(Blocks.COAL_ORE);
        this.d.add(Blocks.LAPIS_ORE);
        this.d.add(Blocks.DIAMOND_ORE);
        this.d.add(Blocks.REDSTONE_ORE);
        this.d.add(Blocks.LIT_REDSTONE_ORE);
        this.d.add(Blocks.TNT);
        this.d.add(Blocks.EMERALD_ORE);
        this.d.add(Blocks.FURNACE);
        this.d.add(Blocks.LIT_FURNACE);
        this.d.add(Blocks.DIAMOND_BLOCK);
        this.d.add(Blocks.IRON_BLOCK);
        this.d.add(Blocks.GOLD_BLOCK);
        this.d.add(Blocks.EMERALD_BLOCK);
        this.d.add(Blocks.QUARTZ_ORE);
        this.d.add((Block)Blocks.BEACON);
        this.d.add(Blocks.MOB_SPAWNER);
    }
    
    public void b() {
        try {
            final Field declaredField = Class.forName("net.minecraftforge.common.ForgeModContainer", true, this.getClass().getClassLoader()).getDeclaredField("forgeLightPipelineEnabled");
            final boolean accessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            declaredField.set(null, this.k);
            declaredField.setAccessible(accessible);
        }
        catch (Exception ex) {}
        final Fullbright fullbright;
        if ((fullbright = (Fullbright)pg.M().M().M((Class)tA.class)) != null && !fullbright.M()) {
            Wallhack.D.gameSettings.gammaSetting = 1.0f;
        }
        Wallhack.D.renderChunksMany = true;
        oh.b();
        super.b();
    }
    
    private void i() {
        this.a.add(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
        this.a.add(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
        this.a.add(Blocks.STONE_PRESSURE_PLATE);
        this.a.add(Blocks.WOODEN_PRESSURE_PLATE);
        this.a.add(Blocks.STONE_BUTTON);
        this.a.add(Blocks.WOODEN_BUTTON);
        this.a.add(Blocks.LEVER);
        this.a.add(Blocks.COMMAND_BLOCK);
        this.a.add(Blocks.CHAIN_COMMAND_BLOCK);
        this.a.add(Blocks.REPEATING_COMMAND_BLOCK);
        this.a.add((Block)Blocks.DAYLIGHT_DETECTOR);
        this.a.add((Block)Blocks.DAYLIGHT_DETECTOR_INVERTED);
        this.a.add(Blocks.DISPENSER);
        this.a.add(Blocks.DROPPER);
        this.a.add((Block)Blocks.HOPPER);
        this.a.add(Blocks.OBSERVER);
        this.a.add(Blocks.TRAPDOOR);
        this.a.add(Blocks.IRON_TRAPDOOR);
        this.a.add(Blocks.REDSTONE_BLOCK);
        this.a.add(Blocks.REDSTONE_LAMP);
        this.a.add(Blocks.REDSTONE_TORCH);
        this.a.add(Blocks.UNLIT_REDSTONE_TORCH);
        this.a.add((Block)Blocks.REDSTONE_WIRE);
        this.a.add((Block)Blocks.POWERED_REPEATER);
        this.a.add((Block)Blocks.UNPOWERED_REPEATER);
        this.a.add((Block)Blocks.POWERED_COMPARATOR);
        this.a.add((Block)Blocks.UNPOWERED_COMPARATOR);
        this.a.add(Blocks.LIT_REDSTONE_LAMP);
        this.a.add(Blocks.REDSTONE_ORE);
        this.a.add(Blocks.LIT_REDSTONE_ORE);
        this.a.add((Block)Blocks.ACACIA_DOOR);
        this.a.add((Block)Blocks.DARK_OAK_DOOR);
        this.a.add((Block)Blocks.BIRCH_DOOR);
        this.a.add((Block)Blocks.JUNGLE_DOOR);
        this.a.add((Block)Blocks.OAK_DOOR);
        this.a.add((Block)Blocks.SPRUCE_DOOR);
        this.a.add((Block)Blocks.DARK_OAK_DOOR);
        this.a.add((Block)Blocks.IRON_DOOR);
        this.a.add(Blocks.OAK_FENCE);
        this.a.add(Blocks.SPRUCE_FENCE);
        this.a.add(Blocks.BIRCH_FENCE);
        this.a.add(Blocks.JUNGLE_FENCE);
        this.a.add(Blocks.DARK_OAK_FENCE);
        this.a.add(Blocks.ACACIA_FENCE);
        this.a.add(Blocks.OAK_FENCE_GATE);
        this.a.add(Blocks.SPRUCE_FENCE_GATE);
        this.a.add(Blocks.BIRCH_FENCE_GATE);
        this.a.add(Blocks.JUNGLE_FENCE_GATE);
        this.a.add(Blocks.DARK_OAK_FENCE_GATE);
        this.a.add(Blocks.ACACIA_FENCE_GATE);
        this.a.add(Blocks.JUKEBOX);
        this.a.add(Blocks.NOTEBLOCK);
        this.a.add((Block)Blocks.PISTON);
        this.a.add((Block)Blocks.PISTON_EXTENSION);
        this.a.add((Block)Blocks.PISTON_HEAD);
        this.a.add((Block)Blocks.STICKY_PISTON);
        this.a.add(Blocks.TNT);
        this.a.add(Blocks.SLIME_BLOCK);
        this.a.add(Blocks.TRIPWIRE);
        this.a.add((Block)Blocks.TRIPWIRE_HOOK);
        this.a.add(Blocks.RAIL);
        this.a.add(Blocks.ACTIVATOR_RAIL);
        this.a.add(Blocks.DETECTOR_RAIL);
        this.a.add(Blocks.GOLDEN_RAIL);
    }
    
    public static int M(final Wallhack wallhack, final int m) {
        return wallhack.M = m;
    }
    
    public static boolean M(final Wallhack wallhack, final Block block) {
        return wallhack.M(block);
    }
    
    public static Minecraft getMinecraft() {
        return Wallhack.D;
    }
    
    public static void M(final Wallhack wallhack) {
        wallhack.C();
    }
    
    private boolean M(final Block block) {
        if (this.mode.M().equals((Object)Ja.v.D)) {
            return this.d.contains(block);
        }
        return this.a.contains(block);
    }
    
    public boolean M(final Block block, final BlockPos blockPos) {
        if (this.mode.M().equals((Object)Ja.v.D)) {
            return this.d.contains(block);
        }
        final boolean contains = this.a.contains(block);
        boolean b = false;
        int i = 0;
        int n = 0;
        while (i < 4) {
            final EnumFacing byHorizontalIndex = EnumFacing.byHorizontalIndex(n);
            final IBlockState blockState;
            if ((blockState = Wallhack.D.world.getBlockState(blockPos.add(byHorizontalIndex.getOpposite().getDirectionVec()))).getBlock() instanceof BlockRedstoneTorch && blockState.getValue((IProperty)BlockTorch.FACING) == byHorizontalIndex.getOpposite()) {
                b = true;
            }
            i = ++n;
        }
        boolean b2 = false;
        final IBlockState blockState2;
        if ((blockState2 = Wallhack.D.world.getBlockState(blockPos.add(EnumFacing.DOWN.getOpposite().getDirectionVec()))).getBlock() instanceof BlockRedstoneTorch && blockState2.getValue((IProperty)BlockTorch.FACING) != EnumFacing.UP) {
            b2 = true;
        }
        final boolean b3 = b;
        final boolean b4 = b2;
        final WorldClient world = Wallhack.D.world;
        final int n2 = 1;
        final int n3 = 0;
        final boolean b5 = this.a.contains(world.getBlockState(blockPos.add(n3, n2, n3)).getBlock()) && !b4;
        return contains || b3;
    }
    
    public static int M(final Wallhack wallhack) {
        return wallhack.M;
    }
    
    public static R M(final Wallhack wallhack) {
        return wallhack.mode;
    }
}
