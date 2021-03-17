package me.tomasztopola.fileManagers;

import java.io.File;

public class DirCreator {
    public static void mkdir(String path, String dir){
        File newDir = new File(path + dir);
        newDir.mkdirs();
    }
}
