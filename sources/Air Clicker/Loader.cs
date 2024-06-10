using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Management;
using System.Net;
using System.Runtime.CompilerServices;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

// Token: 0x02000088 RID: 136
[DesignerGenerated]
public partial class Loader : Form
{
	// Token: 0x060005D0 RID: 1488 RVA: 0x0001D078 File Offset: 0x0001B278
	public Loader()
	{
		base.Closing += this.Loader_Closing;
		base.Load += this.Loader_Load;
		this.int_0 = 255;
		this.int_1 = 0;
		this.InitializeComponent();
	}

	// Token: 0x060005D1 RID: 1489 RVA: 0x0001D0C8 File Offset: 0x0001B2C8
	[DebuggerNonUserCode]
	protected virtual void Dispose(bool disposing)
	{
		try
		{
			if (disposing && this.icontainer_0 != null)
			{
				this.icontainer_0.Dispose();
			}
		}
		finally
		{
			base.Dispose(disposing);
		}
	}

	// Token: 0x17000197 RID: 407
	// (get) Token: 0x060005D3 RID: 1491 RVA: 0x0001D4A4 File Offset: 0x0001B6A4
	// (set) Token: 0x060005D4 RID: 1492 RVA: 0x0001D4AC File Offset: 0x0001B6AC
	internal virtual System.Windows.Forms.Timer Timer_0
	{
		[CompilerGenerated]
		get
		{
			return this.timer_0;
		}
		[CompilerGenerated]
		[MethodImpl(MethodImplOptions.Synchronized)]
		set
		{
			EventHandler value2 = new EventHandler(this.method_1);
			System.Windows.Forms.Timer timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick -= value2;
			}
			this.timer_0 = value;
			timer = this.timer_0;
			if (timer != null)
			{
				timer.Tick += value2;
			}
		}
	}

	// Token: 0x17000198 RID: 408
	// (get) Token: 0x060005D5 RID: 1493 RVA: 0x0001D4F0 File Offset: 0x0001B6F0
	// (set) Token: 0x060005D6 RID: 1494 RVA: 0x0001D4F8 File Offset: 0x0001B6F8
	internal virtual System.Windows.Forms.Timer Timer_1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x17000199 RID: 409
	// (get) Token: 0x060005D7 RID: 1495 RVA: 0x0001D504 File Offset: 0x0001B704
	// (set) Token: 0x060005D8 RID: 1496 RVA: 0x0001D50C File Offset: 0x0001B70C
	internal virtual System.Windows.Forms.Timer Timer_2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700019A RID: 410
	// (get) Token: 0x060005D9 RID: 1497 RVA: 0x0001D518 File Offset: 0x0001B718
	// (set) Token: 0x060005DA RID: 1498 RVA: 0x0001D520 File Offset: 0x0001B720
	internal virtual Label lblNetwork { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700019B RID: 411
	// (get) Token: 0x060005DB RID: 1499 RVA: 0x0001D52C File Offset: 0x0001B72C
	// (set) Token: 0x060005DC RID: 1500 RVA: 0x0001D534 File Offset: 0x0001B734
	internal virtual Class20 FlatLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700019C RID: 412
	// (get) Token: 0x060005DD RID: 1501 RVA: 0x0001D540 File Offset: 0x0001B740
	// (set) Token: 0x060005DE RID: 1502 RVA: 0x0001D548 File Offset: 0x0001B748
	internal virtual Panel Panel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x1700019D RID: 413
	// (get) Token: 0x060005DF RID: 1503 RVA: 0x0001D554 File Offset: 0x0001B754
	// (set) Token: 0x060005E0 RID: 1504 RVA: 0x0001D55C File Offset: 0x0001B75C
	internal virtual GControl8 ProgressBar1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

	// Token: 0x060005E1 RID: 1505 RVA: 0x0001D568 File Offset: 0x0001B768
	private void Loader_Closing(object sender, CancelEventArgs e)
	{
		Application.Exit();
	}

	// Token: 0x060005E2 RID: 1506 RVA: 0x0001D570 File Offset: 0x0001B770
	private void Loader_Load(object sender, EventArgs e)
	{
		this.method_0();
	}

	// Token: 0x060005E3 RID: 1507 RVA: 0x0001D578 File Offset: 0x0001B778
	private void method_0()
	{
		this.process_0 = Process.GetProcessesByName("AnyDesk");
		if (this.process_0.Count<Process>() > 0)
		{
			Interaction.MsgBox("Unauthorized", MsgBoxStyle.OkOnly, null);
			Application.Exit();
			return;
		}
		this.Timer_0.Start();
		this.Timer_1.Start();
	}

	// Token: 0x060005E4 RID: 1508 RVA: 0x0001D5CC File Offset: 0x0001B7CC
	private void method_1(object sender, EventArgs e)
	{
		if (this.ProgressBar1.Int32_0 == 12)
		{
			this.lblNetwork.Text = "Cracked by Syn";
			Thread.Sleep(10);
			if (Operators.CompareString(this.lblNetwork.Text, "Cracked by Syn", false) == 0)
			{
				this.lblNetwork.ForeColor = Color.White;
			}
		}
		if (this.ProgressBar1.Int32_0 == 13)
		{
			this.lblNetwork.Text = "Cracked by Syn";
			Thread.Sleep(10);
		}
		if (this.ProgressBar1.Int32_0 == 14)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 15)
		{
			this.lblNetwork.Text = "Cracked by Syn";
			Thread.Sleep(10);
		}
		if (this.ProgressBar1.Int32_0 == 16)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 17)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 18)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 19)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 20)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 40)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 45)
		{
			this.lblNetwork.Text = "Cracked by Syn";
		}
		if (this.ProgressBar1.Int32_0 == 50)
		{
			this.lblNetwork.Text = "Loaded Meth Clicker";
			Thread.Sleep(2000);
		}
		if (this.ProgressBar1.Int32_0 == 70)
		{
			this.lblNetwork.Text = "Starting Meth...";
		}
		if (this.ProgressBar1.Int32_0 == 80)
		{
			this.lblNetwork.Text = "Starting Meth...";
		}
		checked
		{
			if (this.int_1 != 100)
			{
				this.int_1++;
				this.ProgressBar1.Int32_0 = this.int_1;
				return;
			}
			Class1.Class2_0.Skeet_0.Show();
			Class1.Class2_0.Skeet_0.Location = base.Location;
			base.Hide();
			this.Timer_0.Stop();
		}
	}

	// Token: 0x040002EB RID: 747
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[AccessedThroughProperty("Load")]
	[CompilerGenerated]
	private System.Windows.Forms.Timer timer_0;

	// Token: 0x040002EC RID: 748
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("Timer1")]
	private System.Windows.Forms.Timer timer_1;

	// Token: 0x040002ED RID: 749
	[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	[CompilerGenerated]
	[AccessedThroughProperty("Timer4")]
	private System.Windows.Forms.Timer timer_2;

	// Token: 0x040002F2 RID: 754
	private Process[] process_0;

	// Token: 0x040002F3 RID: 755
	private int int_0;

	// Token: 0x040002F4 RID: 756
	private int int_1;

	// Token: 0x040002F5 RID: 757
	private string string_0;

	// Token: 0x040002F6 RID: 758
	private string string_1;

	// Token: 0x040002F7 RID: 759
	private string string_2;

	// Token: 0x040002F8 RID: 760
	private WebClient webClient_0;

	// Token: 0x040002F9 RID: 761
	private string string_3;

	// Token: 0x02000089 RID: 137
	public class GClass3
	{
		// Token: 0x060005E7 RID: 1511 RVA: 0x0001D840 File Offset: 0x0001BA40
		internal string method_0()
		{
			string result = string.Empty;
			SelectQuery objectQuery_ = Loader.GClass3.smethod_0("Win32_processor");
			ManagementObjectSearcher managementObjectSearcher_ = Loader.GClass3.smethod_1(objectQuery_);
			try
			{
				ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator = Loader.GClass3.smethod_3(Loader.GClass3.smethod_2(managementObjectSearcher_));
				while (Loader.GClass3.smethod_7(managementObjectEnumerator))
				{
					ManagementObject managementBaseObject_ = (ManagementObject)Loader.GClass3.smethod_4(managementObjectEnumerator);
					result = Loader.GClass3.smethod_6(Loader.GClass3.smethod_5(managementBaseObject_, "processorId"));
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator;
				if (managementObjectEnumerator != null)
				{
					Loader.GClass3.smethod_8(managementObjectEnumerator);
				}
			}
			return result;
		}

		// Token: 0x060005E8 RID: 1512 RVA: 0x0001D8BC File Offset: 0x0001BABC
		internal string method_1()
		{
			ManagementClass managementClass_ = Loader.GClass3.smethod_9("Win32_NetworkAdapterConfiguration");
			ManagementObjectCollection managementObjectCollection_ = Loader.GClass3.smethod_10(managementClass_);
			string text = string.Empty;
			try
			{
				ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator = Loader.GClass3.smethod_3(managementObjectCollection_);
				while (Loader.GClass3.smethod_7(managementObjectEnumerator))
				{
					ManagementObject managementObject = (ManagementObject)Loader.GClass3.smethod_4(managementObjectEnumerator);
					if (Loader.GClass3.smethod_11(text, string.Empty))
					{
						if (Loader.GClass3.smethod_12(Loader.GClass3.smethod_5(managementObject, "IPEnabled")))
						{
							text = Loader.GClass3.smethod_6(Loader.GClass3.smethod_5(managementObject, "MacAddress"));
						}
						Loader.GClass3.smethod_13(managementObject);
					}
					text = Loader.GClass3.smethod_14(text, ":", string.Empty);
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator;
				if (managementObjectEnumerator != null)
				{
					Loader.GClass3.smethod_8(managementObjectEnumerator);
				}
			}
			return text;
		}

		// Token: 0x060005E9 RID: 1513 RVA: 0x0001D96C File Offset: 0x0001BB6C
		internal string method_2(string string_0 = "C")
		{
			ManagementObject managementObject = Loader.GClass3.smethod_16(Loader.GClass3.smethod_15("win32_logicaldisk.deviceid=\"{0}:\"", string_0));
			Loader.GClass3.smethod_17(managementObject);
			return Loader.GClass3.smethod_6(Loader.GClass3.smethod_5(managementObject, "VolumeSerialNumber"));
		}

		// Token: 0x060005EA RID: 1514 RVA: 0x0001D9A4 File Offset: 0x0001BBA4
		internal string method_3()
		{
			string result = string.Empty;
			SelectQuery objectQuery_ = Loader.GClass3.smethod_0("Win32_BaseBoard");
			ManagementObjectSearcher managementObjectSearcher_ = Loader.GClass3.smethod_1(objectQuery_);
			try
			{
				ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator = Loader.GClass3.smethod_3(Loader.GClass3.smethod_2(managementObjectSearcher_));
				while (Loader.GClass3.smethod_7(managementObjectEnumerator))
				{
					ManagementObject managementBaseObject_ = (ManagementObject)Loader.GClass3.smethod_4(managementObjectEnumerator);
					result = Loader.GClass3.smethod_6(Loader.GClass3.smethod_5(managementBaseObject_, "product"));
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator;
				if (managementObjectEnumerator != null)
				{
					Loader.GClass3.smethod_8(managementObjectEnumerator);
				}
			}
			return result;
		}

		// Token: 0x060005EB RID: 1515 RVA: 0x0001DA20 File Offset: 0x0001BC20
		internal string method_4(string string_0)
		{
			MD5CryptoServiceProvider hashAlgorithm_ = Loader.GClass3.smethod_18();
			byte[] array = Loader.GClass3.smethod_20(Loader.GClass3.smethod_19(), string_0);
			array = Loader.GClass3.smethod_21(hashAlgorithm_, array);
			string text = "";
			foreach (byte b in array)
			{
				text += b.ToString("x2");
			}
			return text;
		}

		// Token: 0x060005EC RID: 1516 RVA: 0x0001DA84 File Offset: 0x0001BC84
		static SelectQuery smethod_0(string string_0)
		{
			return new SelectQuery(string_0);
		}

		// Token: 0x060005ED RID: 1517 RVA: 0x0001DA8C File Offset: 0x0001BC8C
		static ManagementObjectSearcher smethod_1(ObjectQuery objectQuery_0)
		{
			return new ManagementObjectSearcher(objectQuery_0);
		}

		// Token: 0x060005EE RID: 1518 RVA: 0x0001DA94 File Offset: 0x0001BC94
		static ManagementObjectCollection smethod_2(ManagementObjectSearcher managementObjectSearcher_0)
		{
			return managementObjectSearcher_0.Get();
		}

		// Token: 0x060005EF RID: 1519 RVA: 0x0001DA9C File Offset: 0x0001BC9C
		static ManagementObjectCollection.ManagementObjectEnumerator smethod_3(ManagementObjectCollection managementObjectCollection_0)
		{
			return managementObjectCollection_0.GetEnumerator();
		}

		// Token: 0x060005F0 RID: 1520 RVA: 0x0001DAA4 File Offset: 0x0001BCA4
		static ManagementBaseObject smethod_4(ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator_0)
		{
			return managementObjectEnumerator_0.Current;
		}

		// Token: 0x060005F1 RID: 1521 RVA: 0x0001DAAC File Offset: 0x0001BCAC
		static object smethod_5(ManagementBaseObject managementBaseObject_0, string string_0)
		{
			return managementBaseObject_0[string_0];
		}

		// Token: 0x060005F2 RID: 1522 RVA: 0x0001DAB8 File Offset: 0x0001BCB8
		static string smethod_6(object object_0)
		{
			return object_0.ToString();
		}

		// Token: 0x060005F3 RID: 1523 RVA: 0x0001DAC0 File Offset: 0x0001BCC0
		static bool smethod_7(ManagementObjectCollection.ManagementObjectEnumerator managementObjectEnumerator_0)
		{
			return managementObjectEnumerator_0.MoveNext();
		}

		// Token: 0x060005F4 RID: 1524 RVA: 0x0001DAC8 File Offset: 0x0001BCC8
		static void smethod_8(IDisposable idisposable_0)
		{
			idisposable_0.Dispose();
		}

		// Token: 0x060005F5 RID: 1525 RVA: 0x0001DAD0 File Offset: 0x0001BCD0
		static ManagementClass smethod_9(string string_0)
		{
			return new ManagementClass(string_0);
		}

		// Token: 0x060005F6 RID: 1526 RVA: 0x0001DAD8 File Offset: 0x0001BCD8
		static ManagementObjectCollection smethod_10(ManagementClass managementClass_0)
		{
			return managementClass_0.GetInstances();
		}

		// Token: 0x060005F7 RID: 1527 RVA: 0x0001DAE0 File Offset: 0x0001BCE0
		static bool smethod_11(string string_0, string string_1)
		{
			return string_0.Equals(string_1);
		}

		// Token: 0x060005F8 RID: 1528 RVA: 0x0001DAEC File Offset: 0x0001BCEC
		static bool smethod_12(object object_0)
		{
			return Conversions.ToBoolean(object_0);
		}

		// Token: 0x060005F9 RID: 1529 RVA: 0x0001DAF4 File Offset: 0x0001BCF4
		static void smethod_13(ManagementObject managementObject_0)
		{
			managementObject_0.Dispose();
		}

		// Token: 0x060005FA RID: 1530 RVA: 0x0001DAFC File Offset: 0x0001BCFC
		static string smethod_14(string string_0, string string_1, string string_2)
		{
			return string_0.Replace(string_1, string_2);
		}

		// Token: 0x060005FB RID: 1531 RVA: 0x0001DB08 File Offset: 0x0001BD08
		static string smethod_15(string string_0, object object_0)
		{
			return string.Format(string_0, object_0);
		}

		// Token: 0x060005FC RID: 1532 RVA: 0x0001DB14 File Offset: 0x0001BD14
		static ManagementObject smethod_16(string string_0)
		{
			return new ManagementObject(string_0);
		}

		// Token: 0x060005FD RID: 1533 RVA: 0x0001DB1C File Offset: 0x0001BD1C
		static void smethod_17(ManagementObject managementObject_0)
		{
			managementObject_0.Get();
		}

		// Token: 0x060005FE RID: 1534 RVA: 0x0001DB24 File Offset: 0x0001BD24
		static MD5CryptoServiceProvider smethod_18()
		{
			return new MD5CryptoServiceProvider();
		}

		// Token: 0x060005FF RID: 1535 RVA: 0x0001DB2C File Offset: 0x0001BD2C
		static Encoding smethod_19()
		{
			return Encoding.ASCII;
		}

		// Token: 0x06000600 RID: 1536 RVA: 0x0001DB34 File Offset: 0x0001BD34
		static byte[] smethod_20(Encoding encoding_0, string string_0)
		{
			return encoding_0.GetBytes(string_0);
		}

		// Token: 0x06000601 RID: 1537 RVA: 0x0001DB40 File Offset: 0x0001BD40
		static byte[] smethod_21(HashAlgorithm hashAlgorithm_0, byte[] byte_0)
		{
			return hashAlgorithm_0.ComputeHash(byte_0);
		}
	}
}
