package Spring.Security.Config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest);
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());

        // registrationID로 어떤 OAuth로 로그인 했는지 알 수 있음.
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());

        // 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> 코드 리턴(OAuth-Client라이브러리가 받아줌) -> Access Token 요청
        // userRequest 정보 -> 회원 프로필 받아야함(loadUser함수) -> 회원 프로필을 받음
        System.out.println("super.loadUser(userRequest).getAttributes() = " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);



        return super.loadUser(userRequest);
    }
}
