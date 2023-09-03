package com.artemrogov.streaming.model.datatable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTableRequest implements Serializable {
    @JsonProperty("draw")
    private long draw;
    @JsonProperty("start")
    private long start;
    @JsonProperty("length")
    private long length;
}
