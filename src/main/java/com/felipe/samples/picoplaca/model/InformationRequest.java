package com.felipe.samples.picoplaca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformationRequest {
    private Vehicle vehicle;
    private String dateToValidate;
    private String timeToValidate;
}
