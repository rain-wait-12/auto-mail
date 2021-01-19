package com.auto.mail.constant;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
public class BaseResponse<T> {
    public static final Integer RESPONSE_SUCCESS_CODE = 0;
    public static final String RESPONSE_SUCCESS_MSG = "请求成功";
    public static final String RESPONSE_ERROR_MSG = "请求失败";
    private Integer code;
    private String msg;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseResponse successResponnse() {
        return new BaseResponse(RESPONSE_SUCCESS_CODE, "请求成功", new Object());
    }

    public static <T> BaseResponse successResponnse(T data) {
        return new BaseResponse(RESPONSE_SUCCESS_CODE, "请求成功", data);
    }

    public static <T> BaseResponse successResponnse(String msg, T data) {
        return new BaseResponse(RESPONSE_SUCCESS_CODE, msg, data);
    }

    public static BaseResponse errorResponnse() {
        return new BaseResponse(1, "请求失败");
    }

    public static BaseResponse errorResponnse(String msg) {
        return new BaseResponse(1, msg);
    }

    public static BaseResponse errorResponnse(Integer code, String msg) {
        return new BaseResponse(code, msg);
    }

    public static <T> BaseResponse errorResponse(String msg, T data) {
        return new BaseResponse(1, msg, data);
    }

    public static <T> BaseResponse errorResponse(Integer code, String msg, T data) {
        return new BaseResponse(1, msg, data);
    }

    public boolean isSuccessResponse() {
        return this.getCode().equals(RESPONSE_SUCCESS_CODE);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseResponse)) {
            return false;
        } else {
            BaseResponse<?> other = (BaseResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseResponse;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "BaseResponse(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }
}

