using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x0200003D RID: 61
	[DesignerGenerated]
	public partial class Form5 : Form
	{
		// Token: 0x0600054E RID: 1358 RVA: 0x000054D4 File Offset: 0x000036D4
		public Form5()
		{
			base.Load += this.Form5_Load;
			this.InitializeComponent();
		}

		// Token: 0x170001EA RID: 490
		// (get) Token: 0x06000551 RID: 1361 RVA: 0x000054F4 File Offset: 0x000036F4
		// (set) Token: 0x06000552 RID: 1362 RVA: 0x000054FC File Offset: 0x000036FC
		internal virtual Timer Timer1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x06000553 RID: 1363 RVA: 0x000235D8 File Offset: 0x000217D8
		private void Form5_Load(object sender, EventArgs e)
		{
			base.Location = new Point(checked(Screen.PrimaryScreen.WorkingArea.Width - base.Width), 0);
		}
	}
}
