package com.sanan.googlevisionai.service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import com.sanan.googlevisionai.dto.FacialAnnotation;
import com.sanan.googlevisionai.dto.LabelAnnotation;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisionAIService {

    public List<LabelAnnotation> fetchImageLabelAnnotations(MultipartFile file) {
        List<AnnotateImageResponse> responses;
		List<LabelAnnotation> labelAnnotations;

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
			ByteString imgBytes = ByteString.copyFrom(file.getBytes());

            List<AnnotateImageRequest> requests = new ArrayList<>();
			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
			AnnotateImageRequest request =
					AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			// Performs label detection on the image file
			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
			responses = response.getResponsesList();

			labelAnnotations = new ArrayList<>();

            for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.format("Error: %s%n", res.getError().getMessage());
					return null;
				}

				for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
				    labelAnnotations.add(
				    		LabelAnnotation.builder()
									.description(annotation.getDescription()).
									mid(annotation.getMid()).
									score(annotation.getScore()).
									topicality(annotation.getTopicality()).
									build()
					);
				}
			}
		} catch (Exception e) {
            System.out.println(e.getMessage());
            labelAnnotations = null;
        }
        return labelAnnotations;
    }

    public List<FacialAnnotation> fetchImageFaceAnnotations(MultipartFile file) {
		List<AnnotateImageResponse> responses;
		List<FacialAnnotation> facialAnnotations;

		try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
			ByteString imgBytes = ByteString.copyFrom(file.getBytes());

			List<AnnotateImageRequest> requests = new ArrayList<>();
			Image img = Image.newBuilder().setContent(imgBytes).build();
			Feature feat = Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build();
			AnnotateImageRequest request =
					AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
			requests.add(request);

			// Performs label detection on the image file
			BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
			responses = response.getResponsesList();

			facialAnnotations = new ArrayList<>();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.format("Error: %s%n", res.getError().getMessage());
					return null;
				}

				for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
					facialAnnotations.add(
							FacialAnnotation.builder()
							.angerLikelihood(annotation.getAngerLikelihood())
							.blurredLikelihood(annotation.getBlurredLikelihood())
							.headwearLikelihood(annotation.getHeadwearLikelihood())
							.joyLikelihood(annotation.getJoyLikelihood())
							.sorrowLikelihood(annotation.getSorrowLikelihood())
							.surpriseLikelihood(annotation.getSurpriseLikelihood())
							.underExposedLikelihood(annotation.getUnderExposedLikelihood())
							.build()
					);
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			facialAnnotations = null;
		}
		return facialAnnotations;
	}
}
