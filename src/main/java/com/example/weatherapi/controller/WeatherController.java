//defines the package this controller class belong to 
package main.java.com.example.weatherapi.controller;

//imports the service class that contains the weather logic 
import com.example.weatherapi.service.WeatherService;

//imports validation support for checking that a string is not blank
import jakarta.validation.constraints.NotBlank; 

//annotaiton used to handle HTTP GET requests
import org.springframework.web.bind.annotation.GetMapping; 

//imports the annotationused to read query paramter from the URL 
import org.springframework.web.bind.annotation.RequestParam; 

//import the annotation that says this class as Rest controller
import org.springframework.web.bind.annotation.RestController; 

//tells spring this class handels web/api requests and returns data directly 
@RestController
public class WeatherController {
    //stores a reference to weatherService 
    private final WeatherService weatherService; 

    //constructor used by spring to inject the weatherservice object
    public WeatherController(WeatherService weatherService){
        this.weatherService = weatherService; 
    }

    //maps get request for /weather to this function
    @GetMapping("/weather")
    public String getWeather(
        //reads teh city value from url 
        @RequestParam

        //requires the city string to not be empty or only spaces
        @NotBlank String city; 
    ){
        return weatherService.getWeather(city); 
    }
}
