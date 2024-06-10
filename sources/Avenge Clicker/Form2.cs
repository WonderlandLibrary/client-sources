using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Bypass.My;
using Microsoft.VisualBasic.CompilerServices;

namespace Bypass
{
	// Token: 0x02000009 RID: 9
	[DesignerGenerated]
	public partial class Form2 : Form
	{
		// Token: 0x0600004F RID: 79 RVA: 0x00003708 File Offset: 0x00001B08
		public Form2()
		{
			this.InitializeComponent();
		}

		// Token: 0x17000019 RID: 25
		// (get) Token: 0x06000052 RID: 82 RVA: 0x000039A0 File Offset: 0x00001DA0
		// (set) Token: 0x06000053 RID: 83 RVA: 0x000039B4 File Offset: 0x00001DB4
		internal virtual PictureBox PictureBox1
		{
			get
			{
				return this._PictureBox1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				this._PictureBox1 = value;
			}
		}

		// Token: 0x1700001A RID: 26
		// (get) Token: 0x06000054 RID: 84 RVA: 0x000039C0 File Offset: 0x00001DC0
		// (set) Token: 0x06000055 RID: 85 RVA: 0x000039D4 File Offset: 0x00001DD4
		internal virtual Button Button1
		{
			get
			{
				return this._Button1;
			}
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button1_Click);
				if (this._Button1 != null)
				{
					this._Button1.Click -= value2;
				}
				this._Button1 = value;
				if (this._Button1 != null)
				{
					this._Button1.Click += value2;
				}
			}
		}

		// Token: 0x06000056 RID: 86 RVA: 0x00003A20 File Offset: 0x00001E20
		private void Button1_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form3.Show();
			this.Close();
		}

		// Token: 0x04000027 RID: 39
		[AccessedThroughProperty("PictureBox1")]
		private PictureBox _PictureBox1;

		// Token: 0x04000028 RID: 40
		[AccessedThroughProperty("Button1")]
		private Button _Button1;
	}
}
