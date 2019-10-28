package com.klnm.bridge.mapper.hoc;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface HocUserMapper {

    public int insertUser(HashMap param) throws Exception;

    public int checkId(HashMap param) throws Exception;


}
