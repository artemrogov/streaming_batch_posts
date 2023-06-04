package com.artemrogov.streaming.dto.notion;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentDataDto implements Serializable {
    private Long id;
    private String title;

    private List<Long> categoriesIds = new ArrayList<>();
}
