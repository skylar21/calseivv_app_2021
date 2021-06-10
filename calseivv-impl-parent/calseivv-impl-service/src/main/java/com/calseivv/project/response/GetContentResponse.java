package com.calseivv.project.response;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetContentResponse {

    Map<String, StreamedContent> contentImagesMap = new HashMap<>();

    public Map<String, StreamedContent> getContentImagesMap() {
        return contentImagesMap;
    }

    public void setContentImagesMap(Map<String, StreamedContent> contentImagesMap) {
        this.contentImagesMap = contentImagesMap;
    }
}
