//package suka.ok;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.file.Files;
//
//public class PathCollector {
//    public static void main(String[] args) throws IOException, FileNotFoundException {
//        File dir = new File(".");
//        File files = new File("build.gradle.kts");
//        dir.list();
//        String[] names = dir.list();
//        for (int i = 0; i < names.length; i++ ) {
//            System.out.println(names[i]);
//        }
//        FileInputStream fileInputStream = new FileInputStream(files);
//        int lenght = fileInputStream.available();
//        System.out.println(files.length());
//        System.out.println(lenght);
//
//    }
//}