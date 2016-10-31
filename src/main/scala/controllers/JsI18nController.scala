package controllers

import javax.inject.{ Inject, Singleton }
import play.api.mvc.Controller
import play.api.i18n.{ I18nSupport, MessagesApi }
import ejisan.play.i18n.JsI18nSupport
import play.api.cache.CacheApi

@Singleton
class JsI18nController @Inject()(
  val cacheApi: CacheApi,
  val messagesApi: MessagesApi
) extends Controller with I18nSupport with JsI18nSupport {
  def script = JsI18nAction.script

  def messages(prefix: String = "", variable: String = "messages") =
    JsI18nAction.messages(prefix, variable)

  def additionalMessages(prefix: String = "", variable: String = "messages") =
    JsI18nAction.additionalMessages(prefix, variable)

  def messagesJson(prefix: String = "") = JsI18nAction.messagesJson(prefix)
}
