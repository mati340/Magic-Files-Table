package com.example.FileTable.dao;

import com.example.FileTable.models.File;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LocalDB implements FileDao{

    private static List<File> DB = new ArrayList<>();

    @Override
    public List<File> getAllDocs() {
        return DB;
    }

    @Override
    public Optional<File> getFileByDocId(Integer docId) {
        return DB.stream().filter(file1 -> file1.getDocId().equals(docId) ).findFirst();
    }

    @Override
    public String addDoc(File doc)
    {
        var isExist = getFileByDocId(doc.getDocId());
        var isAdded = false;

        // check if docId is already exist
        if(isExist.isEmpty()){
            isAdded = DB.add(doc);
        }
        else {
            return new String("Can not upload document! DocId: ").concat(String.valueOf(doc.getDocId())).concat(" is already exist");
        }

        if(isAdded == true)
            return new String("File Uploaded successfully!");
        else
            return new String("Unknow error: Can not upload file! Please try again later");

    }

    @Override
    public String deleteDoc(Integer docId)
    {
        Optional<File> fileToDelete = getFileByDocId(docId);

        // check if document is exist
        if (!fileToDelete.isEmpty()){
            var isDeleted = DB.remove(fileToDelete.get());

            // check if the document deleted successfully
            if(isDeleted)
                return new String("File deleted successfully!");
            else
                return new String("Unknow error: Can not delete! Please try again later");
        }
        return new String("Document not found!");
    }
}
