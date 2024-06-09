package org.newdawn.slick.tests.xml;

import HORIZON-6-0-SKIDPROTECTION.XMLElement;
import HORIZON-6-0-SKIDPROTECTION.SlickException;
import HORIZON-6-0-SKIDPROTECTION.XMLParser;

public class XMLTest
{
    private static void HorizonCode_Horizon_È(final String message) {
        throw new RuntimeException(message);
    }
    
    private static void HorizonCode_Horizon_È(final Object object1) {
        if (object1 == null) {
            throw new RuntimeException("TEST FAILS: " + object1 + " must not be null");
        }
    }
    
    private static void HorizonCode_Horizon_È(final float a1, final float a2) {
        if (a1 != a2) {
            throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
        }
    }
    
    private static void HorizonCode_Horizon_È(final int a1, final int a2) {
        if (a1 != a2) {
            throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
        }
    }
    
    private static void HorizonCode_Horizon_È(final Object a1, final Object a2) {
        if (!a1.equals(a2)) {
            throw new RuntimeException("TEST FAILS: " + a1 + " should be " + a2);
        }
    }
    
    public static void main(final String[] argv) throws SlickException {
        final XMLParser parser = new XMLParser();
        final XMLElement root = parser.HorizonCode_Horizon_È("testdata/test.xml");
        HorizonCode_Horizon_È(root.Â(), "testRoot");
        System.out.println(root);
        HorizonCode_Horizon_È((Object)root.Âµá€("simple").HorizonCode_Horizon_È(0).Ý());
        System.out.println(root.Âµá€("simple").HorizonCode_Horizon_È(0).Ý());
        final XMLElement parent = root.Âµá€("parent").HorizonCode_Horizon_È(0);
        HorizonCode_Horizon_È(parent.Âµá€("grandchild").HorizonCode_Horizon_È(), 0);
        HorizonCode_Horizon_È(parent.Âµá€("child").HorizonCode_Horizon_È(), 2);
        HorizonCode_Horizon_È(parent.Âµá€("child").HorizonCode_Horizon_È(0).Ø­áŒŠá().HorizonCode_Horizon_È(), 2);
        final XMLElement child = parent.Âµá€("child").HorizonCode_Horizon_È(0).Ø­áŒŠá().HorizonCode_Horizon_È(0);
        final String name = child.HorizonCode_Horizon_È("name");
        final String test = child.HorizonCode_Horizon_È("nothere", "defaultValue");
        final int age = child.Â("age");
        HorizonCode_Horizon_È(name, "bob");
        HorizonCode_Horizon_È(test, "defaultValue");
        HorizonCode_Horizon_È(age, 1);
        final XMLElement other = root.Âµá€("other").HorizonCode_Horizon_È(0);
        final float x = (float)other.Ý("x");
        final float y = (float)other.HorizonCode_Horizon_È("y", 1.0);
        float z = (float)other.HorizonCode_Horizon_È("z", 83.0);
        HorizonCode_Horizon_È(x, 5.3f);
        HorizonCode_Horizon_È(y, 5.4f);
        HorizonCode_Horizon_È(z, 83.0f);
        try {
            z = (float)other.Ý("z");
            HorizonCode_Horizon_È("Attribute z as a double should fail");
        }
        catch (SlickException ex) {}
    }
}
