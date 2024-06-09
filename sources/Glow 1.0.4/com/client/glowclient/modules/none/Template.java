package com.client.glowclient.modules.none;

import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Template extends ModuleContainer
{
    public static NumberValue templateDouble;
    public static nB templateString;
    public static BooleanValue templateBoolean;
    public static StringValue templateString;
    
    @Override
    public void D() {
    }
    
    static {
        final String s = "Template";
        final String s2 = "TemplateDouble";
        final String s3 = "Template Description";
        final double n = 0.0;
        Template.templateDouble = ValueFactory.M(s, s2, s3, n, n, n, 0.0);
        Template.templateBoolean = ValueFactory.M("Template", "TemplateBoolean", "Template Description", true);
        Template.templateString = ValueFactory.M("Template", "TemplateString", "Template Description", "TempValue");
        Template.templateString = ValueFactory.M("Template", "TemplateString", "Template Description", "Value1", "Value1", "Value2");
    }
    
    public Template() {
        super(Category.NONE, "Template", false, -1, "Template Description");
    }
    
    @Override
    public boolean A() {
        return true;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
    }
    
    @Override
    public String M() {
        return "";
    }
    
    @Override
    public void E() {
    }
}
