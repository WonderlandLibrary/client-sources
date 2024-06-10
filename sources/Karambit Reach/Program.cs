using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using Memory;
using System.Threading;

namespace asdasd
{
    class Program
    {
        [DllImport("kernel32.dll")]
        public static extern IntPtr OpenProcess(int dwDesiredAccess, bool bInheritHandle, int dwProcessId);
        [DllImport("kernel32", SetLastError = true)]
        public static extern int ReadProcessMemory(IntPtr hProcess, UInt64 lpBase, ref UInt64 lpBuffer, int nSize, int lpNumberOfBytesRead);
        [DllImport("kernel32", SetLastError = true)]
        public static extern int WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, ref IntPtr lpBuffer, int nSize, int lpNumberOfBytesWritten);

        public static IntPtr[] lista;
        public static ProcessModule mcMainModule;
        public static IntPtr mcBaseAddress;
        public static Mem memory = new Mem();

        static void Main(string[] args)
        {


            //Process[] minecraft = Process.GetProcessesByName("Minecraft.Windows");
            int pid = memory.GetProcIdFromName("Minecraft.Windows");
            //Process mcpe = minecraft[0];
            /*IntPtr proc = OpenProcess(0x1BA4EB0, false, mcpe.Id);
            ProcessModule mainmodule = mcpe.MainModule;
            IntPtr baseaddress = mcMainModule.BaseAddress;*/

            if (pid > 0 /*minecraft.Length > 0*/)
            {

                Console.Title = "";
                Console.ForegroundColor = ConsoleColor.DarkMagenta;
                Console.WriteLine(" ");
                Console.WriteLine(" ");
                Console.WriteLine(" ");
                Console.WriteLine("                                           __  ,                              ");
                Console.WriteLine("                                          ( /,/                        / o _/_");
                Console.WriteLine("                                           /<   __,  _   __,  _ _ _   / ,  /  ");
                Console.WriteLine("                                          /  \\_(_/(_/ (_(_/(_/ / / /_/_)(_(__");
                Console.WriteLine(" ");
                Console.WriteLine("                                                                                     v1.0");
                Console.WriteLine(" ");
                Console.WriteLine(" ");
                Console.WriteLine(" ");

                memory.OpenProcess(pid);
                Thread threadreach = new Thread(setreach) { IsBackground = true };
                threadreach.Start();

                //Console.Write("                                                         (Default: 3) Reach: ");
                /*VAMemory memory = new VAMemory("Minecraft.Windows");
                int reachaddress = memory.ReadInt32((IntPtr)baseaddress + 0x1BA4EB0);
                //int reach = memory.ReadInt32((IntPtr)reachaddress + );

                while (true)
                {
                    memory.WriteInt32((IntPtr)reachaddress, 7);
                }*/
                /*string reachvalue = Console.ReadLine();
                Console.Write("                                                     (Default: 1) Velocity: ");
                string velocityvalue = Console.ReadLine();
                Console.Write("                                                     (Default: 1) Timer: ");
                string timervalue = Console.ReadLine();*/

            }
        }
        public static void setreach()
        {
            while (true)
            {
                memory.WriteMemory("Minecraft.Windows.exe+1BA4EB0", "int", "7");
            }
        }
    }
}
