package org.example.movie.dto;

/**
 * Data Transfer Object (DTO) for transferring Film data
 * between UI layer and service layer.
 * Helps in encapsulating input data and reducing coupling with entity classes.
 */

import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmDTO {
    private String title;
    private String description;
    private int releaseYear;
    private short languageId;
    private Set<Short> actorIds;
    private Set<Short> categoryIds;
    private short storeId;

    private short rentalDuration;
    private BigDecimal rentalRate;
    private BigDecimal replacementCost;
}
