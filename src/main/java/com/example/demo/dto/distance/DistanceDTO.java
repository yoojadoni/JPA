package com.example.demo.dto.distance;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class DistanceDTO {

    public String addressCode;

    public String addressDetail;

    public Long shopId;

    public Long deliveryCost;
}
