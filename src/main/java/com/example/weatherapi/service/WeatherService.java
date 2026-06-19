package main.java.com.example.weatherapi.service;

//imports the customexception used when the weather api fails
import com.example.weatherapi.exception.WeatherApiException; 

//imports the annotation used to inject values from application properties
import org.springframework.beans.factory.annotation.Value; 

//springs redis helper for storing string keysd and string values 
import org.springframework.data.redis.core.StringRedisTemplate; 

//imports teh annotation that markers this calls as a servce 
import org.springframework.stereotype.Service; 

//import springs http client for claling outside APIs
import org.springframework.web.client.RestClient; 

//imports duration so we can set teh chace expiration time 
import java.time.Duration; 

//tells spring this class contians business logic
@Service
public class WeatherService {
    //stores the redis client use to read and write cached data
    private final StringRedisTemplate redisTemplate; 

    //store the http client used to call visual crossing 
    private final RestClient restClient; 

    @Value("${weather.api.key}")
    private String apiKey; 

    //reads the visual crossing base url from applciation properties 
    @Value("${weather.api.base-url")
    private String baseUrl; 

    //constructor used by spring to inject redis 
    public WeatherService(StringRedisTemplate redisTemplate){
        //saves teh redis clinet into this class field 
        this.redisTemplate = redisTemplate; 
        //creates a rest clinet - for sending http requests
        this.restClient = RestClient.create(); 
    }

    //function that gets weather data for a city 
    public String getWeather(String city){
        //removes extra spaces and lowercases the city so cache keys stay consitent 
        String normalizedCity = city.trim().toLowerCase(); 

        //creates the redis key for this city 
        String cacheKey = "weather" + normalizedCity; 

        //checked reds to see if tis city already has cahed weather data 
        String cacheWeather = redisTemplate.opsForValue().get(cacheKey); 

        if(cacheWeather != null){
            return cacheWeather;
        }
        //start a try block because the outside api could fail 
        try{
            //sends a get request to visla crossing and stroes the json resposne as a string
            String weatherResponse = restClient.get()
                //builds the full apu url using the base url, city, unit type, api key, and json
                .uri(baseUrl + "/" + city + "?unitGroup=us&key=" + apiKey + "&contentType=json")
                //sends the request and prepares to read the response
                .retrieve()
                //converts the resposne body into a string
                .body(String.class); 

                
            //saves the wether resposne in Redis with a 12-hour expiration 
            redisTemplate.opsForValue().set(cacheKey, weatherResponse, Duration.ofHours(12));

            return weatherResponse; 
        }catc(Exception e){
            throw new WeatherApiException("Could not fetch weather data for city:" + city); 
        }

    }
}
