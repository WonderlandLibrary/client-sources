package cc.novoline.modules.player;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.DisplayChestGuiEvent;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.player.ChestStealer$Armor;
import cc.novoline.modules.player.ChestStealer$Tool;
import cc.novoline.modules.player.Freecam;
import cc.novoline.modules.player.InvManager;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Timer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class ChestStealer extends AbstractModule {
   private String[] list = new String[]{"mode", "delivery", "menu", "selector", "game", "gui", "server", "inventory", "play", "teleporter", "shop", "melee", "armor", "block", "castle", "mini", "warp", "teleport", "user", "team", "tool", "sure", "trade", "cancel", "accept", "soul", "book", "recipe", "profile", "tele", "port", "map", "kit", "select", "lobby", "vault", "lock", "anticheat", "travel", "settings", "user", "preference", "compass", "cake", "wars", "buy", "upgrade", "ranged", "potions", "utility"};
   private List M = new CopyOnWriteArrayList();
   private List containerSlots = new CopyOnWriteArrayList();
   private List chestIds = new CopyOnWriteArrayList();
   private Timer timer = new Timer();
   private Timer timerAura = new Timer();
   private boolean isStealing;
   private boolean slotsFilled;
   private int containerSize;
   private int windowID;
   @Property("auto_disable")
   private final ListProperty auto_disable = PropertyFactory.createList((Object)"Death").acceptableValues((Object[])(new String[]{"World Change", "Game End", "Death"}));
   @Property("delay")
   private final DoubleProperty delay = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(100.0D)).minimum(Double.valueOf(50.0D))).maximum(Double.valueOf(150.0D));
   @Property("extra-packet")
   private final BooleanProperty extra_packet = PropertyFactory.booleanFalse();
   @Property("ignore")
   private final BooleanProperty J = PropertyFactory.booleanFalse();
   @Property("no-move")
   private final BooleanProperty L = PropertyFactory.booleanFalse();
   @Property("rotate-camera")
   private final BooleanProperty z;
   @Property("cs-aura")
   private final BooleanProperty aura;
   @Property("aura-range")
   private final DoubleProperty aura_range;

   public ChestStealer(ModuleManager var1) {
      super(var1, "ChestStealer", "Chest Stealer", EnumModuleType.PLAYER, "Steal items from chests");
      Freecam.a();
      this.z = PropertyFactory.booleanFalse();
      this.aura = PropertyFactory.booleanFalse();
      this.aura_range = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(4.0D)).minimum(Double.valueOf(3.0D))).maximum(Double.valueOf(4.5D));
      Manager.put(new Setting("CS_NO_MOVE", "Stop Moving", SettingType.CHECKBOX, this, this.L));
      Manager.put(new Setting("CS_CAM_MOVE", "Camera Move", SettingType.CHECKBOX, this, this.z));
      Manager.put(new Setting("CS_IGNORE", "Ignore", SettingType.CHECKBOX, this, this.J));
      Manager.put(new Setting("CS_EXTRA", "Extra Packet", SettingType.CHECKBOX, this, this.extra_packet));
      Manager.put(new Setting("CS_AURA", "Chest Aura", SettingType.CHECKBOX, this, this.aura));
      SettingType var10004 = SettingType.SLIDER;
      DoubleProperty var10006 = this.aura_range;
      BooleanProperty var10008 = this.aura;
      this.aura.getClass();
      Manager.put(new Setting("CS_AURA_RANGE", "Aura Range", var10004, this, var10006, 0.1D, var10008::get));
      Manager.put(new Setting("CS_DELAY", "Delay", SettingType.SLIDER, this, this.delay, 5.0D, this::lambda$new$0));
      Manager.put(new Setting("CS_AUTO_DISABLE", "Disable On", SettingType.SELECTBOX, this, this.auto_disable));
      if(acE.b() == null) {
         Freecam.a(new int[5]);
      }

   }

   public void onDisable() {
      this.slotsFilled = false;
      this.isStealing = false;
      this.containerSlots.clear();
   }

   @EventTarget
   public void onPacket(PacketEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(var1.getPacket() instanceof S2DPacketOpenWindow) {
            S2DPacketOpenWindow var3 = (S2DPacketOpenWindow)var1.getPacket();
            String[] var4 = this.list;
            int var5 = var4.length;
            int var6 = 0;
            if(var6 < var5) {
               String var7 = var4[var6];
               if(var3.getWindowTitle().getUnformattedText().toLowerCase().contains(var7)) {
                  this.isStealing = false;
                  return;
               }

               ++var6;
            }

            this.isStealing = var3.getGuiId().equals("minecraft:chest");
            if(this.isStealing) {
               this.containerSize = var3.getSlotCount();
               this.windowID = var3.getWindowId();
               this.containerSlots.clear();
               this.slotsFilled = false;
            }
         }

         if(this.isStealing && var1.getPacket() instanceof S30PacketWindowItems) {
            S30PacketWindowItems var8 = (S30PacketWindowItems)var1.getPacket();
            if(var8.getWindowID() == this.windowID && !this.slotsFilled) {
               int var9 = 0;
               if(var9 < this.containerSize) {
                  ItemStack var11 = var8.getItemStacks()[var9];
                  if(!((Boolean)this.J.get()).booleanValue() || this.isNotBad(var11) && this.checkArmor(var11, var8) && this.checkTool(var11, var8) && this.checkSword(var11, var8) && this.checkBow(var11, var8)) {
                     this.containerSlots.add(Integer.valueOf(var9));
                  }

                  ++var9;
               }

               this.slotsFilled = true;
            }
         }
      }

   }

   private float[] a(TileEntity var1) {
      BlockPos var2 = var1.getPos();
      double var3 = (double)var2.getX() + 0.5D;
      double var5 = (double)var2.getY() + 0.5D;
      double var7 = (double)var2.getZ() + 0.5D;
      return RotationUtil.a(var3, var7, var5);
   }

   @EventTarget
   public void onMotion(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(((Boolean)this.aura.get()).booleanValue()) {
         Iterator var3 = this.mc.world.getLoadedTileEntityList().iterator();
         if(var3.hasNext()) {
            TileEntity var4 = (TileEntity)var3.next();
            if(var4 instanceof TileEntityChest) {
               String var5 = var4.toString().replace("net.minecraft.tileentity.TileEntityChest@", "");
               if(!this.chestIds.contains(var5) && (double)this.mc.player.a(var4) <= ((Double)this.aura_range.get()).doubleValue() && this.mc.currentScreen == null && this.timerAura.delay(500.0D)) {
                  if(var1.getState().equals(EventState.PRE)) {
                     float[] var6 = this.a(var4);
                     var1.setYaw(var6[0]);
                     var1.setPitch(var6[1]);
                  }

                  this.mc.at.onPlayerRightClick(this.mc.player, this.mc.world, this.mc.player.getHeldItem(), var4.getPos(), Block.getFacingDirection(var4.getPos()), MathHelper.getVec3(var4.getPos()));
                  this.chestIds.add(var5);
                  this.timerAura.reset();
               }
            }
         }
      }

   }

   @EventTarget
   public void b(MotionUpdateEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getState().equals(EventState.PRE) && this.isStealing) {
         if(!this.containerSlots.isEmpty()) {
            Collections.reverse(this.containerSlots);
            int var3 = 0;
            if(var3 < this.containerSlots.size()) {
               if(((Boolean)this.extra_packet.get()).booleanValue() || this.timer.delay(((Double)this.delay.get()).doubleValue())) {
                  short var4 = this.mc.player.openContainer.d();
                  ItemStack var5 = this.mc.player.openContainer.slotClick(((Integer)this.containerSlots.get(var3)).intValue(), 0, 1, this.mc.player);
                  this.sendPacket(new C0EPacketClickWindow(this.windowID, ((Integer)this.containerSlots.get(var3)).intValue(), 0, 1, var5, var4));
                  if(ServerUtils.isHypixel() && !((Boolean)this.L.get()).booleanValue()) {
                     this.sendPacketNoEvent(new C0EPacketClickWindow(this.windowID, ((Integer)this.containerSlots.get(var3)).intValue(), 0, 1, (ItemStack)null, var4));
                  }

                  this.containerSlots.remove(this.containerSlots.get(var3));
                  this.timer.reset();
               }

               ++var3;
            }
         }

         this.mc.player.closeScreen();
         this.isStealing = false;
      }

   }

   @EventTarget
   public void a(DisplayChestGuiEvent var1) {
      int[] var2 = Freecam.a();
      if(var1.getString().equals("minecraft:chest") && this.f()) {
         this.mc.mouseHelper.grabMouseCursor();
      }

   }

   private boolean checkArmor(ItemStack var1, S30PacketWindowItems var2) {
      int[] var3 = Freecam.a();
      return var1.getItem().getUnlocalizedName().contains("helmet")?var1.equals(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.HELMET)) && (this.bestArmor(ChestStealer$Armor.HELMET) == null || this.getProtection(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.HELMET)) > this.getProtection(this.bestArmor(ChestStealer$Armor.HELMET))):(var1.getItem().getUnlocalizedName().contains("chestplate")?var1.equals(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.CHESTPLATE)) && (this.bestArmor(ChestStealer$Armor.CHESTPLATE) == null || this.getProtection(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.CHESTPLATE)) > this.getProtection(this.bestArmor(ChestStealer$Armor.CHESTPLATE))):(var1.getItem().getUnlocalizedName().contains("boots")?var1.equals(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.BOOTS)) && (this.bestArmor(ChestStealer$Armor.BOOTS) == null || this.getProtection(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.BOOTS)) > this.getProtection(this.bestArmor(ChestStealer$Armor.BOOTS))):(!var1.getItem().getUnlocalizedName().contains("leggings")?true:var1.equals(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.LEGGINS)) && (this.bestArmor(ChestStealer$Armor.LEGGINS) == null || this.getProtection(this.bestArmor(var2.getItemStacks(), ChestStealer$Armor.LEGGINS)) > this.getProtection(this.bestArmor(ChestStealer$Armor.LEGGINS))))));
   }

   private boolean checkTool(ItemStack var1, S30PacketWindowItems var2) {
      int[] var3 = Freecam.a();
      return var1.getItem() instanceof ItemPickaxe?var1.equals(this.bestTool(var2.getItemStacks(), ChestStealer$Tool.PICKAXE)) && (this.bestTool(this.getInventory(), ChestStealer$Tool.PICKAXE) == null || this.getEfficiency(this.bestTool(var2.getItemStacks(), ChestStealer$Tool.PICKAXE)) > this.getEfficiency(this.bestTool(this.getInventory(), ChestStealer$Tool.PICKAXE))):(var1.getItem() instanceof ItemAxe?var1.equals(this.bestTool(var2.getItemStacks(), ChestStealer$Tool.AXE)) && (this.bestTool(this.getInventory(), ChestStealer$Tool.AXE) == null || this.getEfficiency(this.bestTool(var2.getItemStacks(), ChestStealer$Tool.AXE)) > this.getEfficiency(this.bestTool(this.getInventory(), ChestStealer$Tool.AXE))):(!(var1.getItem() instanceof ItemSpade)?true:var1.equals(this.bestTool(var2.getItemStacks(), ChestStealer$Tool.SHOVEL)) && (this.bestTool(this.getInventory(), ChestStealer$Tool.SHOVEL) == null || this.getEfficiency(this.bestTool(var2.getItemStacks(), ChestStealer$Tool.SHOVEL)) > this.getEfficiency(this.bestTool(this.getInventory(), ChestStealer$Tool.SHOVEL)))));
   }

   private boolean checkSword(ItemStack var1, S30PacketWindowItems var2) {
      int[] var3 = Freecam.a();
      return !(var1.getItem() instanceof ItemSword)?true:var1.equals(this.bestSword(var2.getItemStacks())) && (this.bestSword(this.getInventory()) == null || this.getDamage(this.bestSword(var2.getItemStacks())) > this.getDamage(this.bestSword(this.getInventory())));
   }

   private boolean checkBow(ItemStack var1, S30PacketWindowItems var2) {
      int[] var3 = Freecam.a();
      return !(var1.getItem() instanceof ItemBow)?true:var1.equals(this.bestBow(var2.getItemStacks())) && (this.bestBow(this.getInventory()) == null || this.getPower(this.bestBow(var2.getItemStacks())) > this.getPower(this.bestBow(this.getInventory())));
   }

   private boolean isNotBad(ItemStack var1) {
      Freecam.a();
      ItemStack var3 = null;
      float var4 = -1.0F;
      int var5 = 9;
      if(var5 < 45) {
         if(this.mc.player.inventoryContainer.getSlot(var5).getHasStack()) {
            ItemStack var6 = this.mc.player.inventoryContainer.getSlot(var5).getStack();
            if(var6.getItem() instanceof ItemSword && var1.getItem() instanceof ItemSword && var4 < this.getDamage(var6)) {
               this.getDamage(var6);
               var3 = var6;
            }
         }

         ++var5;
      }

      if(var3 != null && var3.getItem() instanceof ItemSword && var1.getItem() instanceof ItemSword) {
         float var8 = this.getDamage(var3);
         float var9 = this.getDamage(var1);
         if(var9 > var8) {
            return true;
         }
      }

      return var1 == null || !var1.getItem().getUnlocalizedName().contains("stick") && (!var1.getItem().getUnlocalizedName().contains("egg") || var1.getItem().getUnlocalizedName().contains("leg")) && !var1.getItem().getUnlocalizedName().contains("string") && !var1.getItem().getUnlocalizedName().contains("compass") && !var1.getItem().getUnlocalizedName().contains("feather") && !var1.getItem().getUnlocalizedName().contains("bucket") && !var1.getItem().getUnlocalizedName().contains("snow") && !var1.getItem().getUnlocalizedName().contains("fish") && !var1.getItem().getUnlocalizedName().contains("enchant") && !var1.getItem().getUnlocalizedName().contains("exp") && !var1.getItem().getUnlocalizedName().contains("shears") && !var1.getItem().getUnlocalizedName().contains("anvil") && !var1.getItem().getUnlocalizedName().contains("torch") && !var1.getItem().getUnlocalizedName().contains("seeds") && !var1.getItem().getUnlocalizedName().contains("leather") && !(var1.getItem() instanceof ItemGlassBottle) && !var1.getItem().getUnlocalizedName().contains("piston") && (!var1.getItem().getUnlocalizedName().contains("potion") || !this.isBadPotion(var1));
   }

   private boolean isBadPotion(ItemStack var1) {
      int[] var2 = Freecam.a();
      if(var1 != null && var1.getItem() instanceof ItemPotion) {
         ItemPotion var3 = (ItemPotion)var1.getItem();
         if(ItemPotion.isSplash(var1.getItemDamage())) {
            return ((InvManager)this.getModule(InvManager.class)).isBadPotionEffect(var1, var3);
         }
      }

      return false;
   }

   private ItemStack[] getInventory() {
      return this.mc.player.inventory.mainInventory;
   }

   private ItemStack[] getArmorInventory() {
      return this.mc.player.inventory.armorInventory;
   }

   public ItemStack bestArmor(ChestStealer$Armor var1) {
      int[] var2 = Freecam.a();
      return this.hasArmor(this.getInventory(), var1) && this.hasArmor(this.getArmorInventory(), var1)?(ItemStack)((List)Stream.of(new ItemStack[]{(ItemStack)((List)Arrays.stream(this.getInventory()).filter(this::lambda$bestArmor$5).sorted(this::lambda$bestArmor$6).collect(Collectors.toList())).stream().findFirst().get(), (ItemStack)((List)Arrays.stream(this.getArmorInventory()).filter(this::lambda$bestArmor$7).sorted(this::lambda$bestArmor$8).collect(Collectors.toList())).stream().findFirst().get()}).filter(this::lambda$bestArmor$9).sorted(ChestStealer::lambda$bestArmor$10).sorted(this::lambda$bestArmor$11).collect(Collectors.toList())).stream().findFirst().get():(this.hasArmor(this.getInventory(), var1)?(ItemStack)((List)Arrays.stream(this.getInventory()).filter(this::lambda$bestArmor$12).sorted(ChestStealer::lambda$bestArmor$13).sorted(this::lambda$bestArmor$14).collect(Collectors.toList())).stream().findFirst().get():(this.hasArmor(this.getArmorInventory(), var1)?(ItemStack)((List)Arrays.stream(this.getArmorInventory()).filter(this::lambda$bestArmor$15).sorted(ChestStealer::lambda$bestArmor$16).sorted(this::lambda$bestArmor$17).collect(Collectors.toList())).stream().findFirst().get():null));
   }

   public ItemStack bestArmor(ItemStack[] var1, ChestStealer$Armor var2) {
      int[] var3 = Freecam.a();
      return this.hasArmor(var1, var2)?(ItemStack)((List)Arrays.stream(var1).filter(this::lambda$bestArmor$18).sorted(ChestStealer::lambda$bestArmor$19).sorted(this::lambda$bestArmor$20).sorted(ChestStealer::lambda$bestArmor$21).collect(Collectors.toList())).stream().findFirst().get():null;
   }

   public ItemStack bestTool(ItemStack[] var1, ChestStealer$Tool var2) {
      int[] var3 = Freecam.a();
      if(this.hasTool(var1, var2)) {
         if(var2.equals(ChestStealer$Tool.PICKAXE)) {
            return (ItemStack)Stream.of(var1).filter(ChestStealer::lambda$bestTool$18).sorted(ChestStealer::lambda$bestTool$23).min(this::lambda$bestTool$24).get();
         }

         if(var2.equals(ChestStealer$Tool.SHOVEL)) {
            return (ItemStack)Stream.of(var1).filter(ChestStealer::lambda$bestTool$21).sorted(ChestStealer::lambda$bestTool$26).min(this::lambda$bestTool$27).get();
         }

         if(var2.equals(ChestStealer$Tool.AXE)) {
            return (ItemStack)Stream.of(var1).filter(ChestStealer::lambda$bestTool$24).sorted(ChestStealer::lambda$bestTool$29).min(this::lambda$bestTool$30).get();
         }
      }

      return null;
   }

   public ItemStack bestSword(ItemStack[] var1) {
      int[] var2 = Freecam.a();
      return this.hasSword(var1)?(ItemStack)((List)Stream.of(var1).filter(ChestStealer::lambda$bestSword$31).sorted(ChestStealer::lambda$bestSword$32).sorted(this::lambda$bestSword$33).collect(Collectors.toList())).stream().findFirst().get():null;
   }

   public ItemStack bestBow(ItemStack[] var1) {
      int[] var2 = Freecam.a();
      return this.hasBow(var1)?(ItemStack)((List)Stream.of(var1).filter(ChestStealer::lambda$bestBow$34).sorted(this::lambda$bestBow$35).collect(Collectors.toList())).stream().findFirst().get():null;
   }

   private int containerContainsArmor(ItemStack[] var1, ChestStealer$Armor var2) {
      Freecam.a();
      int var4 = 0;
      if(var4 < var1.length) {
         if(var1[var4] != null && var1[var4].getItem().getUnlocalizedName().contains(this.armorType(var2))) {
            return var4;
         }

         ++var4;
      }

      return -1;
   }

   private int containerContainsTool(ItemStack[] var1, ChestStealer$Tool var2) {
      Freecam.a();
      int var4 = 0;
      if(var4 < var1.length) {
         if(var1[var4] != null && var1[var4].getItem().getUnlocalizedName().contains(this.toolType(var2))) {
            return var4;
         }

         ++var4;
      }

      return -1;
   }

   private int containerContainsSword(ItemStack[] var1) {
      Freecam.a();
      int var3 = 0;
      if(var3 < var1.length) {
         if(var1[var3] != null && var1[var3].getItem() instanceof ItemSword) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   private int containerContainsBow(ItemStack[] var1) {
      Freecam.a();
      int var3 = 0;
      if(var3 < var1.length) {
         if(var1[var3] != null && var1[var3].getItem() instanceof ItemBow) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   public boolean hasArmor(ItemStack[] var1, ChestStealer$Armor var2) {
      Freecam.a();
      int var4 = this.containerContainsArmor(var1, var2);
      return var4 >= 0;
   }

   public boolean hasTool(ItemStack[] var1, ChestStealer$Tool var2) {
      Freecam.a();
      int var4 = this.containerContainsTool(var1, var2);
      return var4 >= 0;
   }

   public boolean hasSword(ItemStack[] var1) {
      Freecam.a();
      int var3 = this.containerContainsSword(var1);
      return var3 >= 0;
   }

   public boolean hasBow(ItemStack[] var1) {
      Freecam.a();
      int var3 = this.containerContainsBow(var1);
      return var3 >= 0;
   }

   public String armorType(ChestStealer$Armor var1) {
      int[] var2 = Freecam.a();
      return var1.equals(ChestStealer$Armor.LEGGINS)?"leggings":(var1.equals(ChestStealer$Armor.CHESTPLATE)?"chestplate":(var1.equals(ChestStealer$Armor.BOOTS)?"boots":(var1.equals(ChestStealer$Armor.HELMET)?"helmet":"")));
   }

   public String toolType(ChestStealer$Tool var1) {
      int[] var2 = Freecam.a();
      return var1.equals(ChestStealer$Tool.AXE)?"hatchet":(var1.equals(ChestStealer$Tool.PICKAXE)?"pickaxe":(var1.equals(ChestStealer$Tool.SHOVEL)?"shovel":""));
   }

   private float getPower(ItemStack var1) {
      return (float)(1 + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, var1) + EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, var1) + EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, var1));
   }

   private float getDamage(ItemStack var1) {
      float var3 = 0.0F;
      Freecam.a();
      Item var4 = var1.getItem();
      if(var4 instanceof ItemTool) {
         var3 += ((ItemTool)var4).getDamage();
      }

      if(var4 instanceof ItemSword) {
         var3 += ((ItemSword)var4).getAttackDamage();
      }

      var3 = var3 + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, var1) * 1.25F + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, var1) * 0.3F;
      return var3;
   }

   private float getProtection(ItemStack var1) {
      Freecam.a();
      float var3 = 0.0F;
      if(var1.getItem() instanceof ItemArmor) {
         ItemArmor var4 = (ItemArmor)var1.getItem();
         var3 = (float)((double)var3 + (double)var4.damageReduceAmount + (double)((100 - var4.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, var1)) * 0.0075D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, var1) / 100.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var1) / 50.0D);
         var3 = (float)((double)var3 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, var1) / 100.0D);
      }

      return var3;
   }

   private float getEfficiency(ItemStack var1) {
      Freecam.a();
      Item var3 = var1.getItem();
      if(!(var3 instanceof ItemTool)) {
         return 0.0F;
      } else {
         float var6;
         label20: {
            String var4 = var3.getUnlocalizedName();
            ItemTool var5 = (ItemTool)var3;
            if(var3 instanceof ItemPickaxe) {
               var6 = var5.getStrVsBlock(var1, Blocks.stone);
               if(!var4.toLowerCase().contains("gold")) {
                  break label20;
               }

               var6 = var6 - 6.0F;
            }

            if(var3 instanceof ItemSpade) {
               var6 = var5.getStrVsBlock(var1, Blocks.dirt);
               if(!var4.toLowerCase().contains("gold")) {
                  break label20;
               }

               var6 = var6 - 6.0F;
            }

            if(!(var3 instanceof ItemAxe)) {
               return 1.0F;
            }

            var6 = var5.getStrVsBlock(var1, Blocks.log);
            if(var4.toLowerCase().contains("gold")) {
               var6 = var6 - 6.0F;
               return 1.0F;
            }
         }

         var6 = (float)((double)var6 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, var1) * 0.0075D);
         var6 = (float)((double)var6 + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, var1) / 100.0D);
         return var6;
      }
   }

   public ListProperty getAutoDisable() {
      return this.auto_disable;
   }

   public List getChestIds() {
      return this.chestIds;
   }

   public boolean isStealing() {
      return this.isStealing;
   }

   public boolean f() {
      int[] var1 = Freecam.a();
      return this.mc.currentScreen instanceof GuiChest && this.isStealing && this.isEnabled() && ((Boolean)this.z.get()).booleanValue();
   }

   public BooleanProperty a() {
      return this.L;
   }

   private int lambda$bestBow$35(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getPower(var2), this.getPower(var1));
   }

   private static boolean lambda$bestBow$34(ItemStack var0) {
      int[] var1 = Freecam.a();
      return var0 != null && var0.getItem() instanceof ItemBow;
   }

   private int lambda$bestSword$33(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getDamage(var2), this.getDamage(var1));
   }

   private static int lambda$bestSword$32(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private static boolean lambda$bestSword$31(ItemStack var0) {
      int[] var1 = Freecam.a();
      return var0 != null && var0.getItem() instanceof ItemSword;
   }

   private int lambda$bestTool$30(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getEfficiency(var2), this.getEfficiency(var1));
   }

   private static int lambda$bestTool$29(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private static boolean lambda$bestTool$24(ItemStack var0) {
      int[] var1 = Freecam.a();
      return var0 != null && var0.getItem() instanceof ItemAxe;
   }

   private int lambda$bestTool$27(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getEfficiency(var2), this.getEfficiency(var1));
   }

   private static int lambda$bestTool$26(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private static boolean lambda$bestTool$21(ItemStack var0) {
      int[] var1 = Freecam.a();
      return var0 != null && var0.getItem() instanceof ItemSpade;
   }

   private int lambda$bestTool$24(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getEfficiency(var2), this.getEfficiency(var1));
   }

   private static int lambda$bestTool$23(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private static boolean lambda$bestTool$18(ItemStack var0) {
      int[] var1 = Freecam.a();
      return var0 != null && var0.getItem() instanceof ItemPickaxe;
   }

   private static int lambda$bestArmor$21(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getItemDamage(), var0.getItemDamage());
   }

   private int lambda$bestArmor$20(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getProtection(var2), this.getProtection(var1));
   }

   private static int lambda$bestArmor$19(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private boolean lambda$bestArmor$18(ChestStealer$Armor var1, ItemStack var2) {
      int[] var3 = Freecam.a();
      return var2 != null && var2.getItem().getUnlocalizedName().contains(this.armorType(var1));
   }

   private int lambda$bestArmor$17(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getProtection(var2), this.getProtection(var1));
   }

   private static int lambda$bestArmor$16(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private boolean lambda$bestArmor$15(ChestStealer$Armor var1, ItemStack var2) {
      int[] var3 = Freecam.a();
      return var2 != null && var2.getItem().getUnlocalizedName().contains(this.armorType(var1));
   }

   private int lambda$bestArmor$14(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getProtection(var2), this.getProtection(var1));
   }

   private static int lambda$bestArmor$13(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private boolean lambda$bestArmor$12(ChestStealer$Armor var1, ItemStack var2) {
      int[] var3 = Freecam.a();
      return var2 != null && var2.getItem().getUnlocalizedName().contains(this.armorType(var1));
   }

   private int lambda$bestArmor$11(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getProtection(var2), this.getProtection(var1));
   }

   private static int lambda$bestArmor$10(ItemStack var0, ItemStack var1) {
      return Integer.compare(var1.getMaxDamage() - var1.getItemDamage(), var0.getMaxDamage() - var0.getItemDamage());
   }

   private boolean lambda$bestArmor$9(ChestStealer$Armor var1, ItemStack var2) {
      int[] var3 = Freecam.a();
      return var2 != null && var2.getItem().getUnlocalizedName().contains(this.armorType(var1));
   }

   private int lambda$bestArmor$8(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getProtection(var2), this.getProtection(var1));
   }

   private boolean lambda$bestArmor$7(ChestStealer$Armor var1, ItemStack var2) {
      int[] var3 = Freecam.a();
      return var2 != null && var2.getItem().getUnlocalizedName().contains(this.armorType(var1));
   }

   private int lambda$bestArmor$6(ItemStack var1, ItemStack var2) {
      return Float.compare(this.getProtection(var2), this.getProtection(var1));
   }

   private boolean lambda$bestArmor$5(ChestStealer$Armor var1, ItemStack var2) {
      int[] var3 = Freecam.a();
      return var2 != null && var2.getItem().getUnlocalizedName().contains(this.armorType(var1));
   }

   private Boolean lambda$new$0() {
      int[] var1 = Freecam.a();
      return Boolean.valueOf(!((Boolean)this.extra_packet.get()).booleanValue());
   }
}
