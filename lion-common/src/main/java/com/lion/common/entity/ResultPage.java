package com.lion.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.lion.common.constant.ResponseStatus;
import com.lion.common.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ResultPage
 * 分页结果实体类
 *
 * @author Yanzheng https://github.com/micyo202
 * @date 2019/04/13
 * Copyright 2019 Yanzheng. All rights reserved.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
public class ResultPage<T> implements Serializable {

    /**
     * 状态值
     */
    private int code = ResponseStatus.SUCCESS.code();

    /**
     * 提示信息
     */
    private String msg = "Success";

    /**
     * 分页对象
     */
    @JsonIgnore
    private T page;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 页码值
     */
    private int pageNum;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 数据结果集
     */
    private List data;

    /**
     * 时间戳
     */
    private long timestamp = DateUtil.getTimestamp();

    /**
     * 额外扩展数据
     */
    //@JsonIgnore
    private Map<String, Object> extra = null;

    /**
     * 自定义相关getter、setter方法
     */
    public int getCode() {
        return code;
    }

    public ResultPage<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultPage<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getPage() {
        return page;
    }

    public ResultPage<T> setPage(T page) {

        if (page instanceof Page) {
            Page p = (Page) page;
            this.setTotal(p.getTotalElements());
            this.setPageNum(p.getPageable().getPageNumber() + 1);
            this.setPageSize(p.getPageable().getPageSize());
            this.setPages(p.getTotalPages());
            this.setData(p.getContent());
        }

        if (page instanceof PageInfo) {
            PageInfo pageInfo = (PageInfo) page;
            this.setTotal(pageInfo.getTotal());
            this.setPageNum(pageInfo.getPageNum());
            this.setPageSize(pageInfo.getPageSize());
            this.setPages(pageInfo.getPages());
            this.setData(pageInfo.getList());
        }

        this.page = page;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ResultPage<T> setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public ResultPage<T> setExend(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    /**
     * 自定义扩展方法
     */
    public ResultPage<T> addExtra(String key, Object value) {
        if (null == this.extra) {
            this.extra = new HashMap<>(8);
        }
        this.extra.put(key, value);
        return this;
    }

    public ResultPage<T> setStatus(ResponseStatus responseStatus) {
        this.setCode(responseStatus.code());
        this.setMsg(responseStatus.msg());
        return this;
    }

    public static ResultPage status(ResponseStatus responseStatus) {
        ResultPage resultPage = new ResultPage();
        resultPage.setCode(responseStatus.code());
        resultPage.setMsg(responseStatus.msg());
        return resultPage;
    }

    public static ResultPage success() {
        return new ResultPage();
    }

    public static <T> ResultPage success(T page) {
        ResultPage resultPage = new ResultPage();
        resultPage.setPage(page);
        return resultPage;
    }

    public static <T> ResultPage success(T page, Map<String, Object> extra) {
        ResultPage resultPage = new ResultPage();
        resultPage.setPage(page);
        resultPage.setExend(extra);
        return resultPage;
    }

    public static ResultPage failure(String msg) {
        ResultPage resultPage = new ResultPage();
        resultPage.setCode(ResponseStatus.FAILURE.code());
        resultPage.setMsg(msg);
        return resultPage;
    }

    public static ResultPage failure(int code, String msg) {
        ResultPage resultPage = new ResultPage();
        resultPage.setCode(code);
        resultPage.setMsg(msg);
        return resultPage;
    }

}