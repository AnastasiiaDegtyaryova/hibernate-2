package org.example.movie.dto;

/**
 * Data Transfer Object (DTO) for transferring Customer data
 * between UI layer and service layer.
 * Helps in encapsulating input data and reducing coupling with entity classes.
 */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private short countryId;
    private short cityId;
    private short storeId;

    private String address;
    private String district;
    private String postalCode;
    private String phone;

    private String firstName;
    private String lastName;
    private String email;

}
