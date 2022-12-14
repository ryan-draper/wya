package backend.wya.service;

import backend.wya.model.Result;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ScriptService {

    public static String SCRIPT_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/script/";
    private final String programName = "detect.py";

    public Result runScript(String path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", SCRIPT_DIRECTORY + programName, path);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();

        String line;
        Result result = new Result();
        StringBuilder sb = new StringBuilder();
        line = reader.readLine();
        sb.append(line);
        if (line.equals("True")) {
            result.setFlagged(true);
            line = reader.readLine();
            sb.append(line);
            result.setLandMark(line);
        } else {
            result.setFlagged(false);
        }
        reader.close();
        System.out.println(sb.toString());
        return result;
    }
}
