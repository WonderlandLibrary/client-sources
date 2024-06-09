using System;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	internal class NSLabel : Control
	{
		private string _Value1;

		private string _Value2;

		private SolidBrush B1;

		private PointF PT1;

		private PointF PT2;

		private SizeF SZ1;

		private SizeF SZ2;

		public string Value1
		{
			get
			{
				return this._Value1;
			}
			set
			{
				this._Value1 = value;
				base.Invalidate();
			}
		}

		public string Value2
		{
			get
			{
				return this._Value2;
			}
			set
			{
				this._Value2 = value;
				base.Invalidate();
			}
		}

		public NSLabel()
		{
			this._Value1 = "NET";
			this._Value2 = "SEAL";
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.Opaque | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			base.SetStyle(ControlStyles.Selectable, false);
			this.Font = new System.Drawing.Font("Segoe UI", 11.25f, FontStyle.Bold);
			this.B1 = new SolidBrush(Color.FromArgb(75, 0, 130));
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			if (MyProject.Forms.Form1.LightTheme)
			{
				Helpers.G = e.Graphics;
				Helpers.G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				Helpers.G.Clear(this.BackColor);
				this.SZ1 = Helpers.G.MeasureString(this.Value1, this.Font, base.Width, StringFormat.GenericTypographic);
				this.SZ2 = Helpers.G.MeasureString(this.Value2, this.Font, base.Width, StringFormat.GenericTypographic);
				this.PT1 = new PointF(0f, (float)(base.Height / 2) - this.SZ1.Height / 2f);
				this.PT2 = new PointF(this.SZ1.Width + 1f, (float)(base.Height / 2) - this.SZ1.Height / 2f);
				Helpers.G.DrawString(this.Value1, this.Font, this.B1, this.PT1);
				Helpers.G.DrawString(this.Value2, this.Font, this.B1, this.PT2);
				return;
			}
			Helpers.G = e.Graphics;
			Helpers.G.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			Helpers.G.Clear(this.BackColor);
			this.SZ1 = Helpers.G.MeasureString(this.Value1, this.Font, base.Width, StringFormat.GenericTypographic);
			this.SZ2 = Helpers.G.MeasureString(this.Value2, this.Font, base.Width, StringFormat.GenericTypographic);
			this.PT1 = new PointF(0f, (float)(base.Height / 2) - this.SZ1.Height / 2f);
			this.PT2 = new PointF(this.SZ1.Width + 1f, (float)(base.Height / 2) - this.SZ1.Height / 2f);
			Helpers.G.DrawString(this.Value1, this.Font, Brushes.Black, this.PT1.X + 1f, this.PT1.Y + 1f);
			Helpers.G.DrawString(this.Value1, this.Font, Brushes.WhiteSmoke, this.PT1);
			Helpers.G.DrawString(this.Value2, this.Font, Brushes.Black, this.PT2.X + 1f, this.PT2.Y + 1f);
			Helpers.G.DrawString(this.Value2, this.Font, this.B1, this.PT2);
		}
	}
}