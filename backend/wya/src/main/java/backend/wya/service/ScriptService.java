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

    public String runScript(String path) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", SCRIPT_DIRECTORY + programName, path);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        process.waitFor();

//        String line;
//        Result result = new Result();
//        line = reader.readLine();
////        if (line.equals("True")) {
//            result.setFlagged(line);
//            result.setLandMark(reader.readLine());
//            result.setText(reader.readLine());
////        } else {
////            result.setFlagged(false);
////        }
//        reader.close();
//        return result;

        String line;
        StringBuilder sb = new StringBuilder();
        while((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
