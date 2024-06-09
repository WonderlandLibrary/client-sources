package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 16777215, Â = "Auto MLG if you fall.", HorizonCode_Horizon_È = "MLG")
public class MLG extends Mod
{
    int Ý;
    boolean Ø­áŒŠá;
    boolean Âµá€;
    
    public MLG() {
        this.Ý = 0;
        this.Ø­áŒŠá = false;
        this.Âµá€ = false;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (this.Â.á.Ï­à >= 3.0f) {
            final int grounddistance = (int)this.Â.á.Çªà¢ - £à();
            if ((grounddistance < 4 && this.Ý("Water Bucket")) || this.Ý("Cobweb")) {
                this.Â.á.áƒ = 90.0f;
                this.Â.á.É = 90.0f;
                this.Â.á.ÂµÕ = 90.0f;
            }
            this.Âµá€ = true;
            if (this.Ý("Water Bucket")) {
                this.Ø­áŒŠá("Water Bucket");
            }
            else if (this.Ý("Cobweb")) {
                this.Ø­áŒŠá("Cobweb");
            }
            final BlockPos bpos = new BlockPos(this.Â.á.£á().HorizonCode_Horizon_È(), this.Â.á.£á().Â() - 3, this.Â.á.£á().Ý());
            final Block blocks = this.Â.áŒŠÆ.Â(bpos).Ý();
            if ((blocks.Ó() != Material.HorizonCode_Horizon_È && this.Ý("Water Bucket")) || this.Ý("Cobweb")) {
                this.Å();
                ++this.Ý;
                if (this.Ý >= 50) {
                    if (this.Ý("Bucket")) {
                        this.Ø­áŒŠá("Bucket");
                        this.Å();
                    }
                    this.Ý = 0;
                }
            }
        }
        else {
            this.Âµá€ = false;
        }
    }
    
    public void HorizonCode_Horizon_È(final Packet packet) {
        if (this.Âµá€ && packet instanceof C03PacketPlayer.Â) {
            new C03PacketPlayer.Â(90.0f, 90.0f, false);
        }
    }
    
    public void Å() {
        final ItemStack item = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        this.Â.Âµá€.HorizonCode_Horizon_È(this.Â.á, this.Â.áŒŠÆ, item);
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(this.Â.á.ŒÏ, £à(), this.Â.á.Ê), -1, item, 0.0f, 0.0f, 0.0f));
    }
    
    public boolean Ý(final String itemNamee) {
        for (int i = 36; i <= 44; ++i) {
            if (this.Â.á.ŒÂ.HorizonCode_Horizon_È(i).Â()) {
                final String itemName = this.Â.á.ŒÂ.HorizonCode_Horizon_È(i).HorizonCode_Horizon_È().µà();
                if (itemName.equalsIgnoreCase(itemNamee)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void Ø­áŒŠá(final String itemNamee) {
        for (int i = 36; i <= 44; ++i) {
            if (this.Â.á.ŒÂ.HorizonCode_Horizon_È(i).Â()) {
                final String itemName = this.Â.á.ŒÂ.HorizonCode_Horizon_È(i).HorizonCode_Horizon_È().µà();
                if (itemName.equalsIgnoreCase(itemNamee)) {
                    this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = i - 36;
                    break;
                }
            }
        }
    }
    
    public static int £à() {
        final Minecraft mc = Minecraft.áŒŠà();
        final int y = (int)mc.á.Çªà¢;
        int dist = 0;
        final byte result = -1;
        while (result == -1) {
            final BlockPos bpos = new BlockPos(mc.á.ŒÏ, mc.á.Çªà¢ - dist, mc.á.Ê);
            final Block block = mc.áŒŠÆ.Â(bpos).Ý();
            if (!(block instanceof BlockAir)) {
                final int var6 = y - dist;
                return var6;
            }
            ++dist;
        }
        return -1;
    }
}
