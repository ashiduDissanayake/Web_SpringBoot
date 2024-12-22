package com.scope.server.service;

import com.scope.server.model.Image;
import com.scope.server.repo.ImageRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ImageService {
    @Autowired
    ImageRepo imageRepo;

    public List<Image> list(){
        return imageRepo.findAll();
    }

    public Image getImageById(int id){
        return imageRepo.findById(id).orElse(null);
    }

    public void save(Image image){
        imageRepo.save(image);
    }

    public void delete(int id){
        imageRepo.deleteById(id);
    }

    public boolean exists(int id){
        return imageRepo.existsById(id);
    }
}
