using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Windows;

namespace uhssvc
{
	// Token: 0x0200001A RID: 26
	internal class Security
	{
		// Token: 0x0600007F RID: 127 RVA: 0x000055F8 File Offset: 0x000037F8
		public static string Signature(string string_0)
		{
			string result;
			using (MD5 md = MD5.Create())
			{
				byte[] bytes = Encoding.UTF8.GetBytes(string_0);
				byte[] value = md.ComputeHash(bytes);
				result = BitConverter.ToString(value).Replace("-", "");
			}
			return result;
		}

		// Token: 0x06000080 RID: 128 RVA: 0x00005654 File Offset: 0x00003854
		private static string Session(int int_0)
		{
			Random random = new Random();
			return new string((from string_0 in Enumerable.Repeat<string>("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz", int_0)
			select string_0[random.Next(string_0.Length)]).ToArray<char>());
		}

		// Token: 0x06000081 RID: 129 RVA: 0x0000569C File Offset: 0x0000389C
		public static string Obfuscate(int int_0)
		{
			Random random = new Random();
			return new string((from string_0 in Enumerable.Repeat<string>("gd8JQ57nxXzLLMPrLylVhxoGnWGCFjO4knKTfRE6mVvdjug2NF/4aptAsZcdIGbAPmcx0O+ftU/KvMIjcfUnH3j+IMdhAW5OpoX3MrjQdf5AAP97tTB5g1wdDSAqKpq9gw06t3VaqMWZHKtPSuAXy0kkZRsc+DicpcY8E9+vWMHXa3jMdbPx4YES0p66GzhqLd/heA2zMvX8iWv4wK7S3QKIW/a9dD4ALZJpmcr9OOE=", int_0)
			select string_0[random.Next(string_0.Length)]).ToArray<char>());
		}

		// Token: 0x06000082 RID: 130 RVA: 0x000056E4 File Offset: 0x000038E4
		public static void Start()
		{
			string pathRoot = Path.GetPathRoot(Environment.SystemDirectory);
			if (Constants.Started)
			{
				MessageBox.Show("A session has already been started, please end the previous one!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Exclamation);
				Process.GetCurrentProcess().Kill();
			}
			else
			{
				using (StreamReader streamReader = new StreamReader(pathRoot + "Windows\\System32\\drivers\\etc\\hosts"))
				{
					string text = streamReader.ReadToEnd();
					if (text.Contains("api.auth.gg"))
					{
						Constants.Breached = true;
						MessageBox.Show("DNS redirecting has been detected!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Hand);
						Process.GetCurrentProcess().Kill();
					}
				}
				InfoManager infoManager = new InfoManager();
				infoManager.StartListener();
				Constants.Token = Guid.NewGuid().ToString();
				ServicePointManager.ServerCertificateValidationCallback = (RemoteCertificateValidationCallback)Delegate.Combine(ServicePointManager.ServerCertificateValidationCallback, new RemoteCertificateValidationCallback(Security.PinPublicKey));
				Constants.APIENCRYPTKEY = Convert.ToBase64String(Encoding.Default.GetBytes(Security.Session(32)));
				Constants.APIENCRYPTSALT = Convert.ToBase64String(Encoding.Default.GetBytes(Security.Session(16)));
				Constants.IV = Convert.ToBase64String(Encoding.Default.GetBytes(Constants.RandomString(16)));
				Constants.Key = Convert.ToBase64String(Encoding.Default.GetBytes(Constants.RandomString(32)));
				Constants.Started = true;
			}
		}

		// Token: 0x06000083 RID: 131 RVA: 0x00005840 File Offset: 0x00003A40
		public static void End()
		{
			if (!Constants.Started)
			{
				MessageBox.Show("No session has been started, closing for security reasons!", OnProgramStart.Name, MessageBoxButton.OK, MessageBoxImage.Exclamation);
				Process.GetCurrentProcess().Kill();
			}
			else
			{
				Constants.Token = null;
				ServicePointManager.ServerCertificateValidationCallback = ((object object_0, X509Certificate x509Certificate_0, X509Chain x509Chain_0, SslPolicyErrors sslPolicyErrors_0) => true);
				Constants.APIENCRYPTKEY = null;
				Constants.APIENCRYPTSALT = null;
				Constants.IV = null;
				Constants.Key = null;
				Constants.Started = false;
			}
		}

		// Token: 0x06000084 RID: 132 RVA: 0x000024E9 File Offset: 0x000006E9
		private static bool PinPublicKey(object object_0, X509Certificate x509Certificate_0, X509Chain x509Chain_0, SslPolicyErrors sslPolicyErrors_0)
		{
			return x509Certificate_0 != null && x509Certificate_0.GetPublicKeyString() == "045C03C7FB0E76A822AB197B6663C288D9632E55B39B80296CBCD978707A7E5B3EAE1DB5D487CE9F0E448C3557079CE142F5A41B7F1F6436077D8F3FF7C311888C";
		}

		// Token: 0x06000085 RID: 133 RVA: 0x000058C0 File Offset: 0x00003AC0
		public static string Integrity(string string_0)
		{
			string result;
			using (MD5 md = MD5.Create())
			{
				using (FileStream fileStream = File.OpenRead(string_0))
				{
					byte[] value = md.ComputeHash(fileStream);
					result = BitConverter.ToString(value).Replace("-", "").ToLowerInvariant();
				}
			}
			return result;
		}

		// Token: 0x06000086 RID: 134 RVA: 0x00005938 File Offset: 0x00003B38
		public static bool MaliciousCheck(string string_0)
		{
			DateTime d = DateTime.Parse(string_0);
			DateTime now = DateTime.Now;
			TimeSpan timeSpan = d - now;
			bool result;
			if (Convert.ToInt32(timeSpan.Seconds.ToString().Replace("-", "")) >= 30 || Convert.ToInt32(timeSpan.Minutes.ToString().Replace("-", "")) >= 1)
			{
				Constants.Breached = true;
				result = true;
			}
			else
			{
				result = false;
			}
			return result;
		}

		// Token: 0x04000291 RID: 657
		private const string _key = "045C03C7FB0E76A822AB197B6663C288D9632E55B39B80296CBCD978707A7E5B3EAE1DB5D487CE9F0E448C3557079CE142F5A41B7F1F6436077D8F3FF7C311888C";
	}
}
