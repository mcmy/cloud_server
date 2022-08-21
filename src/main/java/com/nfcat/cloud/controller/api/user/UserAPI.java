package com.nfcat.cloud.controller.api.user;

import com.nfcat.cloud.annotation.JSONBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@JSONBody
@Validated
@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPI {


}
