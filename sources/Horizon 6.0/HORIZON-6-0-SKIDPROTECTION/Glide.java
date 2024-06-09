package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Keyboard;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -13330213, Â = "Glide...", HorizonCode_Horizon_È = "Glide")
public class Glide extends Mod
{
    private double áˆºÑ¢Õ;
    private double ÂµÈ;
    private double á;
    private boolean ˆÏ­;
    private TimeHelper £á;
    private TimeHelper Å;
    private TimeHelper £à;
    private boolean µà;
    private float ˆà;
    public int Ý;
    public int Ø­áŒŠá;
    private double ¥Æ;
    public static int Âµá€;
    private double Ø­à;
    private boolean µÕ;
    TimeHelper Ó;
    Integer à;
    private double Æ;
    private boolean Šáƒ;
    private double Ï­Ðƒà;
    private double áŒŠà;
    private double ŠÄ;
    boolean Ø;
    boolean áŒŠÆ;
    
    static {
        Glide.Âµá€ = 0;
    }
    
    public Glide() {
        this.£á = new TimeHelper();
        this.Å = new TimeHelper();
        this.£à = new TimeHelper();
        this.µà = false;
        this.ˆà = 1.0f;
        this.Ø­áŒŠá = 0;
        this.µÕ = false;
        this.Ó = new TimeHelper();
        this.à = 0;
        this.Ø = false;
        this.áŒŠÆ = false;
        this.HorizonCode_Horizon_È = "old";
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.µÕ = false;
        if (this.Â.á != null) {
            this.Ø­à = this.Â.á.Çªà¢ - 0.1;
            this.Å.Ø­áŒŠá();
            this.£à.Ø­áŒŠá();
            this.¥Æ = 1337.0;
            Glide.Âµá€ = 0;
            if (this.Â.á.ŠÂµà) {
                if (this.Â.ÇŽÉ()) {
                    Utiils.HorizonCode_Horizon_È(1);
                }
                else {
                    Utiils.HorizonCode_Horizon_È();
                }
            }
            new Thread("upset") {
                @Override
                public void run() {
                    try {
                        Thread.sleep(50L);
                    }
                    catch (InterruptedException ex) {}
                    if (Horizon.á) {
                        Glide.this.Â.á.Ý(Glide.this.Â.á.ŒÏ, Glide.this.Â.á.Çªà¢ + 1.4, Glide.this.Â.á.Ê);
                    }
                }
            }.start();
        }
    }
    
    @Override
    public void á() {
        this.Ø­áŒŠá = 0;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        if (e.Ý() != EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            return;
        }
        if (this.Â.á.µà > 0 && !this.µÕ && this.£à.Â(1000L)) {
            Minecraft.áŒŠà().á.HorizonCode_Horizon_È(new ChatComponentText(String.valueOf(Horizon.Ø­áŒŠá) + " §cYou can now touch the ground §cwhile §7flying."));
            this.µÕ = true;
        }
        if (this.HorizonCode_Horizon_È.equalsIgnoreCase("new")) {
            if (this.Â.á.ˆá <= -0.010426 && !this.Å() && this.áˆºÑ¢Õ()) {
                ++this.Ø­áŒŠá;
                if (this.Ø­áŒŠá < 5) {
                    this.Â.á.ˆá = -0.010426;
                }
                else if (this.Ø­áŒŠá < 10) {
                    this.Â.á.ˆá = -0.09026;
                }
                else if (this.Ø­áŒŠá < 15) {
                    this.Â.á.ˆá = -0.010426;
                    this.Ø­áŒŠá = 0;
                }
                System.out.println(this.Ø­áŒŠá);
                final BlockPos bpos = new BlockPos((int)this.Â.á.ŒÏ, (int)(this.Â.á.à¢.Â - 0.01), (int)this.Â.á.Ê);
                final Block speed = this.Â.áŒŠÆ.Â(bpos).Ý();
                if (!(speed instanceof BlockAir)) {
                    this.Šáƒ = true;
                }
                else {
                    this.Šáƒ = false;
                }
            }
            else if (this.Šáƒ && this.Å()) {}
        }
        else if (this.HorizonCode_Horizon_È.equalsIgnoreCase("old")) {
            if (this.Â.á.ˆá <= -0.03126 && !this.Å() && this.áˆºÑ¢Õ()) {
                this.Â.á.ˆá = -0.03126;
                final BlockPos bpos = new BlockPos((int)this.Â.á.ŒÏ, (int)(this.Â.á.à¢.Â - 0.01), (int)this.Â.á.Ê);
                final Block speed = this.Â.áŒŠÆ.Â(bpos).Ý();
                if (!(speed instanceof BlockAir)) {
                    this.Šáƒ = true;
                }
                else {
                    this.Šáƒ = false;
                    this.Æ = 0.6000000238418579;
                }
            }
            else if (!this.Šáƒ || this.Å()) {}
        }
        if (this.£á.Â(10L)) {
            this.£á.Ø­áŒŠá();
            if (this.Â.¥Æ != null) {
                return;
            }
            if (Keyboard.isKeyDown(57) && this.Ø­à - 1.0 > this.Â.á.Çªà¢ + (this.Â.ŠÄ.ŒÂ.HorizonCode_Horizon_È ? 1.4 : 0.28)) {
                if (this.Â.ŠÄ.ŒÂ.HorizonCode_Horizon_È) {
                    final EntityPlayerSPOverwrite á = Minecraft.áŒŠà().á;
                    final double œï = this.Â.á.ŒÏ;
                    final EntityPlayerSPOverwrite á2 = this.Â.á;
                    á.Ý(œï, ++á2.Çªà¢, this.Â.á.Ê);
                    return;
                }
                final EntityPlayerSPOverwrite á3 = Minecraft.áŒŠà().á;
                final double œï2 = this.Â.á.ŒÏ;
                final EntityPlayerSPOverwrite á4 = this.Â.á;
                á3.Ý(œï2, á4.Çªà¢ += 0.28, this.Â.á.Ê);
            }
            else if (Keyboard.isKeyDown(42) && this.Ø­à - 1.0 > this.Â.á.Çªà¢ + (this.Â.ŠÄ.ŒÂ.HorizonCode_Horizon_È ? 0.4 : 0.18)) {
                if (this.Â.ŠÄ.ŒÂ.HorizonCode_Horizon_È) {
                    final EntityPlayerSPOverwrite á5 = Minecraft.áŒŠà().á;
                    final double œï3 = this.Â.á.ŒÏ;
                    final EntityPlayerSPOverwrite á6 = this.Â.á;
                    á5.Ý(œï3, --á6.Çªà¢, this.Â.á.Ê);
                    return;
                }
                final EntityPlayerSPOverwrite á7 = Minecraft.áŒŠà().á;
                final double œï4 = this.Â.á.ŒÏ;
                final EntityPlayerSPOverwrite á8 = this.Â.á;
                á7.Ý(œï4, á8.Çªà¢ -= 0.18, this.Â.á.Ê);
            }
            else if (Keyboard.isKeyDown(57)) {
                if (this.Ø­à <= this.Â.á.Çªà¢ + (this.Â.ŠÄ.ŒÂ.HorizonCode_Horizon_È ? 1.9 : 0.68)) {
                    return;
                }
                if (Keyboard.isKeyDown(42)) {
                    if (this.Ø­à <= this.Â.á.Çªà¢ + (this.Â.ŠÄ.ŒÂ.HorizonCode_Horizon_È ? -0.4 : -0.68)) {
                        return;
                    }
                    if (Keyboard.isKeyDown(29)) {
                        final EntityPlayerSPOverwrite á9 = Minecraft.áŒŠà().á;
                        final double œï5 = this.Â.á.ŒÏ;
                        final EntityPlayerSPOverwrite á10 = this.Â.á;
                        á9.Ý(œï5, á10.Çªà¢ -= 0.28, this.Â.á.Ê);
                    }
                }
            }
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend e) {
        if (!this.áˆºÑ¢Õ()) {
            return;
        }
        if (e.Ý() instanceof C03PacketPlayer) {
            final C03PacketPlayer p = (C03PacketPlayer)e.Ý();
            if (p.Â() > this.¥Æ && !this.Â.á.ŠÂµà) {
                ++Glide.Âµá€;
            }
            if (this.Â.á.ŠÂµà) {
                Glide.Âµá€ = 0;
            }
            this.¥Æ = p.Â();
            if (this.Å.Â(1000L)) {
                p.HorizonCode_Horizon_È = this.Â.á.ŒÏ;
                p.Â = this.Â.á.Çªà¢ - 0.25543;
            }
        }
    }
    
    private boolean Å() {
        final BlockPos bpos = new BlockPos((int)this.Â.á.ŒÏ, (int)(this.Â.á.à¢.Â - 0.01), (int)this.Â.á.Ê);
        final Block block = this.Â.áŒŠÆ.Â(bpos).Ý();
        return !(block instanceof BlockAir) && block.£à();
    }
}
