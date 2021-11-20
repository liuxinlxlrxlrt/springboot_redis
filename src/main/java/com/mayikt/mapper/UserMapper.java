package com.mayikt.mapper;

import com.mayikt.entity.MemberEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from users")
    List<MemberEntity> findMemberAll();
}
