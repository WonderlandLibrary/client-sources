using System;
using System.Linq;
using System.Threading;
using MyeGhost;

namespace MCReach
{
	// Token: 0x02000002 RID: 2
	internal class Program
	{
		// Token: 0x06000002 RID: 2 RVA: 0x00002058 File Offset: 0x00000258
		private static void Main(string[] args)
		{
			DotNetScanMemory_SmoLL dotNetScanMemory_SmoLL = new DotNetScanMemory_SmoLL();
			Console.Title = "";
			Console.WriteLine("\n");
			Console.WriteLine("                                          |              |    ");
			Thread.Sleep(160);
			Console.WriteLine("                  ,-.-.,   .,---.    ,---.|---.,---.,---.|--- ");
			Thread.Sleep(160);
			Console.WriteLine("                  | | ||   ||---'    |   ||   ||   |`---.|    ");
			Thread.Sleep(160);
			Console.WriteLine("                  ` ' '`---|`---'    `---|`   '`---'`---'`---'");
			Thread.Sleep(160);
			Console.WriteLine("                       `---'         `---'                    ");
			Thread.Sleep(160);
			Console.WriteLine("\n");
			double num = 3.0;
			double num2 = 0.0;
			Console.ForegroundColor = ConsoleColor.Green;
			Console.Write("TOS: ");
			Console.ForegroundColor = ConsoleColor.White;
			Console.WriteLine("You agree the TOS? [yes/no]");
			string a = Console.ReadLine();
			Console.ForegroundColor = ConsoleColor.Green;
			Console.Write("TOS: ");
			Console.ForegroundColor = ConsoleColor.White;
			Console.WriteLine("confirm, say: i agree");
			string a2 = Console.ReadLine();
			if (a == "yes" && a2 == "i agree")
			{
				VAMemory vamemory = new VAMemory("javaw");
				if (!vamemory.CheckProcess())
				{
					Console.ForegroundColor = ConsoleColor.DarkRed;
					Console.Write("[!]");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine(" - javaw doens't found!");
					Console.ReadKey();
					return;
				}
				Console.ForegroundColor = ConsoleColor.Green;
				Thread.Sleep(160);
				Console.WriteLine("[!] thank's for agree the TOS");
				Console.ForegroundColor = ConsoleColor.Magenta;
				Thread.Sleep(300);
				Console.Clear();
				Console.WriteLine("\n");
				Console.WriteLine("                                          |              |    ");
				Thread.Sleep(160);
				Console.WriteLine("                  ,-.-.,   .,---.    ,---.|---.,---.,---.|--- ");
				Thread.Sleep(160);
				Console.WriteLine("                  | | ||   ||---'    |   ||   ||   |`---.|    ");
				Thread.Sleep(160);
				Console.WriteLine("                  ` ' '`---|`---'    `---|`   '`---'`---'`---'");
				Thread.Sleep(160);
				Console.WriteLine("                       `---'         `---'                    ");
				Thread.Sleep(160);
				Console.WriteLine("\n");
				Console.ForegroundColor = ConsoleColor.White;
				Console.WriteLine(" - [1] Reach & AutoClicker");
				Thread.Sleep(160);
				Console.WriteLine(" - [2] Reach");
				Thread.Sleep(160);
				Console.WriteLine(" - [3] AutoClicker");
				Thread.Sleep(160);
				Console.WriteLine("info: Reach is blatant - to select use one number");
				string text = Console.ReadLine();
				if (text.Equals("1"))
				{
					Console.ForegroundColor = ConsoleColor.Magenta;
					Console.Write("[+]");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine(" - Reach:");
					try
					{
						num2 = Convert.ToDouble(Console.ReadLine());
					}
					catch
					{
						Console.ForegroundColor = ConsoleColor.DarkRed;
						Console.Write("[-]");
						Console.ForegroundColor = ConsoleColor.White;
						Console.WriteLine(" - invalid value.");
						return;
					}
					Console.WriteLine("info: injecting...");
					Program.lista = dotNetScanMemory_SmoLL.ScanArray(dotNetScanMemory_SmoLL.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
					for (int i = 0; i < Program.lista.Count<IntPtr>(); i++)
					{
						if (vamemory.ReadDouble(Program.lista[i]) == num)
						{
							vamemory.WriteDouble(Program.lista[i], num2);
						}
					}
					Console.WriteLine("info: done :D");
					Console.ForegroundColor = ConsoleColor.Green;
					Console.Write("[!]");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine(" - Reach Enabled: " + num2.ToString());
					Thread.Sleep(1500);
					Console.Write("[-");
					Thread.Sleep(550);
					Console.Write("-");
					Thread.Sleep(550);
					Console.Write("-");
					Thread.Sleep(550);
					Console.Write("-");
					Thread.Sleep(550);
					Console.Write("-");
					Thread.Sleep(550);
					Console.Write("-");
					Thread.Sleep(550);
					Console.Write("-");
					Thread.Sleep(550);
					Console.Write("-]\n");
					Console.WriteLine("\n");
					Console.Clear();
					Console.ForegroundColor = ConsoleColor.Magenta;
					Console.WriteLine("\n");
					Console.WriteLine("                                          |              |    ");
					Thread.Sleep(160);
					Console.WriteLine("                  ,-.-.,   .,---.    ,---.|---.,---.,---.|--- ");
					Thread.Sleep(160);
					Console.WriteLine("                  | | ||   ||---'    |   ||   ||   |`---.|    ");
					Thread.Sleep(160);
					Console.WriteLine("                  ` ' '`---|`---'    `---|`   '`---'`---'`---'");
					Thread.Sleep(160);
					Console.WriteLine("                       `---'         `---'                    ");
					Thread.Sleep(160);
					Console.WriteLine("\n");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine("Clicker: 12 cps");
					Console.WriteLine("Reach: " + num2.ToString());
					Console.WriteLine("Clicker Key: F4");
					new clicker().ShowDialog();
					Console.ForegroundColor = ConsoleColor.DarkGreen;
				}
				else if (text.Equals("2"))
				{
					Console.ForegroundColor = ConsoleColor.Green;
					Console.Write("[+]");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine(" - Reach:");
					try
					{
						num2 = Convert.ToDouble(Console.ReadLine());
					}
					catch
					{
						Console.ForegroundColor = ConsoleColor.DarkRed;
						Console.Write("[-]");
						Console.ForegroundColor = ConsoleColor.White;
						Console.WriteLine(" - invalid value.");
						return;
					}
					Console.WriteLine("injecting...");
					Program.lista = dotNetScanMemory_SmoLL.ScanArray(dotNetScanMemory_SmoLL.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
					for (int j = 0; j < Program.lista.Count<IntPtr>(); j++)
					{
						if (vamemory.ReadDouble(Program.lista[j]) == num)
						{
							vamemory.WriteDouble(Program.lista[j], num2);
						}
					}
					Console.WriteLine("log: injected");
					Thread.Sleep(1000);
					Console.Clear();
					Console.ForegroundColor = ConsoleColor.Magenta;
					Console.WriteLine("\n");
					Console.WriteLine("                                          |              |    ");
					Thread.Sleep(160);
					Console.WriteLine("                  ,-.-.,   .,---.    ,---.|---.,---.,---.|--- ");
					Thread.Sleep(160);
					Console.WriteLine("                  | | ||   ||---'    |   ||   ||   |`---.|    ");
					Thread.Sleep(160);
					Console.WriteLine("                  ` ' '`---|`---'    `---|`   '`---'`---'`---'");
					Thread.Sleep(160);
					Console.WriteLine("                       `---'         `---'                    ");
					Thread.Sleep(160);
					Console.WriteLine("\n");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine("Reach:" + num2.ToString());
				}
				else if (text.Equals("3"))
				{
					Console.Clear();
					Console.WriteLine("\n");
					Console.WriteLine("                                          |              |    ");
					Thread.Sleep(160);
					Console.WriteLine("                  ,-.-.,   .,---.    ,---.|---.,---.,---.|--- ");
					Thread.Sleep(160);
					Console.WriteLine("                  | | ||   ||---'    |   ||   ||   |`---.|    ");
					Thread.Sleep(160);
					Console.WriteLine("                  ` ' '`---|`---'    `---|`   '`---'`---'`---'");
					Thread.Sleep(160);
					Console.WriteLine("                       `---'         `---'                    ");
					Thread.Sleep(160);
					Console.WriteLine("\n");
					Console.WriteLine("Clicker: 12 cps");
					Thread.Sleep(160);
					Console.WriteLine("Key: F4");
					new clicker().ShowDialog();
				}
				else
				{
					Console.ForegroundColor = ConsoleColor.DarkRed;
					Console.Write("[!]");
					Console.ForegroundColor = ConsoleColor.White;
					Console.WriteLine(" - authentication Error.");
				}
				Console.Read();
			}
		}

		// Token: 0x04000001 RID: 1
		private static IntPtr[] lista;
	}
}
