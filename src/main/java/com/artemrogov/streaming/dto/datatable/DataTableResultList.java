package com.artemrogov.streaming.dto.datatable;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class DataTableResultList implements Serializable {

    @JsonProperty("draw")
    private long draw;

    @JsonProperty("recordsTotal")
    private long recordsTotal;

    @JsonProperty("recordsFiltered")
    private long recordsFiltered;

    @JsonProperty("data")
    private List<?> data = new ArrayList<>();
}
