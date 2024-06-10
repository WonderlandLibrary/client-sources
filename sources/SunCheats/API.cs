using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Net;

namespace dearDaiAPI
{
    public class API
    {
        public string key = "notset";
        string keycompiled = "notset";
        string linktl_key;
        string pndtl_key;
        string trlink_key;
        


        public void linktl(string apiKey, string approveKey)
        {
            if(approveKey.Length > 1)
            {
                linktl_key = apiKey;
                WebClient wc = new WebClient();
                var link = wc.DownloadString("http://link.tl/api.php?uid=" + linktl_key + "&url=http://slay-systems.site/?key=" + Reverse(approveKey));
                Process.Start(link);
            }
        }

        public void pndtl(string apiKey, string approveKey)
        {
            if (approveKey.Length > 1)
            {
                pndtl_key = apiKey;
                WebClient wc = new WebClient();
                var link = wc.DownloadString("https://www.pnd.tl/api/?api=" + pndtl_key + "&url=http://slay-systems.site/?key=" + Reverse(approveKey) + "&alias=Key" + RandomString(10) + "&format=text");
                Process.Start(link);
            }
        }

        public void trlink(string apiKey, string approveKey)
        {
            if (approveKey.Length > 1)
            {
                trlink_key = apiKey;
                WebClient wc = new WebClient();
                
                var link = wc.DownloadString("https://tr.link/api?api=" + trlink_key + "&url=http://slay-systems.site/?key=" + Reverse(approveKey) + "&format=text");
                Process.Start(link);
            }
        }


        public string createCompiledkey()
        {
            keycompiled = Reverse(createKey());
            return keycompiled;
        }
        
        string createKey()
        {
            key = RandomString(32);
            return key;
        }


        private static Random random = new Random();
        public static string RandomString(int length)
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, length)
              .Select(s => s[random.Next(s.Length)]).ToArray());
        }

        public static string Reverse(string s)
        {
            char[] charArray = s.ToCharArray();
            Array.Reverse(charArray);
            return new string(charArray);
        }



    }
}
