package me.napoleon.napoline.junk.NapoAL;

import java.util.ArrayList;
import java.util.List;

import me.napoleon.napoline.junk.NapoAL.checks.AntiPatch;
import me.napoleon.napoline.junk.NapoAL.checks.FakeWebsite;
import me.napoleon.napoline.junk.NapoAL.othercheck.ReVerify;

public class LnapolineAutoleak {
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
