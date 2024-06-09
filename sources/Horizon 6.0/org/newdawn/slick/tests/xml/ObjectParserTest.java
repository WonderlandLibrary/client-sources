package org.newdawn.slick.tests.xml;

import HORIZON-6-0-SKIDPROTECTION.SlickXMLException;
import HORIZON-6-0-SKIDPROTECTION.ObjectTreeParser;

public class ObjectParserTest
{
    public static void main(final String[] argv) throws SlickXMLException {
        final ObjectTreeParser parser = new ObjectTreeParser("org.newdawn.slick.tests.xml");
        parser.HorizonCode_Horizon_È("Bag", ItemContainer.class);
        final GameData parsedData = (GameData)parser.Ø­áŒŠá("testdata/objxmltest.xml");
        parsedData.HorizonCode_Horizon_È("");
    }
}
