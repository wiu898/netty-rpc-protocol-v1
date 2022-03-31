package com.xc.rpc.example.service;

import com.xc.rpc.example.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 2:58 PM
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}",name);
        return "save User success: " + name;
    }

}
