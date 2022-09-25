package backend.wya.controller;

import backend.wya.model.Result;
import backend.wya.service.ScriptService;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("api/v1")
public class UploadController {

    @Autowired
    private ScriptService scriptService;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @GetMapping("uploadimage")
    public String displayUploadForm() {
//        return new ResponseEntity<>(UPLOAD_DIRECTORY, HttpStatus.OK);
        return "index";
    }

    @PostMapping("upload")
    public ResponseEntity<Result> uploadImage(@RequestBody MultipartFile file) throws Exception {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        Result result = scriptService.runScript(fileNameAndPath.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("getImage")
    @ResponseBody
    public FileSystemResource getFile() {
        return new FileSystemResource("/Users/pasansirithanachai/repositories/hackrice12/wya/backend/wya/result/detected.png");
    }
}