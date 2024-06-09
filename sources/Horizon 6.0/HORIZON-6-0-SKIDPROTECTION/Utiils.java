package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.atomic.AtomicInteger;

public class Utiils
{
    public static Minecraft HorizonCode_Horizon_È;
    private static final AtomicInteger Â;
    private static final Logger Ý;
    private NetworkManager Ø­áŒŠá;
    
    static {
        Utiils.HorizonCode_Horizon_È = Minecraft.áŒŠà();
        Â = new AtomicInteger(0);
        Ý = LogManager.getLogger();
    }
    
    public static void HorizonCode_Horizon_È(final int amount) {
        for (int i = 0; i < 4; ++i) {
            Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.Çªà¢ + 1.01, Minecraft.áŒŠà().á.Ê, false));
            Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.Çªà¢, Minecraft.áŒŠà().á.Ê, false));
        }
        Minecraft.áŒŠà().á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Minecraft.áŒŠà().á.ŒÏ, Minecraft.áŒŠà().á.Çªà¢ + 0.4, Minecraft.áŒŠà().á.Ê, false));
    }
    
    public static void HorizonCode_Horizon_È() {
        Utiils.HorizonCode_Horizon_È.á.ˆá = 0.0;
        Utiils.HorizonCode_Horizon_È.á.Ï­à = 0.0f;
        final double[] d = { 0.2, 0.26 };
        for (int a = 0; a < 72; ++a) {
            for (int i = 0; i < d.length; ++i) {
                Utiils.HorizonCode_Horizon_È.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Utiils.HorizonCode_Horizon_È.á.ŒÏ, Utiils.HorizonCode_Horizon_È.á.Çªà¢ + d[i], Utiils.HorizonCode_Horizon_È.á.Ê, false));
            }
        }
        Utiils.HorizonCode_Horizon_È.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(Utiils.HorizonCode_Horizon_È.á.ŒÏ, Utiils.HorizonCode_Horizon_È.á.Çªà¢ + 0.3, Utiils.HorizonCode_Horizon_È.á.Ê, false));
    }
    
    public static Integer Â() {
        final Random rnd = new Random();
        final int intVal = rnd.nextInt(16777216);
        return intVal;
    }
    
    public static int Ý() {
        int i2 = -1;
        final Minecraft mc = Minecraft.áŒŠà();
        for (int dist = 0; dist < 256; ++dist) {
            final BlockPos bpos = new BlockPos(mc.á.ŒÏ, mc.á.Çªà¢ - dist, mc.á.Ê);
            final Block block = mc.áŒŠÆ.Â(bpos).Ý();
            if (!(block instanceof BlockAir)) {
                i2 = dist;
            }
        }
        return i2;
    }
    
    public static int Ø­áŒŠá() {
        final Minecraft mc = Minecraft.áŒŠà();
        final int y = (int)mc.á.Çªà¢;
        int dist = 0;
        int result = -1;
        while (result == -1) {
            final BlockPos bpos = new BlockPos(mc.á.ŒÏ, mc.á.Çªà¢ - dist, mc.á.Ê);
            final Block block = mc.áŒŠÆ.Â(bpos).Ý();
            if (!(block instanceof BlockAir)) {
                result = y - dist;
                return result;
            }
            ++dist;
        }
        return -1;
    }
    
    public static Double HorizonCode_Horizon_È(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        Double distance = null;
        distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
        return distance;
    }
    
    public static Block HorizonCode_Horizon_È(final double posX, final double posY, final double posZ) {
        final BlockPos bpos = new BlockPos(posX, posY, posZ);
        final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(bpos).Ý();
        return block;
    }
    
    public static void HorizonCode_Horizon_È(final String ip, final int port) {
        final GuiConnecting gui = new GuiConnecting(null, Utiils.HorizonCode_Horizon_È, ip, port);
        Utiils.HorizonCode_Horizon_È.¥Æ = gui;
    }
    
    public static boolean Âµá€() {
        for (int x = MathHelper.Ý(Utiils.HorizonCode_Horizon_È.á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(Utiils.HorizonCode_Horizon_È.á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int y = MathHelper.Ý(Utiils.HorizonCode_Horizon_È.á.à¢.Â); y < MathHelper.Ý(Utiils.HorizonCode_Horizon_È.á.à¢.Âµá€) + 1; ++y) {
                for (int z = MathHelper.Ý(Utiils.HorizonCode_Horizon_È.á.à¢.Ý); z < MathHelper.Ý(Utiils.HorizonCode_Horizon_È.á.à¢.Ó) + 1; ++z) {
                    final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                    if (block != null && !(block instanceof BlockAir)) {
                        final AxisAlignedBB boundingBox = block.HorizonCode_Horizon_È(Utiils.HorizonCode_Horizon_È.áŒŠÆ, new BlockPos(x, y, z), Utiils.HorizonCode_Horizon_È.áŒŠÆ.Â(new BlockPos(x, y, z)));
                        if (boundingBox != null && Utiils.HorizonCode_Horizon_È.á.à¢.Â(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean Â(final int i) {
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        if (!ModuleManager.HorizonCode_Horizon_È(Jesus.class).áˆºÑ¢Õ()) {
            return false;
        }
        int mx = i;
        int mz = i;
        int max = i;
        int maz = i;
        if (Ó().ÇŽÉ > 0.01) {
            mx = 0;
        }
        else if (Ó().ÇŽÉ < -0.01) {
            max = 0;
        }
        if (Ó().ÇŽÕ > 0.01) {
            mz = 0;
        }
        else if (Ó().ÇŽÕ < -0.01) {
            maz = 0;
        }
        final int xmin = MathHelper.Ý(Ó().£É().HorizonCode_Horizon_È - mx);
        final int ymin = MathHelper.Ý(Ó().£É().Â - 1.0);
        final int zmin = MathHelper.Ý(Ó().£É().Ý - mz);
        final int xmax = MathHelper.Ý(Ó().£É().HorizonCode_Horizon_È + max);
        final int ymax = MathHelper.Ý(Ó().£É().Â + 1.0);
        final int zmax = MathHelper.Ý(Ó().£É().Ý + maz);
        for (int x = xmin; x <= xmax; ++x) {
            for (int y = ymin; y <= ymax; ++y) {
                for (int z = zmin; z <= zmax; ++z) {
                    final Block block = HorizonCode_Horizon_È(x, y, z);
                    if (block instanceof BlockLiquid && !Ó().HorizonCode_Horizon_È(Material.áŒŠÆ) && !Ó().HorizonCode_Horizon_È(Material.Ø)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static EntityPlayerSP Ó() {
        return Minecraft.áŒŠà().á;
    }
}
