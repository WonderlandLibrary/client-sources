using System;
using System.Linq;
using System.Threading;

namespace MCReach
{
	// Token: 0x02000003 RID: 3
	internal class Program
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002390 File Offset: 0x00000590
		private static void Main(string[] args)
		{
			if (Class2.smethod_7(0))
			{
				Console.Title = "";
				Console.ForegroundColor = ConsoleColor.Yellow;
				Console.WriteLine("                           Coded By CheatyBlack#0001");
				Console.WriteLine("");
				Console.ForegroundColor = ConsoleColor.DarkRed;
				DotNetScanMemory_SmoLL dotNetScanMemory_SmoLL = new DotNetScanMemory_SmoLL();
				Thread.Sleep(500);
				Console.WriteLine("Value Reach (3.0 - 8.0) :");
				string text = BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(Console.ReadLine()))).Replace("-", " ");
				Program.lista = dotNetScanMemory_SmoLL.ScanArray(dotNetScanMemory_SmoLL.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
				for (int i = 0; i < Program.lista.Count<IntPtr>(); i++)
				{
					dotNetScanMemory_SmoLL.WriteArray(Program.lista[i], text);
				}
				Console.WriteLine("Reach !");
				Console.Clear();
				Console.ForegroundColor = ConsoleColor.Yellow;
				Console.WriteLine("                           Coded By CheatyBlack#0001");
				Console.ForegroundColor = ConsoleColor.DarkRed;
				double num = 8000.0;
				double num2 = 0.0;
				Thread.Sleep(500);
				Console.WriteLine("Velocity Value :");
				try
				{
					num2 = Convert.ToDouble(Console.ReadLine());
				}
				catch
				{
					Console.WriteLine("Wrong Value.");
					return;
				}
				VAMemory vamemory = new VAMemory("javaw");
				Program.lista = dotNetScanMemory_SmoLL.ScanArray(dotNetScanMemory_SmoLL.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
				for (int j = 0; j < Program.lista.Count<IntPtr>(); j++)
				{
					double num3 = vamemory.ReadDouble(Program.lista[j]);
					if (num3 == num)
					{
						vamemory.WriteDouble(Program.lista[j], num2);
					}
				}
			}
		}

		// Token: 0x06000002 RID: 2 RVA: 0x000025F8 File Offset: 0x000007F8
		public Program()
		{

		}

		// Token: 0x06000003 RID: 3 RVA: 0x0000223C File Offset: 0x0000043C
		static Program()
		{

		}

	}
}
