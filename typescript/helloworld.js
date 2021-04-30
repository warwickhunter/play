var HelloWorld = /** @class */ (function () {
    function HelloWorld() {
    }
    HelloWorld.prototype.run = function () {
        console.log("Hello world from a class");
    };
    return HelloWorld;
}());
var hw = new HelloWorld();
hw.run();
