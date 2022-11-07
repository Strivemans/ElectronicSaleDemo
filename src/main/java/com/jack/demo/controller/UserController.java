package com.jack.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * \* User: jack
 * \* Date: 2022/10/25
 * \* Time: 15:42
 * \* Description:
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api("用户管理")
public class UserController {
}
