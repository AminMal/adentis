package repo.impl

import repo.algebra.ProductRepo
import repo.table.ProductTable

class ProductRepoImpl extends ProductRepo {

  import repo.DatabaseProfile.profile.api._
  val db = bootstrap.Bootstrap.db
  val Products = ProductTable.Products

}
