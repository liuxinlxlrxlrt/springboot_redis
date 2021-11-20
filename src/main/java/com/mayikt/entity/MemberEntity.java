package com.mayikt.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 实体类必须实现Serializable接口，不然会报错
 */
@Data
public class MemberEntity implements Serializable {
    private Long id;
    private String name;
    private Integer age;
}
