package com.example.FileTable.controllers;

import com.example.FileTable.models.File;
import com.example.FileTable.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<File> getAllFiles() {
        return fileService.getAllDocs();
    }

    // convert line from file to document in specific format
    private File lineToDoc(String s) throws ParseException {

        File file = null;
        int[] indecesOfParams = {0,1,10,18,27,28,38};

        // format have 38 chars
        if(s.length() == 38){
            char[] docType = new char[indecesOfParams[1] - indecesOfParams[0]];
            char[] companyId = new char[indecesOfParams[2] - indecesOfParams[1]];
            char[] date = new char[indecesOfParams[3] - indecesOfParams[2]];
            char[] docId = new char[indecesOfParams[4] - indecesOfParams[3]];
            char[] sign = new char[indecesOfParams[5] - indecesOfParams[4]];
            char[] amount = new char[indecesOfParams[6] - indecesOfParams[5]];

            s.getChars(indecesOfParams[0],indecesOfParams[1],docType,0);
            s.getChars(indecesOfParams[1],indecesOfParams[2],companyId,0);
            s.getChars(indecesOfParams[2],indecesOfParams[3],date,0);
            s.getChars(indecesOfParams[3],indecesOfParams[4],docId,0);
            s.getChars(indecesOfParams[4],indecesOfParams[5],sign,0);
            s.getChars(indecesOfParams[5],indecesOfParams[6],amount,0);

            Date parsedDate = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(date));
            file = new File(
                    String.valueOf(docType),
                    Integer.parseInt(String.valueOf(companyId)),
                    parsedDate,
                    Integer.parseInt(String.valueOf(docId)),
                    String.valueOf(sign),
                    Integer.parseInt(String.valueOf(amount)));
        }
        return file;
    }

    @PostMapping(path = "/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile multipartFile)
    {
        List<File> files = new ArrayList<>();

        // reading file line by line and add document in DB for each line
        try {
            InputStream inputStream = multipartFile.getInputStream();
            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .forEach(line -> {
                        File file = null;
                        try {
                            file = lineToDoc(line);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(file != null)
                            files.add(file);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check for errors
        AtomicReference<String> res = new AtomicReference<>();
        AtomicBoolean isError = new AtomicBoolean(false);
        files.forEach(file -> {
            String msg = fileService.addDoc(file);
            if(isError.get()){ return; }
            if(msg.contains("Can not")){
                isError.set(true);
            }
            res.set(msg);
        });

        // correct response according to message
        if (res.get().contains("success")){
            return ResponseEntity.ok().body(res);
        }
        else{
            return ResponseEntity.badRequest().body(res);
        }
    }


    @DeleteMapping("{docId}")
    public ResponseEntity deleteFile(@PathVariable("docId") Integer docId)
    {
        var res = fileService.deleteDoc(docId);

        // correct response according to message
        if (res.contains("success")){
            return ResponseEntity.ok().body(res);
        }
        else{
            return ResponseEntity.badRequest().body(res);
        }
    }
}
