using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Globalization;
using System.Linq;
using System.Linq.Expressions;
using System.Management;
using System.Net;
using System.Net.NetworkInformation;
using System.Net.Security;
using System.Runtime.CompilerServices;
using System.Security.Cryptography.X509Certificates;
using System.Windows.Forms;
using beta-src;
using Microsoft.CSharp.RuntimeBinder;
using Newtonsoft.Json;
using ns1;
using ns20;
using ns29;
using ns31;
using ns33;
using ns34;
using ns35;
using ns36;
using Siticone.UI.WinForms;
using _50Cent_Ware.Properties;

namespace loader-src-idk
{
	// Token: 0x0200003A RID: 58
	public partial class Form3 : Form
	{
		// Token: 0x060001C4 RID: 452 RVA: 0x00017E58 File Offset: 0x00016058
		private static bool smethod_0()
		{
			bool result;
			if (Form3.string_0 == null)
			{
				result = false;
			}
			else
			{
				int num = DateTime.Compare(DateTime.Parse(Form3.string_0, CultureInfo.InvariantCulture), DateTime.Now);
				result = (num > 0);
			}
			return result;
		}

		// Token: 0x060001C5 RID: 453 RVA: 0x00017E94 File Offset: 0x00016094
		public static string smethod_1(int int_0)
		{
			return new string(Enumerable.Repeat<string>("abcdefghijklmnopqrstuvwyxz123456789", int_0).Select(new Func<string, char>(Form3.Class21.<>9.method_0)).ToArray<char>());
		}

		// Token: 0x060001C6 RID: 454 RVA: 0x00002798 File Offset: 0x00000998
		public Form3()
		{
			this.InitializeComponent();
		}

		// Token: 0x060001C7 RID: 455 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_0(object sender, EventArgs e)
		{
		}

		// Token: 0x060001C8 RID: 456 RVA: 0x000021D4 File Offset: 0x000003D4
		private void method_1(object sender, EventArgs e)
		{
		}

		// Token: 0x060001C9 RID: 457 RVA: 0x000021D4 File Offset: 0x000003D4
		private void password_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060001CA RID: 458 RVA: 0x000021D4 File Offset: 0x000003D4
		private void username_TextChanged(object sender, EventArgs e)
		{
		}

		// Token: 0x060001CB RID: 459 RVA: 0x00017EDC File Offset: 0x000160DC
		public static NetworkInterface smethod_2()
		{
			return NetworkInterface.GetAllNetworkInterfaces().FirstOrDefault(new Func<NetworkInterface, bool>(Form3.Class21.<>9.method_1));
		}

		// Token: 0x060001CC RID: 460 RVA: 0x00017F18 File Offset: 0x00016118
		public static void smethod_3(string string_1)
		{
			string[] value = new string[]
			{
				string_1
			};
			NetworkInterface networkInterface = Form3.smethod_2();
			if (networkInterface != null)
			{
				ManagementClass managementClass = new ManagementClass("Win32_NetworkAdapterConfiguration");
				ManagementObjectCollection instances = managementClass.GetInstances();
				foreach (ManagementBaseObject managementBaseObject in instances)
				{
					ManagementObject managementObject = (ManagementObject)managementBaseObject;
					if ((bool)managementObject["IPEnabled"] && managementObject["Description"].ToString().Equals(networkInterface.Description))
					{
						ManagementBaseObject methodParameters = managementObject.GetMethodParameters("SetDNSServerSearchOrder");
						if (methodParameters != null)
						{
							methodParameters["DNSServerSearchOrder"] = value;
							managementObject.InvokeMethod("SetDNSServerSearchOrder", methodParameters, null);
						}
					}
				}
			}
		}

		// Token: 0x060001CD RID: 461 RVA: 0x00017FF4 File Offset: 0x000161F4
		private void Form3_Load(object sender, EventArgs e)
		{
			if (Class40.smethod_4())
			{
				MessageBox.Show("Glassware does not support VM's");
				Environment.Exit(0);
			}
			WebClient webClient = new WebClient();
			string a = webClient.DownloadString("https://glassclicker.xyz/auth.txt");
			string a2 = webClient.DownloadString("https://glassclicker.xyz/update.txt");
			if (!(a2 == "2.0"))
			{
				MessageBox.Show("outdated version, please download new version from panel.", "glassclicker.xyz", MessageBoxButtons.OK);
				Process.Start("http://glassclicker.xyz/panel/");
				Environment.Exit(0);
			}
			if (!(a == "1.0"))
			{
				MessageBox.Show("cracked version detected, please purchase @ glassclicker.xyz", "glassclicker.xyz", MessageBoxButtons.OK);
				Environment.Exit(0);
			}
			this.password.Text = Settings.Default.password;
			this.username.Text = Settings.Default.username;
			this.Text = Form3.smethod_1(Form3.random_0.Next(12, 20));
		}

		// Token: 0x060001CE RID: 462 RVA: 0x000180D0 File Offset: 0x000162D0
		private void skeetButton2_Click(object sender, EventArgs e)
		{
			ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
			ServicePointManager.ServerCertificateValidationCallback = new RemoteCertificateValidationCallback(Form3.Class21.<>9.method_3);
			string text = Class38.smethod_0(string.Concat(new string[]
			{
				Class39.smethod_0(),
				Class39.smethod_1(),
				"?user=",
				GClass9.String_0,
				"&pass=",
				Class31.smethod_1(GClass9.String_1),
				"&hwid=",
				Class31.smethod_1(Class33.smethod_0()),
				"&key=",
				Class31.smethod_1(Class39.smethod_2())
			}));
			ServicePointManager.ServerCertificateValidationCallback = null;
			JsonSerializerSettings jsonSerializerSettings = new JsonSerializerSettings
			{
				NullValueHandling = 1,
				DateFormatString = "yyyy-MM-dd"
			};
			string text2 = Class31.smethod_0(text);
			object arg = JsonConvert.DeserializeObject(text2, jsonSerializerSettings);
			if (Form3.Class22.callSite_1 == null)
			{
				Form3.Class22.callSite_1 = CallSite<Func<CallSite, object, bool>>.Create(Binder.UnaryOperation(CSharpBinderFlags.None, ExpressionType.IsTrue, typeof(Form3), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, object, bool> target = Form3.Class22.callSite_1.Target;
			CallSite callSite_ = Form3.Class22.callSite_1;
			if (Form3.Class22.callSite_0 == null)
			{
				Form3.Class22.callSite_0 = CallSite<Func<CallSite, object, object, object>>.Create(Binder.BinaryOperation(CSharpBinderFlags.None, ExpressionType.Equal, typeof(Form3), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.Constant, null)
				}));
			}
			if (!target(callSite_, Form3.Class22.callSite_0.Target(Form3.Class22.callSite_0, arg, null)))
			{
				if (Form3.Class22.callSite_2 == null)
				{
					Form3.Class22.callSite_2 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "status", typeof(Form3), new CSharpArgumentInfo[]
					{
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
					}));
				}
				object arg2 = Form3.Class22.callSite_2.Target(Form3.Class22.callSite_2, arg);
				if (Form3.Class22.callSite_4 == null)
				{
					Form3.Class22.callSite_4 = CallSite<Func<CallSite, object, bool>>.Create(Binder.UnaryOperation(CSharpBinderFlags.None, ExpressionType.IsTrue, typeof(Form3), new CSharpArgumentInfo[]
					{
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
					}));
				}
				Func<CallSite, object, bool> target2 = Form3.Class22.callSite_4.Target;
				CallSite callSite_2 = Form3.Class22.callSite_4;
				if (Form3.Class22.callSite_3 == null)
				{
					Form3.Class22.callSite_3 = CallSite<Func<CallSite, object, string, object>>.Create(Binder.BinaryOperation(CSharpBinderFlags.None, ExpressionType.Equal, typeof(Form3), new CSharpArgumentInfo[]
					{
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.Constant, null)
					}));
				}
				if (target2(callSite_2, Form3.Class22.callSite_3.Target(Form3.Class22.callSite_3, arg2, "failed")))
				{
					if (Form3.Class22.callSite_6 == null)
					{
						Form3.Class22.callSite_6 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form3)));
					}
					Func<CallSite, object, string> target3 = Form3.Class22.callSite_6.Target;
					CallSite callSite_3 = Form3.Class22.callSite_6;
					if (Form3.Class22.callSite_5 == null)
					{
						Form3.Class22.callSite_5 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "error", typeof(Form3), new CSharpArgumentInfo[]
						{
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
						}));
					}
					string a = target3(callSite_3, Form3.Class22.callSite_5.Target(Form3.Class22.callSite_5, arg));
					if (a == "Invalid username" || a == "Invalid password")
					{
						MessageBox.Show("error, wrong username/password");
					}
					else
					{
						MessageBox.Show("please enter your username/password");
					}
				}
				else
				{
					if (Form3.Class22.callSite_8 == null)
					{
						Form3.Class22.callSite_8 = CallSite<Func<CallSite, object, bool>>.Create(Binder.UnaryOperation(CSharpBinderFlags.None, ExpressionType.IsTrue, typeof(Form3), new CSharpArgumentInfo[]
						{
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
						}));
					}
					Func<CallSite, object, bool> target4 = Form3.Class22.callSite_8.Target;
					CallSite callSite_4 = Form3.Class22.callSite_8;
					if (Form3.Class22.callSite_7 == null)
					{
						Form3.Class22.callSite_7 = CallSite<Func<CallSite, object, string, object>>.Create(Binder.BinaryOperation(CSharpBinderFlags.None, ExpressionType.Equal, typeof(Form3), new CSharpArgumentInfo[]
						{
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.Constant, null)
						}));
					}
					if (target4(callSite_4, Form3.Class22.callSite_7.Target(Form3.Class22.callSite_7, arg2, "success")))
					{
						Console.WriteLine("logged in successfully");
						if (Form3.Class22.callSite_10 == null)
						{
							Form3.Class22.callSite_10 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form3)));
						}
						Func<CallSite, object, string> target5 = Form3.Class22.callSite_10.Target;
						CallSite callSite_5 = Form3.Class22.callSite_10;
						if (Form3.Class22.callSite_9 == null)
						{
							Form3.Class22.callSite_9 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "banned", typeof(Form3), new CSharpArgumentInfo[]
							{
								CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
							}));
						}
						string a2 = target5(callSite_5, Form3.Class22.callSite_9.Target(Form3.Class22.callSite_9, arg));
						if (a2 == "1")
						{
							Console.WriteLine("you're banned");
							Environment.Exit(0);
						}
						else
						{
							if (Form3.Class22.callSite_12 == null)
							{
								Form3.Class22.callSite_12 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form3)));
							}
							Func<CallSite, object, string> target6 = Form3.Class22.callSite_12.Target;
							CallSite callSite_6 = Form3.Class22.callSite_12;
							if (Form3.Class22.callSite_11 == null)
							{
								Form3.Class22.callSite_11 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "hwid", typeof(Form3), new CSharpArgumentInfo[]
								{
									CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
								}));
							}
							string text3 = target6(callSite_6, Form3.Class22.callSite_11.Target(Form3.Class22.callSite_11, arg));
							if (Form3.Class22.callSite_14 == null)
							{
								Form3.Class22.callSite_14 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form3)));
							}
							Func<CallSite, object, string> target7 = Form3.Class22.callSite_14.Target;
							CallSite callSite_7 = Form3.Class22.callSite_14;
							if (Form3.Class22.callSite_13 == null)
							{
								Form3.Class22.callSite_13 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "sub", typeof(Form3), new CSharpArgumentInfo[]
								{
									CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
								}));
							}
							Form3.string_0 = target7(callSite_7, Form3.Class22.callSite_13.Target(Form3.Class22.callSite_13, arg));
							if (Class33.smethod_0() == text3 || text3 == null)
							{
								if (a2 == "0")
								{
									Console.WriteLine("you have an active subscription!");
									if (this.siticoneComboBox1.Text == "Stable")
									{
										Form2 form = new Form2();
										form.Show();
										base.Hide();
										this.timer_0.Stop();
									}
									if (this.siticoneComboBox1.Text == "Beta")
									{
										beta beta = new beta();
										beta.Show();
										base.Hide();
										this.timer_0.Stop();
									}
								}
								else
								{
									MessageBox.Show("Wrong Password/Username");
								}
							}
						}
					}
					else
					{
						MessageBox.Show("Error #110, Contact Staff");
						Environment.Exit(0);
					}
				}
			}
		}

		// Token: 0x060001CF RID: 463 RVA: 0x000021D4 File Offset: 0x000003D4
		private void timer_0_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x060001D0 RID: 464 RVA: 0x000021D4 File Offset: 0x000003D4
		private void pictureBox1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060001D1 RID: 465 RVA: 0x000021D4 File Offset: 0x000003D4
		private void siticoneCustomGradientPanel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x060001D2 RID: 466 RVA: 0x0001873C File Offset: 0x0001693C
		protected virtual void Dispose(bool disposing)
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x04000392 RID: 914
		private static readonly Random random_0 = new Random();

		// Token: 0x04000393 RID: 915
		private static string string_0;

		// Token: 0x0200003B RID: 59
		[CompilerGenerated]
		[Serializable]
		private sealed class Class21
		{
			// Token: 0x060001D7 RID: 471 RVA: 0x000027C5 File Offset: 0x000009C5
			internal char method_0(string string_0)
			{
				return string_0[Form3.random_0.Next(string_0.Length)];
			}

			// Token: 0x060001D8 RID: 472 RVA: 0x000194D8 File Offset: 0x000176D8
			internal bool method_1(NetworkInterface networkInterface_0)
			{
				bool result;
				if (networkInterface_0.OperationalStatus == OperationalStatus.Up && (networkInterface_0.NetworkInterfaceType == NetworkInterfaceType.Wireless80211 || networkInterface_0.NetworkInterfaceType == NetworkInterfaceType.Ethernet))
				{
					result = networkInterface_0.GetIPProperties().GatewayAddresses.Any(new Func<GatewayIPAddressInformation, bool>(Form3.Class21.<>9.method_2));
				}
				else
				{
					result = false;
				}
				return result;
			}

			// Token: 0x060001D9 RID: 473 RVA: 0x00019534 File Offset: 0x00017734
			internal bool method_2(GatewayIPAddressInformation gatewayIPAddressInformation_0)
			{
				return gatewayIPAddressInformation_0.Address.AddressFamily.ToString() == "InterNetwork";
			}

			// Token: 0x060001DA RID: 474 RVA: 0x00019564 File Offset: 0x00017764
			internal bool method_3(object object_0, X509Certificate x509Certificate_0, X509Chain x509Chain_0, SslPolicyErrors sslPolicyErrors_0)
			{
				X509Certificate2 x509Certificate = new X509Certificate2(x509Certificate_0);
				string thumbprint = x509Certificate.Thumbprint;
				return ((thumbprint != null) ? thumbprint.ToLower() : null) == "da923273c2140fcd4328839cb557652d4eb87c97" && x509Certificate.Verify();
			}

			// Token: 0x0400039F RID: 927
			public static readonly Form3.Class21 <>9 = new Form3.Class21();

			// Token: 0x040003A0 RID: 928
			public static Func<string, char> <>9__2_0;

			// Token: 0x040003A1 RID: 929
			public static Func<GatewayIPAddressInformation, bool> <>9__9_1;

			// Token: 0x040003A2 RID: 930
			public static Func<NetworkInterface, bool> <>9__9_0;

			// Token: 0x040003A3 RID: 931
			public static RemoteCertificateValidationCallback <>9__12_0;
		}

		// Token: 0x0200003C RID: 60
		[CompilerGenerated]
		private static class Class22
		{
			// Token: 0x040003A4 RID: 932
			public static CallSite<Func<CallSite, object, object, object>> callSite_0;

			// Token: 0x040003A5 RID: 933
			public static CallSite<Func<CallSite, object, bool>> callSite_1;

			// Token: 0x040003A6 RID: 934
			public static CallSite<Func<CallSite, object, object>> callSite_2;

			// Token: 0x040003A7 RID: 935
			public static CallSite<Func<CallSite, object, string, object>> callSite_3;

			// Token: 0x040003A8 RID: 936
			public static CallSite<Func<CallSite, object, bool>> callSite_4;

			// Token: 0x040003A9 RID: 937
			public static CallSite<Func<CallSite, object, object>> callSite_5;

			// Token: 0x040003AA RID: 938
			public static CallSite<Func<CallSite, object, string>> callSite_6;

			// Token: 0x040003AB RID: 939
			public static CallSite<Func<CallSite, object, string, object>> callSite_7;

			// Token: 0x040003AC RID: 940
			public static CallSite<Func<CallSite, object, bool>> callSite_8;

			// Token: 0x040003AD RID: 941
			public static CallSite<Func<CallSite, object, object>> callSite_9;

			// Token: 0x040003AE RID: 942
			public static CallSite<Func<CallSite, object, string>> callSite_10;

			// Token: 0x040003AF RID: 943
			public static CallSite<Func<CallSite, object, object>> callSite_11;

			// Token: 0x040003B0 RID: 944
			public static CallSite<Func<CallSite, object, string>> callSite_12;

			// Token: 0x040003B1 RID: 945
			public static CallSite<Func<CallSite, object, object>> callSite_13;

			// Token: 0x040003B2 RID: 946
			public static CallSite<Func<CallSite, object, string>> callSite_14;
		}
	}
}
