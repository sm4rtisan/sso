package com.j.sso.helper;

import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.j.sso.config.SSOConfiguration;
import com.j.sso.config.SSOJtiStore;
import com.j.sso.entity.SSOUser;

/**
 * token 生成 加解密 校验
 * @author yizhishaonian
 */
public class JwtTokenHelper {

	private static final String userCliam = "extra";
	
	public static String createToken(SSOUser user) {

		return String.format("%s%s", SSOConfiguration.LOGO,
				JWT.create().withIssuer(SSOConfiguration.LOGO).withSubject(String.valueOf(user.getId()))
						.withIssuedAt(new Date()).withJWTId(genJti()).withClaim(userCliam, JSON.toJSONString(user))
						.sign(Algorithm.HMAC256(SSOConfiguration.SECRET_KEY)));
	}

	public static Boolean checkTokenInLaw(String token) {

		if (!checkLogo(token)) {
			
			return false;
		}
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SSOConfiguration.SECRET_KEY)).build();
		try {
			
			String jti = jwtVerifier.verify(subLogo(token)).getId();
			if (!SSOJtiStore.contains(jti)) {
				throw new JWTVerificationException("jti校验失败");
			}
			return true;
		} catch (JWTVerificationException e) {
			
			return false;
		}
	}

	public static SSOUser getTokenSSOUser(String token) {

		String other = decodeToken(token).getClaim(userCliam).asString();
		return JSONObject.parseObject(other, SSOUser.class);
	}

	public static void logout(String token) {

		SSOJtiStore.remove(decodeToken(token).getId());
	}

	private JwtTokenHelper() {
	}
	
	private static String genJti() {

		String jti = UUID.randomUUID().toString();
		SSOJtiStore.add(jti);
		return jti;
	}
	
	private static boolean checkLogo(String token) {

		return token.startsWith(SSOConfiguration.LOGO);
	}

	private static String subLogo(String token) {

		return token.substring(SSOConfiguration.LOGO.length(), token.length());
	}

	private static DecodedJWT decodeToken(String token) {

		return JWT.decode(subLogo(token));
	}
}
