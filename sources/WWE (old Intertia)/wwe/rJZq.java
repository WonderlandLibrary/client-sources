package wwe;

import wwe.modules.render.*;

class rJZq implements pSdr
{
    final XybC hILD;
    
    rJZq(final XybC hild) {
        this.hILD = hild;
        super();
    }
    
    @Override
    public int Gsgl() {
        return (int)(vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarScale.OnGi() * 2.0 + 3.0);
    }
    
    @Override
    public void Hrqs(final int n) {
        this.hILD.eUwe(3, n, vlwv.hNxW.qHPF((Class<Hud>)Ijoo.class).radarScale.OnGi().intValue());
    }
}
