package br.com.sol7.orcamento.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ApplicationResources {

    private Path basePath;

    @Autowired
    public ApplicationResources(@Value("#{environment['user.home']}") String basePath) throws IOException {
        this.basePath = Paths.get(basePath, "repositorio-orc");
        Files.createDirectories(this.basePath);
    }

    public String getPath(String path){
        return Paths.get(basePath.toString(), path).toString();
    }

    public Path getBasePath() {
        return basePath;
    }
}