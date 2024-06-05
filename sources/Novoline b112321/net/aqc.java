package net;

import cc.novoline.utils.Timer;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class aqc {
   private BlockPos c;
   private EnumFacing a;
   private static List b = Arrays.asList(new Block[]{Blocks.anvil, Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.skull, Blocks.trapped_chest, Blocks.flowing_lava, Blocks.chest, Blocks.enchanting_table, Blocks.ender_chest, Blocks.crafting_table});

   public aqc(BlockPos var1, EnumFacing var2) {
      this.c = var1;
      this.a = var2;
   }

   public BlockPos a() {
      return this.c;
   }

   public EnumFacing b() {
      return this.a;
   }

   private static boolean a(BlockPos var0) {
      String[] var1 = Timer.e();
      return !b.contains(Minecraft.getInstance().world.getBlockState(var0).getBlock());
   }

   public static aqc c(BlockPos var0) {
      String[] var1 = Timer.e();
      if(a(var0.a(0, -1, 0))) {
         return new aqc(var0.a(0, -1, 0), EnumFacing.UP);
      } else if(a(var0.a(-1, 0, 0))) {
         return new aqc(var0.a(-1, 0, 0), EnumFacing.EAST);
      } else if(a(var0.a(1, 0, 0))) {
         return new aqc(var0.a(1, 0, 0), EnumFacing.WEST);
      } else if(a(var0.a(0, 0, 1))) {
         return new aqc(var0.a(0, 0, 1), EnumFacing.NORTH);
      } else if(a(var0.a(0, 0, -1))) {
         return new aqc(var0.a(0, 0, -1), EnumFacing.SOUTH);
      } else {
         BlockPos var2 = var0.a(-1, 0, 0);
         if(a(var2.a(0, -1, 0))) {
            return new aqc(var2.a(0, -1, 0), EnumFacing.UP);
         } else if(a(var2.a(-1, 0, 0))) {
            return new aqc(var2.a(-1, 0, 0), EnumFacing.EAST);
         } else if(a(var2.a(1, 0, 0))) {
            return new aqc(var2.a(1, 0, 0), EnumFacing.WEST);
         } else if(a(var2.a(0, 0, 1))) {
            return new aqc(var2.a(0, 0, 1), EnumFacing.NORTH);
         } else if(a(var2.a(0, 0, -1))) {
            return new aqc(var2.a(0, 0, -1), EnumFacing.SOUTH);
         } else {
            BlockPos var3 = var0.a(1, 0, 0);
            if(a(var3.a(0, -1, 0))) {
               return new aqc(var3.a(0, -1, 0), EnumFacing.UP);
            } else if(a(var3.a(-1, 0, 0))) {
               return new aqc(var3.a(-1, 0, 0), EnumFacing.EAST);
            } else if(a(var3.a(1, 0, 0))) {
               return new aqc(var3.a(1, 0, 0), EnumFacing.WEST);
            } else if(a(var3.a(0, 0, 1))) {
               return new aqc(var3.a(0, 0, 1), EnumFacing.NORTH);
            } else if(a(var3.a(0, 0, -1))) {
               return new aqc(var3.a(0, 0, -1), EnumFacing.SOUTH);
            } else {
               BlockPos var4 = var0.a(0, 0, 1);
               if(a(var4.a(0, -1, 0))) {
                  return new aqc(var4.a(0, -1, 0), EnumFacing.UP);
               } else if(a(var4.a(-1, 0, 0))) {
                  return new aqc(var4.a(-1, 0, 0), EnumFacing.EAST);
               } else if(a(var4.a(1, 0, 0))) {
                  return new aqc(var4.a(1, 0, 0), EnumFacing.WEST);
               } else if(a(var4.a(0, 0, 1))) {
                  return new aqc(var4.a(0, 0, 1), EnumFacing.NORTH);
               } else if(a(var4.a(0, 0, -1))) {
                  return new aqc(var4.a(0, 0, -1), EnumFacing.SOUTH);
               } else {
                  BlockPos var5 = var0.a(0, 0, -1);
                  if(a(var5.a(0, -1, 0))) {
                     return new aqc(var5.a(0, -1, 0), EnumFacing.UP);
                  } else if(a(var5.a(-1, 0, 0))) {
                     return new aqc(var5.a(-1, 0, 0), EnumFacing.EAST);
                  } else if(a(var5.a(1, 0, 0))) {
                     return new aqc(var5.a(1, 0, 0), EnumFacing.WEST);
                  } else if(a(var5.a(0, 0, 1))) {
                     return new aqc(var5.a(0, 0, 1), EnumFacing.NORTH);
                  } else if(a(var5.a(0, 0, -1))) {
                     return new aqc(var5.a(0, 0, -1), EnumFacing.SOUTH);
                  } else {
                     BlockPos var6 = var0.a(-2, 0, 0);
                     if(a(var2.a(0, -1, 0))) {
                        return new aqc(var2.a(0, -1, 0), EnumFacing.UP);
                     } else if(a(var2.a(-1, 0, 0))) {
                        return new aqc(var2.a(-1, 0, 0), EnumFacing.EAST);
                     } else if(a(var2.a(1, 0, 0))) {
                        return new aqc(var2.a(1, 0, 0), EnumFacing.WEST);
                     } else if(a(var2.a(0, 0, 1))) {
                        return new aqc(var2.a(0, 0, 1), EnumFacing.NORTH);
                     } else if(a(var2.a(0, 0, -1))) {
                        return new aqc(var2.a(0, 0, -1), EnumFacing.SOUTH);
                     } else {
                        BlockPos var7 = var0.a(2, 0, 0);
                        if(a(var3.a(0, -1, 0))) {
                           return new aqc(var3.a(0, -1, 0), EnumFacing.UP);
                        } else if(a(var3.a(-1, 0, 0))) {
                           return new aqc(var3.a(-1, 0, 0), EnumFacing.EAST);
                        } else if(a(var3.a(1, 0, 0))) {
                           return new aqc(var3.a(1, 0, 0), EnumFacing.WEST);
                        } else if(a(var3.a(0, 0, 1))) {
                           return new aqc(var3.a(0, 0, 1), EnumFacing.NORTH);
                        } else if(a(var3.a(0, 0, -1))) {
                           return new aqc(var3.a(0, 0, -1), EnumFacing.SOUTH);
                        } else {
                           BlockPos var8 = var0.a(0, 0, 2);
                           if(a(var4.a(0, -1, 0))) {
                              return new aqc(var4.a(0, -1, 0), EnumFacing.UP);
                           } else if(a(var4.a(-1, 0, 0))) {
                              return new aqc(var4.a(-1, 0, 0), EnumFacing.EAST);
                           } else if(a(var4.a(1, 0, 0))) {
                              return new aqc(var4.a(1, 0, 0), EnumFacing.WEST);
                           } else if(a(var4.a(0, 0, 1))) {
                              return new aqc(var4.a(0, 0, 1), EnumFacing.NORTH);
                           } else if(a(var4.a(0, 0, -1))) {
                              return new aqc(var4.a(0, 0, -1), EnumFacing.SOUTH);
                           } else {
                              BlockPos var9 = var0.a(0, 0, -2);
                              if(a(var5.a(0, -1, 0))) {
                                 return new aqc(var5.a(0, -1, 0), EnumFacing.UP);
                              } else if(a(var5.a(-1, 0, 0))) {
                                 return new aqc(var5.a(-1, 0, 0), EnumFacing.EAST);
                              } else if(a(var5.a(1, 0, 0))) {
                                 return new aqc(var5.a(1, 0, 0), EnumFacing.WEST);
                              } else if(a(var5.a(0, 0, 1))) {
                                 return new aqc(var5.a(0, 0, 1), EnumFacing.NORTH);
                              } else if(a(var5.a(0, 0, -1))) {
                                 return new aqc(var5.a(0, 0, -1), EnumFacing.SOUTH);
                              } else {
                                 BlockPos var10 = var0.a(0, -1, 0);
                                 if(a(var10.a(0, -1, 0))) {
                                    return new aqc(var10.a(0, -1, 0), EnumFacing.UP);
                                 } else if(a(var10.a(-1, 0, 0))) {
                                    return new aqc(var10.a(-1, 0, 0), EnumFacing.EAST);
                                 } else if(a(var10.a(1, 0, 0))) {
                                    return new aqc(var10.a(1, 0, 0), EnumFacing.WEST);
                                 } else if(a(var10.a(0, 0, 1))) {
                                    return new aqc(var10.a(0, 0, 1), EnumFacing.NORTH);
                                 } else if(a(var10.a(0, 0, -1))) {
                                    return new aqc(var10.a(0, 0, -1), EnumFacing.SOUTH);
                                 } else {
                                    BlockPos var11 = var10.a(1, 0, 0);
                                    if(a(var11.a(0, -1, 0))) {
                                       return new aqc(var11.a(0, -1, 0), EnumFacing.UP);
                                    } else if(a(var11.a(-1, 0, 0))) {
                                       return new aqc(var11.a(-1, 0, 0), EnumFacing.EAST);
                                    } else if(a(var11.a(1, 0, 0))) {
                                       return new aqc(var11.a(1, 0, 0), EnumFacing.WEST);
                                    } else if(a(var11.a(0, 0, 1))) {
                                       return new aqc(var11.a(0, 0, 1), EnumFacing.NORTH);
                                    } else if(a(var11.a(0, 0, -1))) {
                                       return new aqc(var11.a(0, 0, -1), EnumFacing.SOUTH);
                                    } else {
                                       BlockPos var12 = var10.a(-1, 0, 0);
                                       if(a(var12.a(0, -1, 0))) {
                                          return new aqc(var12.a(0, -1, 0), EnumFacing.UP);
                                       } else if(a(var12.a(-1, 0, 0))) {
                                          return new aqc(var12.a(-1, 0, 0), EnumFacing.EAST);
                                       } else if(a(var12.a(1, 0, 0))) {
                                          return new aqc(var12.a(1, 0, 0), EnumFacing.WEST);
                                       } else if(a(var12.a(0, 0, 1))) {
                                          return new aqc(var12.a(0, 0, 1), EnumFacing.NORTH);
                                       } else if(a(var12.a(0, 0, -1))) {
                                          return new aqc(var12.a(0, 0, -1), EnumFacing.SOUTH);
                                       } else {
                                          BlockPos var13 = var10.a(0, 0, 1);
                                          if(a(var13.a(0, -1, 0))) {
                                             return new aqc(var13.a(0, -1, 0), EnumFacing.UP);
                                          } else if(a(var13.a(-1, 0, 0))) {
                                             return new aqc(var13.a(-1, 0, 0), EnumFacing.EAST);
                                          } else if(a(var13.a(1, 0, 0))) {
                                             return new aqc(var13.a(1, 0, 0), EnumFacing.WEST);
                                          } else if(a(var13.a(0, 0, 1))) {
                                             return new aqc(var13.a(0, 0, 1), EnumFacing.NORTH);
                                          } else if(a(var13.a(0, 0, -1))) {
                                             return new aqc(var13.a(0, 0, -1), EnumFacing.SOUTH);
                                          } else {
                                             BlockPos var14 = var10.a(0, 0, -1);
                                             return a(var14.a(0, -1, 0))?new aqc(var14.a(0, -1, 0), EnumFacing.UP):(a(var14.a(-1, 0, 0))?new aqc(var14.a(-1, 0, 0), EnumFacing.EAST):(a(var14.a(1, 0, 0))?new aqc(var14.a(1, 0, 0), EnumFacing.WEST):(a(var14.a(0, 0, 1))?new aqc(var14.a(0, 0, 1), EnumFacing.NORTH):(a(var14.a(0, 0, -1))?new aqc(var14.a(0, 0, -1), EnumFacing.SOUTH):null))));
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static aqc b(BlockPos var0) {
      String[] var1 = Timer.e();
      if(a(var0.a(0, 1, 0))) {
         return new aqc(var0.a(0, 1, 0), EnumFacing.DOWN);
      } else if(a(var0.a(0, 0, 1))) {
         return new aqc(var0.a(0, 0, 1), EnumFacing.NORTH);
      } else if(a(var0.a(0, 1, 1))) {
         return new aqc(var0.a(0, 1, 1), EnumFacing.DOWN);
      } else if(a(var0.a(-1, 0, 0))) {
         return new aqc(var0.a(-1, 0, 0), EnumFacing.EAST);
      } else if(a(var0.a(-1, 1, 0))) {
         return new aqc(var0.a(-1, 1, 0), EnumFacing.DOWN);
      } else if(a(var0.a(1, 0, 0))) {
         return new aqc(var0.a(1, 0, 0), EnumFacing.WEST);
      } else if(a(var0.a(1, 1, 0))) {
         return new aqc(var0.a(1, 1, 0), EnumFacing.DOWN);
      } else if(a(var0.a(0, 0, -1))) {
         return new aqc(var0.a(0, 0, -1), EnumFacing.SOUTH);
      } else if(a(var0.a(0, 1, -1))) {
         return new aqc(var0.a(0, 1, -1), EnumFacing.DOWN);
      } else {
         BlockPos var2 = var0.a(-1, 0, 0);
         if(a(var2.a(-1, 0, 0))) {
            return new aqc(var2.a(-1, 0, 0), EnumFacing.EAST);
         } else if(a(var2.a(-1, 1, 0))) {
            return new aqc(var2.a(-1, 1, 0), EnumFacing.DOWN);
         } else if(a(var2.a(1, 0, 0))) {
            return new aqc(var2.a(1, 0, 0), EnumFacing.WEST);
         } else if(a(var2.a(1, 1, 0))) {
            return new aqc(var2.a(1, 1, 0), EnumFacing.DOWN);
         } else if(a(var2.a(0, 0, -1))) {
            return new aqc(var2.a(0, 0, -1), EnumFacing.SOUTH);
         } else if(a(var2.a(0, 1, -1))) {
            return new aqc(var2.a(0, 1, -1), EnumFacing.DOWN);
         } else if(a(var2.a(0, 0, 1))) {
            return new aqc(var2.a(0, 0, 1), EnumFacing.NORTH);
         } else if(a(var2.a(0, 1, 1))) {
            return new aqc(var2.a(0, 1, 1), EnumFacing.DOWN);
         } else {
            BlockPos var3 = var0.a(1, 0, 0);
            if(a(var3.a(-1, 0, 0))) {
               return new aqc(var3.a(-1, 0, 0), EnumFacing.EAST);
            } else if(a(var3.a(-1, 1, 0))) {
               return new aqc(var3.a(-1, 1, 0), EnumFacing.DOWN);
            } else if(a(var3.a(1, 0, 0))) {
               return new aqc(var3.a(1, 0, 0), EnumFacing.WEST);
            } else if(a(var3.a(1, 1, 0))) {
               return new aqc(var3.a(1, 1, 0), EnumFacing.DOWN);
            } else if(a(var3.a(0, 0, -1))) {
               return new aqc(var3.a(0, 0, -1), EnumFacing.SOUTH);
            } else if(a(var3.a(0, 1, -1))) {
               return new aqc(var3.a(0, 1, -1), EnumFacing.DOWN);
            } else if(a(var3.a(0, 0, 1))) {
               return new aqc(var3.a(0, 0, 1), EnumFacing.NORTH);
            } else if(a(var3.a(0, 1, 1))) {
               return new aqc(var3.a(0, 1, 1), EnumFacing.DOWN);
            } else {
               BlockPos var4 = var0.a(0, 0, -1);
               if(a(var4.a(-1, 0, 0))) {
                  return new aqc(var4.a(-1, 0, 0), EnumFacing.EAST);
               } else if(a(var0.a(-1, 1, 0))) {
                  return new aqc(var4.a(-1, 1, 0), EnumFacing.DOWN);
               } else if(a(var4.a(1, 0, 0))) {
                  return new aqc(var4.a(1, 0, 0), EnumFacing.WEST);
               } else if(a(var4.a(1, 1, 0))) {
                  return new aqc(var4.a(1, 1, 0), EnumFacing.DOWN);
               } else if(a(var4.a(0, 0, -1))) {
                  return new aqc(var4.a(0, 0, -1), EnumFacing.SOUTH);
               } else if(a(var4.a(0, 1, -1))) {
                  return new aqc(var4.a(0, 1, -1), EnumFacing.DOWN);
               } else if(a(var4.a(0, 0, 1))) {
                  return new aqc(var4.a(0, 0, 1), EnumFacing.NORTH);
               } else if(a(var4.a(0, 1, 1))) {
                  return new aqc(var4.a(0, 1, 1), EnumFacing.DOWN);
               } else {
                  BlockPos var5 = var0.a(0, 0, 1);
                  return a(var5.a(-1, 0, 0))?new aqc(var5.a(-1, 0, 0), EnumFacing.EAST):(a(var5.a(-1, 1, 0))?new aqc(var5.a(-1, 1, 0), EnumFacing.DOWN):(a(var5.a(1, 0, 0))?new aqc(var5.a(1, 0, 0), EnumFacing.WEST):(a(var5.a(1, 1, 0))?new aqc(var5.a(1, 1, 0), EnumFacing.DOWN):(a(var5.a(0, 0, -1))?new aqc(var5.a(0, 0, -1), EnumFacing.SOUTH):(a(var5.a(0, 1, -1))?new aqc(var5.a(0, 1, -1), EnumFacing.DOWN):(a(var5.a(0, 0, 1))?new aqc(var5.a(0, 0, 1), EnumFacing.NORTH):(a(var5.a(0, 1, 1))?new aqc(var5.a(0, 1, 1), EnumFacing.DOWN):null)))))));
               }
            }
         }
      }
   }
}
