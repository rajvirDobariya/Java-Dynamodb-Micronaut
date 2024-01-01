package example.test.controller;


import example.test.model.Location;
import example.test.repo.LocationRepository;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/locations")
public class LocationController {

    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Get("/{locationId}")
    public Location findByLocationId(String locationId) {
        return locationRepository.findByLocationId(locationId)
                .orElse(null); // Handle not found scenario as needed
    }

    @Get
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Post
    public Location createLocation(@Body Location location) {
        return locationRepository.createLocation(location);
    }

    @Put("/{locationId}")
    public Location updateLocation(String locationId, @Body  Location updatedLocation) {
        updatedLocation.setLocation_id(locationId);
        return locationRepository.updateLocation(updatedLocation)
                .orElse(null); // Handle update failure or not found scenario as needed
    }

    // Implement other endpoints as needed

}
