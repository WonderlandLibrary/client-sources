 package me.vene.skilled.modules.mods.utility;

 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import net.minecraft.client.Minecraft;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.init.Blocks;
 import net.minecraft.realms.RealmsMth;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.ChatComponentText;
 import net.minecraft.util.IChatComponent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 

public class AutoDisconnect extends Module
{
    private BlockPos localBed;
    private Minecraft mc;
    
    public AutoDisconnect() {
        super("Auto Disconnect", 0, Category.U);
        this.localBed = null;
        this.mc = Minecraft.getMinecraft();
    }
    
    public static boolean bot(final Entity en) {
        if (en.getName().startsWith("\u00c2§c")) {
            return true;
        }
        final String n = en.getDisplayName().getUnformattedText();
        if (n.contains("\u00c2§")) {
            return n.contains("[NPC] ");
        }
        if (n.isEmpty() && en.getName().isEmpty()) {
            return true;
        }
        if (n.length() == 10) {
            int num = 0;
            int let = 0;
            final char[] charArray;
            final char[] var4 = charArray = n.toCharArray();
            for (final char c : charArray) {
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        return false;
                    }
                    ++let;
                }
                else {
                    if (!Character.isDigit(c)) {
                        return false;
                    }
                    ++num;
                }
            }
            return num >= 2 && let >= 2;
        }
        return false;
    }
    
    public boolean isOnSameTeam(final EntityLivingBase entity) {
        if (entity.getTeam() != null && this.mc.thePlayer.getTeam() != null) {
            final char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            final char c2 = this.mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }
    
    double distance(final double x, final double y) {
        return RealmsMth.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
    }
    
    double distance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return this.distance(y1 - y2, this.distance(x1 - x2, z1 - z2));
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Math.abs(this.mc.thePlayer.prevPosX - this.mc.thePlayer.posX) > 10.0) {
            this.updateBed();
            this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Teleport detected"));
        }
        if (this.localBed == null) {
            return;
        }
        this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("ALERT ASDFASDF"));
        for (final Object entity : this.mc.theWorld.loadedEntityList) {
            if (!(entity instanceof EntityPlayer)) {
                continue;
            }
            if (bot((Entity)entity)) {
                this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("BOT ALERT ASDFASDF"));
            }
            else if (this.isOnSameTeam((EntityLivingBase)entity)) {
                this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("same team ALERT ASDFASDF"));
            }
            else {
                this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("SAME ASDFASDF"));
                if (this.distance(((EntityPlayer)entity).posX, ((EntityPlayer)entity).posY, ((EntityPlayer)entity).posZ, this.localBed.getX(), this.localBed.getY(), this.localBed.getZ()) > 30.0) {
                    this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("distanmce ALERT ASDFASDF"));
                }
                else {
                    this.mc.thePlayer.sendChatMessage("/l b");
                }
            }
        }
    }
    
    private void updateBed() {
        int ra;
        for (int y = ra = 30; y >= -ra; --y) {
            for (int x = -ra; x <= ra; ++x) {
                for (int z = -ra; z <= ra; ++z) {
                    final BlockPos p = new BlockPos(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ + z);
                    final boolean bed = this.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                    if (bed) {
                        this.localBed = p;
                        this.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("FOUND BED"));
                        break;
                    }
                }
            }
        }
    }
}
