package com.klnm.bridge.service;

import com.klnm.bridge.mapper.comdb.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public List<HashMap> getAll() throws Exception {
        return userMapper.getAll();
    }
}
