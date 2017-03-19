package helpers

import com.github.mauricio.async.db.{Configuration, Connection}
import com.github.mauricio.async.db.pool.ConnectionPool
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import com.google.inject.AbstractModule

/**
  * Created by domdorn on 19/03/17.
  */
class PostgresModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Configuration]).toProvider(classOf[DatabaseConfigurationProvider])
    bind(classOf[PostgreSQLConnectionFactory]).toProvider(classOf[PostgreSQLConnectionFactoryProvider])
    bind(classOf[ConnectionPool[PostgreSQLConnection]]).toProvider(classOf[ConnectionPoolProvider])
    bind(classOf[Connection]).toProvider(classOf[ConnectionPoolProvider])

  }
}
