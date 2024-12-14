package com.costswatcher.costswatcher.public_expenses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublicExpense {
    @JsonProperty("trip_location")
    private String tripLocation;

    @JsonProperty("trip_expense")
    private int tripExpenseAmount;
}
