package com.cxc.rtlog.webLog;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.OutputStream;

public class WebSocketOutputStream extends OutputStream {

    private final WebSocketSession session;
    private final StringBuilder buffer = new StringBuilder();

    public WebSocketOutputStream(WebSocketSession session) {
        this.session = session;
    }

    @Override
    public void write(int b) throws IOException {
        buffer.append((char) b);
        if (b == '\n') {
            flush();
        }
    }

    @Override
    public void flush() throws IOException {
        if (!buffer.isEmpty()) {
            String message = buffer.toString();
            buffer.setLength(0);
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    @Override
    public void close() throws IOException {
        flush();
    }
}
