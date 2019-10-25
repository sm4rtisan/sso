# sso springboot轻量单点登录框架

## 接入方法
### 配置logo和加密key
```
  在application.properties配置下面两个key
  sso.logo=logo
  sso.secret.key=key
```
### 添加拦截器
```
  @Configuration
  public class MPSsoInterceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

      InterceptorRegistration addInterceptor = registry.addInterceptor(new SSOIntercepter());

      // 拦截的配置
      addInterceptor.addPathPatterns("/**");

      // 不拦截的配置
      addInterceptor.excludePathPatterns("/login");

    }

  }
```
### 不拦截的方法也可在方法上加@SSOCheckToken注解check=true为拦截false为不拦截

### 在执行完自己系统的逻辑后调用下面方法返回token
```
  R<String> r = TokenLoginHelper.loginSuccess(1L, "admin", json.toJSONString());
  r.getData(); 即为生成的jwt token将此token返回，之后每次请求是将此token放在请求头中key为Authorization
```
