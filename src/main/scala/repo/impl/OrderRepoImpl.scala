package repo.impl

import models.Order
import repo.algebra.OrderRepo
import repo.table.OrderTable

import java.time.LocalDateTime
import scala.concurrent.Future

@Singleton
class OrderRepoImpl extends OrderRepo {
  import repo.DatabaseProfile.profile.api._

  private final val Orders = OrderTable.Orders
  private final val db = bootstrap.Bootstrap.db

  override def getById(id: Long): Future[Option[Order]] = db.run {
    Orders.filter(_.id === id)
      .result
      .headOption
  }

  override def getOrdersInInterval(start: LocalDateTime, end: LocalDateTime): Future[Seq[Order]] = db.run {
    Orders.filter { order =>
      order.registeredDateTime > start && order.registeredDateTime < end
    }.result
  }

}
