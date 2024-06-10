using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace AnyDeskResolver.Utils
{
    class Tools
    {
        public static string GetAddy()
        {


            string line;
            using (var file = new System.IO.StreamReader(@"C:\ProgramData\AnyDesk\system.conf"))
            {
                while ((line = file.ReadLine()) != null)
                {
                    if (line.Contains("ad.anynet.id="))
                    {
                        string anydeskaddy = line.Replace("ad.anynet.id=", "");


                        return anydeskaddy;
                    }
                }
                file.Close();
            }
              


            return "";
        }

        public static void resetAddy()
        {
            string anydeskpath = "";

            try
            {

                Process[] anydeskProc = Process.GetProcessesByName("AnyDesk");
                if (anydeskProc.Length != 0)
                {
                    anydeskpath = anydeskProc[0].MainModule.FileName;

                    Process proc = new Process();
                    ProcessStartInfo ps = new ProcessStartInfo();
                    ps.Arguments = $"/c Taskkill /F /T /IM AnyDesk*";
                    ps.FileName = "cmd.exe";
                    ps.WindowStyle = ProcessWindowStyle.Hidden;
                    ps.CreateNoWindow = true;
                    proc.StartInfo = ps;
                    proc.Start();

                    
                    Thread.Sleep(3500);
                

                }
                File.Delete(@"C:\ProgramData\AnyDesk\system.conf");
                File.Delete(@"C:\ProgramData\AnyDesk\service.conf");
                Process.Start(anydeskpath);

                //MessageBox.Show(utils.GetAddy());
                //label13.Text = "New: " +  utils.GetAddy();
            }
            catch (Exception kanker)
            {
                MessageBox.Show(kanker.Message);
            }
        }

    }
}
