package repo.table

import models.DomainProduct
import repo.DatabaseProfile
import slick.lifted.ProvenShape

import java.time.LocalDate


object ProductTable {

  import DatabaseProfile.profile.api._

  private[repo] final class Products(tag: Tag) extends Table[DomainProduct](tag, "products") {
    def id: Rep[Option[Long]] = column("id", O.PrimaryKey, O.Unique, O.AutoInc)
    def name: Rep[String] = column("name")
    def category: Rep[Option[String]] = column("category")
    def weight: Rep[Option[Double]] = column("weight")
    def price: Rep[Double] = column("price")
    def creationDate: Rep[LocalDate] = column("creation_date")

    override def * : ProvenShape[DomainProduct] = (
      id, name, category, weight, price, creationDate
    ) <> ((DomainProduct.apply _).tupled, DomainProduct.unapply)

  }

  val Products: TableQuery[Products] = TableQuery[Products]

}
