using System;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using Guna.UI.WinForms;

// Token: 0x02000015 RID: 21
public partial class Form1 : Form
{
	// Token: 0x06000049 RID: 73 RVA: 0x00008A18 File Offset: 0x00006C18
	public Form1()
	{
		this.InitializeComponent();
	}

	// Token: 0x0600004A RID: 74 RVA: 0x00006C50 File Offset: 0x00004E50
	private void Form1_Load(object sender, EventArgs e)
	{
	}

	// Token: 0x0600004B RID: 75 RVA: 0x00008A44 File Offset: 0x00006C44
	private void button2_Click(object sender, EventArgs e)
	{
		Environment.Exit(0);
	}

	// Token: 0x0600004C RID: 76 RVA: 0x00008A58 File Offset: 0x00006C58
	private void gunaCheckBox2_CheckedChanged(object sender, EventArgs e)
	{
		new DotNetScanMemory_SmoLL();
		string text = BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(Convert.ToString(3.9)))).Replace("-", " ");
		IntPtr[] array = this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
		for (int i = 0; i < array.Count<IntPtr>(); i++)
		{
			this.dotNetScanMemory_SmoLL_0.WriteArray(array[i], text);
		}
		MessageBox.Show("Show!!", "breks??, é o best");
		BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(Convert.ToString(3.7)))).Replace("-", " ");
		for (int j = 0; j < this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID("CheatBreaker"), "00 00 00 00 00 00 08 40 00 00 40 40 40").Count<IntPtr>(); j++)
		{
			this.dotNetScanMemory_SmoLL_0.WriteArray(this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00")[j], BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(Convert.ToString(3.9)))).Replace("-", " "));
		}
	}

	// Token: 0x0600004D RID: 77 RVA: 0x00008BB0 File Offset: 0x00006DB0
	private void gunaCheckBox1_CheckedChanged(object sender, EventArgs e)
	{
		string text = BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(1100))).Replace("-", " ");
		IntPtr[] array = this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
		for (int i = 0; i < array.Count<IntPtr>(); i++)
		{
			this.dotNetScanMemory_SmoLL_0.WriteArray(array[i], text);
		}
		BitConverter.ToString(BitConverter.GetBytes(Convert.ToDouble(1100))).Replace("-", " ");
		this.dotNetScanMemory_SmoLL_0.ScanArray(this.dotNetScanMemory_SmoLL_0.GetPID("CheatBreaker"), "00 00 00 00 00 40 BF 40 40 40 40");
		for (int j = 0; j < array.Count<IntPtr>(); j++)
		{
			this.dotNetScanMemory_SmoLL_0.WriteArray(array[j], text);
		}
	}

	// Token: 0x0600004E RID: 78 RVA: 0x00006C50 File Offset: 0x00004E50
	private void gunaPanel1_Paint(object sender, PaintEventArgs e)
	{
	}

	// Token: 0x0600004F RID: 79 RVA: 0x00008C94 File Offset: 0x00006E94
	protected virtual void Dispose(bool disposing)
	{
		if (disposing && this.icontainer_0 != null)
		{
			this.icontainer_0.Dispose();
		}
		base.Dispose(disposing);
	}

	// Token: 0x04000041 RID: 65
	private DotNetScanMemory_SmoLL dotNetScanMemory_SmoLL_0 = new DotNetScanMemory_SmoLL();

	// Token: 0x04000042 RID: 66
	private IContainer icontainer_0 = null;
}
