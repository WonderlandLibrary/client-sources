using System;
using System.Drawing;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x0200000D RID: 13
	internal class HexTheme : ContainerControl
	{
		// Token: 0x0600003B RID: 59 RVA: 0x00002355 File Offset: 0x00000555
		public HexTheme()
		{
			this._Down = false;
			this._Header = 30;
		}

		// Token: 0x0600003C RID: 60 RVA: 0x0000236C File Offset: 0x0000056C
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (e.Y < this._Header && e.Button == MouseButtons.Left)
			{
				this._Down = true;
				this._MousePoint = e.Location;
			}
		}

		// Token: 0x0600003D RID: 61 RVA: 0x000023A3 File Offset: 0x000005A3
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this._Down = false;
		}

		// Token: 0x0600003E RID: 62 RVA: 0x000023B3 File Offset: 0x000005B3
		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			if (this._Down)
			{
				base.ParentForm.Location = Control.MousePosition - (Size)this._MousePoint;
			}
		}

		// Token: 0x0600003F RID: 63 RVA: 0x000023E4 File Offset: 0x000005E4
		protected override void OnCreateControl()
		{
			base.OnCreateControl();
			base.ParentForm.FormBorderStyle = FormBorderStyle.None;
			base.ParentForm.TransparencyKey = Color.Fuchsia;
			this.Dock = DockStyle.Fill;
			base.Invalidate();
		}

		// Token: 0x06000040 RID: 64 RVA: 0x00002FA4 File Offset: 0x000011A4
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.Clear(Color.FromArgb(47, 51, 60));
			graphics.FillRectangle(new SolidBrush(Color.FromArgb(30, 33, 40)), new Rectangle(0, this._Header, base.Width, checked(base.Height - this._Header)));
			StringFormat stringFormat = new StringFormat();
			stringFormat.Alignment = StringAlignment.Center;
			stringFormat.LineAlignment = StringAlignment.Center;
			graphics.DrawString(this.Text, new Font("Segoe UI", 11f), new SolidBrush(Color.FromArgb(236, 95, 75)), new RectangleF(0f, 0f, (float)base.Width, (float)this._Header), stringFormat);
		}

		// Token: 0x04000020 RID: 32
		private bool _Down;

		// Token: 0x04000021 RID: 33
		private int _Header;

		// Token: 0x04000022 RID: 34
		private Point _MousePoint;
	}
}
