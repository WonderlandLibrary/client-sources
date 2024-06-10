namespace AutoClicker
{
	// Token: 0x0200000D RID: 13
	public partial class SelectionForm : global::System.Windows.Forms.Form
	{
		// Token: 0x06000090 RID: 144 RVA: 0x00009A47 File Offset: 0x00007C47
		protected override void Dispose(bool disposing)
		{
			if (disposing && this.components != null)
			{
				this.components.Dispose();
			}
			base.Dispose(disposing);
		}

		// Token: 0x06000091 RID: 145 RVA: 0x00009A68 File Offset: 0x00007C68
		private void InitializeComponent()
		{
			base.SuspendLayout();
			base.AutoScaleDimensions = new global::System.Drawing.SizeF(6f, 12f);
			base.AutoScaleMode = global::System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = global::System.Drawing.SystemColors.MenuHighlight;
			base.ClientSize = new global::System.Drawing.Size(284, 261);
			base.FormBorderStyle = global::System.Windows.Forms.FormBorderStyle.None;
			base.Name = "SelectionForm";
			base.Opacity = 0.1;
			this.Text = "SelectionForm";
			base.ResumeLayout(false);
		}

		// Token: 0x040000C7 RID: 199
		private global::System.ComponentModel.IContainer components;
	}
}
