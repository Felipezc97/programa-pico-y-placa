package com.felipe.samples.picoplaca.model;

import com.felipe.samples.picoplaca.exception.PicoPlacaException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Vehicle {
    private String plate;
    private Type type;

    public int getPlateLastDigit() {
        String plateSubstring;
        int plateLastDigit = -1;
        try {
            plateSubstring = plate.substring(plate.length()-1);
            plateLastDigit = Integer.valueOf(plateSubstring);
        } catch (NumberFormatException ex) {
        	throw new PicoPlacaException("The plate has not a number at the end. Cannot convert a char to integer");
        } catch (Exception e) {
            throw new PicoPlacaException("There are some problems while retrieving the last digit of plate");
        }
        return plateLastDigit;
    }
}
