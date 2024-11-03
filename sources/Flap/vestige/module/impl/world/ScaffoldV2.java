package vestige.module.impl.world;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.JumpEvent;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.MoveEvent;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.PreMotionEvent;
import vestige.event.impl.Render3DEvent;
import vestige.event.impl.RenderEvent;
import vestige.event.impl.TickEvent;
import vestige.event.impl.UpdateEvent;
import vestige.font.VestigeFontRenderer;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.visual.ClientTheme;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.player.FixedRotations;
import vestige.util.player.MoveCorrect;
import vestige.util.player.MovementUtil;
import vestige.util.player.PlayerUtil;
import vestige.util.player.RotationsUtil;
import vestige.util.render.RenderUtil;
import vestige.util.util.StrafeUtil;
import vestige.util.util.TimerUT;
import vestige.util.util.Utils;
import vestige.util.world.BlockInfo;
import vestige.util.world.BlockUtils;

public class ScaffoldV2 extends Module {
   private final DoubleSetting motionX = new DoubleSetting("Motion X", 0.8D, 0.5D, 1.2D, 0.01D);
   private final ModeSetting rotationsMode = new ModeSetting("Rotation Mode", "Strict", new String[]{"None", "Simple", "Strict", "Back", "Telly"});
   private final ModeSetting blockPicker = new ModeSetting("Block picker", "Spoof", new String[]{"None", "Switch", "Spoof"});
   private final ModeSetting fastScaffoldModes = new ModeSetting("Fast Scaffold", "Edge", new String[]{"Sprint", "Edge", "Watchdog Jump", "Float"});
   private final DoubleSetting fastScaffoldMotion = new DoubleSetting("Fast Scaffold Motion X", 0.8D, 0.5D, 1.2D, 0.01D);
   private final ModeSetting multiPlace = new ModeSetting("Multi-Place", "Disabled", new String[]{"Disabled", "1 extra", "2 extra"});
   private final BooleanSetting autoswap = new BooleanSetting("Auto Swap", true);
   private final BooleanSetting calcelKnockBack = new BooleanSetting("Cancel KnockBack", false);
   private final BooleanSetting delayOnJump = new BooleanSetting("Delay on jump", true);
   private final BooleanSetting fastOnRMB = new BooleanSetting("Fast on RMB", false);
   public final BooleanSetting safeWalk = new BooleanSetting("Safewalk", true);
   private final BooleanSetting blockCount = new BooleanSetting("Block Count", true);
   private final BooleanSetting silentSwing = new BooleanSetting("Silent swing", false);
   private final BooleanSetting showblocks = new BooleanSetting("Show Blocks", false);
   private final BooleanSetting firstticksdisabled = new BooleanSetting("Stop in first ticks", false);
   private final BooleanSetting fastfall = new BooleanSetting("Fast Fall", true);
   private final BooleanSetting securejump = new BooleanSetting("Ctrl Jump", true);
   private final BooleanSetting movecorrect = new BooleanSetting("Moviment Correction", false);
   private final BooleanSetting tower = new BooleanSetting("Tower", true);
   private final ModeSetting towermode = new ModeSetting("Tower Mode", "Disabled", new String[]{"Disabled", "Mush Vanilla", "LowHop", "Hypixel"});
   private final ModeSetting blockcounter = new ModeSetting("Block Counter", "Disabled", new String[]{"Disabled", "Number", "Flap"});
   public float placeYaw;
   public float placePitch;
   public int at;
   public int index;
   public boolean rmbDown;
   private double startPos = -1.0D;
   private Map<BlockPos, TimerUT> highlight = new HashMap();
   private boolean forceStrict;
   private boolean down;
   private boolean delay;
   private boolean place;
   private int add;
   private boolean placedUp;
   private float[] previousRotation;
   private int oldSlot;
   private FixedRotations rotations;
   public static final Set<EnumFacing> LIMIT_FACING;
   public static final MoveCorrect moveCorrect;
   private boolean towering;
   private int towerTicks;
   private int enableticks;
   private boolean blockPlaceRequest = false;
   private int lastOnGroundY;
   private BlockPos deltaPlace;
   private int blockSlot;
   public int blocksPlaced;
   public BlockPos previousBlock;
   private EnumFacing[] facings;
   private BlockPos[] offsets;
   private MovingObjectPosition placeBlock;
   public AtomicInteger lastSlot;
   private int verticalPlaced;
   private BlockInfo info;
   private BlockInfo prevBlockInfo;
   private double lastY;
   private boolean hadBlockInfo;
   private int offGroundTicks;
   private VestigeFontRenderer productSansBold;
   private VestigeFontRenderer productSans;
   private ClientTheme theme;
   boolean wasSlow;
   int jumpTick;
   float yaw;

   public ScaffoldV2() {
      super("Scaffold", Category.WORLD);
      this.deltaPlace = BlockPos.ORIGIN;
      this.blockSlot = -1;
      this.facings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP};
      this.offsets = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0)};
      this.lastSlot = new AtomicInteger(-1);
      this.verticalPlaced = 0;
      this.wasSlow = false;
      this.jumpTick = 0;
      this.yaw = 0.0F;
      this.addSettings(new AbstractSetting[]{this.motionX, this.rotationsMode, this.fastScaffoldModes, this.fastScaffoldMotion, this.multiPlace, this.autoswap, this.calcelKnockBack, this.delayOnJump, this.fastOnRMB, this.safeWalk, this.blockCount, this.silentSwing, this.showblocks, this.fastfall, this.securejump, this.movecorrect, this.tower, this.firstticksdisabled, this.blockPicker, this.towermode, this.blockcounter});
   }

   @Listener
   public void onEnable() {
      MovementUtil.stop();
      MovementUtil.stop();
      MovementUtil.stop();
      MovementUtil.stop();
      MovementUtil.stop();
      MovementUtil.stop();
      MovementUtil.stop();
      this.oldSlot = mc.thePlayer.inventory.currentItem;
      this.startPos = mc.thePlayer.posY;
      this.placePitch = 85.0F;
      this.previousRotation = null;
      this.placeYaw = (mc.thePlayer.rotationYaw + 180.0F) % 360.0F;
   }

   @Listener
   public void onMove(MoveEvent event) {
      if (mc.gameSettings.keyBindJump.isKeyDown()) {
         boolean airUnder = negativeExpand(0.239D);
         String var3 = this.towermode.getMode();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -1248403467:
            if (var3.equals("Hypixel")) {
               var4 = 1;
            }
            break;
         case -1023252392:
            if (var3.equals("Mush Vanilla")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            if (mc.thePlayer.isPotionActive(Potion.jump)) {
               return;
            }

            if (MovementUtil.speed() > 0.1D || !MovementUtil.isMoving()) {
               double towerSpeed = isGoingDiagonally(0.1D) ? 0.31D : 0.31888888D;
               if (!mc.thePlayer.onGround) {
                  if (this.towering) {
                     if (this.towerTicks == 2) {
                        event.setY(Math.floor(mc.thePlayer.posY + 1.0D) - mc.thePlayer.posY);
                     } else if (this.towerTicks == 3) {
                        event.setY(mc.thePlayer.motionY = 0.4198499917984009D);
                        if (MovementUtil.isMoving()) {
                           MovementUtil.strafe((double)((float)towerSpeed) - this.randomAmount());
                        }

                        this.towerTicks = 0;
                     }
                  }
               } else {
                  this.towering = !airUnder;
                  if (this.towering) {
                     this.towerTicks = 0;
                     if (event.getY() > 0.0D) {
                        event.setY(mc.thePlayer.motionY = 0.4198479950428009D);
                        if (MovementUtil.isMoving()) {
                           MovementUtil.strafe((double)((float)towerSpeed) - this.randomAmount());
                        }
                     }
                  }
               }

               ++this.towerTicks;
            }
            break;
         case 1:
            if (mc.thePlayer.isPotionActive(Potion.jump)) {
               return;
            }

            boolean airUndere = !BlockUtils.insideBlock(mc.thePlayer.getEntityBoundingBox().offset(0.0D, -1.0D, 0.0D).expand(0.3D, 0.0D, 0.3D));
            if (MovementUtil.isMoving()) {
               double towerSpeed = isGoingDiagonally(0.1D) ? 0.2D : 0.29888888D;
               if (!mc.thePlayer.onGround) {
                  if (this.towering) {
                     if (this.towerTicks == 2) {
                        event.setY(Math.floor(mc.thePlayer.posY + 1.0D) - mc.thePlayer.posY);
                     } else if (this.towerTicks == 3) {
                        if (this.canTower()) {
                           event.setY(mc.thePlayer.motionY = 0.4198499917984009D);
                           if (MovementUtil.isMoving()) {
                              MovementUtil.strafe((double)((float)towerSpeed) - this.randomAmount());
                           }

                           this.towerTicks = 0;
                        } else {
                           this.towering = false;
                        }
                     }
                  }
               } else {
                  this.towering = this.canTower() && !airUnder;
                  if (this.towering) {
                     this.towerTicks = 0;

                     try {
                        Field jumpTicksField = EntityPlayer.class.getDeclaredField("jumpTicks");
                        jumpTicksField.setAccessible(true);
                        jumpTicksField.setInt(mc.thePlayer, 0);
                     } catch (IllegalAccessException | NoSuchFieldException var9) {
                        var9.printStackTrace();
                     }

                     if (event.getY() > 0.0D) {
                        event.setY(mc.thePlayer.motionY = 0.4198479950428009D);
                        if (MovementUtil.isMoving()) {
                           MovementUtil.strafe((double)((float)towerSpeed) - this.randomAmount());
                        }
                     }
                  }
               }

               ++this.towerTicks;
            }
         }
      }

   }

   public boolean onDisable() {
      Flap.instance.getSlotSpoofHandler().stopSpoofing();
      this.enableticks = 0;
      mc.thePlayer.inventory.currentItem = this.oldSlot;
      this.placeBlock = null;
      this.delay = false;
      this.highlight.clear();
      this.add = 0;
      this.at = this.index = 0;
      this.startPos = -1.0D;
      this.forceStrict = false;
      this.down = false;
      this.place = false;
      this.placedUp = false;
      this.blockSlot = -1;
      this.blocksPlaced = 0;
      return false;
   }

   public static boolean isGoingDiagonally(double amount) {
      return Math.abs(mc.thePlayer.motionX) > amount && Math.abs(mc.thePlayer.motionZ) > amount;
   }

   public static boolean negativeExpand(double negativeExpandValue) {
      return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX + negativeExpandValue, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + negativeExpandValue)).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ - negativeExpandValue)).getBlock() instanceof BlockAir;
   }

   private double randomAmount() {
      return 8.0E-4D + Math.random() * 0.008D;
   }

   @Listener
   public void onPreMotion(PreMotionEvent event) {
      String var2 = this.towermode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -2013023115:
         if (var2.equals("LowHop")) {
            var3 = 1;
         }
         break;
      case -1023252392:
         if (var2.equals("Mush Vanilla")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 1:
         if (mc.gameSettings.keyBindJump.isKeyDown()) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.motionY = 0.4196D;
            } else {
               Utils.setSpeed(Math.max(0.15000000000000002D, 0.0D));
               switch(this.offGroundTicks) {
               case 3:
               case 4:
                  mc.thePlayer.motionY = 0.0D;
                  break;
               case 5:
                  mc.thePlayer.motionY = 0.4191D;
                  break;
               case 6:
                  mc.thePlayer.motionY = 0.3275D;
               case 7:
               case 8:
               case 9:
               case 10:
               default:
                  break;
               case 11:
                  mc.thePlayer.motionY = -0.5D;
               }
            }
         }
      case 0:
      default:
      }
   }

   public boolean onPreSchedulePlace() {
      return !this.noPlace();
   }

   private boolean noPlace() {
      return this.offGroundTicks >= 0 && this.offGroundTicks < 1;
   }

   @Listener
   public void onMotion(MotionEvent event) {
      if (PlayerUtil.nullCheck()) {
         if (!this.rotationsMode.getMode().equals("None")) {
            float var10000 = 85.0F;
            if (this.rotationsMode.getMode() == "Telly") {
               if (Keyboard.isKeyDown(29) && this.securejump.isEnabled()) {
                  if (this.enableticks > 10) {
                     if (!mc.gameSettings.keyBindJump.isKeyDown()) {
                        if (this.noPlace()) {
                           event.setYaw(event.getYaw());
                           event.setPitch(event.getPitch());
                        } else {
                           event.setYaw(this.placeYaw);
                           event.setPitch(this.placePitch);
                        }
                     } else {
                        event.setYaw(this.placeYaw);
                        event.setPitch(this.placePitch);
                     }
                  } else {
                     event.setYaw(this.placeYaw);
                     event.setPitch(this.placePitch);
                  }
               } else {
                  event.setYaw(this.placeYaw);
                  event.setPitch(this.placePitch);
               }
            }

            if (this.rotationsMode.getMode().equals("Back")) {
               event.setPitch(this.placePitch);
               event.setYaw((mc.thePlayer.rotationYaw + 180.0F) % 360.0F);
            }

            if ((!this.rotationsMode.getMode().equals("Strict") || !this.forceStrict) && (!this.rotationsMode.getMode().equals("Precise") || this.placeYaw == 2000.0F)) {
               if (!this.rotationsMode.getMode().equals("Telly") && !this.rotationsMode.getMode().equals("Back")) {
                  event.setYaw(this.placeYaw);
                  event.setPitch(this.placePitch);
               }
            } else {
               event.setPitch(this.placePitch);
               event.setYaw(this.placeYaw);
            }
         }

         this.place = true;
      }
   }

   private void pickBlock() {
      if (!this.blockPicker.is("None")) {
         int highestStackSize = -1;
         int bestSlot = -1;

         for(int i = 0; i < 9; ++i) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlock && stack.stackSize > 0 && stack.stackSize > highestStackSize) {
               highestStackSize = stack.stackSize;
               bestSlot = i;
            }
         }

         if (bestSlot != -1) {
            mc.thePlayer.inventory.currentItem = bestSlot;
         }
      }

      if (this.blockPicker.is("Spoof")) {
         Flap.instance.getSlotSpoofHandler().startSpoofing(this.oldSlot);
      }

   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      if (this.showblocks.isEnabled()) {
         BlockPos lowestBlockPos = BlockUtils.getLowestSolidBlockUnderPlayer(mc.thePlayer);
         if (lowestBlockPos != null && this.theme != null) {
            RenderUtil.renderBlock(lowestBlockPos, this.theme.getColor(1), true, true);
         }
      }

   }

   @Listener
   public final void onRender(RenderEvent event) {
      boolean inChat = mc.currentScreen instanceof GuiChat;
      ScaledResolution scaledRes = new ScaledResolution(mc);
      int screenWidth = scaledRes.getScaledWidth();
      int screenHeight = scaledRes.getScaledHeight();
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.productSansBold = Flap.instance.getFontManager().getProductSansBold20();
      int totalBlocksCount = Integer.parseInt(this.totalBlocks());
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      if (this.isEnabled() || inChat) {
         String var8 = this.blockcounter.getMode();
         byte var9 = -1;
         switch(var8.hashCode()) {
         case -1950496919:
            if (var8.equals("Number")) {
               var9 = 0;
            }
            break;
         case 2192277:
            if (var8.equals("Flap")) {
               var9 = 1;
            }
         }

         switch(var9) {
         case 0:
            int color;
            if (totalBlocksCount < 16) {
               color = 16711680;
            } else if (totalBlocksCount < 30) {
               color = 16776960;
            } else {
               color = 65280;
            }

            this.productSansBold.drawStringWithShadow(this.totalBlocks(), (double)(screenWidth / 2) - this.productSansBold.getStringWidth(this.totalBlocks()) / 2.0D, (double)screenHeight / 1.9D, color);
            break;
         case 1:
            mc.thePlayer.getHeldItem();
            int totalWidth = (int)(this.productSansBold.getStringWidth(this.totalBlocks()) + this.productSans.getStringWidth(" blocks"));
            int posX = screenWidth / 2 - totalWidth / 2;
            int posY = (int)((double)screenHeight / 1.9D);
            this.productSansBold.drawStringWithShadow(this.totalBlocks(), (float)posX, (float)posY, this.theme.getColor(1));
            this.productSans.drawStringWithShadow(" blocks", (double)posX + this.productSansBold.getStringWidth(this.totalBlocks()), (double)posY, -1);
         }
      }

   }

   @Listener
   public void onJump(JumpEvent e) {
      this.delay = true;
   }

   @Listener
   public void onTick(TickEvent event) {
      if (this.movecorrect.isEnabled()) {
         float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(this.placeYaw) - MathHelper.wrapAngleTo180_float(MovementUtil.getPlayerDirection())) + 22.5F;
         if (diff < 0.0F) {
            diff += 360.0F;
         }

         int a = (int)((double)diff / 45.0D);
         float value = mc.thePlayer.moveForward != 0.0F ? Math.abs(mc.thePlayer.moveForward) : Math.abs(mc.thePlayer.moveStrafing);
         float forward = value;
         float strafe = 0.0F;

         for(int i = 0; i < 8 - a; ++i) {
            float[] dirs = MovementUtil.incrementMoveDirection(forward, strafe);
            forward = dirs[0];
            strafe = dirs[1];
         }

         if (forward < 0.8F) {
            mc.gameSettings.keyBindSprint.pressed = false;
            mc.thePlayer.setSprinting(false);
         }
      }

   }

   @Listener
   public void onPreUpdate(UpdateEvent e) {
      if (mc.thePlayer.onGround) {
         this.offGroundTicks = 0;
      } else {
         ++this.offGroundTicks;
      }

      if (!mc.gameSettings.keyBindJump.isKeyDown()) {
         float amplifier;
         Timer var10000;
         if (Keyboard.isKeyDown(29) && this.securejump.isEnabled()) {
            if (this.fastfall.isEnabled() && (this.offGroundTicks == 5 || this.offGroundTicks == 6)) {
               mc.thePlayer.motionY = MovementUtil.predictedMotion(mc.thePlayer.motionY, 1);
            }

            if (mc.thePlayer.onGround) {
               if (MovementUtil.isMoving()) {
                  this.wasSlow = false;
                  if (this.jumpTick > 6) {
                     this.jumpTick = 5;
                  }

                  mc.thePlayer.jump();
                  MovementUtil.strafe((double)(0.475F + (float)this.jumpTick * 0.007F));
                  if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     amplifier = (float)mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                     MovementUtil.strafe((double)(0.46F + (float)this.jumpTick * 0.008F + 0.023F * (amplifier + 1.0F)));
                  }
               } else {
                  this.jumpTick = 0;
               }
            } else {
               if (mc.thePlayer.ticksExisted == 1) {
                  MovementUtil.strafe(0.34125F, this.yaw);
                  if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     MovementUtil.strafe(0.37F, this.yaw);
                  }

                  return;
               }

               if (mc.thePlayer.ticksExisted < 13) {
                  if (mc.thePlayer.motionY < 0.0D) {
                     var10000 = mc.timer;
                     var10000.timerSpeed = 1.0F;
                  } else {
                     var10000 = mc.timer;
                     var10000.timerSpeed = 0.95F;
                  }
               } else {
                  var10000 = mc.timer;
                  var10000.timerSpeed = 1.0F;
               }

               if (!PlayerUtil.isOverAir(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.motionY + 1.0D, mc.thePlayer.posZ) && mc.thePlayer.ticksExisted > 2) {
                  MovementUtil.strafe();
               }

               if (this.wasSlow) {
                  if (!StrafeUtil.isConsecutiveStrafing()) {
                     MovementUtil.strafe(0.14000000059604645D);
                  }

                  this.wasSlow = false;
               }

               if (Math.abs(MathHelper.wrapAngleTo180_float(PlayerUtil.getBindsDirection(mc.thePlayer.rotationYaw) - RotationsUtil.getRotations(new Vec3(0.0D, 0.0D, 0.0D), new Vec3(mc.thePlayer.motionX, 0.0D, mc.thePlayer.motionZ))[0])) > 110.0F) {
                  MovementUtil.stop();
                  var10000 = mc.timer;
                  var10000.timerSpeed = 0.6F;
                  MovementUtil.customStrafeStrength(70.0D);
                  MovementUtil.stop();
                  var10000 = mc.timer;
                  var10000.timerSpeed = 1.0F;
                  this.wasSlow = true;
               }
            }
         } else if (!this.securejump.isEnabled()) {
            if (this.fastfall.isEnabled() && (this.offGroundTicks == 5 || this.offGroundTicks == 6)) {
               mc.thePlayer.motionY = MovementUtil.predictedMotion(mc.thePlayer.motionY, 1);
            }

            if (mc.thePlayer.onGround) {
               if (MovementUtil.isMoving()) {
                  this.wasSlow = false;
                  if (this.jumpTick > 6) {
                     this.jumpTick = 5;
                  }

                  mc.thePlayer.jump();
                  MovementUtil.strafe((double)(0.475F + (float)this.jumpTick * 0.007F));
                  if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     amplifier = (float)mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                     MovementUtil.strafe((double)(0.46F + (float)this.jumpTick * 0.008F + 0.023F * (amplifier + 1.0F)));
                  }
               } else {
                  this.jumpTick = 0;
               }
            } else {
               if (mc.thePlayer.ticksExisted == 1) {
                  MovementUtil.strafe(0.34125F, this.yaw);
                  if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     MovementUtil.strafe(0.37F, this.yaw);
                  }

                  return;
               }

               if (mc.thePlayer.ticksExisted < 13) {
                  if (mc.thePlayer.motionY < 0.0D) {
                     var10000 = mc.timer;
                     var10000.timerSpeed = 1.0F;
                  } else {
                     var10000 = mc.timer;
                     var10000.timerSpeed = 0.95F;
                  }
               } else {
                  var10000 = mc.timer;
                  var10000.timerSpeed = 1.0F;
               }

               if (!PlayerUtil.isOverAir(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.motionY + 1.0D, mc.thePlayer.posZ) && mc.thePlayer.ticksExisted > 2) {
                  MovementUtil.strafe();
               }

               if (this.wasSlow) {
                  if (!StrafeUtil.isConsecutiveStrafing()) {
                     MovementUtil.strafe(0.14000000059604645D);
                  }

                  this.wasSlow = false;
               }

               if (Math.abs(MathHelper.wrapAngleTo180_float(PlayerUtil.getBindsDirection(mc.thePlayer.rotationYaw) - RotationsUtil.getRotations(new Vec3(0.0D, 0.0D, 0.0D), new Vec3(mc.thePlayer.motionX, 0.0D, mc.thePlayer.motionZ))[0])) > 110.0F) {
                  var10000 = mc.timer;
                  var10000.timerSpeed = 0.6F;
                  MovementUtil.strafe();
                  MovementUtil.stop();
                  var10000 = mc.timer;
                  var10000.timerSpeed = 1.0F;
                  this.wasSlow = true;
               }
            }
         }
      }

      ++this.enableticks;
      this.pickBlock();
      if (this.delay && this.delayOnJump.isEnabled()) {
         this.delay = false;
      } else {
         ItemStack heldItem = mc.thePlayer.getHeldItem();
         if (this.autoswap.isEnabled() && this.getSlot() != -1 || heldItem != null && heldItem.getItem() instanceof ItemBlock && Utils.canBePlaced((ItemBlock)heldItem.getItem())) {
            if (this.keepYPosition() && !this.down) {
               this.startPos = Math.floor(mc.thePlayer.posY);
               this.down = true;
            } else if (!this.keepYPosition() || Math.floor(mc.thePlayer.posY) < this.startPos) {
               this.down = false;
               this.placedUp = false;
            }

            if (this.keepYPosition() && (this.fastScaffoldModes.getMode() == "Jump A" || this.fastScaffoldModes.getMode() == "Jump B" || this.fastScaffoldModes.getMode() == "Watchdog Jump") && mc.thePlayer.onGround) {
               if (this.fastScaffoldModes.getMode() == "Watchdog Jump" && this.rotationsMode.getMode() == "Telly" && this.enableticks <= 7 && this.firstticksdisabled.isEnabled()) {
                  if (this.enableticks < 10) {
                     MovementUtil.stop();
                  } else {
                     EntityPlayerSP var55 = mc.thePlayer;
                     var55.motionX *= 0.6D;
                     var55 = mc.thePlayer;
                     var55.motionZ *= 0.6D;
                  }
               }

               this.add = 0;
            }

            double original = this.startPos;
            double motionSetting;
            if (this.fastScaffoldModes.getMode() == "Jump A") {
               if (this.groundDistance() >= 2.0D && this.add == 0) {
                  ++original;
                  ++this.add;
               }
            } else if (this.fastScaffoldModes.getMode().equals("Jump B") || this.fastScaffoldModes.getMode().equals("Watchdog Jump")) {
               motionSetting = Utils.distanceToGroundPos(mc.thePlayer, (int)this.startPos);
               double threshold = 1.0D;
               boolean isGroundDistanceValid = this.groundDistance() > 0.0D;
               boolean isDistanceToGroundValid = motionSetting > 0.0D && motionSetting < threshold;
               boolean isYPositionValid = threshold == 0.6D ? mc.thePlayer.posY - this.startPos < 1.5D : true;
               boolean isFallDistanceValid = mc.thePlayer.fallDistance > 0.0F;
               boolean var56;
               if (this.placedUp && !Utils.isCentered()) {
                  var56 = false;
               } else {
                  var56 = true;
               }

               if (isGroundDistanceValid && isDistanceToGroundValid && isYPositionValid && isFallDistanceValid) {
                  ++original;
               }
            }

            motionSetting = this.sprint() ? this.fastScaffoldMotion.getValue() : this.motionX.getValue();
            int slot;
            if (mc.thePlayer.onGround && Utils.isMoving() && motionSetting != 1.0D) {
               slot = Utils.getSpeedAmplifier();
               switch(slot) {
               case 1:
                  motionSetting = this.motionX.getValue() - 0.022D;
                  break;
               case 2:
                  motionSetting = this.motionX.getValue() - 0.04D;
               }

               Utils.setSpeed(Utils.getHorizontalSpeed() * motionSetting);
            }

            slot = this.getSlot();
            if (slot != -1) {
               if (this.blockSlot == -1) {
                  this.blockSlot = slot;
               }

               if (this.lastSlot.get() == -1) {
                  this.lastSlot.set(mc.thePlayer.inventory.currentItem);
               }

               heldItem = mc.thePlayer.getHeldItem();
               if (heldItem != null && heldItem.getItem() instanceof ItemBlock && Utils.canBePlaced((ItemBlock)heldItem.getItem())) {
                  MovingObjectPosition rayCasted = null;
                  float searchYaw = 90.0F;
                  BlockPos targetPos = new BlockPos(mc.thePlayer.posX, this.keepYPosition() ? original - 1.0D : mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                  ScaffoldV2.PlaceData placeData = this.getBlockData(targetPos);
                  if (placeData != null && placeData.blockPos != null && placeData.enumFacing != null) {
                     float[] targetRotation = Utils.getRotations(placeData.blockPos);
                     Vec3 bestVec = this.getVec3(placeData.enumFacing);
                     float[] var57 = new float[]{78.0F, 12.0F};
                     double closestCombinedDistance = Double.MAX_VALUE;
                     double offsetWeight = 0.2D;

                     for(int i = 0; i < 2; ++i) {
                        searchYaw = 90.0F;
                        float[] searchPitch = new float[]{65.0F, 25.0F};
                        float[] yawSearchList = this.generateSearchSequence(searchYaw);
                        float[] pitchSearchList = this.generateSearchSequence(searchPitch[1]);
                        float[] var22 = yawSearchList;
                        int var23 = yawSearchList.length;

                        for(int var24 = 0; var24 < var23; ++var24) {
                           float checkYaw = var22[var24];
                           float playerYaw = this.getYaw();
                           float fixedYaw = (float)((double)(playerYaw + checkYaw) + this.getRandom());
                           if (Utils.overPlaceable(-1.0D) || this.keepYPosition()) {
                              float[] var28 = pitchSearchList;
                              int var29 = pitchSearchList.length;

                              for(int var30 = 0; var30 < var29; ++var30) {
                                 float checkPitch = var28[var30];
                                 float fixedPitch = Utils.clampTo90((float)((double)(targetRotation[1] + checkPitch) + this.getRandom()));
                                 MovingObjectPosition raycast = RotationsUtil.rayCast((double)mc.playerController.getBlockReachDistance(), fixedYaw, fixedPitch);
                                 if (raycast != null && raycast.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && raycast.getBlockPos().equals(placeData.blockPos) && raycast.sideHit == placeData.getEnumFacing() && ((ItemBlock)heldItem.getItem()).canPlaceBlockOnSide(mc.theWorld, raycast.getBlockPos(), raycast.sideHit, mc.thePlayer, heldItem)) {
                                    double offSetX = raycast.hitVec.xCoord - (double)raycast.getBlockPos().getX();
                                    double offSetY = raycast.hitVec.yCoord - (double)raycast.getBlockPos().getY();
                                    double offSetZ = raycast.hitVec.zCoord - (double)raycast.getBlockPos().getZ();
                                    Vec3 offset = new Vec3(offSetX, offSetY, offSetZ);
                                    double distanceToCenter = offset.distanceTo(bestVec);
                                    double distanceToPreviousRotation = this.previousRotation != null ? (double)Math.abs(fixedYaw - this.previousRotation[0]) : 0.0D;
                                    double combinedDistance = offsetWeight * distanceToCenter + distanceToPreviousRotation / 360.0D;
                                    if (rayCasted == null || combinedDistance < closestCombinedDistance) {
                                       closestCombinedDistance = combinedDistance;
                                       rayCasted = raycast;
                                       this.placeYaw = fixedYaw;
                                       this.placePitch = fixedPitch;
                                       if (this.forceStrict(checkYaw) && i == 1) {
                                          this.forceStrict = true;
                                       } else {
                                          this.forceStrict = false;
                                       }
                                    }
                                 }
                              }
                           }
                        }

                        if (rayCasted != null) {
                           break;
                        }
                     }

                     if (rayCasted != null && (this.place || this.rotationsMode.getMode() == "None")) {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                        this.placeBlock = rayCasted;
                        this.place(this.placeBlock, false);
                        int input = 0;

                        for(int i = 0; i < input; ++i) {
                           this.place(this.placeBlock, true);
                        }

                        this.place = false;
                        if (this.placeBlock.sideHit == EnumFacing.UP && this.keepYPosition()) {
                           this.placedUp = true;
                        }

                        this.previousBlock = placeData.blockPos.offset(placeData.getEnumFacing());
                     }

                  }
               } else {
                  this.blockSlot = -1;
               }
            }
         }
      }
   }

   @Listener
   public void onReceivePacket(PacketReceiveEvent e) {
      if (PlayerUtil.nullCheck() && this.calcelKnockBack.isEnabled()) {
         if (e.getPacket() instanceof S12PacketEntityVelocity) {
            if (((S12PacketEntityVelocity)e.getPacket()).getEntityID() == mc.thePlayer.getEntityId()) {
               e.setCancelled(true);
            }
         } else if (e.getPacket() instanceof S27PacketExplosion) {
            e.setCancelled(true);
         }

      }
   }

   public float[] generateSearchSequence(float value) {
      int length = (int)value * 2;
      float[] sequence = new float[length + 1];
      int index = 0;
      int var6 = index + 1;
      sequence[index] = 0.0F;

      for(int i = 1; (float)i <= value; ++i) {
         sequence[var6++] = (float)i;
         sequence[var6++] = (float)(-i);
      }

      return sequence;
   }

   public boolean canTower() {
      if (mc.currentScreen != null) {
         return false;
      } else {
         return PlayerUtil.nullCheck() && Utils.jumpDown();
      }
   }

   public ScaffoldV2.PlaceData getBlockData(BlockPos pos) {
      if (this.previousBlock != null && (double)this.previousBlock.getY() > mc.thePlayer.posY) {
         this.previousBlock = null;
      }

      for(int lastCheck = 0; lastCheck < 2; ++lastCheck) {
         for(lastCheck = 0; lastCheck < this.offsets.length; ++lastCheck) {
            BlockPos newPos = pos.add(this.offsets[lastCheck]);
            Block block = BlockUtils.getBlock(newPos);
            if (newPos.equals(this.previousBlock)) {
               return new ScaffoldV2.PlaceData(this.facings[lastCheck], newPos);
            }

            if (lastCheck != 0 && !block.getMaterial().isReplaceable() && !BlockUtils.isInteractable(block)) {
               return new ScaffoldV2.PlaceData(this.facings[lastCheck], newPos);
            }
         }
      }

      BlockPos[] additionalOffsets = new BlockPos[]{pos.add(-1, 0, 0), pos.add(1, 0, 0), pos.add(0, 0, 1), pos.add(0, 0, -1), pos.add(0, -1, 0)};

      int var6;
      for(int lastCheck = 0; lastCheck < 2; ++lastCheck) {
         BlockPos[] var18 = additionalOffsets;
         int var20 = additionalOffsets.length;

         for(var6 = 0; var6 < var20; ++var6) {
            BlockPos additionalPos = var18[var6];

            for(int i = 0; i < this.offsets.length; ++i) {
               BlockPos newPos = additionalPos.add(this.offsets[i]);
               Block block = BlockUtils.getBlock(newPos);
               if (newPos.equals(this.previousBlock)) {
                  return new ScaffoldV2.PlaceData(this.facings[i], newPos);
               }

               if (lastCheck != 0 && !block.getMaterial().isReplaceable() && !BlockUtils.isInteractable(block)) {
                  return new ScaffoldV2.PlaceData(this.facings[i], newPos);
               }
            }
         }
      }

      BlockPos[] additionalOffsets2 = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0)};

      for(int lastCheck = 0; lastCheck < 2; ++lastCheck) {
         BlockPos[] var21 = additionalOffsets2;
         var6 = additionalOffsets2.length;

         for(int var22 = 0; var22 < var6; ++var22) {
            BlockPos additionalPos2 = var21[var22];
            BlockPos[] var24 = additionalOffsets;
            int var25 = additionalOffsets.length;

            for(int var11 = 0; var11 < var25; ++var11) {
               BlockPos additionalPos = var24[var11];

               for(int i = 0; i < this.offsets.length; ++i) {
                  BlockPos newPos = additionalPos2.add(additionalPos.add(this.offsets[i]));
                  Block block = BlockUtils.getBlock(newPos);
                  if (newPos.equals(this.previousBlock)) {
                     return new ScaffoldV2.PlaceData(this.facings[i], newPos);
                  }

                  if (lastCheck != 0 && !block.getMaterial().isReplaceable() && !BlockUtils.isInteractable(block)) {
                     return new ScaffoldV2.PlaceData(this.facings[i], newPos);
                  }
               }
            }
         }
      }

      return null;
   }

   public double groundDistance() {
      for(int i = 1; i <= 20; ++i) {
         if (!mc.thePlayer.onGround && !(BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - (double)(i / 10), mc.thePlayer.posZ)) instanceof BlockAir)) {
            return (double)(i / 10);
         }
      }

      return -1.0D;
   }

   public boolean blockAbove() {
      return !(BlockUtils.getBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ)) instanceof BlockAir);
   }

   public boolean sprint() {
      if (this.isEnabled() && this.fastScaffoldModes.getMode() != "Disabled" && this.placeBlock != null && (!this.fastOnRMB.isEnabled() || Mouse.isButtonDown(1))) {
         String var1 = this.fastScaffoldModes.getMode();
         byte var2 = -1;
         switch(var1.hashCode()) {
         case -2065002257:
            if (var1.equals("Jump A")) {
               var2 = 2;
            }
            break;
         case -2065002256:
            if (var1.equals("Jump B")) {
               var2 = 3;
            }
            break;
         case -1811812806:
            if (var1.equals("Sprint")) {
               var2 = 0;
            }
            break;
         case 2154973:
            if (var1.equals("Edge")) {
               var2 = 1;
            }
            break;
         case 67973692:
            if (var1.equals("Float")) {
               var2 = 5;
            }
            break;
         case 1511943777:
            if (var1.equals("Watchdog Jump")) {
               var2 = 4;
            }
         }

         switch(var2) {
         case 0:
            return true;
         case 1:
            return Utils.onEdge();
         case 2:
         case 3:
         case 4:
         case 5:
            return this.keepYPosition();
         }
      }

      return false;
   }

   private boolean forceStrict(float value) {
      return (Utils.inBetween(-170.0D, -105.0D, (double)value) || Utils.inBetween(-80.0D, 80.0D, (double)value) || Utils.inBetween(98.0D, 170.0D, (double)value)) && !Utils.inBetween(-10.0D, 10.0D, (double)value);
   }

   private boolean keepYPosition() {
      return this.isEnabled() && Utils.keysDown() && (this.fastScaffoldModes.getMode() == "Jump B" || this.fastScaffoldModes.getMode() == "Jump A" || this.fastScaffoldModes.getMode() == "Watchdog Jump" || this.fastScaffoldModes.getMode() == "Float") && (!Utils.jumpDown() || this.fastScaffoldModes.getMode() == "Float") && (!this.fastOnRMB.isEnabled() || Mouse.isButtonDown(1)) && (!this.blockAbove() || this.fastScaffoldModes.getMode() == "Float");
   }

   public boolean safewalk() {
      return this.isEnabled() && this.safeWalk.isEnabled() && (!this.keepYPosition() || this.fastScaffoldModes.getMode() == "Jump A" || this.totalBlocks().equals("0"));
   }

   public boolean stopRotation() {
      return this.isEnabled() && (this.rotationsMode.getMode() == "Simple" || this.rotationsMode.getMode() == "Strict" && this.placeBlock != null);
   }

   public float getYaw() {
      float yaw = 0.0F;
      double moveForward = (double)mc.thePlayer.movementInput.moveForward;
      double moveStrafe = (double)mc.thePlayer.movementInput.moveStrafe;
      if (moveForward == 0.0D) {
         if (moveStrafe == 0.0D) {
            yaw = 180.0F;
         } else if (moveStrafe > 0.0D) {
            yaw = 90.0F;
         } else if (moveStrafe < 0.0D) {
            yaw = -90.0F;
         }
      } else if (moveForward > 0.0D) {
         if (moveStrafe == 0.0D) {
            yaw = 180.0F;
         } else if (moveStrafe > 0.0D) {
            yaw = 135.0F;
         } else if (moveStrafe < 0.0D) {
            yaw = -135.0F;
         }
      } else if (moveForward < 0.0D) {
         if (moveStrafe == 0.0D) {
            yaw = 0.0F;
         } else if (moveStrafe > 0.0D) {
            yaw = 45.0F;
         } else if (moveStrafe < 0.0D) {
            yaw = -45.0F;
         }
      }

      return MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw) + yaw;
   }

   private boolean place(MovingObjectPosition block, boolean extra) {
      if (this.rotationsMode.getMode() == "Telly" && this.noPlace() && Keyboard.isKeyDown(29)) {
         return false;
      } else {
         ItemStack heldItem = mc.thePlayer.getHeldItem();
         if (heldItem != null && heldItem.getItem() instanceof ItemBlock && Utils.canBePlaced((ItemBlock)heldItem.getItem())) {
            if (!extra && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, heldItem, block.getBlockPos(), block.sideHit, block.hitVec)) {
               if (this.silentSwing.isEnabled()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
               } else {
                  mc.getItemRenderer().resetEquippedProgress();
                  mc.thePlayer.swingItem();
               }

               this.highlight.put(block.getBlockPos().offset(block.sideHit), new TimerUT(10f));
               this.previousRotation = new float[]{this.placeYaw, this.placePitch};
               if (heldItem.stackSize == 0) {
                  this.blockSlot = -1;
               }
            } else if (extra) {
               float f = (float)(block.hitVec.xCoord - (double)block.getBlockPos().getX());
               float f1 = (float)(block.hitVec.yCoord - (double)block.getBlockPos().getY());
               float f2 = (float)(block.hitVec.zCoord - (double)block.getBlockPos().getZ());
               mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(block.getBlockPos(), block.sideHit.getIndex(), heldItem, f, f1, f2));
               if (this.silentSwing.isEnabled()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
               } else {
                  mc.thePlayer.swingItem();
                  mc.getItemRenderer().resetEquippedProgress();
               }

               return true;
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private int getSlot() {
      int slot = -1;
      int highestStack = -1;
      mc.thePlayer.getHeldItem();

      for(int i = 0; i < 9; ++i) {
         ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
         if (itemStack != null && itemStack.getItem() instanceof ItemBlock && Utils.canBePlaced((ItemBlock)itemStack.getItem()) && itemStack.stackSize > 0 && (Utils.getBedwarsStatus() != 2 || !(((ItemBlock)itemStack.getItem()).getBlock() instanceof BlockTNT)) && itemStack.stackSize > highestStack) {
            highestStack = itemStack.stackSize;
            slot = i;
         }
      }

      return slot;
   }

   public String totalBlocks() {
      int totalBlocks = 0;

      for(int i = 0; i < 9; ++i) {
         ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
         if (stack != null && stack.getItem() instanceof ItemBlock && Utils.canBePlaced((ItemBlock)stack.getItem()) && stack.stackSize > 0) {
            totalBlocks += stack.stackSize;
         }
      }

      return Integer.toString(totalBlocks);
   }

   private Vec3 getVec3(EnumFacing facing) {
      double x = 0.5D;
      double y = 0.5D;
      double z = 0.5D;
      if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
         y += 0.5D;
      } else {
         x += 0.3D;
         z += 0.3D;
      }

      if (facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
         z += 0.15D;
      }

      if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH) {
         x += 0.15D;
      }

      return new Vec3(x, y, z);
   }

   private double getRandom() {
      return (double)Utils.randomizeInt(-40, 40) / 100.0D;
   }

   static {
      LIMIT_FACING = new HashSet(Collections.singleton(EnumFacing.SOUTH));
      moveCorrect = new MoveCorrect(0.3D, MoveCorrect.Mode.POSITION);
   }

   static class PlaceData {
      EnumFacing enumFacing;
      BlockPos blockPos;

      PlaceData(EnumFacing enumFacing, BlockPos blockPos) {
         this.enumFacing = enumFacing;
         this.blockPos = blockPos;
      }

      EnumFacing getEnumFacing() {
         return this.enumFacing;
      }
   }
}
