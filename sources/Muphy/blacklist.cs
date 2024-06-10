using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Management;
using System.Net;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Muphy
{
	// Token: 0x0200000C RID: 12
	[DesignerGenerated]
	public partial class blacklist : Form
	{
		// Token: 0x06000066 RID: 102 RVA: 0x000024A4 File Offset: 0x000006A4
		public blacklist()
		{
			base.Load += this.blacklist_Load;
			this.InitializeComponent();
		}

		// Token: 0x17000034 RID: 52
		// (get) Token: 0x06000069 RID: 105 RVA: 0x000024C4 File Offset: 0x000006C4
		// (set) Token: 0x0600006A RID: 106 RVA: 0x000024CC File Offset: 0x000006CC
		internal virtual Label Label1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000035 RID: 53
		// (get) Token: 0x0600006B RID: 107 RVA: 0x000024D5 File Offset: 0x000006D5
		// (set) Token: 0x0600006C RID: 108 RVA: 0x000024DD File Offset: 0x000006DD
		internal virtual Label Label2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000036 RID: 54
		// (get) Token: 0x0600006D RID: 109 RVA: 0x000024E6 File Offset: 0x000006E6
		// (set) Token: 0x0600006E RID: 110 RVA: 0x000024EE File Offset: 0x000006EE
		internal virtual LinkLabel LinkLabel1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000037 RID: 55
		// (get) Token: 0x0600006F RID: 111 RVA: 0x000024F7 File Offset: 0x000006F7
		// (set) Token: 0x06000070 RID: 112 RVA: 0x000024FF File Offset: 0x000006FF
		internal virtual Label Label3 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000038 RID: 56
		// (get) Token: 0x06000071 RID: 113 RVA: 0x00002508 File Offset: 0x00000708
		// (set) Token: 0x06000072 RID: 114 RVA: 0x00002510 File Offset: 0x00000710
		internal virtual Label Label4 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000039 RID: 57
		// (get) Token: 0x06000073 RID: 115 RVA: 0x00002519 File Offset: 0x00000719
		// (set) Token: 0x06000074 RID: 116 RVA: 0x000042D4 File Offset: 0x000024D4
		internal virtual Timer Timer1
		{
			[CompilerGenerated]
			get
			{
				return this._Timer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				Timer timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this._Timer1 = value;
				timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x06000075 RID: 117 RVA: 0x00002521 File Offset: 0x00000721
		private void blacklist_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x06000076 RID: 118 RVA: 0x00004318 File Offset: 0x00002518
		public object GetHWID()
		{
			ManagementClass managementClass = new ManagementClass("win32_processor");
			ManagementObjectCollection instances = managementClass.GetInstances();
			try
			{
				foreach (ManagementBaseObject managementBaseObject in instances)
				{
					ManagementObject managementObject = (ManagementObject)managementBaseObject;
					if (Operators.CompareString(this.cpuInfo, "", false) == 0)
					{
						this.cpuInfo = managementObject.Properties["processorID"].Value.ToString();
						break;
					}
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator;
				if (enumerator != null)
				{
					((IDisposable)enumerator).Dispose();
				}
			}
			return this.cpuInfo;
		}

		// Token: 0x06000077 RID: 119 RVA: 0x000043B4 File Offset: 0x000025B4
		private void Timer1_Tick(object sender, EventArgs e)
		{
			HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create("https://sharpened-wine.000webhostapp.com/BLACKLIST.txt");
			HttpWebResponse httpWebResponse = (HttpWebResponse)httpWebRequest.GetResponse();
			StreamReader streamReader = new StreamReader(httpWebResponse.GetResponseStream());
			string text = streamReader.ReadToEnd();
			string value = Conversions.ToString(this.GetHWID());
			if (text.Contains(value))
			{
				this.Label4.Text = Conversions.ToString(this.GetHWID());
			}
			else
			{
				this.Label4.Text = Conversions.ToString(this.GetHWID());
			}
		}

		// Token: 0x0400002B RID: 43
		private string cpuInfo;
	}
}
