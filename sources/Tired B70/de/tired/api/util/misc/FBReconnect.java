package de.tired.api.util.misc;

import de.tired.interfaces.IHook;
import de.tired.tired.Tired;
import lombok.SneakyThrows;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class FBReconnect implements IHook {

    private final String mainPath = MC.mcDataDir + "/" + Tired.INSTANCE.CLIENT_NAME;

    public String source() {
        return "'fb_reconnect.vbs (c) 2014 by Michael Engelke <http://www.mengelke.de>\n" +
                "\n" +
                "On Error Resume Next\n" +
                "\n" +
                "host = InputBox(\"Bitte die Adresse der Fritz!Box eingeben!\" & vbcrlf & vbcrlf & \"Alternativ-Adressen:\" & vbcrlf & \"192.168.178.1 oder 169.254.1.1\", _\n" +
                "\t\"FB-Reconnect (c) 2014 by Michael Engelke\",\"fritz.box\")\n" +
                "\n" +
                "If host = False Then\n" +
                " WScript.Quit\n" +
                "End If\n" +
                "Set http = Nothing\n" +
                "Set http = CreateObject(\"WinHttp.WinHttpRequest.5.1\")\n" +
                "If http Is Nothing Then Set http = CreateObject(\"WinHttp.WinHttpRequest.5\")\n" +
                "If http Is Nothing Then Set http = CreateObject(\"WinHttp.WinHttpRequest\")\n" +
                "If http Is Nothing Then Set http = CreateObject(\"MSXML2.ServerXMLHTTP\")\n" +
                "If http Is Nothing Then Set http = CreateObject(\"Microsoft.XMLHTTP\")\n" +
                "If http Is Nothing Then\n" +
                " MsgBox \"Kein HTTP-Objekt verfügbar!\",16,\"Fehler:\"\n" +
                "Else\n" +
                "'On Error Goto 0\n" +
                " body =\t\"<?xml version=\"\"1.0\"\" encoding=\"\"utf-8\"\"?>\" _\n" +
                "  & \"<s:Envelope xmlns:s=\"\"http://schemas.xmlsoap.org/soap/envelope/\"\" s:encodingStyle=\"\"http://schemas.xmlsoap.org/soap/encoding/\"\">\" _\n" +
                "  & \"<s:Body><u:ForceTermination xmlns:u=\"\"urn:schemas-upnp-org:service:WANIPConnection:1\"\" /></s:Body>\" _\n" +
                "  & \"</s:Envelope>\"\n" +
                " For Each url In Array(\"igd\",\"\")\n" +
                "  With http\n" +
                "   .Open \"POST\", \"http://\" & host & \":49000/\" & url & \"upnp/control/WANIPConn1\",false\n" +
                "   .setRequestHeader \"Content-Type\", \"text/xml; charset=\"\"utf-8\"\"\"\n" +
                "   .setRequestHeader \"Connection\", \"close\"\n" +
                "   .setRequestHeader \"Content-Length\", Len(body)\n" +
                "   .setRequestHeader \"HOST\", host & \":49000\"\n" +
                "   .setRequestHeader \"SOAPACTION\", \"\"\"urn:schemas-upnp-org:service:WANIPConnection:1#ForceTermination\"\"\"\n" +
                "   .Send body\n" +
                "  End With\n" +
                " Next\n" +
                "End If\n";
    }

    @SneakyThrows
    public void toData() {
        if (!(new File(mainPath)).exists())
            (new File(mainPath)).mkdir();

        if (!(new File(mainPath + "/fbReconnect.vbs")).exists())
            (new File(mainPath + "/fbReconnect.vbs")).createNewFile();

        FileWriter writer = new FileWriter(mainPath + "/fbReconnect.vbs");

        writer.write(source());

        writer.flush();
        writer.close();
    }

    public void openReconnectTool() {
        {
            try {
                File file = new File(mainPath + "/fbReconnect.vbs");
                if (!Desktop.isDesktopSupported())
                {
                    System.out.println("not supported");
                    return;
                }
                Desktop desktop = Desktop.getDesktop();
                if (file.exists())
                    desktop.open(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
