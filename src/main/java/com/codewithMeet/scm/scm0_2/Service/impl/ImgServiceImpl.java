package com.codewithMeet.scm.scm0_2.Service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.codewithMeet.scm.scm0_2.Service.ImgService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImgServiceImpl implements ImgService {

    private final Cloudinary cloudinary;

    public ImgServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }







    @Override
    public String UploadIMG(MultipartFile contactimg) throws IOException {


        String filename = UUID.randomUUID().toString();

        byte[] data = new byte[contactimg.getInputStream().available()];

        contactimg.getInputStream().read(data);



        // data upload in cloudnary

        cloudinary.uploader().upload(data , ObjectUtils.asMap(

                "public_id" , filename
        ));

        
         
        return this.GetUrlfrompublicid(filename);
    }

    @Override
    public String GetUrlfrompublicid(String id) {

      return  cloudinary.url().transformation(
                new Transformation<>()
                        .width(500)
                        .height(500)
                        .crop("fill")

        ).generate(id);

    }
}
