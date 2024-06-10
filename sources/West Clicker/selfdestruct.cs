using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using Memory;
using System.Runtime.InteropServices;
using System.ServiceProcess;

namespace AnyDesk
{

    public class selfdestruct
    {
        IEnumerable<long> paladin;
        static IntPtr[] explorer;
        static IntPtr[] Isass;
        static IntPtr[] searchindexer;
        public Mem MemLib = new Mem();
        public Random random = new Random();
        public string weqwe(int ewe)
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            return new string(Enumerable.Repeat(chars, ewe).Select(s => s[random.Next(s.Length)]).ToArray());
        }
        private void oof(string wq)
        {
            ServiceController serviceController = new ServiceController(wq);
            try
            {
                if ((serviceController.Status.Equals(ServiceControllerStatus.Running)) || (serviceController.Status.Equals(ServiceControllerStatus.StartPending)))
                {
                    serviceController.Stop();
                }
                serviceController.WaitForStatus(ServiceControllerStatus.Stopped);
                serviceController.Start();
                serviceController.WaitForStatus(ServiceControllerStatus.Running);
            }
            catch
            {

            }
        }
        public async void sede()
        {
            oof("DPS");
            string exename = AppDomain.CurrentDomain.FriendlyName;
            DirectoryInfo pf = new DirectoryInfo(@"C:\windows\prefetch");
            FileInfo[] Files = pf.GetFiles(exename + "*");
            foreach (FileInfo files in Files)
            {
                File.Delete(files.FullName);
            }
            if (!MemLib.OpenProcess("javaw"))
            {

            }
            paladin = await MemLib.AoBScan(0, long.MaxValue, "41 6e 79 44 65 73 6b 2e 65 78 65");
            foreach (long add in paladin)
            {
                MemLib.WriteMemory(add.ToString("X"), "string", weqwe(23));
            }
            DotNetScanMemory_SmoLL dot = new DotNetScanMemory_SmoLL();
            explorer = dot.ScanArray(dot.GetPID("explorer"), "41 6e 79 44 65 73 6b 2e 65 78 65 2c 54 69 6d 65 2c 30");
            for (int i = 0; i < explorer.Count<IntPtr>(); i++)
            {

                dot.WriteArray(explorer[i], "20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20");
            }
            Isass = dot.ScanArray(dot.GetPID("Isass"), "41 6e 79 44 65 73 6b 2e 65 78 65 2c 54 69 6d 65 2c 30");
            for (int i = 0; i < Isass.Count<IntPtr>(); i++)
            {
                dot.WriteArray(Isass[i], "20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20");
            }
            searchindexer = dot.ScanArray(dot.GetPID("searchindexer"), "41 6e 79 44 65 73 6b 2e 65 78 65 2c 54 69 6d 65 2c 30");
            for (int i = 0; i < searchindexer.Count<IntPtr>(); i++)
            {
                dot.WriteArray(searchindexer[i], "20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20 20");
            }
            Environment.Exit(0);
        }
    }

}
