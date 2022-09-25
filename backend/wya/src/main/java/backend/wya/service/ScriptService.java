package backend.wya.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ScriptService {

    public static String SCRIPT_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/script/";
    private final String programName = "hello.py";

    public String runScript(String path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", SCRIPT_DIRECTORY + programName, path);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("ERROR: Script fails");
        }
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
