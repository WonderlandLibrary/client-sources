using System;
using System.Linq;

namespace MCReach
{
	// Token: 0x02000002 RID: 2
	internal class Program
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002050 File Offset: 0x00000250
		private static void Main(string[] args)
		{
			DotNetScanMemory_SmoLL dotNetScanMemory_SmoLL = new DotNetScanMemory_SmoLL();
			Console.Title = "Vesperr B1.0";
			Console.ForegroundColor = ConsoleColor.Blue;
			Console.WriteLine("  _    __                              ");
			Console.WriteLine(" | |  / /__   _________  ___  __________");
			Console.WriteLine(" | | / / _ | / ___/ __ |/ _ |/ ___/ ___/");
			Console.WriteLine(" | |/ /  __/(__  ) /_/ /  __/ /  / /    ");
			Console.WriteLine(" |___/|___/ ____/ ____/|___/_/  /_/     ");
			Console.WriteLine("               /_/                   B1 ");
			Console.WriteLine("                               By Irres ");
			Console.WriteLine("                                        ");
			Console.WriteLine("Reach :                                 ");
			string arrayString = BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(Console.ReadLine()))).Replace("-", " ");
			Program.lista = dotNetScanMemory_SmoLL.ScanArray(dotNetScanMemory_SmoLL.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
			for (int i = 0; i < Program.lista.Count<IntPtr>(); i++)
			{
				dotNetScanMemory_SmoLL.WriteArray(Program.lista[i], arrayString);
			}
			Console.WriteLine("Sucessyfully injected!");
			Console.ReadLine();
		}

		// Token: 0x04000001 RID: 1
		private static IntPtr[] lista;
	}
}
