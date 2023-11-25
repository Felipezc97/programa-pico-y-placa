package com.felipe.samples.picoplaca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.samples.picoplaca.api.InformationQueryService;
import com.felipe.samples.picoplaca.exception.PicoPlacaException;
import com.felipe.samples.picoplaca.model.InformationRequest;

@RestController
@RequestMapping("information")
public class InformationQueryController {

	@Autowired
	InformationQueryService informationQueryService;

	@GetMapping("validate-allowed")
	public String validateAllowedVehicleToCirculate(@RequestBody InformationRequest informationRequest) {
		String responseMessage = "";
		try {
			responseMessage = informationQueryService.validateAllowedOrNot(informationRequest);
		} catch (PicoPlacaException ex) {
			responseMessage = ex.getMessage();
		}
		return responseMessage;

	}

}
