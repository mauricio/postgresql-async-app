package controllers

import play.api.mvc.{AsyncResult, Action, Controller}
import play.api.data._
import play.api.data.Forms._
import helpers.Global.messagesRepository
import com.github.mauricio.async.db.util.ExecutorServiceUtils.CachedExecutionContext
import models.Message

/**
 * User: mauricio
 * Date: 4/27/13
 * Time: 4:17 PM
 */
object Messages extends Controller {

  val messageForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "content" -> text,
      "moment" -> jodaLocalDate
    )(Message.apply)(Message.unapply)
  )

  def index = Action {
    AsyncResult( messagesRepository.list.map {
      messages =>
        Ok(views.html.messages.index(messages))
    } )
  }

  def form = Action {
    Ok(views.html.messages.form(messageForm))
  }

  def edit( id : Long ) = Action {
    AsyncResult {
      messagesRepository.find(id).map {
        messageOption =>
          messageOption match {
            case Some(message) => {
              Ok( views.html.messages.form( messageForm.fill(message) ) )
            }
            case None => Ok( views.html.messages.form( messageForm ) )
          }
      }
    }
  }

  def update = Action { implicit request =>
    messageForm.bindFromRequest().fold(
      form => {
        BadRequest( views.html.messages.form(form) )
      },
      message => {
        AsyncResult {
          messagesRepository.save(message).map {
            message =>
              Redirect(routes.Messages.index())
          }
        }
      } )
  }

}
