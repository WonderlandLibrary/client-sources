namespace svchost
{
	// Token: 0x0200000B RID: 11
	[global::Microsoft.VisualBasic.CompilerServices.DesignerGenerated]
	public partial class cpshandler : global::System.Windows.Forms.Form
	{
		// Token: 0x06000047 RID: 71 RVA: 0x00003F44 File Offset: 0x00002144
		[global::System.Diagnostics.DebuggerNonUserCode]
		protected override void Dispose(bool disposing)
		{
			try
			{
				bool flag = disposing && this.components != null;
				if (flag)
				{
					this.components.Dispose();
				}
			}
			finally
			{
				base.Dispose(disposing);
			}
		}

		// Token: 0x06000048 RID: 72 RVA: 0x00003F94 File Offset: 0x00002194
		[global::System.Diagnostics.DebuggerStepThrough]
		private void InitializeComponent()
		{
			this.components = new global::System.ComponentModel.Container();
			this.Button1 = new global::System.Windows.Forms.Button();
			this.clickingspeed = new global::System.Windows.Forms.Timer(this.components);
			this.reset = new global::System.Windows.Forms.Timer(this.components);
			base.SuspendLayout();
			this.Button1.Dock = global::System.Windows.Forms.DockStyle.Fill;
			this.Button1.Location = new global::System.Drawing.Point(0, 0);
			this.Button1.Name = "Button1";
			this.Button1.Size = new global::System.Drawing.Size(0, 0);
			this.Button1.TabIndex = 0;
			this.Button1.Text = "Button1";
			this.Button1.UseVisualStyleBackColor = true;
			this.clickingspeed.Enabled = true;
			this.clickingspeed.Interval = 1;
			this.reset.Enabled = true;
			this.reset.Interval = 1000;
			base.AutoScaleDimensions = new global::System.Drawing.SizeF(6f, 13f);
			base.AutoScaleMode = global::System.Windows.Forms.AutoScaleMode.Font;
			base.ClientSize = new global::System.Drawing.Size(0, 0);
			base.Controls.Add(this.Button1);
			base.FormBorderStyle = global::System.Windows.Forms.FormBorderStyle.None;
			base.Name = "cpshandler";
			base.ShowIcon = false;
			base.ShowInTaskbar = false;
			this.Text = "cpshandler";
			base.WindowState = global::System.Windows.Forms.FormWindowState.Minimized;
			base.ResumeLayout(false);
		}

		// Token: 0x0400000F RID: 15
		private global::System.ComponentModel.IContainer components;
	}
}
