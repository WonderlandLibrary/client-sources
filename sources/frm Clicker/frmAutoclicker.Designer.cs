namespace Action_Installer
{
	// Token: 0x02000002 RID: 2
	[global::Microsoft.VisualBasic.CompilerServices.DesignerGenerated]
	public partial class frmAutoclicker : global::System.Windows.Forms.Form
	{
		// Token: 0x06000010 RID: 16 RVA: 0x00002794 File Offset: 0x00000994
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

		// Token: 0x06000011 RID: 17 RVA: 0x000027E4 File Offset: 0x000009E4
		[global::System.Diagnostics.DebuggerStepThrough]
		private void InitializeComponent()
		{
			this.components = new global::System.ComponentModel.Container();
			global::gTrackBar.ColorPack colorPack = new global::gTrackBar.ColorPack();
			global::gTrackBar.ColorPack colorPack2 = new global::gTrackBar.ColorPack();
			global::gTrackBar.ColorPack colorPack3 = new global::gTrackBar.ColorPack();
			global::gTrackBar.ColorLinearGradient colorLinearGradient = new global::gTrackBar.ColorLinearGradient();
			global::gTrackBar.ColorLinearGradient colorLinearGradient2 = new global::gTrackBar.ColorLinearGradient();
			global::System.ComponentModel.ComponentResourceManager componentResourceManager = new global::System.ComponentModel.ComponentResourceManager(typeof(global::Action_Installer.frmAutoclicker));
			global::gTrackBar.ColorPack colorPack4 = new global::gTrackBar.ColorPack();
			global::gTrackBar.ColorPack colorPack5 = new global::gTrackBar.ColorPack();
			global::gTrackBar.ColorPack colorPack6 = new global::gTrackBar.ColorPack();
			global::gTrackBar.ColorLinearGradient colorLinearGradient3 = new global::gTrackBar.ColorLinearGradient();
			global::gTrackBar.ColorLinearGradient colorLinearGradient4 = new global::gTrackBar.ColorLinearGradient();
			this.trbCPS = new global::gTrackBar.gTrackBar();
			this.lblCPS = new global::System.Windows.Forms.Label();
			this.lblRand = new global::System.Windows.Forms.Label();
			this.trbRandomization = new global::gTrackBar.gTrackBar();
			this.grpOptions = new global::System.Windows.Forms.GroupBox();
			this.btnChangeAudio = new global::System.Windows.Forms.Button();
			this.txtPixels = new global::System.Windows.Forms.TextBox();
			this.chkJitter = new global::System.Windows.Forms.CheckBox();
			this.chkPlaySound = new global::System.Windows.Forms.CheckBox();
			this.chkRandomStops = new global::System.Windows.Forms.CheckBox();
			this.chkOnlyClickOnMinecraft = new global::System.Windows.Forms.CheckBox();
			this.tmrMouseDown = new global::System.Windows.Forms.Timer(this.components);
			this.tmrMouseUp = new global::System.Windows.Forms.Timer(this.components);
			this.tmrRandomize = new global::System.Windows.Forms.Timer(this.components);
			this.lblZekra = new global::System.Windows.Forms.Label();
			this.grpOptions.SuspendLayout();
			base.SuspendLayout();
			this.trbCPS.BackColor = global::System.Drawing.Color.Transparent;
			this.trbCPS.BrushDirection = global::System.Drawing.Drawing2D.LinearGradientMode.Vertical;
			this.trbCPS.BrushStyle = 2;
			this.trbCPS.ChangeLarge = 1;
			colorPack.Border = global::System.Drawing.Color.Gray;
			colorPack.Face = global::System.Drawing.Color.Gray;
			colorPack.Highlight = global::System.Drawing.Color.Gray;
			this.trbCPS.ColorDown = colorPack;
			colorPack2.Border = global::System.Drawing.Color.Gray;
			colorPack2.Face = global::System.Drawing.Color.Gray;
			colorPack2.Highlight = global::System.Drawing.Color.Gray;
			this.trbCPS.ColorHover = colorPack2;
			colorPack3.Border = global::System.Drawing.Color.Gray;
			colorPack3.Face = global::System.Drawing.Color.Gray;
			colorPack3.Highlight = global::System.Drawing.Color.Gray;
			this.trbCPS.ColorUp = colorPack3;
			this.trbCPS.FloatValue = false;
			this.trbCPS.FloatValueFontColor = global::System.Drawing.Color.Salmon;
			this.trbCPS.Label = null;
			this.trbCPS.Location = new global::System.Drawing.Point(11, 9);
			this.trbCPS.Margin = new global::System.Windows.Forms.Padding(2, 3, 2, 3);
			this.trbCPS.MaxValue = 16;
			this.trbCPS.MinValue = 6;
			this.trbCPS.Name = "trbCPS";
			this.trbCPS.Size = new global::System.Drawing.Size(211, 36);
			this.trbCPS.SliderCapEnd = global::System.Drawing.Drawing2D.LineCap.Flat;
			this.trbCPS.SliderCapStart = global::System.Drawing.Drawing2D.LineCap.Flat;
			colorLinearGradient.ColorA = global::System.Drawing.Color.LightGray;
			colorLinearGradient.ColorB = global::System.Drawing.Color.LightGray;
			this.trbCPS.SliderColorHigh = colorLinearGradient;
			colorLinearGradient2.ColorA = global::System.Drawing.Color.LightGray;
			colorLinearGradient2.ColorB = global::System.Drawing.Color.LightGray;
			this.trbCPS.SliderColorLow = colorLinearGradient2;
			global::gTrackBar.gTrackBar trbCPS = this.trbCPS;
			object @object = componentResourceManager.GetObject("trbCPS.SliderFocalPt");
			trbCPS.SliderFocalPt = ((@object != null) ? ((global::System.Drawing.PointF)@object) : default(global::System.Drawing.PointF));
			global::gTrackBar.gTrackBar trbCPS2 = this.trbCPS;
			object object2 = componentResourceManager.GetObject("trbCPS.SliderHighlightPt");
			trbCPS2.SliderHighlightPt = ((object2 != null) ? ((global::System.Drawing.PointF)object2) : default(global::System.Drawing.PointF));
			this.trbCPS.SliderShape = 1;
			this.trbCPS.SliderSize = new global::System.Drawing.Size(15, 25);
			this.trbCPS.SliderWidthHigh = 18f;
			this.trbCPS.SliderWidthLow = 18f;
			this.trbCPS.TabIndex = 10000;
			this.trbCPS.TabStop = false;
			this.trbCPS.TickColor = global::System.Drawing.Color.CornflowerBlue;
			this.trbCPS.TickInterval = 1;
			this.trbCPS.TickOffset = 13;
			this.trbCPS.TickThickness = 2f;
			this.trbCPS.TickWidth = 15;
			this.trbCPS.UpDownShow = false;
			this.trbCPS.Value = 11;
			this.trbCPS.ValueAdjusted = 11f;
			this.trbCPS.ValueDivisor = 1;
			this.trbCPS.ValueStrFormat = null;
			this.lblCPS.AutoSize = true;
			this.lblCPS.Location = new global::System.Drawing.Point(226, 19);
			this.lblCPS.Name = "lblCPS";
			this.lblCPS.Size = new global::System.Drawing.Size(46, 13);
			this.lblCPS.TabIndex = 38;
			this.lblCPS.Text = "CPS: 11";
			this.lblRand.AutoSize = true;
			this.lblRand.Location = new global::System.Drawing.Point(227, 62);
			this.lblRand.Name = "lblRand";
			this.lblRand.Size = new global::System.Drawing.Size(45, 13);
			this.lblRand.TabIndex = 10001;
			this.lblRand.Text = "Rand: 1";
			this.trbRandomization.BackColor = global::System.Drawing.Color.Transparent;
			this.trbRandomization.BrushDirection = global::System.Drawing.Drawing2D.LinearGradientMode.Vertical;
			this.trbRandomization.BrushStyle = 2;
			this.trbRandomization.ChangeLarge = 1;
			colorPack4.Border = global::System.Drawing.Color.Gray;
			colorPack4.Face = global::System.Drawing.Color.Gray;
			colorPack4.Highlight = global::System.Drawing.Color.Gray;
			this.trbRandomization.ColorDown = colorPack4;
			colorPack5.Border = global::System.Drawing.Color.Gray;
			colorPack5.Face = global::System.Drawing.Color.Gray;
			colorPack5.Highlight = global::System.Drawing.Color.Gray;
			this.trbRandomization.ColorHover = colorPack5;
			colorPack6.Border = global::System.Drawing.Color.Gray;
			colorPack6.Face = global::System.Drawing.Color.Gray;
			colorPack6.Highlight = global::System.Drawing.Color.Gray;
			this.trbRandomization.ColorUp = colorPack6;
			this.trbRandomization.FloatValue = false;
			this.trbRandomization.FloatValueFontColor = global::System.Drawing.Color.Salmon;
			this.trbRandomization.Label = null;
			this.trbRandomization.Location = new global::System.Drawing.Point(11, 51);
			this.trbRandomization.Margin = new global::System.Windows.Forms.Padding(2, 3, 2, 3);
			this.trbRandomization.MaxValue = 3;
			this.trbRandomization.Name = "trbRandomization";
			this.trbRandomization.Size = new global::System.Drawing.Size(211, 36);
			this.trbRandomization.SliderCapEnd = global::System.Drawing.Drawing2D.LineCap.Flat;
			this.trbRandomization.SliderCapStart = global::System.Drawing.Drawing2D.LineCap.Flat;
			colorLinearGradient3.ColorA = global::System.Drawing.Color.LightGray;
			colorLinearGradient3.ColorB = global::System.Drawing.Color.LightGray;
			this.trbRandomization.SliderColorHigh = colorLinearGradient3;
			colorLinearGradient4.ColorA = global::System.Drawing.Color.LightGray;
			colorLinearGradient4.ColorB = global::System.Drawing.Color.LightGray;
			this.trbRandomization.SliderColorLow = colorLinearGradient4;
			global::gTrackBar.gTrackBar trbRandomization = this.trbRandomization;
			object object3 = componentResourceManager.GetObject("trbRandomization.SliderFocalPt");
			trbRandomization.SliderFocalPt = ((object3 != null) ? ((global::System.Drawing.PointF)object3) : default(global::System.Drawing.PointF));
			global::gTrackBar.gTrackBar trbRandomization2 = this.trbRandomization;
			object object4 = componentResourceManager.GetObject("trbRandomization.SliderHighlightPt");
			trbRandomization2.SliderHighlightPt = ((object4 != null) ? ((global::System.Drawing.PointF)object4) : default(global::System.Drawing.PointF));
			this.trbRandomization.SliderShape = 1;
			this.trbRandomization.SliderSize = new global::System.Drawing.Size(15, 25);
			this.trbRandomization.SliderWidthHigh = 18f;
			this.trbRandomization.SliderWidthLow = 18f;
			this.trbRandomization.TabIndex = 10002;
			this.trbRandomization.TabStop = false;
			this.trbRandomization.TickColor = global::System.Drawing.Color.CornflowerBlue;
			this.trbRandomization.TickInterval = 1;
			this.trbRandomization.TickOffset = 13;
			this.trbRandomization.TickThickness = 2f;
			this.trbRandomization.TickWidth = 15;
			this.trbRandomization.UpDownShow = false;
			this.trbRandomization.Value = 1;
			this.trbRandomization.ValueAdjusted = 1f;
			this.trbRandomization.ValueDivisor = 1;
			this.trbRandomization.ValueStrFormat = null;
			this.grpOptions.Controls.Add(this.btnChangeAudio);
			this.grpOptions.Controls.Add(this.txtPixels);
			this.grpOptions.Controls.Add(this.chkJitter);
			this.grpOptions.Controls.Add(this.chkPlaySound);
			this.grpOptions.Controls.Add(this.chkRandomStops);
			this.grpOptions.Controls.Add(this.chkOnlyClickOnMinecraft);
			this.grpOptions.Location = new global::System.Drawing.Point(11, 94);
			this.grpOptions.Name = "grpOptions";
			this.grpOptions.Size = new global::System.Drawing.Size(261, 141);
			this.grpOptions.TabIndex = 10003;
			this.grpOptions.TabStop = false;
			this.grpOptions.Text = "Options";
			this.btnChangeAudio.Location = new global::System.Drawing.Point(148, 64);
			this.btnChangeAudio.Name = "btnChangeAudio";
			this.btnChangeAudio.Size = new global::System.Drawing.Size(107, 23);
			this.btnChangeAudio.TabIndex = 7;
			this.btnChangeAudio.Text = "Change Audio File";
			this.btnChangeAudio.UseVisualStyleBackColor = true;
			this.txtPixels.Location = new global::System.Drawing.Point(221, 88);
			this.txtPixels.Multiline = true;
			this.txtPixels.Name = "txtPixels";
			this.txtPixels.Size = new global::System.Drawing.Size(34, 20);
			this.txtPixels.TabIndex = 4;
			this.txtPixels.Text = "5";
			this.chkJitter.AutoSize = true;
			this.chkJitter.Location = new global::System.Drawing.Point(6, 91);
			this.chkJitter.Name = "chkJitter";
			this.chkJitter.Size = new global::System.Drawing.Size(222, 17);
			this.chkJitter.TabIndex = 3;
			this.chkJitter.Text = "Jitter                                                 Pixels";
			this.chkJitter.UseVisualStyleBackColor = true;
			this.chkPlaySound.AutoSize = true;
			this.chkPlaySound.Location = new global::System.Drawing.Point(7, 68);
			this.chkPlaySound.Name = "chkPlaySound";
			this.chkPlaySound.Size = new global::System.Drawing.Size(107, 17);
			this.chkPlaySound.TabIndex = 2;
			this.chkPlaySound.Text = "Play Fake Sound";
			this.chkPlaySound.UseVisualStyleBackColor = true;
			this.chkRandomStops.AutoSize = true;
			this.chkRandomStops.Checked = true;
			this.chkRandomStops.CheckState = global::System.Windows.Forms.CheckState.Checked;
			this.chkRandomStops.Location = new global::System.Drawing.Point(7, 44);
			this.chkRandomStops.Name = "chkRandomStops";
			this.chkRandomStops.Size = new global::System.Drawing.Size(225, 17);
			this.chkRandomStops.TabIndex = 1;
			this.chkRandomStops.Text = "Add random CPS drops (RECOMENDED) ";
			this.chkRandomStops.UseVisualStyleBackColor = true;
			this.chkOnlyClickOnMinecraft.AutoSize = true;
			this.chkOnlyClickOnMinecraft.Checked = true;
			this.chkOnlyClickOnMinecraft.CheckState = global::System.Windows.Forms.CheckState.Checked;
			this.chkOnlyClickOnMinecraft.Location = new global::System.Drawing.Point(7, 20);
			this.chkOnlyClickOnMinecraft.Name = "chkOnlyClickOnMinecraft";
			this.chkOnlyClickOnMinecraft.Size = new global::System.Drawing.Size(134, 17);
			this.chkOnlyClickOnMinecraft.TabIndex = 0;
			this.chkOnlyClickOnMinecraft.Text = "Only click on Minecraft";
			this.chkOnlyClickOnMinecraft.UseVisualStyleBackColor = true;
			this.tmrMouseDown.Interval = 30;
			this.tmrMouseUp.Interval = 333;
			this.tmrRandomize.Interval = 1000;
			this.lblZekra.AutoSize = true;
			this.lblZekra.Location = new global::System.Drawing.Point(13, 242);
			this.lblZekra.Name = "lblZekra";
			this.lblZekra.Size = new global::System.Drawing.Size(138, 13);
			this.lblZekra.TabIndex = 10004;
			this.lblZekra.Text = "made by Zekra shrêkt lmfao";
			base.AutoScaleDimensions = new global::System.Drawing.SizeF(6f, 13f);
			base.AutoScaleMode = global::System.Windows.Forms.AutoScaleMode.Font;
			base.ClientSize = new global::System.Drawing.Size(284, 261);
			base.Controls.Add(this.lblZekra);
			base.Controls.Add(this.grpOptions);
			base.Controls.Add(this.lblRand);
			base.Controls.Add(this.trbRandomization);
			base.Controls.Add(this.lblCPS);
			base.Controls.Add(this.trbCPS);
			base.FormBorderStyle = global::System.Windows.Forms.FormBorderStyle.FixedSingle;
			base.Icon = (global::System.Drawing.Icon)componentResourceManager.GetObject("$this.Icon");
			base.MaximizeBox = false;
			base.Name = "frmAutoclicker";
			this.Text = "Autoclicker";
			this.grpOptions.ResumeLayout(false);
			this.grpOptions.PerformLayout();
			base.ResumeLayout(false);
			base.PerformLayout();
		}

		// Token: 0x0400000C RID: 12
		private global::System.ComponentModel.IContainer components;
	}
}
