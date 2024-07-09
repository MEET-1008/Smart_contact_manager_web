package com.codewithMeet.scm.scm0_2.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImgService {

    String UploadIMG(MultipartFile multipartFile) throws IOException;

    String GetUrlfrompublicid (String id);
}
