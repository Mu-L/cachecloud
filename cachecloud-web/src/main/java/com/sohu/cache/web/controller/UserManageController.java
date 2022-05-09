package com.sohu.cache.web.controller;

import com.sohu.cache.constant.AppCheckEnum;
import com.sohu.cache.constant.AppUserTypeEnum;
import com.sohu.cache.entity.AppAudit;
import com.sohu.cache.entity.AppUser;
import com.sohu.cache.util.ConstUtils;
import com.sohu.cache.web.enums.SuccessEnum;
import com.sohu.cache.web.util.AppEmailUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户信息管理
 *
 * @author leifu
 * @Time 2014年6月6日
 */
@Controller
@RequestMapping("manage/user")
public class UserManageController extends BaseController {

    @Resource(name = "appEmailUtil")
    private AppEmailUtil appEmailUtil;

    /**
     * 用户初始化
     *
     * @param id 用户id
     * @return
     */
    @RequestMapping(value = "/init")
    public ModelAndView doUserInit(HttpServletRequest request,
                                   HttpServletResponse response, Model model, Long id) {
        if (id != null) {
            AppUser user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("modify", true);
        }
        return new ModelAndView("manage/user/initUser");
    }

    /**
     * 更新用户
     *
     * @param name
     * @param chName
     * @param email
     * @param mobile
     * @param weChat
     * @param type
     * @param userId
     * @return
     */
    @RequestMapping(value = "/add")
    public ModelAndView doAddUser(HttpServletRequest request,
                                  HttpServletResponse response, Model model, String name, String chName, String email, String mobile, String weChat,
                                  Integer type, Long userId, Integer isAlert, String company, String purpose) {
        // 后台暂时不对参数进行验证
        AppUser appUser = AppUser.buildFrom(userId, name, chName, email, mobile, weChat, type, isAlert, company, purpose);
        try {
            if (userId == null) {
                appUser.setPassword(ConstUtils.DEFAULT_USER_PASSWORD);
                userService.save(appUser);
            } else {
                userService.update(appUser);
            }
            write(response, String.valueOf(SuccessEnum.SUCCESS.value()));
        } catch (Exception e) {
            write(response, String.valueOf(SuccessEnum.FAIL.value()));
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/delete")
    public ModelAndView doDeleteUser(HttpServletRequest request,
                                     HttpServletResponse response, Model model, Long userId) {
        userService.delete(userId);
        return new ModelAndView("redirect:/manage/user/list");
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/resetPwd")
    public ModelAndView doResetUserPwd(HttpServletRequest request,
                                     HttpServletResponse response, Model model, Long userId) {
        userService.resetPwd(userId);
        return new ModelAndView("redirect:/manage/user/list");
    }

    /**
     * 修改密码
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/updatePwd")
    public ModelAndView doResetUserPwd(HttpServletRequest request,
                                       HttpServletResponse response, Model model, Long userId, String password) {
        SuccessEnum successEnum = userService.updatePwd(userId, password);
        if(successEnum.equals(SuccessEnum.SUCCESS)){
            write(response, String.valueOf(SuccessEnum.SUCCESS.value()));
        }else{
            write(response, String.valueOf(SuccessEnum.FAIL.value()));
        }
        return null;
    }

    /**
     * 用户列表
     *
     * @param  searchChName 中文名
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView doUserList(HttpServletRequest request,
                                   HttpServletResponse response, Model model, String searchChName) {
        List<AppUser> users = userService.getUserList(searchChName);
        model.addAttribute("users", users);
        model.addAttribute("searchChName", searchChName);
        model.addAttribute("userActive", SuccessEnum.SUCCESS.value());
        return new ModelAndView("manage/user/list");
    }

    @RequestMapping(value = "/addAuditStatus")
    public ModelAndView doAddAuditStatus(HttpServletRequest request,
                                         HttpServletResponse response, Model model, Integer status, Integer type,
                                         Long appAuditId, String refuseReason, Long appId) {
        AppAudit appAudit = appService.getAppAuditById(appAuditId);
        AppUser appUser = userService.get(appAudit.getUserId());
        // 通过或者驳回并记录日志
        appService.updateUserAuditStatus(appAuditId, status, appUser.getId());

        // 记录驳回原因
        if (AppCheckEnum.APP_REJECT.value().equals(status)) {
            appAudit.setRefuseReason(refuseReason);
            appService.updateRefuseReason(appAudit, getUserInfo(request));
            userService.delete(appUser.getId());
        }

        // 发邮件统计
        if (AppCheckEnum.APP_PASS.value().equals(status)
                || AppCheckEnum.APP_REJECT.value().equals(status)) {
            appUser.setType(AppUserTypeEnum.REGULAR_USER.value());
            appAudit.setStatus(status);
            userService.update(appUser);
            appEmailUtil.noticeUserResult(appUser, appAudit);
        }

        // 批准成功直接跳转
        if (AppCheckEnum.APP_PASS.value().equals(status)) {
            return new ModelAndView("redirect:/manage/app/auditList");
        }

        write(response, String.valueOf(SuccessEnum.SUCCESS.value()));
        return null;
    }

}
