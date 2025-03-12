//package suka.ok;
//
//import java.nio.file.*;
//import java.io.IOException;
//
//public class AppDataExample {
//    public static void main(String[] args) {
//        // Получаем путь к профилю пользователя через переменную окружения
//        String userProfile = System.getenv("USERPROFILE");
//        if (userProfile == null) {
//            System.out.println("idk 1");
//            return;
//        }
//
//        // Формируем путь к AppData
//        Path appDataPath = Paths.get(userProfile, "AppData");
//
//        // Проверяем, существует ли папка
//        if (Files.exists(appDataPath)) {
//            System.out.println("papka AppData naydena: " + appDataPath.toString());
//        } else {
//            System.out.println("papka AppData ne naydena");
//            return;
//        }
//
//        // Пример: выводим все файлы и папки в AppData
//    }
//}
