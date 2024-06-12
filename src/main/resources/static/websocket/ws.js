const socket = new WebSocket(`ws://${location.host}/wsRtLog`)
let logMessage = '';
const editor = monaco.editor.create(document.getElementById('editor'), {
    value: logMessage,
    language: 'sql',
    formatOnPaste: true,
    readOnly: true,
    minimap: {
        enabled: false,
    },
    theme: 'vs-dark',
});
function removeAnsiCodes(text) {
    // 正则表达式匹配ANSI转义序列
    const ansiRegex = /[\u001b\u009b][[()#;?]*((?:[a-zA-Z\d]*(?:;[a-zA-Z\d]*)*)?\u0007|(?:\d{1,4}(?:;\d{0,4})*)?[a-zA-Z\d]{1,4})/g;
    return text.replace(ansiRegex, '');
}
socket.onmessage = function (event) {
    // 解码
    logMessage = `${logMessage + removeAnsiCodes(event.data)}`
    editor.setValue(logMessage)
}

socket.onopen = function () {
    console.log('WebSocket connection established')
}

socket.onclose = function () {
    console.log('WebSocket connection closed')
}

socket.onerror = function (error) {
    console.error('WebSocket error:', error)
}

