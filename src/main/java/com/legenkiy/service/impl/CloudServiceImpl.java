package com.legenkiy.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.legenkiy.exceptions.CloudUploadException;
import com.legenkiy.service.api.CloudService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudServiceImpl implements CloudService {
    @Value("${cloudinary.key}")
    private String cloudinaryKey;
    @Value("${cloudinary.secret}")
    private String cloudinarySecret;
    @Value("${cloudinary.name}")
    private String cloudinaryName;
    private Cloudinary cloudinary;


    @PostConstruct
    public void init(){
        this.cloudinary = new Cloudinary("cloudinary://" + cloudinaryKey + ":" + cloudinarySecret + "@" + cloudinaryName);
    }

    @Override
    public String upload(MultipartFile multipartFile, String title) {
        try {
            Map details = cloudinary.uploader().upload(
                    multipartFile.getBytes(),
                    ObjectUtils.asMap("public_id", title)
            );
            return details.get("secure_url").toString();

        } catch (Exception e) {
            throw new CloudUploadException("Error while uploading file to cloud storage");
        }
    }


}
