using System;
using System.ComponentModel;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;
using Bunifu.Framework.UI;
using MetroFramework.Controls;

namespace oxybitch
{
	// Token: 0x02000004 RID: 4
	public partial class Form2 : Form
	{
		// Token: 0x06000010 RID: 16 RVA: 0x00002E86 File Offset: 0x00001086
		public Form2()
		{
			this.InitializeComponent();
		}

		// Token: 0x06000011 RID: 17 RVA: 0x000021D2 File Offset: 0x000003D2
		private void bunifuImageButton2_Click(object sender, EventArgs e)
		{
			Environment.Exit(0);
		}

		// Token: 0x06000012 RID: 18 RVA: 0x00002EA9 File Offset: 0x000010A9
		private void metroTrackBar1_Scroll(object sender, ScrollEventArgs e)
		{
			this.bunifuCustomLabel7.Text = "3." + this.metroTrackBar1.Value;
		}

		// Token: 0x06000013 RID: 19 RVA: 0x00002ED4 File Offset: 0x000010D4
		private void bunifuCheckbox1_OnChange(object sender, EventArgs e)
		{
			bool @checked = this.bunifuCheckbox1.Checked;
			if (@checked)
			{
				try
				{
					bool flag = this.bunifuCustomLabel7.Text == "3.1";
					if (flag)
					{
						double value = 3.1;
						string value2 = Convert.ToString(value);
						double value3 = Convert.ToDouble(value2);
						byte[] bytes = BitConverter.GetBytes(value3);
						string text = BitConverter.ToString(bytes).Replace("-", " ");
						IntPtr[] array = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int i = 0; i < array.Count<IntPtr>(); i++)
						{
							this.dot.WriteArray(array[i], text);
						}
					}
					bool flag2 = this.bunifuCustomLabel7.Text == "3.2";
					if (flag2)
					{
						double value4 = 3.2;
						string value5 = Convert.ToString(value4);
						double value6 = Convert.ToDouble(value5);
						byte[] bytes2 = BitConverter.GetBytes(value6);
						string text2 = BitConverter.ToString(bytes2).Replace("-", " ");
						IntPtr[] array2 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int j = 0; j < array2.Count<IntPtr>(); j++)
						{
							this.dot.WriteArray(array2[j], text2);
						}
					}
					bool flag3 = this.bunifuCustomLabel7.Text == "3.3";
					if (flag3)
					{
						double value7 = 3.3;
						string value8 = Convert.ToString(value7);
						double value9 = Convert.ToDouble(value8);
						byte[] bytes3 = BitConverter.GetBytes(value9);
						string text3 = BitConverter.ToString(bytes3).Replace("-", " ");
						IntPtr[] array3 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int k = 0; k < array3.Count<IntPtr>(); k++)
						{
							this.dot.WriteArray(array3[k], text3);
						}
					}
					bool flag4 = this.bunifuCustomLabel7.Text == "3.4";
					if (flag4)
					{
						double value10 = 3.4;
						string value11 = Convert.ToString(value10);
						double value12 = Convert.ToDouble(value11);
						byte[] bytes4 = BitConverter.GetBytes(value12);
						string text4 = BitConverter.ToString(bytes4).Replace("-", " ");
						IntPtr[] array4 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int l = 0; l < array4.Count<IntPtr>(); l++)
						{
							this.dot.WriteArray(array4[l], text4);
						}
					}
					bool flag5 = this.bunifuCustomLabel7.Text == "3.5";
					if (flag5)
					{
						double value13 = 3.5;
						string value14 = Convert.ToString(value13);
						double value15 = Convert.ToDouble(value14);
						byte[] bytes5 = BitConverter.GetBytes(value15);
						string text5 = BitConverter.ToString(bytes5).Replace("-", " ");
						IntPtr[] array5 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int m = 0; m < array5.Count<IntPtr>(); m++)
						{
							this.dot.WriteArray(array5[m], text5);
						}
					}
					bool flag6 = this.bunifuCustomLabel7.Text == "3.6";
					if (flag6)
					{
						double value16 = 3.6;
						string value17 = Convert.ToString(value16);
						double value18 = Convert.ToDouble(value17);
						byte[] bytes6 = BitConverter.GetBytes(value18);
						string text6 = BitConverter.ToString(bytes6).Replace("-", " ");
						IntPtr[] array6 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int n = 0; n < array6.Count<IntPtr>(); n++)
						{
							this.dot.WriteArray(array6[n], text6);
						}
					}
					bool flag7 = this.bunifuCustomLabel7.Text == "3.7";
					if (flag7)
					{
						double value19 = 3.7;
						string value20 = Convert.ToString(value19);
						double value21 = Convert.ToDouble(value20);
						byte[] bytes7 = BitConverter.GetBytes(value21);
						string text7 = BitConverter.ToString(bytes7).Replace("-", " ");
						IntPtr[] array7 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int num = 0; num < array7.Count<IntPtr>(); num++)
						{
							this.dot.WriteArray(array7[num], text7);
						}
					}
					bool flag8 = this.bunifuCustomLabel7.Text == "3.8";
					if (flag8)
					{
						double value22 = 3.8;
						string value23 = Convert.ToString(value22);
						double value24 = Convert.ToDouble(value23);
						byte[] bytes8 = BitConverter.GetBytes(value24);
						string text8 = BitConverter.ToString(bytes8).Replace("-", " ");
						IntPtr[] array8 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int num2 = 0; num2 < array8.Count<IntPtr>(); num2++)
						{
							this.dot.WriteArray(array8[num2], text8);
						}
					}
					bool flag9 = this.bunifuCustomLabel7.Text == "3.9";
					if (flag9)
					{
						double value25 = 3.9;
						string value26 = Convert.ToString(value25);
						double value27 = Convert.ToDouble(value26);
						byte[] bytes9 = BitConverter.GetBytes(value27);
						string text9 = BitConverter.ToString(bytes9).Replace("-", " ");
						IntPtr[] array9 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int num3 = 0; num3 < array9.Count<IntPtr>(); num3++)
						{
							this.dot.WriteArray(array9[num3], text9);
						}
					}
					MessageBox.Show("Injetado.", "Aviso");
				}
				catch
				{
				}
			}
			else
			{
				bool flag10 = !this.bunifuCheckbox1.Checked;
				if (flag10)
				{
					try
					{
						string value28 = "3.0";
						double value29 = Convert.ToDouble(value28);
						byte[] bytes10 = BitConverter.GetBytes(value29);
						string text10 = BitConverter.ToString(bytes10).Replace("-", " ");
						IntPtr[] array10 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 00 08 40 00 00 00 00 00");
						for (int num4 = 0; num4 < array10.Count<IntPtr>(); num4++)
						{
							this.dot.WriteArray(array10[num4], text10);
						}
						MessageBox.Show("Desinjetado.", "Aviso");
					}
					catch
					{
					}
				}
			}
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00003630 File Offset: 0x00001830
		private void bunifuCheckbox2_OnChange(object sender, EventArgs e)
		{
			bool @checked = this.bunifuCheckbox2.Checked;
			if (@checked)
			{
				try
				{
					string value = "8020";
					double value2 = Convert.ToDouble(value);
					byte[] bytes = BitConverter.GetBytes(value2);
					string text = BitConverter.ToString(bytes).Replace("-", " ");
					IntPtr[] array = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
					for (int i = 0; i < array.Count<IntPtr>(); i++)
					{
						this.dot.WriteArray(array[i], text);
					}
					MessageBox.Show("Injetado.", "Aviso");
				}
				catch
				{
				}
			}
			else
			{
				bool flag = !this.bunifuCheckbox2.Checked;
				if (flag)
				{
					try
					{
						string value3 = "8000";
						double value4 = Convert.ToDouble(value3);
						byte[] bytes2 = BitConverter.GetBytes(value4);
						string text2 = BitConverter.ToString(bytes2).Replace("-", " ");
						IntPtr[] array2 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
						for (int j = 0; j < array2.Count<IntPtr>(); j++)
						{
							this.dot.WriteArray(array2[j], text2);
						}
						MessageBox.Show("Desinjetado.", "Aviso");
					}
					catch
					{
					}
				}
			}
		}

		// Token: 0x06000015 RID: 21 RVA: 0x000037C0 File Offset: 0x000019C0
		private void bunifuCheckbox3_OnChange(object sender, EventArgs e)
		{
			bool flag = !this.bunifuCheckbox2.Checked;
			if (flag)
			{
				bool @checked = this.bunifuCheckbox3.Checked;
				if (@checked)
				{
					try
					{
						string value = "24000";
						double value2 = Convert.ToDouble(value);
						byte[] bytes = BitConverter.GetBytes(value2);
						string text = BitConverter.ToString(bytes).Replace("-", " ");
						IntPtr[] array = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
						for (int i = 0; i < array.Count<IntPtr>(); i++)
						{
							this.dot.WriteArray(array[i], text);
						}
					}
					catch
					{
					}
				}
				else
				{
					bool flag2 = !this.bunifuCheckbox3.Checked;
					if (flag2)
					{
						try
						{
							string value3 = "8000";
							double value4 = Convert.ToDouble(value3);
							byte[] bytes2 = BitConverter.GetBytes(value4);
							string text2 = BitConverter.ToString(bytes2).Replace("-", " ");
							IntPtr[] array2 = this.dot.ScanArray(this.dot.GetPID("javaw"), "00 00 00 00 00 40 BF 40");
							for (int j = 0; j < array2.Count<IntPtr>(); j++)
							{
								this.dot.WriteArray(array2[j], text2);
							}
						}
						catch
						{
						}
					}
				}
			}
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00003948 File Offset: 0x00001B48
		private void bunifuFlatButton1_Click(object sender, EventArgs e)
		{
			MessageBox.Show("SelfDestructing", "Aviso");
			try
			{
				double value = 3.0;
				string value2 = Convert.ToString(value);
				double value3 = Convert.ToDouble(value2);
				byte[] bytes = BitConverter.GetBytes(value3);
				string text = BitConverter.ToString(bytes).Replace("-", " ");
				IntPtr[] array = this.dot.ScanArray(this.dot.GetPID("javaw"), text);
				for (int i = 0; i < array.Count<IntPtr>(); i++)
				{
					this.dot.WriteArray(array[i], "00 00 00 00 00 00 08 40 00 00 00 00 00");
				}
			}
			catch
			{
			}
			try
			{
				string value4 = "8000";
				double value5 = Convert.ToDouble(value4);
				byte[] bytes2 = BitConverter.GetBytes(value5);
				string text2 = BitConverter.ToString(bytes2).Replace("-", " ");
				IntPtr[] array2 = this.dot.ScanArray(this.dot.GetPID("javaw"), text2);
				for (int j = 0; j < array2.Count<IntPtr>(); j++)
				{
					this.dot.WriteArray(array2[j], "00 00 00 00 00 40 BF 40");
				}
			}
			catch
			{
			}
			Environment.Exit(0);
		}

		// Token: 0x06000017 RID: 23 RVA: 0x00002110 File Offset: 0x00000310
		private void bunifuImageButton1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x04000014 RID: 20
		private DotNetScanMemory_SmoLL dot = new DotNetScanMemory_SmoLL();

        private void BunifuCustomLabel4_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }
    }
}
