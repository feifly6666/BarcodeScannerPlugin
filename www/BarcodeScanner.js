var exec = require('cordova/exec');

var myFunc = function(){};

myFunc.prototype.scan = function () {
    exec(
        function(message){},
        function(message){},
        'BarcodeScanner', 'scan', []);
};

myFunc.prototype.show = function (msg) {
    document.getElementById("outputArea").value = document.getElementById("outputArea").value  + msg;

};

myFunc.prototype.scanSetting = function (para) {
exec(
        function(message){},
        function(message){},
        'BarcodeScanner', 'scanSetting', para);

};
var func = new myFunc();
module.exports = func;


