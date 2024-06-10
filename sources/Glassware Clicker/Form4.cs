using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Linq;
using System.Linq.Expressions;
using System.Net;
using System.Net.Security;
using System.Runtime.CompilerServices;
using System.Security.Cryptography.X509Certificates;
using System.Windows.Forms;
using beta-src;
using Guna.UI2.WinForms;
using Microsoft.CSharp.RuntimeBinder;
using Newtonsoft.Json;
using ns20;
using ns29;
using ns31;
using ns34;
using ns35;
using ns36;
using ns5;
using Siticone.UI.WinForms;

namespace Loader-SRC
{
	// Token: 0x0200003D RID: 61
	public partial class Form4 : Form
	{
		// Token: 0x060001DB RID: 475 RVA: 0x000027DD File Offset: 0x000009DD
		public Form4()
		{
			this.InitializeComponent();
		}

		// Token: 0x060001DC RID: 476 RVA: 0x000195A0 File Offset: 0x000177A0
		public static string smethod_0(int int_0)
		{
			Form4.Class23 @class = new Form4.Class23();
			@class.random_0 = new Random();
			return new string(Enumerable.Repeat<string>("abcdefghijklmnopqrstuvwyxz123456789", int_0).Select(new Func<string, char>(@class.method_0)).ToArray<char>());
		}

		// Token: 0x060001DD RID: 477 RVA: 0x000027F2 File Offset: 0x000009F2
		private void guna2Button1_Click(object sender, EventArgs e)
		{
			this.guna2Button1.Checked = true;
			this.guna2Button2.Checked = false;
			this.guna2Button3.Checked = false;
			this.guna2Button4.Checked = false;
		}

		// Token: 0x060001DE RID: 478 RVA: 0x00002824 File Offset: 0x00000A24
		private void guna2Button2_Click(object sender, EventArgs e)
		{
			this.guna2Button2.Checked = true;
			this.guna2Button1.Checked = false;
			this.guna2Button3.Checked = false;
			this.guna2Button4.Checked = false;
			Process.Start("https://discord.gg/dYQFuGmQB3");
		}

		// Token: 0x060001DF RID: 479 RVA: 0x000195E8 File Offset: 0x000177E8
		private void Form4_Load(object sender, EventArgs e)
		{
			this.guna2Button1.Checked = true;
			this.Text = Form4.smethod_0(40);
			this.timer_0.Start();
			if (Class40.smethod_4())
			{
				MessageBox.Show("Glassware does not support VM's");
				Environment.Exit(0);
			}
			WebClient webClient = new WebClient();
			string a = webClient.DownloadString("https://glassclicker.xyz/auth.txt");
			string a2 = webClient.DownloadString("https://glassclicker.xyz/update.txt");
			string text = webClient.DownloadString("https://glassclicker.xyz/text.txt");
			this.outlineLabel2.Text = text;
			if (!(a2 == "2.0"))
			{
				MessageBox.Show("outdated version, please download new version from panel.", "glassclicker.xyz", MessageBoxButtons.OK);
				Process.Start("http://glassclicker.xyz/panel/");
				this.outlineLabel2.Text = "outdated";
				this.outlineLabel2.ForeColor = Color.Red;
			}
			if (!(a == "1.0"))
			{
				MessageBox.Show("cracked version detected, please purchase @ glassclicker.xyz", "glassclicker.xyz", MessageBoxButtons.OK);
				Environment.Exit(0);
			}
		}

		// Token: 0x060001E0 RID: 480 RVA: 0x00002861 File Offset: 0x00000A61
		private void timer_0_Tick(object sender, EventArgs e)
		{
			this.Text = Form4.smethod_0(40);
		}

		// Token: 0x060001E1 RID: 481 RVA: 0x000021D4 File Offset: 0x000003D4
		private void guna2Panel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x060001E2 RID: 482 RVA: 0x000021D4 File Offset: 0x000003D4
		private void guna2GradientPanel1_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x060001E3 RID: 483 RVA: 0x000021D4 File Offset: 0x000003D4
		private void guna2Panel3_Paint(object sender, PaintEventArgs e)
		{
		}

		// Token: 0x060001E4 RID: 484 RVA: 0x000196D4 File Offset: 0x000178D4
		private void guna2Button5_Click(object sender, EventArgs e)
		{
			ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls12;
			ServicePointManager.ServerCertificateValidationCallback = new RemoteCertificateValidationCallback(Form4.Class25.<>9.method_0);
			string text = Class38.smethod_0(string.Concat(new string[]
			{
				Class39.smethod_0(),
				Class39.smethod_1(),
				"?user=",
				this.username.Text,
				"&pass=",
				Class31.smethod_1(this.password.Text),
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
			if (Form4.Class24.callSite_1 == null)
			{
				Form4.Class24.callSite_1 = CallSite<Func<CallSite, object, bool>>.Create(Binder.UnaryOperation(CSharpBinderFlags.None, ExpressionType.IsTrue, typeof(Form4), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
				}));
			}
			Func<CallSite, object, bool> target = Form4.Class24.callSite_1.Target;
			CallSite callSite_ = Form4.Class24.callSite_1;
			if (Form4.Class24.callSite_0 == null)
			{
				Form4.Class24.callSite_0 = CallSite<Func<CallSite, object, object, object>>.Create(Binder.BinaryOperation(CSharpBinderFlags.None, ExpressionType.Equal, typeof(Form4), new CSharpArgumentInfo[]
				{
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
					CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.Constant, null)
				}));
			}
			if (!target(callSite_, Form4.Class24.callSite_0.Target(Form4.Class24.callSite_0, arg, null)))
			{
				if (Form4.Class24.callSite_2 == null)
				{
					Form4.Class24.callSite_2 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "status", typeof(Form4), new CSharpArgumentInfo[]
					{
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
					}));
				}
				object arg2 = Form4.Class24.callSite_2.Target(Form4.Class24.callSite_2, arg);
				if (Form4.Class24.callSite_4 == null)
				{
					Form4.Class24.callSite_4 = CallSite<Func<CallSite, object, bool>>.Create(Binder.UnaryOperation(CSharpBinderFlags.None, ExpressionType.IsTrue, typeof(Form4), new CSharpArgumentInfo[]
					{
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
					}));
				}
				Func<CallSite, object, bool> target2 = Form4.Class24.callSite_4.Target;
				CallSite callSite_2 = Form4.Class24.callSite_4;
				if (Form4.Class24.callSite_3 == null)
				{
					Form4.Class24.callSite_3 = CallSite<Func<CallSite, object, string, object>>.Create(Binder.BinaryOperation(CSharpBinderFlags.None, ExpressionType.Equal, typeof(Form4), new CSharpArgumentInfo[]
					{
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
						CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.Constant, null)
					}));
				}
				if (target2(callSite_2, Form4.Class24.callSite_3.Target(Form4.Class24.callSite_3, arg2, "failed")))
				{
					if (Form4.Class24.callSite_6 == null)
					{
						Form4.Class24.callSite_6 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form4)));
					}
					Func<CallSite, object, string> target3 = Form4.Class24.callSite_6.Target;
					CallSite callSite_3 = Form4.Class24.callSite_6;
					if (Form4.Class24.callSite_5 == null)
					{
						Form4.Class24.callSite_5 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "error", typeof(Form4), new CSharpArgumentInfo[]
						{
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
						}));
					}
					string a = target3(callSite_3, Form4.Class24.callSite_5.Target(Form4.Class24.callSite_5, arg));
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
					if (Form4.Class24.callSite_8 == null)
					{
						Form4.Class24.callSite_8 = CallSite<Func<CallSite, object, bool>>.Create(Binder.UnaryOperation(CSharpBinderFlags.None, ExpressionType.IsTrue, typeof(Form4), new CSharpArgumentInfo[]
						{
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
						}));
					}
					Func<CallSite, object, bool> target4 = Form4.Class24.callSite_8.Target;
					CallSite callSite_4 = Form4.Class24.callSite_8;
					if (Form4.Class24.callSite_7 == null)
					{
						Form4.Class24.callSite_7 = CallSite<Func<CallSite, object, string, object>>.Create(Binder.BinaryOperation(CSharpBinderFlags.None, ExpressionType.Equal, typeof(Form4), new CSharpArgumentInfo[]
						{
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null),
							CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.UseCompileTimeType | CSharpArgumentInfoFlags.Constant, null)
						}));
					}
					if (target4(callSite_4, Form4.Class24.callSite_7.Target(Form4.Class24.callSite_7, arg2, "success")))
					{
						Console.WriteLine("logged in successfully");
						if (Form4.Class24.callSite_10 == null)
						{
							Form4.Class24.callSite_10 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form4)));
						}
						Func<CallSite, object, string> target5 = Form4.Class24.callSite_10.Target;
						CallSite callSite_5 = Form4.Class24.callSite_10;
						if (Form4.Class24.callSite_9 == null)
						{
							Form4.Class24.callSite_9 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "banned", typeof(Form4), new CSharpArgumentInfo[]
							{
								CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
							}));
						}
						string a2 = target5(callSite_5, Form4.Class24.callSite_9.Target(Form4.Class24.callSite_9, arg));
						if (a2 == "1")
						{
							Console.WriteLine("you're banned");
							Environment.Exit(0);
						}
						else
						{
							if (Form4.Class24.callSite_12 == null)
							{
								Form4.Class24.callSite_12 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form4)));
							}
							Func<CallSite, object, string> target6 = Form4.Class24.callSite_12.Target;
							CallSite callSite_6 = Form4.Class24.callSite_12;
							if (Form4.Class24.callSite_11 == null)
							{
								Form4.Class24.callSite_11 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "hwid", typeof(Form4), new CSharpArgumentInfo[]
								{
									CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
								}));
							}
							string text3 = target6(callSite_6, Form4.Class24.callSite_11.Target(Form4.Class24.callSite_11, arg));
							if (Form4.Class24.callSite_14 == null)
							{
								Form4.Class24.callSite_14 = CallSite<Func<CallSite, object, string>>.Create(Binder.Convert(CSharpBinderFlags.None, typeof(string), typeof(Form4)));
							}
							Func<CallSite, object, string> target7 = Form4.Class24.callSite_14.Target;
							CallSite callSite_7 = Form4.Class24.callSite_14;
							if (Form4.Class24.callSite_13 == null)
							{
								Form4.Class24.callSite_13 = CallSite<Func<CallSite, object, object>>.Create(Binder.GetMember(CSharpBinderFlags.None, "sub", typeof(Form4), new CSharpArgumentInfo[]
								{
									CSharpArgumentInfo.Create(CSharpArgumentInfoFlags.None, null)
								}));
							}
							Form4.string_0 = target7(callSite_7, Form4.Class24.callSite_13.Target(Form4.Class24.callSite_13, arg));
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
									}
									if (this.siticoneComboBox1.Text == "Beta")
									{
										beta beta = new beta();
										beta.Show();
										base.Hide();
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

		// Token: 0x060001E5 RID: 485 RVA: 0x00002870 File Offset: 0x00000A70
		private void password_TextChanged(object sender, EventArgs e)
		{
			this.password.UseSystemPasswordChar = true;
		}

		// Token: 0x060001E6 RID: 486 RVA: 0x000021D4 File Offset: 0x000003D4
		private void password_MouseLeave(object sender, EventArgs e)
		{
		}

		// Token: 0x060001E7 RID: 487 RVA: 0x000021D4 File Offset: 0x000003D4
		private void outlineLabel2_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x060001E8 RID: 488 RVA: 0x00019D38 File Offset: 0x00017F38
		protected virtual void Dispose(bool disposing)
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x040003B3 RID: 947
		private static string string_0;

		// Token: 0x0200003E RID: 62
		[CompilerGenerated]
		private sealed class Class23
		{
			// Token: 0x060001EB RID: 491 RVA: 0x0000287E File Offset: 0x00000A7E
			internal char method_0(string string_0)
			{
				return string_0[this.random_0.Next(string_0.Length)];
			}

			// Token: 0x040003CA RID: 970
			public Random random_0;
		}

		// Token: 0x0200003F RID: 63
		[CompilerGenerated]
		private static class Class24
		{
			// Token: 0x040003CB RID: 971
			public static CallSite<Func<CallSite, object, object, object>> callSite_0;

			// Token: 0x040003CC RID: 972
			public static CallSite<Func<CallSite, object, bool>> callSite_1;

			// Token: 0x040003CD RID: 973
			public static CallSite<Func<CallSite, object, object>> callSite_2;

			// Token: 0x040003CE RID: 974
			public static CallSite<Func<CallSite, object, string, object>> callSite_3;

			// Token: 0x040003CF RID: 975
			public static CallSite<Func<CallSite, object, bool>> callSite_4;

			// Token: 0x040003D0 RID: 976
			public static CallSite<Func<CallSite, object, object>> callSite_5;

			// Token: 0x040003D1 RID: 977
			public static CallSite<Func<CallSite, object, string>> callSite_6;

			// Token: 0x040003D2 RID: 978
			public static CallSite<Func<CallSite, object, string, object>> callSite_7;

			// Token: 0x040003D3 RID: 979
			public static CallSite<Func<CallSite, object, bool>> callSite_8;

			// Token: 0x040003D4 RID: 980
			public static CallSite<Func<CallSite, object, object>> callSite_9;

			// Token: 0x040003D5 RID: 981
			public static CallSite<Func<CallSite, object, string>> callSite_10;

			// Token: 0x040003D6 RID: 982
			public static CallSite<Func<CallSite, object, object>> callSite_11;

			// Token: 0x040003D7 RID: 983
			public static CallSite<Func<CallSite, object, string>> callSite_12;

			// Token: 0x040003D8 RID: 984
			public static CallSite<Func<CallSite, object, object>> callSite_13;

			// Token: 0x040003D9 RID: 985
			public static CallSite<Func<CallSite, object, string>> callSite_14;
		}

		// Token: 0x02000040 RID: 64
		[CompilerGenerated]
		[Serializable]
		private sealed class Class25
		{
			// Token: 0x060001EE RID: 494 RVA: 0x00019564 File Offset: 0x00017764
			internal bool method_0(object object_0, X509Certificate x509Certificate_0, X509Chain x509Chain_0, SslPolicyErrors sslPolicyErrors_0)
			{
				X509Certificate2 x509Certificate = new X509Certificate2(x509Certificate_0);
				string thumbprint = x509Certificate.Thumbprint;
				return ((thumbprint != null) ? thumbprint.ToLower() : null) == "da923273c2140fcd4328839cb557652d4eb87c97" && x509Certificate.Verify();
			}

			// Token: 0x040003DA RID: 986
			public static readonly Form4.Class25 <>9 = new Form4.Class25();

			// Token: 0x040003DB RID: 987
			public static RemoteCertificateValidationCallback <>9__10_0;
		}
	}
}
