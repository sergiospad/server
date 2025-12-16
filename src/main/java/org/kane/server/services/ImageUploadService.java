package org.kane.server.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageUploadService {
    public enum ImagePrefix{
        USER, POST;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
    private static final String BUCKET = "images";

    @SneakyThrows
    public static Optional<byte[]> get(String imagePath){
        Path fullPath = Paths.get(BUCKET, imagePath);
        return Files.exists(fullPath)?
                Optional.of(Files.readAllBytes(fullPath))
                :Optional.empty();
    }

    public static Path getImagePath(String imagePath, long id, String prefix){
        return Paths.get(prefix, Long.toString(id), imagePath);
    }

    @SneakyThrows
    public static void delete(String imagePath, long id, String prefix) {
        Files.delete(getImagePath(imagePath, id, prefix));
    }

    @SneakyThrows
    public static String saveImage(MultipartFile file, long id, String prefix) {
        var imagePath = getImagePath(file.getOriginalFilename(), id, prefix).toString();
        Path fullPath = Paths.get(BUCKET, imagePath);
        try(var inputStream = file.getInputStream()) {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath,
                    inputStream.readAllBytes(),
                    CREATE, TRUNCATE_EXISTING);
        }
        return imagePath;
    }

}
