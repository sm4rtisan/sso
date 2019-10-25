package com.j.sso.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Jti容器
 * @author yizhishaonian
 */
public class SSOJtiStore {

	private static ConcurrentHashMap<String, Object> jtiStore = new ConcurrentHashMap<String, Object>();

	private static final Object value = new Object();

	public static void add(String jti) {

		jtiStore.put(jti, value);
	}

	public static boolean contains(String jti) {

		return jtiStore.containsKey(jti);
	}

	private SSOJtiStore() {
	}

	public static void remove(String jti) {
		jtiStore.remove(jti);
	}
}
