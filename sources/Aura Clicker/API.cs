using System;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Net;
using System.Text;
using System.Windows;

namespace uhssvc
{
	// Token: 0x02000019 RID: 25
	internal class API
	{
		// Token: 0x06000077 RID: 119 RVA: 0x000042A4 File Offset: 0x000024A4
		public static void Log(string string_0, string string_1)
		{
			if (!Constants.Initialized)
			{
				MessageBox.Show("Please initialize your application first!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			if (string.IsNullOrWhiteSpace(string_1))
			{
				MessageBox.Show("Missing log information!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			using (WebClient webClient = new WebClient())
			{
				try
				{
					Security.Start();
					webClient.Proxy = null;
					Encoding @default = Encoding.Default;
					WebClient webClient2 = webClient;
					string apiUrl = Constants.ApiUrl;
					NameValueCollection nameValueCollection = new NameValueCollection();
					nameValueCollection["token"] = Encryption.EncryptService(Constants.Token);
					nameValueCollection["aid"] = Encryption.smethod_0(OnProgramStart.AID);
					nameValueCollection["username"] = Encryption.smethod_0(string_0);
					nameValueCollection["pcuser"] = Encryption.smethod_0(Environment.UserName);
					nameValueCollection["session_id"] = Constants.IV;
					nameValueCollection["api_id"] = Constants.APIENCRYPTSALT;
					nameValueCollection["api_key"] = Constants.APIENCRYPTKEY;
					nameValueCollection["data"] = Encryption.smethod_0(string_1);
					nameValueCollection["session_key"] = Constants.Key;
					nameValueCollection["secret"] = Encryption.smethod_0(OnProgramStart.Secret);
					nameValueCollection["type"] = Encryption.smethod_0("log");
					Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					Security.End();
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Process.GetCurrentProcess().Kill();
				}
			}
		}

		// Token: 0x06000078 RID: 120 RVA: 0x00004474 File Offset: 0x00002674
		public static bool AIO(string string_0)
		{
			return API.AIOLogin(string_0) || API.smethod_0(string_0);
		}

		// Token: 0x06000079 RID: 121 RVA: 0x0000449C File Offset: 0x0000269C
		public static bool AIOLogin(string string_0)
		{
			if (!Constants.Initialized)
			{
				MessageBox.Show("Please initialize your application first!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			if (string.IsNullOrWhiteSpace(string_0))
			{
				MessageBox.Show("Missing user login information!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			string[] array = new string[0];
			bool result;
			using (WebClient webClient = new WebClient())
			{
				try
				{
					Security.Start();
					webClient.Proxy = null;
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
					nameValueCollection["username"] = Encryption.smethod_0(string_0);
					nameValueCollection["password"] = Encryption.smethod_0(string_0);
					nameValueCollection["hwid"] = Encryption.smethod_0(Constants.HWID());
					nameValueCollection["session_key"] = Constants.Key;
					nameValueCollection["secret"] = Encryption.smethod_0(OnProgramStart.Secret);
					nameValueCollection["type"] = Encryption.smethod_0("login");
					array = Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					if (array[0] != Constants.Token)
					{
						MessageBox.Show("Security error has been triggered!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Process.GetCurrentProcess().Kill();
					}
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
					string text = array[2];
					string a = text;
					if (a == "success")
					{
						Security.End();
						User.ID = array[3];
						User.Username = array[4];
						User.Password = array[5];
						User.Email = array[6];
						User.HWID = array[7];
						User.UserVariable = array[8];
						User.Rank = array[9];
						User.IP = array[10];
						User.Expiry = array[11];
						User.LastLogin = array[12];
						User.RegisterDate = array[13];
						string text2 = array[14];
						foreach (string text3 in text2.Split(new char[]
						{
							'~'
						}))
						{
							string[] array3 = text3.Split(new char[]
							{
								'^'
							});
							try
							{
								App.Variables.Add(array3[0], array3[1]);
							}
							catch
							{
							}
						}
						return true;
					}
					if (a == "invalid_details")
					{
						Security.End();
						return false;
					}
					if (a == "time_expired")
					{
						MessageBox.Show("Your subscription has expired!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Exclamation);
						Security.End();
						return false;
					}
					if (a == "hwid_updated")
					{
						MessageBox.Show("New machine has been binded, re-open the application!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Asterisk);
						Security.End();
						return false;
					}
					if (a == "invalid_hwid")
					{
						MessageBox.Show("This user is binded to another computer, please contact support!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Security.End();
					Process.GetCurrentProcess().Kill();
				}
				result = false;
			}
			return result;
		}

		// Token: 0x0600007A RID: 122 RVA: 0x000048B0 File Offset: 0x00002AB0
		public static bool smethod_0(string string_0)
		{
			if (!Constants.Initialized)
			{
				MessageBox.Show("Please initialize your application first!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Security.End();
				Process.GetCurrentProcess().Kill();
			}
			if (string.IsNullOrWhiteSpace(string_0))
			{
				MessageBox.Show("Invalid registrar information!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			string[] array = new string[0];
			bool result;
			using (WebClient webClient = new WebClient())
			{
				try
				{
					Security.Start();
					webClient.Proxy = null;
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
					nameValueCollection["type"] = Encryption.smethod_0("register");
					nameValueCollection["username"] = Encryption.smethod_0(string_0);
					nameValueCollection["password"] = Encryption.smethod_0(string_0);
					nameValueCollection["email"] = Encryption.smethod_0(string_0);
					nameValueCollection["license"] = Encryption.smethod_0(string_0);
					nameValueCollection["hwid"] = Encryption.smethod_0(Constants.HWID());
					array = Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					if (array[0] != Constants.Token)
					{
						MessageBox.Show("Security error has been triggered!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						Process.GetCurrentProcess().Kill();
					}
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
					Security.End();
					string text = array[2];
					string a = text;
					if (a == "success")
					{
						return true;
					}
					if (a == "error")
					{
						return false;
					}
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Process.GetCurrentProcess().Kill();
				}
				result = false;
			}
			return result;
		}

		// Token: 0x0600007B RID: 123 RVA: 0x00004B7C File Offset: 0x00002D7C
		public static bool Login(string string_0, string string_1)
		{
			if (!Constants.Initialized)
			{
				MessageBox.Show("Please initialize your application first!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			if (string.IsNullOrWhiteSpace(string_0) || string.IsNullOrWhiteSpace(string_1))
			{
				MessageBox.Show("Missing user login information!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			string[] array = new string[0];
			bool result;
			using (WebClient webClient = new WebClient())
			{
				try
				{
					Security.Start();
					webClient.Proxy = null;
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
					nameValueCollection["username"] = Encryption.smethod_0(string_0);
					nameValueCollection["password"] = Encryption.smethod_0(string_1);
					nameValueCollection["hwid"] = Encryption.smethod_0(Constants.HWID());
					nameValueCollection["session_key"] = Constants.Key;
					nameValueCollection["secret"] = Encryption.smethod_0(OnProgramStart.Secret);
					nameValueCollection["type"] = Encryption.smethod_0("login");
					array = Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					if (array[0] != Constants.Token)
					{
						MessageBox.Show("Security error has been triggered!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Process.GetCurrentProcess().Kill();
					}
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
					string text = array[2];
					string a = text;
					if (a == "success")
					{
						User.ID = array[3];
						User.Username = array[4];
						User.Password = array[5];
						User.Email = array[6];
						User.HWID = array[7];
						User.UserVariable = array[8];
						User.Rank = array[9];
						User.IP = array[10];
						User.Expiry = array[11];
						User.LastLogin = array[12];
						User.RegisterDate = array[13];
						string text2 = array[14];
						foreach (string text3 in text2.Split(new char[]
						{
							'~'
						}))
						{
							string[] array3 = text3.Split(new char[]
							{
								'^'
							});
							try
							{
								App.Variables.Add(array3[0], array3[1]);
							}
							catch
							{
							}
						}
						Security.End();
						return true;
					}
					if (a == "invalid_details")
					{
						MessageBox.Show("Sorry, your username/password does not match!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
					if (a == "time_expired")
					{
						MessageBox.Show("Your subscription has expired!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Exclamation);
						Security.End();
						return false;
					}
					if (a == "hwid_updated")
					{
						MessageBox.Show("New machine has been binded, re-open the application!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Asterisk);
						Security.End();
						return false;
					}
					if (a == "invalid_hwid")
					{
						MessageBox.Show("This user is binded to another computer, please contact support!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Security.End();
					Process.GetCurrentProcess().Kill();
				}
				result = false;
			}
			return result;
		}

		// Token: 0x0600007C RID: 124 RVA: 0x00004FAC File Offset: 0x000031AC
		public static bool Register(string string_0, string string_1, string string_2, string string_3)
		{
			if (!Constants.Initialized)
			{
				MessageBox.Show("Please initialize your application first!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Security.End();
				Process.GetCurrentProcess().Kill();
			}
			if (string.IsNullOrWhiteSpace(string_0) || string.IsNullOrWhiteSpace(string_1) || string.IsNullOrWhiteSpace(string_2) || string.IsNullOrWhiteSpace(string_3))
			{
				MessageBox.Show("Invalid registrar information!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			string[] array = new string[0];
			bool result;
			using (WebClient webClient = new WebClient())
			{
				try
				{
					Security.Start();
					webClient.Proxy = null;
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
					nameValueCollection["type"] = Encryption.smethod_0("register");
					nameValueCollection["username"] = Encryption.smethod_0(string_0);
					nameValueCollection["password"] = Encryption.smethod_0(string_1);
					nameValueCollection["email"] = Encryption.smethod_0(string_2);
					nameValueCollection["license"] = Encryption.smethod_0(string_3);
					nameValueCollection["hwid"] = Encryption.smethod_0(Constants.HWID());
					array = Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					if (array[0] != Constants.Token)
					{
						MessageBox.Show("Security error has been triggered!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						Process.GetCurrentProcess().Kill();
					}
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
					string text = array[2];
					string a = text;
					if (a == "success")
					{
						Security.End();
						return true;
					}
					if (a == "invalid_license")
					{
						MessageBox.Show("License does not exist!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
					if (a == "email_used")
					{
						MessageBox.Show("Email has already been used!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
					if (a == "invalid_username")
					{
						MessageBox.Show("You entered an invalid/used username!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Process.GetCurrentProcess().Kill();
				}
				result = false;
			}
			return result;
		}

		// Token: 0x0600007D RID: 125 RVA: 0x00005300 File Offset: 0x00003500
		public static bool ExtendSubscription(string string_0, string string_1, string string_2)
		{
			if (!Constants.Initialized)
			{
				MessageBox.Show("Please initialize your application first!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Security.End();
				Process.GetCurrentProcess().Kill();
			}
			if (string.IsNullOrWhiteSpace(string_0) || string.IsNullOrWhiteSpace(string_1) || string.IsNullOrWhiteSpace(string_2))
			{
				MessageBox.Show("Invalid registrar information!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
				Process.GetCurrentProcess().Kill();
			}
			string[] array = new string[0];
			bool result;
			using (WebClient webClient = new WebClient())
			{
				try
				{
					Security.Start();
					webClient.Proxy = null;
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
					nameValueCollection["type"] = Encryption.smethod_0("extend");
					nameValueCollection["username"] = Encryption.smethod_0(string_0);
					nameValueCollection["password"] = Encryption.smethod_0(string_1);
					nameValueCollection["license"] = Encryption.smethod_0(string_2);
					array = Encryption.DecryptService(@default.GetString(webClient2.UploadValues(apiUrl, nameValueCollection))).Split("|".ToCharArray());
					if (array[0] != Constants.Token)
					{
						MessageBox.Show("Security error has been triggered!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						Process.GetCurrentProcess().Kill();
					}
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
					string text = array[2];
					string a = text;
					if (a == "success")
					{
						Security.End();
						return true;
					}
					if (a == "invalid_token")
					{
						MessageBox.Show("Token does not exist!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
					if (a == "invalid_details")
					{
						MessageBox.Show("Your user details are invalid!", ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Security.End();
						return false;
					}
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.Message, ApplicationSettings.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
					Process.GetCurrentProcess().Kill();
				}
				result = false;
			}
			return result;
		}
	}
}
