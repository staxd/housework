package com.houseWork.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.houseWork.logging.domin.LogMessage;

import java.text.DateFormat;
import java.util.Date;


/**
 * <pre>
 * 定义LogFilter拦截输出日志
 * </pre>
 *
 * @author zjw
 * @date 2019/7/24 13:39.
 */
public class LogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        LogMessage loggerMessage = new LogMessage(
                event.getFormattedMessage(),
                DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr
        );
        LoggerQueue.getInstance().push(loggerMessage);
        return FilterReply.ACCEPT;
    }
}