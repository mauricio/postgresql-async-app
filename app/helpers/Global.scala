package helpers

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.postgresql.pool.ConnectionObjectFactory
import com.github.mauricio.async.db.util.URLParser
import models.MessageRepository
import play.api.{Application, GlobalSettings}
import com.github.mauricio.async.db.pool.{PoolConfiguration, ConnectionPool}

/**
 * User: mauricio
 * Date: 4/26/13
 * Time: 11:20 PM
 */
object Global extends GlobalSettings {

  private val databaseConfiguration = System.getenv("DATABASE_URL") match {
    case url : String => URLParser.parse(url)
    case _ => new Configuration(
      username = "postgres" ,
      database = Some("postgresql_async_app_development"),
      port = 5433
    )
  }
  private val factory = new ConnectionObjectFactory( databaseConfiguration )
  private val pool = new ConnectionPool(factory, PoolConfiguration.Default)
  val messagesRepository = new MessageRepository( pool )

  override def onStop(app: Application) {
    pool.close
  }

}