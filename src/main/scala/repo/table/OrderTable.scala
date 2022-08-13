package repo.table

import models.Order
import slick.lifted.ProvenShape
import repo.DatabaseProfile

import java.time.LocalDateTime

object OrderTable {

  import DatabaseProfile.profile.api._

  private[repo] final class Orders(tag: Tag) extends Table[Order](tag, "orders") {
    def id:                 Rep[Option[Long]]           = column("id", O.AutoInc, O.PrimaryKey, O.Unique)
    def customerName:       Rep[String]                 = column("customer_name")
    def customerContact:    Rep[String]                 = column("customer_contact")
    def shippingAddress:    Rep[String]                 = column("shipping_address")
    def grandTotal:         Rep[Double]                 = column("grand_total")
    def registeredDateTime: Rep[Option[LocalDateTime]]  = column("registered_date_time", O.Default(Some(LocalDateTime.now())))

    override def * : ProvenShape[Order] = (
      id, customerName, customerContact, shippingAddress, grandTotal, registeredDateTime
    ) <> ((Order.apply _).tupled, Order.unapply)

  }

  val Orders: TableQuery[Orders] = TableQuery[Orders]

}
