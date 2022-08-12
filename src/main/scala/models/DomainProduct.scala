package models

import java.time.LocalDate

// The name "Product" would've been misleading because of Scala's Product type
case class DomainProduct(
                        id: Option[Long] = None,
                        name: String,
                        category: Option[String],
                        weight: Option[Double],
                        price: Double,
                        creationDate: LocalDate
                        )
