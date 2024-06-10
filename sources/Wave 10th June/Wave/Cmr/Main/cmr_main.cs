using System;
using Wave.Cmr.Font;

namespace Wave.Cmr.Main
{
	// Token: 0x02000023 RID: 35
	public class cmr_main
	{
		// Token: 0x0600010E RID: 270 RVA: 0x00003F21 File Offset: 0x00002121
		public static void CMR_MAIN(string[] args)
		{
			Console.Title = "CMRConsole";
			Console.BackgroundColor = ConsoleColor.Black;
			Console.Clear();
			cmr.EnableVirtualTerminalProcessing();
		}

		// Token: 0x0600010F RID: 271 RVA: 0x00003F40 File Offset: 0x00002140
		public static void StartScreen()
		{
			cmr_font.SetConsoleFont("Consolas", 9, 19, 0);
			cmr.CenterConsole();
			cmr.MaximizeConsole();
			Console.Write(cmr.cb(70, 70, 70) + cmr.cf(70, 70, 70));
			Console.Write("######################################################################################\n");
			Console.Write(cmr.cf(198, 148, 255));
			Console.Write(" Welcome to the                                                                       \n");
			Console.Write(cmr.cf(70, 70, 70));
			Console.Write("######################################################################################\n");
			Console.Write(cmr.cf(148, 209, 255));
			Console.Write("                          ██████████  ██        ██  ████████                          \n");
			Console.Write("                          ██          ████    ████  ██    ██                          \n");
			Console.Write("                          ██          ██  ████  ██  ████████                          \n");
			Console.Write("                          ██          ██        ██  ████                              \n");
			Console.Write("                          ██          ██        ██  ██  ██                            \n");
			Console.Write("                          ██████████  ██        ██  ██    ██                          \n");
			Console.Write(cmr.cf(70, 70, 70));
			Console.Write("######################################################################################\n");
			Console.Write(cmr.cf(255, 228, 138));
			Console.Write(" ██████████  ██████████  ██        ██  ██████████  ██████████  ██          ██████████ \n");
			Console.Write(" ██          ██      ██  ████      ██  ██          ██      ██  ██          ██         \n");
			Console.Write(" ██          ██      ██  ██  ██    ██  ██████████  ██      ██  ██          ██████████ \n");
			Console.Write(" ██          ██      ██  ██    ██  ██          ██  ██      ██  ██          ██         \n");
			Console.Write(" ██          ██      ██  ██      ████          ██  ██      ██  ██          ██         \n");
			Console.Write(" ██████████  ██████████  ██        ██  ██████████  ██████████  ██████████  ██████████ \n");
			Console.Write(cmr.cf(70, 70, 70));
			Console.Write("######################################################################################\n");
			Console.Write("######################################################################################\n");
			Console.Write("######################################################################################\n");
			Console.Write(cmr.cr);
			Console.ReadKey();
		}
	}
}
