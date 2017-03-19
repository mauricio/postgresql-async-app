package helpers

import javax.inject.{Inject, Provider}

import com.github.mauricio.async.db.pool.{ConnectionPool, PoolConfiguration}
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import play.api.inject.ApplicationLifecycle

import scala.concurrent.{ExecutionContext, Future}


class ConnectionPoolProvider @Inject()(factory: PostgreSQLConnectionFactory,
                                       applicationLifecycle: ApplicationLifecycle)
                                      (implicit executionContext: ExecutionContext) extends Provider[ConnectionPool[PostgreSQLConnection]] {

  applicationLifecycle.addStopHook(() => Future(get.close))

  lazy val get: ConnectionPool[PostgreSQLConnection] = new ConnectionPool(factory, PoolConfiguration.Default)


}
