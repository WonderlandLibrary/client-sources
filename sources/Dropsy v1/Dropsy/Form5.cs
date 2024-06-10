using System;
using System.ComponentModel;
using System.Drawing;
using System.Security.Cryptography;
using System.Text;
using System.Windows.Forms;

namespace Dropsy
{
	// Token: 0x02000016 RID: 22
	public partial class Form5 : Form
	{
		// Token: 0x06000075 RID: 117 RVA: 0x0000542D File Offset: 0x0000382D
		public Form5()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000076 RID: 118 RVA: 0x0000543C File Offset: 0x0000383C
		private static string sha256(string randomString)
		{
			HashAlgorithm hashAlgorithm = new SHA256Managed();
			string text = string.Empty;
			foreach (byte b in hashAlgorithm.ComputeHash(Encoding.ASCII.GetBytes(randomString)))
			{
				text += b.ToString("x2");
			}
			return text;
		}

		// Token: 0x06000077 RID: 119 RVA: 0x00003129 File Offset: 0x00001529
		private void Form5_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x06000078 RID: 120 RVA: 0x0000548B File Offset: 0x0000388B
		private void TickClose_Tick(object sender, EventArgs e)
		{
			this.TickClose.Stop();
			base.Hide();
		}

		// Token: 0x06000079 RID: 121 RVA: 0x00003129 File Offset: 0x00001529
		private void timer_Tick(object sender, EventArgs e)
		{
		}

		// Token: 0x0600007A RID: 122 RVA: 0x00003129 File Offset: 0x00001529
		private void timer2_Tick(object sender, EventArgs e)
		{
		}
	}
}
