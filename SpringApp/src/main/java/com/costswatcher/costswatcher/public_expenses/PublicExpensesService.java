package com.costswatcher.costswatcher.public_expenses;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PublicExpensesService {
    private final RestTemplate restTemplate;

    public PublicExpensesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PublicExpense> getPublicExpenses() {
        String url = "http://localhost:8081/silence-api/get-trip-expenses";
        PublicExpense[] publicExpenses = restTemplate.getForObject(url, PublicExpense[].class);
        return publicExpenses != null ? Arrays.asList(publicExpenses) : new ArrayList<>();
    }

    public void submitTripExpense(String location, int expense) {
        String url = "http://localhost:8081/silence-api/submit-trip-expense";
        Map<String, Object> bodyParams = new HashMap<>();
        bodyParams.put("trip_location", location);
        bodyParams.put("trip_expense", expense);
        restTemplate.postForEntity(url, bodyParams, String.class);
    }
}
