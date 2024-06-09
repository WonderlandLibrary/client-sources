package HORIZON-6-0-SKIDPROTECTION;

public class ClearWater
{
    public static void HorizonCode_Horizon_È(final GameSettings settings, final World world) {
        if (settings != null) {
            byte cp = 3;
            if (settings.áƒ) {
                cp = 1;
            }
            BlockUtils.HorizonCode_Horizon_È(Blocks.ÂµÈ, cp);
            BlockUtils.HorizonCode_Horizon_È(Blocks.áˆºÑ¢Õ, cp);
        }
        if (world != null) {
            final IChunkProvider var25 = world.ŠÄ();
            if (var25 != null) {
                final Entity rve = Config.È().ÇŽá€();
                if (rve != null) {
                    final int cViewX = (int)rve.ŒÏ / 16;
                    final int cViewZ = (int)rve.Ê / 16;
                    final int cXMin = cViewX - 512;
                    final int cXMax = cViewX + 512;
                    final int cZMin = cViewZ - 512;
                    final int cZMax = cViewZ + 512;
                    int countUpdated = 0;
                    for (int threadName = cXMin; threadName < cXMax; ++threadName) {
                        for (int cz = cZMin; cz < cZMax; ++cz) {
                            if (var25.HorizonCode_Horizon_È(threadName, cz)) {
                                final Chunk c = var25.Ø­áŒŠá(threadName, cz);
                                if (c != null && !(c instanceof EmptyChunk)) {
                                    final int x0 = threadName << 4;
                                    final int z0 = cz << 4;
                                    final int x2 = x0 + 16;
                                    final int z2 = z0 + 16;
                                    final BlockPosM posXZ = new BlockPosM(0, 0, 0);
                                    final BlockPosM posXYZ = new BlockPosM(0, 0, 0);
                                    for (int x3 = x0; x3 < x2; ++x3) {
                                        for (int z3 = z0; z3 < z2; ++z3) {
                                            posXZ.HorizonCode_Horizon_È(x3, 0, z3);
                                            final BlockPos posH = world.µà(posXZ);
                                            for (int y = 0; y < posH.Â(); ++y) {
                                                posXYZ.HorizonCode_Horizon_È(x3, y, z3);
                                                final IBlockState bs = world.Â(posXYZ);
                                                if (bs.Ý().Ó() == Material.Ø) {
                                                    world.HorizonCode_Horizon_È(x3, z3, posXYZ.Â(), posH.Â());
                                                    ++countUpdated;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (countUpdated > 0) {
                        String var26 = "server";
                        if (Config.Ø()) {
                            var26 = "client";
                        }
                        Config.HorizonCode_Horizon_È("ClearWater (" + var26 + ") relighted " + countUpdated + " chunks");
                    }
                }
            }
        }
    }
}
