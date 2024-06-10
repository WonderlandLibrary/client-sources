 package me.vene.skilled.modules.mods.combat;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.item.ItemAxe;
 import net.minecraft.item.ItemSword;
 import net.minecraft.potion.Potion;

 public class Velocity extends Module
 {
	 
  private String hor;
  private String ver;
  private String chanceString;
  private NumberValue HorizontalModifier;
  private NumberValue VerticalModifier;
  private NumberValue chanceValue;
  private BooleanValue onlyTargetting;
  private static BooleanValue onlyClick;
  private static BooleanValue Weapon;
  private static BooleanValue Sprinting;
  private static BooleanValue OnlyGround;
  private static Minecraft mc;
  boolean lpx;
 
 
 public Velocity() {
     super(StringRegistry.register(new String(new char[] { 'V', 'e', 'l', 'o', 'c', 'i', 't', 'y' })), 0, Category.C);
     this.hor = StringRegistry.register(new String(new char[] { 'H', 'o', 'r', 'i', 'z', 'o', 'n', 't', 'a', 'l' }));
     this.ver = StringRegistry.register(new String(new char[] { 'V', 'e', 'r', 't', 'i', 'c', 'a', 'l' }));
     this.chanceString = StringRegistry.register(new String(new char[] { 'C', 'h', 'a', 'n', 'c', 'e' }));
     this.HorizontalModifier = new NumberValue(StringRegistry.register(this.hor), 92.0, 0.0, 100.0);
     this.VerticalModifier = new NumberValue(StringRegistry.register(this.ver), 100.0, 0.0, 100.0);
     this.chanceValue = new NumberValue(StringRegistry.register(this.chanceString), 100.0, 0.0, 100.0);
     this.onlyTargetting = new BooleanValue(StringRegistry.register("Only Targetting"), true);
     Velocity.Sprinting = new BooleanValue(StringRegistry.register("Sprinting"), false);
     Velocity.onlyClick = new BooleanValue(StringRegistry.register("Only Click"), false);
     Velocity.OnlyGround = new BooleanValue(StringRegistry.register("Only Ground"), false);
     Velocity.Weapon = new BooleanValue(StringRegistry.register("Weapon"), false);
     this.mc = Minecraft.getMinecraft();
     Velocity.mc = Minecraft.getMinecraft();
     this.lpx = false;
     this.addValue(this.HorizontalModifier);
     this.addValue(this.VerticalModifier);
     this.addValue(this.chanceValue);
     this.addOption(this.onlyTargetting);
     this.addOption(Velocity.Sprinting);
     this.addOption(Velocity.onlyClick);
     this.addOption(Velocity.OnlyGround);
     this.addOption(Velocity.Weapon);
   }

   public void onLivingUpdate(EntityPlayerSP player) {
     if ((Minecraft.getMinecraft()).thePlayer == null) {
       return;
     }
     if ((Minecraft.getMinecraft()).theWorld == null) {
       return;
     }
  if (!Velocity.mc.thePlayer.isInWater() == false) { 
	    return;	 
        }
   if (!Velocity.mc.thePlayer.isInLava() == false) {
	    return;	 
    }  
   if (!Velocity.mc.thePlayer.isOnLadder() == false) {
    return;	 
   }

  if (!Velocity.mc.thePlayer.isSneaking() == false) {
    return;	 
 }

  if (!(Velocity.mc.thePlayer.getFoodStats().getFoodLevel() > 6)) {
       return;	 
  }

   if (!Velocity.mc.thePlayer.isPotionActive(Potion.poison) == false) {
        return;	 
  } 

   if (!Velocity.mc.thePlayer.isPotionActive(Potion.moveSlowdown) == false) {
         return;	 
  }
   if (!Velocity.mc.thePlayer.isPotionActive(Potion.digSlowdown) == false) {
  return;	 
  }

     if (!Velocity.mc.thePlayer.isPotionActive(Potion.wither) == false) {
   return;	 
    }

     if (!Velocity.mc.thePlayer.isPotionActive(Potion.jump) == false) {
    return;	 
   }
     if (!Velocity.mc.thePlayer.isPotionActive(Potion.blindness) == false) {
     return;	 
   }
     
 	 if (!Velocity.mc.gameSettings.keyBindSneak.isKeyDown() == false) {
           return;
     }  
    
 if (Velocity.onlyClick.getState() && !Velocity.mc.gameSettings.keyBindAttack.isKeyDown()) {
   this.lpx = false;
   return;
       }
 
  if (Velocity.OnlyGround.getState()) {
      if (!Velocity.mc.thePlayer.onGround) { 
     return;   
     }
  }
if (Velocity.Sprinting.getState()) {
     if (!Velocity.mc.thePlayer.isSprinting()) {
     return;   
       }
   }
if (Velocity.Weapon.getState()) {
  if (Velocity.mc.thePlayer.getCurrentEquippedItem() == null) {
      return;
      }
  if (!(Velocity.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) && !(Velocity.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemAxe)) {
      return;
      }
  }

if (this.onlyTargetting.getState() && (this.mc.objectMouseOver == null || this.mc.objectMouseOver.entityHit == null)) {
    return;
  }

 if (MathUtil.random.nextInt(100) >= (int)this.chanceValue.getValue()) {
       return;
     }
     double vertmodifier = this.VerticalModifier.getValue() / 100.0D;
     double horzmodifier = this.HorizontalModifier.getValue() / 100.0D;
     if ((Minecraft.getMinecraft()).thePlayer.hurtTime == (Minecraft.getMinecraft()).thePlayer.maxHurtTime && (Minecraft.getMinecraft()).thePlayer.maxHurtTime > 0) {
       this.mc.thePlayer.motionX *= horzmodifier;
       this.mc.thePlayer.motionZ *= horzmodifier;
       this.mc.thePlayer.motionY *= vertmodifier;
          } 
        }
      } 