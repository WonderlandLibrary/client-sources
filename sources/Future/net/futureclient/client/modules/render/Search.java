package net.futureclient.client.modules.render;

import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import net.futureclient.client.oh;
import net.futureclient.client.ZB;
import net.futureclient.client.modules.render.search.Listener2;
import net.futureclient.client.modules.render.search.Listener1;
import net.futureclient.client.n;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.minecraft.block.Block;
import java.util.List;
import net.futureclient.client.utils.Value;
import net.futureclient.client.sB;
import java.util.concurrent.CopyOnWriteArrayList;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class Search extends Ea
{
    private NumberValue width;
    public CopyOnWriteArrayList<sB.cC> M;
    private Value<Boolean> boundingBox;
    private Value<Boolean> fill;
    public List<Block> D;
    private Value<Boolean> tracers;
    
    public Search() {
        super("Search", new String[] { "Search", "Find" }, true, -6750208, Category.RENDER);
        this.boundingBox = new Value<Boolean>(true, new String[] { "BoundingBox", "Bound" });
        this.tracers = new Value<Boolean>(false, new String[] { "Tracers", "Tracer" });
        this.fill = new Value<Boolean>(false, new String[] { "Fill", "filling", "fillings" });
        this.width = new NumberValue(0.6f, 0.1f, 10.0f, 1.273197475E-314, new String[] { "Width", "With", "Radius", "raidus" });
        final int n = 4;
        this.M = new CopyOnWriteArrayList<sB.cC>();
        this.D = new ArrayList<Block>();
        final Value[] array = new Value[n];
        array[0] = this.width;
        array[1] = this.tracers;
        array[2] = this.boundingBox;
        array[3] = this.fill;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
        new ZB(this, "search_blocks.txt");
    }
    
    public void B() {
        super.B();
        oh.b();
    }
    
    public static Minecraft getMinecraft() {
        return Search.D;
    }
    
    private void C() {
        this.D.add(Blocks.MOB_SPAWNER);
        this.D.add((Block)Blocks.PORTAL);
        this.D.add(Blocks.END_PORTAL_FRAME);
        this.D.add(Blocks.END_PORTAL);
        this.D.add(Blocks.DISPENSER);
        this.D.add(Blocks.DROPPER);
        this.D.add((Block)Blocks.HOPPER);
        this.D.add(Blocks.FURNACE);
        this.D.add(Blocks.LIT_FURNACE);
        this.D.add((Block)Blocks.CHEST);
        this.D.add(Blocks.TRAPPED_CHEST);
        this.D.add(Blocks.ENDER_CHEST);
        this.D.add(Blocks.WHITE_SHULKER_BOX);
        this.D.add(Blocks.ORANGE_SHULKER_BOX);
        this.D.add(Blocks.MAGENTA_SHULKER_BOX);
        this.D.add(Blocks.LIGHT_BLUE_SHULKER_BOX);
        this.D.add(Blocks.YELLOW_SHULKER_BOX);
        this.D.add(Blocks.LIME_SHULKER_BOX);
        this.D.add(Blocks.PINK_SHULKER_BOX);
        this.D.add(Blocks.GRAY_SHULKER_BOX);
        this.D.add(Blocks.SILVER_SHULKER_BOX);
        this.D.add(Blocks.CYAN_SHULKER_BOX);
        this.D.add(Blocks.PURPLE_SHULKER_BOX);
        this.D.add(Blocks.BLUE_SHULKER_BOX);
        this.D.add(Blocks.BROWN_SHULKER_BOX);
        this.D.add(Blocks.GREEN_SHULKER_BOX);
        this.D.add(Blocks.RED_SHULKER_BOX);
        this.D.add(Blocks.BLACK_SHULKER_BOX);
    }
    
    public static Minecraft getMinecraft1() {
        return Search.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Search.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Search.D;
    }
    
    public void b() {
        this.M.clear();
        super.b();
    }
    
    public static Value b(final Search search) {
        return search.boundingBox;
    }
    
    public static Minecraft getMinecraft4() {
        return Search.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Search.D;
    }
    
    public static Value e(final Search search) {
        return search.fill;
    }
    
    public static Minecraft getMinecraft6() {
        return Search.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Search.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Search.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Search.D;
    }
    
    public static Value M(final Search search) {
        return search.tracers;
    }
    
    public static Minecraft getMinecraft10() {
        return Search.D;
    }
    
    public static int M(final Search search, final double n, final double n2, final double n3) {
        return search.M(n, n2, n3);
    }
    
    private int M(final double n, final double n2, final double n3) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        final int idFromBlock;
        if ((idFromBlock = Block.getIdFromBlock(Search.D.world.getBlockState(blockPos).getBlock())) == 56) {
            return 9480789;
        }
        if (idFromBlock == 57) {
            return 9480789;
        }
        if (idFromBlock == 14) {
            return -1869610923;
        }
        if (idFromBlock == 41) {
            return -1869610923;
        }
        if (idFromBlock == 15) {
            return -2140123051;
        }
        if (idFromBlock == 42) {
            return -2140123051;
        }
        if (idFromBlock == 16) {
            return 538976341;
        }
        if (idFromBlock == 21) {
            return 3170389;
        }
        if (idFromBlock == 73) {
            return 1610612821;
        }
        if (idFromBlock == 74) {
            return 1610612821;
        }
        if (idFromBlock == 129) {
            return 8396885;
        }
        if (idFromBlock == 98) {
            return 9480789;
        }
        if (idFromBlock == 354) {
            return 9480789;
        }
        if (idFromBlock == 49) {
            return 1696715042;
        }
        if (idFromBlock == 90) {
            return 1696715076;
        }
        if (idFromBlock == 10) {
            return -7141377;
        }
        if (idFromBlock == 11) {
            return -7141547;
        }
        if (idFromBlock == 52) {
            return 8051029;
        }
        if (idFromBlock == 26) {
            return -16777131;
        }
        if (idFromBlock == 5) {
            return -1517671851;
        }
        if (idFromBlock == 17) {
            return -1517671851;
        }
        if (idFromBlock == 162) {
            return -1517671851;
        }
        if (idFromBlock == 112) {
            return 16728862;
        }
        final int colorValue;
        return (int)Long.parseLong(String.format("%02x%02x%02x%02x", (colorValue = Search.D.world.getBlockState(blockPos).getMaterial().getMaterialMapColor().colorValue) >> 16 & 0xFF, colorValue >> 8 & 0xFF, colorValue & 0xFF, 100), 16);
    }
    
    public static NumberValue M(final Search search) {
        return search.width;
    }
    
    public static void M(final Search search) {
        search.C();
    }
    
    public static Minecraft getMinecraft11() {
        return Search.D;
    }
}
