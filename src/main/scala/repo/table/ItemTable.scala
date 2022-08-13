package repo.table

import models.Item
import repo.DatabaseProfile
import slick.lifted.ProvenShape

import java.time.LocalDateTime

object ItemTable {

  import DatabaseProfile.profile.api._

  private[repo] final class Items(tag: Tag) extends Table[Item](tag, "items") {
    def id: Rep[Option[Long]] = column("id", O.AutoInc, O.PrimaryKey, O.Unique)

    def taxAmount: Rep[Double] = column("tax_amount")

    def shippingFee: Rep[Double] = column("shipping_fee")

    def productId: Rep[Long] = column("product_id")

    def registeredDate: Rep[Option[LocalDateTime]] = column("registered_date", O.Default(Option(LocalDateTime.now())))

    override def * : ProvenShape[Item] = (
      id, taxAmount, shippingFee, productId, registeredDate
    ) <> ((Item.apply _).tupled, Item.unapply)

  }

  val Items: TableQuery[Items] = TableQuery[Items]

}
