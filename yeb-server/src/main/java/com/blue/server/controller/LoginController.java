package com.blue.server.controller;

import com.blue.server.PO.Hr;
import com.blue.server.PO.RespBean;
import com.blue.server.VO.HrLoginData;
import com.blue.server.service.IHrService;
import com.blue.server.utils.Captcha;
import com.blue.server.utils.CaptchaGenImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@RestController()
@RequestMapping("/apis")
//@CrossOrigin
@Api(tags = "LoginController")
public class LoginController {
    @Autowired
    private IHrService hrService;

    @ApiOperation(value = "登陆,并返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody HrLoginData hrLoginData, HttpServletRequest request) {

        return hrService.login(hrLoginData.getUsername(), hrLoginData.getPassword(), hrLoginData.getCode(), request);
    }

    @ApiOperation(value = "获取当前登录信息")
    @GetMapping("/hr/info")
    public Hr getHrInfo(Principal principal) {
        Hr hr = null;
        if (null == principal) return hr;
        else {
            String username = principal.getName();
            hr = hrService.getHrByUsername(username);
            hr.setPassword(null);
            return hr;
        }

    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功");
    }

    @ApiOperation(value = "访问login时生成验证码")
    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_JPEG_VALUE)
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {

        Captcha captcha = new CaptchaGenImpl().generate(80, 28);
        String code = captcha.getCode();

        HttpSession session = request.getSession(true);
        session.setAttribute("captcha", code);
        //设置响应头
        resp.setHeader("Pragma", "no-cache");
        //设置响应头
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        //在代理服务器端防止缓冲
        resp.setDateHeader("Expires", 0);
        //设置响应内容类型,返回jpeg
        resp.setContentType("image/jpeg");
        resp.getOutputStream().write(captcha.getImgBytes());
        resp.getOutputStream().flush();

    }

}
