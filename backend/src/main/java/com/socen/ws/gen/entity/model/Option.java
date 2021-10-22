package com.socen.ws.gen.entity.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName：Option
 * @author: Socen
 * @date:2019/11/7 21:16
 */
@Data
public class Option {

    private String value;
    private String label;
    private String data;
    private List<Option> children;
}
