package com.cxc.rtlog.webLog;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class WebSocketLogHandler extends Handler {

    private WebSocketSession session;
    private SimpleFormatter formatter;

    public WebSocketLogHandler(WebSocketSession session) {
        this.session = session;
        this.formatter = new SimpleFormatter();
    }

    @Override
    public void publish(LogRecord record) {
        if (session != null && session.isOpen()) {
            try {
                String message = formatter.format(record);
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void flush() {
        // No-op
    }

    @Override
    public void close() throws SecurityException {
        // No-op
    }
}
