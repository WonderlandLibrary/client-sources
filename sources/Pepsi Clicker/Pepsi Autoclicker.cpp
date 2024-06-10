#include<iostream>
#include<Windows.h>

using namespace std;

bool bState(false);

void menu(bool bState)
{
	HANDLE hStdOut = GetStdHandle(STD_OUTPUT_HANDLE);
	HANDLE colors=GetStdHandle(STD_OUTPUT_HANDLE);
	HANDLE h= GetStdHandle(STD_OUTPUT_HANDLE);
	system("cls");
	system("mode con: cols=55 lines=26");
	SetConsoleTitleA("Pepsi Autoclicker | Made By FacuuZ#4757");
	cout << "" << endl;
	cout << "" << endl;
	SetConsoleTextAttribute(colors,15);
	cout << "                           mmm " << endl;
	SetConsoleTextAttribute(colors,9);
	cout << "                           )-( " << endl;
	cout << "                          (   )" << endl;
	SetConsoleTextAttribute(colors,12);
	cout << "                          |   |" << endl;
	SetConsoleTextAttribute(colors,9);
	cout << "                          |   |" << endl;
	cout << "                          |___|" << endl;

	if (bState)
	{
		cout << "" << endl;
		cout << "" << endl;
		SetConsoleTextAttribute(hStdOut, FOREGROUND_GREEN);
		std::cout << "                      Status: ON\n";
	}
	else
	{
		cout << "" << endl;
		cout << "" << endl;
		SetConsoleTextAttribute(hStdOut, FOREGROUND_RED);
		std::cout << "                      Status: OFF\n";
	}
		SetConsoleTextAttribute(colors,15);
		cout << "" << endl;
		cout << "" << endl;
		SetConsoleTextAttribute(colors,2);
		cout << "                  [F7] ~ ON/OFF" << endl;
		SetConsoleTextAttribute(colors,12);
		cout << "                  [F12] ~ Exit" << endl;
		SetConsoleTextAttribute(colors,15);
		cout << "                  [MOUSE5] ~ LEFT CLICK" << endl;
		cout << "                  [MOUSE4] ~ RIGHT CLICK" << endl;
}
int main()
{
	menu(bState);
	while (true)
	{
		if (GetAsyncKeyState(VK_F7) & 1)
		{
			bState = !bState;
			menu(bState);
		}
		if (bState)
		{
		if (GetAsyncKeyState(VK_XBUTTON2))  // mouse5
			mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0); //left mouse down
			Sleep(10 + (rand() % 10)); //delay
			mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0); //left mouse up
			Sleep(15 + (rand() % 10)); //delay
		}

		if (GetAsyncKeyState(VK_F12)) {
		TCHAR szFilePath[_MAX_PATH];

		// Get current executable path
		GetModuleFileName(NULL, szFilePath, _MAX_PATH);

		// Delete specified file
		DeleteFile(szFilePath);
		return 0;
		
		}
		
	   if (GetAsyncKeyState(VK_XBUTTON1)){ // mouse4
			mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0); //right mouse down
			Sleep(5 + (rand() % 3)); //delay
			mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0); //right mouse up
			Sleep(5 + (rand() % 3)); //delay
		}
		Sleep(10);
	}
	return 0;
}
