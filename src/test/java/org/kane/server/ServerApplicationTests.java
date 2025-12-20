package org.kane.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
class ServerApplicationTests {

    @Test
    void contextLoads() throws IOException {
            var path = Path.of("images","user","5","479671-futuristic-sunset-city-science-fiction.jpg");
            Path imagePath = path.toAbsolutePath();
        System.out.println(imagePath);
               Files.delete(path);

    }

}
