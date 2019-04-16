package com.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Long id;
    private String text;
    private CategoryDto category;
    private LevelDto level;
    private List<AnswerDto> answers;
    private Integer goodAnswer;
    private MultipartFile multipartFile;
    private String filename;
}
