package main.java.com.example.weatherapi.exception;

//creates a custom runtime exception for weather api failures 
public class WeatherApiException extends RuntimeException {
    //construct that accepts an error string 
    public WeatherApiException(String errorText){
        //sends ther error string to the aprent runtime class 
        super(errorText); 
    }
}
