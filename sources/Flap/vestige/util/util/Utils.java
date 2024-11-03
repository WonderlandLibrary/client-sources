package vestige.util.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import vestige.setting.impl.DoubleSetting;
import vestige.util.IMinecraft;
import vestige.util.player.PlayerUtil;
import vestige.util.world.BlockUtils;

public class Utils {
   private static final Random rand = new Random();

   public static boolean canBePlaced(ItemBlock itemBlock) {
      Block block = itemBlock.getBlock();
      if (block == null) {
         return false;
      } else {
         return !BlockUtils.isInteractable(block) && !(block instanceof BlockSapling) && !(block instanceof BlockDaylightDetector) && !(block instanceof BlockBeacon) && !(block instanceof BlockBanner) && !(block instanceof BlockEndPortalFrame) && !(block instanceof BlockEndPortal) && !(block instanceof BlockLever) && !(block instanceof BlockButton) && !(block instanceof BlockSkull) && !(block instanceof BlockLiquid) && !(block instanceof BlockCactus) && !(block instanceof BlockDoublePlant) && !(block instanceof BlockLilyPad) && !(block instanceof BlockCarpet) && !(block instanceof BlockTripWire) && !(block instanceof BlockTripWireHook) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFlower) && !(block instanceof BlockFlowerPot) && !(block instanceof BlockSign) && !(block instanceof BlockLadder) && !(block instanceof BlockTorch) && !(block instanceof BlockRedstoneTorch) && !(block instanceof BlockFence) && !(block instanceof BlockPane) && !(block instanceof BlockStainedGlassPane) && !(block instanceof BlockGravel) && !(block instanceof BlockClay) && !(block instanceof BlockSand) && !(block instanceof BlockSoulSand) && !(block instanceof BlockRail);
      }
   }

   public static String getHitsToKill(EntityPlayer entityPlayer, ItemStack itemStack) {
      int n = (int)Math.ceil(ap(entityPlayer, itemStack));
      return "§" + (n <= 1 ? "c" : (n <= 3 ? "6" : (n <= 5 ? "e" : "a"))) + n;
   }

   public static double ap(EntityPlayer entityPlayer, ItemStack itemStack) {
      double n = 1.0D;
      if (itemStack != null && (itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemAxe)) {
         n += getDamage(itemStack);
      }

      double n2 = 0.0D;
      double n3 = 0.0D;

      for(int i = 0; i < 4; ++i) {
         ItemStack armorItemInSlot = entityPlayer.inventory.armorItemInSlot(i);
         if (armorItemInSlot != null && armorItemInSlot.getItem() instanceof ItemArmor) {
            n2 += (double)((ItemArmor)armorItemInSlot.getItem()).damageReduceAmount * 0.04D;
            int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, armorItemInSlot);
            if (getEnchantmentLevel != 0) {
               n3 += Math.floor(0.75D * (double)(6 + getEnchantmentLevel * getEnchantmentLevel) / 3.0D);
            }
         }
      }

      return rnd((double)getCompleteHealth(entityPlayer) / (n * (1.0D - (n2 + 0.04D * Math.min(Math.ceil(Math.min(n3, 25.0D) * 0.75D), 20.0D) * (1.0D - n2)))), 1);
   }

   public static double getDamage(ItemStack itemStack) {
      double getAmount = 0.0D;
      Iterator var3 = itemStack.getAttributeModifiers().entries().iterator();

      while(var3.hasNext()) {
         Entry<String, AttributeModifier> entry = (Entry)var3.next();
         if (((String)entry.getKey()).equals("generic.attackDamage")) {
            getAmount = ((AttributeModifier)entry.getValue()).getAmount();
            break;
         }
      }

      return getAmount + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25D;
   }

   public static boolean isWholeNumber(double num) {
      return num == Math.floor(num);
   }

   public static float getCompleteHealth(EntityLivingBase entity) {
      return entity == null ? 0.0F : entity.getHealth() + entity.getAbsorptionAmount();
   }

   public static String getColorForHealth(double n, double n2) {
      double health = rnd(n2, 1);
      return (n < 0.3D ? "§c" : (n < 0.5D ? "§6" : (n < 0.7D ? "§e" : "§a"))) + (isWholeNumber(health) ? (int)health + "" : health);
   }

   public static int getColorForHealth(double health) {
      return health < 0.3D ? -43691 : (health < 0.5D ? -22016 : (health < 0.7D ? -171 : -11141291));
   }

   public static String getHealthStr(EntityLivingBase entity) {
      float completeHealth = getCompleteHealth(entity);
      return getColorForHealth((double)(entity.getHealth() / entity.getMaxHealth()), (double)completeHealth);
   }

   public static int clamp(int n) {
      if (n > 255) {
         return 255;
      } else {
         return n < 4 ? 4 : n;
      }
   }

   public static boolean isHypixel() {
      return !IMinecraft.mc.isSingleplayer() && IMinecraft.mc.getCurrentServerData() != null && IMinecraft.mc.getCurrentServerData().serverIP.contains("hypixel.net");
   }

   public static int merge(int n, int n2) {
      return n & 16777215 | n2 << 24;
   }

   public static boolean isBedwarsPractice() {
      if (isHypixel()) {
         if (!PlayerUtil.nullCheck()) {
            return false;
         } else {
            Scoreboard scoreboard = IMinecraft.mc.theWorld.getScoreboard();
            if (scoreboard == null) {
               return false;
            } else {
               ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
               return objective != null && stripString(objective.getDisplayName()).contains("BED WARS PRACTICE");
            }
         }
      } else {
         return false;
      }
   }

   public static boolean overAir() {
      return !PlayerUtil.nullCheck() ? false : IMinecraft.mc.theWorld.isAirBlock(new BlockPos(IMinecraft.mc.thePlayer.posX, IMinecraft.mc.thePlayer.posY - 1.0D, IMinecraft.mc.thePlayer.posZ));
   }

   public static double rnd(double n, int d) {
      if (d == 0) {
         return (double)Math.round(n);
      } else {
         double p = Math.pow(10.0D, (double)d);
         return (double)Math.round(n * p) / p;
      }
   }

   public static double randomizeDouble(double min, double max) {
      return Math.random() * (max - min) + min;
   }

   public static int getTool(Block block) {
      float n = 1.0F;
      int n2 = -1;

      for(int i = 0; i < InventoryPlayer.getHotbarSize(); ++i) {
         ItemStack getStackInSlot = IMinecraft.mc.thePlayer.inventory.getStackInSlot(i);
         if (getStackInSlot != null) {
            float a = getEfficiency(getStackInSlot, block);
            if (a > n) {
               n = a;
               n2 = i;
            }
         }
      }

      return n2;
   }

   public static float getEfficiency(ItemStack itemStack, Block block) {
      float getStrVsBlock = itemStack.getStrVsBlock(block);
      if (getStrVsBlock > 1.0F) {
         int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
         if (getEnchantmentLevel > 0) {
            getStrVsBlock += (float)(getEnchantmentLevel * getEnchantmentLevel + 1);
         }
      }

      return getStrVsBlock;
   }

   public static boolean overVoid(double posX, double posY, double posZ) {
      for(int i = (int)posY; i > -1; --i) {
         if (!(IMinecraft.mc.theWorld.getBlockState(new BlockPos(posX, (double)i, posZ)).getBlock() instanceof BlockAir)) {
            return false;
         }
      }

      return true;
   }

   public static boolean overVoid() {
      return overVoid(IMinecraft.mc.thePlayer.posX, IMinecraft.mc.thePlayer.posY, IMinecraft.mc.thePlayer.posZ);
   }

   public static void correctValue(@NotNull DoubleSetting c, @NotNull DoubleSetting d) {
      if (c == null) {
         $$$reportNull$$$0(0);
      }

      if (d == null) {
         $$$reportNull$$$0(1);
      }

      if (c.getValue() > d.getValue()) {
         double p = c.getValue();
         c.setValue(d.getValue());
         d.setValue(p);
      }

   }

   public static boolean isLobby() {
      boolean hasNetherStar = false;
      boolean hasCompass = false;
      ItemStack[] var2 = IMinecraft.mc.thePlayer.inventory.mainInventory;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ItemStack stack = var2[var4];
         if (stack != null) {
            if (stack.getItem() == Items.nether_star) {
               hasNetherStar = true;
            }

            if (stack.getItem() == Items.compass) {
               hasCompass = true;
            }

            if (hasNetherStar && hasCompass) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean keysDown() {
      return Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindForward.getKeyCode()) || Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindBack.getKeyCode()) || Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindRight.getKeyCode());
   }

   public static boolean jumpDown() {
      return Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindJump.getKeyCode());
   }

   public static int getBedwarsStatus() {
      if (!PlayerUtil.nullCheck()) {
         return -1;
      } else {
         Scoreboard scoreboard = IMinecraft.mc.theWorld.getScoreboard();
         if (scoreboard == null) {
            return -1;
         } else {
            ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
            if (objective != null && stripString(objective.getDisplayName()).contains("BED WARS")) {
               Iterator var2 = getSidebarLines().iterator();

               while(true) {
                  while(var2.hasNext()) {
                     String line = (String)var2.next();
                     line = stripString(line);
                     String[] parts = line.split("  ");
                     if (parts.length <= 1) {
                        if (!line.equals("Waiting...") && !line.startsWith("Starting in")) {
                           if (!line.startsWith("R Red:") && !line.startsWith("B Blue:")) {
                              continue;
                           }

                           return 2;
                        }

                        return 1;
                     } else if (parts[1].startsWith("L")) {
                        return 0;
                     }
                  }

                  return -1;
               }
            } else {
               return -1;
            }
         }
      }
   }

   public static String stripString(String s) {
      char[] nonValidatedString = StringUtils.stripControlCodes(s).toCharArray();
      StringBuilder validated = new StringBuilder();
      char[] var3 = nonValidatedString;
      int var4 = nonValidatedString.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char c = var3[var5];
         if (c < 127 && c > 20) {
            validated.append(c);
         }
      }

      return validated.toString();
   }

   public static List<String> getSidebarLines() {
      List<String> lines = new ArrayList();
      if (IMinecraft.mc.theWorld == null) {
         return lines;
      } else {
         Scoreboard scoreboard = IMinecraft.mc.theWorld.getScoreboard();
         if (scoreboard == null) {
            return lines;
         } else {
            ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
            if (objective == null) {
               return lines;
            } else {
               Collection<Score> scores = scoreboard.getSortedScores(objective);
               List<Score> list = new ArrayList();
               Iterator var5 = scores.iterator();

               while(var5.hasNext()) {
                  Score input = (Score)var5.next();
                  if (input != null && input.getPlayerName() != null && !input.getPlayerName().startsWith("#")) {
                     list.add(input);
                  }
               }
               if (list.size() > 15) {
                  scores = new ArrayList(Lists.newArrayList(Iterables.skip(list, list.size() - 15)));
               } else {
                  scores = list;
               }

               int index = 0;
               Iterator var11 = scores.iterator();

               while(var11.hasNext()) {
                  Score score = (Score)var11.next();
                  ++index;
                  ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                  lines.add(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName()));
                  if (index == scores.size()) {
                     lines.add(objective.getDisplayName());
                  }
               }

               Collections.reverse(lines);
               return lines;
            }
         }
      }
   }

   public static boolean onEdge() {
      return onEdge(IMinecraft.mc.thePlayer);
   }

   public static boolean onEdge(Entity entity) {
      return IMinecraft.mc.theWorld.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox().offset(entity.motionX / 3.0D, -1.0D, entity.motionZ / 3.0D)).isEmpty();
   }

   public static float[] getRotations(BlockPos blockPos, float n, float n2) {
      float[] array = getRotations(blockPos);
      return fixRotation(array[0], array[1], n, n2);
   }

   public static float[] fixRotation(float n, float n2, float n3, float n4) {
      float n5 = n - n3;
      float abs = Math.abs(n5);
      float n7 = n2 - n4;
      float n8 = IMinecraft.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
      double n9 = (double)(n8 * n8 * n8) * 1.2D;
      float n10 = (float)((double)Math.round((double)n5 / n9) * n9);
      float n11 = (float)((double)Math.round((double)n7 / n9) * n9);
      n = n3 + n10;
      n2 = n4 + n11;
      if (abs >= 1.0F) {
         boolean var10000 = true;
         int n13 = 100 + randomizeInt(-30, 30);
         n = (float)((double)n + (double)randomizeInt(-n13, n13) / 100.0D);
      } else if ((double)abs <= 0.04D) {
         n = (float)((double)n + (abs > 0.0F ? 0.01D : -0.01D));
      }

      return new float[]{n, clampTo90(n2)};
   }

   public static boolean overPlaceable(double yOffset) {
      BlockPos playerPos = new BlockPos(IMinecraft.mc.thePlayer.posX, IMinecraft.mc.thePlayer.posY + yOffset, IMinecraft.mc.thePlayer.posZ);
      return isPlaceable(playerPos);
   }

   public static boolean isPlaceable(BlockPos blockPos) {
      return BlockUtils.replaceable(blockPos) || BlockUtils.isFluid(BlockUtils.getBlock(blockPos));
   }

   public static float clampTo90(float n) {
      return MathHelper.clamp_float(n, -90.0F, 90.0F);
   }

   public static int randomizeInt(int min, int max) {
      return rand.nextInt(max - min + 1) + min;
   }

   public static float[] getRotations(BlockPos blockPos) {
      double n = (double)blockPos.getX() + 0.45D - IMinecraft.mc.thePlayer.posX;
      double n2 = (double)blockPos.getY() + 0.45D - (IMinecraft.mc.thePlayer.posY + (double)IMinecraft.mc.thePlayer.getEyeHeight());
      double n3 = (double)blockPos.getZ() + 0.45D - IMinecraft.mc.thePlayer.posZ;
      return new float[]{IMinecraft.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 57.295780181884766D) - 90.0F - IMinecraft.mc.thePlayer.rotationYaw), clampTo90(IMinecraft.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, (double)MathHelper.sqrt_double(n * n + n3 * n3)) * 57.295780181884766D)) - IMinecraft.mc.thePlayer.rotationPitch))};
   }

   public static float getYaw(Entity ent) {
      double x = ent.posX - IMinecraft.mc.thePlayer.posX;
      double z = ent.posZ - IMinecraft.mc.thePlayer.posZ;
      double yaw = Math.atan2(x, z) * 57.29577951308232D;
      return (float)(yaw * -1.0D);
   }

   public static float ae(float n, float n2, float n3) {
      float n4 = 1.0F;
      if (n2 < 0.0F) {
         n += 180.0F;
         n4 = -0.5F;
      } else if (n2 > 0.0F) {
         n4 = 0.5F;
      }

      if (n3 > 0.0F) {
         n -= 90.0F * n4;
      } else if (n3 < 0.0F) {
         n += 90.0F * n4;
      }

      return n * 0.017453292F;
   }

   public static float n() {
      return ae(IMinecraft.mc.thePlayer.rotationYaw, IMinecraft.mc.thePlayer.movementInput.moveForward, IMinecraft.mc.thePlayer.movementInput.moveStrafe);
   }

   public static double n(Entity en) {
      return ((double)(IMinecraft.mc.thePlayer.rotationYaw - getYaw(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
   }

   public static void setSpeed(double n) {
      if (n == 0.0D) {
         IMinecraft.mc.thePlayer.motionZ = 0.0D;
         IMinecraft.mc.thePlayer.motionX = 0.0D;
      } else {
         float n3 = n();
         IMinecraft.mc.thePlayer.motionX = -Math.sin((double)n3) * n;
         IMinecraft.mc.thePlayer.motionZ = Math.cos((double)n3) * n;
      }
   }

   public static int getSpeedAmplifier() {
      return IMinecraft.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1 + IMinecraft.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() : 0;
   }

   public static double getHorizontalSpeed() {
      return getHorizontalSpeed(IMinecraft.mc.thePlayer);
   }

   public static double getHorizontalSpeed(Entity entity) {
      return Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
   }

   public static double distanceToGroundPos(Entity entity, int groundPos) {
      if (entity.onGround) {
         return 0.0D;
      } else {
         double fallDistance = -1.0D;
         double y = entity.posY;
         if (entity.posY % 1.0D == 0.0D) {
            --y;
         }

         for(int i = (int)Math.floor(y); i > -1; --i) {
            if (i == groundPos) {
               fallDistance = y - (double)i;
               break;
            }
         }

         return fallDistance - 1.0D;
      }
   }

   public static boolean isDiagonal(boolean strict) {
      float yaw = (IMinecraft.mc.thePlayer.rotationYaw % 360.0F + 360.0F) % 360.0F;
      yaw = yaw > 180.0F ? yaw - 360.0F : yaw;
      boolean isYawDiagonal = inBetween(-170.0D, 170.0D, (double)yaw) && !inBetween(-10.0D, 10.0D, (double)yaw) && !inBetween(80.0D, 100.0D, (double)yaw) && !inBetween(-100.0D, -80.0D, (double)yaw);
      if (strict) {
         isYawDiagonal = inBetween(-178.5D, 178.5D, (double)yaw) && !inBetween(-1.5D, 1.5D, (double)yaw) && !inBetween(88.5D, 91.5D, (double)yaw) && !inBetween(-91.5D, -88.5D, (double)yaw);
      }

      boolean isStrafing = Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(IMinecraft.mc.gameSettings.keyBindRight.getKeyCode());
      return isYawDiagonal || isStrafing;
   }

   public static boolean isCentered() {
      double posX = IMinecraft.mc.thePlayer.posX % 1.0D;
      double posZ = IMinecraft.mc.thePlayer.posZ % 1.0D;
      return posX < 0.5D && Math.abs(posZ - 0.5D) < 0.1D;
   }

   public static boolean inBetween(double min, double max, double value) {
      return value >= min && value <= max;
   }

   public static boolean isMoving() {
      return IMinecraft.mc.thePlayer.moveForward != 0.0F || IMinecraft.mc.thePlayer.moveStrafing != 0.0F;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "c";
         break;
      case 1:
         var10001[0] = "d";
      }

      var10001[1] = "vestige/util/util/Utils";
      var10001[2] = "correctValue";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
