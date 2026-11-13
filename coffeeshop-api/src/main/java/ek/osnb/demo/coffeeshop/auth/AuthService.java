package ek.osnb.demo.coffeeshop.auth;

import ek.osnb.demo.coffeeshop.common.ConflictException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@Transactional
class AuthService implements AuthApi, UserDetailsService {

    private final AccountRepository accounts;
    private final PasswordEncoder passwordEncoder;

    AuthService(AccountRepository accounts, PasswordEncoder passwordEncoder) {
        this.accounts = accounts;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String email, String password) {
        String normalizedEmail = normalizeEmail(email);
        if (accounts.existsByEmail(normalizedEmail)) {
            throw new ConflictException("An account with this email already exists");
        }

        Account account = Account.customer(normalizedEmail, passwordEncoder.encode(password));
        accounts.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        String normalizedEmail = normalizeEmail(email);
        Account account = accounts.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        return User.withUsername(account.getEmail())
                .password(account.getPasswordHash())
                .roles(account.getRole())
                .build();
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
