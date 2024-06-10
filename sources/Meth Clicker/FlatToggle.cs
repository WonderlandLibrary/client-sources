using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x0200001E RID: 30
	[DefaultEvent("CheckedChanged")]
	internal class FlatToggle : Control
	{
		// Token: 0x14000014 RID: 20
		// (add) Token: 0x06000238 RID: 568 RVA: 0x00010C40 File Offset: 0x0000EE40
		// (remove) Token: 0x06000239 RID: 569 RVA: 0x00010C78 File Offset: 0x0000EE78
		public event FlatToggle.CheckedChangedEventHandler CheckedChanged;

		// Token: 0x170000AD RID: 173
		// (get) Token: 0x0600023A RID: 570 RVA: 0x00010CAD File Offset: 0x0000EEAD
		// (set) Token: 0x0600023B RID: 571 RVA: 0x00010CB5 File Offset: 0x0000EEB5
		[Category("Options")]
		public FlatToggle._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		// Token: 0x170000AE RID: 174
		// (get) Token: 0x0600023C RID: 572 RVA: 0x00010CBE File Offset: 0x0000EEBE
		// (set) Token: 0x0600023D RID: 573 RVA: 0x00010CC6 File Offset: 0x0000EEC6
		[Category("Options")]
		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
			}
		}

		// Token: 0x0600023E RID: 574 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x0600023F RID: 575 RVA: 0x00010CCF File Offset: 0x0000EECF
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Width = 76;
			base.Height = 33;
		}

		// Token: 0x06000240 RID: 576 RVA: 0x00010CE8 File Offset: 0x0000EEE8
		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000241 RID: 577 RVA: 0x00010CFE File Offset: 0x0000EEFE
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		// Token: 0x06000242 RID: 578 RVA: 0x00010D14 File Offset: 0x0000EF14
		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		// Token: 0x06000243 RID: 579 RVA: 0x00010D2A File Offset: 0x0000EF2A
		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		// Token: 0x06000244 RID: 580 RVA: 0x00010D40 File Offset: 0x0000EF40
		protected override void OnClick(EventArgs e)
		{
			base.OnClick(e);
			this._Checked = !this._Checked;
			FlatToggle.CheckedChangedEventHandler checkedChangedEvent = this.CheckedChangedEvent;
			if (checkedChangedEvent != null)
			{
				checkedChangedEvent(this);
			}
		}

		// Token: 0x06000245 RID: 581 RVA: 0x00010D74 File Offset: 0x0000EF74
		public FlatToggle()
		{
			this._Checked = false;
			this.State = MouseState.None;
			this.BaseColor = Helpers._FlatColor;
			this.BaseColorRed = Color.FromArgb(220, 85, 96);
			this.BGColor = Color.FromArgb(84, 85, 86);
			this.ToggleColor = Color.FromArgb(45, 47, 49);
			this.TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.Transparent;
			base.Size = new Size(44, base.Height + 1);
			this.Cursor = Cursors.Hand;
			this.Font = new Font("Segoe UI", 10f);
			base.Size = new Size(76, 33);
		}

		// Token: 0x06000246 RID: 582 RVA: 0x00010E54 File Offset: 0x0000F054
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width - 1;
			this.H = base.Height - 1;
			GraphicsPath path = new GraphicsPath();
			GraphicsPath graphicsPath = new GraphicsPath();
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Rectangle rectangle2 = new Rectangle(this.W / 2, 0, 38, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			switch (this.O)
			{
			case FlatToggle._Options.Style1:
				path = Helpers.RoundRec(rectangle, 6);
				graphicsPath = Helpers.RoundRec(rectangle2, 6);
				g.FillPath(new SolidBrush(this.BGColor), path);
				g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
				g.DrawString("OFF", this.Font, new SolidBrush(this.BGColor), new Rectangle(19, 1, this.W, this.H), Helpers.CenterSF);
				if (this.Checked)
				{
					path = Helpers.RoundRec(rectangle, 6);
					graphicsPath = Helpers.RoundRec(new Rectangle(this.W / 2, 0, 38, this.H), 6);
					g.FillPath(new SolidBrush(this.ToggleColor), path);
					g.FillPath(new SolidBrush(this.BaseColor), graphicsPath);
					g.DrawString("ON", this.Font, new SolidBrush(this.BaseColor), new Rectangle(8, 7, this.W, this.H), Helpers.NearSF);
				}
				break;
			case FlatToggle._Options.Style2:
				path = Helpers.RoundRec(rectangle, 6);
				rectangle2 = new Rectangle(4, 4, 36, this.H - 8);
				graphicsPath = Helpers.RoundRec(rectangle2, 4);
				g.FillPath(new SolidBrush(this.BaseColorRed), path);
				g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
				g.DrawLine(new Pen(this.BGColor), 18, 20, 18, 12);
				g.DrawLine(new Pen(this.BGColor), 22, 20, 22, 12);
				g.DrawLine(new Pen(this.BGColor), 26, 20, 26, 12);
				g.DrawString("r", new Font("Marlett", 8f), new SolidBrush(this.TextColor), new Rectangle(19, 2, base.Width, base.Height), Helpers.CenterSF);
				if (this.Checked)
				{
					path = Helpers.RoundRec(rectangle, 6);
					rectangle2 = new Rectangle(this.W / 2 - 2, 4, 36, this.H - 8);
					graphicsPath = Helpers.RoundRec(rectangle2, 4);
					g.FillPath(new SolidBrush(this.BaseColor), path);
					g.FillPath(new SolidBrush(this.ToggleColor), graphicsPath);
					g.DrawLine(new Pen(this.BGColor), this.W / 2 + 12, 20, this.W / 2 + 12, 12);
					g.DrawLine(new Pen(this.BGColor), this.W / 2 + 16, 20, this.W / 2 + 16, 12);
					g.DrawLine(new Pen(this.BGColor), this.W / 2 + 20, 20, this.W / 2 + 20, 12);
					g.DrawString("ü", new Font("Wingdings", 14f), new SolidBrush(this.TextColor), new Rectangle(8, 7, base.Width, base.Height), Helpers.NearSF);
				}
				break;
			case FlatToggle._Options.Style3:
				path = Helpers.RoundRec(rectangle, 16);
				rectangle2 = new Rectangle(this.W - 28, 4, 22, this.H - 8);
				graphicsPath.AddEllipse(rectangle2);
				g.FillPath(new SolidBrush(this.ToggleColor), path);
				g.FillPath(new SolidBrush(this.BaseColorRed), graphicsPath);
				g.DrawString("OFF", this.Font, new SolidBrush(this.BaseColorRed), new Rectangle(-12, 2, this.W, this.H), Helpers.CenterSF);
				if (this.Checked)
				{
					path = Helpers.RoundRec(rectangle, 16);
					rectangle2 = new Rectangle(6, 4, 22, this.H - 8);
					graphicsPath.Reset();
					graphicsPath.AddEllipse(rectangle2);
					g.FillPath(new SolidBrush(this.ToggleColor), path);
					g.FillPath(new SolidBrush(this.BaseColor), graphicsPath);
					g.DrawString("ON", this.Font, new SolidBrush(this.BaseColor), new Rectangle(12, 2, this.W, this.H), Helpers.CenterSF);
				}
				break;
			case FlatToggle._Options.Style4:
				if (this.Checked)
				{
				}
				break;
			case FlatToggle._Options.Style5:
			{
				bool @checked = this.Checked;
				break;
			}
			}
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		// Token: 0x04000122 RID: 290
		private int W;

		// Token: 0x04000123 RID: 291
		private int H;

		// Token: 0x04000124 RID: 292
		private FlatToggle._Options O;

		// Token: 0x04000125 RID: 293
		private bool _Checked;

		// Token: 0x04000126 RID: 294
		private MouseState State;

		// Token: 0x04000128 RID: 296
		private Color BaseColor;

		// Token: 0x04000129 RID: 297
		private Color BaseColorRed;

		// Token: 0x0400012A RID: 298
		private Color BGColor;

		// Token: 0x0400012B RID: 299
		private Color ToggleColor;

		// Token: 0x0400012C RID: 300
		private Color TextColor;

		// Token: 0x02000059 RID: 89
		// (Invoke) Token: 0x06000393 RID: 915
		public delegate void CheckedChangedEventHandler(object sender);

		// Token: 0x0200005A RID: 90
		[Flags]
		public enum _Options
		{
			// Token: 0x040001D8 RID: 472
			Style1 = 0,
			// Token: 0x040001D9 RID: 473
			Style2 = 1,
			// Token: 0x040001DA RID: 474
			Style3 = 2,
			// Token: 0x040001DB RID: 475
			Style4 = 3,
			// Token: 0x040001DC RID: 476
			Style5 = 4
		}
	}
}
