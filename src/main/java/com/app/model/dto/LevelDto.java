package com.app.model.dto;

import com.app.model.LevelPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelDto {
    private Long id;
    private LevelPoint nameLevel;
}
