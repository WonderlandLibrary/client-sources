package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public interface ICommand extends Comparable
{
    String Ý();
    
    String Ý(final ICommandSender p0);
    
    List Â();
    
    void HorizonCode_Horizon_È(final ICommandSender p0, final String[] p1) throws CommandException;
    
    boolean HorizonCode_Horizon_È(final ICommandSender p0);
    
    List HorizonCode_Horizon_È(final ICommandSender p0, final String[] p1, final BlockPos p2);
    
    boolean Â(final String[] p0, final int p1);
}
