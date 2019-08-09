package com.houseWork.logging.domin;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * <pre>
 * LogMessage 日志信息
 * </pre>
 *
 * @author zjw
 * @date 2019/7/24 13:39.
 */
@Data
@AllArgsConstructor
public class LogMessage {

    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;
}
