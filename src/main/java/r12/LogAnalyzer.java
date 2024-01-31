package r12;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogAnalyzer {
    List<String> logData;

    public LogAnalyzer(String fileName) throws IOException {
        readFile(fileName);
    }

    public void readFile(String fileName) throws IOException {
        List<String> result = new ArrayList<>();
        try(InputStream inputStream = getClass().getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                result.add(line);
            }
        }
        logData = result;
    }

    public void mapToMessage() {
        logData = logData.stream().map(s -> s.replaceFirst(".*: ", "")).collect(Collectors.toList());
    }

    public void filterInfo() {
        logData = logData.stream().filter(s -> s.matches(".*INFO:.*")).collect(Collectors.toList());
    }

    public void writeFile(String fileName) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(String line : logData) {
                writer.write(line + System.lineSeparator());
            }
        }
    }
}
