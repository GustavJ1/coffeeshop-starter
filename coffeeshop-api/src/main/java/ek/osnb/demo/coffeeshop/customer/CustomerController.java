package ek.osnb.demo.coffeeshop.customer;

import ek.osnb.demo.coffeeshop.common.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
class CustomerController {

    private final CustomerService service;

    CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CustomerResponse create(@Valid @RequestBody CustomerRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    CustomerResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }
    @GetMapping("/me")
    CustomerResponse getProfile(Authentication authentication){
        String email = authentication.getName();

        return service.findCustomerByEmail(email)
                .map(service -> new CustomerResponse(
                        service.id(),
                        service.name(),
                        service.email(),
                        service.street(),
                        service.postalCode(),
                        service.city()
                ))
                .orElseThrow(()-> new NotFoundException("customer not found"));

    }
    @PutMapping("/me")
    CustomerResponse updateProfile(Authentication authentication,
                                   @Valid
                                   @RequestBody CustomerProfileRequest request){
    String email = authentication.getName();
    return service.saveProfile(email,request);
    }

    // GET /api/customers?email=anna@example.com - email is required,
    // so this never lists every customer
    @GetMapping
    List<CustomerResponse> findByEmail(@RequestParam String email) {
        return service.findByEmail(email);
    }

    @PutMapping("/{id}")
    CustomerResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return service.update(id, request);
    }
}
