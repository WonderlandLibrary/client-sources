using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Management;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace skidderino
{
    class anticrack
    {
        [DllImport("kernel32.dll", EntryPoint = "GetModuleHandle")]
        private static extern IntPtr GenericAcl(string lpModuleName);

        [DllImport("kernel32.dll", EntryPoint = "GetProcAddress")]
        private static extern IntPtr TryCode(IntPtr hModule, string procName);

        [DllImport("kernel32.dll", CharSet = CharSet.Auto, EntryPoint = "GetFileAttributes", SetLastError = true)]
        private static extern uint ISymbolReader(string lpFileName);

        private static string SoapNcName([In] string obj0, [In] string obj1)
		{
			RegistryKey registryKey = Registry.LocalMachine.OpenSubKey(obj0, false);
			if (registryKey == null)
			{
				return "noKey";
			}
			object value = registryKey.GetValue(obj1, "noValueButYesKey");
			if (value is string || registryKey.GetValueKind(obj1) == RegistryValueKind.String || registryKey.GetValueKind(obj1) == RegistryValueKind.ExpandString)
			{
				return value.ToString();
			}
			if (registryKey.GetValueKind(obj1) == RegistryValueKind.DWord)
			{
				return Convert.ToString((int)value);
			}
			if (registryKey.GetValueKind(obj1) == RegistryValueKind.QWord)
			{
				return Convert.ToString((long)value);
			}
			if (registryKey.GetValueKind(obj1) == RegistryValueKind.Binary)
			{
				return Convert.ToString((byte[])value);
			}
			if (registryKey.GetValueKind(obj1) == RegistryValueKind.MultiString)
			{
				return string.Join("", (string[])value);
			}
			return "noValueButYesKey";
		}


        private static IntPtr GetModuleHandle(string libName)
        {
            foreach (object obj in Process.GetCurrentProcess().Modules)
            {
                ProcessModule processModule = (ProcessModule)obj;
                if (processModule.ModuleName.ToLower().Contains(libName.ToLower()))
                {
                    return processModule.BaseAddress;
                }
            }
            return IntPtr.Zero;
        }

        internal static bool IsSandbox()
        {
            return GetModuleHandle("SbieDll.dll") != IntPtr.Zero;
        }

        internal static bool SecurityDocumentElement()
        {
            return MessageDictionary();
        }

        internal static bool IsVM()
        {
            return SecurityDocumentElement();
        }

        internal static bool IsEmulation()
        {
            int num = new Random().Next(3000, 10000);
            DateTime now = DateTime.Now;
            Thread.Sleep(num);
            return (DateTime.Now - now).TotalMilliseconds < (double)num;
        }

        internal static bool IsRunning()
        {
            bool result = false;
            if (Debugger.IsAttached || Debugger.IsLogging())
            {
                result = true;
            }
            else
            {
                string[] array = new string[]
                {
                    "codecracker",
                    "x32dbg",
                    "x64dbg",
                    "ollydbg",
                    "ida",
                    "charles",
                    "dnspy",
                    "simpleassembly",
                    "peek",
                    "httpanalyzer",
                    "httpdebug",
                    "fiddler",
                    "wireshark",
                    "dbx",
                    "mdbg",
                    "gdb",
                    "windbg",
                    "dbgclr",
                    "kdb",
                    "kgdb",
                    "mdb",
                    "processhacker",
                    "procexp64",
                    "procexp",
                    "scylla_x86",
                    "scylla_x64",
                    "scylla",
                    "idau64",
                    "idau",
                    "idaq",
                    "idaq64",
                    "idaw",
                    "idaw64",
                    "idag",
                    "idag64",
                    "ida64",
                    "ida",
                    "ImportREC",
                    "IMMUNITYDEBUGGER",
                    "MegaDumper",
                    "CodeBrowser",
                    "reshacker",
                    "cheat engine"
                };
                foreach (Process process in Process.GetProcesses())
                {
                    if (process != Process.GetCurrentProcess())
                    {
                        for (int j = 0; j < array.Length; j++)
                        {
                            if (process.ProcessName.ToLower().Contains(array[j]))
                            {
                                result = true;
                            }
                            if (process.MainWindowTitle.ToLower().Contains(array[j]))
                            {
                                result = true;
                            }
                        }
                    }
                }
            }
            return result;
        }

        private static bool MessageDictionary()
        {
            if (SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VBOX") || SoapNcName("HARDWARE\\Description\\System", "SystemBiosVersion").ToUpper().Contains("VBOX") || SoapNcName("HARDWARE\\Description\\System", "VideoBiosVersion").ToUpper().Contains("VIRTUALBOX") || SoapNcName("SOFTWARE\\Oracle\\VirtualBox Guest Additions", "") == "noValueButYesKey" || ISymbolReader("C:\\WINDOWS\\system32\\drivers\\VBoxMouse.sys") != 4294967295U || SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || SoapNcName("SOFTWARE\\VMware, Inc.\\VMware Tools", "") == "noValueButYesKey" || SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 1\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 2\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("VMWARE") || SoapNcName("SYSTEM\\ControlSet001\\Services\\Disk\\Enum", "0").ToUpper().Contains("vmware".ToUpper()) || SoapNcName("SYSTEM\\ControlSet001\\Control\\Class\\{4D36E968-E325-11CE-BFC1-08002BE10318}\\0000", "DriverDesc").ToUpper().Contains("VMWARE") || SoapNcName("SYSTEM\\ControlSet001\\Control\\Class\\{4D36E968-E325-11CE-BFC1-08002BE10318}\\0000\\Settings", "Device Description").ToUpper().Contains("VMWARE") || SoapNcName("SOFTWARE\\VMware, Inc.\\VMware Tools", "InstallPath").ToUpper().Contains("C:\\PROGRAM FILES\\VMWARE\\VMWARE TOOLS\\") || ISymbolReader("C:\\WINDOWS\\system32\\drivers\\vmmouse.sys") != 4294967295U || ISymbolReader("C:\\WINDOWS\\system32\\drivers\\vmhgfs.sys") != 4294967295U || TryCode(GenericAcl("kernel32.dll"), "wine_get_unix_file_name") != (IntPtr)0 || SoapNcName("HARDWARE\\DEVICEMAP\\Scsi\\Scsi Port 0\\Scsi Bus 0\\Target Id 0\\Logical Unit Id 0", "Identifier").ToUpper().Contains("QEMU") || SoapNcName("HARDWARE\\Description\\System", "SystemBiosVersion").ToUpper().Contains("QEMU"))
            {
                return true;
            }
            foreach (ManagementBaseObject managementBaseObject in new ManagementObjectSearcher(new ManagementScope("\\\\.\\ROOT\\cimv2"), new ObjectQuery("SELECT * FROM Win32_VideoController")).Get())
            {
                ManagementObject managementObject = (ManagementObject)managementBaseObject;
                if (managementObject["Description"].ToString() == "VM Additions S3 Trio32/64" || managementObject["Description"].ToString() == "S3 Trio32/64" || managementObject["Description"].ToString() == "VirtualBox Graphics Adapter" || managementObject["Description"].ToString() == "VMware SVGA II" || managementObject["Description"].ToString().ToUpper().Contains("VMWARE") || managementObject["Description"].ToString() == "")
                {
                    return true;
                }
            }
            return false;
        }
    }
}
