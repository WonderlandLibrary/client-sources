package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = 0, Â = "Keeps Sprinting if you attack an Entity.", HorizonCode_Horizon_È = "KeepSprint")
public class KeepSprint extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketRecieve event) {
        if (event.Ý() instanceof S1CPacketEntityMetadata) {
            final S1CPacketEntityMetadata packet = (S1CPacketEntityMetadata)event.Ý();
            if (packet.Â() == this.Â.á.ˆá()) {
                for (final Object o : packet.HorizonCode_Horizon_È()) {
                    if (o instanceof DataWatcher.HorizonCode_Horizon_È) {
                        final DataWatcher.HorizonCode_Horizon_È obj = (DataWatcher.HorizonCode_Horizon_È)o;
                        if (obj.HorizonCode_Horizon_È() != 0 || obj.Ý() != 0) {
                            continue;
                        }
                        obj.HorizonCode_Horizon_È((byte)((byte)obj.Â() | (this.Â.á.ÇªÂµÕ() ? 1 : 0) << 3));
                    }
                }
            }
        }
    }
}
