package org.example.movie.dto;

/**
 * Data Transfer Object (DTO) for transferring Rental data
 * between UI layer and service layer.
 * Helps in encapsulating input data and reducing coupling with entity classes.
 */

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDTO {
    private Integer customerId;
    private Integer inventoryId;
    private Integer staffId;
    private BigDecimal amount;
}

