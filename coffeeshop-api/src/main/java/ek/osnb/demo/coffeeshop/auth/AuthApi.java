package ek.osnb.demo.coffeeshop.auth;

/** The account operations exposed to the API layer. */
public interface AuthApi {

    /** Creates a customer login account; passwords are stored as BCrypt hashes. */
    void register(String email, String password);
}
