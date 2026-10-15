package com.svc.api.auth.service;

import com.svc.api.auth.model.vo.AuthVo;

public interface AuthService {
    String register(AuthVo vo);
    String login(AuthVo vo);
}
