package HORIZON-6-0-SKIDPROTECTION;

abstract class ControllerControl implements Control
{
    protected static final int HorizonCode_Horizon_È = 0;
    protected static final int Â = 1;
    protected static final int Ý = 2;
    protected static final int Ø­áŒŠá = 3;
    protected static final int Âµá€ = 4;
    private int Ó;
    private int à;
    private int Ø;
    
    protected ControllerControl(final int controllerNumber, final int event, final int button) {
        this.Ó = event;
        this.à = button;
        this.Ø = controllerNumber;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ControllerControl)) {
            return false;
        }
        final ControllerControl c = (ControllerControl)o;
        return c.Ø == this.Ø && c.Ó == this.Ó && c.à == this.à;
    }
    
    @Override
    public int hashCode() {
        return this.Ó + this.à + this.Ø;
    }
}
