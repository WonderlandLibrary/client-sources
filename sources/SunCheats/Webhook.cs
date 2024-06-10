using System;
using System.Text;
using System.Data;
using System.Net;
using System.IO;
using System.Threading.Tasks;
using System.Threading;
using System.Linq;
using System.Collections.Generic;
using System.Collections.Specialized;

public class Webhook : IDisposable
{
    private WebClient client;
    public string URL { get; set; }
    public string Name { get; set; }
    public string Pfp { get; set; }

    public Webhook()
    {
        client = new WebClient();
    }


    public void SendMsg(string msg)
    {
        NameValueCollection collection = new NameValueCollection();
        collection.Add("username", Name);
        collection.Add("avatar_url", Pfp);
        collection.Add("content", msg);
        client.UploadValues(URL, collection);
    }

    public void Dispose()
    {
        client.Dispose();
    }
}
