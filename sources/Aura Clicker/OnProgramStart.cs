using System;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Net;
using System.Text;
using System.Windows;

namespace uhssvc
{
	// Token: 0x02000018 RID: 24
	internal class OnProgramStart
	{
		// Token: 0x06000075 RID: 117 RVA: 0x00003E84 File Offset: 0x00002084
		public static void Initialize(string string_0, string string_1, string string_2, string string_3)
		{
			if (string.IsNullOrWhiteSpace(string_0) || string.IsNullOrWhiteSpace(string_1) || string.IsNullOrWhiteSpace(string_2) || string.IsNullOrWhiteSpace(string_3))
			{
				MessageBox.Show("Invalid application information!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			OnProgramStart.AID = string_1;
			OnProgramStart.Secret = string_2;
			OnProgramStart.Version = string_3;
			OnProgramStart.Name = string_0;
			string[] array = new string[0];
			using (WebClient webClient = new WebClient())
			{
				try
				{
					webClient.Proxy = null;
					Security.Start();
					Encoding @default = Encoding.Default;
					WebClient webClient2 = webClient;
					string apiUrl = Constants.ApiUrl;
					NameValueCollection nameValueCollection = new NameValueCollection();
					nameValueCollection["token"] = Encryption.EncryptService(Constants.Token);
					nameValueCollection["timestamp"] = Encryption.EncryptService(DateTime.Now.ToString());
					nameValueCollection["aid"] = Encryption.smethod_0(OnProgramStart.AID);
					nameValueCollection["session_id"] = Constants.IV;
					nameValueCollection["api_id"] = Constants.APIENCRYPTSALT;
					nameValueCollection["api_key"] = Constants.APIENCRYPTKEY;
					nameValueCollection["session_key"] = Constants.Key;
					nameValueCollection["secret"] = Encryption.smethod_0(OnProgramStart.Secret);
					nameValueCollection["type"] = Encryption.smethod_0("start");
					array = Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					if (Security.MaliciousCheck(array[1]))
					{
						MessageBox.Show("Possible malicious activity detected!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Exclamation);
						Process.GetCurrentProcess().Kill();
					}
					if (Constants.Breached)
					{
						MessageBox.Show("Possible malicious activity detected!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Exclamation);
						Process.GetCurrentProcess().Kill();
					}
					if (array[0] != Constants.Token)
					{
						MessageBox.Show("Security error has been triggered!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Process.GetCurrentProcess().Kill();
					}
					string text = array[2];
					string a = text;
					if (!(a == "success"))
					{
						if (a == "binderror")
						{
							MessageBox.Show(Encryption.Decode("RmFpbGVkIHRvIGJpbmQgdG8gc2VydmVyLCBjaGVjayB5b3VyIEFJRCAmIFNlY3JldCBpbiB5b3VyIGNvZGUh"), OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
							Process.GetCurrentProcess().Kill();
							return;
						}
						if (a == "banned")
						{
							MessageBox.Show("This application has been banned for violating the TOS" + Environment.NewLine + "Contact us at support@auth.gg", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
							Process.GetCurrentProcess().Kill();
							return;
						}
					}
					else
					{
						Constants.Initialized = true;
						if (array[3] == "Enabled")
						{
							ApplicationSettings.Status = true;
						}
						if (array[4] == "Enabled")
						{
							ApplicationSettings.DeveloperMode = true;
						}
						ApplicationSettings.Hash = array[5];
						ApplicationSettings.Version = array[6];
						ApplicationSettings.Update_Link = array[7];
						if (array[8] == "Enabled")
						{
							ApplicationSettings.Freemode = true;
						}
						if (array[9] == "Enabled")
						{
							ApplicationSettings.Login = true;
						}
						ApplicationSettings.Name = array[10];
						if (array[11] == "Enabled")
						{
							ApplicationSettings.Register = true;
						}
						else
						{
							if (array[12] == "Enabled" && ApplicationSettings.Hash != Security.Integrity(Process.GetCurrentProcess().MainModule.FileName))
							{
								MessageBox.Show("File has been tampered with, couldn't verify integrity!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
								Process.GetCurrentProcess().Kill();
							}
							if (ApplicationSettings.Version != OnProgramStart.Version)
							{
								MessageBox.Show("Update " + ApplicationSettings.Version + " available, redirecting to update!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
								Process.Start(ApplicationSettings.Update_Link);
								Process.GetCurrentProcess().Kill();
							}
						}
						if (!ApplicationSettings.Status)
						{
							MessageBox.Show("Looks like this application is disabled, please try again later!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
							Process.GetCurrentProcess().Kill();
						}
					}
					Security.End();
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Process.GetCurrentProcess().Kill();
				}
			}
		}

		// Token: 0x0400028C RID: 652
		public static string AID;

		// Token: 0x0400028D RID: 653
		public static string Secret;

		// Token: 0x0400028E RID: 654
		public static string Version;

		// Token: 0x0400028F RID: 655
		public static string Name;

		// Token: 0x04000290 RID: 656
		public static string Salt;
	}
}
