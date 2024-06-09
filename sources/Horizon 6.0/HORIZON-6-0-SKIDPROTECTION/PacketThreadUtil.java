package HORIZON-6-0-SKIDPROTECTION;

public class PacketThreadUtil
{
    private static final String HorizonCode_Horizon_È = "CL_00002306";
    
    public static void HorizonCode_Horizon_È(final Packet p_180031_0_, final INetHandler p_180031_1_, final IThreadListener p_180031_2_) {
        final EventPacketRecieve event = new EventPacketRecieve();
        event.HorizonCode_Horizon_È(p_180031_0_);
        if (!p_180031_2_.Ï()) {
            p_180031_2_.HorizonCode_Horizon_È(new Runnable() {
                @Override
                public void run() {
                    if (!event.HorizonCode_Horizon_È()) {
                        if (p_180031_0_ instanceof S08PacketPlayerPosLook) {
                            final S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook)p_180031_0_;
                            if (Minecraft.áŒŠà().á != null && Minecraft.áŒŠà().á.É != -180.0f && Minecraft.áŒŠà().á.áƒ != 0.0f) {
                                poslook.Ø­áŒŠá = Minecraft.áŒŠà().á.É;
                                poslook.Âµá€ = Minecraft.áŒŠà().á.áƒ;
                            }
                            poslook.HorizonCode_Horizon_È(p_180031_1_);
                            return;
                        }
                        p_180031_0_.HorizonCode_Horizon_È(p_180031_1_);
                    }
                }
            });
            throw ThreadQuickExitException.HorizonCode_Horizon_È;
        }
    }
}
