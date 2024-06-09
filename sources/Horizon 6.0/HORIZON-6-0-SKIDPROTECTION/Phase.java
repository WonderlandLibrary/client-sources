package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Phase through blocks.", HorizonCode_Horizon_È = "Phase")
public class Phase extends Mod
{
    private TimeHelper Ý;
    private int Ø­áŒŠá;
    
    public Phase() {
        this.Ý = new TimeHelper();
        this.HorizonCode_Horizon_È = "instant";
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Â.á == null) {
            return;
        }
        if (this.HorizonCode_Horizon_È.equals("instant") && (this.Â.á.Çªà¢() || this.Â.á.¥à)) {
            this.Å();
        }
        if (this.HorizonCode_Horizon_È.equals("skip")) {
            float dir = this.Â.á.É;
            if (this.Â.á.Ï­áˆºÓ < 0.0f) {
                dir += 180.0f;
            }
            if (this.Â.á.£áƒ > 0.0f) {
                dir -= 90.0f * ((this.Â.á.Ï­áˆºÓ > 0.0f) ? 0.5f : ((this.Â.á.Ï­áˆºÓ < 0.0f) ? -0.5f : 1.0f));
            }
            if (this.Â.á.£áƒ < 0.0f) {
                dir += 90.0f * ((this.Â.á.Ï­áˆºÓ > 0.0f) ? 0.5f : ((this.Â.á.Ï­áˆºÓ < 0.0f) ? -0.5f : 1.0f));
            }
            final double hOff = 0.3;
            final double vOff = 0.2;
            final float xD = (float)((float)Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0) * hOff);
            final float zD = (float)((float)Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0) * hOff);
            for (int i = 0; i < 6; ++i) {
                this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ - 0.02, this.Â.á.Ê, true));
                this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ + xD * i, this.Â.á.Çªà¢, this.Â.á.Ê + zD * i, true));
            }
        }
    }
    
    public void Å() {
        final double x = this.Â.á.ˆà¢().ˆÏ­().HorizonCode_Horizon_È() * 0.1;
        final double z = this.Â.á.ˆà¢().ˆÏ­().Ý() * 0.1;
        this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ - 0.05, this.Â.á.Ê, true));
        this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ + x * 4.0, this.Â.á.Çªà¢, this.Â.á.Ê + z * 4.0, true));
        this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ - 0.05, this.Â.á.Ê, true));
        this.Â.á.Ý(this.Â.á.ŒÏ + x * 4.0, this.Â.á.Çªà¢ - 0.05, this.Â.á.Ê + z * 4.0);
    }
    
    private boolean HorizonCode_Horizon_È(final ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.HorizonCode_Horizon_È() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.HorizonCode_Horizon_È();
            if (ItemPotion.Ó(stack.à()) && potion.ÂµÈ(stack) != null) {
                for (final Object o : potion.ÂµÈ(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.HorizonCode_Horizon_È() == Potion.Ø.É) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventMovementSpeed event) {
        if (this.HorizonCode_Horizon_È.equals("motionclip") && (this.£à() || this.Â.á.Çªà¢())) {
            if (this.Â.á.Çªà¢()) {
                event.HorizonCode_Horizon_È(event.Ý() / 0.3);
                event.Ý(event.Âµá€() / 0.3);
            }
            event.HorizonCode_Horizon_È(event.Ý() / 2.0);
            event.Ý(event.Âµá€() / 2.0);
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate _event) {
        if (this.HorizonCode_Horizon_È.equals("instant") && this.Â.á.¥à && !this.£à() && !this.Â.á.Çªà¢() && this.Â.á.ŠÂµà && this.Â.ŠÄ.ÇªÉ.Ø­áŒŠá()) {
            this.Å();
        }
        if (this.HorizonCode_Horizon_È.equals("silent") && !this.£à() && this.Â.á.¥à) {
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá >= 2) {
                this.Â.µÕ().HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
                this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ + 2.147483647E9, this.Â.á.Ê, false));
                this.Â.µÕ().HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.Â));
                this.Ø­áŒŠá = 0;
            }
        }
        if (this.HorizonCode_Horizon_È.equals("skip") && this.Â.á.¥à && (!this.£à() || this.Â.á.Çªà¢()) && this.Â.á.ŠÂµà) {
            float dir = this.Â.á.É;
            if (this.Â.á.Ï­áˆºÓ < 0.0f) {
                dir += 180.0f;
            }
            if (this.Â.á.£áƒ > 0.0f) {
                dir -= 90.0f * ((this.Â.á.Ï­áˆºÓ > 0.0f) ? 0.5f : ((this.Â.á.Ï­áˆºÓ < 0.0f) ? -0.5f : 1.0f));
            }
            if (this.Â.á.£áƒ < 0.0f) {
                dir += 90.0f * ((this.Â.á.Ï­áˆºÓ > 0.0f) ? 0.5f : ((this.Â.á.Ï­áˆºÓ < 0.0f) ? -0.5f : 1.0f));
            }
            final double hOff = 0.3;
            final double vOff = 0.2;
            final float xD = (float)((float)Math.cos((dir + 90.0f) * 3.141592653589793 / 180.0) * hOff);
            final float yD = (float)vOff;
            final float zD = (float)((float)Math.sin((dir + 90.0f) * 3.141592653589793 / 180.0) * hOff);
            for (int i = 0; i < 6; ++i) {
                this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ - 0.02, this.Â.á.Ê, true));
                this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ + xD * i, this.Â.á.Çªà¢, this.Â.á.Ê + zD * i, true));
            }
        }
        if (this.HorizonCode_Horizon_È.equals("vanilla") && this.Â.á.¥à) {
            final EntityPlayerSPOverwrite á = this.Â.á;
            final EntityPlayerSPOverwrite á2 = this.Â.á;
            final double n = 0.0;
            á2.ÇŽÕ = n;
            á.ÇŽÉ = n;
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventBoundingBox event) {
        if ((this.Â.á.¥à || !this.Â.á.ŠÂµà || this.£à() || event.Âµá€() > this.Â.á.£É().Âµá€) && event.à().Âµá€ > this.Â.á.£É().Â && (!this.HorizonCode_Horizon_È.equals("skip") || this.£à() || !this.Â.á.ŠÂµà)) {
            event.HorizonCode_Horizon_È((AxisAlignedBB)null);
        }
    }
    
    private boolean £à() {
        for (int x = MathHelper.Ý(this.Â.á.£É().HorizonCode_Horizon_È); x < MathHelper.Ý(this.Â.á.£É().Ø­áŒŠá) + 1; ++x) {
            for (int y = MathHelper.Ý(this.Â.á.£É().Â); y < MathHelper.Ý(this.Â.á.£É().Âµá€) + 1; ++y) {
                for (int z = MathHelper.Ý(this.Â.á.£É().Ý); z < MathHelper.Ý(this.Â.á.£É().Ó) + 1; ++z) {
                    final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.HorizonCode_Horizon_È(this.Â.áŒŠÆ, new BlockPos(x, y, z), this.Â.áŒŠÆ.Â(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && this.Â.á.£É().Â(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketRecieve event) {
        if (event.Ý() instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)event.Ý()).HorizonCode_Horizon_È() == this.Â.á.ˆá() && this.£à()) {
            event.HorizonCode_Horizon_È(true);
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend event) {
        if (this.HorizonCode_Horizon_È.equals("vanilla") && event.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer c03PacketPlayer;
            final C03PacketPlayer packet = c03PacketPlayer = (C03PacketPlayer)event.Ý();
            c03PacketPlayer.Â -= 0.1;
        }
        if (this.HorizonCode_Horizon_È.equals("motionclip") && event.Ý() instanceof C03PacketPlayer && this.Â.á.Çªà¢()) {
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá >= 5) {
                final C03PacketPlayer c03PacketPlayer2 = (C03PacketPlayer)event.Ý();
                --c03PacketPlayer2.Â;
                this.Ø­áŒŠá = 0;
            }
        }
    }
}
