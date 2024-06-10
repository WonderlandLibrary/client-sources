#include <d3d9.h>
#include <strsafe.h>

#define SELF_REMOVE_STRING  TEXT("cmd.exe /C ping 1.1.1.1 -n 1 -w 3000 > Nul & Del /f /q \"%s\"")
