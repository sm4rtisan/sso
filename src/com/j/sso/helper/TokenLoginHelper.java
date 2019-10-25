package com.j.sso.helper;

import com.j.sso.config.SSOConfiguration;
import com.j.sso.entity.R;
import com.j.sso.entity.SSOUser;

/**
 * 登录成功后获取token
 *
 * @author yzsn
 */
public class TokenLoginHelper {

    public static R<String> loginSuccess(Long userId, String username, String extra) {

        if (SSOConfiguration.SECRET_KEY == null || SSOConfiguration.SECRET_KEY.trim().equals("")) {

            throw new IllegalArgumentException("token salt is null");
        }

        // 检查id是否为空
        if (userId == null) {

            return R.notAllow();
        }
        // 检查通过
        SSOUser user = new SSOUser();
        user.setId(userId);
        user.setUsername(username);
        user.setExtra(extra);
        // 创建token
        String token = JwtTokenHelper.createToken(user);
        return R.success(token);
    }

    private TokenLoginHelper() {
    }

}
