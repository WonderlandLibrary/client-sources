using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Threading;
using WaveClient.Module;
using WaveClient.ModuleManagment.ModuleExtensions;

namespace WaveClient.GUI
{
	// Token: 0x02000007 RID: 7
	public class MainWindow_ModulePage : Page, IComponentConnector
	{
		// Token: 0x0600000E RID: 14 RVA: 0x000021F8 File Offset: 0x000003F8
		public MainWindow_ModulePage()
		{
			this.InitializeComponent();
			this.UpdateGUIDispatcherTimer = new DispatcherTimer();
			this.UpdateGUIDispatcherTimer.Interval = TimeSpan.FromMilliseconds(10.0);
			this.UpdateGUIDispatcherTimer.Tick += this.UpdateGUI;
			this.UpdateGUIDispatcherTimer.Start();
		}

		// Token: 0x0600000F RID: 15 RVA: 0x00002258 File Offset: 0x00000458
		~MainWindow_ModulePage()
		{
			this.UpdateGUIDispatcherTimer.Stop();
		}

		// Token: 0x06000010 RID: 16 RVA: 0x0000228C File Offset: 0x0000048C
		public void UpdateGUI(object sender, EventArgs e)
		{
			this.SModule_Airjump.Content = GUIExtensions.GetBoolStateText(AirJump.ToggleState);
			this.SModule_NoFall.Content = GUIExtensions.GetBoolStateText(NoFall.ToggleState);
			this.SModule_Instabreak.Content = GUIExtensions.GetBoolStateText(Instabreak.ToggleState);
			this.SModule_Reach.Content = GUIExtensions.GetBoolStateText(Reach.ToggleState);
			this.SModule_NoSwing.Content = GUIExtensions.GetBoolStateText(NoSwing.ToggleState);
			this.SModule_Coords.Content = GUIExtensions.GetBoolStateText(Coords.ToggleState);
			this.SModule_Speed.Content = GUIExtensions.GetBoolStateText(Speed.ToggleState);
			this.SModule_AutoSprint.Content = GUIExtensions.GetBoolStateText(AutoSprint.ToggleState);
			this.SModule_KillYourSelf.Content = GUIExtensions.GetBoolStateText(KillYourSelf.ToggleState);
			this.SModule_Phase.Content = GUIExtensions.GetBoolStateText(Phase.ToggleState);
			this.SModule_Noclip.Content = GUIExtensions.GetBoolStateText(Noclip.ToggleState);
			this.SModule_Autoclicker.Content = GUIExtensions.GetBoolStateText(Autoclicker.ToggleState);
			this.SModule_AnvilCost.Content = GUIExtensions.GetBoolStateText(AnvilCost.ToggleState);
			this.SModule_HighJump.Content = GUIExtensions.GetBoolStateText(HighJump.ToggleState);
			this.SModule_StickyGround.Content = GUIExtensions.GetBoolStateText(StickyGround.ToggleState);
		}

		// Token: 0x06000011 RID: 17 RVA: 0x000023D4 File Offset: 0x000005D4
		private void SModule_Airjump_Click(object sender, RoutedEventArgs e)
		{
			AirJump.ToggleState = !AirJump.ToggleState;
			this.SModule_Airjump.Content = GUIExtensions.GetBoolStateText(AirJump.ToggleState);
		}

		// Token: 0x06000012 RID: 18 RVA: 0x000023F8 File Offset: 0x000005F8
		private void SModule_NoFall_Click(object sender, RoutedEventArgs e)
		{
			NoFall.ToggleState = !NoFall.ToggleState;
			this.SModule_NoFall.Content = GUIExtensions.GetBoolStateText(NoFall.ToggleState);
		}

		// Token: 0x06000013 RID: 19 RVA: 0x0000241C File Offset: 0x0000061C
		private void SModule_Instabreak_Click(object sender, RoutedEventArgs e)
		{
			Instabreak.ToggleState = !Instabreak.ToggleState;
			this.SModule_Instabreak.Content = GUIExtensions.GetBoolStateText(Instabreak.ToggleState);
		}

		// Token: 0x06000014 RID: 20 RVA: 0x00002440 File Offset: 0x00000640
		private void SModule_Reach_Click(object sender, RoutedEventArgs e)
		{
			Reach.ToggleState = !Reach.ToggleState;
			this.SModule_Reach.Content = GUIExtensions.GetBoolStateText(Reach.ToggleState);
		}

		// Token: 0x06000015 RID: 21 RVA: 0x00002464 File Offset: 0x00000664
		private void SModule_NoSwing_Click(object sender, RoutedEventArgs e)
		{
			NoSwing.ToggleState = !NoSwing.ToggleState;
			this.SModule_NoSwing.Content = GUIExtensions.GetBoolStateText(NoSwing.ToggleState);
		}

		// Token: 0x06000016 RID: 22 RVA: 0x00002488 File Offset: 0x00000688
		private void SModule_Coords_Click(object sender, RoutedEventArgs e)
		{
			Coords.ToggleState = !Coords.ToggleState;
			this.SModule_Coords.Content = GUIExtensions.GetBoolStateText(Coords.ToggleState);
		}

		// Token: 0x06000017 RID: 23 RVA: 0x000024AC File Offset: 0x000006AC
		private void SModule_Speed_Click(object sender, RoutedEventArgs e)
		{
			Speed.ToggleState = !Speed.ToggleState;
			this.SModule_Speed.Content = GUIExtensions.GetBoolStateText(Speed.ToggleState);
		}

		// Token: 0x06000018 RID: 24 RVA: 0x000024D0 File Offset: 0x000006D0
		private void SModule_AutoSprint_Click(object sender, RoutedEventArgs e)
		{
			AutoSprint.ToggleState = !AutoSprint.ToggleState;
			this.SModule_AutoSprint.Content = GUIExtensions.GetBoolStateText(AutoSprint.ToggleState);
		}

		// Token: 0x06000019 RID: 25 RVA: 0x000024F4 File Offset: 0x000006F4
		private void SModule_KillYourSelf_Click(object sender, RoutedEventArgs e)
		{
			KillYourSelf.ToggleState = !KillYourSelf.ToggleState;
			this.SModule_KillYourSelf.Content = GUIExtensions.GetBoolStateText(KillYourSelf.ToggleState);
		}

		// Token: 0x0600001A RID: 26 RVA: 0x00002518 File Offset: 0x00000718
		private void SModule_Phase_Click(object sender, RoutedEventArgs e)
		{
			Phase.ToggleState = !Phase.ToggleState;
			this.SModule_Phase.Content = GUIExtensions.GetBoolStateText(Phase.ToggleState);
		}

		// Token: 0x0600001B RID: 27 RVA: 0x0000253C File Offset: 0x0000073C
		private void SModule_Noclip_Click(object sender, RoutedEventArgs e)
		{
			Noclip.ToggleState = !Noclip.ToggleState;
			this.SModule_Noclip.Content = GUIExtensions.GetBoolStateText(Noclip.ToggleState);
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002560 File Offset: 0x00000760
		private void SModule_Autoclicker_Click(object sender, RoutedEventArgs e)
		{
			Autoclicker.ToggleState = !Autoclicker.ToggleState;
			this.SModule_Autoclicker.Content = GUIExtensions.GetBoolStateText(Autoclicker.ToggleState);
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00002584 File Offset: 0x00000784
		private void SModule_AnvilCost_Click(object sender, RoutedEventArgs e)
		{
			AnvilCost.ToggleState = !AnvilCost.ToggleState;
			this.SModule_AnvilCost.Content = GUIExtensions.GetBoolStateText(AnvilCost.ToggleState);
		}

		// Token: 0x0600001E RID: 30 RVA: 0x000025A8 File Offset: 0x000007A8
		private void SModule_HighJump_Click(object sender, RoutedEventArgs e)
		{
			HighJump.ToggleState = !HighJump.ToggleState;
			this.SModule_HighJump.Content = GUIExtensions.GetBoolStateText(HighJump.ToggleState);
		}

		// Token: 0x0600001F RID: 31 RVA: 0x000025CC File Offset: 0x000007CC
		private void SModule_StickyGround_Click(object sender, RoutedEventArgs e)
		{
			StickyGround.ToggleState = !StickyGround.ToggleState;
			this.SModule_StickyGround.Content = GUIExtensions.GetBoolStateText(StickyGround.ToggleState);
		}

		// Token: 0x06000020 RID: 32 RVA: 0x000025F0 File Offset: 0x000007F0
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		public void InitializeComponent()
		{
			if (this._contentLoaded)
			{
				return;
			}
			this._contentLoaded = true;
			Uri resourceLocator = new Uri("/WaveClient;component/waveclient/gui/waveclient.gui.mainwindow.modulepage.xaml", UriKind.Relative);
			Application.LoadComponent(this, resourceLocator);
		}

		// Token: 0x06000021 RID: 33 RVA: 0x00002620 File Offset: 0x00000820
		[DebuggerNonUserCode]
		[GeneratedCode("PresentationBuildTasks", "4.0.0.0")]
		[EditorBrowsable(EditorBrowsableState.Never)]
		void IComponentConnector.Connect(int connectionId, object target)
		{
			switch (connectionId)
			{
			case 1:
				this.SGrid_Airjump = (Grid)target;
				return;
			case 2:
				this.SModule_Airjump = (Button)target;
				this.SModule_Airjump.Click += this.SModule_Airjump_Click;
				return;
			case 3:
				this.STextBlock_Airjump = (TextBlock)target;
				return;
			case 4:
				this.SGrid_NoFall = (Grid)target;
				return;
			case 5:
				this.SModule_NoFall = (Button)target;
				this.SModule_NoFall.Click += this.SModule_NoFall_Click;
				return;
			case 6:
				this.STextBlock_NoFall = (TextBlock)target;
				return;
			case 7:
				this.SGrid_Instabreak = (Grid)target;
				return;
			case 8:
				this.SModule_Instabreak = (Button)target;
				this.SModule_Instabreak.Click += this.SModule_Instabreak_Click;
				return;
			case 9:
				this.STextBlock_Instabreak = (TextBlock)target;
				return;
			case 10:
				this.SGrid_Reach = (Grid)target;
				return;
			case 11:
				this.SModule_Reach = (Button)target;
				this.SModule_Reach.Click += this.SModule_Reach_Click;
				return;
			case 12:
				this.STextBlock_Reach = (TextBlock)target;
				return;
			case 13:
				this.SGrid_NoSwing = (Grid)target;
				return;
			case 14:
				this.SModule_NoSwing = (Button)target;
				this.SModule_NoSwing.Click += this.SModule_NoSwing_Click;
				return;
			case 15:
				this.STextBlock_NoSwing = (TextBlock)target;
				return;
			case 16:
				this.SGrid_Coords = (Grid)target;
				return;
			case 17:
				this.SModule_Coords = (Button)target;
				this.SModule_Coords.Click += this.SModule_Coords_Click;
				return;
			case 18:
				this.STextBlock_Coords = (TextBlock)target;
				return;
			case 19:
				this.SGrid_Speed = (Grid)target;
				return;
			case 20:
				this.SModule_Speed = (Button)target;
				this.SModule_Speed.Click += this.SModule_Speed_Click;
				return;
			case 21:
				this.STextBlock_Speed = (TextBlock)target;
				return;
			case 22:
				this.SGrid_AutoSprint = (Grid)target;
				return;
			case 23:
				this.SModule_AutoSprint = (Button)target;
				this.SModule_AutoSprint.Click += this.SModule_AutoSprint_Click;
				return;
			case 24:
				this.STextBlock_AutoSprint = (TextBlock)target;
				return;
			case 25:
				this.SGrid_KillYourSelf = (Grid)target;
				return;
			case 26:
				this.SModule_KillYourSelf = (Button)target;
				this.SModule_KillYourSelf.Click += this.SModule_KillYourSelf_Click;
				return;
			case 27:
				this.STextBlock_KillYourSelf = (TextBlock)target;
				return;
			case 28:
				this.SGrid_Phase = (Grid)target;
				return;
			case 29:
				this.SModule_Phase = (Button)target;
				this.SModule_Phase.Click += this.SModule_Phase_Click;
				return;
			case 30:
				this.STextBlock_Phase = (TextBlock)target;
				return;
			case 31:
				this.SGrid_Noclip = (Grid)target;
				return;
			case 32:
				this.SModule_Noclip = (Button)target;
				this.SModule_Noclip.Click += this.SModule_Noclip_Click;
				return;
			case 33:
				this.STextBlock_Noclip = (TextBlock)target;
				return;
			case 34:
				this.SGrid_AutoClicker = (Grid)target;
				return;
			case 35:
				this.SModule_Autoclicker = (Button)target;
				this.SModule_Autoclicker.Click += this.SModule_Autoclicker_Click;
				return;
			case 36:
				this.STextBlock_Autoclicker = (TextBlock)target;
				return;
			case 37:
				this.SGrid_AnvilCost = (Grid)target;
				return;
			case 38:
				this.SModule_AnvilCost = (Button)target;
				this.SModule_AnvilCost.Click += this.SModule_AnvilCost_Click;
				return;
			case 39:
				this.STextBlock_AnvilCost = (TextBlock)target;
				return;
			case 40:
				this.SGrid_HighJump = (Grid)target;
				return;
			case 41:
				this.SModule_HighJump = (Button)target;
				this.SModule_HighJump.Click += this.SModule_HighJump_Click;
				return;
			case 42:
				this.STextBlock_HighJump = (TextBlock)target;
				return;
			case 43:
				this.SGrid_StickyGround = (Grid)target;
				return;
			case 44:
				this.SModule_StickyGround = (Button)target;
				this.SModule_StickyGround.Click += this.SModule_StickyGround_Click;
				return;
			case 45:
				this.STextBlock_StickyGround = (TextBlock)target;
				return;
			default:
				this._contentLoaded = true;
				return;
			}
		}

		// Token: 0x04000005 RID: 5
		private DispatcherTimer UpdateGUIDispatcherTimer;

		// Token: 0x04000006 RID: 6
		internal Grid SGrid_Airjump;

		// Token: 0x04000007 RID: 7
		internal Button SModule_Airjump;

		// Token: 0x04000008 RID: 8
		internal TextBlock STextBlock_Airjump;

		// Token: 0x04000009 RID: 9
		internal Grid SGrid_NoFall;

		// Token: 0x0400000A RID: 10
		internal Button SModule_NoFall;

		// Token: 0x0400000B RID: 11
		internal TextBlock STextBlock_NoFall;

		// Token: 0x0400000C RID: 12
		internal Grid SGrid_Instabreak;

		// Token: 0x0400000D RID: 13
		internal Button SModule_Instabreak;

		// Token: 0x0400000E RID: 14
		internal TextBlock STextBlock_Instabreak;

		// Token: 0x0400000F RID: 15
		internal Grid SGrid_Reach;

		// Token: 0x04000010 RID: 16
		internal Button SModule_Reach;

		// Token: 0x04000011 RID: 17
		internal TextBlock STextBlock_Reach;

		// Token: 0x04000012 RID: 18
		internal Grid SGrid_NoSwing;

		// Token: 0x04000013 RID: 19
		internal Button SModule_NoSwing;

		// Token: 0x04000014 RID: 20
		internal TextBlock STextBlock_NoSwing;

		// Token: 0x04000015 RID: 21
		internal Grid SGrid_Coords;

		// Token: 0x04000016 RID: 22
		internal Button SModule_Coords;

		// Token: 0x04000017 RID: 23
		internal TextBlock STextBlock_Coords;

		// Token: 0x04000018 RID: 24
		internal Grid SGrid_Speed;

		// Token: 0x04000019 RID: 25
		internal Button SModule_Speed;

		// Token: 0x0400001A RID: 26
		internal TextBlock STextBlock_Speed;

		// Token: 0x0400001B RID: 27
		internal Grid SGrid_AutoSprint;

		// Token: 0x0400001C RID: 28
		internal Button SModule_AutoSprint;

		// Token: 0x0400001D RID: 29
		internal TextBlock STextBlock_AutoSprint;

		// Token: 0x0400001E RID: 30
		internal Grid SGrid_KillYourSelf;

		// Token: 0x0400001F RID: 31
		internal Button SModule_KillYourSelf;

		// Token: 0x04000020 RID: 32
		internal TextBlock STextBlock_KillYourSelf;

		// Token: 0x04000021 RID: 33
		internal Grid SGrid_Phase;

		// Token: 0x04000022 RID: 34
		internal Button SModule_Phase;

		// Token: 0x04000023 RID: 35
		internal TextBlock STextBlock_Phase;

		// Token: 0x04000024 RID: 36
		internal Grid SGrid_Noclip;

		// Token: 0x04000025 RID: 37
		internal Button SModule_Noclip;

		// Token: 0x04000026 RID: 38
		internal TextBlock STextBlock_Noclip;

		// Token: 0x04000027 RID: 39
		internal Grid SGrid_AutoClicker;

		// Token: 0x04000028 RID: 40
		internal Button SModule_Autoclicker;

		// Token: 0x04000029 RID: 41
		internal TextBlock STextBlock_Autoclicker;

		// Token: 0x0400002A RID: 42
		internal Grid SGrid_AnvilCost;

		// Token: 0x0400002B RID: 43
		internal Button SModule_AnvilCost;

		// Token: 0x0400002C RID: 44
		internal TextBlock STextBlock_AnvilCost;

		// Token: 0x0400002D RID: 45
		internal Grid SGrid_HighJump;

		// Token: 0x0400002E RID: 46
		internal Button SModule_HighJump;

		// Token: 0x0400002F RID: 47
		internal TextBlock STextBlock_HighJump;

		// Token: 0x04000030 RID: 48
		internal Grid SGrid_StickyGround;

		// Token: 0x04000031 RID: 49
		internal Button SModule_StickyGround;

		// Token: 0x04000032 RID: 50
		internal TextBlock STextBlock_StickyGround;

		// Token: 0x04000033 RID: 51
		private bool _contentLoaded;
	}
}
