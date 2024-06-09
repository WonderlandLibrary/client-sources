package com.client.glowclient;

import java.util.*;
import net.minecraftforge.fml.common.versioning.*;

public class yA
{
    public List<String> L;
    public int A;
    public String B;
    public String b;
    
    public yA() {
        super();
    }
    
    public ComparableVersion M() {
        return new ComparableVersion(this.B);
    }
    
    public String M() {
        if (this.L == null) {
            return "";
        }
        return wB.M().join(this.L);
    }
}
