package com.spring.nebula.util;

/**
 * @author liumengjun on 2017/3/21.
 */
public enum ResultCode {
    /** 成功 */
    SUCCESS("200", "操作成功"),

    /** 失败 */
    FAIL_CODE("2", "操作失败"),

    /** 没有登录 */
    NOT_LOGIN("400", "没有登录"),

    /** 发生异常 */
    EXCEPTION("401", "发生异常"),

    /** 系统错误 */
    SYS_ERROR("402", "系统错误"),

    /** 参数错误 */
    PARAMS_ERROR("403", "参数错误 "),

    /** 不支持或已经废弃 */
    NOT_SUPPORTED("410", "不支持或已经废弃"),

    /** AuthCode错误 */
    INVALID_AUTHCODE("444", "无效的AuthCode"),

    /** 太频繁的调用 */
    TOO_FREQUENT("445", "太频繁的调用"),

    /** 未知的错误 */
    UNKNOWN_ERROR("499", "未知错误"),

    /** 检验失败 **/
    CHECKOUT_FAILURE("4001","检验失败"),

    /** 缺少参数 **/
    LACK_OF_PARAMETERS("4002", "缺少参数"),

    /** Token过期 **/
    TOKEN_EXPIRED("4003", "Token过期"),

    /** 重复生成 **/
    REPEATED_GENERATION("4004", "重复生成"),

    /** 无入库单信息 **/
    NON_WAREHOUSE_INFORMATION("4005", "无入库单信息");


    private ResultCode(String value, String msg){
        this.val = value;
        this.msg = msg;
    }

    public String val() {
        return val;
    }

    public String msg() {
        return msg;
    }

    private String val;
    private String msg;
}
