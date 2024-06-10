using System;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Runtime.CompilerServices;
using System.Windows.Forms;

namespace Client
{
	// Token: 0x0200000B RID: 11
	internal class Class3 : Label
	{
		// Token: 0x0600007C RID: 124 RVA: 0x000020BD File Offset: 0x000002BD
		public Class3()
		{
			<Module>.Class0.smethod_0();
			base..ctor();
			this.Color_0 = Color.Green;
			this.Single_0 = 1.5f;
		}

		// Token: 0x17000010 RID: 16
		// (get) Token: 0x0600007D RID: 125 RVA: 0x000192BC File Offset: 0x000174BC
		// (set) Token: 0x0600007E RID: 126 RVA: 0x00019324 File Offset: 0x00017524
		public Color Color_0
		{
			[CompilerGenerated]
			get
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
				return this.color_0;
			}
			[CompilerGenerated]
			set
			{
				int num = 0;
				do
				{
					if (num == 2)
					{
						this.color_0 = value;
						num = 3;
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
				while (num != 3);
			}
		}

		// Token: 0x17000011 RID: 17
		// (get) Token: 0x0600007F RID: 127 RVA: 0x000193A4 File Offset: 0x000175A4
		// (set) Token: 0x06000080 RID: 128 RVA: 0x0001940C File Offset: 0x0001760C
		public float Single_0
		{
			[CompilerGenerated]
			get
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
				return this.float_0;
			}
			[CompilerGenerated]
			set
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
						this.float_0 = value;
						num = 3;
					}
					if (num == 0)
					{
						num = 1;
					}
				}
				while (num != 3);
			}
		}

		// Token: 0x06000081 RID: 129 RVA: 0x0001948C File Offset: 0x0001768C
		protected virtual void OnPaint(PaintEventArgs e)
		{
			int num = 0;
			GraphicsPath graphicsPath;
			do
			{
				if (num == 2)
				{
					e.Graphics.FillRectangle(new SolidBrush(this.BackColor), base.ClientRectangle);
					num = 3;
				}
				if (num == 3)
				{
					graphicsPath = new GraphicsPath();
					num = 4;
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
			while (num != 4);
			try
			{
				using (Pen pen = new Pen(this.Color_0, this.Single_0)
				{
					LineJoin = LineJoin.Round
				})
				{
					using (StringFormat stringFormat = new StringFormat())
					{
						using (Brush brush = new SolidBrush(this.ForeColor))
						{
							graphicsPath.AddString(this.Text, this.Font.FontFamily, (int)this.Font.Style, this.Font.Size, base.ClientRectangle, stringFormat);
							e.Graphics.ScaleTransform(1.3f, 1.35f);
							e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
							e.Graphics.DrawPath(pen, graphicsPath);
							e.Graphics.FillPath(brush, graphicsPath);
						}
					}
				}
			}
			finally
			{
				if (graphicsPath != null)
				{
					((IDisposable)graphicsPath).Dispose();
				}
			}
		}

		// Token: 0x04000071 RID: 113
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private Color color_0;

		// Token: 0x04000072 RID: 114
		[DebuggerBrowsable(DebuggerBrowsableState.Never)]
		[CompilerGenerated]
		private float float_0;
	}
}
