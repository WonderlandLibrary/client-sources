package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = -1618884, Â = "Automaticly eats Soups if you low life.", HorizonCode_Horizon_È = "AutoSoup")
public class AutoSoup extends Mod
{
    private int Ý;
    private int Ø­áŒŠá;
    private Long Âµá€;
    private long Ó;
    private Long à;
    private Long Ø;
    
    public AutoSoup() {
        this.Ý = 282;
        this.Ø­áŒŠá = 281;
        this.Âµá€ = null;
        this.Ó = 50L;
        this.à = null;
        this.Ø = 125L;
    }
    
    @Handler
    private void HorizonCode_Horizon_È(final EventTick event) {
        if (this.Âµá€ == null || System.nanoTime() / 1000000L >= this.Ó + this.Âµá€) {
            this.Å();
            if (this.à == null || (System.nanoTime() / 1000000L >= this.Ø + this.à && this.Ý == 282)) {
                this.µà();
            }
        }
    }
    
    private void Å() {
        if (this.Â.á.Ï­Ä() <= 10.0) {
            this.£à();
            this.Âµá€ = System.nanoTime() / 1000000L;
        }
    }
    
    private void £à() {
        for (int index = 36; index < 45; ++index) {
            final ItemStack stack = this.Â.á.ŒÂ.HorizonCode_Horizon_È(index).HorizonCode_Horizon_È();
            if (stack != null && stack.HorizonCode_Horizon_È() instanceof ItemSoup) {
                final int oldslot = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
                this.Â.µÕ().HorizonCode_Horizon_È(new C09PacketHeldItemChange(index - 36));
                this.Â.Âµá€.Âµá€();
                this.Â.µÕ().HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, stack, 0.0f, 0.0f, 0.0f));
                this.Â.µÕ().HorizonCode_Horizon_È(new C09PacketHeldItemChange(oldslot));
                break;
            }
        }
    }
    
    private void µà() {
        final Integer firstBowl = this.ˆà();
        final ItemStack slotForBowls = this.Â.á.ŒÂ.HorizonCode_Horizon_È(37).HorizonCode_Horizon_È();
        if (firstBowl != null && (slotForBowls == null || slotForBowls.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(this.Ø­áŒŠá))) {
            this.Â.Âµá€.HorizonCode_Horizon_È(0, firstBowl, 0, 0, this.Â.á);
            this.Â.Âµá€.HorizonCode_Horizon_È(0, 37, 0, 0, this.Â.á);
            this.à = System.nanoTime() / 1000000L;
        }
        else {
            this.¥Æ();
        }
    }
    
    private Integer ˆà() {
        for (int slot = 38; slot <= 44; ++slot) {
            final Slot itemSlot = this.Â.á.ŒÂ.HorizonCode_Horizon_È(slot);
            if (itemSlot != null) {
                final ItemStack stack = itemSlot.HorizonCode_Horizon_È();
                if (stack != null && stack.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(this.Ø­áŒŠá)) {
                    return slot;
                }
            }
        }
        return null;
    }
    
    private void ¥Æ() {
        final Integer firstSoup = this.Ø­à();
        final Integer firstOpenSlot = this.µÕ();
        if (firstSoup != null && firstOpenSlot != null && firstSoup != firstOpenSlot) {
            this.Â.Âµá€.HorizonCode_Horizon_È(0, firstSoup, 0, 0, this.Â.á);
            this.Â.Âµá€.HorizonCode_Horizon_È(0, firstOpenSlot, 0, 0, this.Â.á);
            this.à = System.nanoTime() / 1000000L;
        }
    }
    
    private Integer Ø­à() {
        if (this.Â.¥Æ == null) {
            for (int slot = 9; slot <= 35; ++slot) {
                final Slot itemSlot = this.Â.á.ŒÂ.HorizonCode_Horizon_È(slot);
                if (itemSlot != null) {
                    final ItemStack stack = itemSlot.HorizonCode_Horizon_È();
                    if (stack != null && stack.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(this.Ý)) {
                        return slot;
                    }
                }
            }
        }
        return null;
    }
    
    private Integer µÕ() {
        for (int slot = 37; slot <= 44; ++slot) {
            final Slot itemSlot = this.Â.á.ŒÂ.HorizonCode_Horizon_È(slot);
            if (itemSlot != null) {
                final ItemStack stack = itemSlot.HorizonCode_Horizon_È();
                if (stack == null) {
                    return slot;
                }
            }
        }
        return null;
    }
    
    private Integer Æ() {
        int count = 0;
        for (int slot = 9; slot <= 44; ++slot) {
            final Slot itemSlot = this.Â.á.ŒÂ.HorizonCode_Horizon_È(slot);
            if (itemSlot != null) {
                final ItemStack stack = itemSlot.HorizonCode_Horizon_È();
                if (stack != null && stack.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(this.Ý)) {
                    ++count;
                }
            }
        }
        return count;
    }
}
