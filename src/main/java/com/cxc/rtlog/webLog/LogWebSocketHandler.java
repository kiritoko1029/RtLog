package com.cxc.rtlog.webLog;

import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = Logger.getLogger(LogWebSocketHandler.class.getName());
    private PrintStream originalOut;
    private PrintStream originalErr;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Save the original System.out and System.err
        originalOut = System.out;
        originalErr = System.err;

        // Redirect System.out to both WebSocket and console
        WebSocketOutputStream webSocketOutputStreamOut = new WebSocketOutputStream(session);
        TeeOutputStream teeOutputStreamOut = new TeeOutputStream(originalOut, webSocketOutputStreamOut);
        PrintStream printStreamOut = new PrintStream(teeOutputStreamOut, true, "UTF-8");
        System.setOut(printStreamOut);

        // Redirect System.err to both WebSocket and console
        WebSocketOutputStream webSocketOutputStreamErr = new WebSocketOutputStream(session);
        TeeOutputStream teeOutputStreamErr = new TeeOutputStream(originalErr, webSocketOutputStreamErr);
        PrintStream printStreamErr = new PrintStream(teeOutputStreamErr, true, "UTF-8");
        System.setErr(printStreamErr);

        // Configure logging
        WebSocketLogHandler handler = new WebSocketLogHandler(session);
        handler.setFormatter(new SimpleFormatter());
        handler.setEncoding("UTF-8");
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.info("WebSocket connection established");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 可以处理从前端发送过来的消息
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Restore the original System.out and System.err
        if (originalOut != null) {
            System.setOut(originalOut);
        }
        if (originalErr != null) {
            System.setErr(originalErr);
        }

        // Remove WebSocketLogHandler
        for (Handler handler : logger.getHandlers()) {
            if (handler instanceof WebSocketLogHandler) {
                logger.removeHandler(handler);
            }
        }
        logger.info("WebSocket connection closed");
    }
}
