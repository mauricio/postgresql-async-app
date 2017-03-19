package models

import javax.inject.Inject

import scala.concurrent.Future
import org.joda.time.LocalDate
import com.github.mauricio.async.db.{Connection, RowData}
import com.github.mauricio.async.db.util.ExecutorServiceUtils.CachedExecutionContext

/**
 * User: mauricio
 * Date: 4/26/13
 * Time: 11:44 PM
 */

object MessageRepository {
  val Insert = "INSERT INTO messages (content,moment) VALUES (?,?) RETURNING id"
  val Update = "UPDATE messages SET content = ?, moment = ? WHERE id = ?"
  val Select = "SELECT id, content, moment FROM messages ORDER BY id asc"
  val SelectOne = "SELECT id, content, moment FROM messages WHERE id = ?"
}

class MessageRepository @Inject() (pool: Connection) {

  import MessageRepository._

  def save(m: Message): Future[Message] = {

    m.id match {
      case Some(id) => pool.sendPreparedStatement(Update, Array(m.content, m.moment, id)).map {
        queryResult => m
      }
      case None => pool.sendPreparedStatement(Insert, Array(m.content, m.moment)).map {
        queryResult => new Message(Some(queryResult.rows.get(0)("id").asInstanceOf[Long]), m.content, m.moment)
      }
    }

  }

  def list: Future[IndexedSeq[Message]] = {

    pool.sendQuery(Select).map {
      queryResult => queryResult.rows.get.map {
        item => rowToMessage(item)
      }
    }

  }

  def find(id: Long): Future[Option[Message]] = {

    pool.sendPreparedStatement(SelectOne, Array[Any](id)).map {
      queryResult =>
        queryResult.rows match {
          case Some(rows) => {
            Some(rowToMessage(rows.apply(0)))
          }
          case None => None
        }
    }

  }

  private def rowToMessage(row: RowData): Message = {
    new Message(
      id = Some(row("id").asInstanceOf[Long]),
      content = row("content").asInstanceOf[String],
      moment = row("moment").asInstanceOf[LocalDate]
    )
  }

}
