package kaulikeLion.Backend.oauth.jwt;

import kaulikeLion.Backend.oauth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter
    private Long id;
    private String username;
    private String password;
    @Getter
    private String email;
    private String groupName;
    private String nickname;
    @Getter
    private String provider;
    @Getter
    private String providerId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 현재 유저당 부여되는 권한은 하나임으로 하나만 추가함
        // 여러 Role 부여시 User의 Role 필드 수정 필요
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public static CustomUserDetails fromEntity(User entity) {
        return CustomUserDetails.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .provider(entity.getProvider())
                .providerId(entity.getProviderId())
                .build();
    }

    public User newEntity() {
        User entity = new User();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setProvider(provider);
        entity.setProviderId(providerId);
        return entity;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", email='" + email + '\'' +
                '}';
    }
}
