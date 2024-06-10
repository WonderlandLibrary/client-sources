using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Diagnostics;
using System.Net;
using System.Net.Security;
using System.Runtime.CompilerServices;
using System.Runtime.Serialization;
using System.Security.Cryptography.X509Certificates;
using System.Security.Principal;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using ns11;
using ns12;
using ns13;

namespace auth-src
{
	// Token: 0x0200001F RID: 31
	public class GClass2
	{
		// Token: 0x060000B1 RID: 177 RVA: 0x00005920 File Offset: 0x00003B20
		public GClass2(string string_6, string string_7, string string_8, string string_9)
		{
			if (string.IsNullOrWhiteSpace(string_6) || string.IsNullOrWhiteSpace(string_7) || string.IsNullOrWhiteSpace(string_8) || string.IsNullOrWhiteSpace(string_9))
			{
				MessageBox.Show("Application not setup correctly. Please watch video link found in Login.cs");
				Environment.Exit(0);
			}
			this.string_0 = string_6;
			this.string_1 = string_7;
			this.string_2 = string_8;
			this.string_3 = string_9;
		}

		// Token: 0x060000B2 RID: 178 RVA: 0x000059A0 File Offset: 0x00003BA0
		public void method_0()
		{
			this.string_5 = GClass6.smethod_5(GClass6.smethod_4());
			string value = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("init"));
			nameValueCollection["ver"] = GClass6.smethod_6(this.string_3, this.string_2, value);
			nameValueCollection["enckey"] = GClass6.smethod_6(this.string_5, this.string_2, value);
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string a = GClass2.smethod_0(nameValueCollection_);
			if (a == "KeyAuth_Invalid")
			{
				MessageBox.Show("Application not found.");
				Environment.Exit(0);
			}
			a = GClass6.smethod_7(a, this.string_2, value);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(a);
			if (@class.Boolean_0)
			{
				this.string_4 = @class.String_0;
			}
			else if (@class.String_3 == "invalidver")
			{
				Process.Start(@class.String_4);
				Environment.Exit(0);
			}
			else
			{
				MessageBox.Show(@class.String_3);
				Environment.Exit(0);
			}
		}

		// Token: 0x060000B3 RID: 179 RVA: 0x00005B04 File Offset: 0x00003D04
		public bool method_1(string string_6, string string_7, string string_8)
		{
			string value = WindowsIdentity.GetCurrent().User.Value;
			string value2 = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("register"));
			nameValueCollection["username"] = GClass6.smethod_6(string_6, this.string_5, value2);
			nameValueCollection["pass"] = GClass6.smethod_6(string_7, this.string_5, value2);
			nameValueCollection["key"] = GClass6.smethod_6(string_8, this.string_5, value2);
			nameValueCollection["hwid"] = GClass6.smethod_6(value, this.string_5, value2);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value2;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value2);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			bool result;
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
				result = false;
			}
			else
			{
				this.method_10(@class.Class7_0);
				result = true;
			}
			return result;
		}

		// Token: 0x060000B4 RID: 180 RVA: 0x00005C70 File Offset: 0x00003E70
		public bool method_2(string string_6, string string_7)
		{
			string value = WindowsIdentity.GetCurrent().User.Value;
			string value2 = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("login"));
			nameValueCollection["username"] = GClass6.smethod_6(string_6, this.string_5, value2);
			nameValueCollection["pass"] = GClass6.smethod_6(string_7, this.string_5, value2);
			nameValueCollection["hwid"] = GClass6.smethod_6(value, this.string_5, value2);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value2;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value2);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			bool result;
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
				result = false;
			}
			else
			{
				this.method_10(@class.Class7_0);
				result = true;
			}
			return result;
		}

		// Token: 0x060000B5 RID: 181 RVA: 0x00005DC4 File Offset: 0x00003FC4
		public void method_3(string string_6, string string_7)
		{
			string value = WindowsIdentity.GetCurrent().User.Value;
			string value2 = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("upgrade"));
			nameValueCollection["username"] = GClass6.smethod_6(string_6, this.string_5, value2);
			nameValueCollection["key"] = GClass6.smethod_6(string_7, this.string_5, value2);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value2;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value2);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
				Environment.Exit(0);
			}
			else
			{
				MessageBox.Show(@class.String_3);
			}
		}

		// Token: 0x060000B6 RID: 182 RVA: 0x00005F00 File Offset: 0x00004100
		public bool method_4(string string_6)
		{
			string value = WindowsIdentity.GetCurrent().User.Value;
			string value2 = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("license"));
			nameValueCollection["key"] = GClass6.smethod_6(string_6, this.string_5, value2);
			nameValueCollection["hwid"] = GClass6.smethod_6(value, this.string_5, value2);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value2;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value2);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			bool result;
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
				Environment.Exit(0);
				result = false;
			}
			else
			{
				this.method_10(@class.Class7_0);
				result = true;
			}
			return result;
		}

		// Token: 0x060000B7 RID: 183 RVA: 0x00006044 File Offset: 0x00004244
		public void method_5()
		{
			string value = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("ban"));
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
				Environment.Exit(0);
			}
		}

		// Token: 0x060000B8 RID: 184 RVA: 0x0000612C File Offset: 0x0000432C
		public string method_6(string string_6)
		{
			string value = WindowsIdentity.GetCurrent().User.Value;
			string value2 = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("var"));
			nameValueCollection["varid"] = GClass6.smethod_6(string_6, this.string_5, value2);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value2;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value2);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			string result;
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
				result = "";
			}
			else
			{
				result = @class.String_3;
			}
			return result;
		}

		// Token: 0x060000B9 RID: 185 RVA: 0x00006250 File Offset: 0x00004450
		public void method_7(string string_6, string string_7)
		{
			string value = WindowsIdentity.GetCurrent().User.Value;
			string value2 = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("webhook"));
			nameValueCollection["webid"] = GClass6.smethod_6(string_6, this.string_5, value2);
			nameValueCollection["params"] = GClass6.smethod_6(string_7, this.string_5, value2);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value2;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value2);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
			}
		}

		// Token: 0x060000BA RID: 186 RVA: 0x00006378 File Offset: 0x00004578
		public byte[] method_8(string string_6)
		{
			string value = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("file"));
			nameValueCollection["fileid"] = GClass6.smethod_6(string_6, this.string_5, value);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			string text = GClass2.smethod_0(nameValueCollection_);
			text = GClass6.smethod_7(text, this.string_5, value);
			GClass2.Class6 @class = this.gclass7_0.method_1<GClass2.Class6>(text);
			if (!@class.Boolean_0)
			{
				MessageBox.Show(@class.String_3);
			}
			return GClass6.smethod_1(@class.String_1);
		}

		// Token: 0x060000BB RID: 187 RVA: 0x00006484 File Offset: 0x00004684
		public void method_9(string string_6)
		{
			string value = GClass6.smethod_5(GClass6.smethod_4());
			NameValueCollection nameValueCollection = new NameValueCollection();
			nameValueCollection["type"] = GClass6.smethod_0(Encoding.Default.GetBytes("log"));
			nameValueCollection["pcuser"] = GClass6.smethod_6(Environment.UserName, this.string_5, value);
			nameValueCollection["message"] = GClass6.smethod_6(string_6, this.string_5, value);
			nameValueCollection["sessionid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_4));
			nameValueCollection["name"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_0));
			nameValueCollection["ownerid"] = GClass6.smethod_0(Encoding.Default.GetBytes(this.string_1));
			nameValueCollection["init_iv"] = value;
			NameValueCollection nameValueCollection_ = nameValueCollection;
			GClass2.smethod_0(nameValueCollection_);
		}

		// Token: 0x060000BC RID: 188 RVA: 0x00006568 File Offset: 0x00004768
		private static string smethod_0(NameValueCollection nameValueCollection_0)
		{
			string result;
			try
			{
				using (WebClient webClient = new WebClient())
				{
					webClient.Headers["User-Agent"] = "KeyAuth";
					ServicePointManager.ServerCertificateValidationCallback = new RemoteCertificateValidationCallback(GClass5.smethod_0);
					byte[] bytes = webClient.UploadValues("https://keyauth.com/api/1.0/", nameValueCollection_0);
					ServicePointManager.ServerCertificateValidationCallback = (RemoteCertificateValidationCallback)Delegate.Combine(ServicePointManager.ServerCertificateValidationCallback, new RemoteCertificateValidationCallback(GClass2.Class8.<>9.method_0));
					result = Encoding.Default.GetString(bytes);
				}
			}
			catch
			{
				MessageBox.Show("SSL Pin Error. Please try again with apps that modify network activity closed/disabled.");
				Thread.Sleep(3500);
				Environment.Exit(0);
				result = "nothing";
			}
			return result;
		}

		// Token: 0x060000BD RID: 189 RVA: 0x000023DA File Offset: 0x000005DA
		private void method_10(GClass2.Class7 class7_0)
		{
			this.gclass3_0.String_0 = class7_0.String_0;
			this.gclass3_0.String_1 = class7_0.String_1;
			this.gclass3_0.List_0 = class7_0.List_0;
		}

		// Token: 0x0400023F RID: 575
		public string string_0;

		// Token: 0x04000240 RID: 576
		public string string_1;

		// Token: 0x04000241 RID: 577
		public string string_2;

		// Token: 0x04000242 RID: 578
		public string string_3;

		// Token: 0x04000243 RID: 579
		private string string_4;

		// Token: 0x04000244 RID: 580
		private string string_5;

		// Token: 0x04000245 RID: 581
		public GClass2.GClass3 gclass3_0 = new GClass2.GClass3();

		// Token: 0x04000246 RID: 582
		private GClass7 gclass7_0 = new GClass7(new GClass2.Class6());

		// Token: 0x02000020 RID: 32
		private class Class6
		{
			// Token: 0x1700002F RID: 47
			// (get) Token: 0x060000BE RID: 190 RVA: 0x0000240F File Offset: 0x0000060F
			// (set) Token: 0x060000BF RID: 191 RVA: 0x00002417 File Offset: 0x00000617
			[DataMember]
			public bool Boolean_0 { get; set; }

			// Token: 0x17000030 RID: 48
			// (get) Token: 0x060000C0 RID: 192 RVA: 0x00002420 File Offset: 0x00000620
			// (set) Token: 0x060000C1 RID: 193 RVA: 0x00002428 File Offset: 0x00000628
			[DataMember]
			public string String_0 { get; set; }

			// Token: 0x17000031 RID: 49
			// (get) Token: 0x060000C2 RID: 194 RVA: 0x00002431 File Offset: 0x00000631
			// (set) Token: 0x060000C3 RID: 195 RVA: 0x00002439 File Offset: 0x00000639
			[DataMember]
			public string String_1 { get; set; }

			// Token: 0x17000032 RID: 50
			// (get) Token: 0x060000C4 RID: 196 RVA: 0x00002442 File Offset: 0x00000642
			// (set) Token: 0x060000C5 RID: 197 RVA: 0x0000244A File Offset: 0x0000064A
			[DataMember]
			public string String_2 { get; set; }

			// Token: 0x17000033 RID: 51
			// (get) Token: 0x060000C6 RID: 198 RVA: 0x00002453 File Offset: 0x00000653
			// (set) Token: 0x060000C7 RID: 199 RVA: 0x0000245B File Offset: 0x0000065B
			[DataMember]
			public string String_3 { get; set; }

			// Token: 0x17000034 RID: 52
			// (get) Token: 0x060000C8 RID: 200 RVA: 0x00002464 File Offset: 0x00000664
			// (set) Token: 0x060000C9 RID: 201 RVA: 0x0000246C File Offset: 0x0000066C
			[DataMember]
			public string String_4 { get; set; }

			// Token: 0x17000035 RID: 53
			// (get) Token: 0x060000CA RID: 202 RVA: 0x00002475 File Offset: 0x00000675
			// (set) Token: 0x060000CB RID: 203 RVA: 0x0000247D File Offset: 0x0000067D
			[DataMember(IsRequired = false, EmitDefaultValue = false)]
			public GClass2.Class7 Class7_0 { get; set; }

			// Token: 0x04000247 RID: 583
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private bool bool_0;

			// Token: 0x04000248 RID: 584
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_0;

			// Token: 0x04000249 RID: 585
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_1;

			// Token: 0x0400024A RID: 586
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_2;

			// Token: 0x0400024B RID: 587
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private string string_3;

			// Token: 0x0400024C RID: 588
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private string string_4;

			// Token: 0x0400024D RID: 589
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private GClass2.Class7 class7_0;
		}

		// Token: 0x02000021 RID: 33
		[DataContract]
		private class Class7
		{
			// Token: 0x17000036 RID: 54
			// (get) Token: 0x060000CD RID: 205 RVA: 0x00002486 File Offset: 0x00000686
			// (set) Token: 0x060000CE RID: 206 RVA: 0x0000248E File Offset: 0x0000068E
			[DataMember]
			public string String_0 { get; set; }

			// Token: 0x17000037 RID: 55
			// (get) Token: 0x060000CF RID: 207 RVA: 0x00002497 File Offset: 0x00000697
			// (set) Token: 0x060000D0 RID: 208 RVA: 0x0000249F File Offset: 0x0000069F
			[DataMember]
			public List<GClass2.GClass4> List_0 { get; set; }

			// Token: 0x17000038 RID: 56
			// (get) Token: 0x060000D1 RID: 209 RVA: 0x000024A8 File Offset: 0x000006A8
			// (set) Token: 0x060000D2 RID: 210 RVA: 0x000024B0 File Offset: 0x000006B0
			[DataMember]
			public string String_1 { get; set; }

			// Token: 0x0400024E RID: 590
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_0;

			// Token: 0x0400024F RID: 591
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private List<GClass2.GClass4> list_0;

			// Token: 0x04000250 RID: 592
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private string string_1;
		}

		// Token: 0x02000022 RID: 34
		public class GClass3
		{
			// Token: 0x17000039 RID: 57
			// (get) Token: 0x060000D4 RID: 212 RVA: 0x000024B9 File Offset: 0x000006B9
			// (set) Token: 0x060000D5 RID: 213 RVA: 0x000024C1 File Offset: 0x000006C1
			public string String_0 { get; set; }

			// Token: 0x1700003A RID: 58
			// (get) Token: 0x060000D6 RID: 214 RVA: 0x000024CA File Offset: 0x000006CA
			// (set) Token: 0x060000D7 RID: 215 RVA: 0x000024D2 File Offset: 0x000006D2
			public List<GClass2.GClass4> List_0 { get; set; }

			// Token: 0x1700003B RID: 59
			// (get) Token: 0x060000D8 RID: 216 RVA: 0x000024DB File Offset: 0x000006DB
			// (set) Token: 0x060000D9 RID: 217 RVA: 0x000024E3 File Offset: 0x000006E3
			public string String_1 { get; set; }

			// Token: 0x04000251 RID: 593
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_0;

			// Token: 0x04000252 RID: 594
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private List<GClass2.GClass4> list_0;

			// Token: 0x04000253 RID: 595
			[CompilerGenerated]
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			private string string_1;
		}

		// Token: 0x02000023 RID: 35
		public class GClass4
		{
			// Token: 0x1700003C RID: 60
			// (get) Token: 0x060000DB RID: 219 RVA: 0x000024EC File Offset: 0x000006EC
			// (set) Token: 0x060000DC RID: 220 RVA: 0x000024F4 File Offset: 0x000006F4
			public string String_0 { get; set; }

			// Token: 0x1700003D RID: 61
			// (get) Token: 0x060000DD RID: 221 RVA: 0x000024FD File Offset: 0x000006FD
			// (set) Token: 0x060000DE RID: 222 RVA: 0x00002505 File Offset: 0x00000705
			public string String_1 { get; set; }

			// Token: 0x04000254 RID: 596
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_0;

			// Token: 0x04000255 RID: 597
			[DebuggerBrowsable(DebuggerBrowsableState.Never)]
			[CompilerGenerated]
			private string string_1;
		}

		// Token: 0x02000024 RID: 36
		[CompilerGenerated]
		[Serializable]
		private sealed class Class8
		{
			// Token: 0x060000E2 RID: 226 RVA: 0x0000663C File Offset: 0x0000483C
			internal bool method_0(object object_0, X509Certificate x509Certificate_0, X509Chain x509Chain_0, SslPolicyErrors sslPolicyErrors_0)
			{
				return true;
			}

			// Token: 0x04000256 RID: 598
			public static readonly GClass2.Class8 <>9 = new GClass2.Class8();

			// Token: 0x04000257 RID: 599
			public static RemoteCertificateValidationCallback <>9__19_0;
		}
	}
}
