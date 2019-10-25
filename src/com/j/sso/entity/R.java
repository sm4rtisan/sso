package com.j.sso.entity;

import com.alibaba.fastjson.JSON;

/**
 * 响应类
 * @author yizhishaonian
 */
public class R<T> {
	
	private Integer code;
	private String msg;
	private T data;

	public static final int SUCCESS_CODE = 200;
	public static final int NO_AUTHORIZATION = 401;
	public static final int NOT_ALLOW = 502;
	
	public static <T> R<T> base(Integer code, String msg, T content) {
		R<T> result = new R<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(content);
		return result;
	}

	public static <T> R<T> success(T content) {
		return base(SUCCESS_CODE, "SUCCESS", content);
	}
	
	public static R<String> fail(String msg) {
		return base(NOT_ALLOW, msg, "");
	}

	public static R<String> notAllow() {
		return fail("非法请求，请立即停止操作");
	}

	public static R<String> noAuth() {
		return base(NO_AUTHORIZATION, "没有认证，或认证失效", "");
	}
	
	public static R<String> noLogin() {
		return base(NO_AUTHORIZATION, "未登录", "");
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
