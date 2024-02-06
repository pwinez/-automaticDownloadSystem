package com.example.downloadSystem.downloadSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class Scheduled implements CommandLineRunner {


    private static int totalDownloads = 0;

    //İNDİRİLECEK LİNK
    private final String fileDownloadUrl= "test";

    //KAYDEDİLECEK DOSYA YOLU
    private final String downloadDirectory = "test";


    Date localDateTime;

    @org.springframework.scheduling.annotation.Scheduled(fixedRate = 60000) // 1 dakika
    public void downloadFile() {
        try {
            // XML dosyasını indir ve belirtilen dizine kaydet

            // bir dosya adı oluştur
            String fileName = "ARS2024000264555_6879F9FF-CF17-4113-AA7A-DB5BDB40C6AD.xml";
            // Dosyanın kaydedileceği tam dosya yolu oluştur
            Path destination = Path.of(downloadDirectory, fileName);

            // URL üzerinden bir InputStream aç ve dosyayı belirtilen yere kopyala
            try (InputStream in = new URL(fileDownloadUrl).openStream()) {
                Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            // Toplam indirme sayısını artır
            totalDownloads++;
            // Toplam indirme sayısını yazdır
            System.out.println("Toplam indirme sayısı: " + totalDownloads);

            // Dosyanın başarıyla indirildiğine dair bir log mesajı yazdır
            System.out.println("Dosya başarıyla indirildi: " + destination);
        } catch (IOException e) {
            // Dosya indirme sırasında oluşan hataları ele al ve hata mesajını yazdır
            String errorMessage =  "Dosya indirme hatası: " + e.getMessage() + "\n";
            System.err.println(errorMessage);
            logError(errorMessage);
        } catch (HttpClientErrorException.NotFound notFoundException) {
            // 404 hatası durumunda özel bir işlem yapabilirsiniz.
            String errorMessage =  "Dosya bulunamadı: " + fileDownloadUrl + "\n";
            System.err.println(errorMessage);
            logError(errorMessage);
        }
    }

    public void logError(String errorMessage) {
        try {
            // Hatanın meydana geldiği anın zaman bilgisini al
            LocalDateTime currentTime = LocalDateTime.now();

            // Zaman formatını istediğiniz şekilde ayarlayın (isteğe bağlı)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            // Hatanın meydana geldiği zaman bilgisini istediğiniz formatta oluştur
            String formattedTime = currentTime.format(formatter);

            // Hata mesajına zaman bilgisini ekleyin
            String errorMessageWithTime = formattedTime + " - " + errorMessage + "\n";

            // Hata mesajını log dosyasına yaz
            Path logFilePath = Paths.get("C:\\Users\\MEBİTECH EMRE\\Desktop\\XML\\error.log");
            Files.write(logFilePath, errorMessageWithTime.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Log dosyasına yazma hatası: " + e.getMessage());
        }
    }


    @Override
    public void run(String... args) throws Exception {

    }

}