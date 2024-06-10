using System;
using System.ComponentModel;
using System.Drawing;
using System.Net;
using System.Runtime.InteropServices;
using System.Security.Cryptography;
using System.Security.Principal;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x02000005 RID: 5
	public partial class Form2 : Form
	{
		// Token: 0x06000037 RID: 55 RVA: 0x0000207E File Offset: 0x0000027E
		public Form2()
		{
			<Module>.Class0.smethod_0();
			this.webClient_0 = new WebClient();
			this.icontainer_0 = null;
			base..ctor();
			this.InitializeComponent();
		}

		// Token: 0x06000038 RID: 56
		[DllImport("user32.dll")]
		public static extern bool ReleaseCapture();

		// Token: 0x06000039 RID: 57
		[DllImport("user32.dll")]
		public static extern int SendMessage(IntPtr intptr_0, int int_2, int int_3, int int_4);

		// Token: 0x0600003A RID: 58 RVA: 0x00012EDC File Offset: 0x000110DC
		private void skeetButton1_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 2);
			try
			{
				string value = WindowsIdentity.GetCurrent().User.Value;
				if (value.Length < 10)
				{
					MessageBox.Show("Error: 0x001");
					Environment.Exit(0);
				}
				string text = this.webClient_0.DownloadString("http://a555.altervista.org/docs/solve.php?error=" + value);
				if (text.Contains(Form2.smethod_1(value + "ABC")))
				{
					Form2.bool_0 = false;
					Thread thread = new Thread(new ThreadStart(Form2.smethod_0));
					thread.Start();
					base.Hide();
				}
				else
				{
					MessageBox.Show("Not licensed.");
				}
			}
			catch
			{
				MessageBox.Show("Error: Please check your internet.");
			}
		}

		// Token: 0x0600003B RID: 59 RVA: 0x00013010 File Offset: 0x00011210
		[STAThread]
		public static void smethod_0()
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					Application.Exit();
					num = 4;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					Application.Run(new Form1());
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
		}

		// Token: 0x0600003C RID: 60 RVA: 0x000130A8 File Offset: 0x000112A8
		public static string smethod_1(string string_0)
		{
			int num = 0;
			MD5 md;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					md = MD5.Create();
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
			string result;
			try
			{
				byte[] bytes = Encoding.ASCII.GetBytes(string_0);
				byte[] array = md.ComputeHash(bytes);
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < array.Length; i++)
				{
					stringBuilder.Append(array[i].ToString("X2"));
				}
				result = stringBuilder.ToString().ToLower();
			}
			finally
			{
				if (md != null)
				{
					((IDisposable)md).Dispose();
				}
			}
			return result;
		}

		// Token: 0x0600003D RID: 61 RVA: 0x000131D0 File Offset: 0x000113D0
		private void skeetButton2_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					Clipboard.SetText(WindowsIdentity.GetCurrent().User.Value);
					num = 3;
				}
				if (num == 3)
				{
					MessageBox.Show("Copied.");
					num = 4;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 4);
		}

		// Token: 0x0600003E RID: 62 RVA: 0x00013280 File Offset: 0x00011480
		private void transparentPanel1_MouseMove(object sender, MouseEventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 3)
				{
					bool flag;
					if (!flag)
					{
						break;
					}
					num = 4;
				}
				if (num == 5)
				{
					Form2.SendMessage(base.Handle, 161, 2, 0);
					num = 6;
				}
				if (num == 2)
				{
					bool flag = e.Button == MouseButtons.Left;
					num = 3;
				}
				if (num == 4)
				{
					Form2.ReleaseCapture();
					num = 5;
				}
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 6);
		}

		// Token: 0x0600003F RID: 63 RVA: 0x0000B9E8 File Offset: 0x00009BE8
		private void label4_Click(object sender, EventArgs e)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					Environment.Exit(0);
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
		}

		// Token: 0x06000040 RID: 64 RVA: 0x0001337C File Offset: 0x0001157C
		protected virtual void Dispose(bool disposing)
		{
			int num = 0;
			do
			{
				if (num == 1)
				{
					<Module>.Class0.smethod_0();
					num = 2;
				}
				if (num == 2)
				{
					if (!disposing)
					{
						goto IL_6B;
					}
					num = 3;
				}
				if (num == 0)
				{
					num = 1;
				}
			}
			while (num != 3);
			bool flag = this.icontainer_0 != null;
			goto IL_7E;
			IL_6B:
			flag = false;
			IL_7E:
			if (flag)
			{
				this.icontainer_0.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x06000042 RID: 66 RVA: 0x000020A3 File Offset: 0x000002A3
		// Note: this type is marked as 'beforefieldinit'.
		static Form2()
		{
			<Module>.Class0.smethod_0();
			Form2.bool_0 = true;
		}

		// Token: 0x04000045 RID: 69
		private WebClient webClient_0;

		// Token: 0x04000046 RID: 70
		private const int int_0 = 161;

		// Token: 0x04000047 RID: 71
		private const int int_1 = 2;

		// Token: 0x04000048 RID: 72
		public static bool bool_0;

		// Token: 0x04000049 RID: 73
		private IContainer icontainer_0;
	}
}
