package Spring.Security.Config.auth;

import Spring.Security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인 진행 완료가 되면 시큐리티 세션을 만들어준다.(Security ContextHolder)
 * 오브젝트 타입 -> Authentication 타입 객체
 * Authentication 안에 User 정보가 있어야 한다.
 * User 오브젝트 타입 -> UserDetails 타입 객체
 *
 * Security Session => Authentication => UserDetails
 */

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user;

    // 해당 User의 권한을 리턴하는 곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한(role)을 GrantedAuthority로 변환하여 반환
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 1년이 지났나 안지났나
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계절 활성화
    @Override
    public boolean isEnabled() {
        return true;
    }
}
