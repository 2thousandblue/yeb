package com.blue.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blue.server.PO.Hr;
import com.blue.server.PO.RespBean;
import com.blue.server.config.security.JwtTokenUtil;
import com.blue.server.mapper.HrMapper;
import com.blue.server.service.IHrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-02-16
 */
@Service
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements IHrService {

    @Autowired
    private HrMapper hrMapper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    @Override
    public Hr getHrByUsername(String username) {
        return hrMapper.selectOne(new QueryWrapper<Hr>().eq("username", username).eq("enabled", 1));
    }

    /**
     * 登陆后返回token
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //校验验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println(captcha+"  "+request.getSession().getCreationTime());
        if (null == captcha || "".equals(captcha) || !captcha.equalsIgnoreCase(code)) {

            return RespBean.error("验证码输入错误,请重新输入!");
        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails) {
            return RespBean.error("用户名不存在!");
        } else if (!userDetails.isEnabled()) {
            return RespBean.error("账号已禁用,请联系管理员");
        } else if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("密码输入错误,请重新输入!");
        }

        // 参数:1.userdetails,2.凭证(密码),3.权限列表
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        authenticationionToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        更新security全局登录用户对象
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        return RespBean.success("登陆成功", tokenHead+' '+token);
    }
}
