#include "..\..\klynt.h"

void enablef(double value) {
    double buffer;
    for (size_t x = 0; x < v::modules::fly::locationsfly.size(); x++) {
        if (ReadProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::fly::locationsfly[x], &buffer, sizeof(double), 0)) {
            if (buffer == 0.9800000190734863 || buffer == 0.08) {
                WriteProcessMemory(v::info::minecraft_handle, (LPVOID)v::modules::fly::locationsfly[x], &value, sizeof(double), 0);
            }
            else {
                v::modules::fly::locationsfly.erase(v::modules::fly::locationsfly.begin() + x);
                x = 0;
            }
        }
        else {
            v::modules::fly::locationsfly.erase(v::modules::fly::locationsfly.begin() + x);
            x = 0;
        }
    }

}

void v::modules::fly::disable() {
    enablef(0.9800000190734863);
}

void v::modules::fly::thread() {

    while (v::info::verified) {
        if (v::modules::fly::enabled) {
            v::modules::fly::used = true;
            enablef(0.08);

        }
        else {

            if (v::modules::fly::used) {

                disable();
                v::modules::fly::used = false;

            }

        }

        if (v::modules::fly::enabled) {
            std::this_thread::sleep_for(std::chrono::milliseconds(500));
        }
        else {
            std::this_thread::sleep_for(std::chrono::milliseconds(50));
        }

    }
}