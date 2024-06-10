using System;
using System.Diagnostics;
using System.Linq;

namespace MCReach
{
    class Program
    {
        static IntPtr[] lista;
        static IntPtr[] lista2;
        static void Main(string[] args)
        {
            DotNetScanMemory_SmoLL dot = new DotNetScanMemory_SmoLL();
            Console.Title = "WESSEX CLIENT 0.1";
            Console.WriteLine("\n");
            Console.WriteLine("▒█░░▒█ ▒█▀▀▀█ ▀▄▒▄▀ 　 ▒█▀▀█ ▒█░░░ ▀█▀ ▒█▀▀▀ ▒█▄░▒█ ▀▀█▀▀ ");
            Console.WriteLine("▒█▒█▒█ ░▀▀▀▄▄ ░▒█░░ 　 ▒█░░░ ▒█░░░ ▒█░ ▒█▀▀▀ ▒█▒█▒█ ░▒█░░ ");
            Console.WriteLine("▒█▄▀▄█ ▒█▄▄▄█ ▄▀▒▀▄ 　 ▒█▄▄█ ▒█▄▄█ ▄█▄ ▒█▄▄▄ ▒█░░▀█ ░▒█░░ ");
            Console.WriteLine("\n");

            double legitreach = 3.0;
            double valuereach = 0.0;
            double legitkb = 8000;

            double valuekb = 0.0;
            String name = "";
            String password = "";
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write("[SECURITY]");
            Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine(" - User");
            name = Console.ReadLine();
            Console.ForegroundColor = ConsoleColor.Green;
            Console.Write("[SECURITY]");
            Console.ForegroundColor = ConsoleColor.White;
            Console.WriteLine(" - Password");
            password = Console.ReadLine();
            if ((name == "sev7n") && password == "buythatplease")
            {
                VAMemory v = new VAMemory("javaw");
                String who = "";
                if (!v.CheckProcess())
                {
                    Console.ForegroundColor = ConsoleColor.DarkRed;
                    Console.Write("[!]");
                    Console.ForegroundColor = ConsoleColor.White;
                    Console.WriteLine(" - javaw doens't found!");
                    Console.ReadKey();
                }
                else
                {
                    Console.ForegroundColor = ConsoleColor.Green;
                    Console.Write("[?]");
                    Console.ForegroundColor = ConsoleColor.White;
                    Console.WriteLine(" - Who you want to change?");
                    who = Console.ReadLine();
                    if (who.Equals("reach"))
                    {
                        Console.ForegroundColor = ConsoleColor.Green;
                        Console.Write("[!]");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.WriteLine(" - Reach:");
                        try
                        {
                            valuereach = Convert.ToDouble(Console.ReadLine());
                        }
                        catch
                        {
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.Write("[!]");
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.WriteLine(" - Wrong Value.");
                            return;
                        }
                        lista = dot.ScanArray(dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
                        for (int i = 0; i < lista.Count<IntPtr>(); i++)
                        {
                            double x = v.ReadDouble(lista[i]);
                            if (x == legitreach)
                            {
                                v.WriteDouble(lista[i], valuereach);
                            }
                        }
                        Console.ForegroundColor = ConsoleColor.Green;
                        Console.Write("[!]");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.WriteLine(" - Reach has been defined to: " + valuereach);
                        Console.ForegroundColor = ConsoleColor.DarkGreen;
                        Console.Write("[!]");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.WriteLine(" - Press 'ENTER' to self-destruct and clear strings!");
                        Console.ReadKey();
                        string strB = string.Format("{0}\\{1}", Environment.GetEnvironmentVariable("WINDIR"), "explorer.exe");
                        foreach (Process process in Process.GetProcesses())
                        {
                            try
                            {
                                bool flag = string.Compare(process.MainModule.FileName, strB, StringComparison.OrdinalIgnoreCase) == 0;
                                if (flag)
                                {
                                    process.Kill();
                                }
                            }
                            catch
                            {
                            }
                        }
                        Process.Start("explorer.exe");
                    }

                    else if (who.Equals("velocity"))
                    {

                        Console.WriteLine("Velocity:");
                        try
                        {
                            valuekb = Convert.ToDouble(Console.ReadLine());
                        }
                        catch
                        {
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.Write("[!]");
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.WriteLine(" - Wrong Value.");
                            return;
                        }
                        lista2 = dot.ScanArray(dot.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
                        for (int i = 0; i < lista2.Count<IntPtr>(); i++)
                        {
                            double x = v.ReadDouble(lista[i]);
                            if (x == legitkb)
                            {
                                v.WriteDouble(lista[i], valuekb);
                            }
                        }
                    }
                    else
                    {
                        Console.ForegroundColor = ConsoleColor.DarkRed;
                        Console.Write("[!]");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.WriteLine(" - authentication Error.");
                        Console.WriteLine("Its an error?");
                        Console.WriteLine("Call-me on discord Young Dagger Dick#5340");
                    }
                    Console.Read();
                }
            }
        }
    }
}
