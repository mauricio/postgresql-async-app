package controllers

import javax.inject.Inject

import com.github.mauricio.async.db.util.ExecutorServiceUtils.CachedExecutionContext
import models.{Message, MessageRepository}
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

object Messages {

  val messageForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "content" -> text,
      "moment" -> jodaLocalDate
    )(Message.apply)(Message.unapply)
  )

}

class Messages @Inject() (val messagesRepository: MessageRepository)(implicit val messagesApi: MessagesApi) extends Controller with I18nSupport  {

  import Messages._

  def index = Action.async {
    messagesRepository.list.map {
      messages =>
        Ok(views.html.messages.index(messages))
    }
  }

  def form = Action {
    Ok(views.html.messages.form(messageForm))
  }

  def edit(id: Long) = Action.async {
    messagesRepository.find(id).map {
      messageOption =>
        messageOption match {
          case Some(message) => {
            Ok(views.html.messages.form(messageForm.fill(message)))
          }
          case None => Ok(views.html.messages.form(messageForm))
        }
    }

  }

  def update = Action.async { implicit request =>
    messageForm.bindFromRequest().fold(
      form => {
        Future(BadRequest(views.html.messages.form(form)))
      },
      message => {
        messagesRepository.save(message).map {
          message =>
            Redirect(routes.Messages.index())
        }
      })
  }

}
