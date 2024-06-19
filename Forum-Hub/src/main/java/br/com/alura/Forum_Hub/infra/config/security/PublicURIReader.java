package br.com.alura.Forum_Hub.infra.config.security;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
class PublicURIReader {

    private static final String FILE_PATH = "assets/public-uris.txt";

    public List<String> getPublicURIs() throws IOException {
        List<String> publicURIs = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_PATH)) {
            assert inputStream != null;
            try (Scanner scanner = new Scanner(inputStream)) {
                while (scanner.hasNextLine()) {
                    publicURIs.add(scanner.nextLine());
                }
            }
        }
        return publicURIs;
    }
}
