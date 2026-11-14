package ek.osnb.demo.coffeeshop.customer;
import jakarta.validation.constraints.NotBlank;

record CustomerProfileRequest(
        @NotBlank String name,
        @NotBlank String street,
        @NotBlank String postalCode,
        @NotBlank String city
) {}