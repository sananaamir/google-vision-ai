package com.sanan.googlevisionai.controller;

import com.sanan.googlevisionai.dto.FacialAnnotation;
import com.sanan.googlevisionai.dto.LabelAnnotation;
import com.sanan.googlevisionai.service.VisionAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class VisionController {

    @Autowired
    private VisionAIService visionAIService;

    @PostMapping("/label-image")
    public List<LabelAnnotation> labelImage(@RequestParam("file") MultipartFile file) {
        return visionAIService.fetchImageLabelAnnotations(file);
    }

    @PostMapping("/process-facial-expressions")
    public List<FacialAnnotation> processFacialExpressions(@RequestParam("file") MultipartFile file) {
        return visionAIService.fetchImageFaceAnnotations(file);
    }
}
