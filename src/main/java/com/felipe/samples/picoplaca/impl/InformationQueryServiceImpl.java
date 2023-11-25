package com.felipe.samples.picoplaca.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.felipe.samples.picoplaca.api.InformationQueryService;
import com.felipe.samples.picoplaca.exception.PicoPlacaException;
import com.felipe.samples.picoplaca.model.Constants;
import com.felipe.samples.picoplaca.model.InformationRequest;
import com.felipe.samples.picoplaca.model.PicoPlacaConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InformationQueryServiceImpl implements InformationQueryService {

	@Override
	public String validateAllowedOrNot(InformationRequest informationRequest) {

		// Declaration of variables for validation
		int plateLastDigit = -1;
		boolean isVehicleAllowed = true;

		try {
			plateLastDigit = informationRequest.getVehicle().getPlateLastDigit();
			log.info("Last digit of the vehicle plate is " + plateLastDigit);

			// Validate only if the vehicle type is not exempt for Pico & Placa System
			if (!informationRequest.getVehicle().getType().isExempt()) {
				
				isVehicleAllowed = validateTime(informationRequest.getTimeToValidate())
						|| validateDay(informationRequest.getDateToValidate(), plateLastDigit);

			}
			return buildResponseMessage(informationRequest, isVehicleAllowed);

		} catch (PicoPlacaException ex) {
			log.error(ex.getMessage());
			throw ex;
		}
	}

	private boolean validateDay(String dateString, int plateLastDigit) {

		try {
			// Get current date
			LocalDate date = LocalDate.now();

			// Validation for specific date when attribute is not empty
			if (dateString != null && !dateString.isEmpty()) {
				date = LocalDate.parse(dateString);
				log.info("Validation for specific date: " + dateString);
			}

			// Get day of the week from date
			DayOfWeek dayOfWeek = date.getDayOfWeek();
			log.info("Day of week for validation is " + dayOfWeek);

			// Pico & Placa System does not apply on weekends
			if (!Constants.SATURDAY.equalsIgnoreCase(dayOfWeek.toString())
					&& !Constants.SUNDAY.equalsIgnoreCase(dayOfWeek.toString())) {

				// Get disallowed digits for the specific day of the week
				int[] disallowedDigits = PicoPlacaConfiguration.valueOf(dayOfWeek.toString()).getAllowedDigits();
				log.info("Disallowed digits for " + dayOfWeek + " are " + disallowedDigits[0] + " "
						+ disallowedDigits[1]);

				for (int disallowedDigit : disallowedDigits) {
					// Validate if the plate last digit matches with the disallowed digits
					if (disallowedDigit == plateLastDigit)
						return false;
				}
			}
		} catch (DateTimeParseException ex) {
			throw new PicoPlacaException("The given date cannot be converted to a valid date");
		} catch (Exception ex) {
			throw new PicoPlacaException("There are some problems while validating the day of the week");
		}

		return true;
	}

	private boolean validateTime(String time) {

		// Declare variable considering the vehicle is out of Pico & Placa Range Time
		boolean isOutOfTimeRange = true;

		try {
			String[] parts = time.split(":");
			// Parse String to a Date format to validate the time range
			Calendar timeToValidate = generateTimeObject(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
					Integer.parseInt(parts[2]));

			// First time range (07h00 to 09h30)
			Calendar initialTime1 = generateTimeObject(7, 0, 0);
			Calendar finalTime1 = generateTimeObject(9, 30, 0);

			// Second time range (16h00 - 19h30)
			Calendar initialTime2 = generateTimeObject(16, 0, 0);
			Calendar finalTime2 = generateTimeObject(19, 30, 0);

			// Validation of the two time ranges
			if (timeToValidate.getTime().after(initialTime1.getTime())) {
				if (timeToValidate.getTime().before(finalTime1.getTime())) {
					isOutOfTimeRange = false;
				} else {
					if (timeToValidate.getTime().after(initialTime2.getTime())
							&& timeToValidate.getTime().before(finalTime2.getTime())) {
						isOutOfTimeRange = false;
					}
				}
			}

		} catch (NullPointerException ex) {
			throw new PicoPlacaException("Any of the time objects has a null value");
		} catch (Exception ex) {
			throw new PicoPlacaException("There are some problems while validating time ranges");
		}

		return isOutOfTimeRange;
	}

	private Calendar generateTimeObject(Integer hours, Integer minutes, Integer seconds) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		return cal;
	}

	private String buildResponseMessage(InformationRequest informationRequest, boolean validation) {
		StringBuilder sb = new StringBuilder("The vehicle with plate ");
		sb.append(informationRequest.getVehicle().getPlate());
		sb.append(" (last digit ");
		sb.append(informationRequest.getVehicle().getPlateLastDigit());
		sb.append(") and usage ");
		sb.append(informationRequest.getVehicle().getType().getUsage());
		sb.append(validation ? " IS ALLOWED TO CIRCULATE on " : " IS NOT ALLOWED TO CIRCULATE on ");
		if (informationRequest.getDateToValidate() != null && !informationRequest.getDateToValidate().isEmpty()) {
			sb.append(informationRequest.getDateToValidate());
		} else {
			LocalDate currentDate = LocalDate.now();
			sb.append(currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}

		sb.append(" at ");
		sb.append(informationRequest.getTimeToValidate());
		return sb.toString();
	}

}