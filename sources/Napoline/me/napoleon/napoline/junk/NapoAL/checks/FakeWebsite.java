package me.napoleon.napoline.junk.NapoAL.checks;

import me.napoleon.napoline.Napoline;

public class FakeWebsite {
    //伪站
    public FakeWebsite() {
        Napoline.INSTANCE.lnapolineAL.didVerify.add(1);
    }
}
