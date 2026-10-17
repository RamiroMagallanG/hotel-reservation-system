package org.rmagallangonzalez.hotel_reservation_system.user;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter(AccessLevel.NONE)
    private long id;
    
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;
    
    @ToString.Exclude
    private String password;
    
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    private LocalDate dateOfBirth;
    
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate registrationDate;

    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDate.now();
    }

    public User(String firstName, String lastName, String email, String password, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = UserRole.CLIENT;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String firstName, String lastName, String email, String password, UserRole role, LocalDate dateOfBirth) {
        this(firstName, lastName, email, password, dateOfBirth);
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
