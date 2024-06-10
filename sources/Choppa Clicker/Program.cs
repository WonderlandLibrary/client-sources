using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Diagnostics;
using System.Management;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Runtime.InteropServices;

namespace choppaClicker
{
   public partial class Program : Form
    {

        [DllImport("kernel32.dll")]
        static extern IntPtr GetConsoleWindow();


        [DllImport("user32.dll")]
        static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);


        const int SW_HIDE = 0;
        const int SW_SHOW = 5;

        public Program()
        {
            InitializeComponent();
        }

        static void Main(string[] args)
        {

            IntPtr hWnd = GetConsoleWindow();

            LoggedIN();
            ShowWindow(hWnd, 0);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form2());


            var anydesk = "anydesk";
            var target = Process.GetProcessesByName(anydesk).FirstOrDefault();

            if (target == null)
            {
                Console.Title = " ";

                WebClient client = new WebClient();

                string versionO = client.DownloadString("https://pastebin.com/raw/MeyuhXMP");
                string version = "0.5";

                if (versionO.Contains(version))
                {

                    string HWID = System.Security.Principal.WindowsIdentity.GetCurrent().User.Value;

                    string appHWID = client.DownloadString("https://raw.githubusercontent.com/kkksnuqy/xdddlo/master/chid");

                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("- choppaclicker.xyz -");

                    System.Threading.Thread.Sleep(0200);
                    Console.Write("v");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("1");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("  ");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("i");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("n");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("c");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("o");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("m");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("i");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("n");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("g");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write(" ");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("s");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("o");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("o");
                    System.Threading.Thread.Sleep(0200);
                    Console.Write("n");
                    System.Threading.Thread.Sleep(0200);


                    Console.WriteLine("");

                    // Console.WriteLine(updateLog);

                    Console.ForegroundColor = ConsoleColor.White;

                    Console.ForegroundColor = ConsoleColor.White;
                    Console.Write("Authorizing");
                    System.Threading.Thread.Sleep(0700);
                    Console.Write(".");
                    System.Threading.Thread.Sleep(0700);
                    Console.Write(".");
                    System.Threading.Thread.Sleep(0700);
                    Console.Write(".");
                    System.Threading.Thread.Sleep(0700);
                    Console.Clear();

                    Console.ForegroundColor = ConsoleColor.Red;
                    Console.WriteLine("- choppaclicker.xyz -");

                    Console.WriteLine("");

                    Console.ForegroundColor = ConsoleColor.White;
                    Console.WriteLine("Checking HWID");

                    System.Threading.Thread.Sleep(1000);

                    if (appHWID.Contains(HWID))
                    {

                        Console.ForegroundColor = ConsoleColor.Green;
                        Console.WriteLine("Success!");
                        System.Threading.Thread.Sleep(0700);

                        string updateLog = client.DownloadString("https://pastebin.com/raw/naQ9BNTP");
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine(updateLog);

                        Console.WriteLine("");

                        Console.ForegroundColor = ConsoleColor.White;
                        Console.WriteLine("Press any key to continue.");

                        Console.ReadKey();

                        LoggedIN();
                        Application.EnableVisualStyles();
                        Application.SetCompatibleTextRenderingDefault(false);
                        Application.Run(new Form1());

                        ShowWindow(hWnd, 0);
                    }
                    else
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                        Console.WriteLine("Invalid HWID");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.WriteLine("HWID: " + HWID);
                        Console.WriteLine("Please send HWID to staff for access!");
                    }

                    Console.ReadKey();
                }

                else
                {
                    Update();
                }
            }
            else
            {
                MessageBox.Show("ERROR:  ADENSKY", "error");
            }


        } 


    static void Update()
        {
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine("- choppaclicker.xyz -");

            Console.WriteLine(" ");

            Console.ForegroundColor = ConsoleColor.DarkRed;
            Console.WriteLine("Client is out of date! Goto discord to get newest client");
        }

        static void LoggedIN()
        {

            string HWID = System.Security.Principal.WindowsIdentity.GetCurrent().User.Value;

            var handle = GetConsoleWindow();


            // Hide
            ShowWindow(handle, SW_HIDE);

            Console.Clear();
            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine("- choppaclicker.xyz -");

            Console.WriteLine("");

            Console.ForegroundColor = ConsoleColor.White;
            Console.Write("Logged In As ");
            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine("User");

            Console.WriteLine("");

            Console.ForegroundColor = ConsoleColor.Red;
            Console.WriteLine("Do not close out of this window or clicker will close.");
        }

        private void InitializeComponent()
        {
            this.SuspendLayout();
            this.ClientSize = new System.Drawing.Size(498, 382);
            this.Name = "Program";
            this.ResumeLayout(false);
        }

    }
}
