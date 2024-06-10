using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using Bunifu.Framework.UI;

namespace Steam_Client_Bootstrapper
{
	// Token: 0x02000008 RID: 8
	public partial class teste : Form
	{
		// Token: 0x06000030 RID: 48 RVA: 0x00002246 File Offset: 0x00000446
		public teste()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000031 RID: 49 RVA: 0x0000225E File Offset: 0x0000045E
		private void button1_Click(object sender, EventArgs e)
		{
			MessageBox.Show("Funcionou");
		}

		// Token: 0x06000032 RID: 50 RVA: 0x0000226C File Offset: 0x0000046C
		private void teste_Load(object sender, EventArgs e)
		{
			base.KeyPreview = true;
		}

		// Token: 0x06000033 RID: 51 RVA: 0x00006230 File Offset: 0x00004430
		private void teste_KeyDown(object sender, KeyEventArgs e)
		{
			bool flag = e.Control && e.KeyCode == Keys.G;
			if (flag)
			{
				this.button1.PerformClick();
			}
		}
	}
}
