package ejisan.play.i18n

import play.twirl.api.JavaScript
import play.api.libs.json.Json

object JsI18nHelper {
  val script: JavaScript = JavaScript {
    """
    |function Messages(messages,prefix){
    |  var a=Array.prototype.slice.call(arguments),s=this;
    |  s.messages=messages;a.shift();s.prefix=a;
    |  if(typeof s.messages != 'object') throw new TypeError("Invalid message object.");
    |  s.resolve=function(){
    |    return s.prefix.concat(Array.prototype.slice.call(arguments)).join('.'); };
    |  s.add=function(messages){for(m in messages){s.messages[m]=messages[m];}};
    |  s.translate=function(key){
    |    key=s.resolve(key);
    |    var a=Array.prototype.slice.call(arguments),m=s.messages[key]||key;
    |    a.shift();
    |    m=m.replace(/\{(\d+)\}/g,function(){return a[Number(arguments[1])]||'?';});
    |    return m; };
    |  s.prefixed=function(){
    |    return new (Function.prototype.bind.apply(Messages,
    |      [null].concat([s.messages,Array.prototype.slice.call(arguments)],s.prefix)));};
    |}
    """.stripMargin.trim
  }

  def messagesWithScriptJs(messages: Map[String, String], variable: String): JavaScript = JavaScript {
    s"""
    |(function(){
    |$script
    |window["$variable"] = new Messages(${Json.stringify(Json.toJson(messages))});
    |window.$$m = window["$variable"].translate;
    |}).call(this);
    """.stripMargin.trim
  }

  def messagesAdditionJs(messages: Map[String, String], variable: String): JavaScript = JavaScript {
    s"""(function(){window["$variable"].add(${Json.stringify(Json.toJson(messages))});}).call(this);"""
  }
}
