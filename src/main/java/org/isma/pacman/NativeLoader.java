package org.isma.pacman;

import nullprogram.Arch;
import nullprogram.NativeGuide;

import java.io.IOException;
import java.util.logging.Logger;

import static nullprogram.Arch.WINDOWS_32;
import static nullprogram.Arch.WINDOWS_64;

public class NativeLoader {
    private static final Logger LOGGER = Logger.getLogger(NativeLoader.class.getName());

    private static final String LWJGL_NATIVE = "/lib_lwjgl/native/";

    public void load() throws IOException {
        if (isWin64()) {
            LOGGER.info("arch : win64\n");
            NativeGuide.prepare(WINDOWS_64, LWJGL_NATIVE + "windows/jinput-dx8_64.dll");
            NativeGuide.prepare(WINDOWS_64, LWJGL_NATIVE + "windows/jinput-dx8_64.dll");
            NativeGuide.prepare(WINDOWS_64, LWJGL_NATIVE + "windows/jinput-raw_64.dll");
            NativeGuide.prepare(WINDOWS_64, LWJGL_NATIVE + "windows/lwjgl64.dll");
            NativeGuide.prepare(WINDOWS_64, LWJGL_NATIVE + "windows/OpenAL64.dll");
        } else if (isWin32()) {
            LOGGER.info("arch : win32\n");
            NativeGuide.prepare(WINDOWS_32, LWJGL_NATIVE + "windows/jinput-dx8.dll");
            NativeGuide.prepare(WINDOWS_32, LWJGL_NATIVE + "windows/jinput-dx8.dll");
            NativeGuide.prepare(WINDOWS_32, LWJGL_NATIVE + "windows/jinput-raw.dll");
            NativeGuide.prepare(WINDOWS_32, LWJGL_NATIVE + "windows/lwjgl.dll");
            NativeGuide.prepare(WINDOWS_32, LWJGL_NATIVE + "windows/OpenAL32.dll");
        } else if (isMacOs32()) {
            LOGGER.info("arch : macos32\n");
            NativeGuide.prepare(Arch.MAC_32, LWJGL_NATIVE + "macosx/libjinput-osx.jnilib");
            NativeGuide.prepare(Arch.MAC_32, LWJGL_NATIVE + "macosx/liblwjgl.jnilib");
            NativeGuide.prepare(Arch.MAC_32, LWJGL_NATIVE + "macosx/openal.dylib");
        } else if (isMacOs64()) {
            LOGGER.info("arch : macos64\n");
            NativeGuide.prepare(Arch.MAC_64, LWJGL_NATIVE + "macosx/libjinput-osx.jnilib");
            NativeGuide.prepare(Arch.MAC_64, LWJGL_NATIVE + "macosx/liblwjgl.jnilib");
            NativeGuide.prepare(Arch.MAC_64, LWJGL_NATIVE + "macosx/openal.dylib");
        }else {
            System.err.println("OS not yet handled");
            System.exit(1);
        }
    }

    private boolean isWin64() {
        String arch = System.getProperty("os.arch").toLowerCase();
        String os = System.getProperty("os.name").toLowerCase();
        return os.startsWith("win") && arch.contains("64");
    }

    private boolean isWin32() {
        String arch = System.getProperty("os.arch").toLowerCase();
        String os = System.getProperty("os.name").toLowerCase();
        return os.startsWith("win") && !arch.contains("64");
    }

    private boolean isMacOs64() {
        String arch = System.getProperty("os.arch").toLowerCase();
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac") && arch.contains("64");
    }

    private boolean isMacOs32() {
        String arch = System.getProperty("os.arch").toLowerCase();
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac") && !arch.contains("64");
    }

}
