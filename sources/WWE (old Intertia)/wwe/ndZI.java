package wwe;

class ndZI extends Thread
{
    final cJGk EDcJ;
    final Kdwt nXQQ;
    
    ndZI(final Kdwt nxqq, final cJGk eDcJ) {
        this.nXQQ = nxqq;
        this.EDcJ = eDcJ;
        super();
    }
    
    @Override
    public void run() {
        this.nXQQ.uKMJ.ocQD = FIwW.vdYN("https://crafatar.com/avatars/" + this.EDcJ.umix.toString() + "?size=50");
    }
}
