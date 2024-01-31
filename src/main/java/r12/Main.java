package r12;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Hello, World!");
        LogAnalyzer logAnalyzer = new LogAnalyzer("/R12InputLog.txt");
        logAnalyzer.filterInfo();
        logAnalyzer.mapToMessage();
        logAnalyzer.writeFile("output.txt");
        Path file = Paths.get((Main.class.getResource("/R12InputLog.txt").toURI()));
        try(Stream<String> stringStream = Files.lines(file)) {
                stringStream.forEach(System.out::println);
        }
    }
}
