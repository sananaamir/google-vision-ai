package com.sanan.googlevisionai.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.cloud.vision.v1.Likelihood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FacialAnnotation {
    private Likelihood joyLikelihood;
    private Likelihood sorrowLikelihood;
    private Likelihood angerLikelihood;
    private Likelihood surpriseLikelihood;
    private Likelihood underExposedLikelihood;
    private Likelihood blurredLikelihood;
    private Likelihood headwearLikelihood;
}
