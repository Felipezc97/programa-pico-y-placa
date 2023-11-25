package com.felipe.samples.picoplaca.api;

import com.felipe.samples.picoplaca.model.InformationRequest;

public interface InformationQueryService {
    String validateAllowedOrNot(InformationRequest informationRequest);
}
