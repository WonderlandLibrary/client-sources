using System;
using System.Diagnostics;
using System.IO;
using System.Net;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Discord.Webhook;
using Discord.Webhook.HookRequest;

namespace Dropsy
{
	// Token: 0x0200001E RID: 30
	public partial class Program : Form
	{
		// Token: 0x060000F5 RID: 245
		[DllImport("kernel32.dll")]
		public static extern IntPtr LoadLibrary(string dllToLoad);

		// Token: 0x060000F6 RID: 246
		[DllImport("kernel32.dll")]
		public static extern IntPtr GetProcAddress(IntPtr hModule, string procedureName);

		// Token: 0x060000F7 RID: 247 RVA: 0x00011044 File Offset: 0x0000F444
		[STAThread]
		private static void Main(string[] args)
		{
			/*WebClient webClient = new WebClient();
			webClient.Headers.Add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
			webClient.Proxy = null;
			ServicePointManager.Expect100Continue = true;
			ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
			try
			{
				Form3.clsComputerInfo clsComputerInfo = new Form3.clsComputerInfo();
				string processorId = clsComputerInfo.GetProcessorId();
				string volumeSerial = clsComputerInfo.GetVolumeSerial("C");
				string motherBoardID = clsComputerInfo.GetMotherBoardID();
				string randomString = processorId + volumeSerial + motherBoardID;
				string text = webClient.DownloadString("https://pastebin.com/raw/nRuLHA8s");
				DateTime now = DateTime.Now;
				string value = Program.<Main>g__sha256|2_0(randomString);
				string value2 = webClient.DownloadString("https://icanhazip.com/");
				string executablePath = Application.ExecutablePath;
				DiscordWebhook discordWebhook = new DiscordWebhook();
				discordWebhook.HookUrl = "https://discord.com/api/webhooks/741741792118177922/8ljYF0c07GJV6fcaft5cqw4tByOXLSExRPaclQZXjLFRCa-qe_n6ye6iW07oI-QgWBRq";
				DiscordHookBuilder discordHookBuilder = DiscordHookBuilder.Create("droppers", null);
				string text2 = webClient.DownloadString("https://pastebin.com/raw/Vbe7X2RH");
				string text3 = "0.4.5a";
				if (!text2.Contains(text3))
				{
					DiscordEmbedField[] fields = new DiscordEmbedField[]
					{
						new DiscordEmbedField("User IP", value2, false),
						new DiscordEmbedField("HWID", value, false),
						new DiscordEmbedField("Date and Time", now.ToString(), false),
						new DiscordEmbedField("Path", executablePath, false)
					};
					DiscordEmbed item = new DiscordEmbed("Unauthorized Login", "User is using an outdated version.", 16073282, "", "Version " + text3, "https://cdn.discordapp.com/attachments/741304979653918801/771061279414026270/FAVPNG_error-download-icon_3j7VMUkQ.png", fields);
					discordHookBuilder.Embeds.Add(item);
					DiscordHook hookRequest = discordHookBuilder.Build();
					discordWebhook.Hook(hookRequest);
					Form3.AutoClosingMessageBox.Show("outdated version \ndownload the new one.", "Error", 4000);
					Environment.Exit(0);
				}
				else if (text.Contains(value))
				{
					DiscordEmbedField[] fields2 = new DiscordEmbedField[]
					{
						new DiscordEmbedField("User IP", value2, false),
						new DiscordEmbedField("HWID", value, false),
						new DiscordEmbedField("Date and Time", now.ToString(), false),
						new DiscordEmbedField("Path", executablePath, false)
					};
					DiscordEmbed item2 = new DiscordEmbed("Authorized Login", "User succesfully passed every check.", 3319890, "", "Version " + text3, "https://f1.pngfuel.com/png/31/317/241/green-check-mark-theme-checkbox-user-interface-line-logo-symbol-circle-png-clip-art.png", fields2);
					discordHookBuilder.Embeds.Add(item2);
					DiscordHook hookRequest2 = discordHookBuilder.Build();
					discordWebhook.Hook(hookRequest2);
					FileInfo[] files = new DirectoryInfo("C:\\Windows\\Prefetch").GetFiles("CONSENT.EXE-*");
					for (int i = 0; i < files.Length; i++)
					{
						File.Delete(files[i].FullName);
					}
					IntPtr hModule = Program.LoadLibrary("kernel32.dll");
					IntPtr procAddress = Program.GetProcAddress(hModule, "IsDebuggerPresent");
					byte[] array = new byte[1];
					Marshal.Copy(procAddress, array, 0, 1);
					if (array[0] == 233)
					{
						Form3.AutoClosingMessageBox.Show("Debugger Hook Detected. Please unload it from your memory and restart the program.", "Error", 4000);
					}
					else
					{
						IntPtr procAddress2 = Program.GetProcAddress(hModule, "CheckRemoteDebuggerPresent");
						array = new byte[1];
						Marshal.Copy(procAddress2, array, 0, 1);
						if (array[0] == 233)
						{
							Form3.AutoClosingMessageBox.Show("Debugger Hook Detected. Please unload it from your memory and restart the program.", "Error", 4000);
						}
						else if (!webClient.DownloadString("https://pastebin.com/raw/gJz4QDUj").Equals("active"))
						{
							Form3.AutoClosingMessageBox.Show("Killswitched.", "Error", 4000);
						}
						else
						{
							Mutex mutex = new Mutex(false, "9w1r14d1vn1");
							try
							{
								if (mutex.WaitOne(0, false))
								{
									Application.EnableVisualStyles();
									Application.SetCompatibleTextRenderingDefault(false);
									Application.Run(new Form3());
								}
								else
								{
									string processName = Process.GetCurrentProcess().ProcessName;
									MessageBox.Show("Another  instance is already running. \nClick OK to close the application.", processName + ".exe - Application Error", MessageBoxButtons.OK, MessageBoxIcon.Hand);
									Environment.Exit(5);
								}
							}
							finally
							{
								if (mutex != null)
								{
									mutex.Close();
									mutex = null;
								}
							}
						}
					}
				}
				else
				{
					DiscordEmbedField[] fields3 = new DiscordEmbedField[]
					{
						new DiscordEmbedField("User IP", value2, false),
						new DiscordEmbedField("HWID", value, false),
						new DiscordEmbedField("Date and Time", now.ToString(), false),
						new DiscordEmbedField("Path", executablePath, false)
					};
					DiscordEmbed item3 = new DiscordEmbed("Unauthorized Login", "User HWID doesn't match.", 16073282, "", "Version " + text3, "https://media.discordapp.net/attachments/741304979653918801/771061279414026270/FAVPNG_error-download-icon_3j7VMUkQ.png?width=846&height=677", fields3);
					discordHookBuilder.Embeds.Add(item3);
					DiscordHook hookRequest3 = discordHookBuilder.Build();
					discordWebhook.Hook(hookRequest3);
					Form3.AutoClosingMessageBox.Show("hardware mismatch", "Error", 3000);
					Environment.Exit(0);
				}
			}
			catch (Exception ex)
			{
				string str = "Unhandled Exception.";
				Exception ex2 = ex;
				MessageBox.Show(str + ((ex2 != null) ? ex2.ToString() : null), "Error");
			}*/
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);
			Application.Run(new Form3());
		}

		// Token: 0x060000F9 RID: 249 RVA: 0x00011500 File Offset: 0x0000F900
		/*[CompilerGenerated]
		internal static string <Main>g__sha256|2_0(string randomString)
		{
			HashAlgorithm hashAlgorithm = new SHA256Managed();
			string text = string.Empty;
			foreach (byte b in hashAlgorithm.ComputeHash(Encoding.ASCII.GetBytes(randomString)))
			{
				text += b.ToString("x2");
			}
			return text;
		}*/
	}
}
