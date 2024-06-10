Public Class Form1
    Private Declare Function GetAsyncKeyState Lib "user32" (ByVal vKey As Integer) As Short
    Declare Sub apimouse_event Lib "user32.dll" Alias "mouse_event" (ByVal dwFlags As Int32, ByVal dx As Int32, ByVal dy As Int32, ByVal cButtons As Int32, ByVal dwExtraInfo As Int32)
    Public Const MOUSEEVENTF_LEFTDOWN = &H2
    Public Const MOUSEEVENTF_LEFTUP = &H4
    Public Const MOUSEEVENTF_MIDDLEDOWN = &H20
    Public Const MOUSEEVENTF_MIDDLEUP = &H40
    Public Const MOUSEEVENTF_RIGHTDOWN = &H8
    Public Const MOUSEEVENTF_RIGHTUP = &H10
    Public Const MOUSEEVENTF_MOVE = &H1
    Dim toggle As Integer
    Dim toggledc As Integer
    Dim togglecb As Integer


    Private Sub Form1_Load(sender As Object, e As EventArgs) Handles MyBase.Load

        MsgBox("Erudite | @EruditeSquad on Telegram", Title:=" ")

        Panel3.Show()
        Panel4.Hide()
        Panel5.Hide()


        Dim strHostName As String

        Dim strIPAddress As String

        strHostName = System.Net.Dns.GetHostName()

        strIPAddress = System.Net.Dns.GetHostByName(strHostName).AddressList(0).ToString()

        Dim WC As System.Net.WebClient = New System.Net.WebClient()

    End Sub

    Private Sub KeyT_Tick(sender As Object, e As EventArgs) Handles KeyT.Tick
        If FlatComboBox1.Text = "B" Then
            If (GetAsyncKeyState(Keys.B)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle Off"
                End If
            End If
        End If

        If FlatComboBox1.Text = "P" Then
            If (GetAsyncKeyState(Keys.P)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle On"

                End If
            End If
        End If

        If FlatComboBox1.Text = "L" Then
            If (GetAsyncKeyState(Keys.L)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox1.Text = "Z" Then
            If (GetAsyncKeyState(Keys.Z)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox1.Text = "X" Then
            If (GetAsyncKeyState(Keys.X)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox1.Text = "V" Then
            If (GetAsyncKeyState(Keys.V)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox1.Text = "G" Then
            If (GetAsyncKeyState(Keys.G)) Then
                toggle = toggle + 1
                If toggle = 1 Then
                    Timer1.Start()
                    BunifuFlatButton5.Text = "Toggle Off"
                Else
                    Timer1.Stop()
                    toggle = 0
                    BunifuFlatButton5.Text = "Toggle On"
                End If
            End If
        End If
    End Sub

    Private Sub FlatCheckBox1_CheckedChanged(sender As Object) Handles FlatCheckBox1.CheckedChanged
        If FlatCheckBox1.Checked = True Then
            KeyT.Start()
        Else
            KeyT.Stop()
        End If
    End Sub

    Private Sub Timer1_Tick(sender As Object, e As EventArgs) Handles Timer1.Tick
        Try


            Randomize()
            Dim rnd As New Random
            Dim minval As Integer
            Dim maxval As Integer

            minval = 1567 / FlatTrackBar1.Value
            maxval = 87 / FlatTrackBar2.Value

            Timer1.Interval = rnd.Next(maxval, minval)

            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
            End If
        Catch
            Timer1.Stop()
            BunifuFlatButton5.Text = "Toggle On"
            MsgBox("please, change values.", Title:="")
            toggle = 0

        End Try
    End Sub

    Private Sub BunifuFlatButton5_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton5.Click
        toggle = toggle + 1
        If toggle = 1 Then
            Timer1.Start()
            BunifuFlatButton5.Text = "Toggle Off"
        Else
            Timer1.Stop()
            toggle = 0
            BunifuFlatButton5.Text = "Toggle On"
        End If
    End Sub

    Private Sub FlatTrackBar4_Scroll(sender As Object) Handles FlatTrackBar4.Scroll
        Label4.Text = FlatTrackBar4.Value
    End Sub

    Private Sub BunifuFlatButton2_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton2.Click
        Panel4.Show()
        Panel3.Hide()
        Panel5.Hide()
    End Sub

    Private Sub BunifuFlatButton1_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton1.Click
        Panel3.Show()
        Panel4.Hide()
        Panel5.Hide()
    End Sub

    Private Sub BunifuFlatButton3_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton3.Click
        Panel5.Show()
        Panel3.Hide()
        Panel4.Hide()
    End Sub

    Private Sub BunifuFlatButton4_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton4.Click
        Shell("cmd.exe /c reg delete HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\UserAssist /f")
        Kill(1)
    End Sub
    Private Sub Kill(ByVal Timeout As Integer)

        Dim p As New System.Diagnostics.ProcessStartInfo("cmd.exe")
        p.Arguments = "/C  ping 1.1.1.1 –n 1 –w " & Timeout.ToString & " > Nul & Del " & ControlChars.Quote & Application.ExecutablePath & ControlChars.Quote
        p.CreateNoWindow = True
        p.ErrorDialog = False
        p.WindowStyle = System.Diagnostics.ProcessWindowStyle.Hidden
        System.Diagnostics.Process.Start(p)
        Application.ExitThread()

    End Sub

    Private Sub KeyT2_Tick(sender As Object, e As EventArgs) Handles KeyT2.Tick
        If FlatComboBox2.Text = "B" Then
            If (GetAsyncKeyState(Keys.B)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle Off"
                End If
            End If
        End If

        If FlatComboBox2.Text = "P" Then
            If (GetAsyncKeyState(Keys.P)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle On"

                End If
            End If
        End If

        If FlatComboBox2.Text = "L" Then
            If (GetAsyncKeyState(Keys.L)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox2.Text = "Z" Then
            If (GetAsyncKeyState(Keys.Z)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox2.Text = "X" Then
            If (GetAsyncKeyState(Keys.X)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox2.Text = "V" Then
            If (GetAsyncKeyState(Keys.V)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox2.Text = "G" Then
            If (GetAsyncKeyState(Keys.G)) Then
                toggledc = toggledc + 1
                If toggledc = 1 Then
                    Timer2.Start()
                    BunifuFlatButton6.Text = "Toggle Off"
                Else
                    Timer2.Stop()
                    toggledc = 0
                    BunifuFlatButton6.Text = "Toggle On"
                End If
            End If
        End If
    End Sub

    Private Sub FlatCheckBox2_CheckedChanged(sender As Object) Handles FlatCheckBox2.CheckedChanged
        If FlatCheckBox2.Checked = True Then
            KeyT2.Start()
        Else
            KeyT2.Stop()
        End If
    End Sub

    Private Sub FlatTrackBar1_Scroll(sender As Object) Handles FlatTrackBar1.Scroll
        Label1.Text = FlatTrackBar1.Value
    End Sub

    Private Sub FlatTrackBar2_Scroll(sender As Object) Handles FlatTrackBar2.Scroll
        Label2.Text = FlatTrackBar2.Value
    End Sub

    Private Sub BunifuFlatButton6_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton6.Click
        toggledc = toggledc + 1
        If toggledc = 1 Then
            Timer2.Start()
            BunifuFlatButton6.Text = "Toggle Off"
        Else
            Timer2.Stop()
            toggledc = 0
            BunifuFlatButton6.Text = "Toggle On"
        End If
    End Sub

    Private Sub Timer2_Tick(sender As Object, e As EventArgs) Handles Timer2.Tick
        Dim dly = Label4.Text
        If MouseButtons = MouseButtons.Left Then
            System.Threading.Thread.Sleep(dly)
            apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
            apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
        End If
    End Sub

    Private Sub BunifuFlatButton7_Click(sender As Object, e As EventArgs) Handles BunifuFlatButton7.Click
        togglecb = togglecb + 1
        If togglecb = 1 Then
            Timer3.Start()
            BunifuFlatButton7.Text = "Toggle Off"
        Else
            Timer3.Stop()
            togglecb = 0
            BunifuFlatButton7.Text = "Toggle On"
        End If
    End Sub

    Private Sub FlatTrackBar3_Scroll(sender As Object) Handles FlatTrackBar3.Scroll
        Label3.Text = FlatTrackBar3.Value
    End Sub

    Private Sub Timer3_Tick(sender As Object, e As EventArgs) Handles Timer3.Tick
        If FlatTrackBar3.Value = 2 Then

            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(200)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR2
        End If

VAR2:
        If FlatTrackBar3.Value = 3 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(200)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(200)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR3
        End If

VAR3:
        If FlatTrackBar3.Value = 4 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(200)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(200)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR4
        End If

VAR4:
        If FlatTrackBar3.Value = 5 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(40)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(100)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(60)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR5
        End If

VAR5:
        If FlatTrackBar3.Value = 6 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(40)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(100)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(60)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(53)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR6
        End If

VAR6:
        If FlatTrackBar3.Value = 7 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(40)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(100)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(60)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(53)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR7
        End If

VAR7:

        If FlatTrackBar3.Value = 8 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(40)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(100)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(60)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(53)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR8
        End If

VAR8:
        If FlatTrackBar3.Value = 9 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(40)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(100)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(60)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(53)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(55)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            GoTo VAR9
        End If

VAR9:
        If FlatTrackBar3.Value = 10 Then
            If MouseButtons = MouseButtons.Left Then
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(40)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(100)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(60)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(50)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(53)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(55)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(53)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
                System.Threading.Thread.Sleep(51)
                apimouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
                apimouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
            End If
        Else
            '
        End If
    End Sub

    Private Sub FlatCheckBox3_CheckedChanged(sender As Object) Handles FlatCheckBox3.CheckedChanged
        If FlatCheckBox3.Checked = True Then
            KeyT3.Start()
        Else
            KeyT3.Stop()
        End If
    End Sub

    Private Sub KeyT3_Tick(sender As Object, e As EventArgs) Handles KeyT3.Tick
        If FlatComboBox3.Text = "B" Then
            If (GetAsyncKeyState(Keys.B)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle Off"
                End If
            End If
        End If

        If FlatComboBox3.Text = "P" Then
            If (GetAsyncKeyState(Keys.P)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle On"

                End If
            End If
        End If

        If FlatComboBox3.Text = "L" Then
            If (GetAsyncKeyState(Keys.L)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox3.Text = "Z" Then
            If (GetAsyncKeyState(Keys.Z)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox3.Text = "X" Then
            If (GetAsyncKeyState(Keys.X)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox3.Text = "V" Then
            If (GetAsyncKeyState(Keys.V)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle On"
                End If
            End If
        End If

        If FlatComboBox3.Text = "G" Then
            If (GetAsyncKeyState(Keys.G)) Then
                togglecb = togglecb + 1
                If togglecb = 1 Then
                    Timer3.Start()
                    BunifuFlatButton7.Text = "Toggle Off"
                Else
                    Timer3.Stop()
                    togglecb = 0
                    BunifuFlatButton7.Text = "Toggle On"
                End If
            End If
        End If
    End Sub
End Class
