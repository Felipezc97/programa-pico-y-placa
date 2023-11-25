
# Pico & Placa Simulator

This project simulates and evaluates the Pico & Placa rules. It is developed using Java and Spring Boots and exposes a REST service to evalute whether a vehicle is allowed to circulate or not given its type of usage (private or private), a date that is mapped to the corresponding day of the week and a time of day that is evaluated within the ranges in which which the Pico & Placa applies.


## API Reference

#### Validate allowed
Evaluate whether the vehicle can circulate for the given date and time.

```http
  GET /information/validate-allowed
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `informationRequest` | `InformationRequest` | **(Required)**. Object that contains vehicle informacion, date and time for evaluation |


## Usage/Examples

The default configuration open the application in port 8081 and context-path _/felipe/samples/pico-placa_. To consume the service described in the previous section, you can use cURL as shown below:

```console
curl --location \ 
--request GET 'http://localhost:8081/felipe/samples/pico-placa/information/validate-allowed' \
--header 'Content-Type: application/json' \
--data-raw '{
    "vehicle" : {
        "plate" : "PJG0613",
        "type": "BUS"
    }, 
    "dateToValidate": "2022-05-24", 
    "timeToValidate": "16:36:00"
}'
```

The available vehicles supported for the evaluation are:  
- AUTO: vehicles of particular usage  
- TAXI: vehicles of public usage (Exempt of the Pico & Placa application)  
- TRUCK: vehicles for heavy load and particular usage  
- SCHOOL: vehicles for students transport but corresponds to particular usage  
- BUS: vehicles of public usage (Exempt of the Pico & Placa application)  

Notes:  
1. `dateToValidate` is an optional attribute. If empty, program takes the current date. It's very important to respect the _ISO_LOCAL_DATE_ format "YYYY-mm-dd"  
2. `´timeToValidate` is a required attribute and has a specific time format "HH:mm:ss"  
### About the author

**Luis Felipe Zumárraga Cadena**  
_Electronic and Networks Engineer, Junior Software Developer_  
_Atuntaqui, Imbabura, EC._  
_felipezumarraga@hotmail.com_

You find more information and projects in my networks:  
- [LinkedIn](https://ec.linkedin.com/in/felipezc97)  
- [Github](https://www.github.com/felipezc97)  
- [BitBucket](https://bitbucket.org/Felipezc97)  
