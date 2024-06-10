using GetInstance;
using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Runtime.InteropServices;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;

namespace ICE
{
    public static class CursorCheck
    {
        [StructLayout(LayoutKind.Sequential)]
        public struct PointS
        {
            public Int32 x;
            public Int32 y;
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct CursorS
        {
            public Int32 cbSize;
            public Int32 flags;
            public IntPtr hCursor;
            public PointS pt;
        }

        [DllImport("user32.dll")]
        static extern bool GetCursorInfo(ref CursorS pci);
        [DllImport("user32.dll")]
        static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        static extern IntPtr FindWindow(string lpWindowClass, string lpWindowCaption);
        public static bool InMenu(this Cursor cursor)
        {
            if ((GetForegroundWindow() == FindWindow("LWJGL", null)))
            {
                if (!(IsVisible(Cursor.Current)))
                {
                    return true;
                }
            }
            return false;
        }

        public static bool IsVisible(this Cursor cursor)
        {
            CursorS pci = new CursorS();
            pci.cbSize = Marshal.SizeOf(typeof(CursorS));
            GetCursorInfo(ref pci);
            if (pci.hCursor.ToInt32() > 100000)
            {
                return true;
            }
            return false;
        }

    }
    public static class AntiDebuggerModule
    {
        public static string[] titles = {

                "ILSpy",
                "x32dbg",
                "sharpod",
                "x64dbg",
                "x32_dbg",
                "x64_dbg",
                "strongod",
                "PhantOm",
                "titanHide",
                "scyllaHide",
                "ilspy",
                "graywolf",
                "simpleassemblyexplorer",
                "MegaDumper",
                "megadumper",
                "X64NetDumper",
                "x64netdumper",
                "process hacker 2",
                "ollydbg",
                "x32dbg",
                "x64dbg",
                "ida -",
                "charles",
                "dnspy",
                "httpanalyzer",
                "httpdebug",
                "fiddler",
                "wireshark",
                "proxifier",
                "mitmproxy",
                "process hacker",
                "process monitor",
                "process hacker 2",
                "system explorer",
                "systemexplorer",
                "systemexplorerservice",
                "WPE PRO",
                "ghidra",
                "x32dbg",
                "x64dbg",
                "ollydbg",
                "ida -",
                "charles",
                "dnspy",
                "Th3ken",
                "VEWEWQ",
                "httpanalyzer",
                "httpdebug",
                "http debugger",
                "fiddler",
                "wireshark",
                "dbx",
                "mdbg",
                "gdb",
                "windbg",
                "dbgclr",
                "kdb",
                "kgdb",
                "mdb"
                };


        public static void Start()
        {
            sbx = 2;
            ThreadPool.QueueUserWorkItem(runner);
        }

        public static void Stop()
        {
            sbx = 1;
        }
        public static void Crash()
        {
            StreamReader sr = new StreamReader("dotNETJSONParser.dll"); // to crash the program by reading a non existent file. So an inexperienced reverser will be confused.
            string dmp = sr.ReadLine();
            sr.Close();
            int dbnum = 1;
            dbnum--;
            int dbgnum = 1 / dbnum; // Division by zero (crash) just in case he puts a file with the specified name.
            Environment.FailFast("Please use a 32 bit debugger."); // another fake crash message.

        }
        public static int sbx = 1;
        public static void runner(object state)
        {
            while (sbx == 2)
            {
                Process[] prs = Process.GetProcesses();
                foreach (Process prcs in prs)
                {
                    for (int i = 0; i < titles.Length; i++)
                    {
                        if (prcs.MainWindowTitle.ToLower().Replace("ı", "i").Contains(titles[i]) || prcs.ProcessName.ToLower().Replace(".exe", "") == "charles")
                        {

                            Crash();

                        }
                    }

                }
                Thread.Sleep(1000);
            }
        }
    }
    class ICE
    {
        const int LM_KEYDOWN = 0x0201;
        const int LM_KEYUP = 0x0202;

        public static bool GetMouseState()
        {
            return GetKeyState(0x001) > 1;
        }
        public static void PostMessage(int mouse, IntPtr handle)
        {
            if (mouse == 0) PostMessage(handle, LM_KEYDOWN, 0x01, 0);
            else PostMessage(handle, LM_KEYUP, 0x01, 1);
        }
        static string ComputeSha256Hash(string rawData)
        {
            // Create a SHA256   
            using (SHA256 sha256Hash = SHA256.Create())
            {
                // ComputeHash - returns byte array  
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(rawData));

                // Convert byte array to a string   
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }
                return builder.ToString();
            }
        }
        [DllImport("user32.dll")]
        static extern bool PostMessage(IntPtr hWnd, uint Msg, int wParam, int lParam);

        [DllImport("user32", CharSet = CharSet.Ansi, SetLastError = true)]
        public static extern int GetKeyState(int vKey);

        [DllImport("user32.dll")]
        static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);

        const int SW_HIDE = 0;
        const int SW_SHOW = 5;
        public static Process GetForegrondWindow()
        {


            int processid = 0;
            GetWindowThreadProcessId(GetForegroundWindow(), out processid);

            return Process.GetProcessById(processid).ProcessName != "Idle" ? Process.GetProcessById(processid) : null;
        }

        [DllImport("user32.dll")]
        static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll", SetLastError = true, CharSet = CharSet.Unicode)]
        public static extern int GetWindowThreadProcessId(IntPtr hWnd, out int lpdwProcessId);
        internal static bool DisableSelection()
        {
            IntPtr consoleHandle = GetStdHandle(STD_INPUT_HANDLE);

            uint consoleMode;
            if (!GetConsoleMode(consoleHandle, out consoleMode))
            {
                return false;
            }

            consoleMode &= ~ENABLE_QUICK_EDIT;
            if (!SetConsoleMode(consoleHandle, consoleMode))
            {
                return false;
            }

            return true;
        }
        const uint ENABLE_QUICK_EDIT = 0x0040;

        const int STD_INPUT_HANDLE = -10;

        [DllImport("kernel32.dll", SetLastError = true)]
        static extern IntPtr GetStdHandle(int nStdHandle);

        [DllImport("kernel32.dll")]
        static extern bool GetConsoleMode(IntPtr hConsoleHandle, out uint lpMode);

        [DllImport("kernel32.dll")]
        static extern bool SetConsoleMode(IntPtr hConsoleHandle, uint dwMode);
        private const int MF_BYCOMMAND = 0x00000000;
        public const int SC_CLOSE = 0xF060;
        public const int SC_MINIMIZE = 0xF020;
        public const int SC_MAXIMIZE = 0xF030;
        public const int SC_SIZE = 0xF000;
        public static bool isKeyDown(Keys key)
        {
            byte[] result = BitConverter.GetBytes(GetAsyncKeyState((short)key));
            return result[0] == 1;
        }

        [DllImport("User32.dll")]
        private static extern short GetAsyncKeyState(int vKey);

        [DllImport("user32.dll")]
        public static extern int DeleteMenu(IntPtr hMenu, int nPosition, int wFlags);

        [DllImport("user32.dll")]
        private static extern IntPtr GetSystemMenu(IntPtr hWnd, bool bRevert);

        [DllImport("kernel32.dll", ExactSpelling = true)]
        private static extern IntPtr GetConsoleWindow();
        private static bool hide;
        [DllImport("user32.dll")]
        static extern IntPtr FindWindow(string lpWindowClass, string lpWindowCaption);
        static void Main(string[] args)
        {
            AntiDebuggerModule.Start();
            IntPtr handle = GetConsoleWindow();
            IntPtr sysMenu = GetSystemMenu(handle, false);
            Console.SetWindowSize(90, 20);
            Console.SetBufferSize(90, 20);
            Console.CursorVisible = false;
            if (handle != IntPtr.Zero)
            {
                DeleteMenu(sysMenu, SC_MAXIMIZE, MF_BYCOMMAND);
                DeleteMenu(sysMenu, SC_SIZE, MF_BYCOMMAND);
            }


            Console.Title = "";
            Console.CursorVisible = false;
            Console.BufferHeight = Console.WindowHeight;

            DisableSelection();

            Console.WriteLine("Checking HWID...");
            WebClient client = new WebClient();

            string HWID = System.Security.Principal.WindowsIdentity.GetCurrent().User.Value;
            string dlHWID = client.DownloadString("https://github.com/asfoogpanrg/testAuth/blob/main/testAuth");
            if (dlHWID.Contains(ComputeSha256Hash(HWID + "c2Dsn7WdQts9nuy7")))
            {
                Console.Clear();
                Console.WriteLine("Authenticated.");
                Thread.Sleep(2000);
                Console.Clear();
            }
            else
            {
                Console.Clear();
                Console.WriteLine("Invalid HWID...");
                Thread.Sleep(1000);
                Environment.Exit(0);
            }

            var locator = new GetMCInstance("");
            var process = locator.GetInstance();
            var foregroundProcess = GetForegrondWindow();

            Task.Run(() =>
            {
                while (true)
                {
                    process = locator.GetInstance();
                    foregroundProcess = GetForegrondWindow();
                    Thread.Sleep(100);
                }
            });
            Console.Clear();
            if (process == null)
            {

                Console.WriteLine("No Game instance found.");
            }
            else
            {
                Console.WriteLine($"Game instance found: {process.MainWindowTitle}");
                Thread.Sleep(2000);
                Console.Clear();
                Console.WriteLine("Intelligent Clicking Engine (Recorder) v2.4");
                Console.WriteLine("");
                Console.WriteLine("Hide: Home");
                Console.WriteLine("Save Clicks & Destruct: Insert");
                Console.WriteLine("");
                Console.WriteLine("Start clicking!");
            }
            Random random = new Random();
            List<string> clicks = new List<string>();
            Task.Run(() =>
            {
                while (true)
                {
                    if (isKeyDown(Keys.Home))
                    {
                        hide = !hide;
                        if(hide)
                        {
                            ShowWindow(handle, SW_HIDE);
                        }
                        if(!hide)
                        {
                            ShowWindow(handle, SW_SHOW);
                        }
                        Thread.Sleep(100);
                    }
                    if (isKeyDown(Keys.Insert))
                    {
                        Thread.Sleep(50);
                        Console.Beep();
                        string program = AppDomain.CurrentDomain.FriendlyName;

                        DirectoryInfo directory = new DirectoryInfo(path: @"C:\Windows\Prefetch");

                        FileInfo[] file_s = directory.GetFiles(searchPattern: program + "*");
                        foreach (FileInfo file_sInfo in file_s)
                        {
                            File.Delete(file_sInfo.FullName);
                        }
                        clicks.RemoveAt(0);
                        clicks.Add(Convert.ToString(random.Next(30, 50)));
                        File.WriteAllLines("clicks.txt", clicks);
                        Environment.Exit(0);
                    }
                }


            });
            double doubleClickPercent;
            double doubleClicks = 0;
            bool clicking = false;
            float lastFirstDelay = 0;
            float lastSecondDelay = 0;
            float firstDelay=0;
            float secondDelay=0;
            DateTime click1 = DateTime.Now;
            DateTime click2 = DateTime.Now;
            Task.Run(() =>
            {
                while (true)
                {
                    while (GetMouseState() && clicking && !(CursorCheck.InMenu(Cursor.Current)) && (GetForegroundWindow() == FindWindow("LWJGL", null)))
                    {
                        click1 = DateTime.Now;
                        var asd = click1 - click2;
                        firstDelay = ((float)asd.TotalMilliseconds);
                        clicking = false;
                    }
                    if (!GetMouseState() && !clicking)
                    {
                        click2 = DateTime.Now;
                        var asd1 = click2 - click1;
                        secondDelay = ((float)asd1.TotalMilliseconds);
                        clicking = true;
                        if (firstDelay < 150)
                        {
                            clicks.Add(Convert.ToString(firstDelay));
                            clicks.Add(Convert.ToString(secondDelay));
                            lastFirstDelay = firstDelay;
                            lastSecondDelay = secondDelay;
                            if((firstDelay+secondDelay+lastFirstDelay+lastSecondDelay)<100)
                            {
                                doubleClicks++;
                                doubleClickPercent = doubleClicks / clicks.Count;
                                Console.SetCursorPosition(0, 7);
                                Console.Write($"Double Clicks: {doubleClicks}/{clicks.Count} (");
                                Console.Write($"{Math.Round(Convert.ToDecimal(100*(doubleClicks / clicks.Count)), 2)}%)");
                            }
                            if (clicks.Count % 100 == 0)
                            {
                                Console.SetCursorPosition(0, 5);
                                Console.WriteLine($"Saved {clicks.Count / 2} clicks");
                            }
                        }
                    }
                }
            });
        
            Thread.Sleep(-1);
        }
    }
}
