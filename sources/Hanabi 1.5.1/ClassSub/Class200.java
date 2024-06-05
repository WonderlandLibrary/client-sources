package ClassSub;

import net.minecraft.client.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.client.entity.*;
import net.minecraft.block.*;
import org.lwjgl.util.vector.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Class200
{
    private static Minecraft mc;
    
    
    public void portMove(final float n, final float n2, final float n3) {
        Class200.mc.thePlayer.setPosition(-Math.sin(Math.toRadians(n)) * n2 + Class200.mc.thePlayer.posX, n3 + Class200.mc.thePlayer.posY, Math.cos(Math.toRadians(n)) * n2 + Class200.mc.thePlayer.posZ);
    }
    
    public static double getBaseMoveSpeed() {
        if (Class334.password.length() < 32) {
            System.exit(0);
        }
        double n = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            n *= 1.0 + 0.2 * (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return n;
    }
    
    public static float getDirection() {
        float rotationYaw = Class200.mc.thePlayer.rotationYaw;
        if (Class200.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (Class200.mc.thePlayer.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (Class200.mc.thePlayer.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Class200.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Class200.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    public static boolean isInWater() {
        return Class200.mc.theWorld.getBlockState(new BlockPos(Class200.mc.thePlayer.posX, Class200.mc.thePlayer.posY, Class200.mc.thePlayer.posZ)).getBlock().getMaterial() == Material.water;
    }
    
    public static void toFwd(final double n) {
        final float n2 = Class200.mc.thePlayer.rotationYaw * 0.017453292f;
        final EntityPlayerSP thePlayer = Class200.mc.thePlayer;
        thePlayer.motionX -= MathHelper.sin(n2) * n;
        final EntityPlayerSP thePlayer2 = Class200.mc.thePlayer;
        thePlayer2.motionZ += MathHelper.cos(n2) * n;
    }
    
    public static void setSpeed(final double n) {
        Class200.mc.thePlayer.motionX = -(Math.sin(getDirection()) * n);
        Class200.mc.thePlayer.motionZ = Math.cos(getDirection()) * n;
    }
    
    public static double getSpeed() {
        if (Class334.password.length() < 32) {
            System.exit(0);
        }
        return Math.sqrt(Minecraft.getMinecraft().thePlayer.motionX * Minecraft.getMinecraft().thePlayer.motionX + Minecraft.getMinecraft().thePlayer.motionZ * Minecraft.getMinecraft().thePlayer.motionZ);
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer entityPlayer) {
        return getBlock(new BlockPos(entityPlayer.posX, entityPlayer.posY - 1.0, entityPlayer.posZ));
    }
    
    public static Block getBlock(final BlockPos blockPos) {
        return Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
    }
    
    public static Block getBlockAtPosC(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        return getBlock(new BlockPos(entityPlayer.posX - n, entityPlayer.posY - n2, entityPlayer.posZ - n3));
    }
    
    public static ArrayList<Vector3f> vanillaTeleportPositions(final double n, final double n2, final double n3, final double n4) {
        final ArrayList<Vector3f> list = new ArrayList<Vector3f>();
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        final double n5 = n - getMinecraft.thePlayer.posX;
        final double n6 = n2 - (getMinecraft.thePlayer.posY + getMinecraft.thePlayer.getEyeHeight() + 1.1);
        final double n7 = n3 - getMinecraft.thePlayer.posZ;
        final float n8 = (float)(Math.atan2(n7, n5) * 180.0 / 3.141592653589793 - 90.0);
        final float n9 = (float)(-Math.atan2(n6, Math.sqrt(n5 * n5 + n7 * n7)) * 180.0 / 3.141592653589793);
        final double posX = getMinecraft.thePlayer.posX;
        double posY = getMinecraft.thePlayer.posY;
        final double posZ = getMinecraft.thePlayer.posZ;
        double n10 = 1.0;
        for (double n11 = n4; n11 < getDistance(getMinecraft.thePlayer.posX, getMinecraft.thePlayer.posY, getMinecraft.thePlayer.posZ, n, n2, n3); n11 += n4) {
            ++n10;
        }
        for (double n12 = n4; n12 < getDistance(getMinecraft.thePlayer.posX, getMinecraft.thePlayer.posY, getMinecraft.thePlayer.posZ, n, n2, n3); n12 += n4) {
            final double n13 = getMinecraft.thePlayer.posX - Math.sin(getDirection(n8)) * n12;
            final double n14 = getMinecraft.thePlayer.posZ + Math.cos(getDirection(n8)) * n12;
            posY -= (getMinecraft.thePlayer.posY - n2) / n10;
            list.add(new Vector3f((float)n13, (float)posY, (float)n14));
        }
        list.add(new Vector3f((float)n, (float)n2, (float)n3));
        return list;
    }
    
    public static float getDirection(float n) {
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            n += 180.0f;
        }
        float n2 = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.moveForward < 0.0f) {
            n2 = -0.5f;
        }
        else if (Minecraft.getMinecraft().thePlayer.moveForward > 0.0f) {
            n2 = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f) {
            n -= 90.0f * n2;
        }
        if (Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f) {
            n += 90.0f * n2;
        }
        n *= 0.017453292f;
        return n;
    }
    
    public static double getDistance(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double n7 = n - n4;
        final double n8 = n2 - n5;
        final double n9 = n3 - n6;
        return MathHelper.sqrt_double(n7 * n7 + n8 * n8 + n9 * n9);
    }
    
    public static boolean MovementInput() {
        return ((IKeyBinding)Class200.mc.gameSettings.keyBindForward).getPress() || ((IKeyBinding)Class200.mc.gameSettings.keyBindLeft).getPress() || ((IKeyBinding)Class200.mc.gameSettings.keyBindRight).getPress() || ((IKeyBinding)Class200.mc.gameSettings.keyBindBack).getPress();
    }
    
    public static void blockHit(final Entity entity, final boolean b) {
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        final ItemStack getCurrentEquippedItem = getMinecraft.thePlayer.getCurrentEquippedItem();
        if (getMinecraft.thePlayer.getCurrentEquippedItem() != null && entity != null && b && getCurrentEquippedItem.getItem() instanceof ItemSword && getMinecraft.thePlayer.swingProgress > 0.2) {
            getMinecraft.thePlayer.getCurrentEquippedItem().useItemRightClick((World)getMinecraft.theWorld, (EntityPlayer)getMinecraft.thePlayer);
        }
    }
    
    public static float getItemAtkDamage(final ItemStack itemStack) {
        final Multimap getAttributeModifiers = itemStack.getAttributeModifiers();
        if (!getAttributeModifiers.isEmpty()) {
            final Iterator iterator = getAttributeModifiers.entries().iterator();
            if (iterator.hasNext()) {
                final AttributeModifier attributeModifier = iterator.next().getValue();
                final double n = (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) ? attributeModifier.getAmount() : (attributeModifier.getAmount() * 100.0);
                if (attributeModifier.getAmount() > 1.0) {
                    return 1.0f + (float)n;
                }
                return 1.0f;
            }
        }
        return 1.0f;
    }
    
    public static int bestWeapon(final Entity entity) {
        final Minecraft getMinecraft = Minecraft.getMinecraft();
        final InventoryPlayer inventory = getMinecraft.thePlayer.inventory;
        final boolean currentItem = false;
        inventory.currentItem = (currentItem ? 1 : 0);
        final boolean b = currentItem;
        int n = -1;
        int n2 = 1;
        for (int i = 0; i < 9; i = (byte)(i + 1)) {
            getMinecraft.thePlayer.inventory.currentItem = i;
            final ItemStack getHeldItem = getMinecraft.thePlayer.getHeldItem();
            if (getHeldItem != null) {
                final int n3 = (int)((int)getItemAtkDamage(getHeldItem) + EnchantmentHelper.getModifierForCreature(getHeldItem, EnumCreatureAttribute.UNDEFINED));
                if (n3 > n2) {
                    n2 = n3;
                    n = i;
                }
            }
        }
        if (n != -1) {
            return n;
        }
        return b ? 1 : 0;
    }
    
    public static void shiftClick(final Item item) {
        for (int i = 9; i < 37; ++i) {
            final ItemStack getStack = Class200.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (getStack != null && getStack.getItem() == item) {
                Class200.mc.playerController.windowClick(0, i, 0, 1, (EntityPlayer)Class200.mc.thePlayer);
                break;
            }
        }
    }
    
    public static boolean hotbarIsFull() {
        for (int i = 0; i <= 36; ++i) {
            if (Class200.mc.thePlayer.inventory.getStackInSlot(i) == null) {
                return false;
            }
        }
        return true;
    }
    
    public static void tellPlayer(final String s) {
        if (s != null && Class200.mc.thePlayer != null) {
            Class200.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(s));
        }
    }
    
    public static boolean isMoving() {
        return !Class200.mc.thePlayer.isCollidedHorizontally && !Class200.mc.thePlayer.isSneaking() && (Class200.mc.thePlayer.movementInput.moveForward != 0.0f || Class200.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static boolean isMoving2() {
        return Class200.mc.thePlayer.moveForward != 0.0f || Class200.mc.thePlayer.moveStrafing != 0.0f;
    }
    
    public static void blinkToPos(final double[] array, final BlockPos blockPos, final double n, final double[] array2) {
        double n2 = array[0];
        double n3 = array[1];
        double n4 = array[2];
        final double n5 = blockPos.getX() + 0.5;
        final double n6 = blockPos.getY() + 1.0;
        final double n7 = blockPos.getZ() + 0.5;
        double n8 = Math.abs(n2 - n5) + Math.abs(n3 - n6) + Math.abs(n4 - n7);
        int n9 = 0;
        while (n8 > n) {
            n8 = Math.abs(n2 - n5) + Math.abs(n3 - n6) + Math.abs(n4 - n7);
            if (n9 > 120) {
                break;
            }
            final double n10 = n2 - n5;
            final double n11 = n3 - n6;
            final double n12 = n4 - n7;
            final double n13 = ((n9 & 0x1) == 0x0) ? array2[0] : array2[1];
            if (n10 < 0.0) {
                if (Math.abs(n10) > n13) {
                    n2 += n13;
                }
                else {
                    n2 += Math.abs(n10);
                }
            }
            if (n10 > 0.0) {
                if (Math.abs(n10) > n13) {
                    n2 -= n13;
                }
                else {
                    n2 -= Math.abs(n10);
                }
            }
            if (n11 < 0.0) {
                if (Math.abs(n11) > 0.25) {
                    n3 += 0.25;
                }
                else {
                    n3 += Math.abs(n11);
                }
            }
            if (n11 > 0.0) {
                if (Math.abs(n11) > 0.25) {
                    n3 -= 0.25;
                }
                else {
                    n3 -= Math.abs(n11);
                }
            }
            if (n12 < 0.0) {
                if (Math.abs(n12) > n13) {
                    n4 += n13;
                }
                else {
                    n4 += Math.abs(n12);
                }
            }
            if (n12 > 0.0) {
                if (Math.abs(n12) > n13) {
                    n4 -= n13;
                }
                else {
                    n4 -= Math.abs(n12);
                }
            }
            Minecraft.getMinecraft().getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(n2, n3, n4, true));
            ++n9;
        }
    }
    
    static {
        Class200.mc = Minecraft.getMinecraft();
    }
}
