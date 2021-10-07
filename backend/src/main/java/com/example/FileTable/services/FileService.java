package com.example.FileTable.services;

import com.example.FileTable.dao.FileDao;
import com.example.FileTable.models.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileDao fileDao;

    @Autowired
    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public List<File> getAllDocs() {
        return fileDao.getAllDocs();
    }

    public String addDoc(File doc) {
        return fileDao.addDoc(doc);
    }

    public String deleteDoc(Integer docId) {
        return fileDao.deleteDoc(docId);
    }
}
