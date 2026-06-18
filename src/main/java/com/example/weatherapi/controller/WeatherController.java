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

@RestController
public class WeatherController {
    private final 
}
