package br.com.vardeba.learningspring.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CreateDepositDto {
    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "0.01", message = "Value must be at least 0.01")
    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
