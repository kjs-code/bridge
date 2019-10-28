package com.klnm.bridge.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.klnm.bridge.mapper.hoc.HocUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HocUserService {

    @Autowired
    HocUserMapper hocUserMapper;

    public int insertUser(String param) throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(param);
        String id = element.getAsJsonObject().get("id").getAsString();
        String pw = element.getAsJsonObject().get("pw").getAsString();
        String email = element.getAsJsonObject().get("email").getAsString();

        paramMap.put("id", id);
        paramMap.put("pw", pw);
        paramMap.put("email", email);


        return hocUserMapper.insertUser(paramMap);
    }

    public int checkId(String param) throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(param);
        String id = element.getAsJsonObject().get("id").getAsString();

        paramMap.put("id", id);

        return hocUserMapper.checkId(paramMap);
    }

}
