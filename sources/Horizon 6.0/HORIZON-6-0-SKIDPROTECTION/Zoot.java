package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 268435455, Â = "NoFire/NoPotion.", HorizonCode_Horizon_È = "Zoot")
public class Zoot extends Mod
{
    private final Potion[] Ý;
    
    public Zoot() {
        this.Ý = new Potion[] { Potion.µÕ, Potion.µà, Potion.Ø­áŒŠá, Potion.¥Æ, Potion.Ø­à };
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            Potion[] ý;
            for (int length = (ý = this.Ý).length, i = 0; i < length; ++i) {
                final Potion potion = ý[i];
                if (this.Â.á.Â(potion) != null) {
                    final PotionEffect effect = this.Â.á.Â(potion);
                    for (int index = 0; index < effect.Â() / 20; ++index) {
                        this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer(this.Â.á.ŠÂµà));
                    }
                }
            }
            if (this.Â.á.ˆÏ() && !this.Â.á.HorizonCode_Horizon_È(Potion.£á) && !this.Â.á.ˆáŠ() && this.Â.á.ŠÂµà && !this.Â.á.£ÂµÄ() && !this.Â.á.HorizonCode_Horizon_È(Material.áŒŠÆ) && !this.Â.á.HorizonCode_Horizon_È(Material.Å)) {
                for (int index2 = 0; index2 < 20; ++index2) {
                    this.Â.µÕ().HorizonCode_Horizon_È(new C03PacketPlayer(this.Â.á.ŠÂµà));
                }
            }
        }
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend p) {
        if (p.Ý() instanceof C03PacketPlayer && this.Å() && !this.Â.á.Ñ¢Ó()) {
            if (Horizon.ˆà) {
                p.HorizonCode_Horizon_È(true);
                return;
            }
            if (Horizon.Ø­à && this.Â.á.Âµà && ((this.Â.á.HorizonCode_Horizon_È(Material.áŒŠÆ) && !this.Â.á.ˆÏ()) || this.Â.á.HorizonCode_Horizon_È(Material.Ø))) {
                p.HorizonCode_Horizon_È(true);
            }
            if (Horizon.¥Æ && this.Â.á.ˆÏ() && !Jesus.£à()) {
                p.HorizonCode_Horizon_È(true);
            }
        }
    }
    
    private boolean Å() {
        return Math.abs(this.Â.á.ÇŽÉ) <= 0.01 && Math.abs(this.Â.á.ÇŽÕ) <= 0.01 && Math.abs(this.Â.á.ˆá) <= 0.1 && Math.abs(this.Â.á.ˆá) >= -0.1;
    }
}
