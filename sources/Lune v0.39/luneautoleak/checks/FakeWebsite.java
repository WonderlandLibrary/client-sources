package me.superskidder.lune.luneautoleak.checks;

import me.superskidder.lune.Lune;

public class FakeWebsite {
    //伪站
    public FakeWebsite() {
        Lune.INSTANCE.luneAutoLeak.didVerify.add(1);
    }
}
