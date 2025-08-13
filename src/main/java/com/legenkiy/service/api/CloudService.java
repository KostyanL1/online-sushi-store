package com.legenkiy.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

    String upload(MultipartFile multipartFile, String title);

}
