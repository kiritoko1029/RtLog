// 立即执行函数
(function () {
    document.getElementById("clearBtn").addEventListener('click', clearLog);
})();
function clearLog() {
    editor.setValue('')
}