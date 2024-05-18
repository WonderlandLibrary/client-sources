package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -1, Â = "SPAM SPAM SPAM...", HorizonCode_Horizon_È = "FileSpammer")
public class FileSpammer extends Mod
{
    TimeHelper Ý;
    private int Ø­áŒŠá;
    private List<String> Âµá€;
    
    public FileSpammer() {
        this.Ý = new TimeHelper();
        this.Âµá€ = new ArrayList<String>();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Âµá€ = FileUtils.HorizonCode_Horizon_È(Minecraft.áŒŠà().ŒÏ + "\\Horizon\\spam.ini");
        this.Ø­áŒŠá = 0;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (this.Â.áŒŠÆ == null) {
            return;
        }
        if (!this.Ý.Â(6000L)) {
            return;
        }
        this.Ý.Ø­áŒŠá();
        if (this.Ø­áŒŠá < this.Âµá€.size()) {
            final String text = this.Âµá€.get(this.Ø­áŒŠá);
            final String text2 = text.replaceAll("%randomnumber%", "@" + new Random().nextInt(599901));
            this.Â.á.Â(text2);
            ++this.Ø­áŒŠá;
        }
        else {
            this.Ø­áŒŠá = 0;
        }
    }
}
