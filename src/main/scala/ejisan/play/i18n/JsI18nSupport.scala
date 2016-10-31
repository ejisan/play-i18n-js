package ejisan.play.i18n

import play.api.mvc.Controller
import play.api.i18n.{ I18nSupport, MessagesApi, Lang }

import scala.concurrent.duration.Duration
import play.api.mvc.{ Action, EssentialAction }
import play.api.i18n.{ MessagesApi, Lang }
import play.api.libs.json.{ Json, JsValue }
import play.api.cache.CacheApi
import play.twirl.api.JavaScript

trait JsI18nSupport { self: Controller with I18nSupport =>
  object JsI18nAction {
    val script: EssentialAction = Action { self.Ok(JsI18nHelper.script) }

    def messages(prefix: String, variable: String): EssentialAction = Action {
      implicit request =>
      self.Ok(JsI18nHelper.messagesWithScriptJs(translateMessages(messagesApi, prefix), variable))
    }

    def additionalMessages(prefix: String, variable: String): EssentialAction = Action {
      implicit request =>
      self.Ok(JsI18nHelper.messagesAdditionJs(translateMessages(messagesApi, prefix), variable))
    }

    def messagesJson(prefix: String): EssentialAction = Action { implicit request =>
      self.Ok(Json.toJson(translateMessages(messagesApi, prefix)))
    }
  }

  def translateMessages(messagesApi: MessagesApi)
    (implicit lang: Lang): Map[String, String] =
    translateMessages(messagesApi, None)

  def translateMessages(messagesApi: MessagesApi, prefix: String)
    (implicit lang: Lang): Map[String, String] =
    translateMessages(messagesApi, Option(prefix).filter(!_.isEmpty))

  def translateMessages(messagesApi: MessagesApi, prefix: Option[String])
    (implicit lang: Lang): Map[String, String] = {
      val px = prefix.map(p => if (p.endsWith(".")) p else p + ".")
      val keys = messagesApi.messages
        .map({case (_, value)=>value.keySet})
        .flatten.toSet
      keys
        .map(key => (key -> messagesApi.translate(key, Nil)(lang)))
        .collect({ case (key, Some(value)) if px.map(key.startsWith(_)).getOrElse(true) =>
            px.map(key.stripPrefix(_)).getOrElse(key) -> value
        }).toMap
    }
}
