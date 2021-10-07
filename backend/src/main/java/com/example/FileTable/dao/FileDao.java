package com.example.FileTable.dao;

import com.example.FileTable.models.File;

import java.util.List;
import java.util.Optional;

public interface FileDao {

    List<File> getAllDocs();
    Optional<File> getFileByDocId(Integer docId);
    String addDoc(File doc);
    String deleteDoc(Integer docId);
}
