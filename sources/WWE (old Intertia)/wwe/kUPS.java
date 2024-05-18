package wwe;

class kUPS extends Thread
{
    final dcJR PMyx;
    final iroq bklh;
    
    kUPS(final iroq bklh, final dcJR pMyx) {
        this.bklh = bklh;
        this.PMyx = pMyx;
        super();
    }
    
    @Override
    public void run() {
        iroq.nHeb(this.bklh).ArMM(this.PMyx);
    }
}
