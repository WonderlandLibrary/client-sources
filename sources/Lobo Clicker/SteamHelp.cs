using System;
using System.ComponentModel;
using System.Drawing;
using System.Threading;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000007 RID: 7
	public partial class SteamHelp : Form
	{
		// Token: 0x06000029 RID: 41 RVA: 0x000021D2 File Offset: 0x000003D2
		public SteamHelp()
		{
			this.InitializeComponent();
		}

		// Token: 0x0600002A RID: 42 RVA: 0x000021EA File Offset: 0x000003EA
		private void webBrowser2_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
		{
			this.webBrowser1.Navigate("https://help.steampowered.com/pt-br/wizard/HelpWithLogin?redir=clientlogin");
		}

		// Token: 0x0600002B RID: 43 RVA: 0x000021FE File Offset: 0x000003FE
		private void label6_Click(object sender, EventArgs e)
		{
			this.kgr = new Thread(new ThreadStart(this.novoform1999));
			this.kgr.SetApartmentState(ApartmentState.STA);
			this.kgr.Start();
			base.Close();
		}

		// Token: 0x0600002C RID: 44 RVA: 0x00002238 File Offset: 0x00000438
		private void novoform1999()
		{
			Application.Run(new Steam());
		}

		// Token: 0x0600002D RID: 45 RVA: 0x000020D0 File Offset: 0x000002D0
		private void label8_Click(object sender, EventArgs e)
		{
			base.WindowState = FormWindowState.Minimized;
		}

		// Token: 0x04000044 RID: 68
		private Thread kgr;
	}
}
