package main.java.com.example.weatherapi.exception;

//imports http status codes 
import org.springframework.http.HttpStatus; 

//import response enitty - which lets us control status code and response body
import org.springframework.http.ResponseEntity; 

//imports the annotation used to handle specifc exception
import org.springframework.web.bind.annotation.ExceptionHandler; 

//imports the annotation that makes this calss handle errors globally
import org.springframework.web.bind.annotation.RestControllerAdvice; 

//tells spring this class handles exception across all controllers
@RestControllerAdvice
public class GlobalExceptionHandler {
    //runs this function wehn a weatherapiexception is thrown
    //if weatherapi exception is thrown anywhere - run this class   now 
    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<String> handleWeatherApiException(WeatherApiException exception){
        //builds an http response with a 502 status 
        //sets http status to 502 - bad gateway
        //sedns the exception text as the response body 
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(exception.getMessage()); 
    }

    //runs this funcitonf or any other exception nto already handles 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong"); 
    }
}
