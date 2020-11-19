package com.june.blog.admin.config;

import com.june.blog.admin.config.handler.*;
import com.june.blog.admin.filter.MyFilter;
import com.june.blog.admin.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FailureHandler failureHandler;

    @Autowired
    private SuccessHandler successHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private SysUserServiceImpl userService;

    @Autowired
    private EntryPoint entryPoint;

    @Autowired
    private DenyHandler denyHandler;


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080");//修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        configuration.addAllowedMethod("*");//修改为添加而不是设置
        configuration.addAllowedHeader("*");//这里很重要，起码需要允许 Access-Control-Allow-Origin
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        return new SysUserServiceImpl();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MyFilter(authenticationManager()),UsernamePasswordAuthenticationFilter.class).cors().and().csrf().disable()
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/login").permitAll().anyRequest().authenticated()
                .and().formLogin()
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .and().logout().logoutSuccessHandler(logoutHandler).permitAll()
                .and().exceptionHandling().authenticationEntryPoint(entryPoint)
                .and().exceptionHandling().accessDeniedHandler(denyHandler);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());




    }
//@Override
//protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    // 这里做用户登录判断用户名密码
//    auth.userDetailsService(new UserDetailsService() {
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            SysUser user = userService.queryById((long)1); // 从你的数据库中取出用户
//            if (user == null) {
//                throw new UsernameNotFoundException("用户名不存在");
//            }
//            return user;
//        }
//    }).passwordEncoder(new BCryptPasswordEncoder()); // 使用 BCryptPasswordEncoder 加盐加密
//}
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().authorizeRequests()
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // 这句比较重要，放过 option 请求
//                .antMatchers("/css/**", "/index").permitAll()
//                .antMatchers("/license/**").hasRole("ADMIN")
//
//                // 无权访问是返回 json 格式数据。
//                .and().httpBasic().authenticationEntryPoint(new AuthenticationEntryPoint() {
//            @Override
//            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//                                 AuthenticationException e) throws IOException, ServletException {
//
//                Map<String, String> rm = new HashMap<>();
//                rm.put("msg", "need login");
//                rm.put("code", "need 401");
//                httpServletResponse.getWriter().write(JSON.toJSONString(rm));
//            }
//        })
//
//                // 登录响应
//                .and().formLogin().failureHandler(new AuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
//                                                AuthenticationException e) throws IOException {
//                resp.setContentType("application/json;charset=utf-8");
//                Map<String, String> rm = new HashMap<>();
//                if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
//                    rm.put("账户名或者密码输入错误!", "");
//                } else if (e instanceof LockedException) {
//                    rm.put("账户被锁定，请联系管理员!", "");
//                } else if (e instanceof CredentialsExpiredException) {
//                    rm.put("密码过期，请联系管理员!", "");
//                } else if (e instanceof AccountExpiredException) {
//                    rm.put("账户过期，请联系管理员!", "");
//                } else if (e instanceof DisabledException) {
//                    rm.put("账户被禁用，请联系管理员!", "");
//                } else {
//                    rm.put("登录失败!", "");
//                }
//                resp.setStatus(401);
//                PrintWriter out = resp.getWriter();
//                out.write(JSON.toJSONString(rm));
//                out.flush();
//                out.close();
//            }
//        }).successHandler(new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth)
//                    throws IOException {
//                resp.setContentType("application/json;charset=utf-8");
//                Object curUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                Map<String, String> rm = new HashMap<>();
//                rm.put("msg", "success");
//                ObjectMapper om = new ObjectMapper();
//                PrintWriter out = resp.getWriter();
//                out.write(om.writeValueAsString(rm));
//                out.flush();
//                out.close();
//            }
//        }).permitAll()
//
//                // 退出登录
//                .and().logout().logoutSuccessHandler(new LogoutSuccessHandler() {
//            @Override
//            public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication)
//                    throws IOException, ServletException {
//                resp.setContentType("application/json;charset=utf-8");
//                Map<String, String> rm = new HashMap<>();
//                rm.put("msg", "注销");
//                PrintWriter out = resp.getWriter();
//                out.write(JSON.toJSONString(rm));
//                out.flush();
//                out.close();
//            }
//        }).permitAll()
//
//                // 这里还是要关闭crsf因为crsf还有token之类的安全防护
//                // 这就是这种方式不安全的原因吧
//                .and().csrf().disable();
//    }
}
