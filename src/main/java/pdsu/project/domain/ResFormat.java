package pdsu.project.domain;

import java.io.Serializable;

/**
 * @author yqf
 * @date 2020 上午9:13
 */
public class ResFormat implements Serializable {
    private int code;
    private String message;
    private Object data;

    public ResFormat(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResFormat(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
