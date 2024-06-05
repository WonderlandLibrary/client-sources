package me.superskidder.lune.luneautoleak;

import java.util.ArrayList;
import java.util.List;

import me.superskidder.lune.luneautoleak.checks.AntiPatch;
import me.superskidder.lune.luneautoleak.checks.FakeWebsite;
import me.superskidder.lune.luneautoleak.othercheck.ReVerify;

public class LuneAutoLeak {
    public AntiPatch antiPatch;
    public FakeWebsite fakeWebsite;
    public List<Integer> didVerify = new ArrayList<>();
    
    public void startLeak() {
        antiPatch = new AntiPatch();
        fakeWebsite = new FakeWebsite();
        new ReVerify();

        didVerify.add(3);
    }
}
