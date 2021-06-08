package com.calseivv.project.service;

import com.calseivv.project.persistence.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadService {

    @Autowired
    ContentRepository contentRepository;


}
