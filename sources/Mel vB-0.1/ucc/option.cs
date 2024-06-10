using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using Microsoft.Win32;
using name.Properties;

namespace name.ucc
{
	// Token: 0x02000007 RID: 7
	public class option : UserControl
	{
		// Token: 0x17000005 RID: 5
		// (get) Token: 0x0600001D RID: 29 RVA: 0x00003FAC File Offset: 0x000021AC
		public static option Instance
		{
			get
			{
				bool flag = option._instance == null;
				if (flag)
				{
					option._instance = new option();
				}
				return option._instance;
			}
		}

		// Token: 0x0600001E RID: 30 RVA: 0x000020CB File Offset: 0x000002CB
		public option()
		{
			this.InitializeComponent();
			this.label5.Text = option.GetMachineGuid();
		}

		// Token: 0x0600001F RID: 31 RVA: 0x00002504 File Offset: 0x00000704
		public static string GetMachineGuid()
		{
			string text = "SOFTWARE\\Microsoft\\Cryptography";
			string text2 = "MachineGuid";
			string result;
			using (RegistryKey registryKey = RegistryKey.OpenBaseKey(RegistryHive.LocalMachine, RegistryView.Registry64))
			{
				using (RegistryKey registryKey2 = registryKey.OpenSubKey(text))
				{
					bool flag = registryKey2 == null;
					if (flag)
					{
						throw new KeyNotFoundException(string.Format("Key Not Found: {0}", text));
					}
					object value = registryKey2.GetValue(text2);
					bool flag2 = value == null;
					if (flag2)
					{
						throw new IndexOutOfRangeException(string.Format("Index Not Found: {0}", text2));
					}
					result = value.ToString();
				}
			}
			return result;
		}

		// Token: 0x06000020 RID: 32 RVA: 0x00003FDC File Offset: 0x000021DC
		protected override void Dispose(bool disposing)
		{
			bool flag = disposing && this.components != null;
			if (flag)
			{
				this.components.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x06000021 RID: 33 RVA: 0x00004014 File Offset: 0x00002214
		private void InitializeComponent()
		{
			ComponentResourceManager componentResourceManager = new ComponentResourceManager(typeof(option));
			this.panel3 = new Panel();
			this.pictureBox1 = new PictureBox();
			this.label7 = new Label();
			this.label5 = new Label();
			this.label4 = new Label();
			this.label3 = new Label();
			this.label2 = new Label();
			this.label1 = new Label();
			this.pictureBox4 = new PictureBox();
			this.label6 = new Label();
			this.panel3.SuspendLayout();
			((ISupportInitialize)this.pictureBox1).BeginInit();
			((ISupportInitialize)this.pictureBox4).BeginInit();
			base.SuspendLayout();
			this.panel3.BackColor = Color.FromArgb(25, 25, 25);
			this.panel3.BackgroundImageLayout = ImageLayout.Stretch;
			this.panel3.Controls.Add(this.pictureBox1);
			this.panel3.Controls.Add(this.label7);
			this.panel3.Controls.Add(this.label5);
			this.panel3.Controls.Add(this.label4);
			this.panel3.Controls.Add(this.label3);
			this.panel3.Controls.Add(this.label2);
			this.panel3.Controls.Add(this.label1);
			this.panel3.Controls.Add(this.pictureBox4);
			this.panel3.Controls.Add(this.label6);
			this.panel3.Location = new Point(12, 12);
			this.panel3.Name = "panel3";
			this.panel3.Size = new Size(354, 124);
			this.panel3.TabIndex = 46;
			this.pictureBox1.BackColor = Color.Transparent;
			this.pictureBox1.BackgroundImage = (Image)componentResourceManager.GetObject("pictureBox1.BackgroundImage");
			this.pictureBox1.BackgroundImageLayout = ImageLayout.Stretch;
			this.pictureBox1.Location = new Point(14, 11);
			this.pictureBox1.Name = "pictureBox1";
			this.pictureBox1.Size = new Size(20, 20);
			this.pictureBox1.TabIndex = 72;
			this.pictureBox1.TabStop = false;
			this.label7.AutoSize = true;
			this.label7.BackColor = Color.Transparent;
			this.label7.DataBindings.Add(new Binding("Text", Settings.Default, "c", true, DataSourceUpdateMode.OnPropertyChanged));
			this.label7.Font = new Font("Roboto", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label7.ForeColor = Color.Silver;
			this.label7.Location = new Point(105, 75);
			this.label7.Name = "label7";
			this.label7.Size = new Size(58, 13);
			this.label7.TabIndex = 71;
			this.label7.Text = Settings.Default.c;
			this.label5.AutoSize = true;
			this.label5.BackColor = Color.Transparent;
			this.label5.Font = new Font("Roboto", 9f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label5.ForeColor = Color.Silver;
			this.label5.Location = new Point(105, 96);
			this.label5.Name = "label5";
			this.label5.Size = new Size(35, 14);
			this.label5.TabIndex = 70;
			this.label5.Text = "Hwid";
			this.label4.AutoSize = true;
			this.label4.BackColor = Color.Transparent;
			this.label4.Font = new Font("Roboto", 9f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label4.ForeColor = Color.White;
			this.label4.Location = new Point(8, 96);
			this.label4.Name = "label4";
			this.label4.Size = new Size(35, 14);
			this.label4.TabIndex = 69;
			this.label4.Text = "Hwid";
			this.label3.AutoSize = true;
			this.label3.BackColor = Color.Transparent;
			this.label3.Font = new Font("Roboto", 9f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label3.ForeColor = Color.White;
			this.label3.Location = new Point(8, 74);
			this.label3.Name = "label3";
			this.label3.Size = new Size(31, 14);
			this.label3.TabIndex = 68;
			this.label3.Text = "User";
			this.label2.AutoSize = true;
			this.label2.BackColor = Color.Transparent;
			this.label2.Font = new Font("Roboto", 9f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label2.ForeColor = Color.White;
			this.label2.Location = new Point(8, 51);
			this.label2.Name = "label2";
			this.label2.Size = new Size(54, 14);
			this.label2.TabIndex = 67;
			this.label2.Text = "Days left";
			this.label1.AutoSize = true;
			this.label1.BackColor = Color.Transparent;
			this.label1.Font = new Font("Roboto", 8.25f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label1.ForeColor = Color.Silver;
			this.label1.Location = new Point(105, 52);
			this.label1.Name = "label1";
			this.label1.Size = new Size(57, 13);
			this.label1.TabIndex = 66;
			this.label1.Text = "fixed soon";
			this.pictureBox4.BackColor = Color.White;
			this.pictureBox4.Location = new Point(11, 36);
			this.pictureBox4.Name = "pictureBox4";
			this.pictureBox4.Size = new Size(331, 1);
			this.pictureBox4.TabIndex = 1;
			this.pictureBox4.TabStop = false;
			this.label6.AutoSize = true;
			this.label6.BackColor = Color.Transparent;
			this.label6.Font = new Font("Open Sans", 9.75f, FontStyle.Regular, GraphicsUnit.Point, 0);
			this.label6.ForeColor = Color.White;
			this.label6.Location = new Point(34, 13);
			this.label6.Name = "label6";
			this.label6.Size = new Size(87, 18);
			this.label6.TabIndex = 0;
			this.label6.Text = "Shitty skid account";
			base.AutoScaleDimensions = new SizeF(6f, 13f);
			base.AutoScaleMode = AutoScaleMode.Font;
			this.BackColor = Color.FromArgb(29, 29, 29);
			base.Controls.Add(this.panel3);
			base.Name = "option";
			base.Size = new Size(589, 302);
			this.panel3.ResumeLayout(false);
			this.panel3.PerformLayout();
			((ISupportInitialize)this.pictureBox1).EndInit();
			((ISupportInitialize)this.pictureBox4).EndInit();
			base.ResumeLayout(false);
		}

		// Token: 0x04000020 RID: 32
		private static option _instance;

		// Token: 0x04000021 RID: 33
		private IContainer components = null;

		// Token: 0x04000022 RID: 34
		private Panel panel3;

		// Token: 0x04000023 RID: 35
		private PictureBox pictureBox1;

		// Token: 0x04000024 RID: 36
		private Label label7;

		// Token: 0x04000025 RID: 37
		private Label label5;

		// Token: 0x04000026 RID: 38
		private Label label4;

		// Token: 0x04000027 RID: 39
		private Label label3;

		// Token: 0x04000028 RID: 40
		private Label label2;

		// Token: 0x04000029 RID: 41
		private Label label1;

		// Token: 0x0400002A RID: 42
		private PictureBox pictureBox4;

		// Token: 0x0400002B RID: 43
		private Label label6;
	}
}
