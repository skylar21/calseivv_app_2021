package com.calseivv.project.service;

import com.calseivv.project.persistence.model.ContentEntity;
import com.calseivv.project.persistence.repository.ContentRepository;
import com.calseivv.project.response.GetContentResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;

@Service
public class ContentService {

    @Autowired
    ContentRepository contentRepository;

    /**
     * @param contentId Refers to the user ID of the user where the image will be get
     * @return
     */
    public GetContentResponse getImage(UUID contentId) {
        try {
            GetContentResponse getContentResponse = new GetContentResponse();
            Map<String, StreamedContent> contentImagesMap = new HashMap<>();

            if (contentId != null) {
                Optional<ContentEntity> contentOp = contentRepository.findById(contentId);
                if (contentOp != null) {
                    ContentEntity contentEntity = contentOp.get();
                    ByteArrayInputStream portraitIStream;
                    ByteArrayInputStream identificationIStream;
                    BufferedImage portraitBuffer;
                    BufferedImage identificationBuffer;
                    ByteArrayOutputStream portraitOStream = new ByteArrayOutputStream();
                    ByteArrayOutputStream identificationOStream = new ByteArrayOutputStream();
                    if (contentEntity.getPortraitByte() != null) {
                        portraitIStream = new ByteArrayInputStream(contentEntity.getPortraitByte());
                        portraitBuffer = ImageIO.read(portraitIStream);
                        ImageIO.write(portraitBuffer, "png", portraitOStream);
//                        contentImagesMap.put("portrait", new ByteArrayInputStream(portraitOStream.toByteArray()));
                        contentImagesMap.put("portrait", DefaultStreamedContent.builder().contentType("image/png").stream(() -> new ByteArrayInputStream(portraitOStream.toByteArray())).build());

                    }
                    if (contentEntity.getIdentificationByte() != null) {
                        identificationIStream = new ByteArrayInputStream(contentEntity.getIdentificationByte());
                        identificationBuffer = ImageIO.read(identificationIStream);
                        ImageIO.write(identificationBuffer, "png", identificationOStream);
//                        contentImagesMap.put("identification", new ByteArrayInputStream(identificationOStream.toByteArray()));
                        contentImagesMap.put("identification", DefaultStreamedContent.builder().contentType("image/png").stream(() -> new ByteArrayInputStream(identificationOStream.toByteArray())).build());
                    }
                }
                getContentResponse.setContentImagesMap(contentImagesMap);
                return getContentResponse;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param imageByte  Refers to the image byte to be saved
     * @param isPortrait Refers to the type of image to be saved is a portrait image or an identification image
     */
    public void saveImage(byte[] imageByte, boolean isPortrait) {
        ContentEntity contentEntity = new ContentEntity();
        if (isPortrait) {
            contentEntity.setPortraitByte(imageByte);
        } else {
            contentEntity.setIdentificationByte(imageByte);
        }
        contentRepository.save(contentEntity);
    }

}
