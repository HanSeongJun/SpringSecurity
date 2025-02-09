package Spring.Security.Config.oauth;

import Spring.Security.Config.auth.PrincipalDetails;
import Spring.Security.Config.oauth.provider.FacebookUserInfo;
import Spring.Security.Config.oauth.provider.GoogleUserInfo;
import Spring.Security.Config.oauth.provider.NaverUserInfo;
import Spring.Security.Config.oauth.provider.OAuth2UserInfo;
import Spring.Security.model.User;
import Spring.Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder; // 순환참조 에러
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리를 하는 함수
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원프로필 받아준다.
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        // oAuth2User 타입으로 회원 가입을 강제로 진행해볼 예정.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo;

        if (registrationId.equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        } else if (registrationId.equals("facebook")) {
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }
        else {
            throw new OAuth2AuthenticationException("지원되지 않는 소셜 로그인 제공자입니다: " + registrationId);
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId; // gooogle_12344123455123589876
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            System.out.println("최초 로그인입니다.");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(userEntity);
        }
        else {
            System.out.println("로그인을 이미 한적이 있습니다. 당신은 자동회원 가입이 되어 있습니다.");
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
